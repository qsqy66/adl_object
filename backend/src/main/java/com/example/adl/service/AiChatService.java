package com.example.adl.service;

import com.example.adl.bean.Device;
import com.example.adl.bean.DeviceCategory;
import com.example.adl.bean.Lab;
import com.example.adl.bean.User;
import com.example.adl.dto.ChatMessage;
import com.example.adl.dto.ChatRequest;
import com.example.adl.dto.ChatResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiChatService {
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final DeviceService deviceService;
    private final LabService labService;
    private final DeviceCategoryService categoryService;
    private final RestClient restClient;
    private final String apiKey;

    public AiChatService(
            ObjectMapper objectMapper,
            UserService userService,
            DeviceService deviceService,
            LabService labService,
            DeviceCategoryService categoryService,
            @Value("${spring.ai.openai.api-key:}") String apiKey) {
        this.objectMapper = objectMapper;
        this.userService = userService;
        this.deviceService = deviceService;
        this.labService = labService;
        this.categoryService = categoryService;
        this.apiKey = apiKey;
        this.restClient = RestClient.create("https://api.deepseek.com");
    }

    public ChatResponse chat(ChatRequest request) {
        if (request == null || request.messages() == null || request.messages().isEmpty()) {
            throw new IllegalArgumentException("聊天消息不能为空");
        }
        if (apiKey.isBlank()) {
            throw new IllegalStateException("未配置 DEEPSEEK_API_KEY");
        }

        String modelContent = callModel(request.messages());
        JsonNode result = parseJson(modelContent);
        boolean changed = execute(result.path("operation"));
        String reply = result.path("reply").asText("我暂时无法理解这条指令，请换一种方式描述。");
        return new ChatResponse(reply, changed);
    }

    private String callModel(List<ChatMessage> history) {
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", systemPrompt()));
        for (ChatMessage message : history) {
            if (message != null && message.content() != null
                    && ("user".equals(message.role()) || "assistant".equals(message.role()))) {
                messages.add(Map.of("role", message.role(), "content", message.content()));
            }
        }

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("model", "deepseek-chat");
        body.put("messages", messages);
        body.put("temperature", 0.1);

        JsonNode response = restClient.post()
                .uri("/chat/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + apiKey)
                .body(body)
                .retrieve()
                .body(JsonNode.class);
        if (response == null || !response.path("choices").isArray()
                || response.path("choices").isEmpty()) {
            throw new IllegalStateException("DeepSeek 返回了空响应");
        }
        return response.path("choices").get(0).path("message").path("content").asText();
    }

    private String systemPrompt() {
        return """
                你是实验室设备管理助手。请使用中文回答。
                你必须只输出一个合法 JSON，不要 Markdown，不要代码块：
                {"reply":"给用户看的简短答复","operation":null}
                或：
                {"reply":"给用户看的简短答复","operation":{"type":"create|update","entity":"user|category|lab|device","id":null,"data":{}}}
                只有在用户明确要求新增或修改时才生成 operation；普通查询只返回 operation:null。
                不允许删除数据，不允许执行 SQL，不允许修改 user_pwd。缺少必要字段时不要生成 operation，而是在 reply 中询问缺少的信息。
                新增 user 必须有 userName、userAccount、userPwd；新增 category 必须有 categoryName；
                新增 lab 必须有 labName、labLocation；新增 device 必须有 deviceName、categoryId、labId、deviceStatus、buyDate、price。
                修改时使用下面数据中的 id，data 只放需要修改的字段；设备 categoryId 和 labId 必须使用已有编号。
                当前数据库数据：
                """ + databaseContext();
    }

    private String databaseContext() {
        try {
            Map<String, Object> context = new LinkedHashMap<>();
            context.put("users", userService.findAll().stream().map(user -> {
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("userId", user.getUserId());
                item.put("userName", user.getUsername());
                item.put("userAccount", user.getUserAccount());
                item.put("userPhone", user.getUserPhone());
                return item;
            }).toList());
            context.put("categories", categoryService.findAll());
            context.put("labs", labService.findAll());
            context.put("devices", deviceService.findAll());
            return objectMapper.writeValueAsString(context);
        } catch (Exception exception) {
            throw new IllegalStateException("读取业务数据失败", exception);
        }
    }

    private JsonNode parseJson(String content) {
        try {
            String json = content.trim();
            int start = json.indexOf('{');
            int end = json.lastIndexOf('}');
            if (start >= 0 && end > start) {
                json = json.substring(start, end + 1);
            }
            return objectMapper.readTree(json);
        } catch (Exception exception) {
            throw new IllegalStateException("DeepSeek 返回格式无法解析", exception);
        }
    }

    private boolean execute(JsonNode operation) {
        if (operation == null || operation.isMissingNode() || operation.isNull()
                || !"create".equals(operation.path("type").asText())
                    && !"update".equals(operation.path("type").asText())) {
            return false;
        }
        String type = operation.path("type").asText();
        String entity = operation.path("entity").asText();
        JsonNode data = operation.path("data");
        Integer id = operation.path("id").isNumber() ? operation.path("id").asInt() : null;

        return switch (entity) {
            case "user" -> executeUser(type, id, data);
            case "category" -> executeCategory(type, id, data);
            case "lab" -> executeLab(type, id, data);
            case "device" -> executeDevice(type, id, data);
            default -> throw new IllegalArgumentException("不支持的业务类型");
        };
    }

    private boolean executeUser(String type, Integer id, JsonNode data) {
        if ("create".equals(type)) {
            User user = new User(null, requiredText(data, "userName"), requiredText(data, "userAccount"),
                    requiredText(data, "userPwd"), optionalText(data, "userPhone"));
            userService.add(user);
            return true;
        }
        User user = userService.findById(requiredId(id));
        if (user == null) throw new IllegalArgumentException("找不到要修改的用户");
        setIfPresent(data, "userName", user::setUsername);
        setIfPresent(data, "userAccount", user::setUserAccount);
        setIfPresent(data, "userPhone", user::setUserPhone);
        userService.update(user.getUserId(), user);
        return true;
    }

    private boolean executeCategory(String type, Integer id, JsonNode data) {
        if ("create".equals(type)) {
            categoryService.add(new DeviceCategory(null, requiredText(data, "categoryName"),
                    optionalText(data, "categoryDesc")));
            return true;
        }
        DeviceCategory category = categoryService.findById(requiredId(id));
        if (category == null) throw new IllegalArgumentException("找不到要修改的设备分类");
        setIfPresent(data, "categoryName", category::setCategoryName);
        setIfPresent(data, "categoryDesc", category::setCategoryDesc);
        categoryService.update(category.getCategoryId(), category);
        return true;
    }

    private boolean executeLab(String type, Integer id, JsonNode data) {
        if ("create".equals(type)) {
            labService.add(new Lab(null, requiredText(data, "labName"), requiredText(data, "labLocation"),
                    optionalText(data, "labRemark")));
            return true;
        }
        Lab lab = labService.findById(requiredId(id));
        if (lab == null) throw new IllegalArgumentException("找不到要修改的实验室");
        setIfPresent(data, "labName", lab::setLabName);
        setIfPresent(data, "labLocation", lab::setLabLocation);
        setIfPresent(data, "labRemark", lab::setLabRemark);
        labService.update(lab.getLabId(), lab);
        return true;
    }

    private boolean executeDevice(String type, Integer id, JsonNode data) {
        if ("create".equals(type)) {
            Device device = new Device(null, requiredText(data, "deviceName"), requiredInt(data, "categoryId"),
                    requiredInt(data, "labId"), optionalText(data, "deviceSpec"),
                    requiredText(data, "deviceStatus"), requiredDate(data, "buyDate"),
                    requiredDecimal(data, "price"), null, null);
            deviceService.add(device);
            return true;
        }
        Device device = deviceService.findById(requiredId(id));
        if (device == null) throw new IllegalArgumentException("找不到要修改的设备");
        setIfPresent(data, "deviceName", device::setDeviceName);
        setIfPresent(data, "deviceSpec", device::setDeviceSpec);
        setIfPresent(data, "deviceStatus", device::setDeviceStatus);
        if (data.has("categoryId")) device.setCategoryId(requiredInt(data, "categoryId"));
        if (data.has("labId")) device.setLabId(requiredInt(data, "labId"));
        if (data.has("buyDate")) device.setBuyDate(requiredDate(data, "buyDate"));
        if (data.has("price")) device.setPrice(requiredDecimal(data, "price"));
        deviceService.update(device.getDeviceId(), device);
        return true;
    }

    private Integer requiredId(Integer id) {
        if (id == null) throw new IllegalArgumentException("缺少要修改的数据编号");
        return id;
    }

    private String requiredText(JsonNode data, String field) {
        String value = optionalText(data, field);
        if (value == null || value.isBlank()) throw new IllegalArgumentException("缺少字段：" + field);
        return value;
    }

    private String optionalText(JsonNode data, String field) {
        return data.hasNonNull(field) ? data.path(field).asText() : null;
    }

    private Integer requiredInt(JsonNode data, String field) {
        if (!data.hasNonNull(field) || !data.path(field).canConvertToInt()) {
            throw new IllegalArgumentException("字段必须是数字：" + field);
        }
        return data.path(field).asInt();
    }

    private LocalDate requiredDate(JsonNode data, String field) {
        try {
            return LocalDate.parse(requiredText(data, field));
        } catch (Exception exception) {
            throw new IllegalArgumentException("日期格式必须是 YYYY-MM-DD");
        }
    }

    private BigDecimal requiredDecimal(JsonNode data, String field) {
        try {
            return new BigDecimal(requiredText(data, field));
        } catch (Exception exception) {
            throw new IllegalArgumentException("价格必须是数字");
        }
    }

    private void setIfPresent(JsonNode data, String field, java.util.function.Consumer<String> setter) {
        if (data.hasNonNull(field)) setter.accept(data.path(field).asText());
    }
}

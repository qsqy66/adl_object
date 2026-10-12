package com.example.adl.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private Integer userId;          // 主码 user_id
    private String userName;         // 用户姓名
    private String userAccount;      // 登录账号
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userPwd;          // 登录密码
    private String userPhone;        // 联系电话

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return userPwd;
    }

    public void setPassword(String password) {
        this.userPwd = password;
    }
}
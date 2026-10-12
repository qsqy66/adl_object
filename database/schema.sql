-- ADL laboratory device management database
-- MySQL 8.0+

CREATE DATABASE IF NOT EXISTS lab_device_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE lab_device_db;

CREATE TABLE IF NOT EXISTS `user` (
    user_id      INT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_name    VARCHAR(100) NOT NULL,
    user_account VARCHAR(100) NOT NULL,
    user_pwd     VARCHAR(255) NOT NULL,
    user_phone   VARCHAR(30) NULL,
    PRIMARY KEY (user_id),
    UNIQUE KEY uk_user_account (user_account)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS device_category (
    category_id   INT UNSIGNED NOT NULL AUTO_INCREMENT,
    category_name VARCHAR(100) NOT NULL,
    category_desc VARCHAR(500) NULL,
    PRIMARY KEY (category_id),
    UNIQUE KEY uk_category_name (category_name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS lab (
    lab_id       INT UNSIGNED NOT NULL AUTO_INCREMENT,
    lab_name     VARCHAR(100) NOT NULL,
    lab_location VARCHAR(255) NOT NULL,
    lab_remark   VARCHAR(500) NULL,
    PRIMARY KEY (lab_id),
    UNIQUE KEY uk_lab_name (lab_name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS device (
    device_id     INT UNSIGNED NOT NULL AUTO_INCREMENT,
    device_name   VARCHAR(150) NOT NULL,
    category_id   INT UNSIGNED NOT NULL,
    lab_id        INT UNSIGNED NOT NULL,
    device_spec   VARCHAR(1000) NULL,
    device_status VARCHAR(20) NOT NULL DEFAULT '正常',
    buy_date      DATE NOT NULL,
    price         DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    PRIMARY KEY (device_id),
    KEY idx_device_category_id (category_id),
    KEY idx_device_lab_id (lab_id),
    CONSTRAINT fk_device_category
        FOREIGN KEY (category_id) REFERENCES device_category (category_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT fk_device_lab
        FOREIGN KEY (lab_id) REFERENCES lab (lab_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- Example data: 10 rows per table
INSERT INTO `user`
    (user_name, user_account, user_pwd, user_phone)
VALUES
    ('管理员', 'admin', 'Admin@2026', '13800000001'),
    ('张伟', 'zhangwei', 'User@2026_01', '13800000002'),
    ('李娜', 'lina', 'User@2026_02', '13800000003'),
    ('王强', 'wangqiang', 'User@2026_03', '13800000004'),
    ('刘芳', 'liufang', 'User@2026_04', '13800000005'),
    ('陈杰', 'chenjie', 'User@2026_05', '13800000006'),
    ('赵敏', 'zhaomin', 'User@2026_06', '13800000007'),
    ('黄磊', 'huanglei', 'User@2026_07', '13800000008'),
    ('周婷', 'zhouting', 'User@2026_08', '13800000009'),
    ('吴昊', 'wuhao', 'User@2026_09', '13800000010');

INSERT INTO device_category
    (category_name, category_desc)
VALUES
    ('计算机设备', '台式机、笔记本电脑及工作站'),
    ('网络设备', '交换机、路由器及无线网络设备'),
    ('实验仪器', '用于实验测量和分析的仪器'),
    ('打印设备', '打印机、扫描仪及复印设备'),
    ('投影设备', '投影仪及相关显示设备'),
    ('存储设备', '服务器、硬盘及网络存储设备'),
    ('办公设备', '办公桌、会议设备及其他办公用品'),
    ('安防设备', '摄像头、门禁及监控设备'),
    ('电源设备', 'UPS、电源控制器及稳压设备'),
    ('其他设备', '未归入其他分类的设备');

INSERT INTO lab
    (lab_name, lab_location, lab_remark)
VALUES
    ('计算机实验室 A', '教学楼 101', '用于计算机基础课程实验'),
    ('计算机实验室 B', '教学楼 102', '用于软件开发和程序设计实验'),
    ('网络工程实验室', '实验楼 201', '用于网络配置和通信实验'),
    ('物理实验室', '实验楼 202', '用于基础物理实验'),
    ('化学实验室', '实验楼 203', '配备化学实验安全设施'),
    ('电子实验室', '实验楼 204', '用于电子电路和嵌入式实验'),
    ('多媒体教室', '教学楼 301', '配备投影和音视频设备'),
    ('数据中心机房', '信息楼 101', '存放服务器和网络设备'),
    ('创新实践室', '创新楼 201', '用于学生创新项目实践'),
    ('综合设备室', '行政楼 105', '存放备用及待维修设备');

INSERT INTO device
    (device_name, category_id, lab_id, device_spec, device_status, buy_date, price)
VALUES
    ('联想图形工作站', 1, 1, 'Intel Core i7/32GB/1TB SSD/RTX 4060', '正常', '2025-01-15', 8599.00),
    ('戴尔开发笔记本', 1, 2, 'Intel Core i5/16GB/512GB SSD', '正常', '2025-02-20', 5299.00),
    ('华为交换机', 2, 3, '24口千兆智能交换机', '正常', '2024-09-10', 2680.00),
    ('数字示波器', 3, 4, '100MHz双通道数字示波器', '正常', '2024-05-18', 3150.00),
    ('激光打印机', 4, 7, '黑白激光打印机，支持双面打印', '维修中', '2023-11-06', 1899.00),
    ('教学投影仪', 5, 7, '4000流明，1080P分辨率', '正常', '2024-03-22', 4200.00),
    ('NAS网络存储', 6, 8, '8盘位，支持RAID 5', '正常', '2025-03-12', 7680.00),
    ('高清监控摄像头', 8, 10, '400万像素，支持夜视和PoE', '正常', '2024-07-30', 680.00),
    ('UPS不间断电源', 9, 8, '10KVA在线式UPS', '闲置', '2023-08-14', 12500.00),
    ('物联网开发套件', 10, 9, 'ARM主控，含传感器和无线通信模块', '正常', '2025-04-08', 1680.00);

# 实验室文档管理系统
## 内容要求
设计并实现基于B/S的实验室文档管理系统。用户分为三种主要角色：管理员、教师、学生；该系统的主要功能包括实验开设记录管理、实验报告信息管理、上传、下载、实验报告批阅记录等。
## 数据表设计
1. 账号表
```sql
CREATE TABLE `user_account` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `account` varchar(100) NOT NULL COMMENT '用户账号',
  `user_password` varchar(255) NOT NULL COMMENT '用户密码',
  `user_role` tinyint NOT NULL COMMENT '用户角色 0-管理员 1-老师 2-学生',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account` (`account`),
  KEY `index_1` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=15000 DEFAULT CHARSET=utf16 COMMENT='用户账号表'
```
2. 实验表
```sql
CREATE TABLE `experiment` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_id` varchar(100) NOT NULL COMMENT '创建人id',
  `name` varchar(50) NOT NULL COMMENT '实验名称',
  `comment` text NOT NULL COMMENT '实验要求',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `end_time` datetime NOT NULL COMMENT '截至时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `index_1` (`create_time`),
  KEY `index_2` (`end_time`)
) ENGINE=InnoDB AUTO_INCREMENT=15000 DEFAULT CHARSET=utf16 COMMENT='实验表'
```
3. 实验报告表
```sql
CREATE TABLE `report` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_id` varchar(100) NOT NULL COMMENT '创建人id',
  `experiment_id` int NOT NULL COMMENT '实验id',
  `name` varchar(50) NOT NULL COMMENT '报告名称',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
  `file_path` varchar(100) NOT NULL COMMENT '文件路径',
  `file_meta` varchar(100) NOT NULL COMMENT '文件信息',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `index_1` (`create_time`),
  KEY `index_2` (`experiment_id`),
  KEY `index_3` (`create_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15000 DEFAULT CHARSET=utf16 COMMENT='实验报告表'
```
4. 批阅记录表
```sql
CREATE TABLE `mark` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `report_id` int NOT NULL COMMENT '报告id',
  `teacher_id` varchar(100) NOT NULL COMMENT '老师id',
  `mark` varchar(255) NOT NULL COMMENT '批阅内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '批阅时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `index_1` (`report_id`),
  KEY `index_2` (`teacher_id`),
  KEY `index_3` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=15000 DEFAULT CHARSET=utf16 COMMENT='实验报告表'
```
## 接口整理
1. 登录接口
    ```
    登录后返回token及身份信息, 前端页面根据身份信息显示菜单
    访问后端接口携带token用于鉴权
   参数:
   userId String
   password String
   返回
   token String
   tokenHead String
    ```
2. 老师开设实验
    ```
    参数:
   requestBody
   name String 实验名称
   comment String 实验要求
   endTime Date 截止时间
   返回
   创建成功
    ```
3. 查找实验
   ```
   参数:
   open boolean 是否只查找未到截止日期的
   createId String 创建人id
   name String 实验题目 模糊查询
   pageNum int 页码
   pageSize int 页大小
   返回:
   dataPage
   ```
4. 查看实验报告信息
   ```
   根据实验分类, 老师可查看自己开设实验下的报告
   学生可查看自己提交的报告
   name String 实验题目 模糊查询
   pageNum int 页码
   pageSize int 页大小
   ```
5. 学生上传实验报告
   ```
   
   ```
6. 下载实验报告接口
    ```
    接口进行鉴权, 学生只能下载自己的实验报告, 
    老师可以下载自己开设的实验下的报告
    时间关系未进行鉴权
    ```
7. 实验报告批阅
   ```
   参数
   id int 报告id
   mark String 批阅内容
   ```
8. 学生查看批阅记录
   ```  
   参数
   ids String 报告id 1,2,3,4,5
   ```
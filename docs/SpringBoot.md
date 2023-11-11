# SpringBoot3

## 一、SpringBoot介绍

### 1.1 概述

- SpringBoot是Spring提供的一个子项目，用于快速构建Spring应用程序

- 传统方式构建Spring应用程序的**缺点**

  - 导入依赖繁琐

    ![](../assets/Snipaste_2023-11-11_21-35-19.png)

  - 项目配置繁琐

    ![](../assets/Snipaste_2023-11-11_21-35-41.png)

### 1.2 SpringBoot特性

- **起步依赖**

  - 本质就是一个Maven坐标，整合了完成一个功能需要的所有坐标

    <img src="../assets/Snipaste_2023-11-11_21-37-24.png" style="zoom:50%;" />

- **自动配置**

  - 遵循约定大约配置的原则，在SpringBoot程序启动后，一些bean对象会自动注入到IOC容器，不需要手动声明，简化开发

- 内嵌的Tomcat、Jetty（无序部署WAR文件）

- 外部化配置

- 不需要XML配置（properties/yml）

### 1.3 SpringBoot入门程序

1. 创建Maven工程
2. 导入spring-boot-starter-web起步以来
3. 编写Controller
4. 提供启动类
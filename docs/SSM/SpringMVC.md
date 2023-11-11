# SpringMVC

## 一、SpringMVC简介

### 1.1 概述

- SpringMVC是一种基于Java实现MVC模型的轻量级Web框架
- 有点
  - 使用简单，开发便捷（相比于Servlet）
  - 灵活性强

### 1.2 入门案例

#### 1.2.1 导入SpringMVC坐标与Servlet坐标

```xml
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>3.1.0</version>
    <scope>provided</scope>
</dependency>

<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.2.10.RELEASE</version>
</dependency>
```

#### 1.2.2 创建SpringMVC控制器类（等同于Servlet功能）

```java
@Controller
public class UserController {
    @RequestMapping("/save")
    @ResponseBody
    public String save(String name) {
        System.out.println("name" + name);
        return "{'info':'springmvc'}";
    }
}
```

#### 1.2.3 初始化SpringMVC环境（同Spring环境），设定SpringMVC加载对应的bean

```java
@Configuration
@ComponentScan("com.buaa")
public class SpringConfig {
}
```

#### 1.2.4 初始化Servlet容器，加载SpringMVC环境，并设置SpringMVC技术处理的请求

```java
public class ServletContainersInitConfig extends AbstractDispatcherServletInitializer {
    //加载SpringMVC配置
    @Override
    protected WebApplicationContext createServletApplicationContext() {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(SpringConfig.class);
        return ctx;
    }

    //设置哪些请求归属springMVC处理
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    //加载Spring容器的配置
    @Override
    protected WebApplicationContext createRootApplicationContext() {
        return null;
    }
}
```

### 1.3 入门程序总结

**1+N**

- 一次性工作
  - 创建工程，导入坐标
  - 创建web容器启动类，加载SpringMVC配置，设置SpringMVC请求拦截路径
  - SpringMVC核心配置类（设置配置类，扫描controller包，加载controller控制器bean）
- 多次工作
  - 定义处理请求的控制器类
  - 定义处理请求的控制器方法，并配置映射路径（`@RequestMapping`）与返回json数据（`@ResponseBody`）


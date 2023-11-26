# SpringBoot注解总结

## 读取配置相关

### @Value

```java
@Value("${server.port}")
Integer port;
```



### @ConfigurationProperties

## IOC/DI相关

### @AutoWired

### @Qualifier

### @Component

### @Repository

### @Service

### @Controller

## 配置相关

### @Configuration

### @Import

## SpringMVC相关

### @RestController = @Controller + @ResponseBody

### @RequestMapping

### @RequestBody

### @RequestParam

### @GetMapping、@PostMapping、@DeletMapping、@PutMapping

## lombok相关

### @Data

### @NoArgsConstructor

### @AllArgsConstructor

## 参数校验相关

### 具体步骤

- 导入validation坐标
- 在参数上添加`@Pattern`注解，指定校验规则
- 在Controller类上添加`@Validated`注解
- 在全局异常处理器中处理参数校验失败的异常

```java
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,
                           @Pattern(regexp = "^\\S{5,16}$") String password) {
        User resultUser = userService.findByUsername(username);
        if (resultUser == null) {
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.error("用户已存在！");
        }
    }
}
```

全局异常处理

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage()) ?
                e.getMessage() : "操作失败");
    }
}
```

### @Pattern

### @Validated

## 全局异常处理

### @RestControllerAdvice

| 名称 | @RestControllerAdvice              |
| ---- | ---------------------------------- |
| 类型 | **类注解**                         |
| 位置 | Rest风格开发的控制器增强类定义上方 |
| 作用 | 为Rest风格开发的控制器类做增强     |

### @ExceptionHandler

| 名称 | @ExceptionHandler                                            |
| ---- | ------------------------------------------------------------ |
| 类型 | **方法注解**                                                 |
| 位置 | 专用于异常处理的控制器方法上方                               |
| 作用 | 设置指定异常的处理方案，功能等同于控制器方法，<br/>出现异常后终止原始控制器执行,并转入当前方法执行 |

### 示例

```java
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex) {
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        String message = ex.getMessage();
        if (message.contains("Duplicate entry")) {
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + MessageConstant.ALREADY_EXISTS;
            return Result.error(msg);
        } else {
            return Result.error("未知错误");
        }
    }
}
```

## 拦截器相关

### 定义拦截器

自定义类实现**`HandlerInterceptor`**接口，重写其方法（类上需要加`@Component`注解）

- `preHandle`方法：目标资源方法执行前执行。 返回true：放行 返回false：不放行
- `postHandle`方法：目标资源方法执行后执行
- `afterCompletion`方法：视图渲染完毕后执行，最后执行

```java
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle");
        String token = request.getHeader("authorization");
        try {
            JwtUtils.parseToken(token);
            // 放行
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            // 不放行
            return false;
        }
    }
}
```

### 注册配置拦截器

实现`WebMvcConfigurer`接口，并重写`addInterceptors`方法

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/user/login","/user/register");
    }
}
```


# SpringBoot注解总结

## 读取配置文件相关

### @Value

| 名称 | @Value                                                   |
| ---- | -------------------------------------------------------- |
| 类型 | **属性注解**  或  方法注解（了解）                       |
| 位置 | **属性定义上方**  或  标准set方法上方  或  类set方法上方 |
| 作用 | 为  基本数据类型  或  字符串类型  属性设置值             |
| 属性 | value（默认）：要注入的属性值                            |

- 使用 `@Value` 注解来注入属性，所属的类通常需要是一个Spring管理的bean，使用`@Component`, `@Service`, `@Repository`, 或 `@Controller` 来标记这个类
- 如果 `@Value` 注解用在非 Spring 管理的普通类上，这个注解不会自动生效

```java
@Component
public class App {
    @Value("${server.port}")
    private Integer port;
}
```

### @ConfigurationProperties

```java
@Component
@ConfigurationProperties(prefix = "server")
public class App {
    private Integer port;
}
```

## IOC/DI相关

### @AutoWired

### @Qualifier

## AOP相关

### @Aspect

### @Pointcut

### @Before

### @After

### @Around

### @AfterReturning

### @AfterThrowing

## 配置相关

### @Configuration

### @Import

## SpringMVC相关

### @RestController

### @RequestMapping

### @RequestBody

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

示例

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


# SpringBoot注解总结

## 读取配置文件相关

### @Value

### @ConfigurationProperties

## IOC/DI相关

### @AutoWired

### @Qualifier

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

## 参数校验

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


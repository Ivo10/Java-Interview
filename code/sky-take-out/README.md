# 项目文档
## day03
### 1. 公共字段自动填充

- 使用Spring中的AOP技术实现公共字段的自动填充，包括执行Insert操作时填充create_user、create_time、update_user、update_time，执行update_user、update_time；
- 涉及到的知识点
  - 自定义注解：使用自定义注解来修饰哪些方法是连接点，即需要被增强的方法；
  - 枚举类；自定义注解中value的返回类型为枚举类型，用来表示该方法时update还是insert，从而确定需要填充的字段；
  - AOP的书写流程，切面表达式，@Before、@Around等等
  - 反射：使用反射获取注解中的value，即操作类型，以及方法的参数，即实体对象；使用反射调用相关set方法设置填充的字段；

### 2. 菜品图片上传

- 使用的是阿里云OSS服务，已经定义好了`AliOssUtil`工具类，调用其方法即可；
- 涉及到的知识点：
  - 配置第三方bean：在配置类（`@Configuration`）中，定义一个返回值为第三方bean类型的方法，使用`@Bean`修饰即可；
  - 为第三方bean注入资源：设置上述方法的形参；
### 3. 新增菜品

- 新增一个菜品，需要向菜品表中添加一条数据，向口味表中添加n条数据 
- Spring事务管理：添加菜品的功能涉及到向菜品表和口味表两张表的添加，需要事务管理
- 添加菜品，需要返回其dish表新增数据的id，使用`useGeneratedKeys="true" keyProperty="id"`进行配置
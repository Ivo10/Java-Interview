# Java基础

## 包装类

### Integer的享元机制

```java
Integer a = 100;
Integer b = 100;
System.out.println(a == b);  //true
System.out.println(a.equals(b)); //false
```

原因：Integer用到了享元模式的设计，它针对与[-128, 127]的数字做了缓存。使用`Integer a = 100`进行赋值操作时，它默认使用`valueOf`进行自动装箱，从而触发了缓存机制，使得`a`和`b`指向了相同的内存地址。

<img src="D:\资料\Java八股\assets\image-20230925210521896.png" style="zoom:67%;" />



### Integer和int的区别

1. 作为成员变量，Integer类型的初始值是null，int类型的初始值为0；
2. Integer存储在堆内存中，而int类型存储在栈空间；
3. Integer是对象类型，它封装了很多的方法和属性，我们在使用时候更加灵活。

## 面向对象

### static方法可以被继承吗

1. 在Java中，静态方法可以被继承，但不能被重写(override)。
   当子类继承父类时，会继承父类中的**所有静态方法和静态变量**。子类可以通过类名直接调用父类的静态方法，也可以通过子类名直接调用子类的静态方法。
2. 然而，静态方法不具有多态性，因此不能被子类重写(`override`)。在子类中声明一个与父类中静态方法名称、参数列表和返回类型相同的静态方法，不会覆盖父类的静态方法。在子类中可以重新实现同名的静态方法，但此时父类中的静态方法仍然可以通过父类名调用。
3. 总之，静态方法是属于类的，不是属于实例的，因此它们不需要通过实例化对象来调用，子类可以继承父类中的所有静态方法，但不能重写(override)它们。

### 对象的内存分析

分析以下代码的内存存放结构：

```java
public class TestChinese {
    public static void main(String[] args) {
        Chinese.country = "中国";
        
        Chinese c1 = new Chinese();
        c1.name = "谷哥";
        c1.age = 32;
        
        Chinese c2 = new Chinese();
        c2.name = "谷姐";
        c2.age = 32;
    }
}
```

<img src="D:\资料\Java八股\assets\image-20230925171652123.png" alt="image-20230925171652123" style="zoom:67%;" />

### String类

#### 为什么JDK9要将String的底层实现由char[]改为byte[]?

- 节省内存空间：byte占一个字节，char占用两个字节；
- 如果一个字符串只包含英文字符或者ASCII字符，那么只用一个字节就可以表示所有字符；
- 将`char[]`改为`byte[] + encoding flag field`

## 反射

### 谈一谈对Class类的理解

- 针对于编写好的`.java`源文件进行编译（`javac.exe`），会生成一个或多个`.class`字节码文件；
- 接着，我们使用`java.exe`命令对指定的`.class`文件进行解释运行，在解释运行过程中，我们需要将`.class`字节码文件**加载**到内存中。加载到内存中的`.class`文件对应的结构即为`Class`的一个实例（类的加载过程见JVM篇）。

### 获取Class实例的四种方式

1. ```java
   Class<String> stringClass = String.class;
   ```

2. ```java
   Class<?> stringClass = Class.forName("java.lang.String");
   ```

3. ```java
   String s = "hello";
   Class<? extends String> stringClass3 = s.getClass();
   ```

4. ```java
   Class<?> testClass = ClassLoader.getSystemClassLoader().loadClass("jvm.Test"); // s
   ```


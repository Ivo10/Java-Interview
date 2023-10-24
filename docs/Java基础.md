# Java基础

## 基础

### 方法重载

1. **两同一不同**：同一个类、相同的方法，参数列表不同（参数个数不同、参数类型不同）

   **注意**：方法重载与参数的名、权限修饰符、返回类型都没有关系

2. **如何判断两个方法是相同的呢（编译器如何确定调用的某个具体的方法呢？）**

   先通过方法名确定了一波重载的方法，进而通过不同的形参列表，确定具体的某一个方法。

3. **方法重载——编译时多态**：在编译时确定调用的具体是哪个方法（`.class`字节码中就确定好调用哪个方法了）

### 可变个数形参（jdk5.0）

1. **使用场景**：在用方法时，出现方法形参的类型是确定的，但参数的个数不确定。此时就可以使用可变个数形参的方法。

2. **说明**：

   - 可变个数形参的方法与同一个类中，同名的多个方法之间可以构成重载，**优先匹配固定参数的方法**

     - 特例：

       ```java
       public void print(int...nums) {
           
       }
       
       // error:不构成重载
       public void print(int[] nums) {
           
       }
       ```

   - 可变个数的形参必须声明在形参列表的最后（Vararg parameter must be the last in the list）

   - 可变个数的形参在一个方法的形参列表中最多只有一个

   - **Java 的可变参数编译后实际会被转换成一个数组**

### 包装类

#### Integer的享元机制

```java
Integer a = 100;
Integer b = 100;
System.out.println(a == b);  //true
System.out.println(a.equals(b)); //false
```

原因：Integer用到了享元模式的设计，它针对与**[-128, 127]**的数字做了缓存。使用`Integer a = 100`进行赋值操作时，它默认使用`valueOf`进行自动装箱，从而触发了缓存机制，使得`a`和`b`指向了相同的内存地址。

<img src="../assets/image-20230925210521896.png" style="zoom:67%;" />



#### Integer和int的区别

1. 作为成员变量，Integer类型的初始值是null，int类型的初始值为0；
2. Integer存储在堆内存中，而int类型存储在栈空间；
3. Integer是对象类型，它封装了很多的方法和属性，我们在使用时候更加灵活。

## 面向对象

### final关键字的使用

1. **final修饰类**：表示此类不能被继承，如`String`、`StringBuffer`、`StringBuilder`；

2. **final修饰方法**：表示此方法不能被重写，如`Object`类中的`getClass()`；

3. **final修饰变量**：

   - **final修饰成员变量**：必须进行赋值（不会被默认初始化）
     - 显式赋值
     - 代码块中赋值
     - 构造器中赋值
   - **final修饰局部变量**：一旦赋值就不能修改
     - 方法内声明的局部变量，在调用局部变量时，一定需要赋值。而且一旦赋值，就不可更改；
     - 方法的形参，在调用此方法时，给形参进行赋值。而且一旦赋值，就不可更改；

4. **final和static搭配**：修饰成员变量时，此成员变量称为**全局常量**

   如`Math`中的`PI`

### 接口

1. 可以声明：

   - **属性**：必须使用`public static final`修饰（可以省略）
   - **方法**：
     - `jdk8`之前，声明抽象方法，修饰为`public abstract`（可以省略）
     - `jdk8`：声明**静态方法**、默认方法
     - `jdk9`：声明**私有方法**

   不可以声明：构造器、代码块等
   
2. **接口中声明静态方法**：接口中声明的静态方法只能被接口来调用，不能被其实现类进行调用

   ```java
   public interface CompareA {
       public static void method1() {
           System.out.println("CompareA...");
       }
   }
   
   class SubClass implements CompareA {
       public static void main(String[] args) {
           CompareA.method1();
           SubClass.method1(); //error
       }
   }
   ```

3. **默认方法**：接口中声明的默认方法可以被实现类继承。实现类在没有重写此方法的情况下，默认调用接口中声明的默认方法；重写了即调用自己重写的方法。

   ```java
   public interface CompareA {
       public default void method2() {
           System.out.println("CompareA::method2");
       }
   }
   
   class SubClass implements CompareA {
       public static void main(String[] args) {
           CompareA.method1();
           SubClass subClass = new SubClass();
           subClass.method2();
       }
   }
   ```

4. **接口冲突**：类实现了两个接口，而两个接口中定义了**同名同参数的默认方法**（不是默认方法就不会报错），如果实现类没有重写该默认方法则报错。要求**必须重写该默认方法**。****

   ```java
   public class Test implements A, B{ //error
   }
   
   interface A {
       public default void method1() {
   
       }
   }
   
   interface B {
       public default void method1() {
   
       }
   }
   ```

5. **类优先原则**：子类继承了父类并实现了接口，父类和接口中声明了同名同参数的方法（接口中是默认方法）。子类如果没有重写此方法，调用的是父类中的方法。

   ```java
   public class Test extends SuperClass implements A {
       public static void main(String[] args) {
           Test test = new Test();
           test.method1(); //SuperClass:method1
       }
   }
   
   interface A {
       public default void method1() {
           System.out.println("A:method1");
       }
   }
   
   class SuperClass {
       public void method1() {
           System.out.println("SuperClass:method1");
       }
   }
   ```

   补充：如果接口A中的method1方法不是默认方法，那么也不会报错。因为继承自SuperClass 的method1方法可以当作对接口A中抽象方法的重写。

6. **在实现类中调用接口中的默认方法**：

   ```java
   public class Test implements A, B {
       @Override
       public void method1() {
           System.out.println("A:method1");
       }
   
       public void method() {
           method1();
           A.super.method1();
           B.super.method1();
       }
   
       public static void main(String[] args) {
           Test test = new Test();
           test.method();
       }
   }
   
   interface A {
       default void method1() {
           System.out.println("A:method1");
       }
   }
   
   interface B {
       default void method1() {
           System.out.println("B:method1");
       }
   }
   ```

7. **私有方法**：private子类不可见

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

<img src="../assets/image-20230925171652123.png" alt="image-20230925171652123" style="zoom:67%;" />

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


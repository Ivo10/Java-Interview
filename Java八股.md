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

<img src="./assets/image-20230925210521896.png" style="zoom:67%;" />



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

<img src="./assets/image-20230925171652123.png" alt="image-20230925171652123" style="zoom:67%;" />

# JVM

## 基础概念

### JVM是什么？有什么好处？

**Java Virtual Machine**：Java程序的运行环境（Java二进制字节码的运行环境）

好处：

- 一次编写，到处运行
- 自动内存管理，垃圾回收机制

<img src="./assets/image-20230925194315368.png" alt="image-20230925194315368" style="zoom: 67%;" />

### JVM由哪几部分组成？运行流程是什么？

![image-20230925194507567](./assets/image-20230925194507567.png)

**类加载机制**：类的数据从Class文件加载到内存，并对数据进行校验、转换解析和初始化，最终形成可以被虚拟机直接使用的Java类型（《深入理解JVM》）

## 内存管理

### 什么是程序计数器？

- 程序计数器记录正在执行的字节码指令的地址。它是线程私有的，每个线程一份，内部保存字节码的行号；
- 唯一一个不会出现 `OutOfMemoryError` 的内存区域，它的生命周期随着线程的创建而创建，随着线程的结束而死亡。

### 使用PC寄存器存储字节码指令地址有什么用呢？（为什么使用PC寄存器记录当前线程地执行地址？）

因为CPU需要不停地切换各个线程，这时候切换回来以后，就得知道接着从哪开始继续执行。

JVM的字节码解释器就需要通过改变PC寄存器的值来明确下一条应该执行什么样的字节码指令。

### 什么是虚拟机栈？

虚拟机栈描述的是**Java方法执行的线程内存模型**。

### 栈帧中存储有什么？

- **局部变量表**（Local Varibales）
- **操作数栈**（Operand Stack）
- 动态链接（Dynamic Linking）（或指向运行时常量池的方法引用）
- 方法返回地址（Return Address）（或方法正常退出或者异常退出的定义）
- 一些附加信息

### 关于局部变量表

- 存放**方法参数**、定义在方法体内的**局部变量**；
- 局部变量表中存储空间的单位为**局部变量槽**（slot），局部变量表所需的内存空间在**编译期间完成分配**；
- 如果当前帧是构造方法或者实例方法创建的，那么index为0的slot处将存放该对象引用`this`；
- 局部变量表中的变量不存在系统初始化的过程。一旦定义了局部变量必须**人为初始化值**，否则无法使用。

### 虚拟机栈中可能出现的异常

Java虚拟机规范允许Java栈的大小时动态的或者时固定不变的

- 如果用**固定大小**的Java虚拟机栈，那每一个线程的Java虚拟机栈容量可以在线程创建的时候独立选定。如果线程请求分配的栈容量超过Java虚拟机栈允许的最大容量，Java虚拟机就会抛出一个**`StackOverflowError`**异常
- 如果Java虚拟机栈**可以动态扩展**，并且在尝试扩展的时候无法申请到足够的内存，或者在创建新的线程时没有足够的内存去创建对应的虚拟机栈，那Java虚拟机将会抛出一个**`OutofMemoryError`**异常

### 方法内的局部变量是否线程安全？

- 如果方法内局部变量没有逃离方法的作用范围，它是线程安全的；
- 如果局部变量引用了对象，并逃离方法的作用范围，需要考虑线程安全问题。

### ++i 和 i++

```java
int j = i++ + ++i;
```

- i++: 先从**局部变量表**中load到**操作数栈**中，再把局部变量表中的值加1；
- ++i: 先在局部变量表中将i加1，再load到操作数栈中。

### 为什么要把Java堆分代？

- 经研究，不同对象的生命周期不同，70%~99%的对象是临时对象；
- 分代的唯一理由是**优化GC性能**；
- 分代将新创建的对象放到某一地方，当GC的时候就会先把这部分“朝生夕死”的对象区域进行垃圾回收。

<img src="./assets/image-20230925111911867.png" alt="image-20230925111911867" style="zoom: 67%;" />

### 解释一下方法区？

- 方法区（Method Area）是各个线程共享的内存区域；
- 主要存储类的信息、运行时常量池（类型信息、常量、静态变量、即时编译器编译后的代码缓存等数据）；
- 虚拟机启动的时候创建，关闭虚拟机时释放；
- 如果方法区域中内存无法满足分配请求，则会抛出`OutOfMemoryError:Metaspace`

### JDK中方法区的演进

| 时间           | 变化                                                         |
| :------------- | ------------------------------------------------------------ |
| `jdk1.6`及以前 | 有永久代（permanent generation），静态变量存放在永久代上     |
| `jdk1.7`       | 有永久代，但已经逐步“去永久代”，字符串常量池、静态变量移除，保存在堆中 |
| `jdk1.8`及以后 | 无永久代，类型信息、字段、方法、常量保存在本地内存元空间中，但字符串常量池、静态变量仍在堆 |

<img src="./assets/image-20230925191604365.png" alt="image-20230925191604365" style="zoom:50%;" />

<img src="./assets/image-20230925191629614.png" alt="image-20230925191629614" style="zoom: 50%;" />

<img src="./assets/image-20230925191655309.png" alt="image-20230925191655309" style="zoom:50%;" />

### 为什么要用元空间代替永久代？

- 整个永久代有一个JVM本身设置的固定大小上限，无法进行调整，而元空间使用的是本地内存，受本机可用内存的限制，虽然元空间仍旧可能溢出，但是比原来出现的几率会更小；

  > 当元空间溢出时会得到如下错误：`java.lang.OutOfMemoryError: MetaSpace`

- 元空间里面存放的是类的元数据，这样加载多少类的元数据就不由 `MaxPermSize` 控制了, 而由系统的实际可用空间来控制，这样能加载的类就更多了；

- 在 JDK8，合并 HotSpot 和 JRockit 的代码时, JRockit 从来没有一个叫永久代的东西，合并之后就没有必要额外的设置这么一个永久代的地方了。

### 介绍一下运行时常量池

- **常量池**：可以看作是一张表，虚拟机指令根据这张表找到要执行的类名、方法名、参数类型、字面量等信息；
- 当类被加载，它的常量池信息就会放入**运行时常量池**，并把里面的符号地址变为真实地址。

![image-20230925204815190](./assets/image-20230925204815190.png)

## 垃圾回收

### 对象什么时候被垃圾回收？

如果一个或多个对象没有任何引用指向它了，那么这个对象现在就是垃圾。如果定位了垃圾，则有可能会被垃圾回收器回收。

定位垃圾的方式有两种：

- 引用计数法
- 可达性分析算法

### 引用计数法的优缺点

**优点**：实现简单，垃圾对象便于辨识；判定效率高，回收没有延迟性

**缺点**

- 需要单独的字段存储计数器，增加了存储空间开销
- 每次复制需要更新计数器，增加了时间开销
- **无法处理循环引用的情况**

<img src="./assets/Snipaste_2023-10-08_16-38-40.png" style="zoom: 67%;" />

### GC Roots有哪些？

- 虚拟机栈（栈帧中的本地变量表）中引用的对象
- 方法区中类静态属性引用的对象
  - 如Java类的引用类型静态变量
- 方法区中常量引用的对象
  - 如字符串常量池里的引用
- 本地方法栈中JNI引用的对象

<img src="./assets/Snipaste_2023-10-08_16-43-03.png" style="zoom:67%;" />

### 对象的finalization机制

`finalize()`方法是`Object`类提供的方法。在GC准备释放对象所占用的内存空间之前，它将首先调用`finalize()`方法（也可能不调用）

1. 如果对象`objA`到GC Roots没有引用链，则进行**第一次标记**；
2. 进行筛选，判断`objA`有无必要执行`finalize()`方法：
   - `objA`没有重写`finalize()`或`finalize()`已经被虚拟机调用过，则虚拟机视为“没有必要执行”；
   - 如果对象重写了`finalize()`方法且未被执行过，那么`objA`会被插入到`F-Queue`队列中，由一个虚拟机自动创建的、低优先级的`Finalizer`线程触发其`finalize()`方法执行；
   - 稍后GC会对F-Queue队列中的对象进行**第二次标记**，判断其是否进行回收（是否逃脱死亡成功，略）。`finalize()`不会被再次调用了。

### 解释Minor GC、Major GC、Full GC

GC按照**回收区域**分为两大种类型：**部分收集**（Partial GC）和**整堆收集**（Full GC）

部分收集

- **新生代收集**（Minor GC / Young GC）
  - 年轻代中的Eden代满，会触发Minor GC；这里的年轻代满指的是Eden代满，Survivor满不会引发GC；
  - 由于Java对象大多都具备**朝生夕灭**的特性，故Minor GC回收速度快，较频繁；
  - Minor GC会引发STW，暂停其他用户线程；垃圾回收结束，用户线程恢复。
- **老年代收集**（Major GC / Old GC）
  - 对象从老年代消失时，Major GC 发生；
  - Major GC通常会伴随至少一次的Minor GC（老年代空间不足时，先Minor GC。之后空间还不足，则Major GC）；
  - Major GC的速度一般比Minor GC慢十倍以上，STW时间更长；
  - Major GC后内存还不足，就OOM了。
- **Full GC**

## 类加载子系统

### 什么是类加载器，类加载器有哪些？

1. JVM只会运行二进制文件，类加载器的作用就是**将字节码文件加载到JVM中**，从而让Java程序能够启动起来；
2. 类加载器有哪些？
   - 启动类加载器（BootStrap ClassLoader）：
     - 使用C/C++语言实现，嵌套在JVM内部；
     - 加载Java的核心库（`<JAVA_HOME>lib`目录）;
     - 不继承自`java.lang.ClassLoader`，没有父加载器；
   - 扩展类加载器（Extension ClassLoader）：
     - 加载`<JAVA_HOME>lib\ext`目录中的类库；
     - 派生于`ClassLoader`类，父加载器为启动类加载器；
   - 应用程序类加载器（AppClassLoader）：
     - 用于加载用户类路径（ClassPath）上所有的类库；
     - 是程序中默认的类加载器；
   - 自定义类加载器

### 什么是双亲委派模型？

- 如果一个类加载器收到了类加载的请求，它首先不会自己去尝试加载这个类，而是把**这个请求委派给父类加载器去完成**；

- 每一个层次的类加载器都是如此，因此所有的加载请求最终**都应该传送到顶层的启动类加载器中**；
- 只有当父加载器反馈自己无法完成这个加载请求（它的搜索范围中没有找到所需的类）时，子加载器才会尝试自己去加载。

<img src="./assets/Snipaste_2023-10-07_20-26-57.png" style="zoom:80%;" />

### JVM为什么采用双亲委派机制？

- 通过双亲委派机制可以**避免类的重复加载**。当父类已经加载后则无需重复加载，保证唯一性；
- 保护程序安全，防止核心API被随意篡改。

### 说一下类装载的执行过程？

![](./assets/Snipaste_2023-10-07_20-45-46.png)

#### 加载

查找和导入class文件

- 通过一个类的全限定名来获取定义此类的二进制字节流；
- 将这个字节流所代表的静态存储结构转化为方法区的运行时数据结构；
- 在内存中生成一个代表这个类的`java.lang.Class`对象，作为方法区这个类的各种数据的访问入口。

#### 验证

保证加载类的准确性

- 文件格式验证
- 元数据验证
- 字节码验证
- 符号引用验证

![](./assets/Snipaste_2023-10-07_20-49-53.png)

#### 准备

为类变量分配内存并设置类变量初始值

- 仅包括类变量，不包括实例变量；
- 在方法区中进行分配；
- `static`变量，分配空间在准备阶段完成（设置默认值），赋值在初始化阶段完成；
- `static`变量是`final`的基本类型，以及字符串常量，值以确定，赋值在准备阶段完成；
- `static`变量是`final`的引用类型，赋值在初始化阶段完成。

#### 解析

把类中的符号引用转换为直接引用

#### 初始化

- 初始化阶段就是执行类构造器`<clinit>()`方法的过程
- 对类的静态变量，静态代码块执行初始化操作

# 多线程

### 创建多线程的方式及其优缺点？

1. **继承`Thread`类**

   `MyThread`类：

   ```java 
   public class MyThread extends Thread{
       @Override
       public void run() {
           for (int i = 0; i < 10; i++) {
               int random = (int) (Math.random() * 1000);
               try {
                   Thread.sleep(random);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               System.out.println("run" + Thread.currentThread().getName());
           }
       }
   }
   ```

   `Test`类：

   ```java
   public class Test {
       public static void main(String[] args) {
           MyThread myThread = new MyThread();
           myThread.setName("myThread");
           myThread.start();
           for (int i = 0; i < 10; i++) {
               int random = (int) Math.random();
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               System.out.println("run=" + Thread.currentThread().getName());
           }
       }
   }
   ```

2. **实现`Runnable`接口**

   `MyRunnable`类：

   ```java
   public class MyRunnable implements Runnable{
       @Override
       public void run() {
           System.out.println("运行中!");
       }
   }
   ```

   `Test`类：

   ```java
   public class Test {
       public static void main(String[] args) {
           MyRunnable myRunnable = new MyRunnable();
           Thread thread = new Thread(myRunnable);
           thread.start();
           System.out.println("运行结束!");
       }
   }
   ```

   实现`Runnable`接口的好处
   
   - 避免了单继承的局限性；
   - 更适合处理有共享资源的情况。

### 分析下列程序执行结果

```java
public class MyThread extends Thread {
   public MyThread() {
       System.out.println("MyThread---begin");
       System.out.println("Thread.currentThread().getName(): "
               + Thread.currentThread().getName());
       System.out.println("this.getName(): " + this.getName());
       System.out.println("MyThread---end");
   }

    @Override
    public void run() {
        System.out.println("MyThread---begin");
        System.out.println("Thread.currentThread().getName(): "
                + Thread.currentThread().getName());
        System.out.println("this.getName(): " + this.getName());
        System.out.println("MyThread---end");
    }
}
```

```java
public class Test {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        Thread thread = new Thread(myThread);
        thread.setName("A");
        thread.start();
    }
}
```

**运行结果：**

```
MyThread---begin
Thread.currentThread().getName(): main
this.getName(): Thread-0
MyThread---end
MyThread---begin
Thread.currentThread().getName(): A
this.getName(): Thread-0
MyThread---end
```

重点理解`Thread`类中的`run()`方法的调用：

<img src="./assets/Snipaste_2023-09-29_22-26-46.png" style="zoom: 67%;" />

### 线程中操作系统层面的5种状态和Java API层面的6种状态

1. **操作系统层面**

   <img src="./assets/Snipaste_2023-10-01_16-07-34.png" style="zoom:50%;" />

2. **Java API层面**

   <img src="./assets/Snipaste_2023-10-01_16-08-37.png" style="zoom:50%;" />

   - `NEW`、`TERMINATED`和操作系统层面的相似；
   - `RUNNABLE`为调用了`start()`方法后，该状态涵盖了操作系统层面的**可运行状态**、**运行状态**、**阻塞状态**；
   - `BLOCKED`、`WATING`、`TIMED_WAITING`是Java API层面对**阻塞状态**的细分。

### 线程安全的三个方面

1. **原子性**：互斥访问，同一个时刻只能有一个线程来对它进行操作

   ```java
   public class Test {
       private static int i = 0;
       private static Object obj = new Object();
   
       public static void main(String[] args) throws InterruptedException {
           Thread t1 = new Thread(() -> {
               synchronized (obj) {
                   for (int j = 0; j < 5000; j++) {
                       i++;
                   }
               }
           }, "t1");
           Thread t2 = new Thread(() -> {
               synchronized (obj) {
                   for (int j = 0; j < 5000; j++) {
                       i--;
                   }
               }
           }, "t2");
           t1.start();
           t2.start();
           t1.join();
           t2.join();
           System.out.println(i);
       }
   }
   ```

2. **可见性**：一个线程对主内存的修改可以及时的被其他线程观察到

   ```java
   public class Test {
       private static volatile boolean run = true;
   
       public static void main(String[] args) throws InterruptedException {
           new Thread(() -> {
               while (run) {
   
               }
           },"t1").start();
           Thread.sleep(1000);
           run = false;
       }
   }
   ```

3. **有序性**：一个线程观察其他线程中指令执行顺序，由于指令重排序存在，观察结果一般杂乱无序

   ```java
   int num = 0;
   boolean ready = false;
   
   // 线程1：执行此方法
   public void actor1(I_Result r) {
       if (ready) {
           r.r1 = num + num;
       } else {
           r.r1 = 1;
       }
   }
   
   // 线程2：执行此方法
   public void actor2(I_Result r) {
       num = 2;
       ready = true;
   }
   ```

   可能的结果：1，4，**0（重排）**

   解决办法：`volatile`修饰的变量，可以禁用指令重排（`volatile boolean ready = true`）

### volatile关键字

1. **保证内存可见性**

   可见性是指线程之间的可见性，⼀个线程修改的状态对另⼀个线程是可见的。也就是⼀个线程修改的结果，另⼀个线程马上就能看到；

   实现原理：

   - 当对⾮`volatile`变量进⾏读写的时候，每个线程先从主内存拷贝变量到CPU缓存中，如果计算机有多个CPU， 每个线程可能在不同的CPU上被处理，这意味着每个线程可以拷贝到不同的CPU cache中；
   - `volatile`变量不会被缓存在寄存器或者对其他处理器不可见的地⽅，保证了每次读写变量都从主内存中读，跳 过CPU cache这⼀步。当⼀个线程修改了这个变量的值，新值对于其他线程是⽴即得知的。 

   ```java
   public class Test {
       private static volatile boolean run = true;
   
       public static void main(String[] args) throws InterruptedException {
           new Thread(() -> {
               while (run) {
                   
               }
           },"t1").start();
           Thread.sleep(1000);
           run = false;
       }
   }
   ```

2. **禁止指令重排**

3. 与`synchronized`对比：

   - volatile可以保证数据的可见性，但不能保证原子性；
   - synchronized可以保证原子性，也可以间接保证可见性，因为它会将私有内存和公共内存中的数据做同步

## 并发编程——模式

### 两阶段终止模式

<img src="./assets/Snipaste_2023-10-06_10-13-33.png" style="zoom:50%;" />

代码实现：

```java
@Slf4j(topic = "c.Test3")
class TwoPhaseTermination {
    // 监控线程监控当前线程是否被打断
    private Thread monitor;

    // 启动监控线程
    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                if (current.isInterrupted()) {
                    log.debug("料理后事");
                    break;
                }
                try {
                    Thread.sleep(1000); // 阻塞时线程被打断会抛异常，打断标记不会设置为true
                    log.debug("记录日志");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 重新设置打断标记
                    current.interrupt();
                }
            }
        });
        monitor.start();
    }

    // 停止监控线程
    public void stop() {
        monitor.interrupt();
    }
}
```

不加`current.interrupt()`的**运行结果**：

<img src="./assets/Snipaste_2023-10-06_10-17-39.png" style="zoom:50%;" />

仅抛出异常，但由于没有重新设置打断标记，故没有终止。

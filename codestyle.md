# 1. 源文件基础

## 1.1 文件名

源文件以其最顶层的类名来命名，大小写敏感，文件扩展名为.java。

## 1.2 文件编码：UTF-8

源文件编码格式为 UTF-8。

## 1.3 特殊字符

### 1.3.1 空白字符

除了行结束符序列，ASCII水平空格字符(0x20，即空格)是源文件中唯一允许出现的空白字符，这意味着：

1. 所有其它字符串中的空白字符都要进行转义。
2. 制表符不用于缩进（可以在IDE中Tab键设置为若干个空格）。

### 1.3.2 特殊转义序列

对于具有特殊转义序列的任何字符(\b, \t, \n, \f, \r, ", '及)，我们使用它的转义序列，而不是相应的八进制(比如\012)或Unicode(比如\u000a)转义。

### 1.3.3 非ASCII字符

对于剩余的非ASCII字符，是使用实际的Unicode字符(比如∞)，还是使用等价的Unicode转义符(比如\u221e)，取决于哪个能让代码更易于阅读和理解。

> Tip:在使用Unicode转义符或是一些实际的Unicode字符时，建议做些注释给出解释，这有助于别人阅读和理解。

例如：
 String unitAbbrev = "μs"; | 赞，即使没有注释也非常清晰
 String unitAbbrev = "\u03bcs"; // "μs" | 允许，但没有理由要这样做
 String unitAbbrev = "\u03bcs"; // Greek letter mu, "s" | 允许，但这样做显得笨拙还容易出错
 String unitAbbrev = "\u03bcs"; | 很糟，读者根本看不出这是什么
 return '\ufeff' + content; // byte order mark | Good，对于非打印字符，使用转义，并在必要时写上注释

> Tip:永远不要由于害怕某些程序可能无法正确处理非ASCII字符而让你的代码可读性变差。当程序无法正确处理非ASCII字符时，它自然无法正确运行， 你就会去fix这些问题的了。(言下之意就是大胆去用非ASCII字符，如果真的有需要的话)

# 2. 源文件结构

一个源文件包含(按顺序地)：

1. 许可证或版权信息(如有需要)
2. package语句
3. import语句
4. 一个顶级类(只有一个)以上每个部分之间用一个空行隔开。

## 2.1 许可证或版权信息

如果一个文件包含许可证或版权信息，那么它应当被放在文件最前面。

## 2.2 package语句

package 语句不换行，列限制(4.4节)并不适用于package语句。(即package语句写在一行里)

## 2.3 import语句

### 2.3.1 import不要使用通配符

即，不要出现类似这样的import语句：import java.util.*;

### 2.3.2 不要换行

import语句不换行，列限制(4.4节)并不适用于import语句。(每个import语句独立成行)

### 2.3.3 顺序和间距

import语句可分为以下几组，按照这个顺序，每组由一个空行分隔：

1. 所有的静态导入独立成组
2. com.google imports(仅当这个源文件是在com.google包下)
3. 第三方的包。每个顶级包为一组，字典序。例如：android, com, junit, org, sun
4. java imports5.javax imports组内不空行，按字典序排列。

## 2.4 类声明

### 2.4.1 只有一个顶级

类声明每个顶级类都在一个与它同名的源文件中(当然，还包含.java后缀)。
 例外：package-info.java，该文件中可没有package-info类。

### 2.4.2 类成员顺序

类的成员顺序对易学性有很大的影响，但这也不存在唯一的通用法则。不同的类对成员的排序可能是不同的。
 最重要的一点，每个类应该以某种逻辑去排序它的成员，维护者应该要能解释这种排序逻辑。比如， 新的方法不能总是习惯性地添加到类的结尾，因为这样就是按时间顺序而非某种逻辑来排序的。

#### 2.4.2.1 区块划分

建议使用注释将源文件分为明显的区块，区块划分如下

1. 常量声明区
2. UI控件成员变量声明区
3. 普通成员变量声明区
4. 内部接口声明区
5. 初始化相关方法区
6. 事件响应方法区
7. 普通逻辑方法区
8. 重载的逻辑方法区
9. 发起异步任务方法区
10. 异步任务回调方法区
11. 生命周期回调方法区（出去onCreate()方法）
12. 内部类声明区

#### 2.4.2.2 类成员排列通用规则

1. 按照发生的先后顺序排列
2. 常量按照使用先后排列
3. UI控件成员变量按照layout文件中的先后顺序排列
4. 普通成员变量按照使用的先后顺序排列
5. 方法基本上都按照调用的先后顺序在各自区块中排列
6. 相关功能作为小区块放在一起（或者封装掉）

#### 3.4.2.3 重载：永不分离

当一个类有多个构造函数，或是多个同名方法，这些函数/方法应该按顺序出现在一起，中间不要放进其它函数/方法。

# 3. 格式术语

说明：块状结构(block-like construct)指的是一个类，方法或构造函数的主体。需要注意的是，数组初始化中的初始值可被选择性地视为块状结构(4.8.3.1节)。

## 3.1 大括号

### 3.1.1 使用大括号(即使是可选的)

大括号与if, else, for, do, while语句一起使用，即使只有一条语句(或是空)，也应该把大括号写上。

### 3.1.2 非空块：K & R 风格

对于非空块和块状结构，大括号遵循 Kernighan 和 Ritchie 风格 (Egyptian brackets):

- 左大括号前不换行
- 左大括号后换行
- 右大括号前换行
- 如果右大括号是一个语句、函数体或类的终止，则右大括号后换行; 否则不换行。

例如，如果右大括号后面是else或逗号，则不换行。
 示例：



```java
return new MyClass() {    
    @Override public void method() {        
        if (condition()) {
            try {
                something();
            } catch (ProblemException e) {
                recover();
            }
        }
    }
};
```

3.8.1节给出了enum类的一些例外。

### 3.1.3 空块：可以用简洁版本

一个空的块状结构里什么也不包含，大括号可以简洁地写成{}，不需要换行。
 例外：如果它是一个多块语句的一部分(if/else 或 try/catch/finally) ，即使大括号内没内容，右大括号也要换行。
 示例：



```java
void doNothing() {}
```

## 3.2 块缩进：4个空格

每当开始一个新的块，缩进增加4个空格，当块结束时，缩进返回先前的缩进级别。缩进级别适用于代码和注释。(见4.1.2节中的代码示例)

## 3.3 一行一个语句

每个语句后要换行。

## 3.4 列限制：80或100

一个项目可以选择一行80个字符或100个字符的列限制，除了下述例外，任何一行如果超过这个字符数限制，必须自动换行。
 例外：

- 不可能满足列限制的行(例如，Javadoc中的一个长URL，或是一个长的JSNI方法参考)。
- package和import语句(见3.2节和3.3节)。
- 注释中那些可能被剪切并粘贴到shell中的命令行。

## 3.5 自动换行

术语说明：一般情况下，一行长代码为了避免超出列限制(80或100个字符)而被分为多行，我们称之为自动换行(line-wrapping)。我们并没有全面，确定性的准则来决定在每一种情况下如何自动换行。很多时候，对于同一段代码会有好几种有效的自动换行方式。

> Tip:提取方法或局部变量可以在不换行的情况下解决代码过长的问题(是合理缩短命名长度吧)

### 3.5.1 从哪里断开

自动换行的基本准则是：更倾向于在更高的语法级别处断开。

1. 如果在非赋值运算符处断开，那么在该符号前断开(比如+，它将位于下一行)。注意：这一点与 Google 其它语言的编程风格不同(如 C++ 和 JavaScript )。
2. 这条规则也适用于以下"类运算符"符号：点分隔符(.)，类型界限中的 &（)，catch 块中的管道符号(catch (FooException | BarException e)
3. 如果在赋值运算符处断开，通常的做法是在该符号后断开(比如=，它与前面的内容留在同一行)。这条规则也适用于foreach语句中的分号。
4. 方法名或构造函数名与左括号留在同一行。
5. 逗号(,)与其前面的内容留在同一行。

### 3.5.2 自动换行时缩进至少+8个空格

自动换行时，第一行后的每一行至少比第一行多缩进8个空格(注意：制表符不用于缩进。见2.3.1节)。当存在连续自动换行时，缩进可能会多缩进不只8个空格(语法元素存在多级时)。一般而言，两个连续行使用相同的缩进当且仅当它们开始于同级语法元素。
 第4.6.3水平对齐一节中指出，不鼓励使用可变数目的空格来对齐前面行的符号。

## 3.6 空白

### 3.6.1 垂直空白

以下情况需要使用一个空行：

1. 类内连续的成员之间：字段，构造函数，方法，嵌套类，静态初始化块，实例初始化块。 例外： 两个连续字段之间的空行是可选的，用于字段的空行主要用来对字段进行逻辑分组。
2. 在函数体内，语句的逻辑分组间使用空行。
3. 类内的第一个成员前或最后一个成员后的空行是可选的(既不鼓励也不反对这样做，视个人喜好而定)。
4. 要满足本文档中其他节的空行要求(比如3.3节：import语句)
5. 多个连续的空行是允许的，但没有必要这样做(我们也不鼓励这样做)。

### 3.6.2 水平空白

除了语言需求和其它规则，并且除了文字，注释和Javadoc用到单个空格，单个ASCII空格也出现在以下几个地方：

1. 分隔任何保留字与紧随其后的左括号(()(如if, for catch等)。
2. 分隔任何保留字与其前面的右大括号(})(如else, catch)。
3. 在任何左大括号前({)，两个例外：
    o  @SomeAnnotation({a, b})(不使用空格)。
    o  String[][] x = foo;(大括号间没有空格，见下面的Note)。
4. 在任何二元或三元运算符的两侧。这也适用于以下"类运算符"符号：
    o  类型界限中的&()。
    o  catch块中的管道符号(catch (FooException | BarException e)。
    o  foreach语句中的分号。
5. 在, : ;及右括号())后
6. 如果在一条语句后做注释，则双斜杠(//)两边都要空格。这里可以允许多个空格，但没有必要。
7. 类型和变量之间：List list。
8. 数组初始化中，大括号内的空格是可选的，即new int[] {5, 6}和new int[] { 5, 6 }都是可以的。

> Note：这个规则并不要求或禁止一行的开关或结尾需要额外的空格，只对内部空格做要求。

### 3.6.3 水平对齐：不做要求

术语说明：水平对齐指的是通过增加可变数量的空格来使某一行的字符与上一行的相应字符对齐。
 这是允许的(而且在不少地方可以看到这样的代码)，但Google编程风格对此不做要求。即使对于已经使用水平对齐的代码，我们也不需要去保持这种风格。
 以下示例先展示未对齐的代码，然后是对齐的代码：



```java
private int x; // this is fine
private Color color; // this too

private int    x;         // permitted, but future edits
private Color  color;     // may leave it unaligned
```

> Tip：对齐可增加代码可读性，但它为日后的维护带来问题。考虑未来某个时候，我们需要修改一堆对齐的代码中的一行。
>  这可能导致原本很漂亮的对齐代码变得错位。很可能它会提示你调整周围代码的空白来使这一堆代码重新水平对齐(比如程序员想保持这种水平对齐的风格)。
>  这就会让你做许多的无用功，增加了reviewer的工作并且可能导致更多的合并冲突。

## 3.7 用小括号来限定组：推荐

除非作者和reviewer都认为去掉小括号也不会使代码被误解，或是去掉小括号能让代码更易于阅读，否则我们不应该去掉小括号。
 我们没有理由假设读者能记住整个Java运算符优先级表。

## 3.8 具体结构

### 3.8.1 枚举类

枚举常量间用逗号隔开，换行可选。
 没有方法和文档的枚举类可写成数组初始化的格式：



```java
private enum Suit { 
    CLUBS, 
    HEARTS, 
    SPADES, 
    DIAMONDS
}
```

由于枚举类也是一个类，因此所有适用于其它类的格式规则也适用于枚举类。

### 3.8.2 变量声明

#### 3.8.2.1 每次只声明一个变量

不要使用组合声明，比如int a, b;。

#### 3.8.2.2 需要时才声明，并尽快进行初始化

不要在一个代码块的开头把局部变量一次性都声明了(这是c语言的做法)，而是在第一次需要使用它时才声明。 局部变量在声明时最好就进行初始化，或者声明后尽快进行初始化。

### 3.8.3 数组

#### 3.8.3.1 数组初始化：可写成块状结构

数组初始化可以写成块状结构，比如，下面的写法都是OK的：



```java
new int[] {
        0, 1, 2, 3 
}
new int[] {
        0,
        1,
        2,
        3
}
new int[] {
        0, 1,
        2, 3
}
new int[]
        {0, 1, 2, 3}
```

#### 3.8.3.2 非C风格的数组声明

中括号是类型的一部分：String[] args， 而非 String args[]。

### 3.8.4 switch语句

术语说明：switch块的大括号内是一个或多个语句组。
 每个语句组包含一个或多个switch标签(case FOO:或default:)，后面跟着一条或多条语句。

#### 3.8.4.1 缩进

与其它块状结构一致，switch块中的内容缩进为2个空格。每个switch标签后新起一行，再缩进2个空格，写下一条或多条语句。

#### 3.8.4.2 Fall-through：注释

在一个switch块内，每个语句组要么通过break, continue, return或抛出异常来终止，要么通过一条注释来说明程序将继续执行到下一个语句组， 任何能表达这个意思的注释都是OK的(典型的是用// fall through)。这个特殊的注释并不需要在最后一个语句组(一般是default)中出现。
 示例：



```java
switch (input) {
    case 1:
    case 2:
        prepareOneOrTwo();        // fall through
    case 3:
        handleOneTwoOrThree();
        break;
    default:
        handleLargeNumber(input);
}
```

#### 3.8.4.3 default的情况要写出来

每个switch语句都包含一个default语句组，即使它什么代码也不包含。

### 3.8.5 注解(Annotations)

注解紧跟在文档块后面，应用于类、方法和构造函数，一个注解独占一行。这些换行不属于自动换行(第4.5节，自动换行)，因此缩进级别不变。
 例如：
 @Nullable public String getNameIfPresent() { ... }
 例外：单个的注解可以和签名的第一行出现在同一行。
 例如：
 @Override public int hashCode() { ... }应用于字段的注解紧随文档块出现，应用于字段的多个注解允许与字段出现在同一行。
 例如：
 @Partial @Mock DataLoader loader;
 参数和局部变量注解没有特定规则。

### 3.8.6 注释

#### 4.8.6.1 块注释风格

块注释与其周围的代码在同一缩进级别。它们可以是/* ... */风格，也可以是// ...风格。对于多行的/* ... */注释，后续行必须从*开始， 并且与前一行的*对齐。
 以下示例注释都是OK的。



```java
/** This is // And so /* Or you can
 *  okay. // is this. * even do this. */
 */
```

注释不要封闭在由星号或其它字符绘制的框架里。

> Tip：在写多行注释时，如果你希望在必要时能重新换行(即注释像段落风格一样)，那么使用/* ... */。

### 3.8.7 Modifiers

类和成员的modifiers如果存在，则按Java语言规范中推荐的顺序出现。
 public protected private abstract static final transient volatile synchronized native strictfp

# 4. 命名约定

## 4.1 对所有标识符都通用的规则

标识符只能使用ASCII字母和数字，因此每个有效的标识符名称都能匹配正则表达式\w+。

## 4.2 标识符类型的规则

### 4.2.1 包名

包名全部小写，连续的单词只是简单地连接起来，不使用下划线。
 采用反域名命名规则，全部使用小写字母。一级包名为com，二级包名为xx（可以是公司或则个人的随便），三级包名根据应用进行命名，四级包名为模块名或层级名。
 例如：com.jiashuangkuaizi.kitchen

| 包名                                                        | 此包中包含                                       |
| ----------------------------------------------------------- | :----------------------------------------------- |
| com.xx.应用名称缩写.activity                                | 页面用到的Activity类 (activitie层级名用户界面层) |
| com.xx.应用名称缩写.base                                    | 基础共享的类                                     |
| com.xx.应用名称缩写.adapter                                 | 页面用到的Adapter类 (适配器的类)                 |
| com.xx.应用名称缩写.util                                    | 此包中包含：公共工具方法类（util模块名）         |
| com.xx.应用名称缩写.bean                                    | 下面可分：vo、po、dto 此包中包含：JavaBean类     |
| com.xx.应用名称缩写.model                                   | 此包中包含：模型类                               |
| com.xx.应用名称缩写.db                                      | 数据库操作类                                     |
| com.xx.应用名称缩写.view (或者 com.xx.应用名称缩写.widget ) | 自定义的View类等                                 |
| com.xx.应用名称缩写.service                                 | Service服务                                      |
| com.xx.应用名称缩写.receiver                                | BroadcastReceiver服务                            |

> 注意：
>  如果项目采用MVP，所有M、V、P抽取出来的接口都放置在相应模块的i包下，所有的实现都放置在相应模块的impl下

### 4.2.2 类名

类名都以UpperCamelCase风格编写。
 类名通常是名词或名词短语，接口名称有时可能是形容词或形容词短语。现在还没有特定的规则或行之有效的约定来命名注解类型。
 名词，采用大驼峰命名法，尽量避免缩写，除非该缩写是众所周知的，  比如HTML,URL，如果类名称中包含单词缩写，则单词缩写的每个字母均应大写。

| 类                 | 描述                                                         | 例如                                                         |
| ------------------ | :----------------------------------------------------------- | :----------------------------------------------------------- |
| Activity 类        | Activity为后缀标识                                           | 欢迎页面类WelcomeActivity                                    |
| Adapter类          | Adapter 为后缀标识                                           | 新闻详情适配器 NewDetailAdapter                              |
| 解析类             | Parser为后缀标识                                             | 首页解析类HomePosterParser                                   |
| 工具方法类         | Util或Manager为后缀标识（与系统或第三方的Utils区分）或功能+Util | 线程池管理类：ThreadPoolManager <p> 日志工具类：LogUtil（Logger也可）<p> 打印工具类：PrinterUtil |
| 数据库类           | 以DBHelper后缀标识                                           | 新闻数据库：NewDBHelper                                      |
| Service类          | 以Service为后缀标识                                          | 时间服务TimeServiceBroadcast                                 |
| Receiver类         | 以Receiver为后缀标识                                         | 推送接收JPushReceiver                                        |
| ContentProvider    | 以Provider为后缀标识                                         |                                                              |
| 自定义的共享基础类 | 以Base开头                                                   | BaseActivity,BaseFragment                                    |

测试类的命名以它要测试的类的名称开始，以Test结束。
 例如：HashTest 或 HashIntegrationTest。
 接口（interface）：命名规则与类一样采用大驼峰命名法，多以able或ible结尾，如
 interface Runnable ;
 interface Accessible。

> 注意：
>  如果项目采用MVP，所有Model、View、Presenter的接口都以I为前缀，不加后缀，其他的接口采用上述命名规则。

### 4.2.3 方法名

方法名都以 LowerCamelCase 风格编写。
 方法名通常是动词或动词短语。

| 方法                 | 说明                                                      |
| -------------------- | --------------------------------------------------------- |
| initXX()             | 初始化相关方法,使用init为前缀标识，如初始化布局initView() |
| isXX() checkXX()     | 方法返回值为boolean型的请使用is或check为前缀标识          |
| getXX()              | 返回某个值的方法，使用get为前缀标识                       |
| handleXX()           | 对数据进行处理的方法，尽量使用handle为前缀标识            |
| displayXX()/showXX() | 弹出提示框和提示信息，使用display/show为前缀标识          |
| saveXX()             | 与保存数据相关的，使用save为前缀标识                      |
| resetXX()            | 对数据重组的，使用reset前缀标识                           |
| clearXX()            | 清除数据相关的                                            |
| removeXXX()          | 清除数据相关的                                            |
| drawXXX()            | 绘制数据或效果相关的，使用draw前缀标识                    |

下划线可能出现在JUnit测试方法名称中用以分隔名称的逻辑组件。一个典型的模式是：test_，例如testPop_emptyStack。
 并不存在唯一正确的方式来命名测试方法。

### 4.2.4 常量名

常量名命名模式为CONSTANT_CASE，全部字母大写，用下划线分隔单词。那，到底什么算是一个常量？
 每个常量都是一个静态final字段，但不是所有静态final字段都是常量。在决定一个字段是否是一个常量时，考虑它是否真的感觉像是一个常量。
 例如，如果任何一个该实例的观测状态是可变的，则它几乎肯定不会是一个常量。只是永远不打算改变对象一般是不够的，它要真的一直不变才能将它示为常量。



```java
// Constants
static final int NUMBER = 5;
static final ImmutableListNAMES = ImmutableList.of("Ed", "Ann");
static final Joiner COMMA_JOINER = Joiner.on(','); // because Joiner is immutable
static final SomeMutableType[] EMPTY_ARRAY = {};
enum SomeEnum { ENUM_CONSTANT }
// Not constants
static String nonFinal = "non-final";
final String nonStatic = "non-static";
static final SetmutableCollection = new HashSet();
static final ImmutableSetmutableElements = ImmutableSet.of(mutable);
static final Logger logger = Logger.getLogger(MyClass.getName());
static final String[] nonEmptyArray = {"these", "can", "change"};
```

这些名字通常是名词或名词短语。

### 4.2.5 非常量字段名

非常量字段名以LowerCamelCase风格的基础上改造为如下风格：
 基本结构为scopeVariableNameType，
 **scope：范围**
 非公有，非静态字段命名以m开头。
 静态字段命名以s开头。
 公有非静态字段命名以p开头。
 公有静态字段（全局变量）命名以g开头。
 public static final 字段(常量) 全部大写，并用下划线连起来。
 例子：



```java
1.  public class MyClass {  
2.        public static final int SOME_CONSTANT = 42;  
3.        public int pField;  
4.        private static MyClass sSingleton;  
5.        int mPackagePrivate;  
6.        private int mPrivate;  
7.        protected int mProtected; 
8.        public static int gField; 
9.  }
```

使用1字符前缀来表示作用范围，1个字符的前缀必须小写，前缀后面是由表意性强的一个单词或多个单词组成的名字，而且每个单词的首写字母大写，其它字母小写，这样保证了对变量名能够进行正确的断句。
 **Type：类型**
 考虑到Android中使用很多UI控件，为避免控件和普通成员变量混淆以及更好达意，所有用来表示控件的成员变量统一加上控件缩写作为后缀（文末附有缩写表）。
 对于普通变量一般不添加类型后缀，如果统一添加类型后缀，请参考文末的缩写表。
 用统一的量词通过在结尾处放置一个量词，就可创建更加统一的变量，它们更容易理解，也更容易搜索。

> 注意：如果项目中使用ButterKnife，则不添加m前缀，以LowerCamelCase风格命名。

例如，请使用 mCustomerStrFirst 和 mCustomerStrLast，而不要使用mFirstCustomerStr和mLastCustomerStr。
 量词列表：量词后缀说明
 First  一组变量中的第一个
 Last  一组变量中的最后一个
 Next  一组变量中的下一个变量
 Prev  一组变量中的上一个
 Cur  一组变量中的当前变量。

说明：
 集合添加如下后缀：List、Map、Set
 数组添加如下后缀：Arr

> 注意：所有的VO（值对象）统一采用标准的lowerCamelCase风格编写，所有的DTO（数据传输对象）就按照接口文档中定义的字段名编写。

### 4.2.6 参数名

参数名以LowerCamelCase风格编写

### 4.2.7 局部变量名

局部变量名以LowerCamelCase风格编写，比起其它类型的名称，局部变量名可以有更为宽松的缩写。
 虽然缩写更宽松，但还是要避免用单字符进行命名，除了临时变量和循环变量。
 即使局部变量是final和不可改变的，也不应该把它示为常量，自然也不能用常量的规则去命名它。
 **临时变量**
 临时变量通常被取名为i，j，k，m和n，它们一般用于整型；c，d，e，它们一般用于字符型。 如： for (int i = 0; i < len ; i++)，并且它和第一个单词间没有空格。

### 4.2.8 类型变量名

类型变量可用以下两种风格之一进行命名：

- 单个的大写字母，后面可以跟一个数字(如：E,T,X,T2)。
- 以类命名方式(5.2.2节)，后面加个大写的T(如：RequestT,FooBarT)。

### 4.2.9 资源文件命名规范

*1. 资源布局文件（XML文件（layout布局文件））：*  
 全部小写，采用下划线命名法

1. contentview 命名
    必须以全部单词小写，单词间以下划线分割，使用名词或名词词组。
    所有Activity或Fragment的contentView必须与其类名对应，对应规则为：
    将所有字母都转为小写，将类型和功能调换（也就是后缀变前缀）。
    例如：`activity_main.xml`
2. Dialog命名：`dialog_描述.xml`
    例如：`dialog_hint.xml`
3. PopupWindow命名：`ppw_描述.xml`
    例如：`ppw_info.xml`
4. 列表项命名：`item_描述.xml`
    例如：`item_city.xml`
5. 包含项命名：`模块_(位置)描述.xml`
    例如：`activity_main_head.xml`、`activity_main_bottom.xml`
    注意：通用的包含项命名采用：`项目名称缩写_描述.xml`
    例如：`xxxx_title.xml`
    *2. 资源文件（图片drawable文件夹下）：*
    全部小写，采用下划线命名法，加前缀区分
    命名模式：可加后缀 `_small` 表示小图, `_big` 表示大图，逻辑名称可由多个单词加下划线组成，采用以下规则：
    `用途_模块名_逻辑名称`
    `用途_模块名_颜色`
    `用途_逻辑名称`
    `用途_颜色`
    说明：用途也指控件类型（具体见UI控件缩写表）
    例如：
    `btn_main_home.png`按键
    `divider_maket_white.png` 分割线
    `ic_edit.png` 图标
    `bg_main.png` 背景
    `btn_red.png` 红色按键
    `btn_red_big.png` 红色大按键
    `ic_head_small.png` 小头像
    `bg_input.png`输入框背景
    `divider_white.png`白色分割线
    如果有多种形态如按钮等除外如 `btn_xx.xml`（selector）

| 名称                   | 功能                                   |
| ---------------------- | -------------------------------------- |
| `btn_xx`               | 按钮图片使用`btn_整体效果`（selector） |
| `btn_xx_normal`        | 按钮图片使用`btn_正常情况效果`         |
| `btn_xx_pressed`       | 按钮图片使用`btn_点击时候效果`         |
| `btn_xx_focused`       | `state_focused`聚焦效果                |
| `btn_xx_disabled`      | `state_enabled` (false)不可用效果      |
| `btn_xx_checked`       | `state_checked`选中效果                |
| `btn_xx_selected`      | `state_selected`选中效果               |
| `btn_xx_hovered`       | `state_hovered`悬停效果                |
| `btn_xx_checkable`     | `state_checkable`可选效果              |
| `btn_xx_activated`     | `state_activated`激活的                |
| `btn_xx_windowfocused` | `state_window_focused`                 |
| `bg_head`              | 背景图片使用`bg_功能_说明`             |
| `def_search_cell`      | 默认图片使用`def_功能_说明`            |
| `ic_more_help`         | 图标图片使用`ic_功能_说明`             |
| `seg_list_line`        | 具有分隔特征的图片使用`seg_功能_说明`  |
| `sel_ok`               | 选择图标使用`sel_功能_说明`            |

注意：
 使用AndroidStudio的插件SelectorChapek可以快速生成selector，前提是命名要规范。
 *3. 动画文件（anim文件夹下）：*
 全部小写，采用下划线命名法，加前缀区分。
 具体动画采用以下规则：
 `模块名_逻辑名称`
 逻辑名称
 `refresh_progress.xml`
 `market_cart_add.xml`
 `market_cart_remove.xml`
 普通的tween动画采用如下表格中的命名方式
 // 前面为动画的类型，后面为方向

| 动画命名例子        | 规范写法       |
| ------------------- | -------------- |
| `fade_in`           | 淡入           |
| `fade_out`          | 淡出           |
| `push_down_in`      | 从下方推入     |
| `push_down_out`     | 从下方推出     |
| `push_left`         | 推向左方       |
| `slide_in_from_top` | 从头部滑动进入 |
| `zoom_enter`        | 变形进入       |
| `slide_in`          | 滑动进入       |
| `shrink_to_middle`  | 中间缩小       |

*4. values中name命名*

| 类别    | 命名                                                         | 示例                                                         |
| ------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| strings | strings的name命名使用下划线命名法，采用以下规则：<p>模块名+逻辑名称 | main_menu_about 主菜单按键文字<p>friend_title好友模块标题栏<p>friend_dialog_del好友删除提示<p>login_check_email登录验证<p>dialog_title 弹出框标题<p>button_ok确认键 loading加载文字<p> |
| colors  | colors的name命名使用下划线命名法，采用以下规则：<p>模块名+逻辑名称 颜色 | friend_info_bgfriend_bg transparent gray                     |
| styles  | styles的name命名使用Camel命名法，采用以下规则：模块名+逻辑名称 | main_tabBottom                                               |

*5. layout中的id命名*
 命名模式为：`view缩写_view的逻辑名称`
 使用 AndroidStudio 的插件 ButterKnife Zelezny，生成注解非常方便。
 如果不使用 ButterKnife Zelezny，则建议使用 view 缩写做后缀，如：`username_tv`（展示用户名的TextView）

# 5. 编程实践

## 5.1 @Override：能用则用

只要是合法的，就把@Override注解给用上。

## 5.2 捕获的异常：不能忽视

除了下面的例子，对捕获的异常不做响应是极少正确的。(典型的响应方式是打印日志，或者如果它被认为是不可能的，则把它当作一个 AssertionError 重新抛出。)
 如果它确实是不需要在catch块中做任何响应，需要做注释加以说明(如下面的例子)。



```java
try {
  int i = Integer.parseInt(response);
 return handleNumericResponse();
} catch (NumberFormatException ok) {
 // it's not numeric; that's fine, just continue
}
return handleTextResponse(response);
```

例外：在测试中，如果一个捕获的异常被命名为expected，则它可以被不加注释地忽略。下面是一种非常常见的情形，用以确保所测试的方法会抛出一个期望中的异常，因此在这里就没有必要加注释。



```java
try {
 emptyStack.pop();
 fail();
} catch (NoSuchElementException expected) {
}
```

## 5.3 静态成员：使用类进行调用

使用类名调用静态的类成员，而不是具体某个对象或表达式。



```java
Foo aFoo = ...;
Foo.aStaticMethod(); // good
aFoo.aStaticMethod(); // bad
somethingThatYieldsAFoo().aStaticMethod(); // very bad
```
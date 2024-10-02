## CAN-do-paser：Java开发的CAN总线解析库



## 1 CAN总线数据解析示例

### 1.1 安装依赖

```xml
<dependency>
  <groupId>io.github.jarrettluo</groupId>
  <artifactId>can-frame-parser</artifactId>
  <version>1.0.0</version>
</dependency>
```



### 1.2 对CAN数据帧解析为十进制数据

```Java

CANFrame canFrame = new CANFrame(System.currentTimeMillis(), 1, 8, 20, "275d602702000000");

// 解析具体的信号
int startBit = 32;
int length = 32;
boolean isSigned = true;
boolean isLittleEndian = true;
String factor = "1";
String offset = "0";

// 解析工具的静态解析方法，对信号进行解析
double signal = CANFrameParser.extractSignal(canFrame.getMsgData(), startBit, length, isSigned, 
                isLittleEndian, factor, offset);
```





## 2 CAN总线数据DBC文件解析

```java
String filePath = "xxx.dbc";

// 对dbc文件进行解析
Map<DbcMessage, List<DbcSignal>> dbcMessageListMap = DbcParser.parseFile(filePath);
```



## 3 解析方案

### 3.1 BitSet

BitSet是一个强大且高效的位操作工具类，适用于需要处理大量布尔值的场景。

```java
# 将指定索引处的位设置为 true。
void set(int index)

# 返回指定索引处的值
boolean get(int index)
```



### 3.2 二进制转为浮点数的标准协议IEEE754

该标准的全称为IEEE二进制浮点数算术标准（ANSI/IEEE Std 754-1985），又称IEC 60559:1989，微处理器系统的二进制浮点数算术.



 一个浮点数 (Value) 的表示其实可以这样表示：

```
Value = sign * exponent * fraction
```

也就是浮点数的实际值，等于符号位（sign bit）乘以指数偏移值（exponent bias）再乘以分数值（fraction）。



## 4 代码地址

```
https://github.com/Jarrettluo/CAN-do-parser
```


# 负载生成器

> 本文件夹内的子项目为负载生成器部分

## 架构

文件夹架构为

>.<br>
>├── Main.java<br>
>├── README.md<br>
>├── base<br>
>│   ├── RandomControl.java<br>
>│   ├── format<br>
>│   │   ├── ArrayToString.java<br>
>│   │   ├── KeyAndValue.java<br>
>│   │   └── RangeKeyAndValue.java<br>
>│   └── tuple<br>
>│       ├── TupleChar.java<br>
>│       ├── TupleDate.java<br>
>│       ├── TupleDouble.java<br>
>│       ├── TupleInt.java<br>
>│       └── TupleType.java<br>
>├── generator<br>
>│   └── GenerateTable.java<br>
>└── template<br>
>    ├── sql<br>
>    │   └── SelectTemplate.java<br>
>    ├── table<br>
>    │   └── TableTemplate.java<br>
>    └── transaction<br> 

### 1.base 基础库 

功能包括：

+ format 格式化方法库

  格式化一组输入参数为String，例如select中的选择参数

  格式化key和value为标准的sql输入形式，例如select中的where键值

  格式化key和range value为标准的sql输入形式，例如select中的where  * between * and *

+ tuple 基本单元类型

  包括常见的基本类型，例如int,double,char,date，每个类包含随机初始化方法，和转译为sql语句的方法

+ RandomControl 随机控制类

  所有的随机生成的参数设置，都来自于这个类，修改此类，来改变对应参数的随机概率

### 2. template 模版库

功能包括：

+ sql 模板库

  用于编写各种常见sql的模板

+ table 模板库

  用于编写table的模板生成

+ transaction 模板库

  用于编写事务的模板生成

### 3. generate 模板库

用于结合随机参数，生成随机的表格和事务负载

### 4. Main.java

主程序，运行即可生成随机负载

## 逻辑

## 使用方法


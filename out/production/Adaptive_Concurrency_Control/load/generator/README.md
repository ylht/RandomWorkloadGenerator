# 负载生成器

> 本文件夹内的子项目为负载生成器部分

## 架构

文件夹架构为

<pre>
.
├── Main.java
├── README.md
├── base
│   ├── format
│   │   └── ArrayToString.java
│   ├── random
│   │   ├── SqlRandom.java
│   │   └── TableRandom.java
│   └── tuple
│       ├── TupleChar.java
│       ├── TupleDate.java
│       ├── TupleDouble.java
│       ├── TupleInt.java
│       └── TupleType.java
├── generator
│   └── Generator.java
└── template
    ├── ConditionTemplate.java
    ├── SqlTemplate.java
    └── TableTemplate.java
</pre>
     

### 1.base 基础库 

功能包括：

+ format 格式化方法库

  格式化一组输入参数为String，例如select中的选择参数或者update的set参数
+ tuple 基本单元类型

  包括常见的基本类型，例如int,double,char,date，每个类包含随机初始化方法，和转译为sql语句的方法

+ RandomControl 随机控制

  所有的随机生成的参数设置，都来自于这个包，修改此类，来改变对应参数的随机概率
  包括sql的随机生成控制和table属性的的随机生成控制

### 2. template 模版库

功能包括：

+ sql 模板

  用于编写各种常见sql的模板

+ table 模板

  用于编写table的模板生成

+ condition 模板

  用于编写条件控制的模板生成，包括from表的选择和where条件变量的模板

### 3. generate 模板库

用于结合随机参数，生成随机的表格和事务负载

### 4. Main.java

主程序，运行即可生成随机负载

## 逻辑

## 使用方法


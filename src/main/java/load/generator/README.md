# 负载生成器

> 本文件夹内的子项目为负载生成器部分

# 设计文档

## 表结构的生成

>  主要包括4个点，分别为表属性，表的主键，表的外键，表大小。

### 表属性

表的属性由基类TupleType描述，按照tuple的具体属性继承有tupleint,tupledecimal,tuplechar,tuplefloat,tupledate五种常见属性。

tupletype主要负责功能有：

1. 存储tuple的取值区间，并按照存储的取值类型输出表的结构中本tuple的结构信息，例如varchar(256)和decimal(4,2)等等
2. 向随机数生成器传递随机区间，例如char中的template，和int的取值min和max。

### 表主键

按照统计的记录在生成表的时候随机生成主键的数量。

### 表外键

在表中维持一个tupleForeign类的链表，每一个tupleForegin负责记录来自一张表的外键。

tupleForeign负责记录本表中tuple的位置，引用表的位置，引用的tuple的位置，其中本表的tuple和引用的tuple位置一一对应，并且tupleForeign按照对应关系记录引用外键的范围区间，并重写本地tuple的范围区间。

并设置引用表为不可delete

### 表大小

主键如果只有一个属性，则属性范围更新为表大小的范围，设置此表为不可insert，delete。

如果主键有多个属性，则必然存在一个不参考外键数量值，则用表大小值，从右到左依次除以当前属性的范围，来获取此值的区间，然后将此区间设置为所求值的2倍，方便用来insert

### 具体流程

1. 随机生成表的数量，然后用tableTemplate类新建同等数量的实例
2. 每一个tableTemplate首先按照统计数据随机各类属性的数量，然后按照数量实例化tupleType，在tupleType内部根据不同的类型按照已有的记录随机生成，生成顺序为int，demiacal，float，char，varchar，date。
3. 按照记录生成主键数目，将此数目的int设置为key。
4. 外键
   1. 现有的主键数目减1为主键中需要引用的外键数目，然后按照统计记录生成非主键的tuple需要外键的数目
   2. 随机一张在此表之前出现的表，引用此表中的所有主键属性，若数目不够此表所需的外键数，则继续随机，直到随需的外键数目达到，或者没有表可以引用，每次引用需要新建一个tupleForeign实例。
   3. 由于可能会出现以下情况，如：B中的2属性引用A中的1属性，而此时新建的表C需要外键，首先C（2）从B中引用到2，然后数目不够则C（3）引用表A中的1属性，则C引用了C(2)->B(2)->A(1)和C（3）->A(1)，但是这在应用中是不存在的，所以C引用B之后，便会在随机区间中去处B引用的表，防止重复引用。
   4. 从引用的表中继承相应tuple的数据范围
5. 将主键固定，然后将引用外键的tuple固定，之后随机打乱其他键的顺序

## 数据生成

1. 按照表的顺序生成数据，防止出现外键约束
2. 每一个表数据的生成，由一条insert语句拼接出来，按照tuple的顺序组合一个randomValue lIst，其中每一个元素为一个randomValue生成器，从相应的tuple中记录随机值的范围和方式，例如 int主键的顺序和char键的range区间生成
3. 将数据导出到文件中，之后将数据传输到数据库

## 负载生成

### 事务模板

1. Select模板

   随机生成select中的int属性，char属性等各个属性的数目，从表中筛选出相应数目的属性名称；

   条件选择：

   - 主键选择

     选择这张表的全部主键属性作为条件

   - 缺失一个主键

     缺失掉一个主键，可能存在order by和limit

   - 缺失一个主键，在一个其他属性上取值或者range

     例如选择姓氏

   - 缺失两个主键（在其他的一个属性上取range或者定值）

   - 两表join

     包含上述的约束条件

2. Update模板

   随机生成update中的int属性，char属性等各个属性的数目，从表中筛选出相应数目的属性名称；

   按照随机比例分配，rw和w的数目，rw例为a=a+1，w例为a=1；

   条件选择：

   - 主键选择

     选择这张表的全部主键属性作为条件

3. Delete模板

   条件选择：

   - 主键选择

     选择这张表的全部主键属性作为条件

4. Insert模板

   随机生成update中的int属性，char属性等各个属性的数目，从表中筛选出相应数目的属性名称；

   条件选择

   - 主键选择

     选择这张表的全部主键属性作为条件

5. 按照sql比例组合事务模板

### 数据生成

1. 每一个sql绑定一个randomValue List，用作生成这个sql所需要的数据，其中包括以下方面

2. 条件选择

   可重复主键选择，例如select和upadte，可以交给随机生成器完全随机，其中可以采用tuple选择的range比例来控制冲突；

   不可重复主键选择，insert和delete，需要给每个线程按照线程id，分配能操作的区间，每个线程内，按照线性顺序insert和delete，保证稳定。

   其他键选择，由于都在select上可以采用全随机方式。

### 事务执行

将每个事务的实例分配给多个线程，每个线程维持一个数据库连接，每次随机一个事务，事务内部调用随机生成器，补全事务模板，输出sql语句交给线程连接提交。完成随机负载。


## 架构

项目架构图为：

![随机负载生成器](https://ws1.sinaimg.cn/large/006tKfTcgy1ftaxuhztvkj31kw0o8tv9.jpg)

主要包含四个部分，分别为Table生成，Transaction Template生成，基础组件，和sql 数据生成。按照任务划分，文字表述如下，已完成的任务标注为勾，在上图中也存在任务标注，根据预期当前进度约在30%左右：

### Table 生成

- Table Scheme
  - Table属性
    - [x] 属性比例
    - [x] char/decimal的本身特征
  - [x] Table主键数量
  - [x] Table外键引用
- Table Data
  - Int
    - 基于给定区间完全随机
      - [ ] 数值区间
      - [ ] 外键区间
    - [ ] 顺序id
  - Double
    - [ ] 基于给定区间完全随机
  - Char
    - [ ] 基于给定长度完全随机
    - [ ] 从主键int中转换为char
    - [ ] 随机一组，然后从中抽取
  - Date
    - [ ] CurrentDateTime

### Transaction Template生成

- Sql Template
  - Insert Template
    - [x] Template
    - [x] Record the range on all attributes
  - [x] Delete Template
  - [x] Select Template
  - Update Template
    - [x] Template
    - [x] Record the range on all attributes
  - Condition Template
    - Table
      - [x] Single Table
      - [ ] Join Table
    - [x] Range Or Single Point
    - [ ] Record the range on all attributes
- [ ] 组合Sql Template

### 基础组件

- 随机数生成器
  - [ ] 填入统计之后的数据
  - [x] Table相关属性随机数
  - [x] Sql Template相关属性随机数
  - [ ] 负载模版数值填充比例随机
  - [ ] 稳定随机和不稳定随机
- 数据库连接
  - [ ] 导入 Table Scheme
  - [ ] 导入Table数据
  - [ ] 多线程压入负载

### Sql 数据生成

- 
  - Int数据生成
    - [ ] 给定范围区间
  - Char数据生成
    - [ ] 给定一组char，从里面随机选择
    - [ ] 给定int区间，从int里面选择
  - Double数据生成
    - [ ] 给定范围区间

## 文件架构

<pre>

.
├── BenchMark�\237计记�\225表格.xlsx
├── Main.java
├── README.md
├── generator
│   ├── DataGenerator.java
│   ├── GenerateSqlListFromArray.java
│   ├── Generator.java
│   ├── RandomGenerateSqlAttributesValue.java
│   ├── RandomGenerateTableAttributesVaule.java
│   └── RandomGenerateTupleValue.java
└── template
    ├── ConditionTemplate.java
    ├── SqlTemplate.java
    ├── TableTemplate.java
    └── tuple
        ├── TupleChar.java
        ├── TupleDate.java
        ├── TupleDouble.java
        ├── TupleInt.java
        └── TupleType.java

</pre>


# 负载生成器

> 本文件夹内的子项目为负载生成器部分

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
  - [ ] Table外键引用
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
    - [ ] Record the range on all attributes
  - [x] Delete Template
  - [x] Select Template
  - Update Template
    - [x] Template
    - [ ] Record the range on all attributes
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


# AdaptiveConcurrencyControl
自适应并发控制协议小组代码协同

##  项目架构

### 模型训练

**1. [负载生成器](https://github.com/YE-LUO-HAN-TANG/AdaptiveConcurrencyControl/tree/master/src/load/generator)**

统计已有的各种benchmark，发掘表结构和负载普遍的范围区间，按照这个区间比例模拟生成随机负载

**2. 负载监视器**

需要在各种并发协议，按照相同的硬件配置，各自运行完毕在1中生成的所有负载，并监控负载运行的结果特征。 

**3. 并发协议训练模型**

根据已有的数据训练得出我们需要的模型

> 项目架构图如下
>
> ![图片 1](https://ws4.sinaimg.cn/large/006tNc79gy1fsou6bjg4rj30mz0yl0wd.jpg)

## 模型应用

**1.  初始化并发协议**

事务具有的基本特征，如长短，读写比等，可以作为我们主要依据的，根据训练模型中得出的数据，我们可以先验性的初始化一个可能最佳的并发控制协议 

**2. 模型预测**

应用模型训练中学习出来的模型，根据当前的监控特征输出我们应该在当前采用的并发控制协议。 

**3. 切换并发控制协议**

在得到最优并发控制协议之后，我们需要将当前的协议切换到下一个并发控制协议。 

> 项目架构图如下
>
> ![图片 2](https://ws1.sinaimg.cn/large/006tNc79gy1fsou786q1gj30ky0rnju0.jpg)


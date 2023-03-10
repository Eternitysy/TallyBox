# TallyBox 
### 简易记账本
### 功能介绍：
* 分类别记录收支情况
* 统计当日当月收支总情况，设置预算
* 查看今日账单，历史账单，对历史账单进行逐月百分比分析

### 主要功能的实现思路：
* 使用Android自带数据库，将数据存储在数据库中，进行增删查改，利用cursor循环读取游标内容存储到对象中，传输到实现类中

### 不同页面实现的思路：
* 使用碎片加载界面，滑动视图切换页面
* 列表视图以及网格视图使用适配器和页面加载
* 通过继承Dialog自定义对话框

### APP功能的展示(GIF)：
![功能演示](https://github.com/Eternitysy/ShowFunction)
### 技术亮点：
* 自定义了一些控件
* 使用数据库存储数据
* 功能较完善，思路较清晰

### 问题解决：
* 遇到闪退问题查看日志，逐一排查，无法解决时依据提示参考网页
* 实际与预想不符时，思考来历过程，到相关模块进行排查

### 收获：
* 代码书写更规范，分包更合理，学到了如何高效的解决问题
* 在实现功能的时候也了解到了很多新的库
* 掌握了Android自带数据库的基本用法

### 待提升的地方：
* 总体耗时较长，有部分地方考虑不周全
* 部分预想功能未实现，如：数据分析中可加入图表分析更直观，今日账单中的长按删除改为左滑删除更合理


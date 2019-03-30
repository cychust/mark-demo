# markdemo

## Android 端 记事本 小demo

### gradle 配置

完全按照googleSamples的例子写，学习mvp和数据库的操作

### 学习room的使用
### 学习转场动画
### 学习MVP的架构


### 问题

- 一般presenter有start的话最好也加个destroy一些清除工作可以放里面，还有eventbus反注册
- 然后还有一个我自己用listview adapter的心得，
  里面的list<data>最好是内部维护的对象，不要直接引用外部传进来的，
  然后每次更新数据的时候做一个clear和addAll
- 这样有两个好处：一个是两份数据分开管理，避免一些互相影响带来数据混乱；
  另一个是如果数据更新了没有及时notifydatachange可能会崩溃，这样做了以后就不会有这个问题
  唯一的问题可能就是每次更新的时候clear addAll有一点性能损耗，但是数据量不是特别大、更新不是那么频繁其实就可以忽略不计
- 保持as默认命名格式就好了，用作activity的就activity打头，用作fragment就fragment打头
- MainFragment里面的MainPresenter
应该改成MainContract.Presenter
依赖于抽象
- Activity和Fragment的生命周期方法还是尽量按顺序来写吧   快捷键ctrl+alt+K
- 记录这个删除操作，直到删除真的成功才删除这个记录，否则一直重试删除
  似乎这样界面响应会更快，体验也更好
- 还有一个就是进行update、delete等危险操作的时候进行二次确认或做假删除是个好习惯




2018年2月1日

- 增加 Interceptor
- 用Rxjava+Retrofit重构项目
- 了解七牛云

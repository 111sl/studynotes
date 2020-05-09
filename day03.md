## day03

### service

1.service 默认运行在主线程

2.startservice和bindservice<br/>	bingservice需要实现一个新的ServiceConnection，重写onServiceConnected，绑定后可调用服务中的方法

3.service生命周期

**4.aidl ：跨进程通信使用**<br/>	4.1、定义.aidl接口文件
	4.2、系统自动生成对应java类
	4.3、实现你定义aidl接口中的内部抽象类Stub，实现服务端的Service
	4.4、客户端中实现ServiceConnection获取服务端的Service对象


#包含项目所有结构与细节说明
父目录：HaHaShow
子模块：
HaHa-Common【公共包】
HaHa-User【用户模块】
HaHa-Registry-10000
备注：
1、父目录pom中<dependencyManagement>只是定义依赖，并不实际引入到子模块中。外部的<dependencies>才会引入到子模块。
2、VO（View Object）视图对象:和视图打交道的，那么经历了视图的都归属于这个类，所以我们的输入输出类都是属于VO
PO（Persistent Object）永久对象： 这些对象对应着数据库的每一个字段名以下是我的数据库表
DTO(Data Transfer Object)数据传输对象：我们sql查询的时候是通过Id查询的，但是查询是可以查询出很多条信息的，但是我们给前端的数据只要某一部分

#开发日程
1、下载window docker，用于系统运行容器
2、

#实现计划
1、登录接口（实现实时单点/token校验）

2、简易聊天（socketio实现）

3、

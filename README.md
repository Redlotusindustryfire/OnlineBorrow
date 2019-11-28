# OnlineBorrow
网上借阅系统前端及后台，前端html+css+js+ajax，模板为Themeleaf，后台SpringBoot+java+JPA+MySQL。
该系统分为图书借阅子系统、图书管理子系统。

## 实现的功能
（用户表示登录的普通用户，游客表示未登录的用户）  
### 借阅用户
 a.任何人无需登录即可浏览网页（即用户或游客进入的网站就会来到网站首页，游客也可以浏览发布的一部分网页信息，如浏览图书信息）  

 b.只有登录的用户才能进行图书借阅、加入书单、修改个人信息等操作（需要拦截游客进行借阅、加入书单的行为，同时需要拦截游客从地址栏输	入某些没有权限的请求）  
 
 c.网页显示图示馆录入的图书信息，同时存在图书分类的浏览功能（首页需要向用户展现的图书信息有：图书封面、书名、借阅单价、最大借阅	时长、借阅量等信息）  
 
d.在图书的详情页面，用户可以浏览到的图书信息，其中包括：图书封面（放大版）、图书ISBN、作者、图书介绍、书名、借阅单价、借阅量等；
同时可以浏览到该图书对应的图书馆信息，其中包括：图书馆馆名、所在地址、图书馆联系方式、总借阅量等；  

同时要有图书推荐功能，在用户进入图书详情页面时，同时能浏览相关的图书信息，其中包括：图书封面、书名、借阅单价、最大借阅时长、	借阅量等信息。  

e.用户对图书的搜索功能（通过图书馆名称、书名关键字、类别、作者等进行搜索），搜索功能并不是首页所独有的，它在其他的部分页面也要	存在。
f.在分类页面或搜索关键字后的图书展示页面，用户可以对图书进行排序操作，其中包括：按照综合排序、按照借阅量降序排序、按照价格升序	排序、	按照价格降序排序。  

g.用户注册登录功能（在首页进行注册、登录操作）  

h.密码、个人信息的修改（个人信息包括：昵称、性别）  

i. 用户可以添加多个配送信息，并可以对配送信息进行删除、新建的操作（需要设计一个配送信息表，一个用户对应多个配送信息）；  

j.登录的用户可以通过该系统进行图书借阅（付费）和将图书加入书单，以便下次订阅  

k.用户可以向图书馆提交图书归还申请（用户借阅图书后，需要向用户展示其借阅详情，在详情处设计还书申请）  

l.在图书配送期间，用户可以取消订单（用户借阅图书后，需要向用户展示其借阅配送详情，在详情处设计取消订单的功能）  

m.借阅信息显示：
1.正在配送的图书信息（图书图片、书名、借阅数量、已付总额、下单时间）；
2.已收到并未归还图书的信息（图书图片、书名、借阅单价、借阅数量、已付总额、下单时间、剩余借阅时长）；
3.已归还的图书信息（历史订单）（图书图片、书名、借阅单价、借阅数量、已付总额、下单时间、归还时间、总借阅时长）  

n.对显示的借阅信息可以进行以下操作：
1.对正在配送的图书信息可以进行确认收货的操作（确认收货后，该条信息从配送列表中移除，进入未归还状态）
2.对已收到并未归还图书的信息可以进行还书申请（申请完，该图书从未归还列表中移除，进入历史订单状态）  

### 图书馆  

a.注册登录、修改密码  

b.图书馆用户没有权限进行首页商品浏览等普通用户的权限（图书馆用户一旦登录，就不能进入图书的详情页面，只能对自己录入的图书信息进			行操作）  

c.借出图书功能（对用户新建或修改的图书进行展示）  

d.图书信息管理：图书信息：图书ISBN、图书名称、作者、借阅价、图书类别、库存、已借阅量、最大借阅时长，包括对以上信息进行新增、编			辑、查询、删除的操作。  

e.查看需要配送的图书订单信息（图书图片、书名、借阅数量、已付总额、借阅人账号、姓名、联系方式、收货地址、下单时间）  

f.能及时获取到订单的状态（即刻得到用户取消下单的信息，其中包括被取消订单所对应的图书ISBN、书名、下单时间）  

g.查看借出未归还的书籍的记录（图书图片、书名、借阅单价、借阅数量、借阅人账号、姓名、联系方式、收货地址、已付总额、下单时间、剩余			借阅时长）  

h.查看历史订单（图书图片、书名、借阅单价、借阅数量、已付总额、下单时间、借阅人账号、姓名、联系方式、收货地址、归还时间、总借阅时长）  

g.对显示的借阅信息可以进行以下操作：  

1.对正在配送的图书信息可以进行确认配送成功的操作（确认收货后，该条信息从配送列表中移除，进入已借出状态）  

2.对已收到并未归还图书的信息可以进行确认归还的操作（申请完，该图书从未归还列表中移除，进入历史订单状态）  



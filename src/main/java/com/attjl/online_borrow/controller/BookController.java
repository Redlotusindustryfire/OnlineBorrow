package com.attjl.online_borrow.controller;

import com.attjl.online_borrow.entity.Book;
import com.attjl.online_borrow.entity.DeliveryInfo;
import com.attjl.online_borrow.entity.User;
import com.attjl.online_borrow.repository.BookRepository;
import com.attjl.online_borrow.repository.DeliveryInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.security.Principal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    DeliveryInfoRepository deliveryInfoRepository;

    @GetMapping("/cx/{id}")
    public Book getNumbering(@PathVariable Integer id){
      Book book= bookRepository.findOne(id) ;
      return book;
    }
    @PostMapping("/new_bklist/newBookList")
    public String newBookList(Model model, Book bk,HttpSession session){    //新建图书
        Object obj=session.getAttribute("loginUser");
        User user=(User)obj;
        List<Book> b=bookRepository.findByNumbering(bk.getNumbering()); //必须用List保存找到对象，
                                                        // 因为List可以为空，而Book不能为空。
        if(!b.isEmpty()){    //确保ISBN唯一
            model.addAttribute("msg","图书ISBN重复，请重新填写！");
        }
        else {
            bk.setBw_amount(0);
            String a[] = bk.getImage().split("\\\\");  //处理图片路径
            String s = "/images/" + a[a.length - 1];
            bk.setImage(s);
            bk.setUsername(user.getUsername());
            Book book1 = bookRepository.save(bk);
            model.addAttribute("msg", "添加图书成功！");
        }
        return "new_bklist";
    }

    @GetMapping("/search")
    public String Search(Model model,@RequestParam("stm") String stm){ //搜索功能
        List<Book> list=bookRepository.findByStm(stm,stm,stm,stm);
        if(list.isEmpty()){
            model.addAttribute("msg","您搜索的内容太过前卫，小二做不到呢");
        }
        else {
            model.addAttribute("q",stm);
            model.addAttribute("show_title","的查询结果");
            model.addAttribute("books", list);
        }
        return "Categories";
    }


    @RequestMapping({"/HomePage","/"})
    public String Index(Model model){                              //首页各类图书加载
        Integer MaxP=10;
        Integer max=6;
        List<Book> bookPopular=bookRepository.showPopular();
        List<Book> book1=bookRepository.findBySort("人文社科");
        List<Book> book2=bookRepository.findBySort("经济管理");//按照借阅量进行降序排列
        List<Book> book3=bookRepository.findBySort("科学技术");
        List<Book> book4=bookRepository.findBySort("励志");
        List<Book> book5=bookRepository.findBySort("童书");
        List<Book> book6=bookRepository.findBySort("生活");
        List<Book> book7=bookRepository.findBySort("青春文学");
        List<Book> book8=bookRepository.findBySort("网络小说");
        List<Book> book0=bookPopular.subList(0,4);  //借阅量Top4，幻灯片图片选取
        if (bookPopular.size()>MaxP){
            bookPopular=bookPopular.subList(4,MaxP+4);
        }
        if(book1.size()>max){
             book1=book1.subList(0,max);
        }
        if(book2.size()>max){
            book2=book2.subList(0,max);
        }
        if(book3.size()>max){
            book3=book3.subList(0,max);
        }
        if(book4.size()>max){
            book4=book4.subList(0,max);
        }
        if(book5.size()>max){
            book5=book5.subList(0,max);
        }
        if(book6.size()>max){
            book6=book6.subList(0,max);
        }
        if(book7.size()>max){
            book7=book7.subList(0,max);
        }
        if(book8.size()>max){
            book8=book8.subList(0,max);
        }
        model.addAttribute("book0",book0);
        model.addAttribute("bookPopular",bookPopular);
        model.addAttribute("book1",book1); //用subList方法取list的前6个对象
        model.addAttribute("book2",book2);
        model.addAttribute("book3",book3);
        model.addAttribute("book4",book4);
        model.addAttribute("book5",book5);
        model.addAttribute("book6",book6);
        model.addAttribute("book7",book7);
        model.addAttribute("book8",book8);
        return "HomePage";
    }

    @RequestMapping("/Categories/social")           //首页点击各个栏目的“更多”按钮，跳转到对应的浏览页面
    public String Social(Model model){
        List<Book> bookh=bookRepository.findByCategory("人文社科");
        model.addAttribute("bookh",bookh);
        model.addAttribute("show_title","人文社科");
        return "Categories";
    }
    @RequestMapping("/Categories/economic")
    public String Economic(Model model){
        List<Book> bookh=bookRepository.findByCategory("经济管理");
        model.addAttribute("bookh",bookh);
        model.addAttribute("show_title","经济管理");
        return "Categories";
    }
    @RequestMapping("/Categories/science")
    public String Science(Model model){
        List<Book> bookh=bookRepository.findByCategory("科学技术");
        model.addAttribute("bookh",bookh);
        model.addAttribute("show_title","科学技术");
        return "Categories";
    }
    @RequestMapping("/Categories/inspirational")
    public String Inspirational(Model model){
        List<Book> bookh=bookRepository.findByCategory("励志");
        model.addAttribute("bookh",bookh);
        model.addAttribute("show_title","励志");
        return "Categories";
    }
    @RequestMapping("/Categories/child")
    public String Child(Model model){
        List<Book> bookh=bookRepository.findByCategory("童书");
        model.addAttribute("bookh",bookh);
        model.addAttribute("show_title","童书");
        return "Categories";
    }
    @RequestMapping("/Categories/life")
    public String Life(Model model){
        List<Book> bookh=bookRepository.findByCategory("生活");
        model.addAttribute("bookh",bookh);
        model.addAttribute("show_title","生活");
        return "Categories";
    }
    @RequestMapping("/Categories/youth")
    public String Youth(Model model){
        List<Book> bookh=bookRepository.findByCategory("青春文学");
        model.addAttribute("bookh",bookh);
        model.addAttribute("show_title","青春文学");
        return "Categories";
    }
    @RequestMapping("/Categories/novel")   //首页点击个栏目的“更多”按钮，跳转到对应的浏览页面-------结束
    public String Novel(Model model){
        List<Book> bookh=bookRepository.findByCategory("小说");
        model.addAttribute("bookh",bookh);
        model.addAttribute("show_title","小说");
        return "Categories";
    }

    @RequestMapping("/shop_trading/{id}")          //进入图书详情、订阅页面
    public String bkInform(Model model,@PathVariable("id") Integer id){
     Book bookInf=bookRepository.findOne(id); //通过指定的图书，寻找推荐的书籍
       List<DeliveryInfo> deLi=deliveryInfoRepository.findByUsername(bookInf.getUsername());//找到对应的图书馆信息
        if(!deLi.isEmpty()){
           DeliveryInfo d=deLi.get(0);
            model.addAttribute("delivery",d);
        }
        List<Book> books=bookRepository.findByUsername(bookInf.getUsername());//找到图书馆所有的图书
        Integer total=0;
        for(Book b: books){
            total+=b.getBw_amount(); //计算该图书馆总出售的图书本书
        }
     List<Book> listBook=bookRepository.findByAuthorOrCategory(bookInf.getAuthor(),bookInf.getCategory());
        Iterator<Book> iterator=listBook.iterator();//遍历listBook，排除属性id为自身的对象
        while(iterator.hasNext()){
            Book book = iterator.next();
            if(book.getId()==id){
                iterator.remove();
            }
        }
     model.addAttribute("bookInf",bookInf);
     model.addAttribute("total",total);
     model.addAttribute("listBook",listBook);
     return "shop_trading";
    }

    @ResponseBody
    @GetMapping("/Categories/saleDesc")         //按照借阅量进行降序排列
    public List<Book> saleDesc(@RequestParam("category") String category,@RequestParam("a") String a){
        if(a.isEmpty()) {   //通过传入参数a来判断请求来源是“更多”后的排序请求
            List<Book> bookDesc = bookRepository.findBySort(category);
            return bookDesc;
        }
        else {           //通过传入参数a来判断请求来源是“搜索”后的排序请求
            List<Book> bookDesc = bookRepository.findByStmSort(a,a,a,a);
            return bookDesc;
        }

    }
    @ResponseBody
    @GetMapping("/Categories/integrate")       //利用借阅量除以单价来作为综合能力的衡量标准
    public List<Book> Integrate(@RequestParam("category") String category,@RequestParam("a") String a){
        if(a.isEmpty()) {  //通过传入参数a来判断请求来源是“更多”后的排序请求
            List<Book> bookIntegrate = bookRepository.findIntegrate(category);
            return bookIntegrate;
        }
        else {        //通过传入参数a来判断请求来源是“搜索”后的排序请求
            List<Book> bookIntegrate=bookRepository.findByStmIntegrate(a,a,a,a);
            return bookIntegrate;
        }

    }
    @ResponseBody
    @GetMapping("/Categories/priceDesc")       //按照价格降序进行排序
    public List<Book> priceDesc(@RequestParam("category") String category,@RequestParam("a") String a){
        if(a.isEmpty()) {
            List<Book> bookPriceDesc = bookRepository.findPriceDesc(category);
            return bookPriceDesc;
        }
        else {
            List<Book> bookPriceDesc=bookRepository.findByStmPriceDesc(a,a,a,a);
            return bookPriceDesc;
        }

    }
    @ResponseBody
    @GetMapping("/Categories/priceAsc")       //按照价格升序进行排序
    public List<Book> priceAsc(@RequestParam("category") String category,@RequestParam("a") String a){
        if(a.isEmpty()) {
            List<Book> bookPriceAsc = bookRepository.findPriceAsc(category);
            return bookPriceAsc;
        }
        else {
            List<Book> bookPriceAsc = bookRepository.findByStmPriceAsc(a,a,a,a);
            return bookPriceAsc;
        }
    }

    @RequestMapping("/book_list")  //图书馆所属图书显示（book_list页面）
    public String bookList(HttpSession session,Model model){
        User user=(User)session.getAttribute("loginUser");
       List<Book> books= bookRepository.findLibraryBooks(user.getUsername());
       model.addAttribute("books",books);
        return "book_list";
    }

    @RequestMapping("/searchBooks")    //搜索图书馆所属书单
    public String searchBooks(HttpSession session,Model model,@RequestParam("stm") String stm){
        User user=(User)session.getAttribute("loginUser");
        List<Book> books=bookRepository.findLibraryBooksByStm(user.getUsername(),stm,stm,stm);
        model.addAttribute("bks",books);
        return "book_list";
    }

    @RequestMapping("/bookInfoChange/{id}")   //图书信息回显并修改
    public String changeBookInfo(@PathVariable("id") Integer id,Model model){
        Book book=bookRepository.findOne(id);
        model.addAttribute("book",book);
        return "bookInfoChange";
    }

    @RequestMapping("/bookInfoChange/modifySave")  //保存修改后的图书信息
    public  String modifySave(Model model,Book b){
       Book oldBook= bookRepository.findOne(b.getId());
        if(b.getImage().isEmpty()){  //判断要修改的图书信息的图片是否已赋值，如果没有，
                                            // 则将数据库中原来的对应图片赋值给他
            b.setImage(oldBook.getImage());
        }
        else {
            String a[] = b.getImage().split("\\\\");  //处理图片路径
            String s = "/images/" + a[a.length - 1];
            b.setImage(s);
        }
        bookRepository.bookModifySave(b.getImage(),b.getNumbering(),b.getTitle(),b.getCategory(),
                b.getAuthor(),b.getStock(),b.getMax_day(),b.getPrice(),b.getId());
        model.addAttribute("msg","图书信息修改成功！");
        return "bookInfoChange";
    }

    @ResponseBody
    @PostMapping("/deleteBook")   //图书删除
    public String deleteBook(@RequestParam("id") Integer id){
        bookRepository.delete(id);
        return "图书删除成功！";
    }

}

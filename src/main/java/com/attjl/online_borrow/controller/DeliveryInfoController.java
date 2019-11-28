package com.attjl.online_borrow.controller;

import com.attjl.online_borrow.entity.Book;
import com.attjl.online_borrow.entity.DeliveryInfo;
import com.attjl.online_borrow.entity.User;
import com.attjl.online_borrow.repository.BookRepository;
import com.attjl.online_borrow.repository.DeliveryInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class DeliveryInfoController {
    @Autowired
    DeliveryInfoRepository deliveryInfoRepository;
    @Autowired
    BookRepository bookRepository;  //!!每引入一个接口的实现必须加上  @Autowired


    @GetMapping("/changDeliveryInfo")   //普通用户新增配送地址
    public String updateDelivery(Model model,HttpSession session, DeliveryInfo d){
        User user = (User)session.getAttribute("loginUser");  //获取登录者对象信息
        d.setUsername(user.getUsername());
        deliveryInfoRepository.save(d);
        model.addAttribute("msg","配送信息添加成功！");
        return "ship_address";
    }

    @PostMapping("/libraryInfo")  //图书馆信息保存
    public String libraryInfo(Model model,HttpSession session, DeliveryInfo d){
        User user = (User)session.getAttribute("loginUser");  //获取登录者对象信息
       List<DeliveryInfo> deliveries=deliveryInfoRepository.findByUsername(user.getUsername());
       if(deliveries.isEmpty()){
            d.setUsername(user.getUsername());
            deliveryInfoRepository.save(d);
           model.addAttribute("msg","图书馆信息保存成功！");
       }
       else {
           deliveryInfoRepository.updateDeliveryInfo(d.getName(),d.getPhone(),d.getAddress(),user.getUsername());
           model.addAttribute("msg","图书馆信息修改成功！");
       }

        return "lib_information";
    }

    @RequestMapping("/address_list.html")  //用户配送地址的显示
    public String showAddressList(Model model,HttpSession session){
        User user=(User)session.getAttribute("loginUser");      //通过session获取登陆者信息
        List<DeliveryInfo> list = deliveryInfoRepository.findByUsername(user.getUsername());
        model.addAttribute("lists",list);
        return "address_list";
    }

    @GetMapping("/deleteDelivery")
    public void deleteDelivery(@RequestParam("d") Integer d,HttpSession session){
        User user=(User)session.getAttribute("loginUser");  //通过session获取登陆者信息
        DeliveryInfo deliveryInfo= deliveryInfoRepository.findByUsername(user.getUsername()).get(d);
        deliveryInfoRepository.delete(deliveryInfo);   //删除指定的配送地址
    }

    @RequestMapping("/Submit_orders/{id}")  //从shop_trading页面带参（id）调转到Submit_orders页面
    public String submitOrder(Model model,@PathVariable("id") Integer id,HttpSession session){
        User user=(User)session.getAttribute("loginUser");//通过session获取登陆者信息
        //通过登录的用户名找到对应的配送地址
        List<DeliveryInfo> deliveryInfo= deliveryInfoRepository.findByUsername(user.getUsername());
        Book book1= this.bookRepository.findById(id);  //通过传过来的id找到唯一的book
       model.addAttribute("book",book1);
       model.addAttribute("deliveries",deliveryInfo);
        return "Submit_orders";
    }
}

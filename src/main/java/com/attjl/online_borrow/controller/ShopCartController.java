package com.attjl.online_borrow.controller;

import com.attjl.online_borrow.entity.Book;
import com.attjl.online_borrow.entity.ShopCart;
import com.attjl.online_borrow.entity.User;
import com.attjl.online_borrow.repository.BookRepository;
import com.attjl.online_borrow.repository.ShopCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ShopCartController {
    @Autowired
    ShopCartRepository shopCartRepository;
    @Autowired
    BookRepository bookRepository;

    @ResponseBody
    @PostMapping("/addInBookList")  //添加到书单
    public String addInBookList(@RequestParam("id") Integer id, HttpSession session){
        User user=(User)session.getAttribute("loginUser");
        Book book=bookRepository.findOne(id);
       List<ShopCart> shopCarts= shopCartRepository.findByUsername(user.getUsername()); //找到该用户所有的看单信息
       for(ShopCart sp :shopCarts){
           if(sp.getNumbering().equals(book.getNumbering())){  //该图书已存在于书单中
               return "该图书已存在于看单中";
           }
       }
        ShopCart shopCart=new ShopCart();
        shopCart.setUsername(user.getUsername());
        shopCart.setNumbering(book.getNumbering());
        shopCartRepository.save(shopCart);
        return "图书已成功加入看单！";

    }

    @RequestMapping("/my_cart")   //我的书单
    public String showCartInfo(HttpSession session,Model model){
        User user=(User)session.getAttribute("loginUser");
        List<ShopCart> shopCarts=shopCartRepository.findByUsername(user.getUsername());
        List<Book> books=new ArrayList<>();
        for(ShopCart s :shopCarts){
           Book book= bookRepository.findNumbering(s.getNumbering());
           books.add(book);
        }
        model.addAttribute("books",books);
        return "my_cart";
    }

    @ResponseBody
    @PostMapping("/deleteShopCart")
    public String deleteShopCart(@RequestParam("index") Integer i,HttpSession session){
        User user=(User)session.getAttribute("loginUser");
        ShopCart shopCart=shopCartRepository.findByUsername(user.getUsername()).get(i);
        shopCartRepository.delete(shopCart);
        return "看单图书删除成功！";
    }

}

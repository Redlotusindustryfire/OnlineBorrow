package com.attjl.online_borrow.controller;

import com.attjl.online_borrow.LastTime;
import com.attjl.online_borrow.entity.Book;
import com.attjl.online_borrow.entity.Borrow;
import com.attjl.online_borrow.entity.DeliveryInfo;
import com.attjl.online_borrow.entity.User;
import com.attjl.online_borrow.repository.BookRepository;
import com.attjl.online_borrow.repository.BorrowRepository;
import com.attjl.online_borrow.repository.DeliveryInfoRepository;
import com.attjl.online_borrow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class BorrowController {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BorrowRepository borrowRepository;
    @Autowired
    DeliveryInfoRepository deliveryInfoRepository;

    @ResponseBody
    @PostMapping("/Submit_orders/submit") //下单处理  active：找到配送信息， id：为图书主键， quantity：购买数量
    public String Transact(HttpSession session, @RequestParam("active")
          Integer active,@RequestParam("id") Integer id,@RequestParam("quantity") Integer quantity)
    {
        Date date=new Date();
        User user=(User)session.getAttribute("loginUser"); //获取登陆者信息，强制转换为User类型
        Book book=bookRepository.findById(id);
        if(book.getStock()>=quantity){
            Borrow borrow=new Borrow();  //必须在此创建一个新的对象，不能引入一个borrow对象，不然插入数据会变成更新数据
            borrow.setNumbering(book.getNumbering());
            borrow.setQuantity(quantity);
            borrow.setUsername(user.getUsername());
            borrow.setType(1);
            borrow.setLibraryType(1);
            borrow.setSerialNumber(active);
            borrow.setTime(date);
            book.setStock(book.getStock()-quantity);//库存自减借阅数量
            book.setBw_amount(book.getBw_amount()+quantity); //借阅量自加借阅数量
            bookRepository.save(book);
            borrowRepository.save(borrow);
            return "订单提交成功！";
        }
        else
            return "对不起，该书库存为"+book.getStock()+",您不能借阅"+quantity+"本";

    }

    @RequestMapping("/my_page")
    public String showDelivering(Model model,HttpSession session){
        User user=(User)session.getAttribute("loginUser");
       List<Borrow> borrows= borrowRepository.findDelivering(user.getUsername(),1);
       if(!borrows.isEmpty()){
           List<Book> books=new ArrayList<>();
           for(Borrow borrow:borrows){
               books.add(bookRepository.findNumbering(borrow.getNumbering()));
           }
           model.addAttribute("books",books);
           model.addAttribute("borrows",borrows);
           return "my_page";
       }
      else
          return "my_page";
    }

    @ResponseBody
    @PostMapping("/cancelOrder")   //取消订单
    public void cancelOrder(@RequestParam("index") Integer i,HttpSession session){
        User user=(User)session.getAttribute("loginUser");
        Borrow borrow=borrowRepository.findDelivering(user.getUsername(),1).get(i);
        borrow.setType(0);
        borrowRepository.save(borrow);
    }


    @ResponseBody
    @PostMapping("/arrived")  //用户确认书本送达
    public void arrived(HttpSession session,@RequestParam("index") Integer i){
        User user=(User)session.getAttribute("loginUser");
        Borrow borrow= borrowRepository.findDelivering(user.getUsername(),1).get(i);
        borrow.setType(2);  //借阅的图书进入借阅未归还状态
        borrowRepository.save(borrow);
    }


    @ResponseBody
    @PostMapping("/returnBook")   //用户确认归还图书
    public void returnBook(HttpSession session,@RequestParam("index") Integer i){
        Date date=new Date();  //获取当前系统的时间
        User user=(User)session.getAttribute("loginUser");
        Borrow borrow= borrowRepository.findDelivering(user.getUsername(),2).get(i);
        borrow.setType(3);  //归还借阅的图书，发出还书申请后的借阅状态
        borrow.setReturnTime(date);
        Long allTime= borrow.getReturnTime().getTime()-borrow.getTime().getTime();  //用户总共借阅的时长
        borrow.setBorrowedTime(LastTime.calculatingTime(allTime));//以天、时、分、秒的形式记录总借阅时长
        borrowRepository.save(borrow);
    }

    @RequestMapping("/my_finished")  //用户借阅未归还
    public String borrowFinished(HttpSession session,Model model){
        Date date=new Date(); //获取当前系统时间
        User user=(User)session.getAttribute("loginUser");
        List<Borrow> borrows2=borrowRepository.findDelivering(user.getUsername(),2);//借阅中未归还
        List<Book> books=new ArrayList<>();
        for(Borrow borrow2:borrows2){
           Book book= bookRepository.findNumbering(borrow2.getNumbering());  //通过ISBN找到指定图书信息
            books.add(book);
        }
        for(Integer i=0;i<books.size();i++){
         Long millisecond= (books.get(i).getMax_day())*24*60*60*1000L;  //最大借阅时间（毫秒）
            Long m=millisecond-(date.getTime()-borrows2.get(i).getTime().getTime()); //计算剩余借阅时间
          borrows2.get(i).setLastTime(LastTime.calculatingTime(m));//通过静态方法calculatingTime将毫秒转换为天、时、分、秒的格式
        }
        Double[] totalPrices=new Double[books.size()];
        for(Integer i=0;i<books.size();i++){
            totalPrices[i]=books.get(i).getPrice()*borrows2.get(i).getQuantity(); //计算每本书的总价
        }
        model.addAttribute("books",books);
        model.addAttribute("borrows2",borrows2);
        model.addAttribute("totalPrices",totalPrices);

        return "my_finished";
    }


    @RequestMapping("/history")   //用户历史书单
    public String borrowHistory(HttpSession session,Model model){
        User user=(User)session.getAttribute("loginUser");
        List<Borrow> borrows3=borrowRepository.findDelivering(user.getUsername(),4);//已还的书籍
        List<Book> books=new ArrayList<>();
        for(Borrow borrow3:borrows3){
            Book book= bookRepository.findNumbering(borrow3.getNumbering());  //通过ISBN找到指定图书信息
            books.add(book);
        }
        Double[] totalPrices=new Double[books.size()];
        for(Integer i=0;i<books.size();i++){
            totalPrices[i]=books.get(i).getPrice()*borrows3.get(i).getQuantity(); //计算每本书的总价
        }
        model.addAttribute("books",books);
        model.addAttribute("borrows3",borrows3);
        model.addAttribute("totalPrices",totalPrices);
        return "history";
    }

    @RequestMapping("/library")   //图书馆用户主页
    public String immediateDelivery(HttpSession session,Model model){
        User user=(User)session.getAttribute("loginUser");
        List<DeliveryInfo> libDeliveries=deliveryInfoRepository.findByUsername(user.getUsername());
        if(!libDeliveries.isEmpty()) {
            DeliveryInfo libDelivery = libDeliveries.get(0);
            model.addAttribute("lib_delivery",libDelivery);//图书馆信息
        }
            List<Book> bks=bookRepository.findByUsername(user.getUsername());//找到指定图书馆的所有图书信息
            Integer total=0;
            for(Book b :bks){
                total=total+b.getBw_amount();       //图书馆总借阅量
            }
            List<Borrow> firstBorrows=borrowRepository.findByLibraryType(1);  //所有需要配送的信息
            List<Borrow> borrows=new ArrayList<>();
            List<Book> books=new ArrayList<>();
            for(Integer i=0;i<firstBorrows.size();i++){
                for(Book bk : bks){
                    if(firstBorrows.get(i).getNumbering().equals(bk.getNumbering())){   //判断是否为指定图书馆的借阅信息
                        borrows.add(firstBorrows.get(i));
                        books.add(bk);
                    }
                }
            }

            List<DeliveryInfo> deliveryInfos=new ArrayList<>();
            for(Integer i=0;i<borrows.size();i++){
                Borrow b=borrows.get(i);
                Integer j=b.getSerialNumber(); //配送信息的索引值
                DeliveryInfo d= deliveryInfoRepository.findByUsername(b.getUsername()).get(j); //获取指定的配送信息
                deliveryInfos.add(d);
            }
            Double[] totalPrices=new Double[books.size()];
            for(Integer i=0;i<books.size();i++){
                totalPrices[i]=books.get(i).getPrice()*borrows.get(i).getQuantity(); //计算每本书的总价
            }
            model.addAttribute("total",total);
            model.addAttribute("books",books);
            model.addAttribute("borrows2",borrows);
            model.addAttribute("deliveries",deliveryInfos);//借书用户的配送信息
            model.addAttribute("totalPrices",totalPrices);
            return "library";
    }


    @ResponseBody
    @PostMapping("/CheckOrderCancelled")    //检查订单是否被用户取消、发起还书申请
    public String CheckOrderCancelled(HttpSession session){
        User user=(User)session.getAttribute("loginUser");
        List<Book> books=bookRepository.findByUsername(user.getUsername());//图书馆所有图书
        List<Borrow> firstBorrows=borrowRepository.findByLibraryType(1);  //所有需要配送的信息
        List<Borrow> borrows=new ArrayList<>();

        for(Integer i=0;i<firstBorrows.size();i++){
            for(Book bk : books){
                if(firstBorrows.get(i).getNumbering().equals(bk.getNumbering())){   //判断是否为指定图书馆的借阅信息
                    borrows.add(firstBorrows.get(i));   //找到属于该图书馆的配送订单
                }
            }
        }
        for(Integer j=0;j<borrows.size();j++){

            if(borrows.get(j).getType().equals(0)){  //用户取消订单
             Book b=bookRepository.findByNumbering(borrows.get(j).getNumbering()).get(0);
                borrows.get(j).setLibraryType(0);   //将取消的订单type设置为0
                borrowRepository.save(borrows.get(j));  //保存修改信息
                return "书名："+b.getTitle()+"、ISBN："+b.getNumbering()+"、下单时间："
                        +borrows.get(j).getTime()+"的订单被用户取消了";
            }
            else if(borrows.get(j).getType().equals(3)){   //用户是否发起了还书申请
                Borrow b=borrows.get(j);
                b.setType(4);     //正在回收图书的状态
                borrowRepository.save(b);
                return "用户："+b.getUsername()+"在"+b.getReturnTime()+"发起了还书申请，请进入页面查看详情！";
            }
        }
        return "";
    }

    @RequestMapping("/ReturnApplication")  //申请归还图书详情
    public String ReturnApplication(HttpSession session,Model model){
        User user=(User)session.getAttribute("loginUser");
        List<Borrow> firstBorrows=borrowRepository.findReturnApplication(4,2);  //所有的归还申请
        List<Book> bks=bookRepository.findByUsername(user.getUsername());
        List<Book> books=new ArrayList<>();
        List<Borrow> borrows=new ArrayList<>();
        for(Integer i=0;i<firstBorrows.size();i++){
            for(Book bk : bks){
                if(firstBorrows.get(i).getNumbering().equals(bk.getNumbering())){   //判断是否为指定图书馆的借阅信息
                    borrows.add(firstBorrows.get(i));
                    books.add(bk);
                }
            }
        }
        List<DeliveryInfo> deliveryInfos=new ArrayList<>();
        for(Borrow b :borrows){
            DeliveryInfo d=deliveryInfoRepository.findByUsername(b.getUsername()).get(b.getSerialNumber());
            deliveryInfos.add(d);        //找到对应的配送信息
        }
        model.addAttribute("books",books);
        model.addAttribute("borrows2",borrows);
        model.addAttribute("deliveries",deliveryInfos);//还书用户的配送信息
        return "ReturnApplication";
    }

    @ResponseBody
    @PostMapping("/successfulDelivery")  //图书馆确认书本送达
    public void  successfulDelivery(HttpSession session,@RequestParam("index") Integer i){
        User user=(User)session.getAttribute("loginUser");
        List<Book> books=bookRepository.findByUsername(user.getUsername());
        List<Borrow> borrows=borrowRepository.findByLibraryType(1);
        List<Borrow> lastBorrows=new ArrayList<>();
        for(Borrow borrow:borrows){
            for(Integer j=0;j<books.size();j++){
                if(borrow.getNumbering().equals(books.get(j).getNumbering())){
                    lastBorrows.add(borrow);
                }
            }
        }
        lastBorrows.get(i).setLibraryType(2);//借阅的图书进入借阅未归还状态
        borrowRepository.save(lastBorrows.get(i));
    }

    @ResponseBody
    @PostMapping("/returningSuccess")  //图书馆确认图书归还
    public void  returningSuccess(HttpSession session,@RequestParam("index") Integer i){
        User user=(User)session.getAttribute("loginUser");
        List<Book> books=bookRepository.findByUsername(user.getUsername());
        List<Borrow> borrows=borrowRepository.findReturnApplication(4,2);
        List<Borrow> lastBorrows=new ArrayList<>();
        for(Borrow borrow:borrows){
            for(Integer j=0;j<books.size();j++){
                if(borrow.getNumbering().equals(books.get(j).getNumbering())){
                    lastBorrows.add(borrow);
                }
            }
        }
        lastBorrows.get(i).setLibraryType(3);//借阅的图书归还后，进入历史状态
        borrowRepository.save(lastBorrows.get(i));
    }



    @RequestMapping("/lib_finished")//图书馆交易中的订单显示
    public String libFinished(HttpSession session,Model model){
        Date date=new Date();
        User user=(User)session.getAttribute("loginUser");
        List<Book> bks=bookRepository.findByUsername(user.getUsername());//找到指定图书馆的所有图书信息
        List<Borrow> firstBorrows=borrowRepository.findByLibraryType(2);  //所有需要配送的信息
        List<Borrow> borrows=new ArrayList<>();
        List<Book> books=new ArrayList<>();
        for(Integer i=0;i<firstBorrows.size();i++){
            for(Book bk : bks){
                if(firstBorrows.get(i).getNumbering().equals(bk.getNumbering())){   //判断是否为指定图书馆的借阅信息
                    borrows.add(firstBorrows.get(i));
                    books.add(bk);
                }
            }
        }
        for (Integer j=0;j<books.size();j++){
            Book b=books.get(j);
            Long millisecond= (b.getMax_day())*24*60*60*1000L;  //最大借阅时间（毫秒）
            Long m=millisecond-(date.getTime()-borrows.get(j).getTime().getTime()); //计算剩余借阅时间
            borrows.get(j).setLastTime(LastTime.calculatingTime(m));
        }
        List<DeliveryInfo> deliveryInfos=new ArrayList<>();
        for(Integer i=0;i<borrows.size();i++){
            Borrow b=borrows.get(i);
            Integer j=b.getSerialNumber();
            DeliveryInfo d= deliveryInfoRepository.findByUsername(b.getUsername()).get(j);
            deliveryInfos.add(d);
        }
        Double[] totalPrices=new Double[books.size()];
        for(Integer i=0;i<books.size();i++){
            totalPrices[i]=books.get(i).getPrice()*borrows.get(i).getQuantity(); //计算每本书的总价
        }
        model.addAttribute("books",books);
        model.addAttribute("borrows2",borrows);
        model.addAttribute("deliveries",deliveryInfos);
        model.addAttribute("totalPrices",totalPrices);
        return "lib_finished";
    }

    @RequestMapping("/lib_history")//图书馆历史订单显示
    public String libHistory(HttpSession session,Model model){
        User user=(User)session.getAttribute("loginUser");
        List<Book> bks=bookRepository.findByUsername(user.getUsername());//找到指定图书馆的所有图书信息
        List<Borrow> firstBorrows=borrowRepository.findByLibraryType(3);  //所有需要配送的信息
        List<Borrow> borrows=new ArrayList<>();
        List<Book> books=new ArrayList<>();
        for(Integer i=0;i<firstBorrows.size();i++){
            for(Book bk : bks){
                if(firstBorrows.get(i).getNumbering().equals(bk.getNumbering())){   //判断是否为指定图书馆的借阅信息
                    borrows.add(firstBorrows.get(i));
                    books.add(bk);
                }
            }
        }

        List<DeliveryInfo> deliveryInfos=new ArrayList<>();
        for(Integer i=0;i<borrows.size();i++){
            Borrow b=borrows.get(i);
            Integer j=b.getSerialNumber();
            DeliveryInfo d= deliveryInfoRepository.findByUsername(b.getUsername()).get(j);
            deliveryInfos.add(d);
        }
        Double[] totalPrices=new Double[books.size()];
        for(Integer i=0;i<books.size();i++){
            totalPrices[i]=books.get(i).getPrice()*borrows.get(i).getQuantity(); //计算每本书的总价
        }
        model.addAttribute("books",books);
        model.addAttribute("borrows2",borrows);
        model.addAttribute("deliveries",deliveryInfos);
        model.addAttribute("totalPrices",totalPrices);
        return "lib_history";
    }
}

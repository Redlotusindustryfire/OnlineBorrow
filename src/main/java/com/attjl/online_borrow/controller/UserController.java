package com.attjl.online_borrow.controller;

import com.attjl.online_borrow.entity.User;
import com.attjl.online_borrow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/{page}")
    public String hello(@PathVariable String page){
        return page;
    }

    @GetMapping("/wa/{id}")
    public User getUser(@PathVariable("id") Integer id){
        User user=userRepository.findOne(id);
        return user;
    }
    @ResponseBody
    @PostMapping("/HomePage/reg")  //注册功能
    public String Reg( User user, @RequestParam("pw1") String pw1,@RequestParam("pw2") String pw2,@RequestParam("userType") String userType){
                List<User> u= userRepository.findByUsername(user.getUsername());
                if(!u.isEmpty()) {    //判断数据库中是否存在相同的用户名
                    return "用户名已被注册";
                }
                else {
                    if(pw1.equals(pw2)){
                        user.setPassword(pw1);
                        if ("general".equals(userType)) {
                            user.setType(1);    //普通用户type为1
                        }
                        else {
                            user.setType(2);     //图书馆用户type为2
                        }
                        User save=userRepository.save(user);
                        return "注册成功，请登录！";
                    }
                }
                return "";
    }

    @ResponseBody
    @PostMapping("/HomePage/login")
    public User Login(User user, HttpSession session){
       List<User> u= userRepository.findByUsername(user.getUsername());
        if(u.isEmpty()){
            user.setNickname("用户名不存在！");
            return user;
        }
        else if(!u.get(0).getPassword().equals(user.getPassword())){
            user.setNickname("密码错误！");
            return user;
        }
        else {
           session.setAttribute("loginUser",u.get(0));  //登录成功后，用session保存用户信息
           User user1=(User)session.getAttribute("loginUser");  //将保存的session强制转换为User，返回给ajax
            return user1;
        }

    }

    @RequestMapping("/exit")   //退出
    public void Exit(HttpSession session,@RequestParam("exitP") String exitP){
        if(!exitP.isEmpty()) {
            session.setAttribute("loginUser", null);//清空session
        }
    }

    @PostMapping("/change_pw")       //修改密码
    public String ChangePw(Model model,User user,HttpSession session,@RequestParam("pw1") String pw1,@RequestParam("pw2") String pw2){
        User user1=(User)session.getAttribute("loginUser"); //从session获取登录的对象信息
        if(!user1.getPassword().equals(user.getPassword())){
            model.addAttribute("CPMsg","原密码填写错误！");
        }
        else {
            if (pw1.equals(pw2)){
                user.setPassword(pw1);
                userRepository.updateUserPw(pw1,user1.getId());
                model.addAttribute("CPMsg","密码修改成功！请重新登录");
                session.setAttribute("loginUser","");  //将session清空
            }
            else {
                model.addAttribute("CPMsg","新密码填写不一致！");
            }
        }
        return "password_change";
    }

    @PostMapping("/changePsInfo")    //修改昵称和性别
    public String changePsInfo(Model model,User user,HttpSession session){
        User user1=(User)session.getAttribute("loginUser"); //从session获取登录的对象信息
        userRepository.updateUserInfo(user.getNickname(),user.getSex(),user1.getId());
        model.addAttribute("msg_cpi","信息修改成功！");
        return "personal_inform";
    }


}

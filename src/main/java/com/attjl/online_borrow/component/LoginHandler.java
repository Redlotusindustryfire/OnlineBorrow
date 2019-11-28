package com.attjl.online_borrow.component;

import com.attjl.online_borrow.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandler implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
       Object user= httpServletRequest.getSession().getAttribute("loginUser");
       if(user==null){
           //未登录，拦截请求资源，返回到首页
           httpServletRequest.getRequestDispatcher("/HomePage").forward(httpServletRequest,httpServletResponse);
           return false;
       }
       else { //已登录
         return true;
       }


    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

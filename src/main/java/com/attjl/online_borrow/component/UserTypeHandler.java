package com.attjl.online_borrow.component;

import com.attjl.online_borrow.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserTypeHandler implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Object object= httpServletRequest.getSession().getAttribute("loginUser");
        User user=(User)object;
        if(user!=null){  //这里必须判断用户是否登录，只有登录的用户才需要进一步判断
            if(user.getType().equals(1)){   //普通用户的拦截器
                httpServletRequest.getRequestDispatcher("/HomePage").forward(httpServletRequest,httpServletResponse);
                return false;
            }
            else {
                return true;
            }
        }
        else
            return true;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}






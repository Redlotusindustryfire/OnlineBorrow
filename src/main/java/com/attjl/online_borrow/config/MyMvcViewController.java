package com.attjl.online_borrow.config;
import com.attjl.online_borrow.component.LoginHandler;
import com.attjl.online_borrow.component.UserType2Handler;
import com.attjl.online_borrow.component.UserTypeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class MyMvcViewController extends WebMvcConfigurerAdapter{
        @Override
        public void addViewControllers(ViewControllerRegistry registry) {//视图映射
            registry.addViewController("/").setViewName("HomePage");

        }
        @Bean
        public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
            WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
                @Override
                public void addViewControllers(ViewControllerRegistry registry) {
                    registry.addViewController("/new_list").setViewName("new_bklist");
                }

                @Override
                public void addInterceptors(InterceptorRegistry registry) {  //拦截器配置
                    //未登录用户
                    registry.addInterceptor(new LoginHandler()).addPathPatterns("/address_list",
                    "/book_list","/completed","/lib_information","/lib_pwd","/library","/my_cart","/my_finished",
                     "/my_page","/new_bklist","/password_change","/personal_inform","/ship_address","/Submit_orders/**",
                    "/transacting");

                    //普通用户
                    registry.addInterceptor(new UserTypeHandler()).addPathPatterns("/book_list",
                            "/lib_information","/new_bklist","/completed","/library","/transacting");

                    //图书馆用户
                    registry.addInterceptor(new UserType2Handler()).addPathPatterns("/my_cart","/my_finished",
                            "/my_page","/personal_inform","/ship_address","/Submit_orders/**","/Categories/**","/shop_trading/**");
                }
            };
            return adapter;
        }

}

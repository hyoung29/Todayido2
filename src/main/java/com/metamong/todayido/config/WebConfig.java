//package com.metamong.todayido.config;
//
//import com.metamong.todayido.util.SessionIntercepter;
//import org.aopalliance.intercept.Interceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Autowired
//    private SessionIntercepter intercepter;
//
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(intercepter) //인터셉터 등록
//                .addPathPatterns("/**") //인터셉터 범위 지정
//                .excludePathPatterns("/","/css/**","/js/**","/images/**")
//                .excludePathPatterns("/joinForm","/loginForm","/idCheck","AdminController","BoardController","BoardRestController","MailController","OwnerController","ReserveFormController",
//                        "SearchController","ShowAddContentsFormController","UseController")
//                .excludePathPatterns("/loginProc","/joinProc","/error/**","AdminController","BoardController","BoardRestController","MailController","OwnerController","ReserveFormController",
//                        "SearchController","ShowAddContentsFormController","UseController");
//
//
//    }
//}
//

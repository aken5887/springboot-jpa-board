package com.mvc.jpaboard.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        log.info("preHandle.................................");

        Enumeration<String> en = request.getSession().getAttributeNames();

        while(en.hasMoreElements()){
            String name = en.nextElement();
            log.info("NAME: " + name);
            log.info("" + request.getSession().getAttribute(name));
        }


        String dest = request.getParameter("dest");

        if(dest != null){
            request.getSession().setAttribute("dest",  dest);
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }


}
package com.mvc.jpaboard.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        log.info("-------------------determineTargetUrl----------------------------");
        Object dest = request.getSession().getAttribute("dest");
        String nextURL = null;

        if(dest != null){
            request.getSession().removeAttribute("dest");
            nextURL = (String) dest;
        }else{
            nextURL =  "/boards/list";
        }

        log.info("----------------------------------------"+nextURL+"--------------------------------------------");
        return nextURL;

    }
}

package com.mvc.jpaboard.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/common")
public class CommonController {

    @GetMapping("/")
    public String index() {
        log.info("index");
        return "/boards/list";
    }

    @RequestMapping("/guest")
    public void forGuest(){
        log.info("guest");
    }

    @RequestMapping("/manager")
    public void forManager(){
        log.info("manager");
    }

    @RequestMapping("/admin")
    public void forAdmin(){
        log.info("admin");
    }

    @GetMapping("/login")
    public void login(){
        log.info("login..");
    }

    @GetMapping("/accessDenied")
    public void accessDenied(){
        log.info("accessDenied ..");
    }

    @GetMapping("/logout")
    public void logout() {
        log.info("logout..");
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping("/adminSecret")
    public void forAdminSecret() {
        log.info("admin secret");
    }

    @Secured({"ROLE_MANAGER"})
    @RequestMapping("/managerSecret")
    public void forManagerSecret() {
        log.info("manager secret");
    }
}

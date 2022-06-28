package jpabook.SpringBootJPA.controller;

import jpabook.SpringBootJPA.domain.Address;
import jpabook.SpringBootJPA.domain.Member;
import jpabook.SpringBootJPA.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final MemberService memberService;

    @RequestMapping("/")
    public String home() {
        log.info("home controller");
        return "home";
    }//end home()

}//end class()

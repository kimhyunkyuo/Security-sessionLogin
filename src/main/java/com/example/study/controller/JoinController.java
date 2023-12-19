package com.example.study.controller;

import com.example.study.dto.JoinDTO;
import com.example.study.dto.LoginDTO;
import com.example.study.entity.UserEntity;
import com.example.study.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class JoinController {

    private final  UserService userService;

   @GetMapping(value = { "/"})
    public String home(Model model, Authentication auth) {

        if(auth != null) {
            UserEntity loginUser = userService.getLoginUserByLoginId(auth.getName());
            if (loginUser != null) {
                model.addAttribute("userName", loginUser.getUserName());
            }
        }
        return "index";
    }


    @GetMapping("/join")
    public String joinPage(Model model) {
       model.addAttribute("joinDTO", new JoinDTO());
        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute JoinDTO joinDTO, BindingResult bindingResult, Model model) {


        // loginId 중복 체크
        if(userService.checkLoginIdDuplicate(joinDTO.getLoginId())) {
            bindingResult.addError(new FieldError("joinDTO", "loginId", "로그인 아이디가 중복됩니다."));
        }
        // password와 passwordCheck가 같은지 체크
        if(!joinDTO.getUserPassword().equals(joinDTO.getUserPassWordCheck())) {
            bindingResult.addError(new FieldError("joinDTO", "userPassWordCheck", "비밀번호가 일치하지 않습니다."));
        }
        if(bindingResult.hasErrors()) {
            return "join";
        }
        userService.join(joinDTO);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
       model.addAttribute("loginDTO", new LoginDTO());
        return "login";
    }



    /*@GetMapping("/admin")
    public String adminPage( Model model) {
        model.addAttribute("loginType", "security-login");
        model.addAttribute("pageName", "Security 로그인");

        return "admin";


    }*/


}




package com.ifif.controller;

import com.ifif.dto.MemberFormDto;
import com.ifif.entity.Member;
import com.ifif.service.MailService;
import com.ifif.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    String confirm = "";
    boolean confirmCheck = false;


    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto",new MemberFormDto());
        return "member/memberForm";
    }
    @PostMapping(value = "/new")
    public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult,
                             Model model) {
        // @Valid 붙은 객체를 검사해서 결과에 에러가 있으면 실행
        if(bindingResult.hasErrors()){
            return "member/memberForm";//다시 회원가입으로 돌려보닙니다.
        }
        if(!confirmCheck){
            model.addAttribute("errorMessage","이메일 인증을 하세요.");
            return "member/memberForm";
        }
        try{
            //Member 객체 생성
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            //데이터베이스에 저장
            memberService.saveMember(member);
        }
        catch (IllegalStateException e){
            System.out.println("aaaaaaaaaaaaaaaaaa");

            model.addAttribute("errorMessage",e.getMessage());
            System.out.println("bbbbbbbbbbbbbbbbbbaaa");
            return "member/memberForm";
        }
        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String loginMember(){
        return "/member/memberLoginForm";
    }
    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }

    @PostMapping("/{email}/emailConfirm")
    public @ResponseBody ResponseEntity emailConfrim(@PathVariable("email") String email)
        throws Exception{
        confirm = mailService.sendSimpleMessage(email);
        return new ResponseEntity<String>("인증 메일을 보냈습니다.", HttpStatus.OK);
    }

    @PostMapping("/{code}/codeCheck")
    public @ResponseBody ResponseEntity codeConfirm(@PathVariable("code")String code)
        throws Exception{
        if(confirm.equals(code)){
            confirmCheck = true;
            return new ResponseEntity<String>("인증 성공하였습니다.",HttpStatus.OK);
        }
        return new ResponseEntity<String>("인증 코드를 올바르게 입력해주세요.",HttpStatus.BAD_REQUEST);
    }
    @GetMapping(value = "/delivery")
    public String delivery(){
        return "/delivery/delivery tracking";
    }

    @GetMapping(value ="/map")
    public String map(){return "/map/map";}
}

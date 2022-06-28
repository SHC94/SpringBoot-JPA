package jpabook.SpringBootJPA.controller;

import jpabook.SpringBootJPA.domain.Address;
import jpabook.SpringBootJPA.domain.Member;
import jpabook.SpringBootJPA.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 가입 페이지 이동
     * @param model
     * @return
     */
    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }//end createForm()

    /**
     * 회원 가입
     * @param form
     * @param result
     * @return
     */
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        //@Valid 통과를 못했을 경우
        //error에 대한 데이터가 BindingResult에 적재
        //form 데이터도 유지
        if(result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }//end create()

    /**
     * 회원 목록 페이지 이동
     * @param model
     * @return
     */
    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }//end list()

}//end class()

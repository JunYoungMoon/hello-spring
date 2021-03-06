package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

// 스프링이 올라올때 컴포넌트 스캔으로 @Component가 있는걸 다 찾아서 객체를 하나씩 생성하고
// 스프링 컨테이너에 등록한다.
@Controller
public class MemberController {

    // memberService은 많은 기능이 있는게 아니라서 매번 인스턴스를 생성할 필요 없이
    // 공용으로 하나만 쓸수있도록 스프링 컨테이너에게 등록을 하고 사용한다.
    //private final MemberService memberService = new MemberService();

    private MemberService memberService;

    //setMemberService로 만들게 되면 누구나 쉽게 정보를 변경할수 있기 때문에 이방법을 쓰지 않는다.
    //생성자를 통해서 한번만 호출하면됨.
    //@Autowired
    //public void setMemberService(MemberService memberService) {
    //    this.memberService = memberService;
    //}

    // 컨트롤러가 컨테이너에 등록이 완료되면 생성자를 호출한다.
    // 스프링 컨테이너에 등록이 된것만 Autowired가 가능하다.
    // 생성자에 Autowired가 있으면 MemberService가 필요하다고 판단 하고
    // 스프링 bean에 등록이 되어있는 MemberService 객체를 넣어준다. DI
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }
}

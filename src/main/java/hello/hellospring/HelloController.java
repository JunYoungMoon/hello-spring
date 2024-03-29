package hello.hellospring;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("data") String name, Model model) {
        model.addAttribute("data", name);
        return "hello-template";
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("data") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    @Getter
    @Setter
    static class Hello{
        private String name;
    }
}
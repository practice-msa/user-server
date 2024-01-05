package msa.user.controller;

import msa.user.service.SignInResultDto;
import msa.user.service.SignService;
import msa.user.service.SignServiceImpl;
import msa.user.service.SignUpResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sign-api")
public class SignController {
    private final SignService signService;

    @Autowired
    public SignController(SignServiceImpl signService){
        this.signService = signService;
    }

    @PostMapping("/sign-in")
    public SignInResultDto signIn(@RequestBody Map<String, String> requestBody) throws  RuntimeException{
        String id = requestBody.get("id");
        String password = requestBody.get("password");
        SignInResultDto signInResultDto = signService.signIn(id,password);
        return signInResultDto;
    }

    @PostMapping("/sign-up")
    public SignUpResultDto signup(@RequestBody Map<String, String> requestBody) {
        String id = requestBody.get("id");
        String password = requestBody.get("password");
        String name = requestBody.get("name");
        String role = requestBody.get("role");
        System.out.println("test");
        SignUpResultDto signUpResultDto = signService.signUp(id, password, name, role);
        return signUpResultDto;
    }

    @GetMapping("/exception")
    public void exceptionTest() throws RuntimeException {
        throw new RuntimeException("접근이 금지되었습니다.");
    }
}

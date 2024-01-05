package msa.user.service;

import io.jsonwebtoken.Jwt;
import msa.user.domain.User;
import msa.user.jwt.JwtTokenProvider;
import msa.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SignServiceImpl implements  SignService{

    public UserRepository userRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;

    @Autowired
    public SignServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SignUpResultDto signUp(String id, String password, String name, String role){
        User user;
        if(role.equalsIgnoreCase("admin")){
            user = User.builder()
                    .uid(id)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .build();
        }else{
            user = User.builder()
                    .uid(id)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }

        User savedUser = userRepository.save(user);
        SignUpResultDto signUpResultDto = new SignUpResultDto();

        if(!savedUser.getName().isEmpty()){
            setSuccessResult(signUpResultDto);
        }else{
            setFailResult(signUpResultDto);
        }

        return signUpResultDto;
    }

    @Override
    public SignInResultDto signIn(String id, String password) throws RuntimeException{
        User user = userRepository.getByUid(id);
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new RuntimeException();
        }

        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(String.valueOf(user.getUid()),user.getRoles()))
                .build();

        setSuccessResult(signInResultDto);

        return signInResultDto;
    }

    private void setSuccessResult(SignUpResultDto result){
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.msg);
    }

    private void setFailResult(SignUpResultDto result){
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }
}

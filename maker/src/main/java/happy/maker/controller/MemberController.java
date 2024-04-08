package happy.maker.controller;

import happy.maker.domain.Member;
import happy.maker.dto.MemberDto;
import happy.maker.model.Auth;
import happy.maker.security.TokenProvider;
import happy.maker.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Transactional
public class MemberController {
    private final MemberService memberService;

    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Auth.SignUp request){
        //회원가입을 위한 API: 간단하다. 우리가 받은 request정보를 memberReposittory에 저장해주면됨
        var result = this.memberService.register(request);
        return ResponseEntity.ok(result);//결과를 반환해 주는 것으로 함수를 종료함.
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Auth.SignIn request){
        //로그인용 API:1. signin에서 입력받은 아이디와 패스워드가 일치하는지 먼저확인. 2.인증이 되었다면, tokenProvider에 구현해준 generate~로 토큰을 생성해서 반환해 줄거다.
        //멤버서비스에서 먼저 추가로 메서드를 하나더 구현(authenticate)
        var member = this.memberService.authenticate(request);
        var token = this.tokenProvider.generateToken(member.getUsername(), member.getRoles());//엔티티를 기준으로해서 토큰을 만들어주자.
        return ResponseEntity.ok(token);
    }



}

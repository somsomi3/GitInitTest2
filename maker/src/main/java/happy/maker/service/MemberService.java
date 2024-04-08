package happy.maker.service;

import happy.maker.domain.Member;
import happy.maker.dto.MemberDto;
import happy.maker.model.Auth;
import happy.maker.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static happy.maker.dto.MemberDto.toMemberEntity;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional

public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository; //멤버변수 만들어 주기.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) this.memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("couldn't find user -> " + username));//만약에 없는 경우의 사용자 메시지.

    }
    //회원가입기능과 로그인기능을 지원하는

    public Member register(Auth.SignUp member){//회원가입에대한메서드
        boolean exists  =this.memberRepository.existsByUsername(member.getUsername());
        if (exists){
            throw new RuntimeException("이미 사용중인 아이디 입니다.");
        }
        member.setPassword(this.passwordEncoder.encode(member.getPassword()));//이렇게 되면 member에는 실제 password가 인코딩된 값이 들어있겠다.
        var reasult = this.memberRepository.save(member.toEntity());//결과로는 저장된 엔티티 값을 받게 된다.
        //중복정보가 없는 경우, 정보를 DB에 저장해줘야하는데, 이때 날것 그대로의 비밀번호저장시에 개인정보 노출우려가 있으므로, Password는 한번
        //암호화를 해서 넣어주게 된다.
        //회원가입을 하기위한 해당 정보를 받았으면, memberRepository에 동일한 아이디가 있는지 확인해주기

        return reasult;
    }

    public Member authenticate(Auth.SignIn member){//로그인 할때, 검증을 하기위한 메서드
        var user = this.memberRepository.findByUsername(member.getUsername())
                .orElseThrow(() -> new RuntimeException("존재 하지 않는 아이디야~"));
        //들어오는 값도 인코딩해서 비교해보자
        if (!this.passwordEncoder.matches(member.getPassword(), user.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        return user;//정상적으로 일치하는 경우
    }



}
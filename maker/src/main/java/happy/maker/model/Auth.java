package happy.maker.model;

import happy.maker.domain.Member;
import lombok.Data;

import java.util.List;

public class Auth {

    @Data
    public static class SignIn {//로그인을 할때 사용할 클래스
        private String username;//로그인할때 반드시 주고받아야 하는 정보: username과 password
        private String password;
    }

    @Data
    public static class SignUp {//회원가입을 할때 사용할 클래스
        private String username;
        private String password;
        private List<Member> roles;/

        public Member toEntity(){
            return Member.builder()
                    .username(this.username)
                    .password(this.password)
                    .roles(this.roles)
                    .build();
        }
    }
}

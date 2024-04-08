package happy.maker.security;

import happy.maker.domain.Member;
import happy.maker.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;//룸북말고 어노테이션 값으로 가져와야 jwt주소 가져오는거에 빨간 줄 안뜸.
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final long TOKEN_EXPIRED_TIME = 1000 * 60 * 6;//1hour
    private static final String KEY_ROLES = "roles";

    @Value("{spring.jwt.secret}")// 아까만든 jwt의 키 값을 가져올 것이다.
    private String secretKey;
    private MemberService memberService;


    /**
     * 토큰 생성(발급)
     *
     * @param username
     * @param roles
     * @return
     */
    public String generateToken(String username, List<Member> roles) {
        Claims claims = Jwts.claims().setSubject(username);//사용자의 권한정보를 저장하기 위한, claim을 만들어 주겠다.
        //먼저 사용자 이름을 저장해주고,
        claims.put(KEY_ROLES, roles);//클래임에 데이터를 저장할때는 키벨류 형테로 저장되어야함.

        var now = new Date();//토큰이 생성된 시간. 이렇게 해서 현재시간을 구해주고,
        var expiredDate = new Date(now.getTime() + TOKEN_EXPIRED_TIME);//토큰 만료시간.(TOKEN_EXPIRED_TIME을 함으로서, 토큰이 생성된시간으로부터 1시간동안 토큰이 유효하다)


        return Jwts.builder()//이렇게 생성된 클레임정보와 만료시간을토큰에 넣어서 생성해 주겠다.
                .setClaims(claims)
                .setIssuedAt(now)//토큰생성시간
                .setExpiration(expiredDate)//토큰만료시간
                .signWith(SignatureAlgorithm.HS512, this.secretKey)//토큰의 시그니쳐를 위해서, 사용할 암호화 알고리즘과 비밀키.
                .compact();

    }

    public UsernamePasswordAuthenticationToken getAuthentication(String jwt) {
        UserDetails userDetails = this.memberService.loadUserByUsername(this.getUsername(jwt));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //토큰이 유효한지 확인하는 메서드

    public String getUsername(String token){
        return this.parseClaims(token).getSubject();
    }

    public boolean validateToken(String token){
        if (!StringUtils.hasText(token)) return false;//토큰의 값이 빈값이라면, 이것은 유효하지 않으므로, false

        var claims = this.parseClaims(token);
        return !claims.getExpiration().before(new Date());//클레임의 만료시간에 현재시간을 비교한 값을 받아서
        //!; 토큰이 유효한지 아닌지 비교해준다.
    }

    private Claims parseClaims(String token) {//토큰으로부터 클레임 정보를 가져오는 메서드 만들기, 토큰으로 부터 클레임 정보를 파싱해 보겠다.
        try {
            return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();

        } catch (ExpiredJwtException e){
            //TODO
            return e.getClaims();
        }
        }

    }
package happy.maker.repository;

import happy.maker.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String userName);//아이디를 기준으로 회원정보를 찾기위해서 사용

    boolean existsByUsername(String username);//회원가입을 할때 이미 존재하는 아이디인지 여부를 확인하기 위해서 사용.
}//Member와 MemberRepository를 사용해서, 회원정보를 DB에서 가져오기 위한 정보는 끝났음.
//-> MemberService코드를 구현해주러감.

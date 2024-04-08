package happy.maker.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    MEMBER_NOT_FOUND("존재하지 않는 회원입니다."),
    INVALID_MEMBER_STATUS_ERROR("PARTNER로 등록된 회원만 매장을 등록할 수 있습니다."),
    ALREADY_EXIST_STORE("이미 존재하는 매장 이름입니다."),
    STORE_NOT_FOUND("존재하지 않는 매장입니다.");
    private final String description;
}

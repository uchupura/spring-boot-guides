package com.guide.oauth2.resource.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ExceptionType {
    OK(200, true, "SS001", "성공"),
    CREATED(201, true, "SS002", "성공"),
    NO_CONTENT(204, true, "SS003", "성공"),
    ACCEPTED(202, true, "SS004", "성공"),

    //파라미터
    MANDATORY_PARAM(400, false, "PA001", "필수 파라미터가 없습니다."),
    INVALID_PARAM(400,false, "PA002", "유효하지 않은 데이터입니다."),
    EPISODE_ID_NOT_FOUND(400,false, "PA003", "episodeId는 필수 값입니다."),
    INVALID_PERIOD(400,false, "PA004", "시작일은 종료일보다 이후일 수 없습니다."),
    DUPLICATE_ID(400,false, "PA005", "이미 사용중인 아이디 입니다."),
    INVALID_FORMAT(400, false, "PA006", "유효하지 않은 형식의 데이터입니다."),
    DUPLICATE_EXTERNAL_USERID(400,false, "PA007", "이미 사용중인 고객 키 입니다."),
    NEW_PASSWORD_DOES_NOT_MATCH(400, false, "PA008", "비밀번호 재 확인값이 일치하지 않습니다."),
    PASSWORD_DOES_NOT_MATCH(400, false, "PA009", "비밀번호가 일치하지 않습니다."),

    //인증 권한
    INVALID_TOKEN(401,false, "AU001", "유효하지 않은 토큰입니다."),
    UNAUTHORIZED_USER(401, false, "AU002", "인증되지 않은 요청입니다."),
    NO_EXIST_USER(401, false, "AU003", "존재하지 않는 사용자입니다."),
    BAD_CREDENTIAL(401, false, "AU004", "아이디 또는 비밀번호가 틀립니다."),
    LOCKED(401, false, "AU005", "계정이 잠겨 있습니다."),
    DISABLED(401, false, "AU006", "계정이 비활성화 상태입니다."),
    EXPIRED_CREDENTIALS(401, false, "AU007", "만료된 계정입니다."),
    FORBIDDEN(403, false, "AU008", "요청하신 작업에 대한 권한이 없습니다"),

    //컨텐트
    NO_EXIST_CONTENT(204, false, "CT001", "존재하지 않는 컨텐츠입니다."),

    METHOD_NOT_ALLOWED(405,false, "NA001", "지원하지 않은 메서드입니다."),

    // 리워드
    REWARD_NOT_EXIST(400, false, "RE001", "해당 리워드 정보는 존재하지 않습니다."),

    // 방송
    DUPLICATED_NICKNAME(500,false, "ES001", "중복되는 닉네임입니다."),

    // 스케쥴러
    ADD_JOB_ERROR(500,false, "LS001", "스케쥴러에 Job 추가 시 오류가 있습니다."),

    // Server Error 5XXX
    SERVICE_ERROR(500,false, "IS5001", "요청을 처리하는 중에 오류가 있습니다.");

    private final int status;

    private final Boolean success;
    private final String code;
    private final String message;

    private final static Map<String, ExceptionType> map = new HashMap<>();
    static {
        for (ExceptionType type : ExceptionType.values()) {
            map.put(type.getCode(), type);
        }
    }

    ExceptionType(int status, Boolean success, String code, String message) {
        this.status = status;
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public static ExceptionType fromCode(String code) {
        return map.get(code);
    }
}

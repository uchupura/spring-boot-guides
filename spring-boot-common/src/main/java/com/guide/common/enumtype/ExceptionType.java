package com.guide.common.enumtype;

public enum ExceptionType {

    // Common
    SERVICE_ERROR("COMMON001", "요청을 처리하는 중에 오류가 있습니다."),
    INVALID_PARAM("COMMON002", "유효하지 않은 데이터입니다."),
    METHOD_NOT_ALLOWED("COMMON003", "지원하지 않은 메서드입니다."),
    UNAUTHORIZED_USER("SECURITY001", "인증되지 않은 사용자입니다."),
    USER_NOT_FOUND("USER001", "유효하지 않은 사용자입니다.")
    ;

    private final String code;
    private final String message;

    ExceptionType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public static ExceptionType resolve(String code) {
        for (ExceptionType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null;
    }
}

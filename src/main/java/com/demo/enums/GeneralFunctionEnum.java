package com.demo.enums;


public enum GeneralFunctionEnum {

    UNDEFINED(0, "未定义", "未定义"),
    USED_VALID(1, "合格", "使用且结果合格"),
    USED_INVALID(2, "不合格", "使用且结果不合格"),
    UNUSED(3, "未使用", "未定义");

    private final String definition;
    private final String longDefinition;
    private final int code;

    GeneralFunctionEnum(int code, String definition, String longDefinition) {
        this.code = code;
        this.definition = definition;
        this.longDefinition = longDefinition;
        
    }

    private static final GeneralFunctionEnum[] FUNCTION_ENUMS = GeneralFunctionEnum.values();

    public static String mapDefinition(int ordinal) {
        return FUNCTION_ENUMS[ordinal].definition;
    }

    public static String mapLongDefinition(int ordinal) {
        return FUNCTION_ENUMS[ordinal].longDefinition;
    }

    public int getCode() {
        return this.code;
    }
}

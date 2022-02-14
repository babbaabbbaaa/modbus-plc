package com.demo.enums;


public enum GeneralFunctionEnum {

    UNDEFINED(0, "未定义"),
    USED_VALID(1, "使用且结果合格"),
    USED_INVALID(2, "使用但结果不合格"),
    UNUSED(3, "未使用");

    private final String definition;

    GeneralFunctionEnum(int code, String definition) {
        this.definition = definition;
    }

    public static String mapDefinition(int ordinal) {
        return GeneralFunctionEnum.values()[ordinal].definition;
    }
}

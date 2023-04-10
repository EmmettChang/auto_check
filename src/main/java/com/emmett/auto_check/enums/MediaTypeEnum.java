package com.emmett.auto_check.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tpf
 * @description
 * @date Created in 2022/08/06
 */
@Getter
@AllArgsConstructor
public enum MediaTypeEnum {

    APPLICATION_FORM_URLENCODED_VALUE("application/x-www-form-urlencoded; charset=utf-8"),
    APPLICATION_JSON("application/json; charset=utf-8");

    private String mediaType;
}

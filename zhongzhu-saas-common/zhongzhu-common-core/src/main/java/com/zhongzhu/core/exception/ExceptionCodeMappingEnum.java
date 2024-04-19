package com.zhongzhu.core.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionCodeMappingEnum {

    CODE_200("200", "Request successful"), CODE_400("400", "bad request"),

    CODE_401("401", "Login status has expired"),

    CODE_403("403", "Access denied without permission"),

    CODE_404("200", "Unable to find the requested resource"),

    CODE_429("429", "Request too frequently"), CODE_500("500", "Internal server error, unable to complete request"),


    // **** 业务异常*** //

    CAPACITY_REFILL_ERROR("1001", "Capacity and refill rate must be positive"),

    P_SYSTEM_ID_IS_NULL("P_SYSTEM_ID_IS_NULL","ID不能为空"),

    OAUTH2_TENANT_ID_REQUIRE("OAUTH2_TENANT_ID_REQUIRE","公司ID不能为空");

    private String code;

    private String message;
}

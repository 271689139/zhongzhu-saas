/*
 * Copyright (c) 2022-2024 KCloud-Platform-Alibaba Author or Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.zhongzhu.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author laokou
 * 全局异常
 */
@EqualsAndHashCode(callSuper = true)
@Data
abstract class GlobalException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4102669900127613541L;

    private final String code;

    private final String msg;

    protected GlobalException(String code) {
        this.code = code;
        this.msg = Enum.valueOf(ExceptionCodeMappingEnum.class, code).getMessage();
    }

    protected GlobalException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}

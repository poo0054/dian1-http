/*
 * Copyright 2023 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dian1.http.example.entity;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author zhangzhi

 */
@Data
public class Request {
    /**
     * 费用科目代码
     */
    @NotNull
    private String expenseAccountCode;
    /**
     * 费用科目类型
     */
    private String expenseAccountType;
    /**
     * 主键
     */
    private Long id;
    /**
     * 产品代码
     */
    private String productCode;
    /**
     * 产品主键
     */
    private Long productId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 服务类型，取数据字典：入库/增值/出库/尾程/退货
     */
    private String serviceType;

    @NotNull
    @Valid
    private OmsContractHead omsContractHead;
}

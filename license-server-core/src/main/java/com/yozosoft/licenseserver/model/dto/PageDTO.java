package com.yozosoft.licenseserver.model.dto;

import lombok.Data;

import javax.validation.constraints.Min;

/**
 * 分页对象
 *
 * @author zhouf
 */
@Data
public class PageDTO {

    @Min(value = 1L, message = "pageNum illegal")
    private Integer pageNum;

    @Min(value = 1L, message = "pageSize illegal")
    private Integer pageSize;
}

package com.zhongzhu.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author shihao.liu
 */
@Data
@NoArgsConstructor
@Schema(name = "PageQuery", description = "分页查询参数")
public class PageQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 6412915892334241813L;

    /**
     * 分页参数.
     */
    public static final String PAGE_QUERY = "pageQuery";

    @Min(1)
    @Schema(name = "pageNum", description = "页码")
    private Integer pageNum = 1;

    @Schema(name = "pageSize", description = "条数")
    @Min(1)
    private Integer pageSize = 10;

    @Schema(name = "pageIndex", description = "索引")
    private Integer pageIndex;

    public PageQuery(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }


    public PageQuery page() {
        this.pageIndex = (pageNum - 1) * pageSize;
        return this;
    }

}

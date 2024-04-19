package com.zhongzhu.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author shihao.liu
 */
@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
@Schema(name = "Datas", description = "对象集合")
public class Datas<T> implements Serializable {

    @Schema(name = "total", description = "总数")
    private long total;

    @Schema(name = "records", description = "对象集合")
    private List<T> records;

    /**
     * 构建空对象集合.
     *
     * @param <T> 泛型
     * @return 空对象集合
     */
    public static <T> Datas<T> of() {
        return new Datas<>(0, new ArrayList<>(0));
    }

    public static <T> Datas<T> of(List<T> list, long total) {
        return new Datas<>(total, list);
    }

}

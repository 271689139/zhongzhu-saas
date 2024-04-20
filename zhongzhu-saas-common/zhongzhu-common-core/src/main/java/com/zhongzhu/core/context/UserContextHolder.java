package com.zhongzhu.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

/**
 * 用户上下文.
 *
 * @author shihao.liu
 */
public class UserContextHolder {

    /**
     * 用户上下文本地线程变量.
     */
    private static final ThreadLocal<User> USER_LOCAL = new TransmittableThreadLocal<>();

    /**
     * 注销本地线程变量.
     */
    public static void clear() {
        USER_LOCAL.remove();
    }

    /**
     * 获取本地线程变量.
     *
     * @return 用户
     */
    public static User get() {
        User user = new User();
        user.setTenantId(1L);
        user.setId(9999L);
        user.setUsername("系统管理员");
        return Optional.ofNullable(USER_LOCAL.get()).orElse(user);
    }

    /**
     * 往本地线程变量写入.
     *
     * @param user 用户
     */
    public static void set(User user) {
        USER_LOCAL.set(user);
    }

    @Data
    @Builder
    @AllArgsConstructor(access = PRIVATE)
    @NoArgsConstructor(access = PRIVATE)
    @Schema(name = "User", description = "用户")
    public static class User {

        @Schema(name = "id", description = "ID")
        private Long id;

        @Schema(name = "username", description = "用户名")
        private String username;

        @Schema(name = "tenantId", description = "租户ID")
        private Long tenantId;

        @Schema(name = "deptId", description = "部门ID")
        private Long deptId;

    }

}

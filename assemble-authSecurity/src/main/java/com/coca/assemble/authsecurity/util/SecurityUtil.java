package com.coca.assemble.authsecurity.util;

import com.coca.assemble.authsecurity.domain.SystemUserSubject;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    /**
     * 获取当前用户信息
     */
    public static SystemUserSubject getUserInfo(){
        SystemUserSubject userDetails = (SystemUserSubject)
                SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        return userDetails;
    }

    /**
     * 获取当前用户ID
     */
    public static Long getUserId(){
        return getUserInfo().getUserId();
    }

    /**
     * 获取当前用户账号
     */
    public static String getUserName(){
        return getUserInfo().getUsername();
    }
}

package com.coca.assemble.authsecurity.authen;

import com.coca.assemble.authsecurity.rbac.po.SystemMenu;
import com.coca.assemble.authsecurity.rbac.po.SystemRole;
import com.coca.assemble.authsecurity.rbac.po.SystemUser;
import com.coca.assemble.authsecurity.rbac.srv.SysUserService;
import com.coca.assemble.common.model.rpc.RpcRequest;
import com.coca.assemble.common.model.rpc.RpcResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RpcAccountProcessApi implements AccountProcessApi{
    @Autowired
    SysUserService sysUserService;


    /**
     * 根据用户名称查询用户信息
     * @param username
     * @return
     */
    @Override
    public RpcResponse<SystemUser> selectUserByName(RpcRequest<String> username) {
        try {
            return RpcResponse.success(sysUserService.selectUserByName(username.getEnity()));
        } catch (Exception e) {
            log.error("执行查询用户信息根据名称失败！",e);
            return RpcResponse.failure("执行查询用户信息根据名称失败");
        }
    }

    /**
     * 执行查询用户角色信息
     * @param userId
     * @return
     */
    @Override
    public RpcResponse<List<SystemRole>> selectSysRoleByUserId(RpcRequest<Long> userId) {
        try {
            return RpcResponse.success(sysUserService.selectSysRoleByUserId(userId.getEnity()));
        } catch (Exception e) {
            log.error("执行查询用户角色信息失败！",e);
            return RpcResponse.failure("执行查询用户角色信息失败");
        }
    }

    /**
     * 执行查询用户资源菜单信息
     * @param userId
     * @return
     */
    @Override
    public RpcResponse<List<SystemMenu>> selectSysMenuByUserId(RpcRequest<Long> userId) {
        try {
            return RpcResponse.success(sysUserService.selectSysMenuByUserId(userId.getEnity()));
        } catch (Exception e) {
            log.error("执行查询用户资源菜单信息失败！",e);
            return RpcResponse.failure("执行查询用户资源菜单信息失败");
        }
    }
}

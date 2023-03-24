package com.coca.assemble.authsecurity.authen;

import com.coca.assemble.authsecurity.rbac.po.SystemMenu;
import com.coca.assemble.authsecurity.rbac.po.SystemRole;
import com.coca.assemble.authsecurity.rbac.po.SystemUser;
import com.coca.assemble.common.model.rpc.RpcRequest;
import com.coca.assemble.common.model.rpc.RpcResponse;

import java.util.List;

public interface AccountProcessApi {
    /**
     * 根据用户名查询实体
     * @Author Sans
     * @CreateTime 2019/9/14 16:30
     * @Param  username 用户名
     * @Return SysUserEntity 用户实体
     */
    RpcResponse<SystemUser>selectUserByName(RpcRequest<String> username);

    /**
     * 根据用户ID查询角色集合
     * @Author Sans
     * @CreateTime 2019/9/18 18:01
     * @Param  userId 用户ID
     * @Return List<SysRoleEntity> 角色名集合
     */
    RpcResponse<List<SystemRole>> selectSysRoleByUserId(RpcRequest<Long> userId);

    /**
     * 根据用户ID查询权限集合
     * @Author Sans
     * @CreateTime 2019/9/18 18:01
     * @Param  userId 用户ID
     * @Return List<SysMenuEntity> 角色名集合
     */
    RpcResponse<List<SystemMenu>> selectSysMenuByUserId(RpcRequest<Long> userId);
}

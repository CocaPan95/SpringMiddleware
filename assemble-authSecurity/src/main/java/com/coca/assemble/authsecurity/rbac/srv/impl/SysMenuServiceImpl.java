package com.coca.assemble.authsecurity.rbac.srv.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coca.assemble.authsecurity.rbac.dao.SysMenuDao;
import com.coca.assemble.authsecurity.rbac.po.SystemMenu;
import com.coca.assemble.authsecurity.rbac.srv.SysMenuService;
import org.springframework.stereotype.Service;

/**
 * @Description 权限业务实现
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SystemMenu> implements SysMenuService {


}
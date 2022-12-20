package com.xwl.mybatishelper.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xwl.mybatishelper.test.entity.SysUser;
import com.xwl.mybatishelper.test.service.ISysUserService;
import com.xwl.mybatishelper.test.vo.QueryUserVO;
import com.xwl.mybatishelper.test.vo.UserVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author xwl
 * @since 2022/12/6 15:44
 */
@RestController
@RequestMapping("/user")
public class SysUserController {
    @Resource
    private ISysUserService iSysUserService;

    @PostMapping("/insert")
    public Object insert(@RequestBody SysUser sysUser) {
        // 支持
        boolean save = iSysUserService.save(sysUser);
        return save;
    }

    @PostMapping("/saveBatch")
    public Object saveOrUpdate(@RequestBody List<SysUser> sysUsers) {
        // 支持
        boolean save = iSysUserService.saveBatch(sysUsers);
        return save;
    }

    @PostMapping("/saveOrUpdate")
    public Object saveOrUpdate(@RequestBody SysUser sysUser) {
        // 支持
        boolean save = iSysUserService.saveOrUpdate(sysUser);
        return save;
    }

    @PostMapping("/insertBySql")
    public Object insertBySql(@RequestBody SysUser sysUser) {
        // 支持
        boolean insertBySql = iSysUserService.insertBySql(sysUser);
        return insertBySql;
    }

    @PostMapping("/updateById")
    public Object updateById(@RequestBody SysUser sysUser) {
        // 支持
        boolean updateById = iSysUserService.updateById(sysUser);
        return updateById;
    }

    @PostMapping("/updateBatchById")
    public Object updateById(@RequestBody List<SysUser> sysUsers) {
        // 支持
        boolean updateById = iSysUserService.updateBatchById(sysUsers);
        return updateById;
    }

    @PostMapping("/updateByWrapper")
    public Object updateByWrapper(@RequestBody SysUser sysUsers) {
        // 支持
        LambdaUpdateWrapper wrapper = Wrappers.lambdaUpdate(new SysUser())
                .eq(SysUser::getUsername, sysUsers.getUsername());
        boolean updateById = iSysUserService.update(sysUsers, wrapper);
        return updateById;
    }

    @GetMapping("/getById")
    public Object getById(Integer id) {
        // 支持（id非加密）
        SysUser sysUser = iSysUserService.getById(id);
        return sysUser;
    }

    @GetMapping("/getByLambdaQueryWrapper")
    public Object getByLambdaQueryWrapper(String username, String idNumber) {
        // 查询一个字段，获取不到实体类型
//        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
//                .select(SysUser::getUsername);
//        List<String> list = iSysUserService.listObjs(wrapper, String::valueOf);

        // 查询多个字段，获取不到实体类型
//        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
//                .select(SysUser::getUsername, SysUser::getIdNumber);
//        List<SysUser> list = iSysUserService.list(wrapper);

        // 查询所有字段，获取不到实体类型
//        LambdaQueryWrapper<SysUser> wrapper = Wrappers.<SysUser>lambdaQuery()
//                .eq(SysUser::getUsername, username);
//        List<SysUser> list = iSysUserService.list(wrapper);

        // 查询单个字段，能获取到实体类型，参数能加密，返回不能解密
//        LambdaQueryWrapper wrapper = Wrappers.lambdaQuery(new SysUser())
//                .select(SysUser::getIdNumber)
//                .eq(SysUser::getUsername, username);
//        List<String> list = iSysUserService.listObjs(wrapper, String::valueOf);



        // 查询单个字段，能获取到实体类型，参数能加密，返回能解密
//        LambdaQueryWrapper wrapper = Wrappers.lambdaQuery(new SysUser())
//                .select(SysUser::getIdNumber)
//                .eq(SysUser::getUsername, username);
//        List<SysUser> list = iSysUserService.list(wrapper);

        // 查询所有字段，能获取到实体类型（推荐使用该方式构建查询条件）
//        LambdaQueryWrapper wrapper = Wrappers.lambdaQuery(new SysUser())
//                .eq(SysUser::getUsername, username);
//        List<SysUser> list = iSysUserService.list(wrapper);

        // 能获取到实体类型
//        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>(new SysUser())
//                .eq(SysUser::getUsername, username);
//        List<SysUser> list = iSysUserService.list(wrapper);

        // 能获取到实体类型
//        LambdaQueryWrapper wrapper = new LambdaQueryWrapper<>(new SysUser())
//                .eq(SysUser::getUsername, username)
//                .eq(SysUser::getIdNumber, idNumber)
//                .orderByAsc(SysUser::getAccount);
//        List<SysUser> list = iSysUserService.list(wrapper);

        // 能获取到实体类型
        LambdaQueryWrapper wrapper = new LambdaQueryWrapper<>(new SysUser())
                .in(SysUser::getIdNumber, Arrays.asList("123456", "123456789"))
                .eq(SysUser::getUsername, username)
                .orderByAsc(SysUser::getAccount);
        List<SysUser> list = iSysUserService.list(wrapper);
        return list;
    }

    @GetMapping("/getByQueryWrapper")
    public Object getByQueryWrapper(String username, String idNumber) {
        // 支持
        QueryWrapper wrapper = Wrappers.query(new SysUser())
                .eq("username", username)
                .eq("id_number", idNumber)
                .orderByAsc("account")
                .last("limit 1");
        List<SysUser> sysUsers = iSysUserService.list(wrapper);
        return sysUsers;
    }

    @GetMapping("/listByUsername")
    public Object listByUsername(String username, String idNumber) {
        // 支持
        List<SysUser> sysUsers = iSysUserService.listByUsername(username, idNumber);
        return sysUsers;
    }

    @GetMapping("/list")
    public Object list() {
        List<SysUser> sysUsers = iSysUserService.list();
        return sysUsers;
    }

    @GetMapping("/getByUsername2")
    public Object getByUsername2(String username) {
        SysUser sysUser = iSysUserService.getByUsername2(username);
        return sysUser;
    }

    @GetMapping("/getByUsername3")
    public Object getByUsername3(String username) {
        SysUser sysUser = iSysUserService.getByUsername3(username);
        return sysUser;
    }

    @GetMapping("/getByUsernameAndIdNumber")
    public Object getByUsernameAndIdNumber(String username, String idNumber) {
        SysUser sysUser = iSysUserService.getByUsernameAndIdNumber(username, idNumber);
        return sysUser;
    }

    @GetMapping("/getByMap")
    public Object getByMap(String username) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        List<SysUser> sysUsers = iSysUserService.listByMap(map);
        return sysUsers;
    }

    @PostMapping("/queryByVo")
    public Object queryByVo(@RequestBody QueryUserVO vo) {
        // 支持
        SysUser sysUser = iSysUserService.queryByVo(vo);
        return sysUser;
    }

    @PostMapping("/listByUsernames")
    public Object listByUsernames(@RequestBody List<String> usernames) {
        List<SysUser> sysUsers = iSysUserService.listByUsernames(usernames);
        return sysUsers;
    }

    @PostMapping("/listByIdNumbers")
    public Object listByIdNumbers(@RequestBody Set<String> idNumbers) {
        List<SysUser> sysUsers = iSysUserService.listByIdNumbers(idNumbers);
        return sysUsers;
    }

    @GetMapping("/selectByMap")
    public Object selectByMap(String username, String idNumber) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("idNumber", idNumber);
        List<SysUser> sysUsers = iSysUserService.selectByMap(map);
        return sysUsers;
    }

    @GetMapping("/getUser")
    public Object getUser(String username, String idNumber) {
        List<UserVO> sysUsers = iSysUserService.getUser(username, idNumber);
        return sysUsers;
    }

    @GetMapping("/listMap")
    public Object listMap(String username, String idNumber) {
        List<Map<String, Object>> sysUsers = iSysUserService.listMap(username, idNumber);
        return sysUsers;
    }

    @GetMapping("/mybatisPlusPage")
    public Object page(Integer pageNum, Integer pageSize, String account) {
        IPage<SysUser> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.<SysUser>lambdaQuery().eq(SysUser::getAccount, account);
        IPage<SysUser> result = iSysUserService.page(page, wrapper);
        return result;
    }

    @GetMapping("/mybatisPlusPageBySql")
    public Object pageBySql(Integer pageNum, Integer pageSize, String username) {
        IPage<SysUser> page = new Page<>(pageNum, pageSize);
        IPage<SysUser> result = iSysUserService.pageBySql(page, username);
        return result;
    }

    @GetMapping("/pagehelper")
    public Object pagehelper(Integer pageNum, Integer pageSize, String username) {
        PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username);
        List<SysUser> list = iSysUserService.list(wrapper);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @GetMapping("/pagehelperBySql")
    public Object pagehelperBySql(Integer pageNum, Integer pageSize, String username) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> list = iSysUserService.pagehelperBySql(username);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}

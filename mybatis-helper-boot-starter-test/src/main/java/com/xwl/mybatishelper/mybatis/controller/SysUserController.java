package com.xwl.mybatishelper.mybatis.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xwl.mybatishelper.mybatis.entity.SysUser;
import com.xwl.mybatishelper.mybatis.mapper.SysUserMapper;
import com.xwl.mybatishelper.mybatis.vo.OutVO;
import com.xwl.mybatishelper.mybatis.vo.ParamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xwl
 * @since 2022/12/19 10:07
 */
@RestController
@RequestMapping("/user")
public class SysUserController {
    @Autowired
    private SysUserMapper sysUserMapper;

    @PostMapping("/save")
    public Object save(@RequestBody SysUser user) {
        int res = sysUserMapper.save(user);
        return res;
    }

    @PostMapping("/update")
    public Object update(@RequestBody ParamVO vo) {
        int res = sysUserMapper.update(vo);
        return res;
    }

    @GetMapping("/list")
    public Object list() {
        List<SysUser> users = sysUserMapper.listUsers();
        return users;
    }

    @GetMapping("/page")
    public Object page(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> users = sysUserMapper.listUsers();
        PageInfo pageInfo = new PageInfo(users);
        return pageInfo;
    }

    @GetMapping("/getBy")
    public Object getBy(String idNumber) {
        SysUser user = sysUserMapper.getBy(idNumber);
        return user;
    }

    @PostMapping("/getVo")
    public Object getVo(@RequestBody ParamVO vo) {
        List<OutVO> res = sysUserMapper.getVo(vo);
        return res;
    }

    @GetMapping("/join")
    public Object join(Integer deptId) {
        List<OutVO> res = sysUserMapper.join(deptId);
        return res;
    }

    /**
     * 查询用户信息，返回map
     * 注意：查询如果返回的是map，得到的的结果不会解密，只有返回实体或者VO并用`@CryptoField`注解标识才会解密
     *
     * @return
     */
    @GetMapping("/getMap")
    public Object getMap() {
        List<Map<String, Object>> res = sysUserMapper.getMap();
        return res;
    }

    @PostMapping("/listByIdNumberSet")
    public Object listByIdNumberSet(@RequestBody Set<String> idNumbers) {
        List<SysUser> sysUsers = sysUserMapper.listByIdNumberSet(idNumbers);
        return sysUsers;
    }

    @PostMapping("/listByIdNumberList")
    public Object listByIdNumberList(@RequestBody List<String> idNumbers) {
        List<SysUser> sysUsers = sysUserMapper.listByIdNumberList(idNumbers);
        return sysUsers;
    }

    @PostMapping("/listByIdNumberListPageHelper")
    public Object listByIdNumberListPageHelper(@RequestBody List<String> idNumbers) {
        PageHelper.startPage(1, 10);
        List<SysUser> sysUsers = sysUserMapper.listByIdNumberList(idNumbers);
        return sysUsers;
    }

    @PostMapping("/notIn")
    public Object notIn(@RequestBody List<String> idNumbers) {
        List<SysUser> sysUsers = sysUserMapper.notIn(idNumbers);
        return sysUsers;
    }

    @PostMapping("/notInPageHelper")
    public Object notInPageHelper(@RequestBody List<String> idNumbers) {
        PageHelper.startPage(1, 10);
        List<SysUser> sysUsers = sysUserMapper.notIn(idNumbers);
        return sysUsers;
    }

    @PostMapping("/listByUsernames")
    public Object listByUsernames(@RequestBody List<String> usernames) {
        List<SysUser> sysUsers = sysUserMapper.listByUsernames(usernames);
        return sysUsers;
    }
}

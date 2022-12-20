package com.xwl.mybatishelper.test.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xwl.mybatishelper.test.entity.SysUser;
import com.xwl.mybatishelper.test.mapper.SysUserMapper;
import com.xwl.mybatishelper.test.vo.OutVO;
import com.xwl.mybatishelper.test.vo.ParamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xwl
 * @since 2022/12/19 10:07
 */
@RestController
@RequestMapping("/user")
public class SysUserController {
    @Autowired
    private SysUserMapper sysUserMapper;

    @GetMapping("/list")
    public Object list() {
        List<SysUser> users = sysUserMapper.list();
        return users;
    }

    @GetMapping("/page")
    public Object page(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> users = sysUserMapper.list();
        PageInfo pageInfo = new PageInfo(users);
        return pageInfo;
    }

    @GetMapping("/getBy")
    public Object getBy(String username, String idNumber) {
        List<SysUser> users = sysUserMapper.getBy(username, idNumber);
        return users;
    }

    @PostMapping("/getVo")
    public Object getVo(@RequestBody ParamVO vo) {
        List<OutVO> res = sysUserMapper.getVo(vo);
        return res;
    }

    @PostMapping("/save")
    public Object save(SysUser user) {
        int res = sysUserMapper.save(user);
        return res;
    }

    @PostMapping("/update")
    public Object update(@RequestBody ParamVO vo) {
        int res = sysUserMapper.update(vo);
        return res;
    }

    @GetMapping("/join")
    public Object join(Integer deptId) {
        List<OutVO> res = sysUserMapper.join(deptId);
        return res;
    }
}

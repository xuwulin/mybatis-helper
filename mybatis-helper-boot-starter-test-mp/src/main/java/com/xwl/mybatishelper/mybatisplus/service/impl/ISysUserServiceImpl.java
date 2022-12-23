package com.xwl.mybatishelper.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwl.mybatishelper.mybatisplus.entity.SysUser;
import com.xwl.mybatishelper.mybatisplus.service.ISysUserService;
import com.xwl.mybatishelper.mybatisplus.mapper.SysUserMapper;
import com.xwl.mybatishelper.mybatisplus.vo.QueryUserVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xwl
 * @since 2022/12/6 15:44
 */
@Service
public class ISysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Override
    public boolean insertBySql(SysUser sysUser) {
        int count = baseMapper.insertBySql(sysUser);
        return count > 0;
    }

    @Override
    public List<SysUser> listByUsername(String username, String idNumber) {
        LambdaQueryWrapper wrapper = Wrappers.lambdaQuery(new SysUser())
                .eq(SysUser::getUsername, username)
                .eq(SysUser::getIdNumber, idNumber);
        List<SysUser> sysUsers = baseMapper.selectList(wrapper);
        return sysUsers;
    }

    @Override
    public SysUser getByIdNumber(String idNumber) {
        return baseMapper.getByIdNumber(idNumber);
    }

    @Override
    public SysUser getByIdNumber2(String idNumber) {
        return baseMapper.getByIdNumber2(idNumber);
    }

    @Override
    public SysUser queryByVo(QueryUserVO vo) {
        return baseMapper.queryByVo(vo);
    }

    @Override
    public List<SysUser> listByIdNumberSet(Set<String> idNumbers) {
        return baseMapper.listByIdNumberSet(idNumbers);
    }

    @Override
    public List<SysUser> listByIdNumberList(List<String> idNumbers) {
        return baseMapper.listByIdNumberList(idNumbers);
    }

    @Override
    public List<SysUser> selectByMap(Map<String, Object> map) {
        return baseMapper.getByMap(map);
    }

    @Override
    public List<Map<String, Object>> listMap(String username, String idNumber) {
        return baseMapper.listMap(username, idNumber);
    }

    @Override
    public IPage<SysUser> pageBySql(IPage<SysUser> page, String idNumber) {
        return baseMapper.pageBySql(page, idNumber);
    }

    @Override
    public List<SysUser> pagehelperBySql(String idNumber) {
        return baseMapper.pagehelperBySql(idNumber);
    }

    @Override
    public List<SysUser> notIn(List<String> idNumbers) {
        return baseMapper.notIn(idNumbers);
    }

    @Override
    public List<SysUser> listByUsernames(List<String> usernames) {
        return baseMapper.listByUsernames(usernames);
    }

    @Override
    public List<SysUser> listByIdNumbersAndPassword(List<String> idNumbers, List<String> passwords) {
        return baseMapper.listByIdNumbersAndPassword(idNumbers, passwords);
    }

    @Override
    public List<SysUser> listByIdNumbersAndPassword2(List<String> idNumbers, String password) {
        return baseMapper.listByIdNumbersAndPassword2(idNumbers, password);
    }

    @Override
    public List<SysUser> listByIdNumbersAndPassword3(List<String> idNumbers, String account) {
        return baseMapper.listByIdNumbersAndPassword3(idNumbers, account);
    }

    @Override
    public List<SysUser> listByUsers(List<SysUser> users) {
        return baseMapper.listByUsers(users);
    }

    @Override
    public SysUser getByUsers(SysUser user) {
        return baseMapper.getByUsers(user);
    }
}

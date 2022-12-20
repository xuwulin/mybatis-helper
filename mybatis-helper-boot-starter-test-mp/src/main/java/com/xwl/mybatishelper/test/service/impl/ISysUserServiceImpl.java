package com.xwl.mybatishelper.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwl.mybatishelper.test.entity.SysUser;
import com.xwl.mybatishelper.test.service.ISysUserService;
import com.xwl.mybatishelper.test.mapper.SysUserMapper;
import com.xwl.mybatishelper.test.vo.QueryUserVO;
import com.xwl.mybatishelper.test.vo.UserVO;
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
    public SysUser getByUsername2(String username) {
        return baseMapper.getByUsername2(username);
    }

    @Override
    public SysUser getByUsername3(String username) {
        return baseMapper.getByUsername3(username);
    }

    @Override
    public SysUser getByUsernameAndIdNumber(String username, String idNumber) {
        return baseMapper.getByUsernameAndIdNumber(username, idNumber);
    }

    @Override
    public SysUser queryByVo(QueryUserVO vo) {
        return baseMapper.queryByVo(vo);
    }

    @Override
    public List<SysUser> listByUsernames(List<String> usernames) {
        return baseMapper.listByUsernames(usernames);
    }

    @Override
    public List<SysUser> listByIdNumbers(Set<String> idNumbers) {
        return baseMapper.listByIdNumbers(idNumbers);
    }

    @Override
    public List<SysUser> selectByMap(Map<String, Object> map) {
        return baseMapper.getByMap(map);
    }

    @Override
    public List<UserVO> getUser(String username, String idNumber) {
        return baseMapper.getUser(username, idNumber);
    }

    @Override
    public List<Map<String, Object>> listMap(String username, String idNumber) {
        return baseMapper.listMap(username, idNumber);
    }

    @Override
    public IPage<SysUser> pageBySql(IPage<SysUser> page, String username) {
        return baseMapper.pageBySql(page, username);
    }

    @Override
    public List<SysUser> pagehelperBySql(String username) {
        return baseMapper.pagehelperBySql(username);
    }
}

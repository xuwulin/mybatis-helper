package com.xwl.mybatishelper.mybatisplus.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xwl.mybatishelper.mybatisplus.entity.SysUser;
import com.xwl.mybatishelper.mybatisplus.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author xwl
 * @since 2022/12/27 10:17
 */
@Slf4j
@SpringBootTest
public class UpdateWrapperTest {
    @Resource
    private ISysUserService iSysUserService;

    @Test
    public void testLambdaUpdateWrapper() {
        String idNumber = "372522195710100019";
        SysUser sysUser = new SysUser();
        sysUser.setUsername("张三疯");
        LambdaUpdateWrapper wrapper = Wrappers.lambdaUpdate(new SysUser())
                .eq(SysUser::getIdNumber, idNumber);
        iSysUserService.update(sysUser, wrapper);
    }

    @Test
    public void testNewLambdaUpdateWrapper() {
        String idNumber = "372522195710100019";
        SysUser sysUser = new SysUser();
        sysUser.setUsername("张三疯");
        LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>(new SysUser())
                .eq(SysUser::getIdNumber, idNumber);
        iSysUserService.update(sysUser, wrapper);
    }

    @Test
    public void testUpdateWrapper() {
        String idNumber = "372522195710100019";
        SysUser sysUser = new SysUser();
        sysUser.setUsername("张三疯");
        UpdateWrapper<SysUser> wrapper = Wrappers.update(new SysUser())
                .eq("id_number", idNumber);
        iSysUserService.update(sysUser, wrapper);
    }

    @Test
    public void testNewUpdateWrapper() {
        String idNumber = "372522195710100019";
        SysUser sysUser = new SysUser();
        sysUser.setUsername("张三疯");
        UpdateWrapper<SysUser> wrapper = new UpdateWrapper<>(new SysUser())
                .eq("id_number", idNumber);
        iSysUserService.update(sysUser, wrapper);
    }
}

package com.xwl.mybatishelper.mybatisplus.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xwl.mybatishelper.mybatisplus.entity.SysUser;
import com.xwl.mybatishelper.mybatisplus.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author xwl
 * @since 2022/12/26 20:24
 */
@Slf4j
@SpringBootTest
public class QueryWrapperTest {
    @Resource
    private ISysUserService iSysUserService;

    @Test
    public void testLambdaQueryWrapper() {
        String idNumber = "372522195710100019";
        LambdaQueryWrapper wrapper = Wrappers.lambdaQuery(new SysUser())
                .eq(SysUser::getIdNumber, idNumber);
        SysUser sysUser = iSysUserService.getOne(wrapper);
        String pretty = JSON.toJSONString(sysUser, SerializerFeature.PrettyFormat);
        log.info("query result:\n{}", pretty);
    }

    @Test
    public void testLambdaQueryWrapperWithoutEntity() {
        String idNumber = "372522195710100019";
        LambdaQueryWrapper wrapper = Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getIdNumber, idNumber);
        SysUser sysUser = iSysUserService.getOne(wrapper);
        String pretty = JSON.toJSONString(sysUser, SerializerFeature.PrettyFormat);
        log.info("query result:\n{}", pretty);
    }

    @Test
    public void testNewLambdaQueryWrapper() {
        String idNumber = "372522195710100019";
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>(new SysUser())
                .eq(SysUser::getIdNumber, idNumber);
        SysUser sysUser = iSysUserService.getOne(wrapper);
        String pretty = JSON.toJSONString(sysUser, SerializerFeature.PrettyFormat);
        log.info("query result:\n{}", pretty);
    }

    @Test
    public void testNewLambdaQueryWrapperWithoutEntity() {
        String idNumber = "372522195710100019";
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getIdNumber, idNumber);
        SysUser sysUser = iSysUserService.getOne(wrapper);
        String pretty = JSON.toJSONString(sysUser, SerializerFeature.PrettyFormat);
        log.info("query result:\n{}", pretty);
    }

    @Test
    public void testQueryWrapper() {
        String idNumber = "372522195710100019";
        QueryWrapper<SysUser> wrapper = Wrappers.query(new SysUser())
                .eq("id_number", idNumber);
        SysUser sysUser = iSysUserService.getOne(wrapper);
        String pretty = JSON.toJSONString(sysUser, SerializerFeature.PrettyFormat);
        log.info("query result:\n{}", pretty);
    }

    @Test
    public void testQueryWrapperWithoutEntity() {
        String idNumber = "372522195710100019";
        QueryWrapper<SysUser> wrapper = Wrappers.<SysUser>query()
                .eq("id_number", idNumber);
        SysUser sysUser = iSysUserService.getOne(wrapper);
        String pretty = JSON.toJSONString(sysUser, SerializerFeature.PrettyFormat);
        log.info("query result:\n{}", pretty);
    }

    @Test
    public void testNewQueryWrapper() {
        String idNumber = "372522195710100019";
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>(new SysUser())
                .eq("id_number", idNumber);
        SysUser sysUser = iSysUserService.getOne(wrapper);
        String pretty = JSON.toJSONString(sysUser, SerializerFeature.PrettyFormat);
        log.info("query result:\n{}", pretty);
    }

    @Test
    public void testNewQueryWrapperWithoutEntity() {
        String idNumber = "372522195710100019";
        QueryWrapper wrapper = new QueryWrapper<>()
                .eq("id_number", idNumber);
        SysUser sysUser = iSysUserService.getOne(wrapper);
        String pretty = JSON.toJSONString(sysUser, SerializerFeature.PrettyFormat);
        log.info("query result:\n{}", pretty);
    }

    @Test
    public void testQueryWrapperMultipleConditions() {
        String username = "张三";
        String account = "15888888888";
        String idNumber = "372522195710100019";
        QueryWrapper wrapper = Wrappers.query(new SysUser())
                .eq("username", username)
                .eq("id_number", idNumber)
                .eq("account", account)
                .orderByAsc("account")
                .last("limit 1");
        SysUser sysUser = iSysUserService.getOne(wrapper);
        String pretty = JSON.toJSONString(sysUser, SerializerFeature.PrettyFormat);
        log.info("query result:\n{}", pretty);
    }

    @Test
    public void testLambdaQueryWrapperIn() {
        List<String> idNumbers = Arrays.asList("372522195710100019", "371521198411051559");
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery(new SysUser())
                .in(SysUser::getIdNumber, idNumbers);
        List<SysUser> sysUsers = iSysUserService.list(wrapper);
        String pretty = JSON.toJSONString(sysUsers, SerializerFeature.PrettyFormat);
        log.info("query result:\n{}", pretty);
    }

    @Test
    public void testLambdaQueryWrapperInNotEnc() {
        List<String> ids = Arrays.asList("1", "2", "3");
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery(new SysUser())
                .in(SysUser::getId, ids);
        List<SysUser> sysUsers = iSysUserService.list(wrapper);
        String pretty = JSON.toJSONString(sysUsers, SerializerFeature.PrettyFormat);
        log.info("query result:\n{}", pretty);
    }

    @Test
    public void testLambdaQueryWrapperInAndEq() {
        String account = "15888888888";
        List<String> idNumbers = Arrays.asList("372522195710100019", "371521198411051559");
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery(new SysUser())
                .in(SysUser::getIdNumber, idNumbers)
                .eq(SysUser::getAccount, account);
        List<SysUser> sysUsers = iSysUserService.list(wrapper);
        String pretty = JSON.toJSONString(sysUsers, SerializerFeature.PrettyFormat);
        log.info("query result:\n{}", pretty);
    }

    @Test
    public void testLambdaQueryWrapperPage() {
        IPage<SysUser> page = new Page<>(1, 2);
        List<String> idNumbers = Arrays.asList("372522195710100019", "371521198411051559");
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery(new SysUser())
                .in(SysUser::getIdNumber, idNumbers);
        IPage<SysUser> result = iSysUserService.page(page, wrapper);
        String pretty = JSON.toJSONString(result, SerializerFeature.PrettyFormat);
        log.info("query result:\n{}", pretty);
    }

    @Test
    public void testLambdaQueryWrapperPageHelper() {
        PageHelper.startPage(1, 2);
        List<String> idNumbers = Arrays.asList("372522195710100019", "371521198411051559");
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery(new SysUser())
                .in(SysUser::getIdNumber, idNumbers);
        List<SysUser> list = iSysUserService.list(wrapper);
        PageInfo pageInfo = new PageInfo(list);
        String pretty = JSON.toJSONString(pageInfo, SerializerFeature.PrettyFormat);
        log.info("query result:\n{}", pretty);
    }
}

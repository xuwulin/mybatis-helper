package com.xwl.mybatishelper.mybatisplus.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xwl.mybatishelper.mybatisplus.entity.SysUser;
import com.xwl.mybatishelper.mybatisplus.service.ISysUserService;
import com.xwl.mybatishelper.mybatisplus.vo.OutExtendsVO;
import com.xwl.mybatishelper.mybatisplus.vo.OutVO;
import com.xwl.mybatishelper.mybatisplus.vo.ParamExtendsVO;
import com.xwl.mybatishelper.mybatisplus.vo.QueryUserVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xwl
 * @since 2022/12/27 10:27
 */
@Slf4j
@SpringBootTest
public class QueryVoTest {
    @Resource
    private ISysUserService iSysUserService;

    @Test
    public void testQueryByVo() {
        QueryUserVO vo = new QueryUserVO();
        vo.setIdNumber("372522195710100019");
        SysUser sysUser = iSysUserService.queryByVo(vo);
        String pretty = JSON.toJSONString(sysUser, SerializerFeature.PrettyFormat);
        log.info("query result:\n{}", pretty);
    }

    @Test
    public void testOutVo() {
        QueryUserVO vo = new QueryUserVO();
        vo.setIdNumber("372522195710100019");
        OutVO outVO = iSysUserService.outVo(vo);
        String pretty = JSON.toJSONString(outVO, SerializerFeature.PrettyFormat);
        log.info("query result:\n{}", pretty);
    }

    @Test
    public void testExtendsVo() {
        ParamExtendsVO vo = new ParamExtendsVO();
        vo.setIdNumber("372522195710100019");
        vo.setAccount("15888888888");
        vo.setUsername("张三");
        vo.setPassword("123456");
        OutExtendsVO outVO = iSysUserService.testExtendsVo(vo);
        String pretty = JSON.toJSONString(outVO, SerializerFeature.PrettyFormat);
        log.info("query result:\n{}", pretty);
    }

    @Test
    public void testPageHelper() {
        PageHelper.startPage(1, 10);
        QueryUserVO vo = new QueryUserVO();
        vo.setIdNumber("372522195710100019");
        List<SysUser> list = iSysUserService.testPageHelper(vo);
        PageInfo pageInfo = new PageInfo(list);
        String pretty = JSON.toJSONString(pageInfo, SerializerFeature.PrettyFormat);
        log.info("query result:\n{}", pretty);
    }
}

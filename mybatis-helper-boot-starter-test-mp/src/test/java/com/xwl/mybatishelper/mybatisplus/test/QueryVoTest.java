package com.xwl.mybatishelper.mybatisplus.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xwl.mybatishelper.mybatisplus.entity.SysUser;
import com.xwl.mybatishelper.mybatisplus.service.ISysUserService;
import com.xwl.mybatishelper.mybatisplus.vo.OutVO;
import com.xwl.mybatishelper.mybatisplus.vo.QueryUserVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

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
}

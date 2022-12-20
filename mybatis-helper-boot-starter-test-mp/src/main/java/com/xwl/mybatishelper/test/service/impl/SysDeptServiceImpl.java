package com.xwl.mybatishelper.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwl.mybatishelper.test.service.ISysDeptService;
import com.xwl.mybatishelper.test.entity.SysDept;
import com.xwl.mybatishelper.test.mapper.SysDeptMapper;
import org.springframework.stereotype.Service;

/**
 * @author xwl
 * @since 2022/12/12 10:17
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {
}

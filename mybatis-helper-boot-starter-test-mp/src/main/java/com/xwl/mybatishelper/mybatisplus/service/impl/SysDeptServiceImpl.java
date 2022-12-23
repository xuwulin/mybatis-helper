package com.xwl.mybatishelper.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwl.mybatishelper.mybatisplus.service.ISysDeptService;
import com.xwl.mybatishelper.mybatisplus.entity.SysDept;
import com.xwl.mybatishelper.mybatisplus.mapper.SysDeptMapper;
import org.springframework.stereotype.Service;

/**
 * @author xwl
 * @since 2022/12/12 10:17
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {
}

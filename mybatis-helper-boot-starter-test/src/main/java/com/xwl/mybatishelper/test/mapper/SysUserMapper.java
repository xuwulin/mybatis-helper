package com.xwl.mybatishelper.test.mapper;

import com.xwl.mybatishelper.test.entity.SysUser;
import com.xwl.mybatishelper.test.vo.OutVO;
import com.xwl.mybatishelper.test.vo.ParamVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xwl
 * @since 2022/12/6 15:42
 */
@Mapper
public interface SysUserMapper {

    /**
     * 获取用户列表
     *
     * @return
     */
    List<SysUser> list();

    /**
     * 根据用户名和身份证号码查询
     *
     * @param username 用户名
     * @param idNumber 身份证号码
     * @return
     */
    List<SysUser> getBy(@Param("enc_username") String username, @Param("enc_idNumber") String idNumber);

    /**
     * 根据参数查询
     *
     * @param vo 参数
     * @return
     */
    List<OutVO> getVo(@Param("vo") ParamVO vo);

    /**
     * 保存用户
     *
     * @param user 用户信息
     * @return
     */
    int save(@Param("user") SysUser user);

    /**
     * 更新用户
     *
     * @param vo 参数
     * @return
     */
    int update(@Param("vo") ParamVO vo);

    /**
     * 连表查询
     *
     * @param deptId 部门id
     * @return
     */
    List<OutVO> join(@Param("deptId") Integer deptId);
}

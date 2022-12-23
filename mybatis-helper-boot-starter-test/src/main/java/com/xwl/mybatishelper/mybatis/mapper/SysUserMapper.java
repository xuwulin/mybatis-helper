package com.xwl.mybatishelper.mybatis.mapper;

import com.xwl.mybatishelper.mybatis.entity.SysUser;
import com.xwl.mybatishelper.mybatis.vo.OutVO;
import com.xwl.mybatishelper.mybatis.vo.ParamVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xwl
 * @since 2022/12/6 15:42
 */
@Mapper
public interface SysUserMapper {

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
     * 获取用户列表
     *
     * @return
     */
    List<SysUser> listUsers();

    /**
     * 根据用户份证号码查询
     *
     * @param idNumber 身份证号码
     * @return
     */
    SysUser getBy(@Param("enc_idNumber") String idNumber);

    /**
     * 根据参数查询
     *
     * @param vo 参数
     * @return
     */
    List<OutVO> getVo(@Param("vo") ParamVO vo);

    /**
     * 联表查询
     *
     * @param deptId 部门id
     * @return
     */
    List<OutVO> join(@Param("deptId") Integer deptId);

    /**
     * 查询用户返回map
     *
     * @return
     */
    List<Map<String, Object>> getMap();

    /**
     * 根据用户身份证号码列表查询
     *
     * @param idNumbers 用户身份证号码
     * @return
     */
    List<SysUser> listByIdNumberSet(@Param("enc_idNumbers") Set<String> idNumbers);

    /**
     * 根据用户身份证号码列表查询
     *
     * @param idNumbers 用户身份证号码
     * @return
     */
    List<SysUser> listByIdNumberList(@Param("enc_idNumbers") List<String> idNumbers);

    /**
     * 根据用户身份证号码列表查询
     *
     * @param idNumbers 用户身份证号码
     * @return
     */
    List<SysUser> notIn(@Param("enc_idNumbers") List<String> idNumbers);

    /**
     * 根据用户名列表查询
     *
     * @param usernames 用户名
     * @return
     */
    List<SysUser> listByUsernames(@Param("usernames") List<String> usernames);
}

package com.xwl.mybatishelper.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xwl.mybatishelper.mybatisplus.entity.SysUser;
import com.xwl.mybatishelper.mybatisplus.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xwl
 * @since 2022/12/6 15:42
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 新增用户，通过sql方式插入
     *
     * @param sysUser 用户信息
     * @return
     */
    int insertBySql(@Param("sysUser") SysUser sysUser);

    /**
     * 根据用户身份证号码查询
     *
     * @param idNumber 用户身份证号码
     * @return
     */
    SysUser getByIdNumber(@Param("enc_idNumber") String idNumber);

    /**
     * 根据用户身份证号码查询
     *
     * @param idNumber 用户身份证号码
     * @return
     */
    @Select("select * from sys_user where id_number = #{enc_idNumber}")
    SysUser getByIdNumber2(@Param("enc_idNumber") String idNumber);

    /**
     * 查询用户
     *
     * @param vo 查询参数
     * @return
     */
    SysUser queryByVo(@Param("vo") QueryUserVO vo);

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
     * 根据map查询
     *
     * @param map 查询参数
     * @return
     */
    List<SysUser> getByMap(@Param("map") Map<String, Object> map);

    /**
     * 获取用户，返回map
     *
     * @param username 用户名
     * @param idNumber 身份证号码
     * @return
     */
    List<Map<String, Object>> listMap(@Param("username") String username, @Param("enc_idNumber") String idNumber);

    /**
     * 分页查询
     *
     * @param page     分页对象
     * @param idNumber 用户身份证号码
     * @return
     */
    IPage<SysUser> pageBySql(IPage<SysUser> page, @Param("enc_idNumber") String idNumber);

    /**
     * 分页查询
     *
     * @param idNumber 用户身份证号码
     * @return
     */
    List<SysUser> pagehelperBySql(@Param("enc_idNumber") String idNumber);

    /**
     * 根据用户名列表查询
     *
     * @param usernames 用户名
     * @return
     */
    List<SysUser> listByUsernames(@Param("usernames") List<String> usernames);

    /**
     * 根据用户身份证号码和密码查询
     *
     * @param idNumbers 身份证号码集合
     * @param passwords 密码集合
     * @return
     */
    List<SysUser> listByIdNumbersAndPassword(@Param("enc_idNumbers") List<String> idNumbers,
                                             @Param("enc_passwords") List<String> passwords);

    /**
     * 根据用户身份证号码和密码查询
     *
     * @param idNumbers 身份证号码集合
     * @param password  密码
     * @return
     */
    List<SysUser> listByIdNumbersAndPassword2(@Param("enc_idNumbers") List<String> idNumbers,
                                              @Param("enc_password") String password);

    /**
     * 根据用户身份证号码和账号查询
     *
     * @param idNumbers 身份证号码集合
     * @param account   账号
     * @return
     */
    List<SysUser> listByIdNumbersAndPassword3(@Param("enc_idNumbers") List<String> idNumbers,
                                              @Param("account") String account);

    /**
     * 根据users查询
     *
     * @param users 用户列表
     * @return
     */
    List<SysUser> listByUsers(@Param("users") List<SysUser> users);

    /**
     * 根据user查询
     *
     * @param user 用户
     * @return
     */
    SysUser getByUsers(@Param("user") SysUser user);

    /**
     * 根据身份证号码集合查询
     *
     * @param vo
     * @return
     */
    List<SysUser> getByIdNumberVo(@Param("enc_vo") IdNumberVO vo);

    /**
     * 查询结果为vo
     *
     * @param vo 参数
     * @return
     */
    OutVO outVo(@Param("vo") QueryUserVO vo);

    /**
     * vo有继承情况
     *
     * @param vo
     * @return
     */
    OutExtendsVO testExtendsVo(@Param("vo") ParamExtendsVO vo);
}

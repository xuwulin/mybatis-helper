package com.xwl.mybatishelper.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xwl.mybatishelper.test.entity.SysUser;
import com.xwl.mybatishelper.test.vo.UserVO;
import com.xwl.mybatishelper.test.vo.QueryUserVO;
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
     * 根据用户名查询
     *
     * @param username 用户名
     * @return
     */
    SysUser getByUsername2(@Param("enc_username") String username);

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return
     */
    @Select("select * from sys_user where username = #{username}")
    SysUser getByUsername3(@Param("username") String username);

    /**
     * 根据用户名和身份证号码查询
     *
     * @param username 用户名
     * @param idNumber 用户身份证号码
     * @return
     */
    SysUser getByUsernameAndIdNumber(@Param("username") String username, @Param("idNumber") String idNumber);

    /**
     * 查询用户
     *
     * @param vo 查询参数
     * @return
     */
    SysUser queryByVo(@Param("vo") QueryUserVO vo);

    /**
     * 根据用户名列表查询
     *
     * @param usernames 用户名列表
     * @return
     */
    List<SysUser> listByUsernames(@Param("enc_usernames") List<String> usernames);

    /**
     * 根据用户身份证号码列表查询
     *
     * @param idNumbers 用户身份证号码
     * @return
     */
    List<SysUser> listByIdNumbers(@Param("enc_idNumbers") Set<String> idNumbers);

    /**
     * 根据map查询
     *
     * @param map 查询参数
     * @return
     */
    List<SysUser> getByMap(@Param("map") Map<String, Object> map);

    /**
     * 获取用户
     *
     * @param username 用户名
     * @param idNumber 身份证号码
     * @return
     */
    List<UserVO> getUser(@Param("enc_username") String username, @Param("enc_idNumber") String idNumber);

    /**
     * 获取用户，返回map
     *
     * @param username 用户名
     * @param idNumber 身份证号码
     * @return
     */
    List<Map<String, Object>> listMap(@Param("enc_username") String username, @Param("enc_idNumber") String idNumber);

    /**
     * 分页查询
     *
     * @param page     分页对象
     * @param username 用户名
     * @return
     */
    IPage<SysUser> pageBySql(IPage<SysUser> page, @Param("enc_username") String username);

    /**
     * 分页查询
     * @param username 用户名
     * @return
     */
    List<SysUser> pagehelperBySql(@Param("enc_username") String username);
}

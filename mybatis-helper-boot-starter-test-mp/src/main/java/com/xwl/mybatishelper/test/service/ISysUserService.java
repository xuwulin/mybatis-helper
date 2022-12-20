package com.xwl.mybatishelper.test.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xwl.mybatishelper.test.entity.SysUser;
import com.xwl.mybatishelper.test.vo.QueryUserVO;
import com.xwl.mybatishelper.test.vo.UserVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xwl
 * @since 2022/12/6 15:43
 */
public interface ISysUserService extends IService<SysUser> {
    /**
     * 新增用户，通过sql方式插入
     *
     * @param sysUser 用户信息
     * @return
     */
    boolean insertBySql(SysUser sysUser);

    /**
     * 根据用户名和身份证号码查询
     *
     * @param username 用户名
     * @param idNumber 用户身份证号码
     * @return
     */
    List<SysUser> listByUsername(String username, String idNumber);

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return
     */
    SysUser getByUsername2(String username);

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return
     */
    SysUser getByUsername3(String username);

    /**
     * 根据用户名和身份证号码查询
     *
     * @param username 用户名
     * @param idNumber 用户身份证号码
     * @return
     */
    SysUser getByUsernameAndIdNumber(String username, String idNumber);

    /**
     * 查询用户
     *
     * @param vo 查询参数
     * @return
     */
    SysUser queryByVo(QueryUserVO vo);

    /**
     * 根据用户名列表查询
     *
     * @param usernames 用户名列表
     * @return
     */
    List<SysUser> listByUsernames(List<String> usernames);

    /**
     * 根据用户身份证号码列表查询
     *
     * @param idNumbers 用户身份证号码
     * @return
     */
    List<SysUser> listByIdNumbers(Set<String> idNumbers);

    /**
     * 根据map查询
     *
     * @param map 查询参数
     * @return
     */
    List<SysUser> selectByMap(Map<String, Object> map);

    /**
     * 获取用户，返回VO
     *
     * @param username 用户名
     * @param idNumber 身份证号码
     * @return
     */
    List<UserVO> getUser(String username, String idNumber);

    /**
     * 获取用户，返回map
     *
     * @param username 用户名
     * @param idNumber 身份证号码
     * @return
     */
    List<Map<String, Object>> listMap(String username, String idNumber);

    /**
     * 分页查询
     *
     * @param page     分页对象
     * @param username 用户名
     * @return
     */
    IPage<SysUser> pageBySql(IPage<SysUser> page, String username);

    /**
     * 分页查询
     *
     * @param username 用户名
     * @return
     */
    List<SysUser> pagehelperBySql(String username);
}

package com.xwl.mybatishelper.mybatisplus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xwl.mybatishelper.mybatisplus.entity.SysUser;
import com.xwl.mybatishelper.mybatisplus.vo.QueryUserVO;

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
     * 根据用户份证号码查询
     *
     * @param idNumber 用户身份证号码
     * @return
     */
    SysUser getByIdNumber(String idNumber);

    /**
     * 根据用户份证号码查询
     *
     * @param idNumber 用户身份证号码
     * @return
     */
    SysUser getByIdNumber2(String idNumber);

    /**
     * 查询用户
     *
     * @param vo 查询参数
     * @return
     */
    SysUser queryByVo(QueryUserVO vo);

    /**
     * 根据用户身份证号码列表查询
     *
     * @param idNumbers 用户身份证号码
     * @return
     */
    List<SysUser> listByIdNumberSet(Set<String> idNumbers);

    /**
     * 根据用户身份证号码列表查询
     *
     * @param idNumbers 用户身份证号码
     * @return
     */
    List<SysUser> listByIdNumberList(List<String> idNumbers);

    /**
     * 根据map查询
     *
     * @param map 查询参数
     * @return
     */
    List<SysUser> selectByMap(Map<String, Object> map);

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
     * @param idNumber 用户身份证号码
     * @return
     */
    IPage<SysUser> pageBySql(IPage<SysUser> page, String idNumber);

    /**
     * 分页查询
     *
     * @param idNumber 用户身份证号码
     * @return
     */
    List<SysUser> pagehelperBySql(String idNumber);

    /**
     * 根据用户身份证号码列表查询
     *
     * @param idNumbers 用户身份证号码列表
     * @return
     */
    List<SysUser> notIn(List<String> idNumbers);

    /**
     * 根据用户名列表查询
     *
     * @param usernames 用户名列表
     * @return
     */
    List<SysUser> listByUsernames(List<String> usernames);

    /**
     * 根据用户身份证号码和密码查询
     *
     * @param idNumbers 身份证号码集合
     * @param passwords 密码集合
     * @return
     */
    List<SysUser> listByIdNumbersAndPassword(List<String> idNumbers, List<String> passwords);

    /**
     * 根据用户身份证号码和密码查询
     *
     * @param idNumbers 身份证号码集合
     * @param password  密码
     * @return
     */
    List<SysUser> listByIdNumbersAndPassword2(List<String> idNumbers, String password);

    /**
     * 根据用户身份证号码和账号查询
     *
     * @param idNumbers 身份证号码集合
     * @param account   账号
     * @return
     */
    List<SysUser> listByIdNumbersAndPassword3(List<String> idNumbers, String account);

    /**
     * 根据users查询
     *
     * @param users 用户列表
     * @return
     */
    List<SysUser> listByUsers(List<SysUser> users);

    /**
     * 根据user查询
     *
     * @param user 用户
     * @return
     */
    SysUser getByUsers(SysUser user);
}

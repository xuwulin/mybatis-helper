package com.xwl.mybatishelper.mybatisplus.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xwl.mybatishelper.mybatisplus.entity.SysUser;
import com.xwl.mybatishelper.mybatisplus.service.ISysUserService;
import com.xwl.mybatishelper.mybatisplus.vo.IdNumberVO;
import com.xwl.mybatishelper.mybatisplus.vo.QueryUserVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author xwl
 * @since 2022/12/6 15:44
 */
@RestController
@RequestMapping("/user")
public class SysUserController {
    @Resource
    private ISysUserService iSysUserService;

    @PostMapping("/insert")
    public Object insert(@RequestBody SysUser sysUser) {
        boolean save = iSysUserService.save(sysUser);
        return save;
    }

    @PostMapping("/saveBatch")
    public Object saveOrUpdate(@RequestBody List<SysUser> sysUsers) {
        boolean save = iSysUserService.saveBatch(sysUsers);
        return save;
    }

    @PostMapping("/saveOrUpdate")
    public Object saveOrUpdate(@RequestBody SysUser sysUser) {
        boolean save = iSysUserService.saveOrUpdate(sysUser);
        return save;
    }

    @PostMapping("/insertBySql")
    public Object insertBySql(@RequestBody SysUser sysUser) {
        boolean insertBySql = iSysUserService.insertBySql(sysUser);
        return insertBySql;
    }

    @PostMapping("/updateById")
    public Object updateById(@RequestBody SysUser sysUser) {
        boolean updateById = iSysUserService.updateById(sysUser);
        return updateById;
    }

    @PostMapping("/updateBatchById")
    public Object updateById(@RequestBody List<SysUser> sysUsers) {
        boolean updateById = iSysUserService.updateBatchById(sysUsers);
        return updateById;
    }

    /**
     * LambdaUpdateWrapper wrapper = Wrappers.lambdaUpdate(new SysUser()).eq(SysUser::getIdNumber, "");
     *
     * @param sysUser
     * @return
     */
    @PostMapping("/updateByLambdaUpdateWrapper")
    public Object updateByLambdaUpdateWrapper(@RequestBody SysUser sysUser) {
        LambdaUpdateWrapper wrapper = Wrappers.lambdaUpdate(new SysUser())
                .eq(SysUser::getIdNumber, sysUser.getIdNumber());
        boolean update = iSysUserService.update(sysUser, wrapper);
        return update;
    }

    /**
     * LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>(new SysUser()).eq(SysUser::getIdNumber, "");
     *
     * @param sysUser
     * @return
     */
    @PostMapping("/updateByNewLambdaUpdateWrapper")
    public Object updateByNewLambdaUpdateWrapper(@RequestBody SysUser sysUser) {
        LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>(new SysUser())
                .eq(SysUser::getIdNumber, sysUser.getIdNumber());
        boolean update = iSysUserService.update(sysUser, wrapper);
        return update;
    }

    /**
     * UpdateWrapper<SysUser> wrapper = Wrappers.update(new SysUser()).eq("id_number", "");
     *
     * @param sysUser
     * @return
     */
    @PostMapping("/updateByUpdateWrapper")
    public Object updateByUpdateWrapper(@RequestBody SysUser sysUser) {
        UpdateWrapper<SysUser> wrapper = Wrappers.update(new SysUser())
                .eq("id_number", sysUser.getIdNumber());
        boolean update = iSysUserService.update(sysUser, wrapper);
        return update;
    }

    /**
     * UpdateWrapper<SysUser> wrapper = new UpdateWrapper<>(new SysUser()).eq("id_number", "");
     *
     * @param sysUser
     * @return
     */
    @PostMapping("/updateByNewUpdateWrapper")
    public Object updateByNewUpdateWrapper(@RequestBody SysUser sysUser) {
        UpdateWrapper<SysUser> wrapper = new UpdateWrapper<>(new SysUser())
                .eq("id_number", sysUser.getIdNumber());
        boolean update = iSysUserService.update(sysUser, wrapper);
        return update;
    }

    @GetMapping("/getByLambdaQueryWrapper")
    public Object getByLambdaQueryWrapper(String idNumber) {
        LambdaQueryWrapper wrapper = Wrappers.lambdaQuery(new SysUser())
                .eq(SysUser::getIdNumber, idNumber);
        return iSysUserService.getOne(wrapper);
    }

    @GetMapping("/getByNewLambdaQueryWrapper")
    public Object getByNewLambdaQueryWrapper(String idNumber) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>(new SysUser())
                .eq(SysUser::getIdNumber, idNumber);
        return iSysUserService.getOne(wrapper);
    }

    @GetMapping("/getByQueryWrapper")
    public Object getByQueryWrapper(String idNumber) {
        QueryWrapper<SysUser> wrapper = Wrappers.query(new SysUser())
                .eq("id_number", idNumber);
        return iSysUserService.getOne(wrapper);
    }

    @GetMapping("/getByNewQueryWrapper")
    public Object getByNewQueryWrapper(String idNumber) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>(new SysUser())
                .eq("id_number", idNumber);
        return iSysUserService.getOne(wrapper);
    }

    @GetMapping("/getByQueryWrapperMultipleConditions")
    public Object getByQueryWrapperMultipleConditions(String account, String username, String idNumber) {
        QueryWrapper wrapper = Wrappers.query(new SysUser())
                .eq("username", username)
                .eq("id_number", idNumber)
                .eq("account", account)
                .orderByAsc("account")
                .last("limit 1");
        List<SysUser> sysUsers = iSysUserService.list(wrapper);
        return sysUsers;
    }

    @GetMapping("/getById")
    public Object getById(Integer id) {
        SysUser sysUser = iSysUserService.getById(id);
        return sysUser;
    }

    @GetMapping("/listBy")
    public Object listBy(String username, String idNumber) {
        List<SysUser> sysUsers = iSysUserService.listBy(username, idNumber);
        return sysUsers;
    }

    @GetMapping("/list")
    public Object list() {
        List<SysUser> sysUsers = iSysUserService.list();
        return sysUsers;
    }

    @GetMapping("/getByIdNumber")
    public Object getByIdNumber(String idNumber) {
        SysUser sysUser = iSysUserService.getByIdNumber(idNumber);
        return sysUser;
    }

    @GetMapping("/getByIdNumber2")
    public Object getByIdNumber2(String username) {
        SysUser sysUser = iSysUserService.getByIdNumber2(username);
        return sysUser;
    }

    /**
     * 查询参数为map类型，不会加密
     *
     * @param password 密码
     * @return
     */
    @GetMapping("/getByMap")
    public Object getByMap(String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("password", password);
        List<SysUser> sysUsers = iSysUserService.listByMap(map);
        return sysUsers;
    }

    @PostMapping("/queryByVo")
    public Object queryByVo(@RequestBody QueryUserVO vo) {
        SysUser sysUser = iSysUserService.queryByVo(vo);
        return sysUser;
    }

    /**
     * id_number IN ()
     * 支持
     *
     * @param idNumbers 身份证号码
     * @return
     */
    @PostMapping("/listByIdNumbers")
    public Object listByIdNumbers(@RequestBody Set<String> idNumbers) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery(new SysUser())
                .in(SysUser::getIdNumber, idNumbers);
        return iSysUserService.list(wrapper);
    }

    /**
     * id_number IN () AND account = ''
     * 支持
     *
     * @param idNumbers 身份证号码
     * @return
     */
    @PostMapping("/listByIdNumbersAndAccount")
    public Object listByIdNumbersAndAccount(@RequestBody Set<String> idNumbers) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery(new SysUser())
                .in(SysUser::getIdNumber, idNumbers)
                .eq(SysUser::getAccount, "15888888888");
        return iSysUserService.list(wrapper);
    }

    /**
     * id_number IN ()
     * 支持
     *
     * @param idNumbers 身份证号码
     * @return
     */
    @PostMapping("/listByIdNumberSet")
    public Object listByIdNumberSet(@RequestBody Set<String> idNumbers) {
        List<SysUser> sysUsers = iSysUserService.listByIdNumberSet(idNumbers);
        return sysUsers;
    }

    /**
     * id_number IN ()
     * 支持
     *
     * @param idNumbers 身份证号码
     * @return
     */
    @PostMapping("/listByIdNumberList")
    public Object listByIdNumberList(@RequestBody List<String> idNumbers) {
        List<SysUser> sysUsers = iSysUserService.listByIdNumberList(idNumbers);
        return sysUsers;
    }

    /**
     * id_number NOT IN ()
     * 支持
     *
     * @param idNumbers 身份证号码
     * @return
     */
    @PostMapping("/notIn")
    public Object notIn(@RequestBody List<String> idNumbers) {
        List<SysUser> sysUsers = iSysUserService.notIn(idNumbers);
        return sysUsers;
    }

    /**
     * 多个IN条件加密
     * 支持
     *
     * @return
     */
    @PostMapping("/listByIdNumbersAndPassword")
    public Object listByIdNumbersAndPassword() {
        List<String> idNumbers = Arrays.asList("372522195710100019", "371521198411051559");
        List<String> passwords = Arrays.asList("123456", "1234567");
        List<SysUser> sysUsers = iSysUserService.listByIdNumbersAndPassword(idNumbers, passwords);
        return sysUsers;
    }

    /**
     * 一个IN条件加密，一共=条件加密
     * 支持
     *
     * @return
     */
    @PostMapping("/listByIdNumbersAndPassword2")
    public Object listByIdNumbersAndPassword2() {
        List<String> idNumbers = Arrays.asList("372522195710100019", "371521198411051559");
        String password = "123456";
        List<SysUser> sysUsers = iSysUserService.listByIdNumbersAndPassword2(idNumbers, password);
        return sysUsers;
    }

    /**
     * 一个IN条件加密，一共=条件不加密
     * 支持
     *
     * @return
     */
    @PostMapping("/listByIdNumbersAndPassword3")
    public Object listByIdNumbersAndPassword3() {
        List<String> idNumbers = Arrays.asList("372522195710100019", "371521198411051559");
        String account = "15888888888";
        List<SysUser> sysUsers = iSysUserService.listByIdNumbersAndPassword3(idNumbers, account);
        return sysUsers;
    }

    /**
     * 参数为map时，不会加密
     *
     * @param username 用户名
     * @param idNumber 身份证号码
     * @return
     */
    @GetMapping("/selectByMap")
    public Object selectByMap(String username, String idNumber) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("idNumber", idNumber);
        List<SysUser> sysUsers = iSysUserService.selectByMap(map);
        return sysUsers;
    }

    /**
     * 返回结果为map时，不会解密
     *
     * @param username 用户名
     * @param idNumber 身份证号码
     * @return
     */
    @GetMapping("/listMap")
    public Object listMap(String username, String idNumber) {
        List<Map<String, Object>> sysUsers = iSysUserService.listMap(username, idNumber);
        return sysUsers;
    }

    @GetMapping("/mybatisPlusPage")
    public Object page(Integer pageNum, Integer pageSize, String idNumber) {
        IPage<SysUser> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery(new SysUser())
                .eq(SysUser::getIdNumber, idNumber);
        IPage<SysUser> result = iSysUserService.page(page, wrapper);
        return result;
    }

    @GetMapping("/mybatisPlusPageBySql")
    public Object pageBySql(Integer pageNum, Integer pageSize, String idNumber) {
        IPage<SysUser> page = new Page<>(pageNum, pageSize);
        IPage<SysUser> result = iSysUserService.pageBySql(page, idNumber);
        return result;
    }

    @GetMapping("/pagehelper")
    public Object pagehelper(Integer pageNum, Integer pageSize, String idNumber) {
        PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery(new SysUser())
                .eq(SysUser::getIdNumber, idNumber);
        List<SysUser> list = iSysUserService.list(wrapper);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @GetMapping("/pagehelperBySql")
    public Object pagehelperBySql(Integer pageNum, Integer pageSize, String idNumber) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> list = iSysUserService.pagehelperBySql(idNumber);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @PostMapping("/listByUsernames")
    public Object listByUsernames(@RequestBody List<String> usernames) {
        List<SysUser> sysUsers = iSysUserService.listByUsernames(usernames);
        return sysUsers;
    }

    /**
     * 不支持此钟方式查询需加密查询的字段
     *
     * @param users
     * @return
     */
    @PostMapping("/listByUsers")
    public Object listByUsers(@RequestBody List<SysUser> users) {
        List<SysUser> sysUsers = iSysUserService.listByUsers(users);
        return sysUsers;
    }

    /**
     * 支持加密查询的字段
     *
     * @param user
     * @return
     */
    @PostMapping("/getByUsers")
    public Object getByUsers(@RequestBody SysUser user) {
        SysUser sysUser = iSysUserService.getByUsers(user);
        return sysUser;
    }

    /**
     * 支持加密查询的字段
     *
     * @param vo
     * @return
     */
    @PostMapping("/getByIdNumberVo")
    public Object getByIdNumberVo(@RequestBody IdNumberVO vo) {
        List<SysUser> sysUsers = iSysUserService.getByIdNumberVo(vo);
        return sysUsers;
    }
}

# mybatis-helper

## 简介

mybatis-helper是mybatis插件合集：目前提供字段存取加解密、脱敏等功能

### 特性

- 支持Mybatis&Mybatis-Plus的加解密（插入、更新、查询）
- 支持Mybatis-Plus的wrapper条件更新&查询
- 支持@Param标识加密参数，@Param注解value属性添加前缀：“enc_”
- 支持VO+注解的方式查询、插入、更新
- 支持自定义加解密、脱敏方式

### 代码托管：

> **[Github](https://github.com/xuwulin/mybatis-helper)**



### 参与贡献

欢迎各路好汉一起来参与完善 mybatis-helper，期待你的 PR！

- 贡献代码：代码地址 [mybatis-helper](https://github.com/xuwulin/mybatis-helper)，欢迎提交 Issue 或者 Pull Requests
- 维护文档：文档地址 [mybatis-helper](https://github.com/xuwulin/mybatis-helper)，欢迎参与翻译和修订

## 快速开始

### 安装

#### 环境

- JDK 8+
- Spring Boot 2+
- Mybatis或Mybatis-Plus 3.4.3+
- Maven or Gradle

#### 依赖

##### Spring Boot

Maven：

```xml
<dependency>
    <groupId>io.github.xuwulin</groupId>
    <artifactId>mybatis-helper-boot-starter</artifactId>
    <version>1.0.2</version>
</dependency>
```

Gradle：

```gr
compile group: 'io.github.xuwulin', name: 'mybatis-helper-boot-starter', version: '1.0.2'
```

### 配置

在`application.yml`配置文件中添加相关配置

```yaml
mybatis-helper:
  # 加解密
  crypto:
    # 加密方式（默认SM4）
    mode: SM4
    # 参数加密前缀（默认enc_，@Param注解value前缀，如@Param("enc_username") String username，表示username需要加密）
    param-prefix: enc_
    # 加解密类全类名（默认com.xwl.mybatishelper.service.impl.DefaultCryptoImpl）
    class-name: com.xwl.mybatishelper.service.impl.DefaultCryptoImpl
    # SM4对称加密密钥（必须为随机16位字符串）
    key: a3deb145671dd04e
    # SM2非对称加密公钥
    publicKey: 3059301306072a8648ce3d020106082a811ccf5501822d03420004e6d02503196bda21356390f43e324aa9cb42dbe64204ac4d9e1652fe055e4636702f84f376a54af81ca165d355f3e68d137702b9c715b4ecf70d50b65d9d6e78
    # SM2非对称加密私钥
    privateKey: 308193020100301306072a8648ce3d020106082a811ccf5501822d047930770201010420169d98f96e7a3deb145671dd04eefdf54b3c2196ca5569b5fb96a9ff5abe957da00a06082a811ccf5501822da14403420004e6d02503196bda21356390f43e324aa9cb42dbe64204ac4d9e1652fe055e4636702f84f376a54af81ca165d355f3e68d137702b9c715b4ecf70d50b65d9d6e78
  # 脱敏
  desensitized:
    # 填充符号
    replacement: '*'
    # 脱敏类全类名
    class-name: com.xwl.mybatishelper.service.impl.DefaultDesensitizedImpl
```

#### 配置说明

| 配置                                    | 默认值                                                     | 说明                                                         |
| :-------------------------------------- | :--------------------------------------------------------- | ------------------------------------------------------------ |
| mybatis-helper.crypto.mode              | SM4                                                        | 加密方式，默认国密SM4                                        |
| mybatis-helper.crypto.param-prefix      | enc_                                                       | @Param注解value前缀，如@Param("enc_username") String username，表示username需要加密） |
| mybatis-helper.crypto.class-name        | com.xwl.mybatishelper.service.impl.DefaultCryptoImpl       | 加解密类全类名，可自定义加解密方式，需要实现ICrypto接口      |
| mybatis-helper.crypto.key               | a3deb145671dd04e                                           | SM4对称加密密钥（必须为16位字符串），【建议替换默认值】      |
| mybatis-helper.crypto.publicKey         | 3059301306072xxx...                                        | SM2非对称加密公钥，【建议替换默认值】                        |
| mybatis-helper.crypto.privateKey        | 3081930201003xxx...                                        | SM2非对称加密私钥，【建议替换默认值】                        |
| mybatis-helper.desensitized.replacement | *                                                          | 脱敏填充值                                                   |
| mybatis-helper.desensitized.class-name  | com.xwl.mybatishelper.service.impl.DefaultDesensitizedImpl | 脱敏类全类名，可自定义脱敏方式，需要实现IDesensitized接口    |

### 注解

#### @CryptoField

- 描述：加解密字段注解，标识该字段需要加解密
- 使用位置：实体类需要加解密的字段

```java
public class SysUser {
    private String id;
    
    @CryptoField
    private String username;
}
```

| 属性      | 类型   | 必须指定 | 默认值               | 描述                                                         |
| --------- | ------ | -------- | -------------------- | ------------------------------------------------------------ |
| key       | String | 否       | ""                   | 对称加密秘钥（对称加密用，必须是16位字符串）<br />注解默认值为""，使用全局配置文件中指定的值，方便全局替换使用自定义填充值<br />当注解值不为""时，使用注解值 |
| algorithm | Enum   | 否       | CryptoAlgorithm.NONE | 加密解密算法<br />注解默认值为CryptoAlgorithm.NONE，使用全局配置文件中指定的加密算法，方便全局替换使用自定义填充值<br />当注解值不为CryptoAlgorithm.NONE时，使用注解值 |
| iCrypto   | Class  | 否       | NoneCryptoImpl.class | 加密解密器<br />注解默认值为NoneCryptoImpl.class，使用全局配置文件中指定的值（配置文件默认DefaultCryptoImpl.class），方便全局替换使用自定义脱敏实现<br />当注解值不为NoneCryptoImpl.class时，使用使用注解值<br />注：NoneCryptoImpl.class可以理解为null，因为注解属性的默认值不能指定为null，所以使用NoneCryptoImpl替换 |

CryptoAlgorithm

```java
public enum CryptoAlgorithm {
    /**
     * NONE 不指定，通过全局配置文件指定加密方式
     */
    NONE,

    /**
     * 国密 SM2 非对称加密算法，基于 ECC
     */
    SM2,

    /**
     * 国密 SM3 消息摘要算法，可以用 MD5 作为对比理解
     */
    SM3,

    /**
     * 国密 SM4 对称加密算法，无线局域网标准的分组数据算法
     */
    SM4
}
```

#### @DesensitizedField

- 描述：脱敏字段注解，标识该字段需要脱敏
- 使用位置：实体类需要脱敏的字段

```java
public class SysUser {
    private String id;
    
    @DesensitizedField
    private String username;
}
```

| 属性          | 类型   | 必须指定 | 默认值                     | 描述                                                         |
| ------------- | ------ | -------- | -------------------------- | ------------------------------------------------------------ |
| replacement   | String | 否       | ""                         | 填充值<br />注解默认值为""，使用全局配置文件中指定的值（配置文件默认*），方便全局替换使用自定义填充值<br />当注解值不为""时，使用注解值 |
| type          | Enum   | 否       | DesensitizedType.DEFAULT   | 脱敏类型<br />注解默认值为DesensitizedType.DEFAULT，使用正则表达式自动匹配（目前只能匹配：手机号、邮箱、银行卡号、身份证号）<br />当注解值不为DesensitizedType.DEFAULT时，使用注解值 |
| iDesensitized | Class  | 否       | NoneDesensitizedImpl.class | 脱敏器<br />注解默认值为NoneDesensitizedImpl.class，使用全局配置文件中指定的值（配置文件默认DefaultDesensitizedImpl.class），方便全局替换使用自定义脱敏实现<br />当注解值不为NoneDesensitizedImpl.class时，使用使用注解值<br />注：NoneDesensitizedImpl.class可以理解为null，因为注解属性的默认值不能指定为null，所以使用NoneDesensitizedImpl替换 |

DesensitizedType

```java
public enum DesensitizedType {
    /**
     * 默认
     */
    DEFAULT,
    /**
     * 用户id
     */
    USER_ID,
    /**
     * 中文名
     */
    CHINESE_NAME,
    /**
     * 身份证号
     */
    ID_CARD,
    /**
     * 座机号
     */
    FIXED_PHONE,
    /**
     * 手机号
     */
    MOBILE_PHONE,
    /**
     * 地址
     */
    ADDRESS,
    /**
     * 电子邮件
     */
    EMAIL,
    /**
     * 密码
     */
    PASSWORD,
    /**
     * 中国大陆车牌，包含普通车辆、新能源车辆
     */
    CAR_LICENSE,
    /**
     * 银行卡
     */
    BANK_CARD
}
```

## 自定义加解密

### 实现接口

实现com.xwl.mybatishelper.service.ICrypto接口

```java
@Slf4j
public class CryptoImpl implements ICrypto {
    @Override
    public String encrypt(CryptoAlgorithm cryptoAlgorithm, String plaintext, String key, String publicKey, String privateKey) throws Exception {
        log.info("自定义加密方法，明文：{}，加密结果：{}", plaintext, SM4Utils.encryptData_ECB(plaintext, key));
        return SM4Utils.encryptData_ECB(plaintext, key);
    }

    @Override
    public String decrypt(CryptoAlgorithm cryptoAlgorithm, String ciphertext, String key, String publicKey, String privateKey) throws Exception {
        log.info("自定义解密方法，密文：{}, 解密结果：{}", ciphertext, SM4Utils.decryptData_ECB(ciphertext, key));
        return SM4Utils.decryptData_ECB(ciphertext, key);
    }
}
```

### 用法

方式一：在配置文件application.yml中指定

```yaml
mybatis-helper:
  # 加解密
  crypto:
    # 加解密类全类名（默认com.xwl.mybatishelper.service.impl.DefaultCryptoImpl）
    class-name: com.xwl.mybatishelper.mybatisplus.crypto.CryptoImpl
```

方式二：在注解中指定

```java
@Data
public class SysUser {
    @CryptoField(iCrypto = CryptoImpl.class)
    private String password;
}
```

## 自定义脱敏

### 实现接口

实现com.xwl.mybatishelper.service.IDesensitized接口

### 用法

略：同自定义加解密

## 案例

建表sql：

```sql
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL,
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `id_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `dept_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
```

实体类：

```java
@Data
public class SysUser {
    private Integer id;

    @DesensitizedField
    private String account;

    @CryptoField
    @DesensitizedField(type = DesensitizedType.PASSWORD)
    private String password;

    @DesensitizedField(type = DesensitizedType.CHINESE_NAME)
    private String username;

    @CryptoField
    @DesensitizedField
    private String idNumber;

    private Integer deptId;
}
```

### Mybatis

引入依赖

```xml
 <dependency>
     <groupId>org.mybatis.spring.boot</groupId>
     <artifactId>mybatis-spring-boot-starter</artifactId>
     <version>2.2.2</version>
</dependency>
```

#### 插入

controller

```java
@PostMapping("/save")
public Object save(@RequestBody SysUser user) {
    int res = sysUserMapper.save(user);
    return res;
}


```

mapper

```java
/**
 * 保存用户
 *
 * @param user 用户信息
 * @return
 */
int save(@Param("user") SysUser user);
```

mapper.xml

```xml
<insert id="save">
    INSERT INTO sys_user (id, account, username, id_number, password, dept_id)
    VALUE (#{user.id}, #{user.account}, #{user.username}, #{user.idNumber}, #{user.password}, #{user.deptId});
</insert>
```

请求参数：

```json
{
  "id": 1,
  "account": "15888888888",
  "password": "123456",
  "username": "张三",
  "idNumber": "372522195710100019",
  "deptId": 1
}
```

插入结果

| id   | account     | password                         | username | id_number                                                    | dept_id |
| ---- | ----------- | -------------------------------- | -------- | ------------------------------------------------------------ | ------- |
| 1    | 15888888888 | eecba6b6b1258aedef3b3a319efc63c9 | 张三     | 693ccec8cf4cd38fd460deae4a197317553848b2b6a6451369f6a59d6cfabfa8 | 1       |

#### 更新

controller

```java
@PostMapping("/update")
public Object update(@RequestBody ParamVO vo) {
    int res = sysUserMapper.update(vo);
    return res;
}
```

ParamVO

```java
@Data
public class ParamVO {
    private String account;
    private String username;
    @CryptoField
    private String idNumber;
}
```

mapper

```java
/**
 * 更新用户
 *
 * @param vo 参数
 * @return
 */
int update(@Param("vo") ParamVO vo);
```

mapper.xml

```xml
<update id="update">
    UPDATE sys_user SET username = #{vo.username}, id_number = #{vo.idNumber} WHERE account = #{vo.account}
</update>
```

请求参数

```json
{
  "account": "15888888888",
  "username": "张三疯",
  "idNumber": "372522195710100017"
}
```

更新结果

| id   | account     | password                         | username | id_number                                                    | dept_id |
| ---- | ----------- | -------------------------------- | -------- | ------------------------------------------------------------ | ------- |
| 1    | 15888888888 | eecba6b6b1258aedef3b3a319efc63c9 | 张三疯   | 693ccec8cf4cd38fd460deae4a1973174ea38a7541e148440253c0565efb8e3c | 1       |

#### 查询

controller

```java
@GetMapping("/getBy")
public Object getBy(String idNumber) {
    List<SysUser> users = sysUserMapper.getBy(idNumber);
    return users;
}

// 分页查询
@GetMapping("/page")
public Object page(Integer pageNum, Integer pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    List<SysUser> users = sysUserMapper.list();
    PageInfo pageInfo = new PageInfo(users);
    return pageInfo;
}
```

mapper

```java
SysUser getBy(@Param("enc_idNumber") String idNumber);

// 分页查询
List<SysUser> list();
```

mapper.xml

```xml
<select id="getBy" resultType="com.xwl.mybatishelper.mybatisplus.entity.SysUser">
    SELECT * FROM sys_user WHERE id_number = #{enc_idNumber}
</select>

// 分页查询
<select id="list" resultType="com.xwl.mybatishelper.mybatisplus.entity.SysUser">
    SELECT * FROM sys_user
</select>
```

请求参数：372522195710100017

查询结果（先解密再脱敏）

```json
{
  "id": 1,
  "account": "158****8888",
  "password": "******",
  "username": "张*",
  "idNumber": "372522********0017",
  "deptId": 1
}

// 分页查询
{
  "total": 1,
  "list": [
    {
      "id": 1,
      "account": "158****8888",
      "password": "******",
      "username": "张*",
      "idNumber": "372522********0017",
      "deptId": 1
    }
  ],
  "pageNum": 1,
  "pageSize": 10,
  "size": 1,
  "startRow": 1,
  "endRow": 1,
  "pages": 1,
  "prePage": 0,
  "nextPage": 0,
  "isFirstPage": true,
  "isLastPage": true,
  "hasPreviousPage": false,
  "hasNextPage": false,
  "navigatePages": 8,
  "navigatepageNums": [
    1
  ],
  "navigateFirstPage": 1,
  "navigateLastPage": 1
}
```

### Mybatis-Plus

引入依赖

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.2</version>
</dependency>
```

#### Mybatis的写法Mybatis-Plus都支持

#### Mybatis-Plus的wrapper更新&查询

支持写法：

```java
// 更新，推荐使用Wrappers.lambdaUpdate构建更新条件
LambdaUpdateWrapper wrapper = Wrappers.lambdaUpdate(new SysUser()).eq(SysUser::getIdNumber, "");
UpdateWrapper<SysUser> wrapper = Wrappers.update(new SysUser()).eq("id_number", "");

LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>(new SysUser()).eq(SysUser::getIdNumber, "");
UpdateWrapper<SysUser> wrapper = new UpdateWrapper<>(new SysUser()).eq("id_number", "");

// 查询，推荐使用Wrappers.lambdaQuery构建查询条件
LambdaQueryWrapper wrapper = Wrappers.lambdaQuery(new SysUser()).eq(SysUser::getIdNumber, "");
QueryWrapper<SysUser> wrapper = Wrappers.query(new SysUser()).eq("id_number", "");

LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>(new SysUser()).eq(SysUser::getIdNumber, "");
QueryWrapper<SysUser> wrapper = new QueryWrapper<>(new SysUser()).eq("id_number", ""); 
```

不支持写法：获取不到实体类型

```java
// 更新
LambdaUpdateWrapper<SysUser> wrapper = Wrappers.<SysUser>lambdaUpdate().eq(SysUser::getIdNumber, "");
UpdateWrapper<SysUser> wrapper = Wrappers.<SysUser>update().eq("id_number", "");

LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<SysUser>().eq(SysUser::getIdNumber, "");
UpdateWrapper<SysUser> wrapper = new UpdateWrapper<>().eq("id_number", "");

// 查询
LambdaQueryWrapper<SysUser> wrapper = Wrappers.<SysUser>lambdaQuery().eq(SysUser::getIdNumber, "");
QueryWrapper<SysUser> wrapper = Wrappers.<SysUser>query().eq("id_number", "");

LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>().eq(SysUser::getIdNumber, "");
QueryWrapper<SysUser> wrapper = new QueryWrapper<>().eq("id_number", "");
```

用法详见案例：https://github.com/xuwulin/mybatis-helper 【mybatis-helper-boot-starter-test-mp】

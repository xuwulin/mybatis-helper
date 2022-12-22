package com.xwl.mybatishelper.util;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xwl.mybatishelper.enums.ConditionEnum;
import com.xwl.mybatishelper.enums.CryptoAlgorithm;
import com.xwl.mybatishelper.mapping.ParameterColumnMapping;
import com.xwl.mybatishelper.mapping.ParameterValueMapping;
import com.xwl.mybatishelper.properties.CryptoProperties;
import com.xwl.mybatishelper.service.ICrypto;
import com.xwl.mybatishelper.service.impl.NoneCryptoImpl;
import com.xwl.mybatishelper.annotation.CryptoField;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * mybatis-plus wrapper参数工具类
 *
 * @author xwl
 * @since 2022/12/7 10:56
 */
public class ParameterUtils {
    private static final Logger logger = LoggerFactory.getLogger(ParameterUtils.class);

    /**
     * 查找分页参数
     *
     * @param parameterObject 参数对象
     * @return 分页参数
     */
    public static Optional<IPage> findPage(Object parameterObject) {
        if (parameterObject != null) {
            if (parameterObject instanceof Map) {
                Map<?, ?> parameterMap = (Map<?, ?>) parameterObject;
                for (Map.Entry entry : parameterMap.entrySet()) {
                    if (entry.getValue() != null && entry.getValue() instanceof IPage) {
                        return Optional.of((IPage) entry.getValue());
                    }
                }
            } else if (parameterObject instanceof IPage) {
                return Optional.of((IPage) parameterObject);
            }
        }
        return Optional.empty();
    }

    /**
     * 获取要执行的SQL参数名、参数值、列名、表达式
     *
     * @param configuration Configuration
     * @param boundSql      BoundSql
     * @return sql完整语句
     */
    public static List<ParameterValueMapping> getParameterMapping(Configuration configuration, BoundSql boundSql) {
        List<ParameterValueMapping> parameterValueMappingList = new ArrayList<>();
        // 获取mapper里面方法上的参数
        Object sqlParameter = boundSql.getParameterObject();
        // sql语句里面需要的参数 -- 真实需要用到的参数 因为sqlParameter里面的每个参数不一定都会用到
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings.size() > 0 && sqlParameter != null) {
            // mybatis plus语法
            boolean wrapperStatus = parameterMappings.stream().anyMatch(item -> item.getProperty().startsWith(Constants.WRAPPER));
            if (sqlParameter instanceof Map && wrapperStatus) {
                Map<String, Object> parameterObjectMap = (Map<String, Object>) sqlParameter;
                AbstractWrapper<?, ?, ?> abstractWrapper = (AbstractWrapper<?, ?, ?>) parameterObjectMap.getOrDefault(Constants.WRAPPER, null);
                return getParameterMappingList(abstractWrapper);
            } else {
                Map<String, Object> keyValueMap = getParameterValueMap(configuration, boundSql);
                List<ParameterColumnMapping> parameterColumnMappingList = getParameterColumnMappingList(boundSql);
                parameterValueMappingList = parameterColumnMappingList.stream().map(item -> {
                    ParameterValueMapping parameterValueMapping = new ParameterValueMapping();
                    parameterValueMapping.setParamName(item.getParamName());
                    parameterValueMapping.setColumnName(item.getColumnName());
                    parameterValueMapping.setConditionEnum(item.getConditionEnum());
                    parameterValueMapping.setParamValue(keyValueMap.get(item.getParamName()));
                    return parameterValueMapping;
                }).collect(Collectors.toList());
                return parameterValueMappingList;
            }
        }
        return parameterValueMappingList;
    }

    /**
     * 获取要执行的SQL参数名、参数值、列名、表达式
     *
     * @param boundSql BoundSql
     * @return sql完整语句
     */
    public static List<ParameterColumnMapping> getParameterColumnMappingList(BoundSql boundSql) {
        List<ParameterColumnMapping> parameterColumnMappingList = new ArrayList<>();
        // 获取mapper里面方法上的参数
        Object sqlParameter = boundSql.getParameterObject();
        // sql语句里面需要的参数 -- 真实需要用到的参数 因为sqlParameter里面的每个参数不一定都会用到
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings.size() > 0 && sqlParameter != null) {
            try {

                String originalSql = boundSql.getSql();
                for (ParameterMapping parameterMapping : parameterMappings) {
                    originalSql = originalSql.replace("?", parameterMapping.getProperty());
                }
                String conditionSql = originalSql.substring(originalSql.toLowerCase().indexOf("where"));
                Expression expression = CCJSqlParserUtil.parseCondExpression(conditionSql);
                parameterColumnMappingList = getParameterColumnMappingList(expression);
                return parameterColumnMappingList;
            } catch (JSQLParserException e) {
                logger.error("条件表达式解析出错： sql = {}", boundSql.getSql(), e);
                return parameterColumnMappingList;
            } catch (Exception e) {
                logger.error("系统异常：", e);
                return parameterColumnMappingList;
            }
        }
        return parameterColumnMappingList;
    }

    public static Map<String, Object> getParameterValueMap(Configuration configuration, BoundSql boundSql) {
        Map<String, Object> parameterValueMap = new HashMap<>();
        // 获取mapper里面方法上的参数
        Object sqlParameter = boundSql.getParameterObject();
        // sql语句里面需要的参数 -- 真实需要用到的参数 因为sqlParameter里面的每个参数不一定都会用到
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings.size() > 0 && sqlParameter != null) {
            // 常用org.apache.ibatis.type.JdbcType数据类型处理
            if (configuration.getTypeHandlerRegistry().hasTypeHandler(sqlParameter.getClass())) {
                parameterValueMap.put(parameterMappings.get(0).getProperty(), sqlParameter);
            } else {
                MetaObject metaObject = configuration.newMetaObject(sqlParameter);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String name = parameterMapping.getProperty();
                    if (metaObject.hasGetter(name)) {
                        Object valueObject = metaObject.getValue(name);
                        parameterValueMap.put(name, valueObject);
                    } else if (boundSql.hasAdditionalParameter(name)) {
                        Object valueObject = boundSql.getAdditionalParameter(name);
                        parameterValueMap.put(name, valueObject);
                    }
                }
            }
        }
        return parameterValueMap;
    }

    /**
     * 获取查询条件封装真实的参数名，列名
     * abstractWrapper.getSqlSegment() 结果为(id = ew.paramNameValuePairs.MPGENVAL1 AND age IN (ew.paramNameValuePairs.MPGENVAL2,ew.paramNameValuePairs.MPGENVAL3))")
     *
     * @param abstractWrapper 查询条件封装对象
     * @return
     */
    public static List<ParameterValueMapping> getParameterMappingList(AbstractWrapper<?, ?, ?> abstractWrapper) {
        List<ParameterValueMapping> parameterValueMappingList = new ArrayList<>();
        if (abstractWrapper == null || StringUtils.isBlank(abstractWrapper.getSqlSegment())) {
            return parameterValueMappingList;
        }
        try {
            // 参数格式转换,对应SqlScriptUtils.safeParam 安全入参: #{入参}
            String sqlSegment = new GenericTokenParser(Constants.DOLLAR_LEFT_BRACE, Constants.RIGHT_BRACE, text -> text).parse(abstractWrapper.getSqlSegment());
            // 参数格式转换,对应SqlScriptUtils.unSafeParam 非安全入参:  ${入参}
            sqlSegment = new GenericTokenParser(Constants.HASH_LEFT_BRACE, Constants.RIGHT_BRACE, text -> text).parse(sqlSegment);
            // 将sqlSegment解析成expression
            Expression expression = CCJSqlParserUtil.parseCondExpression(sqlSegment);
            List<ParameterColumnMapping> parameterColumnMappingList = getParameterColumnMappingList(expression);
            parameterValueMappingList = parameterColumnMappingList
                    .stream()
                    .map(item -> {
                        ParameterValueMapping parameterValueMapping = new ParameterValueMapping();
                        parameterValueMapping.setParamName(item.getParamName());
                        parameterValueMapping.setColumnName(item.getColumnName());
                        parameterValueMapping.setConditionEnum(item.getConditionEnum());
                        // 需要去除前缀，格式参照AbstractWrapper.formatParam(String mapping, Object param)
                        String prefix = abstractWrapper.getParamAlias() + Constants.WRAPPER_PARAM_MIDDLE;
                        String key = item.getParamName().replace(prefix, "");
                        Object paramValue = abstractWrapper.getParamNameValuePairs().get(key);
                        parameterValueMapping.setParamValue(paramValue);
                        return parameterValueMapping;
                    })
                    .collect(Collectors.toList());
            return parameterValueMappingList;
        } catch (JSQLParserException e) {
            logger.error("条件表达式解析出错： sqlSegment = {}", abstractWrapper.getSqlSegment(), e);
            return parameterValueMappingList;
        } catch (Exception e) {
            logger.error("系统出错 = {}", abstractWrapper.getSqlSegment(), e);
            return parameterValueMappingList;
        }
    }

    /**
     * 获取sql表达式参数名、列名映射列表
     *
     * @param expression sql表达式
     * @return 参数、列名映射集合
     */
    public static List<ParameterColumnMapping> getParameterColumnMappingList(Expression expression) {
        List<ParameterColumnMapping> parameterColumnMappingList = new ArrayList<>();
        if (expression == null) {
            return parameterColumnMappingList;
        }
        expression.accept(new ExpressionVisitorAdapter() {
            @Override
            public void visit(Between expr) {
                String columnName = expr.getLeftExpression().toString();
                String startParamName = expr.getBetweenExpressionStart().toString();
                String endParamName = expr.getBetweenExpressionEnd().toString();
                parameterColumnMappingList.add(new ParameterColumnMapping(startParamName, columnName, ConditionEnum.BETWEEN_START));
                parameterColumnMappingList.add(new ParameterColumnMapping(endParamName, columnName, ConditionEnum.BETWEEN_END));
            }

            @Override
            public void visit(EqualsTo expr) {
                parameterColumnMappingList.add(new ParameterColumnMapping(expr.getRightExpression().toString(), expr.getLeftExpression().toString(), ConditionEnum.EQUAL));
            }

            @Override
            public void visit(GreaterThan expr) {
                parameterColumnMappingList.add(new ParameterColumnMapping(expr.getRightExpression().toString(), expr.getLeftExpression().toString(), ConditionEnum.GREATER));
            }

            @Override
            public void visit(GreaterThanEquals expr) {
                parameterColumnMappingList.add(new ParameterColumnMapping(expr.getRightExpression().toString(), expr.getLeftExpression().toString(), ConditionEnum.GREATER_EQUAL));
            }

            @Override
            public void visit(InExpression expr) {
                ItemsList itemsList = expr.getRightItemsList();
                if (itemsList instanceof ExpressionList) {
                    ((ExpressionList) itemsList).getExpressions().forEach(item -> {
                        parameterColumnMappingList.add(new ParameterColumnMapping(item.toString(), expr.getLeftExpression().toString(), ConditionEnum.IN));
                    });
                }
            }

            @Override
            public void visit(LikeExpression expr) {
                parameterColumnMappingList.add(new ParameterColumnMapping(expr.getRightExpression().toString(), expr.getLeftExpression().toString(), ConditionEnum.LIKE));
            }

            @Override
            public void visit(MinorThan expr) {
                parameterColumnMappingList.add(new ParameterColumnMapping(expr.getRightExpression().toString(), expr.getLeftExpression().toString(), ConditionEnum.LESS));
            }

            @Override
            public void visit(MinorThanEquals expr) {
                parameterColumnMappingList.add(new ParameterColumnMapping(expr.getRightExpression().toString(), expr.getLeftExpression().toString(), ConditionEnum.LESS_EQUAL));
            }

            @Override
            public void visit(NotEqualsTo expr) {
                parameterColumnMappingList.add(new ParameterColumnMapping(expr.getRightExpression().toString(), expr.getLeftExpression().toString(), ConditionEnum.NOT_EQUAL));
            }
        });
        return parameterColumnMappingList;
    }

    /**
     * 判断是参数类型否是AbstractWrapper
     *
     * @param obj 参数
     * @return
     */
    public static boolean isWrapper(Object obj) {
        try {
            // 防止没有引入mybatis-plus依赖时报：ClassNotFoundException
            boolean isPresent = null != Class.forName("com.baomidou.mybatisplus.core.conditions.AbstractWrapper");
            if (isPresent) {
                return obj instanceof AbstractWrapper;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 处理mybatis-plus的Wrapper（QueryWrapper、LambdaQueryWrapper、UpdateWrapper、LambdaUpdateWrapper）
     *
     * @param obj mybatis-plus的Wrapper条件
     * @throws Exception
     */
    public static void handleWrapper(Object obj, CryptoProperties cryptoProperties) throws Exception {
        AbstractWrapper wrapper = (AbstractWrapper) obj;
        // 获取查询条件封装真实的参数名，列名
        List<ParameterValueMapping> parameterMappingList = getParameterMappingList(wrapper);

        // 查询参数
        Map paramNameValuePairs = wrapper.getParamNameValuePairs();
        // wrapper泛型实体
        Object entity = wrapper.getEntity();
        if (Objects.nonNull(entity)) {
            TableInfo tableInfo = TableInfoHelper.getTableInfo(entity.getClass());
            List<TableFieldInfo> fieldList = tableInfo.getFieldList();
            for (ParameterValueMapping parameterValueMapping : parameterMappingList) {
                for (TableFieldInfo tableFieldInfo : fieldList) {
                    Field field = tableFieldInfo.getField();
                    CryptoField annotation = field.getAnnotation(CryptoField.class);
                    if (annotation != null) {
                        String property = tableFieldInfo.getProperty();
                        String column = tableFieldInfo.getColumn();
                        if (StrUtil.equals(parameterValueMapping.getColumnName(), column)) {
                            String paramName = parameterValueMapping.getParamName();
                            Object paramValue = parameterValueMapping.getParamValue();
                            if (paramName.contains(".")) {
                                // paramKey
                                String paramKey = paramName.substring(paramName.lastIndexOf(".") + 1);

                                // 非对称加密私钥
                                String privateKey = cryptoProperties.getPrivateKey();
                                // 非对称加密公钥
                                String publicKey = cryptoProperties.getPublicKey();
                                // 对称加密密钥（优先使用注解上的配置）
                                String secret = annotation.key();
                                if (StrUtil.isBlank(secret)) {
                                    secret = cryptoProperties.getKey();
                                }
                                // 加密算法（优先使用注解上的配置）
                                CryptoAlgorithm cryptoAlgorithm = annotation.algorithm();
                                if (cryptoAlgorithm == null || cryptoAlgorithm == CryptoAlgorithm.NONE) {
                                    cryptoAlgorithm = CryptoAlgorithm.valueOf(cryptoProperties.getMode());
                                }
                                // 加密实现（优先使用注解上的配置）
                                ICrypto iCrypto;
                                Class<? extends ICrypto> iCryptoImpl = annotation.iCrypto();
                                if (iCryptoImpl == null || iCryptoImpl == NoneCryptoImpl.class) {
                                    Class cryptClazz = Class.forName(cryptoProperties.getClassName());
                                    iCrypto = (ICrypto) cryptClazz.newInstance();
                                } else {
                                    iCrypto = iCryptoImpl.newInstance();
                                }

                                // 加密
                                String encryptValue = iCrypto.encrypt(cryptoAlgorithm, String.valueOf(paramValue), secret, publicKey, privateKey);
                                paramNameValuePairs.put(paramKey, encryptValue);
                                logger.info("属性：{}，列名：{}，条件：{}，参数原值：{}，参数加密后值：{}", property, column, parameterValueMapping.getConditionEnum().getCode(), paramValue, encryptValue);
                            }
                        }
                    }
                }
            }
        }
    }
}

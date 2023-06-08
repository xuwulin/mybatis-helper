package com.xwl.mybatishelper.service.impl;

import com.xwl.mybatishelper.enums.IntegrityAlgorithm;
import com.xwl.mybatishelper.service.IIntegrity;
import com.xwl.mybatishelper.util.IntegrityUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 完整性实现
 * @author xwl
 * @since 2023/6/5 12:10
 */
public class DefaultIntegrityImpl implements IIntegrity {
    @Override
    public String calc(IntegrityAlgorithm integrityAlgorithm, String... fields) {
        String value = null;
        if (fields == null || fields.length == 0) {
            return null;
        }
        value = Arrays.asList(fields).stream().collect(Collectors.joining(","));

        switch (integrityAlgorithm) {
            case MD5:
                return IntegrityUtils.md5(value);
            case SHA1:
                return IntegrityUtils.sha1(value);
            case SHA256:
                return IntegrityUtils.sha256(value);
            default:
                return null;
        }
    }
}

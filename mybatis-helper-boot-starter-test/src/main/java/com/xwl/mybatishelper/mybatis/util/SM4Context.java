package com.xwl.mybatishelper.mybatis.util;

public class SM4Context {
    public int mode = 1;
    public long[] sk = new long[32];
    public boolean isPadding = true;

    public SM4Context() {
    }
}
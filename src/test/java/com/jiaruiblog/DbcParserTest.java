package com.jiaruiblog;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class DbcParserTest {

    // 测试传入一个空路径的文件
    @Test
    public void testParseFile1() {
        boolean thrown = false;
        String filePath = "";
        try {
            Map<DbcMessage, List<DbcSignal>> dbcMessageListMap = DbcParser.parseFile(filePath);
            System.out.println(dbcMessageListMap);
            Assert.assertEquals(0, dbcMessageListMap.size());
        } catch (Exception e) {
            if ("文件不存在".equals(e.getMessage())) {
                thrown = true;
            }
        }
        Assert.assertTrue(thrown);
    }

    // 测试传入一个空路径的文件
    @Test
    public void testParseFile2() {
        boolean thrown = false;
        String filePath = "C://project/dbcs/Ch2_TimeSpcEnv_V1.2.dbc";
        try {
            Map<DbcMessage, List<DbcSignal>> dbcMessageListMap = DbcParser.parseFile(filePath);
            System.out.println(dbcMessageListMap);
            Assert.assertEquals(0, dbcMessageListMap.size());
        } catch (Exception e) {
            if ("文件不存在".equals(e.getMessage())) {
                thrown = true;
            }
        }
        Assert.assertTrue(thrown);
    }
}
package com.jiaruiblog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;

/**
 * DbcParser测试类
 *
 * @author Jarrett Luo
 * @version 1.0
 */
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

        Map<DbcMessage, List<DbcSignal>> dbcMessageListMap = null;
        String dbcPath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("dbcs/OBD2.dbc")).getPath();
        String jsonPath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("dbcs/OBD2.json")).getPath();

        try {
            dbcMessageListMap = DbcParser.parseFile(dbcPath);
        } catch (Exception e) {
            e.printStackTrace();
        }


        String content = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonPath));
            content = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assert dbcMessageListMap != null;
        Assert.assertEquals(content, dbcMessageListMap.toString());
    }
}
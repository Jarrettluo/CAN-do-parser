package com.jiaruiblog;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class DbcParserTest {

    @Test
    public void parseFile() {
        String filePath = "";
        try {
            Map<DbcMessage, List<DbcSignal>> dbcMessageListMap = DbcParser.parseFile(filePath);
            System.out.println(dbcMessageListMap);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
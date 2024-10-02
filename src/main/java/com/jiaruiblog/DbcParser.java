package com.jiaruiblog;

import com.jiaruiblog.utils.CollectionUtils;
import com.jiaruiblog.utils.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jiaruiblog.utils.FileUtil.getEncoding;

/**
 * DBC解析工具
 *
 * @author Jarrett Luo
 * @version 1.0
 */
public class DbcParser {

    static final List<Object> SIGNAL_LIST = new ArrayList<>();

    static final List<Object> SIGNAL_NAME = new ArrayList<>();

    static final List<Object> MESSAGE_NAME = new ArrayList<>();

    public static final Pattern NODE_PATTERN = Pattern.compile("^BU_: (.*)", Pattern.DOTALL);

    public static final Pattern MESSAGE_PATTERN = Pattern.compile("^BO_ (.*?) (.*?): (.*?) (.*)", Pattern.DOTALL);

    public static final Pattern SIGNAL_PATTERN = Pattern.compile("^[\\t| ]SG_ (.*?) ?: (.*?)\\|(.*?)@(.*?) \\((.*?),(.*?)\\) \\[(.*?)\\|(.*?)\\] \"(.*?)\" (.*)", Pattern.DOTALL);

    // dbc的属性定义部分
    public static final Pattern BA_PATTERN = Pattern.compile("^BA_ \"SystemSignalLongSymbol\" SG_ (.*?) (.*?) \"(.*?)\"", Pattern.DOTALL);

    /**
     * <p>解读dbc文件</p>
     * @param filePath 传递的文件信息
     * @return <code>java.util.Map&lt; com.jiaruiblog.DbcMessage,java.util.List&lt;com.jiaruiblog.DbcSignal&gt;&gt;</code>
     * @throws IOException io异常
     **/
    public static Map<DbcMessage, List<DbcSignal>> parseFile(String filePath) throws IOException {
        if (StringUtils.isEmpty(filePath)) {
            throw new IOException("文件不存在");
        }

        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("文件不存在");
        }

        Map<String, String> baMap = new HashMap<>();
        //解析文件
        List<Object> dbcData = (List<Object>) DbcParser.regexDbc(filePath, baMap);
        return dbcDetail(dbcData, baMap);
    }

    private static BufferedReader readDbc(String filePath) throws IOException {
        String charset = getEncoding(filePath);
        if (Objects.isNull(charset)) {
            charset = "windows-1252";
        }

        InputStream inputStream = new FileInputStream(filePath);
        InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName(charset));
        return new BufferedReader(reader);
    }

    public static Object regexDbc(String file, Map<String, String> baMap) throws IOException {
        BufferedReader dbcBuffer = readDbc(file);
        String strTmp;
        List<Object> allDataList = new ArrayList<>();
        strTmp = dbcBuffer.readLine();
        while (strTmp != null) {
            Matcher matcherNode = NODE_PATTERN.matcher(strTmp);
            if (matcherNode.find()) {
                String matcherGroup = matcherNode.group(1);
                Arrays.asList(matcherGroup.split(" "));
            }
            // matcher messagePattern
            Matcher matcherMessage = MESSAGE_PATTERN.matcher(strTmp);
            if (matcherMessage.find()) {
                SIGNAL_LIST.clear();
                List<Object> message = new ArrayList<>();
                message.add(matcherMessage.group(1));
                message.add(matcherMessage.group(2));
                message.add(matcherMessage.group(3));
                message.add(matcherMessage.group(4));
                SIGNAL_LIST.add(message);
                MESSAGE_NAME.add(message.get(1));
                strTmp = dbcBuffer.readLine();
                matcherMessage = MESSAGE_PATTERN.matcher(strTmp);
                strTmp = addAllDataList(dbcBuffer, strTmp, allDataList, matcherMessage);
            } else {
                strTmp = dbcBuffer.readLine();
                if (strTmp != null) {
                    Matcher baMatcher = BA_PATTERN.matcher(strTmp);
                    if (baMatcher.find()) {
                        String idFormat = baMatcher.group(1);
                        String originSignal = baMatcher.group(2);
                        String attribute = baMatcher.group(3);
                        baMap.put(idFormat + "_" + originSignal, attribute);
                    }
                } else {
                    break;
                }

            }
        }
        dbcBuffer.close();
        return allDataList;
    }


    private static String addAllDataList(BufferedReader dbcBuffer, String strTmp, List<Object> allDataList,
                                         Matcher matcherMessage) throws IOException {
        Matcher matcherSignal = SIGNAL_PATTERN.matcher(strTmp);
        if (!matcherMessage.find()) {
            while (matcherSignal.find()) {
                List<Object> signal = new ArrayList<>();
                signal.add(matcherSignal.group(1));
                signal.add(matcherSignal.group(2));
                signal.add(matcherSignal.group(3));
                signal.add(matcherSignal.group(4));
                signal.add(matcherSignal.group(5));
                signal.add(matcherSignal.group(6));
                signal.add(matcherSignal.group(7));
                signal.add(matcherSignal.group(8));
                signal.add(matcherSignal.group(9));
                signal.add(matcherSignal.group(10));
                SIGNAL_LIST.add(signal);
                SIGNAL_NAME.add(signal.get(0));
                strTmp = dbcBuffer.readLine();
                if (StringUtils.isEmpty(strTmp)) {
                    break;
                }
                matcherSignal = SIGNAL_PATTERN.matcher(strTmp);
            }
            if (CollectionUtils.isNotEmpty(SIGNAL_LIST)) {
                List<Object> c = new ArrayList<>(SIGNAL_LIST);
                allDataList.add(c);
            }
        } else {
            strTmp = dbcBuffer.readLine();
            MESSAGE_PATTERN.matcher(strTmp);
        }
        return strTmp;
    }

    /**
     * 将dbc文件转换为map类型
     *
     * @param dbcData dbc集合数据
     * @return dbc解析结果
     */
    private static Map<DbcMessage, List<DbcSignal>> dbcDetail(List<Object> dbcData, Map<String, String> baMap) {
        if (dbcData == null || dbcData.isEmpty()) {
            return new HashMap<>();
        }
        Map<DbcMessage, List<DbcSignal>> resultMap = new HashMap<>(24);
        for (Object dbcDatum : dbcData) {
            List<Object> messages = (List<Object>) dbcDatum;
            List<Object> message = (List<Object>) messages.get(0);

            DbcMessage dbcMessageDTO = new DbcMessage();
            dbcMessageDTO.setIdFormat(message.get(0).toString());
            dbcMessageDTO.setName(message.get(1).toString());
            dbcMessageDTO.setDlc(Integer.parseInt(message.get(2).toString()));
            dbcMessageDTO.setTransmitter(message.get(3).toString());

            dbcMessageDTO.setTxMethod("");
            dbcMessageDTO.setCycleTime("");
            dbcMessageDTO.setComment("");

            List<DbcSignal> dbcSignals = new ArrayList<>(10);
            for (int j = 1; j < messages.size(); j++) {
                List<Object> signal = (List<Object>) messages.get(j);

                DbcSignal dbcSignalDTO = new DbcSignal();

                String idFormat = dbcMessageDTO.getIdFormat();
                String signalName = signal.get(0).toString();
                // 替换BA属性
                String userDefinedKey = idFormat + "_" + signalName;
                if (baMap.containsKey(userDefinedKey)) {
                    signalName = baMap.get(userDefinedKey);
                }
                dbcSignalDTO.setName(signalName);
                dbcSignalDTO.setStartBit(Integer.parseInt(signal.get(1).toString()));
                dbcSignalDTO.setLength(Integer.valueOf(signal.get(2).toString()));

                dbcSignalDTO.setLittleEndian(((String) signal.get(3)).charAt(0) == '1' ? "true" : "false");

                dbcSignalDTO.setSign(((String) signal.get(3)).substring(1));
                dbcSignalDTO.setFactor(String.valueOf(Float.valueOf(signal.get(4).toString())));
                dbcSignalDTO.setOffset(String.valueOf(Float.valueOf(signal.get(5).toString())));
                dbcSignalDTO.setMinimum(String.valueOf(Float.valueOf(signal.get(6).toString())));
                String maximum = signal.get(7).toString().trim().replace("\u202C", "");
                dbcSignalDTO.setMaximum(String.valueOf(Float.parseFloat(maximum)));
                dbcSignalDTO.setUnit(signal.get(8).toString());

                dbcSignals.add(dbcSignalDTO);
            }
            resultMap.put(dbcMessageDTO, dbcSignals);
        }
        return resultMap;
    }

}

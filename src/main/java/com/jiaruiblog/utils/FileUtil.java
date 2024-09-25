package com.jiaruiblog.utils;

import info.monitorenter.cpdetector.io.*;

import java.io.File;

public class FileUtil {

    private FileUtil() {
        throw new IllegalArgumentException("Utility class");
    }

    public static String getEncoding(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new RuntimeException("file is null or empty");
        }
        File file = new File(fileName);
        if (!file.exists()) {
            throw new RuntimeException("file is not exist");
        }

        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
        detector.add(JChardetFacade.getInstance());
        detector.add(new ParsingDetector(false));
        // ASCIIDetector用于ASCII编码测定
        detector.add(ASCIIDetector.getInstance());
        // UnicodeDetector用于Unicode家族编码的测定
        detector.add(UnicodeDetector.getInstance());
        java.nio.charset.Charset charset;
        try {
            charset = detector.detectCodepage(file.toURI().toURL());
            return charset.name();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

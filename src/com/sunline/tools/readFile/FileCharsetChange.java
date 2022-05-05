package com.sunline.tools.readFile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class FileCharsetChange {

    /**
     * 转换目标文件或目录为指定编码
     * @param filePath
     * @param sourceCharsetname
     * @param targetCharsetName
     */
    public static void convert(String filePath, String sourceCharsetname,
                               String targetCharsetName) {
        convert(new File(filePath),sourceCharsetname,targetCharsetName);
    }

    /**
     * 转换目标文件或目录为指定编码
     * @param file
     * @param sourceCharsetname
     * @param targetCharsetName
     */
    public static void convert(File file, String sourceCharsetname,
                               String targetCharsetName) {
        convert(file,sourceCharsetname,targetCharsetName,null);
    }

    /**
     * 转换目标文件或目录为指定编码
     * @param filePath
     * @param sourceCharsetname
     * @param targetCharsetName
     * @param filter
     */
    public static void convert(String filePath, String sourceCharsetname,
                               String targetCharsetName, FilenameFilter filter) {
        convert(new File(filePath),sourceCharsetname,targetCharsetName,filter);
    }

    /**
     * 转换目标文件或目录为指定编码
     * @param file
     * @param sourceCharsetname
     * @param targetCharsetName
     * @param filter
     */
    public static void convert(File file, String sourceCharsetname,
                               String targetCharsetName, FilenameFilter filter) {
        if (file.isDirectory()) {
            File[] files = null;
            if (filter == null) {
                files = file.listFiles();
            }else {
                files = file.listFiles(filter);
            }
            for (File f: files) {
                convert(f,sourceCharsetname,targetCharsetName,filter);
            }
        }else {
            if (filter == null || filter.accept(file.getParentFile(),file.getName())) {
                String fileContent = getFileContentFromCharset(file,
                        sourceCharsetname);
                saveFileUseTargetCharset(file, targetCharsetName, fileContent);

            }
        }
    }

    /**
     * 以指定编码方式读取文件，返回文件内容
     *
     * @param file
     *            要转换的文件
     * @param sourceCharsetName
     *            源文件的编码
     * @return
     * @throws UnsupportedCharsetException
     */
    public static String getFileContentFromCharset(File file,
                                                   String sourceCharsetName) {
        if (!Charset.isSupported(sourceCharsetName)) {
            throw new UnsupportedCharsetException(sourceCharsetName);
        }
        try (InputStream inputStream = new FileInputStream(file);
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, sourceCharsetName);) {
            char[] chars = new char[(int) file.length()];
            inputStreamReader.read(chars);
            String str = new String(chars).trim();
            return str;
        }catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 以指定编码方式写文本文件，存在会覆盖
     *
     * @param file
     *            要写入的文件
     * @param targetCharsetName
     *            要转换的编码
     * @param content
     *            文件内容
     * @throws UnsupportedCharsetException
     */
    public static void saveFileUseTargetCharset(File file, String targetCharsetName,
                                        String content) {
        if (!Charset.isSupported(targetCharsetName)) {
            throw new UnsupportedCharsetException(targetCharsetName);
        }
        try (OutputStream outputStream = new FileOutputStream(file);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, targetCharsetName)) {
            outputStreamWriter.write(content);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}

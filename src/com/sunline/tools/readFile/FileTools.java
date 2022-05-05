package com.sunline.tools.readFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileTools {

    /**
     * 从路径中获得文件名
     * @param filePath 文件路径
     * @return 返回文件名
     */
    public static String getFileName(String filePath) {
        filePath = filePath.trim();
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        return fileName;
    }

    /**
     *
     * @param originalText 原始字符串
     * @param targetText   目标字符
     * @param num          目标字符在原始字符中第几次出现
     * @return int         返回目标字符在原始字符中的index坐标
     */
    public static int findCharIndex(String originalText,String targetText,int num) {
        int count = 0;
        int index = 0;
        while((index = originalText.indexOf(targetText,index))!=-1){
            count++;
            if(count>=num){
                break;
            }else{
                index=index+targetText.length();
            }
        }
        return index;
    }
    /**
     *
     * @param  content 内容
     * @param  fileCatage 文件路径
     * @param  fileName 文件名称
     * @param  code 字符编码 GBK,utf-8
     * @param  flag true文件尾部追加,false覆盖原文件内容
     */
    public static void writeFile(String content, String fileCatage, String fileName, String code,boolean flag) {
        //System.out.println(file.exists() + "-----" + file.getAbsolutePath());
        if (!new File(fileCatage).exists()) {
            try {
                Files.createDirectories(Paths.get(fileCatage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try(FileOutputStream outputStream = new FileOutputStream(new File(fileCatage + fileName), flag)){
            outputStream.write(content.getBytes(code));
            //Thread.sleep(1); //生成文件的文件夹设置在C盘有时会报错FileNotFoundException
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getEncoding(String filePath) {

        return null;
    }
}

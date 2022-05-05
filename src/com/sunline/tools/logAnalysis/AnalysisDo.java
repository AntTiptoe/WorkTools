package com.sunline.tools.logAnalysis;

import com.sunline.tools.readFile.FileCharsetChange;
import com.sunline.tools.readFile.FileTools;
import org.hswebframework.utils.file.EncodingDetect;

import java.io.*;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AnalysisDo{
    private static BufferedReader bufferedReader;
    private static String logContentTemp;
    private static String logContent;
    private static String startTime;
    private static String endTime;
    private static long timeDifference;
    private static String analysisLogName;

    private static AnalysisDo analysisDo = new AnalysisDo();
    private AnalysisDo(){
        bufferedReader = null;
        logContentTemp = "";
        logContent = "";
        startTime = "0000-00-00 00:00:00,000";
        endTime = "0000-00-00 00:00:00,000";
        timeDifference = 0;
        analysisLogName = "";
    }
    public static AnalysisDo getAnalysisDo() {
        return analysisDo;
    }

    private static void flush() {
        logContentTemp = "";
        logContent = "";
        startTime = "0000-00-00 00:00:00,000";
        endTime = "0000-00-00 00:00:00,000";
        timeDifference = 0;
        analysisLogName = "";
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 基本思路：顺序读取日志A的内容，提取时间戳之间的耗时，同时，收集两个时间戳之间的日志内容；给定一个耗时范围，如果耗时大于这个范围的，则去日志
     * B中收集相同的内容。日志B的处理思路：因为日志B的遍历是随A动而动，那么每次保留上一次的遍历的末尾。下一次遍历的开始即为上一次遍历的末尾。
     * 匹配到B中的内容后，将A,B中的日志内容输出到输出文件中。
     * @param logTimestampContentStartSign 日志时间内容起始标识，一般为"["符号
     * @param overTimeSize 默认警戒时长，超过该值我们认为此段耗时比较长，需要提取
     * @param logsPath 日志所在路径
     * @param analysisLogDir 生成的分析日志路径
     * @return String 分析日志所在路径
     */
    public static String analysisLogDo(String logTimestampContentStartSign,long overTimeSize,String logsPath,String analysisLogDir) {
        try {
            String encode = EncodingDetect.getJavaEncode(logsPath);                     //获得要处理日志的编码格式
            System.out.println(encode);
            Charset charset = Charset.forName(encode);                                 //设定读入字符集
            bufferedReader = new BufferedReader(new FileReader(logsPath,charset));        //根据字符集创建读入缓存
            analysisLogName = FileTools.getFileName(logsPath);                            //获取要处理的文件名，方便后边生成分析日志的文件名
            while ((logContentTemp = bufferedReader.readLine()) != null) {
                if (logContentTemp.startsWith(logTimestampContentStartSign)) {
                    if (startTime.compareTo("0000-00-00 00:00:00,000") == 0) {
                        endTime = logContentTemp.substring(FileTools.findCharIndex(logContentTemp, "[", 2) + 1, FileTools.findCharIndex(logContentTemp, "]", 2));
                        startTime = endTime;
                        logContent = logContentTemp + "";
                        continue;
                    }
                    startTime = endTime;
                    endTime = logContentTemp.substring(FileTools.findCharIndex(logContentTemp, "[", 2) + 1, FileTools.findCharIndex(logContentTemp, "]", 2));
                    if (startTime.compareTo(endTime) == 0) {
                        logContent = logContentTemp + "";
                        continue;
                    }
                    logContent = logContent + "\r\n" + logContentTemp ;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS");
                    timeDifference = sdf.parse(endTime).getTime() - sdf.parse(startTime).getTime();
                    if (timeDifference >= overTimeSize) {
                        logContent = logContent + "\r\n" + "耗时差为：" + timeDifference + "ms" + "\r\n" + "\r\n" + "\r\n";
                        FileTools.writeFile(logContent, analysisLogDir, "analysis_"+analysisLogName, encode, true);
                        logContent = logContentTemp;
                        continue;
                    }
                    logContent = logContentTemp + "";
                    continue;
                }
                logContent = logContent + "\r\n" + logContentTemp;
            }
            String returnString = analysisLogDir + "analysis_"+analysisLogName;
            flush();
            return returnString;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            flush();
        }finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                    flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        flush();
        return "";
    }

    /**
     * 指定转换文件的编码格式
     * @param logTimestampContentStartSign 日志时间内容起始标识，一般为"["符号
     * @param overTimeSize 默认警戒时长，超过该值我们认为此段耗时比较长，需要提取
     * @param logsPath 日志所在路径
     * @param analysisLogDir 生成的分析日志路径
     * @param analysisLogEncode 指定生成分析日志的编码格式
     * @return String 分析日志所在路径
     */
    public static String analysisLogDo(String logTimestampContentStartSign,long overTimeSize,String logsPath,String analysisLogDir,String analysisLogEncode) {
        String analysisLogPath = analysisLogDo(logTimestampContentStartSign,overTimeSize,logsPath,analysisLogDir);
        String javaEncode = EncodingDetect.getJavaEncode(analysisLogPath);
        if (analysisLogEncode.equalsIgnoreCase(javaEncode)) {
            return analysisLogPath;
        }
        FileCharsetChange.convert(analysisLogPath,javaEncode,analysisLogEncode);
        System.out.println(analysisLogPath + "文件已由" + javaEncode + "转化为" + analysisLogEncode);
        return analysisLogPath;
    }
}

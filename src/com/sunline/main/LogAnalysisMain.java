package com.sunline.main;

import com.sunline.tools.logAnalysis.AnalysisDo;
import com.sunline.tools.readFile.ReadFile;

import java.io.*;
import java.util.ArrayList;

/**
 * 批量日志慢日志提取程序
 * 此程序旨在从日志中提取耗时比较长的日志块，方便开发人员分析统计
 * 最好在原日志中截取一笔交易，不截取也可，就是统计日志也会比较大
 * 第一版 完成文件读写功能，摘取日志中的时间，统计各时间段的耗时并输出成文件
 * 第二版 根据第一版中摘取日志中的时间，完成耗时长日志的摘取功能，完善文件读写模块。完成根据原日志自动匹配输出日志的编码格式，也可以指定输出文件的编码格式
 */
public class LogAnalysisMain {
    private final static String logTimestampContentStartSign = "[";                          //设置日志中含时间戳的内容开始标志
    private final static long overTimeSize = 10;                                             //设置超时范围，一旦日志的时间戳差大于这个值，我们就认为该块用时异常
    private final static String logsDir = "C:/Users/92497/Desktop/logs/";                  //设置日志存放路径
    private final static String analysisLogDir = "D:/Test/analysis/";                     //设置分析日志存放路径，自动创建不存在的文件夹
    public static void main(String[] args) {
        File file = new File(logsDir);
        ArrayList<String> filePaths = new ReadFile().getFilePaths(file);
        AnalysisDo analysisDo = AnalysisDo.getAnalysisDo();
        for (String pathString: filePaths) {
            String analysisLogPath = analysisDo.analysisLogDo(logTimestampContentStartSign, overTimeSize, pathString, analysisLogDir,"utf-8");
            System.out.println(analysisLogPath + "------------->" + "已生成完成");
        }
    }
}

package com.sunline.tools.readFile;

import java.io.File;
import java.util.ArrayList;

public class ReadFile {
    public ReadFile() {};
    /**
     * 此方法返回的路径中带\符号
     * @param file
     * @return ArrayList<String>
     */
    public static ArrayList<String> readFile(File file) {
        ArrayList<String> filePaths = new ArrayList<String>();
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                readFile(f);
            }else {
                String fileAbsolutePath = f.getAbsolutePath();
                filePaths.add(fileAbsolutePath);
            }
        }
        return filePaths;
    }

    /**
     * 此方法返回的路径中带/符号
     * @param file
     * @return ArrayList<String>
     */
    public static ArrayList<String> getFilePaths(File file) {
        ArrayList<String> filePaths = readFile(file);
        String stringTemp = "";
        for (int i = 0; i < filePaths.size(); i++) {
            stringTemp = filePaths.get(i).replace("\\","/");
            filePaths.remove(i);
            filePaths.add(i,stringTemp);
        }
        return filePaths;
    }
}

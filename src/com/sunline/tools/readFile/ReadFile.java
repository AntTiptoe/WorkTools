package com.sunline.tools.readFile;

import java.io.File;
import java.util.ArrayList;

public class ReadFile {
    public ReadFile() {};
    public ArrayList<String> filePaths = new ArrayList<String>();
    /**
     * 此方法返回的路径中带\符号
     * @param file
     * @return ArrayList<String>
     */
    public ArrayList<String> readFile(File file) {
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
    public ArrayList<String> getFilePaths(File file) {
        ArrayList<String> returnFilePaths = readFile(file);
        String stringTemp = "";
        for (int i = 0; i < returnFilePaths.size(); i++) {
            stringTemp = returnFilePaths.get(i).replace("\\","/");
            returnFilePaths.remove(i);
            returnFilePaths.add(i,stringTemp);
        }
        return returnFilePaths;
    }
}

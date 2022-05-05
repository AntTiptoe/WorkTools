package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;


public class testT {
    public static void main(String[] args) {
        //声明需要比较的文件名    和   路径
        String fileSheng  ="生产的一笔全三级户内转日志.txt";
        String fileYa = "dpzz_dpzz_压测.log";
        String filePath = "C:/Users/92497/Desktop/ceshi/";
        File sf = new File(filePath+"总结.txt");
        try{
            FileOutputStream fos = new FileOutputStream(sf);
            String li = "";
            File file = new File(filePath+fileSheng) ;
            if(file.isFile() && file.exists()){
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"utf-8");
                BufferedReader br  = new BufferedReader(isr);
                String linTxt = null;
                String nextlin = null;
                int num = 0;
                int num1 = 0;
                int num2 = 0;
                while((linTxt = br.readLine()) != null){
                    //System.out.println(linTxt);
                    if(linTxt.indexOf("[")>=0){
                        //System.out.println("我进来了");
                        String time = linTxt.substring(linTxt.indexOf(",",0)+1,linTxt.indexOf(",",0)+4);
                        //System.out.println("time"+time);
                        if(num1!=0){
                            num2 = Integer.parseInt(time) -num1;
                            System.out.println("文件1此次使用的时间为:"+num2);
                            //超过10毫秒写入文件并记录数据
                            if(num2>=10){
                                if(!sf.exists()){
                                    sf.createNewFile();
                                }
                                String wrfile = linTxt+"本条使用时间为:"+num2;
                                System.out.println("此次插入语句为"+wrfile);
                                wrfile+="\r\n";
                                fos.write(wrfile.getBytes());
                            }
                        }
                        num1 = Integer.parseInt(time);
                        //if(nextlin.equals(null)){
                        //}
                        nextlin = linTxt;
                    };
                    li =
                            linTxt.substring(
                                    linTxt.indexOf("[",linTxt.indexOf("[",linTxt.indexOf("[",linTxt.indexOf("[",linTxt.indexOf("[",linTxt.indexOf("[", linTxt.indexOf("[", linTxt.indexOf("[", 0)+1)+1)+1)+1)+1)+1)+1)+1
                                    ,linTxt.indexOf("]",linTxt.indexOf("]",linTxt.indexOf("]",linTxt.indexOf("]",linTxt.indexOf("]",linTxt.indexOf("]", linTxt.indexOf("]", linTxt.indexOf("]", 0)+1)+1)+1)+1)+1)+1)+1)+1
                            );
                    System.out.println(li);
                    //chaxun(filePath+fileYa,li);

                    //String str1 = linTxt.substring(linTxt.indexOf("["), linTxt.indexOf("]")+1);
                    //String str1 [] = linTxt.split("[");
                    //System.out.println(str1);
                    num++;
                    System.out.println("第"+num+"数据"+"当前时间为:"+num1);
                }
                fos.flush();
                fos.close();
            }else{
                System.out.println("文件不存在！");
            }

        }catch(Exception e){
            System.out.println("文件读取失败！");
        }


    }

    //输入查询文件目录文件名    和需要搜索的字符串
    public static int chaxun(String strpath, String str1){
        String linTxt = null;
        File file = new File(strpath) ;
        String linTxt2 = null;
        int num1 = 0;
        int num2 = 0;
        if(file.isFile() && file.exists()){
            try{
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"utf-8");
                BufferedReader br  = new BufferedReader(isr);
                while((linTxt = br.readLine()) != null){
                    if(linTxt.indexOf("[")>=0){
                        if(linTxt.indexOf(str1)>=0){
                            String time = linTxt.substring(linTxt.indexOf(",",0)+1,linTxt.indexOf(",",0)+4);
                            if(num1!=0){
                                num2 = Integer.parseInt(time) -num1;
                                System.out.println("文件2此次使用的时间为:"+num2);
                            }
                            num1 = Integer.parseInt(time);
                        }
                    }
                }
            }catch(Exception e){
                System.out.println("文件读取失败！");
            }

        }


        return 0;

    }
}

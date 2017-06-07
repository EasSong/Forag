package cuit.log.impl;

import cuit.util.ConstantDeclare;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Esong on 2017/5/26.
 * 封装日志文件所有操作
 */
public class LogRandomFileUtil {
    private static final String LOG_FILE_ROOT_PATH = "E:/ForagLogFile/";
    private static LogRandomFileUtil logRandomFileUtil = null;
    private static final long MAX_LOG_FILE_SIZE = 1 * 1024 * 1024;//1M最大日志文件大小

    private LogRandomFileUtil() {
    }

    public static LogRandomFileUtil getInstance() {
        if (logRandomFileUtil == null) {
            logRandomFileUtil = new LogRandomFileUtil();
        }
        return logRandomFileUtil;
    }

    public static int writeUserLog(String logFileName, String logDetails) {
        int codeState = ConstantDeclare.ERROR_WRITE_LOG;

        String fullFilePath = LOG_FILE_ROOT_PATH+"/"+logFileName;
        File file = new File(fullFilePath);
        try {
            // 打开一个可有随机读写的文件
            RandomAccessFile randomFile = new RandomAccessFile(file, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            if (fileLength >= MAX_LOG_FILE_SIZE) {
                System.err.println("Log File is so bigger!");
                createLogFile(fullFilePath+".tmp");
                File file1 = new File(fullFilePath+".tmp");
                RandomAccessFile tempFile = new RandomAccessFile(file1,"rw");
                tempFile.write(randomFile.readLine().getBytes("UTF-8"));//写入日志头
                randomFile.readLine();//过滤掉第一条日志
                while (randomFile.getFilePointer() < fileLength){
                    tempFile.write(randomFile.readLine().getBytes("UTF-8"));
                }
                tempFile.write(logDetails.getBytes("UTF-8"));
                if (file.delete()){
                    System.out.println("Delete original file success!");
                }
                if (file1.renameTo(new File(fullFilePath))){
                    System.out.println("Rename file to: "+fullFilePath);
                }
            }
            else{
                randomFile.seek(fileLength);
                randomFile.write(logDetails.getBytes("UTF-8"));
            }
            randomFile.close();
            codeState = ConstantDeclare.SUCCESS_WRITE_LOG;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return codeState;
    }

    public static ArrayList<String> readUserLog(String logFileName) {
        ArrayList<String> listLogInfo = new ArrayList<>();
        String fullFilePath = LOG_FILE_ROOT_PATH+"/"+logFileName;
        Path writePath = Paths.get(fullFilePath);
        try {
            List<String> lines = Files.readAllLines(writePath);
            for (String line : lines) {
                listLogInfo.add(line);
                System.out.println(line);
            }
        } catch (IOException e) {
            listLogInfo = null;
            e.printStackTrace();
        }


        return listLogInfo;
    }
    private final static String FILE_LOG_HEADER = "time|log-type|obj-type|obj-intro|msg-id|msg-tags\r\n#\r\n";
    public static boolean createLogFile(String logFileName) {
        String fullFilePath = LOG_FILE_ROOT_PATH+"/"+logFileName;
        File file = new File(fullFilePath);
        //判断目标文件所在的目录是否存在
        if (!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            if (!file.getParentFile().mkdirs()) {
                System.out.println("Make file dir failed!");
                return false;
            }
        }
        //创建目标文件
        try {
            if (file.createNewFile()) {
                writeUserLog(fullFilePath,FILE_LOG_HEADER);
                System.out.println("File length:"+file.length());
                return true;
            } else {
                System.out.println("Make file " + fullFilePath + " failed!");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("make file " + fullFilePath + " failed! errorMsg:" + e.getMessage());
            return false;
        }
    }

}

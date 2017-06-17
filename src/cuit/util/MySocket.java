package cuit.util;

import net.sf.json.JSONObject;

import java.io.*;
import java.net.Socket;

/**
 * Created by Esong on 2017/6/7.
 */
public class MySocket {
    public MySocket(){}
    public JSONObject runSocket(String parame){
        JSONObject jsonData = new JSONObject();
        Socket socket = null;
        try {//10.18.48.248  192.168.43.170 192.168.1.116
            socket = new Socket("192.168.1.116",9999);
            OutputStream out = socket.getOutputStream();//字节输出流
            PrintWriter pw =new PrintWriter(out);//将输出流包装成打印流
            pw.write(parame);
            pw.flush();
            socket.shutdownOutput();
            //3、获取输入流，并读取服务器端的响应信息
            InputStream in = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String info = null;
            while((info=br.readLine()) != null){
                System.out.println("Service Information"+info);
                jsonData = JSONObject.fromObject(info);
            }
            //4、关闭资源
            br.close();
            in.close();
            pw.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

}

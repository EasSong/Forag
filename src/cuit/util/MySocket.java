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
            OutputStream out = socket.getOutputStream();//�ֽ������
            PrintWriter pw =new PrintWriter(out);//���������װ�ɴ�ӡ��
            pw.write(parame);
            pw.flush();
            socket.shutdownOutput();
            //3����ȡ������������ȡ�������˵���Ӧ��Ϣ
            InputStream in = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String info = null;
            while((info=br.readLine()) != null){
                System.out.println("Service Information"+info);
                jsonData = JSONObject.fromObject(info);
            }
            //4���ر���Դ
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

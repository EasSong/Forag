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
        try {
            socket = new Socket("10.18.48.248",9999);
            OutputStream os = socket.getOutputStream();//�ֽ������
            PrintWriter pw =new PrintWriter(os);//���������װ�ɴ�ӡ��
            pw.write(parame);
            pw.flush();
            socket.shutdownOutput();
            //3����ȡ������������ȡ�������˵���Ӧ��Ϣ
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            while((info=br.readLine()) != null){
                System.out.println("Hello,���ǿͻ��ˣ�������˵��"+info);
                jsonData = JSONObject.fromObject(info);
            }
            //4���ر���Դ
            br.close();
            is.close();
            pw.close();
            os.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

}

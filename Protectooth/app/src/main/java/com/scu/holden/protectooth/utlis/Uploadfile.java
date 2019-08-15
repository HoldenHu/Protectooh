package com.scu.holden.protectooth.utlis;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mirrorssssssss on 11/1/17.
 */

public class Uploadfile {

    /**

     * @category 上传文件至Server的方法

     * @param uploadUrl 上传路径参数

     * @param uploadFilePath 文件路径

     * @author ylbf_dev

     */

    public static void uploadFile(String uploadUrl,String uploadFilePath) {

        String end = "\r\n";

        String twoHyphens = "--";

        String boundary = "******";

        try {

            URL url = new URL(uploadUrl);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setDoInput(true);

            httpURLConnection.setDoOutput(true);

            httpURLConnection.setUseCaches(false);

            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");

            httpURLConnection.setRequestProperty("Charset", "UTF-8");

            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);



            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + end);

            dos.writeBytes("Content-Disposition: form-data; name=\"img\"; filename=\"test222.jpg\"" + end);

            dos.writeBytes(end);

//             文件通过输入流读到Java代码中-++++++++++++++++++++++++++++++`````````````````````````

            FileInputStream fis = new FileInputStream(uploadFilePath);

            byte[] buffer = new byte[8192]; // 8k

            int count = 0;

            while ((count = fis.read(buffer)) != -1) {

                dos.write(buffer, 0, count);



            }

            fis.close();

            System.out.println("file send to server............");

            dos.writeBytes(end);

            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);

            dos.flush();

            System.out.println("file send to server over");



            // 读取服务器返回结果

            InputStream is = httpURLConnection.getInputStream();

            InputStreamReader isr = new InputStreamReader(is, "utf-8");

            System.out.println("over1");

            BufferedReader br = new BufferedReader(isr);

            System.out.println("over2");

            String result = br.readLine();

            System.out.println("over3");

//                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

            System.out.println("over4");

            System.out.print(result);

            is.close();

            dos.close();

//            mLog.d(result);





        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}

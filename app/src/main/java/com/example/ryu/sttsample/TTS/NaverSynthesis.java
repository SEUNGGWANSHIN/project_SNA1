/*
    Text To Speech
    String을 parameter로 받아 api로 전송하고 결과값을 받아 mp3파일 생성
    현재 speech 폴더에 같은 파일명으로 덮어쓰고 바로 play
*/
package com.example.ryu.sttsample.TTS;

import android.media.MediaPlayer;
import android.os.Environment;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Ryu on 2017-07-26.
 */

public class NaverSynthesis {
    public static void main(String args) {
        String clientId = "pd5f0tIAI_AohgWvr3Oo";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "vGAZUdBXrj";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode(args, "UTF-8"); // 13자
            String apiURL = "https://openapi.naver.com/v1/voice/tts.bin";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "speaker=clara&speed=0&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                File dir = new File(Environment.getExternalStorageDirectory()+"/","Speech");
                if( !dir.exists() )
                    dir.mkdirs();

                InputStream is = con.getInputStream();
                int read = 0;
                byte[] bytes = new byte[1024];
                // 같은 이름으로 mp3 파일 생성
                String tempname = "TTStest";
                File f = new File(Environment.getExternalStorageDirectory()+"/Speech/"+tempname + ".mp3");
                f.createNewFile();
                OutputStream outputStream = new FileOutputStream(f);
                while ((read =is.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
                is.close();

                String path = Environment.getExternalStorageDirectory()+"/Speech/"+tempname+".mp3";
                MediaPlayer player = new MediaPlayer();
                player.setDataSource(path);
                player.prepare();
                player.start();

            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

package test.main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StringTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String path = "http://i.tianqi.com/index.php?c=code&py=yuzhouqu";
        try {
            URL url = new URL(path);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int resultCode = 0;
                    try {
                        resultCode = conn.getResponseCode();

                        if (resultCode == 200) {
                            InputStream is = conn.getInputStream();
                            String result = readInputStream(is);
                            
                            result = result.substring(result.indexOf("<body"), result.indexOf("</body>"));
                            String[] soures = result.trim().split("<");
                            System.out.println(soures.length);
                            List<String> msg = new ArrayList<>();
                            for (String s : soures) {
                            	String[] ss = s.trim().split(">");
                            	if (ss.length > 1) {
                            		if (ss[1].trim().length() > 0) {
										//System.out.println(s.split(">")[1]);
                            			msg.add(s.split(">")[1]);
                            		}
								}
                            	
                            	if (s.indexOf("title") > 0) {
                            		//System.out.println(s.substring(s.lastIndexOf("=") + 2, s.lastIndexOf("\"")));
                            		msg.add(s.substring(s.lastIndexOf("=") + 2, s.lastIndexOf("\"")));
								}
                            }
                            
                            for (int i = 0; i < msg.size(); i++) {
								if (msg.get(i).contains("今天")) {
									System.out.println("当日天气 ： " + msg.get(i-1));
									System.out.println("当日温度 ： " + msg.get(i+1));
								}
							}
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static String readInputStream(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            is.close();
            baos.close();
            byte[] result = baos.toByteArray();
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

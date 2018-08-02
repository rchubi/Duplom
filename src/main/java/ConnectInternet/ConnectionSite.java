package ConnectInternet; /**
 * Created by Roman on 13.05.2017.
 */

import java.io.*;
import java.net.URLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConnectionSite {

    ConnectionSite(String pathFile) {
        BufferedReader in = null;
        try {

            in = new BufferedReader(new FileReader(pathFile));
            for(;;) {
                String str = in.readLine();
                if ( str == null )
                    break;
                SendWord(str);
                System.out.println(str);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if ( in != null ) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
    }


    public void SendWord(String text) throws IOException {
        String link = "http://upodn.com/phon.php";
        URL url = new URL(link);
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("intext=", text);
        params.put("ipa=", 0);

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> p : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(p.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(p.getValue()), "UTF-8"));
        }
        String urlParameters = postData.toString();
        URLConnection conn = url.openConnection();

        conn.setDoOutput(true);

        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

        writer.write(urlParameters);
        writer.flush();

        String result = "";
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        while ((line = reader.readLine()) != null) {
            result += line;
        }
        writer.close();
        reader.close();
        System.out.println(result);
    }

    public static void main (String[] args) {
        String path ="D:/friend.txt";
        ConnectionSite connectionSite = new ConnectionSite(path);
    }
}
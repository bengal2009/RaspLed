/**
 * Created by Lin on 2015/6/24.
 */
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lin on 2015/6/4.
 * http://tool.chinaz.com/Tools/URLEncode.aspx
 */
public class TTS {
    //    private static final String serverURL = "http://tsn.baidu.com/text2audio";
    //private static String serverURL = "http://tsn.baidu.com/text2audio?lan=zh&cuid=6131442&ctp=1&tok=";
    private static StringBuilder serverURL=new StringBuilder();
    private static String token = "";
    private static final String testFileName = "sound.mp3";
    //put your own params here
    private static final String apiKey = "bd6kSzmqtlUaG1SEjbqR4R28";
    private static final String secretKey = "5eaad29500bcbd35c84bf6bfac5e9190";
    private static final String cuid = "6131442";

    public static void main(String[] args) throws Exception {
        getToken();
        serverURL.append("http://tsn.baidu.com/text2audio?lan=zh&cuid=6131442&vol=9&ctp=1&tok=");
        serverURL.append(token + "&tex=");
        //%e7%8e%b0%e5%9c%a8%e6%97%b6%e9%97%b4
//        System.out.println(URLEncoder.encode("??b??", "GBK"));
        serverURL.append(URLEncoder.encode("??b??", "utf-8"));
//        serverURL =serverURL+"&tex="+  URLEncoder.encode("??b??", "GBK"); ;
//        serverURL =serverURL+"&tex=this is a book";
       /* StringBuilder sb=new StringBuilder();
        sb.append(sb);
        sb.append("??b??");

*/
//        System.out.println(serverURL.toString());
        method3();
        ReadMP3();
    }

    private static void method3() throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL("http://tsn.baidu.com/text2audio").openConnection();
        String USER_AGENT = "Mozilla/5.0";
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
//        System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
        String urlParameters =
//                "tex=" + URLEncoder.encode("?A?n", "BIG5") +
                "tex="  +"%e4%bb%8a%e5%a4%a9%e6%98%af"+
                        URLEncoder.encode(dateFormat1.format(date), "utf-8")+
                        /*"tex="  +"%e7%8e%b0%e5%9c%a8%e6%97%b6%e9%97%b4"+
                                URLEncoder.encode(dateFormat.format(date), "utf-8") +*/
//                "tex="+ConvUrl("??b??")+
                        "&cuid=" + cuid +
                        "&ctp=1"+"&tok="+token+"&lan=zh";
//        tex=?A?n&cuid=xxx&ctp=1&tok=24.c5e6897f4ff7b0af2303baf572fcc56e.2592000.1428462020.282335-288453
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Language", "utf-8");

//        System.out.println(urlParameters.toString());

//        System.out.println("Tok:" + token + " cuid:" + cuid);
        // add request header
//        conn.setRequestMethod("POST");
        /*conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type","application/json; charset=utf-8");
*/
        conn.setDoInput(true);
        conn.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(urlParameters.toString());
        wr.flush();
        wr.close();
        if (conn.getResponseCode() != 200) {
            // request error
            System.out.println(conn.getResponseCode());
        }
        InputStream is = conn.getInputStream();
        FileOutputStream fos = new FileOutputStream(new File(testFileName));
        long length=0;
        byte[] buffer = new byte[1024];



        while((length=is.read(buffer,0,1024))>0){
//            System.out.println(length);
            fos.write(buffer,0,(int)length);

        }


        fos.flush();
        fos.close();
        /*DataInputStream dis = new DataInputStream(conn.getInputStream());
        FileOutputStream fos = new FileOutputStream(new File(testFileName));

        long length=0;
        byte[] buffer = new byte[1024];
        while((length=dis.read(buffer,0,1024))>0){
            fos.write(buffer,0,(int)length);

        }
        fos.flush();
        fos.close();*/
//        printResponse(conn);
    }
    private static void method1() throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(serverURL.toString()).openConnection();
        String USER_AGENT = "Mozilla/5.0";
        conn.setRequestProperty("User-Agent", USER_AGENT);

//        File mp3File = new File(testFileName);
        // construct params
        JSONObject params = new JSONObject();
        params.put("tex", "Hello");
        params.put("cuid", cuid);
        params.put("ctp", "1");
//        params.put("lan", "zh");
        params.put("tok", token);


//        System.out.println("Tok:" + token + " cuid:" + cuid);
        // add request header
//        conn.setRequestMethod("POST");
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "audio/mp3; charset=utf-8");

        conn.setDoInput(true);
        conn.setDoOutput(true);
        /*DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        System.out.println(params.toString());
        wr.writeBytes(params.toString());
        wr.flush();
        wr.close();*/
        if (conn.getResponseCode() != 200) {
            // request error
            System.out.println(conn.getResponseCode());
        }
        InputStream is = conn.getInputStream();
        FileOutputStream fos = new FileOutputStream(new File(testFileName));
        long length=0;
        byte[] buffer = new byte[1024];



        while((length=is.read(buffer,0,1024))>0){
//            System.out.println(length);
            fos.write(buffer,0,(int)length);

        }


        fos.flush();
        fos.close();
        /*DataInputStream dis = new DataInputStream(conn.getInputStream());
        FileOutputStream fos = new FileOutputStream(new File(testFileName));

        long length=0;
        byte[] buffer = new byte[1024];
        while((length=dis.read(buffer,0,1024))>0){
            fos.write(buffer,0,(int)length);

        }
        fos.flush();
        fos.close();*/
//        printResponse(conn);
    }
    private static void method2() throws Exception {
        File pcmFile = new File(testFileName);
        HttpURLConnection conn = (HttpURLConnection) new URL(serverURL
                + "?cuid=" + cuid + "&token=" + token).openConnection();

        // add request header
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "audio/amr; rate=8000");

        conn.setDoInput(true);
        conn.setDoOutput(true);

        // send request
       /* DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.write(WriteFile(pcmFile));
        wr.flush();
        wr.close();*/
        DataInputStream dis = new DataInputStream(conn.getInputStream());
        FileOutputStream fos = new FileOutputStream(new File(testFileName) );

        long length=0;
        byte[] buffer = new byte[1024];
        while((length=dis.read(buffer,0,1024))>0){
            fos.write(buffer, 0, (int) length);

        }
        fos.flush();
        fos.close();
        printResponse(conn);
    }
    private static void getToken() throws Exception {
        String getTokenURL = "https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials" +
                "&client_id=" + apiKey + "&client_secret=" + secretKey;
        HttpURLConnection conn = (HttpURLConnection) new URL(getTokenURL).openConnection();
        token = new JSONObject(printResponse(conn)).getString("access_token");
//        System.out.println(token);
    }
    private static String printResponse(HttpURLConnection conn) throws Exception {
        if (conn.getResponseCode() != 200) {
            // request error
            return "";
        }
        InputStream is = conn.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
//        System.out.println(new JSONObject(response.toString()).toString(4));
        return response.toString();
    }
    private static void WriteFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            is.close();
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
    }
    private static String ConvUrl(String chinese)
    {
//        String chinese = "????";//java?????
        try {
            String gbkChinese = new String(chinese.getBytes("gbk"), "ISO-8859-1");//????gbk??
            String unicodeChinese = new String(gbkChinese.getBytes("ISO-8859-1"), "GBK");//java?????
//            System.out.println("Test:"+URLEncoder.encode(unicodeChinese,"utf-8"));//????

            return URLEncoder.encode(unicodeChinese,"utf-8");
        }
        catch (Exception E)
        {
            System.out.println(E.toString());
        }
        return null;
    }
    private static  void ReadMP3()
    {

        try {
            Runtime rt = Runtime.getRuntime();
            //Process pr = rt.exec("cmd /c dir");
            Process pr = rt.exec("omxplayer sound.mp3");

            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            String line=null;

            while((line=input.readLine()) != null) {
                System.out.println(line);
            }

            int exitVal = pr.waitFor();
            System.out.println("Exited with error code "+exitVal);

        } catch(Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }


}


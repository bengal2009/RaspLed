/**
 * Created by Lin on 2015/6/24.
 */

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//import net.sf.json.JSONException;

/**
 * Created by Lin on 2015/6/4.
 * http://tool.chinaz.com/Tools/URLEncode.aspx
 */
public class SpeakWeather {
    //    private static final String serverURL = "http://tsn.baidu.com/text2audio";
    //private static String serverURL = "http://tsn.baidu.com/text2audio?lan=zh&cuid=6131442&ctp=1&tok=";
    private static StringBuilder serverURL=new StringBuilder();
    private static String token = "";
    private static final String testFileName = "sound.mp3";
    //put your own params here
    private static final String apiKey = "bd6kSzmqtlUaG1SEjbqR4R28";
    private static final String secretKey = "5eaad29500bcbd35c84bf6bfac5e9190";
    private static final String cuid = "6131442";

    public static void main(String[] args){
       /* String TempStr;
        TempStr=GetBaiduWeather();
        getToken();
        serverURL.append("http://tsn.baidu.com/text2audio?lan=zh&cuid=6131442&vol=9&ctp=1&tok=");
        serverURL.append(token + "&tex=");
        serverURL.append(URLEncoder.encode(TempStr, "utf-8"));
        System.out.println(TempStr.toString());
        System.out.println(serverURL.toString());*/
//        DispTime();
//        ReadMP3("now.mp3");
//        ReadMP3("sound.mp3");
    }
    private static String GetBaiduWeather() throws IOException ,NullPointerException
    {
        String Ctiyid;
        String Wetstr="";
        URLConnection connectionData;
        StringBuilder sb= new StringBuilder();
        BufferedReader br;// ???data??u?y
        net.sf.json.JSONObject jsonData;
        net.sf.json.JSONObject info;
        URL url = new URL("http://api.map.baidu.com/telematics/v3/weather?location=%E4%B8%8A%E6%B5%B7&output=json&ak=Ib0QNBwBbmsdYRq2EHMI18jp");
        connectionData = url.openConnection();
        connectionData.setConnectTimeout(1000);
        try {
            br = new BufferedReader(new InputStreamReader(
                    connectionData.getInputStream(), "UTF-8"));
//            sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null)
                sb.append(line);
        } catch (SocketTimeoutException e) {
            System.out.println("????W?");
        } catch (FileNotFoundException e) {
            System.out.println("?[????X?");
        }
        String datas = sb.toString();
        jsonData = net.sf.json.JSONObject.fromObject(datas);
        if (jsonData.getInt("error") != 0) {
            return "none";
        }
        String date = jsonData.getString("date");
        System.out.println(date);
        JSONArray results = jsonData.getJSONArray("results");
        net.sf.json.JSONObject results0 = results.getJSONObject(0);
        String location = results0.getString("currentCity");
        System.out.println(location);
        int pmTwoPointFive;

        if (results0.getString("pm25").isEmpty()) {
            pmTwoPointFive = 0;
        } else {
            pmTwoPointFive = results0.getInt("pm25");
        }
        System.out.println(pmTwoPointFive);
        try {
            JSONArray weather_data = results0.getJSONArray("weather_data");
            net.sf.json.JSONObject index0 = weather_data.getJSONObject(0);//穿衣
            String DATESTR,FORCTEMP,WIND,WEATHER,CURTEMP;
            DATESTR=index0.getString("date");
            System.out.println(DATESTR);
            FORCTEMP=index0.getString("temperature");
            System.out.println(FORCTEMP);
            WIND=index0.getString("wind");
            System.out.println(WIND);
            WEATHER=index0.getString("weather");
            System.out.println(WEATHER);
//            周三 08月27日 (??：29℃)","
            Integer StartNum,EndNum;
//            StartNum=DATESTR.indexOf("：");
            EndNum=DATESTR.indexOf(")");
            CURTEMP=DATESTR.substring(EndNum - 3, EndNum);
            Wetstr="?在外面?度:"+CURTEMP+"今天?度"+FORCTEMP+" "+WEATHER;
//            System.out.println(Wetstr);
            return Wetstr;


        } catch (JSONException jsonExp) {
            System.out.println(jsonExp.toString());
        }
        return Wetstr;
    }
    private static void DispTime() throws Exception {

        String USER_AGENT = "Mozilla/5.0";
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String Hourstr[]={"9:00","10:00","11:00","12:00","13:00","14:00",
                "15:00","16:00","17:00","18:00"};
        String Filename;
//        System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48

        for(Integer i=0;i<Hourstr.length;i++) {
            HttpURLConnection conn = (HttpURLConnection) new URL("http://tsn.baidu.com/text2audio").openConnection();
            String[] aArray =Hourstr[i].split(":");
            Filename=aArray[0]+".mp3";
            System.out.println(Filename);
           String urlParameters =
                           "tex=" +
                            URLEncoder.encode(Hourstr[i], "utf-8") +
                            "&cuid=" + cuid +
                            "&ctp=1" + "&tok=" + token + "&lan=zh";
//        tex=?A?n&cuid=xxx&ctp=1&tok=24.c5e6897f4ff7b0af2303baf572fcc56e.2592000.1428462020.282335-288453
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Language", "utf-8");
           conn.setRequestProperty("Content-Type","application/json; charset=utf-8");
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
            FileOutputStream fos = new FileOutputStream(new File(Filename));
            long length = 0;
            byte[] buffer = new byte[1024];


            while ((length = is.read(buffer, 0, 1024)) > 0) {
//            System.out.println(length);
                fos.write(buffer, 0, (int) length);

            }


            fos.flush();
            fos.close();
            conn.disconnect();
        }
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
    private static  void ReadMP3(String FileName)
    {

        try {
            Runtime rt = Runtime.getRuntime();
            //Process pr = rt.exec("cmd /c dir");
            Process pr = rt.exec("omxplayer "+FileName);

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


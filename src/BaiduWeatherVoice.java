/**
 * Created by Lin on 2015/6/26.
 */

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.*;


public class BaiduWeatherVoice {
    String Ctiyid;
    private static StringBuilder serverURL=new StringBuilder();
    private static String token = "";
    private static final String testFileName = "weather.mp3";
    //put your own params here
    private static final String apiKey = "bd6kSzmqtlUaG1SEjbqR4R28";
    private static final String secretKey = "5eaad29500bcbd35c84bf6bfac5e9190";
    private static final String cuid = "6131442";




    private static  String ReadBaidu() throws IOException
    {
        URLConnection connectionData;
        JSONObject jsonData;
        JSONObject info;
        String Wetstr="";
        StringBuilder sb = new StringBuilder();
        BufferedReader br;// ?��data?�u�y
        try {
        URL url = new URL("http://api.map.baidu.com/telematics/v3/weather?location=%E4%B8%8A%E6%B5%B7&output=json&ak=Ib0QNBwBbmsdYRq2EHMI18jp");
        connectionData = url.openConnection();
        connectionData.setConnectTimeout(1000);

            br = new BufferedReader(new InputStreamReader(
                    connectionData.getInputStream(), "UTF-8"));

            String line = null;
            while ((line = br.readLine()) != null)
                sb.append(line);
        } catch (SocketTimeoutException e) {
            System.out.println("?���W?");
        } catch (FileNotFoundException e) {
            System.out.println("�[?���X?");
        } catch (IOException e) {
            System.out.println("�[?���X?");
        }

        String datas = sb.toString();
        jsonData = JSONObject.fromObject(datas);
        if (jsonData.getInt("error") != 0) {
            return "error";
        }
        String date = jsonData.getString("date");
//        System.out.println(date);
        JSONArray results = jsonData.getJSONArray("results");
        JSONObject results0 = results.getJSONObject(0);
        String location = results0.getString("currentCity");
//        System.out.println(location);
        int pmTwoPointFive;

        if (results0.getString("pm25").isEmpty()) {
            pmTwoPointFive = 0;
        } else {
            pmTwoPointFive = results0.getInt("pm25");
        }
//        System.out.println(pmTwoPointFive);
        try {
            JSONArray weather_data = results0.getJSONArray("weather_data");
            JSONObject index0 = weather_data.getJSONObject(0);//穿衣
            String DATESTR,FORCTEMP,WIND,WEATHER,CURTEMP;
            DATESTR=index0.getString("date");
//            System.out.println(DATESTR);
            FORCTEMP=index0.getString("temperature");
//            System.out.println(FORCTEMP);
            WIND=index0.getString("wind");
//            System.out.println(WIND);
            WEATHER=index0.getString("weather");
//            System.out.println(WEATHER);
//            周三 08月27日 (实时：29℃)","
            Integer StartNum,EndNum;
//            StartNum=DATESTR.indexOf("：");
            EndNum=DATESTR.indexOf(")");
            CURTEMP=DATESTR.substring(EndNum - 3, EndNum);
            Wetstr="现在外面温度:"+CURTEMP+",今天温度"+FORCTEMP+","+WEATHER;
//            System.out.println(Wetstr);


        } catch (JSONException jsonExp) {
            System.out.println(jsonExp.toString());
        }
                return Wetstr;
    }


    private static void SpeakWeather(String s1)
    {
        try {
            getToken();
            HttpURLConnection conn = (HttpURLConnection) new URL("http://tsn.baidu.com/text2audio").openConnection();
            String USER_AGENT = "Mozilla/5.0";
            String urlParameters =
                    "tex="  +
                            URLEncoder.encode(s1, "utf-8")+
                            "&cuid=" + cuid +
                            "&ctp=1"+"&tok="+token+"&lan=zh&vol=9";
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Language", "utf-8");


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


        }catch (Exception E)
        {

        }


    }
    private static void getToken() throws Exception {
        String getTokenURL = "https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials" +
                "&client_id=" + apiKey + "&client_secret=" + secretKey;
        HttpURLConnection conn = (HttpURLConnection) new URL(getTokenURL).openConnection();
        token = new org.json.JSONObject(printResponse(conn)).getString("access_token");
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
    public static void main(String[] args) {
        try {
            String s1 = ReadBaidu();
//            System.out.println(s1);
            SpeakWeather(s1);
//            ReadMP3("/home/pi/prog/javatest/out/production/RaspLed/weather.mp3");
        } catch (Exception e) {
            System.out.println("�[?���X?");
        }
    }
    private static  void ReadMP3(String FileName)
    {

        try {
            Runtime rt = Runtime.getRuntime();
            //Process pr = rt.exec("cmd /c dir");
            Process pr = rt.exec("omxplayer "+FileName);

            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            String line=null;

            /*while((line=input.readLine()) != null) {
                System.out.println(line);
            }*/

            int exitVal = pr.waitFor();
//            System.out.println("Exited with error code "+exitVal);

        } catch(Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
}

/**
 * Created by Lin on 2015/6/26.
 */

import net.sf.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;


public class Weather {
    String Ctiyid;
    URLConnection connectionData;
    StringBuilder sb;
    BufferedReader br;// ?��data?�u�y
    JSONObject jsonData;
    JSONObject info;

    //?���a�I�ѪR��??
    String city ;// ����
    String date_y;//���
    String week ;// �P��
    String fchh ;// ?��??

    String weather1 ;// ��?1��6�Ѥ��a��?
    String weather2 ;
    String weather3 ;
    String weather4 ;
    String weather5 ;
    String weather6 ;

    String wind1;//��?1��6��??
    String wind2;
    String wind3;
    String wind4;
    String wind5;
    String wind6;

    String fl1;//?����?
    String fl2;
    String fl3;
    String fl4;
    String fl5;
    String fl6;


    String temp1 ;// ��?1��6�Ѫ��a?
    String temp2 ;
    String temp3 ;
    String temp4 ;
    String temp5 ;
    String temp6 ;

    String index;// ���Ѫ�����?
    String index_uv ;// ���~��?
    String index_tr ;// �ȴ��?
    String index_co ;// �����?
    String index_cl ;// ��?��?
    String index_xc;//�~?��?
    String index_d;//���a??����?



    public Weather(String Cityid) throws IOException ,NullPointerException{
        // �ѪR����ip�a�}

        this.Ctiyid = Cityid;
        // ?�������a�H�x��API
        URL url = new URL("http://m.weather.com.cn/atad/" + Ctiyid + ".html");
        connectionData = url.openConnection();
        connectionData.setConnectTimeout(1000);
        try {
            br = new BufferedReader(new InputStreamReader(
                    connectionData.getInputStream(), "UTF-8"));
            sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null)
                sb.append(line);
        } catch (SocketTimeoutException e) {
            System.out.println("?���W?");
        } catch (FileNotFoundException e) {
            System.out.println("�[?���X?");
        }
        String datas = sb.toString();
        jsonData = JSONObject.fromObject(datas);
        info = jsonData.getJSONObject("weatherinfo");

        city = info.getString("city").toString();
        week =  info.getString("week").toString();
        date_y = info.getString("date_y").toString();
        fchh = info.getString("fchh").toString();
        //1��6�Ѫ����a
        weather1 =  info.getString("weather1").toString();
        weather2 =  info.getString("weather2").toString();
        weather3 =  info.getString("weather3").toString();
        weather4 =  info.getString("weather4").toString();
        weather5 =  info.getString("weather5").toString();
        weather6 =  info.getString("weather6").toString();
        //1��6�Ѫ��a?
        temp1 = info.getString("temp1").toString();
        temp2 = info.getString("temp2").toString();
        temp3 = info.getString("temp3").toString();
        temp4 = info.getString("temp4").toString();
        temp5 = info.getString("temp5").toString();
        temp6 = info.getString("temp6").toString();
        //1��6�Ѫ�??
        wind1 = info.getString("wind1").toString();
        wind2 = info.getString("wind2").toString();
        wind3 = info.getString("wind3").toString();
        wind4 = info.getString("wind4").toString();
        wind5 = info.getString("wind5").toString();
        wind6 = info.getString("wind6").toString();
        //1��6�Ѫ�?�t
        fl1 = info.getString("fl1").toString();
        fl2 = info.getString("fl2").toString();
        fl3 = info.getString("fl3").toString();
        fl4 = info.getString("fl4").toString();
        fl5 = info.getString("fl5").toString();
        fl6 = info.getString("fl6").toString();
        //�U�����a��?
        index = info.getString("index").toString();
        index_uv = info.getString("index_uv").toString();
        index_tr = info.getString("index_tr").toString();
        index_co= info.getString("index_co").toString();
        index_cl = info.getString("index_cl").toString();
        index_xc = info.getString("index_xc").toString();
        index_d =  info.getString("index_d").toString();
        System.out.println(city);
    }
    public static void main(String[] args) {
        try {
            new Weather("101020100"); //  101270803�N�O�A�������N?
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Created by Lin on 2015/7/8.
     */
    public static class test {
        public static void main(String[] args) throws Exception {
            System.out.println("TEST");
        }
    }
}

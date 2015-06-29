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


public class BaiduWeather {
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



    public BaiduWeather(String Cityid) throws IOException ,NullPointerException{
        // �ѪR����ip�a�}

        this.Ctiyid = Cityid;
        // ?�������a�H�x��API
        URL url = new URL("http://api.map.baidu.com/telematics/v3/weather?location=%E4%B8%8A%E6%B5%B7&output=json&ak=Ib0QNBwBbmsdYRq2EHMI18jp");
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
        info = jsonData.getJSONObject("results");
        city = info.getString("currentCity").toString();

        System.out.println(city);
    }
    public static void main(String[] args) {
        try {
            new BaiduWeather("101020100"); //  101270803�N�O�A�������N?
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

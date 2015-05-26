/**
 * Created by Lin on 2015/5/26.
 */
import com.pi4j.io.gpio.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * ?�{����?�����W��GPIO?��
 * @author �Q�ڤ���
 */
public class YeeLed {
    GpioController gpio;
    GpioPinDigitalOutput pin;
    public static void main(String s[]){
        //�U����?���N�O�A�bYeelink��??��??URL
        String urlStr = "http://api.yeelink.net/v1.0/device/21817/sensor/38496/datapoints";

        YeeLed cl = new YeeLed();
        cl.gpio = GpioFactory.getInstance();
        cl.pin = cl.gpio.provisionDigitalOutputPin

                (RaspiPin.GPIO_01, "LED", PinState.HIGH);
        boolean current = cl.getStatus(urlStr);
        cl.setLedStatus(current);
        while(true){
            try{
                if(cl.getStatus(urlStr) != current){
                    current = cl.getStatus(urlStr);
                    cl.setLedStatus(current);
                }
                Thread.sleep(2000);
            }catch(Exception e){e.printStackTrace();}
        }
    }
    public boolean getStatus(String urlStr){
        URL url;boolean on = false;
        try {
            url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection)

                    url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = conn.getInputStream();
            BufferedReader rd = new BufferedReader(new

                    InputStreamReader(in));
            StringBuilder tempStr = new StringBuilder();
            while (rd.read() != -1) {
                tempStr.append(rd.readLine());
            }
            System.out.print("--> �A?���W?�P�����H���G");

            String status = tempStr.substring

                    (tempStr.lastIndexOf(":")+1, tempStr.length()-1);
            System.out.println(status);
            on = status.equals("1")? true:false;
            System.out.println(on);
            return on;
        } catch (IOException e) {
            e.printStackTrace();
        }return on;
    }
    public void setLedStatus(boolean sta){
        if(sta){
            pin.low();
            //�]?�ڪ�??���O�C?������
            System.out.println("--> ��sGPIO��??: ?");
        }else {
            pin.high();
            System.out.println("--> ��sGPIO��??: ?");
        }

    }
}
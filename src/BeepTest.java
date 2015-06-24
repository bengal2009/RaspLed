import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;
/**
 * Created by Lin on 2015/6/24.
 */
public class BeepTest {


    public static void main(String args[]) throws InterruptedException {
        final GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalOutput Beep1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07);
        for(Integer i=0;i<5;i++) {
            Beep1.high();
            Thread.sleep(100);
            Beep1.low();
            Thread.sleep(100);
        }
    }
}

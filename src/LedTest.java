import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Created by Lin on 2015/6/24.
 */
public class LedTest {


    public static void main(String args[]) throws InterruptedException {
        final GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalOutput Led1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_09);
        Led1.blink(500,15000);


    }
}

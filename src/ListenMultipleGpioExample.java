/**
 * Created by Lin on 2015/7/3.
 */
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 * This example code demonstrates how to setup a listener
 * for GPIO pin state changes on the Raspberry Pi.
 *
 * @author Robert Savage
 */
public class ListenMultipleGpioExample {

    public static void main(String args[]) throws InterruptedException {

        System.out.println("<--Pi4J--> GPIO Listen Example ... started.");

        // create GPIO controller
        final GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalOutput Beep1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07);
        // create GPIO listener
        GpioPinListenerDigital listener  = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                if(event.getState().toString()=="LOW") {
                    System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                    try {
                        Beep1.high();
                        Thread.sleep(100);
                        Beep1.low();
                    }
                    catch (Exception E)
                    {

                    }
                }
            }
        };

        // provision gpio input pins with its internal pull down resistor enabled
        GpioPinDigitalInput[] pins = {
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_04,PinPullResistance.PULL_UP),
                /*gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_DOWN),
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN),
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_DOWN),
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN),
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_DOWN),
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_06, PinPullResistance.PULL_DOWN),
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_07, PinPullResistance.PULL_DOWN),*/
        };

        // create and register gpio pin listener
        gpio.addListener(listener, pins);

        System.out.println(" ... complete the GPIO circuit and see the listener feedback here in the console.");

        // keep program running until user aborts (CTRL-C)
        for (;;) {
            Thread.sleep(500);
        }

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        // gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller
    }
}
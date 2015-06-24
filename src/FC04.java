/**
 * Created by Lin on 2015/6/11.
 */

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
public class FC04 {
    public static void main(String args[]) throws InterruptedException {
        System.out.println("<--Pi4J--> GPIO Listen Example ... started.");

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_UP);

        // create and register gpio pin listener
        myButton.addListener(new GpioPinListenerDigital() {

                @Override
                public void handleGpioPinDigitalStateChangeEvent (GpioPinDigitalStateChangeEvent event) {
                    // display pin state on console

//                    System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                    if(event.getState()== PinState.HIGH) {
                        System.out.println("Voice up!");
                        try {
                            Thread.sleep(5000);
                        } catch (Exception E) {
                        }
                    }


                }
        });

        System.out.println(" ... complete the GPIO #02 circuit and see the listener feedback here in the console.");

        // keep program running until user aborts (CTRL-C)
        for (;;) {
            Thread.sleep(500);
        }

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        // gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller
    }
}

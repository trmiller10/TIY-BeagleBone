import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.gpio.AnalogInput;
import org.bulldog.core.gpio.Pwm;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.devices.led.Led;

/**
 * Touch-sensitive Dimmer controls brightness of LED
 *
 * Note: This flexible potentiometer needs 2 10K ohm resistors on the power and grounding lines, and a +100k ohm resistor on the
 * input line.  This seems to heighten the resistance sensitivity of the potentiometer and improve the quality of the readings
 * it outputs (leading to improved performance for the LED dimmer) as well as limit the amount of power flowing through the device.
 */
public class TouchDimmer {

    public static void main(String[] args) throws InterruptedException{

        //create board instance
        Board board = Platform.createBoard();

        //create PWM class, assign it
        Pwm pwm = board.getPin(BBBNames.P8_13).as(Pwm.class);

        //create LED instance that incorporates PWM
        Led led = new Led(pwm);

        //create softpot analog input instance, assign it to analog input pin, map that to analog input class
        AnalogInput touchSensor = board.getPin(BBBNames.P9_40).as(AnalogInput.class);

        //while the program is running indefinitely
        while(true) {

            //create double variable 'touch' and have it read the input from touchSensor
            double touch = touchSensor.read();

            //print readings to terminal
            System.out.println(touch);
            //set the LED's brightness determined by the touch sensor readings
            led.setBrightness(touch);

            //tell the program to limit the amount of data readings it performs
            Thread.sleep(50);
        }

    }

}

# Cry "Havoc," And Let Slip the Dogs of Hardware

## Touch Sensor LED

For our project, we used a soft potentiometer ("touch sensor") to control the brighness and dimness of a LED light with a BeagleBone Black and Java. The potentiometer is connected to analog power, ground and input, and the LED is connected to a pulse width modulated (PWM) output and a digital ground.

## Assembly

The LED output can be plugged directly into the BeagleBone Black. P8_13 offers PWM output so the LED can emulate analog behavior. P8_1 offers a digital ground to complete the circuit.

The touch sensor has three pins - one for power, one to ground, and one to send analog input. The power should go to the BeagleBone Black's only 1.8V analog power source, P9_32, and the ground should go to the only analog ground source, P9_34.

The analog input can go to any of the BeagleBone Black's 7 analog input pins - in our case, we used P9_40. 

The product description for our particular touch sensor (found here: https://www.adafruit.com/products/178) contains instructions on how to properly connect the power, ground, and input pins to the BeagleBone Black utilizing three resistors (two 10k Ω resistors and a 100k Ω resistor).  It also explicitly advises against connecting the pins to ground and power directly.  Our power and ground connections each used a 10k Ω resistor between the touch sensor and the BeagleBone Black. The product instruction for the analog input connection called for a 100k Ω resistor; we didn't have one, so to play it safe we used a 330k Ω resistor. This (surprisingly to us!) made our analog input much more responsive.

This diagram shows how we made all of our connections (the mystery part represents our soft potentiometer):

![Fritzing Diagram](http://i.imgur.com/OamZ10R.png)

And here is a picture of what it looks like in reality:

![LED Project](http://i.imgur.com/z7G4iMT.jpg)

## IntelliJ Setup

We used Java in the IntelliJ IDE to create this project. The Lib Bulldog library must be imported in order to work with the BeagleBone Black. Since the library is not available through Maven, it must be downloaded directly and added to the project. The library can be found at [the Lib Bulldog website](http://libbulldog.org/). This library can be added by opening the "Project Structure" under the File menu, choosing the Libraries tab, pressing the "+" button and selecting "Java" and selecting the Lib Bulldog library.

In order to upload our project, including .java source files and .class files, to the BeagleBone Black, we had to set up an SFTP connection for deployment. In order to do this, open the Deployment -> Configuration menu and add a new server. Make sure this an SFTP connection. The SFTP host is the IP address for your BeagleBone Black- the default IP being 192.168.7.2. Enter the username and password you will use to log in to the BeagleBone Black via SSH, and make sure to click the "Mappings" tab and enter the deployment path for which directory you want to upload to.

If you want to work with the same code we did, you can clone this repository by copying the HTTPS URL at the top of this repository and running "git clone <URL>" in your terminal.

## Code

In order to get started, we must first instantiate the board for the BeagleBone Black. This can be done by using the static method "createBoard" on the Platform class in Lib Bulldog.

	Board board = Platform.createBoard();

Then we instantiate an AnalogInput object for our touch sensor, referencing the pin that we've plugged it into on the board. We will have to cast this to an AnalogInput using the .as() method.

	AnalogInput touchSensor = board.getPin(BBBNames.AIN1).as(AnalogInput.class);

Finally, we instantiate a Pwm object referencing the pin that we've plugged our LED into, then create a Led object that utilizes that Pwm.

	Pwm pwm = board.getPin(BBBNames.P8_13).as(Pwm.class);
	Led led = new Led(pwm);

Now that our setup is complete, we can get to work. The remainder of the code is fairly simple. In an infinite loop, we run the .read() method on the AnalogInput in order to test where it is being touched. We print this information to the console to keep track of it, and run the setBrightness() method on the Led to correspond to the input. As the touch moves from left to right, the LED goes from bright to dim.

## Project in Action

Here is a YouTube video of our small hardware project in action.

[![BBB Project in Action](http://img.youtube.com/vi/FsLj8Dov3sc/0.jpg)](http://www.youtube.com/watch?v=FsLj8Dov3sc "BBB Project in Action")

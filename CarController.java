import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/*
* This class represents the Controller part in the MVC pattern.
* It's responsibilities is to listen to the View and responds in a appropriate manner by
* modifying the model state and the updating the view.
 */

public class CarController {
    // member fields:

    // The delay (ms) corresponds to 20 updates a sec (hz)
    private final int delay = 50;
    // The timer is started with a listener (see below) that executes the statements
    // each step between delays.
    private Timer timer = new Timer(delay, new TimerListener());

    // The frame that represents this instance View of the MVC pattern
    CarView frame;
    // A list of cars, modify if needed
    ArrayList<Car> cars = new ArrayList<>();
    VolvoWorkshop workshop = new VolvoWorkshop(5);
    //methods

    public static void main(String[] args) {
        // Instance of this class
        CarController cc = new CarController();

        Volvo240 volvo = new Volvo240();
        volvo.setPosition(0, 0);
        cc.cars.add(volvo);

        Saab95 saab = new Saab95();
        saab.setPosition(0, 100);
        cc.cars.add(saab);

        Scania scania = new Scania();
        scania.setPosition(0, 200);
        cc.cars.add(scania);

        // Start a new view and send a reference of self
        cc.frame = new CarView("CarSim 1.0", cc);
        cc.timer.start();
    }

    /* Each step the TimerListener moves all the cars in the list and tells the
    * view to update its images. Change this method to your needs.
    * */
    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < cars.size(); i++) {
                Car car = cars.get(i);
                car.move();

                int x = (int) Math.round(car.getX());
                int y = (int) Math.round(car.getY());

                if (y > 700 || y < 0) {
                    car.stopEngine();
                    car.turnLeft();
                    car.turnLeft();
                    car.startEngine();
                }

                if (car instanceof Volvo240) {
                    double dist = Math.sqrt(Math.pow(car.getX() -300, 2));
                    if (dist < 10) {
                        workshop.carIntake((Volvo240) car);
                        cars.remove(car);
                        i--;
                    }
                }
                frame.drawPanel.moveit(x, y);
                // repaint() calls the paintComponent method of the panel
            }
            frame.drawPanel.repaint();

        }
    }

    // Calls the gas method for each car once
    void gas(int amount) {
        double gas = ((double) amount) / 100;
         for (Car car : cars
                ) {
            car.gas(gas);
        }
    }
}
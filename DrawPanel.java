import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;

// This panel represents the animated part of the view with the car images.

public class DrawPanel extends JPanel {

    // Just a single image, TODO: Generalize
    private Map<String, BufferedImage> images = new HashMap<>();
    private CarController cc;

   /* BufferedImage carImage;
    // To keep track of a single car's position
    Point carPoint = new Point();

    BufferedImage carWorkshopImage;
    Point carWorkshopPoint = new Point(300,300);

    // TODO: Make this general for all cars
    void moveit(int x, int y){
        carPoint.x = x;
        carPoint.y = y;
    }
*/
    // Initializes the panel and reads the images
    public DrawPanel(int x, int y, CarController cc) {
        this.cc = cc;
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.green);
        // Print an error message in case file is not found with a try/catch block
        try {
            // You can remove the "pics" part if running outside of IntelliJ and
            // everything is in the same main folder.
            // volvoImage = ImageIO.read(new File("Volvo240.jpg"));
            images.put("Volvo240", ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Volvo240.jpg")));
            images.put("Saab95", ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Saab95.jpg")));
            images.put("Scania", ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Scania.jpg")));
            images.put("Workshop", ImageIO.read(DrawPanel.class.getResourceAsStream("pics/VolvoBrand.jpg")));


            // Rememember to rightclick src New -> Package -> name: pics -> MOVE *.jpg to pics.
            // if you are starting in IntelliJ.
        } catch (IOException ex) { ex.printStackTrace(); }
    }


    // This method is called each time the panel updates/refreshes/repaints itself
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(images.get("Workshop"), (int)cc.workshop.getX(), (int)cc.workshop.getY(), null); // see javadoc for more info on the parameters

        for (Car car : cc.cars) {
            BufferedImage img = images.get(car.getModelName());
            if (img != null) {
                g.drawImage(img, (int)Math.round(car.getX()), (int)Math.round(car.getY()), null);
            }
        }
    }
}

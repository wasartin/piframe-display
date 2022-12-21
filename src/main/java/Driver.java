import javax.swing.*;
import java.awt.*;

public class Driver {
    public static void main(String[] args) {
//        Display asdf = new Display();
//        asdf.show();
                // Load the image
        ImageIcon image = new ImageIcon("/Users/wsartin/dev/workshop/seer/src/main/python/imgs/jurassicpark.jpeg");

        // Create a label to hold the image
        JLabel label = new JLabel(image);

        // Create a frame to hold the label
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add the label to the frame
        frame.add(label);

        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Set the frame to full screen
        frame.setSize(screenSize.width, screenSize.height);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);

        // Show the frame
        frame.setVisible(true);
    }
}

import model.Album;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

public class Displayer extends Window {

    private BufferedImage pic;
    private static Album photoAlbum;
    private static boolean keepRunning = true;

    //private static ArrayList<File> shuffledDirectory;

    public static void main(String[] args) throws Exception {
        // args: folder location, interval in minute
        String givenDirectory = "";
        long intervalInMinutes = 15;
        System.out.println("START");
        if(args != null && args.length > 0) {
            givenDirectory = args[0];
            intervalInMinutes = Long.parseLong(args[1]);
            System.out.println("New intervalInMinutes: " + intervalInMinutes);
        }

        String directoryFilePath = (givenDirectory.length() != 0) ? givenDirectory: "/Users/wsartin/dev/workshop/PhotoAlbum/resrc/jpgPosters";
        photoAlbum = new Album(directoryFilePath);
        long intervalInMilliseconds = TimeUnit.MINUTES.toMillis(intervalInMinutes);
        GraphicsDevice screen = setUp();
        try {
            do {
                showPoster(intervalInMilliseconds, screen);
            } while(keepRunning);
        } catch(Exception e) {
            System.out.println("What the hell is going on here?");
        }
    }

    private static GraphicsDevice setUp() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice screen = ge.getDefaultScreenDevice();

        if (!screen.isFullScreenSupported()) {
            System.out.println("Full screen mode not supported");
            System.exit(1);
        }
        return screen;
    }

    // can this be made into a thread? and when I have to force forward or backward,
    // can I then kill the thread and make a new one.
    private static void showPoster(long intervalInMilliseconds, GraphicsDevice screen) {
        try {
            File currentPhoto = photoAlbum.next();
            System.out.println("FILE: "+ currentPhoto.toPath());
            BufferedImage image = ImageIO.read(currentPhoto);
            BufferedImage rotatedImage = rotate(image);
            screen.setFullScreenWindow(new Displayer(rotatedImage));
            Thread.sleep(intervalInMilliseconds);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public Displayer(BufferedImage pic) {
        super(new Frame());

        this.pic = pic;

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()== MouseEvent.BUTTON1) { //Left
                    // go back
                    System.out.println("Go back");
                    exitApplication();
                } else if(e.getButton() == MouseEvent.BUTTON2){ // Right
                    // go forward
                    // skip photo
                    System.out.println("Skip");
                } else if(e.getButton() == MouseEvent.BUTTON3) { // Middle
                    System.out.println("Exit");
                    keepRunning = false;
                }
            }
        });
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        g.drawImage(pic, 0, 0, getWidth(), getHeight(), this);
    }

    private void exitApplication() {
        System.exit(0);
    }

    private static BufferedImage rotate(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage newImage = new BufferedImage(height, width, image.getType());

        Graphics2D g = newImage.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((height - width) / 2, (height - width) / 2);
        at.rotate(Math.toRadians(90), height / 2, width / 2);
        g.setTransform(at);
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return newImage;
    }
}

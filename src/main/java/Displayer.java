import model.Album;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;

public class Displayer extends Window {

    private BufferedImage pic; // Do I actually need this thing at all?
    private static Album photoAlbum; // Could pass in
    private static boolean keepRunning = true; // Don't really need this when running correctly
    private static boolean displayCurrentPoster = true; // Keep

    int counter = 0; // TODO: Delete

    public static void main(String[] args) throws Exception {
        System.out.println("Startup");
        String directoryFilePath = "/Users/wsartin/dev/workshop/PhotoAlbum/resrc/jpgPosters";
        int intervalInMinutes = 15;
        if(args != null && args.length > 0) {
            directoryFilePath = args[0];
            intervalInMinutes = Integer.parseInt(args[1]);
            System.out.println("New intervalInMinutes: " + intervalInMinutes);
        }

        photoAlbum = new Album(directoryFilePath);
        GraphicsDevice screen = setUp();

        run(intervalInMinutes, screen);
    }

    private static void run(int intervalInMinutes, GraphicsDevice screen){
        try {
            do {
                displayCurrentPoster = true;
                LocalTime startTime = LocalTime.now();
                LocalTime endTime = startTime.plusMinutes(intervalInMinutes);
                showPoster(screen);
                while(displayCurrentPoster){
                    Thread.sleep(5);
                    if(LocalTime.now().isAfter(endTime)){
                        displayCurrentPoster = false;
                    }
                }
            } while(keepRunning);
        } catch(Exception e) {
            System.out.println("What the hell is going on here?");
        }
    }

    private static void showPoster(GraphicsDevice screen) {
        try {
            screen.setFullScreenWindow(null);

            File currentPhoto = photoAlbum.next();
            System.out.println("FILE: "+ currentPhoto.toPath());
            BufferedImage image = ImageIO.read(currentPhoto);
            BufferedImage rotatedImage = rotate(image);

            screen.setFullScreenWindow(new Displayer(rotatedImage));
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
                    counter++;
                    if(counter > 2){
                        keepRunning = false;
                        System.out.println("Go back");
                        exitApplication();
                    }
                    displayCurrentPoster = false;
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

    private static GraphicsDevice setUp() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice screen = ge.getDefaultScreenDevice();

        if (!screen.isFullScreenSupported()) {
            System.out.println("Full screen mode not supported");
            System.exit(1);
        }
        return screen;
    }

}


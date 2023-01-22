import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

//TODO:
//  - run from given section
//  - categories of photos in DB?
public class Displayer extends Window {

    private BufferedImage pic;

    // TODO: Add functionality for sleepiung at night.
    public static void main(String[] args) {
        // args: folder location, interval in minutes, repeat (optional)
        String givenDirectory = "";
        long intervalInMinutes = 15;
        System.out.println("START");
        if(args != null && args.length > 0) {
            givenDirectory = args[0];
            intervalInMinutes = Long.parseLong(args[1]);
            System.out.println("New intervalInMinutes: " + intervalInMinutes);
        }

        String directoryFilePath = (givenDirectory.length() != 0) ? givenDirectory: "/Users/wsartin/dev/workshop/PhotoAlbum/resrc/jpgPosters";
        long intervalInMilliseconds = TimeUnit.MINUTES.toMillis(intervalInMinutes);
        GraphicsDevice screen = setUp();
        try {
            do {
                showPoster(directoryFilePath, intervalInMilliseconds, screen);
            } while(true);
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

    private static void showPoster(String directoryFilePath, long intervalInMilliseconds, GraphicsDevice screen) {
        try {
            File currentDirectory = new File(directoryFilePath);
            File[] files = currentDirectory.listFiles();

            if(files == null) {
                throw new Exception("Given a bad directory. Couldn't find any files at: " + currentDirectory);
            }

            for (File file : files) {
                if (file.isFile() && !file.isHidden()) {
                    System.out.println("FILE: "+ file.toPath());
                    BufferedImage loadedpic2 = ImageIO.read(file);
                    BufferedImage rotated2 = rotate(loadedpic2);
                    screen.setFullScreenWindow(new Displayer(rotated2));

                    Thread.sleep(intervalInMilliseconds);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public Displayer(BufferedImage pic) {
        super(new Frame());

        this.pic = pic;

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                exitApplication();
            }
        });
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

        // Draw the image
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

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//TODO:
//  - run from given section
//  - categories of photos in DB?
public class Displayer extends Window {

    private static final Logger LOGGER = LoggerFactory.getLogger(Displayer.class);

    private BufferedImage pic;

    // TODO: Add functionality for sleepiung at night.
    public static void main(String[] args) {
        // args: folder location, interval in minutes, repeat (optional)
        String givenDirectory = "";
        long intervalInMinutes = 15;

        LOGGER.info(String.format("Startup: %s", LocalDateTime.now()));
        if(args != null && args.length > 0) {
            givenDirectory = args[0];
            intervalInMinutes = Long.parseLong(args[1]);
        }

        String directoryFilePath = (givenDirectory.length() != 0) ? givenDirectory: "/Users/wsartin/dev/workshop/PhotoAlbum/resrc/jpgPosters";
        LOGGER.info(String.format("Image Directory: %s, Interval in Minutes: %d", givenDirectory, intervalInMinutes));
        long intervalInMilliseconds = TimeUnit.MINUTES.toMillis(intervalInMinutes);
        GraphicsDevice screen = setUp();
        try {
            do {
                showPoster(directoryFilePath, intervalInMilliseconds, screen);
            } while(true);
        } catch(Exception e) {
            LOGGER.error(String.format("Ended at: %s", LocalDateTime.now()));
        }
    }

    private static GraphicsDevice setUp() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice screen = ge.getDefaultScreenDevice();

        if (!screen.isFullScreenSupported()) {
            LOGGER.error("Full Screen is not supported");
            System.exit(1);
        }
        return screen;
    }

    private static void showPoster(String directoryFilePath, long intervalInMilliseconds, GraphicsDevice screen) {
        try {
            File currentDirectory = new File(directoryFilePath);
            File[] files = currentDirectory.listFiles();

            if(files == null) {
                LOGGER.error("Given a bad directory. Couldn't find any files at: " + currentDirectory);
                throw new Exception();
            }
            ArrayList<File> shuffledList = new ArrayList<>(List.of(files));
            Collections.shuffle(shuffledList);

            for (File file : shuffledList) {
                if (file.isFile() && !file.isHidden()) {
                    LOGGER.info(String.format("Displaying %s", file.toPath()));
                    BufferedImage loadedpic2 = ImageIO.read(file);
                    BufferedImage rotated2 = rotate(loadedpic2);
                    screen.setFullScreenWindow(new Displayer(rotated2));

                    Thread.sleep(intervalInMilliseconds);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
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

package model;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.imageio.ImageIO;

public class Album {
    private ArrayList<File> shuffledDirectory = new ArrayList<File>();
    private int index = 0;

    public Album(String directory) throws Exception {
        File currentDirectory = new File(directory);
        File[] files = currentDirectory.listFiles(pathname -> {
            String extension = pathname.getName().toLowerCase();
            return extension.endsWith(".jpg") || extension.endsWith(".jpeg") || extension.endsWith(".png");
        });

        if(files == null) {
            throw new Exception("Given a bad directory. Couldn't find any files at: " + currentDirectory);
        }
        shuffledDirectory.addAll(Arrays.asList(files));
    }

    public File next(){
        if(index > shuffledDirectory.size()) {
            reShuffleFiles();
            index = 0;
        }
        return shuffledDirectory.get(index++);
    }

    public File previous() {
        index--;
        if(index < 0) {
            index = 0;
        }
        return shuffledDirectory.get(index);
    }

    private void reShuffleFiles() {
        Collections.shuffle(shuffledDirectory.stream().toList());
    }


}

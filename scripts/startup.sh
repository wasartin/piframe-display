#!/bin/bash

if [ "$#" = "0" ]; then
    PHOTO_DIRECTORY = "/Users/wsartin/Downloads/jpgPosters/"
    INTERVAL_IN_MINUTES = 15
fi

# execute input file, append/create results to *_result.txt,
java src/main/java/Displayer.java PHOTO_DIRECTORY INTERVAL_IN_MINUTES

# java src/main/java/Displayer.java "/Users/wsartin/Downloads/jpgPosters/" 0.5 -r
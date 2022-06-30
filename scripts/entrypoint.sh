#!/bin/bash
# remove old
# rm /tmp/.X99-lock # needed when docker container is restarted
# Xvfb :99 -screen 0 640x480x8 -nolisten tcp &
#java --module-path lib/javafx-sdk-17.0.1 -jar display-0.0.1-SNAPSHOT.jar
java -jar display-0.0.1-SNAPSHOT.jar

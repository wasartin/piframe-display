# openjdk:8-jdk-alpine
FROM amazoncorretto:8
# RUN apt-get update && apt-get install libgtk-3-0 libglu1-mesa xvfb -y && apt-get update
# RUN apt-get update && apt-get install -y openjfx

# COPY library/javafx-sdk-17.0.1 javafx-sdk-17.0.1

ADD build/libs/display-0.0.1-SNAPSHOT.jar display-0.0.1-SNAPSHOT.jar

# TODO: Idk if I need this
#ADD lib lib

# TODO: What is this?
#ADD config.properties config.properties
# RUN apt-get install xvfb
#ENV DISPLAY=:99
ADD scripts/entrypoint.sh entrypoint.sh
RUN chmod +x entrypoint.sh
ENTRYPOINT ["sh", "entrypoint.sh"]
# CMD ["java", "-Djavafx.verbose=true", "-Dprism.verbose=true", "-jar", "display-0.0.1-SNAPSHOT.jar"]

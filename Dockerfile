FROM openjdk:alpine
 
EXPOSE 80

RUN apk add --no-cache maven
ADD src /jjson/src
ADD pom.xml /jjson/pom.xml
WORKDIR /jjson
RUN mvn clean install -e
CMD ["java","-jar","target/jjson-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]

# EXPOSE 80
#  sudo docker build -t jjson .
#  sudo docker run -it -p 8000:80 jjson

# FROM maven:3.3-jdk-8-onbuild
# CMD ["java","-jar","target/jjson-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]
# EXPOSE 80
#  sudo docker build -t jjson .
#  sudo docker run -it -p 8000:80 jjson

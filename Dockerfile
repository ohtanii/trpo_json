FROM maven:3.3-jdk-8-onbuild
CMD ["java","-jar","target/jjson-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]
EXPOSE 80
#  sudo docker build -t jjson .
#  sudo docker run -it -p 8000:80 jjson

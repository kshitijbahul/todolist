FROM hirokimatsumoto/alpine-openjdk-11:latest
EXPOSE 8007
ADD /build/todolist.jar todolist.jar
ENTRYPOINT ["java","-jar","todolist.jar"]
FROM anapsix/alpine-java:8

ARG JAR_FILE

ADD target/${JAR_FILE} /usr/share/service/service.jar

ENTRYPOINT ["java", "-jar", "/usr/share/service/service.jar"]

CMD ["java", "-jar", "/usr/share/service/service.jar", "--help"]

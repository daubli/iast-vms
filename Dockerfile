FROM adoptopenjdk/openjdk11-openj9

VOLUME /tmp

ADD vms-0.0.1-SNAPSHOT.jar app.jar
ADD docker-entrypoint.sh docker-entrypoint.sh

ENTRYPOINT ["/docker-entrypoint.sh"]

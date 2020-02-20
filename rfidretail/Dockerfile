FROM maven:3-jdk-8-slim as build
COPY . /app
WORKDIR /app

RUN mvn package -Dmaven.test.skip=true

FROM adoptopenjdk/openjdk8:alpine

VOLUME /tmp
COPY --from=build /app/target/spyder_retail-1.0.war app.war

RUN set -eux; \
    apk add --no-cache freetype-dev fontconfig ttf-dejavu; \
    rm -rf /var/cache/apk/*;
    
RUN apk --no-cache add tzdata && \
        cp /usr/share/zoneinfo/Asia/Seoul /etc/localtime && \
        echo "Asia/Seoul" > /etc/timezone

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Djava.awt.headless=true","-Djava.library.path=/home", "-jar","/app.war"]

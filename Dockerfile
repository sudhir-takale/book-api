
From maven:3.8.5-openjdk-17

WORKDIR /book

COPY . .

RUN ./gradlew clean build

CMD ./gradlew bootRun
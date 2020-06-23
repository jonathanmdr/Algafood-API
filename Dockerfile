FROM maven:3.6.2-jdk-12 AS builder

COPY . /build

RUN cd /build && mvn --batch-mode package

RUN mkdir /var/jar && mv /build/target/algafood-api-*.jar /var/jar/algafood-api.jar

COPY pom.xml /var/jar/

FROM azul/zulu-openjdk:12.0.2

RUN  apt-get update \
  && apt-get install -y wget \
  && rm -rf /var/lib/apt/lists/*

RUN mkdir /var/jar

COPY --from=builder /var/jar/* /var/jar/

WORKDIR /var/jar/

RUN wget -O apm-agent.jar https://search.maven.org/remotecontent?filepath=co/elastic/apm/elastic-apm-agent/1.16.0/elastic-apm-agent-1.16.0.jar

ENTRYPOINT ["java", "-javaagent:apm-agent.jar", "-Djava.security.egd=file:/dev/./urandom","-jar","algafood-api.jar"]

ARG VERSION
ARG OPENJKD_VERSION

FROM dockerize:${VERSION} AS dockerz
FROM openjdk:${OPENJKD_VERSION}

USER root

RUN mkdir -p /opt
WORKDIR /opt

RUN apt-get update
RUN apt-get install -y --no-install-recommends bash=4.4-5

COPY --from=dockerz /usr/local/bin/dockerize /usr/local/bin/

WORKDIR /opt

COPY lib.zip .
RUN unzip lib.zip && rm lib.zip
ENV CLASSPATH=/opt/lib

COPY ft.jar .
COPY run.sh .
RUN chmod +x run.sh

ENV DB_HOST=db
ENV DB_PORT=5432

ENV SUSPEND=n
EXPOSE 5005
ENTRYPOINT ["/opt/run.sh"]

ARG MINIO_VERSION

FROM minio/minio:${MINIO_VERSION}

RUN apk add --no-cache bash shadow

ENV MINIO_ACCESS_KEY=TESTINGACCESS123
ENV MINIO_SECRET_KEY=tesTingPass123

RUN mkdir /opt/volume/data -p
RUN addgroup -S jdoe && adduser -S jdoe -G jdoe
RUN usermod  -u 1000 jdoe
RUN groupmod -g 1000 jdoe

COPY run.sh /opt/run.sh
RUN chmod u+x /opt/run.sh
RUN chown -R jdoe:jdoe /opt

USER jdoe
ENTRYPOINT ["/opt/run.sh"]
CMD ["server", "/opt/volume/data"]

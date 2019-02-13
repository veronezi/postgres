ARG VERSION
ARG POSTGRES_VERSION

FROM dockerize:${VERSION} AS dockerz

FROM ubuntu:18.04 as MC
RUN mkdir -p /opt
RUN apt-get update
RUN apt-get install -y curl
RUN curl -o /opt/mc https://dl.minio.io/client/mc/release/linux-amd64/mc

FROM postgres:${POSTGRES_VERSION}

COPY postgres.conf /etc/postgresql/
COPY restore.conf /opt

COPY archive_backup.sh /opt
COPY archive_restore.sh /opt
COPY create_base_backup.sh /opt
COPY restore_base_backup.sh /opt
COPY --from=MC /opt/mc /bin/mc
RUN chmod ugo+x /bin/mc

COPY --from=dockerz /usr/local/bin/dockerize /usr/local/bin/

COPY run.sh /opt
RUN chmod u+x /opt/run.sh

RUN chown -R postgres:postgres /opt

RUN usermod  -u 1000 postgres
RUN groupmod -g 1000 postgres

ENV UPLOAD_BASEBACKUP_PATH=basebackup
ENV UPLOAD_WAL_PATH=wals
ENV BUCKET_PREFIX=postgres
ENV MINIOSRV_NAME=miniosrv
ENV RESTORE_BASE_BACKUP=""
ENTRYPOINT ["/opt/run.sh"]
CMD ["postgres", "-c", "config_file=/etc/postgresql/postgres.conf"]
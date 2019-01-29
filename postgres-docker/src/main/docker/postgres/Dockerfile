ARG POSTGRES_VERSION
ARG VERSION

FROM dockerize:${VERSION} AS dockerz
FROM postgres:${POSTGRES_VERSION}

COPY --from=dockerz /usr/local/bin/dockerize /usr/local/bin/

COPY postgres.conf /etc/postgresql/
COPY restore.conf /opt

COPY archive_backup.sh /opt
COPY archive_restore.sh /opt
COPY create_base_backup.sh /opt
COPY restore_base_backup.sh /opt

COPY run.sh /opt
RUN chmod u+x /opt/run.sh

RUN mkdir /opt/backup
VOLUME /opt/backup

RUN chown -R postgres:postgres /opt

RUN usermod  -u 1000 postgres
RUN groupmod -g 1000 postgres

ENV RESTORE_BASE_BACKUP=""
ENTRYPOINT ["/opt/run.sh"]
CMD ["postgres", "-c", "config_file=/etc/postgresql/postgres.conf"]
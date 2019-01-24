ARG POSTGRES_VERSION
ARG VERSION

FROM dockerize:${VERSION} AS dockerz
FROM postgres:${POSTGRES_VERSION}

RUN apt-get update
RUN apt-get install curl -y

COPY --from=dockerz /usr/local/bin/dockerize /usr/local/bin/

ENV BACKUP_URL http://backup:3000

COPY --chown=postgres:postgres postgres.conf /etc/postgresql/
COPY --chown=postgres:postgres restore.conf /opt

COPY --chown=postgres:postgres run.sh /opt
RUN chmod u+x /opt/run.sh
COPY --chown=postgres:postgres archive_backup.sh /opt
COPY --chown=postgres:postgres archive_restore.sh /opt

COPY --chown=postgres:postgres create_base_backup.sh /opt
COPY --chown=postgres:postgres restore_base_backup.sh /opt
COPY --chown=postgres:postgres test.sh /opt

ENV RESTORE_MODE=false

ENTRYPOINT ["/opt/run.sh"]
CMD ["postgres", "-c", "config_file=/etc/postgresql/postgres.conf"]
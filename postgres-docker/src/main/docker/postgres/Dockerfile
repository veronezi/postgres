ARG POSTGRES_VERSION

FROM postgres:${POSTGRES_VERSION}
COPY postgres.conf /etc/postgresql/
COPY archive_backup.sh /
RUN chmod u+x /archive_backup.sh

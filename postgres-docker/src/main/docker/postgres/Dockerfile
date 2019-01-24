ARG POSTGRES_VERSION

FROM postgres:${POSTGRES_VERSION}
COPY postgres.conf /etc/postgresql/
COPY archive_backup.sh /
CMD ["postgres", "-c", "config_file=/etc/postgresql/postgres.conf"]
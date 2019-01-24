ARG VERSION
ARG NODE_VERSION

FROM dockerize:${VERSION} AS dockerz
FROM node:${NODE_VERSION}-alpine AS auth-ui-build

USER root

RUN mkdir -p /opt/backup
WORKDIR /opt/backup

RUN chown node:node -R /opt

RUN apk add --no-cache bash
COPY --from=dockerz /usr/local/bin/dockerize /usr/local/bin/

WORKDIR /opt
COPY --chown=node:node run.sh .
RUN chmod +x run.sh

WORKDIR /opt/backup

COPY --chown=node:node backup-${VERSION} .

RUN mkdir -p /opt/backup/uploaded/
RUN mkdir -p /opt/backup/uploads/
RUN mkdir -p /opt/backup/basebackup/

ENV BASE_DIR /opt/backup

EXPOSE 3000

RUN chown -R node:node /opt
RUN chmod -R u+rwx /opt

USER node

RUN npm install
RUN npm run-script test

ENTRYPOINT ["/opt/run.sh"]
CMD ["index.js"]

#!/bin/bash
set -e

echo "[postgres] creating base backup with user $(whoami)..."
rm -Rf /tmp/base
pg_basebackup -U $POSTGRES_USER -Ft -X none -D /tmp/base
mc cp /tmp/base/base.tar $MINIOSRV_NAME/$UPLOAD_BASEBACKUP_PATH/base_$(date +%s)
mc cp /tmp/base/base.tar $MINIOSRV_NAME/$UPLOAD_BASEBACKUP_PATH/base

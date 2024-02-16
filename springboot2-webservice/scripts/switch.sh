#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
soruce ${ABSDIR}/profile.sh

function switch_proxy()
{
  IDLE_PORT=$(find_idle_port)

  echo "> 전활할 port: $IDLE_PORT"
  echo "> port 전환"
  echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/dservice-url.inc

  echo "> 엔진엑스 Reload"
  sudo service nginx reload
}
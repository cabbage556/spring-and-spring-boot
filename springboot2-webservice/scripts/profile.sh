#!/usr/bin/env bash

# bash는 return이 안되므로 마지막 줄에 echo로 결과 출력 후 클라이언트에서 출력된 값을 사용함

# 쉬고 있는 profile 찾기: real1 사용 중이라면 real2 가 쉬고 있음(반대면 real1 이 쉬고 있음)
function find_idle_profile()
{
  RESPONSE_CODE = $(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

  if [ ${RESPONSE_CODE} -ge 400 ] # 400보다 크면(4xx/5xx 에러 포함)
  then
    CURRENT_PROFILE=real2
  else
    CURRENT_PROFILE=$(curl -s http://localhost/profile)
  fi

  if [ ${CURRENT_PROFILE} == real1 ]
  then
    IDLE_PROFILE=real2
  else
    IDLE_PROFILE=real1
  fi

  echo "${IDLE_PROFILE}"
}

# 쉬고 있는 profile의 port 찾기
function find_idle_port()
{
  IDLE_PROFILE=$(find_idle_profile)

  if [ ${IDLE_PROFILE} == real1 ]
  then
    echo "8081"
  else
    echo "8082"
  fi
}
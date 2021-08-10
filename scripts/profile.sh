#!/usr/bin/env bash

# 쉬고 있는 profile찾기 : real1이 사용중이면 real2가 쉬고 있고, 반대면 real1이 쉬고 있음

function find_idle_profile() {
  
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)  # 현재 엔진엑스가 바라보고 있는 스프링 부트가 정상적으로 수행 중인지 확인
                                                                                    # 응답값을 HttpStatus로 받음
                                                                                    # 정상이면 200, 오류가 발생한다면 400~503사이로 발생하니 400이상은 모두 예외 로 보고 real2를 현재 profile로 사용
  
  if [ ${RESPONSE_CODE} -ge 400 ] # 400 보다 크면 (즉, 40x/50x 에러 모두 포함)
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
# 편집하기
crontab -e
# 변경사항 확인 
crontab -l
# 스케줄링 작업 시작
service cron start
# 스케줄링 작업 중지
service cron stop
# 스케줄링 작업 상태 확인
service cron status
# 권한 부여
chmod +x run_kafka_command.sh
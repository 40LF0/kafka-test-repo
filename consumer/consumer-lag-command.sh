#!/bin/bash
NOW=$(date +"%Y-%m-%d %H:%M:%S")
echo "[$NOW] Kafka Command Output:" >> consumer_info.txt
kafka_2.13-3.6.1/bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group testgroup --describe >> consumer_info.txt
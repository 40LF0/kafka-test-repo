nohup kafka_2.13-3.6.1/bin/kafka-producer-perf-test.sh --topic test1 --record-size 1000 --num-records 40000000 \
--producer-props bootstrap.servers=localhost:9092  \
--producer.config kafka_2.13-3.6.1/config/producer.properties \
--throughput 200000 > /root/producer_info.txt &

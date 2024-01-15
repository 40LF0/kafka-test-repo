kafka_2.13-3.6.1/bin/kafka-producer-perf-test.sh --topic test2 --record-size 50000 --num-records 4000000 \
--producer-props bootstrap.servers=10.41.151.246:9092  \
--producer.config kafka_2.13-3.6.1/config/producer.properties \
--throughput -1

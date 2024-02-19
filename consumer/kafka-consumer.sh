nohup kafka_2.13-3.6.1/bin/kafka-console-consumer.sh --bootstrap-server 10.41.220.185:9092 --topic test1 --group testgroup --from-beginning \
> consumer1.txt 2>&1 &

nohup kafka_2.13-3.6.1/bin/kafka-console-consumer.sh --bootstrap-server 10.41.220.185:9092 --topic test1 --group testgroup --from-beginning &
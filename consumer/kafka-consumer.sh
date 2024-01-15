nohup kafka_2.13-3.6.1/bin/kafka-console-consumer.sh --bootstrap-server 10.41.148.142:9092 --topic test1 --group testgroup --from-beginning \
> consumer1.txt 2>&1 &

nohup kafka_2.13-3.6.1/bin/kafka-console-consumer.sh --bootstrap-server 10.41.149.147:9092 --topic test1 --group testgroup --from-beginning &
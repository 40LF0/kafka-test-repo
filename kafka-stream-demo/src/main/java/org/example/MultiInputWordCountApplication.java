package org.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.Properties;

public class MultiInputWordCountApplication {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "testgroup4");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "27.96.130.10:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.STATE_DIR_CONFIG, "/test3");
        props.put(StreamsConfig.consumerPrefix(ConsumerConfig.METADATA_MAX_AGE_CONFIG), "60000");
        StreamsBuilder builder = new StreamsBuilder();

        // 두 개의 입력 토픽에서 메시지를 읽어옵니다.
        KStream<String, String> textLines1 = builder.stream("test1", Consumed.with(Serdes.String(), Serdes.String()));
        KStream<String, String> textLines2 = builder.stream("test2", Consumed.with(Serdes.String(), Serdes.String()));

        // 두 스트림을 병합합니다.
        KStream<String, String> mergedStream = textLines1.merge(textLines2);

        // 병합된 스트림에서 단어 빈도수를 계산합니다.
        KTable<String, Long> wordCounts = mergedStream
                .flatMapValues(textLine -> Arrays.asList(textLine.toLowerCase().split("\\W+")))
                .groupBy((key, word) -> word)
                .count();

        // 결과를 출력 토픽으로 내보냅니다.
        wordCounts.toStream().to("test3", Produced.with(Serdes.String(), Serdes.Long()));

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        // 애플리케이션이 종료될 때 스트림을 종료합니다.
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}

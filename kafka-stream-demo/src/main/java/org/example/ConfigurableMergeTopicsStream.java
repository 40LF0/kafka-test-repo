package org.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.RangeAssignor;
import org.apache.kafka.clients.consumer.RoundRobinAssignor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KafkaStreams.State;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ConfigurableMergeTopicsStream {
    private static final Logger log = LoggerFactory.getLogger(ConfigurableMergeTopicsStream.class);

    public static void main(String[] args) {
        String bootstrapServers = "27.96.130.10:9092";
        String inputTopic1 = "test1";
        String inputTopic2 = "test2";
        String outputTopic = "test3";

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "testgroup");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        props.put(StreamsConfig.consumerPrefix(ConsumerConfig.METADATA_MAX_AGE_CONFIG), "10000");

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> stream1 = builder.stream(inputTopic1);
        KStream<String, String> stream2 = builder.stream(inputTopic2);
        KStream<String, String> mergedStream = stream1.merge(stream2);
        mergedStream.to(outputTopic);

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.setStateListener((newState, oldState) -> {
            log.info("State changed from {} to {}", oldState, newState);
            if (newState == State.RUNNING) {
                log.info("Streams are running");
            }
        });

        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
package org.example;


import kafka.server.KafkaConfig;
import kafka.server.KafkaServer;
import kafka.utils.MockTime;
import kafka.utils.TestUtils;
import kafka.zk.EmbeddedZookeeper;

import org.apache.kafka.common.utils.Time;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

public class Main {

    private static final int MESSAGE_COUNT_PER_TOPIC = 1_000_000;
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final boolean USE_EMBEDDED_KAFKA = true;
    private static final String AUTO_OFFSET_RESET = "earliest";

    private static final String SINK_NAME = "sink";

    private EmbeddedZookeeper zkServer;
    private KafkaServer kafkaServer;
    private TopicUtil topicUtil;



    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        try {
            if (USE_EMBEDDED_KAFKA) {
                createKafkaCluster();
            }
            topicUtil = new TopicUtil(BOOTSTRAP_SERVERS);

            topicUtil.createTopic("abc",1);

        } finally {

            topicUtil.deleteTopic("abc");

            topicUtil.close();
            if (USE_EMBEDDED_KAFKA) {
                shutdownKafkaCluster();
            }
        }
    }

    // Creates an embedded zookeeper server and a kafka broker
    private void createKafkaCluster() throws IOException {

        zkServer = new EmbeddedZookeeper();
        String zkConnect = "localhost:" + zkServer.port();
         Properties properties = new Properties();

         properties.setProperty("zookeeper.connect", zkConnect);
        properties.setProperty("broker.id", "0");
        properties.setProperty("log.dirs", "C:/Users/krishna reddy/eclipse-workspace/kafkafromgit/kafka");
        properties.setProperty("listeners", "PLAINTEXT://localhost:9092");
        properties.setProperty("offsets.topic.replication.factor" , "1");
        properties.setProperty("log.cleaner.enable","false");


        KafkaConfig config = new KafkaConfig(properties);
        Time mock = new MockTime();
        System.out.println("Creating Kafka");
        kafkaServer = TestUtils.createServer(config, mock);
        System.out.println("Kafka Created");

    }

    // Creates 2 topics (t1, t2) with different partition counts (32, 64) and fills them with items


    private void shutdownKafkaCluster() {
        System.out.println("Shutting down kafka");
        try {

            kafkaServer.shutdown();
            zkServer.shutdown();
        }catch(Exception e){

        }
        System.out.println("Kafka stopped");
    }


}
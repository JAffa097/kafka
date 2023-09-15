package org.example;

import kafka.server.KafkaConfig;
import kafka.server.KafkaConfig$;
import kafka.server.KafkaServer;
import kafka.utils.TestUtils;
import org.apache.kafka.common.utils.Time;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Properties;

import static org.example.PrintUtil.print;

public class EmbeddedKafka {
    public static final String LISTENER = "PLAINTEXT://localhost:9092";


    private KafkaServer kafka;
int brokerId=1;
Path logDirectory;
String logDirectoryPath;
    public void start(String zookeeperConnectUrl){

        if (kafka != null) {
            print("The embedded Kafka broker with ID {} is already running.",String.valueOf( brokerId));
            return;
        }
        try {
            Properties brokerConfig = new Properties();
            brokerConfig.putAll(getProperties());
            brokerConfig.put(KafkaConfig$.MODULE$.ListenersProp(), LISTENER);
            brokerConfig.put(KafkaConfig$.MODULE$.ZkConnectProp(), zookeeperConnectUrl);
            brokerConfig.put(KafkaConfig$.MODULE$.BrokerIdProp(), brokerId);
            logDirectory = Files.createTempDirectory("kafka-vijay");
            logDirectoryPath=logDirectory.toFile().getAbsolutePath();
            brokerConfig.put(KafkaConfig$.MODULE$.LogDirProp(), logDirectoryPath);
               print("trying to start embedded kafka");
            KafkaConfig config = new KafkaConfig(brokerConfig,true);
            kafka=TestUtils.createServer(config, Time.SYSTEM);


            print("The embedded Kafka broker with ID {} has been started. Its logs can be found at {}.",String.valueOf( brokerId), logDirectoryPath);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Unable to start the embedded Kafka broker with ID %s.", brokerId), e);
        }


    }
    public void stop() throws IOException {
          kafka.shutdown();
          kafka.awaitShutdown();
          kafka=null;
          recursivelyDelete(logDirectory);
    }
      public Properties getProperties(){
              Properties properties  = new Properties();
          properties.put(KafkaConfig$.MODULE$.ZkSessionTimeoutMsProp(), "8000");
          properties.put(KafkaConfig$.MODULE$.ZkConnectionTimeoutMsProp(), "10000");
          properties.put(KafkaConfig$.MODULE$.NumPartitionsProp(), "1");
          properties.put(KafkaConfig$.MODULE$.DefaultReplicationFactorProp(), "1");
          properties.put(KafkaConfig$.MODULE$.MinInSyncReplicasProp(), "1");
          properties.put(KafkaConfig$.MODULE$.AutoCreateTopicsEnableProp(), "true");
          properties.put(KafkaConfig$.MODULE$.MessageMaxBytesProp(), "1000000");
          properties.put(KafkaConfig$.MODULE$.ControlledShutdownEnableProp(), "true");
          properties.put(KafkaConfig$.MODULE$.OffsetsTopicReplicationFactorProp(), "1");
          properties.put(KafkaConfig$.MODULE$.GroupInitialRebalanceDelayMsProp(), 0);
          properties.put(KafkaConfig$.MODULE$.TransactionsTopicReplicationFactorProp(), "1");
          properties.put(KafkaConfig$.MODULE$.TransactionsTopicMinISRProp(), "1");
          properties.put(KafkaConfig$.MODULE$.SslClientAuthProp(), "none");
          properties.put(KafkaConfig$.MODULE$.AutoLeaderRebalanceEnableProp(), "true");
          properties.put(KafkaConfig$.MODULE$.ControlledShutdownEnableProp(), "true");
          properties.put(KafkaConfig$.MODULE$.LeaderImbalanceCheckIntervalSecondsProp(), 5);
          properties.put(KafkaConfig$.MODULE$.LeaderImbalancePerBrokerPercentageProp(), 1);
          properties.put(KafkaConfig$.MODULE$.UncleanLeaderElectionEnableProp(), "false");

          return properties;

      }
    private void recursivelyDelete(final Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

                file.toFile().delete();
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                dir.toFile().delete();
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public void createTopic(String topicName){
         TopicCreation topicCreation = new TopicCreation();
         topicCreation.createTopic(topicName);
    }

}

package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

       EmbeddedZookeeper zookeeper = new EmbeddedZookeeper();
       zookeeper.start();
       System.err.println(zookeeper.getConnectString());
       EmbeddedKafka kafka = new EmbeddedKafka();
       kafka.start(zookeeper.getConnectString());
       kafka.createTopic("Topic1");
        kafka.stop();
       zookeeper.stop();


    }
}
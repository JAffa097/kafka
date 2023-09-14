package org.example;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

import java.io.Closeable;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class TopicUtil  {
    private final Admin admin;

    public TopicUtil(String broker) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", broker);
        admin = Admin.create(props);
    }

    public void createTopic(String topicId, int partitionCount) {
        List<NewTopic> newTopics = Collections.singletonList(new NewTopic(topicId, partitionCount, (short) 1));
        CreateTopicsResult createTopicsResult = admin.createTopics(newTopics);
        System.out.print("topic created");
        try {
            createTopicsResult.all().get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.print("topic creation failed");
            throw new RuntimeException(e);

        }
    }

    public void deleteTopic(String topicId) {
        System.out.print("deleting topic");
        admin.deleteTopics(Collections.singletonList(topicId));
        System.out.print("topic deleted");
    }


    public void close() {
        admin.close();
    }
}

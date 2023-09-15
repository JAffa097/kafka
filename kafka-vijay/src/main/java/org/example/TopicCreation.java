package org.example;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.errors.TopicExistsException;
import org.apache.kafka.connect.util.TopicAdmin;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TopicCreation {
    static Properties properties;
    public static void topicConfig(){
      properties= new Properties();
        properties.put("cleanup.policy", "delete");
        properties.put("delete.retention.ms", "86400000");
       properties.put("min.insync.replicas", "1");

    }
    public Map<String, Object> getPropertiesMap() {
        topicConfig();
        final Map<String, Object> map = new HashMap<>();
        for (String propertyName : properties.stringPropertyNames()) {
            map.put(propertyName, properties.get(propertyName));
        }
        return map;
    }

    public void createTopic(String topicName){
        Properties props = new Properties();
        props.put("bootstrap.servers","localhost:9092");
        props.put("client.id", "kafka-vijay-admin-client");
        try (AdminClient client = AdminClient.create(props)) {
            final NewTopic newTopic = TopicAdmin.defineTopic(topicName)
                    .partitions(1)
                    .replicationFactor((short) 1)
                    .config(getPropertiesMap())
                    .build();
            client.createTopics(Collections.singletonList(newTopic))
                    .all()
                    .get(10000, TimeUnit.MILLISECONDS);
            System.out.println("Topic created SuccessFUlly");
        } catch (InterruptedException e) {
            // due to interface compatibility, we cannot re-throw the InterruptedException,
            // but will restore the interrupted flag. we also need to throw a RuntimeException
            // so that the test case fails properly
            Thread.currentThread().interrupt();
            final String message = "Caught an unexpected InterruptedException while trying to create topic '%s'.";
            throw new RuntimeException(String.format(message, topicName), e);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof TopicExistsException) {
                final String message = "The topic '%s' already exists.";
                throw new RuntimeException(String.format(message, topicName), e.getCause());
            } else {
                final String message = "Unable to create topic '%s'.";
                throw new RuntimeException(String.format(message, topicName), e.getCause());
            }
        } catch (TimeoutException e) {
            final String message = "A timeout occurred while trying to create topic '%s'.";
            throw new RuntimeException(String.format(message, topicName), e);
        }


    }
}

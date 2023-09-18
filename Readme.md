 implementation 'org.apache.kafka:kafka_2.13:${kafka.version}'
    implementation('org.apache.kafka:kafka_2.13:${kafka.version}:test') {
        exclude group: 'org.apache.zookeeper', module: 'zookeeper'
    }
    implementation 'org.apache.kafka:kafka-clients:${kafka.version}'
    implementation('org.apache.kafka:kafka-clients:${kafka.version}:test') {
        exclude group: '*', module: '*'
        transitive = false
    }
    implementation 'org.apache.kafka:connect-api:${kafka.version}'
    implementation 'org.apache.kafka:connect-json:${kafka.version}'
    implementation 'org.apache.kafka:connect-runtime:${kafka.version}'
    implementation 'org.apache.curator:curator-test:${curator.version}'

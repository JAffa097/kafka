implementation 'org.apache.kafka:kafka_2.13:3.4.0' {
        exclude group: 'org.apache.zookeeper', module: 'zookeeper'
    }

    testImplementation 'org.apache.kafka:kafka_2.13:3.4.0' {
        exclude module: '*'
    }

    implementation 'org.apache.kafka:kafka-clients:3.4.0'
    
    compileOnly('org.apache.kafka:kafka-clients:3.4.0') {
        exclude module: '*'
    }

    implementation 'org.apache.kafka:connect-api:3.4.0'
    implementation 'org.apache.kafka:connect-json:3.4.0'
    implementation 'org.apache.kafka:connect-runtime:3.4.0'

    testImplementation 'org.apache.curator:curator-test:5.1.0'

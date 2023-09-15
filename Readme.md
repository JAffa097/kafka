implementation group: 'org.apache.kafka', name: 'kafka_2.13', version: '3.4.0', {
        exclude group: 'org.apache.zookeeper', module: 'zookeeper'
    }
    
    testImplementation group: 'org.apache.kafka', name: 'kafka_2.13', version: '3.4.0', {
        exclude group: '*'
    }

    implementation group: 'org.apache.kafka', name: 'kafka-clients', version: '3.4.0'
    
    compileOnly(group: 'org.apache.kafka', name: 'kafka-clients', version: '3.4.0') {
        exclude group: '*'
    }

    implementation group: 'org.apache.kafka', name: 'connect-api', version: '3.4.0'
    implementation group: 'org.apache.kafka', name: 'connect-json', version: '3.4.0'
    implementation group: 'org.apache.kafka', name: 'connect-runtime', version: '3.4.0'
    
    testImplementation group: 'org.apache.curator', name: 'curator-test', version: '5.1.0'

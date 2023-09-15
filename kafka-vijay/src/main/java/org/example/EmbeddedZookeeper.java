package org.example;

import org.apache.curator.test.TestingServer;

import static org.example.PrintUtil.print;

public class EmbeddedZookeeper {
    public static final int port = -1;
private TestingServer zookeeperInternalServer;
    public void start(){
        if(this.zookeeperInternalServer!=null){
            print("The embedded ZooKeeper instance is already running.");
        }else{
            try {
                System.out.println("Embedded ZooKeeper is starting.");
                this.zookeeperInternalServer = new TestingServer(port);
                print("Successfully started an embedded ZooKeeper instance at {} which is assigned to the temporary directory {}.", this.zookeeperInternalServer.getConnectString(), this.zookeeperInternalServer.getTempDirectory().getAbsolutePath());
            } catch (Exception var) {
                throw new RuntimeException("Unable to start an embedded ZooKeeper instance.", var);
            }
        }

    }
    public void stop(){
        if (this.zookeeperInternalServer == null) {
            print("The embedded ZooKeeper is not running or was already shut down.");
        } else {
            try {
                print("The embedded ZooKeeper instance at {} is stopping.", this.zookeeperInternalServer.getConnectString());
                this.zookeeperInternalServer.close();
                print("The embedded ZooKeeper instance at {} has been shut down.", this.zookeeperInternalServer.getConnectString());
            } catch (Exception var) {
                throw new RuntimeException("Unable to stop the embedded ZooKeeper instance.", var);
            }
        }

    }
    public String getConnectString() {

        return this.zookeeperInternalServer.getConnectString();
    }
}

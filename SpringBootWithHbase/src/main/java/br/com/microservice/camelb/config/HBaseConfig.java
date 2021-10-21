package br.com.microservice.camelb.config;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

import java.io.IOException;

@Configuration
public class HBaseConfig {

//    @Bean
//    public HbaseTemplate hbaseTemplate() {
//        org.apache.hadoop.conf.Configuration conf = configuration();
//        conf.set("hbase.zookeeper.quorum", "localhost");
//        conf.set("hbase.zookeeper.property.clientPort", "2181");
//        conf.set("hbase.client.autoflush", "true");
//        return new HbaseTemplate(conf);
//    }


    @Bean
    public Connection connection() throws IOException {
        System.setProperty("hadoop.home.dir", "C:\\winutils\\hadoop-3.0.0\\");

        org.apache.hadoop.conf.Configuration conf = configuration();
        conf.set("hbase.zookeeper.quorum", "localhost");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hbase.client.autoflush", "true");
        //conf.set("zookeeper.znode.parent", "/hbase-unsecure");

        return ConnectionFactory.createConnection(conf);
    }

    @Bean
    public Admin getHbaseAdmin(Connection connection) throws IOException{
        Admin admin = connection.getAdmin();
        
        return admin;
    }

    public org.apache.hadoop.conf.Configuration configuration() {

        return HBaseConfiguration.create();

    }
}

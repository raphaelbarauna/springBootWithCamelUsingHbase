//package br.com.microservice.camelb.config;
//
//
//import lombok.extern.log4j.Log4j2;
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.hbase.HBaseConfiguration;
//import org.apache.hadoop.hbase.TableName;
//import org.apache.hadoop.hbase.client.*;
//import org.apache.hadoop.hbase.util.Bytes;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.hadoop.hbase.HbaseTemplate;
//
//import java.io.IOException;
//import java.nio.file.Paths;
//
//@Log4j2
//public class TestHbase {
//
//    @Autowired
//    private HbaseTemplate hbaseTemplate;
//
//    static Configuration configuration = HBaseConfiguration.create();
//
//    @Test
//    void testConexaoHbase() throws IOException {
//
//        configuration.set("hbase.zookeeper.quorum", "localhost");
//        configuration.set("hbase.zookeeper.property.clientPort", "2181");
//        configuration.set("zookeeper.znode.parent", "/hbase-unsecure");
//
//        String OS = System.getProperty("os.name").toLowerCase();
//
//        if (OS.contains("win")) {
//            System.setProperty("hadoop.home.dir", Paths.get("winutils").toAbsolutePath().toString());
//        } else {
//            System.setProperty("hadoop.home.dir", "/");
//        }
//
//        Connection conn = ConnectionFactory.createConnection(configuration);
//
//
//
//    }
//
//}

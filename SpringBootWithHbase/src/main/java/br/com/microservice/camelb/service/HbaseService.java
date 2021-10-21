package br.com.microservice.camelb.service;


import br.com.microservice.camelb.config.HBaseConfig;
import br.com.microservice.camelb.model.SystemModel;
import br.com.microservice.camelb.model.dto.SystemDTO;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Log4j2
@EnableScheduling
public class HbaseService {

    @Autowired
    private Connection connection;

    private static String tableName = "systems";

    @Autowired
    HBaseConfig hbase;

    static final String TABLE_NAME = "box_device";

    public SystemDTO getSystemsHbase() throws IOException {

        Table table = connection.getTable(TableName.valueOf(tableName));

        Get g = new Get(Bytes.toBytes(1));
        Result result = table.get(g);

        byte[] agregator = result.getValue(Bytes.toBytes("systems"), Bytes.toBytes("agregator"));
        byte[] reconciliationByte = result.getValue(Bytes.toBytes("systems"), Bytes.toBytes("reconciliation"));
        byte[] acronymName = result.getValue(Bytes.toBytes("acronyms"), Bytes.toBytes("acronym_name"));

        return createSystemDTO(agregator, reconciliationByte, acronymName, "");

    }

    public SystemDTO getHbaseById(String systemId) throws IOException {

        Table table = connection.getTable(TableName.valueOf(tableName));

        Get g = new Get(Bytes.toBytes(systemId));
        Result result = table.get(g);

        if(result.isEmpty()){
            return new SystemDTO();
        }

        byte[] agregator = result.getValue(Bytes.toBytes("systems"), Bytes.toBytes("agregator"));
        byte[] reconciliationByte = result.getValue(Bytes.toBytes("systems"), Bytes.toBytes("reconciliation"));
        byte[] acronymName = result.getValue(Bytes.toBytes("acronyms"), Bytes.toBytes("acronym_name"));

        return createSystemDTO(agregator, reconciliationByte, acronymName, systemId);

    }

    public List<SystemDTO> listAllSystems() throws IOException {

        Table table = connection.getTable(TableName.valueOf("systems"));

        Scan scan = new Scan();

        scan.addColumn(Bytes.toBytes("systems"), Bytes.toBytes("agregator"));
        scan.addColumn(Bytes.toBytes("systems"), Bytes.toBytes("reconciliation"));
        scan.addColumn(Bytes.toBytes("acronyms"), Bytes.toBytes("acronym_name"));

        List<SystemDTO> systemDTOList = new ArrayList<>();
        ResultScanner scanner = table.getScanner(scan);

        try {
            for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
                String systemId = Bytes.toString(rr.getRow());

                byte[] agregator = rr.getValue(Bytes.toBytes("systems"), Bytes.toBytes("agregator"));
                byte[] reconciliationBye = rr.getValue(Bytes.toBytes("systems"), Bytes.toBytes("reconciliation"));
                byte[] acronymName = rr.getValue(Bytes.toBytes("acronyms"), Bytes.toBytes("acronym_name"));
                systemDTOList.add(createSystemDTO(agregator, reconciliationBye, acronymName, systemId));

            }
        }catch (Exception e){

            log.error(e.getMessage());

        } finally {
            // Make sure you close your scanners when you are done!
            scanner.close();
            table.close();
        }
        return systemDTOList;
    }

    public SystemDTO putHbase(SystemModel systemModel) throws IOException {

        Table table = connection.getTable(TableName.valueOf(tableName));
        String id = String.valueOf(systemModel.hashCode());
        Put put = new Put(Bytes.toBytes(id));

        String res = String.join(",", systemModel.getReconciliation());

        put.addColumn(Bytes.toBytes("systems"), Bytes.toBytes("agregator"), Bytes.toBytes(String.valueOf(systemModel.getAgregator())));
        put.addColumn(Bytes.toBytes("acronyms"), Bytes.toBytes("acronym_name"), Bytes.toBytes(systemModel.getAcronym_name()));
        put.addColumn(Bytes.toBytes("systems"), Bytes.toBytes("reconciliation"), Bytes.toBytes(res));

        table.put(put);
        table.close();
        return new SystemDTO(systemModel.hashCode(), systemModel.getAgregator(), systemModel.getReconciliation(), systemModel.getAcronym_name());
    }

    public SystemDTO updateSystem(String id, SystemModel systemModel) throws IOException {

        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(id));

        String res = String.join(",", systemModel.getReconciliation());

        put.addColumn(Bytes.toBytes("systems"), Bytes.toBytes("agregator"), Bytes.toBytes(String.valueOf(systemModel.getAgregator())));
        put.addColumn(Bytes.toBytes("acronyms"), Bytes.toBytes("acronym_name"), Bytes.toBytes(systemModel.getAcronym_name()));
        put.addColumn(Bytes.toBytes("systems"), Bytes.toBytes("reconciliation"), Bytes.toBytes(res));

        table.put(put);
        table.close();
        return new SystemDTO(Integer.valueOf(id), systemModel.getAgregator(), systemModel.getReconciliation(), systemModel.getAcronym_name());
    }

    public void deleteFromHbase(String systemId) {
        try {

           Table table = connection.getTable(TableName.valueOf(tableName));

            Delete delete = new Delete(Bytes.toBytes(systemId));

            table.delete(delete);

            table.close();
        } catch (IOException e) {

            log.error(e.getMessage());
        }

    }

    public SystemDTO createSystemDTO(byte[] agregator, byte[] reconciliationByte, byte[] acronymName, String systemId) {

        String reconciliation = Bytes.toString(reconciliationByte);

        List<String> reconciliationList = new ArrayList<>();

        SystemDTO systemModel = new SystemDTO();

        if (!systemId.isEmpty()) {
            systemModel.setSystemId(Integer.valueOf(systemId));
        }

        if (Bytes.toString(reconciliationByte).contains(",")) {
            reconciliationList = Arrays.asList(reconciliation.split(","));
        } else {
            reconciliationList.add(reconciliation);
        }
        systemModel.setReconciliation(reconciliationList);

        systemModel.setAgregator(Boolean.valueOf(Bytes.toString(agregator)));

        systemModel.setAcronym_name(Bytes.toString(acronymName));

        return systemModel;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    public static Writable toWritable(ArrayList<String> list) {
        Writable[] content = new Writable[list.size()];
        for (int i = 0; i < content.length; i++) {
            content[i] = new Text(list.get(i));
        }
        return new ArrayWritable(Text.class, content);
    }

    public static ArrayList<String> fromWritable(ArrayWritable writable) {
        Writable[] writables = (writable).get();
        ArrayList<String> list = new ArrayList<>(writables.length);
        for (Writable wrt : writables) {
            list.add(((Text) wrt).toString());
        }
        return list;
    }
}

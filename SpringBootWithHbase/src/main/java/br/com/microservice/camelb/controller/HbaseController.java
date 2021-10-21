package br.com.microservice.camelb.controller;


import br.com.microservice.camelb.model.StatusMessage;
import br.com.microservice.camelb.model.SystemModel;
import br.com.microservice.camelb.model.dto.SystemDTO;
import br.com.microservice.camelb.service.HbaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/hbase")
public class HbaseController {

    @Autowired
    private HbaseService hbaseService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<SystemDTO>> getSystemsList() throws IOException {

        return new ResponseEntity<>(hbaseService.listAllSystems(), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<SystemDTO> createSystem(@RequestBody SystemModel systemModel) throws IOException {

        return new ResponseEntity<>(hbaseService.putHbase(systemModel), HttpStatus.OK);

    }

    @PutMapping(value = "/{system_id}")
    public ResponseEntity<SystemDTO> updateSystem(@PathVariable(value="system_id") String id, @RequestBody SystemModel systemModel) throws IOException {

        return new ResponseEntity<>(hbaseService.updateSystem(id, systemModel), HttpStatus.OK);

    }

    @GetMapping(value = "/{system_id}")
    public ResponseEntity<SystemDTO> getSystems(@PathVariable(value="system_id") String systemId) throws IOException {

        return new ResponseEntity<>(hbaseService.getHbaseById(systemId), HttpStatus.OK);

    }

    @DeleteMapping(value = "/{system_id}")
    public ResponseEntity<StatusMessage> deleteSystem(@PathVariable(value="system_id") String systemId) throws IOException {

        if(hbaseService.getHbaseById(systemId).getSystemId() == null){
            return new ResponseEntity<>(new StatusMessage("Not_found"), HttpStatus.NOT_FOUND);
        }
        hbaseService.deleteFromHbase(systemId);

        return new ResponseEntity<>(new StatusMessage("Deleted"), HttpStatus.OK);

    }
}

package com.UCOS.simple.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/simple")
public class BuildInfoController {

    @Autowired
    private BuildProperties buildProperties;

    @GetMapping("/version")
    public ResponseEntity<String> getVersion() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(buildProperties.getVersion());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


}

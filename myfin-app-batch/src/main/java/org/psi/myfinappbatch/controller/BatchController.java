package org.psi.myfinappbatch.controller;

import java.io.ByteArrayInputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.psi.myfinappbatch.configuration.TaskletBatchConfiguration;
import org.psi.myfinappbatch.model.FileBase64;
import org.psi.myfinappbatch.service.DataService;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;


@RestController
@RequestMapping("/api")
@Tag(name = "Api", description = "Operations related to reload data base account")
public class BatchController {


    @Autowired
    Tasklet myTasklet;

    @Autowired
    DataService dataService;

    @RequestMapping(value = "/uploadBase64Files/{token}", method = RequestMethod.POST)
    public ResponseEntity<String> uploadBase64Files(@PathVariable("token") Optional<String> token, @RequestBody List<FileBase64> files ) {

        List<InputStream> list = new ArrayList<>();

        files.stream().forEach(f -> {

            byte[] decodedBytes = Base64.getDecoder().decode(f.getBase64());

            InputStream inputStream = new ByteArrayInputStream(decodedBytes);

            list.add(inputStream);

        }); 


        try {

            if(token.isPresent()){

                dataService.setToken(token.get());
            }else{

                System.out.println("pas de tokeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeen");
            }
            
            dataService.setFiles(list);

            myTasklet.execute(null, null);

        } catch (Exception e) {
            e.printStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during tasklet execution"); // Return 500 for error;
        }


        return ResponseEntity.ok("Upload successful!");
    }

}

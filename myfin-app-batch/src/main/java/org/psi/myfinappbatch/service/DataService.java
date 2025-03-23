package org.psi.myfinappbatch.service;

import java.util.List;
import java.util.Map;

import java.io.InputStream;

import org.springframework.stereotype.Component;

@Component
public class DataService {

    private List<InputStream> files;

    public List<InputStream> getFiles(){

        return this.files;
    }

    public void setFiles(List<InputStream> inputStreams){

        this.files = inputStreams; 
    }

}

package org.psi.myfinappfrontapp.custom;

import org.psi.myfinappfrontapp.api2.api.ApiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class CustomApiApi2 extends ApiApi{

    @Value("${BASEREFGATE_BATCHSERVICE:http://localhost:8889/MYFINAPPBATCHSERVICE}")
    private String baseRefGatewayBatchService;

    @PostConstruct
    public void setBasePath(){

        this.getApiClient().setBasePath(baseRefGatewayBatchService);

    }


}

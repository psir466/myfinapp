package org.psi.myfinappfrontapp.custom;

import org.psi.myfinappfrontapp.api2.api.ApiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class CustomApiApi2 extends ApiApi{

    @Value("${app_base_ref_gateway_batch_service}")
    private String baseRefGatewayBatchService;

    @PostConstruct
    public void setBasePath(){

        this.getApiClient().setBasePath(baseRefGatewayBatchService);

    }


}

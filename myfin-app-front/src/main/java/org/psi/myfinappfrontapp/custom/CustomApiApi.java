package org.psi.myfinappfrontapp.custom;

import org.psi.myfinappfrontapp.api.api.ApiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class CustomApiApi extends ApiApi {

    @Value("${app_base_ref_gateway_back_service}")
    private String baseRefGatewayBackService;
 
    @PostConstruct
    public void setBasePath(){

        this.getApiClient().setBasePath(baseRefGatewayBackService);

    }

}

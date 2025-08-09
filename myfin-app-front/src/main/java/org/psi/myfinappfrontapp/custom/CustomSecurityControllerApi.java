package org.psi.myfinappfrontapp.custom;

import org.psi.myfinappfrontapp.api.api.SecurityControllerApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class CustomSecurityControllerApi extends SecurityControllerApi {

  @Value("${BASEREFGATE_BACKSERVICE:http://localhost:8889/MYFINAPPBACKSERVICE}")
    private String baseRefGatewayBackService;
 
    @PostConstruct
    public void setBasePath(){

        this.getApiClient().setBasePath(baseRefGatewayBackService);

    }

}

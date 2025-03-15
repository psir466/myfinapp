package org.psi.myfinappfrontapp.api;

import org.psi.myfinappfrontapp.ApiClient;

import org.psi.myfinappfrontapp.model.AccountDateSumDTO;
import org.psi.myfinappfrontapp.model.AccountHeaderDTO;
import java.time.LocalDate;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-03-08T19:30:38.128453200+01:00[Europe/Paris]", comments = "Generator version: 7.12.0")
public class ApiApi {
    private ApiClient apiClient;

    public ApiApi() {
        this(new ApiClient());
    }

    @Autowired
    public ApiApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    
    /**
     * Get account by id
     * Retrieves an account by its id
     * <p><b>200</b> - OK
     * @param id The id parameter
     * @return AccountHeaderDTO
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getAccountByIdRequestCreation(Long id) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new WebClientResponseException("Missing the required parameter 'id' when calling getAccountById", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "*/*"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<AccountHeaderDTO> localVarReturnType = new ParameterizedTypeReference<AccountHeaderDTO>() {};
        return apiClient.invokeAPI("/api/account/{id}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get account by id
     * Retrieves an account by its id
     * <p><b>200</b> - OK
     * @param id The id parameter
     * @return AccountHeaderDTO
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<AccountHeaderDTO> getAccountById(Long id) throws WebClientResponseException {
        ParameterizedTypeReference<AccountHeaderDTO> localVarReturnType = new ParameterizedTypeReference<AccountHeaderDTO>() {};
        return getAccountByIdRequestCreation(id).bodyToMono(localVarReturnType);
    }

    /**
     * Get account by id
     * Retrieves an account by its id
     * <p><b>200</b> - OK
     * @param id The id parameter
     * @return ResponseEntity&lt;AccountHeaderDTO&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<AccountHeaderDTO>> getAccountByIdWithHttpInfo(Long id) throws WebClientResponseException {
        ParameterizedTypeReference<AccountHeaderDTO> localVarReturnType = new ParameterizedTypeReference<AccountHeaderDTO>() {};
        return getAccountByIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get account by id
     * Retrieves an account by its id
     * <p><b>200</b> - OK
     * @param id The id parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getAccountByIdWithResponseSpec(Long id) throws WebClientResponseException {
        return getAccountByIdRequestCreation(id);
    }

    /**
     * Get all the account
     * Retrieves all the account
     * <p><b>200</b> - OK
     * @return List&lt;AccountHeaderDTO&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getAllAccountRequestCreation() throws WebClientResponseException {
        Object postBody = null;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "*/*"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<AccountHeaderDTO> localVarReturnType = new ParameterizedTypeReference<AccountHeaderDTO>() {};
        return apiClient.invokeAPI("/api/accounts", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get all the account
     * Retrieves all the account
     * <p><b>200</b> - OK
     * @return List&lt;AccountHeaderDTO&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<AccountHeaderDTO> getAllAccount() throws WebClientResponseException {
        ParameterizedTypeReference<AccountHeaderDTO> localVarReturnType = new ParameterizedTypeReference<AccountHeaderDTO>() {};
        return getAllAccountRequestCreation().bodyToFlux(localVarReturnType);
    }

    /**
     * Get all the account
     * Retrieves all the account
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;AccountHeaderDTO&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<AccountHeaderDTO>>> getAllAccountWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<AccountHeaderDTO> localVarReturnType = new ParameterizedTypeReference<AccountHeaderDTO>() {};
        return getAllAccountRequestCreation().toEntityList(localVarReturnType);
    }

    /**
     * Get all the account
     * Retrieves all the account
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getAllAccountWithResponseSpec() throws WebClientResponseException {
        return getAllAccountRequestCreation();
    }

    /**
     * Get sum of solde by month and year
     * Retrieves sum of solde of all accounts by month and year
     * <p><b>200</b> - OK
     * @param start The start parameter
     * @param end The end parameter
     * @return List&lt;AccountDateSumDTO&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getSumByDateRequestCreation(LocalDate start, LocalDate end) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'start' is set
        if (start == null) {
            throw new WebClientResponseException("Missing the required parameter 'start' when calling getSumByDate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'end' is set
        if (end == null) {
            throw new WebClientResponseException("Missing the required parameter 'end' when calling getSumByDate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("start", start);
        pathParams.put("end", end);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "*/*"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<AccountDateSumDTO> localVarReturnType = new ParameterizedTypeReference<AccountDateSumDTO>() {};
        return apiClient.invokeAPI("/api/accountSumDate/{start}/{end}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get sum of solde by month and year
     * Retrieves sum of solde of all accounts by month and year
     * <p><b>200</b> - OK
     * @param start The start parameter
     * @param end The end parameter
     * @return List&lt;AccountDateSumDTO&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<AccountDateSumDTO> getSumByDate(LocalDate start, LocalDate end) throws WebClientResponseException {
        ParameterizedTypeReference<AccountDateSumDTO> localVarReturnType = new ParameterizedTypeReference<AccountDateSumDTO>() {};
        return getSumByDateRequestCreation(start, end).bodyToFlux(localVarReturnType);
    }

    /**
     * Get sum of solde by month and year
     * Retrieves sum of solde of all accounts by month and year
     * <p><b>200</b> - OK
     * @param start The start parameter
     * @param end The end parameter
     * @return ResponseEntity&lt;List&lt;AccountDateSumDTO&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<AccountDateSumDTO>>> getSumByDateWithHttpInfo(LocalDate start, LocalDate end) throws WebClientResponseException {
        ParameterizedTypeReference<AccountDateSumDTO> localVarReturnType = new ParameterizedTypeReference<AccountDateSumDTO>() {};
        return getSumByDateRequestCreation(start, end).toEntityList(localVarReturnType);
    }

    /**
     * Get sum of solde by month and year
     * Retrieves sum of solde of all accounts by month and year
     * <p><b>200</b> - OK
     * @param start The start parameter
     * @param end The end parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getSumByDateWithResponseSpec(LocalDate start, LocalDate end) throws WebClientResponseException {
        return getSumByDateRequestCreation(start, end);
    }
}

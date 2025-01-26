package com.rk.temporal.utils;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


@Component
@AllArgsConstructor
@Slf4j
public class AppRestClientUtils {

    private final RestClient restClient;

    /**
     * Synchronously calls downstream remote service using Spring RestClient
     *
     * @param request         the request
     * @param requestHeaders  the request headers
     * @return the string
     */
    public String callDownstream(HttpMethod httpMethod , String url,HttpHeaders requestHeaders,Object request) {
        try{
            if(httpMethod == HttpMethod.GET) {
                return restClient.method(httpMethod)
                        .uri(url).headers(headers -> headers.addAll(requestHeaders))
                        .retrieve()
                        .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> res.getBody())
                        .body(String.class);
            }else{
                return restClient.method(httpMethod)
                        .uri(url).headers(headers -> headers.addAll(requestHeaders)).body(request)
                        .retrieve()
                        .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> res.getBody())
                        .body(String.class);
            }
        }catch (Exception e){
            log.error("callDownstream()-Error while calling downstream: {}",e.getMessage());
            throw e;
        }
    }
}

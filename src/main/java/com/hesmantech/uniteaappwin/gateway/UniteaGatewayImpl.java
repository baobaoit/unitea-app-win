package com.hesmantech.uniteaappwin.gateway;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;

import com.hesmantech.uniteaappwin.model.login.LoginRequest;
import com.hesmantech.uniteaappwin.model.login.LoginResponse;
import com.hesmantech.uniteaappwin.model.order.Order;
import com.hesmantech.uniteaappwin.model.order.ResponseOrderList;

import reactor.core.publisher.Mono;

@Service
public class UniteaGatewayImpl implements UniteaGateway {

  @Value("${unitea.api.baseUrl}")
  private String baseUrl;

  @Value("${unitea.api.username}")
  private String username;

  @Value("${unitea.api.password}")
  private String password;

  private static String token;

  @Override
  public Order getOrderDetailsById(long orderId) {
    getToken();
    WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();

    UriSpec<RequestBodySpec> uriSpec = webClient.method(HttpMethod.GET);
    RequestBodySpec bodySpec =
        uriSpec.uri("api/v1/private/orders/{orderId}?store=UNITEAANNARBOR", orderId);
    bodySpec.header("Authorization", String.format("Bearer %s", token));

    ResponseEntity<Order> response =
        bodySpec
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .acceptCharset(StandardCharsets.UTF_8)
            .retrieve()
            .toEntity(Order.class)
            .block();

    return response != null && HttpStatus.OK.equals(response.getStatusCode())
        ? response.getBody()
        : null;
  }

  @Override
  public boolean login(String loginId, String password) {
    try {
      WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();

      UriSpec<RequestBodySpec> uriSpec = webClient.post();
      RequestBodySpec bodySpec = uriSpec.uri("api/v1/private/login?store=UNITEAANNARBOR");

      RequestHeadersSpec<?> headersSpec =
          bodySpec.body(Mono.just(new LoginRequest(loginId, password)), LoginRequest.class);

      ResponseEntity<LoginResponse> response =
          headersSpec
              .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
              .accept(MediaType.APPLICATION_JSON)
              .acceptCharset(StandardCharsets.UTF_8)
              .retrieve()
              .toEntity(LoginResponse.class)
              .block();

      if (response != null
          && HttpStatus.OK.equals(response.getStatusCode())
          && response.getBody() != null) {
        token = response.getBody().getToken();
        return true;
      }
    } catch (Exception e) {
      token = null;
    }
    return false;
  }

  private void getToken() {
    try {
      WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();

      UriSpec<RequestBodySpec> uriSpec = webClient.post();
      RequestBodySpec bodySpec = uriSpec.uri("api/v1/private/login?store=UNITEAANNARBOR");

      RequestHeadersSpec<?> headersSpec =
          bodySpec.body(Mono.just(new LoginRequest(username, password)), LoginRequest.class);

      ResponseEntity<LoginResponse> response =
          headersSpec
              .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
              .accept(MediaType.APPLICATION_JSON)
              .acceptCharset(StandardCharsets.UTF_8)
              .retrieve()
              .toEntity(LoginResponse.class)
              .block();

      if (response != null
          && HttpStatus.OK.equals(response.getStatusCode())
          && response.getBody() != null) {
        token = response.getBody().getToken();
      }
    } catch (Exception e) {
      token = null;
    }
  }

  @Override
  public ResponseOrderList getOngoingOrders() {
    getToken();
    WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();

    UriSpec<RequestBodySpec> uriSpec = webClient.method(HttpMethod.GET);
    RequestBodySpec bodySpec =
        uriSpec.uri(
            "/api/v1/private/orders?store=UNITEAANNARBOR&lang=en&count=5&page=0&status=PROCESSED");
    bodySpec.header("Authorization", String.format("Bearer %s", token));

    ResponseEntity<ResponseOrderList> response =
        bodySpec
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .acceptCharset(StandardCharsets.UTF_8)
            .retrieve()
            .toEntity(ResponseOrderList.class)
            .block();

    return response != null && HttpStatus.OK.equals(response.getStatusCode())
        ? response.getBody()
        : null;
  }
}

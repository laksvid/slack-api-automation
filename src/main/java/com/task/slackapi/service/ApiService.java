package com.task.slackapi.service;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApiService {

  @Value("${token}")
  private String token;

  public Response getApiResponse(String url, Map<String, String> params) {

    Map<String, String> requestParam = new HashMap<>();
    requestParam.put("token", token);
    requestParam.putAll(params);
    return given()
        .relaxedHTTPSValidation()
        .queryParams(requestParam)
        .contentType(ContentType.JSON)
        .when()
        .post(url)
        .then()
        .log()
        .ifError()
        .statusCode(200)
        .extract()
        .response();
  }
}

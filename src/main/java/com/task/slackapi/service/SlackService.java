package com.task.slackapi.service;

import io.restassured.response.Response;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SlackService {
  private static final String CREATE_URL = "/conversations.create";
  private static final String JOIN_URL = "/conversations.join";
  private static final String RENAME_URL = "/conversations.rename";
  private static final String LIST_URL = "/conversations.list";
  private static final String ARCHIVE_URL = "/conversations.archive";
  private static final String GET_INFO_URL = "/conversations.info";

  @Value("${url}")
  private String url;

  @Autowired
  private ApiService apiService;

  public Response createChannel(String channelName) {
    Map<String, String> params = new HashMap<>();
    params.put("name", channelName);
    return apiService.getApiResponse(url + CREATE_URL, params);
  }

  public Response joinChannel(String channelId) {
    Map<String, String> params = new HashMap<>();
    params.put("channel", channelId);
    return apiService.getApiResponse(url + JOIN_URL, params);
  }

  public Response renameChannel(String channelId, String newName) {
    Map<String, String> params = new HashMap<>();
    params.put("channel", channelId);
    params.put("name", newName);
    return apiService.getApiResponse(url + RENAME_URL, params);
  }

  public Response listChannel() {
    return apiService.getApiResponse(url + LIST_URL, Collections.emptyMap());
  }

  public Response archiveChannel(String channelId) {
    Map<String, String> params = new HashMap<>();
    params.put("channel", channelId);
    return apiService.getApiResponse(url + ARCHIVE_URL, params);
  }

  public Response getChannelInfo(String channelId) {
    Map<String, String> params = new HashMap<>();
    params.put("channel", channelId);
    return apiService.getApiResponse(url + GET_INFO_URL, params);
  }
}

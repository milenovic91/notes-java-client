package com.kimi.notesclient;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimi.notesclient.model.Note;
import com.kimi.notesclient.model.NotesListResponse;
import com.kimi.notesclient.model.User;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class NotesService {
  public static CompletableFuture<User> login(String username, String password) {
    return CompletableFuture.supplyAsync(() -> {
      User result = null;
      try {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost method = new HttpPost("http://localhost:8000/user/login");
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(new HashMap<String, String>() {
          {
            put("username", username);
            put("password", password);
          }
        });
        method.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));
        CloseableHttpResponse response = client.execute(method);
        if (!"OK".equals(response.getStatusLine().getReasonPhrase())) {
          throw new Exception(response.getStatusLine().toString());
        }
        HttpEntity entity = response.getEntity();
        if (entity == null) {
          throw new Exception("missing entity");
        }
        result = mapper.readValue(EntityUtils.toString(entity), User.class);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      return result;
    });
  }

  public static Object getNotes(String token, int page, int pageSize) {
    ObjectMapper mapper = new ObjectMapper();
    Object result = null;
    CloseableHttpClient client = HttpClients.createDefault();
    HttpGet method = new HttpGet(String.format("http://localhost:8000/notes?page=%d&limit=%d", page, pageSize));
    method.addHeader("Authorization", "Bearer " + token);
    try {
      CloseableHttpResponse response = client.execute(method);
      if (!"OK".equals(response.getStatusLine().getReasonPhrase())) {
        throw new Exception("invalid response");
      }
      String responseString = EntityUtils.toString(response.getEntity());
      result = mapper.readValue(responseString, NotesListResponse.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  public static Note getById(int id) {
    return null;
  }
}

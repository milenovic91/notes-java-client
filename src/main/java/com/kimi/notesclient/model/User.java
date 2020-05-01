package com.kimi.notesclient.model;

public class User {
  private String username;
  private String token;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String toString() {
    return "User [token=" + token + ", username=" + username + "]";
  }
}

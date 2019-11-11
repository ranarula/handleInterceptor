package com.example.demo.dto;

public class AuthResponse {

private String result;
private Throwable error;

  public AuthResponse(String result) {
    this.result = result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getResult() {
    return result;
  }

  public Throwable getError() {
    return error;
  }

  public void setError(Throwable error) {
    this.error = error;
  }
}

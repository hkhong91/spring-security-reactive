package com.example.demo.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
public abstract class AbstractControllerTest {

  protected static final String JWT = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpZCI6IjYxMDUzYjc4MWY4ZWZjMjc2OGJmMjAwMCIsImV4cCI6MTYyODk1MTY5NSwiaWF0IjoxNjI4ODY1Mjk1fQ.4AsSphvPU_hTH941uzIdG2GHclBa1e11_ySx1QTjNSu0oGgkUPGjIAyWjRHUeqjq-qlHfyNIaz5DT2N0fLpjAA";

  @Autowired
  protected WebTestClient client;
}

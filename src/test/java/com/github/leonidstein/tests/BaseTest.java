package com.github.leonidstein.tests;

import com.github.leonidstein.services.FileService;
import com.github.leonidstein.services.GameService;
import com.github.leonidstein.services.StatusCodeService;
import com.github.leonidstein.services.TrainService;
import com.github.leonidstein.services.UserService;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.github.leonidstein.config.ConfigManager.config;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.defaultParser;
import static io.restassured.RestAssured.port;
import static io.restassured.parsing.Parser.JSON;

@ExtendWith({AllureJunit5.class})
public abstract class BaseTest {

    protected UserService userService;
    protected FileService fileService;
    protected GameService gameService;
    protected StatusCodeService statusCodeService;
    protected TrainService trainService;

    @BeforeAll
    static void setUp() {

        baseURI = config().baseURI();
        basePath = config().basePath();
        port = config().basePort();
        defaultParser = JSON;

        RestAssured.config = RestAssured.config().httpClient(
                HttpClientConfig.httpClientConfig()
                                .setParam("http.socket.timeout", 30_000)
                                .setParam("http.connection.timeout", 30_000)
                                .setParam("http.connection-manager.timeout", 30_000)
        );
    }

    @BeforeEach
    void initService() {

        this.userService = new UserService();
        this.fileService = new FileService();
        this.gameService = new GameService();
        this.statusCodeService = new StatusCodeService();
        this.trainService = new TrainService();
    }
}

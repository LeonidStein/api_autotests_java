package com.github.leonidstein.utils;

import io.qameta.allure.restassured.AllureRestAssured;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class ReportTpl {

    private final static String REQUEST_TEMPLATE_FILE = "request.ftl";
    private final static String RESPONSE_TEMPLATE_FILE = "response.ftl";

    private static volatile ReportTpl instance;

    public AllureRestAssured withTemplates() {

        return new AllureRestAssured().setRequestTemplate(REQUEST_TEMPLATE_FILE)
                                      .setResponseTemplate(RESPONSE_TEMPLATE_FILE);
    }

    public static ReportTpl getInstance() {

        if (instance == null) {
            synchronized (ReportTpl.class) {
                if (instance == null) {
                    instance = new ReportTpl();
                }
            }
        }
        return instance;
    }

}

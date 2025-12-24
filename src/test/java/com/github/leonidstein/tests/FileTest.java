package com.github.leonidstein.tests;

import com.github.leonidstein.utils.annotations.TestType;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static com.github.leonidstein.asserts.conditions.ConditionManager.hasDownloadedImage;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasLastUploadedFile;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasMessage;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasStatus;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasStatusCode;
import static com.github.leonidstein.constants.info.Message.FILE_UPLOADED_TO_SERVER;
import static com.github.leonidstein.constants.info.Status.SUCCESS;
import static com.github.leonidstein.utils.annotations.TestType.Type.NEGATIVE;
import static com.github.leonidstein.utils.annotations.TestType.Type.POSITIVE;
import static io.qameta.allure.SeverityLevel.MINOR;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;

@Epic("API. files-controller")
@Feature("API. Скачивание и загрузка файла")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public final class FileTest extends BaseTest {

    @DisplayName("Загрузка файла в формате JPEG")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/36")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(MINOR)
    @TestType(POSITIVE)
    @Test
    public void testDownloadFile() {

        fileService
                .makeRequest()
                    .downloadFile()
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasDownloadedImage());
    }

    @DisplayName("Загрузка файла меньше, чем 3 МБ")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/37")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(MINOR)
    @TestType(POSITIVE)
    @Test
    public void testUploadFileLessThanThreeMByte() {

        fileService
                .makeRequest()
                    .uploadFileLessThanThreeMB()
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasStatus(SUCCESS.getStatus()))
                    .should(hasMessage(FILE_UPLOADED_TO_SERVER.getMessage()));
    }

    @Issue("58981290")
    @DisplayName("Загрузка файла больше, чем 3 МБ")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/38")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(MINOR)
    @TestType(NEGATIVE)
    @Test
    public void testUploadFileMoreThanThreeMByte() {

        fileService
                .makeRequest()
                    .uploadFileMoreThanThreeMB()
                .checkResponse()
                    .should(hasStatusCode(SC_BAD_REQUEST));
    }

    @DisplayName("Проверка последнего загруженного файла")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/39")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(MINOR)
    @TestType(POSITIVE)
    @Test
    public void testDownloadLastUploadedFile() {

        fileService
                .makeRequest()
                    .uploadFileLessThanThreeMB()
                .checkResponse()
                    .should(hasStatusCode(SC_OK));

        fileService
                .makeRequest()
                    .downloadLastUploadedFile()
                .checkResponse()
                    .should(hasLastUploadedFile());
    }

}

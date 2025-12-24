package com.github.leonidstein.services;

import com.github.leonidstein.asserts.AssertableResponse;
import com.github.leonidstein.utils.annotations.GET;
import com.github.leonidstein.utils.annotations.POST;
import com.github.leonidstein.utils.annotations.PUT;
import io.qameta.allure.Step;
import lombok.SneakyThrows;

import java.io.File;

import static com.github.leonidstein.constants.endpoint.FileEndpoint.DOWNLOAD_IMAGE;
import static com.github.leonidstein.constants.endpoint.FileEndpoint.DOWNLOAD_LAST_UPLOADED;
import static com.github.leonidstein.constants.endpoint.FileEndpoint.UPLOAD_FILE;
import static com.github.leonidstein.utils.Helper.getFileFromResources;

public final class FileService extends BaseService implements Requestable<FileService> {

    private static final String FIELD_NAME = "file";

    @Override
    public FileService makeRequest() {

        return this;
    }

    @GET
    @Step("Скачивание последнего загруженного файла")
    public AssertableResponse downloadLastUploadedFile() {

        return makeGetRequest(DOWNLOAD_LAST_UPLOADED.getEndpoint());
    }

    @PUT
    @Step("Скачивание файла в формате JPEG")
    public AssertableResponse downloadFile() {

        return makeGetRequest(DOWNLOAD_IMAGE.getEndpoint());
    }

    @POST
    @SneakyThrows
    @Step("Загрузка файла меньше 3 МБ")
    public AssertableResponse uploadFileLessThanThreeMB() {

        final String resource = "testdata/uploadedFileLessThan3MB.jpeg";
        final File file = getFileFromResources(resource);

        return makePostRequest(UPLOAD_FILE.getEndpoint(), FIELD_NAME, file);
    }

    @POST
    @SneakyThrows
    @Step("Загрузка файла больше 3 Мбайт")
    public AssertableResponse uploadFileMoreThanThreeMB() {

        final String resource = "testdata/uploadedFileMoreThan3MB.jpeg";
        final File file = getFileFromResources(resource);

        return makePostRequest(UPLOAD_FILE.getEndpoint(), FIELD_NAME, file);
    }
}

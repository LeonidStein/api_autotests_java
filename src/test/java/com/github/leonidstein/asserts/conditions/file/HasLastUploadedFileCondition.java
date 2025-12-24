package com.github.leonidstein.asserts.conditions.file;

import com.github.leonidstein.asserts.conditions.Condition;
import io.qameta.allure.Attachment;
import io.restassured.response.ValidatableResponse;
import lombok.SneakyThrows;

import java.io.File;

import static com.github.leonidstein.asserts.conditions.Extractor.extractByteArray;
import static com.github.leonidstein.utils.Helper.getFileFromResources;
import static java.nio.file.Files.readAllBytes;
import static org.assertj.core.api.Assertions.assertThat;

public final class HasLastUploadedFileCondition implements Condition, AttachableFile {

    private final static String RESOURCE_PATH = "testdata/uploadedFileLessThan3MB.jpeg";

    @Override
    @Attachment(value = "Загруженный файл", type = "image/png")
    public byte[] attachUploadedlFile(byte[] bytes) {

        return bytes;
    }

    @Override
    @Attachment(value = "Скачанный файл", type = "image/png")
    public byte[] attachDownloadedlFile(byte[] bytes) {

        return bytes;
    }

    @Override
    @SneakyThrows
    public void check(final ValidatableResponse response) {

        final File expectedFile = getFileFromResources(RESOURCE_PATH);

        final byte[] actualFileInByte = extractByteArray(response);
        final byte[] expectedFileInByte = readAllBytes(expectedFile.toPath());

        attachUploadedlFile(expectedFileInByte);
        attachDownloadedlFile(actualFileInByte);

        assertThat(actualFileInByte.length).as("Проверка веса актуального файла в байтах")
                                           .isGreaterThan(0)
                                           .isEqualTo(expectedFileInByte.length);
    }
}


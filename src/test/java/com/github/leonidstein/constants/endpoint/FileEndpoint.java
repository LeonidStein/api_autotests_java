package com.github.leonidstein.constants.endpoint;

import com.github.leonidstein.utils.annotations.GET;
import com.github.leonidstein.utils.annotations.POST;
import lombok.Getter;

@Getter
public enum FileEndpoint {

    @GET
    DOWNLOAD_IMAGE("/files/download"),

    @GET
    DOWNLOAD_LAST_UPLOADED("/files/downloadLastUploaded"),

    @POST
    UPLOAD_FILE("/files/upload");

    private final String endpoint;

    FileEndpoint(String endpoint) {

        this.endpoint = endpoint;
    }
}

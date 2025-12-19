package com.github.leonidstein.asserts.conditions.file;

public interface AttachableFile {

    byte[] attachDownloadedlFile(byte[] bytes);

    byte[] attachUploadedlFile(byte[] bytes);
}

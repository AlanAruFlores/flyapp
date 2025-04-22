package com.flybook.librovuelo.response;

// Clase para subir respuestas HTTP
public class UploadResponse {
    private final String fileName;

    public UploadResponse(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}

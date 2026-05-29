package com.prototype.honda.api.utils;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileMultipart implements MultipartFile {

    private final Path path;
    private final String contentType;

    public FileMultipart(Path path) {

        this.path = path;

        try {

            this.contentType =
                    Files.probeContentType(path);

        } catch (IOException exception) {

            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getName() {
        return path.getFileName().toString();
    }

    @Override
    public String getOriginalFilename() {
        return path.getFileName().toString();
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {

        try {

            return Files.size(path) == 0;

        } catch (IOException exception) {

            throw new RuntimeException(exception);
        }
    }

    @Override
    public long getSize() {

        try {

            return Files.size(path);

        } catch (IOException exception) {

            throw new RuntimeException(exception);
        }
    }

    @Override
    public byte[] getBytes() throws IOException {
        return Files.readAllBytes(path);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return Files.newInputStream(path);
    }

    @Override
    public void transferTo(File dest)
            throws IOException, IllegalStateException {

        Files.copy(
                path,
                dest.toPath()
        );
    }
}

package com.prototype.honda.api.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

public class Utils {

    private static final String BUCKET_DIR =
            System.getProperty("user.dir")
                    + "/src/main/resources/bucket/";

    public static String saveFile(MultipartFile file) {

        try {

            // Nome original
            String originalFilename = file.getOriginalFilename();

            // Extensão do arquivo
            String extension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(
                        originalFilename.lastIndexOf(".")
                );
            }

            // Novo nome UUID
            String newFilename = UUID.randomUUID() + extension;

            // Caminho final
            Path uploadPath = Paths.get(BUCKET_DIR);

            // Cria pasta se não existir
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Caminho completo do arquivo
            Path filePath = uploadPath.resolve(newFilename);

            // Salva arquivo
            file.transferTo(filePath.toFile());

            // Retorna nome salvo
            return newFilename;

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo", e);
        }
    }

    public static String getFile(String filename) {

        try {

            if (filename == null || filename.isBlank()) {
                return null;
            }

            Path filePath = Paths.get(BUCKET_DIR)
                    .resolve(filename)
                    .normalize();

            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new RuntimeException("Arquivo não encontrado");
            }

            // lê bytes do arquivo
            byte[] bytes = Files.readAllBytes(filePath);

            // converte para base64
            String base64 = Base64.getEncoder()
                    .encodeToString(bytes);

            // pega extensão
            String extension = getExtension(filename);

            // devolve formato completo para frontend usar direto no img src
            return "data:image/"
                    + extension
                    + ";base64,"
                    + base64;

        } catch (MalformedURLException e) {
            throw new RuntimeException("Erro ao carregar arquivo", e);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao converter arquivo", e);
        }
    }

    private static String getExtension(String filename) {

        if (filename == null || !filename.contains(".")) {
            return "png";
        }

        return filename.substring(
                filename.lastIndexOf(".") + 1
        );
    }
}

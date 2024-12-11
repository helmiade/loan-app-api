package com.enigmacamp.livecode_loan_app.Controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

@RestController
public class ImageController {

    // Path ke folder gambar yang akan disajikan
    private final Path imagePath = Paths.get("/home/enigma/Documents/Enigmacamp/livecode-loan-app/src/main/resources/asset/images/");

    // Endpoint untuk mengakses gambar
    @GetMapping("/assets/images/{fileName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        try {
            // Menentukan file berdasarkan nama yang diberikan
            Path file = imagePath.resolve(fileName).normalize();
            Resource resource = new UrlResource(file.toUri());

            // Memeriksa apakah file ada
            if (!resource.exists()) {
                return ResponseEntity.notFound().build(); // Mengembalikan 404 jika file tidak ditemukan
            }

            // Mendeteksi tipe konten berdasarkan ekstensi file
            String contentType = Files.probeContentType(file);

            if (contentType == null || !contentType.startsWith("image")) {
                return ResponseEntity.badRequest().body(null); // Mengembalikan 400 jika bukan gambar
            }

            // Mengembalikan file dengan header yang sesuai untuk gambar
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType)) // Menggunakan tipe konten yang terdeteksi
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity.status(500).body(null); // Mengembalikan 500 jika ada error
        }
    }
}

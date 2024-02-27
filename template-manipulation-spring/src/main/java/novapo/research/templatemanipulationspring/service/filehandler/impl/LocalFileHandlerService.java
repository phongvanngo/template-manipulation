package novapo.research.templatemanipulationspring.service.filehandler.impl;

import novapo.research.templatemanipulationspring.service.filehandler.FileHandlerService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;


@Service
@Primary
@Slf4j
public class LocalFileHandlerService implements FileHandlerService {
    private final static String PROCCESSING_FILE_LOCATION = "/processing-file/";

    @Override
    public InputStream get(String filePath) {
        Path file = Paths.get(filePath);
        if (Files.exists(file)) {
            // Load the file as a resource
            Resource resource = new FileSystemResource(file);

            // Get the input stream from the resource
            InputStream inputStream = null;
            try {
                inputStream = resource.getInputStream();
            } catch (IOException e) {
                log.error("Error when get file");
            }

            // Return the input stream as a ResponseEntity
            return inputStream;
        } else {
            // Return a 404 Not Found response if the file does not exist
            return null;
        }
    }


    @Override
    public void save(ByteArrayOutputStream byteArrayOutputStream, String location, String fileName) throws IOException {
        String filePath = location + "/" + fileName;
        // Create the directory if it doesn't exist
        Path directoryPath = Paths.get(location);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            byteArrayOutputStream.writeTo(fos);
        }
    }
    @Override
    public void save(byte[] data, String location, String fileName) throws IOException {
        String filePath = location + "/" + fileName;
        // Create the directory if it doesn't exist
        Path directoryPath = Paths.get(location);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(data); // Directly write the byte array to the file
        }
    }

    @Override
    public InputStream getFromResource(String filePath) {
        ClassPathResource resource = new ClassPathResource(filePath);
        try {
            return resource.getInputStream();
        } catch (IOException e) {
            log.error("Error when get file");
            return null;
        }
    }

    @Override
    public String save(MultipartFile file) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            FileCopyUtils.copy(file.getInputStream(), byteArrayOutputStream);
        } catch (IOException e) {
            log.error("#Read file error {}", e.getMessage());
            return null;
        }

        String[] names = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        String uniqueFileName = names[0] + " " + UUID.randomUUID().toString().substring(0, 7) + ".xlsx";

        try {
            this.save(byteArrayOutputStream, PROCCESSING_FILE_LOCATION, uniqueFileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return PROCCESSING_FILE_LOCATION + uniqueFileName;
    }


    @Override
    public Resource save(InputStream inputStream, String fileName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            FileCopyUtils.copy(inputStream, byteArrayOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] names = fileName.split("\\.");
        String uniqueFileName = names[0] + " " + UUID.randomUUID().toString().substring(0, 7) + ".xlsx";

        try {
            this.save(byteArrayOutputStream, "proccessing_files", uniqueFileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Resource getResource(String filePath) {
        Path file = Paths.get(filePath);
        if (Files.exists(file)) {
            return new FileSystemResource(file);
        } else {
            return null;
        }
    }

    @Override
    public void deleteResource(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                log.info("File {} is deleted successfully.", filePath);
            } else {
                log.info("Failed to delete the file {}.", filePath);
            }
        } else {
            log.info("File {} does not exist.", filePath);
        }
    }
}

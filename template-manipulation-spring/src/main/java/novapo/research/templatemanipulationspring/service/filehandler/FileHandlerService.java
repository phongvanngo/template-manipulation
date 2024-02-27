package novapo.research.templatemanipulationspring.service.filehandler;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public interface FileHandlerService {
    InputStream get(String filePath);
    InputStream getFromResource(String filePath);
    void save(ByteArrayOutputStream byteArrayOutputStream, String location, String fileName) throws IOException;
    void save(byte[] data, String location, String fileName) throws IOException;
    String save(MultipartFile file);
    Resource save(InputStream inputStream, String fileName);
    Resource getResource(String filePath);
    void deleteResource(String filePath);

}
package cl.proyecto.clientes.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

public interface IUploadFileService {

    public Resource cargarArchivo(String nombreFoto) throws MalformedURLException;

    public String copiarArchivo(MultipartFile archivo) throws IOException;

    public boolean eliminarArchivo(String nombreFoto);

}

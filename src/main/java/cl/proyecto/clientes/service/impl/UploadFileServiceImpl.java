package cl.proyecto.clientes.service.impl;

import cl.proyecto.clientes.service.IUploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements IUploadFileService {
    private static final Logger logger = LoggerFactory.getLogger(UploadFileServiceImpl.class);

    @Value("${cliente.foto.defecto}")
    private String fotoDefecto;
    @Value("${cliente.ruta.defecto}")
    private String rutaDefecto;
    @Value("${cliente.ruta.uploads}")
    private String carpetaUploads;


    @Override
    public Resource cargarArchivo(String nombreFoto) throws MalformedURLException {
        Path rutaArchivo = getPath(carpetaUploads, nombreFoto);
        Resource recurso = null;
        recurso = new UrlResource(rutaArchivo.toUri());


        if(!recurso.exists() && !recurso.isReadable()){
            rutaArchivo = getPath(rutaDefecto, fotoDefecto);
            recurso = new UrlResource(rutaArchivo.toUri());
        }
        return recurso;
    }

    @Override
    public String copiarArchivo(MultipartFile archivo) throws IOException {
        //UUID sirve para generar valores aleatorios, en este caso para que el nombre de la imagen sea unico.
        String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "");

        //creo ruta archivo donde lo voy a guardar, ruta relativa si esta en el proyecto,
        // ruta absoluta si esta en directorio externo
        Path rutaArchivo = getPath(carpetaUploads, nombreArchivo);

        Files.copy(archivo.getInputStream(), rutaArchivo);
        return nombreArchivo;
    }

    @Override
    public boolean eliminarArchivo(String nombreFoto) {
        Path rutaArchivoAntiguo = null;
        if(nombreFoto != null){
            rutaArchivoAntiguo = getPath(carpetaUploads, nombreFoto);
            logger.debug("rutaArchivoAntiguo: " + rutaArchivoAntiguo);
        }
        if(rutaArchivoAntiguo != null){
            try {
                Files.delete(rutaArchivoAntiguo);
            } catch (IOException e) {
                logger.error("mensaje" , "Error al eliminar imagen antigua: ".concat(nombreFoto));
                logger.error("error" , "Descripcion error: ".concat(e.getCause().getMessage()));
                return false;
            }
        }
        return true;
    }

    private Path getPath(String directorio, String nombreFoto) {
        return Paths.get(directorio).resolve(nombreFoto).toAbsolutePath();
    }
}

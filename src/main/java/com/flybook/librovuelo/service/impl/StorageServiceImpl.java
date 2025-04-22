package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.exceptions.FileException;
import com.flybook.librovuelo.model.AdjuntoDocumentacion;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

import static com.google.common.io.Files.getFileExtension;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path fileStorageLocation;
    private final String uploadDir;

    @Autowired
    public StorageServiceImpl(Environment env) {
        // Accedo al valor de archivo de un archivo de propiedades. El primer parametro es una cadena
        // que contiene el nombre de la propiedad, si no existe devuelve un valor por defecto
        // asignado en el segundo parametro.
        this.uploadDir = env.getProperty("app.file.upload-dir", "./uploads/files");
        // Obtenemos la ruta absoulta usando un método estático y eliminando elementos redundantes ("..", ".")
        this.fileStorageLocation = Paths.get(this.uploadDir)
                .toAbsolutePath().normalize();

        try {
            // Creo el directorio y le paso la ubicacion donde quiero que se almacenen mis archivos
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "No se pudo crear el directorio donde se almacenarán los archivos cargados.", ex);
        }
    }

    /*
    @Override
    public AdjuntoDocumentacion storeFileDocumentacion(MultipartFile file, String subFolder, User user) {

        String fileName = file.getOriginalFilename();
        try {
            if (fileName.contains("..")) {
                throw new FileException("El nombre de archivo contiene una secuencia de ruta no válida!" + fileName);
            }


            Path directorio = this.fileStorageLocation.resolve(this.fileStorageLocation + "/" + user.getLegajo()  + subFolder);
            Path targetLocation = this.fileStorageLocation.resolve(directorio.toString()).resolve(fileName)  ;

            Files.createDirectories(targetLocation);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String extension = getFileExtension(fileName);
            Long tamanio = Files.size(targetLocation);
            byte[] data = Files.readAllBytes(targetLocation);


            String path = this.uploadDir.substring(1) + "/" + user.getLegajo()  + subFolder+ "/" + fileName;
            String path2 = directorio.toString()+ "/" + fileName;

            AdjuntoDocumentacion adjuntoDoc = new AdjuntoDocumentacion();
            adjuntoDoc.setFecha(LocalDate.now());
            adjuntoDoc.setNombre(fileName);
            adjuntoDoc.setExtension(extension);
            adjuntoDoc.setTamanio(tamanio);
            adjuntoDoc.setData(data);
            adjuntoDoc.setPath(path);

            return adjuntoDoc;
        } catch (IOException | FileException ex) {
            throw new RuntimeException("No se pudo almacenar el archivo " + fileName + ". ¡Inténtalo de nuevo!", ex);
        }
    }


     */
}

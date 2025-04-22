package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.exceptions.FileException;
import com.flybook.librovuelo.model.ImageProfile;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.repository.ImageProfileRepository;
import com.flybook.librovuelo.service.ImageProfileService;
import com.flybook.librovuelo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class imageProfileServiceImpl implements ImageProfileService {
    private final Path fileStorageLocation ;
    @Autowired
    private ImageProfileRepository imageProfileRepository;

    private final String uploadDir;
    @Autowired
    UserService userService;

    @Autowired
    public imageProfileServiceImpl(Environment env) {
        this.uploadDir = env.getProperty("app.file.upload-img-dir", "./uploads/files");
        this.fileStorageLocation = Paths.get(this.uploadDir)
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "No se pudo crear el directorio donde se almacenarán las imagenes.", ex);
        }
    }

    @Override
    public void save(ImageProfile imageProfile) {
        this.imageProfileRepository.save(imageProfile);
    }


    public String buildName(User user) {
        String nameImage = "";
        if (user.getUsername()!= null &&!user.getUsername().isEmpty()) {
            nameImage+=user.getUsername()+"-";
        }
        if (user.getLegajo()!= null) {
            nameImage+= user.getLegajo().toString()+"-";
        }
        if (user.getApellido()!= null) {
            nameImage+= user.getApellido();
        }
        return nameImage;
    }

    @Override
    public String storeFile(MultipartFile file, Long userId) {
        User user = this.userService.getById(userId);
/*
        String fileName=file.getOriginalFilename();
*/      String extension = getFileExtension(file);
        String fileName=buildName(user).concat(".").concat(extension);
        try {
            assert fileName != null;
            if (fileName.contains("..")) {
                throw new FileException(
                        "El nombre contiene una secuencia de ruta no válida!" + fileName);
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);


            String path = this.uploadDir.substring(1) + "/" + fileName+"";

            ImageProfile imageProfile = new ImageProfile();
            imageProfile.setFileName(fileName);
            imageProfile.setPath(path);
            save(imageProfile);
            user.setImageProfile(imageProfile);
            userService.save(user);
            return fileName;
        } catch (IOException | FileException ex) {
            throw new RuntimeException("No se pudo almacenar la imagen " + fileName + ". ¡Inténtalo de nuevo!", ex);
        }
    }

    @Override
    public void deleteFile(String nombre) {
        try {
            Path path = this.fileStorageLocation.resolve(nombre);
            Files.delete(path);
        } catch (IOException ex) {
            throw new RuntimeException("No se pudo eliminar el archivo. ¡Inténtalo de nuevo!", ex);
        }

}

    public String getFileExtension(MultipartFile  file) {
        String fileName;
        if (file == null) {
            return null;
        }
        fileName = file.getOriginalFilename();
        // Devuelvo una matriz de strings divididos por un punto
        String[] fileNameParts = fileName.split("\\.");

        // Retorna el tamaño del string
        return fileNameParts[fileNameParts.length - 1];
    }

    @Override
    public ImageProfile getById(Long id) {
        return this.imageProfileRepository.getById(id);
    }
}

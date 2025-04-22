package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.ImageProfile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageProfileService {

    void save(ImageProfile imageProfile);


    String storeFile(MultipartFile file, Long userId);

    void deleteFile(String nombre);

    ImageProfile getById(Long id);
}

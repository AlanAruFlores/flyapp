package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.AdjuntoDocumentacion;
import com.flybook.librovuelo.model.Documentacion;
import com.flybook.librovuelo.model.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

// Interfaz para el almacenamiento de archivos
public interface FileStorageService {

    String getFileExtension(String fileName);



    AdjuntoDocumentacion storeFileDocumentacion(MultipartFile file, Documentacion documentacion);

  //  String storeFileDocumentacion(MultipartFile file, Long documentacionId);

    Resource load(String filename);

    void deleteFile(String nombre);

    void deleteFolder(String pathName, Documentacion documentacion);

    AdjuntoDocumentacion updateStoreFile(MultipartFile file, Documentacion documentacionId);

    ResponseEntity download(Long id);


    // HttpServletResponse: HttpServletResponse permite que un servlet formule una respuesta HTTP a un cliente.
    // El objeto de respuesta encapsula toda la información que se devolverá del servidor al cliente.
    void showAttachment(Long id, HttpServletResponse response);
}

// MultipartFile: Una representación de un archivo cargado recibido en una solicitud de varias partes.
// El contenido del archivo se almacena en la memoria o temporalmente en el disco. En cualquier caso, el usuario
// es responsable de copiar el contenido del archivo a un nivel de sesión o almacenamiento persistente
// como y si lo desea. El almacenamiento temporal se borrará al final del procesamiento de la solicitud.
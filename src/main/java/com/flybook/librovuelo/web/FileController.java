package com.flybook.librovuelo.web;

import com.flybook.librovuelo.model.AdjuntoDocumentacion;
import com.flybook.librovuelo.response.UploadResponse;
import com.flybook.librovuelo.service.AdjuntoDocumentacionService;
import com.flybook.librovuelo.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
// @RequestMapping({"/files"})
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private AdjuntoDocumentacionService adjuntoDocumentacionService;
/*
    @PostMapping("/upload/documentacion/{documentacionId}")
    public ResponseEntity<UploadResponse> uploadFile( // Para manejar respuestas HTTP
            @PathVariable("documentacionId") Long documentacionId,
            @RequestParam(name = "file", required = false) MultipartFile file){

        String fileName = fileStorageService.storeFileDocumentacion(file, documentacionId);
        UploadResponse uploadResponse = new UploadResponse(fileName);

        return ResponseEntity.ok().body(uploadResponse);
    }

    @GetMapping("/documentacion/{documentacionId}/adjuntoDocumentacion/{adjuntoDocumentacionId}/eliminar")
    public String deleteFile(@PathVariable("documentacionId") Long documentacionId, @PathVariable("adjuntoDocId") Long adjuntoDocId) {
        Optional<AdjuntoDocumentacion> adjuntoOptional = this.adjuntoDocumentacionService.findById(adjuntoDocId);

        if(adjuntoOptional.isPresent()){
            AdjuntoDocumentacion adjuntoDoc = adjuntoOptional.get();
            this.fileStorageService.deleteFile(adjuntoDoc.getNombre());
            this.adjuntoDocumentacionService.delete(adjuntoDoc);
        }

        return "redirect:/documentacion/editar/" + documentacionId;
    }
*/
}

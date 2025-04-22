package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.conf.MailConfig;
import com.flybook.librovuelo.model.AdjuntoDocumentacion;
import com.flybook.librovuelo.model.Documentacion;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.repository.AdjuntoDocumentacionRepository;
import com.flybook.librovuelo.repository.DocumentacionRepository;
import com.flybook.librovuelo.service.AdjuntoDocumentacionService;
import com.flybook.librovuelo.service.DocumentacionService;
import com.flybook.librovuelo.service.FileStorageService;
import com.flybook.librovuelo.service.StorageService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.*;

@Service
public class DocumentacionServiceImpl implements DocumentacionService {

    @Autowired
    private DocumentacionRepository documentacionRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private AdjuntoDocumentacionService adjuntoDocumentacionService;

    @Autowired
    MailConfig mailConfigSender;

    @Override
    public void save(Documentacion documentacion) {
        documentacionRepository.save(documentacion);
    }

    @Override
    public void guardarDocumentacionConArchivo(Documentacion documentacion, MultipartFile file, User user) {
        Documentacion documentacionNueva = this.documentacionRepository.save(documentacion);
        AdjuntoDocumentacion adjuntoDocumentacion = this.fileStorageService.storeFileDocumentacion(file,documentacionNueva);
        adjuntoDocumentacion.setDocumentacion(documentacion);
        this.adjuntoDocumentacionService.save(adjuntoDocumentacion);
        this.documentacionRepository.save(documentacionNueva);
    }

    @Override
    @Transactional
    public void delete(Documentacion documentacion) {
        List<AdjuntoDocumentacion> adjuntos = this.adjuntoDocumentacionService.findAllByDocumentacion(documentacion);
        //Elimino todos los archivos almacenados en el servidor de la documentacion
        adjuntos.forEach(adj->this.fileStorageService.deleteFile(adj.getPath()));
        if(adjuntos.size() > 0)
            this.fileStorageService.deleteFolder(adjuntos.get(0).getPath(), documentacion);


        this.adjuntoDocumentacionService.deleteAdjuntosByDocumentacion(documentacion);
        documentacionRepository.delete(documentacion);
    }

    @Override
    public Documentacion findByUsername(User username) {
        return documentacionRepository.findByUser(username);
    }

    @Override
    public Optional<Documentacion> findById(Long idDocumentacion) {
        return documentacionRepository.findById(idDocumentacion);
    }

    @Override
    public List<Documentacion> findAll() {
        return documentacionRepository.findAll();
    }

    @Override
    public List<Documentacion> findAll(Sort sort) {
        return documentacionRepository.findAll(sort);
    }

    @Override
    public Documentacion findDocumentacionById(Long id) {
        return documentacionRepository.findDocumentacionById(id);
    }

    @Override
    public List<Documentacion> findAllByUsername(Sort sort, User user) {
        return documentacionRepository.findAllByUser(sort, user);
    }

    @Override
    public Documentacion getById(Long id) {
        return documentacionRepository.getById(id);
    }

    @Override
    public List<Documentacion> findDocumentacionsById(Long id) {
        return documentacionRepository.findDocumentacionsById(id);
    }

    @Override
    public void updateDocumentacion(Documentacion documentacion, MultipartFile file) {
        if(!file.getOriginalFilename().isBlank() && file.getSize() != 0){
            AdjuntoDocumentacion adjuntoDocumentacion=this.fileStorageService.storeFileDocumentacion(file,documentacion);
            adjuntoDocumentacion.setDocumentacion(documentacion);
            this.adjuntoDocumentacionService.save(adjuntoDocumentacion);
        }

        this.save(documentacion);
    }

    @Override
    public void updateDocumentacion(Documentacion documentacion){
        this.save(documentacion);
    }

    //public void sendEmailAllDocumentacionIfAlmostIsExpired() {}

    @Scheduled(cron = "0 0 7 * * ?")
    //@Scheduled(cron = "1/20 * * * * ?")
    @Async
    @Override
    public void sendEmailAllDocumentacionIfAlmostIsExpired() {
        List<Documentacion> documentacionList  = getAllDocumentacionAlmostIsExpired();

        documentacionList.forEach((doc) -> {
            String linkToSend = "http://localhost:8080/fbtripulantes/documentacion/desactivar-notificacion/"+doc.getId();
            try {
                this.mailConfigSender.sendEmail(doc.getUser().getMail(),"Aviso de Expiracion",
                        "Nos gustaría recordarle que la fecha de expiración " +
                                "de su Documentacion está próxima a vencer el " + doc.getFechaDeVencimiento()+
                                ". Si desea no seguir viendo este mensaje haga click en: "+ linkToSend);

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private List<Documentacion> getAllDocumentacionAlmostIsExpired(){
        List<Documentacion> documentacion = this.documentacionRepository.findAllDocumentacionByNotificacionActivadaIsTrueAndFechaDeVencimientoGreaterThanAndFechaDeVencimientoIsLessThanEqual(LocalDate.now(), LocalDate.now().plusMonths(2));
        return documentacion;
    }


    @Override
    public List<Documentacion> buscarDocumentacionDeUnUsuario(User user) {
        return documentacionRepository.findAllByUser(user);
    }


}

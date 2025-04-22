package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.exceptions.FileException;
import com.flybook.librovuelo.model.AdjuntoDocumentacion;
import com.flybook.librovuelo.model.Documentacion;
import com.flybook.librovuelo.service.AdjuntoDocumentacionService;
import com.flybook.librovuelo.service.FileStorageService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.nio.file.*;
import java.time.LocalDate;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    // Path: Contiene métodos estáticos para convertir una string de ruta o URI en una ruta.
    // File: Proporciona información acerca de los archivos, de sus atributos, de los directorios, etc.
    private final Path fileStorageLocation;
    private final String uploadDir;

    @Autowired
    private AdjuntoDocumentacionService adjuntoDocumentacionService;

   // @Autowired
   // private DocumentacionService documentacionService;

    @Autowired
    // Paso por parametro el entorno en el que se ejecuta la aplicación actual
    public FileStorageServiceImpl(Environment env) {
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


    @Override
    public String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        // Devuelvo una matriz de strings divididos por un punto
        String[] fileNameParts = fileName.split("\\.");

        // Retorna el tamaño del string
        return fileNameParts[fileNameParts.length - 1];
    }


    @Override
    public AdjuntoDocumentacion storeFileDocumentacion(MultipartFile file, Documentacion documentacion) {

        String fileName = file.getOriginalFilename();
        try {
            if (fileName.contains("..")) {
                throw new FileException("El nombre de archivo contiene una secuencia de ruta no válida!" + fileName);
            }


            //Path directorio = this.fileStorageLocation.resolve(this.fileStorageLocation + "/" + user.getLegajo()  + subFolder);
            Path directorio = this.fileStorageLocation.resolve(this.fileStorageLocation + "/legajo_" + documentacion.getUser().getLegajo()  + "/documentacion_"+documentacion.getId());
            Path targetLocation = this.fileStorageLocation.resolve(directorio.toString()).resolve(fileName)  ;

            String baseFileName = getBaseFileName(fileName);
            String extensionFileName = getExtensionFileName(fileName);
            int counterSufixToFile = 1;

            while(Files.exists(targetLocation)){
                fileName = baseFileName +"_"+counterSufixToFile + extensionFileName;
                targetLocation = this.fileStorageLocation.resolve(directorio.toString()).resolve(fileName);
                counterSufixToFile++;
            }



            Files.createDirectories(directorio);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String extension = getFileExtension(fileName);
            Long tamanio = Files.size(targetLocation);
            byte[] data = Files.readAllBytes(targetLocation);


            //String path = targetLocation.toString();
            String path = this.uploadDir.substring(1) + "/legajo_" + documentacion.getUser().getLegajo()  + "/documentacion_"+documentacion.getId()+"/" + fileName;


            //String path = this.uploadDir.substring(1) + "/" + user.getLegajo()  + subFolder+ "/" + fileName;

            AdjuntoDocumentacion adjuntoDoc = new AdjuntoDocumentacion();
            adjuntoDoc.setFecha(LocalDate.now());
            adjuntoDoc.setNombre(fileName);
            adjuntoDoc.setExtension(extension);
            adjuntoDoc.setDocumentacion(documentacion);
            adjuntoDoc.setTamanio(tamanio);
  //          adjuntoDoc.setData(data);
            adjuntoDoc.setPath(path);

            return adjuntoDoc;
        } catch (IOException | FileException ex) {
            throw new RuntimeException("No se pudo almacenar el archivo " + fileName + ". ¡Inténtalo de nuevo!", ex);
        }
    }

    private String getExtensionFileName(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    private String getBaseFileName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }



    @Override
    public Resource load(String filename) {
        try {
            Path file = this.fileStorageLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("No se puede leer el archivo!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteFile(String pathName) {
        try {
            // Utilizado para convertir un string de ruta dada en una ruta y
            // la resuelve contra esta ruta exactamente de la misma manera

            //Elimino el archivo teniendo como referencia la ruta del objeto del Adjunto Documentacion
            pathName = pathName.substring("/uploads/files".length());
            Path path = this.fileStorageLocation.resolve(this.fileStorageLocation+pathName);
            // Elimino un archivo pasandole la ruta
            Files.delete(path);
        } catch (IOException ex) {
            throw new RuntimeException("No se pudo eliminar el archivo. ¡Inténtalo de nuevo!", ex);
        }
    }


    @Override
    public void deleteFolder(String pathName, Documentacion documentacion){
        try{
            pathName = pathName.substring("/uploads/files".length(),("documentacion_"+documentacion.getId()).length()-1);
            pathName += "legajo_"+documentacion.getUser().getLegajo()+"/documentacion_"+documentacion.getId();
            Path path = this.fileStorageLocation.resolve(this.fileStorageLocation+pathName);
            Files.delete(path);
            //"/legajo_" + documentacion.getUser().getLegajo()  + "/documentacion_"+documentacion.getId()
        }catch(IOException ex){
            throw new RuntimeException("No se pudo eliminar la carpeta");
        }
    }

    @Override
    public AdjuntoDocumentacion updateStoreFile(MultipartFile file, Documentacion documentacion) {
        // Obtengo la id de documentacion
        //Documentacion documentacion = this.documentacionService.getById(documentacionId);
        // Obtengo una list de adjuntoDoc buscado por id documentacion

        // Devuelve el nombre de archivo original en el sistema de archivos del cliente.
        String fileName =/*"(" +cantidadDefiles +")" + " " + */file.getOriginalFilename();
        try {
            // Compruebo si el nombre del archivo contiene caracteres no válidos
            if (fileName.contains("..")) {
                throw new FileException(
                        "El nombre de archivo contiene una secuencia de ruta no válida!" + fileName);
            }

            // Convierto un string de ruta en una ruta
//            Path targetLocation = this.fileStorageLocation.resolve(fileName);
  //          Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Path directorio = this.fileStorageLocation.resolve(this.fileStorageLocation + "/" + documentacion.getUser().getLegajo()  + "/documentacion");
            Path targetLocation = this.fileStorageLocation.resolve(directorio.toString()).resolve(fileName)  ;

            Files.createDirectories(directorio);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);





            String extension = getFileExtension(fileName);
            Long tamanio = Files.size(targetLocation);
            byte[] data = Files.readAllBytes(targetLocation);
            // Devuelvo una parte de la cadena comenzando desde 1, asociandole / y el nombre del archivo
//            String path = this.uploadDir.substring(1) + "/" + fileName;


            String path = this.uploadDir.substring(1) + "/" + documentacion.getUser().getLegajo()  + "/documentacion/" + fileName;

            AdjuntoDocumentacion adjuntoDoc = adjuntoDocumentacionService.findByDocumentacion(documentacion);
            adjuntoDoc.setFecha(LocalDate.now());
            adjuntoDoc.setNombre(fileName);
            adjuntoDoc.setDocumentacion(documentacion);
            adjuntoDoc.setExtension(extension);
            adjuntoDoc.setTamanio(tamanio);
           // adjuntoDoc.setData(data);
            adjuntoDoc.setPath(path);
            // Guardo en la BD el adjuntoDoc
            this.adjuntoDocumentacionService.save(adjuntoDoc);

            return adjuntoDoc;
            // Si no se puede guardar en la BD tiro una de las dos excepciones.
        } catch (IOException | FileException ex) {
            throw new RuntimeException("No se pudo almacenar el archivo " + fileName + ". ¡Inténtalo de nuevo!", ex);
        }
    }

    // ResponseEntity: representa la respuesta HTTP completa: código de estado, encabezados y cuerpo.
    // Como resultado, podemos usarlo para configurar completamente la respuesta HTTP.
    @Override
    public ResponseEntity download(Long id) {
        AdjuntoDocumentacion adj = adjuntoDocumentacionService.getById(id);
        // URLConnection: Se pueden usar tanto para leer como para escribir en el recurso al que
        // hace referencia la URL.
        // guessContentTypeFromName: Intenta determinar el tipo de contenido de un objeto,
        // en función del componente de "archivo" especificado de una URL.
        String mimeType = URLConnection.guessContentTypeFromName(adj.getNombre());
        // HTTP 200
        return ResponseEntity.ok()
                // contentType: Dice al cliente que tipo de contenido será retornado.
                // MediaType: Es un identificador de dos partes para formatos de archivo
                // y contenidos de formato transmitidos en Internet
                // parseMediaType: Analice la cadena dada en una sola MediaType.
                .contentType(MediaType.parseMediaType(mimeType))
                // Indica el tamaño de la entidad-cuerpo, en bytes, enviado al destinatario.
                .contentLength(adj.getTamanio())
                // Una estructura de datos que representa encabezados de solicitud o respuesta HTTP,
                // que asigna nombres de encabezados de cadena a una lista de valores de cadena
                // y que también ofrece accesores para tipos de datos comunes a nivel de aplicación.
                // HttpHeaders.CONTENT_DISPOSITION: En una respuesta HTTP regular, el encabezado de respuesta de disposición de contenido
                // es un encabezado que indica si se espera que el contenido se muestre en línea en el navegador,
                // es decir, como una página web o como parte de una página web,
                // o como un archivo adjunto, que se descarga y se guarda localmente.
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + adj.getNombre() + "\"")
                // Obtiene los bytes del archivo
                .body(null);
                //.body(adj.getData());
    }

    // HttpServletResponse: HttpServletResponse permite que un servlet formule una respuesta HTTP a un cliente.
    // El objeto de respuesta encapsula toda la información que se devolverá del servidor al cliente.
    @Override
    public void showAttachment(Long id, HttpServletResponse response) {
        AdjuntoDocumentacion adj = adjuntoDocumentacionService.getById(id);
        try {
            // Obtener adjunto en Bytes
           // byte[] documentInBytes = adj.getData();
            // Establece un encabezado de respuesta con el nombre y el valor dados.
            response.setHeader("Content-Disposition", "inline; filename=\"" + adj.getNombre() + "\"");
            // Establece un encabezado de respuesta con el nombre dado y el valor de fecha.
            response.setDateHeader("Expires", -1);
            // Establece el tipo de contenido de la respuesta que se envía al cliente, si la respuesta aún no se ha confirmado.
            // El tipo de contenido dado puede incluir una especificación de codificación de caracteres,
            // por ejemplo, text/html;charset=UTF-8.
            response.setContentType("application/" + adj.getExtension());
            // Establece la longitud del cuerpo del contenido en la respuesta.
            //response.setContentLength(documentInBytes.length);
            // Devuelve un ServletOutputStream adecuado para escribir datos binarios en la respuesta.
            // El contenedor de servlets no codifica los datos binarios.
            // write: Puede enviar texto de caracteres al cliente.
            //response.getOutputStream().write(documentInBytes);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo abrir el archivo adjunto. ¡Inténtalo de nuevo!", e);
        }
    }
}

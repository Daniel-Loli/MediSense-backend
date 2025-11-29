package com.medisense.medisense_back.service.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.medisense.medisense_back.Enum.CarpetaEnum;
import com.medisense.medisense_back.Enum.TipoFile;
import com.medisense.medisense_back.service.interfaces.IAzureBlobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AzureBlobServiceImpl implements IAzureBlobService {

    private final BlobServiceClient blobServiceClient;

    @Value("${azure.storage.container.name}")
    private String nombreContainer;

    /**
     * Sube un archivo a una subcarpeta específica dentro del container
     */
    @Override
    public String uploadFile(MultipartFile file, String nombre, CarpetaEnum carpetaEnum) throws IOException {
        String extension = getFileExtension(file.getOriginalFilename());
        String fileName = nombre.endsWith(extension) ? nombre : nombre + extension;

        // Construye la ruta dentro del container usando la carpeta
        String blobPath = carpetaEnum.getCarpeta() + "/" + fileName;

        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(nombreContainer);
        BlobClient blobClient = containerClient.getBlobClient(blobPath);

        BlobHttpHeaders headers = new BlobHttpHeaders()
                .setContentType(file.getContentType());

        blobClient.upload(file.getInputStream(), file.getSize(), true);
        blobClient.setHttpHeaders(headers);

        String url = blobClient.getBlobUrl();
        log.info("Archivo subido exitosamente: {}", url);
        return url;
    }

    /**
     * Sube material académico
     */
    @Override
    public String uploadMaterialAcademico(MultipartFile file) throws IOException {
        String nombre = "material_" + UUID.randomUUID() + getFileExtension(file.getOriginalFilename());
        return uploadFile(file, nombre, CarpetaEnum.ACADEMICO);
    }

    /**
     * Elimina archivo de una carpeta específica
     */
    @Override
    public void deleteFile(String fileUrl, CarpetaEnum carpetaEnum) {
        try {
            String blobName = extractBlobNameFromUrl(fileUrl, carpetaEnum);

            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(nombreContainer);
            BlobClient blobClient = containerClient.getBlobClient(blobName);

            if (blobClient.exists()) {
                blobClient.delete();
                log.info("Archivo eliminado exitosamente: {}", fileUrl);
            } else {
                log.warn("El archivo no existe en Azure Blob: {}", fileUrl);
            }
        } catch (Exception e) {
            log.error("Error al eliminar archivo: {}", fileUrl, e);
            throw new RuntimeException("Error al eliminar archivo de Azure Blob", e);
        }
    }

    @Override
    public String replaceMaterialAcademico(String oldFileUrl, MultipartFile newFile) throws IOException {
        // Elimina el archivo antiguo
        deleteFile(oldFileUrl, CarpetaEnum.ACADEMICO);
        // Sube el nuevo archivo
        return uploadMaterialAcademico(newFile);
    }

    /**
     * Extrae el nombre del blob desde la URL considerando la carpeta
     */
    private String extractBlobNameFromUrl(String url, CarpetaEnum carpetaEnum) {
        try {
            URI uri = new URI(url);
            String path = uri.getPath();
            String prefijo = "/" + nombreContainer + "/" + carpetaEnum.getCarpeta() + "/";

            if (path.startsWith(prefijo)) {
                return path.substring(prefijo.length());
            } else {
                throw new IllegalArgumentException("URL no corresponde al container o carpeta configurada");
            }
        } catch (URISyntaxException e) {
            log.error("URL inválida: {}", url, e);
            throw new IllegalArgumentException("URL inválida: " + url, e);
        }
    }

    // Métodos auxiliares (isImage, isDocument, getFileExtension, etc.)
    @Override
    public String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) return "";
        return filename.substring(filename.lastIndexOf("."));
    }

    @Override
    public TipoFile getTipoFile(MultipartFile file) {
        String contentType = file.getContentType();

        if (contentType == null) return TipoFile.DEFAULT;
        if (contentType.startsWith("image/")) return TipoFile.IMAGEN;
        if (contentType.contains("pdf")) return TipoFile.PDF;
        if (contentType.contains("word")) return TipoFile.WORD;
        if (contentType.contains("excel")) return TipoFile.EXCEL;
        if (contentType.contains("presentation")) return TipoFile.PPT;

        return TipoFile.DEFAULT;
    }

    @Override
    public boolean isImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    @Override
    public boolean isDocument(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (
                contentType.contains("pdf") ||
                        contentType.contains("word") ||
                        contentType.contains("excel") ||
                        contentType.contains("presentation")
        );
    }

    @Override
    public boolean isValidFileSize(MultipartFile file, int maxSizeMB) {
        long maxBytes = maxSizeMB * 1024L * 1024L;
        return file.getSize() <= maxBytes;
    }

    @Override
    public boolean validarRelacionAspecto(MultipartFile file, int ratioWidth, int ratioHeight) {
        try {
            var img = javax.imageio.ImageIO.read(file.getInputStream());
            if (img == null) return false;

            int width = img.getWidth();
            int height = img.getHeight();
            return (width * ratioHeight) == (height * ratioWidth);
        } catch (IOException e) {
            log.error("Error leyendo imagen para validar relación de aspecto", e);
            return false;
        }
    }
}

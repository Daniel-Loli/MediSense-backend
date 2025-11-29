package com.medisense.medisense_back.service.interfaces;

import com.medisense.medisense_back.Enum.CarpetaEnum;
import com.medisense.medisense_back.Enum.TipoFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IAzureBlobService {

    // Subida de archivos
    String uploadFile(MultipartFile file, String nombre, CarpetaEnum carpetaEnum) throws IOException;

    String uploadMaterialAcademico(MultipartFile file) throws IOException;

    // Eliminación de archivos
    void deleteFile(String fileUrl, CarpetaEnum carpetaEnum);

    // Reemplazo de archivos
    String replaceMaterialAcademico(String oldFileUrl, MultipartFile newFile) throws IOException;

    // Validaciones de archivos
    boolean isImage(MultipartFile file);

    boolean isDocument(MultipartFile file);

    boolean isValidFileSize(MultipartFile file, int maxSizeMB);

    boolean validarRelacionAspecto(MultipartFile file, int ratioWidth, int ratioHeight);

    // Utilidades
    String getFileExtension(String filename);

    TipoFile getTipoFile(MultipartFile file);
}
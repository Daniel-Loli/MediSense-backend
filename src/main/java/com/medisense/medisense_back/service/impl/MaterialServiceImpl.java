package com.medisense.medisense_back.service.impl;

import com.medisense.medisense_back.Enum.CarpetaEnum;
import com.medisense.medisense_back.Enum.Modulo;
import com.medisense.medisense_back.dto.base.ListResponse;
import com.medisense.medisense_back.dto.base.ObjectResponse;
import com.medisense.medisense_back.dto.material.MaterialAllResponse;
import com.medisense.medisense_back.dto.material.MaterialShortResponse;
import com.medisense.medisense_back.mapper.IMapperService;
import com.medisense.medisense_back.model.Material;
import com.medisense.medisense_back.repository.base.IGenericRepo;
import com.medisense.medisense_back.repository.interfaces.IMaterialRepo;
import com.medisense.medisense_back.repository.interfaces.IUsuarioRepo;
import com.medisense.medisense_back.service.base.CRUDImpl;
import com.medisense.medisense_back.service.interfaces.IAzureBlobService;
import com.medisense.medisense_back.service.interfaces.IMaterialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaterialServiceImpl
        extends CRUDImpl<Material, Long>
        implements IMaterialService {

    private final IMaterialRepo materialRepo;
    private final IAzureBlobService azureBlobService;
    private final IUsuarioRepo usuarioRepo;
    private final IMapperService mapperService;

    @Override
    protected IGenericRepo<Material, Long> getRepo() {
        return materialRepo;
    }

    @Override
    public ObjectResponse<MaterialAllResponse> registrarMaterial(MultipartFile file, String descripcion) {


        try {
            // validar tipo de archivo
            if (!azureBlobService.isDocument(file)) {
                return new ObjectResponse<>(400,
                        "El archivo debe ser un documento PDF, EXCEL, PPT o WORD",
                        null);
            }

            // validar tamaño maximo 20MB
            if (!azureBlobService.isValidFileSize(file, 20)) {
                return new ObjectResponse<>(400,
                        "El archivo no puede superar los 20MB",
                        null);
            }

            // subir archivo al contenedor
            String url = azureBlobService.uploadMaterialAcademico(file);

            // crear registro
            Material material = Material.builder()
                    .descripcion(descripcion)
                    .url(url)
                    .tipo(azureBlobService.getTipoFile(file))
                    .carpeta(CarpetaEnum.ACADEMICO)
                    .build();

            materialRepo.save(material);

            return new ObjectResponse<>(
                    201,
                    Modulo.MATERIAL.registrado(),
                    mapperService.convMaterialAll(material)
            );

        } catch (Exception e) {
            log.error("Error al registrar material académico: {}", e.getMessage());
            return new ObjectResponse<>(
                    500,
                    "Error interno al registrar material académico",
                    null
            );
        }
    }

    @Override
    public ListResponse<MaterialShortResponse> listarMaaterial() {


        List<MaterialShortResponse> lista = materialRepo.listar()
                .stream()
                .map(mapperService::convMaterialShort)
                .toList();

        return new ListResponse<>(200, Modulo.MATERIAL.listado(), lista, null);
    }



    @Override
    public ObjectResponse<String> eliminarMaterial(Long idMaterial) {

        Optional<Material> opt = materialRepo.buscarPorId(idMaterial);
        if (opt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.MATERIAL.noEncontrado(), null);
        }

        try {
            Material material = opt.get();

            // borrar archivo en Azure
            azureBlobService.deleteFile(material.getUrl(),CarpetaEnum.ACADEMICO);

            // marcar como eliminado
            material.setEnabled(false);
            materialRepo.save(material);

            return new ObjectResponse<>(200, Modulo.MATERIAL.eliminado(), null);

        } catch (Exception e) {
            log.error("Error eliminando material: {}", e.getMessage());
            return new ObjectResponse<>(500, "Error interno al eliminar material", null);
        }
    }

    @Override
    public ObjectResponse<MaterialAllResponse> buscarMaterial(Long idMaterial) {

        Optional<Material> opt = materialRepo.buscarPorId(idMaterial);
        if (opt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.MATERIAL.noEncontrado(), null);
        }
        return  new ObjectResponse<>(200, Modulo.MATERIAL.encontrado(),
                mapperService.convMaterialAll(opt.get()));

    }


}
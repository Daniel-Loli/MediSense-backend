package com.medisense.medisense_back.mapper;

import com.medisense.medisense_back.auth.dto.EspecialistaAuthResponse;
import com.medisense.medisense_back.dto.caso.CasoAllResponse;
import com.medisense.medisense_back.dto.caso.CasoEShortResponse;
import com.medisense.medisense_back.dto.caso.CasoPShortResponse;
import com.medisense.medisense_back.dto.caso.CasoShortResponse;
import com.medisense.medisense_back.dto.cita.CitaAllResponse;
import com.medisense.medisense_back.dto.cita.CitaCShortResponse;
import com.medisense.medisense_back.dto.cita.CitaEShortResponse;
import com.medisense.medisense_back.dto.cita.CitaPShortResponse;
import com.medisense.medisense_back.dto.especialidad.EspecialidadAllResponse;
import com.medisense.medisense_back.dto.especialidad.EspecialidadShortResponse;
import com.medisense.medisense_back.dto.especialista.EspecialistaAllResponse;
import com.medisense.medisense_back.dto.especialista.EspecialistaShortResponse;
import com.medisense.medisense_back.dto.material.MaterialAllResponse;
import com.medisense.medisense_back.dto.material.MaterialShortResponse;
import com.medisense.medisense_back.dto.paciente.PacienteAllResponse;
import com.medisense.medisense_back.dto.paciente.PacienteShortResponse;
import com.medisense.medisense_back.dto.rol.RolAllResponse;
import com.medisense.medisense_back.dto.usuario.UsuarioAllResponse;
import com.medisense.medisense_back.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MapperServiceImpl
        implements IMapperService {

    private final ModelMapper modelMapper;

    @Override
    public RolAllResponse convRolAll(Rol obj) {
        return modelMapper.map(obj,RolAllResponse.class);
    }

    public UsuarioAllResponse convUsuarioAll(Usuario obj) {
        return modelMapper.map(obj, UsuarioAllResponse.class);
    }

    @Override
    public EspecialistaAuthResponse convEspecialistaAuth(Especialista obj) {
        return modelMapper.map(obj, EspecialistaAuthResponse.class);
    }

    @Override
    public MaterialAllResponse convMaterialAll(Material obj) {
        return modelMapper.map(obj,MaterialAllResponse.class);
    }

    @Override
    public MaterialShortResponse convMaterialShort(Material obj) {
        return modelMapper.map(obj, MaterialShortResponse.class);
    }

    @Override
    public EspecialidadShortResponse convEspecialidadShort(Especialidad obj) {
        return modelMapper.map(obj, EspecialidadShortResponse.class);
    }

    @Override
    public EspecialidadAllResponse convEspecialidadAll(Especialidad obj) {
        return modelMapper.map(obj, EspecialidadAllResponse.class);
    }
    @Override
    public EspecialistaAllResponse convEspecialistaAll(Especialista obj) {
        return modelMapper.map(obj, EspecialistaAllResponse.class);
    }

    @Override
    public PacienteAllResponse convPacienteAll(Paciente obj) {
        return modelMapper.map(obj,PacienteAllResponse.class);
    }

    @Override
    public PacienteShortResponse convPacienteShort(Paciente obj) {
        return modelMapper.map(obj, PacienteShortResponse.class);
    }

    @Override
    public CasoAllResponse convCasoAll(Caso obj) {
        return modelMapper.map(obj,CasoAllResponse.class);
    }

    @Override
    public CasoShortResponse convCasoShort(Caso obj) {
        return modelMapper.map(obj,CasoShortResponse.class);
    }

    @Override
    public CasoPShortResponse convCasoPShort(Caso obj) {
        return modelMapper.map(obj,CasoPShortResponse.class);
    }

    @Override
    public CasoEShortResponse convCasoEShort(Caso obj) {
        return modelMapper.map(obj,CasoEShortResponse.class);
    }

    @Override
    public CitaAllResponse convCitaAll(Cita obj) {
        return modelMapper.map(obj,CitaAllResponse.class);
    }

    @Override
    public CitaCShortResponse convCitaShort(Cita obj) {
        return modelMapper.map(obj, CitaCShortResponse.class);
    }

    @Override
    public CitaPShortResponse convCitaPShort(Cita obj) {
        return modelMapper.map(obj,CitaPShortResponse.class);
    }

    @Override
    public CitaEShortResponse convCitaEShort(Cita obj) {
        return modelMapper.map(obj,CitaEShortResponse.class);
    }

    @Override
    public CitaCShortResponse convCitaCShort(Cita obj) {
        return modelMapper.map(obj,CitaCShortResponse.class);
    }

    @Override
    public EspecialistaShortResponse convEspecialistaShort(Especialista obj) {
        return modelMapper.map(obj, EspecialistaShortResponse.class);
    }

}

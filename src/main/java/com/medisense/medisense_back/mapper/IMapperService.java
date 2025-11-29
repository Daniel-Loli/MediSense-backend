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

public interface IMapperService {
    RolAllResponse convRolAll(Rol obj);
    UsuarioAllResponse convUsuarioAll(Usuario obj);
    EspecialistaAuthResponse convEspecialistaAuth(Especialista obj);
    MaterialAllResponse convMaterialAll(Material obj);
    MaterialShortResponse convMaterialShort(Material obj);
    EspecialidadShortResponse convEspecialidadShort(Especialidad obj);
    EspecialidadAllResponse convEspecialidadAll(Especialidad obj);
    EspecialistaShortResponse convEspecialistaShort(Especialista obj);
    EspecialistaAllResponse convEspecialistaAll(Especialista obj);
    PacienteAllResponse convPacienteAll(Paciente obj);
    PacienteShortResponse convPacienteShort(Paciente obj);
    CasoAllResponse convCasoAll(Caso obj);
    CasoShortResponse convCasoShort(Caso obj);
    CasoPShortResponse convCasoPShort(Caso obj);
    CasoEShortResponse convCasoEShort(Caso obj);
    CitaAllResponse convCitaAll(Cita obj);
    CitaCShortResponse convCitaShort(Cita obj);
    CitaPShortResponse convCitaPShort(Cita obj);
    CitaEShortResponse convCitaEShort(Cita obj);
    CitaCShortResponse convCitaCShort(Cita obj);
}

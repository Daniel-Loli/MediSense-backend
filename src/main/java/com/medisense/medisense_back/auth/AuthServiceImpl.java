package com.medisense.medisense_back.auth;


import com.medisense.medisense_back.Enum.EstadoUsuarioEnum;
import com.medisense.medisense_back.Enum.RolEnum;
import com.medisense.medisense_back.auth.dto.*;
import com.medisense.medisense_back.dto.base.ObjectResponse;
import com.medisense.medisense_back.dto.usuario.UsuarioAllResponse;
import com.medisense.medisense_back.mapper.IMapperService;
import com.medisense.medisense_back.model.Especialista;
import com.medisense.medisense_back.model.Rol;
import com.medisense.medisense_back.model.Usuario;
import com.medisense.medisense_back.repository.interfaces.IEspecialistaRepo;
import com.medisense.medisense_back.repository.interfaces.IRolRepo;
import com.medisense.medisense_back.repository.interfaces.IUsuarioRepo;
import com.medisense.medisense_back.security.JwtTokenUtil;
import com.medisense.medisense_back.utils.GeneratorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import static com.medisense.medisense_back.Enum.Message.*;
import static com.medisense.medisense_back.Enum.Modulo.ROL;
import static com.medisense.medisense_back.Enum.Modulo.USUARIO;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl
        implements IAuthService{
    private final IUsuarioRepo userRepo;
    private final IEspecialistaRepo especialistaRepo;
    private final IRolRepo rolRepo;
    private final IMapperService mapperService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    private void authenticate(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (DisabledException e) {
            log.error("Usuario deshabilitado: {}", request.getEmail());
            throw new DisabledException("Usuario deshabilitado", e);
        } catch (BadCredentialsException e) {
            log.error("Credenciales inválidas para: {}", request.getEmail());
            throw new BadCredentialsException("Credenciales inválidas", e);
        }
    }
    @Override
    public ObjectResponse<AuthResponse> login(LoginRequest request) {
        // 1. Autenticar usuario
        authenticate(request);

        // 2. Cargar detalles del usuario
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        // 3. Generar token
        final String token = jwtTokenUtil.generateToken(userDetails);
        Optional<Usuario> optionalUser = userRepo.buscarPorEmail(request.getEmail());
        if (optionalUser.isEmpty()) {
            return new ObjectResponse<>(CORREO_NO_ENCONTRADO.getStatus(), CORREO_NO_ENCONTRADO.toString(), null);
        }
        Usuario usuario = optionalUser.get();
        UsuarioAllResponse usuarioAllResponse = mapperService.convUsuarioAll(usuario);

        // 5. Validar rol del usuario
        Long idRol = usuario.getRol().getIdRol();
        RolEnum rol = RolEnum.fromValor(idRol);

        if (rol == null) {
            return new ObjectResponse<>(404, "Usuario no cuenta con un rol válido", null);
        }
        EspecialistaAuthResponse especialistaAuthResponse  =null;
        switch (rol){
            case ADMIN:
                break;
            case ESPECIALISTA:
                Especialista especialista = especialistaRepo.buscarPorId(usuario.getIdUsuario()).orElse(null);
                if(especialista !=null){
                    especialistaAuthResponse = mapperService.convEspecialistaAuth(especialista);

                }
                break;
            case AGENTE: // Docente
                break;

        }
        AuthResponse authResponse = AuthResponse.builder()
                .token(token)
                .usuario(usuarioAllResponse)
                .especialista(especialistaAuthResponse)
                .build();

        return new ObjectResponse<>(LOGIN_ACCESS.getStatus(), LOGIN_ACCESS.toString(), authResponse);
    }

    //no se usara
    public ObjectResponse<AuthResponse> registerAdmin(RegisterAdminRequest request) {

        if (userRepo.buscarPorEmail(request.getEmail()).isPresent()) {
            return new ObjectResponse<>(CORREO_EN_USO.getStatus(), CORREO_EN_USO.toString(), null);
        }
        RolEnum rol = RolEnum.ADMIN;
        Optional<Rol> rolOpt = rolRepo.buscarPorId(rol.getValor());
        if (rolOpt.isEmpty()) {
            return new ObjectResponse<>(404,ROL.noEncontrado(), null);
        }
        Rol rolUsuario = rolOpt.get();
        String codigo;
        do {
            codigo = "US"+ GeneratorUtil.generarCodigoNumerico(6);
        } while (userRepo.existePorCodigo(codigo));

        Usuario usuario = Usuario.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .codigo(codigo)
                .rol(rolUsuario)
                .estado(EstadoUsuarioEnum.ACTIVO)
                .build();

        userRepo.save(usuario);

        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getEmail());
        String token = jwtTokenUtil.generateToken(userDetails);


        AuthResponse authResponse = AuthResponse.builder()
                .token(token)
                .usuario(mapperService.convUsuarioAll(usuario))
                .especialista(null)
                .build();


        return new ObjectResponse<>(201, USUARIO.registrado(), authResponse);
    }
    public ObjectResponse<AuthResponse> registerAgente(RegistrarAgenteRequest request) {


        if (userRepo.buscarPorEmail(request.getEmail()).isPresent()) {
            return new ObjectResponse<>(CORREO_EN_USO.getStatus(), CORREO_EN_USO.toString(), null);
        }
        RolEnum rol = RolEnum.AGENTE;
        Optional<Rol> rolOpt = rolRepo.buscarPorId(rol.getValor());
        if (rolOpt.isEmpty()) {
            return new ObjectResponse<>(404,ROL.noEncontrado(), null);
        }
        Rol rolUsuario = rolOpt.get();
        String codigo;
        do {
            codigo = "AG"+GeneratorUtil.generarCodigoNumerico(6);
        } while (userRepo.existePorCodigo(codigo));

        Usuario usuario = Usuario.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .codigo(codigo)
                .rol(rolUsuario)
                .estado(EstadoUsuarioEnum.ACTIVO)
                .build();

        userRepo.save(usuario);

        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getEmail());
        String token = jwtTokenUtil.generateToken(userDetails);


        AuthResponse authResponse = AuthResponse.builder()
                .token(token)
                .usuario(mapperService.convUsuarioAll(usuario))
                .especialista(null)
                .build();


        return new ObjectResponse<>(201, USUARIO.registrado(), authResponse);
    }



}

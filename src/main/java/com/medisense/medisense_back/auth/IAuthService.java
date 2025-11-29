package com.medisense.medisense_back.auth;


import com.medisense.medisense_back.auth.dto.AuthResponse;
import com.medisense.medisense_back.auth.dto.LoginRequest;
import com.medisense.medisense_back.auth.dto.RegisterAdminRequest;
import com.medisense.medisense_back.auth.dto.RegistrarAgenteRequest;
import com.medisense.medisense_back.dto.base.ObjectResponse;

public interface IAuthService {
    ObjectResponse<AuthResponse> login(LoginRequest request);
    ObjectResponse<AuthResponse> registerAdmin(RegisterAdminRequest request);
    ObjectResponse<AuthResponse> registerAgente(RegistrarAgenteRequest request);
}

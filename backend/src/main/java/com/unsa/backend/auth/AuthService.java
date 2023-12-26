package com.unsa.backend.auth;

public interface AuthService {
  
    public AuthUserResponse login(LoginRequest request);
  
    public AuthUserResponse register(RegisterRequest request);
  
}

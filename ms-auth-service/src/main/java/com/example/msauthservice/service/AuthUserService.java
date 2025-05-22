package com.example.msauthservice.service;

import com.example.msauthservice.dto.AuthUserDto;
import com.example.msauthservice.dto.TokenDto;
import com.example.msauthservice.entity.AuthUser;

public interface AuthUserService {
    public AuthUser save(AuthUserDto authUserDto);
    public TokenDto login(AuthUserDto authUserDto);
    public TokenDto validate(String token);
}

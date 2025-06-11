package com.laroz.infra.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CryptService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public CryptService(){
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public String encode(String password){
        return bCryptPasswordEncoder.encode(password);
    }
}

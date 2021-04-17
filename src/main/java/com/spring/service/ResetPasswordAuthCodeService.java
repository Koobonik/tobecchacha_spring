package com.spring.service;

import com.spring.model.ResetPasswordAuthCode;
import com.spring.model.ResetPasswordAuthCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j2
public class ResetPasswordAuthCodeService {

    private final ResetPasswordAuthCodeRepository resetPasswordAuthCodeRepository;

    public ResetPasswordAuthCode findByCode(String code){
        return resetPasswordAuthCodeRepository.findByCode(code);
    }
    public void save(ResetPasswordAuthCode resetPasswordAuthCode){
        resetPasswordAuthCodeRepository.save(resetPasswordAuthCode);
    }
}
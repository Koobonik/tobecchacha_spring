package com.spring.model;

import com.spring.util.AES256Cipher;
import com.spring.util.yml.ApplicationAESRead;
import lombok.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "spring_reset_password_auth_code")
public class ResetPasswordAuthCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String code;

    @Column(name = "auth_user_id", nullable = false)
    private long authUserId;

    @Column(name = "created_date", nullable = false, columnDefinition = "datetime")
    private Timestamp createdDate;

    @Column(name = "certified_date", columnDefinition = "datetime")
    private Timestamp certifiedDate;

    @Column(name = "is_can_use", nullable = false, columnDefinition = "TINYINT(4)")
    private boolean isCanUse;
    //
    public String createCode(String issuerName, String grade, ApplicationAESRead applicationYamlRead) throws NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        AES256Cipher aes256Cipher = new AES256Cipher(applicationYamlRead);
        UUID uuid = UUID.randomUUID();
        // 발급자.권한 받는자.권한.uuid
        String code = aes256Cipher.AES_Encode(issuerName + "."+ grade + "." + grade + "." + uuid);
        System.out.println(code);
        return code;
    }
    public String decodeCreatedCode(String code, ApplicationAESRead applicationYamlRead) throws NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        AES256Cipher aes256Cipher = new AES256Cipher(applicationYamlRead);
        return aes256Cipher.AES_Decode(code);
    }

    public ResetPasswordAuthCode(String code, long authUserId, Timestamp createdDate, boolean isCanUse) {
        this.code = code;
        this.authUserId = authUserId;
        this.createdDate = createdDate;
        this.isCanUse = isCanUse;
    }
}
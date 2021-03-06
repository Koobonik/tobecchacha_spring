package com.spring.util;

import com.spring.util.yml.ApplicationAESRead;
import lombok.Getter;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Getter
@Component
public class AES256Cipher {
    private static volatile AES256Cipher INSTANCE;

    private final ApplicationAESRead applicationAESRead;


    private String secretKey; //32bit
    private String IV; //16bit
    private String url;

//    public AES256Cipher getInstance() {
//        if (INSTANCE == null) {
//            synchronized (AES256Cipher.class) {
//                if (INSTANCE == null)
//                    INSTANCE = new AES256Cipher();
//            }
//        }
//        return INSTANCE;
//    }

    public AES256Cipher(ApplicationAESRead applicationAESRead) {
        this.secretKey = applicationAESRead.getSecretkey();
        this.IV = applicationAESRead.getIv();
        this.applicationAESRead = applicationAESRead;
        this.url = applicationAESRead.getUrl();
        //IV = secretKey.substring(0,16);
    }

    //μνΈν
    public String AES_Encode(String str) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] keyData = this.secretKey.getBytes();

        SecretKey secureKey = new SecretKeySpec(keyData, "AES");

        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(this.IV.getBytes()));

        byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
        String enStr = new String(Base64.encodeBase64(encrypted));

        return enStr;
    }

    //λ³΅νΈν
    public String AES_Decode(String str) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] keyData = this.secretKey.getBytes();
        SecretKey secureKey = new SecretKeySpec(keyData, "AES");
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(this.IV.getBytes("UTF-8")));

        byte[] byteStr = Base64.decodeBase64(str.getBytes());

        return new String(c.doFinal(byteStr), "UTF-8");
    }


//
//    public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
//
//
////    /**
////     * μΌλ° λ¬Έμμ΄μ μ§μ λ ν€λ₯Ό μ΄μ©νμ¬ AES256 μΌλ‘ μνΈν
////     * @param  String - μνΈν λμ λ¬Έμμ΄
////     * @param  String - λ¬Έμμ΄ μνΈνμ μ¬μ©λ  ν€
////     * @return String - key λ‘ μνΈνλ  λ¬Έμμ΄
////     * @exception
////     */
//    public static String strEncode(String str, String key) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
//
//        byte[] textBytes = str.getBytes("UTF-8");
//        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
//        SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
//        Cipher cipher = null;
//        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
//        return Base64.encodeBase64String(cipher.doFinal(textBytes));
//    }
//
//
////
////    /**
////     * μνΈνλ λ¬Έμμ΄μ μ§μ λ ν€λ₯Ό μ΄μ©νμ¬ AES256 μΌλ‘ λ³΅νΈν
////     * @param  String - λ³΅νΈν λμ λ¬Έμμ΄
////     * @param  String - λ¬Έμμ΄ λ³΅νΈνμ μ¬μ©λ  ν€
////     * @return String - key λ‘ λ³΅νΈνλ  λ¬Έμμ΄
////     * @exception
////     */
//    public static String strDecode(String str, String key) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
//
//        byte[] textBytes = Base64.decodeBase64(str);
//        //byte[] textBytes = str.getBytes("UTF-8");
//        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
//        SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
//        return new String(cipher.doFinal(textBytes), "UTF-8");
//    }
}

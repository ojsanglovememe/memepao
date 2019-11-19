package com.wj.babapao.http.utils;


import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class EncryptUtil {

    private final static String iv = "01234567";

    // 加解密统一使用的编码方式
    public final static String CHARSET = "utf-8";

    private EncryptUtil() {
    }

    /**
     * DES加密
     *
     * @param key       密钥
     * @param plaintext 明文
     * @return
     * @throws Exception
     */
    public static String DESEncrypt(String key, String plaintext) throws Exception {
        return encode(key, plaintext);
    }

    /**
     * DES解密
     *
     * @param key        密钥
     * @param ciphertext 密文
     * @return
     * @throws Exception
     */
    public static String DESDecrypt(String key, String ciphertext) throws Exception {
        return decode(key, ciphertext);
    }


    /**
     * RSA加密
     *
     * @param publicKey 公钥
     * @param plaintext 明文
     * @return
     * @throws Exception
     */
    public static String RSAEncrypt(String publicKey, String plaintext) throws Exception {
        PublicKey pub = getPublicKey(publicKey);
        byte[] encrypt = encrypt(plaintext.getBytes(CHARSET), pub, 2048, 11, "RSA/ECB/PKCS1Padding");
        return encodeToString(encrypt);
    }

    /**
     * RSA解密
     *
     * @param privateKey 私钥
     * @param ciphertext 密文
     * @return
     */
    public static String RSADecrypt(String privateKey, String ciphertext) throws Exception {
        PrivateKey pri = getPraivateKey(privateKey);
        byte[] decrypt = decrypt(decode(ciphertext), pri, 2048, 11, "RSA/ECB/PKCS1Padding");
        return new String(decrypt, CHARSET);
    }


    private static String encode(String key, String plainText) throws Exception {
        Key deskey;
        DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] encryptData = cipher.doFinal(plainText.getBytes(CHARSET));
        return encodeToString(encryptData);
    }


    private static String decode(String key, String encryptText) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

        byte[] decryptData = cipher.doFinal(decode(encryptText));

        return new String(decryptData, CHARSET);
    }

    private static byte[] decrypt(byte[] encryptedBytes, PrivateKey privateKey, int keyLength, int reserveSize, String cipherAlgorithm) throws Exception {
        int keyByteSize = keyLength / 8;
        int decryptBlockSize = keyByteSize - reserveSize;
        int nBlock = encryptedBytes.length / keyByteSize;
        ByteArrayOutputStream outbuf = null;
        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            outbuf = new ByteArrayOutputStream(nBlock * decryptBlockSize);
            for (int offset = 0; offset < encryptedBytes.length; offset += keyByteSize) {
                int inputLen = encryptedBytes.length - offset;
                if (inputLen > keyByteSize) {
                    inputLen = keyByteSize;
                }
                byte[] decryptedBlock = cipher.doFinal(encryptedBytes, offset, inputLen);
                outbuf.write(decryptedBlock);
            }
            outbuf.flush();
            return outbuf.toByteArray();
        } catch (Exception e) {
            throw new Exception("DEENCRYPT ERROR:", e);
        } finally {
            try {
                if (outbuf != null) {
                    outbuf.close();
                }
            } catch (Exception e) {
            }
        }
    }

    private static byte[] encrypt(byte[] plainBytes, PublicKey publicKey, int keyLength, int reserveSize, String cipherAlgorithm) throws Exception {
        int keyByteSize = keyLength / 8;
        int encryptBlockSize = keyByteSize - reserveSize;
        int nBlock = plainBytes.length / encryptBlockSize;
        if ((plainBytes.length % encryptBlockSize) != 0) {
            nBlock += 1;
        }
        ByteArrayOutputStream outbuf = null;
        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            outbuf = new ByteArrayOutputStream(nBlock * keyByteSize);
            for (int offset = 0; offset < plainBytes.length; offset += encryptBlockSize) {
                int inputLen = plainBytes.length - offset;
                if (inputLen > encryptBlockSize) {
                    inputLen = encryptBlockSize;
                }
                byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
                outbuf.write(encryptedBlock);
            }
            outbuf.flush();
            return outbuf.toByteArray();
        } catch (Exception e) {
            throw new Exception("ENCRYPT ERROR:", e);
        } finally {
            try {
                if (outbuf != null) {
                    outbuf.close();
                }
            } catch (Exception e) {
            }
        }
    }


    private static PrivateKey getPraivateKey(String privateKeyStr) throws Exception {
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(decode(privateKeyStr));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(priPKCS8);
    }

    private static PublicKey getPublicKey(String publicKeyStr) throws Exception {
        X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(decode(publicKeyStr));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(pubX509);
    }

    private static String encodeToString(byte[] encrypt) {
        return Base64Util.encode(encrypt);
    }

    private static byte[] decode(String str) throws Exception {
        return Base64Util.decode(str);
    }


}

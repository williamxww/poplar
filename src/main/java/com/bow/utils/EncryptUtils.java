package com.bow.utils;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author ViVi
 * @date 2015年9月12日 下午1:52:48
 */
public class EncryptUtils {

    private static final Logger logger = LoggerFactory.getLogger(EncryptUtils.class);

    /**
     * 用MD5算法进行加密
     * 
     * @param str
     *            需要加密的字符串
     * @return MD5加密后的结果
     */
    public static String encodeMD5String(String str) {
        return encode(str, "MD5");
    }

    /**
     * 用SHA算法进行加密
     * 
     * @param str
     *            需要加密的字符串
     * @return SHA加密后的结果
     */
    public static String encodeSHAString(String str) {
        return encode(str, "SHA");
    }

    /**
     * 用base64算法进行加密
     * 
     * @param str
     *            需要加密的字符串
     * @return base64加密后的结果
     */
    @SuppressWarnings("restriction")
    public static String encodeBase64String(String str) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(str.getBytes());
    }

    /**
     * 用base64算法进行解密
     * 
     * @param str
     *            需要解密的字符串
     * @return base64解密后的结果
     * @throws IOException
     */
    @SuppressWarnings("restriction")
    public static String decodeBase64String(String str) {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] result = null;
        try {
            result = decoder.decodeBuffer(str);
        } catch (IOException e) {
            logger.error("Error in decoding " + str, e);
        }
        return new String(result);
    }

    private static String encode(String str, String method) {
        String plainText = null;
        try {
            MessageDigest md = MessageDigest.getInstance(method);
            md.update(str.getBytes());
            plainText = new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error in encoding", e);
        }
        return plainText;
    }

    public static void main(String[] args) throws IOException {
        String base64Str = encodeBase64String("vislecaina");
        System.out.println("Base64 encode " + base64Str);
    }
}

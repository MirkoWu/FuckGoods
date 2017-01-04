package com.mirkowu.fuckgoods.network.rtrofit;

import java.security.MessageDigest;

/**
 * 类描述：MD5加密工具类
 */
public class MD5Util {
    //密钥
    public static String SECRET = "Ysljsd&sfli%87wirioew3^534rjkljl";
    public static String PSWKEY = "77&*Kouyubi%4p3";
    /**
     * 将字符串进行MD5加密 Ysljsd&sfli%87wirioew3^534rjkljl
     *
     * @param pstr 被加密的字符串
     * @return MD5 string
     */
    public static String ToMD5(String pstr) {
        //加密
        pstr = SECRET + pstr;
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            MessageDigest md5Temp = MessageDigest.getInstance("MD5");
            md5Temp.update(pstr.getBytes("UTF8"));
            byte[] md = md5Temp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 加密登陆密码
     *
     * @param pstr
     * @return
     */
    public static String ToMD5NOKey(String pstr) {

        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            MessageDigest md5Temp = MessageDigest.getInstance("MD5");
            md5Temp.update(pstr.getBytes("UTF8"));
            byte[] md = md5Temp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            return null;
        }
    }

}

package eop.util;

import java.security.MessageDigest;

public class MD5Util {

    public final static String MD5(String s) {
        if (s == null) {
            return null;
        }
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[(byte0 & 0xf0) >> 4];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }



    /**
     * MD5加密处理 加盐值
     * @param str 待加密字符串
     * @return
     */
    public final static String MD5Salt(String str){
         return MD5("^*}" + str + "#@$");
    }

    public static void main(String[] args){
        System.out.println(MD5Salt("fff"));
    }
}

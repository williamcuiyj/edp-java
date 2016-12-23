package eop.util;

/**
 * 加密
 */
public class Authcode {
    
    private static final String AUTH_TYPE_ENCODE = "ENCODE";
    
    private static final String AUTH_TYPE_DECODE = "DECODE";
    
    /**
     * 加密方法
     * @param str 需要加密的字符串
     * @return
     */
    public static String encrypt(String str) {
        return encrypt(str, null);
    }
    
    /**
     * 加密方法
     * @param str 需要加密的字符串
     * @param key 加密盐
     * @return
     */
    public static String encrypt(String str, String key) {
        return authcode(str, AUTH_TYPE_ENCODE, key, 0);
    }
    
    
    /**
     * 解密方法
     * @param str 需要解密的字符串
     * @return
     */
    public static String decrypt(String str) {
        return decrypt(str, null);
    }
    
    /**
     * 解密方法
     * @param str 需要解密的字符串
     * @param key 解密盐
     * @return
     */
    public static String decrypt(String str, String key) {
        return authcode(str, AUTH_TYPE_DECODE, key, 0);
    }

    /**
     * 加解密方法
     *
     * @param str
     *            需要加密或者解密的字符串
     * @param operation
     *            加密传参:ENCODE 解密传null
     * @param key
     *            参与运算的密钥
     * @param expiry
     *            有效时间
     * @return
     */
    public static String authcode(String str, String operation, String key, int expiry) {
        operation = operation == null ? AUTH_TYPE_ENCODE : operation;// 默认操作加密
        expiry = expiry == 0 ? 0 : expiry;
        // 临时变量,计算算法时使用
        int tmp = 0;
        // 动态密匙长度，相同的明文会生成不同密文就是依靠动态密匙
        int ckey_length = 4;
        // 密匙
        key = eop.util.MD5Util.MD5((null != key && key != "") ? key : "4w50c34h05w28");
        // 密匙a会参与加解密
        String keya = eop.util.MD5Util.MD5(key.substring(0, 16));
        // 密匙b会用来做数据完整性验证
        String keyb = eop.util.MD5Util.MD5(key.substring(16, 32));
        // 密匙c用于变化生成的密文
        String MD5time = eop.util.MD5Util.MD5(String.valueOf(System.currentTimeMillis() / 1000));
        // 如果是解密则直接获取动态密钥,加密则根据当前时间生成
        String keyc = operation == AUTH_TYPE_DECODE ? str.substring(0, ckey_length) : MD5time.substring(MD5time.length()
                - ckey_length, MD5time.length());
        // 参与运算的密匙
        String cryptkey = keya + eop.util.MD5Util.MD5(keya + keyc);
        // 参与运算的密匙长度
        int key_length = cryptkey.length();
        // 明文，前10位用来保存时间戳，解密时验证数据有效性，10到26位用来保存$keyb(密匙b)，解密时会通过这个密匙验证数据完整性
        // 如果是解码的话，会从第$ckey_length位开始，因为密文前$ckey_length位保存 动态密匙，以保证解密正确
        str = operation == AUTH_TYPE_DECODE ? Base64.decode(str.substring(ckey_length)) : (expiry == 0 ? "0000000000" : String
                .valueOf(System.currentTimeMillis() / 1000 + expiry)) + eop.util.MD5Util.MD5(str + keyb).substring(0, 16) + str;
        // 明文长度
        int str_length = str.length();
        // 用于保存加解密生成的字符串
        String result = "";
        int[] box = new int[256];
        for (int i = 0; i < 256; i++) {
            box[i] = i;
        }
        // 产生密匙簿
        int[] rndkey = new int[256];
        for (int i = 0; i < 256; i++) {
            rndkey[i] = (int) cryptkey.toCharArray()[i % key_length];
        }

        // 用固定的算法，打乱密匙簿，增加随机性，好像很复杂，实际上对并不会增加密文的强度
        for (int j = 0, i = 0; i < 256; i++) {
            j = (j + box[i] + rndkey[i]) % 256;
            tmp = box[i];
            box[i] = box[j];
            box[j] = tmp;
        }
        // 核心加解密部分
        for (int a = 0, j = 0, i = 0; i < str_length; i++) {
            a = (a + 1) % 256;
            j = (j + box[a]) % 256;
            tmp = box[a];
            box[a] = box[j];
            box[j] = tmp;
            // 从密匙簿得出密匙进行异或，再转成字符
            result += (char) ((str.toCharArray()[i]) ^ (box[(box[a] + box[j]) % 256]) % 128);
        }
        if (AUTH_TYPE_DECODE.equals(operation)) {
            // Long.parseLong(result.substring(0, 10))==0 验证 expiry=0时数据有效性
            // (Long.parseLong(result.substring(0, 10))- (System.currentTimeMillis() / 1000)) > 0 验证expiry!=0时数据有效性
            // result.substring(10, 26).equals(MD5Util.MD5(result.substring(26) + keyb).substring(0, 16)) 验证数据完整性
            // 验证数据有效性，请看未加密明文的格式
            if ((Long.parseLong(result.substring(0, 10)) == 0 || (Long.parseLong(result.substring(0, 10)) - (System
                    .currentTimeMillis() / 1000)) > 0)
                    && result.substring(10, 26).equals(MD5Util.MD5(result.substring(26) + keyb).substring(0, 16))) {
                return result.substring(26);
            } else {
                return "密匙无效或过期";
            }
        } else {
            // 把动态密匙保存在密文里，这也是为什么同样的明文，生产不同密文后能解密的原因
            // 因为加密后的密文可能是一些特殊字符，复制过程可能会丢失，所以用base64编码
            return keyc + Base64.encode(result).replace("=", "");
        }
    }
    
    public static void main(String[] args) {
        System.out.println(encrypt("1"));
    }
}

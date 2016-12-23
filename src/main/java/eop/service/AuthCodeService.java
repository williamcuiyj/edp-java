package eop.service;

import eop.exception.BusinessException;
import eop.model.AuthCode;

public interface AuthCodeService {
    /**
     * 发送验证码
     *
     * @param smsType
     * @param phone
     */
    boolean sendSmsAuthCode(String smsType, String phone) throws BusinessException;

    /**
     * 设置验证码失效
     * @param mobile
     * @param phoneRegister
     */
    void setAuthCodeInvalid(String mobile, AuthCode.Type phoneRegister);

    /**
     * 返回验证码信息用于比对
     * @param name
     * @param type
     * @return
     */
    AuthCode getAuthCode(String name, AuthCode.Type type);

}

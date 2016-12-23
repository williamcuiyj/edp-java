package eop.service.impl;

import eop.exception.BusinessException;
import eop.mapper.AuthCodeMapper;
import eop.model.AuthCode;
import eop.model.AuthCodeLog;
import eop.service.AuthCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import eop.service.UserService;
import eop.util.Constants;
import eop.util.CustomizedMessageSource;
import eop.util.StringUtils;
import eop.util.YunPianMessager;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;


@Service
@Transactional
public class AuthCodeServiceImpl implements AuthCodeService {

    private static Logger logger = LoggerFactory.getLogger(AuthCodeServiceImpl.class);
    @Resource
    private UserService userService;
    @Resource
    private AuthCodeMapper authCodeMapper;


    @Override
    public boolean sendSmsAuthCode(String smsType, String phone) throws BusinessException {
        if (smsType.equals(AuthCode.Type.PHONE_REGISTER.name())) {
            sendForPhoneReg(phone,AuthCode.Type.PHONE_REGISTER.name());
            return true;
        }else if(smsType.equals(AuthCode.Type.PHONE_BIND.name())){
            sendForPhoneReg(phone,AuthCode.Type.PHONE_BIND.name());
            return true;
        }else if(smsType.equals(AuthCode.Type.PHONE_RESET_PASSWORD.name())){
            sendForReSetPwd(phone,smsType);
            return true;
        }
        return false;
    }

    /**
     * 重设密码发送验证码
     * @param phone
     * @param smsType
     */
    private boolean sendForReSetPwd(String phone, String smsType) {
        if(userService.isExist(phone)&&canSendAuthCode(phone)){
            if (sendAuthCode(phone, smsType)) {
                return true;
            } else {
                throw new BusinessException("短信系统繁忙,请稍后再试！");
            }
        }
        return false;
    }

    /**
     * 让验证码失效
     *
     * @param name
     * @param type
     */
    @Override
    public void setAuthCodeInvalid(String name, AuthCode.Type type) {
        AuthCode authCode = new AuthCode();
        authCode.setName(name);
        authCode.setType(type.name());
        AuthCode authCodeLast = authCodeMapper.getAuthCode(authCode);
        if (authCodeLast == null) {
            return;
        }
        authCodeLast.setIsInvalid(AuthCode.TYPE_IS_INVALID_YES);
        authCodeMapper.update(authCodeLast);
    }

    private boolean sendForPhoneReg(String phone, String type) throws BusinessException {
        if (canSignUp(phone) && canSendAuthCode(phone)) {
            //发送手机验证码
            if (sendAuthCode(phone, type)) {
                return true;
            } else {
                throw new BusinessException("短信系统繁忙,请稍后再试！");
            }
        }
        return false;
    }

    /**
     * 发送手机验证码
     * <p>
     * @param mobile   手机号
     * @param type     验证码类型 phoneRegister注册
     * @return 发送成功，返回true，返回失败 返回false
     */
    private boolean sendAuthCode(String mobile, String type) throws BusinessException {

        //生成验证码
        String authCode = genAuthCode(mobile, type).getAuthCode();

        //如果生成失败
        if (authCode == null) {
            throw new BusinessException("短信系统繁忙，请稍后再试！");
        } else {
            saveAuthCodeLog(mobile, type, authCode.toString(), "");
        }

        String sendMsg = null;
        if (type.equals(AuthCode.Type.PHONE_REGISTER.name())) {
            sendMsg = CustomizedMessageSource.getMessage("register.authcode.content", authCode.toString());
        } else if (type.equals(AuthCode.Type.PHONE_RESET_PASSWORD.name())) {
            sendMsg = CustomizedMessageSource.getMessage("resetpwd.authcode.content", authCode.toString());
        }else if (type.equals(AuthCode.Type.PHONE_BIND.name())) {
            sendMsg = CustomizedMessageSource.getMessage("resetpwd.authcode.content", authCode.toString());
        }

        if (sendMsg != null)

        {
//            return true; //测试时屏蔽短信接口处 TODO:正式环境一定要将此处进行正式更改
            return YunPianMessager.sendSms(sendMsg, mobile);
        } else

        {
            return false;
        }

    }

    /**
     * 保存发送的验证码信息
     *
     * @param mobile
     * @param type
     * @param authCode
     * @param remoteIp
     * @return
     */
    private int saveAuthCodeLog(String mobile, String type, String authCode, String remoteIp) {
        AuthCodeLog authCodeLog = new AuthCodeLog();
        authCodeLog.setAuthCode(authCode);
        authCodeLog.setAuthCodeMobile(mobile);
        authCodeLog.setAuthCodeType(type);
        authCodeLog.setAuthCodeIpAddr(remoteIp);
        authCodeLog.setAuthCodeTime(new Date());
        return authCodeMapper.insertAuthCodeLog(authCodeLog);
    }

    /**
     * 新用户注册时，先验证手机号是否可以注册
     *
     * @param phone
     * @return
     * @throws IOException
     */
    public boolean canSignUp(String phone) throws BusinessException {

        if (!StringUtils.isMobile(phone)) {
            throw new BusinessException("手机号格式错误!");
        }

        if (userService.isExist(phone)) {
            throw new BusinessException("手机号已存在");
        }
        return true;
    }

    /**
     * 检查手机号能否正常发送验证码
     * 1,异常，24小时发送数量超过10条
     * 2,异常，1分钟内再次请求
     *
     * @param mobile
     * @return 如果不能正常发送验证码，返回false 并处理异常原因 如果能正常发送 返回true
     */
    private boolean canSendAuthCode(String mobile) throws BusinessException {

        if (this.isMoreThanCertainTimes(mobile)) {
            throw new BusinessException("手机号:" + mobile + "已达获取短信次数上限！");
        }

        boolean lastAuthCodeLog = this.isSendInOneMinute(mobile);
        if (lastAuthCodeLog) {
            throw new BusinessException("验证码发送太快！请稍等片刻...");
        }
        return true;
    }

    /**
     * 检查某手机号是否异常发送验证码
     * 规则：如果24小时内发送验证码超过10条，则认为是异常发送 返回true
     *
     * @param mobile
     * @return
     */
    private boolean isMoreThanCertainTimes(String mobile) {
        long countSendTimesOneDay = authCodeMapper.countSendTimesOneDay(mobile);
        if (countSendTimesOneDay >= Constants.AUTH_CODE_MAX_TIMES) return true;
        return false;
    }

    /**
     * 检查1分钟内是否发送过验证码
     * 忽略不同验证码类型
     *
     * @param mobile
     * @return 如果发送过验证码 则返回ture 否则返回false
     */
    private boolean isSendInOneMinute(String mobile) {
        Date lastLog = authCodeMapper.getLastLog(mobile);
        if (lastLog != null) {
            return this.compareMinutes(lastLog, 1);
        }
        return false;
    }

    private boolean compareMinutes(Date authCodeTime, Integer timeScale) {
        if (System.currentTimeMillis() - authCodeTime.getTime() < 1000 * 60 * timeScale) return true;
        return false;
    }

    /**
     * 返回验证码
     * @param name
     * @param type
     * @return
     */
    public AuthCode getAuthCode(String name, AuthCode.Type type){
        AuthCode authCode = new AuthCode();
        authCode.setName(name);
        authCode.setType(type.name());
        return authCodeMapper.getAuthCode(authCode);
    }


    /**
     * 生成手机验证码
     *
     * @param name
     * @param type
     * @return
     */
    private AuthCode genAuthCode(String name, String type) {

        AuthCode authCode = new AuthCode();
        authCode.setName(name);
        authCode.setType(type);

        AuthCode authCodeLast = authCodeMapper.getAuthCode(authCode);

        if (authCodeLast == null) {
            return generateAuthCode(name, type);
        }

        //重新生成一个,让上一个失效，为空或超过30分钟 TODO:通过配置文件设置超时时间
        if (System.currentTimeMillis() - authCodeLast.getRequstTime().getTime() > 1000 * 60 * Long.parseLong("30")) {
            authCodeLast.setIsInvalid(AuthCode.TYPE_IS_INVALID_YES);
            authCodeMapper.update(authCodeLast);
            return generateAuthCode(name, type);
        } else {
            return authCodeLast;
        }
    }

    /**
     * 生成验证码
     *
     * @param name
     * @param type
     * @return
     */
    private AuthCode generateAuthCode(String name, String type) {
        AuthCode authCodeNew = new AuthCode();
        authCodeNew.setName(name);
        String nowTimeLongStr = String.valueOf(System.currentTimeMillis());
//        authCodeNew.setAuthCode("9527");//TODO:测试调试时候使用
        authCodeNew.setAuthCode(nowTimeLongStr.substring(nowTimeLongStr.length() - 4, nowTimeLongStr.length()));
        authCodeNew.setIsInvalid(AuthCode.TYPE_IS_INVALID_NO);
        authCodeNew.setRequstTime(new Date(System.currentTimeMillis()));
        authCodeNew.setType(type);
        authCodeMapper.insert(authCodeNew);
        return authCodeNew;
    }







}

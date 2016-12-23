package eop.controller;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import eop.exception.BusinessException;
import eop.model.AuthCode;
import eop.model.User;
import eop.service.AuthCodeService;
import eop.util.*;
import eop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping
public class IndexController {
    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Resource private UserService userService;
    @Resource private AuthCodeService authCodeService;

    @RequestMapping(value = "/")
    public ModelAndView index(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        if (SessionUtil.getCurrentUser()==null){
            modelAndView.setViewName("login");
        }else {
            modelAndView.setViewName("redirect:usercenter");
        }
        return modelAndView;
    }

    /**
     *  登录
     * @param request
     */
    @RequestMapping("login")
    @ResponseBody
    public ResultMsg login(HttpServletRequest request) {
        ResultMsg resultMsg = new ResultMsg();
        try {
            String phone = request.getParameter("phone");//手机号
            String password = MD5Util.MD5Salt(request.getParameter("password"));//密码 使用MD5加密
            User userByPhone = userService.getByPhone(phone);
            //web安全设置
//            String verificationCode = request.getParameter("verificationCode");
//            if(!verifyCode(verificationCode,request)){
//                resultMsg.setResultCode("0");
//                resultMsg.setMessage("图形验证码错误");
//                return resultMsg;
//            }
            if(userByPhone == null){
                resultMsg.setResultCode("0");
                resultMsg.setMessage("手机号未注册");
                return resultMsg;
            }
            if(!userByPhone.getPassword().equals(password)){
                resultMsg.setResultCode("0");
                resultMsg.setMessage("密码错误");
                return resultMsg;
            }
            resultMsg.setResultCode("1");
            resultMsg.setMessage("登录成功！");

            String userIPAddress = request.getRemoteHost();
            User userLogin = userService.login(userByPhone.getUserId(), userIPAddress);
            request.getSession().setAttribute("loginUserInfo", userLogin);
            request.getSession().setAttribute("userHeadImg", userLogin.getHeadImg());

        } catch (BusinessException be) {
            be.printStackTrace();
            resultMsg.setResultCode("0");
            resultMsg.setMessage(be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            resultMsg.setResultCode("0");
            resultMsg.setMessage("请求错误！");
        }
        return resultMsg;
    }

    /**
     * 获取手机验证码
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "sendcode", method = RequestMethod.POST)//FIXME 此接口可能会被攻击
    @ResponseBody
    public ResultMsg sendAuthCode(HttpServletRequest request) throws Exception {
        ResultMsg resultMsg = new ResultMsg();
        try {
            //web安全设置
//            String verificationCode = request.getParameter("verificationCode");
//            if(!verifyCode(verificationCode,request)){
//                resultMsg.setResultCode("0");
//                resultMsg.setMessage("图形验证码错误");
//                return resultMsg;
//            }
            String smsType = request.getParameter("smsType");//短信类型
            String phone = request.getParameter("phone");//手机号
            String md5 = request.getParameter("str");//摘要
            if (authCodeService.sendSmsAuthCode(smsType, phone)) {
                resultMsg.setResultCode("1");
                resultMsg.setMessage("成功");
            } else {
                resultMsg.setResultCode("0");
                resultMsg.setMessage("发送短信失败，请稍后再试");
            }

        } catch (BusinessException be) {
            be.printStackTrace();
            resultMsg.setResultCode("0");
            resultMsg.setMessage(be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            resultMsg.setResultCode("0");
            resultMsg.setMessage("请求错误！");
        }
        return resultMsg;
    }

    /**
     * 注册
     * @param request
     * @return
     */
    @RequestMapping("register")
    @ResponseBody
    public ResultMsg register(HttpServletRequest request) {
        ResultMsg resultMsg = new ResultMsg();
        try {
            String phone = request.getParameter("phone");//手机号
            String password = request.getParameter("password");//密码
            String authCode = request.getParameter("authCode");//手机验证码

            //检查验证码是否正确
            if (!authCode.equals(authCodeService.getAuthCode(phone, AuthCode.Type.PHONE_REGISTER).getAuthCode())) {
                resultMsg.setMessage("手机验证码错误，请重新输入或再次获取！");
                resultMsg.setResultCode("0");
                return resultMsg;
            }
            //检查密码格式 TODO
//            if (RegexUtil.matcher()) {
//                resultMsg.setMessage("密码格式错误，请重新输入！");
//                resultMsg.setResultCode("0");
//                return resultMsg;
//            }

            User user = new User();
            user.setPassword(MD5Util.MD5Salt(password));
            user.setUuid(UUID.randomUUID().toString());
            user.setMobile(phone);
            user.setRegTime(new Date());
            user.setRegIp(HttpRequest.getIpAddr(request));
            user.setNickName(user.getMobile());
            user.setHeadImg("http://szb-head.oss-cn-shanghai.aliyuncs.com/201611031509084.png");
            User register = userService.register(user);
            authCodeService.setAuthCodeInvalid(user.getMobile(), AuthCode.Type.PHONE_REGISTER);

            String userIPAddress = request.getRemoteHost();
            User userLogin = userService.login(register.getUserId(), userIPAddress);
            //正常登陆
            request.getSession().setAttribute("loginUserInfo", userLogin);
            request.getSession().setAttribute("userHeadImg", userLogin.getHeadImg());

            resultMsg.setMessage("注册成功");
            resultMsg.setResultCode("1");

        } catch (BusinessException be) {
            be.printStackTrace();
            resultMsg.setResultCode("0");
            resultMsg.setMessage(be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            resultMsg.setResultCode("0");
            resultMsg.setMessage("请求错误");
        }
        return resultMsg;
    }

    @RequestMapping("findpwd")
    @ResponseBody
    public ResultMsg findPwd(HttpServletRequest request) {
        ResultMsg resultMsg = new ResultMsg();
        try {
            String phone = request.getParameter("phone");//手机号
            String password = MD5Util.MD5Salt(request.getParameter("password"));//密码 使用MD5加密
            String authCode = request.getParameter("authCode");//手机验证码

            if(SessionUtil.getCurrentUser() != null){
                phone = SessionUtil.getCurrentUser().getMobile();
            }

            if (StringUtils.isEmpty(phone)){
                resultMsg.setMessage("手机号码输入错误，请重新输入");
                resultMsg.setResultCode("0");
                return resultMsg;
            }

            if (SessionUtil.getCurrentUser() != null){
                User user = userService.getSimpleUserById(SessionUtil.getCurrentUserId());
                if (!phone.equals(user.getMobile())){
                    resultMsg.setMessage("手机号码非本账户绑定手机，请重新输入");
                    resultMsg.setResultCode("0");
                    return resultMsg;
                }
            }

            //检查验证码是否正确
            if (!authCode.equals(authCodeService.getAuthCode(phone, AuthCode.Type.PHONE_RESET_PASSWORD).getAuthCode())) {
                resultMsg.setMessage("手机验证码错误，请重新输入或再次获取！");
                resultMsg.setResultCode("0");
                return resultMsg;
            }

            //检查密码格式 TODO
//            if (RegexUtil.matcher()) {
//                resultMsg.setMessage("密码格式错误，请重新输入！");
//                resultMsg.setResultCode("0");
//                return resultMsg;
//            }

            User user = userService.getByPhone(phone);
            userService.resetPwd(password, user.getUserId());
            authCodeService.setAuthCodeInvalid(user.getMobile(), AuthCode.Type.PHONE_RESET_PASSWORD);

            resultMsg.setMessage("重设密码成功,请使用新密码登录");
            resultMsg.setResultCode("1");

        } catch (BusinessException be) {
            be.printStackTrace();
            resultMsg.setResultCode("0");
            resultMsg.setMessage(be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            resultMsg.setResultCode("0");
            resultMsg.setMessage("请求错误！");
        }
        return resultMsg;
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:";
    }

    private boolean verifyCode(String verificationCode,HttpServletRequest request){
        String realVerificationCode = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        if (!StringUtils.isEmpty(realVerificationCode)) {
            if (!realVerificationCode.equalsIgnoreCase(verificationCode)) {
                return false;
            }
        }
        return true;
    }

}

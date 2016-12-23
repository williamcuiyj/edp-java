package eop.service;

import eop.exception.BusinessException;
import eop.model.User;
public interface UserService {
    User login(Integer userId, String host);

    User update(User user);

    /**
     * 用户注册逻辑
     *
     * @param user
     * @return
     */
    User register(User user);

    User getSimpleUserById(Integer id);

    int updateHeadImg(User user);


    /**
     * 判断手机号是否已经存在
     * @param phone
     * @return 存在返回true 不存在返回false
     */
    boolean isExist(String phone);

    /**
     * 重设登录密码
     * @param pwd
     * @param userId
     * @return
     */
    int resetPwd(String pwd, Integer userId);

    User getByPhone(String phone);

    /**
     * 绑定手机号业务逻辑处理
     * @param userByToken
     */
    void bindPhone(User userByToken) throws BusinessException;
}

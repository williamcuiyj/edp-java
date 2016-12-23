package eop.service.impl;

import eop.exception.BusinessException;
import eop.model.User;
import eop.mapper.UserMapper;
import eop.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 用户登录
     *
     * @return 用户信息
     */
    @Override
    public User login(Integer userId, String host) {
        User userFromDB = userMapper.getById(userId);

        if (userFromDB == null) {
            return null;
        }

        userFromDB.setLogincount(userFromDB.getLogincount() + 1);
        userFromDB.setLastip(host);
        userFromDB.setLasttime(new Date(System.currentTimeMillis()));
        userMapper.update(userFromDB);
        return userFromDB;
    }

    @Override
    public User getSimpleUserById(Integer id) {
        User fullUser = userMapper.getById(id);
        String nickName = fullUser.getNickName();
        User user = new User();
        user.setNickName(nickName);
        if (fullUser.getHeadImg() == null) {
            user.setHeadImg("http://szb-head.oss-cn-shanghai.aliyuncs.com/201611031509084.png");
        } else {
            user.setHeadImg(fullUser.getHeadImg());
        }
        user.setStatus(fullUser.getStatus());
        user.setMobile(fullUser.getMobile());
        user.setCompany(fullUser.getCompany());
        user.setCompanyAddr(fullUser.getCompanyAddr());
        user.setCompanyTitle(fullUser.getCompanyTitle());
        user.setCompanyPhone(fullUser.getCompanyPhone());
        user.setRealName(fullUser.getRealName());
        user.setProvince(fullUser.getProvince());
        user.setQq(fullUser.getQq());
        user.setEmail(fullUser.getEmail());
        user.setSchool(fullUser.getSchool());
        user.setFinishSchool(fullUser.getFinishSchool());
        user.setDegree(fullUser.getDegree());
        user.setGender(fullUser.getGender());

        return user;
    }

    @Override
    public User update(User user) {
        userMapper.update(user);
        return user;
    }

    /**
     * 用户注册处理
     *
     * @param user
     * @return
     */
    @Override
    public User register(User user) {
        user.setRegTime(new Date());
        user.setStatus("1");
        user.setLogincount(0);
        user.setEmail("");
        userMapper.insert(user);
        return user;
    }



    @Override
    public int updateHeadImg(User user) {
        return userMapper.updateHeadImg(user);
    }


    /**
     * 判断手机号是否已经存在
     *
     * @param phone
     * @return 存在返回true 不存在返回false
     */
    @Override
    public boolean isExist(String phone) {
        if (userMapper.getUserByPhone(phone) != null) return true;
        else return false;
    }

    @Override
    public int resetPwd(String pwd, Integer userId) {
        return userMapper.resetPwd(pwd, userId);
    }

    @Override
    public User getByPhone(String phone) {
        return userMapper.getUserByPhone(phone);
    }

    @Override
    public void bindPhone(User user) throws BusinessException {
        userMapper.bindPhone(user);
    }
}
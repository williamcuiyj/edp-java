package eop.mapper;

import org.apache.ibatis.annotations.Param;
import eop.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    int insert(User entity);

    int update(User entity);

    User getById(Integer id);

    User getUserByUUId(String uuid);

    /**
     * 根据手机号查询用户
     *
     * @param phone
     * @return
     */
    User getUserByPhone(String phone);

    /**
     * 更换用户头像url
     */
    int updateHeadImg(User user);



    /**
     * 根据昵称查询用户信息
     * @param nickName
     * @return
     */
    User getUserByNickName(String nickName);


    /**
     * 重设登录密码
     * @param pwd
     * @param userId
     * @return
     */
    int resetPwd(@Param("pwd") String pwd, @Param("userId") Integer userId);

    void bindPhone(User user);
}

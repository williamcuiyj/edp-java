package eop.mapper;

import org.springframework.stereotype.Repository;
import eop.base.BaseMapper;
import eop.model.AuthCode;
import eop.model.AuthCodeLog;
import eop.vo.query.AuthCodeQuery;

import java.util.Date;
import java.util.List;

@Repository
public interface AuthCodeMapper extends BaseMapper<AuthCode,AuthCodeQuery> {


    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    List<AuthCode> findPage(AuthCodeQuery query);

    /**
     * 获取验证码
     *
     * @param authCode
     * @return
     */
    AuthCode getAuthCode(AuthCode authCode);


    /**
     * 统计某手机号在24小时内发送的验证码数量
     *
     * @param phone
     * @return
     */
    long countSendTimesOneDay(String phone);

    /**
     * 获取某手机号最后一次发送的验证码
     *
     * @param phone
     * @return
     */
    Date getLastLog(String phone);


    /**
     * 插入一条短信发送日志
     * @param entity
     * @return
     */
    int insertAuthCodeLog(AuthCodeLog entity);
}

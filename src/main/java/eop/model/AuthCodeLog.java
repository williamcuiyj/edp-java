package eop.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

public class AuthCodeLog implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;

    //alias
    public static final String TABLE_ALIAS = "AuthCodeLog";
    public static final String ALIAS_ID = "messageLogId";
    public static final String ALIAS_MESSAGE_TIME = "发送时间";
    public static final String ALIAS_MESSAGE_MOBILE = "手机号";
    public static final String ALIAS_MESSAGE_TYPE = "短信类型";
    public static final String ALIAS_MESSAGE_AUTH_CODE = "验证码";

    /**
     * id db_column: auth_code_log_id
     */
    private Integer authCodeLogId;
    /**
     * 发送时间 db_column: auth_code_time
     */
    private Date authCodeTime;
    /**
     * 手机号 db_column: auth_code_mobile
     */
    private String authCodeMobile;
    /**
     * 短信类型 db_column: auth_code_type
     */
    private String authCodeType;
    /**
     * 验证码 db_column: auth_code
     */
    private String authCode;

    /**
     * auth_code_ipaddr 登录IP
     */
    private String authCodeIpAddr;

    //columns END

    public AuthCodeLog() {
    }

    public AuthCodeLog(
            Integer authCodeLogId) {
        this.authCodeLogId = authCodeLogId;
    }

    public String getAuthCodeIpAddr() {
        return authCodeIpAddr;
    }

    public void setAuthCodeIpAddr(String authCodeIpAddr) {
        this.authCodeIpAddr = authCodeIpAddr;
    }

    public Integer getAuthCodeLogId() {
        return authCodeLogId;
    }

    public void setAuthCodeLogId(Integer authCodeLogId) {
        this.authCodeLogId = authCodeLogId;
    }

    public Date getAuthCodeTime() {
        return authCodeTime;
    }

    public void setAuthCodeTime(Date authCodeTime) {
        this.authCodeTime = authCodeTime;
    }

    public String getAuthCodeMobile() {
        return authCodeMobile;
    }

    public void setAuthCodeMobile(String authCodeMobile) {
        this.authCodeMobile = authCodeMobile;
    }

    public String getAuthCodeType() {
        return authCodeType;
    }

    public void setAuthCodeType(String authCodeType) {
        this.authCodeType = authCodeType;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("authCodeLogId", getAuthCodeLogId())
            .append("authCodeTime", getAuthCodeTime())
            .append("authCodeMobile", getAuthCodeMobile())
            .append("authCodeType", getAuthCodeType())
            .append("authCode", getAuthCode())
            .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getAuthCodeLogId())
            .toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof AuthCodeLog == false)
            return false;
        if (this == obj)
            return true;
        AuthCodeLog other = (AuthCodeLog) obj;
        return new EqualsBuilder()
            .append(getAuthCodeLogId(), other.getAuthCodeLogId())
            .isEquals();
    }
}

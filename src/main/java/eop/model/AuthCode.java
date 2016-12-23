package eop.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.validation.constraints.NotNull;

public class AuthCode implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;

    /**
     * 是否失效， 1 失效
     */
    public static final String TYPE_IS_INVALID_NO = "0";
    public static final String TYPE_IS_INVALID_YES = "1";

    public enum Type{
        PHONE_REGISTER,//手机注册
        PHONE_RESET_PASSWORD,//手机重设密码
        PHONE_BIND,//绑定手机号
        EMAIL_BIND//邮箱绑定
    }
    
    /**
     * id db_column: auth_code_ID
     */

    private Integer authCodeId;
    /**
     * 查询标识 db_column: NAME
     */
    private String name;
    /**
     * 请求时间 db_column: REQUST_TIME
     */
    @NotNull
    private java.util.Date requstTime;
    /**
     * 验证码 db_column: AUTH_CODE
     */
    private String authCode;
    /**
     * 是否有效 db_column: IS_INVALID
     */
    private String isInvalid;
    /**
     * 类型 db_column: TYPE
     */
    private String type;

    //columns END

    public AuthCode() {
    }

    public AuthCode(
      Integer authCodeId) {
        this.authCodeId = authCodeId;
    }

    public Integer getAuthCodeId() {
        return authCodeId;
    }

    public void setAuthCodeId(Integer authCodeId) {
        this.authCodeId = authCodeId;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }

    public void setRequstTime(java.util.Date value) {
        this.requstTime = value;
    }

    public java.util.Date getRequstTime() {
        return this.requstTime;
    }

    public void setAuthCode(String value) {
        this.authCode = value;
    }

    public String getAuthCode() {
        return this.authCode;
    }

    public void setIsInvalid(String value) {
        this.isInvalid = value;
    }

    public String getIsInvalid() {
        return this.isInvalid;
    }

    public void setType(String value) {
        this.type = value;
    }

    public String getType() {
        return this.type;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("AuthCodeId", getAuthCodeId())
            .append("Name", getName())
            .append("RequstTime", getRequstTime())
            .append("AuthCode", getAuthCode())
            .append("IsValid", getIsInvalid())
            .append("Type", getType())
            .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getAuthCodeId())
            .toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof AuthCode == false)
            return false;
        if (this == obj)
            return true;
        AuthCode other = (AuthCode) obj;
        return new EqualsBuilder()
            .append(getAuthCodeId(), other.getAuthCodeId())
            .isEquals();
    }
}

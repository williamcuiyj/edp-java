package eop.vo.query;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import eop.base.BaseQuery;

import java.io.Serializable;



public class AuthCodeQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** auth_code_ID */
	private Integer authCodeId;
	/** 查询标识 */
	private String name;
	/** 请求时间 */
	private java.util.Date requstTimeBegin;
	private java.util.Date requstTimeEnd;
	/** 验证码 */
	private String authCode;
	/** 是否有效 */
	private String isValid;
	/** 类型 */
	private String type;

    public Integer getAuthCodeId() {
        return authCodeId;
    }

    public void setAuthCodeId(Integer authCodeId) {
        this.authCodeId = authCodeId;
    }

    public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public java.util.Date getRequstTimeBegin() {
		return this.requstTimeBegin;
	}

	public void setRequstTimeBegin(java.util.Date value) {
		this.requstTimeBegin = value;
	}

	public java.util.Date getRequstTimeEnd() {
		return this.requstTimeEnd;
	}

	public void setRequstTimeEnd(java.util.Date value) {
		this.requstTimeEnd = value;
	}

	public String getAuthCode() {
		return this.authCode;
	}

	public void setAuthCode(String value) {
		this.authCode = value;
	}

	public String getIsValid() {
		return this.isValid;
	}

	public void setIsValid(String value) {
		this.isValid = value;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String value) {
		this.type = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}


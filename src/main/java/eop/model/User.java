package eop.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


public class User implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;

    private Integer userId;

    private String uuid;

    private String nickName;

    private String password;
    /**
     * 旧密码 db_column: 无
     */
    private String oldPwd;
    /**
     * 状态(0删除1正常2锁定) db_column: status
     */
    private String status;
    /**
     * 用户的email db_column: email
     */
    private String email;
    /**
     * 手机 db_column: mobile
     */
    private String mobile;

    private String headImg;

    private Integer logincount;

    /**
     * 上次登陆时间 db_column: lasttime
     */

    private java.util.Date lasttime;
    /**
     * 上次登陆ip db_column: lastip
     */
    private String lastip;
    /**
     * 注册ip db_column: reg_ip
     */
    private String regIp;
    /**
     * 注册时间 db_column: reg_time
     */
    private java.util.Date regTime;

    private String realName;

    private String qq;

    private String idNumber;

    private String gender;

    private String userType;

    private String province;

    private String company;

    private String companyTitle;

    private String companyAddr;

    private String companyPhone;

    private String school;

    private String degree;

    private String finishSchool;

    public User() {
    }

    public User(Integer userId) {
        this.userId = userId;
    }

    public boolean equals(Object obj) {
        if (obj instanceof User == false)
            return false;
        if (this == obj)
            return true;
        User other = (User) obj;
        return new EqualsBuilder().append(getUserId(), other.getUserId()).isEquals();
    }


    public Integer getLogincount() {
        return logincount;
    }

    public void setLogincount(Integer logincount) {
        this.logincount = logincount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getEmail() {
        return this.email;
    }



    public String getLastip() {
        return this.lastip;
    }

    public java.util.Date getLasttime() {
        return this.lasttime;
    }

    public String getMobile() {
        return this.mobile;
    }

    public String getPassword() {
        return this.password;
    }


    public String getRegIp() {
        return this.regIp;
    }

    public java.util.Date getRegTime() {
        return this.regTime;
    }

    public String getStatus() {
        return this.status;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public String getUuid() {
        return this.uuid;
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getUserId()).toHashCode();
    }

    public void setEmail(String value) {
        this.email = value;
    }


    public void setLastip(String value) {
        this.lastip = value;
    }

    public void setLasttime(java.util.Date value) {
        this.lasttime = value;
    }

    public void setMobile(String value) {
        this.mobile = value;
    }

    public void setPassword(String value) {
        this.password = value;
    }


    public void setRegIp(String value) {
        this.regIp = value;
    }

    public void setRegTime(java.util.Date value) {
        this.regTime = value;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    public void setUserId(Integer value) {
        this.userId = value;
    }

    public void setUuid(String value) {
        this.uuid = value;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getFinishSchool() {
        return finishSchool;
    }

    public void setFinishSchool(String finishSchool) {
        this.finishSchool = finishSchool;
    }

    public String getCompanyTitle() {
        return companyTitle;
    }

    public void setCompanyTitle(String companyTitle) {
        this.companyTitle = companyTitle;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }
}

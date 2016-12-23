<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>后台管理登录</title>
    <script type="application/javascript" src="${ctx}/js/jquery.min.js"></script>
</head>
<body>
<div id="primaryContainer">
    <div id="loginFrame" class="loginFrame">
        <div class="form">
                <input id="username" name="username" class="userId" maxlength="20"/>
                <input id="password" name="password" class="passWord" type="password" autocomplete=off maxlength="20"/>
                <input id="verificationCode" class="valid" maxlength="4" name="verificationCode"/>
                <img id="kaptchaImg" name="validimg" class="validImg" src="${ctx}/imagecode.jpg" alt="点击刷新"
                     onClick="changeVerifyCode()"/>
                <a href="javascript:;" onclick="changeVerifyCode()" class="refresh"> 刷 新 </a>
                <a class="loginBotton" href="javascript:check_login();" title="登录后台管理系统"></a>
                <p class="txtTip"><font color="#FF0000" size="2">${errorMsg}</font></p>
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    $(function () {
        //回车登陆验证事件
        $("#verificationCode").keypress(function (event) {
            if (event.keyCode == 13) {
                check_login();
            }
        });
    });

    //登陆
    function check_login() {
        if ($("#username").val().trim() == "") {
            alert("用户名不能为空！");
        } else if ($("#password").val().trim() == "") {
            alert("密码不能为空！");
        } else if ($("#verificationCode").val().trim() == "") {
            alert("验证码不能为空！");
        }
        else {

            $("#ff").submit();
        }
    }
    $('#loginOut').click(function () {
        $.confirm('系统提示', '您确定要退出本次登录?', function (r) {
            if (r) {
                location.href = "logout";
            }
        });

    });

    function changeVerifyCode() {
        $("#kaptchaImg").attr("src", "${ctx }/imagecode.jpg?" + Math.floor(Math.random() * 100));
    }
</script>
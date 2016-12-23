<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="application/javascript" src="${ctx}/js/jquery.min.js"></script>
</head>
<body>

</body>
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
</html>
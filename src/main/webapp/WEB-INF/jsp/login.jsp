
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
    <title>Title</title>

</head>
<body>

    <input id="uuidSalt" type="hidden" value="${uuidSalt}"/>
    登录页面<br>
    <form action="/login.html" method="post" onsubmit="return checkForm()">
        用户名：<input type="text" name="username"><br><br>
        密  码：<input id="pwd" type="password" name="password"><br><br>
        <input type="checkbox" name="rememberme" value="1">记住我7天
        <br><br>
        <input type="submit" value="登录">
    </form>
    <br><br>
    <shiro:guest>
        游客访问<a href="login.jsp">登录</a>
    </shiro:guest>

    <script type="text/javascript" src="../../static/lib/md5/md5a.js" charset="UTF-8"></script>
    <script type="text/javascript" src="../../static/lib/aes/aes.js" charset="UTF-8"></script>
    <script type="text/javascript" src="../../static/lib/aes/pad-zeropadding-min.js" charset="UTF-8"></script>
    <script type="text/javascript" src="../../static/lib/login.js" charset="UTF-8"></script>
</body>
</html>

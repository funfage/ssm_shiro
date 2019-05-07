<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/4/25
  Time: 21:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    登录成功后的后台主页面<br><br>
    <a href="/logout.html">退出登录</a><br><br>
    <a href="/admin/userlist.html">用户列表</a><br><br>
    <a href="/admin/adduser.html">添加用户</a><br><br>
    <br><br>
    <shiro:guest>
        游客访问<a href="login.jsp">登录</a>
    </shiro:guest>
</body>
</html>

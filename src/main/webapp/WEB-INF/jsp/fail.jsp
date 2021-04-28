<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
    <title>NICE 认证测试</title>
    <script type="text/javascript" src="/js/jquery-1.11.3.min.js"></script>
</head>
<body>
    <p><p><p><p>
     认证失败<br>
    <table border=1>
        <tr>
            <td>解密时间</td>
            <td>${sCipherTime } (YYMMDDHHMMSS)</td>
        </tr>
        <tr>
            <td>请求ID</td>
            <td>${sRequestNumber }</td>
        </tr>            
        <tr>
            <td>认证失败值</td>
            <td>${sErrorCode }</td>
        </tr>            
        <tr>
            <td>认证方法</td>
            <td>${sAuthType }</td>
        </tr>
     </table><br><br>        
    ${sMessage }<br>
</body>
</html>
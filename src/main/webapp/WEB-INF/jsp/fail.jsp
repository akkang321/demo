<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
    <title>NICE평가정보 - CheckPlus 안심본인인증 테스트</title>
</head>
<body>
    <p><p><p><p>
    본인인증이 실패하였습니다.<br>
    <table border=1>
        <tr>
            <td>복호화한 시간</td>
            <td>${sCipherTime } (YYMMDDHHMMSS)</td>
        </tr>
        <tr>
            <td>요청 번호</td>
            <td>${sRequestNumber }</td>
        </tr>            
        <tr>
            <td>본인인증 실패 코드</td>
            <td>${sErrorCode }</td>
        </tr>            
        <tr>
            <td>인증수단</td>
            <td>${sAuthType }</td>
        </tr>
     </table><br><br>        
    ${sMessage }<br>
</body>
</html>
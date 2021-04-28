<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
    <title>NICE 认证测试</title>
    <script type="text/javascript" src="/js/jquery-1.11.3.min.js"></script>
</head>
<body>
    
    <p><p><p><p>
         认证结束.<br>
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
            <td>NICE 返回ID</td>
            <td>${sResponseNumber }</td>
        </tr>            
        <tr>
            <td>认证方法</td>
            <td>${sAuthType }</td>
        </tr>
		<tr>
            <td>姓名</td>
            <td>${sName }</td>
        </tr>
		<tr>
            <td>重复加入确认值(DI)</td>
            <td>${sDupInfo }</td>
        </tr>
		<tr>
            <td>链接确认值(CI)</td>
            <td>${sConnInfo }</td>
        </tr>
		<tr>
            <td>生日 (YYYYMMDD)</td>
            <td>${sBirthDate }</td>
        </tr>
		<tr>
            <td>性别</td>
            <td>${sGender }</td>
        </tr>
				<tr>
            <td>国内/海外 </td>
            <td>${sNationalInfo }</td>
        </tr>
        </tr>
			<td>手机号</td>
            <td>${sMobileNo }</td>
        </tr>
		<tr>
			<td>通信公司</td>
			<td>${sMobileCo }</td>
        </tr>
		<tr>
			<td colspan="2">인증 후 결과값은 내부 설정에 따른 값만 리턴받으실 수 있습니다. <br>
			일부 결과값이 null로 리턴되는 경우 관리담당자 또는 계약부서(02-2122-4615)로 문의바랍니다.</td>
		</tr>
		</table><br><br>        
    ${sMessage}<br>
</body>
</html>
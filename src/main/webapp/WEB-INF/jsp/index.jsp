<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/js/jquery-1.11.3.min.js"></script>
<script language='javascript'>
	window.name ="Parent_window";
	function fnPopup(){
		$.ajax({
			type: "post",
			url: "/ajax/doCheck",
			datatype:"json",
			success:function( data ) {
				$("#EncodeData").val(data.sEncData);
				window.open('', 'popupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
				document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
				document.form_chk.target = "popupChk";
				document.form_chk.submit();
			}
		});
	}
	</script>

</head>
<body>
	<form name="form_chk" method="post">
		<input type="hidden" name="m" value="checkplusService">						<!-- 필수 데이타로, 누락하시면 안됩니다. -->
		<input type="hidden" id="EncodeData" name="EncodeData">		<!-- 위에서 업체정보를 암호화 한 데이타입니다. -->
	    
		<a href="javascript:fnPopup();"> 认证</a>
	</form>
</body> 
</html> 
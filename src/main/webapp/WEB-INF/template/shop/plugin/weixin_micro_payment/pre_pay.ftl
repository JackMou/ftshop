<!DOCTYPE html>
<html>
<head>
	<meta charset="${requestCharset!"utf-8"}">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="author" content="FTSHOP Team">
	<meta name="copyright" content="FTSHOP">
	<title>${message("shop.payment.prePay")} [#if showPowered] - Powered By FTSHOP[/#if]</title>
	<link href="${base}/favicon.ico" rel="icon">
	<!--[if lt IE 9]>
		<script src="${base}/resources/common/js/html5shiv.js"></script>
		<script src="${base}/resources/common/js/respond.js"></script>
	<![endif]-->
    <script src="${base}/resources/common/js/jquery.js"></script>
</head>
<body onload="javascript: document.forms[0].submit();">

	<form action="${requestUrl}" method="${requestMethod!"get"}"[#if requestCharset?has_content] accept-charset="${requestCharset}"[/#if]>
		<input name="openid" type="hidden" id="openId" />
	</form>
    <script>
        $(function(){
            var openId = localStorage.getItem('openId');
            if(openId){
                $('#openId').val(openId);
            } else {
                alert('openId为空');
            }
        });
    </script>
</body>
</html>
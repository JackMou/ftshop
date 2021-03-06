<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="author" content="FTSHOP Team">
	<meta name="copyright" content="FTSHOP">
	<title>${message("shop.payment.payResult")}[#if showPowered] - Powered By FTSHOP[/#if]</title>
	<link href="${base}/favicon.ico" rel="icon">
	<link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
	<link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
	<link href="${base}/resources/common/css/font-awesome.css" rel="stylesheet">
	<link href="${base}/resources/common/css/base.css" rel="stylesheet">
	<link href="${base}/resources/shop/css/base.css" rel="stylesheet">
	<link href="${base}/resources/shop/css/payment.css" rel="stylesheet">
	<!--[if lt IE 9]>
		<script src="${base}/resources/common/js/html5shiv.js"></script>
		<script src="${base}/resources/common/js/respond.js"></script>
	<![endif]-->
	<script src="${base}/resources/common/js/jquery.js"></script>
	<script src="${base}/resources/common/js/bootstrap.js"></script>
	<script src="${base}/resources/common/js/jquery.qrcode.js"></script>
	<script src="${base}/resources/common/js/underscore.js"></script>
	<script src="${base}/resources/common/js/url.js"></script>
	<script src="${base}/resources/common/js/velocity.js"></script>
	<script src="${base}/resources/common/js/velocity.ui.js"></script>
	<script src="${base}/resources/common/js/base.js"></script>
	<script src="${base}/resources/shop/js/base.js"></script>
	[#noautoesc]
		[#escape x as x?js_string]
			<script>
                $().ready(function() {
                    var i = 10;
                    var timer = setInterval(function(){
                        --i;
                        if(i === 0) {
                            clearInterval(timer);
                            window.location.href = "${base}${redirectUrl}";
                        }
                        $('#time').text(i);
                    }, 1000);
                });
			</script>
            <style>
                main {
                    position: relative;
                    padding: 100px 0px;
                    background: url(../resources/common/images/error_background.png) repeat-x;
                }

                .redirect {
                    text-align: center;
                    font-size: 16px;
                }

                .redirect p {
                    padding-bottom: 20px;
                }

                .redirect a {
                    text-decoration: underline;
                }
            </style>
		[/#escape]
	[/#noautoesc]
</head>
<body>
	[#include "/shop/include/main_header.ftl" /]
	[#include "/shop/include/main_sidebar.ftl" /]
	<main>
		<div class="redirect">
			<p>购买商品前，请先购买商家指定商.<br /><span id="time">10</span>秒后将自动跳转...</p>
            <a href="${base}${redirectUrl}">立即跳转</a>
		</div>
	</main>
	[#include "/shop/include/main_footer.ftl" /]
</body>
</html>
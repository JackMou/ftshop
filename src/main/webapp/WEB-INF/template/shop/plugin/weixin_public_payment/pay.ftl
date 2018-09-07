<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="author" content="FTSHOP Team">
	<meta name="copyright" content="FTSHOP">
	<title>${message("shop.payment.pay")} [#if showPowered] - Powered By FTSHOP[/#if]</title>
	<link href="${base}/favicon.ico" rel="icon">
	<!--[if lt IE 9]>
		<script src="${base}/resources/common/js/html5shiv.js"></script>
		<script src="${base}/resources/common/js/respond.js"></script>
	<![endif]-->
	<script src="${base}/resources/common/js/jweixin.js"></script>
</head>
<body>
	[#noautoesc]
		[#escape x as x?js_string]
			<script>
                if (typeof WeixinJSBridge == "undefined") {
                    if (document.addEventListener) {
                        document.addEventListener("WeixinJSBridgeReady", onBridgeReady, false);
                    } else if (document.attachEvent) {
                        document.attachEvent("WeixinJSBridgeReady", onBridgeReady);
                        document.attachEvent("onWeixinJSBridgeReady", onBridgeReady);
                    }
                } else {
                    onBridgeReady();
                }
                
                function onBridgeReady() {
                    WeixinJSBridge.invoke("getBrandWCPayRequest", {
                        appId: "${appId}",
                        timeStamp: "${timeStamp}",
                        nonceStr: "${nonceStr}",
                        package: "${package}",
                        signType: "${signType}",
                        paySign: "${paySign}"
                    }, function(res) {
                        if (res.err_msg == "get_brand_wcpay_request:ok") {
                            location.href = "${postPayUrl}";
                        }
                        else{
                            history.go(-2);
                        }
                    });
                }
			</script>
		[/#escape]
	[/#noautoesc]
</body>
</html>
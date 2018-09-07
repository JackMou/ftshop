<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="author" content="FTSHOP Team">
	<meta name="copyright" content="FTSHOP">
	<title>${message("shop.socialUserLogin.signInResult")}[#if showPowered] - Powered By FTSHOP[/#if]</title>
	<link href="${base}/favicon.ico" rel="icon">
	<script src="${base}/resources/common/js/jquery.js"></script>
	<script src="${base}/resources/common/js/base.js"></script>
	[#noautoesc]
		[#escape x as x?js_string]
			<script>
			$().ready(function() {
				
				var $document = $(document);
				
				[#if socialUser??]
					[#if socialUser.user??]
						$document.trigger("loggedIn.ftshop.user", [{
							type: "member",
							username: "${socialUser.user.username?js_string}"
						}]);
                        location.href = "${base}${(redirectUrl!memberLoginSuccessUrl)}";
					[#else]
						location.href = "${base}${memberLoginUrl}?socialUserId=${socialUser.id}&uniqueId=${socialUser.uniqueId}&loginPluginId=${socialUser.loginPluginId}";
					[/#if]
				[/#if]
			
			});
			</script>
		[/#escape]
	[/#noautoesc]
</head>
<body>
	[#if !socialUser??]
		<p>${message("shop.socialUserLogin.failure")}</p>
	[/#if]
</body>
</html>
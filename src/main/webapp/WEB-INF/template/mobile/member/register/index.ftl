<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta name="author" content="FTSHOP Team">
    <meta name="copyright" content="FTSHOP">
    <title>${message("member.register.title")}[#if showPowered] - Powered By FTSHOP[/#if]</title>
    <link href="${base}/favicon.ico" rel="icon">
    <link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
    <link href="${base}/resources/common/css/font-awesome.css" rel="stylesheet">
    <link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
    <link href="${base}/resources/common/css/awesome-bootstrap-checkbox.css" rel="stylesheet">
    <link href="${base}/resources/common/css/bootstrap-datetimepicker.css" rel="stylesheet">
    <link href="${base}/resources/common/css/base.css" rel="stylesheet">
    <link href="${base}/resources/mobile/member/css/base.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="${base}/resources/common/js/html5shiv.js"></script>
    <script src="${base}/resources/common/js/respond.js"></script>
    <![endif]-->
    <script src="${base}/resources/common/js/jquery.js"></script>
    <script src="${base}/resources/common/js/bootstrap.js"></script>
    <script src="${base}/resources/common/js/bootstrap-growl.js"></script>
    <script src="${base}/resources/common/js/jquery.scrolltofixed.js"></script>
    <script src="${base}/resources/common/js/moment.js"></script>
    <script src="${base}/resources/common/js/bootstrap-datetimepicker.js"></script>
    <script src="${base}/resources/common/js/jquery.lSelect.js"></script>
    <script src="${base}/resources/common/js/jquery.validate.js"></script>
    <script src="${base}/resources/common/js/jquery.validate.additional.js"></script>
    <script src="${base}/resources/common/js/jquery.form.js"></script>
    <script src="${base}/resources/common/js/jquery.cookie.js"></script>
    <script src="${base}/resources/common/js/jquery.base64.js"></script>
    <script src="${base}/resources/common/js/underscore.js"></script>
    <script src="${base}/resources/common/js/url.js"></script>
    <script src="${base}/resources/common/js/base.js"></script>
    <script src="${base}/resources/mobile/member/js/base.js"></script>
    <style>
        .register main {
            padding: 10px;
            background-color: #ffffff;
        }

        .register main .btn-primary {
            margin-bottom: 10px;
        }
    </style>
	[#noautoesc]
        [#escape x as x?js_string]
			<script>
                function loaded() {
                    [#--console.info("1111111111111");--]
                    [#--console.info(${loginPlugin.id});--]
                    [#--$.ajax({--]
                        [#--type: "GET",--]
                        [#--url: "${base}/social_user_login?loginPluginId=weixinPublicLoginPlugin",--]
                        [#--success: function (res) {--]
                            [#--console.info("222222222");--]
                        [#--}--]
                    [#--});--]
                    //判断是否是微信浏览器的函数

                    //window.navigator.userAgent属性包含了浏览器类型、版本、操作系统类型、浏览器引擎类型等信息，这个属性可以用来判断浏览器类型
                    var ua = window.navigator.userAgent.toLowerCase();
                    //通过正则表达式匹配ua中是否含有MicroMessenger字符串
                    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
                        if($("#registerFlag").length <= 0) {
                            //元素存在时执行的代码
                            $('#weixin>img').trigger('click') ;
                        }
                    }else{
                        return false;
                    }


                }
                $().ready(function () {

                    var $document = $(document);
                    var $registerForm = $("#registerForm");
                    var $spreadMemberUsername = $("input[name='spreadMemberUsername']");
                    var $username = $("#username");
                    var $areaId = $("#areaId");
                    var $captcha = $("#captcha");
                    var $captchaImage = $("[data-toggle='captchaImage']");

                    var $show = $(".isshowName");
                    var $required = false

                    // 推广用户
                    var spreadUser = $.getSpreadUser();
                    if (spreadUser != null) {
                        $spreadMemberUsername.val(spreadUser.username);
                    }

                    // 地区选择
                    $areaId.lSelect({
                        url: "${base}/common/area"
                    });
                    //控制名字等信息的显示与隐藏
                    $username.blur(function () {
                        $.ajax({
                            type: "GET",
                            url: "${base}/member/register/check_name",
                            data: {
                                username: $username.val()
                            },
                            success: function (res) {
                                if (res) {
                                    $show.show();
                                    $required = true;
                                } else {
                                    $show.hide();
                                    $required = false;
                                }
                            }
                        });
                    });
                    // 表单验证
                    $registerForm.validate({
                        rules: {
                            username: {
                                required: true,
                                minlength: 11,
                                maxlength: 11,
                                username: true,
                                notAllNumber: false,
                            [#--remote: {--]
                            [#--url: "${base}/member/register/check_username",--]
                            [#--cache: false--]
                            [#--}--]
                            },
                            password: {
                                required: false,
                                minlength: 4,
                                normalizer: function (value) {
                                    return value;
                                }
                            },
                            rePassword: {
                                required: false,
                                equalTo: "#password",
                                normalizer: function (value) {
                                    return value;
                                }
                            },
                            email: {
                                required: false,
                                email: true,
                                remote: {
                                    url: "${base}/member/register/check_email",
                                    cache: false
                                }
                            },
                            mobile: {
                                required: false,
                                mobile: true,
                                remote: {
                                    url: "${base}/member/register/check_mobile",
                                    cache: false
                                }
                            },
                            authCode: {
                                required: true
                            },
                            captcha: "required"
						[@member_attribute_list]
                            [#list memberAttributes as memberAttribute]
                                [#if memberAttribute.isRequired || memberAttribute.pattern?has_content]
									, "memberAttribute_${memberAttribute.id}": {
										[#if memberAttribute.isRequired]
											required: $required
                                            [#if memberAttribute.pattern?has_content],[/#if]
                                        [/#if]
										[#if memberAttribute.pattern?has_content]
											pattern: new RegExp("${memberAttribute.pattern}")
                                        [/#if]
                                    }
                                [/#if]
                            [/#list]
                        [/@member_attribute_list]
                        },
                        messages: {
                            username: {
                                remote: "${message("member.register.usernameExist")}"
                            },
                            email: {
                                remote: "${message("member.register.emailExist")}"
                            },
                            mobile: {
                                remote: "${message("member.register.mobileExist")}"
                            },
                            authCode: {
                                remote: "${message("member.register.authCodeError")}"
                            }
                        },
                        submitHandler: function (form) {
                            $(form).ajaxSubmit({
                                successMessage: false,
                                successRedirectUrl: redirectUrl!=null?"${base}"+redirectUrl:"${base}/"
                            });
                        }
                    });
                    //
                    function getQueryString(name) {
                        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
                        var r = window.location.search.substr(1).match(reg);
                        if (r != null) return unescape(r[2]); return null;
                    }
                    //设置cookie
                    var redirectUrl = getQueryString("redirectUrl");

                    // 用户注册成功
                    $registerForm.on("success.ftshop.ajaxSubmit", function () {
                        $document.trigger("registered.ftshop.user", [{
                            type: "member",
                            username: $username.val()
                        }]);
                    });

                    // 验证码图片
                    $registerForm.on("error.ftshop.ajaxSubmit", function () {
                        $captchaImage.captchaImage("refresh");
                    });

                    // 验证码图片
                    $captchaImage.on("refreshed.ftshop.captchaImage", function () {
                        $captcha.val("");
                    });

                    var $sendCode = $('#sendCode');
                    var sending = false;

                    $sendCode.on('click', function () {
                        if (!$username.val()) {
                            return false;
                        }
                        [#if setting.captchaTypes?? && setting.captchaTypes?seq_contains("MEMBER_REGISTER")]
                            if (!$captcha.val()) {
                                return false;
                            }
                        [/#if]
                        if (sending) {
                            return false;
                        }

                        $.ajax({
                            type: "POST",
                            url: "${base}/member/register/authCode",
                            data:
                                    {
                                        username: $username.val(),
                                        captcha: $captcha.val(),
                                        captchaId: $("#captchaId").val(),
                                        captchaType: "1"
                                    },
                            success: function (res) {
                                if (res === 'success') {
                                    sending = true;
                                    $sendCode.addClass('disabled');
                                    var i = 0;
                                    var t = setInterval(function () {
                                        i++;
                                        $sendCode.text(59 - i);
                                        if (i == 59) {
                                            $sendCode.text('重新获取').removeClass('disabled');
                                            sending = false;
                                            clearInterval(t);
                                        }
                                    }, 1000);
                                }
                            }
                        })
                    });
                });
            </script>
        [/#escape]
    [/#noautoesc]
</head>
<body class="member register" onload="loaded();">
<header class="header-default" data-spy="scrollToFixed">
    <div class="container-fluid">
        <div class="row">
            <div class="col-xs-1">
                <a href="javascript:;" data-action="back">
                    <i class="iconfont icon-back"></i>
                </a>
            </div>
            <div class="col-xs-10">
                <h5>
						[#if socialUserId?has_content && uniqueId?has_content]
                            ${message("member.register.bind")}
                        [#else]
                            ${message("member.login.title")}
                        [/#if]
                </h5>
                [#if socialUserId?has_content && uniqueId?has_content]
                 <input id ="registerFlag" style="display: none">
                [/#if]

            </div>
        </div>
    </div>
</header>
<main>
    <div class="container-fluid">
        <form id="registerForm" action="${base}/member/register/submit" method="post">
            <input name="socialUserId" type="hidden" value="${socialUserId}">
            <input name="uniqueId" type="hidden" value="${uniqueId}">
            <input name="loginPluginId" type="hidden" value="${loginPluginId}">
            <input name="spreadMemberUsername" type="hidden">
            <div class="form-group">
                <label class="item-required" for="username">${message("member.register.username")}</label>
                <input id="username" name="username" class="form-control" type="text" minlength="11" maxlength="11"
                       autocomplete="off">
            </div>
        [#--<div class="form-group">--]
        [#--<label class="item-required" for="password">${message("member.register.password")}</label>--]
        [#--<input id="password" name="password" class="form-control" type="hidden" maxlength="20" autocomplete="off">--]
        [#--</div>--]
        [#--<div class="form-group">--]
        [#--<label class="item-required" for="rePassword">${message("member.register.rePassword")}</label>--]
        [#--<input id="rePassword" name="rePassword" class="form-control" type="password" maxlength="20" autocomplete="off">--]
        [#--</div>--]
            [#if setting.captchaTypes?? && setting.captchaTypes?seq_contains("MEMBER_REGISTER")]
					<div class="form-group">
                        <label class="item-required" for="captcha">${message("common.captcha.name")}</label>
                        <div class="input-group">
                            <input id="captcha" name="captcha" class="captcha form-control" type="text" maxlength="4"
                                   autocomplete="off">
                            <div class="input-group-btn">
                                <img class="captcha-image" src="${base}/resources/common/images/transparent.png"
                                     title="${message("common.captcha.imageTitle")}" data-toggle="captchaImage">
                            </div>
                        </div>
                    </div>
            [/#if]
            <div class="form-group">
                <label class="item-required" for="authCode">${message("member.register.authCode")}</label>
                <div class="input-group">
                    <input id="authCode" name="authCode" class="form-control" maxlength="6" type="text">
                    <span class="input-group-btn">
                            <button id="sendCode" class="btn btn-default"
                                    type="button">${message("member.register.sendAuthCode")}</button>
                        </span>
                </div>
            </div>
        [#--<div class="form-group">--]
        [#--<label class="item-required" for="email">${message("member.register.email")}</label>--]
        [#--<input id="email" name="email" class="form-control"  maxlength="200" type="hidden">--]
        [#--</div>--]
        [#--<div class="form-group">--]
        [#--<label class="item-required" for="mobile">${message("member.register.mobile")}</label>--]
        [#--<input id="mobile" name="mobile" class="form-control"  maxlength="200" type="hidden">--]
        [#--</div>--]
            <div class="isshowName" style="display: none">
				[@member_attribute_list]
					[#list memberAttributes as memberAttribute]
						<div class="form-group">
                            <label[#if memberAttribute.isRequired]
                                    class="item-required"[/#if][#if memberAttribute.type != "GENDER" && memberAttribute.type != "AREA" && memberAttribute.type != "CHECKBOX"]
                                    for="memberAttribute_${memberAttribute.id}"[/#if]>${memberAttribute.name}</label>
							[#if memberAttribute.type == "NAME"]
								<input id="memberAttribute_${memberAttribute.id}"
                                       name="memberAttribute_${memberAttribute.id}" class="form-control" type="text"
                                       maxlength="200">
                            [#elseif memberAttribute.type == "GENDER"]
								<p>
                                [#list genders as gender]
										<div class="radio radio-inline">
                                            <input id="${gender}" name="memberAttribute_${memberAttribute.id}"
                                                   type="radio" value="${gender}">
                                            <label for="${gender}">${message("Member.Gender." + gender)}</label>
                                        </div>
                                [/#list]
								</p>
                            [#elseif memberAttribute.type == "BIRTH"]
								<div class="input-group">
                                    <input id="memberAttribute_${memberAttribute.id}"
                                           name="memberAttribute_${memberAttribute.id}" class="form-control" type="text"
                                           data-provide="datetimepicker">
                                    <span class="input-group-addon">
										<i class="iconfont icon-calendar"></i>
									</span>
                                </div>
                            [#elseif memberAttribute.type == "AREA"]
								<div class="input-group">
                                    <input id="areaId" name="memberAttribute_${memberAttribute.id}" type="hidden">
                                </div>
                            [#elseif memberAttribute.type == "ADDRESS"]
								<input id="memberAttribute_${memberAttribute.id}"
                                       name="memberAttribute_${memberAttribute.id}" class="form-control" type="text"
                                       maxlength="200">
                            [#elseif memberAttribute.type == "ZIP_CODE"]
								<input id="memberAttribute_${memberAttribute.id}"
                                       name="memberAttribute_${memberAttribute.id}" class="form-control" type="text"
                                       maxlength="200">
                            [#elseif memberAttribute.type == "PHONE"]
								<input id="memberAttribute_${memberAttribute.id}"
                                       name="memberAttribute_${memberAttribute.id}" class="form-control" type="text"
                                       maxlength="200">
                            [#elseif memberAttribute.type == "TEXT"]
								<input id="memberAttribute_${memberAttribute.id}"
                                       name="memberAttribute_${memberAttribute.id}" class="form-control" type="text"
                                       maxlength="200">
                            [#elseif memberAttribute.type == "SELECT"]
								<select id="memberAttribute_${memberAttribute.id}"
                                        name="memberAttribute_${memberAttribute.id}" class="form-control">
                                    <option value="">${message("common.choose")}</option>
									[#list memberAttribute.options as option]
										<option value="${option}">${option}</option>
                                    [/#list]
                                </select>
                            [#elseif memberAttribute.type == "CHECKBOX"]
								<p>
                                [#list memberAttribute.options as option]
										<div class="checkbox checkbox-inline">
                                            <input id="${option}_${memberAttribute.id}"
                                                   name="memberAttribute_${memberAttribute.id}" type="checkbox"
                                                   value="${option}">
                                            <label for="${option}_${memberAttribute.id}">${option}</label>
                                        </div>
                                [/#list]
								</p>
                            [/#if]
                        </div>
                    [/#list]
                [/@member_attribute_list]
            </div>

            <button class="btn btn-primary btn-lg btn-block" type="submit">${message("member.login.submit")}</button>
        </form>
        <div class="row">
            <div class="col-xs-6 text-left">
                <a href="${base}/article/detail/1_1">${message("member.register.agreement")}</a>
            </div>
        [#--<div class="col-xs-6 text-right">--]
        [#--<a href="${base}/member/login">${message("member.register.login")}</a>--]
        [#--</div>--]
        </div>
    </div>
    <a href="[#if successLoginRedirectUrl??]${base}/social_user_login?loginPluginId=weixinPublicLoginPlugin&successLoginRedirectUrl=${successLoginRedirectUrl}[#else]${base}/social_user_login?loginPluginId=weixinPublicLoginPlugin[/#if]" id="weixin" style="display: none" >
            <img src="" alt="">
    </a>
</main>
</body>
</html>
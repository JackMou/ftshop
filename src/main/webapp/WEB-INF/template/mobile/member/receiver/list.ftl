<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="author" content="FTSHOP Team"
	<meta name="copyright" content="FTSHOP">
	<title>${message("member.receiver.list")}[#if showPowered] - Powered By FTSHOP[/#if]</title>
	<link href="${base}/favicon.ico" rel="icon">
	<link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
	<link href="${base}/resources/common/css/font-awesome.css" rel="stylesheet">
	<link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
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
	<script src="${base}/resources/common/js/bootbox.js"></script>
	<script src="${base}/resources/common/js/jquery.cookie.js"></script>
	<script src="${base}/resources/common/js/underscore.js"></script>
	<script src="${base}/resources/common/js/url.js"></script>
	<script src="${base}/resources/common/js/velocity.js"></script>
	<script src="${base}/resources/common/js/velocity.ui.js"></script>
	<script src="${base}/resources/common/js/base.js"></script>
	<script src="${base}/resources/mobile/member/js/base.js"></script>
    <script src="https://res.wx.qq.com/open/js/jweixin-1.3.2.js"></script>
	[#noautoesc]
        [#escape x as x?js_string]
			<script>
                $().ready(function() {
                    var $addReceiver = $("#addReceiver");
                    $.ajax({
                        url: '${base}/shop/share/weixin',
                        methods: 'get',
                        data: {
                            url: encodeURIComponent(window.location.href.split('?')[0])
                        },
                        success: function(res) {
                            window.wx.config({
                                appId: res.appId,
                                timestamp: res.timestamp,
                                nonceStr: res.noncestr,
                                signature: res.signature,
                                jsApiList: ['getLocation']
                            });

                            window.wx.ready(function() {
                                // 获取gps位置
                                window.wx.getLocation({
                                    type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
                                    success: function (res) {
                                        var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                                        var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                                        var speed = res.speed; // 速度，以米/每秒计
                                        var accuracy = res.accuracy; // 位置精度
                                        if(latitude){
                                            $addReceiver.append('<a class="btn btn-primary btn-flat btn-block" href="${base}/member/receiver/add?location='+latitude+','+longitude+'">${message("member.receiver.add")}</a>');
                                           // window.localStorage.setItem('location', latitude+","+longitude);
                                        }else{
                                            $addReceiver.append('<a class="btn btn-primary btn-flat btn-block" href="${base}/member/receiver/add">${message("member.receiver.add")}</a>');
                                            //window.localStorage.removeItem('location');
                                        }
                                    }
                                });
                            });
                        }
                    });
                });
            </script>
        [/#escape]
    [/#noautoesc]
</head>
<body class="member">
	<header class="header-default" data-spy="scrollToFixed">
		<div class="container-fluid">
			<div class="row">
				<div class="col-xs-1">
                    <a href="javascript:;" data-action="back">
                        <i class="iconfont icon-back"></i>
                    </a>
					[#--<a href="${base}/member/index">--]
						[#--<i class="iconfont icon-back"></i>--]
					[#--</a>--]
				</div>
				<div class="col-xs-10">
					<h5>${message("member.receiver.list")}</h5>
				</div>
			</div>
		</div>
	</header>
	<main>
		<div class="container-fluid">
			[#if page.content?has_content]
				[#list page.content as receiver]
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="list-group">
								<div class="list-group-item">
									<div class="media">
										<h5 class="media-heading">
											<span title="${receiver.consignee}">${abbreviate(receiver.consignee, 30, "...")}</span>
											<span class="pull-right">${receiver.phone}</span>
										</h5>
										<span class="small" title="${receiver.areaName}${receiver.address}">${receiver.areaName}${abbreviate(receiver.address, 30, "...")}</span>
									</div>
								</div>
								<div class="list-group-item">
									<span class="small text-orange">${message("Receiver.isDefault")}: ${receiver.isDefault?string(message("member.receiver.true"), message("member.receiver.false"))}</span>
								</div>
							</div>
						</div>
						<div class="panel-footer text-right">
							<a class="btn btn-default btn-sm" href="edit?receiverId=${receiver.id}">${message("common.edit")}</a>
							<a class="btn btn-default btn-sm" href="javascript:;" data-action="delete" data-url="${base}/member/receiver/delete?receiverId=${receiver.id}">${message("common.delete")}</a>
						</div>
					</div>
				[/#list]
			[#else]
				<p class="no-result">${message("common.noResult")}</p>
			[/#if]
		</div>
	</main>
	<footer class="footer-action footer-default" data-spy="scrollToFixed" data-bottom="0">
		<div class="container-fluid" id="addReceiver">
		</div>
	</footer>
</body>
</html>
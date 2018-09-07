<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="author" content="FTSHOP Team">
	<meta name="copyright" content="FTSHOP">
	<title>我的余额</title>
	<link href="${base}/favicon.ico" rel="icon">
	<link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
	<link href="${base}/resources/common/css/font-awesome.css" rel="stylesheet">
	<link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
	<link href="${base}/resources/common/css/base.css" rel="stylesheet">
	<link href="${base}/resources/mobile/member/css/base.css" rel="stylesheet">
    <link href="${base}/resources/mobile/member/css/walletList.css" rel="stylesheet">
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

    [#noautoesc]
        [#escape x as x?js_string]
        <script>
            var userName=JSON.parse(localStorage.getItem('currentUser'));
            var username=userName.username;
            function loaded(){

                console.info(username);
                $.ajax({
                    url: '${base}/member/weixinWithdraw/queryWithdrawRecordList',
                    methods: 'post',
                    data: {
                        userName: username
                    },
                    success: function(res) {
                        var list='';
                        for(var i=0 ; i<res.length ;i++){
                            list+='<li>' +
                                    '<p>提现成功</p>' +
                                    '<span>'+formatDateTime(res[i].createdDate)+'</span>' +
                                    '<div class="suc">- '+res[i].amout+' 元</div>' +
                                    '</li>'
						}
                        $(".tixian ul").append(list);
                    }
                });
                $.ajax({
                    url: '${base}/api/spread',
                    methods: 'post',
                    success: function(res) {
                        var data=res.data.content;
                        var list='';
                        for(var i=0 ; i<data.length ;i++){
                            list+='<li>' +
                                    '<p>领取奖励</p>' +
                                    '<span>'+formatDateTime(data[i].createdDate)+'</span>' +
                                    '<div class="add">+ '+data[i].feeAmount+' 元</div>' +
                                    '</li>'
                        }
                        $(".jiangli ul").append(list);
                    }
                });
            }

            function formatDateTime(timeStamp) {
                var date = new Date();
                date.setTime(timeStamp);
                var y = date.getFullYear();
                var m = date.getMonth() + 1;
                m = m < 10 ? ('0' + m) : m;
                var d = date.getDate();
                d = d < 10 ? ('0' + d) : d;
                var h = date.getHours();
                h = h < 10 ? ('0' + h) : h;
                var minute = date.getMinutes();
                var second = date.getSeconds();
                minute = minute < 10 ? ('0' + minute) : minute;
                second = second < 10 ? ('0' + second) : second;
                return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
            };
            $(function(){
                $('#tabs li').click(function(e) {
                    var i = $(this).index();
                    console.info(i);
                    $(this).addClass('select').siblings().removeClass('select');
                   $('#content>div').eq(i).show().siblings().hide();
                });
            });
            // $().ready(function () {
            //     var $money=parseInt($(".money p").text());
            //
            //     if($money<100){
            //
            //     }
            // });
        </script>
        [/#escape]
    [/#noautoesc]
</head>
<body class="member" onload="loaded();">
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
					<h5>明细</h5>
				</div>
			</div>
		</div>
	</header>
	<div  class="tab-contain">
		<ul id="tabs">
			<li class="select"><a >领取记录</a></li>
			<li><a>提现记录</a></li>
		</ul>
        <hr>
        <div id="content">
            <div class="tab jiangli" >
				<ul >
					[#--<li>--]
						[#--<p>领取奖励</p>--]
						[#--<span>2018-05-28 23:34:23 小甜甜购买成功</span>--]
						[#--<div class="add">+ 120 元</div>--]
					[#--</li>--]
				</ul>
            </div>
            <div class="tab tixian yc">
                <ul >
                    [#--<li>--]
                        [#--<p>提现成功</p>--]
                        [#--<span>2018-05-28 23:34:23 小甜甜购买成功</span>--]
                        [#--<div class="suc">- 120 元</div>--]
                    [#--</li>--]
                </ul>
            </div>
        </div>
	</div>

</body>
</html>
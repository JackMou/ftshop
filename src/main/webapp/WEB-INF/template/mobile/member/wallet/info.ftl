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
    <link href="${base}/resources/mobile/member/css/walletInfo.css" rel="stylesheet">
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
                    url: '${base}/member/weixinWithdraw/getUserAccount',
                    methods: 'post',
                    data: {
                        userName: username
                    },
                    success: function(res) {
                        $(".amount").text(res.balance);
                        if(res.balance>=100){
                            $("#tx_btn").addClass("btn-primary");
                            $("#tx_btn").on('click',function(){
                                $.ajax({
                                    url: '${base}/member/weixinWithdraw/doWithdraw',
                                    methods: 'post',
                                    data: {
                                        userName: username
                                    },
                                    success: function(res) {
                                        $('#myModal').modal('show');
                                        if(res.code=="0"){
                                            $(".sf").text(res.msg);
                                        }else{
                                            $(".sf").text(res.msg);
                                        }
                                    }
                                })
                            })
                        }
                    }
                });
            }
            function update(){
                window.location.reload();
            }
            $().ready(function () {

            });
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
					<h5>我的余额</h5>
				</div>
                <div class="col-xs-1">
                    <h6><a href="${base}/member/wallet/list">明细</a></h6>
                </div>
			</div>
		</div>
	</header>
	<div>
		<div class="bg">

		</div>
		<div class="money">
			<span>当前余额（元）</span>
			<p class="amount"></p>

		</div>
	</div>
    <div class="form-group">
        <button class="btn btn-lg btn-block" type="submit" id="tx_btn">提现</button>
        <span class="ts">* 余额到达一百元，可直接整百提现到微信</span>
    </div>
    <div class="modal fade" tabindex="-1" role="dialog" id="myModal">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="update()"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">通知</h4>
                </div>
                <div class="modal-body">
                    <p class="sf"></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="update()">关闭</button>
                    [#--<button type="button" class="btn btn-primary">Save changes</button>--]
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</body>
</html>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="author" content="FTSHOP Team">
	<meta name="copyright" content="FTSHOP">
	<meta name="keywords" content="用户须知">
	<meta name="description" content="用户须知">
	<title>用户须知</title>
	<link href="${base}/favicon.ico" rel="icon">
	<link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
	<link href="${base}/resources/common/css/font-awesome.css" rel="stylesheet">
	<link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
	<link href="${base}/resources/common/css/base.css" rel="stylesheet">
	<link href="${base}/resources/mobile/shop/css/base.css" rel="stylesheet">
	<link href="${base}/resources/mobile/shop/css/article.css" rel="stylesheet">
	<!-- add -->
	<link href="${base}/resources/mobile/member/css/common.css" rel="stylesheet" />
	<link href="${base}/resources/mobile/member/css/animate.min.css" rel="stylesheet" /> 
	<!--[if lt IE 9]>
		<script src="${base}/resources/common/js/html5shiv.js"></script>
		<script src="${base}/resources/common/js/respond.js"></script>
	<![endif]-->
	<script src="${base}/resources/common/js/jquery.js"></script>
	<script src="${base}/resources/common/js/bootstrap.js"></script>
	<script src="${base}/resources/common/js/bootstrap-growl.js"></script>
	<script src="${base}/resources/common/js/jquery.scrolltofixed.js"></script>
	<script src="${base}/resources/common/js/jquery.cookie.js"></script>
	<script src="${base}/resources/common/js/underscore.js"></script>
	<script src="${base}/resources/common/js/url.js"></script>
	<script src="${base}/resources/common/js/base.js"></script>
	<script src="${base}/resources/mobile/member/js/base.js"></script>
	
	
	<!-- <script src="${base}/resources/mobile/member/js/rebate.js"></script> -->
	<script>
		$().ready(function() {
			
			var $articleSearchForm = $("#articleSearchForm");
			var $keyword = $("#articleSearchForm [name='keyword']");
			var $hits = $("#hits");
			
			// 搜索
			$articleSearchForm.submit(function() {
				if ($.trim($keyword.val()) == "") {
					return false;
				}
			});
			
			// 点击数
			$.get("/article/hits/8").done(function(data) {
				$hits.text(data.hits);
			});
		
		});
		
	</script>
</head>
<body class="shop article-detail">
	<header class="header-default" data-spy="scrollToFixed">
		<div class="container-fluid">
			<div class="row">
				<div class="col-xs-1">
					<a href="javascript:;" data-action="back">
						<i class="iconfont icon-back"></i>
					</a>
				</div>
				<div class="col-xs-10" style="font-size: 15px;">
					用户须知
				</div>
				<div class="col-xs-1">
					<div class="dropdown">
						<a href="javascript:;" data-toggle="dropdown">
							<i class="iconfont icon-sort"></i>
						</a>
						<ul class="dropdown-menu dropdown-menu-right">
							
							<li>
								<a href="/cart/list">
									<i class="iconfont icon-cart"></i>
									购物车
								</a>
							</li>
							<li>
								<a href="/member/index">
									<i class="iconfont icon-people"></i>
									会员中心
								</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</header>
	<main>
		<div class="container-fluid">
			
				<article>
					<p>
						欢迎加入全美食预约点餐全美食（以下简称全美食）!<br>
						在注册之前请务必仔细阅读以下条款：<br><br>
						<h5>一、须知内容</h5>
						1、本协议内容包括协议正文及所有全美食已经发布的或将来可能发布的各类规则。所有规则为本协议不可分割的组成部分，与协议正文具有同等法律效力。除另行明确声明外，任意全美食及其关联公司提供的服务均受本协议约束。<br>
						2、您应当在使用全美食服务之前认真阅读全部协议内容，如您对协议有任何疑问的，应向全美食咨询。但无论您事实上是否在使用全美食之前认真阅读了本协议内容，只要您使用全美食服务，则本协议即对您产生约束，届时您不应以未阅读本协议的内容或者未获得全美食对您问询的解答等理由，主张本协议无效，或要求撤销本协议。<br>
						3、您承诺接受并遵守本协议的约定。如果您不同意本协议的约定，您应立即停止注册程序或停止使用全美食服务。<br>
						4、全美食有权根据需要不时地制订、修改本协议或各类规则，并以网站公示的方式进行公告，不再单独通知您。变更后的协议和规则一经在网站公布后，立即自动生效。如您不同意相关变更，应当立即停止使用全美食服务。您继续使用全美食服务的，即表示您接受经修订的协议和规则。<br><br>
						<h5>二、会员</h5>
						1、申请资格<br>
						本须知中所指的成员是指拥有中华人民共和国公民资格，具有完全的民事行为能力，并能够独立承担法律责任的自然人。<br>
						2、会员注册<br>
						在您按照注册页面提示填写信息、阅读且同意本协议，并完成全部注册程序后或者以其他全美食允许的方式使用全美食服务，您即成为全美食的会员。在注册时，您应当按照法律法规要求，和注册页面的提示语要求，将注册信息填写完整清楚。您可以对账户设置用户名和密码，通过该用户名和密码可以登录全美食。您应对您的账户（会员名）和密码的安全，以及对通过您的账户（会员名）和密码实施的行为负责。除非有法律规定或司法裁定，且征得全美食的同意，否则，账户（会员名）和密码不得以任何方式转让、赠与或继承（与账户相关的财产权益除外）。如果发现任何人不当使用您的账户或有任何其他可能危及您的账户安全的情形时，您应当立即以有效方式通知全美食，要求全美食暂停相关服务。<br><br>
						<h5>三、会员权利</h5>
						3.1用户注册成为全美食的会员后，即刻可以享受全美食的提供的服务；<br>
						3.2会员奖励：对接一家餐厅且成功使用全美食平台、平台从次月开始给予332元的现金奖励、连续返12个月;且在第13个月给予398元的现金奖励（如果餐厅中途停止使用全美食平台、则奖励停止发放、需要重新成功对接后再继续发放）<br>
						3.3奖励发放：如果没有出现违规情况，美吖吖平台将按照规定给会员正常支付返利。<br>
						3.4发放时间：次月15号，遇节假日随延（比如5月份餐厅上线并使用全美食平台，6月份可得332元奖励，7月15日奖励到帐）。<br>
						3.5您了解并同意，全美食有权应政府部门（包括司法及行政部门）的要求，向其提供您在全美食填写的注册信息和返现记录等必要信息；<br><br>
						<h5>四、会员的义务</h5>
						4.1会员必须保证注册信息的准确性；<br>
						4.2如果会员填写的资料有变更，应及时通知全美食做出相应的修改；<br>
						4.3会员在使用全美食服务过程中实施的所有行为均遵守国家法律、法规等规范性文件及全美食各项规则的规定和要求，不违背社会公共利益或公共道德，不损害他人的合法权益，不违反本协议及相关规则。您如果违反前述承诺，产生任何法律后果的，您应以自己的名义独立承担所有的法律责任，并确保全美食免于因此产生任何损失。<br>
						4.4不使用任何装置、软件或例行程序干预或试图干预全美食的正常运作或正在全美食上进行的任何交易、活动。您不得采取任何将导致不合理的庞大数据负载加诸全美食网络设备的行动。<br>
					</p>
					<p><a href="javascript:;" class="bounceIn dialog">点我填写详细信息</a></p>
					<div id="rbutton" style="right: 20%;">
					    <svg width="100%" height="100%" id="rot">
					    <circle r="33" cx="40" cy="40" stroke="red" stroke-width="1" fill="transparent"></circle>
					    <circle r="5" cx="35" cy="6" fill="red"></circle>
					    <circle r="26" cx="50%" cy="50%" fill="red"></circle>
					  </svg>
					    <svg width="100%" height="100%" id="rot2">
					    <circle r="33" cx="40" cy="40" stroke="red" stroke-width="1" fill="transparent"></circle>
					    <circle r="6" cx="6" cy="40" fill="red"></circle>
					  </svg>
					    <div class="in">
					        <a href="javascript:;" class="bounceIn dialog">
					            <span>点我</span>
					            <span>填写</span></a>
					    </div>
					</div>
					
					<script language="javascript">
					  function agree(){
					       if(document.getElementById('cb').checked)
					              document.getElementById('tj').disabled=false;
					    else
					        document.getElementById('tj').disabled='disabled';  
					  }   
					</script>
					


					<div id="HBox">
						<form action="${base}/member/rebate/save" method="post" onsubmit="return false;">
							<ul class="list">
								<li>
									<strong>姓 名<font color="#ff0000"></font></strong>
									<div class="fl"><input type="text" name="username" value="" class="ipt username" /></div>
								</li>
								<li>
									<strong>手 机<font color="#ff0000"></font></strong>
									<div class="fl"><input type="text" name="mobile" value="" class="ipt mobile" /></div>
								</li>
								<li>
									<strong>美容院<font color="#ff0000"></font></strong>
									<div class="fl"><input type="text" name="consumerPlace" value="" class="ipt consumerPlace" /></div>
								</li>
								<li>
									<strong>餐厅名称<font color="#ff0000"></font></strong>
									<div class="fl"><input type="text" name="recommendDiningRoom" value="" class="ipt recommendDiningRoom" /></div>
								</li>
								<li>
									<strong>餐厅地址<font color="#ff0000"></font></strong>
									<div class="fl"><input type="text" name="eataddress" value="" class="ipt eataddress" /></div>
								</li>
								<li>
									<strong>餐厅联系人<font color="#ff0000"></font></strong>
									<div class="fl"><input type="text" name="linkman" value="" class="ipt linkman" /></div>
								</li>
								<li>
									<strong>联系人电话<font color="#ff0000"></font></strong>
									<div class="fl"><input type="text" name="linkmanTelephone" value="" class="ipt linkmanTelephone" /></div>
								</li>
								
								<li><input name="confirm"  type="checkbox" onclick="agree();" id="cb"/>我认真阅读并接受以上协议。</li>
								<li><input type="submit" value="确认提交" class="submitBtn" disabled="disabled" id="tj"/></li>
							</ul>
						</form>
					</div>
					<!-- add -->
					<script src="${base}/resources/mobile/member/js/jquery.hDialog.js"></script>
					<script>
						$(function(){
							var $el = $('.dialog');
							$el.hDialog();
	
							//返回顶部
							// $.goTop();
							//提交并验证表单
							$('.submitBtn').click(function() {
								var PhoneReg = /^0{0,1}(13[0-9]|15[0-9]|153|156|18[7-9])[0-9]{8}$/ ; //手机正则
								var $username = $('.username').val();
								var $mobile = $('.mobile').val();
								var $consumerPlace = $('.consumerPlace').val();
								var $recommendDiningRoom = $('.recommendDiningRoom').val();
								var $eataddress = $('.eataddress').val();
								var $linkman = $('.linkman').val();
								var $linkmanTelephone = $('.linkmanTelephone').val();
								if($username == ''){
									$.tooltip('姓名还没填呢...'); $('.username').focus();
								}else if($mobile == ''){
									$.tooltip('手机还没填呢...'); $('.mobile').focus();
								}else if(!PhoneReg.test($mobile)){
									$.tooltip('手机格式错咯...'); $('.mobile').focus();
								}else if($consumerPlace == ''){
									$.tooltip('所属美容院还没填呢...'); $('.consumerPlace').focus();
								}else if($recommendDiningRoom == ''){
									$.tooltip('餐厅名称还没填呢...'); $('.recommendDiningRoom').focus();
								}else if($eataddress == ''){
									$.tooltip('餐厅地址还没填呢...'); $('.eataddress').focus();
								}else if($linkman == ''){
									$.tooltip('餐厅联系人还没填呢...'); $('.linkman').focus();
								}else if($linkmanTelephone == ''){
									$.tooltip('餐厅联系人手机还没填呢...'); $('.linkmanTelephone').focus();
								}else if(!PhoneReg.test($linkmanTelephone)){
									$.tooltip('餐厅联系人手机格式错咯...'); $('.linkmanTelephone').focus();
								}else{
									 $.ajax({
								      type:'get',
								      url:'${base}/member/rebate/check',
								      data:{
								        mobile:$mobile,
								        linkmanTelephone:$linkmanTelephone
								      },
								      dataType : "json",
								      success : function(data) {
								      		//console.log(data);
											if ($.trim(data) == '0') {
								      			$.tooltip('该用户在7月15日之前没有购买记录,无法参加此次活动!');
								      			//console.log(data);
								      			return false;
								      		} else if($.trim(data) == '1') {
								      			$.tooltip('该餐厅已被推荐,请重新填写!');
								      			return false;
								      		} else if($.trim(data) == '2') {
								      			$.ajax({
								      				type:'post',
								     				 url:'${base}/member/rebate/save',
								      				 data:{
								      				 	username:$username,
								       				 	mobile:$mobile,
								       				 	eataddress:$eataddress,
								       				 	linkman:$linkman,
								       				 	recommendDiningRoom:$recommendDiningRoom,
								       				 	consumerPlace:$consumerPlace,
								       				 	linkmanTelephone:$linkmanTelephone
								     			 	},
								     				 dataType : "json",
								      				 success : function(result) {
								      				 //console.log(result);								      		
													if ($.trim(result) == 'true') {
								      					$.tooltip('提交成功!',2000,true);
														setTimeout(function(){
															$el.hDialog('close',{box:'#HBox'},'http://www.520myy.com/member/index');
														},1000);
								      				} else if($.trim(result) == 'false') {
								      					$.tooltip('提交失败!');
								      					setTimeout(function(){
															$el.hDialog('close',{box:'#HBox'},'http://www.520myy.com/member/index');
														},1000);
								      				}
								 				}

								   		    });
												//$el.hDialog('close',{box:'#HBox'},'http://www.520myy.com/');
								      		}
								 		}

								     });
									//setTimeout(function(){
										//$el.hDialog('close',{box:'#HBox'},'http://www.520myy.com/');  //也可以加跳转链接哦~
										//$el.hDialog('close',{box:'#HBox'}); 
									//},2000);
								}
							});


						});
						$(function(){
							 $(window).scroll(function() {
							     var stop = $(document).scrollTop();
							     if (stop >= 200) {
							         $("#rbutton").css({
							             "right": "0px"
							         });
							     } else {
							         $("#rbutton").css({
							             "right": "-40px"
							         });
							     }
							 })
						});
					</script>					
				</article>
			</div>
		</main>
	</body>
</html>
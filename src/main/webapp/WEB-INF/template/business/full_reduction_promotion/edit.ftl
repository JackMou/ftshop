<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="author" content="FTSHOP Team">
	<meta name="copyright" content="FTSHOP">
	<title>${message("business.fullReductionPromotion.edit")}[#if showPowered] - Powered By FTSHOP[/#if]</title>
	<link href="${base}/favicon.ico" rel="icon">
	<link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
	<link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
	<link href="${base}/resources/common/css/font-awesome.css" rel="stylesheet">
	<link href="${base}/resources/common/css/awesome-bootstrap-checkbox.css" rel="stylesheet">
	<link href="${base}/resources/common/css/bootstrap-select.css" rel="stylesheet">
	<link href="${base}/resources/common/css/ajax-bootstrap-select.css" rel="stylesheet">
	<link href="${base}/resources/common/css/bootstrap-datetimepicker.css" rel="stylesheet">
	<link href="${base}/resources/common/css/bootstrap-fileinput.css" rel="stylesheet">
	<link href="${base}/resources/common/css/summernote.css" rel="stylesheet">
	<link href="${base}/resources/common/css/base.css" rel="stylesheet">
	<link href="${base}/resources/business/css/base.css" rel="stylesheet">
	<!--[if lt IE 9]>
		<script src="${base}/resources/common/js/html5shiv.js"></script>
		<script src="${base}/resources/common/js/respond.js"></script>
	<![endif]-->
	<script src="${base}/resources/common/js/jquery.js"></script>
	<script src="${base}/resources/common/js/bootstrap.js"></script>
	<script src="${base}/resources/common/js/bootstrap-growl.js"></script>
	<script src="${base}/resources/common/js/bootstrap-select.js"></script>
	<script src="${base}/resources/common/js/ajax-bootstrap-select.js"></script>
	<script src="${base}/resources/common/js/moment.js"></script>
	<script src="${base}/resources/common/js/bootstrap-datetimepicker.js"></script>
	<script src="${base}/resources/common/js/bootstrap-fileinput.js"></script>
	<script src="${base}/resources/common/js/summernote.js"></script>
	<script src="${base}/resources/common/js/jquery.nicescroll.js"></script>
	<script src="${base}/resources/common/js/jquery.validate.js"></script>
	<script src="${base}/resources/common/js/jquery.validate.additional.js"></script>
	<script src="${base}/resources/common/js/jquery.form.js"></script>
	<script src="${base}/resources/common/js/jquery.cookie.js"></script>
	<script src="${base}/resources/common/js/underscore.js"></script>
	<script src="${base}/resources/common/js/url.js"></script>
	<script src="${base}/resources/common/js/velocity.js"></script>
	<script src="${base}/resources/common/js/velocity.ui.js"></script>
	<script src="${base}/resources/common/js/base.js"></script>
	<script src="${base}/resources/business/js/base.js"></script>
	[#noautoesc]
		[#escape x as x?js_string]
			<script>
			$().ready(function() {
				
				var $fullReductionPromotionForm = $("#fullReductionPromotionForm");
				var $useAmountPromotion = $("#useAmountPromotion");
				var $useNumberPromotion = $("#useNumberPromotion");
				var $conditionsAmount = $("#conditionsAmount");
				var $creditAmount = $("#creditAmount");
				var $conditionsNumber = $("#conditionsNumber");
				var $creditNumber = $("#creditNumber");
				var $giftIds = $("#giftIds");
				
				// 使用金额促销
				$useAmountPromotion.change(function() {
					var $element = $(this);
					
					if ($element.prop("checked")) {
						$conditionsAmount.add($creditAmount).prop("disabled", false).closest(".form-group").velocity("slideDown");
						$conditionsNumber.add($creditNumber).prop("disabled", true).closest(".form-group").velocity("slideUp");
						$useNumberPromotion.prop("checked", false);
					} else {
						$conditionsAmount.add($creditAmount).prop("disabled", true).closest(".form-group").velocity("slideUp");
					}
				});
				
				// 使用数量促销
				$useNumberPromotion.change(function() {
					var $element = $(this);
					
					if ($element.prop("checked")) {
						$conditionsNumber.add($creditNumber).prop("disabled", false).closest(".form-group").velocity("slideDown");
						$conditionsAmount.add($creditAmount).prop("disabled", true).closest(".form-group").velocity("slideUp");
						$useAmountPromotion.prop("checked", false);
					} else {
						$conditionsNumber.add($creditNumber).prop("disabled", true).closest(".form-group").velocity("slideUp");
					}
				});
				
				// 赠品选择
				$giftIds.selectpicker({
					liveSearch: true
				}).ajaxSelectPicker({
					ajax: {
						url: "${base}/business/full_reduction_promotion/gift_select",
						type: "GET",
						data: function() {
							return {
								keyword: "{{{q}}}"
							};
						},
						dataType: "json"
					},
					preprocessData: function(data) {
						return $.map(data, function(item) {
							return {
								value: item.id,
								text: item.name,
								data: {
									subtext: item.specifications.length > 0 ? "[" + _.escape(item.specifications.join(",")) + "]" : null
								},
								disabled: false
							};
						});
					}
				}).selectpicker("selectAll");
				
				// 表单验证
				$fullReductionPromotionForm.validate({
					rules: {
						name: "required",
						title: "required",
						minimumPrice: {
							number: true,
							min: 0,
							decimal: {
								integer: 12,
								fraction: ${setting.priceScale}
							}
						},
						maximumPrice: {
							number: true,
							min: 0,
							decimal: {
								integer: 12,
								fraction: ${setting.priceScale}
							},
							greaterThanEqual: "#minimumPrice"
						},
						minimumQuantity: "digits",
						maximumQuantity: {
							digits: true,
							greaterThanEqual: "#minimumQuantity"
						},
						conditionsAmount: {
							required: true,
							number: true,
							min: 0,
							decimal: {
								integer: 12,
								fraction: ${setting.priceScale}
							}
						},
						creditAmount: {
							required: true,
							number: true,
							min: 0,
							decimal: {
								integer: 12,
								fraction: ${setting.priceScale}
							},
							lessThanEqual: "#conditionsAmount"
						},
						conditionsNumber: {
							required: true,
							digits: true
						},
						creditNumber: {
							required: true,
							digits: true,
							lessThanEqual: "#conditionsNumber"
						},
						order: "digits"
					}
				});
			
			});
			</script>
		[/#escape]
	[/#noautoesc]
</head>
<body class="business">
	[#include "/business/include/main_header.ftl" /]
	[#include "/business/include/main_sidebar.ftl" /]
	<main>
		<div class="container-fluid">
			<ol class="breadcrumb">
				<li>
					<a href="${base}/business/index">
						<i class="iconfont icon-homefill"></i>
						${message("common.breadcrumb.index")}
					</a>
				</li>
				<li class="active">${message("business.fullReductionPromotion.edit")}</li>
			</ol>
			<form id="fullReductionPromotionForm" class="ajax-form form-horizontal" action="${base}/business/full_reduction_promotion/update" method="post">
				<input name="promotionId" type="hidden" value="${promotion.id}">
				<div class="panel panel-default">
					<div class="panel-body">
						<ul class="nav nav-tabs">
							<li class="active">
								<a href="#base" data-toggle="tab">${message("business.fullReductionPromotion.base")}</a>
							</li>
							<li>
								<a href="#introduction" data-toggle="tab">${message("Promotion.introduction")}</a>
							</li>
						</ul>
						<div class="tab-content">
							<div id="base" class="tab-pane active">
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label item-required" for="name">${message("Promotion.name")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input id="name" name="name" class="form-control" type="text" value="${promotion.name}" maxlength="200">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label item-required" for="title">${message("Promotion.title")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input id="title" name="title" class="form-control" type="text" value="${promotion.title}" maxlength="200">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label">${message("Promotion.image")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input name="image" type="hidden" value="${promotion.image}" data-provide="fileinput" data-file-type="IMAGE">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label" for="beginDate">${message("common.dateRange")}:</label>
									<div class="col-xs-9 col-sm-4">
										<div class="input-group" data-provide="datetimerangepicker" data-date-format="YYYY-MM-DD HH:mm:ss">
											<input id="beginDate" name="beginDate" class="form-control" type="text" value="[#if promotion.beginDate??]${promotion.beginDate?string("yyyy-MM-dd HH:mm:ss")}[/#if]">
											<span class="input-group-addon">-</span>
											<input name="endDate" class="form-control" type="text" value="[#if promotion.endDate??]${promotion.endDate?string("yyyy-MM-dd HH:mm:ss")}[/#if]">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label" for="minimumPrice">${message("common.priceRange")}:</label>
									<div class="col-xs-9 col-sm-4">
										<div class="input-group">
											<input id="minimumPrice" name="minimumPrice" class="form-control" type="text" value="${promotion.minimumPrice}" maxlength="16">
											<span class="input-group-addon">-</span>
											<input name="maximumPrice" class="form-control" type="text" value="${promotion.maximumPrice}" maxlength="16">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label" for="minimumQuantity">${message("common.quantityRange")}:</label>
									<div class="col-xs-9 col-sm-4">
										<div class="input-group">
											<input id="minimumQuantity" name="minimumQuantity" class="form-control" type="text" value="${promotion.minimumQuantity}" maxlength="9">
											<span class="input-group-addon">-</span>
											<input name="maximumQuantity" class="form-control" type="text" value="${promotion.maximumQuantity}" maxlength="9">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label">${message("common.setting")}:</label>
									<div class="col-xs-9 col-sm-4">
										<div class="checkbox checkbox-inline">
											<input id="useAmountPromotion" name="useAmountPromotion" type="checkbox" value="true"[#if promotion.conditionsAmount?? && promotion.creditAmount??] checked[/#if]>
											<label for="useAmountPromotion">${message("business.fullReductionPromotion.useAmountPromotion")}</label>
										</div>
										<div class="checkbox checkbox-inline">
											<input id="useNumberPromotion" name="useNumberPromotion" type="checkbox" value="true"[#if promotion.conditionsNumber?? && promotion.creditNumber??] checked[/#if]>
											<label for="useNumberPromotion">${message("business.fullReductionPromotion.useNumberPromotion")}</label>
										</div>
									</div>
								</div>
								<div class="form-group[#if !promotion.conditionsAmount??] hidden-element[/#if]">
									<label class="col-xs-3 col-sm-2 control-label item-required" for="conditionsAmount">${message("Promotion.conditionsAmount")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input id="conditionsAmount" name="conditionsAmount" class="form-control" type="text" value="${promotion.conditionsAmount}" maxlength="16" [#if !promotion.conditionsAmount??] disabled[/#if]>
									</div>
								</div>
								<div class="form-group[#if !promotion.creditAmount??] hidden-element[/#if]">
									<label class="col-xs-3 col-sm-2 control-label item-required" for="creditAmount">${message("Promotion.creditAmount")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input id="creditAmount" name="creditAmount" class="form-control" type="text" value="${promotion.creditAmount}" maxlength="16" [#if !promotion.creditAmount??] disabled[/#if]>
									</div>
								</div>
								<div class="form-group[#if !promotion.conditionsNumber??] hidden-element[/#if]">
									<label class="col-xs-3 col-sm-2 control-label item-required" for="conditionsNumber">${message("Promotion.conditionsNumber")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input id="conditionsNumber" name="conditionsNumber" class="form-control" type="text" value="${promotion.conditionsNumber}" maxlength="9" [#if !promotion.conditionsNumber??] disabled[/#if]>
									</div>
								</div>
								<div class="form-group[#if !promotion.creditNumber??] hidden-element[/#if]">
									<label class="col-xs-3 col-sm-2 control-label item-required" for="creditNumber">${message("Promotion.creditNumber")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input id="creditNumber" name="creditNumber" class="form-control" type="text" value="${promotion.creditNumber}" maxlength="9" [#if !promotion.creditNumber??] disabled[/#if]>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label">${message("Promotion.memberRanks")}:</label>
									<div class="col-xs-9 col-sm-10">
										[#list memberRanks as memberRank]
											<div class="checkbox checkbox-inline">
												<input id="memberRank_${memberRank.id}" name="memberRankIds" type="checkbox" value="${memberRank.id}"[#if promotion.memberRanks?seq_contains(memberRank)] checked[/#if]>
												<label for="memberRank_${memberRank.id}">${memberRank.name}</label>
											</div>
										[/#list]
									</div>
								</div>
								[#if coupons?has_content]
									<div class="form-group">
										<label class="col-xs-3 col-sm-2 control-label">${message("Promotion.coupons")}:</label>
										<div class="col-xs-9 col-sm-10">
											[#list coupons as coupon]
												<div class="checkbox checkbox-inline">
													<input id="coupon_${coupon.id}" name="couponIds" type="checkbox" value="${coupon.id}"[#if promotion.coupons?seq_contains(coupon)] checked[/#if]>
													<label for="coupon_${coupon.id}">${coupon.name}</label>
												</div>
											[/#list]
										</div>
									</div>
								[/#if]
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label">${message("common.setting")}:</label>
									<div class="col-xs-9 col-sm-4">
										<div class="checkbox checkbox-inline">
											<input name="_isFreeShipping" type="hidden" value="false">
											<input id="isFreeShipping" name="isFreeShipping" type="checkbox" value="true"[#if promotion.isFreeShipping] checked[/#if]>
											<label for="isFreeShipping">${message("Promotion.isFreeShipping")}</label>
										</div>
										<div class="checkbox checkbox-inline">
											<input name="_isCouponAllowed" type="hidden" value="false">
											<input id="isCouponAllowed" name="isCouponAllowed" type="checkbox" value="true"[#if promotion.isCouponAllowed] checked[/#if]>
											<label for="isCouponAllowed">${message("Promotion.isCouponAllowed")}</label>
										</div>
										<div class="checkbox checkbox-inline">
											<input name="_isEnabled" type="hidden" value="false">
											<input id="isEnabled" name="isEnabled" type="checkbox" value="true"[#if promotion.isEnabled] checked[/#if]>
											<label for="isEnabled">${message("Promotion.isEnabled")}</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label">${message("Promotion.gifts")}:</label>
									<div class="col-xs-9 col-sm-4">
										<select id="giftIds" name="giftIds" class="form-control" title="${message("business.fullReductionPromotion.giftTitle")}" multiple>
											[#list promotion.gifts as gift]
												<option value="${gift.id}"[#if gift.specifications?has_content] data-subtext="[${gift.specifications?join(", ")}]"[/#if]>${gift.name}</option>
											[/#list]
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label" for="order">${message("common.order")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input id="order" name="order" class="form-control" type="text" value="${promotion.order}" maxlength="9">
									</div>
								</div>
							</div>
							<div id="introduction" class="tab-pane">
								<textarea name="introduction" data-provide="editor">${promotion.introduction}</textarea>
							</div>
						</div>
					</div>
					<div class="panel-footer">
						<div class="row">
							<div class="col-xs-9 col-sm-10 col-xs-offset-3 col-sm-offset-2">
								<button class="btn btn-primary" type="submit">${message("common.submit")}</button>
								<button class="btn btn-default" type="button" data-action="back">${message("common.back")}</button>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</main>
</body>
</html>
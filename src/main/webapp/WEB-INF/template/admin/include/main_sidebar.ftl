[#noautoesc]
	[#escape x as x?js_string]
		<script>
		$().ready(function() {
			
			var $window = $(window);
			var $body = $("body");
			var $mainSidebarCollapseToggle = $("[data-toggle='mainSidebarCollapse']");
			var $mainSidebar = $("#mainSidebar");
			var $searchForm = $("#mainSidebar .search form");
			var $searchValue = $("#mainSidebar .search input[name='searchValue']");
			var $panelCollapse = $("#mainSidebar .panel-collapse");
			
			// 主侧边栏折叠
			$mainSidebarCollapseToggle.click(function() {
				var niceScroll = $mainSidebar.getNiceScroll();
				var interval = setInterval(function() {
					niceScroll.resize();
				}, 10);
				
				if ($window.width() > 767) {
					$body.removeClass("main-sidebar-expanded").toggleClass("main-sidebar-mini");
				} else {
					$body.removeClass("main-sidebar-mini").toggleClass("main-sidebar-expanded");
				}
				
				$body.one("bsTransitionEnd", function() {
					niceScroll.resize();
					window.clearInterval(interval);
				}).emulateTransitionEnd(500);
			});
			
			// 主侧边栏滚动条
			$mainSidebar.niceScroll({
				cursorwidth: "4px",
				cursorcolor: "#ffffff",
				cursorborder: "0px",
				cursoropacitymax: 0.4
			});
			
			// 搜索
			$searchForm.submit(function() {
				if ($.trim($searchValue.val()) == "") {
					return false;
				}
			});
			
			// 面板折叠
			$panelCollapse.on("shown.bs.collapse hidden.bs.collapse", function() {
				$mainSidebar.getNiceScroll().resize();
			});
		
		});
		</script>
	[/#escape]
[/#noautoesc]
<aside id="mainSidebar" class="main-sidebar">
	<div class="search">
		<form action="${base}/admin/product/list" method="get">
			<input name="searchProperty" type="hidden" value="name">
			<div class="input-group">
				<input name="searchValue" class="form-control" type="text" placeholder="${message("admin.mainSidebar.search")}" x-webkit-speech="x-webkit-speech" x-webkit-grammar="builtin:search">
				<div class="input-group-btn">
					<button class="btn btn-default" type="submit">
						<i class="iconfont icon-search"></i>
					</button>
				</div>
			</div>
		</form>
	</div>
	<div id="mainSidebarPanelGroup" class="panel-group">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a href="#storeGroupPanelCollapse" data-toggle="collapse" data-parent="#mainSidebarPanelGroup"[#if .main_template_name?matches("admin/index.*|admin/business/.*|admin/store/.*|admin/store_category/.*|admin/store_rank/.*|admin/business_attribute/.*|admin/business_cash/.*|admin/category_application/.*|admin/business_deposit/.*")] aria-expanded="true"[/#if]>
						<i class="iconfont icon-shop"></i>
						<span>${message("admin.mainSidebar.storeGroup")}</span>
						<i class="iconfont icon-unfold"></i>
					</a>
				</h4>
			</div>
			<div id="storeGroupPanelCollapse" class="panel-collapse collapse[#if .main_template_name?matches("admin/index.*|admin/business/.*|admin/store/.*|admin/store_category/.*|admin/store_rank/.*|admin/business_attribute/.*|admin/business_cash/.*|admin/category_application/.*|admin/business_deposit/.*")] in[/#if]">
				<div class="panel-body">
					<ul class="list-group">
						<li class="list-group-item[#if .main_template_name?matches("admin/business/.*")] active[/#if]">
							<a href="${base}/admin/business/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.business")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/store/.*")] active[/#if]">
							<a href="${base}/admin/store/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.store")}
								[@store_count status = "PENDING"]
									<span class="label label-info" title="${message("admin.mainSidebar.pendingReviewStoreCount")}" data-toggle="tooltip">${count}</span>
								[/@store_count]
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/store_category/.*")] active[/#if]">
							<a href="${base}/admin/store_category/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.storeCategory")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/store_rank/.*")] active[/#if]">
							<a href="${base}/admin/store_rank/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.storeRank")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/business_attribute/.*")] active[/#if]">
							<a href="${base}/admin/business_attribute/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.businessAttribute")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/business_cash/.*")] active[/#if]">
							<a href="${base}/admin/business_cash/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.businessCash")}
								[@business_cash_count status = "PENDING"]
									<span class="label label-warning" title="${message("admin.mainSidebar.pendingReviewBusinessCashCount")}" data-toggle="tooltip">${count}</span>
								[/@business_cash_count]
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/category_application/.*")] active[/#if]">
							<a href="${base}/admin/category_application/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.categoryApplication")}
								[@category_application_count status = "PENDING"]
									<span class="label label-success" title="${message("admin.mainSidebar.pendingReviewCategoryApplicationCount")}" data-toggle="tooltip">${count}</span>
								[/@category_application_count]
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/business_deposit/.*")] active[/#if]">
							<a href="${base}/admin/business_deposit/log">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.businessDeposit")}
							</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a href="#productGroupPanelCollapse" data-toggle="collapse" data-parent="#mainSidebarPanelGroup"[#if .main_template_name?matches("admin/product/.*|admin/stock/.*|admin/product_category/.*|admin/product_tag/.*|admin/parameter/.*|admin/attribute/.*|admin/specification/.*|admin/brand/.*")] aria-expanded="true"[/#if]>
						<i class="iconfont icon-goods"></i>
						<span>${message("admin.mainSidebar.productGroup")}</span>
						<i class="iconfont icon-unfold"></i>
					</a>
				</h4>
			</div>
			<div id="productGroupPanelCollapse" class="panel-collapse collapse[#if .main_template_name?matches("admin/product/.*|admin/stock/.*|admin/product_category/.*|admin/product_tag/.*|admin/parameter/.*|admin/attribute/.*|admin/specification/.*|admin/brand/.*")] in[/#if]">
				<div class="panel-body">
					<ul class="list-group">
						<li class="list-group-item[#if .main_template_name?matches("admin/product/.*")] active[/#if]">
							<a href="${base}/admin/product/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.product")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/stock/.*")] active[/#if]">
							<a href="${base}/admin/stock/log">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.stock")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/product_category/.*")] active[/#if]">
							<a href="${base}/admin/product_category/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.productCategory")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/product_tag/.*")] active[/#if]">
							<a href="${base}/admin/product_tag/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.productTag")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/parameter/.*")] active[/#if]">
							<a href="${base}/admin/parameter/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.parameter")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/attribute/.*")] active[/#if]">
							<a href="${base}/admin/attribute/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.attribute")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/specification/.*")] active[/#if]">
							<a href="${base}/admin/specification/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.specification")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/brand/.*")] active[/#if]">
							<a href="${base}/admin/brand/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.brand")}
							</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a href="#orderGroupPanelCollapse" data-toggle="collapse" data-parent="#mainSidebarPanelGroup"[#if .main_template_name?matches("admin/order/.*|admin/order_payment/.*|admin/order_refunds/.*|admin/order_shipping/.*|admin/order_returns/.*|admin/delivery_center/.*|admin/delivery_template/.*|admin/aftersales/.*")] aria-expanded="true"[/#if]>
						<i class="iconfont icon-form"></i>
						<span>${message("admin.mainSidebar.orderGroup")}</span>
						<i class="iconfont icon-unfold"></i>
					</a>
				</h4>
			</div>
			<div id="orderGroupPanelCollapse" class="panel-collapse collapse[#if .main_template_name?matches("admin/order/.*|admin/order_payment/.*|admin/order_refunds/.*|admin/order_shipping/.*|admin/order_returns/.*|admin/delivery_center/.*|admin/delivery_template/.*|admin/aftersales/.*")] in[/#if]">
				<div class="panel-body">
					<ul class="list-group">
						<li class="list-group-item[#if .main_template_name?matches("admin/order/.*")] active[/#if]">
							<a href="${base}/admin/order/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.order")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/order_payment/.*")] active[/#if]">
							<a href="${base}/admin/order_payment/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.orderPayment")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/order_refunds/.*")] active[/#if]">
							<a href="${base}/admin/order_refunds/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.orderRefunds")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/order_shipping/.*")] active[/#if]">
							<a href="${base}/admin/order_shipping/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.orderShipping")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/order_returns/.*")] active[/#if]">
							<a href="${base}/admin/order_returns/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.orderReturns")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/delivery_center/.*")] active[/#if]">
							<a href="${base}/admin/delivery_center/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.deliveryCenter")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/delivery_template/.*")] active[/#if]">
							<a href="${base}/admin/delivery_template/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.deliveryTemplate")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/aftersales/.*")] active[/#if]">
							<a href="${base}/admin/aftersales/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.aftersalesList")}
							</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a href="#memberGroupPanelCollapse" data-toggle="collapse" data-parent="#mainSidebarPanelGroup"[#if .main_template_name?matches("admin/member/.*|admin/member_rank/.*|admin/member_attribute/.*|admin/point/.*|admin/member_deposit/.*|admin/review/.*|admin/consultation/.*|admin/message_config/.*|admin/rebate/.*")] aria-expanded="true"[/#if]>
						<i class="iconfont icon-people"></i>
						<span>${message("admin.mainSidebar.memberGroup")}</span>
						<i class="iconfont icon-unfold"></i>
					</a>
				</h4>
			</div>
			<div id="memberGroupPanelCollapse" class="panel-collapse collapse[#if .main_template_name?matches("admin/member/.*|admin/member_rank/.*|admin/member_attribute/.*|admin/point/.*|admin/member_deposit/.*|admin/review/.*|admin/consultation/.*|admin/message_config/.*|admin/rebate/.*")] in[/#if]">
				<div class="panel-body">
					<ul class="list-group">
						<li class="list-group-item[#if .main_template_name?matches("admin/member/.*")] active[/#if]">
							<a href="${base}/admin/member/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.member")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/member_rank/.*")] active[/#if]">
							<a href="${base}/admin/member_rank/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.memberRank")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/member_attribute/.*")] active[/#if]">
							<a href="${base}/admin/member_attribute/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.memberAttribute")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/point/.*")] active[/#if]">
							<a href="${base}/admin/point/log">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.point")}
							</a>
						</li>
						
						<!-- add start version 1.0 -->
						<li class="list-group-item[#if .main_template_name?matches("admin/rebate/.*")] active[/#if]">
							<a href="${base}/admin/rebate/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.rebate")}
							</a>
						</li>
						<!-- add end version 1.0 -->
						
						<li class="list-group-item[#if .main_template_name?matches("admin/member_deposit/.*")] active[/#if]">
							<a href="${base}/admin/member_deposit/log">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.memberDeposit")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/review/.*")] active[/#if]">
							<a href="${base}/admin/review/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.review")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/consultation/.*")] active[/#if]">
							<a href="${base}/admin/consultation/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.consultation")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/message_config/.*")] active[/#if]">
							<a href="${base}/admin/message_config/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.messageConfig")}
							</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a href="#distributorGroupPanelCollapse" data-toggle="collapse" data-parent="#mainSidebarPanelGroup"[#if .main_template_name?matches("admin/distributor/.*|admin/distribution_cash/.*|admin/distribution_commission/.*")] aria-expanded="true"[/#if]>
						<i class="iconfont icon-friend"></i>
						<span>${message("admin.mainSidebar.distributorGroup")}</span>
						<i class="iconfont icon-unfold"></i>
					</a>
				</h4>
			</div>
			<div id="distributorGroupPanelCollapse" class="panel-collapse collapse[#if .main_template_name?matches("admin/distributor/.*|admin/distribution_cash/.*|admin/distribution_commission/.*")] in[/#if]">
				<div class="panel-body">
					<ul class="list-group">
						<li class="list-group-item[#if .main_template_name?matches("admin/distributor/.*")] active[/#if]">
							<a href="${base}/admin/distributor/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.distributor")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/distribution_cash/.*")] active[/#if]">
							<a href="${base}/admin/distribution_cash/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.distributionCash")}
								[@distribution_cash_count status = "PENDING"]
									<span class="label label-warning" title="${message("admin.mainSidebar.pendingReviewDistributionCashCount")}" data-toggle="tooltip">${count}</span>
								[/@distribution_cash_count]
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/distribution_commission/.*")] active[/#if]">
							<a href="${base}/admin/distribution_commission/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.distributionCommission")}
							</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a href="#contentGroupPanelCollapse" data-toggle="collapse" data-parent="#mainSidebarPanelGroup"[#if .main_template_name?matches("admin/navigation_group/.*|admin/navigation/.*|admin/article/.*|admin/article_category/.*|admin/article_tag/.*|admin/friend_link/.*|admin/ad_position/.*|admin/ad/.*|admin/template/.*|admin/cache/.*")] aria-expanded="true"[/#if]>
						<i class="iconfont icon-text"></i>
						<span>${message("admin.mainSidebar.contentGroup")}</span>
						<i class="iconfont icon-unfold"></i>
					</a>
				</h4>
			</div>
			<div id="contentGroupPanelCollapse" class="panel-collapse collapse[#if .main_template_name?matches("admin/navigation_group/.*|admin/navigation/.*|admin/article/.*|admin/article_category/.*|admin/article_tag/.*|admin/friend_link/.*|admin/ad_position/.*|admin/ad/.*|admin/template/.*|admin/cache/.*")] in[/#if]">
				<div class="panel-body">
					<ul class="list-group">
						<li class="list-group-item[#if .main_template_name?matches("admin/navigation_group/.*")] active[/#if]">
							<a href="${base}/admin/navigation_group/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.navigationGroup")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/navigation/.*")] active[/#if]">
							<a href="${base}/admin/navigation/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.navigation")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/article/.*")] active[/#if]">
							<a href="${base}/admin/article/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.article")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/article_category/.*")] active[/#if]">
							<a href="${base}/admin/article_category/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.articleCategory")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/article_tag/.*")] active[/#if]">
							<a href="${base}/admin/article_tag/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.articleTag")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/friend_link/.*")] active[/#if]">
							<a href="${base}/admin/friend_link/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.friendLink")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/ad_position/.*")] active[/#if]">
							<a href="${base}/admin/ad_position/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.adPosition")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/ad/.*")] active[/#if]">
							<a href="${base}/admin/ad/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.ad")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/template/.*")] active[/#if]">
							<a href="${base}/admin/template/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.template")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/cache/.*")] active[/#if]">
							<a href="${base}/admin/cache/clear">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.cache")}
							</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a href="#marketingGroupPanelCollapse" data-toggle="collapse" data-parent="#mainSidebarPanelGroup"[#if .main_template_name?matches("admin/promotion/.*|admin/coupon/.*|admin/seo/.*")] aria-expanded="true"[/#if]>
						<i class="iconfont icon-like"></i>
						<span>${message("admin.mainSidebar.marketingGroup")}</span>
						<i class="iconfont icon-unfold"></i>
					</a>
				</h4>
			</div>
			<div id="marketingGroupPanelCollapse" class="panel-collapse collapse[#if .main_template_name?matches("admin/promotion/.*|admin/coupon/.*|admin/seo/.*")] in[/#if]">
				<div class="panel-body">
					<ul class="list-group">
						<li class="list-group-item[#if .main_template_name?matches("admin/promotion/.*")] active[/#if]">
							<a href="${base}/admin/promotion/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.promotion")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/coupon/.*")] active[/#if]">
							<a href="${base}/admin/coupon/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.coupon")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/seo/.*")] active[/#if]">
							<a href="${base}/admin/seo/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.seo")}
							</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a href="#systemGroupPanelCollapse" data-toggle="collapse" data-parent="#mainSidebarPanelGroup"[#if .main_template_name?matches("admin/setting/.*|admin/area/.*|admin/payment_method/.*|admin/shipping_method/.*|admin/delivery_corp/.*|.*/plugin/.*Payment/.*|admin/payment_plugin/.*|admin/plugin/.*payment/.*|.*/plugin/.*Storage/.*|admin/storage_plugin/.*|admin/plugin/.*storage/.*|.*/plugin/.*Login/.*|admin/login_plugin/.*|admin/plugin/.*login/.*|.*/plugin/.*Promotion/.*|admin/promotion_plugin/.*|admin/plugin/.*promotion/.*|admin/admin/.*|admin/role/.*|admin/audit_log/.*")] aria-expanded="true"[/#if]>
						<i class="iconfont icon-settings"></i>
						<span>${message("admin.mainSidebar.systemGroup")}</span>
						<i class="iconfont icon-unfold"></i>
					</a>
				</h4>
			</div>
			<div id="systemGroupPanelCollapse" class="panel-collapse collapse[#if .main_template_name?matches("admin/setting/.*|admin/area/.*|admin/payment_method/.*|admin/shipping_method/.*|admin/delivery_corp/.*|.*/plugin/.*Payment/.*|admin/payment_plugin/.*|admin/plugin/.*payment/.*|.*/plugin/.*Storage/.*|admin/storage_plugin/.*|admin/plugin/.*storage/.*|.*/plugin/.*Login/.*|admin/login_plugin/.*|admin/plugin/.*login/.*|.*/plugin/.*Promotion/.*|admin/promotion_plugin/.*|admin/plugin/.*promotion/.*|admin/admin/.*|admin/role/.*|admin/audit_log/.*")] in[/#if]">
				<div class="panel-body">
					<ul class="list-group">
						<li class="list-group-item[#if .main_template_name?matches("admin/setting/.*")] active[/#if]">
							<a href="${base}/admin/setting/edit">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.setting")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/area/.*")] active[/#if]">
							<a href="${base}/admin/area/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.area")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/payment_method/.*")] active[/#if]">
							<a href="${base}/admin/payment_method/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.paymentMethod")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/shipping_method/.*")] active[/#if]">
							<a href="${base}/admin/shipping_method/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.shippingMethod")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/delivery_corp/.*")] active[/#if]">
							<a href="${base}/admin/delivery_corp/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.deliveryCorp")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches(".*/plugin/.*Payment/.*|admin/payment_plugin/.*|admin/plugin/.*payment/.*")] active[/#if]">
							<a href="${base}/admin/payment_plugin/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.paymentPlugin")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches(".*/plugin/.*Storage/.*|admin/storage_plugin/.*|admin/plugin/.*storage/.*")] active[/#if]">
							<a href="${base}/admin/storage_plugin/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.storagePlugin")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches(".*/plugin/.*Login/.*|admin/login_plugin/.*|admin/plugin/.*login/.*")] active[/#if]">
							<a href="${base}/admin/login_plugin/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.loginPlugin")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches(".*/plugin/.*Promotion/.*|admin/promotion_plugin/.*|admin/plugin/.*promotion/.*")] active[/#if]">
							<a href="${base}/admin/promotion_plugin/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.promotion_plugin")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/admin/.*")] active[/#if]">
							<a href="${base}/admin/admin/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.admin")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/role/.*")] active[/#if]">
							<a href="${base}/admin/role/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.role")}
							</a>
						</li>
						<li class="list-group-item[#if .main_template_name?matches("admin/audit_log/.*")] active[/#if]">
							<a href="${base}/admin/audit_log/list">
								<i class="iconfont icon-weixuanzhong"></i>
								${message("admin.mainSidebar.auditLog")}
							</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<ul class="list-group">
		<li class="list-group-item">
			<p>${message("admin.mainSidebar.statistics")}</p>
		</li>
		<li class="list-group-item[#if .main_template_name?matches("admin/order_statistic/.*")] active[/#if]">
			<a href="${base}/admin/order_statistic/list">
				<i class="iconfont icon-baobiao-xianxing text-blue-light"></i>
				<span>${message("admin.mainSidebar.orderStatisticList")}</span>
			</a>
		</li>
		<li class="list-group-item[#if .main_template_name?matches("admin/fund_statistic/.*")] active[/#if]">
			<a href="${base}/admin/fund_statistic/list">
				<i class="iconfont icon-zhexiantu-xianxing text-green"></i>
				<span>${message("admin.mainSidebar.fundStatisticList")}</span>
			</a>
		</li>
		<li class="list-group-item[#if .main_template_name?matches("admin/register_statistic/.*")] active[/#if]">
			<a href="${base}/admin/register_statistic/list">
				<i class="iconfont icon-friendadd text-orange-light"></i>
				<span>${message("admin.mainSidebar.registerStatisticList")}</span>
			</a>
		</li>
		<li class="list-group-item[#if .main_template_name?matches("admin/product_ranking/.*")] active[/#if]">
			<a href="${base}/admin/product_ranking/list">
				<i class="iconfont icon-tiaoxingtu-xianxing text-yellow-light"></i>
				<span>${message("admin.mainSidebar.productRankingList")}</span>
			</a>
		</li>
	</ul>
</aside>
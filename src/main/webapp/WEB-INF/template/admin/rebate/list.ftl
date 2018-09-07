<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="author" content="FTSHOP Team">
	<meta name="copyright" content="FTSHOP">
	<title>会员协议 [#if showPowered] - Powered By FTSHOP[/#if]</title>
	<link href="${base}/favicon.ico" rel="icon">
	<link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
	<link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
	<link href="${base}/resources/common/css/font-awesome.css" rel="stylesheet">
	<link href="${base}/resources/common/css/base.css" rel="stylesheet">
	<link href="${base}/resources/admin/css/base.css" rel="stylesheet">	
	<link href="${base}/resources/common/css/bootstrap-datetimepicker.css" rel="stylesheet">
	<!--[if lt IE 9]>
		<script src="${base}/resources/common/js/html5shiv.js"></script>
		<script src="${base}/resources/common/js/respond.js"></script>
	<![endif]-->
	<script src="${base}/resources/common/js/jquery.js"></script>
	<script src="${base}/resources/common/js/bootstrap.js"></script>
	<script src="${base}/resources/common/js/bootstrap-growl.js"></script>
	<script src="${base}/resources/common/js/jquery.nicescroll.js"></script>
	<script src="${base}/resources/common/js/jquery.validate.js"></script>
	<script src="${base}/resources/common/js/jquery.validate.additional.js"></script>
	<script src="${base}/resources/common/js/moment.js"></script>
	<script src="${base}/resources/common/js/bootstrap-datetimepicker.js"></script>
	<script src="${base}/resources/common/js/jquery.form.js"></script>
	<script src="${base}/resources/common/js/jquery.cookie.js"></script>
	<script src="${base}/resources/common/js/underscore.js"></script>
	<script src="${base}/resources/common/js/url.js"></script>
	<script src="${base}/resources/common/js/base.js"></script>
	<script src="${base}/resources/admin/js/base.js"></script>
	[#noautoesc]
		[#escape x as x?js_string]
			<script>
			$().ready(function() {
				var $export = $("#export");
				
				$export.click(function () {
                        $export.attr('disabled', "true");
                        var form = $('<form method="get" action="${base}/admin/rebate/download"></form>');
                        form.appendTo('body');
                        if (typeof($("#beginDate").val()) != "undefined") {
                            input = $(' <input name="beginDate" type="hidden" value="' + $("#beginDate").val() + '">');
                            form.append(input);
                        }
                        if (typeof($("#endDate").val()) != "undefined") {
                            input = $(' <input name="endDate" type="hidden" value="' + $("#endDate").val() + '">');
                            form.append(input);
                        }

                        form.submit().remove();

                        var i = 0;
                        var t = setInterval(function () {
                            i++;
                            $export.text("导出中...,剩余"+(119 - i)+"秒");
                            if (i == 119 ) {
                                $export.text("");
                                $export.append('<i class="iconfont icon-upload"></i> ${message("admin.orderStatistic.export")}');
                                $export.removeAttr("disabled");
                                clearInterval(t);
                            }
                        }, 1000);
                    })
			});
			</script>
		[/#escape]
	[/#noautoesc]
</head>
<body class="admin">
	[#include "/admin/include/main_header.ftl" /]
	[#include "/admin/include/main_sidebar.ftl" /]
	<main>
		<div class="container-fluid">
			<ol class="breadcrumb">
				<li>
					<a href="${base}/admin/index">
						<i class="iconfont icon-homefill"></i>
						${message("common.breadcrumb.index")}
					</a>
				</li>
				<li class="active">${message("会员协议")}</li>
			</ol>
			<form action="${base}/admin/rebate/list" method="get">
            <input name="pageSize" type="hidden" value="${page.pageSize}">
            <input name="searchProperty" type="hidden" value="${page.searchProperty}">
            <input name="orderProperty" type="hidden" value="${page.orderProperty}">
            <input name="orderDirection" type="hidden" value="${page.orderDirection}">
            <div id="filterModal" class="modal fade" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button class="close" type="button" data-dismiss="modal">&times;</button>
                            <h5 class="modal-title">${message("common.moreOption")}</h5>
                        </div>
                        <div class="modal-body form-horizontal">
                            <div class="form-group">
                                <label class="col-xs-3 control-label" for="beginDate">${message("common.dateRange")}
                                    :</label>
                                <div class="col-xs-9 col-sm-7">
                                    <div class="input-group" data-provide="datetimerangepicker"
                                         data-date-format="YYYY-MM-DD HH:mm:ss">
                                        <input id="beginDate" name="beginDate" class="form-control" type="text" [#if beginDate??] value="${beginDate?string("yyyy-MM-dd HH:mm:ss")}"[/#if]>
                                        <span class="input-group-addon">-</span>
                                        <input id="endDate" name="endDate" class="form-control" type="text" [#if endDate??] value="${endDate?string("yyyy-MM-dd HH:mm:ss")}"[/#if]>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-primary" type="submit">${message("common.ok")}</button>
                            <button class="btn btn-default" type="button" data-dismiss="modal">${message("common.cancel")}</button>
                        </div>
                    </div>
                </div>
            </div>
           </form>
				<div class="panel panel-default">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-12 col-sm-9">
								<div class="btn-group">									
									<button class="btn btn-default" type="button" data-action="refresh">
										<i class="iconfont icon-refresh"></i>
										${message("common.refresh")}
									</button>
									<!-- 更多选项日期选择 -->
									<button class="btn btn-default" type="button" data-toggle="modal" data-target="#filterModal">${message("common.moreOption")}</button>
									<div class="btn-group">
										<button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">
											${message("common.pageSize")}
											<span class="caret"></span>
										</button>
										<ul class="dropdown-menu">
											<li[#if page.pageSize == 10] class="active"[/#if] data-page-size="10">
												<a href="javascript:;">10</a>
											</li>
											<li[#if page.pageSize == 20] class="active"[/#if] data-page-size="20">
												<a href="javascript:;">20</a>
											</li>
											<li[#if page.pageSize == 50] class="active"[/#if] data-page-size="50">
												<a href="javascript:;">50</a>
											</li>
											<li[#if page.pageSize == 100] class="active"[/#if] data-page-size="100">
												<a href="javascript:;">100</a>
											</li>
										</ul>
									</div>
									<div class="btn-group">
										<button id="export" class="btn btn-default" type="button">
                                    		<i class="iconfont icon-upload"></i>
                                			${message("admin.orderStatistic.export")}
                                		</button>
									</div>
								</div>
							</div>
							<!--
							<div class="col-xs-12 col-sm-3">
								<div id="search" class="input-group">
									<div class="input-group-btn">
										<button class="btn btn-default" type="button">
											手机
										</button>										
									</div>
									<input name="searchValue" class="form-control" type="text" value="${page.searchValue}" placeholder="${message("common.search")}" x-webkit-speech="x-webkit-speech" x-webkit-grammar="builtin:search">
									<div class="input-group-btn">
										<button class="btn btn-default" type="submit">
											<i class="iconfont icon-search"></i>
										</button>
									</div>
								</div>
							</div>
							    -->
						</div>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-hover">
								<thead>
									<tr>
										<th>
											<a href="javascript:;" data-order-property="name">
												姓名
												<i class="iconfont icon-biaotou-kepaixu"></i>
											</a>
										</th>
										<th>
											<a href="javascript:;" data-order-property="mobile">
												电话
												<i class="iconfont icon-biaotou-kepaixu"></i>
											</a>
										</th>
										<th>
											<a href="javascript:;" data-order-property="eataddress">
												消费地点
												<i class="iconfont icon-biaotou-kepaixu"></i>
											</a>
										</th>
										<th>
											<a href="javascript:;" data-order-property="recommendDiningRoom">
												推荐餐厅名
												<i class="iconfont icon-biaotou-kepaixu"></i>
											</a>
										</th>
										<th>
											<a href="javascript:;" data-order-property="linkman">
												餐厅联系人
												<i class="iconfont icon-biaotou-kepaixu"></i>
											</a>
										</th>
										<th>
											<a href="javascript:;" data-order-property="linkmanTelephone">
												联系人电话
												<i class="iconfont icon-biaotou-kepaixu"></i>
											</a>
										</th>
										<th>
											<a href="javascript:;" data-order-property="status">
												餐厅状态
												<i class="iconfont icon-biaotou-kepaixu"></i>
											</a>
										</th>
										<th>
											<a href="javascript:;" data-order-property="createdDate">
												创建日期
												<i class="iconfont icon-biaotou-kepaixu"></i>
											</a>
										</th>
									</tr>
								</thead>
								[#if page.content?has_content]
									<tbody>
										[#list page.content as rebate]
											<tr>
												<td>${rebate.name}</td>
												<td>${rebate.mobile}</td>
												<td>${rebate.eataddress}</td>
												<td>${rebate.recommendDiningRoom}</td>
												<td>${rebate.linkman}</td>
												<td>${rebate.linkmanTelephone}</td>
												<td><span class="[#if rebate.status == "PENDING_REVIEW"]text-gray-dark[#elseif rebate.status == "CONFIRMED"]text-orange[#elseif rebate.status == "LAUNCHED"]text-green[#else][/#if]">${message("Rebate.Status." + rebate.status)}</span><td>
													<span title="${rebate.createdDate?string("yyyy-MM-dd HH:mm:ss")}" data-toggle="tooltip">${rebate.createdDate}</span>
												</td>
											</tr>
										[/#list]
									</tbody>
								[/#if]
							</table>
							[#if !page.content?has_content]
								<p class="no-result">${message("common.noResult")}</p>
							[/#if]
						</div>
					</div>
					[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
						[#if totalPages > 1]
							<div class="panel-footer text-right">
								[#include "/admin/include/pagination.ftl" /]
							</div>
						[/#if]
					[/@pagination]
				</div>
			</form>
		</div>
	</main>
</html>
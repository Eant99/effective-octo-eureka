<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>凭证信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	html{
		overflow-x: hidden;
	}
	.input_info{
		width:100px !important;
		height:14px !important;
		font-size:2px !important;
	}
	.select_info{
		width:50px !important;
		height:18px !important;
	}
	.top_table{
		width:50% !important;
		margin:auto;
		border-right:none;
		border-collapse:collapse!important;
	}
	.top_table>tbody>tr>td {
   	 	border-right: 1px solid #b3b0b0;
   	 	border-bottom: 1px solid #b3b0b0;
   	 	height:30px;
   	 	width:300px !important;
	}
	.index{
		border-left:1px solid #b3b0b0;
	}
	.input_title{
		border:180px !important;
		width:180px;
		height:40px!important;
	}
	.pull-left button{
		background-color: #00acec !important;
		color: white !important;
	}
	.sub-title .zhanwei{
		color:#fff;
	}
	#bottom_info{
/* 		width:60% !important; */
/* 		margin-left:18%!important; */
	}
	span .s{
	   font:bold 15px "Microsoft YaHei";
	}
	.money{
		width:100%;
		background:#f4f4f4;
		margin:0 auto;
	}
	.top_div{
		float:left;
		width:6.66%;
		border:1px solid rgba(191, 187, 187, 1);
	}
	.yc_style{
		display:none!important;
	}
	div .left_div{
		width:100%;
		height:100%;
	}
	div #green{
		border-right:1px solid green!important;
	}
	div #red{
		border-right:1px solid red!important;
	}
	div .left_bootom_div{
		float:left;
		width:6.66%;
		height:100%;
		border:1px solid rgba(191, 187, 187, 1);
	}
	.delete{
		background:#f5d8d8;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class='page-header'>
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>凭证信息</span>
		</div>
    </div>
	<div class="box">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"></div>
        	</div>		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary" style="width:100%;text-align: center;">
            	<span class="zhanwei">占</span>
            	<span class="newf">记账凭证</span>
            	
            	</div>
        	</div>
<!-- 			<hr class="hr-normal"> -->
			<!-- table start -->
			<div class="container-fluid">
				<div class='responsive-table'>
					<div class='scrollable-area'> 
						<table class="top_table">
						    <tbody style="border:none;">
						    <tr>
							    <td colspan="5" style="border:none;">
							    <table style="width:100%;">
							    <tr>
							    <td style="width: 45%;"></td>
							    <td>
							    日期<input  name="" class="input_info date" value="${sysDate}" data-format="yyyy-MM-dd" readonly/>
							    </td>
							    <td style="text-align: right;">
							         凭证字<select class="select_info">
            				<option value="0" selected>记</option>
            			</select>
            		<input  name="" style="width:50px;height:18px;" value="0001"/>号
							    </td>
							    </tr>
							    </table>
							    
							    </td>
							        </tr>
						   		 	<tr>
						   		 		<td style="text-align:center;border-left:1px solid #b3b0b0;border-top:1px solid #b3b0b0;" valign="middle">
						   		 		</td>
							            <td style="text-align:center;border-left:1px solid #b3b0b0;border-top:1px solid #b3b0b0;" valign="middle">摘要</td>
							            <td style="text-align:center;border-left: 1px solid #b3b0b0;border-top:1px solid #b3b0b0;" valign="middle"><span class="s">科目</span></td>
							            <td style="text-align:center;border-left: 1px solid #b3b0b0;border-top:1px solid #b3b0b0;" valign="bottom">
							            <span class="s">借方金额</span>
							            	<div class="money">
							            		<div class="top_div">万</div>
							            		<div class="top_div">千</div>
							            		<div class="top_div">百</div>
							            		<div class="top_div">十</div>
							            		<div class="top_div">亿</div>
							            		<div class="top_div">千</div>
							            		<div class="top_div">百</div>
							            		<div class="top_div">十</div>
							            		<div class="top_div">万</div>
							            		<div class="top_div">千</div>
							            		<div class="top_div">百</div>
							            		<div class="top_div">十</div>
							            		<div class="top_div">元</div>
							            		<div class="top_div">角</div>
							            		<div class="top_div">分</div>
							            	</div>
							            </td>
							            <td style="height:40px;text-align:center;border-left: 1px solid #b3b0b0;border-top:1px solid #b3b0b0;" valign="bottom">
							            <span class="s">贷方金额</span>
							            <div class="money">
							            		<div class="top_div">万</div>
							            		<div class="top_div">千</div>
							            		<div class="top_div">百</div>
							            		<div class="top_div">十</div>
							            		<div class="top_div">亿</div>
							            		<div class="top_div">千</div>
							            		<div class="top_div">百</div>
							            		<div class="top_div">十</div>
							            		<div class="top_div">万</div>
							            		<div class="top_div">千</div>
							            		<div class="top_div">百</div>
							            		<div class="top_div">十</div>
							            		<div class="top_div">元</div>
							            		<div class="top_div">角</div>
							            		<div class="top_div">分</div>
							            	</div>
							            </td>
							            <td rowspan="3" id="dual" valign="middle" style="text-align:left;border:none;height:40px;">
							     			&nbsp;附<br><br>
							     			&nbsp;单<br><br>
							     			&nbsp;据<br><br>
							     			&nbsp;<input name="" type="text" style="width:25px;height:20px;" 
							     					onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
							     			<br><br>
							     			&nbsp;张
							     		</td>
						      	  	</tr>	
						    		<tr id="tr_1" >
						    			<td style="text-align:center;border-left:1px solid #b3b0b0;border-top:1px solid #b3b0b0;" valign="middle">
<!-- 						   		 			<a href="javascript:void(0);" class="btn btn-link btn_delete">删除</a> -->
												<div class="dual"></div>
						   		 		</td>
							            <td class="index"><input type="" class="input_title writes"/></td>
							            <td class="">
							           	 <input type="" class="input_title writes" id="km_1"readonly/>
							            </td>
							            <td class="div_td">
							            	<div class="left_div">
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="red"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            	</div>
							            	<input type="" class="input_title yc_style sq left"	onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"
							            		style="text-align:right;"/>
							            </td>
							            <td class="div_td">
							            	<div class="left_div">
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="red"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            	</div>
							            	<input type="" class="input_title yc_style sq right" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"
							            		style="text-align:right;"/>
							            </td>
							        </tr>
							        <c:forEach varStatus="i" begin="2" end="2">
								        <tr id="tr_${i.index}" >
								        	<td style="text-align:center;border-left:1px solid #b3b0b0;border-top:1px solid #b3b0b0;" valign="middle">
						   		 				<div class="dual"></div>
						   		 			</td>
								            <td class="index"><input type="" class="input_title writes" /></td>
								            <td class=""><input type="" class="input_title writes" id="km_2" readonly/></td>
								            <td class="div_td">
								            <div class="left_div">
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="red"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            	</div>
								            	<input type="" class="input_title yc_style sq left"	onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"
								            		style="text-align:right;"/>
								            </td>
								            <td class="div_td" valign="middle">
								            <div class="left_div">
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="green"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div" id="red"></div>
							            		<div class="left_bootom_div"></div>
							            		<div class="left_bootom_div"></div>
							            	</div>
								            	<input type="" class="input_title yc_style sq right" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"
								            		style="text-align:right;"/>
								            </td>
								        </tr>
							        </c:forEach>
							    	<tr class="tr_end">
							            <td colspan="3" class="index" style="text-align:left;">合计:</td>
							            <td class="td_end ld" style="text-align:right;">
							            	<input type="" class="input_title"	readonly style="text-align:right;"/>
							            </td>
							            <td class="td_end rd" style="text-align:right;">
							            	<input type="" class="input_title"	readonly style="text-align:right;"/>
							            </td>
							        </tr>
							        <tr>
							            <td colspan="3" style="border:none;"></td>
							            <td style="border:none;text-align:left;">
							            	<input class="input_title"	readonly value="制单人：${loginName}"/>
							            </td>
							        </tr>
						    </tbody>
						</table>
				    </div>
				</div>
			</div>
			<!-- table end -->
		</div>
		<div class="box-panel yc_style" id="bottom_info">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">支付方式</span>
							<select id="drp_zffs" class="form-control input-radius" name="zffs"> 
                        		<option value="0" >支付宝</option>
                        		<option value="1" >微信</option>
                        		<option value="2" >银行卡</option>
							</select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">部&emsp;&emsp;门</span>
	                         <input type="text" id="txt_bm" class="form-control input-radius window" name="bm" value="">
	                         <span class="input-group-btn"><button type="button" id="btn_bm" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">合&emsp;&emsp;同</span>
	                         <input type="text" id="txt_ht" class="form-control input-radius" name="ht" value="">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">往来号</span>
	                         <input type="text" id="txt_wldc" class="form-control input-radius " name="wldc" value="">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">现金银行</span>
	                        <input type="text" id="txt_xjyh" class="form-control input-radius window" name="xjyh" value="">
	                        <span class="input-group-btn"><button type="button" id="btn_xjyh" class="btn btn-link btn-custom btn-links">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">国库支付</span>
							 <select id="drp_zffs" class="form-control input-radius" name="zffs"> 
                        		<option value="0" >基本支出</option>
                        		<option value="1" >项目支出</option>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">资产对账</span>
	                         <select id="drp_zcdz" class="form-control input-radius" name="zcdz"> 
	                        	<option value="0" >房屋</option>
	                        	<option value="1" >仪表仪器</option>
	                        	<option value="2" >机电</option>
	                        	<option value="3" >电子产品</option>
	                        	<option value="4" >印刷机械</option>
	                        	<option value="5" >医疗机械</option>
	                        	<option value="6" >问题设备</option>
	                        	<option value="7" >标本模型</option>
	                        	<option value="8" >图书、软件</option>
	                        	<option value="9" >工具量具和器皿</option>
	                        	<option value="10" >家具</option>
	                        	<option value="11" >办公设备</option>
							 </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">项&emsp;&emsp;目</span>
	                        <input type="text" id="txt_xm" class="form-control input-radius window" name="xm" value="">
	                        <span class="input-group-btn"><button type="button" id="btn_xm" class="btn btn-link btn-custom btn-links">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">职&emsp;&emsp;员</span>
	                        <input type="text" id="txt_zy" class="form-control input-radius window" name="zy" value="">
	                        <span class="input-group-btn"><button type="button" id="btn_zy" class="btn btn-link btn-custom btn-links">选择</button></span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">数量</span>
	                         <input type="text" id="txt_sl" class="form-control input-radius" name="sl" value="" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"
	                        	 style="text-align:right;">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">往来单位</span>
	                        <input type="text" id="txt_wldw" class="form-control input-radius window" name="wldw" value="">
	                        <span class="input-group-btn"><button type="button" id="btn_wldw" class="btn btn-link btn-custom btn-links">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon">(网银)支付账号</span>
	                        <input type="text" id="" class="form-control input-radius" name="" value="">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>

<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function() {	
	//列表右侧悬浮按钮
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
});
</script>
</body>
</html>
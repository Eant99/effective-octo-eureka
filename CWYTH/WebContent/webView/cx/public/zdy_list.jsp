<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产附单列表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.title {
		font-size:14px;
		font-weight:300;
		padding: 10px 15px 20px;
	}
	.btn-default {
/* 		background-color:#00acec; */
/* 		color:white; */
		min-width: 76px;
	}
	.mid_top{
		text-align: center;
		margin-top: -20px;
		width:90px;
	}
	.mid_top input{
		margin-top: 20px;
	}
	#lst_All,#lst_Sel{
		height:200px !important;
	}
	.tool-fix{
		display:none;
	}
</style>
<body >
	<form name="Form1" method="post" action="" id="Form1">
      <div id="centent_body">
		<div class="div_title" style="font-weight:bolder;text-align: center;color: red;margin-top: 30px;font-size: 16px;">汇总分析条件选择</div>
        <div class="div_table">
            <table id="Table1" class="form-detail-table" width="350px" cellpadding="3" border="0" style="margin: 0 auto;margin-top: 10px;">
				<tr>
					<td style="height: 20px; font-weight:bolder; font-size:12px;text-align:center">源数据列</td>
					<td style="height: 20px; text-align:center">&nbsp;<span id="lbl_dlh"></span>&nbsp;</td>
					<td style="height: 20px; font-weight:bolder; font-size:12px; text-align:center">进行汇总统计列</td>
				</tr>
				<tr>
					<td><select size="4" name="lst_All" id="lst_All" style="height:200px;width:160px;">
						<option value="YQBH">资产编号</option>
						<option value="YQMC">资产名称</option>
						<option value="FLH">分类号</option>
						<option value="FLMC">分类名称</option>
						<option value="XZ">现状</option>
						<option value="DJ">单价</option>
						<option value="ZZJ">总价</option> 
						<option value="SYR">使用人</option>
						<option value="SYDW">使用单位</option>
						<option value="SYFX">使用方向</option>
						<option value="BZXX">存放地点</option>
						<option value="GG">规格</option>
						<option value="XH">型号</option>
						<option value="ZCLY">资产来源</option>
						<option value="GZRQ">购置日期</option>
						<option value="JSR">经手人</option>
						<option value="CGR">采购人</option>
					</select></td>
					<td>
						<div class="mid_top">
							<input type="button" class="btn btn-default" id="button_tj" value="添加"><br/>
							<input type="button" class="btn btn-default" id="button_qx" value="取消"><br/>
							<input type="button" class="btn btn-default" id="button_qd" value="确定"><br/>
							<input type="button" class="btn btn-default" id="button_gb" value="关闭">
						</div>
					</td>
					<td>
						<select size="4" name="lst_Sel" id="lst_Sel" style="height:200px;width:160px;">
						</select>
					</td>
				</tr>
			</table>
        </div>
    </div>
	</form>
</body>
</html>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	var winId = getTopFrame().layer.getFrameIndex(window.name);
	//添加
	$("#button_tj").click(function(){
		var text = $("#lst_All>option:selected").text();
		var value = $("#lst_All>option:selected").val();
		var option = '<option value="'+value+'">'+text+'</option>';
		$("#lst_Sel").append(option);
		$("#lst_All>option:selected").hide();
	});
	$("#lst_All option").dblclick(function(){
		var text = $(this).text();
		var value = $(this).val();
		$(this).hide();
		var option = '<option value="'+value+'">'+text+'</option>';
		$("#lst_Sel").append(option);
	});
	//取消
	$("#button_qx").click(function(){
		var value = $("#lst_Sel>option:selected").val();
		$("#lst_All>option").each(function(){
			if($(this).val()==value){
				$(this).show();
			}
		});
		$("#lst_Sel>option:selected").remove();
	});
	$(document).on("dblclick","#lst_Sel option",function(){
		var text = $(this).text();
		var value = $(this).val();
		$(this).remove();
		$("#lst_All>option").each(function(){
			if($(this).val() == value){
				$(this).show();
			}
		});
	});
	//保存
	$("#button_qd").click(function(){
		var zdy = [];
		var zdymc = [];
		$("#lst_Sel>option").each(function(){
			if($(this).css('display')!='none'){
				zdy.push($(this).val());
				zdymc.push($(this).text());
			}
		});
		var wstr = "";
		if(getIframWindow("${param.pname}").$("form").length > 0){
			wstr = encodeURIComponent(searchJson(getIframWindow("${param.pname}").$("form").attr("id"),"${param.pname}"));
		}
		window.open("${pageContext.request.contextPath}/select/doZdyTable?zdy="+zdy.join(",")+"&zdymc="+zdymc.join(",")+"&wstr="+wstr+"&jdbh=${param.jdbh}");
    	close(winId);
	});
	//关闭
	$("#button_gb").click(function(){
    	close(winId);
	});
});
</script>
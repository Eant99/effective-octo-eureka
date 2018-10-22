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
		width: 90px;
		margin-top: -15px;
	}
	.mid_top input{
		margin-top: 20px;
	}
	#lst_All,#lst_Sel{
		height:300px !important;
	}
</style>
<body >
	<form name="Form1" method="post" action="" id="Form1">
      <div id="centent_body">
		<div class="div_title" style="font-weight:bolder;text-align: center;color: red;margin-top: 20px;font-size: 16px;">排 序 选 择</div>
        <div class="div_table">
            <table id="Table1" class="form-detail-table" width="350px" cellpadding="3" border="0" style="margin: 0 auto;">
				<tr>
					<td style="height: 20px; font-weight:bolder; font-size:12px;text-align:center">源数据列</td>
					<td style="height: 20px; text-align:center">&nbsp;<span id="lbl_dlh"></span>&nbsp;</td>
					<td style="height: 20px; font-weight:bolder; font-size:12px; text-align:center">进行排序列</td>
				</tr>
				<tr>
					<td id="td_sel" class="td"></td>
					<td>
						<div class="mid_top">
							<input type="button" class="btn btn-default" id="button_sx" value="添加升序"><br/>
							<input type="button" class="btn btn-default" id="button_jx" value="添加降序"><br/>
							<input type="button" class="btn btn-default" id="button_qx" value="取消"><br/>
							<input type="button" class="btn btn-default" id="button_qd" value="确定"><br/>
							<input type="button" class="btn btn-default" id="button_gb" value="关闭">
						</div>
					</td>
					<td class="td">
						<select size="4" name="lst_Sel" id="lst_Sel" style="height:300px;width:160px;">
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
<script src="${pageContext.request.contextPath}/static/javascript/public/public-json.js"></script>
<script type="text/javascript">
$(function(){
	var columns = JSON.getJson("${param.columns}");
	var select = [];
	select.push('<select size="4" name="lst_All" id="lst_All" style="height:300px;width:160px;">');
	for(var i=0;i<columns.length;i++){
		var d = columns[i];
		select.push('<option value="' + d.zhpxzd + '">' + d.zhpxname + '</option>');
	}
	select.push('</select>');
	$("#td_sel").prop("innerHTML",select.join(""));
	//添加升序
	$("#button_sx").click(function(){
		var text = $("#lst_All>option:selected").text();
		var value = $("#lst_All>option:selected").val();
		var option;
		if(value == undefined){
			alert("请选择数据源列！");
		}else{
			option = '<option class="asc" value="k.'+value+' asc">'+text+'↑</option>';
		}
		$("#lst_Sel").append(option);
		$("#lst_All>option:selected").hide();
		$("#lst_All>option").attr("selected",false);
		return false;
	});
	//添加降序
	$("#button_jx").click(function(){
		var text = $("#lst_All>option:selected").text();
		var value = $("#lst_All>option:selected").val();
		var option;
		if(value == undefined){
			alert("请选择数据源列！");
		}else{
			option = '<option class="desc" value="k.'+value+' desc">'+text+'↓</option>';
		}
		$("#lst_Sel").append(option);
		$("#lst_All>option:selected").hide();
		$("#lst_All>option").attr("selected",false);
	});
	//取消
	$("#button_qx").click(function(){
		var value = $("#lst_Sel>option:selected").val();
		$("#lst_All>option").each(function(){
			if("k."+($(this).val()+' asc')==value||"k."+($(this).val()+' desc')==value){
				$(this).show();
			}
		});
		$("#lst_Sel>option:selected").hide();
	});
	//保存
	$("#button_qd").click(function(){
		var asc = [];
		$("#lst_Sel>option").each(function(){
			if($(this).css('display')!='none'){
				asc.push($(this).val());
			}
		});
		if(asc.length>0){
			getIframWindow("${param.pname}").table.ajax.url("${pageContext.request.contextPath}/select/getPublicList?jdbh=${param.jdbh}&asc="+asc.join(",")+"&pxzh=pxzh").load();
			var winId = getTopFrame().layer.getFrameIndex(window.name);
	    	close(winId);
		}else{
			alert("请选择数据源列！");
		}
	});
	//关闭
	$("#button_gb").click(function(){
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
});
</script>
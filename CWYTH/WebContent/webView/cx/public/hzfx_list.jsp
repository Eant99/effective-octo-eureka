<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>汇总分析</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
#drp_zc,#drp_qt,#txys,#hztj{
	display: none;
}
.select,.tool-fix{
	display: none;
}
</style>
<body>
	<form action="" method="post" id="Form1">
		<div id="centent_body">
			<div class="div_title" style="text-align: center;margin-top: 5px;font-size: 16px;">
			    <label style="color:Red" class="div_title">汇 总 分 析</label>
			</div>
			<div class="div_table">
				<table id="table" border='1' class="tab" style="width: 368px; height: 64px; margin:0 auto;border-color: rgba(211, 214, 214, 0.35);font-size: 14px;" cellspacing="0" cellpadding="0">
					<tr>
						<td style="text-align:center">
							<input id="cb_1" type="radio" checked="checked" name="check" value="sydw"/>
							<label for="cb_1">使用单位</label><font face="宋体"></font>
						</td>
						<td id="td_sel" style="text-align:center" rowspan="3">
							<select name="drp_dw" id="drp_dw" style="width: 100px;">
								<option value="0">所有单位</option>
								<option value="-1">末级单位</option>
								<option value="1">1级</option>
								<option value="2">2级</option>
							</select>
							<select name="drp_zc" id="drp_zc" style="width: 100px;">
								<option value="1">1级</option>
								<option value="2">2级</option>
								<option value="3">3级</option>
								<option value="4">4级</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style=" text-align:center">
                            <input id="cb_2" type="radio" name="check" value="flh"/>
                            <label for="cb_2">资产分类</label>
                        </td>
					</tr>
					<tr>
						<td style=" text-align:center">
                            <input id="cb_2" type="radio" name="check" value="bzxx"/>
                            <label for="cb_2">存放地点</label>
                        </td>
					</tr>
					<tr>
						<td style=" text-align:center">
                            <input id="cb_2" type="radio" name="check" value="syfx"/>
                            <label for="cb_2">使用方向</label>
                        </td>
                        <td style="text-align:center;font-size: 14px;" rowspan="1">汇总方式
							<select style="width:100px;" id="hzfx">
								<option selected="selected" value="1">汇总分析表</option>
								<option value="2">汇总分析图</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style=" text-align:center">
                            <input id="cb_2" type="radio" name="check" value="syr"/>
                            <label for="cb_2">使用人&emsp;</label>
                        </td>
                        <td style="text-align:center;font-size: 14px;" rowspan="1"><span class="select">汇总条件</span>
							<select style="width:100px;" id="hztj">
								<option value="zj">金额</option>
								<option value="sl">数量</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style=" text-align:center">
				        	<input id="cb_3" type="radio" name="check" value="xz"/>
				        	<label for="cb_3">资产现状</label>
				        </td>
				        <td style="text-align:center;font-size: 14px;" rowspan="1"><span class="select">图形样式</span>
							<select style="width: 100px;" id="txys">
								<option value="1">折线图</option>
								<option value="2">饼状图</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style=" text-align:center">
				        	<input id="cb_5" type="radio" name="check" value="zcly"/>
				        	<label for="cb_5">资产来源</label>
				        </td>
				        <td align="center" rowspan="1">&nbsp;</td>
					</tr>
					<tr>
						<td style=" text-align:center">
				        	<input id="cb_4" type="radio" name="check" value="jfkm"/>
				        	<label for="cb_4">经费来源</label>
				        </td>
				        <td align="center" rowspan="1">&nbsp;</td>
					</tr>
					<tr>
						<td style=" text-align:center">
					        <input id="cb_6" type="radio" name="check" value="gzrq"/>
					        <label for="cb_6">购置年份</label>
				       	</td>
						<td align="center" rowspan="1">&nbsp;</td>
					</tr>
				</table>
			</div>
			<div style="text-align:center;margin-top: 18px;">
				<button type="button" class="btn btn-default" id="button_qd">确定</button>
				<button type="button" class="btn btn-default" id="button_zdy">自定义条件</button>
				<button type="button" class="btn btn-default" id="button_gb">关闭</button>
			</div>
		</div>
	</form>
</body>
</html>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	var winId = getTopFrame().layer.getFrameIndex(window.name);
	//显示与隐藏
	$("[name='check']").change(function(){
		var value = $(this).val();
		$("#td_sel select").hide();
		if(value=='sydw'){
			$("#drp_dw").show();
		}else if(value=='flh'){
			$("#drp_zc").show();
		}
	});
	//汇总方式显示与隐藏
	$("#hzfx").change(function(){
		var value = $("#hzfx").val();
		if(value=='2'){
			$("#txys,#hztj,.select").show();
		}else{
			$("#txys,#hztj,.select").hide();
		}
	});
	//确定
	$("#button_qd").click(function(){
		var value = $("input[type='radio']:checked").val();
		var text = $("input[type='radio']:checked").parent().text();
		var hztj = "";
		var txys = "";
		var hzfx = $("#hzfx").val();
		if(hzfx=='2'){
			txys = $("#txys").val();
			hztj = $("#hztj").val();
		}
		var jc = "";
		if(value=='sydw'){
			jc = $("#drp_dw").val();
		}else if(value=='flh'){
			jc = $("#drp_zc").val();
		}
		
		var wstr = "";
		if(getIframWindow("${param.pname}").$("form").length > 0){
			wstr = encodeURIComponent(searchJson(getIframWindow("${param.pname}").$("form").attr("id"),"${param.pname}"));
		}
		window.open("${pageContext.request.contextPath}/select/doHzfx?lb="+value+"&wstr="+wstr+"&jdbh=${param.jdbh}&jc="+jc+"&tj="+hztj+"&txys="+txys+"&text="+text);
		close(winId);
		return false;
	});
	//自定义条件
	$("#button_zdy").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/cx/public/zdy_list.jsp?pname=${param.pname}&jdbh=${param.jdbh}", "自定义汇总列选择", 500, 400);
		close(winId);
		return false;
	});
	//关闭
	$("#button_gb").click(function(){
    	close(winId);
	});
});
</script>
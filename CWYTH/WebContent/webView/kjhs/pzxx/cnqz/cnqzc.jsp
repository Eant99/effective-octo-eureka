<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.googosoft.constant.Constant"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>列选择</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<style type="text/css">
	body{
		overflow-x:hidden;
	}
</style>
<style type="text/css">
	button{
		background-color: #00acec !important;
		color: white !important;
	}
</style>
<body class="contrast-red" >
<div id='wrapper'>
<section>
    <div class='row' id='content-wrapper'>
        <div class='col-md-12'>
             <div class="box">
                 <div class='box-content' style="padding-bottom: 0; overflow:visible;">
                 <button type="button" class="btn btn-primary" id="btn_cx"><i class="fa icon-cx"></i>确定</button>
                 	<div class="alert alert-info" style="padding: 6px;margin-bottom: 4px;">
			          	<strong>提示：</strong><c:if test="${param.from == 'wxjfhb'}">（1）</c:if>不选择默认为全部</strong>;
			        </div>
			        
			        <hr class="hr-normal" id="hr">
                     <div class='responsive-table'>
                     <div class='scrollable-area'>
                     <table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					     <thead>
					     <tr id="header">
					          <th></th>
					          <th>序号</th>
					          <th>名称</th>
					     </tr>
					     </thead>
					     <tbody>
					     	<tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_pzrq" name="pzrq"/>
					          </td>
					          <td>1</td>
					          <td>凭证日期</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_pzzh" name="pzrq"/>
					          </td>
					          <td>2</td>
					          <td>凭证字号</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_zy" name="zy"/>
					          </td>
					          <td>3</td>
					          <td>摘要</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_kmbh" name="wldc"/>
					          </td>
					          <td>4</td>
					          <td>科目编号</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_kmmc" name="wldc"/>
					          </td>
					          <td>5</td>
					          <td>科目名称</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_jfje" name="wldc"/>
					          </td>
					          <td>6</td>
					          <td>借方金额（元）</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_dfje" name="wldc"/>
					          </td>
					          <td>7</td>
					          <td>贷方金额（元）</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_bm" name="wldc"/>
					          </td>
					          <td>8</td>
					          <td>部门</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_xm" name="wldc"/>
					          </td>
					          <td>9</td>
					          <td>项目</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_zyy" name="wldc"/>
					          </td>
					          <td>10</td>
					          <td>职员</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_wldw" name="wldc"/>
					          </td>
					          <td>11</td>
					          <td>往来单位</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_zdr" name="wldc"/>
					          </td>
					          <td>12</td>
					          <td>制单人</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_fhr" name="wldc"/>
					          </td>
					          <td>13</td>
					          <td>复核人</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_cnr" name="wldc"/>
					          </td>
					          <td>14</td>
					          <td>出纳人</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_jzr" name="wldc"/>
					          </td>
					          <td>15</td>
					          <td>记账人</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_pzzt" name="wldc"/>
					          </td>
					          <td>16</td>
					          <td>凭证状态</td>
					     </tr>
					     <tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          	<input type="hidden" value="tr_sfdy" name="wldc"/>
					          </td>
					          <td>17</td>
					          <td>是否打印</td>
					     </tr>
					     </tbody>
				     </table>
                     </div>
                     </div>
                  </div>
              </div>
         </div>
      </div>
</section>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function (){
	var winId = getTopFrame().layer.getFrameIndex(window.name);
	
//     //双击事件
//     $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
//     	var val = $(this).find("[name='wldc']").val();
//     	console.log(val);
//     	if(val==''||val==null||val=='undefined'){
//     		alert("没有可以选择的数据！");
//     	}else{
//     		getIframeControl("${param.pname}","${param.controlId}").val(val);
//         	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
//         	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
//         	close(winId);
//     	}
//     });
	//取消
   	$("#btn_cancel").on("click",function(){
   		close(winId);
   	});
	$("#btn_cx").click(function(){
		var col = $("#mydatatables tr:not(#header)").find(":checkbox").filter(":checked");
		var cols = [];
		$.each(col.next(),function(){
			cols.push($(this).val());
		});
		getIframeControl("${param.pname}","${param.controlId}").val(cols.join(","));
    	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
    	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
    	getIframWindow("${param.pname}").selectCol();
    	close(winId);
	});
});
</script>
</body>
</html>
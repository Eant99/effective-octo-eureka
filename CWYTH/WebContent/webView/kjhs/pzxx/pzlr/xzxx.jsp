<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>薪资报销信息列表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style>
.btn-link{
	padding: 0px!important;
	margin: 0px!important;
}
 .dataTables_scrollBody{
    height:425px !important;
 }
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
<!-- 		<div class="row rybxx" style="margin-left:-15px"> -->
<!-- 			<div class="col-ms-12 tabs" style="padding-right: 0px"> -->
				
<!-- 			</div> -->
<!-- 		</div> -->
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
<!-- 				<div class="form-group"> -->
<!-- 					<label>单据类型</label> -->
<!-- 					<select id="sel_djlx" class="form-control"> -->
<!-- 						<option value="0" >报销信息</option> -->
<!-- 						<option value="1" selected>薪资信息</option> -->
<!-- 					</select> -->
<!-- 				</div> -->
					<div class="form-group">
						<label>人员类型</label>
						<select id="sel_rylx" class="form-control">
							<option value="1" >在职人员</option>
							<option value="0" selected>退休人员</option>
						</select>
					</div>
				<div class="form-group">
					<label>工资月份</label>
					<input type="text" id="txt_gzyf" class="form-control xzpznydate" name="gzyf"  placeholder="请输入工资月份"/>
				</div>
				
<!-- 				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button> -->
			</div>
		</form>
		<button type="button" class="btn btn-primary" id="btn_submit">确定</button>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
var target = "${ctx}/kjhs/pzxx/pzlr";
var winId = top.layer.getFrameIndex(window.name);
$(function () {
	$(".tool-fix").hide();

	$("#btn_submit").click(function(){
		var gzyf = $("#txt_gzyf").val();
		var rylx = $("#sel_rylx").val();
		if(gzyf.length > 0){
			$.ajax({
				  type:"post",
				  url:target+"/doaddxz",
				  data:"pzlx=${param.pzlx}&rylx="+rylx+"&gzyf="+gzyf,
				  dataType:"json",
				  success:function(data){
						alert(data.msg);
					  if(data.success){
			   		 	 getIframWindow("${param.pname}").pageSkip(getBasePath(),"pzlr&type=self&pzlx=${param.pzlx}");
			        	close(winId);
					  }
				  },
				  error:function(){
					  alert("抱歉，系统出现错误！");
				  }
			  });
		}else{
			alert("请输入工资月份");
		}
		
	});
});
</script>
</body>
</html>
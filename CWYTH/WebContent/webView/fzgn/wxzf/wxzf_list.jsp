<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>支付详情</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<div class="row rybxx" style="margin-left:-15px">
			
		</div>
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
			    <div class="form-group">
					<label>支付状态</label>
					<select class="form-control" id="txt_zfzt" name="zfzt">
						<c:forEach items="${zfztList}" var="item">
							<option value="${item.DM }" <c:if test="${item.DM eq '03' }">selected</c:if>>${item.MC }</option>
						</c:forEach>						
					</select>
				</div>
				<div class="form-group">
					<label>支付方式</label>
					<select class="form-control" id="txt_zffs" name="zffs">
						<option value="">--请选择--</option>
						<option value="微信" <c:if test="${'微信' eq param.zffs }">selected</c:if>>微信</option>
						<option value="支付宝" <c:if test="${'支付宝' eq param.zffs }">selected</c:if>>支付宝</option>						
					</select>
				</div>
				<div class="form-group">
					<label>人员类型</label>
					<select class="form-control" id="txt_RYLX" name="RYLX">
						<option value="">--请选择--</option>
						<option value="学生" <c:if test="${'学生' eq param.rylx }">selected</c:if>>学生</option>
						<option value="教师" <c:if test="${'教师' eq param.rylx }">selected</c:if>>教师</option>					
					</select>
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
                <button type="button" class="btn btn-primary" id="btn_hz">统计</button>
                <div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>综合查询</span>
					<div class="search-more" style="width: 450px">
						<div class="form-group">
							<label>姓名</label>
							<input  type="text" name="NAMES" id="txt_xms" class="input_info  form-control" style="width:130px;" placeholder="请输入姓名" value=""  />
							<button type="button" id="btn_xms" class="btn btn-link ">选择</button>
						</div>
						<div class="form-group">
							<label>承包商名称</label>
							<input type="text" id="txt_cbsmc" class="form-control" name="SSCBSMC" table="K" placeholder="请输入承包商名称">
							<button type="button" id="btn_cbsmc" class="btn btn-link ">选择</button>
						</div>
						<div class="form-group">
							<label>消费地点名称</label>
							<input type="text" id="txt_xfdd" class="form-control" name="xfddmc" table="k" placeholder="请输入消费地点名称">
							<button type="button" id="btn_xfddmc" class="btn btn-link ">选择</button>
						</div>
						<div class="form-group">
							<label>消费时间</label>
							<br>
							<input   type="text" name="XFSJ" types="TILA" id="txt_km1" class="input_info  form-control times"  placeholder="开始时间" style="width:130px;" value=""  />
							<label>至</label>
							<input   type="text" name="XFSJ"  types="TIHB" id="txt_km2" class="input_info  form-control times" placeholder="截止时间" style="width:130px;" value=""  />
						</div>
						<div class="search-more-bottom clearfix">
							<div class="pull-right">
								<button type="button" class="btn btn-primary" id="btn_search" >
									<i class="fa icon-chaxun"></i>
									查 询
								</button>
<!-- 								<button type="button" class="btn btn-default" id="btn_cancel"> -->
<!-- 									取 消 -->
<!-- 								</button> -->
							</div>
						</div>
					</div>
				</div>
<!-- 				<div class="btn-group pull-right" role="group"> -->
<!-- 	               <button type="button" class="btn btn-primary" id="btn_hz">汇总</button> -->
<!-- 	               <button type="button" class="btn btn-default" id="btn_del">批量删除</button> -->
	               
<!-- 				</div> -->
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th>
				            <th>审核状态</th>
				            <th>工号/学号</th>
				            <th>姓名</th>
				            <th>所属单位</th>	
				            <th>消费地点</th>
				            <th>业务类型</th>
				            <th>所属承包商</th>
				            <th>消费时间</th>
				            <th>消费金额(元)</th>           
				            <th>支付方式</th>
				            <th>人员类型</th>
				        </tr>
					</thead>
				    <tbody>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<%-- <script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script> --%>
<script>
$(function () {
	//联想输入提示
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
	var zfzt = $("[name='zfzt']").val();
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},  
       {"data": "ZFZT",orderable:false,'render':function(data,type,full,meta){
    	   if("01"==full.ZFZT){
    		   return '未支付';
    	   }else if("02"==full.ZFZT){
    		   return '支付中';
    	   }else{
    		   return '已支付';
    	   }
    		   
         	},"class":"text-center"},  	
       {"data": "RYGH",defaultContent:""},
       {"data": "RYXM",defaultContent:""},
       {"data": "SSDWMC",defaultContent:""},       
       {"data": "XFDDMC",defaultContent:""},
       {"data": "YWLXMC",defaultContent:""},
       {"data": "SSCBSMC",defaultContent:""},       
       {"data": "XFSJ",defaultContent:"","class":"text-center"},
       {"data": "XFJE",defaultContent:"","class":"text-right"},  
       {"data": "ZFFS",defaultContent:""}, 
       {"data": "RYLX",defaultContent:""}  
       
     ];
    	table = getDataTableByListHj("mydatatables","${ctx}/wxzf/getPageList",[9,'desc'],columns,0,1,setGroup);

 	//汇总按钮
   	$("#btn_hz").click(function(){
   		window.location.href = "${ctx}/webView/fzgn/wxzf/zfxxhz_list.jsp";
   	});
 	$("#txt_zfzt").change(function(){
 		$("#btn_search").click();
 	});
 	//默认查询  支付状态
 	$("#btn_search").click();	
 	//查询弹窗
   	$("#btn_xms").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_xms","人员信息","920","630");
    });
   //查询弹窗--承包商
   	$("#btn_cbsmc").click(function(){
		select_commonWin("${ctx}/wxzf/ToWindow?controlId=txt_cbsmc","承包商信息","920","630");
    });
   
   //查询弹窗--消费地点
   	$("#btn_xfddmc").click(function(){
		select_commonWin("${ctx}/wxzf/ToWindow?controlId=txt_xfdd","消费地点信息","920","630");
    });
});
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
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>操作日志信息</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" role="form" action="">
    		<div class="search-simple">
				<div class="form-group">
					<label>操&ensp;作&ensp;人</label>
			        <input type="text" id="txt_rybh" class="form-control input-radius" name="rybh" value="" types="R" table="K" placeholder="请输入操作人">
			        <button type="button" id="btn_czr" class="btn btn-link ">选择</button>
				</div>
				<div class="form-group">
					<label>设备类型</label>
					 <select  name="syd" table="K" class="form-control" style="width: 150px;">
					  <option value="">--请选择--</option>
					 	<option value="1">web端</option>
					 	<option value="0">移动端</option>
					 </select>
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div id="btn_search_more">
					<span id="btn_search_gjcx">
						<i class="fa icon-btn-gjcx"></i>
						<span>高级查询</span>
					</span>
					<div class="search-more">
						<div class="form-group">
							<label>单据编号</label>
							<input type="text" id="txt_dbh" class="form-control" table="K" name="dbh" placeholder="请输入单据编号">
						</div>
						<div class="form-group">
							<label>操作机器</label>
						    <input type="text" id="txt_czjq" class="form-control" name="czjq" table="K" placeholder="请输入操作机器">
						</div>
						<div class="form-group">
							<label>操作日期</label>
							<div class="input-group" style="width:110px;">
								<input type="text" id="txt_czrq_begin" class="form-control datetime" name="czrq" types="TIL" table="K" data-format="yyyy-MM-dd" placeholder="开始日期">
								<span class='input-group-addon'>
							    	<i class="glyphicon glyphicon-calendar"></i>
							   	</span>
						   	</div>
							<label>--</label>
							<div class="input-group" style="width:110px;">
								<input type="text" id="txt_czrq_end" class="form-control datetime" name="czrq" types="TIH" table="K" data-format="yyyy-MM-dd" placeholder="截止日期">
								<span class='input-group-addon'>
							    	<i class="glyphicon glyphicon-calendar"></i>
						   		</span>
							</div>
						</div>
						<div class="search-more-bottom clearfix">
							<div class="pull-right">
								<button type="button" class="btn btn-primary" id="btn_search">
									<i class="fa icon-chaxun"></i>
									查 询
								</button>
								<button type="button" class="btn btn-default" id="btn_cancel">
									取 消
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
        </form>
    </div>
    <div class="hj" style="display: none;">
    	<div class="btn-group pull-right btn-group-marginTop" role="group" style="padding-right: 20px;">
            <button id="btn_del" type = "button" class="btn btn-default">清空</button>
            <button id="btn_exp"  type = "button" class="btn btn-default" >导出Excel</button>
        </div>
    </div>
	<div class="container-fluid">
        <div class='responsive-table'>
            <div class='scrollable-area'>
                 <table id="mydatatables" class="table table-striped table-bordered">
				    <thead>
					    <tr>
					        <th><input type="checkbox" class="select-all"/></th>
					        <th>序号</th>
					        <th>操作人</th>
					        <th>操作日期</th>
					        <th>单据编号</th>
					        <th>操作类型</th>
					        <th>操作内容</th>
					        <th>操作机器</th>	
					        <th>设备类型</th>
					        <th>设备名称</th>				       
					    </tr>
				    </thead>
				    <tbody></tbody>
				</table>
            </div>
        </div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function (){
	$(".date").datetimepicker();
	//联想输入提示
	$("#txt_rybh").bindChange("${ctx}/suggest/getXx","R");
	
   	$("#more").bind('click', function (){
       	$(this).parents(".box-content").find(".row:not(:first):not(:nth-child(2))").toggleClass("hidden");
        $(this).toggleClass("dropup");
    });
   	
	//导出Excel
	$(document).on("click","#btn_exp",function() {
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='logbh']").filter(":checked");
		var logbh = [];
		checkbox.each(function(){
			logbh.push($(this).val());
		});
   		doExp(json,"${ctx}/oplog/expExcel","操作日志","${ctx}",logbh.join(","));
   	});
	
    //信息清空
    $(document).on("click","#btn_del",function() {
   		var checkbox = $("#mydatatables").find(":checkbox");
   		var index;
   		if (checkbox.length>1){
   		confirm("确认要清空所有信息吗？",{title:"提示"},function(index){
   			$.ajax({
				type :"post",
				url:"${ctx}/oplog/doDeleteAll",
				dataType:"json",
				success:function(val){					
					close(index);
					if(val.success == 'true'){
						alert(val.msg);
						table.ajax.reload();
					}else if(val == "false"){
						alert(val.msg);
						table.ajax.reload();
					}
				},
				error:function(val){
      				close(index);
      				alert(getPubErrorMsg());      					 
      			},
				beforeSend:function(){
	   				index = loading(2);
	   			}
			});
   		});
   		}else{
   			alert("信息已被清空！");
   		}
   	});
   	//弹窗
   	$("#btn_czr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_rybh","人员信息","920","630");
    });
   	//列表数据
   	var columns = [
		{"data": "LOGBH",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="logbh" value="' + data + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
	   	{"data": "RYBH",defaultContent:""},
		{"data": "CZRQ",defaultContent:""},    
	   	{"data": "DBH",defaultContent:""},
	   	{"data": "CZLX",defaultContent:""},
	   	{"data": "CZNR",defaultContent:""},
	   	{"data": "CZJQ",defaultContent:""},	
	   	{"data": "SYD",defaultContent:""},
		{"data": "LLQ",defaultContent:""},
	];
   	table = getDataTableByListHj("mydatatables","${ctx}/oplog/getPageList",[3,'desc'],columns,0,1,setGroup);
    
    //综合查询
    $("#btn_search").on("click",function(){
		var json = searchJson("searchBox");
    	$('#mydatatables').DataTable().search(json,"json").draw();
    });
   
	$(".btn_search_more").on("click", function(){
		$(".btn_search_more").toggleClass("dropup");
		$("#searchBox .row:not(:first-child)").toggleClass("hide");
	});
   	$(".wxts").append($(".hj").html());
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
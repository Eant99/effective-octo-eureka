<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>固定资产管理系统单位信息</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<!-- --------------------------------综合查询 box开始-------------------------------------------->
<div class="box">
    <div class="box-content">
    	<form id="myform" class="form-horizontal" action="">
    	<div class="clearfix">
           	<div class="sub-title pull-left text-primary">综合查询</div>
        </div>
        <hr class="hr-normal">
        <div class="container-fluid" id="searchBox">
        <!-- row 表示一行，col-md-x 表示一列占几个位置，一行一共12个位置,此处表示一行三列   开始 -->
            <div class="row">
            <!-- input-group 表示一个表单组，包含一个label和1个input或者1个select，或者1个input-group  开始-->
            	<div class="col-md-3">
	            	<div class="input-group">
			            <span class="input-group-addon">部&ensp;门&ensp;号</span>
		                <input type="text" id="txt_bmh" class="form-control" name="bmh" table="K" placeholder="请输入部门号">
	                </div>
                </div>
                <div class="col-md-3">
            <!-- input-group 表示一个表单组，包含一个label和1个input或者1个select，或者1个input-group  结束-->
	             	<div class="input-group">
	                    <span class="input-group-addon">单位名称</span>
                     	<input type="text" id="txt_mc" class="form-control" name="mc" table="K" placeholder="请输入单位名称">
	               	</div>
               	</div>
                <div class="col-md-3">
	               	<div class="input-group">
	                    <span class="input-group-addon">简&emsp;&emsp;称</span>
                     	<input type="text" id="txt_jc" class="form-control" name="jc" table="K" placeholder="请输入单位简称">
	                </div>
                </div>
                <div class="col-md-3">
	               	<div class="input-group">
	                    <span class="input-group-addon">单位性质</span>
               			<select id="drp_dwxz" class="form-control" name="dwxz" table="K" types="E">
               				<option value="">未选择</option>
                		</select>
	                 </div>
                 </div>
             </div>
       <!-- row 表示一行，col-md-x 表示一列占几个位置，一行一共12个位置,此处表示一行三列   结束 -->
             <div class="row hide"><!-- hide 页面加载完隐藏 -->
                <div class="col-md-3">
					<div class="input-group">
				     	<span class="input-group-addon">地&emsp;&emsp;址</span>
				     	<input type="text" id="txt_dz" class="form-control" table="K" name="dz" placeholder="请输入单位地址">
					</div>
				</div>
                <div class="col-md-3">
					<div class="input-group">
					    <span class="input-group-addon">单位领导</span>
			     		<input type="text" id="txt_dwld" class="form-control input-radius" name="dwld" value="" types="R" table="K" placeholder="请输入单位领导">
						<span class="input-group-btn"><button type="button" id="btn_dwld" class="btn btn-link ">选择</button></span>
					</div>
				</div>
                <div class="col-md-6">
			    	<div class="double-input">
				    	<div class="input-group">
			    			<span class="input-group-addon">建立日期</span>
						    <input type="text" id="txt_jlrq_begin" class="form-control date" name="jlrq" types="TL" table="K" data-format="yyyy-MM-dd" placeholder="请输入开始日期">
							<span class='input-group-addon'>
						    	<i class="glyphicon glyphicon-calendar"></i>
						   	</span>
					   	</div>
				   		<span class="connector" >—</span>
				   		<div class="input-group">
						    <input type="text" id="txt_jlrq_end" class="form-control date" name="jlrq" types="TH" table="K" data-format="yyyy-MM-dd" placeholder="请输入截止日期">
							<span class='input-group-addon'>
						    	<i class="glyphicon glyphicon-calendar"></i>
					   		</span>
					   	</div>
					</div>
				</div>
            </div>
            <div class="row hide">
                <div class="col-md-3">
	            	<div class="input-group">
	                	<span class="input-group-addon">单位状态</span>
             		 	<select id="drp_dwzt" class="form-control" name="dwzt" table="K">
             		 		<option value="">未选择</option>
                       		<option value="1">正常</option>
                       		<option value="0">禁用</option>
                   		</select>
	              	</div>
              	</div>
            </div>
            <div class="row hide">
            	<div class="col-md-1 text-right">
            		<span class="zt_label">单据状态：</span>
            	</div>
                <div class="col-md-11 checkBox" table="K" types="C" name="dwzt">
                	<a type="button" data-value="" class="btn btn-link btn-mark btn-mark-all">全部<span class="label label-success">&radic;</span></a>
                	<a type="button" data-value="55" class="btn btn-link btn-mark active">未提交 <span class="badge">12</span><span class="label label-success">&radic;</span></a>
                	<a type="button" data-value="00" class="btn btn-link btn-mark">已提交 <span class="badge">10</span><span class="label label-success">&radic;</span></a>
                	<a type="button" data-value="11" class="btn btn-link btn-mark">归口审核通过 <span class="badge">8</span><span class="label label-success">&radic;</span></a>
                	<a type="button" data-value="10" class="btn btn-link btn-mark">归口审核退回 <span class="badge">1</span><span class="label label-success">&radic;</span></a>
              	</div>
            </div>
            <div class="center-content">
             	<button type="button" class="btn btn-default" id="btn_search">
             		<i class="fa icon-search"></i>查询
             	</button>
                <button type="reset" class="btn btn-default">
                	<i class="fa icon-reset"></i>重置
               	</button>
            </div>
            <button id="btn_search_more" class="btn_search_more" type="button">高级查询 <span class="caret"></span></button>
        </div>
        </form>
    </div>
</div>
<!-- --------------------------------综合查询 box   结束------------------------------------------ -->
<!-- --------------------------------列表项  box   开始------------------------------------------ -->
<div class="box">
    <div class='box-content'>
    <!-- 列表操作按钮组    开始 -->
        <div class="clearfix">
            <div class="pull-left sub-title text-primary">单位信息</div>
            <div class="btn-group pull-right" role="group">
                <button type='button' class="btn btn-default" id="btn_add"><i class='fa icon-add'></i>增加</button>
                <button type='button' id="btn_del" class="btn btn-default"><i class='fa icon-del'></i>批量删除</button>
               	<button type='button' id="btn_assig" class="btn btn-default"><i class='fa icon-assig'></i>批量赋值</button>
                <button type='button' id="btn_exp"  class="btn btn-default" ><i class='fa icon-exp'></i>导出Excel</button>
                <!-- ------带下拉菜单的按钮组     开始------ -->
                <div class="btn-group">
				  <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">
				    	<i class='fa icon-sangangy'></i>
				  </button>
				  <ul class="dropdown-menu dropdown-menu-right" role="menu">
				  	<li><a id="btn_del" class="btn btn-link"><i class='fa icon-piliangshanchu-copy'></i>批量删除</a></li>
				  </ul>
				</div>
				<!-- ------带下拉菜单的按钮组     结束------ -->
            </div>
        </div>
        <!-- 列表操作按钮组    结束 -->
        <hr class="hr-normal"/>
        <div class='responsive-table'>
            <div class='scrollable-area'>
            <!-- datatables  开始 -->
                 <table id="mydatatables" class="table table-striped table-bordered">
				    <thead>
				    <tr>
				        <th><input type="checkbox" class="select-all"/></th>
				        <th>序号</th>
				        <th>部门号</th>
				        <th>单位名称</th>
				        <th>单位简称</th>
				        <th>单位地址</th>
				        <th>单位性质</th>
				        <th>成立日期</th>
				        <th>单位领导</th>
				        <th>上级单位</th>
				        <th>单位状态</th>
				        <th>操作</th>
				    </tr>
				    </thead>
				    <tbody>
				    </tbody>
				</table>
			<!-- datatables  结束 -->
            </div>
        </div>
    </div>
</div>
<!-- --------------------------------列表项  box  结束------------------------------------------ -->
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function (){
	//联想输入提示
	$("#txt_dwld").bindChange("${pageContext.request.contextPath}/suggest/getXx","R");
	
   	$("#more").bind('click', function (){
       	$(this).parents(".box-content").find(".row:not(:first):not(:nth-child(2))").toggleClass("hidden");
        $(this).toggleClass("dropup");
    });
   	//添加按钮
   	$("#btn_add").click(function(){
   		doOperate("${pageContext.request.contextPath}/webView/system/template/dwxxManagerTemplate.jsp");
   	});
   	//修改操作
   	$(document).on("click",".btn_upd",function(){
		var dwbh = $(this).parents("tr").find("[name='dwbh']").val();
		doOperate("${pageContext.request.contextPath}/dwxx/getDwxx?dwbh="+dwbh,"U");
	});
   	//单条删除操作
   	$(document).on("click",".btn_delxx",function(){
   		var dwbh = $(this).parents("tr").find("[name='dwbh']").val();
   		doDel("dwbh="+dwbh,"${pageContext.request.contextPath}/dwxx/deleteDwxx",function(val){
   			table.ajax.reload();
   			//....
   		},function(val){
   			//....
   		},"1");
   	});
   	//批量删除按钮
   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='dwbh']").filter(":checked");
   		if(checkbox.length>0){
   			var dwbh = [];
   			checkbox.each(function(){
   				dwbh.push($(this).val());
   			});
   			doDel("dwbh="+dwbh.join(","),"${pageContext.request.contextPath}/dwxx/deleteDwxx",function(val){
   	   			table.ajax.reload();
   	   			//....
   	   		},function(val){
   	   			//....
   	   		},dwbh.length);
   		}else{
   			alert("请选择至少一条信息删除");
   		}
   	});
	//批量赋值按钮
   	$("#btn_assig").click(function(){
   		var checkbox = $("#mydatatables").find("[name='dwbh']").filter(":checked");
   		if(checkbox.length>0){
   			var dwbh = [];
   			checkbox.each(function(){
   				dwbh.push($(this).val());
   			});
   			doPlfz(dwbh.length,"${pageContext.request.contextPath}/dwxx/findPlFuzhi?dwbh="+dwbh.join("','"),"600","400");
   		}else{
   			alert("请选择至少一条信息赋值");
   		}
   	});
   	//查看按钮
   	$("#btn_look").click(function(){
   		var checkbox = $("#mydatatables").find("[name='dwbh']").filter(":checked");
   		if(checkbox.length==1){
   			window.location.href = "${pageContext.request.contextPath}/dwxx/getDwxx?operateType=L&dwbh="+checkbox.val();
   		}else{
   			alert("只能选择一条信息查看");
   		}
   	});
   	//弹窗
   	$("#btn_dwld").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/window/ryxx/window.jsp?controlId=dwld","人员信息","920","630");
    });
   	//列表数据
   	var columns = [
		{"data": "DWBH",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="dwbh" value="' + data + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":30,"searchable": false,"class":"text-center"},
	   	{"data": "BMH",defaultContent:""},
	   	{"data": "MC",'render':function (data, type, full, meta){
		   	return '<a href="javascript:void(0);" class="btn btn-link">'+ data +'</a>';
		}},
	   	{"data": "JC",defaultContent:""},
	   	{"data": "DZ",defaultContent:""},
	   	{"data": "DWXZ",defaultContent:""},
	   	{"data": "JLRQ",defaultContent:""},
	   	{"data": "DWLD",defaultContent:""},
	   	{"data": "SJDW",defaultContent:""},
	   	{"data": "DWZT",defaultContent:"","render": function (data, type, full, meta){
	       	return '<input type="hidden" name="dwzt" value="' + data + '">' + data;
	    }},
	   	{"data":function (data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
		},orderable:false,"width":105}
	];
   	table = getDataTable("mydatatables","${pageContext.request.contextPath}/dwb/getPageList",[2,'asc'],columns);
    
    //综合查询
    $("#btn_search").on("click",function(){
		var json = searchJson("searchBox");
    	$('#mydatatables').DataTable().search(json,"json").draw();
    });
    $(".btn-mark-all").on("click",function(){
    	if($(this).hasClass("active")){
    		$(".btn-mark").each(function(){
       			$(this).removeClass("active");
    		});
   		} else {
   			$(".btn-mark").each(function(){
       			$(this).addClass("active");
    		});
   		}
    	return false;
    });
    $(".btn-mark:not(.btn-mark-all)").on("click",function(){
    	if($(this).hasClass("active")){
   			$(this).removeClass("active");
   		} else {
   			$(this).addClass("active");
   		}
    });
//  $('#button').click(function(){
//      table.row('.selected').remove().draw(false);
//  });
});
function resetForm(){
	$("#btn_search").click();
}


</script>
</body>
</html>
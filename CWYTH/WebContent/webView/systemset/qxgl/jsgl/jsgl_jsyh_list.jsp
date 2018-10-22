<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<base target="_blank" />
<title>角色管理-角色用户</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
ul{
  cursor:pointer;
  }
</style>
</head>
<body>
<!-- --------------------------------综合查询 box   开始------------------------------------------ -->
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
					    <span class="input-group-addon">人员工号</span>
			     		<input type="text" id="txt_rygh" class="form-control input-radius" name="rygh" value="" types="R"  table="G" placeholder="请输入人员工号">
						<span class="input-group-btn"><button type="button" id="btn_rygh" class="btn btn-link ">选择</button></span>
					</div>
				</div>
                <div class="col-md-3">
	            	<div class="input-group">
			            <span class="input-group-addon">人员姓名</span>
		                <input type="text" id="txt_ryxm" class="form-control" name="xm" table="G" placeholder="请输入人员姓名">
	                </div>
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
         </div>
        </form>
    </div>
</div>
<div class="box">
	<div class='box-content'>
		<div class="clearfix">
<!-- 			<div class="pull-left sub-title text-primary">用户信息</div> -->
                <div class="pull-left sub-title text-primary">
                   <ul class="nav nav-tabs" role="tablist">
			          <li role="presentation" id="jsxx"><a>角色信息</a></li>
			          <li role="presentation" class="active"><a id="btn_jsyh">角色用户</a></li>
			       </ul>
			   </div>
			<div class="btn-group pull-right" role="group">
				<button type='button' class="btn btn-default" id="btn_add"><i class='fa icon-add'></i>增加</button>
				<button type='button' class="btn btn-default" id="btn_del"><i class='fa icon-del'></i>批量删除</button>
			</div>
		</div>
		<hr class="hr-normal" />
		 <div class='responsive-table'>
           <div class='scrollable-area'>
                <table id="mydatatables" class="table table-striped table-bordered">
				    <thead>
				    <tr>
						<th><input type="checkbox" value="" id="select-all" /></th>
						<th>序号</th>
						<th>人员工号</th>
						<th>人员姓名</th>
						<th>单位编号</th>
						<th>单位名称</th>
						<th>角色编号</th>
						<th>角色名称</th>
						<th>操作</th>
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
	<script>
		$(function() {
			//列表数据
			var columns = [
			    {"data": "RYGH",orderable:false, "render": function (data, type, full, meta){
				 return '<input type="checkbox" class="keyId" name="rygh" value="' + data + '"/>';
				},"width":10,searchable: false},
				{"data":"_XH",orderable:false,"width":30,searchable: false,"class":"text-center"},
				{"data": "RYGH", defaultContent:""},
				{"data": "XM", defaultContent:""},
              /*   {"data": "XM","render":function (data, type, full, meta){
				   	return '<div><a href="javascript:void(0);" class="btn btn-look">'+ data +'</a></div>';
				}}, */
                {"data": "DWBH",defaultContent:""},
                {"data": "MC",defaultContent:""},
                {"data": "JSBH",defaultContent:""},
                {"data": "JSMC",defaultContent:""},
                {"data": function (data, type, full, meta){
                	return '<div><a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a></div>';
	        	},orderable:false,"width":100}
			];
			var _jsbh=""+${jsbh};
			table = getDataTable("mydatatables","${pageContext.request.contextPath}/jsxx/getRyxxPageList?jsbh="+_jsbh,[2,'asc'],columns);
			

			//联想输入提示
			$("#txt_rygh").bindChange("${pageContext.request.contextPath}/suggest/getXx","R");
			
			
			//添加按钮
			$("#btn_add").click(function() {
				select_commonWin("${pageContext.request.contextPath}/webView/window/callBackWindow/ryxx/window.jsp","人员信息", "920", "630");
			});
			
			//角色信息
		    $("#jsxx").click(function(){
		    	window.location.href = "${pageContext.request.contextPath}/webView/systemset/qxgl/jsgl/jsgl_list.jsp";
		   	});

			//单条删除操作
			$(document).on("click",".btn_delxx",
					function() {
						var rygh = $(this).parents("tr").find("[name='rygh']").val();
						doDel("rygh="+rygh,"${pageContext.request.contextPath}/jsxx/doDeleteRy",function(val){
				   			table.ajax.reload();
				   		},function(val){
		   			},"1");
			});
			//批量删除按钮
			$("#btn_del").click(function() {
				var checkbox = $("#mydatatables").find("[name='rygh']").filter(":checked");
		   		if(checkbox.length>0){
		   			var rygh = [];
		   			checkbox.each(function(){
		   				rygh.push($(this).val());
		   			});
		   			doDel("rygh="+rygh.join(","),"${pageContext.request.contextPath}/jsxx/doDeleteRy",function(val){
		   	   			table.ajax.reload();
		   	   			//....
		   	   		},function(val){
		   	   			//....
		   	   		},rygh.length);
		   		}else{
		   			alert("请选择至少一条信息删除");
		   		}
			});

			//弹窗
			$("#btn_rygh").click(function() {
				select_commonWin("${pageContext.request.contextPath}/webView/window/ryxx/window.jsp?controlId=txt_rygh","人员信息", "920", "630");
			});
			//综合查询
			$("#btn_search").on("click", function() {
				var json = searchJson("searchBox");
		    	$('#mydatatables').DataTable().search(json,"json").draw();
			});
		});
		 function callBack(val){
			 $.ajax({
					type:"post",
					url:"${pageContext.request.contextPath}/jsxx/doSaveRy",
					data:val,
					dataType:"json",
					success:function(val){
						close(index);
						if(val.success == 'true'){
							alert(val.msg);
							if(_success!=""&&_success!=""&&_success!=""){
								_success(val);
							}
						}else if(val.success == 'false'){
							alert(val.msg);
							if(_fail!=""&&_fail!=""&&_fail!=""){
								_fail(val);
							}
						}
					},
					error:function(val){
						close(index);
						alert(getPubErrorMsg());
					},
					beforeSend:function(val){
						index = loading(2);
					}
				});
		};
	</script>
</body>
</html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>角色管理</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.box {
		padding:0px;
		margin-left:0px;
		margin-right:0px;
		margin-top: 15px;
	}
	.zhxxcx .icon-chaxun {
	top:10px;
	}
</style>
</head>
<body style="background:white">
<!-- 角色信息 -->
 
<div class="box-content col-md-6" style="padding-right:5px;">
	<div class="box">
		<div class=" box-content">
	   		<!-- 列表操作按钮组-->
	   		
			<div class="clearfix">
				<div class="pull-left sub-title text-primary">角色信息</div>
				
				<div class="btn-group pull-right" role="group">
					<button type='button' class="btn btn-default" id="btn_addl">增加</button>
					<button type='button' class="btn btn-default" id="btn_qy_new">启用</button>
					<button type='button' class="btn btn-default" id="btn_jy_new">禁用</button>
					<button type='button' class="btn btn-default" id="btn_exp">导出Excel</button>
				</div>
			</div>
			<div class="search" id="searchBox">
    	<form id="myform" class="form-inline" role="form" action="">
    	<div class="search-simple">
				<div class="form-group">
					<label>角色名称</label>
					<input type="text" id="txt_jsmc" class="form-control input-radius" name="jsmc" table="D" placeholder="请输入角色名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search">查 询</button>
    	</div>
    	</form>
    	</div>
			<hr class="hr-normal" />
			<div class="responsive-table">
	          	<div class="scrollable-area">
		            <table id="mydatatables" class="table table-striped table-bordered">
					    <thead>
						    <tr>
								<th><input type="checkbox" class="select-all" /></th>
								<th>序号</th>
								<th>角色名称</th>
								<th>状态</th>
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
</div>
<!-- 角色用户 -->
<div class=" box-content col-md-6" >
	<input type="hidden" id="txt_jsbh">
	<div class="box">
		<div class='box-content'>
			<div class="clearfix">
				<div class="pull-left sub-title text-primary">用户角色信息&emsp;当前角色：
					<input type="text" readonly style ="border:0;color:red;font-weight:bolder" class="jsxm"  value="未选择">
				</div>
				<div class="btn-group pull-right" role="group">
					<button type='button' class="btn btn-default" id="btn_addr">增加</button>
					<button type='button' class="btn btn-default" id="btn_delr">批量删除</button>
					<button type="button" class="btn btn-default" id="btn_expr">导出Excel</button>
				</div>
			</div>
			<hr class="hr-normal" />
			<div class='responsive-table'>
	        	<div class='scrollable-area'>
	                <table id="jsyh" class="table table-striped table-bordered">
					    <thead>
						    <tr>
								<th><input type="checkbox"  id="select-all"/></th>
								<th>序号</th>
								<th>工号</th>
								<th>姓名</th>
								<th>单位名称</th>
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
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function() {
	$('#mydatatables').DataTable({
	    ajax: {
	        url: "${ctx}/jsxx/getPageList"
	      },
	        "lengthMenu":[20],
	        "order": [ 2, 'asc' ],
	        "serverSide": true,
	        "scrollXOption": true,
	        "scrollYOption": true,
	        "columns": [
                 {"data": "JSBH",orderable:false, "render": function (data, type, full, meta){
	                 return '<input type="checkbox" class="keyId" name="jsbh" jsmc = "'+full.JSMC+'" value="' + data + '" zt="'+full.ZT+'"/>';
	             },"width":10,searchable: false},
	             {"data":"_XH",orderable:false,"width":41,searchable: false,"class":"text-center"},
                 {"data": "JSMC","render":function (data, type, full, meta){
	   	             return '<div><a href="javascript:void(0);" class="btn btn-lookl btn-link">'+ data +'</a></div>';
	             }},
                 {"data": "ZT",defaultContent:""},
                 {"data":"JSBH","render": function (data, type, full, meta){
                if (full.JSBH =="00"){
		            return '<div><a href="javascript:void(0);" class="btn btn-link btn_czxz">操作权限</a></div>';
	            }else if(full.JSBH =="01"||full.JSBH =="02"||full.JSBH =="03"){
   		            return '<div><a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_czxz">操作权限</a></div>';
	            }else{
   		            return '<div><a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxxl">删除</a>|<a href="javascript:void(0);" class="btn btn-link btn_czxz">操作权限</a></div>';
	            }
	            },orderable:false,"width":160}	 
	        ],
	        "language":{
// 	            "search":"",
// 	            "searchPlaceholder":"请输入角色编号或角色名称"
	        },
	        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>",
	        "initComplete": function(){
	        }
	    });
    $('#jsyh').DataTable({
	    ajax: {
	        url: "${ctx}/jsxx/getRyxxPageList?jsbh="+$("#txt_jsbh").val()
	      },
	        "lengthMenu":[40],
	        "order": [ 2, 'asc' ],
	        "serverSide": true,
	        "scrollXOption": true,
	        "scrollYOption": true,
	        "columns": [
                  {"data": "RYBH",orderable:false, "render": function (data, type, full, meta){
				 return '<input type="checkbox" class="keyIdNew" name="rybh" value="' + data + '" />';
				},"width":10,searchable: false},
				{"data":"_XH",orderable:false,"width":41,searchable: false,"class":"text-center"},
                {"data": "RYGH",defaultContent:""},
            	{"data": "XM",'render':function(data,type,full,meta){
            		return '<a href="javascript:void(0);" id="look" class="btn btn-link rylook" rybh="'+(full.RYBH==undefined? '': full.RYBH)+'" path="${ctx}">'+ (data==undefined ? '':data) +'</a>';
                }},
                {"data": "DWMC","render":function (data, type, full, meta){
        	    	return '<a href="javascript:void(0);" class="btn btn-link dwlook" dwbh="'+(full.DWBH==undefined? '': full.DWBH)+'" path="${ctx}">'+ (data==undefined ? '':data) +'</a>';
        		},orderable:false},
                {"data": function (data, type, full, meta){
                	return '<div><a href="javascript:void(0);" class="btn btn-link btn_delxxr">取消授权</a></div>';
	        	},orderable:false,"width":60} 
	        ],
	        "language":{
	            "search":"",
	            "searchPlaceholder":"请输入人员工号或人员姓名 "
	        },
	        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>",
	        "initComplete": function(){
	        }
	    });
    $("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
	$(".icon-chaxun").css("position","absolute");
	//点击角色信息的行事件
	$(document).on('click','#mydatatables tr',function(){
		$(this).siblings().find(":checkbox").filter(":checked").attr("checked",false);
		var jsbh = $(this).find(":checkbox").filter(":checked").val();
		var jsmc = $(this).find(":checkbox").filter(":checked").attr("jsmc");
		if(undefined != jsbh){
			$('#jsyh').DataTable().search("",jsbh).draw();
			$("#txt_jsbh").val(jsbh);
			$(".jsxm").val(jsmc);
		}else {
			$('#jsyh').DataTable().search("","json").draw();
			$(".jsxm").val("未选择");
		}
    });
	//导出Excel（角色信息）
	$(document).on("click","#btn_exp",function(){
   		var json = $("#mydatatables_filter").find("input[type=search]").val();
   		var checkbox = $("#mydatatables").find("[name='jsbh']").filter(":checked");
		var id = [];
		checkbox.each(function(){
			id.push($(this).val());
		});
   		doExp(json,"${ctx}/jsxx/expExcel","角色信息","${ctx}",id.join(","));
   	});
  	//导出Excel（角色用户）
//    	$("#btn_expr").click(function(){
//    		var json = $("#jsyh_filter").find("input[type=search]").val();
//    		var checkbox = $("#jsyh").find("[name='rybh']").filter(":checked");
//    	    var jsbh = $("#txt_jsbh").val();
// 		var id = [];
// 		checkbox.each(function(){
// 			id.push($(this).val());
// 		});
//    		doExp(json+"&jsbh="+jsbh,"${ctx}/jsxx/expExcelr","角色用户","${ctx}",id.join(","));
//    	});
  	
  //导出Excel
  $(document).on("click","#btn_expr",function(){
   		var id = [];
   		var checkbox = $("#jsyh").find("[name='rybh']").filter(":checked");
   		var jsbh = $("#txt_jsbh").val();
   		if(checkbox.length > 0){
			checkbox.each(function(){
				id.push($(this).val());
			});
			id = id.join("','");
   		}else{
   			id = "";
   		}
   		doExp("","${ctx}/jsxx/expExcel2?jsbh="+jsbh,"角色人员信息","${pageContext.request.contextPath}",id);
   		
   		
//    		var checkbox = $("#jsyh").find("[name='rybh']").filter(":checked");
//    		var jsbh = $("#txt_jsbh").val();
// 				if (checkbox.length > 0) {
// 					var guid = [];
// 					checkbox.each(function() {guid.push($(this).val());});
// 					$.ajax({
// 						type : "post",
// 						data : {guid:guid.join(","),jsbh:jsbh},
// 						url : "${ctx}/jsxx/expExcel2",
// 						success : function(val) {
// 						   FileDownload("${ctx}/file/fileDownload","角色人员信息.xls",val.url);
// 						}
// 					});
// 				} else {
// 					alert("请至少选择一条信息导出!");
// 				}
			});
  	
	//添加按钮：角色信息
	$(document).on("click","#btn_addl",function(){
		select_commonWin("${ctx}/jsxx/getJsxx?operateType=C","角色信息", "400", "450");
	});
	//添加按钮：角色用户
	$(document).on("click","#btn_addr",function(){
		var jsxx = $("table").eq(0).find(":checkbox").filter(":checked");
		var zt = jsxx.attr("zt");
		if(zt=="禁用"){
			alert("所选角色已经禁用！");
			return;
		}
		if(jsxx.length == 0){
			alert("请先选择角色信息！");
		}else if(jsxx.length > 1){
			alert("只能选择一种角色信息！");
		}else{
			var jsbh = jsxx.val();
			if(jsbh =="00"){
				alert("系统默认角色不能添加角色用户！");
			}else{
				select_commonWin("${ctx}/jsxx/doAdd?jsbh="+jsbh+" ","人员信息", "920", "630");
			}
		}				
	});
	//查看
   	$(document).on("click",".btn-lookl",function(){
   		var jsbh = $(this).parents("tr").find("[name='jsbh']").val();
   		select_commonWin("${ctx}/jsxx/getJsxx?operateType=L&jsbh="+jsbh,"角色信息", "400", "450");
   	});
	//单条删除操作：角色信息
	$(document).on("click",".btn_delxxl",function() {
		var jsbh = $(this).parents("tr").find("[name='jsbh']").val();
		console.log("___"+jsbh);
		 $.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/jsxx/getNewStatus",
			data:"sjdd="+jsbh,
			success:function(val){
				var data = JSON.getJson(val);
				 if(data.success=='true'){
					 if(jsbh !="00"){
							doDel("jsbh="+jsbh,"${ctx}/jsxx/doDelete",function(val){
								$('#mydatatables').DataTable().ajax.reload();
								$('#jsyh').DataTable().ajax.reload();
						   		},function(val){},"1");
						}else{
				   			alert("系统默认角色不能删除！");
				   		}
				 }else{
						alert("已经启用的角色不能删除！");
				 }
			},
		});  
			
			
			
	});
	//单条删除操作：角色用户
	$(document).on("click",".btn_delxxr",function() {
		var rybh = $(this).parents("tr").find("[name='rybh']").val();
		var jsbh =$("#txt_jsbh").val();
		doDelQxsq("rybh="+rybh+"&jsbh="+jsbh,"${ctx}/jsxx/doDeleteRy",function(val){
			$('#jsyh').DataTable().ajax.reload();
   		},function(val){},"1");
	});
	//批量删除按钮：角色用户
	$(document).on("click","#btn_delr",function() {
		var checkbox = $("#jsyh").find("[name='rybh']").filter(":checked");
		var jsbh =$("#txt_jsbh").val();
   		if(checkbox.length>0){
   			var rybh = [];		   			
   			checkbox.each(function(){
   				rybh.push($(this).val());
   			});
   			doDelQxsq("rybh="+rybh.join(",")+"&jsbh="+jsbh,"${ctx}/jsxx/doDeleteRy",function(val){
   				$('#jsyh').DataTable().ajax.reload();
   	   			//....
   	   		},function(val){
   	   			//....
   	   		},rybh.length);
   		}else{
   			alert("请选择至少一条信息删除!");
   		}
	});
	//修改操作
	$(document).on("click",".btn_upd",function() {
		var jsbh = $(this).parents("tr").find("[name='jsbh']").val();
		if(jsbh !="00"){
			select_commonWin("${ctx}/jsxx/getJsxx?operateType=U&jsbh="+jsbh,"角色信息", "400", "450");
		}else{
			alert("系统默认角色不能修改！");
		}
	});
	//操作权限
	$(document).on("click",".btn_czxz",function(){
		var jsbh = $(this).parents("tr").find("[name='jsbh']").val();
		select_commonWin("${ctx}/jsxx/findJsCzqxszList?jsbh="+jsbh,"操作权限设置", "1100", "710");
	});
	//综合查询--角色信息		
	$("#zhcx_1").blur(function(){
		$('#mydatatables').DataTable().search($(this).val(),"json").draw();
	});
	//综合查询--角色用户		
	$("#zhcx_2").blur(function(){
		var jsbh = $("#txt_jsbh").val();
		$('#jsyh').DataTable().search($(this).val(),jsbh).draw();
	});
});
function doDelQxsq(_data,_url,_success,_fail,_length,xx){
	var index;
	if(xx == undefined || xx == ""){
		xx = "确认要取消该人员的授权吗？";
		if(_length!="1"&&_length!="0"){
			xx = "确认要取消这"+_length+"位人员的授权吗？";
		}
	}
	confirm(xx,{title:"提示"},function(){
		 $.ajax({
			type:"post",
			data:_data,
			url:_url,
			success:function(val){
				close(index);
				var data = JSON.getJson(val);
				if(data.success){
					alert(data.msg);
					if(_success!=""&&_success!=undefined&&_success!=null){
						_success(data);
					}
				}else
				{
					alert(data.msg);
					if(_fail!=""&&_fail!=""&&_fail!=""){
						_fail(data);
					}
				}
			},
			error:function(){
				close(index);
				alert(getPubErrorMsg());
			},
			beforeSend:function(){
				index = loading(2);
			}
		});
	}); 
}
//启用禁用
$(document).on("click","[id$=_new]",function(){
	var checkbox = $("#mydatatables").find("[name='jsbh']").filter(":checked");
	var id = $(this).attr("id");
	var msg = "";
	if(id=="btn_qy_new"){
		msg = "确定启用所选择的用户？";
	}else{
		msg = "确定禁用所选择的用户？";
	}
	if(checkbox.length==0){
		alert("请至少选择一条信息进行操作！");
		return;
	}else{
		confirm(msg,{title:"提示信息"},function(){
			var jsbh = [];
   			checkbox.each(function(){
   				jsbh.push($(this).val());
   			});
			$.ajax({
				url:"${ctx}/jsxx/qyjy",
				data:"jsbh="+jsbh.join(",")+"&type="+id,
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.success){
						alert("操作成功！");
						location.reload(); 
					}else{
						alert("操作失败！");
					}
				}
			});
		});
	}
});
//角色信息全选
$(document).on("click","[id=select-all]",function(){
	if($(this).prop("checked")){
		$(":checkbox.keyIdNew").prop("checked", true);
		$(":checkbox.keyIdNew").parents("tr").addClass('selected');
	}else{
		$(":checkbox.keyIdNew").prop("checked", false);
		$(":checkbox.keyIdNew").parents("tr").removeClass('selected');
	}
	//类似于资产闲置申请中选择资产的时候，右下角有个蓝色小圆圈，选中列表上的复选框的时候，要把该资产放到蓝色小圆圈里，
	//所以需要执行一下复选框的change事件，这个事件在页面上，如果没有change事件，写在这儿也不受影响
	$(":checkbox.keyId").change();
});
</script>
</body>
</html>
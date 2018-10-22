<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
<style>
.null {
	background-color: wheat;
}
.bk{
		border:none;
		width:100%;
	    padding:4px !important;
	} 
</style>
</head>
<body>
<% 
SimpleDateFormat sbnd = new SimpleDateFormat("yyyy");
String date = sbnd.format(new Date());
%>
	<div class="fullscreen">
		<div class="search" id="searchBox">
			<form id="myform" class="form-inline" role="form" action="">
				<div class="search-simple">
					<div class="form-group">
						<label>部门编号</label> <input type="text" id="txt_bmbh"
							class="form-control" name="tbbm" table="K" placeholder="请输入部门编号">
					</div>
					<div class="form-group">
						<label>部门名称</label> <input type="text" id="txt_bmmc"
							class="form-control" name="dwmc" table="K" placeholder="请输入部门名称">
					</div>
					<button type="button" class="btn btn-primary" id="btn_search">
						<i class="fa icon-chaxun"></i>查询
					</button>
					<div class="btn-group pull-right" role="group">
						<button type="button" class="btn btn-default" id="btn_save">保存</button>
						<button type="button" class="btn btn-default" id="btn_exp">导出Excel</button>
					</div>
				</div>
			</form>
		</div>
		<div class="container-fluid">
			<div class='responsive-table'>
				<div class='scrollable-area'>
				<form id="myform1" class="add">
			    
					<table id="mydatatables" class="table table-striped table-bordered">
						<thead>
							<tr>
								<th style="text-align: center;"><input type="checkbox" class="select-all" /></th>
								<th class=" text-center">序号</th>
								<th class=" text-center">部门编号</th>
								<th class=" text-center">部门名称</th>
								<th class=" text-center">项目名称</th>
								<th class=" text-center">经费类型</th>
								<th class=" text-center">申报年度</th>
								<th class=" text-center">预算金额（万元）</th>
								<th class=" text-center"><span class="required"></span>建议金额（万元）</th>
							</tr>
						</thead>
						
						<tbody id="bod">
							
						</tbody>
						
					</table>
				
					</form>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/static/include/public-list-js.inc"%>
	<script>
		$(function() {
			//联想输入提示
			$("#btn_bmmc").bindChange("${ctx}/suggest/getXx", "D");
			//列表数据
			var columns = [

			   	   {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
			         return  '<input type="checkbox" name="xmid" class="keyId" value="' + full.XMID + '" >'+
			        		 '<input type="hidden" name="jflx" value="' + full.JFLX + '" >'+
			        		 '<input type="hidden" name="nd" value="' + full.ND + '" >'+
			        		 '<input type="hidden" name="tbbm" value="' + full.TBBM + '" >'+
			        		 '<input type="hidden" name="zcyshz" value="' + full.MONEY + '" >';
			       },"width":10,'searchable': false},

			       {"data":"_XH",orderable:false,'render': function(data,type,full,meta){
			    		return  '<input type="hidden"  class="uid"  id="' + data + '">'+data+'';
			    		},"width":41,"searchable":false,"class":"text-center"},

			       {"data": "TBBM",defaultContent:""},
			       
			       {"data": "DWMC",defaultContent:""},
			       {"data": "XMMC",defaultContent:""},
			       {"data": "JFLX",defaultContent:""},
			       {"data": "ND",defaultContent:"","class":"text-center"},
			       
			       {"data": "MONEY",defaultContent:"0.0000","class":"text-right"},
			     
			       
			       {"data": "JYJE","class":"text-center",'render':function(data, type, full, meta){
			    	   if(full.JYJE==""||full.JYJE=="undefined"||full.JYJE==null){
				   		return '<input type="textarea" name="jyje" class="number1 bk" style="width:100%;border:none;text-align:right;" value="" />';
			    	   }else{
			    	    return '<input type="textarea" name="jyje" class="number1 bk" style="width:100%;border:none;text-align:right;" value="'+data+'" />';   
			    	   }
			       },orderable:false,"width":220,"class":"text-right"},
			       
			];
 			table = getDataTableByListHj("mydatatables","${ctx}/bmysxd/getPageLists?treeid=${param.dwbh}", [2, 'asc,xmid' ], columns, 0, 1, setGroup);
			//保存按钮
			$("#btn_save").click(function() {
				var tr = $("#bod").find("tr");
				var params = [];
				var param = "";
				$.each(tr,function(){
					var $this = $(this);
					var jflx = $this.find("[name='jflx']").val();
					var nd = $this.find("[name='nd']").val();
					var xmid = $this.find("[name='xmid']").val();
					var jyje = $this.find("[name='jyje']").val();
					var tbbm = $this.find("[name='tbbm']").val();
					var zcyshz = $this.find("[name='zcyshz']").val();
					param = check(tbbm)+"="+check(nd)+"="+check(xmid)+"="+check(jflx)+"="+check(zcyshz)+"="+check(jyje);
					params.push(param);
				});
				if(params.length==0){
					alert("无可下达数据！");
					return;
				}
				$.ajax({
					url:"${ctx}/bmysxd/doSaveXd",
					data:"params="+params,
					type:"post",
					dataType:"json",
					success:function(result){
						if(result){
							alert("操作成功！");
						}else{
							alert("操作失败！");
						}
					}
				});
			});
			$(document).on("blur", ".number1", function(e){
				$(this).Num(4,true,false,e);
				return false;
			});
			function check(param){
				if(param==""||param==null||param=="null"||param=="undefined"){
					return "@";
				}
				return param;
			}
			//导出Excel
			$("#btn_exp").click(function(){
		   		var json = searchJson("searchBox");
		   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");  
		   		
				var guid = [];
				checkbox.each(function(){
				    guid.push($(this).val());
				});
		   		doExp(json,"${ctx}/bmysxd/expExcel?treeBmbh=${param.dwbh}","部门预算下达信息","${ctx}",guid.join("','"));
		   	});


		});

		$(function() {
			//列表右侧悬浮按钮
			$(window).resize(
					function() {
						$("div.dataTables_wrapper").width(
								$("#searchBox").width());
						heights = $(window).outerHeight()
								- $(".search").outerHeight()
								- $(".row.bottom").outerHeight() - 20
								- $(".dataTables_scrollHead").outerHeight();
						$(".dataTables_scrollBody").height(heights);
						table.draw();
					});
		});
		
		
		
	</script>
</body>
</html>
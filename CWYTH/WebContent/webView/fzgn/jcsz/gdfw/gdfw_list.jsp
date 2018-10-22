<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>归档范围分类</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
	<div class="fullscreen">
	<div class="search" id="searchBox">
		<form id="myform" class="form-inline" role="form" action="" style="padding-top: 8px;padding-bottom: 2px">
	
    		<div class="search-simple">
				<div class="form-group ">
					<label>分类名称</label>
					<input type="text"  class="form-control " name="typename" table="k" placeholder="请输入分类名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search">
						<i class="fa icon-chaxun"></i>查 询
				</button>
				<div class="btn-group pull-right"role="group" >
		             <button type="button" class="btn btn-default" id="btn_queding">确定</button>
	           </div>
			</div>
			
			
        </form>
	</div>
		<div class="container-fluid">
        <div class='responsive-table'>
            <div class='scrollable-area'>
                 <table id="mydatatables" class="table table-striped table-bordered">
				    <thead>
				    <tr>
					<th><input type="checkbox" class="select-all" />全选</th>
					<th>序号</th>
					<th>分类名称</th>
					<th>分类编号</th>
					<th>保管期限</th>
					<th>归档期限</th>
					<th>创建时间</th>
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
		$(function() {
			//列表数据
			var columns = [
					{"data" : "TYPEID",orderable : false,"render" : function(data, type, full, meta) {
							return '<input type="checkbox" class="keyId" name="id" value="' + data + '">';
					},"width" : 10,'searchable' : false},
					{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
						return data;
					},"width":41,"searchable": false,"class":"text-center"},
					{"data" : "TYPENAME","render":function (data, type, full, meta){
						var maxlength=30;
						if(data.length>=maxlength){
							return '<div class="change"><span title="'+data+'">'+ data.substr(0,maxlength-3) +'...</span></div>';
						}else{
							return '<div class="change"><span title="'+data+'">'+ data +'</span></div>';
						}
				    },"width":300},
					{"data" : "TYPENUMBER",defaultContent : "","width":150},
					{"data" : "SAVEIDS",defaultContent : "","width":150},
					{"data" : "GDTIME",defaultContent : "","width":150},
					{"data" : "CREATETIME",defaultContent : "","width":150}
					 ];
			table = getDataTableByListHj("mydatatables",
					"${pageContext.request.contextPath}/dwb/getGdflPageList?treeId=${treeId}", [ 4,'desc' ], columns);
		});
		 //确定按钮
		$("#btn_queding").click(function(){
			var checkbox = $("#mydatatables").find("[name='id']").filter(":checked");			
			if(checkbox.length>1){
				alert("只能选择一个分类");
			}else{
				var id = $(checkbox).val();
				var gdfl = $(checkbox).parent().next().next().text();
				
	        	getIframeControl("${pname}","${controlName}").val(id);
	        	getIframeControl("${pname}","${controlName}").focus();//手动触发验证
	        	getIframeControl("${pname}","${controlName}").trigger("blur");//手动触发验证
	        	
	        	getIframeControl("${pname}","${param.controlId}").val(gdfl);
	        	getIframeControl("${pname}","${param.controlId}").focus();//手动触发验证
	        	getIframeControl("${pname}","${param.controlId}").trigger("blur");//手动触发验证
	        	
	        	var winId = top.layer.getFrameIndex(parent.window.name);;
				close(winId);
				//table.ajax.reload();
			}
		});
		
		
	</script>
</body>
</html>
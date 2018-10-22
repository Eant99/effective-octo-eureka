<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>会计科目设置</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	select{
		font-size:12px !important; 
		max-height: 25px !important;  
		padding: 0px !important;
		padding-left: 5px !important;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="" style="padding-top:8px">
    		<div class="search-simple">
				<div class="form-group">
					<label>科目名称</label>
					<input type="text" class="form-control input-radius" name="kmmc"  table="k" placeholder="请输入科目名称">
				</div>
				<div class="form-group">
					<label>科目编号</label>
					<input type="text" id="txt_xm" class="form-control input-radius" name="kmbh"  table="k" placeholder="请输入科目编号">
				</div>
				<div class="form-group">
				  
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div class="btn-group pull-right" role="group">
	                <button class="btn btn-default" type="button" id="btn_queding">确定</button>
            	</div>
			</div>
        </form>
    </div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'>
		    	<input type="hidden" name="guid" value="${guid}"/>
		    
		        <table id="mydatatables" class="table table-striped table-bordered">
					<thead>
		 				<tr>
			 				<th><input type="checkbox" class="select-all"/></th>
			 				<th>序号</th>
						    <th>科目编号</th>
						    <th>科目名称</th>
						    <th>科目属性</th>
						    <th>助记符</th>
						    <th>余额方向</th>
						    <th>核算类别</th>
						    <th>是否启用</th>
						   
						    <th>科目级次</th>
						  
			   			</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
	</div>
</div>
 <%@include file="/static/include/public-list-js.inc"%> 
<%--  <script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script>  --%>
<script >

$(function(){
	var yefx1="${yefx}";
	var kmsx1="${kmsx}";
	var bz ="${param.bz}";
// 	alert(bz);
// 	return;
	var columns = [
	       		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
	       	       	return '<input type="checkbox" kmmc="'+full.KMMC+'"  kmbh= "'+full.KMBH+'" class="keyId" name="guid" value="' + data + '">';
	       	    },"width":10,'searchable': false},
	       		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	       	   		return data;
	       		},"width":41,"searchable": false,"class":"text-center"},
	       	    {"data": "KMBH","render":function (data, type, full, meta){
	 		     	return '<div><a href="javascript:void(0);" class="btn btn_look btn-link" guid = "'+full.GUID+'" sfkjzd="'+full.SFKJZD+'" kmjc="'+full.KMJC+'" dwbh = "'+full.GUID+'">'+ data +'</a></div>';
	 	        }},
	       	   	{"data": "KMMC",defaultContent:"","class":"text-left"},
	       	   	{"data": "KMSXMC",defaultContent:""},
	       	   	{"data": "ZJF",defaultContent:""},
	       	   	{"data": "YEFXMC",defaultContent:"","class":"text-center",},
	       	   	{"data": "HSLBMC",defaultContent:""},
	       	   	{"data": "QYFMC",defaultContent:"","class":"text-center",},
	       	   	{"data": "KMJC",defaultContent:"","class":"text-center",},
	       	   
	       	];
	    if(bz == "yhck"){
	    	table = getDataTableByListHj("mydatatables","${ctx}/yhckrjz/getUnderPageList",[2,'asc'],columns,0,1,setGroup);
	    }else if(bz == "kcxj"){
	    	table = getDataTableByListHj("mydatatables","${ctx}/kcxjrjz/getUnderPageList",[2,'asc'],columns,0,1,setGroup);
	    }else{
	    	table = getDataTableByListHj("mydatatables","${ctx}/lyerjz/getUnderPageList",[2,'asc'],columns,0,1,setGroup);
	    }   	
          
         //双击 事件
         var winId = getTopFrame().layer.getFrameIndex(window.name);
       
         
          $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
          	var kmbh = $(this).find("[name='guid']").attr("kmbh");//科目编号
          	var kmmc = $(this).find("[name='guid']").attr("kmmc");
          	var  val = '('+kmbh+')'+kmmc;
          	getIframeControl("${param.pname}","${param.controlId}").val(val);
            getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
            close(winId);
          });
      //确定 事件
          $(document).on("click","#btn_queding",function(){  
        	  var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
        	  var val = [];
        	  if(checkbox.length>0){
        		  checkbox.each(function(){
	        		  var kmbh = $(this).attr("kmbh");//科目编号
	                  var kmmc = $(this).attr("kmmc");
	                  var  bhmc = '('+kmbh+')'+kmmc;
	                  val.push(bhmc);
        		  });
        	  }
        		getIframeControl("${param.pname}","${param.controlId}").val(val);
                getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
            	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
            	
                close(winId);
          });
});
</script>
</body>
</html>
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
<section>
	<div class="container-fluid">
		<div class="alert alert-info">
          	<strong>提示：</strong>先找到需要收支结余的科目，然后<strong>双击</strong>这条信息。
        </div>
        <hr class="hr-normal" id="hr">
		<div class='responsive-table'>
			<div class='scrollable-area'>
		    	<input type="hidden" name="guid" value="${guid}"/>
		        <table id="mydatatables" class="table table-striped table-bordered">
					<thead>
		 				<tr>
			 				<th><input type="checkbox" class="select-all"/></th>
			 				<th>序号</th>
						    <th>科目编号</th>
						    <th>科目年度</th>
						    <th>科目名称</th>
						    <th>总科目属性代码</th>
						    <th>科目级代码</th>
						    <th>科目类别代码</th>
						    <th>科目封存</th>
						    <th>助记符</th>
						    <th>余额的方向</th>
			   			</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
	</div>
	</section>
</div>
 <%@include file="/static/include/public-list-js.inc"%>  
<script >
$(function(){
	var winId = getTopFrame().layer.getFrameIndex(parent.window.name);
	var columns = [
		{"data": "guid",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="guid" bz="'+full.BZS+'" value="' + "("+full.FYFL+")"+full.KMMC + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
	   	{"data": "FYFL",defaultContent:""},
	   	{"data": "KMND",defaultContent:"","class":"text-center"},
	   	{"data": "KMMC",defaultContent:""},
	   	{"data": "ZKMSXDM",defaultContent:""},
	   	{"data": "KMJDM",defaultContent:""},
	   	{"data": "KMLBDM",defaultContent:""},
	   	{"data": "KMFC",defaultContent:""},
	   	{"data": "ZJF",defaultContent:""}, 
	   	{"data": "YEFX",defaultContent:""},
	];
	
   	table = getDataTableByListHj("mydatatables","${ctx}/kmsz/getPageList?treeDm=${param.dm}&treesearch=${treesearch}",[2,'asc'],columns,0,1,setGroup);
   //table = getDataTableByListHj("mydatatables","${ctx}/json/kjhs/kmsz/kjkmsz/kjkmsz_list.json",[2,'asc'],columns,0,1,setGroup);

  //双击事件
    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	
    	var val = $(this).find("[name='guid']").val();
    	console.log("_______"+val);
    	if(val==''||val==null||val=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		getIframeControl("${param.pname}","${param.controlId}").val(val);
        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
        	close(winId);
    	}
    });
    if("${param.windowModel}" == "1"){
	   	$("div.button").prop("innerHTML","<button type='button' id='btn_sure' class='btn btn-primary' style='margin-right:20px;'>确定</button><button type='button' id='btn_cancel' class='btn btn-primary'>取消</button>");
	}else{
    	$("div.button").prop("innerHTML","单位信息列表").css("font-size","14px");
    }
	
});

</script>
</body>
</html>
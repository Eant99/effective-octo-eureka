<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>人员论文情况</title>
<%@include file="/static/include/public-list-css.inc"%>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body style="background-color: white;">
	<form class="addLwqk" class="form-horizontal" id="lwqkform" action="" method="post">	        
 		<input type="hidden" name="operateType" id="operateType" value="${operateType}">
		<input type="hidden" name="rybh" id="txt_rybh" value="${rybh}">
		<input type="hidden" name="guid" id="txt_guid" value="${guid}">
   		<div class="container-fluid dialog-body">
   			<div class="row">
      			<div class="col-sm-12">
					<div class="input-group">
            			<span class="input-group-addon">发表日期</span>
                  		<input type="text" id="txt_rq" class="form-control date" name="rq" value="<fmt:formatDate value="${rylwb.rq}" pattern="yyyy-MM-dd"/>"  data-format="yyyy-MM-dd" placeholder="发表日期">
                   		<span class='input-group-addon'>
                       		<i class="glyphicon glyphicon-calendar"></i>
                   		</span>
             		</div>	
 				</div>
 			</div>
			<div class="row">
				<div class="col-sm-12">
					<div class="input-group">
						<!-- 带有必填项的label元素 -->
						<span class="input-group-addon">论文题目</span>
						<input type="text" id="txt_lwtm" class="form-control input-radius" name="lwtm" maxlength="60" value="${rylwb.lwtm}" placeholder="最多输入60个字符"/>	
  					</div>	
				</div>
   			</div>
   			<div class="row">
      			<div class="col-sm-12">
					<div class="input-group">
						<!-- 带有必填项的label元素 -->
						<span class="input-group-addon">论文级别</span>
						<select id="txt_lwjb" class="form-control input-radius" name="lwjb">
                 			<c:forEach var="lwjbList" items="${lwjbList}">
               					<option value="${lwjbList.DM}" <c:if test="${rylwb.lwjb == lwjbList.DM}">selected</c:if>>${lwjbList.MC}</option>
               				</c:forEach>
                     	</select>
		   			</div>	
   				</div>
 			</div>
			<div class="row">
	  			<div class="col-sm-12">
					<div class="input-group">
						<!-- 带有必填项的label元素 -->
						<span class="input-group-addon">所发刊物</span>
						<input type="text" id="txt_sfkw" class="form-control input-radius" name="sfkw" maxlength="100" value="${rylwb.sfkw}" placeholder="最多输入100个字符"/>
				   	</div>	
		   		</div>
			</div>
		</div>
      	<div class='page-bottom clearfix'>
        	<div class="pull-right">
	        	<button type="button" class="btn btn-default" id="btn_save"><i class='fa icon-save'></i>保存</button>
				<button type="button" class="btn btn-default btn-without-icon" id="btn_back">返回</button>
        	</div>
    	</div>
	</form>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function(){
  	//人员列表信息
    $("#tab_ryxx").click(function(){
    	window.location.href = "${ctx}/ryb/goRybListPage";
   	});
    //外语水平
    $("#tab_wysp").click(function(){
    	window.location.href ="${ctx}/rywyspb/goWyspListPage?rybh=${rybh}";
   	});
    //进修情况
    $("#tab_jxqk").click(function(){
    	window.location.href ="${ctx}/ryjxb/goJxqkListPage?rybh=${rybh}";
   	});
    //著作情况
    $("#tab_zzqk").click(function(){
    	window.location.href ="${ctx}/ryzzb/goZzqkListPage?rybh=${rybh}";
   	});
    //成果奖励
    $("#tab_cgjl").click(function(){
    	window.location.href ="${ctx}/rycgjlb/getCgjlListPage?rybh=${rybh}";
   	});
	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
	   	doOperate("${ctx}/rylwb/goRylwbPage?guid="+guid+"&rybh=${rybh}","U");
   	});
   	//单个删除
   	$(document).on("click",".btn_delxx",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		doDel("guid="+guid,"${ctx}/rylwb/doDelete",function(val){
   			table.ajax.reload();
   			//....
   		},function(val){
   			//....
   		},"1");
   	});
   	//批量删除按钮
   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   			var guid = [];
   			checkbox.each(function(){
   				guid.push($(this).val());
   			});
   			doDel("guid="+guid.join(","),"${ctx}/rylwb/doDelete",function(val){
   	   			table.ajax.reload();
   	   			//....
   	   		},function(val){
   	   			//....
   	   		},guid.length);
   		}else{
   			alert("请选择至少一条信息删除");
   		}
   	});
    //列表数据
   	var columns = [
		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="guid" value="' + data + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
	   	{"data": "RQ",defaultContent:""},
	   	{"data": "LWTM",defaultContent:""},
	   	{"data": "LWJB",defaultContent:""},
	   	{"data": "SFKW",defaultContent:""},
	   	{"data":function (data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
		},orderable:false,"width":105}
	];
 	table = getDataTable("mydatatables","${ctx}/rylwb/getLwqkList?rybh=${rybh}",[4,'asc'],columns);
    //综合查询
    $("#btn_search").on("click",function(){
		var json = searchJson("searchBox");
    	$('#mydatatables').DataTable().search(json,"json").draw();
    });
  //验证方法
	var validate = $('#lwqkform').bootstrapValidator({fields:{
			lwtm:{validators:{stringLength : {max : 60,message : '最多输入60个字符'},}},
			sfkw:{validators:{stringLength : {max : 60,message : '最多输入100个字符'},}}}
	      });
	//保存按钮
	$("#btn_save").click(function(e){
		doSave(validate,"lwqkform","${ctx}/rylwb/doSave",
		function(val){//成功
			$("#operateType").val("U");
			getIframWindow("${param.pname}").table.ajax.reload();
 			var winId = getTopFrame().layer.getFrameIndex(window.name);
 	    	close(winId);
		},function(val){//失败
			
		});
	});
	//返回按钮
	$("#btn_back").click(function(){
		getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});
});
function reloadList(){
	table.ajax.reload();
}
</script>
</body>
</html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<title>预约详细设置</title>
<%@include file="/static/include/public-manager-css.inc"%>
<%@include file="/static/include/public-list-css.inc"%>
<%@include file="/static/include/public-manager-js.inc"%>
<%@include file="/static/include/public-list-js.inc"%>
<script>
	function callTime(){
		 var date1=$("#txt_kssj").val();
	        var date2=$("#txt_jssj").val();
	        var dstart=new Date(date1);
	        var dend=new Date(date2);    
	        var limidate= dend.getTime()-dstart.getTime();
	      //计算出相差天数
	        var days=Math.floor(limidate/(24*3600*1000))
	        var leave1=limidate%(24*3600*1000)    //计算天数后剩余的毫秒数
	        var hours=Math.floor(leave1/(3600*1000))
	        //计算相差分钟数
	        var leave2=leave1%(3600*1000)        //计算小时数后剩余的毫秒数
	        var minutes=Math.floor(leave2/(60*1000))
	        if(limidate>0){
	        	if(days !=0){
	  	    	  $("#txt_yysc").val(days+"天"+hours+"小时"+minutes+"分钟");
	  	      }else if(hours !=0){
	  	    	  $("#txt_yysc").val(hours+"小时"+minutes+"分钟");
	  	      }else if(minutes !=0){
	  	    	  $("#txt_yysc").val(minutes+"分钟");
	  	      }
	        }else{
	        	alert("结束时间应大于开始时间");
	        	 $("#txt_yysc").val("");
	        }
	      
	                   
		          
	          
	}
</script>
</head>
<body>
<div class="fullscreen">
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid"  value="${fykmdysz.guid}">
	<input type="hidden" name="czr"  value="${loginId}">
	<div class="page-header">
	
                     <div class="container-fluid box-content">
                     <div class="row">
                     <div class="col-sm-3">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>开始时间</span>
                           <input type="text" id="txt_kssj" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="form-control input-radius" value="<fmt:formatDate value="${dwb.JLRQ}" pattern="HH:mm:ss"/>"/>   
						</div>
					</div>
					<div class="col-sm-3">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>结束时间</span>
                              <input type="text" id="txt_jssj" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="form-control input-radius" value="<fmt:formatDate value="${dwb.JLRQ}" pattern="HH:mm:ss"/>"/>   
						</div>
					</div>
					<div class="col-sm-3">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>预约时长</span>
                            <input type="text" id="txt_yysc" class="form-control input-radius" name="yysc" value="${fykmdysz.fymc}"/>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>允许预约人数</span>
                            <input type="text" id="txt_fymc" class="form-control input-radius" name="fymc" value="${fykmdysz.fymc}"/>
						</div>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
							<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>生成</button>
						</div>
					</div>
                     </div>                   
                     </div>		
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>本日详细信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
				<div class="container-fluid">
			<div class='responsive-table'>
				<div class='scrollable-area'>					
					<table id="mydatatables" class="table table-striped table-bordered">
						<thead>
							<tr>
								<th><input type="checkbox" class="select-all" /></th>
								<th>序号</th>
								<th>开始时间</th>
								<th>结束时间</th>
								<th>允许预约人数</th>						
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
			</div>
		</div>
		</div>
	</div>
</form>
</div>

<script type="text/javascript">
$(function(){
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
       
          }
	      });
	//保存按钮
	$("#btn_save").click(function(){		
		alert("生成成功！");
	});
	
	

	//列表数据
	var columns = [
			{
				"data" : "GUID",
				orderable : false,
				"render" : function(data, type, full, meta) {
					return '<input type="checkbox" class="keyId" name="guid" bz="'+full.BZS+'" value="' + data + '">';
				},
				"width" : 10,
				'searchable' : false
			},	
			{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
            	return data;
            },"width":41,"searchable": false,"class":"text-center"},
			{
				"data" : "KSSJ",
				defaultContent : "",
					"render" : function(data, type, full, meta) {
						return '<input type="hidden"  class="keyId" name="kssj" bz="'+full.BZS+'" value="' + data + '">'+data+'';
					}
			},
			{
				"data" : "JSSJ",
				defaultContent : ""
			},
			{
				"data" : "YXYYRS",						
				defaultContent : "",class:"text-right",
					"render" : function(data, type, full, meta) {
						return '<input type="hidden"  class="keyId" name="yxyyrs" bz="'+full.BZS+'" value="' + data + '">'+data+'';
					}
			}
			
			 ];
	table = $('#mydatatables').DataTable({
        ajax: {
            url: "${pageContext.request.contextPath}/yysz/getPageList"//获取数据的方法
        },
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [ 2, 'asc' ],
        "serverSide": true,
        "columns": columns,
        "language":{
            //"search":"",
            //"searchPlaceholder":"请输入种类名称"
        },
        "dom":"<'row'<'col-sm-7 col-sm-7 col-xs-7'li><'col-sm-5 col-sm-5 col-xs-5'>>t<'row'<'col-sm-12 col-sm-12 col-xs-12 pull-right'p>>"
    });
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/webView/wsbx/jcsz/fykmdysz/fykmdysz_list.jsp";
	});
	//刷新按钮
	$(".reload").click(function(){
		 var operateType =  $("[name='operateType']").val();
		 if(operateType=='U'){
			 window.location.href = window.location.href+"&operateType=U&rybh=${ryb.rybh}"
		 }else{
			 var url = window.location.href;
	    	if(url.indexOf("?")>0){
	    		window.location.href = window.location.href+"&gxgdzc_uuid=googosoft2016"
	    	}else{
	    		window.location.href = window.location.href+"?gxgdzc_uuid=googosoft2016"
	    	}
		 }
	});

	
});

</script>
</body>
</html>
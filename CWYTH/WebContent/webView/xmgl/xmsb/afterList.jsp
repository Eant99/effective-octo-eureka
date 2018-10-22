<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.text-green{
		color:green!important;
	}
/* 	select{ */
/* 		width:220px!important; */
/* 	} */
	.inputs[type=text]{
		width:100%!important;
	}
	.radiodiv{
	    border: 1px solid #ccc;
	    border-top-right-radius: 4px;
		border-bottom-right-radius:4px;
/* 	    border-radius: 4px; */
	    height: 25px;
	    line-height: 25px;
	    padding-left: 6px;
	}
	
	.select2{
	    border-radius: 4px;
	}
	/* .yc{
		display:none;
	} */
	.table-bordered {
    	border: none;
	}
	table{
		 border-collapse: collapse;
		 width:100%;
	}
	[class^=col-md-]{
		width:100%!important;
	}
</style>
</head>
<body>
<div  class="form-horizontal" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid" id="guid" value="${operateType}">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑采购明细信息</span>
		</div>
		<div class="pull-right" style="margin-top:3px;">
				<div class="sub-title pull-left text-primary">
				<div style="width:26px; height:26px; background-color:green; border-radius:13px;">
        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">1</span>
    			</div>
				</div>
				<div class="sub-title pull-left text-green">项目信息&nbsp;></div>
				
				<div class="sub-title pull-left ">
				<div style="width:26px; height:26px; background-color:#00ACEC; border-radius:13px;">
        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span>
    			</div>
				</div>
				<div class="sub-title pull-left text-primary">采购明细信息</div>
				
				
        </div>
	</div>
	<div class="box" style="top:36px" >
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>第二步,采购明细信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
            	<div class="pull-right">
            	<button type="button" class="btn btn-link" id="btn_ywsm" title="点击可查看业务说明">
						<i class="fa icon-help" style="color:#36b5f8;font-size:20px;"></i>
						<font style="font-size:14px;">业务说明</font>
					</button>
            		<button type="button" class="btn btn-default" id="btn_save" >保存</button>
            		<button type="button" class="btn btn-default" id="btn_del" style="background:#00acec;color: white;">删除</button>
					<button type="button" class="btn btn-default" id="btn_back" style="background:#00acec;color: white;">返回</button>
        		</div>
        	</div>
			<hr class="hr-normal">
			
			<div class="container-fluid box-content" id="group_panel">
				<div style="border:none;width:100%;">
				
				<table id="mydatatables" class="table table-striped table-bordered yc ">
				    	<tr>
				    		<td rowspan="5" style="width:5%;text-align:center;vertical-align: middle;">
								<input type="checkbox" class="ck" value=""/>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td>
				    			<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon">采购目录</span>
										<input type="text" id="txt_xmfl" name="xmfl" class="form-control input-radius inputs" value="${param.flmc}"/>
									</div>
                				</div>
				    		</td>
				    		<td>
				    			 <div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon">物品名称</span>
                       					<input type="text" id="txt_wpmc" class="form-control input-radius inputs" name="wpmc" value=""/>
									</div>
								</div>
				    		</td>
				    		<td>
				    			<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon">计量单位</span>
										<select class="form-control input-radius select2" name="jldw" style="width:100%;">
											<option value="">个</option>
											<option value="1">升</option>
											<option value="2">其他</option>
										</select>
									</div>
								</div>	
				    		</td>
				    	</tr>
				    	<tr>
				    		<td>
				    			<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon">数&emsp;&emsp;量</span>
										<input type="text" id="sl" name="sl" class="form-control input-radius inputs int" style="text-align:right;" value=""/>
									</div>
                				</div>
				    		</td>
				    		<td>
				    			 <div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon">单&emsp;&emsp;价(元)</span>
                       					<input type="text" id="txt_dj" class="form-control input-radius number inputs" style="text-align:right;" name="dj" value=""/>
									</div>
								</div>
				    		</td>
				    		<td>
				    			<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon">金&emsp;&emsp;额(元)</span>
										<input type="text" id="txt_je" class="form-control input-radius number inputs" style="text-align:right;" name="je" value=""/>
									</div>
								</div>	
				    		</td>
				    	</tr>
				    	<tr>
					    	<td colspan="3">
					    		<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon">是否进口</span>
										<div class="radiodiv">
										<input type="radio" id="txt_sfjk1" name="sfjk" value="1" class=""/>是
										<input type="radio" id="txt_sfjkk" name="sfjk" value="0" class="" />否
										</div>
									</div>
   								 </div>
					    	</td>
				    	</tr>
				    	<tr>
					    	<td colspan="3">
					    		<div class="col-md-12">
									<div class="input-group">
										<span class="input-group-addon">备注</span>
<!-- 										<textarea style="width:100%;height:100px;" class="form-control input-radius"></textarea> -->
                           				<textarea style="width:100%;height:100px;" name="bz" class="form-control input-radius"></textarea>
									</div>
   								 </div>
					    	</td>
				    	</tr>
				</table>
			<form id="myform" method="post" name="myform">
			<input type="hidden" name="xmbh" id="xmbh"  value="${param.xmbh}">
			</form>
				</div>
			</div>
			</div>
			<div class="container-fluid box-content">
				<div class="row">
				<div class="pull-center" style="text-align:center;">
            		<button type="button" class="btn btn-default" id="btn_after" style="background:#00acec;color: white;">上一步</button>
            		<button type="button" class="btn btn-default" id="btn_finish" style="background:#00acec;color: white;">完成</button>
        		</div>
			</div>
			</div>
	</div>
</div>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//document.getElementById("txt_sfjk2").checked="checked";
  var operate="${param.operate}";
	//保存按钮
	$("#btn_save").click(function(){
		/* var json = $("#myform").serializeObject("xmbh","bz");  //json对象
		
		var jsonStr = JSON.stringify(json);  //json字符串
		console.log("===="+jsonStr.toString());
		$.ajax({
  	   			url:ADDRESS+"/xmgl/xmsb/cgmxSaveFlow?",
  	   			type:"post",
  	   			data:"json="+jsonStr,
  	   			async:"true",
  	   			success:function(data){
  	   				var result = JSON.getJson(data);
  	   				if(result.success){
						alert("保存成功！");  	   					

  	   				}
  	   			}
  	   		}); */
  	   		alert("保存成功！")
   	});
	$.fn.serializeObject = function(start,end){
		var f = {"list":[]};
	    var a = this.serializeArray();
	    var o = {};
	    $.each(a, function() {
	    	if (this.name == start) {
	        	o = {};
	        	o[this.name] = this.value;
	        } else if(this.name == end){
	        	o[this.name] = this.value;
	        	f["list"].push(o);
	        }else if(this.name.indexOf("sfjk")>=0){
                  o.sfjk=this.value;
                  this.name=undefined;
	        }else{
	        	o[this.name] = this.value;
	        }
	    });
	    return f;
	};
	$("#btn_finish").click(function(){
		var type = "${param.type}"
		if(true){
			var json = $("#myform").serializeObject("xmfl","bz");  //json对象
			
			var jsonStr = JSON.stringify(json);  //json字符串
			console.log("===="+jsonStr.toString());
			$.ajax({
	  	   			url:ADDRESS+"/xmgl/xmsb/cgmxSaveFlow?",
	  	   			type:"post",
	  	   			data:"json="+jsonStr,
	  	   			async:"true",
	  	   			success:function(data){
	  	   				var result = JSON.getJson(data);
	  	   				if(result.success){
	  	   				parent.location.href = "${ctx}/webView/xmgl/xmsb/next_list.jsp?mkbh=${param.mkbh}";		

	  	   				}
	  	   			}
	  	   		});
			
		}else{
			alert("请先保存该页面的数据！");
		}
	});
	//返回按钮
	$("#btn_back").click(function(){
		parent.window.location.href = "${ctx}/webView/xmgl/xmsb/main.jsp?mkbh=${param.mkbh}";
	});
	//上一步
	$("#btn_after").click(function(){
		if(true){
			//parent.location.href="${ctx}/webView/xmgl/xmsb/xmsb_operate.jsp?type=second&mkbh=${param.mkbh}";
			doOperate("${ctx}/xmsb/xmsb/goEditOnePage?guid=${param.guid}","U");	
		}else{
			alert("请先保存该页面数据！");
		}
	});
	//删除
	$("#btn_del").click(function(){
		var guid=$("#guid").val();
		confirm("确定删除该项目下维护的采购明细？","{title:提示信息}",function(){
			$.ajax({
   	   			url:ADDRESS+"/xmgl/xmsb/cgmxDeleteFlow",
   	   			data:"guid="+guid,
   	   			type:"post",
   	   			async:"false",
   	   			success:function(val){
   	   				alert(val);
   	   			window.location.href = "${ctx}/webView/xmgl/xmsb/afterList.jsp";
   	   			}
   	   		});
		});
	});
});
$(function(){
	//项目分类弹窗 
	$("#btn_xmfl").click(function(){
		select_commonWin("${ctx}/xmsb/xmsb/window?controlId=txt_xmfl","项目分类信息","920","630");
    });
	//专业弹窗 
	$("#btn_fwzy").click(function(){
		select_commonWin("${ctx}/xmsb/xmsb/window?controlId=txt_fwzy","专业信息","920","630");
    });
});
$("#btn_ywsm").click(function(){
	zysx();
});
function zysx(){
	select_commonWin("${pageContext.request.contextPath}/ywsm/getYwsmWin?mkbh=${param.mkbh}&sfbc=1","", "920", "530");
}
</script>
</body>
</html>
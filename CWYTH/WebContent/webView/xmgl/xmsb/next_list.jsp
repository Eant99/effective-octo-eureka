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
	select{
		width:220px!important;
	}
	.yc{
		display:none;
	}
	.select2{
	    border-radius: 4px;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑长期绩效指标</span>
		</div>
		<div class="pull-right" style="margin-top:3px;">
				<div class="sub-title pull-left text-primary">
				<div style="width:26px; height:26px; background-color:green; border-radius:13px;">
        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">1</span>
    			</div>
				</div>
				<div class="sub-title pull-left text-green">项目信息&nbsp;></div>
				
				<div class="sub-title pull-left ">
				<div style="width:26px; height:26px; background-color:green; border-radius:13px;">
        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span>
    			</div>
				</div>
				<div class="sub-title pull-left text-green">采购明细信息&nbsp;></div>
				
				<div class="sub-title pull-left ">
				<div style="width:26px; height:26px; background-color:#00ACEC; border-radius:13px;">
        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">3</span>
    			</div>
				</div>
				<div class="sub-title pull-left text-primary">长期绩效指标</div>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>第三步,长期绩效指标</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
            	<div class="pull-right">
            	<button type="button" class="btn btn-link" id="btn_ywsm" title="点击可查看业务说明">
						<i class="fa icon-help" style="color:#36b5f8;font-size:20px;"></i>
						<font style="font-size:14px;">业务说明</font>
					</button>
            		<button type="button" class="btn btn-default" id="btn_add" style="background:#00acec;color: white;">增加</button>
            		<button type="button" class="btn btn-default" id="btn_save" >保存</button>
            		<button type="button" class="btn btn-default" id="btn_del" style="background:#00acec;color: white;">删除</button>
					<button type="button" class="btn btn-default" id="btn_back" style="background:#00acec;color: white;">返回</button>
        		</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content" id="group_panel">
			<table id="mydatatables" class="table table-striped table-bordered yc">
				    <tbody>
				    	<tr>
				    		<td rowspan="4" style="width:50px;text-align:center;vertical-align: middle;">
				    			<input type="checkbox" value="" class="delBox"/>
				    		</td>
				    	</tr>
				    	<tr id="one">
				    		<td>
				    			<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>一级指标</span>
                           				<select id="drp_yjzb" class="form-control input-radius" name="yjzb">
	                                  		<option value="">未选择</option>
	                                  		<option value="1">产品指标</option>
	                                  		<option value="2">效益指标</option>
	                                  		<option value="3">社会公众或服务对象满意度指标</option>
	                        			</select>
									</div>
								</div>	
				    		</td>
				    		<td>
				    			<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>二级指标</span>
                           				<select id="drp_ejzb" class="form-control input-radius" name="ejzb">
	                                  		<option value="">--未选择--</option>
	                        			</select>
									</div>
								</div>	
				    		</td>
				    		<td>
				    			<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>指标值</span>
                           				<input type="text" id="txt_zbz" style="width:220px;" class="form-control input-radius" name="zbz" value="">
									</div>
								</div>	
				    		</td>
				    	</tr>
				    	
				    	<tr>
				    		<td colspan="3">
				    			<div class="col-md-12">
									<div class="input-group">
										<span class="input-group-addon">备注</span>
                           				<textarea style="width:100%;height:100px;"></textarea>
									</div>
								</div>
				    		</td>
				    	</tr>
				    </tbody>
				</table>
				<table id="mydatatables_1" class="table table-striped table-bordered">
				    <tbody>
				    	<tr>
				    		<td rowspan="4" style="width:50px;text-align:center;vertical-align: middle;">
				    			<input type="checkbox" value="" class="delBox"/>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td>
				    			<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>一级指标</span>
                           				<select id="drp_yjzb" class="form-control input-radius" name="yjzb">
	                                  		<option value="">未选择</option>
	                                  		<option value="1">产品指标</option>
	                                  		<option value="2">效益指标</option>
	                                  		<option value="3">社会公众或服务对象满意度指标</option>
	                        			</select>
									</div>
								</div>	
				    		</td>
				    		<td>
				    			<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>二级指标</span>
                           				<select id="drp_ejzb" class="form-control input-radius" name="ejzb">
	                                  		<option value="">--未选择--</option>
	                        			</select>
									</div>
								</div>	
				    		</td>
				    		<td>
				    			<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon"><span class="required">*</span>指标值</span>
                           				<input type="text" id="txt_zbz" style="width:220px;" class="form-control input-radius" name="zbz" value="">
									</div>
								</div>	
				    		</td>
				    	</tr>
				    	
				    	<tr>
				    		<td colspan="3">
				    			<div class="col-md-12">
									<div class="input-group">
										<span class="input-group-addon">备注</span>
                           				<textarea style="width:100%;height:100px;" class="form-control input-radius"></textarea>
									</div>
								</div>
				    		</td>
				    	</tr>
				    </tbody>
				</table>
			</div>
			</div>
			<div class="container-fluid box-content" id="group_panel">
				<div class="row">
				<div class="pull-center" style="text-align:center;">
            		<button type="button" class="btn btn-default" id="btn_after" style="background:#00acec;color: white;">上一步</button>
            		<button type="button" class="btn btn-default" id="btn_fin" style="background:#00acec;color: white;">完成</button>
        		</div>
			</div>
			</div>
		</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var num = 2;
$(function(){
	//保存按钮
	$("#btn_save").click(function(){
		alert("操作成功");
	});
	$("#btn_fin").click(function(){
		alert("操作成功");
	});
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/webView/xmgl/xmsb/main.jsp?mkbh=${param.mkbh}";
	});
	//上一步
	$("#btn_after").click(function(){
		if(true){
			location.href="${ctx}/webView/xmgl/xmsb/after.jsp?mkbh=${param.mkbh}";
		}else{
			alert("请先保存该页面数据！");
		}
	});
	//删除
	$("#btn_del").click(function(){
		
	});
});
$(function(){
	//增加
	$(document).on("click","[id=btn_add]",function(){
		var parentTable = $("#mydatatables").clone();
		
		parentTable.find("input").val("");
		parentTable.removeClass("yc");
		parentTable.attr("id","mydatatables_"+num);
		parentTable.find("[id^=one]").find("[id^=drp_yjzb]").attr("id","drp_yjzb"+num);
		parentTable.find("[id^=one]").find("[id^=drp_ejzb]").attr("id","drp_ejzb"+num);
		$("#mydatatables").before(parentTable);
		num++;
	});
	//删除
	$(document).on("click","[id=btn_del]",function(){
		var $_del = $(".delBox").filter(":checked");
		if($_del.length==0){
			alert("请选择要删除的项！");
			return;
		}
		$_del.parents("table").remove();
	});
	//指标改变时连动
	$(document).on("change","[id^=drp_yjzb]",function(){
		var yjzb=$(this).val();
		$(this).parents("tr").find("[id^=drp_ejzb]").children("option").remove();
		if(yjzb=="1"){
			var trHTML = "<option  value='1'>数量指标</option>"
			+"<option  value='2'>质量指标</option>"
			+"<option  value='3'>时效指标</option>"
			+"<option  value='4'>成本指标</option>"
            $(this).parents("tr").find("[id^=drp_ejzb]").append(trHTML);
		}else if(yjzb=="2"){
			var trHTML = "<option  value='1'>经济效益指标</option>"
				+"<option  value='2'>社会效益指标</option>"
				+"<option  value='3'>生态效益指标</option>"
				+"<option  value='4'>可持续影响指标</option>"
	            $(this).parents("tr").find("[id^=drp_ejzb]").append(trHTML);
		}else if(yjzb=="3"){
			var trHTML = "<option  value='1'>具体指标</option>"
		   $(this).parents("tr").find("[id^=drp_ejzb]").append(trHTML);
		}
	});
// 	function zbchange(){
// 		var qx = $(".qx").val();
// 		$.ajax({
//      		type : 'post',
//      		url : '${pageContext.request.contextPath}/LbjsServlet.do?method=findSchool',
//      		dataType : 'json' ,
//      		data : {"qx": qx},
//      		success : function(data) {
//      			$(".school").children("option").remove();
//      			for(var i=0;i<data.total;i++){
//      				var trHTML = "<option id='schoolOption' "
//      				+"value='"+data.data[i].id+"'>"
//      				+data.data[i].name
//      				+"</option>";
//      				$(".school").append(trHTML);
//      			}
//      		}
//      	  });
// 	}
	
	
	
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
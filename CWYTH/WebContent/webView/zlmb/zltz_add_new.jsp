<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目类型增加</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
.tables {
    margin-left: 20px;
    max-width: 100%;
    margin-bottom: 20px;
    width:50%;
   }
.tables1 {
    margin-left: 20px;
    max-width: 100%;
    margin-bottom: 20px;
    width:97%;
   }
 .p1{
    text-align:center;
    font-family:宋体;
    color:red;
    font-size:18px;
   }
 [class^=td_one_]{
 	text-align:center;
 	width:10%;
 	height:30px;
 }
 [class^=td_two_]{
 	width:15%
 }
 [class^=td_three_]{
 	width:15%
 }
 [class^=td_four_]{
 	width:15%
 }
 [class^=td_five_]{
 	width:15%
 }
 [class^=td_six_]{
 	width:15%
 }
  [class^=td_seven_]{
 	width:15%
 }
 .td_input{
 	width:100%;
 	border:none;
 }
 a{
	 text-decoration:none;
 }

</style>

</head>
<body>
<div id="myform"  >
  
	<div class='page-header'>
        <div class="pull-left">
            <span class="page-title text-primary">
            	<i class='fa icon-xiangxixinxi'></i>
            		战略调整
			</span>
		</div>
        <div class="pull-right">
			   <button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			   <button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
    </div>
	<div class="box">
		 <!-- 附件设置 -->
		  <form id="table4" class="myform myformPzby" mc="addfjsz1">
			<div class="box-panel">
				<div class="box-header clearfix">
	            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>战略调整详细信息</div>
	            	<div class="actions">
	            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
	            	</div>
	        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
			<div class="row">
					<div class="col-md-4">
						<div class="input-group">
								<span class="input-group-addon">目标名称</span>
							<input type="text" id="txt_bmh" class="form-control input-radius"   name="mbbh"  value="2020年增加科技发明20项" readonly/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
								<span class="input-group-addon">目标年度</span>
							<input type="text" id="txt_mc" class="form-control input-radius"  readonly="readonly" name="mbnd" value="2019 "/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group ">
							<span class="input-group-addon" style="border: 1px solid #ccc!important;vertical-align: middle; ">制定日期</span>
							<input type="text" id="txt_kssj" name="zdrq" class="form-control input-radius date window" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  data-bv-field="kgrq"
							value="2018-1-30" disabled/>
						</div>	
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">调整原因</span>
	                        <textarea id="txt_bz"  class="form-control" name="xmszsm" style=" height: 50px; width: 100%;">${map.XMSZSM }</textarea>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">调整内容</span>
	                        <textarea id="txt_bz"  class="form-control" name="xmszsm" style=" height: 50px; width: 100%;">${map.XMSZSM }</textarea>
						</div>
					</div>
				</div>
        		<div class="row">
        			<table id="mydatatables" class="tables1 table-striped table-bordered" style="border-collapse: collapse">
        			<tbody id="tbody4">
        				<tr>
					    	<td class="td_one_1" style="text-align:center;" valign="middle">
					    		部门名称
					    	</td>
					    	<td class="td_two_1" style="text-align:center;" valign="middle">
					    		调整前一级指标
					    	</td>
					    	<td class="td_three_1" style="text-align:center;" valign="middle">
					    		<span class="required" style="color:red">*</span>调整后一级指标
					    	</td>
					    	<td class="td_four_1"  style="text-align:center;" valign="middle">
					    		调整前二级指标
					    	</td>
					    	<td class="td_five_1"  style="text-align:center;" valign="middle">
					    		<span class="required" style="color:red">*</span>调整后二级指标
					    	</td>
					    	<td class="td_six_1"  style="text-align:center;" valign="middle" >
					    		调整前指标内容
					    	</td>
					    	<td class="td_seven_1"  style="text-align:center;" valign="middle" >
					    		<span class="required" style="color:red;">*</span>调整后指标内容
					    	</td>
				   		</tr>
				   		<tr id="fjsztr_1" a='a'>
					    	<td class="td_one_1" >
								<input type="text" id="txt_bmbh0" name="bmbh" class="form-control input-radius " style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" value="教务处" disabled>
					    	</td>
					    	<td class="td_two_1">
				    			<select id="drp_dwbb" class="form-control input-radius " name="xmlb" style="border: 0px;" disabled>
                               	 	<option>学校管理</option>
                               	 	<option selected="selected">基础条件</option>
                               	 	<option>校企合作</option>
                               	 	<option>教育教学</option>
                               	 	<option>办学效益</option>
                               </select>
					    	</td>
					    	<td class="td_three_1" style="text-align:center;" valign="middle">
					    		<select id="drp_dwbb" class="form-control input-radius " name="xmlb" style="border: 0px;" >
                               	 	<option>未调整</option>
                               	 	<option>学校管理</option>
                               	 	<option>基础条件</option>
                               	 	<option>校企合作</option>
                               	 	<option>教育教学</option>
                               	 	<option>办学效益</option>
                               </select>
					    	</td>
					    	<td class="td_four_1">
					    		<select id="drp_dwbb" class="form-control input-radius " name="xmlb" style="border: 0px;" disabled>
                               	 	<option>办学方向</option>
                               	 	<option>办学行为</option>
                               	 	<option selected="selected">领导班子</option>
                               	 	<option>规范管理</option>
                               	 	<option>校园建设</option>
                               	 	<option>德育工作</option>
                               	 	<option>师资队伍</option>
                               </select>
					    	</td>
					    	<td class="td_five_1">
					    		<select id="drp_dwbb" class="form-control input-radius " name="xmlb" style="border: 0px;">
                               	 	<option>未调整</option>
                               	 	<option>办学方向</option>
                               	 	<option>办学行为</option>
                               	 	<option>领导班子</option>
                               	 	<option>规范管理</option>
                               	 	<option>校园建设</option>
                               	 	<option>德育工作</option>
                               	 	<option>师资队伍</option>
                               </select>
					    	</td>
					    	<td class="td_six_1" >
					    		<input  value="测试内容1" class="form-control input-radius" readonly />
					    	</td>
					    	<td class="td_seven_1" >
					    		<input  placeholder="请输入调整后指标内容"  class="form-control input-radius" />
					    	</td>
				   		</tr>
        			 	<tr id="fjsztr_1" a='a'>
					    	<td class="td_one_1" >
									<input type="text" id="txt_bmbh0" name="bmbh" class="form-control input-radius" value="科技处" disabled>
					    	</td>
					    	<td class="td_two_1">
				    			<select id="drp_dwbb" class="form-control input-radius " name="xmlb" style="border: 0px;" disabled>
                               	 	<option>学校管理</option>
                               	 	<option selected="selected" >基础条件</option>
                               	 	<option>校企合作</option>
                               	 	<option>教育教学</option>
                               	 	<option>办学效益</option>
                               </select>
					    	</td>
					    	<td class="td_three_1" style="text-align:center;" valign="middle">
					    		<select id="drp_dwbb" class="form-control input-radius " name="xmlb" style="border: 0px;" >
                               	 	<option>未调整</option>
                               	 	<option>学校管理</option>
                               	 	<option>基础条件</option>
                               	 	<option>校企合作</option>
                               	 	<option>教育教学</option>
                               	 	<option>办学效益</option>
                               </select>
					    	</td>
					    	<td class="td_four_1">
					    		<select id="drp_dwbb" class="form-control input-radius " name="xmlb" style="border: 0px;" disabled>
                               	 	<option>办学方向</option>
                               	 	<option>办学行为</option>
                               	 	<option selected="selected">领导班子</option>
                               	 	<option>规范管理</option>
                               	 	<option>校园建设</option>
                               	 	<option>德育工作</option>
                               	 	<option>师资队伍</option>
                               </select>
					    	</td>
					    	<td class="td_five_1">
					    		<select id="drp_dwbb" class="form-control input-radius " name="xmlb" style="border: 0px;">
                               	 	<option>未调整</option>
                               	 	<option>办学方向</option>
                               	 	<option>办学行为</option>
                               	 	<option>领导班子</option>
                               	 	<option>规范管理</option>
                               	 	<option>校园建设</option>
                               	 	<option>德育工作</option>
                               	 	<option>师资队伍</option>
                               </select>
					    	</td>
					    	<td class="td_six_1" >
					    		<input  value="测试内容2" class="form-control input-radius" readonly />
					    	</td>
					    	<td class="td_seven_1" >
					    		<input  placeholder="请输入调整后指标内容"  class="form-control input-radius" />
					    	</td>
				   		</tr>
				   		
        			</tbody>
				</table>
		 	</div>
		 </div>
		 </div>
		 </form>
		 
	</div>
</div>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	var target ="${ctx}/xmlx";
	//返回按钮
	$("#btn_back").click(function(e){
		window.location.href  = "${ctx}/webView/zlmb/zltz_list.jsp";
	});
	//联想输入
	$("#txt_dwld").bindChange("${ctx}/suggest/getXx","R");
	$("#txt_fgld").bindChange("${ctx}/suggest/getXx","R");
	$("#txt_sjdw").bindChange("${ctx}/suggest/getXx","D");	
	//收入科目弹窗
	$(document).on("click",".addBtn1",function(){
		select_commonWin("${ctx}/xmlx/window?lx=sr","收入科目信息","920","630");
	})
	//支出科目弹窗
	$(document).on("click",".addBtn2",function(){
		select_commonWin("${ctx}/xmlx/window?lx=zc","支出科目信息","920","630");
	})
	//经济科目弹窗
	$(document).on("click",".addBtn3",function(){
		select_commonWin("${ctx}/xmlx/jjflwindow","经济科目信息","920","630");
	})
	//项目弹窗
	$(document).on("click","[id^='btn_xmlx_']",function(){
		var id = $(this).attr("sjid");
		select_commonWin("${ctx}/xmlx/getxmlxall2?controlId="+id+"&id=txt_xmlxid","项目信息","920","630");	
	})
	//部门名称弹窗
	$(document).on("click","#btn_bmbh",function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_bmbh","部门信息","920","630");
	});
// 	$(document).on("click","#btn_xmlx_1",function(){
// 		select_commonWin("${ctx}/xmlx/getxmlxall2?controlId=txt_xmlx_1&id=txt_xmlxid","项目信息","920","630");	
// 	})
	
	
	//新增按钮3
	var nums = 2;
	$(document).on("click", "[class=addBtn4]", function() {
		var $parentTr = $(this).parents("tr").clone();
		$(this).removeClass("addBtn4").addClass("delBtn4");
		$parentTr.find("[a='a']").val("");
		$parentTr.find(":input").removeClass("border");
		$parentTr.find("[id^='txt_xmlx_']").attr("id","txt_xmlx_"+nums);
		$parentTr.find("[id^='btn_xmlx_']").attr("sjid","txt_xmlx_"+nums);
		$parentTr.find("[id^='btn_xmlx_']").attr("id","btn_xmlx_"+nums);
		$parentTr.attr("id", "tr_" + nums);
		$parentTr.find("[name^='sfczzc_']").attr("name","sfczzc_"+nums);
		$parentTr.find("[name^='sjzt_']").attr("name","sjzt_"+nums);
		console.log("__"+nums);
		nums++;
		$(this).parents("tr").after($parentTr);
// 		$("#cczb3").attr("rowspan", nums3);
// 		$("#ndjxzb").attr("rowspan", nums3+11);
	});
	$(document).on("click","[class=delBtn4]",function(){
		var $parentTr = $(this).parents("tr");
		$parentTr.remove();
		
	});
	
	//必填验证
	var validate = $('#myform1').bootstrapValidator({fields:{
				xmlxbh:{validators:{notEmpty:{message:'不能为空'}}},
				xmlxmc:{validators:{notEmpty: {message: '不能为空'}}},
				sfczzc:{validators:{notEmpty: {message: '不能为空'}}},
				srkm:{validators:{notEmpty: {message: '不能为空'}}},
				xmlb:{validators:{notEmpty:{message:'不能为空'}}},
         }});
	//保存按钮
	$("#btn_save").click(function(e){	
// 		checkAutoInput();
// 			doSave1(validate,"myform1","${ctx}/xmlx/doSave?operateType=C",function(val){
// 				window.location.href = "${ctx}/webView/ysgl/xmsz/xmlx/xmlx_list.jsp";
// 				alert("保存成功");
// 			});
				window.location.href = "${ctx}/webView/zlmb/zltz_list.jsp";
				alert("保存成功");
		
	});	
	function doSave1(_validate, _formId, _url, _success, _fail){
		checkAutoInput();
		var index;
		var valid;
		if(_validate){
			_validate.bootstrapValidator("validate");
			valid = $("#myform1").data('bootstrapValidator').isValid();//ldhldhldh
		} else {
			valid = true;
		}
		if(!tag){
			valid = false;
		}
		if(true){
			$.ajax({
				type:"post",
				url:_url,
// 				dataType:"json",
				data:arrayToJson($("#myform1").serializeArray()),
				success:function(val){	
					next();
				},
				error:function(val){
					console.log("___"+val);
// 					next();
				}	
			});
		}
	}
	//查看页面
	if($("[name='operateType']").val()=='L'){
		$("input,select,textarea").attr("readonly","true");
		$("select").attr("disabled","true");
	}
	//页面加载完后，控制实验室信息模块是否展示
	sysbz();
	//onoff按扭切换
	$("#btn_onoff").click(function(){
		if($("[name='sysbz']").val()=='0'){
			$("[name='sysbz']").val("1");
		}else if($("[name='sysbz']").val()=='1'){
			$("[name='sysbz']").val("0");
		}else{
			$("[name='sysbz']").val("0");
		}
		sysbz();
	});
	//nnoff按扭切换
	$("#btn_nnoff").click(function(){
		if($("[name='sfxy']").val()=='0'){
			$("[name='sfxy']").val("1");
		}else if($("[name='sfxy']").val()=='1'){
			$("[name='sfxy']").val("0");
		}else{
			$("[name='sfxy']").val("1");
		}
	});

	//dnoff按扭切换
	$("#btn_dnoff").click(function(){
		if($("[name='dwzt']").val()=='0'){
			$("[name='dwzt']").val("1");
		}else if($("[name='dwzt']").val()=='1'){
			$("[name='dwzt']").val("0");
		}else{
			$("[name='dwzt']").val("1");
		}
	});
	
	//收入
// 	 var num = 2;
// 	 $(document).on("click","[class*=addBtn1]",function(){
		
// 		var $parentTr = $(this).parents("tr").clone();
// 		$(this).removeClass("addBtn1");
// 		$(this).addClass("delBtn");
// 		$parentTr.find("input:not(.a)").val("");
// 		$parentTr.find(":input").removeClass("border");
// 		$parentTr.attr("id","tr_"+num);
// 		//转入
// 		$parentTr.find("[id^=txt_srkm]").attr({"id":"txt_srkm"+num});
// 		$parentTr.find("[id^=txt_srkbh]").attr({"id":"txt_srkbh"+num});
// 		$parentTr.find("[id^=btn_srkm]").attr({"id":"btn_srkm"+num,"mc":"txt_srkm"+num,"bh":"txt_srkbh"+num});
// 		num++;
// 		$(this).parents("tr").after($parentTr);
// 	});
	//支出
// 	 var num = 2;
	/*  $(document).on("click","[class*=addBtn2]",function(){
		
		 var $parentTr = $(this).parents("tr").clone();
			$(this).removeClass("addBtn2");
			$(this).addClass("delBtn");
			$parentTr.find("input:not(.b)").val("");
			$parentTr.find(":input").removeClass("border");
			$parentTr.attr("id","tr_"+num);
			//支出
			$parentTr.find("[id^=txt_zckm]").attr({"id":"txt_zckm"+num});
			$parentTr.find("[id^=txt_zckbh]").attr({"id":"txt_zckbh"+num});
			$parentTr.find("[id^=btn_zckm]").attr({"id":"btn_zckm"+num,"mc":"txt_zckm"+num,"bh":"txt_zckbh"+num});
			num++;
			$(this).parents("tr").after($parentTr);
		 
	 }); */
// 	 var num = 2;
// // 	 $(document).on("click","[class*=addBtn3]",function(){
		 
// 		 var $parentTr = $(this).parents("tr").clone();
// 			$(this).removeClass("addBtn3");
// 			$(this).addClass("delBtn");
// 			$parentTr.find("input:not(.c)").val("");
// 			$parentTr.find(":input").removeClass("border");
// 			$parentTr.attr("id","tr_"+num);
// 			//支出
// 		$parentTr.find("[id^=txt_jjflkm]").attr({"id":"txt_jjflkm"+num});
// 		$parentTr.find("[id^=txt_jjflkbh]").attr({"id":"txt_jjflkbh"+num});
// 		$parentTr.find("[id^=btn_jjflkm]").attr({"id":"btn_jjflkm"+num,"mc":"txt_jjflkm"+num,"bh":"txt_jjflkbh"+num});
// 			num++;
// 			$(this).parents("tr").after($parentTr);
// 	 });
	 
	 
		$(document).on("focus","[class*=border]",function(){
			$(this).removeClass("border");
		});
		 $(document).on("click",".delBtn1",function(){
			 $(this).parents("tr").remove();				 
		 });
		 $(document).on("click",".delBtn2",function(){
			 $(this).parents("tr").remove();				 
		 });
		 $(document).on("click",".delBtn3",function(){
			 $(this).parents("tr").remove();				 
		 });
		 $(document).on("click",".delBtn4",function(){
			 $(this).parents("tr").remove();				 
		 });
		 function checkAutoInput(){
				tag = true;
				var ys = $(".null");
				var value = "";
				$.each(ys,function(){
					value = $(this).val();
					if(value==""){
						$(this).addClass("border");
						tag = false;
					}
				});
			}

	//刷新按钮
	$(".reload").click(function(){
		 var operateType =  $("[name='operateType']").val();
		 if(operateType=='U'){
			 window.location.href = window.location.href+"&operateType=U&dwbh=${dwb.DWBH}";
		 }else{
			 var url = window.location.href;
	    	if(url.indexOf("?")>0){
	    		window.location.href = window.location.href+"&gxgdzc_uuid=googosoft2016";
	    	}else{
	    		window.location.href = window.location.href+"?gxgdzc_uuid=googosoft2016";
	    	}
		 }
	});
});
function sysbz(){
	var $this = $("[name='sysbz']").val();
	if($this == '0'){
		$("#sysxx").show();
	}else{
		$("[name='syslx']").val("");
		$("[name='syslb']").val("");
		$("[name='sysmj']").val("0.00");
		$("[name='sysjb']").val("");
		$("[name='ssxk']").val("");
		$("[name='sysgs']").val("");
		$("#sysxx").hide();
	}
}
$(function(){
	$("[name='dwzt']").change(function(){
		if($("[name='dwzt']").val()=='0'){
		 $.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/dwb/getNewStatus",
			data:"dwbh=${dwb.DWBH}",
			success:function(val){
				var data = JSON.getJson(val);
				 if(data.success=='false'){
					alert(data.msg);
					$("#drp_dwzt option").eq(0).attr("selected",true);
					$("#drp_dwzt option").eq(1).attr("selected",false);
				} 
			},
		}); 
			
		}
	});
});
function next(){
	var srlength=$("#pbtr_1").find("[name=kmbh]").val();
	var zclength=$("#zctr_1").find("[name=kmbh]").val();
	var jjfllength=$("#jjfltr_1").find("[name=kmbh]").val();
	var fjszlength=$("#fjsztr_1").find("[name=xmmc]").val();
	console.log(srlength);
	if(noNull(srlength)&&srlength.length==0){
		$("#pbtr_1").remove();
	}
	if(noNull(zclength)&&zclength.length==0){
		$("#zctr_1").remove();
	}
	if(noNull(zclength)&&jjfllength.length==0){
		$("#jjfltr_1").remove();
	}
	/* if(fjszlength.length==0){
		$("#fjsztr_1").remove();
	} */
	var mytable = $(".myform");
	var aryId = [];
	$.each(mytable,function(i,v){
		var $this = $(this);
		var id = $this.attr("id");
		var mc = $this.attr("mc")
		console.log($("#"+id+"").serializeArray());
		var json = $("#"+id+"").serializeObject("start","end","sjzt_<>sfczzc_","sjzt_<>sfczzc_");  //json对象		
		var jsonStr = JSON.stringify(json);  //json字符串
		console.log("===jsonStr"+jsonStr);
	
		$.ajax({
	        url:"${ctx}/xmlx/"+mc,
   			type:"post",
   			data:"json="+jsonStr,
   			success:function(data){
			// 
			//window.location.href = "${ctx}/pzlx/gopzlxListPage";
   			}

		}); 
	});
	alert("保存成功！"); 
	window.location.href = "${ctx}/webView/ysgl/xmsz/xmlx/xmlx_list.jsp";
}
function addsrkm(kmbh,kmmc){
	for(var i=0;i<kmbh.length;i++){
		var a = $(".aa");
		var kmbh1="";
		$.each(a,function(){
			kmbh1 +=$(this).val()+",";
		});
	   if(kmbh1.indexOf(kmbh[i])=="-1"){
			var $parentTr = $("#pbtr_1").clone();
			$parentTr.removeAttr("id");
			$parentTr.find("input:not(.a)").val("");
			$parentTr.find("[id=txt_srkbh]").val(kmbh[i]);
			$parentTr.find("[id=txt_srkm]").val(kmmc[i]);		
			$("#pbtr_1").before($parentTr);
			$(".addBtn1").attr("class","delBtn1");		
			$(".delBtn1:last").attr("class","addBtn1");
		 }
	}
}
function addzckm(kmbh,kmmc){
	for(var i=0;i<kmbh.length;i++){
		var a = $(".bb");
		var kmbh1="";
		$.each(a,function(){
			kmbh1 +=$(this).val()+",";
		});
	   if(kmbh1.indexOf(kmbh[i])=="-1"){
		   var $parentTr = $("#zctr_1").clone();
			$parentTr.removeAttr("id");
			$parentTr.find("input:not(.b)").val("");
			$parentTr.find("[id=txt_zckbh]").val(kmbh[i]);
			$parentTr.find("[id=txt_zckm]").val(kmmc[i]);		
			$("#zctr_1").before($parentTr);
			$(".addBtn2").attr("class","delBtn2");		
			$(".delBtn2:last").attr("class","addBtn2");
		 }
	}
}
function addjjflkm(kmbh,kmmc){
	for(var i=0;i<kmbh.length;i++){
		var a = $(".cc");
		var kmbh1="";
		$.each(a,function(){
			kmbh1 +=$(this).val()+",";
		});
	   if(kmbh1.indexOf(kmbh[i])=="-1"){
		   var $parentTr = $("#jjfltr_1").clone();
			$parentTr.removeAttr("id");
			$parentTr.find("input:not(.c)").val("");
			$parentTr.find("[id=txt_jjflkbh]").val(kmbh[i]);
			$parentTr.find("[id=txt_jjflkm]").val(kmmc[i]);		
			$("#jjfltr_1").before($parentTr);
			$(".addBtn3").attr("class","delBtn3");		
			$(".delBtn3:last").attr("class","addBtn3");
		 }
	}
	
}
</script>

</html>
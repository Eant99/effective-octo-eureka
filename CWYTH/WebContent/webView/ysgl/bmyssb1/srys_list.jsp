<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html style="background-color: white;">
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>银行帐号管理</title>
<%@include file="/static/include/public-list-css.inc"%>
<%@include file="/static/include/public-list-css.inc"%>
<%@include file="/static/include/public-manager-css.inc"%>
<style>
.btn_td{
		width:10px!important;
		height:6mm!important;
		text-align: center;
	}
.addBtn{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.addBtn:after{
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 17px;
	    position: relative;
	    cursor: pointer;
	}
	.deleteBtn{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.deleteBtn:after{
/* 		2014 */
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 1em;
	    position: relative;
	    cursor: pointer;
	    top:3px;
	}
	.border{
		border:1px solid #a94442;
		background:url("${ctx}/webView/wsbx/rcbx/no_null.bmp") center right no-repeat;;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
		<c:if test="${'sh'!=sh && 'ck'!=ck}">
		<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
		</c:if>
		<button type="button" class="btn btn-default" id="btn_back"><i class="fa icon-save"></i>返回</button>
		<input type="hidden" name="operateType" id="operateType" value="${operateType}">
		<center><h4><strong>收入预算表</strong></h4></center>
			<div class="search-simple">	
				<div class="form-group"> 
					<label>填报部门</label>					
 					<input type="text" class="form-control input-radius" name="tbbm"  id="txt_tbbm" readonly="readonly" value="${bmmc}">
<!--  					<button type="button" id="btn_dwmc" class="btn btn-link btn-custom">选择</button> -->
				 </div> 
				 <div class="pull-right">
					<label>单位：万元</label>
				 </div>
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<form id="myform1">
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead id="thead">
				        <tr>
				        	<th style="text-align: center;">操作</th>
				            <th style="text-align: center;">项目名称</th>
				            <th style="text-align: center;">预计完成数额（万元）</th>
				            <th style="text-align: center;">完成时间</th>
				            <th style="text-align: center;">备注</th>
				        </tr>
					</thead>
				   <tbody id="tbody">
				    
				    <c:forEach items="${list}" var="list" varStatus="i">
				    	<tr id="tr_${i.index+1}">
				    	<c:if test="${list.xmmc!=null}">
				    		<td class="btn_td">
	                               <div class="deleteBtn"></div>
				    		</td>
				    		</c:if>
				    		<td>
				    		    <input type="hidden" id="txt_wlbh1" class="form-control input-radius" name="guid"  value="${guid }">
				    		    <div class="input-group">
					    			<input type="text" id="txt_xmmc" a="a" class="form-control input-radius null" readonly name="xmmc" value="${list.XMMC }">
					    			<button type="button" id="btn_xmmc" name="btn_xz" class="btn btn-link btn-custom">选择</button>
				    			</div>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null number1" style="text-align: right;" name="yjwcse" value="${list.YJWCSE }">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null date" name="wcsj" value="${list.WCSJ }">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null" name="bz" value="${list.BZ }">
				    		</td>
				    	</tr>
				    	</c:forEach>
				    	<tr id="tr_1">
				    		<td class="btn_td">
	                               <div class="addBtn"></div>
				    		</td>
				    		<td>
				    		    <input type="hidden" id="txt_wlbh1" class="form-control input-radius" name="guid" value="">
				    		    <div class="input-group">
					    			<input type="text" id="txt_xmmc" a="a" class="form-control input-radius null" readonly="readonly" style="width:100%;" name="xmmc" value="">
					    			<button type="button" id="btn_xmmc" name="btn_xz" class="btn btn-link btn-custom">选择</button>
				    			</div>
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null number1" style="text-align: right;" name="yjwcse" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null date" name="wcsj" value="">
				    		</td>
				    		<td>
				    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null" name="bz" value="">
				    		</td>
				    	</tr>
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
	//必填验证
	var fields = {fields:{
		xmmc:{validators:{notEmpty:{message:'不能为空'}}}
	} };
	//弹框
	$("#btn_xmmc").click(function(){
		select_commonWin("${ctx}/xmxxt/goXmxxTree?controlId=txt_xmmc","项目信息","920","630");
	});
	function qhe($p){
		  var num = 0.00;
	      var zh = 0.00;
	      $.each($p,function(){
		      zh = $(this).html();
		      if(zh!=""){
			      num = num + parseFloat(zh);
		      }
		  });
	      $q.html(num.toFixed(2));
	}
	//保存
    $("#btn_save").click(function(){
	   	if($("#txt_xmmc").val()==""){
			alert("项目名称不能为空！");
		}else{
		   	var url = "${ctx}/ysgl/bmyssb/doSave?operateType=C&dm=3";
			doSave1($("#myform1"),url,function(val){
				var result = JSON.getJson(val);
				if(result.success){
					alert("保存成功！");
// 					window.location = "${ctx}/ysgl/bmyssb/goListPage?operateType=C&dm=3&bzid=${bzid}";
				}
			});
		}
	});
  //返回按钮
$("#btn_back").click(function(e){
				var sh="${param.sh}";
				var ck="${param.ck}";
				if(sh==''||sh==null||sh==undefined){
					if(ck==''||ck==null||ck==undefined){
					parent.location.href  = "${ctx}/ysgl/bmyssb/goBmyssbPage";
					}else{
						parent.location.href  = "${ctx}/ysgl/bmyssh/goBmysshPage";
					}
				}else{
				parent.location.href  = "${ctx}/ysgl/bmyssh/goBmysshPage";
				}
		});
    //保存方法	
    function doSave1($form, _url, _success, _fail){
//     	var validator = $('#myform1').bootstrapValidator(fields);
//     	if(validator){
//     		validator.bootstrapValidator("fields");
//     		var valid = $('#myform1').data('bootstrapValidator').isValid();
//     		if(!valid){
//     			return;
//     		}
//     	}
        var tbbm = $("[name='tbbm']").val()
    	var json = $("#myform1").serializeObject("guid","bz");
    	var name = $("#txt_tbbm").attr("name");
    	var value = $("#txt_tbbm").val();
    	json[name]=value;
    	var jsonStr = JSON.stringify(json);
    	//console.log("___formJson_"+jsonStr);return;
    	$.ajax({
    		type:"post",
    		url:_url,
    		data: "json="+jsonStr+"&tbbm="+tbbm,
    		success:function(data){
    			_success(data);
    		},
    		error:function(val){
    			alert("抱歉，系统出现问题！");
    		},
    	});	
    }
  	//新增按钮
	var num = 2;
	$(document).on("click","[class*=addBtn]",function(){
		//var wlbh = $("#txt_wlbh").val()
		var $parentTr = $(this).parents("tr").clone();
		$(this).removeClass("addBtn");
		$(this).addClass("deleteBtn");
		console.log("______"+num);
		$parentTr.find("[name='xmmc']").attr("id","txt_xmmc_"+num);
		$parentTr.find("[name='btn_xz']").attr("id","btn_xmmc_"+num);
		
		$parentTr.find("[id^=btn_xmmc]").attr({"sj":"txt_xmmc_"+num,"mc":"txt_zckmid"+num});
		
		
		$parentTr.find("[a='a']").val("");
		$parentTr.find(":input").removeClass("border");
		//$parentTr.find("[id=txt_wlbh]").val(wlbh);
		num++;
		$(this).parents("tr").after($parentTr);
	});
	$(document).on('click',"[id^=btn_xmmc_]",function(){
		var id = $(this).attr("sj");
		console.log("___11__"+id);
		select_commonWin("${ctx}/xmxxt/goXmxxTree?controlId="+id,"项目信息","920","630");
	});
	
	//删除按钮
	$(document).on("click","[class*=deleteBtn]",function(){
		$(this).parents("tr").remove();
	});
	//弹框
	$("#btn_dwmc").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_tbbm","单位信息","920","630");
	});
	$(document).on("blur", ".number1", function(e){
		$(this).Num(4,true,false,e);
		return false;
	});

  	
});
</script>
</body>
</html>
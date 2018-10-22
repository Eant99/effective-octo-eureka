<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>学籍详细信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
.radiodiv{
    border: 1px solid #ccc;
    border-top-right-radius:4px;
    border-bottom-right-radius:4px;
    height: 28px;
    line-height: 28px;
    padding-left: 6px;
}
.sub-title2{
		font-size:14px;
	}
	.number{
	text-align:right;
	}
	.radiodiv{
    border: 1px solid #ccc;
    border-top-right-radius: 4px;
	border-bottom-right-radius:4px;
    height: 25px;
    line-height: 25px;
    padding-left: 6px;
   
    }
	.text-color{
	    color:red!important;
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
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 1em;
	    position: relative;
	    cursor: pointer;
	    top:3px;
	}
	.btn_td{
		width:10px!important;
		height:6mm!important;
		text-align: center;
	}
	.border{
		border:1px solid #a94442;
		background:url("${ctx}/webView/wsbx/rcbx/no_null.bmp") center right no-repeat;;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="czr"  value="${loginId}">
	<input type="hidden" id="txt_guid" name="zbguid" value="${zbguid}"/>
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>添加学生信息</span>
		</div>
		<div class="pull-right" style="margin-top:3px;">
				<div class="sub-title pull-left text-primary">
				<div style="width:26px; height:26px; background-color:green; border-radius:13px;">
        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">1</span>
    			</div>
				</div>
				<div class="sub-title pull-left text-green" style="color:green;">学生基本信息&nbsp;></div>
				
				<div class="sub-title pull-left ">
				<div style="width:26px; height:26px; background-color:#00ACEC; border-radius:13px;">
        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span>
    			</div>
				</div>
				<div class="sub-title pull-left text-primary">学生学籍信息&nbsp;</div>
				
				
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>第二步，学生学籍信息</div>
            		<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
            	<div class="pull-right">
            		<button type="button" class="btn btn-default" id="btn_save" >保存</button>
					<button type="button" class="btn btn-default" id="btn_back" style="background:#00acec;color: white;">返回</button>
        		</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>入学年月</span>
							<input type="text" id="txt_rxny" name="rxny" class="form-control date" value="${xjxx.RXNY}"/>
							<span class='input-group-addon'>
                           		 <i class="glyphicon glyphicon-calendar"></i>
                       		</span>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">学生类别</span>
                           <select id="txt_xslb" class="form-control input-radius select2" name="xslb">
                               <c:forEach items="${xslbList}" var="item">
                               		<option value="${item.DM}" <c:if test="${item.DM == xjxx.XSLB}">selected</c:if>>${item.MC}</option>
                               </c:forEach>
                            </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>所在班号</span>
							<input type="text" id="txt_szbh" name="szbh" class="form-control input-radius window" value="${xjxx.SZBJ}"/>
							<span class="input-group-btn"><button type="button" id="btn_zy1" class="btn btn-link btn-custom">选择</button></span>
							
						</div>
                    </div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon"><span class="required">*</span>所在年级</span>            
	                        <input type="text" id="txt_sznj" class="form-control input-radius window" name="sznj" value="${xjxx.SZNJ}"/>
							<span class="input-group-btn"><button type="button" id="btn_zy2" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>院系所号</span>
	                        <input type="text" id="txt_yxbh" class="form-control input-radius window" name="yxsh" value="${xjxx.SZYX}"/>
							<span class="input-group-btn"><button type="button" id="btn_zy3" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>专&emsp;&emsp;业</span>
							<input type="text" id="txt_zy" class="form-control input-radius window" name="zy" value="${xjxx.SZZY}"/>
							<span class="input-group-btn"><button type="button" id="btn_zy4" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
				</div>
				<div class="row">
				<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>导&ensp;师&ensp;号</span>
                            <input type="text" id="txt_dsh" class="form-control input-radius window" name="dsh" value="${xjxx.DSH}"/>
							<span class="input-group-btn"><button type="button" id="btn_zy5" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
                  <div class="col-md-4">                 	                 	
                  	<div class="input-group">
							<span class="input-group-addon">培养方式</span>
                            <select id="txt_pyfs" class="form-control input-radius select2" name="pyfs">
                              <c:forEach items="${pyfsList}" var="item">
                               		<option value="${item.DM}" <c:if test="${item.DM == xjxx.PYFX }">selected</c:if>>${item.MC}</option>
                               </c:forEach>
                            </select>
					</div>
                  </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">研究方向</span>
                            <select id="txt_yjfx" class="form-control input-radius select2" name="yjfx">
                              <c:forEach items="${yjfxList}" var="item">
                               		<option value="${item.DM}" <c:if test="${item.DM == xjxx.YJFX }">selected</c:if>>${item.MC}</option>
                               </c:forEach>
                            </select>
						</div>
					</div>
				</div>
				<div class="row">
				<div class="col-md-4">
				<div class="input-group">
						<span class="input-group-addon">学科门类</span>
                            <select id="txt_xkml" class="form-control input-radius select2" name="xkml">
                              <c:forEach items="${xkmlList}" var="item">
                               		<option value="${item.DM}" <c:if test="${item.DM == xjxx.XKML }">selected</c:if>>${item.MC}</option>
                               </c:forEach>
                            </select>
                  </div>
                  </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">获得学历方式</span>
                            <select id="txt_hdxlfs" class="form-control input-radius select2" name="hdxlfs">
                             <c:forEach items="${hdxlfsList}" var="item">
                               		<option value="${item.DM}" <c:if test="${item.DM == xjxx.HDXLFS }">selected</c:if>>${item.MC}</option>
                               </c:forEach>
                            </select>
						</div>
					</div>
				
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">是否学分制</span>
							<div class="radiodiv">&nbsp;&nbsp;&nbsp;
                               <input type="radio" name="sfxfz" id="sfxfz" value="01" <c:if test="${xjxx.SFXFZ=='01'}">checked</c:if>>是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                   <input type="radio" name="sfxfz" value="02" <c:if test="${xjxx.SFXFZ=='02'}">checked</c:if>>否
							</div>
						</div>
                    </div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">培养层次</span>
                            <select id="txt_pycc" class="form-control input-radius select2" name="pycc">
                               <c:forEach items="${pyccList}" var="item">
                               		<option value="${item.DM}" <c:if test="${item.DM == xjxx.PYCC }">selected</c:if>>${item.MC}</option>
                               </c:forEach>
                            </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">连读方式</span>
                            <select id="txt_ldfs" class="form-control input-radius select2" name="ldfs">
                                <c:forEach items="${ldfsList}" var="item">
                               		<option value="${item.DM}" <c:if test="${item.DM == xjxx.LDFS }">selected</c:if>>${item.MC}</option>
                               </c:forEach>
                            </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">学生当前状态</span>
							 <select id="txt_xsdqzt" class="form-control input-radius select2" name="xsdqzt">
                                <c:forEach items="${xsdqztList}" var="item">
                               		<option value="${item.DM}" <c:if test="${item.DM == xjxx.XSDQZT }">selected</c:if>>${item.MC}</option>
                               </c:forEach>                          
                            </select>
						</div>
					</div>
			</div>
			<div class="row">
				<div class="pull-center" style="text-align:center;">
            		<button type="button" class="btn btn-default" id="btn_after" style="background:#00acec;color: white;">上一步</button>
            		<button type="button" class="btn btn-default" id="btn_fin" style="background:#00acec;color: white;">完成</button>
        		</div>
			</div>
			</div>
		</div>
	</div>
</form>
<div class="box-panel" id="operate">
		<div class="box-header clearfix">
            	<div class="sub-title2 pull-left text-primary"><i class="fa icon-xiangxi"></i>银行清单
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        </div>
        
			<div class="container-fluid box-content">
			 <form id="myform1" class="myform">
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse: collapse">
		        	<thead id="thead">
				        <tr>
				        	<th style="text-align: center;">操作</th>
				            <th style="width:300px; text-align: center;">开户银行名称</th>
				            <th style="width:300px;text-align: center;">开户银行帐号</th>
				             <th style="width:300px;text-align: center;">人民银行联行号</th>
				        </tr>
					</thead>
				     <tbody id="tbody" class="tbody">   
				    <c:choose>
                          <c:when test="${not empty jsyhzh}">
  								<c:forEach var="jsyhzh" items="${jsyhzh}"> 
							    	<tr id="tr_1">
							    		<td class="btn_td">
				                               <div class="addBtn add"></div>
							    		</td>
							    		<td>
							    		   <input type="hidden" id="txt_guid" class="form-control input-radius" a="a" name="guid" value="${jsyhzh.guid }">
							    		    <input type="hidden" id="txt_jsbh1" class="form-control input-radius" name="jsbh1" value="${zbguid}">
							    			<input type="text" id="txt_khyh1"  class="form-control input-radius null" name="khyh1" value="${jsyhzh.YHMC}">
							    			
							    		</td>
							    		<td>
							    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null" name="khyhzh1" value="${jsyhzh.KHYHH}">
							    		</td>
							    		<td>
							    			<input type="text" id="txt_yhlhh1" a="a" class="form-control input-radius int" name="yhlhh1" value="${jsyhzh.LHH}">
							    		</td>
							    	</tr>
				    			</c:forEach>
                          </c:when>
                          <c:otherwise>
	                          	<tr id="tr_1">
						    		<td class="btn_td">
			                               <div class="addBtn add"></div>
						    		</td>
						    		<td>
						    		   <input type="hidden" id="txt_guid" class="form-control input-radius" a="a" name="guid" value="${zbguid}">
						    		    <input type="hidden" id="txt_jsbh1" class="form-control input-radius" name="jsbh1" value="${zbguid}">
						    			<input type="text" id="txt_khyh1"  class="form-control input-radius null" name="khyh1" value="">
						    		</td>
						    		<td>
						    			<input type="text" id="txt_khyhzh1" a="a" class="form-control input-radius null khyhzh1" name="khyhzh1" value="">
						    		</td>
						    		<td>
							    		<input type="text" id="txt_yhlhh1" a="a" class="form-control input-radius int" name="yhlhh1" value="">
							    	</td>
					    		</tr>
                           </c:otherwise>
                    </c:choose>
				    </tbody>
				</table>
				</form>
			</div>
		</div>   
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//身份证号获取出生年月	
	$("#txt_sfzh").blur(function(){
		var IDcard = $(this).val();
		var length = IDcard.length;
		//身份证号输完自动填写出生日期
		if(length == 18){
        var birthday = IDcard.substr(6, 4)+ "-" + IDcard.substr(10, 2) + "-" + IDcard.substr(12, 2);		
		$("#txt_csrq").val(birthday);
		}      
	});
});
$(function(){
	 //班号弹窗
	$("#btn_zy1").click(function(){
		select_commonWin("${ctx}/xsxx/goBjPage?controlId=txt_szbh","班级信息","920","630");
    });
	//年级弹窗
	$("#btn_zy2").click(function(){
		select_commonWin("${ctx}/xsxx/goNjPage?controlId=txt_sznj","年级信息","920","630");
    });
	//院系弹窗
	$("#btn_zy3").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_yxbh","院系信息","920","630");	
    });
	//专业弹窗
	$("#btn_zy4").click(function(){
		select_commonWin("${ctx}/xsxx/goZyPage?controlId=txt_zy","专业信息","920","630");
    });
	//导师弹窗 
	$("#btn_zy5").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_dsh","人员信息","920","630");
    });
	//新增按钮
	var num = 2;
	$(document).on("click","[class*=addBtn]",function(){
		var jsbh = $("#txt_jsbh1").val();
		var $parentTr = $(this).parents("tr").clone();
		$(this).removeClass("addBtn");
		$(this).addClass("deleteBtn");
		$parentTr.find(":input").val("");
		$parentTr.find(":input").removeClass("border");
		//$parentTr.find("[id=txt_wlbh]").val(wlbh);
		$parentTr.attr("id","tr_"+num);
		$parentTr.find("#txt_jsbh1").val(jsbh);
		num++;
		$(this).parents("tr").after($parentTr);
	});
	//删除按钮
	$(document).on("click","[class*=deleteBtn]",function(){
		$(this).parents("tr").remove();
	});
  //联想输入提示院系
	$("#txt_szyx").bindChange("${ctx}/suggest/getXx","D");
	//联想输入提示专业
	$("#txt_zy").bindChange("${ctx}/suggest/getXx","ZY");
	//联想输入提示班级
	$("#txt_szbh").bindChange("${ctx}/suggest/getXx","BJ");
	//联想输入提示年级
	$("#txt_sznj").bindChange("${ctx}/suggest/getXx","NJ");
	
	$("#txt_dsh").bindChange("${ctx}/suggest/getXx","R");
	
	$("#txt_yxbh").bindChange("${ctx}/suggest/getXx","D");
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
          rxny:{validators:{notEmpty: {message: '入学年月不能为空'}}},
          szbh:{validators:{ notEmpty: {message: '所在班号不能为空'}}},
          sznj:{validators: {notEmpty: {message: '所在年级不能为空'}}},
          yxsh:{validators: {notEmpty: {message: '院系所号不能为空'}}},
          zy:{validators: {notEmpty: {message: '专业不能为空'}}},
          xkml:{validators: {notEmpty: {message: '学科门类不能为空'}}},
          dsh:{validators: {notEmpty: {message: '导师号不能为空'}}}         
          }
	      });
	//上一步
	$("#btn_after").click(function(){
		var zbguid=$("#txt_guid").val();
		doSave1(validate,"myform","${ctx}/xsxx/doSaveXj?zbguid="+zbguid,function(val){
			if(val.success){
				doOperate("${ctx}/xsxx/goXsxxPage?guid="+zbguid,"U");
			}
		});
	});
	//保存按钮,完成按妞
	$("#btn_fin").click(function(){
		var zbguid = $("#txt_guid").val();
		doSave(validate,"myform","${ctx}/xsxx/doSaveXj?zbguid="+zbguid,function(val){
			if(val.success){
				tag = true;
// 				$("#operateType").val("C");
				next();
			}
		});
	});
	$("#btn_save").click(function(){
		var zbguid = $("#txt_guid").val();
		doSave(validate,"myform","${ctx}/xsxx/doSaveXj?zbguid="+zbguid,function(val){
			if(val.success){
				tag = true;
// 				$("#operateType").val("C");
				next();
			}
		});
	});
	function doSave1(_validate, _formId, _url, _success, _fail){
		var index;
		var valid;
		if(_validate){
			_validate.bootstrapValidator("validate");
			valid = $('#' + _formId).data('bootstrapValidator').isValid();
		} else {
			valid = true;
		}
		if(valid){
			$.ajax({
				type:"post",
				url:_url,
				data:arrayToJson($('#' + _formId).serializeArray()),
				success:function(val){
					close(index);
					var data = JSON.getJson(val);
					if(data.success){
						$("#operateType").val("U");
						if(_success != ""){
							_success(data);
						}
					} else {
						if(_fail != ""){
							_fail(data);
						}
					}
				},
				error:function(val){
					close(index);
					alert(getPubErrorMsg());
				},
				beforeSend:function(val){
					index = loading(2);
				}
			});
		}
	}

	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/xsxx/goXsxxListPage?";
	});
	//刷新按钮
	$(".reload").click(function(){
		 window.location.reload();
	});
});
$.fn.serializeObject1 = function(start,end){
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
        }else{
        	o[this.name] = this.value;
        }
    });
    	console.log(JSON.stringify(f));
    return f;
};
function next(){
	var lastval = $(".myform tr:last").find("[name=khyh1]").val();
	if(lastval==""){
		$(".myform tr:last").remove();
	}
	var json = $('#myform1').serializeObject1("guid","yhlhh1");  //json对象	
	var jsonStr = JSON.stringify(json);  //json字符串
	var jsbh ="${zbguid}";//guid
	//var jsbh = $("#txt_jsbh1").val(); //guid
	var zds = $("[name='khyhzh1']");//开户银行账号
	var lhh = $("[name='yhlhh1']");//银行联行号
	var zdid = [];  //所有账号
	var lhhid = [];  //所有联行号
	zds.each(function(){
		zdid.push($(this).val());
	});
	lhh.each(function(){
		lhhid.push($(this).val());
	});
	var xx = "tg";
	for (var i=0;i<zdid.length;i++) {
		var aa = zdid[i];
			if (aa.length=="16"||aa.length=="19"||aa.length=="22") {
				console.log("abc");	
			}else{
				xx = "th";
				alert("请输入正确的银行卡号或者存折！");
				console.log("def");	
			}
	}
	//判断是否是建行  填写银行联行号
	for (var i=0;i<zdid.length;i++) {
		var aa = zdid[i]; //每个银行账号
		var bankCode = bankCardAttribution(aa); //每个银行的标志符  建行 CCB
		var code = bankCode[0];
		if (aa!="" && code == "CCB") {
			var lhhVal = lhhid[i];	
			if(lhhVal==""){
			}else if(lhhVal.length != "12"){
				alert("请输入第"+(i+1)+"行正确的人民银行联行号");
				xx = "th";
			}
		}
		if (aa!="" && code != "CCB") {
			var lhhVal = lhhid[i];		
			console.log(lhhVal.length);
			if(lhhVal==""){
				alert("请输入第"+(i+1)+"行的人民银行联行号");
				xx = "th";
			}else if(lhhVal.length != "12"){
				alert("请输入第"+(i+1)+"行正确的人民银行联行号");
				xx = "th";
			}
		}
	}
	if(xx=="tg"){
		$.ajax({
		        url:"${ctx}/xsxx/editJsyhzh?jsbh="+jsbh,
	   			type:"post",
	   			data:"json="+jsonStr,
	   			success:function(data){
				  alert("保存成功！");  	
				  window.location.href = "${ctx}/xsxx/goXsxxListPage";
	   			}
		}); 
	}
}
</script>
</body>
</html>
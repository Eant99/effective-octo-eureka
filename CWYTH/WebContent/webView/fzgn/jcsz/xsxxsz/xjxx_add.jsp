<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>学籍详细信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="czr"  value="${loginId}">
	<input type="hidden" id="txt_xh" name="xh" value="${param.guid }"/>
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
							<input type="text" id="txt_rxny" name="rxny" class="form-control date window"/>
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
                               		<option value="${item.DM }">${item.MC }</option>
                               </c:forEach>
                            </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>所在班号</span>
							<input type="text" id="txt_szbh" name="szbh" class="form-control input-radius window"/>
							<span class="input-group-btn"><button type="button" id="btn_zy1" class="btn btn-link btn-custom">选择</button></span>
							
						</div>
                    </div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon"><span class="required">*</span>所在年级</span>            
	                        <input type="text" id="txt_sznj" class="form-control input-radius window" name="sznj"/>
							<span class="input-group-btn"><button type="button" id="btn_zy2" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>院系所号</span>
	                        <input type="text" id="txt_yxbh" class="form-control input-radius window" name="yxbh"/>
							<span class="input-group-btn"><button type="button" id="btn_zy3" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>专&emsp;&emsp;业</span>
							<input type="text" id="txt_zy" class="form-control input-radius window" name="zy"/>
							<span class="input-group-btn"><button type="button" id="btn_zy4" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>导&ensp;师&ensp;号</span>
                            <input type="text" id="txt_dsh" class="form-control input-radius window" name="dsh" />
							<span class="input-group-btn"><button type="button" id="btn_zy5" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
                  <div class="col-md-4">                 	                 	
                  	<div class="input-group">
							<span class="input-group-addon">培养方式</span>
                            <select id="txt_pyfs" class="form-control input-radius select2" name="pyfs">
                              <c:forEach items="${pyfsList}" var="item">
                               		<option value="${item.DM }">${item.MC }</option>
                               </c:forEach>
                            </select>
					</div>
                  </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">研究方向</span>
                            <select id="txt_yjfx" class="form-control input-radius select2" name="yjfx">
                              <c:forEach items="${yjfxList}" var="item">
                               		<option value="${item.DM }">${item.MC }</option>
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
                               		<option value="${item.DM }">${item.MC }</option>
                               </c:forEach>
                            </select>
                  </div></div>
					
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">获得学历方式</span>
                            <select id="txt_hdxlfs" class="form-control input-radius select2" name="hdxlfs">
                             <c:forEach items="${hdxlfsList}" var="item">
                               		<option value="${item.DM }">${item.MC }</option>
                               </c:forEach>
                            </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">是否学分制</span>&emsp;
							<input type="radio" name="sfxfz" value="1" checked />是&emsp;&emsp;
							<input type="radio" name="sfxfz" value="2" />否
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">培养层次</span>
                            <select id="txt_pycc" class="form-control input-radius select2" name="pycc">
                               <c:forEach items="${pyccList}" var="item">
                               		<option value="${item.DM }">${item.MC }</option>
                               </c:forEach>
                            </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">连读方式</span>
                            <select id="txt_ldfs" class="form-control input-radius select2" name="ldfs">
                                <c:forEach items="${ldfsList}" var="item">
                               		<option value="${item.DM }">${item.MC }</option>
                               </c:forEach>
                            </select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">学生当前状态</span>
							 <select id="txt_xsdqzt" class="form-control input-radius select2" name="xsdqzt">
                                <c:forEach items="${xsdqztList}" var="item">
                               		<option value="${item.DM }">${item.MC }</option>
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
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var tag = false;
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
	/* //班号弹窗
	$("#btn_zy1").click(function(){
		select_commonWin("${ctx}/xsxx/goBjPage?controlId=txt_szbh","班级信息","920","630");
    });
	//年级弹窗
	$("#btn_zy2").click(function(){
		select_commonWin("${ctx}/xsxx/goNjPage?controlId=txt_sznj","年级信息","920","630");
    });
	//院系弹窗
	$("#btn_zy3").click(function(){
		select_commonWin("${ctx}/xsxx/goYxPage?controlId=txt_yxbh","院系信息","920","630");
    });
	//专业弹窗
	$("#btn_zy4").click(function(){
		select_commonWin("${ctx}/xsxx/goZyPage?controlId=txt_zy","专业信息","920","630");
    });
	//导师弹窗
	$("#btn_zy5").click(function(){
		select_commonWin("${ctx}/xsxx/goDsPage?controlId=txt_dsh","导师信息","920","630");
    });  */

	
	
 	/* //联想输入提示院系
	$("#txt_szyx").bindChange("${ctx}/suggest/getXx","D");
	//联想输入提示专业
	$("#txt_zy").bindChange("${ctx}/suggest/getXx","ZY");
	//联想输入提示班级
	$("#txt_bj").bindChange("${ctx}/suggest/getXx","BJ");
	//联想输入提示年级
	$("#txt_nj").bindChange("${ctx}/suggest/getXx","NJ"); */
	 
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
          rxny:{validators:{notEmpty: {message: '入学年月不能为空'}}},
          szbh:{validators:{ notEmpty: {message: '所在班号不能为空'}}},
          sznj:{validators: {notEmpty: {message: '所在年级不能为空'}}},
          yxsh:{validators: {notEmpty: {message: '院系所号不能为空'}}},
          zy:{validators: {notEmpty: {message: '专业不能为空'}}},
          dsh:{validators: {notEmpty: {message: '导师号不能为空'}}}         
          }
	      });
	//完成按妞
	$("#btn_fin").click(function(){
	      if(tag){
	    	  alert("添加成功！");
			window.location.href = "${ctx}/xsxx/goXsxxListPage";
			}else{
	         doSave1(validate,"myform","http://localhost/apis/xsxx/editXjxx",function(val){
	        	 alert("添加成功！");
				window.location.href = "${ctx}/xsxx/goXsxxListPage";	
			},function(val){
				alert("保存失败！请稍后重试！");
			});
		}	
	});
	//上一步
	var guid;
	$("#btn_after").click(function(){
		guid = $("#txt_xh").val();
		window.location.href = "${ctx}/xsxx/goXsxxEditPage?guid="+guid;
	});
	//保存
	$("#btn_save").click(function(){
		doSave1(validate,"myform","http://localhost/apis/xsxx/editXjxx",function(val){
			tag = true;
			alert("保存成功！");
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
					//alert(data.msg);
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
</script>
</body>
</html>
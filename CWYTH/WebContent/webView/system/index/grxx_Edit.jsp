<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>编辑个人信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body style="background-color: white;">
<form id="myform" class="form-horizontal" action="" method="post">
	<div class="container-fluid dialog-body">
		<div class="row">
			<div class="col-md-12">
				<div class="input-group">
					<!-- 带有必填项的label元素 -->
					<span class="input-group-addon">工&emsp;&emsp;号</span>
					<input type="text" id="txt_rygh" class="form-control input-radius" name="rygh" value="${ryb.rygh}" readonly/>
					<input type="hidden" name="rybh"  value="${ryb.rybh}"/>
				</div>
             </div>
         </div>
         <div class="row">  
             <div class="col-md-12">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>姓&emsp;&emsp;名</span>
                    <input type="text" id="txt_xm" class="form-control input-radius" name="xm" value="${ryb.xm}" readonly/>
				</div>
			</div>
		</div>
		<div class="row">	
			<div class="col-md-12">
				<div class="input-group">
					<span class="input-group-addon">性&emsp;&emsp;别</span>
					<select id="drp_xb" class="form-control input-radius" name="xb"> 
						<option value="1" <c:if test="${ryb.xb==1}">selected</c:if>>男</option>
	                    <option value="0" <c:if test="${ryb.xb==0}">selected</c:if>>女</option>
					</select>
				</div>
			</div>
		  </div>
         <div class="row"> 	
			<div class="col-md-12">
				<div class="input-group">
					<span class="input-group-addon">出生日期</span>
					<input type="text" id="txt_csrq" class="form-control date" name="csrq" value="<fmt:formatDate value="${ryb.CSRQ}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="出生日期">
					<span class='input-group-addon'>
						<i class="glyphicon glyphicon-calendar"></i>
					</span>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="input-group">
					<span class="input-group-addon">身份证号</span>
                    <input type="text" id="txt_sfzh" class="form-control input-radius" name="sfzh" value="${ryb.sfzh}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="input-group">
					<span class="input-group-addon">文化程度</span>
                    <select id="txt_whcd" class="form-control input-radius" name="whcd">
                        <c:forEach var="whcdList" items="${whcdList}">
                        	<option value="${whcdList.DM}" <c:if test="${ryb.whcd == whcdList.DM}">selected</c:if>>${whcdList.MC}</option>
                        </c:forEach>
                    </select>
				</div>
            </div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="input-group">
					<span class="input-group-addon">所在单位</span>
                    <input type="text" id="txt_dwbh" class="form-control input-radius" name="dwbh" value="${ryb.dwmc}" readonly/>
				</div>
			</div>
		</div>
		<div class="row">	
			<div class="col-md-12">
				<div class="input-group">
					<span class="input-group-addon">联系电话</span>
                    <input type="text" id="txt_lxdh" class="form-control input-radius" name="lxdh" value="${ryb.lxdh}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="input-group">
					<span class="input-group-addon">QQ号码</span>
                    <input type="text" id="txt_qq" class="form-control input-radius" name="qq" value="${ryb.qq}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="input-group">
					<span class="input-group-addon">E-mail地址</span>
                    <input type="text" id="txt_mail" class="form-control input-radius" name="mail" value="${ryb.mail}"/>
				</div>
			</div>
		</div>
	</div>
	<div class='page-bottom clearfix'>
			<div class="pull-right">
				<button class="btn btn-default" type="button" id="btn_save">
				    <i class="fa icon-save"></i>保存
				</button>
				<button type="button" class="btn btn-default" id="btn_back"> 
					关闭 
				</button> 
           </div> 
     </div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//关闭按钮
	$("#btn_back").click(function(){
		getTopFrame().closeAll();
	});
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
        	xm:{validators:{notEmpty: {message: '人员姓名不能为空'}}},
            mail:{validators:{emailAddress:{message: '请输入有效的E-mail地址'}}},
            lxdh:{validators:{phone:{message: '请输入有效的联系电话'}}},
            sfzh:{validators:{cardId:{message: '请输入有效的身份证号'}}}}
	});
	//保存按钮
	$("#btn_save").click(function(){
		doSave(validate,"myform","${pageContext.request.contextPath}/grsz/doSavePersonInfo",function(val){
			getIframWindow("${param.pname}").location.href = getIframWindow("${param.pname}").location.href;
			var winId = getTopFrame().layer.getFrameIndex(window.name);
			close(winId);
		},function(val){
		});
	});
});
</script>
</body>
</html>
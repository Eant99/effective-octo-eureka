<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>银行帐号管理</title>
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
			<div class="search-simple">	
				<div class="form-group"> 
<!-- 					<label>填报部门</label>					 -->
<!--  					<input type="text" readonly class="form-control input-radius" name="sbnd"  id="txt_sbnd" placeholder="请输入填报部门"> -->
<!--  					<span class="input-group-btn"><button type="button" id="btn_tbbm" class="btn btn-link btn-custom">选择</button></span> -->
				 </div> 
			</div>
		</form>
	</div>
	<div class="container-fluid" style="margin-bottom: 150px">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<form id="myform1">
				<center><h4><strong>基本信息表</strong></h4></center>
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse: collapse">
		        	<thead id="thead">
				        <tr>
				            <th style="text-align: center;">项目</th>
				            <th style="text-align: center;">基本信息值</th>
				            <th style="text-align: center;">项目</th>
				            <th style="text-align: center;">基本信息值</th>
				            <th style="text-align: center;">项目</th>
				            <th style="text-align: center;">基本信息值</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	<tr>
				            <th style="vertical-align: middle;">单位名称</th>
				            <th>
				            	<div class="input-group">
				            		<input type="text" id="txt_dwmc" windth="90%" class="form-control input-radius" name="dwmc" readonly="readonly" value="${bmmc }"/>
<!-- 				            		<span class="input-group-btn"><button type="button" id="btn_dwmc" class="btn btn-link btn-custom">选择</button></span> -->
				               	</div>
				            </th>
				            <th style="vertical-align: middle;">单位负责人</th>
				            <th>
					            <div class="input-group">
					            	<input type="text" id="txt_dwfzr" class="form-control input-radius"  name="dwfzr" readonly value="${info.dwfzrmc }"/>
					            	<span class="input-group-btn"><button type="button" id="btn_dwfzr" class="btn btn-link btn-custom">选择</button></span>
					            </div>
				            </th>
				            <th style="vertical-align: middle;">财务负责人</th>
				            <th>
					            <div class="input-group">
					            	<input type="text" id="txt_cwfzr" class="form-control input-radius" name="cwfzr" readonly value="${info.cwfzrmc }"/>
					            	<span class="input-group-btn"><button type="button" id="btn_cwfzr" class="btn btn-link btn-custom">选择</button></span>
					            </div>
				            </th>
				        </tr>
				         <tr>
				            <th style="vertical-align: middle;">填报人</th>
				            <th>
				            <div class="input-group">
				            	<c:if test="${empty info.tbrmc}">
					            	<input type="text" id="txt_tbr" class="form-control input-radius" name="tbr" readonly value="${rymc}"/>
					            </c:if>
					            <c:if test="${not empty info.tbrmc}">
					            	<input type="text" id="txt_tbr" class="form-control input-radius" name="tbr" readonly value="${info.tbrmc}"/>
					            </c:if>
					            <span class="input-group-btn"><button type="button" id="btn_sbr" class="btn btn-link btn-custom">选择</button></span>
				            </div>
				            </th>
				            <th style="vertical-align: middle;">电话号码</th>
				            <th>
				            	<input type="text" id="txt_dhhm" class="form-control input-radius" style="text-align: right;" name="dhhm"  value="${info.dhhm }"/>
				            </th>
				            <th style="vertical-align: middle;">教师人数</th>
				            <th><input type="text" id="txt_jsrs" class="form-control input-radius " style="text-align: right;" name="jsrs"  value="${info.jsrs }"/></th>
				        </tr>
				         <tr>
				            <th style="vertical-align: middle;">兼职教师人数</th>
				            <th><input type="text" id="txt_jzjsrs" class="form-control input-radius" style="text-align: right;" name="jzjsrs"  value="${info.jzjsrs }"/></th>
				            <th style="vertical-align: middle;">外聘教师人数</th>
				            <th><input type="text" id="txt_wpjsrs" class="form-control input-radius" style="text-align: right;"  name="wpjsrs"  value="${info.wpjsrs }"/></th>
				            <th style="vertical-align: middle;">专业数</th>
				            <th><input type="text" id="txt_zys" class="form-control input-radius" style="text-align: right;" name="zys"  value="${info.zys }"/></th>
				        </tr>
				         <tr>
				            <th style="vertical-align: middle;">班级数</th>
				            <th><input type="text" id="txt_bjs" class="form-control input-radius" style="text-align: right;" name="bjs"  value="${info.bjs }"/></th>
				            <th style="vertical-align: middle;">学生人数</th>
				            <th><input type="text" id="txt_xsrs" class="form-control input-radius" style="text-align: right;" name="xsrs"  value="${info.xsrs }"/></th>
				            <th></th>
				            <th></th>
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
	//弹框
	$("#btn_dwmc").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_dwmc","单位信息","920","630");
	});
	//弹框
	$("#btn_dwfzr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_dwfzr","人员信息","920","630");
    });
    //弹框
	$("#btn_cwfzr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_cwfzr","人员信息","920","630");
    });
  	//弹框
	$("#btn_sbr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_tbr","人员信息","920","630");
	});
	//保存按钮
	$("#btn_save").click(function(e){
		if($("#txt_tbr").val()==""){
			alert("填报人不能为空！");
		}else{
			doSave(validate,"myform1","${ctx}/ysgl/bmyssb/doSave?operateType=C&dm=2&bzid=${bzid}",function(val){
				if(val.success){
					alert(val.msg);
// 					window.location.href  = "${ctx}/ysgl/bmyssb/goEditPage?dm=2&bzid=${bzid}";
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
	//必填验证
	var validate = $('#myform1').bootstrapValidator({fields:{
		dwmc:{validators:{notEmpty:{message:'不能为空'}}},
		dwfzr:{validators:{notEmpty:{message:'不能为空'}}},
		nd:{validators:{notEmpty: {message: '不能为空'}}},
    }});
});
</script>
</body>
</html>
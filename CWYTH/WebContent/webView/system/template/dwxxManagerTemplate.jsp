<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>单位信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<!----------------------------------------------页头开始------------------------------------------------------->
	<div class='page-header'>
		<!-- 该页面的title 开始 -->
        <div class="pull-left">
            <span class="page-title text-primary">
            	<i class='fa icon-xiangxixinxi'></i>
				编辑单位信息
			</span>
		</div>
		<!-- 该页面的title 结束-->
		<!-- 保存和返回按钮 开始 -->
        <div class="pull-right">
			<button type='button' class="btn btn-default" id="btn_save">
				<i class="fa icon-save"></i>
				保存
			</button>
			<button type="button" class="btn btn-default" id="btn_back">
				<i class="fa icon-back"></i>
				返回
			</button>
        </div>
        <!-- 保存和返回按钮 结束 -->
    </div>
    <!-- --------------------------------------------页头结束----------------------------------------------------- -->
    <!---------------------------------------- box 页面的主体部分 开始  --------------------------------------------------->
	<div class="box">
		<div class="box-panel">
			<!-- 子标题 开始 -->
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-xiangxi"></i>
            		单位信息
            	</div>
            	<!-- 右侧折叠按钮 -->
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
        	<!-- 子标题 结束 -->
			<hr class="hr-normal">
			<!-- 单位信息详细信息开始 -->
			<div class="container-fluid box-content">
				<!-- row 表示一行，col-md-x 表示一列占几个位置，一行一共12个位置,此处表示一行三列   开始 -->
				<div class="row">
					<!-- input-group 表示一个表单组，包含一个label和1个input或者1个select，或者1个input-group  开始-->
					<div class="col-md-4">
						<div class="input-group">
							<!-- 带有必填项的label元素 -->
							<span class="input-group-addon"><span class="required">*</span>单位编号</span>
							<input type="text" id="txt_bmh" class="form-control input-radius" name="bmh"  value="${dwb.BMH}"/>
							<input type="hidden" name="dwbh"  value="${dwb.DWBH}"/>
						</div>
					</div>
					<!-- input-group 表示一个表单组，包含一个label和1个input或者1个select，或者1个input-group  结束-->
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单位名称</span>
							<input type="text" id="txt_mc" class="form-control input-radius" name="mc" value="${dwb.MC}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<!-- 不带必填项的label元素 -->
							<span class="input-group-addon">单位简称</span>
							<input type="text" id="txt_jc" class="form-control input-radius" name="jc" value="${dwb.JC}"/>
						</div>
					</div>
				</div>
				<!-- row 表示一行，col-md-x 表示一列占几个位置，一行一共12个位置      结束 -->
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单位性质</span>
							<select id="drp_dwxx" class="form-control input-radius" name="dwxz">
							</select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" >地&emsp;&emsp;址</span>
							<input type="text" id="txt_dz" class="form-control input-radius" name="dz" value="${dwb.DZ}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单位领导</span>
							<input type="text" id="txt_dwld" class="form-control input-radius" name="dwld" value="${dwb.DWLDMC}">
							<span class="input-group-btn"><button type="button" id="btn_dwld" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">分管领导</span>
							<input type="text" id="txt_fgld" class="form-control input-radius" name="fgld" id="fgld" value="${dwb.FGLDMC}">
							<span class="input-group-btn"><button type="button" id="btn_fgld" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">上级单位</span>
							<input type="text" id="txt_sjdw" class="form-control input-radius window"name="sjdw" id="sjdw" value="${dwb.SJDWMC}">
							<span class="input-group-btn"><button type="button" id="btn_sjdw" class="btn btn-link btn-custom">选择</button></span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">建立日期</span>
							<input type="text" id="txt_jlrq" class="form-control date" name="jlrq" value="<fmt:formatDate value="${dwb.JLRQ}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="单位成立日期">
							<span class='input-group-addon'>
								<i class="glyphicon glyphicon-calendar"></i>
							</span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">实验室标志</span>
							<select id="drp_sysbz" class="form-control input-radius" name="sysbz"> 
								<option value="1">非实验室</option>
								<option value="0">实验室</option>
							</select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">单位状态</span>
							<select id="drp_dwzt" class="form-control input-radius" name="dwzt">
								<option value="1" <c:if test="${dwb.DWZT == 1}">selected</c:if>>正常</option>
								<option value="0" <c:if test="${dwb.DWZT == 0}">selected</c:if>>禁用</option>
							</select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">排序序号</span>
							<input type="text" id="txt_pxxh" class="form-control input-radius" name="pxxh" value="${dwb.PXXH}">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-8">
						<div class="input-group">
							<span class="input-group-addon">备&emsp;&emsp;注</span>
							<textarea id="txt_bz" class="form-control" name="bz" ></textarea>
						</div>
					</div>
				</div>
			</div>
			<!-- 单位信息详细信息结束 -->
		</div>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-zichanchuzhi01"></i>
            		实验室信息
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">实验室类型</span>
							<select id="drp_syslx" class="form-control input-radius" name="syslx">
								<c:forEach var="syslxlist" items="${syslxlist}"> 
									<option value="${syslxlist.DM}" <c:if test="${dwb.SYSLX == syslxlist.DM}">selected</c:if>>${syslxlist.MC}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">实验室类别</span>
							<select id="drp_syslb" class="form-control input-radius" name="syslb">
								<c:forEach var="syslblist" items="${syslblist}"> 
									<option value="${syslblist.DM}" <c:if test="${dwb.SYSLB == syslblist.DM}">selected</c:if>>${syslblist.MC}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">所属学科</span>
							<select id="drp_ssxk" class="form-control input-radius" name="ssxk">
								<c:forEach var="ssxklist" items="${ssxklist}"> 
									<option value="${ssxklist.DM}" <c:if test="${dwb.SSXK == ssxklist.DM}">selected</c:if>>${ssxklist.MC}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">实验室面积</span>
							<input type="text" id="txt_sysmj" class="form-control input-radius int text-right" name="sysmj" value="<fmt:formatNumber value='${dwb.SYSMJ}' pattern='0.00'/>">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">实验室级别</span>
							<select id="drp_sysjb" class="form-control input-radius" name="sysjb">
								<c:forEach var="sysjblist" items="${sysjblist}"> 
									<option value="${sysjblist.DM}" <c:if test="${dwb.SYSJB == sysjblist.DM}">selected</c:if>>${sysjblist.MC}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">实验室归属</span>
							<select id="drp_sysgs" class="form-control input-radius" name="sysgs">
								<c:forEach var="sysgslist" items="${sysgslist}"> 
									<option value="${sysgslist.DM}" <c:if test="${dwb.SYSGS == sysgslist.DM}">selected</c:if>>${sysgslist.MC}</option>
								</c:forEach>
							</select>
						</div>	
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- ---------------------------------------------------- box 页面的主体部分 结束  ---------------------------------------->
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	sysbz();
	//查看页面时处理函数
	if($("[name='operateType']").val()=='L'){
		$("input,select,textarea").attr("readonly","true");
		$("select").attr("disabled","true");
	}
	//日期控件，如果想要日期到具体的时间，可以自己加上参数format:'yyyy-mm-dd hh:ii'
	$(".date").datetimepicker();
	//实验室标志按钮
	$("[name='sysbz']").change(function(){
		sysbz();
	});
	//联想输入开始
	$("#txt_sjdw").bindChange("${pageContext.request.contextPath}/suggest/getXx","D");
	
	//弹窗开始
	$("#btn_sjdw").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/window/dwxx/window.jsp?controlId=txt_sjdw","单位信息","920","630");
    });
	$("#btn_dwld").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/window/ryxx/window.jsp?controlId=txt_dwld","人员信息","920","630");
    });
	$("#btn_fgld").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/window/ryxx/window.jsp?controlId=txt_fgld","人员信息","920","630");
    });
	//弹窗结束
	
	//返回按钮
	$("#back").click(function(){
		window.location.href  = "${pageContext.request.contextPath}/webView/system/template/dwxxListTemplate.jsp";
	});
	var validate = $('#myform').bootstrapValidator({
        fields: {
            dwbh: {
                validators: {
                    notEmpty: {message: '不能为空'}
                }
            },
            mc:{
            	validators:{
            		notEmpty:{message:'不能为空'}
            	}
            },
            sjdw:{
            	validators:{
            		notEmpty:{message:'不能为空'}
            	}
            },
            jlrq:{
            	validators:{
            		notEmpty:{message:'不能为空'}
            	}
            }
        }
    });
	//保存按钮
	$("#btn_save").click(function(e){
		doSave(validate,"myform","${pageContext.request.contextPath}/dwxx/saveDwxx",function(val){
			//......
		},function(val){
			$("#bmh").val("");
			$("#bmh").focus();
		});
	}); 
});
function sysbz(){
	var $this = $("[name='sysbz']").val();
	if($this == '0'){
		$("#sysxx").show();
	}else if($this == '1'){
		$("[name='syslb']").val("");
		$("[name='sysmj']").val("");
		$("[name='sysjb']").val("");
		$("[name='sysgs']").val("");
		$("#sysxx").hide();
	}
}
</script>
</body>
</html>
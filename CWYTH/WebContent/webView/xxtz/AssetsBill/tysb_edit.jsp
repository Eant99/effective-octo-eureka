<%@page import="com.googosoft.util.Validate"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产建账详细信息（通用设备）</title>
</head> 
<body>
		<!-- row 表示一行，col-md-x 表示一列占几个位置，一行一共12个位置,此处表示一行三列   开始 -->
		<div class="row">
			<!-- input-group 表示一个表单组，包含一个label和1个input或者1个select，或者1个input-group  开始-->
			<div class="col-md-4">
				<div class="input-group">
					<!-- 带有必填项的label元素 <span class="required">*</span>-->
					<span class="input-group-addon">发&ensp;票&ensp;号</span>
					<input type="text" id="txt_fph" class="form-control input-radius" name="fph"  value="${yshd.FPH}"/>
				</div>
			</div>
			<!-- input-group 表示一个表单组，包含一个label和1个input或者1个select，或者1个input-group  结束-->
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">现&emsp;&emsp;状</span>
					<select id="drp_xz" class="form-control input-radius" name="xz" disabled>
                        <c:forEach var="xzlist" items="${xzlist}"> 
                             <option value="${xzlist.DM}" <c:if test="${yshd.XZ == xzlist.DM}">selected</c:if>>${xzlist.MC}</option>
						</c:forEach>
                   </select>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<!-- 不带必填项的label元素 -->
					<span class="input-group-addon">价值类型</span>
					<select id="drp_jzxs" class="form-control input-radius" name="jzxs">
                        <c:forEach var="jzxslist" items="${jzxslist}"> 
                             <option value="${jzxslist.DM}" <c:if test="${yshd.JZXS == jzxslist.DM}">selected</c:if>>${jzxslist.MC}</option>
						</c:forEach>
                   </select>
				</div>
			</div>
		</div>
		<!-- row 表示一行，col-md-x 表示一列占几个位置，一行一共12个位置      结束 -->
 		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon" ><span class="required">*</span>单&emsp;&emsp;价</span>
					<input type="text" id="txt_dj" class="form-control input-radius text-right number" name="dj" value="${yshd.DJ}" onkeyup="clearNoNumB(this);" readonly="readonly"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>数&emsp;&emsp;量</span>
					<input type="text" id="txt_shl" class="form-control input-radius text-right" name="shl" value="${yshd.SHL}" onkeyup="clearNoNumA(this);" readonly="readonly"/>
				</div>
			</div>
			<div class="col-md-4"> 
				<div class="input-group">
					<span class="input-group-addon" >总&emsp;&emsp;价</span>
					<input type="text" id="txt_zzj" class="form-control input-radius text-right number" name="zzj" value="${yshd.ZZJ}" readonly="readonly"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">计量单位</span>
					<select id="drp_jldw" class="form-control input-radius" name="jldw">
                        <c:forEach var="jldwlist" items="${jldwlist}"> 
                             <option value="${jldwlist.DM}" <c:if test="${yshd.JLDW == jldwlist.DM}">selected</c:if>>${jldwlist.MC}</option>
						</c:forEach>
                   </select>
				</div>
			</div> 
			<div class="col-md-4"> 
				<div class="input-group">
					<span class="input-group-addon" id="span_cgr">采&ensp;购&ensp;人</span>
					<input type="text" id="txt_cgr" class="form-control input-radius" name="cgr"  value="${yshd.CGR}">
					<c:if test="${operateType == 'U' or operateType == 'C'}">
					<span class="input-group-btn"><button type="button" id="btn_cgr" class="btn btn-link btn-custom">选择</button></span>
					</c:if>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>申购单位</span>
					<input type="text" id="txt_shgdw" class="form-control input-radius" name="shgdw"  value="${yshd.SHGDW}" readonly>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">采购组织形式</span>
                    <select id="drp_cgzzxs" class="form-control input-radius" name="cgzzxs">
                        <c:forEach var="cgzzxslist" items="${cgzzxslist}"> 
                             <option value="${cgzzxslist.DM}" <c:if test="${yshd.CGZZXS == cgzzxslist.DM}">selected</c:if>>${cgzzxslist.MC}</option>
						</c:forEach>
                   </select>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">入账形式</span>
					<select id="drp_jzlx" class="form-control input-radius" name="jzlx">
						<c:forEach var="jzlxlist" items="${jzlxlist}"> 
                             <option value="${jzlxlist.DM}" <c:if test="${yshd.JZLX == jzlxlist.DM}">selected</c:if>>${jzlxlist.MC}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">预算项目编号</span>
					<input type="text" id="txt_xmh" class="form-control input-radius" name="xmh" value="${yshd.XMH}">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">经费来源</span>
					<select id="drp_jfkm" class="form-control input-radius" name="jfkm">
						<c:forEach var="jfkmlist" items="${jfkmlist}"> 
                             <option value="${jfkmlist.DM}" <c:if test="${yshd.JFKM == jfkmlist.DM}">selected</c:if>>${jfkmlist.MC}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">资产来源</span>
					<select id="drp_zcly" class="form-control input-radius" name="zcly">
						<c:forEach var="zclylist" items="${zclylist}"> 
                             <option value="${zclylist.DM}" <c:if test="${yshd.ZCLY == zclylist.DM}">selected</c:if>>${zclylist.MC}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">预计使用年限</span>
					<input type="text" id="txt_synx" class="form-control input-radius text-right int" name="synx"  value="${yshd.SYNX}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>购置日期</span>
					<input type="text" id="txt_dzrrq" class="form-control date" name="dzrrq" value="<fmt:formatDate value="${yshd.DZRRQ}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="购置日期">
					<span class='input-group-addon'>
						<i class="glyphicon glyphicon-calendar"></i>
					</span>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon" id="span_hdrq">货到日期</span>
					<input type="text" id="txt_hdrq" class="form-control date" name="hdrq" value="<fmt:formatDate value="${yshd.HDRQ}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="货到日期">
					<span class='input-group-addon'>
						<i class="glyphicon glyphicon-calendar"></i>
					</span>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>验收日期</span>
					<input type="text" id="txt_yshrq" class="form-control date" name="yshrq" value="<fmt:formatDate value="${yshd.YSHRQ}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="验收日期">
					<span class='input-group-addon'>
						<i class="glyphicon glyphicon-calendar"></i>
					</span>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">学&emsp;&emsp;科</span>
					<input type="text" id="txt_xk" class="form-control input-radius" name="xk" value="${yshd.XK}">
					<c:if test="${operateType == 'U' or operateType == 'C'}">
					<span class="input-group-btn"><button type="button" id="btn_xk" class="btn btn-link btn-custom">选择</button></span>
					</c:if>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">学科类别</span>
					<select id="drp_xklb" class="form-control input-radius" name="xklb">
						<c:forEach var="xklblist" items="${xklblist}"> 
                             <option value="${xklblist.DM}" <c:if test="${yshd.XKLB == xklblist.DM}">selected</c:if>>${xklblist.MC}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">外文名称</span>
					<input type="text" id="txt_wwmc" class="form-control input-radius" name="wwmc" value="${yshd.WWMC}">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">国&emsp;&emsp;别</span>
					<input type="text" id="txt_gbm" class="form-control input-radius" name="gbm" value="${yshd.GBM}">
					<c:if test="${operateType == 'U' or operateType == 'C'}">
					<span class="input-group-btn"><button type="button" id="btn_gbm" class="btn btn-link btn-custom">选择</button></span>
					</c:if>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">随机资料</span>
					<input type="text" id="txt_sjzl" class="form-control input-radius" name="sjzl" value="${yshd.SJZL}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">经&ensp;手&ensp;人</span>
					<input type="text" id="txt_jsr" class="form-control input-radius" readonly="readonly" name="jsr" value="${yshd.JSR}">
<%-- 					<c:if test="${operateType == 'U' or operateType == 'C'}"> --%>
<!-- 					<span class="input-group-btn"><button type="button" id="btn_jsr" class="btn btn-link btn-custom">选择</button></span> -->
<%-- 					</c:if> --%>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">型&emsp;&emsp;号</span>
					<input type="text" id="txt_xh" class="form-control input-radius" name="xh" value="${yshd.XH}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">规&emsp;&emsp;格</span>
					<input type="text" id="txt_gg" class="form-control input-radius" name="gg" value="${yshd.GG}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">生产厂家</span>
					<input type="text" id="txt_sccj" class="form-control input-radius" name="sccj" value="${yshd.SCCJ}">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon" id="span_ccrq">出厂日期</span>
					<input type="text" id="txt_ccrq" class="form-control date" name="ccrq" value="<fmt:formatDate value="${yshd.CCRQ}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="出厂日期">
					<span class='input-group-addon'>
						<i class="glyphicon glyphicon-calendar"></i>
					</span>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">销&ensp;售&ensp;商</span>
					<input type="text" id="txt_xss" class="form-control input-radius" name="xss"  value="${yshd.XSS}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">合同编号</span>
					<input type="text" id="txt_htbh" class="form-control input-radius" name="htbh"  value="${yshd.HTBH}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">用&emsp;&emsp;途</span>
					<input type="text" id="txt_gzyq" class="form-control input-radius" name="gzyq"  value="${yshd.GZYQ}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">单&ensp;据&ensp;号</span>
					<input type="text" id="txt_djh" class="form-control input-radius" name="djh"  value="${yshd.DJH}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">投入使用日期</span>
					<input type="text" id="txt_qsrq" class="form-control date" name="qsrq" value="<fmt:formatDate value="${yshd.QSRQ}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="投入使用日期">
					<span class='input-group-addon'>
						<i class="glyphicon glyphicon-calendar"></i>
					</span>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">品&emsp;&emsp;牌</span>
					<input type="text" id="txt_pp" class="form-control input-radius" name="pp"  value="${yshd.PP}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">功&emsp;&emsp;率</span>
					<input type="text" id="txt_sb_gl" class="form-control input-radius" name="sb_gl"  value="${yshd.SB_GL}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">保修截止日期</span>
					<input type="text" id="txt_bxjzrq" class="form-control date" name="bxjzrq" value="<fmt:formatDate value="${yshd.BXJZRQ}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="保修截止日期">
					<span class='input-group-addon'>
						<i class="glyphicon glyphicon-calendar"></i>
					</span>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">附&ensp;件&ensp;数</span>
					<input type="text" id="txt_tph" class="form-control input-radius text-right" name="tph"  value="${yshd.TPH}" readonly="readonly"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">附件总价</span>
					<input type="text" id="txt_fjzj" class="form-control input-radius text-right number" name="fjzj"  value="${yshd.FJZJ}" readonly="readonly"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">外币种类</span>
					<select id="drp_wbzl" class="form-control input-radius" name="wbzl">
						<c:forEach var="wbzllist" items="${wbzllist}"> 
                             <option value="${wbzllist.DM}" <c:if test="${yshd.WBZL == wbzllist.DM}">selected</c:if>>${wbzllist.MC}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">进口总价</span>
					<input type="text" id="txt_jkdj" class="form-control input-radius text-right number" name="jkdj"  value="<fmt:formatNumber value="${yshd.JKDJ}" pattern="0.00" />"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">外币单价</span>
					<input type="text" id="txt_wbdj" class="form-control input-radius text-right number" name="wbdj"  value="${yshd.WBDJ}"/>
				</div> 
			</div>
			<c:if test="${yshd.ffldm=='2010601'}">
				<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon">打印机类别</span>
						<select id="drp_sb_dyjlb" class="form-control input-radius" name="sb_dyjlb">
							<option value="1" <c:if test="${yshd.SB_DYJLB==1}">selected</c:if>>岗位</option>
                        	<option value="2" <c:if test="${yshd.SB_DYJLB==2}">selected</c:if>>公用</option>
						</select>
					</div>
				</div>
			</c:if>
		</div>
		<c:if test="${sfqy=='1'}">
			<div class="row">
				<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon">折旧状态</span>
						<select id="drp_zjjt" class="form-control input-radius" name="zjjt">
							<c:forEach var="zjjtlist" items="${zjjtlist}"> 
	                             <option value="${zjjtlist.DM}" <c:if test="${yshd.ZJJT == zjjtlist.DM}">selected</c:if>>${zjjtlist.MC}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon">折旧方法</span>
						<select id="drp_zjff" class="form-control input-radius" name="zjff">
							<option value="">未选择</option>
							<c:forEach var="zjfflist" items="${zjfflist}"> 
	                        	<option value="${zjfflist.DM}" <c:if test="${yshd.ZJFF == zjfflist.DM}">selected</c:if>>${zjfflist.MC}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon">净残值率(%)</span>
						<input type="text" id="txt_jczl" class="form-control input-radius text-right" name="jczl"  value="${yshd.JCZL}"/>
					</div>
				</div>
			</div>
		</c:if>
		<div class="row">
			<div class="col-md-12">
				<div class="input-group">
					<span class="input-group-addon">备&emsp;&emsp;注</span>
					<textarea id="txt_bz" class="form-control" name="bz">${yshd.BZ}</textarea>
				</div>
			</div>
		</div>
<script src="${pageContext.request.contextPath}/static/plugins/fileinput/fileinput.js" type="text/javascript"></script>     
<script type="text/javascript">
$(function(){
	var str = "${modetype}".split(",");
	var flh=$('#txt_flh').val();
	if(str.length>0){//必填项样式
		for(var i=0;i<str.length;i++){
			var ctlname = str[i].split("&")[0];
			var modetype = str[i].split("&")[1];
			if(modetype == "00"){//公用
				if(ctlname == "fph" && flh.substring(0,2)!='01'){
					$("#txt_fph").parent().find("span").replaceWith("<span class='input-group-addon'><span class='required'>*</span>发&ensp;票&ensp;号</span>");
				}
			}else if(modetype =="E"){//一般设备及家具类动态验证
				if(ctlname == "ccrq"){
					$("#txt_ccrq").parent().find("#span_ccrq").replaceWith("<span class='input-group-addon'><span class='required'>*</span>出厂日期</span>");
				}else if(ctlname == "sccj"){
					$("#txt_sccj").parent().find("span").replaceWith("<span class='input-group-addon'><span class='required'>*</span>生产厂家</span>");
				}else if(ctlname == "xss"){
					$("#txt_xss").parent().find("span").replaceWith("<span class='input-group-addon'><span class='required'>*</span>销&ensp;售&ensp;商</span>");
				}else if(ctlname == "gg"){
					$("#txt_gg").parent().find("span").replaceWith("<span class='input-group-addon'><span class='required'>*</span>规&emsp;&emsp;格</span>");
				}else if(ctlname == "xh"){
					$("#txt_xh").parent().find("span").replaceWith("<span class='input-group-addon'><span class='required'>*</span>型&emsp;&emsp;号</span>");
				}else if(ctlname == "SB_PP"){
					$("#txt_pp").parent().find("span").replaceWith("<span class='input-group-addon'><span class='required'>*</span>品&emsp;&emsp;牌</span>");
				}else if(ctlname == "cgr"){
					$("#txt_cgr").parent().find("#span_cgr").replaceWith("<span class='input-group-addon'><span class='required'>*</span>采&ensp;购&ensp;人</span>");
				}else if(ctlname == "hdrq"){
					$("#txt_hdrq").parent().find("#span_hdrq").replaceWith("<span class='input-group-addon'><span class='required'>*</span>货到日期</span>");
				}else if(ctlname == "3_9_htbh"){
					$("#txt_htbh").parent().find("span").replaceWith("<span class='input-group-addon'><span class='required'>*</span>合同编号</span>");
				}else if(ctlname == "jsh"){
					$("[id*=txt_jsh]").parent().find("span").replaceWith("<span class='input-group-addon'><span class='required'>*</span>机&ensp;身&ensp;号</span>");
				}else if(ctlname == "cch"){
					$("[id*=txt_cch]").parent().find("span").replaceWith("<span class='input-group-addon'><span class='required'>*</span>出厂编号</span>");
				}
			}
		}
	}
	//页面验证
	var validate = $('#myform').bootstrapValidator({
        fields: {
        	<%=Validate.isNullToDefault(request.getAttribute("jjjson"), "")%>
        }
    });
});

</script>
</body>
</html>
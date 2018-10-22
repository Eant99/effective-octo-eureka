<%@page import="com.googosoft.util.Validate"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产建账详细信息（无形资产）</title>
<style>
	.del_mx {
		margin-left: 10px;
		padding: 3px 6px;
		margin-bottom: 7px;
	}
	.fa.icon-cuowu {
		color:red;
	}
</style>
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
					<span class="input-group-addon"><span class="required">*</span>总&emsp;&emsp;价</span>
					<input type="text" id="txt_dj" class="form-control input-radius text-right number" name="dj" value="${yshd.DJ}" onkeyup="clearNoNumB(this);" readonly>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">经&ensp;手&ensp;人</span>
					<input type="text" id="txt_jsr" class="form-control input-radius" name="jsr" readonly="readonly" value="${yshd.JSR}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">评估价值</span>
					<input type="text" id="txt_pgjz" class="form-control input-radius text-right number" name="pgjz" value="${yshd.PGJZ}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>申购单位</span>
					<input type="text" id="txt_shgdw" class="form-control input-radius" name="shgdw"  value="${yshd.SHGDW}" readonly>
				</div>
			</div>
		</div>
		<!-- row 表示一行，col-md-x 表示一列占几个位置，一行一共12个位置      结束 -->
		<div class="row">
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
					<span class="input-group-addon">形成方式</span>
					<select id="drp_wx_xcfs" class="form-control input-radius" name="wx_xcfs">
						<c:forEach var="wx_xcfslist" items="${wx_xcfslist}"> 
                             <option value="${wx_xcfslist.DM}" <c:if test="${yshd.WX_XCFS == wx_xcfslist.DM}">selected</c:if>>${wx_xcfslist.MC}</option>
						</c:forEach>
					</select>
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
					<span class="input-group-addon">预算项目编号</span>
					<input type="text" id="txt_xmh" class="form-control input-radius" name="xmh" value="${yshd.XMH}">
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
					<span class="input-group-addon">外文名称</span>
					<input type="text" id="txt_wwmc" class="form-control input-radius" name="wwmc" value="${yshd.WWMC}">
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
					<span class="input-group-addon"><span class="required">*</span>货到日期</span>
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
		<c:if test="${yshd.FLH=='17010000'}">
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">注册登记日期</span>
					<input type="text" id="txt_wx_djrq" class="form-control date" name="wx_djrq" value="<fmt:formatDate value="${yshd.WX_DJRQ}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="注册登记日期">
					<span class='input-group-addon'>
						<i class="glyphicon glyphicon-calendar"></i>
					</span>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">批准文号</span>
					<input type="text" id="txt_wx_pzwh" class="form-control input-radius" name="wx_pzwh" value="${yshd.WX_PZWH}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">注册登记机关</span>
					<input type="text" id="txt_wx_djjg" class="form-control input-radius" name="wx_djjg" value="${yshd.WX_DJJG}">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">专&ensp;利&ensp;号</span>
					<input type="text" id="txt_wx_zlh" class="form-control input-radius" name="wx_zlh" value="${yshd.WX_ZLH}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">发&ensp;明&ensp;人</span>
					<input type="text" id="txt_czr" class="form-control input-radius" name="czr" value="${yshd.CZR}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">授权公告日</span>
					<input type="text" id="txt_fzrq" class="form-control date" name="fzrq" value="<fmt:formatDate value="${yshd.FZRQ}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="授权公告日">
					<span class='input-group-addon'>
						<i class="glyphicon glyphicon-calendar"></i>
					</span>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">发明名称</span>
					<input type="text" id="txt_wx_fmmc" class="form-control input-radius" name="wx_fmmc" value="${yshd.WX_FMMC}">
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
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">证&ensp;书&ensp;号</span>
					<input type="text" id="txt_djh" class="form-control input-radius" name="djh" value="${yshd.DJH}">
				</div>
			</div>
		</div>
		</c:if>
		<c:if test="${yshd.FLH=='17040000'}">
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
				<span class="input-group-addon">出版日期</span>
				<input type="text" id="txt_wx_djrq" class="form-control date" name="wx_djrq" value="<fmt:formatDate value="${yshd.WX_DJRQ}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="注册登记日期">
				<span class='input-group-addon'>
					<i class="glyphicon glyphicon-calendar"></i>
				</span>
			</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">著作权证书号</span>
					<input type="text" id="txt_djh" class="form-control input-radius" name="djh" value="${yshd.DJH}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">著作权人</span>
					<input type="text" id="txt_czr" class="form-control input-radius" name="czr" value="${yshd.CZR}">
				</div>
			</div>
		</div>	
		</c:if>
		<c:if test="${yshd.FLH=='17030000'}">
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">注册登记日期</span>
					<input type="text" id="txt_wx_djrq" class="form-control date" name="wx_djrq" value="<fmt:formatDate value="${yshd.WX_DJRQ}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="注册登记日期">
					<span class='input-group-addon'>
						<i class="glyphicon glyphicon-calendar"></i>
					</span>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">批准文号</span>
					<input type="text" id="txt_wx_pzwh" class="form-control input-radius" name="wx_pzwh" value="${yshd.WX_PZWH}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">注册登记机关</span>
					<input type="text" id="txt_wx_djjg" class="form-control input-radius" name="wx_djjg" value="${yshd.WX_DJJG}">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">专&ensp;利&ensp;号</span>
					<input type="text" id="txt_wx_zlh" class="form-control input-radius" name="wx_zlh" value="${yshd.WX_ZLH}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">发&ensp;明&ensp;人</span>
					<input type="text" id="txt_czr" class="form-control input-radius" name="czr" value="${yshd.CZR}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">申请日期</span>
					<input type="text" id="txt_fzrq" class="form-control date" name="fzrq" value="<fmt:formatDate value="${yshd.FZRQ}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="授权公告日">
					<span class='input-group-addon'>
						<i class="glyphicon glyphicon-calendar"></i>
					</span>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">著作权证书号</span>
					<input type="text" id="txt_djh" class="form-control input-radius" name="djh" value="${yshd.DJH}">
				</div>
			</div>
		</div>
		</c:if>
		<c:if test="${yshd.FLH=='17060000'}">
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">授权证明</span>
					<input type="text" id="txt_wx_fmmc" class="form-control input-radius" name="wx_fmmc" value="${yshd.WX_FMMC}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">授权证明号</span>
					<input type="text" id="txt_djh" class="form-control input-radius" name="djh" value="${yshd.DJH}">
				</div>
			</div>
		</div>
		</c:if>
		<c:if test="${yshd.FLH=='17020000'}">
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">专利申请号</span>
					<input type="text" id="txt_wx_pzwh" class="form-control input-radius" name="wx_pzwh" value="${yshd.WX_PZWH}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">发&ensp;明&ensp;人</span>
					<input type="text" id="txt_czr" class="form-control input-radius" name="czr" value="${yshd.CZR}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">发明名称</span>
					<input type="text" id="txt_wx_fmmc" class="form-control input-radius" name="wx_fmmc" value="${yshd.WX_FMMC}">
				</div>
			</div>
		</div>
		</c:if>
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
				if(ctlname == "fph"){
					$("#txt_fph").parent().find("span").replaceWith("<span class='input-group-addon'><span class='required'>*</span>发&ensp;票&ensp;号</span>");
				}
			}
		}
	}
	//页面验证
	var validate = $('#myform').bootstrapValidator({
        fields: {
        	<%=Validate.isNullToDefault(request.getAttribute("wxzcjson"), "")%>
        }
    });
});
</script>
</body>
</html>
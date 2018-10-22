<%@page import="com.googosoft.util.Validate"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产建账详细信息（土地）</title>
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
					<select id="drp_xz" class="form-control input-radius" name="xz">
                        <c:forEach var="xzlist" items="${xzlist}"> 
                             <option value="${xzlist.DM}" <c:if test="${yshd.XZ == xzlist.DM}">selected</c:if>>${xzlist.MC}</option>
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
		</div>
		<!-- row 表示一行，col-md-x 表示一列占几个位置，一行一共12个位置      结束 -->
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
					<span class="input-group-addon"><span class="required">*</span>验收日期</span>
					<input type="text" id="txt_yshrq" class="form-control date" name="yshrq" value="<fmt:formatDate value="${yshd.YSHRQ}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="验收日期">
					<span class='input-group-addon'>
						<i class="glyphicon glyphicon-calendar"></i>
					</span>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">发证日期</span>
					<input type="text" id="txt_td_fzrq" class="form-control date" name="td_fzrq" value="<fmt:formatDate value="${yshd.TD_FZRQ}" pattern="yyyy-MM-dd"/>" data-format="yyyy-MM-dd" placeholder="发证日期">
					<span class='input-group-addon'>
						<i class="glyphicon glyphicon-calendar"></i>
					</span>
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
					<span class="input-group-addon"><span class="required">*</span>申购单位</span>
					<input type="text" id="txt_shgdw" class="form-control input-radius" name="shgdw"  value="${yshd.SHGDW}" readonly>
				</div>
			</div>
		</div>
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
					<span class="input-group-addon">经费来源</span>
					<select id="drp_jfkm" class="form-control input-radius" name="jfkm">
						<c:forEach var="jfkmlist" items="${jfkmlist}"> 
                             <option value="${jfkmlist.DM}" <c:if test="${yshd.JFKM == jfkmlist.DM}">selected</c:if>>${jfkmlist.MC}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon" ><span class="required">*</span>总&emsp;&emsp;价</span>
					<input type="text" id="txt_dj" class="form-control input-radius text-right number" name="dj" value="${yshd.DJ}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">坐落位置</span>
					<input type="text" id="txt_xss" class="form-control input-radius" name="xss" value="${yshd.XSS}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">产权形式</span>
					<select id="drp_cqxs" class="form-control input-radius" name="cqxs">
						<c:forEach var="cqxslist" items="${cqxslist}"> 
                             <option value="${cqxslist.DM}" <c:if test="${yshd.CQXS == cqxslist.DM}">selected</c:if>>${cqxslist.MC}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">入账科目</span>
					<select id="drp_td_rzkm" class="form-control input-radius" name="td_rzkm">
						<c:forEach var="td_rzkmlist" items="${td_rzkmlist}"> 
                             <option value="${td_rzkmlist.DM}" <c:if test="${yshd.TD_RZKM == td_rzkmlist.DM}">selected</c:if>>${td_rzkmlist.MC}</option>
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
					<span class="input-group-addon">经&ensp;手&ensp;人</span>
					<input type="text" id="txt_jsr" class="form-control input-radius" name="jsr" readonly="readonly" value="${yshd.JSR}">
<%-- 					<c:if test="${operateType == 'U' or operateType == 'C'}"> --%>
<!-- 					<span class="input-group-btn"><button type="button" id="btn_jsr" class="btn btn-link btn-custom">选择</button></span> -->
<%-- 					</c:if> --%>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">权属性质</span>
					<select id="drp_td_qsxz" class="form-control input-radius" name="td_qsxz">
						<c:forEach var="td_qsxzlist" items="${td_qsxzlist}"> 
                             <option value="${td_qsxzlist.DM}" <c:if test="${yshd.TD_QSXZ == td_qsxzlist.DM}">selected</c:if>>${td_qsxzlist.MC}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">权属证明</span>
					<input type="text" id="txt_td_qszm" class="form-control input-radius" name="td_qszm" value="${yshd.TD_QSZM}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">权属年限</span>
					<input type="text" id="txt_td_qsnx" class="form-control input-radius text-right" name="td_qsnx" value="${yshd.TD_QSNX}">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">使用权面积</span>
					<input type="text" id="txt_zmmj" class="form-control input-radius text-right number" name="zmmj"  value="${yshd.ZMMJ}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">均&emsp;&emsp;价</span>
					<input type="text" id="txt_jj" class="form-control input-radius text-right number" name="jj"  value="${yshd.JJ}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">取得价格</span>
					<input type="text" id="txt_td_qdjz" class="form-control input-radius text-right number" name="td_qdjz"  value="${yshd.TD_QDJZ}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">独用面积</span>
					<input type="text" id="txt_td_dymj" class="form-control input-radius text-right number" name="td_dymj"  value="${yshd.TD_DYMJ}"/>
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
					<span class="input-group-addon">使用权类型</span>
					<select id="drp_fgxs" class="form-control input-radius" name="fgxs">
						<c:forEach var="fgxslist" items="${fgxslist}"> 
                             <option value="${fgxslist.DM}" <c:if test="${yshd.FGXS == fgxslist.DM}">selected</c:if>>${fgxslist.MC}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">分摊面积</span>
					<input type="text" id="txt_td_ftmj" class="form-control input-radius text-right number" name="td_ftmj"  value="${yshd.TD_FTMJ}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">土地使用权人</span>
					<input type="text" id="txt_djh" class="form-control input-radius" name="djh"  value="${yshd.DJH}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">权属证号</span>
					<input type="text" id="txt_td_dh" class="form-control input-radius" name="td_dh"  value="${yshd.TD_DH}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">实际面积</span>
					<input type="text" id="txt_fjzj" class="form-control input-radius text-right number" name="fjzj"  value="${yshd.FJZJ}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">地下设施</span>
					<input type="text" id="txt_gzyq" class="form-control input-radius" name="gzyq"  value="${yshd.GZYQ}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">其他价值</span>
					<input type="text" id="txt_qtjz" class="form-control input-radius text-right number" name="qtjz"  value="${yshd.QTJZ}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">自用面积</span>
					<input type="text" id="txt_td_zymj" class="form-control input-radius text-right number" name="td_zymj"  value="${yshd.TD_ZYMJ}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">自用价值</span>
					<input type="text" id="txt_zyjz" class="form-control input-radius text-right number" name="zyjz"  value="${yshd.ZYJZ}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">土地用途</span>
					<input type="text" id="txt_td_yt" class="form-control input-radius" name="td_yt"  value="${yshd.TD_YT}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">出租面积</span>
					<input type="text" id="txt_td_czmj" class="form-control input-radius text-right number" name="td_czmj"  value="${yshd.TD_CZMJ}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">出租价值</span>
					<input type="text" id="txt_czjz" class="form-control input-radius text-right number" name="czjz"  value="${yshd.CZJZ}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">宗地编号</span>
					<input type="text" id="txt_jsh" class="form-control input-radius" name="jsh"  value="${yshd.JSH}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">出借面积</span>
					<input type="text" id="txt_td_cjmj" class="form-control input-radius text-right number" name="td_cjmj"  value="${yshd.TD_CJMJ}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">出借价值</span>
					<input type="text" id="txt_cjjz" class="form-control input-radius text-right number" name="cjjz"  value="${yshd.CJJZ}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">土地等级</span>
					<input type="text" id="txt_tddj" class="form-control input-radius" name="tddj"  value="${yshd.TDDJ}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">对外投资面积</span>
					<input type="text" id="txt_td_jymj" class="form-control input-radius text-right number" name="td_jymj"  value="${yshd.TD_JYMJ}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">对外投资价值</span>
					<input type="text" id="txt_jyjz" class="form-control input-radius text-right number" name="jyjz"  value="${yshd.JYJZ}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">校区编号</span>
					<input type="text" id="txt_htbh" class="form-control input-radius" name="htbh"  value="${yshd.HTBH}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">其他面积</span>
					<input type="text" id="txt_td_qtmj" class="form-control input-radius text-right number" name="td_qtmj"  value="${yshd.TD_QTMJ}"/>
				</div>
			</div>
			<c:if test="${sfqy=='1'}">
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
			</c:if>	
		</div>
		<c:if test="${sfqy=='1'}">
			<div class="row">
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
			}else if(modetype =="C"){//土地
				if(ctlname == "sccj"){
					$("#txt_xss").parent().find("span").replaceWith("<span class='input-group-addon'><span class='required'>*</span>坐落位置</span>");
				}else if(ctlname == "td_dh"){
					$("#txt_td_dh").parent().find("span").replaceWith("<span class='input-group-addon'><span class='required'>*</span>权属证号</span>");
				}else if(ctlname == "hth"){
					$("#txt_htbh").parent().find("span").replaceWith("<span class='input-group-addon'><span class='required'>*</span>校区编号</span>");
				}else if(ctlname == "fjzj"){
					$("#txt_fjzj").parent().find("span").replaceWith("<span class='input-group-addon'><span class='required'>*</span>实际面积</span>");
				}else if(ctlname == "td_zmmj"){
					$("#txt_zmmj").parent().find("span").replaceWith("<span class='input-group-addon'><span class='required'>*</span>使用权面积</span>");
				}
			}
		}
	}
	//页面验证
	var validate = $('#myform').bootstrapValidator({
        fields: {
        	<%=Validate.isNullToDefault(request.getAttribute("tdjson"), "")%>
        }
    });
});
</script>
</body>
</html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%-- <%@include file="/static/include/public-list-css.inc"%> --%>
<title>单位信息</title>
</head> 
<body>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">价值类型</span>
				<input type="text" class="form-control input-radius" value="${yshd.jzxsmc}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon" >单&emsp;&emsp;价</span>
				<input type="text" id="txt_dj" class="form-control input-radius text-right number" name="dj" value="${yshd.DJ}"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">现&emsp;&emsp;状</span>
				<input type="text" class="form-control input-radius" value="${yshd.xzmc}">

			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">计量单位</span>
				<input type="text" class="form-control input-radius" value="${yshd.jldwmc}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">经费来源</span>
				<input type="text" class="form-control input-radius" value="${yshd.jfkmmc}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">资产来源</span>
				<input type="text" class="form-control input-radius" value="${yshd.zclymc}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4"> 
			<div class="input-group">
				<span class="input-group-addon">使用方向</span>
				<c:if test="${mxlist.SYFX =='' or mxlist.SYFX ==null}"> 
				<input type="text" class="form-control input-radius" value=""> 
				</c:if>
				<c:if test="${mxlist.SYFX!=''}">
					<c:forEach var="syfxlist" items="${syfxlist}">  
					<c:if test="${mxlist.SYFX == syfxlist.DM}"> 
						<input type="text" class="form-control input-radius" value="${syfxlist.MC}"> 
					</c:if>
					</c:forEach>
				</c:if> 
			</div> 
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">使&ensp;用&ensp;人</span>
				<input type="text" id="zjbsyr" class="form-control input-radius" name="zjbsyr" value="${yshd.SYR}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">使用单位</span>
				<input type="text" id="zjbsybm" class="form-control input-radius" name="zjbsydw" value="${yshd.SYDW}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">存放地点</span>
				<input type="text" id="bzxx" class="form-control input-radius" name="bzxx" value="${yshd.BZXX}">
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
				<input type="text" class="form-control input-radius" value="${yshd.jzlxmc}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">会计凭证号</span>
				<input type="text" id="zjbpzh" class="form-control input-radius" name="zjbpzh"  value="${yshd.PZH}"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">入账日期</span>
				<input type="text"  class="form-control"  value="${yshd.RZRQ}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">调转入日期</span>
				<input type="text"  class="form-control"  value="${yshd.DZRRQ}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">采购组织形式</span>
                <input type="text" class="form-control input-radius" value="${yshd.cgzzxsmc}">
			</div>
		</div>
		<div class="col-md-4"> 
			<div class="input-group">
				<span class="input-group-addon">采&ensp;购&ensp;人</span>
				<input type="text" id="txt_cgr" class="form-control input-radius" name="cgr"  value="${yshd.CGR}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">出厂编号</span>
				<input type="text" class="form-control input-radius" name="zjbmx[${status.index}].cch" value="${yshd.CCH}"/>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">销&ensp;售&ensp;商</span>
				<input type="text" id="txt_xss" class="form-control input-radius" name="xss"  value="${yshd.XSS}"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">生产厂家</span>
				<input type="text" id="txt_sccj" class="form-control input-radius" name="sccj" value="${yshd.SCCJ}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">出厂日期</span>
				<input type="text"  class="form-control"  value="${yshd.CCRQ}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">购置日期</span>
				<input type="text"  class="form-control"  value="${yshd.GZRQ}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">投入使用日期</span>
				<input type="text"  class="form-control"  value="${yshd.QSRQ}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">保修截止日期</span>
				<input type="text"  class="form-control"  value="${yshd.BXJZRQ}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">品牌型号</span>
				<input type="text" id="txt_jt_cpxh" class="form-control input-radius" name="jt_cpxh" value="${yshd.JT_CPXH}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">排&ensp;气&ensp;量</span>
				<input type="text" id="txt_jt_pql" class="form-control input-radius text-right number" name="jt_pql"  value="${yshd.JT_PQL}"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">资&emsp;&emsp;料</span>
				<input type="text" id="txt_sjzl" class="form-control input-radius" name="sjzl"  value="${yshd.GZYQ}"/>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">车辆产地</span>
				<input type="text" id="txt_jt_clcd" class="form-control input-radius" name="jt_clcd" value="${yshd.JT_CLCD}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">编制情况</span>
				<input type="text" class="form-control input-radius" value="${yshd.clbzmc}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">使用性质</span>
				<input type="text" class="form-control input-radius" value="${yshd.JT_SYXZMC}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">车牌号码</span>
				<input type="text" id="txt_jsh" class="form-control input-radius" name="zjbmx[${status.index}].jsh" value="${yshd.JSH}"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">车辆识别号</span>
				<input type="text" class="form-control input-radius" name="zjbmx[${status.index}].gg" value="${yshd.GG}"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">发动机号</span>
				<input type="text" id="txt_cch" class="form-control input-radius" name="zjbmx[${status.index}].cch" value="${yshd.CCH}"/>
			</div>
		</div>	
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">车辆用途</span>
				<input type="text" class="form-control input-radius" value="${yshd.SYXZMC}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">持&ensp;证&ensp;人</span>
				<input type="text" id="txt_czr" class="form-control input-radius" name="czr"  value="${yshd.CZR}"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">持证日期</span>
				<input type="text"  class="form-control"  value="${yshd.FZRQ}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">国&emsp;&emsp;别</span>
				<input type="text" id="txt_gbm" class="form-control input-radius" name="gbm" value="${yshd.GBM}">
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
				<span class="input-group-addon">合同编号</span>
				<input type="text" id="txt_htbh" class="form-control input-radius" name="htbh"  value="${yshd.HTBH}"/>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">预计使用年限</span>
				<input type="text" id="txt_synx" class="form-control input-radius text-right" name="synx"  value="${yshd.SYNX}"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">附&ensp;件&ensp;数</span>
				<input type="text" id="txt_tph" class="form-control input-radius text-right" name="tph"  value="${yshd.FJS}" readonly="readonly"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">附件总价</span>
				<input type="text" id="txt_fjzj" class="form-control input-radius text-right number" name="fjzj"  value="${yshd.FJZJ}" readonly="readonly"/>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">进口总价</span>
				<input type="text" id="txt_jkdj" class="form-control input-radius text-right number" name="jkdj"  value="${yshd.JKDJ}"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">外币种类</span>
				<input type="text" class="form-control input-radius" value="${yshd.wbzlmc}">
			</div>
		</div>
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
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">品牌性质</span>
				<input type="text" class="form-control input-radius" value="${yshd.JT_PPXZMC}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">注册登记年份</span>
				<input type="text" id="txt_jt_zcnf" class="form-control input-radius text-right" name="jt_zcnf"  value="${yshd.JT_ZCNF}"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">车辆类型</span>
				<input type="text" class="form-control input-radius" value="${yshd.CLLXMC}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<div class="input-group">
				<span class="input-group-addon">备&emsp;&emsp;注</span>
				<textarea id="txt_bz" class="form-control" name="bz">${yshd.BZ}</textarea>
			</div>
		</div>
	</div>
</body>
</html>
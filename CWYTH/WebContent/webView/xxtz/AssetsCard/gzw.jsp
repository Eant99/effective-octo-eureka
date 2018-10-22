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
				<span class="input-group-addon">总&emsp;&emsp;价</span>
				<input type="text" id="txt_zzj" class="form-control input-radius text-right number" name="zzj" value="${yshd.ZZJ}"/>
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
				<span class="input-group-addon">入账形式</span>
				<input type="text" class="form-control input-radius" value="${yshd.jzlxmc}">
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
				<span class="input-group-addon">折旧状态</span>
				<input type="text" class="form-control input-radius" value="${yshd.zjjtmc}">
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
				<span class="input-group-addon">学科类别</span>
				<input type="text" class="form-control input-radius" value="${yshd.xklbmc}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">学&emsp;&emsp;科</span>
				<input type="text" id="txt_xk" class="form-control input-radius" name="xk" value="${yshd.XK}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">竣工日期</span>
				<input type="text"  class="form-control"  value="${yshd.CCRQ}">
			</div>
		</div>
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
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">承建单位</span>
				<input type="text" id="txt_sccj" class="form-control input-radius" name="sccj"  value="${yshd.SCCJ}"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">权属性质</span>
				<input type="text" class="form-control input-radius" value="${yshd.qsxzmc}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">房管形式</span>
				<input type="text" class="form-control input-radius" value="${yshd.fgxsmc}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">产权形式</span>
				<input type="text" class="form-control input-radius" value="${yshd.cqxsmc}">
			</div>
		</div>
		<div class="col-md-4">
			 <div class="input-group">
				<span class="input-group-addon">产权来源</span>
				<input type="text" id="txt_cqly" class="form-control input-radius" name="cqly" value="${yshd.CQLY}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">权属证号</span>
				<input type="text" id="txt_jsh" class="form-control input-radius" name="jsh"  value="${yshd.JSH}"/>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">坐落位置</span>
				<input type="text" id="txt_xss" class="form-control input-radius" name="xss" value="${yshd.XSS}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">校区编号</span>
				<input type="text" id="txt_htbh" class="form-control input-radius" name="htbh"  value="${yshd.HTBH}"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">层&emsp;&emsp;数</span>
				<input type="text" id="txt_jkdj" class="form-control input-radius text-right" name="jkdj"  value="${yshd.JKDJ}"/>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">建筑面积</span>
				<input type="text" id="txt_fjzj" class="form-control input-radius text-right number" name="fjzj"  value="${yshd.FJZJ}"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">供暖面积</span>
				<input type="text" id="txt_gnmj" class="form-control input-radius text-right number" name="gnmj"  value="${yshd.GNMJ}"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">建筑结构</span>
				<input type="text" class="form-control input-radius" value="${yshd.xhmc}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">使用面积</span>
				<input type="text" id="txt_symj" class="form-control input-radius text-right number" name="symj"  value="${yshd.SYMJ}"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">地上面积</span>
				<input type="text" id="txt_DSMJ" class="form-control input-radius text-right number" name="dsmj"  value="${yshd.DSMJ}"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">地下面积</span>
				<input type="text" id="txt_DXMJ" class="form-control input-radius text-right number" name="dxmj"  value="${yshd.DXMJ}"/>
			</div>
		</div>
	</div>	
	<div class="row">
		<div class="col-md-4">
		    <div class="input-group">
				<span class="input-group-addon">建成年份</span>
				<input type="text" id="txt_jcnf" class="form-control input-radius text-right" name="jcnf" value="${yshd.JCNF}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">房间数量</span>
				<input type="text" id="txt_tshcs" class="form-control input-radius text-right" name="tshcs"  value="${yshd.SYKH}"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">附属设施</span>
				<input type="text" id="txt_sjzl" class="form-control input-radius" name="sjzl"  value="${yshd.GZYQ}"/>
			</div>
		</div>
	</div>	
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">主要用途</span>
				<input type="text" id="txt_gzyq" class="form-control input-radius" name="gzyq"  value="${yshd.ZYYT}"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">预计使用年限</span>
				<input type="text" id="txt_synx" class="form-control input-radius text-right" name="synx"  value="${yshd.SYNX}"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">安全等级</span>
				<input type="text" id="txt_tddj" class="form-control input-radius" name="tddj"  value="${yshd.TDDJ}"/>
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
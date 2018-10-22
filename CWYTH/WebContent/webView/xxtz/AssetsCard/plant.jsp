<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
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
				<span class="input-group-addon">学&emsp;&emsp;科</span>
				<input type="text" id="txt_xk" class="form-control input-radius" name="xk" value="${yshd.XK}">
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
				<span class="input-group-addon">折旧状态</span>
				<input type="text" class="form-control input-radius" value="${yshd.zjjtmc}">
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
				<span class="input-group-addon">购置日期</span>
					<input type="text"  class="form-control"  value="${yshd.GZRQ}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">坐落位置</span>
				<input type="text" id="txt_xss" class="form-control input-radius" name="xss" value="${yshd.XSS}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">纲&ensp;属&ensp;科</span>
				<input type="text" id="txt_gg" class="form-control input-radius" name="gg" value="${yshd.GG}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">拉丁文名</span>
				<input type="text" id="txt_gzyq" class="form-control input-radius" name="gzyq" value="${yshd.GZYQ}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">分&emsp;&emsp;类</span>
				<input type="text" id="txt_djh" class="form-control input-radius" name="djh" value="${yshd.DJH}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">校区编号</span>
				<input type="text" id="txt_htbh" class="form-control input-radius" name="htbh" value="${yshd.HTBH}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">栽种年份</span>
				<input type="text" id="txt_jcnf" class="form-control input-radius text-right" name="jcnf" value="${yshd.JCNF}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">栽种树龄</span>
				<input type="text" id="txt_tph" class="form-control input-radius text-right" name="tph" value="${yshd.TPH}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">进口总价</span>
				<input type="text" id="txt_jkdj" class="form-control input-radius text-right number" name="jkdj" value="${yshd.JKDJ}">
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
				<span class="input-group-addon">外币单价</span>
				<input type="text" id="txt_wbdj" class="form-control input-radius text-right number" name="wbdj" value="${yshd.WBJE}">
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
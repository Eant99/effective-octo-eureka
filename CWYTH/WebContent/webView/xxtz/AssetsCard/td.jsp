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
				<span class="input-group-addon" >总&emsp;&emsp;价</span>
				<input type="text" id="txt_zzj" class="form-control input-radius text-right number" name="zzj" value="${yshd.ZZJ}"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">均&emsp;&emsp;价</span>
				<input type="text" id="txt_jj" class="form-control input-radius text-right number" name="jj"  value="${yshd.JJ}"/>
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
				<span class="input-group-addon">坐落位置</span>
				<input type="text" id="txt_xss" class="form-control input-radius" name="xss" value="${yshd.XSS}">
			</div>
		</div>
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
				<span class="input-group-addon">入账科目</span>
				<input type="text" class="form-control input-radius" value="${yshd.td_rzkmmc}">
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
				<span class="input-group-addon">产权形式</span>
				<input type="text" class="form-control input-radius" value="${yshd.cqxsmc}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">权属性质</span>
				<input type="text" class="form-control input-radius" value="${yshd.td_qsxzmc}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">权属证明</span>
				<input type="text" id="txt_td_qszm" class="form-control input-radius" name="td_qszm" value="${yshd.TD_QSZM}">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">权属年限</span>
				<input type="text" id="txt_td_qsnx" class="form-control input-radius text-right" name="td_qsnx" value="${yshd.TD_QSNX}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">权属证号</span>
				<input type="text" id="txt_jsh" class="form-control input-radius" name="jsh"  value="${yshd.TD_DH}"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">土地使用权人</span>
				<input type="text" id="txt_djh" class="form-control input-radius" name="djh"  value="${yshd.DJH}"/>
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
				<span class="input-group-addon">使用权类型</span>
				<input type="text" class="form-control input-radius" value="${yshd.fgxsmc}">
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">购置日期</span>
				<input type="text"  class="form-control"  value="${yshd.GZRQ}">
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
				<span class="input-group-addon">发证日期</span>
				<input type="text"  class="form-control"  value="${yshd.TD_FZRQ}">
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
				<span class="input-group-addon">分摊面积</span>
				<input type="text" id="txt_td_ftmj" class="form-control input-radius text-right number" name="td_ftmj"  value="${yshd.TD_FTMJ}"/>
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
				<span class="input-group-addon">地下设施</span>
				<input type="text" id="txt_gzyq" class="form-control input-radius" name="gzyq"  value="${yshd.GZYQ}"/>
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
				<span class="input-group-addon">土地用途</span>
				<input type="text" id="txt_td_yt" class="form-control input-radius" name="td_yt"  value="${yshd.TD_YT}"/>
			</div>
		</div>	
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">自用面积</span>
				<input type="text" id="txt_td_zymj" class="form-control input-radius text-right number" name="td_zymj"  value="${yshd.TD_ZYMJ}"/>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">自用价值</span>
				<input type="text" id="txt_zyjz" class="form-control input-radius text-right number" name="zyjz"  value="${yshd.ZYJZ}"/>
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
				<span class="input-group-addon">出租面积</span>
				<input type="text" id="txt_td_czmj" class="form-control input-radius text-right number" name="td_czmj"  value="${yshd.TD_CZMJ}"/>
			</div>
		</div>
	</div>
	<div class="row">
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
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">出借面积</span>
				<input type="text" id="txt_td_cjmj" class="form-control input-radius text-right number" name="td_cjmj"  value="${yshd.TD_CJMJ}"/>
			</div>
		</div>
	</div>
	<div class="row">
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
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">对外投资面积</span>
				<input type="text" id="txt_td_jymj" class="form-control input-radius text-right number" name="td_jymj"  value="${yshd.TD_JYMJ}"/>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">对外投资价值</span>
				<input type="text" id="txt_jyjz" class="form-control input-radius text-right number" name="jyjz"  value="${yshd.JYJZ}"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="input-group">
				<span class="input-group-addon">其他面积</span>
				<input type="text" id="txt_td_qtmj" class="form-control input-radius text-right number" name="td_qtmj"  value="${yshd.TD_QTMJ}"/>
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
		<div class="col-md-12">
			<div class="input-group">
				<span class="input-group-addon">备&emsp;&emsp;注</span>
				<textarea id="txt_bz" class="form-control" name="bz">${yshd.BZ}</textarea>
			</div>
		</div>
	</div>	
</body>
</html>
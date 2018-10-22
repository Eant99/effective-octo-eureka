<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>房屋信息</title>
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
					<span class="input-group-addon">采购组织形式</span>
                    <input type="text" class="form-control input-radius" value="${yshd.cgzzxsmc}">
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
					<span class="input-group-addon">入账日期</span>
					<input type="text" id="zjbrzrq" class="form-control" name="zjbrzrq" value="${yshd.RZRQ}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">会计凭证号</span>
					<input type="text" id="zjbpzh" class="form-control input-radius" name="zjbpzh"  value="${yshd.PZH}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">投入使用日期</span>
					<input type="text" id="zjbrzrq" class="form-control" name="zjbrzrq" value="${yshd.QSRQ}">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">购置日期</span>
					<input type="text" class="form-control" value="${yshd.GZRQ}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">竣工日期</span>
					<input type="text" class="form-control" value="${yshd.CCRQ}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">验收日期</span>
					<input type="text" class="form-control" value="${yshd.DZRRQ}">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">房屋所有权人</span>
					<input type="text" id="txt_czr" class="form-control" name="czr" value="${yshd.CZR}" placeholder="">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">外文名称</span>
					<input type="text" id="txt_wwmc" class="form-control input-radius" name="wwmc" value="${yshd.WWMC}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">经&ensp;手&ensp;人</span>
					<input type="text" id="txt_jsr" class="form-control input-radius" name="jsr" value="${yshd.JSR}">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">附属设施</span>
					<input type="text" id="txt_gzyq" class="form-control input-radius" name="gzyq"  value="${yshd.GZYQ}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">建成年份</span>
					<input type="text" id="txt_jcnf" class="form-control input-radius text-right" name="jcnf" value="${yshd.JCNF}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">承建单位</span>
					<input type="text" id="txt_sccj" class="form-control input-radius" name="sccj" value="${yshd.SCCJ}">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">完损状况</span>
					<input type="text" class="form-control input-radius" value="${yshd.tddjmc}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">校区编号</span>
					<input type="text" id="txt_htbh" class="form-control input-radius" name="htbh" value="${yshd.HTBH}">
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
					<span class="input-group-addon">权属性质</span>
					<input type="text" class="form-control input-radius" value="${yshd.qsxzmc}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">权属证明</span>
					<input type="text" id="txt_fw_qszm" class="form-control input-radius" name="fw_qszm" value="${yshd.FW_QSZM}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">产权形式</span>
					<input type="text" class="form-control input-radius" value="${yshd.cqxsmc}">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">产权来源</span>
					<input type="text" id="txt_cqly" class="form-control input-radius" name="cqly" value="${yshd.CQLY}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">房管形式</span>
					<input type="text" class="form-control input-radius" value="${yshd.fgxsmc}">
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
					<span class="input-group-addon">证载面积</span>
					<input type="text" id="txt_zmmj" class="form-control input-radius text-right number" name="zmmj"  value="${yshd.ZMMJ}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">发证日期</span>
					<input type="text" class="form-control" value="${yshd.FW_FZRQ}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">设计用途</span>
					<input type="text" id="txt_yt" class="form-control input-radius" name="yt"  value="${yshd.YT}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">房屋用途</span>
					<input type="text" class="form-control input-radius" value="${yshd.fwytmc}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">建筑类型</span>
					<input type="text" class="form-control input-radius" value="${yshd.zjmc}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">地上面积</span>
					<input type="text" id="txt_dsmj" class="form-control input-radius text-right number" name="dsmj"  value="${yshd.DSMJ}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">地下面积</span>
					<input type="text" id="txt_dxmj" class="form-control input-radius text-right number" name="dxmj"  value="${yshd.DXMJ}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">建筑结构</span>
					<input type="text" class="form-control input-radius" value="${yshd.xhmc}">
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">建筑朝向</span>
					<input type="text" id="txt_gg" class="form-control input-radius" name="gg"  value="${yshd.GG}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">自然间数</span>
					<input type="text" id="txt_fw_zrjs" class="form-control input-radius text-right" name="fw_zrjs"  value="${yshd.FW_ZRJS}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">建筑面积</span>
					<input type="text" id="txt_fjzj" class="form-control input-radius text-right number" name="fjzj"  value="${yshd.FJZJ}"/>
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
					<span class="input-group-addon">自用面积</span>
					<input type="text" id="txt_fw_zymj" class="form-control input-radius text-right number" name="fw_zymj"  value="${yshd.FW_ZYMJ}"/>
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
					<span class="input-group-addon">使用面积</span>
					<input type="text" id="txt_symj" class="form-control input-radius text-right number" name="symj"  value="${yshd.SYMJ}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">出借面积</span>
					<input type="text" id="txt_fw_cjmj" class="form-control input-radius text-right number" name="fw_cjmj"  value="${yshd.FW_CJMJ}"/>
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
					<span class="input-group-addon">占地面积</span>
					<input type="text" id="txt_wbdj" class="form-control input-radius text-right number" name="wbdj"  value="${yshd.WBJE}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">出租面积</span>
					<input type="text" id="txt_fw_czmj" class="form-control input-radius text-right number" name="fw_czmj"  value="${yshd.FW_CZMJ}"/>
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
					<span class="input-group-addon">供暖面积</span>
					<input type="text" id="txt_fw_gnmj" class="form-control input-radius text-right number" name="fw_gnmj"  value="${yshd.FW_GNMJ}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">对外投资面积</span>
					<input type="text" id="txt_fw_jymj" class="form-control input-radius text-right number" name="fw_jymj"  value="${yshd.FW_JYMJ}"/>
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
					<span class="input-group-addon">层&emsp;&emsp;数</span>
					<input type="text" id="txt_jkdj" class="form-control input-radius text-right" name="jkdj"  value="${yshd.JKDJ}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">担保面积</span>
					<input type="text" id="txt_fw_xzmj" class="form-control input-radius text-right number" name="fw_xzmj"  value="${yshd.FW_XZMJ}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">担保价值</span>
					<input type="text" id="txt_xzjz" class="form-control input-radius text-right number" name="xzjz"  value="${yshd.XZJZ}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">幢&emsp;&emsp;号</span>
					<input type="text" id="txt_ccbh" class="form-control input-radius" name="ccbh"  value="${yshd.CCH}"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">其他面积</span>
					<input type="text" id="txt_fw_qtmj" class="form-control input-radius text-right number" name="fw_qtmj"  value="${yshd.FW_QTMJ}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">其他价值</span>
					<input type="text" id="txt_qtjz" class="form-control input-radius text-right number" name="qtjz"  value="${yshd.QTJZ}"/>
				</div>
			</div>
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">平面图数</span>
					<input type="text" id="txt_tph" class="form-control input-radius text-right" name="tph"  value="${yshd.TPH}"/>
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
						<c:forEach var="zjfflist" items="${zjfflist}"> 
                        	<option value="${zjfflist.DM}" <c:if test="${yshd.ZJFF == zjfflist.DM}">selected</c:if>>${zjfflist.MC}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="input-group">
					<span class="input-group-addon">净残值率(%)</span>
					<input type="text" id="txt_jczl" class="form-control input-radius text-right" name="jczl"  value="${yshd.JCZL}"/>
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
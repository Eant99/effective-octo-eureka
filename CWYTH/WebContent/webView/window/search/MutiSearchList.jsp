<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>综合查询</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.col-xs-1,.col-xs-2,.col-xs-3{
		padding:0px;
	}
</style>
</head>
<body>
	<form id="myform" class="form-horizontal" action="" method="post">
		<input type="hidden" name="operateType" id="operateType" value="">
		<input type="hidden" name="gid" value="" />
		<div class="box" style="top:4px;">
			<div class="box-panel">
			    <div class="container-fluid box-content">
					<div class="row">
						<div class="col-xs-1">
							<div class="input-group">
								<select id="ddl_left" class="form-control input-radius" name="ddl_left">
								     <option value="" selected></option>
								     <option value="(" >(</option>
								</select>
							</div>
						</div>
						<div class="col-xs-3">
							<div class="input-group">
								<select id="ddl_mc" class="form-control input-radius" name="ddl_mc" >
									<c:forEach var="lmList" items="${lmList}" varStatus="status"> 
										<option value="${lmList.bh }" <c:if test="${status.index == 0}">selected</c:if>>${lmList.mc }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-xs-2">
							<div class="input-group">
								<select id="ddl_bj" class="form-control input-radius" name="ddl_bj" >
									<c:forEach var="fhList" items="${fhList}" varStatus="status"> 
										<option value="${fhList.bh }" <c:if test="${status.index == 0}">selected</c:if>>${fhList.mc }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-xs-3">
							<div class="input-group">
								<input type="text" class="form-control input-radius" name="txt_value" id="txt_value" value="" />
							</div>
						</div>
						<div class="col-xs-1">
							<div class="input-group">
								<select id="ddl_right" class="form-control input-radius" name="ddl_right">
								     <option value="" ></option>
								     <option value=")" >)</option>
								</select>
							</div>
						</div>
						<div class="col-xs-2">
							<div class="input-group">
								<select id="ddl_option" class="form-control input-radius" name="ddl_option" >
									<option value="and " selected>并且</option>
									<option value="or " >或者</option>
								</select>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<div class="input-group">
								<button type='button' class="btn btn-default" id="btn_add">
									<i class="fa icon-add"></i>
									添加条件
								</button>
								<button type='button' class="btn btn-default" id="btn_del">
									<i class="fa icon-del"></i>
									删除条件
								</button>
							</div>
						</div>
					</div>
				    <div class="row">
						<div class="col-md-12">
							<div class="input-group" >
								<div class="box">
								</div>
							</div>
						</div>
				    </div>
			    </div>
			</div>
			<div class="box-panel">
			    <div class="container-fluid box-content">
					<div class="row">
						<div class="col-md-12">
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span>标题</span>
								<input type="text" id="txt_czbgbh" name ="czbgbh" class="form-control input-radius" value=""/>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span></span>
								<input type="text" id="txt_czbgbh" name ="czbgbh" class="form-control input-radius" value=""/>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="input-group">
								<span class="input-group-addon"><span class="required">*</span></span>
								<input type="text" id="txt_czbgbh" name ="czbgbh" class="form-control input-radius" value=""/>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class='page-bottom clearfix'>
			     <div class="pull-right">
					 <button type='button' class="btn btn-default" id="btn_save" style="display: <c:if test="${operateType == 'L'}">none</c:if>;">
					     <i class="fa icon-save"></i>
							保存
					 </button>
					 <button type='button' class="btn btn-default btn-without-icon" id="btn_cancelw">
							取消
					</button>
			    </div>
			</div>	
		</div>
	</form>

</body>
</html>
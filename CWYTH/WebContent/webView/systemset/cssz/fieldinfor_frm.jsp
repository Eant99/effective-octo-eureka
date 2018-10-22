<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>验收单必填项设置</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<style type="text/css">
.input-group-addon:first-child {
  height:30px;
  float:left;
  border: 1px solid #ddd;
}
.checkbox-group{
	width:20%;
 	float:left;
 	border: 1px solid #ddd;
 	height:30px;
 	line-height:32px;
 	text-align:center;
}
.input-group-addon {
  	padding: 7px 4px;
}
input[type=checkbox]{
  	margin: 5px 0 0;
}
.col-md-2{
	margin-bottom:6px;
}
</style>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<div class='page-header'>
        <div class="pull-left">
            <span class="page-title text-primary">
            	<i class='fa icon-xiangxixinxi'></i>
				自定义验收单必填项设置
			</span>
		</div>
        <div class="pull-right">
			<button class="btn btn-default" type="button" id="btn_save">
				<i class="fa icon-save"></i> 保存
			</button>
			<button class="btn btn-default" type="button" id="btn_chk">
				 全选
			</button>
			<button type="reset" class="btn btn-default">
                                                 重置
            </button>
        </div>
    </div>
    <div class="box" style="top:35px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-xiangxi"></i>
            		公&ensp;用&ensp;项
            	</div>
        	</div>
			<div class="container-fluid box-content">
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="00|yshd|fph|stri|[发票号]" />
					</div>
					<div class="input-group">
						<span class="input-group-addon">发&ensp;票&ensp;号</span>
					</div>
				</div>										
			</div>
		</div>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-xiangxi"></i>
            		房&ensp;屋&ensp;类
            	</div>
        	</div>
			<div class="container-fluid box-content">
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="01|yshd|fjzj|stri|[建筑面积]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">建筑面积</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="01|yshd|ccrq|stri|[竣工日期]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">竣工日期</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="01|yshd|htbh|stri|[校区编号]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">校区编号</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="01|yshd|xss|stri|[坐落位置]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">坐落位置</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="01|yshd|jsh|stri|[权属证号]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">权属证号</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="01|yshd|czr|stri|[房屋所有权人]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">房屋所有权人</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="01|yshd|qsrq|stri|[投入使用日期]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">投入使用日期</span>
					</div>
				</div>
			</div>
		</div>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-xiangxi"></i>
            		构筑物类
            	</div>
        	</div>
			<div class="container-fluid box-content">
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="02|yshd|ccrq|stri|[竣工日期]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">竣工日期</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="02|yshd|xss|stri|[坐落位置]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">坐落位置</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="02|yshd|htbh|stri|[校区编号]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">校区编号</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="02|yshd|fjzj|stri|[建筑面积]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">建筑面积</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="02|yshd|qsrq|stri|[投入使用日期]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">投入使用日期</span>
					</div>
				</div>
			</div>
		</div>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-xiangxi"></i>
            		土&ensp;地&ensp;类
            	</div>
        	</div>
			<div class="container-fluid box-content">
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="03|yshd|xss|stri|[坐落位置]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">坐落位置</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="03|yshd|htbh|stri|[校区编号]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">校区编号</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="03|yshd|td_dh|stri|[权属证号]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">权属证号</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="03|yshd|fjzj|stri|[实际面积]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">实际面积</span>
					</div>
				</div>	
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="03|yshd|zmmj|stri|[使用权面积]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">使用权面积</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="03|yshd|qsrq|stri|[投入使用日期]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">投入使用日期</span>
					</div>
				</div>
			</div>
		</div>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-xiangxi"></i>
            		植&ensp;物&ensp;类
            	</div>
        	</div>
			<div class="container-fluid box-content">
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="04|yshd|htbh|stri|[校区编号]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">校区编号</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="04|yshd|jcnf|stri|[栽种年份]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">栽种年份</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="04|yshd|tph|stri|[栽种树龄]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">栽种树龄</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="04|yshd|xss|stri|[坐落位置]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">坐落位置</span>
					</div>
				</div>	
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="04|yshd|hdrq|stri|[货到日期]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">货到日期</span>
					</div>
				</div>
			</div>
		</div>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-xiangxi"></i>
            		一般设备类
            	</div>
        	</div>
			<div class="container-fluid box-content">
				<div style="color: #337ab7;margin-bottom:3px">&emsp;&emsp;验收单信息</div>	
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="05|yshd|ccrq|stri|[出厂日期]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">出厂日期</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="05|yshd|sccj|stri|[生产厂家]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">生产厂家</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="05|yshd|xss|stri|[销售商]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">销&ensp;售&ensp;商</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="05|yshd|gg|stri|[规格]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">规&emsp;&emsp;格</span>
					</div>
				</div>	
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="05|yshd|xh|stri|[型号]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">型&emsp;&emsp;号</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="05|yshd|pp|stri|[品牌]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">品&emsp;&emsp;牌</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="05|yshd|cgr|stri|[采购人]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">采&ensp;购&ensp;人</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="05|yshd|hdrq|stri|[货到日期]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">货到日期</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="05|yshd|htbh|stri|[合同编号]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">合同编号</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="05|yshd|qsrq|stri|[投入使用日期]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">投入使用日期</span>
					</div>
				</div>
			</div>
			<div class="container-fluid box-content">
				<div style="color: #337ab7;margin-bottom:3px">&emsp;&emsp;卡片信息</div>	
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="05|zjb|jsh|stri|[机身号]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">机&ensp;身&ensp;号</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="05|zjb|cch|stri|[出厂编号]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">出厂编号</span>
					</div>
				</div>
			</div>
		</div>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-xiangxi"></i>
            		交通运输类
            	</div>
        	</div>
			<div class="container-fluid box-content">
			 	<div style="color: #337ab7;margin-bottom:3px;">&emsp;&emsp;验收单信息</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
					 	<input type="checkbox" name="06|yshd|ccrq|stri|[出厂日期]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">出厂日期</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="06|yshd|sccj|stri|[生产厂家]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">生产厂家</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="06|yshd|jt_cpxh|stri|[品牌型号]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">品牌型号</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="06|yshd|jt_clcd|stri|[车辆产地]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">车辆产地</span>
					</div>
				</div>	
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="06|yshd|jt_pql|stri|[排气量]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">排&ensp;气&ensp;量</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="06|yshd|xss|stri|[销售商]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">销&ensp;售&ensp;商</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="06|yshd|cgr|stri|[采购人]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">采&ensp;购&ensp;人</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						 <input type="checkbox" name="06|yshd|hdrq|stri|[货到日期]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">货到日期</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="06|yshd|htbh|stri|[合同编号]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">合同编号</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="06|yshd|qsrq|stri|[投入使用日期]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">投入使用日期</span>
					</div>
				</div>
			</div>
			<div class="container-fluid box-content">
				<div style="color: #337ab7;margin-bottom:3px;">&emsp;&emsp;卡片信息</div>	
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="06|zjb|jsh|stri|[车牌号码]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">车牌号码</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="06|zjb|gg|stri|[车辆识别号]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">车辆识别号</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="06|zjb|cch|stri|[发动机号]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">发动机号</span>
					</div>
				</div>
			</div>
		</div>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-xiangxi"></i>
            		软&ensp;件&ensp;类
            	</div>
        	</div>
			<div class="container-fluid box-content">
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="11|yshd|pp|stri|[品牌]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">品&emsp;&emsp;牌</span>
					</div>
				</div>
			</div>
		</div>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-xiangxi"></i>
            		图&ensp;书&ensp;类
            	</div>
        	</div>
			<div class="container-fluid box-content">
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="08|yshd|ccrq|stri|[出版日期]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">出版日期</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="08|yshd|sccj|stri|[出版社]" />
					</div>
					<div class="input-group">
						<span class="input-group-addon">出&ensp;版&ensp;社</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="08|yshd|xss|stri|[销售商]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">销&ensp;售&ensp;商</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="08|yshd|dabh|stri|[档案号]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">档&ensp;案&ensp;号</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="08|yshd|cgr|stri|[采购人]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">采&ensp;购&ensp;人</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="08|yshd|hdrq|stri|[货到日期]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">货到日期</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="08|yshd|htbh|stri|[合同编号]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">合同编号</span>
					</div>
				</div>
			</div>
		</div>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-xiangxi"></i>
            		家&ensp;具&ensp;类
            	</div>
        	</div>
			<div class="container-fluid box-content">
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="09|yshd|ccrq|stri|[出厂日期]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">出厂日期</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="09|yshd|sccj|stri|[生产厂家]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">生产厂家</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="09|yshd|xss|stri|[销售商]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">销&ensp;售&ensp;商</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="09|yshd|gg|stri|[规格]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">规&emsp;&emsp;格</span>
					</div>
				</div>	
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="09|yshd|xh|stri|[型号]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">型&emsp;&emsp;号</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="09|yshd|pp|stri|[品牌]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">品&emsp;&emsp;牌</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="09|yshd|cgr|stri|[采购人]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">采&ensp;购&ensp;人</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="09|yshd|hdrq|stri|[货到日期]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">货到日期</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="09|yshd|htbh|stri|[合同编号]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">合同编号</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="09|yshd|qsrq|stri|[投入使用日期]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">投入使用日期</span>
					</div>
				</div>
			</div>
		</div>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-xiangxi"></i>
            		动&ensp;物&ensp;类
            	</div>
        	</div>
			<div class="container-fluid box-content">
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="10|yshd|sccj|stri|[产地]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">产&emsp;&emsp;地</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="10|yshd|xss|stri|[销售商]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">销&ensp;售&ensp;商</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="10|yshd|cgr|stri|[采购人]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">采&ensp;购&ensp;人</span>
					</div>
				</div>
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="10|yshd|hdrq|stri|[货到日期]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">货到日期</span>
					</div>
				</div>	
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="10|yshd|htbh|stri|[合同编号]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">合同编号</span>
					</div>
				</div>
			</div>
		</div>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary">
            		<i class="fa icon-xiangxi"></i>
            		无形资产类
            	</div>
        	</div>
			<div class="container-fluid box-content">
				<div class="col-md-2 col-lg-2">
					<div class="checkbox-group">
						<input type="checkbox" name="12|yshd|qsrq|stri|[投入使用日期]"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">投入使用日期</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>
<%@include file="/static/include/public-list-js.inc"%>
<script type="text/javascript">
$(function(){
	//必填项checked
	var arr = "${modetype}".split(",");
	for(var i = 0; i < arr.length; i++){
		$(":checkbox[name='" + arr[i] + "']").prop("checked",true);
	}
	
	$("#btn_chk").click(function(){
		$(":checkbox").prop("checked",true);
	});
	//保存
	$("#btn_save").click(function(){
		var selectItem = [];
		$(":checkbox").filter(":checked").each(function(){
			selectItem.push($(this).attr("name"));
		});

		$.ajax({
   	    	type :"post",
			url:"${pageContext.request.contextPath}/fieldinfor/doSave",
			data:"selectItem="+selectItem.join(","),
			dataType:"json",
			success:function(val){
				close(index);	
				if(val.success){
					alert(val.msg);
				}else{
					alert(val.msg);
				}
			},
			error:function(val){
				close(index);
				alert(getPubErrorMsg());      					 
			},
			beforeSend:function(val){
				index = loading(2);
			}
		});
	});
});
</script>
</body>
</html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>编辑项目信息</title>
<%@include file="/static/include/public-manager-css.inc"%>

<style>
/* 	.radio-group{ */
/* 		height: 25px!important; */
/* 		line-height: 25px; */
/* 		vertical-align: middle; */
/* 		display: inline-block; */
/* 	} */
/* 	.radio-group > .rdo{ */
/* 		margin: 0px 0px 0px 10px; */
/* 		height: 25px; */
/* 	} */

.tables {
    margin-left: 20px;
    max-width: 100%;
    margin-bottom: 20px;
    width:50%;
   }
 .p1{
    text-align:center;
    font-family:宋体;
    color:red;
    font-size:18px;
   }
   .aa{
text-align:right;
}
[class^=addBtn]{
		text-align:center;
	    width: 20px;
	    height: 20px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	    margin-top:2px;
	}
	[class^=addBtn]:after{
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
/* 	    font-size: 17px; */
	    position: relative;
	    cursor: pointer;
	}
	[class^=deleteBtn]{
		text-align:center;
	    width: 20px;
	    height: 20px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	    margin-top:2px;
	}
	[class^=deleteBtn]:after{
/* 		2014 */
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
/* 	    font-size: 1em; */
	    position: relative;
	    cursor: pointer;
/* 	    top:3px; */
	}
 [class^=td_first_]{
 	text-align:center;
 	width:100%;
 	height:30px;
 }
 [class^=td_second_]{
 	width:100%;
 	height:30px;
 }
 [class^=td_three_]{
 	width:100%;
 	height:30px;
 }
 [class$=first_2],[class$=first_1],[class$=first_3]{
 width:20%;
 }
 [class$=second_1],[class$=second_2],[class$=second_3]{
 width:30%;
 }
 [class$=operation_1],[class$=operation_2],[class$=operation_3]{
 width:10%;
 }
 .td_input{
 	width:100%;
 	border:none;
 }
 a{
	 text-decoration:none;
 }
 .addBtn0,.addBtn1,.addBtn2,.addBtn3{
		text-align:center;
	    width: 20px;
	    height: 20px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.addBtn0:after,.addBtn1:after,.addBtn2:after,.addBtn3:after{
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
/* 	    font-size: 17px; */
	    position: relative;
	    cursor: pointer;
	}
	.delBtn1,.delBtn2,.delBtn3{
		text-align:center;
	    width: 20px;
	    height: 20px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.delBtn1:after,.delBtn2:after,.delBtn3:after{
/* 		2014 */
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
/* 	    font-size: 1em; */
	    position: relative;
	    cursor: pointer;
/* 	    top:3px; */
	}
	.radiodiv{
	    border: 1px solid #ccc;
	    border-top-right-radius: 4px;
		border-bottom-right-radius:4px;
/* 	    border-radius: 4px; */
	    height: 25px;
	    line-height: 25px;
	    padding-left: 6px;
	   
	    }
.double-input span:first-child:not(.connector) {
    float: left;
    height: 27px;
    line-height: 27px;
}
.double-input .input-group, .double-input .input[type=text] {
    float: left;
    width: 40%;
} 
.double-input span.required {
    color: red;
    padding: 3px;
    font-size: 14px;
}
</style>
</head>
<body>
<div id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="czr"  value="${loginId}">
	<input type="hidden" name="czrq" value=""/>
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>预算管理模型详细信息</span>
		</div>
        <div class="pull-right">
            <button type="button" class="btn btn-default" id="btn_save" >保存</button>
            <button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	
	<div class="box" style="top:36px">
	<form id="myform1">
	     <input type="hidden" id="guid" name="guid" value="${guid }">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>编辑管理模型信息</div>
            		<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
				<div class="col-md-4">
						<div class="input-group">	
							<span class="input-group-addon"><span class="required">*</span>模型编号</span>
							<input type="text" id="txt_bmh" class="form-control input-radius" readonly name="xmlxbh"  value="系统自动生成"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group ">
							<span class="input-group-addon"><span class="required">*</span>模型名称</span>
							<input type="text" id="txt_bmbh" value="${xmxx.BMBH }" name="bmbh" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请输入管理模型名称"/>
						</div>
                    </div>
					<div class="col-md-4">
	            		<div class="double-input ">
	                      <span class="input-group-addon"><span class="required">*</span>金额区间</span>
	                      <div class="input-group" >
	                          <input type="text" id="txt_jeqj_begin"  class="form-control input-radius" name="jeqj1" placeholder="最小金额" value="" >
	                      </div>
	                      <span class="connector" style="float:left;margin-left:2px;margin-right:2px;">—</span>
	                      <div class="input-group" >
	                          <input type="text" id="txt_jeqj_end"  class="form-control input-radius" name="jeqj2" placeholder="最大金额" value="">		                          
	                      </div>
	                  </div>
				    </div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>模型分类</span>
							<input type="text" id="txt_xmmc" name="xmmc" value="${xmxx.XMMC }" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" placeholder="请输入项目名称" />
						</div>
                    </div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目大类</span>
                            <select id="txt_xmdl" class="form-control input-radius select2" name="xmdl">
                            	<option value="">未选择</option>
                           		<option value="1">部门业务费</option>
                           		<option value="2">科研经费</option>
                           		<option value="3">其他经费</option>
                           		<option value="4">专项经费</option>
                            </select>
                        </div>
					</div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>预算方法</span>
                            <select id="txt_xmlb" class="form-control input-radius select2" name="xmlb">
                            	<option value="">未选择</option>
                           		<option value="1" >固定预算</option>
                           		<option value="2" >零基预算</option>
                           		<option value="3" >弹性预算</option>
                           		<option value="4" >滚动预算</option>
                           		<option value="5" >作业预算</option>
                            </select>
                        </div>
					</div>
				</div>
			<div class="row">
				<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-addon" style="min-width: 150px!important;">是否启用</span>
						<div class="switch">
							<div class="onoffswitch">
								<input type="checkbox" name="zdscpz" id="zdscpz" class="onoffswitch-checkbox" checked="" value="Y">
								<label class="onoffswitch-label" for="zdscpz"> <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span>
								</label>
							</div>
						</div>
					</div>
				</div>
			</div>	
			</div>
         </div>
         </form>
	</div>
</div>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$("#btn_back").click(function(e){
	window.location.href  = "${ctx}/webView/ysgl/yssz/glmxsz.jsp";
});

$("#btn_save").click(function(){
	$.ajax({
			url:"",
			type:"post",
			data:"ms=",
			success:function(data){
				alert("保存成功");  
				window.location.href  = "${ctx}/webView/ysgl/yssz/glmxsz.jsp";
			}
	}); 
	
});
</script>
</body>
</html>
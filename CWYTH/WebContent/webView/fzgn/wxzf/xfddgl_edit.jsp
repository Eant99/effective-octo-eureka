<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>消费地点详细信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.sub-title2{
		font-size:14px;
	}
	.number{
	text-align:right;
	}
	.radiodiv{
    border: 1px solid #ccc;
    border-top-right-radius: 4px;
	border-bottom-right-radius:4px;
	    /*border-radius: 4px; */
    height: 25px;
    line-height: 25px;
    padding-left: 6px;
   
    }
	.text-color{
	    color:red!important;
	}
	.addBtn{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.addBtn:after{
/* 		2722 */
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 17px;
	    position: relative;
	    cursor: pointer;
	}
	.deleteBtn{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.deleteBtn:after{
/* 		2014 */
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 1em;
	    position: relative;
	    cursor: pointer;
	    top:3px;
	}
	.btn_td{
		width:10px!important;
		height:6mm!important;
		text-align: center;
	}
	.border{
		border:1px solid #a94442;
		background:url("${ctx}/webView/wsbx/rcbx/no_null.bmp") center right no-repeat;;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType }">
	<input type="hidden"  id="guid" name="guid"  value="${map.GUID}">
	
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑消费地点信息</span>
		</div>
		<div class="pull-right" style="margin-top:3px;">
			
        </div>
		
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title2 pull-left text-primary"><i class="fa icon-xiangxi"></i>消费地点基本信息</div>
            	
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
            	<div class="pull-right">
			         <button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			         <button type="button" class="btn btn-default" id="btn_back">返回</button>
                 </div>
        	</div>
			<hr class="hr-normal">
			
			<div class="container-fluid box-content">
			
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>消费地点编号</span>
							<input type="text" id="txt_xfddbh" name="xfddbh" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  value="${xfddxx.XFDDBH}"/>
						</div>
                    </div>
                    <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>消费地点名称</span>
                            <input type="text" id="txt_xfddmc" class="form-control input-radius" name="xfddmc" value="${xfddxx.XFDDMC}"/>
						</div>
					</div>
					
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							 <span class="input-group-addon"><span class="required">*</span>所属承包商</span>
<%-- 	                         <input type="text" id="txt_sscbsbh" class="form-control input-radius" name="sscbsbh" value="${xfddxx.SSCBSBH}"> --%>
                                   <select name="sscbsbh" class="form-control input-radius ">
                                   <option value="">请选择</option>
                                     <c:forEach var="cbsList" items="${cbsList}">
                                       <option value="${cbsList.CBSBH}" <c:if test="${cbsList.CBSBH==xfddxx.SSCBSBH}">selected</c:if>>${cbsList.CBSMC}</option>
                                      </c:forEach>
                                  </select>
						
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>所属校区</span>
	                        <select name="zlwz" class="form-control input-radius ">
                                   <option value="">请选择</option>
                                     <c:forEach var="xqlist" items="${xqlist}">
                                       <option value="${xqlist.DM}" <c:if test="${xqlist.DM==xfddxx.ZLWZ}">selected</c:if>>${xqlist.MC}</option>
                                      </c:forEach>
                                  </select>
						</div>
					</div>
					
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>状态</span>
							<select id="zt" name="zt" class="form-control input-radius ">
							   <option  value="1" <c:if test="${xfddxx.ZT=='1' }">selected</c:if>>正常</option>
							   <option value="0" <c:if test="${xfddxx.ZT=='0' }">selected</c:if>>不正常</option>
							</select>							
						</div>
                  </div>   
                  <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">负责人</span>
	                        <input type="text" id="txt_fzr" class="form-control input-radius" name="fzr" value="${xfddxx.FZR}">
						</div>
					</div>              
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>业务类型</span>
							<select id="ywlx" name="ywlx" class="form-control input-radius ">
							 <option value="">请选择</option>
								<c:forEach var="item" items="${ywlxlist}"> 
								   <option value="${item.DM}" <c:if test="${xfddxx.YWLX == item.DM}">selected</c:if> >${item.MC}</option>
								</c:forEach>
							</select>							
						</div>
                  </div>   
				</div>
				
				</div>
<!-- 				上传消费地点二维码注释    需要可以放开 -->
<!-- 				 <div class="box-panel"> -->
<!-- 			<div class="box-header clearfix"> -->
<!-- 				<div class="sub-title pull-left text-primary"> -->
<!-- 					<i class="fa icon-xiangxi"></i>上传消费地点二维码 -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 			<div class="container-fluid box-content"> -->
<!-- 				<div class="row"> -->
<!-- 					<div class="col-md-8"> -->
<!-- 						<input id="imageFile" name="path" type="file" multiple class="file-loading"/> -->
<!-- 						<div id="errorBlock" class="help-block"></div> -->
<!-- 					</div> -->
<!-- 				</div> -->
			</div>
		</div>
		</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var tag = false;
var operate = "${operateType}";
$(function(){
    /* 图片信息开始 */
	var fjView = [<%=request.getAttribute("fjView")%>];
	var fjConfig = [<%=request.getAttribute("fjConfig")%>];
	//附件信息
     $("#imageFile").fileinput({
       	fileActionSettings:{
    		showUpload:false//隐藏上传按钮
    	},
    	initialPreview:fjView,
    	initialPreviewAsData:true,
    	initialPreviewConfig:fjConfig,
    	uploadUrl: '${ctx}/file/fileUpload',//上传请求路径
        maxFilePreviewSize: 10240,//最大上传文件的大小
        showUpload:false,//是否显示上传按钮
        initialPreviewShowDelete:true,
        showBrowse:true,
        showCaption:true,
        showClose:false,
        uploadExtraData:function(id,index){
        	return {"fold":"XFDDGL","id":"${map.GUID}","djlx":"000000"};
        },//区分不同的模块：fold：文件夹，id:生成文件的id(主键)，djlx:单据类型。
        //uploasExtraData:是把页面你想要往后退传的东西放（return）     到域里面然后去后台去取
        overwriteInitial: false,  
        deleteUrl: "${ctx}/file/fileDelete"//删除文件的路径
    });
   //扫码上传
 	SmscLoad("${ctx}",{"id":"${map.GUID}","djlx":"000000","fold":"LDQZ","rybh":"000000","mkbh":"xfddgl"},"imageFile",getPname());
	/*图片信息结束  */ 
});
//
$(function(){
	//验证方法
	var validate = $('#myform').bootstrapValidator({
		fields:{
          xfddbh:{validators:{notEmpty: {message: '消费地点编号不能为空'},regexp: {regexp: /^[a-zA-Z0-9_]+$/,message: '消费地点编号只能包含大写、小写、数字和下划线'},stringLength:{message:'编号过长',max:'20'}}},
          xfddmc:{validators:{ notEmpty: {message: '消费地点名称不能为空'}}},
          sscbsbh:{validators: {notEmpty: {message: '所属承包商不能为空'}}},
          ywlx:{validators: {notEmpty: {message: '业务类型不能为空'}}},
          zt:{validators: {notEmpty: {message: '状态不能为空'}}},         
          }
	      });
	 //保存按钮
	$("#btn_save").click(function(){
		
		$("#imageFile").fileinput("upload");
		doSave(validate,"myform","${ctx}/xfddgl/doSave",function(val){
			if(val.success == "true"){
				tag = true;
                window.location.href = "${ctx}/xfddgl/goPageList";
			}
		});
	}); 
	function doSaveFb(_validate, _formId, _url, _success, _fail){
		var index;
		var valid;
		if(_validate){
			_validate.bootstrapValidator("validate");
			valid = $('#' + _formId).data('bootstrapValidator').isValid();
		} else {
			valid = true;
		}
		if(valid){
			$.ajax({
				type:"post",
				url:_url,
				data:arrayToJson($('#' + _formId).serializeArray()),
				success:function(val){
					next();
					close(index);
					var data = JSON.getJson(val);
					if(data.success == "true"){
						$("#operateType").val("U");
						if(_success != "" && _success != "" && _success != ""){
							_success(data);
						}
					} else {
						alert(data.msg);
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
		}
	}
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/xfddgl/goPageList";
	});
	//刷新按钮
	$(".reload").click(function(){
		 var operateType =  $("[name='operateType']").val();
		 if(operateType=='U'){
			 window.location.href = window.location.href+"&operateType=U&rybh=${ryb.rybh}"
		 }else{
			 var url = window.location.href;
	    	if(url.indexOf("?")>0){
	    		window.location.href = window.location.href+"&gxgdzc_uuid=googosoft2016"
	    	}else{
	    		window.location.href = window.location.href+"?gxgdzc_uuid=googosoft2016"
	    	}
		 }
	});
});
//新增按钮
$.fn.serializeObject1 = function(start,end){
	var f = {"list":[]};
    var a = this.serializeArray();
    var o = {};
    $.each(a, function() {
    	if (this.name == start) {
        	o = {};
        	o[this.name] = this.value;
        } else if(this.name == end){
        	o[this.name] = this.value;
        	f["list"].push(o);
        }else{
        	o[this.name] = this.value;
        }
    });
    	console.log(JSON.stringify(f));
    return f;
};
var jsbh = $("#txt_jsbh1").val();
</script>
</body>
</html>
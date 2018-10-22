<%@page import="com.googosoft.commons.LUser"%>
<%@page import="com.googosoft.pojo.FileBean"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.googosoft.controller.base.FileController"%>
<%@page import="com.googosoft.util.Validate"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%
String path = request.getContextPath();
String url = request.getParameter("url");
String rybh = Validate.isNullToDefault(request.getParameter("rybh"),LUser.getRybh()).toString();
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	.btn-file{
		height: 27px;
	    width: 86px;
	    position: relative;
	    border: 0px
    }
    .btn-primary:hover{
    	background-color: #337ab7 !important;
    }
    .btn-file .hidden-xs{
	    height: 26px;
	    width: 58px;
	    background-color: #337ab7;
	    display: block;
	    position: absolute;
	    top: 0px;
    }
    #imageFile{
    	position: absolute;
	    top: 3px;
	    z-index: -1;
	    width: 66px;
	    background-color: #337ab7;
    }
</style>
<title>图片处理页面</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap/bootstrap.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/public/font-awesome.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/cropper/cropper.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/cropper/main.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/layer/skin/layer.css">
</head>
<body>
<div class="container" style="width:90%;padding-top:10px;">
   	<div class="row">
      	<div class="col-sm-8">
	        <div class="img-container">
	          	<img id="image" src="<%=url.indexOf("?")>=0?url.substring(0,url.indexOf("?")):url%>" alt="Picture">
	        </div>
      	</div>
      	<div class="col-sm-4">
	        <div class="docs-preview clearfix">
				<div class="img-preview preview-lg" id="d_lg" style="width:260px;"></div>
	        </div>

       		<div class="docs-data" style="margin-top:20px;">
	          	<div class="input-group input-group-sm">
		            <label class="input-group-addon" for="dataWidth">截取宽度</label>
		            <input type="text" class="form-control int" id="data_Width" data-option="width" value="150" style="text-align: right;" placeholder="截取宽度">
		            <span class="input-group-addon">像素</span>
	          	</div>
	          	<div class="input-group input-group-sm">
		            <label class="input-group-addon" for="dataHeight">截取高度</label>
		            <input type="text" class="form-control int" id="data_Height" data-option="height" style="text-align: right;" placeholder="截取高度">
		            <span class="input-group-addon">像素</span>
	          	</div>
        	</div>
        	<div class="btn-group" style="margin-top:20px;">
	          	<button type="button" class="btn btn-primary" data-method="move" data-option="-10" data-second-option="0" title="Move Left">
		            <span class="docs-tooltip" data-toggle="tooltip" title="左移">
		              	<span class="faw fa-arrow-left"></span>左移
		            </span>
	          	</button>
	          	<button type="button" class="btn btn-primary" data-method="move" data-option="10" data-second-option="0" title="Move Right">
		            <span class="docs-tooltip" data-toggle="tooltip" title="右移">
		              	<span class="faw fa-arrow-right"></span>右移
		            </span>
	          	</button>
	          	<button type="button" class="btn btn-primary" data-method="move" data-option="0" data-second-option="-10" title="Move Up">
		            <span class="docs-tooltip" data-toggle="tooltip" title="上移">
		              	<span class="faw fa-arrow-up"></span>上移
		            </span>
	          	</button>
	          	<button type="button" class="btn btn-primary" data-method="move" data-option="0" data-second-option="10" title="Move Down">
		            <span class="docs-tooltip" data-toggle="tooltip" title="下移">
		              	<span class="faw fa-arrow-down"></span>下移
		            </span>
	          	</button>
	       	</div>
        	
        	<div class="btn-group" style="margin-top:10px;">
	          	<button type="button" class="btn btn-primary" data-method="zoom" data-option="0.1" title="">
		            <span class="docs-tooltip" data-toggle="tooltip" title="放大">
		              	<span class="faw fa-search-plus"></span> 放大
		            </span>
	          	</button>
	          	<button type="button" class="btn btn-primary" data-method="zoom" data-option="-0.1" title="">
		            <span class="docs-tooltip" data-toggle="tooltip" title="缩小">
		              	<span class="faw fa-search-minus"></span> 缩小
		            </span>
	          	</button>
	          	<button type="button" class="btn btn-primary" data-method="rotate" data-option="45" title="Rotate Right">
		            <span class="docs-tooltip" data-toggle="tooltip" title="顺时针旋转">
		              	<span class="faw fa-rotate-right"></span> 顺时针旋转
		            </span>
	          	</button>
	       	</div>
        	
        	<div class="btn-group" style="margin-top:10px;">
	          	<button type="button" class="btn btn-primary" data-method="reset" title="Reset">
		            <span class="docs-tooltip" data-toggle="tooltip" title="重置">
		              	<span class="faw fa-refresh"></span> 重置
		            </span>
	          	</button>
	          	<button type="button" class="btn btn-primary" id="btn_set" style="margin:0px auto;">
		            <span class="docs-tooltip" data-toggle="tooltip" title="预览">
		              	<span class="faw fa-th"></span> 预览
		            </span>
	          	</button>
	          	<button type="button" class="btn btn-primary" id="btn_save" style="margin:0px auto;">
		            <span class="docs-tooltip" data-toggle="tooltip" title="保存">
		              	<span class="faw fa-floppy-o"></span> 保存图片
		            </span>
	          	</button>
	       	</div>
      	</div>
    </div>
    <div class="btn-group" style="margin-top:0px;margin-left: 220px;">
		<input id="imageFile" name="imageFile[]" type="file" multiple class="file-loading" style="display:none;">
	</div>
</div>
<script src="${pageContext.request.contextPath}/static/javascript/jquery/jquery.js"></script>
<script src="${pageContext.request.contextPath}/static/javascript/bootstrap/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/cropper/cropper.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/datetimepicker/bootstrap-datetimepicker.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/static/javascript/public/public-js.js"></script>
<script src="${pageContext.request.contextPath}/static/javascript/public/public-window.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/fileinput/fileinput.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	cutOutImg();
	$("#imageFile").fileinput({
	       	fileActionSettings:{
	    		showUpload:false//隐藏上传按钮
	    	},
	    	initialPreviewAsData:true,
	        uploadUrl: '${pageContext.request.contextPath}/grsz/PerPhotoUpload',//上传请求路径
	        maxFilePreviewSize: 10240,//最大上传文件的大小
	        showUpload:false,//是否显示上传按钮
	    	initialPreviewShowDelete:false,
	        showBrowse:true,
	        showPreview:false,
	        showCaption:false,
	        showRemove:false,
	        showClose:false,
	        uploadExtraData:function(id,index){
	        	return {"fold":"ryphoto","id":"<%=rybh%>"};
	        },//上传额外的东东
	        allowedFileExtensions : ['jpg', 'png','gif'],//允许的文件类型  
	        overwriteInitial: true,  
	        maxFileSize: 1000,//文件的最大大小  
	        maxFilesNum: 1//最多文件数量 
	    }).on("filebatchselected", function(event, files) {
            $(this).fileinput("upload");
        }).on("fileuploaded",function(event,data){
	    	//每个文件上传成功后
		    $(".file-input .kv-upload-progress").css("display","none");
		    $(".file-input .fileinput-remove-button").css("display","none");
	    	$("#image").cropper("destroy");
	    	$("#image").attr("src",data.response.initialPreview[0]);
	    	$(':button[data-method]').unbind("click");
	    	$("#btn_set").unbind("click");
	    	$("#btn_save").unbind("click");
	    	cutOutImg();
	    });
	 	$("#imageFile").prev("span.hidden-xs").html($(".txfont").val());
	    $(".glyphicon-folder-open").remove();
	    
	  	//上传按钮点击事件
		$(".btn-file .hidden-xs").click(function(){
			$("#imageFile").click();
		});
});
var cutOutImg = function (){
	var h = parseFloat("<%=Validate.isNullToDefault(request.getParameter("h"), "1")%>");
	var w = parseFloat("<%=Validate.isNullToDefault(request.getParameter("w"), "1")%>");
	'use strict';//表示强规则
	var console = window.console || { log: function () {} };
  	var URL = window.URL || window.webkitURL;
  	var $image = $('#image');
  	var $dataX = $('#data_X');
  	var $dataY = $('#data_Y');
  	var $dataHeight = $('#data_Height');
  	var $dataWidth = $('#data_Width');
  	var $dataRotate = $('#data_Rotate');
  	
	var options = {
		preview:'.img-preview',//设置预览的图片显示在class="img-preview"的容器里
		viewMode:2,//0:裁剪框能在整个背景框内移动  1：裁剪框只能在图片内移动  2：图片不全部铺满背景框  3：图片全部铺满背景框
		dragMode:'move',//拖拽模式，move是背景图片可移动，crop是背景图片不能移动
        crop: function (e) {
        	initData(e);
        },
        resizable:false,
        data:{"width":230,"height":300},
        
  	};
  	$image.on({
    	'crop.cropper': function (e) {//图片移动或缩放时执行（注意：如果不允许移动也不允许缩放，则不会触发该事件）
    		initData(e);
    	}
  	}).cropper(options);
  	
  	$(':button[data-method]').on('click', function () {
  		var data = $(this).data();
  		switch (data.method) {
  			case "move":
  				$image.cropper(data.method, parseFloat(data.option),parseFloat(data.secondOption));
  			break;
  			default:
  				$image.cropper(data.method, parseFloat(data.option));
  			break;
  		}
  		initData($image.cropper("getData"));
  	});
  	
  	$("#btn_set").click(function(){
  		setData();
  	});
  	
  	$("#btn_save").on("click",function(){
  		var flag = setData();
  		if(flag){
  			var originalImageURL = $image.attr('src');
	  		var data = $image.cropper("getData");
	  		var path = "<%=path%>/";
	  		var pre = window.location.href.substr(0,window.location.href.indexOf(path));
	  		originalImageURL = originalImageURL.replace(pre,"").replace(path,"");//因为有可能路径有可能是已经拼接好的，也有可能是还没有拼接的，所以先把前缀替换到
	  		data.oldurl = originalImageURL;
	  		data.fold = "imgFile/ryphoto/";
	  		data.rybh = "<%=rybh%>";
	  		 $.ajax({  
		        url: "${pageContext.request.contextPath}/convert/cuttingimg",
		        type :"post",
		        data: data,
		        dataType:"json",
		        success: function (result) {
		        	close(index);
		        	alert(result.msg);
		        	if(result.success){
			        	var pname = "${param.pname}";
						var win = getIframWindow(pname);
						win.$(".grtx").attr("src",path + result.url+"?sysdate="+Math.random());
			        	var winId = getTopFrame().layer.getFrameIndex(window.name);
			        	close(winId);
		        	}
		       	},
		        error: function (returndata) {
		        	close(index);
		        	console.log("error:"+returndata);
		        },
				beforeSend:function(val){
					index = loading(2);
				}
	 		}); 
  		}
  	});

  	var initData = function(data){
  		$dataX.val(Math.round(data.x));
  		$dataY.val(Math.round(data.y));
  		$dataHeight.val(Math.round(data.height));
  		$dataWidth.val(Math.round(data.width));
  		$dataRotate.val(data.rotate);
  	};
  	var setData = function(){
  		var sdata = {};
  		var msg = "";
  		$(":text[id^='data_']").each(function(){
  			var val = $(this).val();
  			var op = $(this).data().option;
  			if(val != ""){
  	  			var d = parseFloat(val);
  	  			if(isNaN(d)){
  	  				msg = "请输入数值";
  	  			}
  	  			else if(op == "rotate" && d < 0){
  	  				msg = "旋转角度只能是正整数";
  	  			}
  	  			else{
  	  				sdata[op] = d;
  	  			}
  			}
  		});
  		
  		if(msg != ""){
  			alert(msg);
  			return false;
  		}else if(sdata == {}){
  			alert("请输入需要设置的选项");
  			return false;
  		}
  		else{
  			$image.cropper("setData",sdata);
  			return true;
  		}
  	}
};

</script>
</body>
</html>
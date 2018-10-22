<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>在职工资信息导入</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
.row{
  margin-right:0
}
</style>
</head>
<body>
<form id="myform"  class="form-horizontal" action="${ctx}/xzgl/upload?pname=${param.pname}" method="post" enctype="multipart/form-data">
           <div class="box" style="margin-top: 17px;">
                <div class="row">
                     <div class="col-md-12">
                           <div class="alert alert-danger" style="font-size: 14px;width: 530px"
                                role="alert">
                                <strong>注 意：</strong><br />
                                &emsp;&emsp;为保证您的数据正确导入，请先下载模板并在模板上输入所需导入数据（示例数据可删除）
                           </div>
                     </div>
                </div>
                <br>
                <div class="row">
                     <div class="col-md-2 ">
                           <div class="input-group">
                                <span class="input-group-addon" style="border-radius: 5px;">数据位置</span>
                                <div class="col-md-12" style="height: 25px;">
                                     <input id="imageFile" name="imageFile" type="file"
                                           class="file-loading">
                                     <div id="errorBlock" class="help-block"></div>
                                </div>
                           </div>
                     </div>
                </div>
                <div class="row">
                     <div class="col-md-2 ">
                           <div class="input-group">
                                <span class="input-group-addon"
                                     style="height: 25px; border-radius: 5px;">模板下载</span>
                                <div style="margin-top: 5px;">
                                     <a href="javascript:void(0);" id="btn_exp"
                                           style="color: red; font-size: 16px; text-decoration: none;">&nbsp;&nbsp;&nbsp;下载模板</a>
                                </div>
                           </div>
                     </div>
                </div>
                <div class="row">
                     <div class="col-md-2 col-md-offset-1">
                           <button name="ibtnBf" type="button" id="ibtnBf"
                                class="ibtn_bf btn btn-default btn-lg btn-block" style="width: 530px;">
                                <i class="faw fa-cloud-upload"
                                     style="color: #03A9F4; font-size: 22px;"></i>导入数据
                           </button>
                     </div>
                </div>
                <div class="row">
                     <div class="col-md-2 ">
                           <div style="overflow: auto; color: red;">
                                <c:forEach var="error" items="${error}">
                        ${error}
                     </c:forEach>
                           </div>
                     </div>
                </div>
           </div>
           <div class='page-bottom clearfix'>
                <div class="pull-right">
                      <button class="btn btn-default btn-without-icon" id="btn_cancelw">关闭</button> 
                </div>
           </div>
</form>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function() {
// 	if("${bool}"=="true"){
// 		alert("操作成功");
// 		var pname = "${param.pname}";
// 		var winId = getTopFrame().layer.getFrameIndex(window.name);
//     	close(winId);
// 		getIframWindow("${param.pname}").table.ajax.reload();
// 	}
// 	if("${bool}"=="false"){
// 		alert("操作失败");
// 		var pname = "${param.pname}";
// 		var winId = getTopFrame().layer.getFrameIndex(window.name);
//     	close(winId);
// 		getIframWindow("${param.pname}").table.ajax.reload();
// 	}
	//下载模板
// 	$("#btn_exp").click(function(){
// 		var fname = "薪资导入模板.xls";
// 		var path = "uploadFiles/薪资导入模板.xls";
// 		FileDownload("${pageContext.request.contextPath}/xzwhb/fileDownloadImpo",fname,path);
// 	});
	 
	//下载模板
	$("#btn_exp").click(function() {
		var texts = "${param.texts}";
		doExp("","${ctx}/xzgl/ZzryexpExcelnew?texts="+texts,"薪资导入模板","${pageContext.request.contextPath}","");
  		//doExp2("","${ctx}/xzgl/Zzxzdrmb","薪资导入模板","${pageContext.request.contextPath}","");
	});
	//取消
	$("#btn_cancelw").click(function(){
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
		getIframWindow("${pname}").table.ajax.reload();
	});	
	
	var commonWin = "${commonWin}";
	if (commonWin == 'T') {
		var file = "${file}";
		select_commonWin("${pageContext.request.contextPath}/xzgl/getImpXzQr?file="+ file ,"数据确认", "750", "550");
		return false;
	}
	
	$("#ibtnBf").click(function(){
		var fileName=$("#imageFile").val();
		if(fileName==null||fileName==""){
			alert("请选择文件上传");
		}else if(fileName.trim().substr(fileName.lastIndexOf("."))==".xlsx"){
			alert("请将.xlsx文件另存为.xls文件!");
		}else if(fileName.trim().substr(fileName.lastIndexOf("."))!=".xls"){
			alert("请选择.xls格式的文件!");
		}else{
			confirm("您确定导入吗？",{title:"提示"},function(index){
				$("#myform").submit();
   				close(index);
   			});
		}
	});
});
function doExp(_data, _url, _fileName, _path, _selectedId,xx){
	var index;
	if(xx == undefined || xx == ""){
		xx = "确认下载全部信息？";
		if(_selectedId !=''&& _selectedId !=undefined){
			xx = "确认要下载选中的信息？";
		}
	}
	confirm(xx, {title:"提示"}, function(z){
		$.ajax({
			type:"post",
			data:"searchJson=" + _data + "&xlsname=" + _fileName + "&id=" + _selectedId,
			url:_url,
			dataType:"json",
			success:function(val){
				close(index);
				close(z);
				FileDownload(_path + "/file/fileDownload", _fileName + ".xls", val.url);
			},
			error:function(){
				close(index);
				alert(getPubErrorMsg());
			},
			beforeSend:function(){
				index = loading();
			}
		});
	}); 
}
</script>
</body>
</html>
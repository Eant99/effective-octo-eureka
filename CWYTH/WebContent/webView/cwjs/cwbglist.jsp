<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*"
	pageEncoding="utf-8"%>
<%
PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
//设置服务器页面
poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
poCtrl.addCustomToolButton("保存", "Save()", 1);
poCtrl.addCustomToolButton("打印", "PrintFile()", 6);
poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
//设置保存页面
poCtrl.setSaveFilePage("SaveFile.jsp");
//打开Word文档
poCtrl.webOpen("/CWYTH/webView/cwjs/doc/cwbg.doc",OpenModeType.docNormalEdit,"张佚名");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
  <head>
   <title>最简单的打开保存Word文件</title>
   <%@include file="/static/include/public-manager-css.inc"%> 
<style type="text/css">
.row{margin-right:0px;margin-left: -15px;}
</style>
</head>
<body>
    <div style="position:absolute;top:5px; width:100%; height:100px;">

    <div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon" >年度:</span>
							 <select class="form-control" id="xznd">
								  <option value="1" >2016年</option>
								  <option value="2">2017年</option>
								  <option value="3" selected = "selected">2018年</option>
							</select>
						</div>
   					 </div>
    </div>
    <div style="position:absolute;top:30px;width:100%; height:845px;">
        <%=poCtrl.getHtmlCode("PageOfficeCtrl1")%>
    </div>
    <!-- PageOffice.js文件一定要引用 -->
  <%@include file="/static/include/public-list-js.inc"%>
 <script type="text/javascript" src="../jquery.min.js"></script>
 <script type="text/javascript" src="../pageoffice.js"  id="po_js_main"></script>
    <script type="text/javascript">
    $(document).ready(function() {
        $('#xznd').change(function(){
             var nd = $("#xznd").val();
             if(nd=='2'){
            	 location.href="/CWYTH/webView/cwjs/cwbg2017.jsp";
             }else if(nd=='3'){
            	 location.href="/CWYTH/webView/cwjs/cwbg.jsp";
             }else{
            	 location.href="/CWYTH/webView/cwjs/cwbg2016.jsp";
             }
        });
	});
        function Save() {
             document.getElementById("PageOfficeCtrl1").WebSave();
             
           }
        function PrintFile(){
             document.getElementById("PageOfficeCtrl1").ShowDialog(4); 
             
           }
        function IsFullScreen(){
             document.getElementById("PageOfficeCtrl1").FullScreen = !document.getElementById("PageOfficeCtrl1").FullScreen;
             
           }
        function CloseFile(){
             POBrowser.closeWindow(); 
           }
    </script>
</body>
</html>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>综合分类</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.box {
    margin-left: 2px !important;
    margin-right: 0px !important;
    position: relative;
    margin-top: 0px !important;
    margin-bottom: 0px;
    background-color: white;
    padding: 0px !important;
    border-radius: 2px;
}
.box .box-content{
    margin-bottom: 0px!important;
}
body{
	overflow-x:hidden;
}
.tool-fix{
		display:none;
	}
</style>
</head>
<body class="flxx">
<div class="box">
	<div class='box-content' style="padding-bottom: 0px;">
		<div class="alert alert-info" style="padding: 6px;margin-bottom: 4px;">
	       	<strong>提示：</strong>先找到需要的分类信息，然后<strong>双击</strong>这条信息。
	    </div>
		<hr class="hr-normal">
              <div class='responsive-table'>
                 <div class='scrollable-area'>
                 <table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
		      <thead>
			      <tr id="header">
			       	  <th><input type="checkbox" value="" class="select-all"/></th>
			          <th>分类代码</th>
			          <th>分类名称</th>
			          <th>财政分类</th>
			      </tr>
		      </thead>
		      <tbody>
		      </tbody>
		  </table>
		  </div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function (){
	var post = "${param.post}";
	var winId;
	if(post=='A'){
		winId = getTopFrame().layer.getFrameIndex(parent.parent.window.name);
	}else{
		winId = getTopFrame().layer.getFrameIndex(parent.window.name);
	}
	//var flbm = "${param.flbm}";//点击左侧树上的节点，传过来的flbm（分类号去掉右侧的0）
    table = $('#mydatatables').DataTable({
        ajax: {
            url: "${pageContext.request.contextPath}/window/flxx?treesearch=${param.treesearch}&flbm=${param.flbm}&lyr=${param.lyr}&flh=${param.flh}"//获取数据的方法
        },
        "pagingType":"full_numbers",
        "lengthMenu":[14],
        "order": [ 1, 'asc' ],
        "scrollX": true,
        "scrollY": false,
        "serverSide": true,
        "columns": [
			{"data": "FLH",orderable:false, 'render': function (data, type, full, meta){
				return "<input type='checkbox' class='keyId' name='flh' value='"+data+"' flmc='" + full.FLMC + "'><input type='hidden' class='keyId' name='flmc' value='"+full.FLMC+"'><input type='hidden' class='keyId' name='czfl' value='"+full.CZFLMC+"'> <input type='hidden' class='keyId' name='czdm' value='"+full.FFLDM+"'> <input type='hidden' class='keyId' name='bzdm' value='"+full.CZBZDM+"'>";
			},"width":10,'searchable': false},
			{"data": "FLH",defaultContent:""},
			{"data": "FLMC",defaultContent:""},
			{"data": "FLH",'render': function (data, type, full, meta){
				return full.CZFLMC;
			},defaultContent:""}
        ],
        "language":{
            "search":"",
            "searchPlaceholder":"请输入分类信息关键字"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>",
    });
	$("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
    //双击事件
    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	
    	var flh = $(this).find("[name='flh']").val();
    	if(flh==''||flh==null||flh=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		var flmc = $(this).find("[name='flmc']").val();
        	var czfl = $(this).find("[name='czfl']").val();
        	var yqmc = $(this).find("[name='flmc']").val();
        	var bzdm = $(this).find("[name='bzdm']").val();
        	var dqflh = "${param.dqflh}";
        	var mkbh = "${param.mkbh}";
        	var URL="";
        	if("plfz"=='${param.flag}'){
        		URL= "${pageContext.request.contextPath}/zcfltree/checkSftj_plfz?flh="+flh+"&dqflh="+dqflh+"&czfl="+czfl+"&bzdm="+bzdm;//检测
        	}else{
        		URL= "${pageContext.request.contextPath}/zcfltree/checkSftj?flh="+flh+"&dqflh="+dqflh+"&czfl="+czfl+"&bzdm="+bzdm;
        	}
        	$.ajax({
    			url:URL,
    			type:"post",
    			success:function(val){
    				var data = JSON.getJson(val);
    				if(data.success=="false" && "${param.Next}"=="F"){
    					getIframWindow("${param.pname}").location.href="${pageContext.request.contextPath}/yshd/goZcflPage?flh="+flh+"&flmc="+flmc+"&czfl="+czfl+"&bzdm="+bzdm+"&yqmc="+yqmc+"&operateType=C&mkbh="+mkbh+"&ysdh=${param.ysdh}";
    				}else if(data.success=="false" && "${param.Next}"=="N"){//需要跳转，但是又不允许跳转，例如：资产建账归口审核，选择分类号
    					alert("当前分类与选中的分类信息项差距较大，不允许选择！");
    				}else{
    					getIframeControl("${param.pname}","hid_gbmid").val(data.bzdm);
    					getIframeControl("${param.pname}","hid_mbh").val(data.mbh);
    					getIframeControl("${param.pname}","hid_flgbm").val(data.flgbm);
    					if("${param.Next}"=="T"){
    						getIframeControl("${param.pname}","${param.controlId}").val(flh);
    						getIframeControl("${param.pname}","txt_flmc").val(flmc);
    					}else if("${param.Next}"=="C"){
    						getIframeControl("${param.pname}","${param.controlId}").val("(" + flh + ")" + flmc);
    					}else{
    						if("plfz"=='${param.flag}'){
    							getIframeControl("${param.pname}","${param.controlId}").val("("+flh+")"+flmc);
    						}else{
    							getIframeControl("${param.pname}","${param.controlId}").val(flh);
    						}
    						if(getIframeControl("${param.pname}","txt_czfl").length > 0){
    							getIframeControl("${param.pname}","txt_czfl").val(czfl);
    				        	getIframeControl("${param.pname}","txt_czfl").focus();//手动触发验证
    				        	getIframeControl("${param.pname}","txt_czfl").trigger("blur");//手动触发验证
    						}
    						if(getIframeControl("${param.pname}","sp_czfl").length > 0){
    							getIframeControl("${param.pname}","sp_czfl").prop("innerHTML",czfl);
    						}
    			        	if(getIframeControl("${param.pname}","txt_yqmc").length > 0){
    			        		if('${yqmc}'==''){
    			        			getIframeControl("${param.pname}","txt_yqmc").val(yqmc);
    			        		}
    				        	getIframeControl("${param.pname}","txt_yqmc").focus();//手动触发验证
    				        	getIframeControl("${param.pname}","txt_yqmc").trigger("blur");//手动触发验证
    			        	}
    			        	if(getIframeControl("${param.pname}","txt_flmc").length > 0){
    			        		getIframeControl("${param.pname}","txt_flmc").val(flmc);
    				        	getIframeControl("${param.pname}","txt_flmc").focus();//手动触发验证
    				        	getIframeControl("${param.pname}","txt_flmc").trigger("blur");//手动触发验证
    			        	}
    			        	
    			        	//下边这句是干什么的？资产建账第一步首次选择资产的时候，因为${param.flmc}没有值，所以会报错
    			        	if("${param.flmc}" != ""){
    			        		getIframeControl("${param.pname}","${param.flmc}").val(flmc);
    			        	}
    			        	if(getIframeControl("${param.pname}","hid_bzdm").length > 0){
    			        		getIframeControl("${param.pname}","hid_bzdm").val(bzdm);
    			        	}
    			        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
    			        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
    					}
    				}
    		    	getTopFrame().close(winId);
    			}
    		});
    	}
    });
    if("${param.windowModel}" == "1"){
	   	$("div.button").prop("innerHTML","<button type='button' id='btn_sure' class='btn btn-primary' style='margin-right:4px;'>确定</button><button type='button' id='btn_cancel' class='btn btn-primary'>取消</button>");
	}
  	//确定按钮
   	$("#btn_sure").on("click",function(){
   		var checkbox = $("#mydatatables").find("[name='flh']").filter(":checked");
   		if(checkbox.length == 0){
   			alert("请先选择分类信息！");
   	   		return false;
   		}else{
	   		var selFlbhS = [];
	   		checkbox.each(function(){
	   			selFlbhS.push("("+$(this).val()+")"+$(this).attr("flmc"));
	   		});
	   		getIframeControl("${param.pname}","${param.controlId}").val(selFlbhS.join(";"));
	    	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
	    	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
	    	getTopFrame().close(winId);
   		}
   	});
	//取消
   	$("#btn_cancel").on("click",function(){
   		getTopFrame().close(winId);
   	});
});
</script>
</body>
</html>
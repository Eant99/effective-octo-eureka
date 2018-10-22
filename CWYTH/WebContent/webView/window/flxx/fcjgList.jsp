<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>反查结果</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<style type="text/css">
body{
	overflow-x:hidden;
}
.container-fluid {
    padding-right: 1px !important;
    padding-left: 1px !important;
}
.tool-fix{
		display:none;
}
.zhxxcx{
	margin-top:  2px !important;
}
.mar_left{
	margin-left: 15px !important;
}
th{
	padding: 5px 0px !important;
}
.sorting_disabled{
	width:  41px !important;
}
.page_jump{
	width:  80px !important;
	margin-left: 3px !important;
 }
 .paginate_button  a{
 	padding: 4px 6px !important;
 }
</style>
<body>
<div class="fullscreen">
	<div class="container-fluid">
		<div class="row" style="margin-left: -1px;">
			<div class="col-md-12 tabs">
				<ul id="tabTop" class="nav nav-tabs" role="tablist">
					<li role="presentation" id="btn_fcjg" class='active'><a>反查结果</a></li>
					<li role="presentation" id="btn_flxz"><a>分类选择</a></li>
					<li role="presentation" id="btn_zhfl"><a>综合分类</a></li>
					<li role="presentation" id="btn_radio" style="padding-top: 10px;padding-left: 10px;">
						<span><input id="rdo_yqmc" type="radio" name="A" value="rdo_yqmc" <c:if test="${yqmc !='' && flag=='yqmcSelected' }">checked="checked"</c:if> <c:if test="${yqmc ==''}">disabled="disabled"</c:if>><label for="rdo_yqmc">资产名称</label></span>
						<span><input id="rdo_flh" type="radio" name="A" value="rdo_flh" <c:if test="${flh !=''&& flag=='flhSelected' }">checked="checked"</c:if> <c:if test="${flh ==''}">disabled="disabled"</c:if>><label for="rdo_flh">分类名称</label></span>
					</li>
				</ul>
			</div>
		</div>
		<div id="fcjg">
			<div class='box-content' style="padding-bottom: 0px; overflow:visible;">
				<div class="alert alert-info" style="padding: 6px;margin-bottom: 4px;margin-left: 1px;margin-right: 1px;margin-top: 5px;"> 
		       		<strong>提示：</strong><strong>双击</strong>需要的信息进行，进行选择。
			    </div>
			</div>
			<hr class="hr-normal" style="margin-bottom: 0px;">
			<div class="box-content" >
				<div class="box box1 pull-left " style="margin-left: 1px;margin-right: 0px;width: 430px;;margin-bottom: 0px;margin-top: 0px;">
					<div class=" box-content" style=" padding-left: 0px;padding-right: 0px;">
            			<div class='responsive-table'>
               				<div class='scrollable-area'>
                  				<table id="example" class="table table-striped table-bordered">
				      				<thead>
				      					<tr id="header">
				          					<th>序号</th>
				          					<th style="width: 70px;">分类号</th>
				          					<th>分类名称</th>
				          					<th>资产名称</th>
				      					</tr>
				      				</thead>
				      				<tbody>
				      				</tbody>
								</table>
			  				</div>
						</div>
					</div>
				</div>
				<div class="box box2 pull-right"style="margin-left: 1px;margin-right: 1px;width: 498px;;margin-bottom: 0px;margin-top: 0px;">
					<div class=" box-content"style=" padding-left: 0px;padding-right: 0px;">
            			<div class='responsive-table'>
               				<div class='scrollable-area'>
                  				<table id="myTable" class="table table-striped table-bordered">
				      				<thead>
				      					<tr id="header">
				          					<th>序号</th>
				          					<th style="width: 70px;">分类号</th>
				          					<th>分类名称</th>
				      					</tr>
				      				</thead>
				      				<tbody>
				      				</tbody>
								</table>
			  				</div>
						</div>
					</div>
				</div>
			</div>
		</div>	
		<div id="flxz" style="height: 600px;display: none;">
			<iframe width="100%" height="100%" style="border: none;" src="${pageContext.request.contextPath}/zcfltree/goWindowFlxz?yqmc=${yqmc}&pname=${param.pname}&controlId=${param.controlId}&post=A&lyr=${param.lyr}&Next=${param.Next}&dqflh=${param.dqflh}&flag=${flag}"></iframe>      		
      	</div>
		<div id="zhfl" style="height: 600px;display: none;">
			<iframe width="100%" height="100%" style="border: none;" src="${pageContext.request.contextPath}/zcfltree/goWindow?yqmc=${yqmc}&pname=${param.pname}&controlId=${param.controlId}&post=A&lyr=${param.lyr}&Next=${param.Next}&dqflh=${param.dqflh}&flag=${flag}"></iframe>      		
      	</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script type="text/javascript">
$(function(){
	var post = "${param.post}";
	var winId;
	if(post=='A'){
		winId = getTopFrame().layer.getFrameIndex(parent.window.name);
	}else{
		winId = getTopFrame().layer.getFrameIndex(window.name);
	}
 	table = $('#example').DataTable({
        ajax: {
        	url: "${pageContext.request.contextPath}/zjb/getPageList?flh=${flh}&yqmc=${yqmc}&flag=${flag}&lyr=${lyr}"//获取数据的方法
        },
        "pagingType":"full_numbers",
        "lengthMenu":[13],
        "order": [ 2, 'asc' ],
        "serverSide": true,
        "columns": [
            {"data": "_XH",orderable:false, 'render': function (data, type, full, meta){
            	return data +"<input type='hidden' class='keyId' name='flh' value='"+full.FLH+"' flmc='" + full.YQMC + "'><input type='hidden' class='keyId' name='flmc' value='"+full.FLMC+"'><input type='hidden' class='keyId' name='czfl' value='"+full.GBMID+"'><input type='hidden' class='keyId' name='bzdm' value='"+full.CZBZDM+"'>";
		    },orderable:false,"width":41,searchable: false,"class":"text-center"},
            {"data": "FLH",defaultContent:""},
            {"data": "FLMC",defaultContent:""},
            {"data": "YQMC",defaultContent:""},
        ],
        "language":{
            "search":"",
            "searchPlaceholder":"请输入关键字"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'mar_left pull-left'p>>",
        "initComplete": function(){
        }
    });
 	 $(".box1 input[type=search]").addClass("zhxxcx");
     $("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width","180px");
 	 $(".icon-chaxun").css("position","absolute").css("top","6px").css("right","30px");
 	
 	table = $('#myTable').DataTable({
        ajax: {
        	url: "${pageContext.request.contextPath}/zjb/getPageListAll?flh=${flh}&yqmc=${yqmc}&flag=${flag}"//获取数据的方法
        },
        "pagingType":"full_numbers",
        "lengthMenu":[13],
        "order": [ 2, 'asc' ],
        "serverSide": true,
        "columns": [
            {"data": "_XH",orderable:false, 'render': function (data, type, full, meta){
            	return data +"<input type='hidden' class='keyId' name='flh' value='"+full.FLH+"' flmc='" + full.YQMC + "'><input type='hidden' class='keyId' name='flmc' value='"+full.FLMC+"'><input type='hidden' class='keyId' name='czfl' value='"+full.GBMID+"'><input type='hidden' class='keyId' name='bzdm' value='"+full.CZBZDM+"'>";
		    },orderable:false,"width":41,searchable: false,"class":"text-center"},
            {"data": "FLH",defaultContent:""},
            {"data": "FLMC",defaultContent:""},
        ],
        "language":{
            "search":"",
            "searchPlaceholder":"请输入关键字"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'mar_left pull-left'p>>",
        "initComplete": function(){
        }
    });
 	
 	$(".box2  input[type=search]").addClass("zhxxcx");
    $("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width","180px");
	$(".icon-chaxun").css("position","absolute").css("top","6px").css("right","30px");
    //双击事件，
    $(document).on("dblclick","#example tr:not(#header)",function(){
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
        	if("plfz"=='${flag}'){//列表页面进行批量赋值时，执行！进行多个分类号是否跨大类了
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
    						if("plfz"=='${flag}'){
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
    //双击列表某条数据事件
    $(document).on("dblclick","#myTable tr:not(#header)",function(){
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
        	if("plfz"=='${flag}'){
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
    						if("plfz"=='${flag}'){
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
    //点击,综合分类
    $("#btn_zhfl").click(function(){
    	$("#zhfl").css("display","block");
    	$("#fcjg").css("display","none");
    	$("#flxz").css("display","none");
    	$("#btn_fcjg").removeClass("active");
    	$("#btn_flxz").removeClass("active");
    	$("#btn_zhfl").addClass("active");
    	$("#btn_radio").css("display","none");
    });
    //点击,反查结果
    $("#btn_fcjg").click(function(){
    	$("#fcjg").css("display","block");
    	$("#zhfl").css("display","none");
    	$("#flxz").css("display","none");
    	$("#btn_zhfl").removeClass("active");
    	$("#btn_flxz").removeClass("active");
    	$("#btn_fcjg").addClass("active");
    	$("#btn_radio").css("display","");
    });
    //点击,分类选择
    $("#btn_flxz").click(function(){
    	$("#flxz").css("display","block");
    	$("#zhfl").css("display","none");
    	$("#fcjg").css("display","none");
    	$("#btn_zhfl").removeClass("active");
    	$("#btn_fcjg").removeClass("active");
    	$("#btn_flxz").addClass("active");
    	$("#btn_radio").css("display","none");
    });
    //单选按钮，资产名称被点击
    $("#rdo_yqmc").click(function(){
    	 window.location.href = "${pageContext.request.contextPath}/zcfltree/goWindow1?controlId=txt_flh&flh=${flh}&yqmc=${yqmc}&mkbh=${mkbh}&lyr=${lyr}&flag=yqmcSelected&pname=${param.pname}";
    });
    //单选按钮,分类号被点击
     $("#rdo_flh").click(function(){
    	 window.location.href = "${pageContext.request.contextPath}/zcfltree/goWindow1?controlId=txt_flh&flh=${flh}&yqmc=${yqmc}&mkbh=${mkbh}&lyr=${lyr}&flag=flhSelected&pname=${param.pname}";
    });
    //如果flh,yqmc为空，默认分类选择窗口被选中
    if('${yqmc}'=='' &&'${flh}'==''){
    	$("#btn_flxz").click();
    }
});
</script>
</body>
</html>
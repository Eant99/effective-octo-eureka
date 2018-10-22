<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>凭证查询</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.input_info{width:100px;}
	.div_bottom{width: 99%;position: absolute;bottom: 50px;}
	.bom{color:red;}
	.yc{display:none!important;}
	.dataTables_wrapper tr.selected td[colspan] {  padding: 0px!important; }
	.dataTables_wrapper tr td[colspan] { padding: 0px!important;}
	table.table-bordered.dataTable tbody th, table.table-bordered.dataTable tbody td {  border-bottom-width: 1px!important;	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>凭证状态</label>
					<div class="form-group">
						<select style="width:80px;" id="txt_pzzt" class="form-control" name="pzzt" >
						    <option value="0" <c:if test="${pzzt=='0' }">selected</c:if>>全部</option> 
							<option value="00"<c:if test="${pzzt=='00' }">selected</c:if>>已保存</option>
							<option value="01"<c:if test="${pzzt=='01' }">selected</c:if>>已复核</option>
							<option value="02"<c:if test="${pzzt=='02' }">selected</c:if>>已记账</option>
							<option value="99"<c:if test="${pzzt=='99' }">selected</c:if>>已结账</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label>凭证类型</label>
					<select id="txt_pzlx" class="form-control input-radius" name="zbpzlxmc"  >
                           <option value="">全部</option>
							<c:forEach var="pzlxmc" items="${pzlxmc}"  varStatus="st">
								<option value="${pzlxmc.pzlxmc}" <c:if test="${pzlxmc.pzlxmc==zbpzlxmc }">selected</c:if>>${pzlxmc.pzlxmc}</option>
							</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label>&emsp;凭证摘要</label>
					<div class="form-group">
						<input type="text" id="txt_zy" name="zy" class="form-control input-radius"  placeholder="凭证摘要" value="${zy}">
					</div>
				</div>
				<div class="form-group">
				 	<div class="form-group">
	                <label>日期</label>
	             	<input  name="pzrq" types="CX1" id="pzrqs" table="K" class="input_info date form-control" style="width:130px;" placeholder="开始时间" value="${pzrqs }" data-format="yyyy-MM-dd" readonly/>
					<label>至</label>
					<input  name="pzrq" types="CX2" id="pzrqf" table="K" class="input_info date form-control" placeholder="结束时间" style="width:130px;" value="${pzrqf }" data-format="yyyy-MM-dd" readonly/>
					</div>
					<button type="button" class="btn btn-primary" id="btn_search">
						<i class="fa icon-chaxun"></i>
					查 询
				 	</button>
				</div>
				<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>综合查询</span>
					<div class="search-more">
						<div class="form-group">
							<label>凭证起止编号</label><br>
							<input type="text" types="BHL" id="qsbh" name="pzbh" class="bhl" table="k" class="input_info  form-control" style="width:130px;" placeholder="凭证编号" value="${bhl }"  />
							<label>至</label>
							<input TYPE="text" types="BHH" id="jsbh" name="pzbh" class="bhh" table="k"  class="input_info  form-control" placeholder="凭证编号" style="width:130px;" value="${bhh }"  />
						</div>
						<div class="form-group">
							<label>起止会计科目</label>
							<br>
							<input id="kssj"  type="text" name="kmbh" types="NHL"  class="input_info  form-control kjkm1 " style="width:130px;" placeholder="科目编号" value="${kjkm1 }"  />
							<label>至</label>
							<input id="jssj"  type="text" name="kmbh"  types="NHH"  class="input_info  form-control kjkm2" placeholder="科目编号" style="width:130px;" value="${kjkm2 }"  />
						</div>
						<div class="form-group">
							<label>制单人</label><br>
							<input type="hidden" id="txt_zdr_guid" name="zdr" value="${zdr }" >
							<input type="text" id="txt_zdr" class="form-control input-radius "  placeholder="制单人" readonly value="${txt_zdr }">
							<button type="button" id="btn_zdr" class="btn btn-link btn-custom">选择</button>
						</div>
						<div class="form-group">
							<label>复核人</label><br>
							<input type="hidden" id="txt_fhr_guid" name="fhr" value="${fhr }" >
							<input type="text" id="txt_fhr" class="form-control input-radius"  placeholder="复核人" readonly value="${txt_fhr }">
							<button type="button" id="btn_fhr" class="btn btn-link btn-custom">选择</button>
						</div>
						<div class="form-group">
							<label>记账人</label><br>
							<input type="hidden" id="txt_jzr_guid" name="jzr" value="${jzr }" >
							<input type="text" id="txt_jzr" class="form-control input-radius"  placeholder="记账人" readonly value="${txt_jzr }">
							<button type="button" id="btn_jzr" class="btn btn-link btn-custom">选择</button>
						</div>
<!-- 						<div class="form-group"> -->
<!-- 							<label>辅助项目</label><br> -->
<!-- 							<input type="text" id="txt_fzxm" name="xmmc" class="form-control input-radius"   placeholder="辅助项目"   value=""> -->
<!-- 							<button type="button" id="btn_fzxm" class="btn btn-link btn-custom">选择</button> -->
<!-- 						</div> -->
<!-- 						<div class="form-group"> -->
<!-- 							<label>辅助部门</label><br> -->
<!-- 							<input type="text" id="txt_fzbm" name="bmmc" class="form-control input-radius"  placeholder="请选择辅助部门" value=""> -->
<!-- 							<button type="button" id="btn_fzbm" class="btn btn-link btn-custom">选择</button> -->
<!-- 						</div> -->
<!-- 						<div class="form-group"> -->
<!-- 							<label>辅助经济科目</label><br> -->
<!-- 							<input type="text" id="txt_fzjjkm" name="jjmc" class="form-control input-radius" readonly placeholder="辅助经济科目" value=""> -->
<!-- 							<button type="button" id="btn_fzjjkm" class="btn btn-link btn-custom">选择</button> -->
<!-- 						</div> -->
<!-- 						<div class="form-group"> -->
<!-- 							<label>金额</label><br> -->
<!-- 							<input type="text" id="txt_dfhjje1" style="width:125px !important" types="NL" name="jfjehj" class="form-control input-radius number"  placeholder="金额" value=""> -->
		
<!-- 						</div> -->
						<div class="form-group">
							<label>金额</label><br>
							<input type="text" id="txt_jfhjje1" style="width:125px !important" name="jfjehj" types="NL" class="form-control input-radius number"  placeholder="金额" value="${txt_jfhjje1 }">
							<span>至</span>
							<input type="text" id="txt_jfhjje2" style="width:125px !important" name="jfjehj" types="NH" class="form-control input-radius number"  placeholder="金额" value="${txt_jfhjje2 }">
						</div>
						<div class="form-group">
							<label>分录金额</label><br>
							<input type="text" id="txt_jfje1" style="width:125px !important" name="jfje1" types="NLS" class="form-control input-radius number"  placeholder="金额" value="${txt_jfhjje1 }">
							<span>至</span>
							<input type="text" id="txt_jfje2" style="width:125px !important" name="jfje2" types="NHS" class="form-control input-radius number"  placeholder="金额" value="${txt_jfhjje2 }">
						</div>
						<div class="search-more-bottom clearfix">
							<div class="pull-right">
								<button type="button" class="btn btn-primary" id="btn_search" name="btncx">
									<i class="fa icon-chaxun"></i>
									查 询
								</button>
								<button type="button" class="btn btn-default" id="btn_cancel">
									取 消
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
			<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th style="text-align:center;" >详情</th>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th style="text-align:center;" >序号</th>
				            <th style="text-align:center;" id="tr_pzrq">凭证状态</th>
				            <th style="text-align:center;" id="tr_pzlx">凭证类型</th>
				            <th style="text-align:center;" id="tr_kmbh">凭证号</th>
				            <th style="text-align:center;" id="tr_pzzh">凭证日期</th>
<!-- 				            <th style="text-align:center;" id="tr_zy">凭证类型</th> -->
                            <th style="text-align:center;" id="tr_zy">摘要</th>
				            <th style="text-align:center;" id="tr_jfje">制单人</th>
				            <th style="text-align:center;" id="tr_dfje">复核人</th>
				            <th style="text-align:center;" id="tr_dfje">记账人</th>
<!-- 				            <th style="text-align:center;" id="tr_wldw">贷方合计区(元)</th> -->
				            <th style="text-align:center;" id="tr_zdr">金额(元)</th>
				            <th style="text-align:center;">分录条数</th>
				            <th style="text-align:center;">附件张数</th>
				            <th style="text-align:center;">是否已打印</th>
<!-- 				            <th style="text-align:center;" id="tr_zdr">操作</th> -->
				        </tr>
					</thead>
					<tbody>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<input type="hidden" id="pzcxs" class="form-control input-radius window" name="pzcxs" value="">
<%@include file="/static/include/public-list-js.inc"%>
<script src="${pageContext.request.contextPath}/static/javascript/public/public-detailslist.js"></script>
<script>
// $("#txt_fzbm").bindChange("${cxt}/suggest/getXx","D");
$(function () {
	var target = "${ctx}/pzcxs";
	var pzzt = $("[name='pzzt']").val();
	var jfje1 = $("[name='jfje1']").val();
	var jfje2 = $("[name='jfje2']").val();
	var columns = [
		{"data": "GUID",orderable:false,"class":"details-control", "render": function (data, type, full, meta){
		 	return '<i class="fa" style="cursor: pointer;" class="cxxjcs" guid = "'+full.GUID+'" ></i>';
		},"width":10,'searchable': false},
         {"data": "KJQJ",orderable:false, 'render': function (data, type, full, meta){
           return '<input type="checkbox" name="guid" sfzt="'+full.SHZT+'"  pzbh="'+full.PZBH+'" sszt="'+full.SSZT+'" sfzt="'+full.SHZT+'" pzz="'+full.PZZ+'" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
         },"width":10,'searchable': false,"class":"text-center"},
         {"data": "_XH",defaultContent:"","class":"text-center","width":20},
         {"data": "PZZTMC",defaultContent:"凭证状态","class":"text-center","width":50},
         {"data": "ZBPZLXMC",defaultContent:"","width":50},
            {"data": "PZBH",defaultContent:"","class":"text-left","width":30,"id":"tr_pzzh","width":30,'render':function (data, type, full, meta){
  		   	return '<a href="javascript:void(0);" class="btn btn-link cxslook" path="${ctx}">'+ (full.PZBH==undefined?'':full.PZBH) +'</a>';
  		}},
         {"data": "PZRQY",defaultContent:"凭证时间","id":"tr_pzzh",'render':function(data, type, full, meta){
             return full.PZRQ;
         } ,"class":"text-center","width":50},
            {"data": "FIRZY",defaultContent:"无","class":"text-left","id":"tr_zy",'render':function(data, type, full, meta){
         	   var maxlength=20;
     			if(data==""||data==null||data=="undefined"){
     				return "";
     			}else{
     				if(data.length>=maxlength){
     					return '<div class="change"><span title="'+data+'">'+data.substr(0,maxlength-3)+'...</sapn></div>'
     				}else{
     					return '<div class="change"><span title="'+data+'">'+ data +'</span></div>'
     				}
     			}
            }},
         {"data": "ZDRMC",defaultContent:"无","id":"tr_bm","width":20},
         {"data": "FHRMC",defaultContent:"无","id":"tr_xm","width":20},
         {"data": "JZRMC",defaultContent:"无","id":"tr_zyy","width":20},
         {"data": "JFJEHJ",defaultContent:"0","id":"tr_cnr","class":"text-right","width":30},
         {"data": "FLTS",defaultContent:"0","id":"tr_cnr","class":"text-right","width":10},
         {"data": "FJZS",defaultContent:"0","id":"tr_cnr","class":"text-right","width":10},
         {"data": "SFDY",defaultContent:"","id":"tr_cnr"}
         
       ];
    table = getDataTableByListHj("mydatatables","${ctx}/pzcxs/getPageList?jfje1="+jfje1+"&jfje2="+jfje2+"&mkmc=pzcx&pzzt1="+pzzt+"&pzzt=${pzzt}&zbpzlxmc=${zbpzlxmc}&zy=${zy}&pzrqs=${pzrqs}&pzrqf=${pzrqf}&bhl=${bhl}&bhh=${bhh}&kjkm1=${kjkm1}&kjkm2=${kjkm2}&zdr=${zdr}&fhr=${fhr}&jzr=${jzr}&txt_jfhjje1=${txt_jfhjje1}&txt_jfhjje2=${txt_jfhjje2}",[1,'asc'],columns,0,1,setGroup);
    var pzzt = $("[name='pzzt']").val();
	 var zbpzlxmc = $("[name='zbpzlxmc']").val();
	 var zy = $("[name='zy']").val();
	 var pzrqs = $("[id='pzrqs']").val();
	 var pzrqf = $("[id='pzrqf']").val();
	 var bhl = $("[class='bhl']").val();
	 var bhh = $("[class='bhh']").val();
	 var kjkm1 = $("[id='kssj']").val();
	 var kjkm2 = $("[id='jssj']").val();
	 var zdr = $("[name='zdr']").val();
	 var fhr = $("[name='fhr']").val();
	 var jzr = $("[name='jzr']").val();
	 var txt_zdr = $("[id='txt_zdr']").val();
	 var txt_fhr = $("[id='txt_fhr']").val();
	 var txt_jzr = $("[id='txt_jzr']").val();
	 var txt_jfhjje1 = $("[id='txt_jfhjje1']").val();
	 var txt_jfhjje2 = $("[id='txt_jfhjje2']").val();
	 
	if(pzzt.length>0||zbpzlxmc.length>0||zy.length>0||pzrqs.length>0||zdr.length>0||fhr.length>0||jzr.length>0
			||txt_zdr.length>0||txt_fhr.length>0||txt_jzr.length>0||txt_jfhjje1.length>0||txt_jfhjje2.length>0
		||pzrqf.length>0||bhl.length>0||bhh.length>0||kjkm1.length>0||kjkm2.length>0){
		$("#btn_search").click();
	}
    
    
    
    $("#btn_selcolums").click(function(){
		 select_commonWin("${ctx}/pzjz/col?controlId=pzcxs","列信息","500","700");
	 });
	//制单人弹窗
	$("#btn_zdr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_zdr&ryid=txt_zdr_guid&mkbh=${param.mkbh}","制单人","920","650");
	});
	//复核人弹窗
	$("#btn_fhr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_fhr&ryid=txt_fhr_guid&mkbh=${param.mkbh}","复核人","920","650");
	});
	//记账人弹窗
	$("#btn_jzr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_jzr&ryid=txt_jzr_guid&mkbh=${param.mkbh}","记账人","920","650");
	});
	//辅助项目
	$("#btn_fzxm").click(function(){
		select_commonWin("${ctx}/ysgl/xmsz/xmxx/goXmxxPage?controlId=txt_fzxm&isWindow=1&","辅助项目","920","630");
	});
	
	//辅助部门
	$("#btn_fzbm").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_fzbm","辅助部门","920","630");
	});
	//经济科目信息
	$("#btn_fzjjkm").click(function(){
		 select_commonWin("${ctx}/window/mainJjkmWindowTree?controlId=txt_fzjjkm","经济科目信息","1000","630");	
	});
	//查询
	$("[id=btn_cxs]").click(function(){
	var pzzt = $("[name='pzzt']").val();
	var kssj = $("[name='kssj']").val();
	var jssj = $("[name='jssj']").val();
	
	var ksbh = $("[name='ksbh']").val();
	var jsbh = $("[name='jsbh']").val();
	var kskm = $("[name='kskm']").val();
	var jskm = $("[name='jskm']").val();
	var zdr = $("#txt_zdr").val();
	var fhr = $("#txt_fhr").val();
	var jzr = $("#txt_jzr").val();
	var fzxm = $("#txt_fzxm").val();
	var fzbm = $("#txt_fzbm").val();
	var fzjjkm = $("#txt_fzjjkm").val();
	var dfhjje1 = $("#txt_dfhjje1").val();
		doOperate("${ctx}/pzcxs/goPzcxs?pzzt="+pzzt+"&kssj="+kssj+"&jssj="+jssj+"&ksbh="+kssj+"&jsbh="+jsbh+"&kskm="+kskm+
    		   "&jskm="+jskm+"&zdr="+zdr+"&fhr="+fhr+"&jzr="+jzr+"&fzxm="+fzxm+"&fzbm="+fzbm+
    		   "&fzjjkm="+fzjjkm+"&dfhjje1="+dfhjje1,"");
	});

     $(document).on("click",".cxslook",function(){
    	 var guid = $(this).parents("tr").find("[name='guid']").attr("guid");
    	 var pzzt = $("[name='pzzt']").val();
    	 var zbpzlxmc = $("[name='zbpzlxmc']").val();
    	 var zy = $("[name='zy']").val();
    	 var pzrqs = $("[id='pzrqs']").val();
    	 var pzrqf = $("[id='pzrqf']").val();
    	 var bhl = $("[class='bhl']").val();
    	 var bhh = $("[class='bhh']").val();
    	 var kjkm1 = $("[id='kssj']").val();
    	 var kjkm2 = $("[id='jssj']").val();
    	 var zdr = $("[name='zdr']").val();
    	 var fhr = $("[name='fhr']").val();
    	 var jzr = $("[name='jzr']").val();
    	 var txt_zdr = $("[id='txt_zdr']").val();
    	 var txt_fhr = $("[id='txt_fhr']").val();
    	 var txt_jzr = $("[id='txt_jzr']").val();
    	 var txt_jfhjje1 = $("[id='txt_jfhjje1']").val();
    	 var txt_jfhjje2 = $("[id='txt_jfhjje2']").val();
    	 var ymbz = "pz";
    	 var gs = "pzcxs";
		 pageSkip("${ctx}/kjhs/pzxx/pzlr","pzlrck&guid="+guid+"&ymbz="+ymbz+"&gs="+gs+"&pzzt="+pzzt+"&zbpzlxmc="+zbpzlxmc+"&zy="+zy+"&pzrqs="+pzrqs+"&pzrqf="+pzrqf+"&bhl="+bhl+"&bhh="+bhh+"&kjkm1="+kjkm1+"&kjkm2="+kjkm2+"&zdr="+zdr+"&fhr="+fhr+"&jzr="+jzr+"&txt_zdr="+txt_zdr+"&txt_fhr="+txt_fhr+"&txt_jzr="+txt_jzr+"&txt_jfhjje1="+txt_jfhjje1+"&txt_jfhjje2="+txt_jfhjje2);
	 });
	$("[id=btn_cancel]").click(function(){
		window.location.href = "${ctx}/pzcxs/goPzcxs?mkbh=040901";
		$("[name='pzzt']").val("");
   	 	$("[name='zbpzlxmc']").val("");
   		$("[name='zy']").val("");
   	 	$("[id='pzrqs']").val('');
   	 	$("[id='pzrqf']").val('');
   	 	$("[class='bhl']").val('');
   	  	$("[class='bhh']").val('');
   	 	$("[id='kssj']").val('');
   		$("[id='jssj']").val('');
   	  	$("[name='zdr']").val('');
   	  	$("[name='fhr']").val('');
   	 	$("[name='jzr']").val('');
   	 	$("[id='txt_zdr']").val('');
   	  	$("[id='txt_fhr']").val('');
   	  	$("[id='txt_jzr']").val('');
   	  	$("[id='txt_jfhjje1']").val('');
   	 	$("[id='txt_jfhjje2']").val(''); 
	});
	$("#btn_view").click(function(){
		 var checkbox = $("#mydatatables").find("tbody").find(":checked").filter(":checked");
		   if(checkbox.length==0){
			   alert("请选择一条信息！");
			   return;
		   }
		   select_commonWin("${ctx}/pzjz/view","查看信息","1000","700");
	});
	$("#btn_cancel1").click(function(){
		$("[name='ksbh']").val("");
		$("[name='jsbh']").val("");
		$("[name='kskm']").val("");
		$("[name='jskm']").val("");
		$("#txt_zdr").val("");
		$("#txt_fhr").val("");
		$("#txt_jzr").val("");
		$("#txt_fzxm").val("");
		$("#txt_fzbm").val("");
		$("#txt_fzjjkm").val("");
		$("#txt_dfhjje1").val("");
	});
});
$(function() {	
	//列表右侧悬浮按钮
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
});
function selectCol(){
	var cols = $("#pzcxs").val();
	var trs = $("[id^=tr_]");
	if(cols==""){
		$("[id^=tr_]").removeClass("yc");
		return;
	}
	$.each(trs,function(i,v){
		var cs = $(this).attr("id");
		if(cols.indexOf(cs)<0){
			$(this).addClass("yc");
		}else{
			$(this).removeClass("yc");
		}
	});
}
function test(){
	$.each($("[role='row']"),function(){
		$(this).find("td").eq(1).attr("id","tr_pzrq");
	 	$(this).find("td").eq(2).attr("id","tr_pzzh");
	 	$(this).find("td").eq(3).attr("id","tr_zy");
		$(this).find("td").eq(4).attr("id","tr_kmbh");
		$(this).find("td").eq(5).attr("id","tr_kmmc");
		$(this).find("td").eq(6).attr("id","tr_jfje");
		$(this).find("td").eq(7).attr("id","tr_dfje");
		$(this).find("td").eq(8).attr("id","tr_bm");
		$(this).find("td").eq(9).attr("id","tr_xm");
		$(this).find("td").eq(10).attr("id","tr_zyy");
		$(this).find("td").eq(11).attr("id","tr_wldw");
		$(this).find("td").eq(12).attr("id","tr_zdr");
		$(this).find("td").eq(13).attr("id","tr_fhr");
		$(this).find("td").eq(14).attr("id","tr_cnr");
		$(this).find("td").eq(15).attr("id","tr_jzr");
		$(this).find("td").eq(16).attr("id","tr_pzzt");
		$(this).find("td").eq(17).attr("id","tr_sfdy");
	});
	
}

//下面这个方法是原来的，在下拉框中选择后，刷新页面的方式来显示新数据的
// $("#txt_pzzt").change(function(){
// 	var pzzt = $(this).val();
// 	location.href="${ctx}/pzcxs/goPzcxs?pzzt="+pzzt;
// });

//凭证类型改变
//ajax刷新，仿财务一体化/网上报销/报销业务查询
//根据列表table的显示的代码改的，只写其中改变了的下拉列表的这个参数
/* "${ctx}/pzcxs/getPageList?pzzt1=${pzzt}&kssj=${kssj}&jssj=${jssj}&ksbh=${ksbh}&jsbh=${jsbh}&kskm=${kskm}"+
	        		   "&jskm=${jskm}&zdr=${zdr}&fhr=${fhr}&jzr=${jzr}&fzxm=${fzxm}&fzbm=${fzbm}"+
		    		   "&fzjjkm=${fzjjkm}&dfhjje1=${dfhjje1}&dfhjje2=${dfhjje2}&jfhjje1=${jfhjje1}&jfhjje2=${jfhjje2}"
*/
$("#txt_pzzt").change(function(){
 	tableRelod(table);
});
  	
var tableRelod = function(table) {
 	var pzzt = $("[id='txt_pzzt']").val();
    table.ajax.url("${ctx}/pzcxs/getPageList?pzzt1="+pzzt);
    table.ajax.reload();
};

//下拉：列表项数据
$('#mydatatables tbody').on('click', 'td.details-control', function (e) {
	var pzbh = $(this).find(".fa").attr("guid");
    detailslist($(this),"zt","${ctx}/pzcxs/getpzmxlist","pzbh="+pzbh,format);
    return false;
});
function format (val,zt) {
	console.log("111111");
	var str = '<table id="mydatatables" class="hqys table table-striped table-bordered dataTable no-footer" >';
	str+='<thead><tr>';
	str+='<th>序号</th>';
	str+='<th>科目编号</th>';
	str+='<th>经济分类</th>';
	str+='<th>部门编号</th>';
	str+='<th>项目编号</th>';
	str+='<th>借方金额（/元）</th>';
	str+='<th>贷方金额（/元）</th>';
	str+='<th>摘要</th>';
	str+='</tr></thead>';
	str+='<tbody>';
	for(var i=0;i<val.length;i++){
		var kmbh="("+val[i].KMBH+")"+val[i].KMMC+"";
		var bmbh="("+val[i].BMBH+")"+val[i].BMMC+"";
		var xmbh="("+val[i].XMBH+")"+val[i].XMMC+"";
		if(val[i].BMBH==null){
			bmbh="";
		}
		if(val[i].KMBH==null){
			kmbh="";
		}
		if(val[i].XMBH==null){
			xmbh="";
		}
		str+='<tr class="detailTr" id="ddel_'+i+'">';
		str+='<td style="width: 2.3rem;;text-align:center;">'+ (i+1) +'</td>';
		str+='<td style="width: 14rem;">'+ isNullToDefaultString(kmbh,'') +'</td>';
		str+='<td style="width: 17rem;">'+ isNullToDefaultString(val[i].JJMC,'') +'</td>';
		str+='<td style="width: 16rem;">'+ isNullToDefaultString(bmbh,'') +'</td>';
		str+='<td style="width: 14rem;">'+ isNullToDefaultString(xmbh,'') +'</td>';
		str+='<td style="width:7rem;text-align: right;">'+ isNullToDefaultString(val[i].JFJE,'') +'</td>';
		str+='<td style="width:8rem;text-align: right;">'+ isNullToDefaultString(val[i].DFJE,'') +'</td>';
		str+='<td style="width:47rem;">'+ isNullToDefaultString(val[i].ZY,'') +'</td>';
	}
	if(val.length==0){
		str+='<tr class="odd">';
		str+='<td  colspan="7">暂无数据</td>';
		str+='</tr>';
	}
	str+='</tbody>';
	str+='</table>';
	return str;
}
function fz(){
	var fzxm = $("#txt_fzxm").val()
	var bh = fzxm.substr(fzxm.indexOf("("),fzxm.indexOf(")"))
	alert(bh)
}
</script>
</body>
</html>

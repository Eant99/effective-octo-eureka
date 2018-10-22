<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>查看出差业务申请信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style>
	.btn-del{
		text-align:center;
	    width: 26px;
	    height: 26px;
	    background-color: #F3F9FF;
	    border-radius: 4px;
	    border: 1px solid #cccccb;
	    display: inline-block;
	    position: relative;
	}
	.btn-del:after{
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 1em;
	    position: relative;
	    cursor: pointer;
	    top:3px;
	}
	tbody > tr > td{
		padding: 4px!important;
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
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
	    font-size: 17px;
	    position: relative;
	    cursor: pointer;
	}
	.btn_td{
		width:14mm!important;
		height:6mm!important;
		text-align: center;
	}
	.radio-group{
		height: 25px!important;
		line-height: 25px;
		vertical-align: middle;
		display: inline-block;
	}
	.radio-group > .rdo{
		margin: 0px 0px 0px 10px;
		height: 25px;
	}
	.input-radius2{
		border-bottom-right-radius: 4px!important;
		border-top-right-radius: 4px!important;
	}
	th,td{
		text-align: center;
	}
		.radiodiv{
    border: 1px solid #ccc;
    border-top-right-radius:4px;
    border-bottom-right-radius:4px;
    height: 25px;
    padding-left: 6px;
}
tr{
background-color: white !important;
}
.bk{
		border:none;
		width:100%;
		padding:4px !important;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" id="txt_czr" name="czr"  value="${loginId}">
	<input type="hidden" id="txt_guid" name="guid"  value="${param.guid}">
	<input type="hidden" id="txt_today" name="today" value="${today }"	/>
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>出差业务信息</span>
		</div>
        <div class="pull-right">
			<button type="button" class="btn btn-default plugin" id="btn_pass" style="display: none;">通过</button>
			<button type="button" class="btn btn-default plugin" id="btn_refuse"  style="display: none;">退回</button>
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>出差业务详细信息</div>
            		<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>单据编号</span>
							<input type="text" id="txt_djbh" name="djbh" class="form-control input-radius2" value="${ccywsq.djbh }" readonly/>
						</div>
                    </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">申请人</span>
							<input type="text" id="txt_sqr" name="sqr" class="form-control input-radius2" value="${ccywsq.sqrxm }" readonly/>
						</div>
                    </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">所在部门</span>
							<input type="text" id="txt_szbm" name="szbm" class="form-control input-radius2" value="${ccywsq.SZBM}" readonly/>
						</div>
                    </div>
				</div>
				<div class="row">
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon">出差天数（天）</span>
                            <input type="text" id="txt_ccts" name="ccts" class="form-control input-radius2 text-right" value="${ccywsq.ccts }" readonly/>
                        </div>
					</div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>出差类型</span>
                            <select id="txt_cclx" class="form-control input-radius2" name="cclx">
                            <option value="">未选择</option>
                               <c:forEach var="item" items="${cclxList}">
                           			<option value="${item.DM}" <c:if test="${item.DM eq  ccywsq.cclx}">selected</c:if>>${item.MC}</option>
                            	</c:forEach>
                            </select>
                        </div>
					</div>
<!-- 					<div class="col-md-4" id="content_sxfy"> -->
<!--                     	<div class="input-group"> -->
<!-- 							<span class="input-group-addon"><span class="required">*</span><span id="word_sxfy"> -->
<%-- 								<c:if test="${ccywsq.cclx eq 01 }">会议费用（元）</c:if> --%>
<%-- 								<c:if test="${ccywsq.cclx eq 02 }">培训费用（元）</c:if> --%>
<%-- 								<c:if test="${ccywsq.cclx eq 03 }">公务费用（元）</c:if> --%>
<!-- 							</span></span> -->
<%--                             <input type="text" id="txt_sxfy" name="sxfy" class="form-control input-radius2 number text-right" value="${ccywsq.sxfy}" /> --%>
<!--                         </div> -->
<!-- 					</div>	 -->
<!-- 				</div> -->
<!-- 				<div class="row"> -->
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon">出差人数（人）</span>
                            <input type="text" id="txt_ccrs" name="ccrs" class="form-control input-radius2 text-right" value="${ccywsq.ccrs }" readonly/>
                        </div>
					</div>
					<div class="col-md-4">
<!--                     	<div class="input-group"> -->
<!-- 							<span class="input-group-addon"><span class="required">*</span>是否预支付</span> -->
<!--                             <div class="radiodiv">&nbsp;&nbsp;&nbsp; -->
<%-- 								<input type="radio" name="sfyzf" class="rdo" value="1" <c:if test="${ccywsq.sfyzf eq 1 }">checked</c:if> /> --%>
<!-- 								<label>是</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
							
						
<%-- 								<input type="radio" name="sfyzf" class="rdo" value="0" <c:if test="${ccywsq.sfyzf eq 0 }">checked</c:if> /> --%>
<!-- 								<lable>否</lable> -->
<!-- 							</div> -->
<!--                         </div> -->
					</div>
					<div class="col-md-4" <c:if test="${ccywsq.sfyzf eq 0 }">style="display: none;"</c:if> id="content_yzfje">
<!--                     	<div class="input-group"> -->
<!-- 							<span class="input-group-addon"><span class="required">*</span>预支付金额（元）</span> -->
<%--                             <input type="text" id="txt_yzfje" name="yzfje" class="form-control input-radius2 number text-right" value="${ccywsq.yzfje }"/> --%>
<!--                         </div> -->
					</div>					
				</div>
				<div class="row">
				<div class="col-md-12">
					<div class="input-group">
						<span class="input-group-addon"><span class="required">*</span>出差内容</span>
                       	<textarea id="txt_ccnr"  class="form-control" name="ccnr" style="height: 60px;">${ccywsq.ccnr }</textarea>
					</div>
				</div>
				</div>
			</div>
         </div>
         
         <div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>项目经费支出信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered" style="border-collapse: collapse;width: 50%;">
		        	<thead id="thead">
				        <tr>
<!-- 				        <th style="width: 5%;">操作</th> -->
						<th style="width: 50%; border-bottom-width: 0px;">项目名称</th>
						<th style="width: 15%; border-bottom-width: 0px;" >经费类型</th>
						<th style="width: 25%; border-bottom-width: 0px;">剩余金额（元）</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    
				    	  <c:choose>
                             <c:when test="${not empty xmxxlist}">
  								<c:forEach var="xmxx" items="${xmxxlist}"> 
				    	         <tr id="tr_1">
				    	         		<input type="hidden"  name="start"  value="start">
<!-- 				    		         <td class="btn_td"> <div class="addBtn2 add" ></div> </td>				    		 -->
				    		<td>
<!-- 				    		 <div class="input-group">  -->
				    		<input type="text" id="txt_ktmc" name="ktmc" style="background-color: white !important;"  class="bk" value="${xmxx.mc}" readonly="readonly" />
                            <input type="hidden" id="txt_jfbh" name="jfbh" value="${xmxx.guid}"/>
<!--                             <span class="input-group-btn"> <button type="button" id="btn-ktmc"  class="btn btn-link btn-ktmc">选择</button></span> -->
<!-- 				    		</div> -->
				    		</td>
				    		<td >
				    		<input type="text" id="txt_jflx" name="jflx" class="bk" style="background-color: white !important;" value="${xmxx.jflxmc}" readonly/>
				    		</td>
				    		<td >
				    		 <input type="text" id="txt_syje" name="syje" class="bk text-right number" style="background-color: white !important;" placeholder="0.00" value="${xmxx.ye}" readonly/>
				    		</td>
				    		<input type="hidden"  name="end"  value="start">
				    	</tr>
				    	</c:forEach>
                          </c:when>
        
                          <c:otherwise>
							<%-- <tr id="tr_1">
							<input type="hidden"  name="start"  value="start">
				    		<!-- <td class="btn_td"> -->
				    		<!-- 	<div class="addBtn2 add" ></div> -->
				    		<!-- </td>	 -->			    		
				    		 <td>
				    			<input type="text"  class="form-control input-radius bk"  name="ktmc" style="background-color: white !important;" value="">
				    		</td>
				    		<td >
				    			<input type="text"  class="form-control input-radius  text-right number bk" style="background-color: white !important;" name="jflx" value="">
				    		</td>
				    		<td >
				    			<input type="text"  class="form-control input-radius  text-right number bk" style="background-color: white !important;" name="syje" value="<fmt:formatNumber value='' pattern='0.00'/>">
				    		</td>
				    		<input type="hidden"  name="end"  value="start">
				    	</tr> --%>
                          </c:otherwise>
                    </c:choose>
				    </tbody>
				</table>
			</div>
		</div>
         
         
         
         <div class="box-panel">
			<div class="box-header clearfix">
				<div class="sub-title pull-left text-primary">
					<i class="fa icon-xiangxi"></i>上传来函
				</div>
			</div>
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-8">
						<input id="imageFile" name="path" type="file" multiple class="file-loading"/>
						<div id="errorBlock" class="help-block"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>行程信息</div>
            		<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>开始时间</span>
							<input type="text" id="txt_kssj" name="kssj" class="form-control input-radius2 date" value="${ccywsq.kssjmc }" />
						</div>
                    </div>
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>结束时间</span>
							<input type="text" id="txt_jssj" name="jssj" class="form-control input-radius2 date" value="${ccywsq.jssjmc }"/>
						</div>
                    </div>
					<div class="col-md-6">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>交通工具</span>
							<c:forEach var="item" items="${jtgjList}" >
								<c:if test="${item.MS == '1'}">
                           			<input type="checkbox" name="jtgj" id="txt_jtgj_${item.DM}" value="${item.DM}"/>${item.MC}
                           		</c:if>
                           	</c:forEach>
<!--                             <select id="txt_jtgj" class="form-control input-radius2" name="jtgj"> -->
<!--                             	<option value="">未选择</option> -->
<%--                                <c:forEach var="item" items="${jtgjList}"> --%>
<%--                            			<option value="${item.DM}" <c:if test="${item.DM == ccywsq.jtgj }">selected</c:if>>${item.MC}</option> --%>
<%--                             	</c:forEach> --%>
<!--                             </select> -->
                        </div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>目的地省份</span>
                            <input type="text" id="sel_provinceid" name="provinceid" class="form-control input-radius2"  value="${ccywsq.province }"/>
                        </div>
					</div>
					<div class="col-md-3">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>目的地市区</span>
                            <input type="text" id="sel_cityid" name="cityid" class="form-control input-radius2"  value="${ccywsq.city }"/>
                        </div>
					</div>
					<div class="col-md-6">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>目的地点</span>
                            <input type="text" id="txt_mddd" name="mddd" class="form-control input-radius2" value="${ccywsq.mddd }"/>
                        </div>
					</div>
				</div>
			</div>
         </div>
		<%-- <div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>同行人员</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<table id="mytable" class="table table-striped table-bordered table-alterable"
					style="border-collapse: collapse; width: 50%;">
					<tr>
						<th>序号</th>
						<th><span style="padding: 4px;color: red;font-size: 13px;">*</span>姓名</th>
						<th>所在部门</th>
					</tr>
					<c:forEach items="${txryList}" var="item" varStatus="status" >
						<tr>
							<td class="xh" style="vertical-align: middle;">${status.index + 1}</td>
							<td>
								<input type="text" class="bk" name="xm" style="background-color: white !important;" readonly value="${item.XM }"/>
								<input type="hidden" class="form-control" name="rybh" value="${item.RYBH }" <c:if test="${item.RYBH == loginId }">data-luser</c:if>/>
							</td>
							<td>
								<input type="text" class="bk" name="szbm" style="background-color: white !important;" value="${item.szbm }" readonly/>
							</td>
							
						</tr>
					</c:forEach>
				</table>
			</div>
         </div> --%>
         <c:if test="${ccywsq.shyj != null && ccywsq.shyj != ''}">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>审核意见</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				
				<div class="row">
					<div class="col-md-12">
					<div class="input-group">
						<span class="input-group-addon"><span class="required">*</span>审核意见</span>
                       	<textarea id="txt_ccnr"  class="form-control" name="ccnr" style="height: 60px;">${ccywsq.shyj }</textarea>
					</div>
				</div>
				</div>
				
			</div>
         </div>
         </c:if>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
var target = "${ctx}/wsbx/ccyw/";
var suffix = "&mkbh=${param.mkbh}";

var guid = "${param.guid}";
var procinstid = "${param.procinstid}";
$(document).ready(function(){
	$(".number,.number1").blur();
	$("input,select,textarea").attr("disabled","disabled");
	var action = "${param.action}";
	if(action == "sh"){
		$(".plugin").show();
	}
});
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
    initialPreviewShowDelete:false,
    showBrowse:false,
    showCaption:true,
    showClose:false,
    uploadExtraData:function(id,index){
    	return {"fold":"zcxx","id":"${param.guid}","djlx":"000000"};
    },//区分不同的模块：fold：文件夹，id:生成文件的id(主键)，djlx:单据类型。
    //uploasExtraData:是把页面你想要往后退传的东西放（return）     到域里面然后去后台去取
    overwriteInitial: false,  
    deleteUrl: "${ctx}/file/fileDelete"//删除文件的路径
});
//审核
$(function(){
	//通过
	$(document).on("click","#btn_pass",function(){
   		select_commonWin(target+"/ccywsh/goVerifyPage?guid="+guid+"&procinstid="+procinstid+"&status=pass","审核通过","500","250");
   	});
	//退回
	$(document).on("click","#btn_refuse",function(){
		select_commonWin(target+"/ccywsh/goVerifyPage?guid="+guid+"&procinstid="+procinstid+"&status=false","审核退回","500","250");
		/*  var alert = "您确定要回退到申请人吗？";
		 var apply="apply";
		confirm(alert, {title:"提示"}, function(){
			if(true){
				select_commonWin(target+"/ccywsh/goVerifyPage?guid="+guid+"&procinstid="+procinstid+"&apply="+apply+"&status=false","审核退回1","500","250");
			}else{
				select_commonWin(target+"/ccywsh/goVerifyPage?guid="+guid+"&procinstid="+procinstid+"&status=false","审核退回2","500","250");
			}
		}); */
   	});
//返回
$("#btn_back").click(function(){
	var link = "${param.link}";
	var before = document.referrer;
	if(before.indexOf("login_toIndex")>0){
		location.href="${ctx}/wsbx/ccyw/ccywsh/goCcywshPage";
	}else{
		window.history.back(-1);
	}
});
//刷新按钮
$(".reload").click(function(){
	 window.location.reload();
});
//给复选框赋值
var a="${jtgjcl}";
var checkArray = a.split(",");//劈开存入的单个字段
var checkBoxAll = $("input[name='jtgj']");
for(var i=0;i<"${length}";i++){
 	 $.each(checkBoxAll,function(j,checkbox){
     	 var checkValue=$(checkbox).val();
     	 if(checkArray[i]==checkValue){
     		 $(checkbox).attr("checked",true);
     	 }
     	$(checkbox).attr("disabled",true);
 	 })
	 }
});
</script>
</body>
</html>
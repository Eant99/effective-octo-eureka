<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>添加出差业务申请信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style>
	.btn-del{
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
	.btn-del:after{
	    content: "\2014";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
/* 	    font-size: 1em; */
	    position: relative;
	    cursor: pointer;
/* 	    top:3px; */
	}
	.addBtn{
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
	.addBtn:after{
	    content: "+";
	    color:#9c9c9c!important;
	    color:#337ab7!important;
/* 	    font-size: 17px; */
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
	tbody > tr > td{
		padding: 0px!important;
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

.addBtn2 {
	text-align: center;
	width: 20px;
	height: 20px;
	background-color: #F3F9FF;
	border-radius: 4px;
	border: 1px solid #cccccb;
	display: inline-block;
	position: relative;
	margin-left:5px;
	margin-top:2px;
}

.addBtn2:after {
	/* 		2722 */
	content: "+";
	color: #9c9c9c !important;
	color: #337ab7 !important;
/* 	font-size: 17px; */
	position: relative;
	cursor: pointer;

}

.deleteBtn {
	text-align: center;
	width: 20px;
	height: 20px;
	background-color: #F3F9FF;
	border-radius: 4px;
	border: 1px solid #cccccb;
	display: inline-block;
	position: relative;
	margin-left:5px;
	margin-top:2px;
}

.deleteBtn:after {
	/* 		2014 */
	content: "\2014";
	color: #9c9c9c !important;
	color: #337ab7 !important;
	font-size: 1em;
	position: relative;
	cursor: pointer;
/* 	top: 3px; */
}
 .bk{
		border:none;
		width:100%;
		padding:4px !important;
	}
	.border{
		border:1px solid #a94442;
		background:url("${ctx}/webView/wsbx/rcbx/no_null.bmp") center right no-repeat;;
	}
	
</style>
</head>
<body>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println(path+"@"+basePath);
%>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" id="txt_czr" name="czr"  value="${loginId}">
	<input type="hidden" id="txt_guid" name="guid" value="${guid}"/>
	<input type="hidden" id="txt_today" name="today" value="${today }"	/>
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>添加出差业务申请信息</span>
		</div>
        <div class="pull-right">
            <button type="button" class="btn btn-default" id="btn_save" >保存</button>
			<button type="button" class="btn btn-default" id="btn_back" >返回</button>
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
							<input type="text" id="txt_djbh" name="djbh" class="form-control input-radius2" value="系统自动生成" readonly/>
						</div>
                    </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>申请人</span>
							<input type="text" id="txt_sqrxm" name="sqrxm" class="form-control input-radius2" value="${ryxm }" readonly />
							<input type="hidden" id="txt_sqrid" name="sqrid" value="${loginId }" />
							 <span class="input-group-btn"><button type="button" id="btn_sqr" class="btn btn-link btn-custom">选择</button></span>
						</div>
                    </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">所在部门</span>
							<input type="text" id="txt_szbm" name="szbm" class="form-control input-radius2" value="${szbm }" readonly/>
						</div>
                    </div>
				</div>
<!-- 				<div class="row"> -->
<!-- 					<div class="col-md-4"> -->
<!--                     	<div class="input-group"> -->
<!-- 							<span class="input-group-addon" ><span class="required ktmc-plugin">*</span>项目名称</span> -->
<!--                             <input type="text" id="txt_ktmc" name="ktmc" placeholder="请选择项目名称" class="form-control input-radius2" style="background-color: white;" /> -->
<!--                             <input type="hidden" id="txt_jfbh" name="jfbh"/> -->
<!--                             <span class="input-group-btn"><button type="button" id="btn-ktmc" class="btn btn-link">选择</button></span> -->
<!--                         </div> -->
<!-- 					</div> -->
<!-- 					<div class="col-md-4"> -->
<!--                     	<div class="input-group"> -->
<!-- 							<span class="input-group-addon">经费类型</span> -->
<!-- 							<input type="text" id="txt_jflx" name="jflx" class="form-control input-radius2 text-right" value="" readonly/> -->
<!--                         </div> -->
<!-- 					</div> -->
<!-- 					<div class="col-md-4"> -->
<!--                     	<div class="input-group"> -->
<!-- 							<span class="input-group-addon">剩余金额（元）</span> -->
<!--                             <input type="text" id="txt_syje" name="syje" class="form-control input-radius2 text-right number" placeholder="0.00" readonly/> -->
<!--                         </div> -->
<!-- 					</div> -->
<!-- 				</div> -->
				<div class="row">
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>出差天数（天）</span>
                            <input type="text" id="txt_ccts" name="ccts" class="form-control input-radius2 text-right int" value=""/>
                        </div>
					</div>
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>出差类型</span>
                            <select id="txt_cclx" class="form-control input-radius2" name="cclx">
                            <option value="">未选择</option>
                               <c:forEach var="item" items="${cclxList}">
                           			<option value="${item.DM}">${item.MC}</option>
                            	</c:forEach>
                            </select>
                        </div>
					</div>
					<div class="col-md-4" style="display: none;" id="content_sxfy">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span><span id="word_sxfy"></span></span>
                            <input type="text" id="txt_sxfy" name="sxfy" class="form-control input-radius2 number text-right" />
                        </div>
					</div>	
<!-- 				</div> -->
<!-- 				<div class="row"> -->
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>出差人数（人）</span>
                            <input type="text" id="txt_ccrs" name="ccrs" class="form-control input-radius2 text-right" value=""/>
                        </div>
					</div>
<!-- 					<div class="col-md-4"> -->
<!--                     	<div class="input-group"> -->
                    	 
<!-- 							<span class="input-group-addon"><span class="required">*</span>是否预支付</span> -->
<!-- 							<div class="radiodiv"> -->
<!--                            &nbsp;&nbsp;&nbsp; -->
<!-- 								<input type="radio" name="sfyzf" class="rdo" value="1" /> -->
								
<!-- 							<label>是</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
							
<!-- 								<input type="radio" name="sfyzf" class="rdo" value="0" checked /> -->
								
<!-- 						<label>否</label> -->
<!-- 							</div> -->
							
<!--                         </div> -->
<!-- 					</div> -->
					<div class="col-md-4" style="display: none" id="content_yzfje">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>预支付金额（元）</span>
                            <input type="text" id="txt_yzfje" name="yzfje" class="form-control input-radius2 number text-right" />
                        </div>
					</div>					
				</div>
				
				<div class="row">
				<div class="col-md-12">
					<div class="input-group">
						<span class="input-group-addon"><span class="required">*</span>出差内容</span>
                       	<textarea id="txt_ccnr"  class="form-control" name="ccnr" style="height: 60px;"></textarea>
					</div>
				</div>
				</div>
				
				<div class="box-panel" style="margin-left:-12px;">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>项目信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content" style="width:50%;float:left;">
				<table id="mydatatables" class="table table-striped table-bordered xmxx" style="border-collapse: collapse;">
		        	<thead id="thead">
				        <tr>
<!-- 				        <th style="width: 52px; border-bottom-width: 0px;">操作</th> -->
<!-- 						<th style="width: 5%;">序号</th> -->
						<th style="width: 50%; border-bottom-width: 0px;"><span class="required" style="color: red;">*</span>项目名称</th>
						<th style="width: 15%; border-bottom-width: 0px;" >经费类型</th>
						<th style="width: 25%; border-bottom-width: 0px;">剩余金额（元）</th>
				        </tr>
					</thead>
				    <tbody id="tbody">
				    	<tr>
<!-- 						<td class="btn_td" style=" vertical-align: middle;"> -->
<!-- 							<div class="addBtn2" ></div> -->
<!-- 						</td> -->
<!-- 						<td class="xh" style="vertical-align: middle;">1</td> -->
						<td style="padding: 4px !important" >
						  <div class="input-group"> 
						<input type="text" id="txt_ktmc" name="ktmc" placeholder="请选择项目名称" class="bk null" style="background-color: white !important ;width=300px" />
                            <input type="hidden" id="txt_jfbh" name="jfbh"/>
                            <span class="input-group-btn">
                            <button type="button" id="btn-ktmc"  class="btn btn-link btn-ktmc">选择</button>
                            </span>
						</div>
						</td>
						<td style="padding: 4px !important">
						<input type="text" id="txt_jflx" name="jflx"  class=" bk" value="" readonly/>
						</td>
						<td style="padding: 4px !important">
						 <input type="text" id="txt_syje" name="syje" class="bk text-right number" placeholder="0.00" readonly/>
						</td>
					</tr>
				    </tbody>
				</table>
			</div>
		</div>
				
				
			</div>
         </div>
         <div class="box-panel">
			<div class="box-header clearfix">
				<div class="sub-title pull-left text-primary">
					<i class="fa icon-xiangxi"></i>上传来函
				</div>
				<button type="button" class="btn btn-default" style="margin-left:10px;" id="scanToServer">扫描上传</button>
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
			<div class="container-fluid box-content" id="mydatatablesm">
				<div class="row">
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>开始时间</span>
							<input type="text" id="txt_kssj" name="kssj"  onchange="ckts();"  class="form-control input-radius2 datetime" />
						</div>
                    </div>
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>结束时间</span>
							<input type="text" id="txt_jssj" name="jssj"  onchange="ckts();"  class="form-control input-radius2 datetime" />
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
                        </div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>目的地省份</span>
							<select class="form-control input-radius2" name="provinceid" id="sel_provinceid">
                            	<option value="">未选择</option>
                               <c:forEach var="item" items="${ProvicesList}">
                           			<option value="${item.provinceid}">${item.province}</option>
                            	</c:forEach>
                            </select>
<!--                             <input type="text" id="txt_mddd" name="provinceid" class="form-control input-radius2" /> -->
                        </div>
					</div>
					<div class="col-md-3">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>目的地市区</span>
							<select class="form-control input-radius2" name="cityid" id="sel_cityid">
                            	<option value="">未选择</option>
                            </select>
<!--                             <input type="text" id="txt_mddd" name="cityid" class="form-control input-radius2" /> -->
                        </div>
					</div>
					<div class="col-md-6">
                    	<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>目的地详细地址</span>
                            <input type="text" id="txt_mddd" name="mddd" class="form-control input-radius2" />
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
				<table id="mytable" class="table table-striped table-bordered table-alterable txry"
					style="border-collapse: collapse; width: 50%;">
					<tr>
						<th>操作</th>
						<th>序号</th>
						<th>姓名</th>
						<th>所在部门</th>
					</tr>
					<tr>
						<td class="btn_td" style=" vertical-align: middle;">
							<div class="addBtn"></div>
						</td>
						<td class="xh" style="vertical-align: middle;">1</td>
						<td style="padding: 4px !important">
							<div class="input-group"> 
								<input type="text" class="bk" name="xm" id="xm" readonly value="${ryxm }"style="background-color: white !important ;width=300px"/>
								<input type="hidden" class="form-control" name="rybh" id="rybh" value="${loginId }" />
								<span class="input-group-btn">
	                            	<button type="button" id = "btn_txry" class="btn btn-link btn-txry hide">选择</button>
	                            </span>
                            </div>
						</td>
						<td style="padding: 4px !important">
							<input type="text" style="background-color: white !important;" class="bk" name="szbm" id="szbmm" value="${szbm}" readonly/>
							<input type="hidden" name="end"/>
						</td>
					</tr>
				</table>
			</div>
         </div> --%>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
	var basePath = "${ctx}/wsbx/ccyw/ccywsq";
	var suffix = "&mkbh=${param.mkbh}";

	var today = $("#txt_today").val();
	
// 	addbutton();
// 	function addbutton(){
// 		var a = $(".tbody").find("tr").length;
// 		var aaa="${hy}";
// 		if(aaa.length>0){
// 			$(".add").removeClass("addBtn2");			
// 			$(".add").addClass("deleteBtn");
// 			$(".tbody tr:last").find(".deleteBtn").addClass("addBtn2");
// 			$(".tbody tr:last").find(".deleteBtn").removeClass("deleteBtn");
// 		}else{
			
// 		}
// 	}
	var num = 2;
	 $(document).on("click","[class*=addBtn2]",function(){
			var $parentTr = $(this).parents("tr").clone();
			$parentTr.find(":input").val("");
			$(this).removeClass("addBtn2");
			$(this).addClass("deleteBtn");
			
			$parentTr.find("[id^=txt_ktmc]").attr({"id":"txt_ktmc"+num});
			$parentTr.find("[id^=txt_jflx]").attr({"id":"txt_jflx"+num});
			$parentTr.find("[id^=txt_syje]").attr({"id":"txt_syje"+num});
			$parentTr.find("[id^=txt_jfbh]").attr({"id":"txt_jfbh"+num});
			num++;
			
			$(this).parents("tr").after($parentTr);
		});
	//删除按钮
		$(document).on("click","[class*=deleteBtn]",function(){
			$(this).parents("tr").remove();
		});
		//选择 项目名称
		 $(document).on("click",".btn-ktmc",function(){
			var $parentTr = $(this).parents("tr").clone();
	 		var txt_ktmc = $parentTr.find("[id^=txt_ktmc]").attr("id");
	 		var txt_jflx = $parentTr.find("[id^=txt_jflx]").attr("id");
	 		var txt_syje = $parentTr.find("[id^=txt_syje]").attr("id");
	 		var txt_jfbh = $parentTr.find("[id^=txt_jfbh]").attr("id");
	 		var checkbox =$("[name='jfbh']");
	   		if(checkbox.length>0){
	   			var guid = [];
		   	   	checkbox.each(function(){
		   	   		if($(this).val() !=''){
		   	   		guid.push($(this).val());
		   	   		}
		   	   	});
		   	   	}
			select_commonWin(basePath+"/jfszList?cId_1="+txt_ktmc+"&cId_2="+txt_jflx+"&cId_3="+txt_syje+"&cId_4="+txt_jfbh+"&guid="+guid.join("','")+"&ryid="+$("#txt_sqrid").val()+"&type=ccywsq","项目信息","900","620");
		 })
		 
		 //添加项目
	function addXmxx($checkbox){
		//添加的项目信息
		var addNum = 0;
		$.each($checkbox,function(){
			var $tr = $(".xmxx tbody tr:last-child").clone();
			var ktmc = $(this).find(".keyId").attr("ktmc");
			var jflx = $(this).find(".keyId").attr("jflx");
			var syje = $(this).find(".keyId").attr("syje");
			var jfbh = $(this).find(".keyId").attr("gid");
			$tr.find("[name='ktmc']").val(ktmc);
			$tr.find("[name='jflx']").val(jflx);
			$tr.find("[name='syje']").val(syje);
			$tr.find("[name='jfbh']").val(jfbh);
			$tr.find(".addBtn2").attr("class","deleteBtn");
			$(".xmxx tbody tr:last-child").before($tr);
			addNum ++;
		});
		 $(".xmxx tbody tr:last-child").remove();
		 $(".xmxx tbody tr:last-child").find(".deleteBtn").attr("class","addBtn2");
	}
	
	//验证表单
	var fields={fields:{
        djbh:{validators:{notEmpty: {message: '单据编号不能为空'},stringLength:{message:'项目编号过长',max:'12'}}},
        cclx:{validators:{notEmpty: {message: '出差类型不能为空'}}},
        sqrxm:{validators:{notEmpty: {message: '申请人姓名不能为空'}}},
        sxfy:{validators:{notEmpty: {message: ' '}}},
        yzfje:{validators:{notEmpty: {message: ' '}}},
        ccnr:{validators:{notEmpty: {message: '出差内容不能为空'}}},
        ccts:{validators:{notEmpty: {message: '出差天数不能为空'}}},
        ccrs:{validators:{notEmpty: {message: '出差人数不能为空'}}},
        kssj:{validators:{notEmpty: {message: '开始时间不能为空'}}},
        jssj:{validators:{notEmpty: {message: '结束时间不能为空'}}},
//         cfdd:{validators:{notEmpty: {message: '出发地点不能为空'}}},
        mddd:{validators:{notEmpty: {message: '目的地点不能为空'}}},
        jtgj:{validators:{notEmpty: {message: '交通工具不能为空'}}},
        provinceid:{validators:{notEmpty: {message: '目的地省份不能为空'}}},
        cityid:{validators:{notEmpty: {message: '目的地市区不能为空'}}},
        ccts:{validators:{notEmpty: {message: '不能为空'}}}
        }
	  };

	var json_sxfy = {validators:{notEmpty: {message: '所需费用不能为空'}},"enable":true};
    var json_yzfje = {validators:{notEmpty: {message: '预支付金额不能为空'}},"enable":true};
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
        	return {"fold":"zcxx","id":"${guid}","djlx":"000000"};
        },//区分不同的模块：fold：文件夹，id:生成文件的id(主键)，djlx:单据类型。
        //uploasExtraData:是把页面你想要往后退传的东西放（return）     到域里面然后去后台去取
        overwriteInitial: false,  
        deleteUrl: "${ctx}/file/fileDelete"//删除文件的路径
    }).on("filebatchpreupload",function(){
    	load = loading(2);
    }).on("filebatchuploadcomplete",function(){
    	close(load);
    	var  src = "${ctx}/wsbx/ccyw/ccywsq/goCcywsqPage?&mkbh=${param.mkbh}";
    	location.href = src;
    });
   //扫码上传
 	SmscLoad("${ctx}",{"id":"${guid}","djlx":"000000","fold":"zcxx","rybh":"000000","mkbh":"zjb"},"imageFile",getPname());
	/*图片信息结束  */
//保存方法	
function doSave1($form, _url, _success, _fail){
		
	var validator = $('#myform').bootstrapValidator(fields);
	
	if(validator){
		validator.bootstrapValidator("validate");
		var valid = $form.data('bootstrapValidator').isValid();
		if(!valid){
			return;
		}
		if(!tag){
			valid = false;
		}
	}
	if(checkDjbhExist()){
		return;
	}
// 	$("#imageFile").fileinput("upload");
	var json = $form.serializeObject("xm","end");
	var jsonStr = JSON.stringify(json);
	var formJson = arrayToJson($form.serializeArray());
	formJson.json = jsonStr;
	
	var json2 = $form.serializeObject("jfbh","syje");
	var jsonStr2 = JSON.stringify(json2);
	formJson.json2 = jsonStr2;
	
	$.ajax({
		type:"post",
		url:_url,
		data:formJson,
		success:function(data){
			_success(data);
		},
		error:function(val){
			alert("抱歉，系统出现问题！");
		},
	});	
	
}
	
$("#sel_provinceid").change(function(){
	$.ajax({
		url:"${pageContext.request.contextPath}/provinces/getCitiesByProvince?provinceid="+$(this).val(),
		dataType:"json",
		type:"post",
		async:false,
		success:function(val){
			$("#sel_cityid").find("option").remove();
			var cities = '<option value="" >请选择...</option>';
			for(var i = 0; i < val.length; i++){
				var item = val[i];
				cities += '<option value="'+item.CITYID+'">'+item.CITY+'</option>';
			}
			$("#sel_cityid").append(cities);
		},
		error:function(){
			alert("系统繁忙，请稍候重试！");
		}
	});
});
//弹窗
$(document).on("click","#btn_sqr",function(){
	select_commonWin("${ctx}/window/rypage?controlId=txt_sqrxm&ryid=txt_sqrid&szbmm=txt_szbm","负责人","920","630");
});
//联想输入
//  $(document).on("click",".btn-ktmc",function(){
//  $("#txt_ktmc").bindChange("${ctx}/suggest/getXx","XMMC",null,null,function(){
 $("#txt_ktmc000").bindChange("${ctx}/suggest/getXx","XMMC",null,null,function(){
	 var xmmc = $("#txt_ktmc").val();
	 console.log("aaa++++"+xmmc);
	 $.ajax({
			type:"post",
			url:"${ctx}/wsbx/ccyw/ccywsq/getxmxx",
			dataType:"json",
			data:"xmmc="+xmmc,
			success:function(val){
				var guid = val.GUID;
				var jflx = val.JFLXMC;
				var syje = val.YE;
               $("#txt_jfbh").val(guid);
               $("#txt_jflx").val(jflx);
               $("#txt_syje").val(syje.toFixed(2));
				}		
		});
 });
 // 计算出差天数
	function ckts(){
		var kssj = $("[name='kssj']").val();
		var jssj = $("[name='jssj']").val();
		var qssjd=new Date(kssj);
		var zzsjd=new Date(jssj);
		var hms = zzsjd.getTime()-qssjd.getTime();//计算相差的毫秒数
		var count=Math.floor(hms / (1000*60*60*24) );//计算相差的天数
		
		if(count<0){
			$(".box-panel").find("[name='ccts']").val('');	
		}else if(kssj==null||jssj==null||kssj==''||jssj==''){
			$(".box-panel").find("[name='ccts']").val('');	
		}else if(count==0){
			$(".box-panel").find("[name='ccts']").val(1);	
		}else {
		
			var count0 = hms%(24*3600*1000);   //计算天数后剩余的毫秒数
			var hours=Math.floor(count0 / (3600*1000)); //计算出小时数   count天 hours小时
			$(".box-panel").find("[name='ccts']").val(count);	
		}
	}
//计算出差人数
function computerCcrs(){
	var txrs = $(".txry tr:last-child").find(".xh").text();
	$("#txt_ccrs").val(txrs);
}
//计算日期间隔
function dateDiff(str_a,str_b){
	var date_a = new Date(str_a);
	var date_b = new Date(str_b);
	var interval = parseInt((date_a - date_b)/(24*60*60*1000))-1;
	return interval < 0 ? (0-interval) : interval;
}
//验证金额
function moneyVerify(){
	var yzfje = parseFloat($("#txt_yzfje").val());
// 	var syje = parseFloat($("#txt_syje").val()) * 10000;
	var syje = parseFloat($("#txt_syje").val());
	if(!isNull(syje) && !isNull(yzfje) && yzfje > syje){
		alert("预支付金额不能大于剩余金额！");
		$("#txt_yzfje").val("");
	}
}
//验证日期
function dateVerify(){
	var kssj = $("#txt_kssj").val();
	var jssj = $("#txt_jssj").val();
	var ccts = $("#txt_ccts").val();
	if(!isNull(kssj) && !isNull(jssj)){
		/* if(kssj < today){
			alert("开始时间不能小于当前时间！");
			$("#txt_kssj").val("");
		}else if(jssj < today){
			alert("结束时间不能小于当前时间！");
			$("#txt_jssj").val("");
		}else  */
		if(jssj < kssj){
			alert("结束时间不能小于开始时间！");
			$("#txt_jssj").val("");
		}else if(ccts == ""){
// 			var ccts = dateDiff(kssj,jssj);
// 			$("#txt_ccts").val(ccts);
		}
	}
}
//同行人员排序
function sort(){
	$.each($(".txry tr").has(".xh"),function(index){
		$(this).find(".xh").text(index+1);
	});
}
//项目信息排序
function sort(){
	$.each($("tr").has(".xh"),function(index){
		$(this).find(".xh").text(index+1);
	});
}
//验证空值
function isNull(a_str){
	if(a_str == "" || a_str == undefined || a_str == null){
		return true;
	}
	return false;
}
//添加同行人员
function addTxry($checkbox){
	var txry = {};
	$.each($("[name='rybh']"),function(){
		var guid = $(this).val();
		txry[guid] = "1";
	});
	var errMsg = "<br/>";
	//添加的人员
	var addNum = 0;
	$.each($checkbox,function(){
		var ryid = $(this).find(".keyId").attr("guid");
		var ryxm = "("+$(this).find(".keyId").attr("rybh")+")"+$(this).find(".xm").text();
		var btn_id= $(this).find(".keyId").attr("controlId");
		if(txry[ryid] != null){
			errMsg += ryxm+"<br/>";
			return;
		}
		var $tr = $("#"+btn_id).parents("tr");
		$tr.find("[name='rybh']").val(ryid);
// 		console.log($tr.find("[name='rybh']").val());
		$tr.find("[name='xm']").val(ryxm).attr({"readonly":true});
// 		console.log($tr.find("[name='xm']").val());
		$tr.find("[name='szbm']").val($(this).find(".dwmc").text()).attr({"readonly":true});
// 		console.log($tr.find("[name='szbm']").val());
		$("tr.txry_tr:first").find(".btn-del").addClass("addBtn");
		$("tr.txry_tr:first").find(".btn-del").removeClass("btn-del");
// 		$tr.find(".addBtn").attr("class","btn-del");
// 		$(".txry tr:last-child").before($tr);
		addNum ++;
	});
	//如果添加的人员为0，直接退出
	if(addNum == 0){
		return;
	}else if(addNum < $checkbox.length){
		alert(errMsg+"已经存在！");
	}
	sort();
	computerCcrs(1);
}
//检查出差业务申请编号是否已经存在
function checkDjbhExist(){
	var tag = false;
	var djbh = $("#txt_djbh").val();
	$.ajax({
		type:"post",
		//url:ADDRESS+"/xmxx/checkXmbh",
		url:basePath+"/checkDjbhExist",
		data:"djbh="+djbh,
		async:false,
		success:function(val){
			var result = JSON.getJson(val);
			if(result.exist){
				tag = true;
				alert("该单据编号已经存在！");
			}
		}
	})
	return tag;
}
$(function(){
	function checkAutoInput(){
		tag = true;
		var ys = $(".null");
		var value = "";
		$.each(ys,function(){
			value = $(this).val();
			if(value==""){
				$(this).addClass("border");
				tag = false;
			}
		});
	}
	$(document).on("focus","[class*=border]",function(){
		$(this).removeClass("border");
	});

	
	//保存
   $("#btn_save").click(function(){	   
        checkAutoInput();
	 	//var url = ADDRESS+"/xmxx/add";
	 	var checkbox = $("#mydatatablesm").find("[name='jtgj']").filter(":checked");
	 	if(checkbox.length>0){
   			var jtgj = [];
	   	   	checkbox.each(function(){
	   	   		jtgj.push($(this).val());
	   	   	});
	   		var url = basePath+"/ccywsqAdd?jtgjm="+jtgj;
		    var ktmcval =  $(".xmxx tbody [name='ktmc']").val();
//	 	    var ktmcval =  $(".xmxx tbody tr:nth-child(2)").attr("name");
		    if(ktmcval==null||ktmcval==""){
		    	alert("请选择项目！！！")
		    	return;
		    }
			doSave1($("#myform"),url,function(val){
				var result = JSON.getJson(val);
				var frame = $(".file-live-thumbs").find(".file-preview-frame");
				if(frame.length > 0){
					$("#imageFile").fileinput("upload");
				}else{
					if(result.success){
						alert("保存成功！");
						window.location = basePath+"/goCcywsqPage?"+suffix;
					}
				}
				
			});
	 	}else{
   			alert("请选择至少一种交通工具!");
   		}
	 	
	   	
	});
	//返回
	$("#btn_back").click(function(){
		window.location.href = basePath+"/goCcywsqPage?"+suffix;
	});
	//日期验证
	$("#txt_kssj,#txt_jssj").blur(function(){
		dateVerify();
	})
	//添加
	$(document).on("click",".btn-txry",function(){
		var controlId = $(this).parents("tr").find("[name='xm']").attr("id");
		var szbm = $(this).parents("tr").find("[name='szbm']").attr("id");
		var rybh = $(this).parents("tr").find("[name='rybh']").attr("id");
		select_commonWin("${ctx}/window/rypagewqx?controlId="+controlId+"&szbmm="+szbm+"&rybh="+rybh,"人员信息","920","630");
	});
	var txrynum = 2;
	$(document).on("click",".addBtn",function(){
			var $parentTr = $(this).parents("tr").clone();
			$parentTr.find(":input").val("");
			$parentTr.find(".addBtn").removeClass("addBtn").addClass("btn-del");
			$parentTr.find("[name=xm]").attr({"readonly":false});
			$parentTr.find("[name=szbm]").attr({"readonly":false});
			$parentTr.find(".btn-txry").removeClass("hide");
			$parentTr.find("[name=xm]").attr({"id":"xm"+txrynum});
			$parentTr.find("[name=szbm]").attr({"id":"szbmm"+txrynum});
			$parentTr.find("[name=rybh]").attr({"id":"rybh"+txrynum});
			$parentTr.find("[id^=btn_txry]").attr({"id":"btn_txry"+txrynum});
			txrynum++;
			
			$(this).parents("table").append($parentTr);
			sort();
			computerCcrs(1);
		});
	//删除
	$(document).on("click",".btn-del",function(){
		$(this).parents("tr").remove();
		sort();
		computerCcrs(0);
	})
	$(document).on("blur","[name=xm]",function(){
		if($(this).val()==null||$(this).val()==""){
			$(this).parents("tr").find("[name='szbm']").val("");
			$(this).parents("tr").find("[name='rybh']").val("");
		}
	});
	//扫描上传
	$("#scanToServer").click(function(){
		scanToServer();
	});
	function scanToServer(){
		select_commonWin("${pageContext.request.contextPath}/ywsm/goScanPage?mkbh=${param.mkbh}&sfbc=1&zbid=${guid}&basePath=<%=basePath%>&fold=zcxx","", "920", "530");
	}
	
	//金额验证
	$("#txt_yzfje,#txt_syje").change(function(){
		moneyVerify();
	})
	//刷新按钮
	$(".reload").click(function(){
		 window.location.reload();
	});
	//文本框展示
	$(document).on("change","#txt_cclx",function(){
		var val = $(this).val();
		var txt;
		switch (val) {
		case "01":
			txt = "会议费用（元）";
			break;
		case "02":
			txt = "培训费用（元）";
			break;
		case "03":
			txt = "公务费用（元）";
			break;
		default:
			txt = "";
			$("#content_sxfy").hide();
			$("#txt_sxfy").val("").attr("disabled","disabled");
			//delete fields.fields.sxfy;
			return;
		}
		$("#word_sxfy").text(txt);
// 		$("#content_sxfy").show();
// 		$("#txt_sxfy").removeAttr("disabled");
		//fields.fields.sxfy = json_sxfy;
	})
	$(document).on("change","[name='sfyzf']",function(){
		var val = $(this).val();
		switch (val) {
		case "1":
			$("#content_yzfje").show();
			$("#txt_yzfje").removeAttr("disabled");
			//fields.fields.yzfje = json_yzfje;
			break;
		case "0":
			$("#content_yzfje").hide();
			$("#txt_yzfje").val("").attr("disabled","disabled");
			//delete fields.fields.yzfje;
			break;
		}
	})
});
</script>
</body>
</html>
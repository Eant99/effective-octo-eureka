<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>待移交资产</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.tool-fix{
		display:none;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<c:if test="${flag == 'part'}">
		<div class="search" id="searchBox">
			<form id="myform" class="form-inline" role="form" action="">
	    		<div class="search-simple">
					<div class="form-group">
						<label>资产编号</label>
						<input type="text" id="txt_zcbh" class="form-control input-radius" name="yqbh" value="" table="T" placeholder="请输入资产编号">
					</div>
					<div class="form-group">
						<label>资产名称</label>
						<input type="text" id="txt_zcmc" class="form-control input-radius" name="yqmc" table="T" placeholder="请输入资产名称">
					</div>
					<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
					<div class="form-group">
						<label><span class="red">*</span>接&ensp;收&ensp;人</label>
						<div class="input-group">
							<input type="text" id="txt_jsr" class="form-control input-radius" name="jsr" placeholder="请选择接收人">
							<span class="input-group-btn"><button type="button" id="btn_jsr" class="btn btn-link">选择</button></span>
						</div>
 					</div>
				</div>
	        </form>
		</div>
	</c:if>
	
	<c:if test="${flag == 'all'}">
		<div class="search" id="searchBox">
			<form id="myform" class="form-inline" role="form" action="">
	    		<div class="search-simple" style="padding:30px 10px;">
	    			<span><span class="red">*</span>接&ensp;收&ensp;人<input type="text" id="txt_jsr" class="form-control input-radius" style="display:inline;width:160px;" name="jsr" placeholder="请选择接收人"><button type="button" id="btn_jsr" class="btn btn-link">选择</button><button type="button" class="btn btn-primary" id="btn_save">确认</button></span>
				</div>
	        </form>
		</div>
	</c:if>
	
	
	<c:if test="${flag == 'part'}">
		<div class="container-fluid">
			<div class='responsive-table'>
		        <div class='scrollable-area'>
		            <div class="alert alert-info pull-right wxts_sh">温馨提示：请先选择接收人，再选择需要移交的资产，然后将鼠标移动到右下角的蓝色圆圈上，点击圆圈里的“确认移交”按钮，完成操作！</div>
					<table id="mydatatables" class="table table-striped table-bordered">
					    <thead>
						    <tr>
								<th><input type="checkbox" class="select-all"/></th>
								<th>序号</th>
								<th>资产编号</th>
								<th>资产名称</th>
								<th>分类号</th>
								<th>分类名称</th>
								<th>使用人</th>
								<th>使用单位</th>
								<th>现状</th>
								<th>数量</th>
								<th>总价</th>
								<th>入账日期</th>
								<th>存放地点</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
			</div>
		</div>
	</c:if>
</div>
<c:if test="${flag == 'part'}">
	<div class="batch-container">
		<div class="batch-btn-wr">
			<div class="batch-btn">
				<i class="fa icon-shujutubiao17" style="color:white"></i><span>0</span>
			</div>
		</div>
		<div class="batch-file-container">
			<div class="batch-file-header">
				已选择的资产列表
				<div class="batch-file-close">×</div>
			</div>
			<div class="batch-file-controller text-right">
				<a class="batch-file-controller-next btn btn-default" href="javascript:void(0);">
					确认移交
				</a>
				<a class="batch-file-controller-clear btn btn-default" href="javascript:void(0);">
				清空列表
				</a>
			</div>
			<div class="batch-file-list-container" style ="padding: 0 4px 10px 4px;">
				<div class="batchs" style ="margin-top:2px">
				    <table class="table table-striped table-bordered">
				        <thead>
				            <tr>
				                <th style ="text-align:center">资产编号</th>
				                <th style ="text-align:center">资产名称</th>
				                <th style ="text-align:center">使用单位</th>
				                <th style ="text-align:center">存放地点</th>
				                <th style ="width:35px;text-align:center">操作</th>
				            </tr>
				         </thead>
				         <tbody class = "batch-file-list">
				         </tbody>
				    </table>
				</div>
			</div>
		</div>
	</div>
</c:if>
<%@include file="/static/include/public-list-js.inc"%>
<script src="${ctx}/static/javascript/public/public-drag.js"></script>
<script type="text/javascript">
	$(function() {
		//联想输入提示
	   	$("#txt_jsr").bindChange("${ctx}/suggest/getXx","R");
		//选择弹窗
		$("#btn_jsr").click(function(){
			select_commonWin("${ctx}/window/rypage?controlId=txt_jsr","人员信息","920","630");
	    });
		
		//列表数据
		var columns = [
		    {"data": "YQBH",orderable:false, "render": function (data, type, full, meta){
	    		return '<input type="checkbox" id="chk_' + data + '" class="keyId" name="yqbh" value="' + data + '" ztdm="' + (full.BDDM == undefined ? "" : full.BDDM) + '" zczt="' + (full.BDZT == undefined ? "" : full.BDZT) + '" yqmc="' + (full.YQMC == undefined ? "" : full.YQMC) + '" flh="'+(full.FLH == undefined ? "" : full.FLH)+'" yq="'+(full.YQ == undefined ? "" : full.YQ)+'" sydwmc="'+(full.SYDWMC == undefined ? "" : full.SYDWMC)+'" cfddmc="'+(full.CFDDMC == undefined ? "" : full.CFDDMC)+'"/>';
			},"width":10,searchable: false},
			{"data":"_XH",orderable:false,"width":41,searchable: false,"class":"text-center"},
			{"data": "YQBH",defaultContent:""}, 
			{"data": "YQMC",defaultContent:""}, 
			{"data": "FLH",defaultContent:""},
			{"data": "FLMC",defaultContent:""},
			{"data": "SYR",'render':function(data,type,full,meta){
	    		return '<a href="javascript:void(0);" class="btn btn-link rylook" rybh="'+(full.SYRBH==undefined? '': full.SYRBH)+'" path="${pageContext.request.contextPath}">'+ (data==undefined ? '':data) +'</a>';
	        }},
	        {"data": "SYDW",defaultContent:"","render":function(data, type, full,  meta){
		   		return '<a href="javascript:void(0);" class="btn btn-link dwlook" dwbh="'+(full.SYDWBH==undefined? '': full.SYDWBH)+'" path="${pageContext.request.contextPath}">'+ (data==undefined ? '':data) +'</a>';
		   	}},
			{"data": "XZ",defaultContent:""},
			{"data": "SYKH",defaultContent:"","class":"text-right"},
			{"data": "ZZJ",defaultContent:"","class":"text-right"},
			{"data": "RZRQ",defaultContent:""},
			{"data": "BZXX",defaultContent:""},
		];
		table = getDataTableByListHj("mydatatables","${ctx}/lzry/getZcPageList?rybh=${rybh}",[2,'asc'],columns,0,1,setGroup);
		
		//确认按钮
		$("#btn_save").click(function(){
			var jsr = $("#txt_jsr").val();
			if(jsr == ''){
				alert("请选择接收人！");
			}else{
				confirm("确认后资产将全部直接移交，确定移交吗？",{title:"提示"},function(index){
		   			close(index);
		   			$.ajax({
						type:"post",
						url:"${ctx}/lzry/doSave",
						data:"rybh=${rybh}&jsr="+jsr,
						success:function(data){
							close(idx);
							var val = JSON.getJson(data);
							if(val.success == 'true'){
								alert(val.msg);
								getIframWindow("${param.pname}").table.ajax.reload();
								var winId = getTopFrame().layer.getFrameIndex(window.name);
						    	close(winId);
							}
							else
							{
								alert(val.msg);
							}
						},
						error:function(val){
							close(idx);
							alert(getPubErrorMsg());
						},
						beforeSend:function(val){
							idx = loading(2);
						}
					});
				});
			}
		});
		//选择变动操作
	    $(document).on('change','.keyId:not(.select-all)',function(e){
			var yqbh = $(this).val();
			var flh = $(this).attr("flh");
			var yqmc = $(this).attr("yqmc");
			var zczt = $(this).attr("ztdm");
			var yq = $(this).attr("yq");
			var sydwmc = $(this).attr("sydwmc");
			var cfddmc = $(this).attr("cfddmc");
			if($(this).prop("checked")){
	    		$("#hid_gkry").val($("#sel_gkry").val());
	    		if($("#d_chkzc_"+yqbh).length == 0){
	    			var StrHtml="<tr id=\"d_chkzc_" + yqbh + "\" class = 'checkzc' flh=\"" + flh + "\">"+
		               "<td>"+yqbh+"</td>"+
		               "<td>"+yq+"</td>"+
		               "<td>"+sydwmc+"</td>"+
		               "<td>"+cfddmc+"</td>"+
		               "<td><a id=\"d_delzc_" + yqbh + "\" class=\"batch-file-item-delete\" href=\"javascript:void(0);\"><i class=\"fa icon-cuowu\" style=\"color:red;line-height: 21px;padding-left:5px\"></i></a></td>"+
		            "</tr>";
					$(".batch-file-list").append(StrHtml);
	    		}
	 		} else {
	 			$("#d_chkzc_"+yqbh).remove();
   				$(this).parents("tr").removeClass("selected");
	 		}
			$(".batch-btn span").prop("innerHTML",$("[id^='d_chkzc_']").length);
	   		return e.preventDefault;
	    });

	  	//选中的资产删除操作
	   	$(document).on("click",".batch-file-item-delete",function(){
	   		var yqbh = $(this).attr("id").replace("d_delzc_","");
	   		$("#chk_" + yqbh).prop("checked",false);
	   		$("#chk_" + yqbh).parents("tr").removeClass("selected");
	   		
	   		$("#d_chkzc_" + yqbh).remove();
	   		
	   		$(".batch-btn span").prop("innerHTML",$("[id^='d_chkzc_']").length);
	   	});
	  	
	 	//已选择明细详细信息清空列表事件
	   	$(".batch-file-controller-clear").bind("click",function(){
	   		var selLength=$(".batch-btn span").text();
	   		if(selLength == 0){
	   			alert("您还没有选择，请先选择！");
	   		}
	   		else{
		   		confirm("确定要清空选中的资产吗？",{title:"提示"},function(index){
		 			close(index);
		 	   		//1.更新所有行的已添加标志为变动
		 	   		$(":checkbox[id^='chk_']").prop("checked",false);
		 	   		$("tr").removeClass("selected");
		 	   		//2.删除所有选中的行
		 	   		$('.checkzc').remove();
		 	   		//3.页面展示的选择条数清空为0
		 	   		$(".batch-btn span").prop("innerHTML","0");
		   		});
	   		}
	   		return false;
	   	});
	  	//已选择明细详细信息下一步事件
	   	$(".batch-file-controller-next").click(function(){
	   		var selLength = $(".batch-btn span").text();
	   		var jsr = $("#txt_jsr").val();
	   		if(jsr == '' || jsr == 'undefined'){
	   			alert("请选择接收人！");
	   			return false;
			}else{
		   		if(selLength == 0){
		   			alert("您还没有选择，请先选择！");
		   	   		return false;
		   		}
		   		else{
			   		var yqbh = [];
		   			$("tr[id^='d_chkzc_']").each(function(){
		   				yqbh.push($(this).attr("id").replace("d_chkzc_",""));
		   			});
		   			confirm("确认后选择的资产将直接移交，确定移交吗？",{title:"提示"},function(index){
			   			close(index);
			   			$.ajax({
							type:"post",
							url:"${ctx}/lzry/doSave",
							data:"rybh=${rybh}&jsr="+jsr+"&yqbhs="+yqbh+"&operateType=C",
							success:function(data){
								close(idx);
								var val = JSON.getJson(data);
								if(val.success == 'true'){
									alert(val.msg);
									getIframWindow("${param.pname}").table.ajax.reload();
									var winId = getTopFrame().layer.getFrameIndex(window.name);
							    	close(winId);
								}
								else
								{
									alert(val.msg);
								}
							},
							error:function(val){
								close(idx);
								alert(getPubErrorMsg());
							},
							beforeSend:function(val){
								idx = loading(2);
							}
						});
					});
		   		}
			}
	   		return false;
		});
		
		//列表右侧悬浮按钮
		$(window).resize(function(){
	    	$("div.dataTables_wrapper").width($("#searchBox").width());
	    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
	    	$(".dataTables_scrollBody").height(heights);
	    	table.draw();
		});
	});
</script>
</body>
</html>
<%@page import="com.googosoft.constant.MenuFlag"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>验收单信息查询</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
	<div class="fullscreen">
		<div class="search" id="searchBox">
			<form id="myform" class="form-inline" action="">
<!-- 				<div class="row ztbz"> -->
<!-- 	                <div class="col-md-12 checkbox" table="K" types="C" name="ztbz"> -->
<!-- 	                	<span class="zt_label">单据状态：</span> -->
<!-- 	                	<a type="button" data-value="" class="btn btn-link btn-mark btn-mark-all active">全部<span class="label label-success">&radic;</span></a> -->
<!-- 	                	<a type="button" data-value="55,20" class="btn btn-link btn-mark active">未提交 <span class="badge wtj">0</span><span class="label label-success">&radic;</span></a> -->
<!-- 	                	<a type="button" data-value="00,25" class="btn btn-link btn-mark active">已提交 <span class="badge ytj">0</span><span class="label label-success">&radic;</span></a> -->
<!-- 	                	<a type="button" data-value="10" class="btn btn-link btn-mark active">归口审核退回<span class="badge gkshwtg">0</span><span class="label label-success">&radic;</span></a> -->
<!-- 	                	<a type="button" data-value="90" class="btn btn-link btn-mark active">归口审核通过<span class="badge gkshtg">0</span><span class="label label-success">&radic;</span></a> -->
<!-- 	                	<a type="button" data-value="91" class="btn btn-link btn-mark active">财务审核退回 <span class="badge cwshth">0</span><span class="label label-success">&radic;</span></a> -->
<!-- 	                	<a type="button" data-value="99" class="btn btn-link btn-mark active">财务审核通过 <span class="badge cwshtg">0</span><span class="label label-success">&radic;</span></a> -->
<!-- 	              	</div> -->
<!--            		 </div> -->
<!--            		<hr class="hr-normal" style="margin: 5px -15px"> -->
				<div class="search-simple">
					<div class="form-group">
						<label>验收单号</label>
						<input type="text"  class="form-control" name="ysdh" table="K" placeholder="请输入验收单号">
					</div>
					<div class="form-group">
						<label>资产名称</label>
						<input type="text" id="txt_yqmc" class="form-control input-radius" table="K" name="yqmc" placeholder="请输入资产名称">
					</div>
					<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
					<div id="btn_search_more">
						<span id="btn_search_gjcx">
							<i class="fa icon-btn-gjcx"></i>
							<span>高级查询</span>
						</span>
						<div class="search-more">
							<div class="form-group">
								<label>分&ensp;类&ensp;号</label>
								<div class="input-group">
									<input type="text" id="sel_flh" class="form-control input-radius" types="F" table="K" name="flh" placeholder="请选择分类号">
									<span class="input-group-btn"><button type="button" id="btn_flh" class="btn btn-link ">选择</button></span>
								</div>
							</div>
							<!-- <div class="form-group">
								<label>分类名称</label>
								<input type="text" id="txt_yqbh" class="form-control input-radius" table="K" name="yqbh" placeholder="请输入分类名称">
							</div> -->
							<div class="form-group">
								<label>现&emsp;&emsp;状</label>
								<select id="drp_xz" class="form-control" style="width:152px;" name="xz" table="K" types="E">
	               					<option value="">未选择</option>
		                             <c:forEach var="xzList" items="${xzList}"> 
			                             <option value="${xzList.DM}" <c:if test="${yshd.XZ == xzList.DM}">selected</c:if>>${xzList.DM}-${xzList.MC}</option>
									</c:forEach>
		                		</select>
							</div>
							<div class="form-group">
								<label>申购单位</label>
								<div class="input-group">
									<input type="text" id="txt_shgdw" class="form-control input-radius" name="shgdw" value="" types="D" table="K" placeholder="请选择申购单位">
									<span class="input-group-btn"><button type="button" id="btn_shgdw" class="btn btn-link ">选择</button></span>
								</div>
							</div>
							<div class="form-group">
								<label>购置日期</label>
								<div class="input-group">
									<input type="text" id="txt_dzrrq_begin" class="form-control date" name="dzrrq" types="TL" table="K" data-format="yyyy-MM-dd" placeholder="请输入开始日期">
									<span class='input-group-addon'>
								    	<i class="glyphicon glyphicon-calendar"></i>
								   	</span>
							   	</div>
								<label>--</label>
								<div class="input-group">
									<input type="text" id="txt_dzrrq_end" class="form-control date" name="dzrrq" types="TH" table="K" data-format="yyyy-MM-dd" placeholder="请输入截止日期">
									<span class='input-group-addon'>
								    	<i class="glyphicon glyphicon-calendar"></i>
							   		</span>
								</div>
							</div>
							<div class="search-more-bottom clearfix">
								<div class="pull-right">
									<button type="button" class="btn btn-primary" id="btn_search">
										<i class="fa icon-chaxun"></i>
										查 询
									</button>
									<button type="button" class="btn btn-default" id="btn_cancel">
										<i class="fa icon-cuowu"></i>
										取 消
									</button>
								</div>
							</div>
						</div>
					</div>
            		<div class="btn-group pull-right" role="group">
<!--             			<button type="button" id="btn_zcfd" class="btn btn-default">资产附单</button> -->
<!--                     	<button type="button" id="btn_pxzh" class="btn btn-default">组合排序</button> -->
<!--                     	<button type="button" id="btn_hzfx" class="btn btn-default">汇总分析</button> -->
                    	<button type="button" id="btn_exp"  class="btn btn-default">导出Excel</button>
                    	<button type="button" id="btn_dayin1"  class="btn btn-default">打印预览</button>
<!--                     	<div class="btn-group"> -->
<!-- 					        <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown"> -->
<!-- 					            <i class='fa icon-sangangy'></i> -->
<!-- 					        </button> -->
<!-- 					        <ul class="dropdown-menu dropdown-menu-right" role="menu"> -->
<!-- 					            <li><a id="btn_dayin1" class="btn btn-default">打印预览</a></li> -->
<!-- 					  	        <li><a id="btn_dayin2" class="btn btn-default">打印列表</a></li> -->
<!-- 					  	        <li><a id="btn_dayin3" class="btn btn-default">打印卡片</a></li> -->
<!-- 					  	        <li><a class="btn btn-default" id="btn_txm">打印条码</a></li> -->
<!-- 					        </ul> -->
<!-- 				        </div> -->
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
					        <th><input type="checkbox" class="select-all"/></th>
					        <th>序号</th>
					        <th>单据状态</th>
					        <th>验收单号</th>
					        <th>分类号</th>
					        <th>分类名称</th>
					        <th>资产名称</th>
					        <th>外文名称</th>
					        <th>申购单位</th>
					        <th>发票号</th>
					        <th>销售商</th>
					        <th>购置日期</th>
					        <th>计量单位</th>
					        <th>数量</th>
					        <th>单价</th>
					        <th>总价</th>
					        <th>现状</th>
					        <th>经费来源</th>
					        <th>资产来源</th>
					        <th>经手人</th>
					        <th>验收日期</th>
					        <th>货到日期</th>
					        <th>财政分类</th>
					        <th>财政分类名称</th>
					        <th>凭证号</th>
					        <th>记帐类型</th>
					        <th>记帐日期</th>
					        <th>学科</th>
					        <th>学科类别</th>
					        <th>生产厂家</th>
					        <th>型号</th>
					        <th>规格</th>
	<!-- 				        <th>操作</th> -->
					    </tr>
					    </thead>
					    <tbody>
					    </tbody>
					</table>
					<!-- datatables  结束 -->
	            </div>
	        </div>
		</div>
	</div>
<%@include file="/static/include/public-list-js.inc"%>
<script src="${pageContext.request.contextPath}/static/javascript/public/public-checked.js"></script>
<script>
$(function (){
	$("#txt_shgdw").bindChange("${pageContext.request.contextPath}/suggest/getXx","D");
	//列表数据
   	var columns = [
	   {"data": "YSDH",orderable:false, "render": function (data, type, full, meta){
	           return '<input type="checkbox" class="keyId" name="id" value="' + data + '" jzrlx="'+full.JZRLX+'" flh = "'+full.FLH+'" flmc = "'+full.YQBH+'" flgbm = "'+full.FLGBM+'" zt = "'+full.ZTBZ+'">';
	      },"width":20,'searchable': false},
	   {"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   	   return data;
	   		},"width":41,"searchable": false,"class":"text-center"},
	   {"data": "ZTBZ","render":function(data, type, full, meta){
		   if(full.ZTBZ=='00'){
			   return "<span style='color: red'>"+full.ZTBZMC+"</span>";
		   }else if(full.ZTBZ=='10'||full.ZTBZ=='15'){
			   return "<span style='color: rgb(248, 172, 89)'>"+full.ZTBZMC+"</span>";
		   }else if(full.ZTBZ=='20'||full.ZTBZ=='25'){
			   return "<span style='color: #8BC34A'>"+full.ZTBZMC+"</span>";
		   }else if(full.ZTBZ=='91'||full.ZTBZ=='99'){
			   return "<span style='color: #00acec'>"+full.ZTBZMC+"</span>";
		   }else{
			   return full.ZTBZMC;
		   }
	   },defaultContent:""},
	   {"data": "YSDH","zhpxname":"验收单号",defaultContent:"",'render':function (data, type, full, meta){
	   		return '<span class="btn btn-link btn_look" yqbh="'+(full.YSDH==undefined? '': full.YSDH)+'" path="${pageContext.request.contextPath}">'+ (data==undefined ? '':data) +'</span>';
		}},
	   {"data": "FLH","zhpxname":"分类号",defaultContent:""},
	   {"data": "YQBH","zhpxname":"分类名称",defaultContent:""},
	   {"data": "YQMC","zhpxname":"资产名称",defaultContent:""},
	   {"data": "WWMC","zhpxname":"外文名称",defaultContent:""},
	   {"data": "SHGDW","zhpxname":"申购单位",defaultContent:""},
	   {"data": "FPH","zhpxname":"发票号",defaultContent:""},
	   {"data": "XSS","zhpxname":"销售商",defaultContent:""},
	   {"data": "DZRRQ","zhpxname":"购置日期",defaultContent:""},
	   {"data": "JLDW","zhpxname":"计量单位",defaultContent:""},
	   {"data": "SHL","zhpxname":"数量",defaultContent:"","class":"text-right"},
	   {"data": "DJ","zhpxname":"单价",defaultContent:"","class":"text-right"},
	   {"data": "ZZJ","zhpxname":"总价",defaultContent:"","class":"text-right"},
	   {"data": "XZ","zhpxname":"现状",defaultContent:""},
	   {"data": "JFKM","zhpxname":"经费来源",defaultContent:""},
	   {"data": "ZCLY","zhpxname":"资产来源",defaultContent:"",'render':function (data, type, full, meta){
	   		return full.ZCLYZ==undefined? '': full.ZCLYZ;
		}},
	   {"data": "JSR","zhpxname":"经手人",defaultContent:"",'render':function(data,type,full,meta){
   		return '<a href="javascript:void(0);" id="look" class="btn btn-link rylook" rybh="'+(full.JSRBH==undefined? '': full.JSRBH)+'" path="${pageContext.request.contextPath}">'+ (data==undefined ? '':data) +'</a>';
       }},
	   {"data": "YSHRQ","zhpxname":"验收日期",defaultContent:""},
	   {"data": "HDRQ","zhpxname":"货到日期",defaultContent:""},
	   {"data": "FLGBM","zhpxname":"财政分类",defaultContent:""},
	   {"data": "FLGBM","zhpxname":"财政分类名称",defaultContent:"",'render':function (data, type, full, meta){
	   		return full.CZFLMC==undefined? '': full.CZFLMC;
		}},
	   {"data": "PZH","zhpxname":"凭证号",defaultContent:""},
	   {"data": "JZLX","zhpxname":"记帐类型",defaultContent:""},
	   {"data": "JZRQ","zhpxname":"记账日期",defaultContent:""},
	   {"data": "XK","zhpxname":"学科",defaultContent:""},
	   {"data": "XKLB","zhpxname":"学科类别",defaultContent:""},
	   {"data": "SCCJ","zhpxname":"生产厂家",defaultContent:""},
	   {"data": "XH","zhpxname":"型号",defaultContent:""},
	   {"data": "GG","zhpxname":"规格",defaultContent:""}
// 	   ,
// 	   {"data": "ZTBZ","render": function (data, type, full, meta){
// 		   return '<a href="javascript:void(0);" class="btn btn-link btn_look">查看</a>';
// 		},"width":50,orderable:false}
	];
   	table = getDataTableByListHj("mydatatables","${pageContext.request.contextPath}/ysdcx/getPageList?treedwbh=${dwbh}"+"&flag=${param.flag}&operate=CK&deskbz=${deskbz}",[3,'asc'],columns);
	//弹窗
   	$("#btn_shgdw").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/window/dwxx/window.jsp?controlId=txt_shgdw","单位信息","920","630");
    });
	
   	$("#sel_flh").bindChange("${pageContext.request.contextPath}/suggest/getXx","FC");
  	//分类代码弹窗
	$("#btn_flh").click(function(){
		select_commonWin("${pageContext.request.contextPath}/zcfltree/goWindow?controlId=sel_flh&&Next=C","分类信息","920","630");
    });
  	//查看操作
   	$(document).on("click",".btn_look",function(){
   		var mkbh = "<%=MenuFlag.ZCJZ_LYR%>";
   		var $this = $(this).parents("tr").find("[name='id']");
   		var ysdh = $this.val();
   		var ztbz = $this.attr("zt");
   		var flh = $this.attr("flh");
   		var flmc = $this.attr("flmc");
   		var flgbm = $this.attr("flgbm");
   		var operate = "CK";
   		select_commonWin("${pageContext.request.contextPath}/yshd/goZcflPage?operateType=L&ysdh="+ysdh+"&flh="+flh+"&flmc="+flmc+"&czfl="+flgbm+"&mkbh="+mkbh+"&operate="+operate+"&ztbz="+ztbz,"验收单信息","1200","630");
//    		doOperate("${pageContext.request.contextPath}/yshd/goZcflPage?ysdh="+ysdh+"&flh="+flh+"&flmc="+flmc+"&czfl="+flgbm+"&mkbh="+mkbh+"&operate="+operate+"&ztbz="+ztbz,"L");
   		return false;
   	});
  	//资产附单
	$("#btn_zcfd").click(function(){
		var $checked = $("#mydatatables").find("[name='id']").filter(":checked");
		if($checked.length != 1){
			alert("请选择一条数据进行操作！");
		}else{
			var ysdh = $checked.val();
			window.open("${pageContext.request.contextPath}/ysdcx/getZcfdList?ysdh="+ysdh);
		}
		return false;
	});
  	//排序组合
	$("#btn_pxzh").click(function(){
		var list = [];
		for(var i = 0; i < columns.length; i++){
			if(columns[i].zhpxname != undefined){
				list.push("{zhpxzd:'" + columns[i].data + "',zhpxname:'" + columns[i].zhpxname + "'}");
			}
		}
		select_commonWin("${pageContext.request.contextPath}/webView/cx/ysdcx/pxzh_list.jsp?columns=" + encodeURIComponent("[" + list.join(",") + "]"), "选择组合排序", 500, 500);
		return false;
	});
  	//汇总分析
	$("#btn_hzfx").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/cx/ysdcx/hzfx_list.jsp", "汇总分析", 500, 350);
		return false;
	});
    //导出Excel
	$("#btn_exp").click(function(){
		var json = searchJson("searchBox");
		var checkbox = $("#mydatatables").find("[name='id']").filter(":checked");
		var id = [];
		checkbox.each(function(){
			id.push($(this).val());
		});
		$.ajax({
   			type:"post",
   			data:"searchJson="+json+"&xlsname=验收单信息&dwbh=${dwbh}&id="+id.join(",")+"&flag=${param.flag}&deskbz=${deskbz}",
   			url:"${pageContext.request.contextPath}/ysdcx/doExp",
   			dataType:"json",
   			success:function(val){
   				close(index);
   				FileDownload("${pageContext.request.contextPath}/file/fileDownload","验收单信息.xls",val.url);
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
	//打印预览
	$("#btn_dayin1").click(function(){
		var $checked = $("#mydatatables").find("[name='id']").filter(":checked");
		var id = [];
		$checked.each(function(){
			id.push($(this).val());
		});
		if($checked.length != 1){
			alert("请先选择一条数据进行打印！");
		}else{
			$.ajax({
				type:"post",
				data:"&ysdh="+id.join(","),
				url:"${pageContext.request.contextPath}/yshd/gkCheck",
				dataType:"json",
				success:function(data){
					if(data.success=="true"){
						window.open("${pageContext.request.contextPath}/yshd/doPrintYsh?ysdh="+id.join(","));
						return false;
					}else{
						alert(data.msg);
					}
				}
			});
		}
	});
	
	//打印列表
	$("#btn_dayin2").click(function(){
		var $checked = $("#mydatatables").find("[name='id']").filter(":checked");
		var id = [];
		$checked.each(function(){
			id.push($(this).val());
		});
		window.open("${pageContext.request.contextPath}/yshd/doPrintYsdlb?id="+id.join(","));
		return false;
	});
	//打印卡片
	$("#btn_dayin3").click(function(){
		var $checked = $("#mydatatables").find("[name='id']").filter(":checked");
		if($checked.length != 1){
			alert("请先选择一条数据进行打印！");
		}else{
			var ysdh = $checked.val();
			window.open("${pageContext.request.contextPath}/yshd/doPrintCard?ysdh="+ysdh);
		}
		return false;
	});
	//打印条码
	$("#btn_txm").click(function(){
		var $checked = $("#mydatatables").find("[name='id']").filter(":checked");
		if($checked.length > 0){
			var id = [];
			$checked.each(function(){
				id.push($(this).val());
			});
			window.open("${pageContext.request.contextPath}/yshd/doPrintTxm?lb=1&id="+id.join("','"));
		}else{
			alert("请先选择数据再进行打印！");
		}
		return false;
	});
    
	$("#btn_search").click();
    //点击单据状态
    $(document).on("click",".btn-mark",function(){
    	markcheck($(this),$("#btn_search"));
    	return false;
    });
});
</script>
</body>
</html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产处置信息查询列表</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
   	<div class="search" id="searchBox">
		<form class="form-inline" role="form" id="myform" action="">
<!-- 			<div class="row ztbz"> -->
<!--                 <div class="col-md-12 checkbox" table="K" types="C" name="ztbz"> -->
<!--                 	<span class="zt_label">单据状态：</span> -->
<!--                 	<a type="button" data-value="" class="btn btn-link btn-mark btn-mark-all active">全部<span class="label label-success">&radic;</span></a> -->
<!--                 	<a type="button" data-value="55" class="btn btn-link btn-mark active">未提交 <span class="badge wtj">0</span><span class="label label-success">&radic;</span></a> -->
<!--                 	<a type="button" data-value="00" class="btn btn-link btn-mark active">已提交 <span class="badge ytj">0</span><span class="label label-success">&radic;</span></a> -->
<!--                 	<a type="button" data-value="10" class="btn btn-link btn-mark active">归口审核通过 <span class="badge gktg">0</span><span class="label label-success">&radic;</span></a> -->
<!--                 	<a type="button" data-value="90" class="btn btn-link btn-mark active">归口审核退回 <span class="badge gkth">0</span><span class="label label-success">&radic;</span></a> -->
<!--                 	<a type="button" data-value="99" class="btn btn-link btn-mark active">财务审核通过 <span class="badge cwtg">0</span><span class="label label-success">&radic;</span></a> -->
<!--                 	<a type="button" data-value="91" class="btn btn-link btn-mark active">财务审核退回 <span class="badge cwth">0</span><span class="label label-success">&radic;</span></a> -->
<!--               	</div> -->
<!--             </div> -->
<!--             <hr class="hr-normal" style="margin: 5px -15px"> -->
           	<div class="search-simple">
				<div class="form-group">
					<label>处置单号</label>
					<input type="text"  class="form-control" name="czbgbh" table="K" placeholder="请输入处置单号">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div id="btn_search_more">
					<span id="btn_search_gjcx">
						<i class="fa icon-btn-gjcx"></i>
						<span>高级查询</span>
					</span>
					<div class="search-more">
						<div class="form-group">
							<label>编报单位</label>
							<div class="input-group">
								<input type="text" id="txt_dwmc" class="form-control input-radius" types="D" table="K" name="dwbh" placeholder="请选择编报单位">
			     				<span class="input-group-btn"><button type="button" id="btn_dwmc" class="btn btn-link ">选择</button></span>
							</div>
						</div>
						<div class="form-group">
							<label>处&ensp;置&ensp;人</label>
							<div class="input-group">
								<input type="text" id="txt_czrxm" class="form-control input-radius" name="rybh" value="" types="R" table="K" placeholder="请选择处置人">
								<span class="input-group-btn"><button type="button" id="btn_czrxm" class="btn btn-link ">选择</button></span>
							</div>
						</div>
						<div class="form-group">
							<label>归口人员</label>
							<div class="input-group">
								<input type="text"  id="txt_gkry" class="form-control input-radius" name="gkry" types="R" table="K" placeholder="请选择归口人员">
								<span class="input-group-btn"><button type="button" id="btn_gkry" class="btn btn-link ">选择</button></span>
							</div>
						</div>
						<div class="form-group">
							<label>处置原因</label>
							<input type="text"  class="form-control input-radius" name="czyy"  table="K" placeholder="请输入处置原因">
						</div>
						<div class="form-group">
							<label>处置方式</label>
							<select  class="form-control" name="hxz" table="K" types="E">
	               				<option value="">未选择</option>
	                            <c:forEach var="czfslist" items="${czfslist}"> 
	                                <option value="${czfslist.DM}" <c:if test="${czsqb.HXZ == czfslist.DM}">selected</c:if>>${czfslist.DM}-${czfslist.MC}</option>
								</c:forEach>
	                		</select>
						</div>
						
						<div class="form-group">
							<label>帐面总值</label>
								<input type="text" id="txt_zmyz_begin" class="form-control number text-right" name="zmyz" types="NL" table="K"  placeholder="">
							<label>--</label>
								<input type="text" id="txt_zmyz_end" class="form-control number text-right" name="zmyz" types="NH" table="K"  placeholder="">
						</div>
						<div class="form-group">
							<label>处置价值</label>
								<input type="text" id="txt_czjz_begin" class="form-control number text-right" name="czjz" types="NL" table="K"  placeholder="">
							<label>--</label>
								<input type="text" id="txt_czjz_end" class="form-control number text-right" name="czjz" types="NH" table="K"  placeholder="">
						</div>
						<div class="form-group">
							<label>处置日期</label>
							<div class="input-group">
								<input type="text"  class="form-control date" name="czrq" types="TL" table="K" data-format="yyyy-MM-dd" placeholder="开始日期">
								<span class='input-group-addon'>
							    	<i class="glyphicon glyphicon-calendar"></i>
							   	</span>
						   	</div>
							<label>--</label>
							<div class="input-group">
								<input type="text"  class="form-control date" name="czrq" types="TH" table="K" data-format="yyyy-MM-dd" placeholder="截止日期">
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
									取 消
								</button>
							</div>
						</div>
					</div>
				</div>
				<div class="btn-group pull-right" role="group">
	            	<button type="button" class="btn btn-default" id="btn_exp">导出Excel</button>
				    <button type="button" id="btn_print" class="btn btn-default">打印预览</button>
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
					    	<th></th>
					        <th><input type="checkbox" class="select-all"/></th>
					        <th>序号</th>
					        <th>单据状态</th>
					        <th>处置单号</th>
					        <th>编报单位</th>
					        <th>处置方式</th>
					        <th>处置人</th>
					        <th>归口人员</th>
					        <th>账面总值</th>
					        <th>评估价值</th>
					        <th>处置价值</th>
					        <th>处置日期</th>
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
<script src="${pageContext.request.contextPath}/static/javascript/public/public-checked.js"></script>
<script>
$(function () {
	//联想输入提示
	$("#txt_czrxm").bindChange("${pageContext.request.contextPath}/suggest/getXx","R");
	$("#txt_dwmc").bindChange("${pageContext.request.contextPath}/suggest/getXx","D");
	//高级查询的展开与隐藏
    $(".btn_search_more").on("click", function(e){
		$(".btn_search_more").toggleClass("dropup");
		$("#searchBox .row:not(:first-child)").toggleClass("hide");
		$(".center-content").toggleClass("hide");
	});
   	//弹窗
   	$("#btn_czrxm").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/window/ryxx/window.jsp?controlId=txt_czrxm","人员信息","920","630");
    });
   	$("#btn_gkry").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/window/ryxx/window.jsp?controlId=txt_gkry","人员信息","920","630");
    });
   	$("#btn_dwmc").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/window/dwxx/window.jsp?controlId=txt_dwmc","单位信息","920","630");
    });
   	$("#txt_czrxm").bindChange("${pageContext.request.contextPath}/suggest/getXx","R");
   	$("#txt_gkry").bindChange("${pageContext.request.contextPath}/suggest/getXx","R");
   	$("#txt_dwmc").bindChange("${pageContext.request.contextPath}/suggest/getXx","D");
  //列表数据
   var columns = [
		{"data": "CZBGBH",orderable:false,"class":"details-control", "render": function (data, type, full, meta){
		   	return '<i class="fa" style="cursor: pointer;"></i>';
		},"width":10,'searchable': false},
		{"data": "CZBGBH",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="czbgbh" ztbz="'+full.ZT+'" value="' + data + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,"width":41,"searchable": false,"class":"text-center"},
	   	{"data": "ZTBZ",defaultContent:"","class":"text-red"},
	   	{"data": "CZBGBH",'render':function(data,type,full,meta){
	   		return '<span id="look" class="btn btn-link btn_look" rybh="'+(full.RYBH==undefined? '': full.RYBH)+'" path="${pageContext.request.contextPath}">'+ (data==undefined ? '':data) +'</span>';
	   		}
	   	},
		{"data": "DWMC",defaultContent:""},
	   	{"data": "HXZ",defaultContent:""},
	   	{"data": "CZRXM",'render':function(data,type,full,meta){
    		return '<span id="look" class="btn btn-link rylook" rybh="'+(full.RYBH==undefined? '': full.RYBH)+'" path="${pageContext.request.contextPath}">'+ (data==undefined ? '':data) +'</span>';
        }},
		{"data": "GKRY",'render':function(data,type,full,meta){
    		return '<span id="look" class="btn btn-link rylook" rybh="'+(full.GKRYBH==undefined? '': full.GKRYBH)+'" path="${pageContext.request.contextPath}">'+ (data==undefined ? '':data) +'</span>';
        }},
	   	{"data": "ZMYZ",defaultContent:"","class":"text-right"},
	   	{"data": "PGJZ",defaultContent:"","class":"text-right"},
	   	{"data": "CZJZ",defaultContent:"","class":"text-right"},
	   	{"data": "CZRQ",defaultContent:""},
	];
   	table = getDataTableByListHj("mydatatables","${pageContext.request.contextPath}/window/getZcczPageList?flag=${param.flag}&deskbz=${deskbz}",[4,'asc'],columns,"","",setGroup);
  //查看操作
   	$(document).on("click",".btn_look",function(){
   		var $this = $(this).parents("tr").find("[name='czbgbh']");
   		var czbgbh = $this.val();
   		var operate = "CK";
   		select_commonWin("${pageContext.request.contextPath}/czbgbHz/goEditPage?operateType=CK&czbgbh="+czbgbh,"处置单信息","1200","630");
//    		doOperate("${pageContext.request.contextPath}/yshd/goZcflPage?ysdh="+ysdh+"&flh="+flh+"&flmc="+flmc+"&czfl="+flgbm+"&mkbh="+mkbh+"&operate="+operate+"&ztbz="+ztbz,"L");
   		return false;
   	});
   	//导出Excel
   	$("#btn_exp").click(function(){
		var json = searchJson("searchBox");
		var checkbox = $("#mydatatables").find("[name='czbgbh']").filter(":checked");
		var id = [];
		checkbox.each(function(){
			id.push($(this).val());
		});
		doExp(json,"${pageContext.request.contextPath}/zcczcx/doExp?treedwbh=${dwbh}&flag=${param.flag}&deskbz=${deskbz}","资产处置信息","${pageContext.request.contextPath}",id.join(","));
	});
  //打印预览
	$("#btn_print").click(function(){
		var $checked = $("#mydatatables").find("[name='czbgbh']").filter(":checked");
		if($checked.length != 1){
			alert("请先选择一条数据进行打印！");
		}else{
			var id = $checked.val();
			window.open("${pageContext.request.contextPath}/czbgb/doPrintCzd?id="+id);
		}
		return false;
	});
  
    $('#mydatatables tbody').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = table.row( tr );
        var czbgbh = $(this).parents("tr").find("[name='czbgbh']").val();
        var ztbz = $(this).parents("tr").find("[name='czbgbh']").attr("ztbz");
        if ( row.child.isShown() ) {
            row.child.hide();
            tr.removeClass('shown');
        } else {
            $.ajax({
            	type:"post",
            	data:"czbgbh=" + czbgbh,
            	url:"${pageContext.request.contextPath}/czbgbHz/getTableList",
            	dataType:"json",
            	success:function(val){
                    row.child( format(val,ztbz,czbgbh) ).show();
                    tr.addClass('shown');
            	},
            	error:function(){
            		alert("数据请求错误！！！");
            	}
            });
        }
    } );
    function format ( val, ztbz, czbgbh ) {
    	var str;
		str='<table id="detailTable" class="table table-bordered table-striped">';
		str+='<thead><tr>';
		str+='<th>资产编号</th>';
		str+='<th>资产名称</th>';
		str+='<th>分类号</th>';
		str+='<th>分类名称</th>';
		str+='<th>总价</th>';
		str+='<th>入账日期</th>';
		str+='<th>使用人</th>';
		str+='<th>使用单位</th>';
		str+='<th>存放地点</th>';
		str+='<th>型号</th>';
		str+='<th>规格</th>';
		str+='</tr></thead>';
		str+='<tbody>';
		for(var i=0;i<val.length;i++){
			str+='<td>'+ val[i].yqbh +'</td>';
			str+='<td>'+ val[i].yqmc +'</td>';
			str+='<td>'+ val[i].flh +'</td>';
			str+='<td>'+ val[i].flmc +'</td>';
			str+='<td  style="text-align:right;">'+ val[i].zzj +'</td>';
			str+='<td>'+ val[i].rzrq +'</td>';
			str+='<td>'+ val[i].syrxm +'</td>';
			str+='<td>'+ val[i].sydwmc +'</td>';
			str+='<td>'+ val[i].cfdd +'</td>';
			str+='<td>'+ val[i].xh +'</td>';
			str+='<td>'+ val[i].gg +'</td></tr>';
		}
		str+='</tbody>';
		str+='</table>';
		return str;
    }
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
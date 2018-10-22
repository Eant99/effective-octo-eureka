<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产变动查询</title>
<%@include file="/static/include/public-list-css.inc"%>
<style>
.DTFC_RightBodyLiner{
	overflow-y: hidden !important;
}
</style>
</head>
<body>
<div class="fullscreen">
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" action="">
<!--         	<div class="row ztbz"> -->
<!--                 <div class="col-md-12 checkbox" table="B" types="C" name="ztbz"> -->
<!--                 	<span class="zt_label">单据状态：</span> -->
<!--                 	<a type="button" data-value="" class="btn btn-link btn-mark btn-mark-all active">全部<span class="label label-success">&radic;</span></a> -->
<!--                 	<a type="button" data-value="55" class="btn btn-link btn-mark active">未提交 <span class="badge wtj">0</span><span class="label label-success">&radic;</span></a> -->
<!--                 	<a type="button" data-value="00" class="btn btn-link btn-mark active">已提交 <span class="badge ytj">0</span><span class="label label-success">&radic;</span></a> -->
<!--                 	<a type="button" data-value="90" class="btn btn-link btn-mark active">归口审核通过<span class="badge gktg">0</span><span class="label label-success">&radic;</span></a> -->
<!--                 	<a type="button" data-value="10" class="btn btn-link btn-mark active">归口审核退回 <span class="badge gkth">0</span><span class="label label-success">&radic;</span></a> -->
<!--               		<a type="button" data-value="99" class="btn btn-link btn-mark active">财务审核通过 <span class="badge cwtg">0</span><span class="label label-success">&radic;</span></a> -->
<!--                 	<a type="button" data-value="91" class="btn btn-link btn-mark active">财务审核退回 <span class="badge cwth">0</span><span class="label label-success">&radic;</span></a> -->
<!--               	</div> -->
<!--             </div> -->
<!--             <hr class="hr-normal" style="margin: 5px 0"> -->
            <div class="search-simple">
				<div class="form-group">
					<label>变动单号</label>
					<input type="text" id="txt_bdbgbh" class="form-control" name="bdbgbh" table="B" placeholder="请输入变动单号">
				</div>
				<div class="form-group">
					<label>编报单位</label>
					<div class="input-group">
						<input type="text" id="txt_dwbh" class="form-control input-radius" name="dwbh" value="" types="D" table="B" placeholder="请选择编报单位">
						<span class="input-group-btn"><button type="button" id="btn_dwbh" class="btn btn-link ">选择</button></span>
					</div>
				</div>
				<div class="form-group">
					<label>变动日期</label>
					<div class="input-group">
						<input type="text" id="txt_bdrq_begin" class="form-control date" name="bdrq" types="TL" table="B" data-format="yyyy-MM-dd" placeholder="请输入开始日期">
						<span class='input-group-addon'>
					    	<i class="glyphicon glyphicon-calendar"></i>
					   	</span>
				   	</div>
					<label>--</label>
					<div class="input-group">
						<input type="text" id="txt_bdrq_end" class="form-control date" name="bdrq" types="TH" table="B" data-format="yyyy-MM-dd" placeholder="请输入截止日期">
						<span class='input-group-addon'>
					    	<i class="glyphicon glyphicon-calendar"></i>
				   		</span>
					</div>
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<button type="button" class="btn btn-default" id="btn_cancel">取 消 </button>
				<div class="btn-group pull-right" role="group">
                    <button id="btn_exp" type="button" class="btn btn-default" >导出Excel</button>
                    <button id="btn_print" type="button" class="btn btn-default" >打印预览</button>
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
				        <th>审核状态</th>
				        <th>变动状态</th>
				        <th>变动单号</th>
				        <th>编报单位</th>		
				        <th>变动人</th>
				        <th>变动日期</th>
				        <th>变动原因</th>				        
				        <th>操作</th>
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
<script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script>
<script>
$(function () {
	//弹窗
   	$("#btn_dwbh").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/window/dwxx/window.jsp?controlId=txt_dwbh","单位信息","920","630");
    });
   	//联想输入
	$("#txt_dwbh").bindChange("${pageContext.request.contextPath}/suggest/getXx","D");
	//列表数据
    var columns = [
		{"data": "BDBGBH",orderable:false,"class":"details-control", "render": function (data, type, full, meta){
		   	return '<i class="fa" style="cursor: pointer;"></i>';
		},"width":10,'searchable': false},
		{"data": "BDBGBH",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="bdbgbh" ztbz="'+full.ZT+'" djbz="'+full.BDZT+'" value="' + data + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
		{"data": "ZTBZ",defaultContent:"","class":"text-red"},
		{"data": "DJBZ",defaultContent:""},
	   	{"data": "BDBGBH",defaultContent:""},
		{"data": "DWBH",defaultContent:""},
	   	{"data": "RYBH",'render':function(data,type,full,meta){
    		return '<a href="javascript:void(0);" id="look" class="btn btn-link rylook" rybh="'+(full.RY==undefined? '': full.RY)+'" path="${pageContext.request.contextPath}">'+ (data==undefined ? '':data) +'</a>';
        }},
	   	{"data": "BDRQ",defaultContent:""},
	 	{"data": "BDYY",defaultContent:""},
	   	{"data":"ZT","render":function (data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_slook" bdbgbh="'+full.BDBGBH+'" djbz="'+full.BDZT+'" ztbz="'+data+'">查看</a>';
		},orderable:false}
	];
   	table = getDataTableByListHj("mydatatables","${pageContext.request.contextPath}/window/getZcbdPageList?flag=${param.flag}&deskbz=${deskbz}",[5,'asc'],columns,0,1,setGroup);

   	//查看标签
   	$(document).on("click",".btn_slook",function(){
	   	var bdbgbh = $(this).attr("bdbgbh");
	   	var djbz = $(this).attr("djbz");
	   	var ztbz = $(this).attr("ztbz");
	   	var mkbh = "${param.mkbh}";
	   	var flag = "${param.flag}";
// 	   	select_commonWin("${pageContext.request.contextPath}/zcbdcx/goEditPage?operateType=L&bdbgbh="+bdbgbh+"&mkbh="+mkbh+"&djbz="+djbz+"&ztbz="+ztbz+"&operate=CX","变动单信息","1200","630");
	   doOperate("${pageContext.request.contextPath}/zcbdcx/goEditPage?bdbgbh="+bdbgbh+"&mkbh="+mkbh+"&djbz="+djbz+"&ztbz="+ztbz+"&operate=DESK&flag="+flag,"L");
	   	return false;
   	});
  //打印预览
	$("#btn_print").click(function(){
		var $checked = $("#mydatatables").find(".keyId").filter(":checked");
		if($checked.length != 1){
			alert("请先选择一条数据进行打印！");
		}else{
			var bdbgbh = $checked.val();
			window.open("${pageContext.request.contextPath}/bdbgb/doPrint?bdbgbh="+bdbgbh);
		}
		return false;
	});
  //导出Excel
   	$("#btn_exp").click(function(){
		var json = searchJson("searchBox");
		var checkbox = $("#mydatatables").find("[name='bdbgbh']").filter(":checked");
		var id = [];
		checkbox.each(function(){
			id.push($(this).val());
		});
		doExp(json,"${pageContext.request.contextPath}/zcbdcx/doExp?treedwbh=${dwbh}&flag=${param.flag}&deskbz=${deskbz}","资产变动信息","${pageContext.request.contextPath}",id.join(","));
	});
  
  	//下拉：列表项数据
    $('#mydatatables tbody').on('click', 'td.details-control', function (e) {
        var tr = $(this).closest('tr');
        var row = table.row( tr );
        var bdbgbh = $(this).parents("tr").find("[name='bdbgbh']").val();
        var ztbz = $(this).parents("tr").find("[name='bdbgbh']").attr("ztbz");
        if ( row.child.isShown() ) {
            row.child.hide();
            tr.removeClass('shown');
        } else {
            $.ajax({
            	type:"post",
            	data:"bdbgbh=" + bdbgbh,
            	url:"${pageContext.request.contextPath}/bdbgb/doShowTable",
            	dataType:"json",
            	success:function(val){
                    row.child( format(val,ztbz) ).show();
                    tr.addClass('shown');
            	},
            	error:function(){
            		alert("数据请求错误！");
            	}
            });
        }
        e.stopPropagation();
    });
    function format ( val ,ztbz) {
    	var str;
		str='<table id="detailTable" class="table table-bordered table-striped">';
		str+='<thead><tr>';
		str+='<th>资产编号</th>';
		str+='<th>资产名称</th>';
		str+='<th>型号</th>';
		str+='<th>规格</th>';
		str+='<th>总价</th>';
		str+='<th>记账日期</th>';
		str+='<th>使用人</th>';
		str+='<th>使用单位</th>';
		str+='<th>存放地点</th>';
		str+='</tr></thead>';
		str+='<tbody>';
		for(var i=0;i<val.length;i++)
		{
			str+='<td>'+ val[i].fjbh +'</td>';
			str+='<td>'+ val[i].yqmc +'</td>';
			str+='<td>'+ val[i].xh +'</td>';
			str+='<td>'+ val[i].gg +'</td>';
			str+='<td class="text-right">'+ val[i].zzjs +'</td>';
			str+='<td>'+ val[i].rzrq +'</td>';
			str+='<td>'+ val[i].syrxm +'</td>';
			str+='<td>'+ val[i].sydwmc +'</td>';
			str+='<td>'+ val[i].cfdd +'</td></tr>';
		}
		str+='</tbody>';
		str+='</table>';
		return str;
    }
	 //组织冒泡事件
    $(document).on('mouseover','.detailTr',function(e){
    	e.stopPropagation();
    });
    //组织冒泡事件
    $(document).on('mouseout','.detailTr',function(e){
    	e.stopPropagation();
    });
    //综合查询
    $("#btn_search").on("click",function(){
		var json = searchJson("searchBox");
    	$('#mydatatables').DataTable().search(json,"json").draw();
    });
    $("div.DTFC_RightWrapper").css("display","none");
 	$(document).on('ifChecked',":checkbox[name='icheck']",function(event){
   		table.column( $(this).attr('data-column') ).visible(true);
   		if($("#mydatatables").width() > $("#searchBox").width()){
   			$("div.DTFC_RightWrapper").hide();
   		}
   	});
   	$(document).on('ifUnchecked',":checkbox[name='icheck']",function(event){
   		table.column( $(this).attr('data-column') ).visible(false);
   		if($("#mydatatables").width() <= $("#searchBox").width()){
   			$("div.DTFC_RightWrapper").hide();
   		}
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
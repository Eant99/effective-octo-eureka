<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产分类（全部）</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
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
		        <th>数量</th>
		        <th>单价</th>
		        <th>总价</th>
		        <th>现状</th>
		    </tr>
    	</thead>
	    <tbody>
	    </tbody>
		</table>
        </div>
	</div>
<%@include file="/static/include/public-list-js.inc"%>	
<script>
$(function (){
	//列表数据
   	var columns = [
		{"data": "YSDH",orderable:false, "render": function (data, type, full, meta){
           return '<input type="checkbox" class="keyId" name="id" value="' + data + '" jzrlx="'+full.JZRLX+'" flh = "'+full.FLH+'" flmc = "'+full.YQBH+'" flgbm = "'+full.FLGBM+'" zt = "'+full.ZTBZ+'">';
      	},"width":20,'searchable': false},
	   	{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   	   return data;
   		},"width":41,"searchable": false,"class":"text-center"},
	   	{"data": "ZTBZ","render":function(data, type, full, meta){
		   if(data == '15'&&full.JZRLX!='1'){
			   return "<span style='color: red'>领用人</span><span>提交</span>";
		   }else if(full.ZTBZ == '15'&&full.JZRLX=='1'){
			   return "<span style='color: red'>管理员</span><span>提交</span>";
		   }else if(data == '00'){
			   return "领用人提交";
		   }else if(data == '20'||data =='25'){
			   return "<span style='color: #8BC34A'>"+full.ZTBZMC+"</span>";
		   }else if(data == '99'||data =='91'){
			   return "<span style='color: #00acec'>"+full.ZTBZMC+"</span>";
		   }else{
			   return full.ZTBZMC;
		   }
	   },defaultContent:""},
	   {"data": "YSDH",defaultContent:""},
	   {"data": "FLH",defaultContent:""},
	   {"data": "YQBH",defaultContent:""},
	   {"data": "YQMC",defaultContent:""},
	   {"data": "SHL",defaultContent:"","class":"text-right"},
	   {"data": "DJ",defaultContent:"","class":"text-right"},
	   {"data": "ZZJ",defaultContent:"","class":"text-right"},
	   {"data": "XZ",defaultContent:""}
	];
   	var length = getTopFrame().window.sessionRowNumLength;
   	table = $('#mydatatables').DataTable({
        "ajax": {
            url:"${pageContext.request.contextPath}/yshd/getPageList"//获取数据的方法
        },
        "lengthMenu": length,//每页显示条数设置
        "order": [3,'asc'],//排序列
        "columns": columns,//列定义
        "scrollX": true,
        "scrollY": true,
        "dom":"t<'row bottom'<'col-md-5 col-sm-5 col-xs-5'i><'col-md-7 col-sm-7 col-xs-7 pull-right'p>>",
        "drawCallback":function(){
        	setGroup();
           	$('body').jNice();
        }
   	});
});
</script>	
</body>
</html>
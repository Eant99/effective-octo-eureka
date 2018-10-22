<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>固定资产管理系统地点信息</title>
<%@include file="/static/include/public-list-css.inc"%>
<%@include file="/static/include/public-list-js.inc"%>
<style type="text/css">
	.box{
		width:170%;
	}
	.alert{
		width:58%;
	}
</style>
</head>
<body>
<div id='wrapper'>
<section>
    <div class='row' id='content-wrapper'>
        <div class='col-md-12'>
             <div class="box">
                <div class='box-content' style="padding-bottom: 0;">
                	<div class="alert alert-info " >
			          	<strong>提示：</strong>当资产为管理权限下设备类资产，<strong>双击</strong>某条即可添加该条资产，点击<strong>选择</strong>按钮可添加多条资产。
			        </div>
			        <button class="btn btn-default" type="button" id="btn_save" style="float: left;margin-left:615px;margin-top: -30px;">
			        	<i class="fa icon-add"></i>
			        	选择
					</button>
					<hr class="hr-normal" id="hr">
                     <div class='responsive-table'>
                     <div class='scrollable-area'>
                     <table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					     <thead>
						     <tr id="header">
						         <th><input type="checkbox" value="" class="select-all"/></th>
						         <th>序号</th>
					        	 <th>资产编号</th>
					        	 <th>资产名称</th>
<!-- 					        	 <th>分类名称</th> -->
					        	 <th>使用单位</th>
					        	 <th>使用人</th>
					        	 <th>存放地点</th>
					        	 <th>总价</th>
					        	 <th>规格</th>
					        	 <th>型号</th>
						         <th>使用方向</th>
						         <th>购置日期</th>
						         <th>保修截止日期</th>
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
</section>
</div>
<script>
$(function (){
	//列表数据
 	$('#mydatatables').DataTable({
        "ajax": {
            url:"${pageContext.request.contextPath}/window/wxzcxx?dwbh=${param.dwbh}"//获取数据的方法
        },
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [3,'asc'],
        "serverSide": true,
        "columns":[
        	{"data": "YQBH",orderable:false, "render": function (data, type, full, meta){
            	return '<input type="checkbox" class="keyId" name="ddbh" value="' + data + '" yqbh="'+full.YQBH + '" yqmc="'+full.YQMC+'" flh="'+full.FLH+'" flmc="'+full.FLMC+'" xz="'+full.XZ+'" gzrq="'+full.GZRQ+'" syr="'+full.SYR+'" sydw="'+full.SYDW+'" bzxx="'+full.BZXX+'" dj="'+full.DJ+'" zzj = "'+full.ZZJ+'" gg = "'+full.GG+'" xh = "'+full.XH+'" bxjzrq = "'+full.BXJZRQ+'">';//  	
        	},"width":10,'searchable': false},
        	{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
            	return data;
        	},"width":41,"searchable": false,"class":"text-center"},
        	{"data": "YQBH",'render':function (data, type, full, meta){
    	   		return '<span class="btn btn-link zclook" yqbh="'+(full.YQBH==undefined? '': full.YQBH)+'" path="${pageContext.request.contextPath}">'+ (data==undefined ? '':data) +'</span>';
    		}},
        	{"data": "YQMC",defaultContent:""},
//         	{"data": "FLMC",defaultContent:""},
        	{"data": "SYDW",defaultContent:""},
        	{"data": "SYR",defaultContent:""},
        	{"data": "BZXX",defaultContent:""},
        	{"data": "ZZJ",defaultContent:"","class":"text-right",'render': function (data, type, full, meta){
            	return data==null?"":parseFloat(data).toFixed(2);
        	},"width":80,"searchable": false},
        	{"data": "GG",defaultContent:""},
        	{"data": "XH",defaultContent:""},
        	{"data": "SYFX",defaultContent:""},
        	{"data": "GZRQ",defaultContent:""},
        	{"data": "BXJZRQ",defaultContent:""}
        ],
        "language":{
            "search":"", 
            "searchPlaceholder":"请输入资产信息关键字"
        },
        "dom":"<'row'<'col-md-4 col-sm-4 col-xs-4'li><'col-md-3 col-sm-3 col-xs-3'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
	});
 	$("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
    //单击事件
    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	var id ="${param.reportid}";
    	var pname = "${param.pname}";
    	var yqbh = $(this).find("[name='ddbh']").attr("yqbh"); 
	        $.ajax({  
		       	type:"post",
		       	data:"yqbh="+yqbh+"&reportid="+id,
		       	url:"${pageContext.request.contextPath}/wxsq/dozcedit",
		       	dataType:"json",
		       	success:function(val){
		       		var winId = top.layer.getFrameIndex(parent.window.name);
		       		var pWindow = getIframWindow(pname);
		       		pWindow.location.href = pWindow.location.href;
		       		close(winId);
		       	},
		       	error:function(){
		       		alert("数据请求错误！");
		       	}
	        });
    });
	$("#btn_save").click(function(e){
		var id ="${param.reportid}";
    	var pname = "${param.pname}";
   		var checkbox = $("#mydatatables").find("[name='ddbh']").filter(":checked");
   		if(checkbox.length>0){ 
   			var yqbh = [];
   			checkbox.each(function(){
   				yqbh.push($(this).val());
   			});
	        $.ajax({  
		       	type:"post",
		       	data:"yqbh="+yqbh.join(",")+"&reportid="+id,
		       	url:"${pageContext.request.contextPath}/wxsq/dozcedit",
		       	dataType:"json",
		       	success:function(val){
		       		var winId = top.layer.getFrameIndex(parent.window.name);
		       		var pWindow = getIframWindow(pname);
		       		pWindow.location.href = pWindow.location.href;
		       		close(winId);
		       	},
		       	error:function(){
		       		alert("数据请求错误！");
		       	}
	        });
   		}else{
   			alert("请选择至少一条资产信息！");
   		}
	});
});
</script>
</body>
</html>
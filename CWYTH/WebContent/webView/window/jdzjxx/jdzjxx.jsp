<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>设备维修鉴定专家信息</title>
<%@include file="/static/include/public-list-css.inc"%>
<%@include file="/static/include/public-list-js.inc"%>
</head>
<style type="text/css">
#mydataTables_filter input[type=search]{
	display:inline-block;
	width:auto;
}
body{
	overflow-x:hidden;
}
.zhxxcx{
margin-top:8px;
}
</style>
<body class="contrast-red">
<div id='wrapper'>
<section>
	<div class='row' id='content-wrapper'>
		<div class='col-md-12'>
			<div class="box">
				<div class='box-content' style="padding-bottom: 0; overflow:visible;">
				<div class="alert alert-info">
		          	<strong>提示：</strong>先找到需要的人员，然后<strong>双击</strong>这条信息
		        </div>
                <div class='responsive-table'>
                    <div class='scrollable-area'>
                    <table id="mydataTables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					      <thead>
					      <tr id="header">
					          <th><input type="checkbox" value="" class="select-all"/></th>
					          <th>人员工号</th>
					          <th>姓名</th>
					          <th>性别</th>
					          <th>所在单位</th>
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
$(function(){
    table = $('#mydataTables').DataTable({
    	 ajax: {
             url: "${pageContext.request.contextPath}/window/ryxx?dwbh=${param.dwbh}"//获取数据的方法
         },
         "pagingType":"full_numbers",
         "lengthMenu":[10],
         "order": [ 1, 'asc' ],
         "serverSide": true,
         "columns":[
             {"data":"RYBH",orderable:false,'render':function (data, type,full,meta){
            	    return "<input type='checkbox' class='keyId' name='rygh' xm='"+full.XM+"' rygh='"+full.RYGH+"' csrq='"+full.CSRQ+"' xb='"+full.XBDM+"' zyzc='"+full.ZYZCDM+"'  dwmc='"+full.DWMC+"'   value='"+data+"'>";	
             },"width":10,'searchable':false},
             {"data": "RYGH",defaultContent:""},
             {"data": "XM",defaultContent:""},
             {"data": "XB",defaultContent:""},
             {"data": "DWMC",defaultContent:"",orderable:false},
                    ],
        "language":{
            "lengthMenu": "_MENU_ 条记录每页",
            "zeroRecords": "没有找到记录",
            "info": "第 _PAGE_页 ( 总共 _PAGES_页 )",
            "infoEmpty": "无记录",
            "infoFiltered": "(从 _MAX_条记录过滤)",
            "paginate": {
                "previous": "上一页",
                "next": "下一页",
                "first": "首页",
                "last": "末页",
                "jump":"跳转"
            },
            "search":"",
            "searchPlaceholder":"请输入人员信息关键字"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'il><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
    }); 
    $("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun' style='font-size:14px'></i>").css("width","175");
	$("div.button").prop("innerHTML","人员信息列表").css("font-size","14px");
    //单击事件
    $(document).on("dblclick","#mydataTables tr:not(#header)",function(){
    	var val = $(this).find("[name='rygh']").val();//取值
    	if(val==''||val==null||val=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		var xm=$(this).find("[name='rygh']").attr("xm");//获取姓名
        	var csrq=$(this).find("[name='rygh']").attr("csrq");//获取出生日期
        	if(csrq =="null" || csrq=="undefined"){
        		csrq="";
        	}
        	var xb=$(this).find("[name='rygh']").attr("xb");//性别
        	var zyzc=$(this).find("[name='rygh']").attr("zyzc");//专业职称
        	var dwmc=$(this).find("[name='rygh']").attr("dwmc");
        	var rygh=$(this).find("[name='rygh']").attr("rygh");
          	getIframeControl("${param.pname}","${param.controlId}").val(rygh);//传值
          	getIframeControl("${param.pname}","txt_xm").val(xm);  //传姓名值
          	getIframeControl("${param.pname}","txt_csrq").val(csrq);//传出生日期
          	getIframeControl("${param.pname}","drp_xb").val(xb);//传性别
          	getIframeControl("${param.pname}","txt_zhicheng").val(zyzc);//专业职称
          	getIframeControl("${param.pname}","txt_school").val(dwmc);
        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
        	var winId = top.layer.getFrameIndex(parent.window.name);
        	close(winId);
    	}
    });
});
</script>
</body>
</html>
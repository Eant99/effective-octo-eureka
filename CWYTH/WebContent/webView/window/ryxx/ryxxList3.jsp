<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>固定资产管理系统人员信息</title>
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

</style>
<body>
<div id='wrapper'>
<section>
	<div class='row' id='content-wrapper'>
		<div class='col-md-12'>
			<div class="box">
				<div class='box-content' style="padding-bottom: 0; overflow:visible;">
				<div class="alert alert-info">
		          	<strong>提示：</strong>先找到需要的人员，然后<strong>双击</strong>这条信息，或先选中需要的人员，然后点击<strong>确定</strong>按钮。
		        </div>
		        <button style="margin-top:5px;" type="button" class="btn btn-primary" id="btn_qd">确定</button>
		        <hr class="hr-normal" id="hr">
                <div class='responsive-table'>
                    <div class='scrollable-area'>
                    <table id="mydataTables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					      <thead>
					      <tr id="header">
					          <th><input type="checkbox" value="" class="select-all"/></th>
					          <th>序号</th>
					          <th>人员工号</th>
					          <th>姓名</th>
					          <th>性别</th>
					          <th>所在单位</th>
					          <th>排序序号</th>
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
    table = $('#mydataTables').DataTable({
        ajax: {
            url: "${pageContext.request.contextPath}/window/ryxx?treesearch=${param.treesearch}&dwbh=${param.dwbh}&flag=${param.flag}"//获取数据的方法
        },
        "pagingType":"full_numbers",
        "lengthMenu":getTopFrame().window.sessionRowNumLength,
        "order": [ 6, 'asc' ],
        "serverSide": true,
        "columns": [
            {"data": "RYGH",orderable:false, 'render': function (data, type, full, meta){
	             return "<input type='checkbox' class='keyId' guid='"+full.GUID+"' name='rygh' rybh='"+full.RYBH+"' dwmc='"+full.DWMC+"' lxdh='"+(full.LXDH==undefined?'':full.LXDH)+"' ryxm='"+full.XM +"' value='" + "("+full.RYBH+")"+full.XM + "'>";
		    },"width":10,'searchable': false
		    },
		    {"data":"_XH",orderable:false,'render': function (data, type, full, meta){
            	return data;
            },"width":41,"searchable": false,"class":"text-center"},
            {"data": "RYGH",defaultContent:""},
            {"data": "XM",defaultContent:""},
            {"data": "XB",defaultContent:""},
            {"data": "DWMC",defaultContent:"",orderable:false},
            {"data": "PXXH",defaultContent:""}
        ],
        "language":{
            "search":"",
            "searchPlaceholder":"请输入人员工号或姓名"
        },
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
    });
    $("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
	$("div.button").prop("innerHTML","人员信息列表").css("font-size","14px");
	var model="${param.model}";
	if(model ="xzcxxm"){
		$(document).on("dblclick","#mydataTables tr:not(#header)",function(){
        	var val = $(this).find("[name='rygh']").val();
        	var winId = getTopFrame().layer.getFrameIndex(parent.window.name);
        	if(val==''||val==null||val=='undefined'){
        		alert("没有可以选择的数据！");
        	}else{
        		getIframeControl("${param.pname}","${param.controlId}").val(val);
            	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
            	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
            	close(winId);
        	}
        });
	}else{
	    //单击事件
	    $(document).on("dblclick","#mydataTables tr:not(#header)",function(){
	    	var guid = $(this).find("[name='rygh']").attr("guid");
	    	var dwmc = $(this).find("[name='rygh']").attr("dwmc");
	    	var ry = $(this).find("[name='rygh']").val();
	    	var gh = $(this).find("[name='rygh']").attr("rygh");
	    	var rydom = $(this).find("[name='rygh']");
	    	if(guid==''||guid==null||guid=='undefined'){
	    		alert("没有可以选择的数据！");
	    	}else{
	    		getIframeControl("${param.pname}","${param.controlId}").val("").val(ry);
	    		if("${param.szbmm}" !=''){
	    			getIframeControl("${param.pname}","${param.szbmm}").val("").val(dwmc);
	    		}
	    		if("${param.ryid}" !=''){
	    			getIframeControl("${param.pname}","${param.ryid}").val("").val(guid);
	    		}
	    		if(typeof getIframWindow("${param.pname}").ryWindowCallBack == "function"){//岗位交接时获取该人员的角色信息
	    			getIframWindow("${param.pname}").ryWindowCallBack("${param.controlId}",rydom);
	    		}
	        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
	        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
	        	var winId;
	        	if(window.name == "iframe_list_ryxx"){//iframe_list_ryxx是window.jsp里写死的，所以只要相等一定是有左侧树的
	        		winId = top.layer.getFrameIndex(parent.window.name);
	        	}else{//这种是不带左侧树的
	        		winId = top.layer.getFrameIndex(window.name);
	        	}
	        	close(winId);
	    	}
	    });
	}
    $(document).on("click","#btn_qd",function(){
    	var checkbox=$(".keyId").filter(":checked");
    	if(checkbox.length<1){
    		alert("请先选择一条信息！");
    	}else if(checkbox.length>1){
    		alert("只能选择一条信息！");
    	}else{
    		var guid=$("#mydataTables").find(".keyId").filter(":checked").attr("guid");
    		var dwmc=$("#mydataTables").find(".keyId").filter(":checked").attr("dwmc");
    		var ry=$("#mydataTables").find(".keyId").filter(":checked").val();
    		
    		getIframeControl("${param.pname}","${param.controlId}").val(ry);
    		if("${param.szbmm}" !=''){
    			getIframeControl("${param.pname}","${param.szbmm}").val(dwmc);
    		}
    		if("${param.ryid}" !=''){
    			getIframeControl("${param.pname}","${param.ryid}").val(guid);
    		}
    		if(typeof getIframWindow("${param.pname}").ryWindowCallBack == "function"){//岗位交接时获取该人员的角色信息
    			getIframWindow("${param.pname}").ryWindowCallBack("${param.controlId}",rydom);
    		}
        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
        	var winId;
        	if(window.name == "iframe_list_ryxx"){//iframe_list_ryxx是window.jsp里写死的，所以只要相等一定是有左侧树的
        		winId = top.layer.getFrameIndex(parent.window.name);
        	}else{//这种是不带左侧树的
        		winId = top.layer.getFrameIndex(window.name);
        	}
        	close(winId);
    	}
    })
});
</script>
</body>
</html>
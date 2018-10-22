<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>会计科目设置</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	select{
		font-size:12px !important; 
		max-height: 25px !important;  
		padding: 0px !important;
		padding-left: 5px !important;
	}
</style>
</head>
<body>
<div class="fullscreen">
<section>
	<div class="container-fluid">
		<div class="alert alert-info">
          	<strong>提示：</strong>先找到需要收支结余的科目，然后<strong>双击</strong>这条信息。
          	<c:if test="${param.flag == 'zhcx' && param.controlId=='km'}">
          	<button type="button" class="btn btn-primary pull-right "  style="margin-top: -5px" id="btn_qd" >
          	<i class="fa icon-chaxun "></i>确定</button>
          	</c:if>
        </div>
        <hr class="hr-normal" id="hr">
		<div class='responsive-table'>
			<div class='scrollable-area'>
		        <table id="mydatatables" class="table table-striped table-bordered">
					<thead>
		 				<tr>
			 				<th><input type="checkbox" class="select-all"/></th>
			 				<th>序号</th>
						    <th>科目编号</th>
						    <th>科目名称</th>
						    <th>科目属性</th>
<!-- 						    <th>助记符</th> -->
<!-- 						    <th>余额方向</th> -->
<!-- 						    <th>核算类别</th> -->
<!-- 						    <th>启用否</th> -->
			   			</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
	</div>
	</section>
</div>
 <%@include file="/static/include/public-list-js.inc"%>  
<script >
$(function(){
	var winId = getTopFrame().layer.getFrameIndex(parent.window.name);
	 table = $('#mydatatables').DataTable({
	        ajax: {
	            url: "${pageContext.request.contextPath}/jzmb/getkjkmPageList?treedwbh=${dwbh}&kmnd=${kmnd}&dm=${dm}&jb=${jb}"//获取数据的方法
	        },
	        "pagingType":"full_numbers",
	        "lengthMenu":getTopFrame().window.sessionRowNumLength,
	        "order": [ 2, 'asc' ],
	        "serverSide": true,
	        "columns": [
	        	{"data": "guid",orderable:false, "render": function (data, type, full, meta){
	       	       	return '<input type="checkbox" name="guid"  class="keyId" bhmc="' + "("+full.KMBH+")"+full.KMMC + '" bh="'+full.KMBH+'"  value="' + full.GUID + '">';
	       	    },"width":10,'searchable': false},
	       		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	       	   		return data;
	       		},"width":41,"searchable": false,"class":"text-center"},
	       	   	{"data": "KMBH",defaultContent:""},
	       	   	{"data": "KMMC",defaultContent:"","class":"text-left"},
	       	   	{"data": "KMSXMC",defaultContent:""},
	    /*    	   	{"data": "ZJF",defaultContent:""},
	       	   	{"data": "YEFXMC",defaultContent:""},
	       	   	{"data": "HSLBMC",defaultContent:""},
	       	   	{"data": "QYFMC",defaultContent:""} */
	            
	        ],
	        "language":{
	            "search":"",
	            "searchPlaceholder":"请输入科目编号或名称"
	        },
	        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
	    });
	
	 $("input[type=search]").parent("label").addClass("zhxxcx");
		$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
		$("div.button").prop("innerHTML","科目信息列表").css("font-size","14px");
	
	
	
// 	var columns = [
// 		{"data": "guid",orderable:false, "render": function (data, type, full, meta){
//    	       	return '<input type="checkbox" name="guid"  class="keyId" bhmc="' + "("+full.KMBH+")"+full.KMMC + '"  value="' + full.GUID + '">';
//    	    },"width":10,'searchable': false},
//    		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
//    	   		return data;
//    		},"width":41,"searchable": false,"class":"text-center"},
//    	   	{"data": "KMBH",defaultContent:""},
//    	   	{"data": "KMMC",defaultContent:"","class":"text-center"},
//    	   	{"data": "KMSXMC",defaultContent:""},
//    	   	{"data": "ZJF",defaultContent:""},
//    	   	{"data": "YEFXMC",defaultContent:""},
//    	   	{"data": "HSLBMC",defaultContent:""},
//    	   	{"data": "QYFMC",defaultContent:""},
// 	];
	
//     table = getDataTableByListHj("mydatatables","${ctx}/jzmb/getkjkmPageList?treedwbh=${dwbh}&kmnd=${kmnd}&dm=${dm}&jb=${jb}",[3,'asc'],columns,0,1,setGroup);
   //table = getDataTableByListHj("mydatatables","${ctx}/json/kjhs/kmsz/kjkmsz/kjkmsz_list.json",[2,'asc'],columns,0,1,setGroup);

  //双击事件
    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	var bhmc = $(this).find("[name=guid]").attr("bhmc");
    	var bh = $(this).find("[name=guid]").attr("bh");
    	var val = $(this).find("[name=guid]").val();
    	if(val==''||val==null||val=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		getIframeControl("${param.pname}","${param.controlId}").val(val);
    		getIframeControl("${param.pname}","${param.controlId1}").val(bhmc);

        	if("${param.flag}"=="zhcx"){
        		getIframeControl("${param.pname}","${param.controlId1}").val(bh);
        	}
        	
        	getIframeControl("${param.pname}","${param.controlId1}").focus();//手动触发验证
        	//getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
        	console.log("${param.type}");
        	if("${param.type}"=="zr"){
        		getIframWindow("${param.pname}").addkmmc(bhmc);
        	}
        	close(winId);
    	}
    });
    //多选
    	 $("#btn_qd").click(function(){
    	var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
    	 if(checkbox.length>0){
    		 var bh = [];
    		 checkbox.each(function(){
                 bh.push($(this).attr("bh"));
             });

    		getIframeControl("${param.pname}","${param.controlId1}").val(bh.join(","));
//      		getIframeControl("${param.pname}","${param.controlId1}").val(bh.join("','"));
    		 
     		close(winId);
    	 }else{
    		 alert("请选择至少一条信息！"); 
    	 }
    });
    if("${param.windowModel}" == "1"){
	   	$("div.button").prop("innerHTML","<button type='button' id='btn_sure' class='btn btn-primary' style='margin-right:20px;'>确定</button><button type='button' id='btn_cancel' class='btn btn-primary'>取消</button>");
	}else{
    	$("div.button").prop("innerHTML","单位信息列表").css("font-size","14px");
    }
	
});

</script>
</body>
</html>
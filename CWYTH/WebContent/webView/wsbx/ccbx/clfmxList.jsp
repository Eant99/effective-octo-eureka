<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>差旅费明细</title>
<%@include file="/static/include/public-list-css.inc"%>
<%@include file="/static/include/public-list-js.inc"%>
</head>
<style type="text/css">
	#example_filter input[type=search]{
		display:inline-block;
		width:auto;
	}
	body{
		overflow-x:hidden;
	}
	.go-back{
		display:none;
	}
	.odd{
		height: 35px;
	}
	.icon-chaxun {
	    top: 12px!important;
	}
</style>
<body class="contrast-red">
<div id='wrapper'>
<section>
	<div class='row' id='content-wrapper'>	
		<div class='col-md-12'>
			<div class="box">
				<div class='box-content' style="padding-bottom: 0; overflow:visible;">
				<div class="alert alert-info" style="padding: 6px;margin-bottom: 4px;float: left;">
			         <strong>提示：</strong>先找到需要的信息，然后<strong>双击</strong>这条信息
			     </div>
					<div class="clearfix">
						<div class="btn-group pull-right" role="group">
			           		<!-- <button class="btn btn-default" id="btn_add">增加</button> -->
			            </div>
					</div>
					<hr class="hr-normal" />
	                <div class='responsive-table'>
	                    <div class='scrollable-area'>
		                    <table id="example" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
							      <thead>
							      <tr id="header">
							          <th><input type="checkbox" value="" class="select-all"/></th>
<!-- 							          <th>课题名称</th> -->
							          <th>开始时间</th>
							          <th>结束时间</th>
							          <th>出发地点</th>
							          <th>目的地点</th>
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
	var pname = "${param.pname}";
    table = $('#example').DataTable({
        ajax: {
            url: "${ctx}/wsbx/ccbx/getMXPageListByLoginXm"//获取数据的方法
        },
        "pagingType":"full_numbers",
        "lengthMenu":[10],
        "order": [ 1, 'asc' ],
        "serverSide": true,
        "scrollXOption": true,
        "scrollYOption": true,
        "columns": [
            {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
		             return "<input type='checkbox' class='keyId' name='guid' value='"+data+"' kssj='"+full.KSSJ+"' jssj='"+full.JSSJ+"'   cfdd='"+full.CFDD+"' mddd='"+full.MDDD+"'>";
		    },"width":10,'searchable': false},
            {"data": "KSSJ",defaultContent:""},
            {"data": "JSSJ",defaultContent:""},
            {"data": "MDDD",defaultContent:""},
            {"data": "CFDD",defaultContent:""}
        ],
        "language":{
            "lengthMenu": "每页_MENU_ 条记录",
            "zeroRecords": "没有找到记录",
            "info": "第_PAGE_页/共_PAGES_页(总_MAX_条记录)",
            "infoEmpty": "无记录",
            "infoFiltered": "(从 _MAX_ 条记录过滤)",
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
        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'il><'col-md-5 col-sm-5 col-xs-5'>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
    });
	$("input[type=search]").parent("label").addClass("zhxxcx");
	$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
	$(".icon-chaxun").css("position","absolute");
    //双击事件
    $(document).on("dblclick","#example tr:not(#header)",function(){
    	var kssj = $(this).find("[name='guid']").attr("kssj");
    	var jssj = $(this).find("[name='guid']").attr("jssj");
    	var cfdd = $(this).find("[name='guid']").attr("cfdd");
    	var mddd = $(this).find("[name='guid']").attr("mddd");
    	var guid = $(this).find("[name='guid']").val();
    	$.ajax({
   			url:"${ctx}/wsbx/ccbx/checkXzxmguid?zbguid="+"${param.zbguid}",
   			type:"post",
   			data:"xzxmguid="+guid,
   			success:function(data){
   			if(data=='success'){
   				getIframWindow("${param.pname}").checkGuid(guid);
   			
   		
   					getIframeControl("${param.pname}","${param.kssj}").val(kssj);
   	   		    	getIframeControl("${param.pname}","${param.jssj}").val(jssj);
   	   		    	getIframeControl("${param.pname}","${param.cfdd}").val(cfdd);
   	   		    	getIframeControl("${param.pname}","${param.mddd}").val(mddd);
   	   		    	getIframeControl("${param.pname}","${param.xzxmguid}").val(guid);
   	   		    	var winId = getTopFrame().layer.getFrameIndex(window.name);
   	   		    	//alert(val2);
   	   		    	close(winId);	
   			}else{
   				alert("此信息已经选择过！");
   				return;
   			}
   				
   			}
		}); 
    	
    	
    });
  //添加按钮：角色信息
	$("#btn_add").click(function() {
		var checkbox = $("#example").find("[name='rygh']").filter(":checked");
		var rybh = [];
		checkbox.each(function(){
				rybh.push($(this).attr("rybh"));
			});
		if(checkbox.length<1){
			alert("请至少选择一条信息进行操作！");
		}else{
			$.ajax({
	   	    	type :"post",
				url:"${ctx}/jsxx/doSaveRy",
				data:"rybh="+rybh.join(",")+"&jsbh=${param.jsbh}",
				dataType:"json",
				success:function(val){		
					if(val.success == 'true'){
						alert(val.msg);
						getIframWindow("${param.pname}").$('#jsyh').DataTable().ajax.reload();
						var winId = top.layer.getFrameIndex(parent.window.name);
				    	close(winId);
					}else if(val.success == 'false'){
						alert(val.msg);
					}
					close(index);
				}   				
	   	   });
		}
	});
});
</script>
</body>
</html>
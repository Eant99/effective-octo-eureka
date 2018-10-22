<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>固定资产管理系统单位信息</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<!-- --------------------------------综合查询 box   开始------------------------------------------ -->
<div class="box">
    <div class="box-content">
    	<form id="myform" class="form-horizontal" action="">
    	<div class="clearfix">
           	<div class="sub-title pull-left text-primary">综合查询</div>
        </div>
        <hr class="hr-normal">
        <div class="container-fluid" id="searchBox">
        <!-- row 表示一行，col-md-x 表示一列占几个位置，一行一共12个位置,此处表示一行三列   开始 -->
            <div class="row">
            <!-- input-group 表示一个表单组，包含一个label和1个input或者1个select，或者1个input-group  开始-->
            	<div class="col-md-3">
	            	<div class="input-group">
			            <span class="input-group-addon">部&ensp;门&ensp;号</span>
		                <input type="text" id="txt_bmh" class="form-control" name="bmh" table="K" placeholder="请输入部门号">
	                </div>
                </div>
                <div class="col-md-3">
            <!-- input-group 表示一个表单组，包含一个label和1个input或者1个select，或者1个input-group  结束-->
	             	<div class="input-group">
	                    <span class="input-group-addon">单位名称</span>
                     	<input type="text" id="txt_mc" class="form-control" name="mc" table="K" placeholder="请输入单位名称">
	               	</div>
               	</div>
                <div class="col-md-3">
	               	<div class="input-group">
	                    <span class="input-group-addon">简&emsp;&emsp;称</span>
                     	<input type="text" id="txt_jc" class="form-control" name="jc" table="K" placeholder="请输入单位简称">
	                </div>
                </div>
                <div class="col-md-3">
	               	<div class="input-group">
	                    <span class="input-group-addon">单位性质</span>
               			<select id="drp_dwxz" class="form-control" name="dwxz" table="K" types="E">
               				<option value="">未选择</option>
                		</select>
	                 </div>
                 </div>
             </div>
       <!-- row 表示一行，col-md-x 表示一列占几个位置，一行一共12个位置,此处表示一行三列   结束 -->
             <div class="row hide"><!-- hide 页面加载完隐藏 -->
                <div class="col-md-3">
					<div class="input-group">
				     	<span class="input-group-addon">地&emsp;&emsp;址</span>
				     	<input type="text" id="txt_dz" class="form-control" table="K" name="dz" placeholder="请输入单位地址">
					</div>
				</div>
                <div class="col-md-3">
					<div class="input-group">
					    <span class="input-group-addon">单位领导</span>
			     		<input type="text" id="txt_dwld" class="form-control input-radius" name="dwld" value="" types="R" table="K" placeholder="请输入单位领导">
						<span class="input-group-btn"><button type="button" id="btn_dwld" class="btn btn-link ">选择</button></span>
					</div>
				</div>
                <div class="col-md-6">
			    	<div class="double-input">
				    	<div class="input-group">
			    			<span class="input-group-addon">建立日期</span>
						    <input type="text" id="txt_jlrq_begin" class="form-control date" name="jlrq" types="TL" table="K" data-format="yyyy-MM-dd" placeholder="请输入开始日期">
							<span class='input-group-addon'>
						    	<i class="glyphicon glyphicon-calendar"></i>
						   	</span>
					   	</div>
				   		<span class="connector" >—</span>
				   		<div class="input-group">
						    <input type="text" id="txt_jlrq_end" class="form-control date" name="jlrq" types="TH" table="K" data-format="yyyy-MM-dd" placeholder="请输入截止日期">
							<span class='input-group-addon'>
						    	<i class="glyphicon glyphicon-calendar"></i>
					   		</span>
					   	</div>
					</div>
				</div>
            </div>
            <div class="row hide">
                <div class="col-md-3">
	            	<div class="input-group">
	                	<span class="input-group-addon">单位状态</span>
             		 	<select id="drp_dwzt" class="form-control" name="dwzt" table="K">
             		 		<option value="">未选择</option>
                       		<option value="1">正常</option>
                       		<option value="0">禁用</option>
                   		</select>
	              	</div>
              	</div>
            </div>
            <div class="center-content">
             	<button type="button" class="btn btn-default" id="btn_search">
             		<i class="fa icon-search"></i>查询
             	</button>
                <button type="reset" class="btn btn-default">
                	<i class="fa icon-reset"></i>重置
               	</button>
            </div>
            <button id="btn_search_more" class="btn_search_more" type="button">高级查询 <span class="caret"></span></button>
        </div>
        </form>
    </div>
</div>
<!-- --------------------------------综合查询 box   结束------------------------------------------ -->
<!-- --------------------------------列表项  box   开始------------------------------------------ -->
<div class="box">
    <div class='box-content'>
    <!-- 列表操作按钮组    开始 -->
        <div class="clearfix">
            <div class="pull-left sub-title text-primary">单位信息</div>
            <div class="btn-group pull-right" role="group">
                <button type='button' class="btn btn-default" id="btn_add"><i class='fa icon-add'></i>增加</button>
                <button type='button' id="btn_del" class="btn btn-default"><i class='fa icon-del'></i>批量删除</button>
               	<button type='button' id="btn_assig" class="btn btn-default"><i class='fa icon-assig'></i>批量赋值</button>
                <button type='button' id="btn_exp"  class="btn btn-default" ><i class='fa icon-exp'></i>导出Excel</button>
                <!-- ------带下拉菜单的按钮组     开始------ -->
                <div class="btn-group">
				  <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">
				    	<i class='fa icon-sangangy'></i>
				  </button>
				  <ul class="dropdown-menu dropdown-menu-right" role="menu">
				  	<li><a id="btn_del" class="btn btn-link"><i class='fa icon-piliangshanchu-copy'></i>批量删除</a></li>
				  </ul>
				</div>
				<!-- ------带下拉菜单的按钮组     结束------ -->
            </div>
        </div>
        <!-- 列表操作按钮组    结束 -->
        <hr class="hr-normal"/>
        <div class='responsive-table'>
            <div class='scrollable-area'>
            <!-- datatables  开始 -->
                 <table id="mydatatables" class="table table-striped table-bordered">
				    <thead>
				    <tr>
				        <th><input type="checkbox" class="select-all"/></th>
				        <th>序号</th>
				        <th>部门号</th>
				        <th>单位名称</th>
				        <th>单位简称</th>
				        <th>单位地址</th>
				        <th>单位性质</th>
				        <th>成立日期</th>
				        <th>单位领导</th>
				        <th>上级单位</th>
				        <th>单位状态</th>
				        <th>操作</th>
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
				<i class="fa icon-add"></i>
				生成审批表
				</a>
				<a class="batch-file-controller-clear btn btn-default" href="javascript:void(0);">
				<i class="fa icon-shanchu"></i>
				清空列表
				</a>
			</div>
			<div class="batch-file-list-container">
				<div class="batch-file-list"></div>
			</div>
		</div>
	</div>
<!-- --------------------------------列表项  box  结束------------------------------------------ -->
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function (){
	//联想输入提示
	$("#txt_dwld").bindChange("${pageContext.request.contextPath}/suggest/getXx","R");
	
   	$("#more").bind('click', function (){
       	$(this).parents(".box-content").find(".row:not(:first):not(:nth-child(2))").toggleClass("hidden");
        $(this).toggleClass("dropup");
    });
   	//添加按钮
   	$("#btn_add").click(function(){
   		doOperate("${pageContext.request.contextPath}/webView/system/template/dwxxManagerTemplate.jsp");
   	});
   	//修改操作
   	$(document).on("click",".btn_upd",function(){
		var dwbh = $(this).parents("tr").find("[name='dwbh']").val();
		doOperate("${pageContext.request.contextPath}/dwxx/getDwxx?dwbh="+dwbh,"U");
	});
   	//单条删除操作
   	$(document).on("click",".btn_delxx",function(){
   		var dwbh = $(this).parents("tr").find("[name='dwbh']").val();
   		doDel("dwbh="+dwbh,"${pageContext.request.contextPath}/dwxx/deleteDwxx",function(val){
   			table.ajax.reload();
   			//....
   		},function(val){
   			//....
   		},"1");
   	});
   	//批量删除按钮
   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='dwbh']").filter(":checked");
   		if(checkbox.length>0){
   			var dwbh = [];
   			checkbox.each(function(){
   				dwbh.push($(this).val());
   			});
   			doDel("dwbh="+dwbh.join(","),"${pageContext.request.contextPath}/dwxx/deleteDwxx",function(val){
   	   			table.ajax.reload();
   	   			//....
   	   		},function(val){
   	   			//....
   	   		},dwbh.length);
   		}else{
   			alert("请选择至少一条信息删除");
   		}
   	});
	//批量赋值按钮
   	$("#btn_assig").click(function(){
   		var checkbox = $("#mydatatables").find("[name='dwbh']").filter(":checked");
   		if(checkbox.length>0){
   			var dwbh = [];
   			checkbox.each(function(){
   				dwbh.push($(this).val());
   			});
   			doPlfz(dwbh.length,"${pageContext.request.contextPath}/dwxx/findPlFuzhi?dwbh="+dwbh.join("','"),"600","400");
   		}else{
   			alert("请选择至少一条信息赋值");
   		}
   	});
   	//查看按钮
   	$("#btn_look").click(function(){
   		var checkbox = $("#mydatatables").find("[name='dwbh']").filter(":checked");
   		if(checkbox.length==1){
   			window.location.href = "${pageContext.request.contextPath}/dwxx/getDwxx?operateType=L&dwbh="+checkbox.val();
   		}else{
   			alert("只能选择一条信息查看");
   		}
   	});
   	//弹窗
   	$("#btn_dwld").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/window/ryxx/window.jsp?controlId=dwld","人员信息","920","630");
    });
   	//列表数据
   	var columns = [
		{"data": "DWBH",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="key" name="dwbh" value="' + data + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":30,"searchable": false,"class":"text-center"},
	   	{"data": "BMH",defaultContent:""},
	   	{"data": "MC",'render':function (data, type, full, meta){
		   	return '<a href="javascript:void(0);" class="btn btn-link">'+ data +'</a>';
		}},
	   	{"data": "JC",defaultContent:""},
	   	{"data": "DZ",defaultContent:""},
	   	{"data": "DWXZ",defaultContent:""},
	   	{"data": "JLRQ",defaultContent:""},
	   	{"data": "DWLD",defaultContent:""},
	   	{"data": "SJDW",defaultContent:""},
	   	{"data": "DWZT",defaultContent:"","render": function (data, type, full, meta){
	       	return '<input type="hidden" name="dwzt" value="' + data + '">' + data;
	    }},
	   	{"data":function (data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
		},orderable:false,"width":105}
	];
   	table = getDataTable("mydatatables","${pageContext.request.contextPath}/dwb/getPageList",[2,'asc'],columns);
    
    //综合查询
    $("#btn_search").on("click",function(){
		var json = searchJson("searchBox");
    	$('#mydatatables').DataTable().search(json,"json").draw();
    });
  	//选择变动操作
    $(document).on('click','.key',function(e){
		var dwbh = $(this).parents("tr").find("[name='dwbh']").val();
		var dwmc = $(this).parents("tr").find("[name='dwbh']").attr("dwmc");
    	if($(this).prop("checked")){
			var StrHtml="<div class=\"batch-file-list-item-line\">"+
					    "    <div class=\"batch-file-list-item-del\">"+
					    "        <a class=\"batch-file-item-delete\" herf=\"javascript:void(0);\"><i class=\"fa icon-cuowu\" style=\"color:red;line-height: 21px;\"></i></a>"+
					    "    </div>"+
					    "    <div class=\"batch-file-list-item\">	"+
					    "    	单位编号:<span class=\"selDwbh\">"+dwbh+"</span>  单位名称:"+dwmc+          	
					    "    </div>"+
					    "</div>";
			$(".batch-file-list").append(StrHtml);
			var selCount=parseInt($(".batch-btn span").text())+1;
			$(".batch-btn span").text(selCount);
			$(".batch-btn").addClass("active");
			setTimeout(function(){$(".batch-btn").removeClass("active")},1000);
 		} else {
 			var selDwbh;
 			$(".batch-file-list-item-line").each(function(){
 				selDwbh=$(this).find(".selDwbh").text();
 				if(selDwbh==dwbh)
				{
 					$(this).remove();
 					var selCount=parseInt($(".batch-btn span").text())-1;
 					$(".batch-btn span").text(selCount);
 					return false;
				}
 			});
 		}
    	return e.stopPropagation();
    });
    $(document).on('click','table tr',function(){
    	$(this).find(".key").click();
    });
  	//选中的资产删除操作
   	$(document).on("click",".batch-file-list-item-del",function(){
   		var selDwbh= $(this).parents(".batch-file-list-item-line").find(".selDwbh").text();
   		$('#mydatatables tr').each(function(){
   			var dwbh=$(this).find("[name='dwbh']").val();
   			if(selDwbh == dwbh)
  			{
   				$(this).find("[name='dwbh']").prop("checked",false);
   				$(this).removeClass("selected");
   				return false;
  			}   			
   		});
   		$(this).parents(".batch-file-list-item-line").remove();
		var selCount=parseInt($(".batch-btn span").text())-1;
		$(".batch-btn span").text(selCount);
   	});
 	//已选择明细详细信息清空列表事件
   	$(".batch-file-controller-clear").bind("click",function(){
   		var selLength=$('.batch-file-list-item-line').length;
   		if(selLength<1)
   		{
   			alert("您还没有选择，请先选择！");
   	   		return false;
   		}
   		confirm("确认要删除这条信息？",{title:"提示"},function(index){
 			close(index);
 	   		//1.更新所有行的已添加标志为变动
 	   		$('#mydatatables tr').each(function(){
 	   			$(this).find("[name='dwbh']").prop("checked",false);
 	   		});  	
 	   		//2.删除所有选中的行
 	   		$('.batch-file-list-item-line').remove();
 	   		//3.页面展示的选择条数清空为0
 	   		$(".batch-btn span").text("0");
   		});
   		return false;
   	});
  	//已选择明细详细信息下一步事件
   	$(".batch-file-controller-next").bind("click",function(){
   		var selLength=$('.batch-file-list-item-line').length;
   		if(selLength<1)
   		{
   			alert("您还没有选择，请先选择！");
   	   		return false;
   		}
   		var selDwbhS = [];
   		$('.batch-file-list-item-line').each(function(){
   			var dwbh=$(this).find(".selDwbh").text();
   			selDwbhS.push(dwbh);
   		});
   		alert(1);
   		return false;
   	});
  	//选择条数小圈点击查看详细信息
    $(".batch-btn-wr").bind("mouseover",function(){
    	$(".batch-file-container").toggle();
    	$(this).toggle(); 
    });
  	//已选择明细详细信息弹出框关闭
    $(".batch-file-close").bind("click",function(){
    	$(".batch-file-container").toggle();
    	$(".batch-btn-wr").toggle(); 
    });
	Dragging(getDraggingDialog).enable();//开启弹窗移动功能
//  $('#button').click(function(){
//      table.row('.selected').remove().draw(false);
//  });
});
	//弹窗可移动
	var Dragging=function(validateHandler){ //参数为验证点击区域是否为可移动区域，如果是返回欲移动元素，负责返回null
	        var draggingObj=null; //dragging Dialog
	        var diffX=0;
	        var diffY=0;
	        
	        function mouseHandler(e){
	            switch(e.type){
	                case 'mousedown':
	                    draggingObj=validateHandler(e);//验证是否为可点击移动区域
	                    if(draggingObj!=null){
	                        diffX=e.clientX-draggingObj.offsetLeft;
	                        diffY=e.clientY-draggingObj.offsetTop;
	                    }
	                    break;
	                
	                case 'mousemove':
	                    if(draggingObj){
	                        draggingObj.style.left=(e.clientX-diffX)+'px';
	                        draggingObj.style.top=(e.clientY-diffY)+'px';
	                    }
	                    break;
	                
	                case 'mouseup':
	                    draggingObj =null;
	                    diffX=0;
	                    diffY=0;
	                    break;
	            }
	        };
	        
	        return {
	            enable:function(){
	                document.addEventListener('mousedown',mouseHandler);
	                document.addEventListener('mousemove',mouseHandler);
	                document.addEventListener('mouseup',mouseHandler);
	            },
	            disable:function(){
	                document.removeEventListener('mousedown',mouseHandler);
	                document.removeEventListener('mousemove',mouseHandler);
	                document.removeEventListener('mouseup',mouseHandler);
	            }
	        };
	};

	function getDraggingDialog(e){
	    var target=e.target;
	    while(target && target.className.indexOf('batch-file-header')==-1){
	        target=target.offsetParent;
	    }
	    if(target!=null){
	        return target.offsetParent;
	    }else{
	        return null;
	    }
	}
	
</script>
</body>
</html>
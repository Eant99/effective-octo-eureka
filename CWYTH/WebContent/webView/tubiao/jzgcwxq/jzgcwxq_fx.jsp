<%@page import="com.googosoft.util.Validate"%>
<%@page import="com.googosoft.constant.Constant"%>
<%@page import="com.googosoft.commons.LUser"%>
<%@page import="com.googosoft.util.DateUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>教师评价分析</title>
	<%@include file="/static/include/public-list-css.inc"%>
	<%@include file="/static/include/public-draw-js.inc"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/datatable/datatables.min.js"></script>
	
	<style type="text/css">
		.btn-default{
			margin-left: 5px;
		}
		.ndqj{
			width: 16% ! important;
		}
		.row{
            margin-right:6px;		
		}
		.nlqj{
			width: 6% ! important;
		}
		.header{
			position:fixed;
			top:0px;
			z-index:100;
			background-color: white;
	    	border-bottom: 1px solid #F3F3F3;
	    	padding: 10px;
	    	width:100%;
	    	}
	    .box-panel .container-fluid .box-content{
	    	border:0px ! important;
	    }	
	    .table td,
		.table th {
			white-space: nowrap;
	}
	.ndheader{
    border-bottom-color: #dddddd;
    margin: 0px 15px;
    color: #222;
    background: white;
    padding: 2px 3px;
    position: fixed;
    width: 90%;
    z-index: 9999}
	</style>
	<%
 	String name=LUser.getRyxm(); 
	%>
</head>
<body>
<form id="form" class="form-horizontal" action="" method="post">
<input type="hidden" name="xm" value="<%=name %>">
	<div class="ndheader" style="top:18px;background: #d9edf7;padding-left: 30px;color:#31708f;font-size:13px;"> 
        <div class="row">
            <div class='box-panel'  id ="grgzfx">
                &emsp;说明：按月度或年度分析个人工资情况。
            </div>
        </div>
    </div>
	<div class="container-fluid" >  
		<div class="row" style="margin-top:35px">
			<div class="col-md-6" >
				<!-- 教师总评价前十名 -->
				<div class="box">
					<div class='box-panel'>
			     		<div class='box-header clearfix'>
			         		<div class="sub-title pull-left text-primary">
			            		<i class='fa icon-jibenxinxi'></i>
			            		税前工资分布情况
			         		</div>
			         		<div class=" col-md-2 col-sm-2">
					            <div class="input-group" style="min-width: 180px;">
					                <span class="input-group-addon">月&emsp;度</span>
					                <input type="text"  class="form-control nydate" name="ndqj1" onchange placeholder="请选择月度" value="<%=DateUtil.getYearMonth()%>" >
									<span class='input-group-addon'> 
										<i class="glyphicon glyphicon-calendar"></i>
									</span>
					            </div>
					  		</div>
				   			<div class='actions'>
				       			<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
				   			</div>
			     		</div>
						<hr class="hr-normal">
						<div class="container-fluid" style="height:350px;" id="d_gzfbqk">
						</div>
			 		</div>
				</div>
			</div>
			<div class="col-md-6" >
				<!-- 教师学历评价 -->
				<div class="box">
					<div class='box-panel'>
			     		<div class='box-header clearfix'>
			         		<div class="sub-title pull-left text-primary">
			            		<i class='fa icon-jibenxinxi'></i>
			            		年度工资变化情况-按月度分析
			         		</div>
			         		<div class=" col-md-2 col-sm-2">
					            <div class="input-group" style="min-width: 180px;">
					                <span class="input-group-addon">年&emsp;度</span>
					                <input type="text"  class="form-control year" name="nd"  onchange data-format="yyyy" placeholder="请选择年度" value="<%=DateUtil.getYear() %>" >
									<span class='input-group-addon'> 
										<i class="glyphicon glyphicon-calendar"></i>
									</span>
					            </div>
					  		</div>
				   			<div class='actions'>
				       			<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
				   			</div>
			     		</div>
						<hr class="hr-normal">
						<div class="container-fluid" style="height:350px;" id="d_ydgzbh">
						</div>
			 		</div>
				</div>
			</div>
		</div>
		<div class="box" style="margin-top:18px;">
		<div class="container-fluid">
			<div class='list' style="margin-top: 10px">
				<div class="box-header clearfix">
					<div class="pull-left sub-title text-primary">工资明细</div>
				</div>
				<hr class="hr-normal" />
				<table id="mydatatables" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>序号</th>
							<th>姓名</th>
							<th>年度</th>
							<th>月份</th>
							<th>岗位工资</th>
							<th>薪级工资</th>
							<th>新住房贴</th>
							<th>物业补贴</th>
							<th>独/回</th>
							<th>基础绩效</th>
							<th>奖励绩效1</th>
							<th>博士补贴</th>
							<th>岗位补贴</th>
							<th>补工资</th>
							<th>应发合计</th>
							<th>住房积金</th>
							<th>补住房积金</th>
							<th>医疗保险</th>
							<th>代扣税</th>
							<th>房租</th>
							<th>失业金</th>
							<th>社保金</th>
							<th>养老金</th>
							<th>补医疗保险</th>
							<th>补失业金</th>
							<th>补养老金</th>
							<th>扣款</th>
							<th>扣款合计</th>
							<th>实发合计</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	</div>		
</form>	
<script type="text/javascript">
$(function(){
	gzfbDataLoad();
	ydgzDataLoad();
	var table = loadTable("mydatatables", "${pageContext.request.contextPath}/jzgcwxq/getPageList?"+$("form").serialize());
	$(".nydate").change(function(){
		gzfbDataLoad();
	});
	$(".year").change(function(){
		ydgzDataLoad();
		tableRelod(table,"${pageContext.request.contextPath}/jzgcwxq/getPageList?"+$("form").serialize());
	});
});
function gzfbDataLoad(){
    $.ajax({
        type:"post",
        url:"${pageContext.request.contextPath}/jzgcwxq/getJzgcwxq",
        data:$("form").serialize(),
        success:function(val){
            close(index);
            val = $.trim(val);
            val = JSON.getJson(val);
            if(val.success){
                if(val.gzfboption){//工资分布
                    setPieTb($("#d_gzfbqk")[0],val.gzfboption);
                }else{
                    $("#d_gzfbqk").prop("innerHTML","<div class='center-content' style='color:Red;'>没有查询到信息</div>");
                }
            }else{
                alert(val.msg);
            }
        },
        error:function(val){
            close(index);
            alert(val);
        },
        beforeSend:function(val){
            index = loading();
        }
    });
}

function ydgzDataLoad(){
	$.ajax({
		type:"post",
		url:"${pageContext.request.contextPath}/jzgcwxq/getJzgcwxq",
		data:$("form").serialize(),
		success:function(val){
			close(index);
			val = $.trim(val);
			val = JSON.getJson(val);
			if(val.success){
				if(val.gzbhoption){//工资分布
					setBarTb($("#d_ydgzbh")[0],val.gzbhoption);
				}else{
					$("#d_ydgzbh").prop("innerHTML","<div class='center-content' style='color:Red;'>没有查询到信息</div>");
 				}
			}else{
				alert(val.msg);
			}
		},
		error:function(val){
			close(index);
			alert(val);
		},
		beforeSend:function(val){
			index = loading();
		}
	});
}
var tableRelod = function(table, _url) {
    table.ajax.url(_url);
    return table.ajax.reload();
}

var loadTable = function(_tableId,_url){
    var dataTable = $('#'+_tableId).DataTable({
        "ajax": {
            url: _url
        },
        "pagingType":"full_numbers",
        "lengthMenu":[12],
        "order": [ 4, 'desc' ],
        "serverSide": true,
        "scrollX": true,
        "scrollY": true,
        "columns": [
			{"data" : "_XH",orderable : false,class:'text-center','render' : function(data, type, full, meta) {
				return data;
			},"width" : 401},
			{"data" : "XM",class:'text-left',defaultContent : "","width" : "5%",orderable:false},
			{"data" : "YEAR",class:'text-left',defaultContent : "","width" : "5%",orderable:false},
			{"data" : "MON",class:'text-left',defaultContent : "","width" : "5%",orderable:false},
			{"data" : "GWGZ",class:'text-left',defaultContent : "","width" : "5%",orderable:false},
			{"data" : "XJGZ",class:'text-right',defaultContent : "",orderable:false},
			{"data" : "XZFT",class:'text-right',defaultContent : "",orderable:false},
			{"data" : "WYBT",class:'text-right',defaultContent : "",orderable:false},
			{"data" : "DSZF",class:'text-right',defaultContent : "",orderable:false},
			{"data" : "JCJX",class:'text-right',defaultContent : "",orderable:false},
			{"data" : "JLJX1",class:'text-right',defaultContent : "",orderable:false},
			{"data" : "BSBT",class:'text-right',defaultContent : "",orderable:false},
			{"data" : "GWBT",class:'text-right',defaultContent : "",orderable:false},
			{"data" : "BGZ",class:'text-right',defaultContent : "",orderable:false},
			{"data" : "YFHJ",class:'text-right',defaultContent : "",orderable:false},
			{"data" : "ZFJJ",class:'text-right',defaultContent : "",orderable:false},
			{"data" : "BGJJ",class:'text-right',defaultContent : "",orderable:false},
			{"data" : "YLBX",class:'text-right',defaultContent : "",orderable:false},
			{"data" : "DKS",class:'text-right',defaultContent : "",orderable:false},
			{"data" : "FZ",class:'text-right',defaultContent : "",orderable:false},
			{"data" : "SYJ",class:'text-right',defaultContent : "",orderable:false},
			{"data" : "SBJ",class:'text-right',defaultContent : "",orderable:false},
			{"data" : "YLJ",class:'text-right',defaultContent : "",orderable:false},
			{"data" : "BKYLBX",class:'text-right',defaultContent : "",orderable:false},
			{"data" : "BKSYJ",class:'text-right',defaultContent : "",orderable:false},
			{"data" : "BKYLJ",class:'text-right',defaultContent : "",orderable:false},
			{"data" : "KK",class:'text-right',defaultContent : "",orderable:false},
			{"data" : "KKHJ",class:'text-right',defaultContent : "",orderable:false},
			{"data" : "SFHJ",class:'text-right',defaultContent : "",orderable:false}
        ] ,
        "dom":  "<'row'<'col-md-12 mar-bo5'p>>" +  "t" + "<'row'<'col-md-12 text-right'i>>"

    }); 
    return dataTable;
}
</script>
</body>
</html>
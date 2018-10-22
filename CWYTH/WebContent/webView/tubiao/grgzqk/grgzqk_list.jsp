<%@page import="com.googosoft.util.DateUtil"%>
<%@page import="com.googosoft.constant.Constant"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<base target="_blank"/>
<title>教职工工资概况分析</title>
<%@include file="/static/include/public-draw-css.inc"%>
<%@include file="/static/include/public-draw-js.inc"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/datatable/datatables.min.js"></script>
<%
String xn = DateUtil.getYear();  //学年
xn="2016";
//String xq = Constant.MR_XQ();   //学期
%>
<style type="text/css">
/* 以下调节datatable */
.dataTables_length input[name=mytable_length]{
    max-width: 50px;
}

.dataTables_wrapper .row{
    padding-right: 15px !important;
    padding-left: 15px !important;
}
.page_jump  {
    float: right;
}
.page_jump input[type=number]{
    height: 31px !important;
}

.page_jump button{
    height: 31px !important;
}
 
.mar-bo5{
    margin-bottom: 5px;    
}

.pagination{
    float: right;
    margin: 0;
    padding-right: 20px;
}

.table td,.table th {
    white-space: nowrap;  /* 列宽根据内容自适应 */
} 

.table td .btn{   
    padding-top: 0;
    padding-bottom: 0;
}
.ndheader{
border-bottom-color: #dddddd;
margin-top: -18px;
color: #222;
background: white;
padding: 10px;
position: fixed;
width: 100%;
z-index: 9999}
</style>
</head>
<body>
<form id="form" class="form-horizontal" action="" method="post">
    <div class="ndheader" style="top:18px"> 
        <div class="row">
            <div class=" col-md-12">
                <div class="input-group" style="max-width:240px;" >
                    <span class="input-group-addon">年&nbsp;度</span>
                    <input type="text" id="txt_ndqj_begin" class="form-control window year" name="nd" data-format="yyyy" placeholder="请输入开始年度" value="<%=xn%>">
                    <span class='input-group-addon'>
                        <i class="glyphicon glyphicon-calendar"></i>
                    </span>
                </div>
            </div>
        </div>
    </div>
    <div class="container-fluid" >
        <div class="row" style="margin-top: 43px">
            <div class="col-md-12">
                <div class="box">
                    <div class='box-panel'>
                        <div class='box-header clearfix'>
                            <div class="sub-title pull-left text-primary">
                                <i class='fa icon-jibenxinxi'></i>
                               <font name="font_nd"><%=xn %></font>年工资支出情况
                            </div>
                            <div class='actions'>
                                <a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
                            </div>
                        </div>
                        <hr class="hr-normal">
                        <div class="container-fluid box-content" style="height:350px;" id="d_gzzc">
                            
                        </div>
                    </div>
                </div>
            </div>
        </div>
		<div class="row" style="margin-top: 0;">
            <div class="col-md-12" >
                 <div class="box" >
                     <div class='box-panel'>
                         <div class='box-header clearfix'>
                             <div class="sub-title pull-left text-primary">
                                 <i class='fa icon-jibenxinxi'></i>
                                 <font name="font_nd"><%=xn %></font>年度教职工工资列表
                             </div>
                             <div class='actions'>
                                 <a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
                             </div>
                         </div>
                         <hr class="hr-normal">
                         <div class="container-fluid box-content"  id="d_gzmx">
                             <div class='responsive-table'>
                                <div class='scrollable-area'>
                                    <table id="mytable" class="table table-striped table-bordered table-hover" style="margin-bottom:0;width:100% !important; ">
                                        <thead>
					                        <tr>
					                            <th>序号</th>
					                            <th>姓名</th>
<!-- 					                            <th>职称</th> -->
					                            <th>月度代码</th>
					                            <th>岗位工资</th>
					                            <th>薪级工资</th>
					                            <th>卫生费</th>
					                            <th>一次性支付</th>
					                            <th>独生子女补贴</th>
					                            <th>误餐补贴三</th>
					                            <th>赛罕区津贴</th>
					                            <th>一次性补贴</th>
					                            <th>基础性绩效</th>
					                            <th>应发合计</th>
					                            <th>公积金</th>
					                            <th>公积金本息</th>
					                            <th>医疗保险</th>
					                            <th>养老保险</th>
					                            <th>失业保险</th>
					                            <th>物业费</th>
					                            <th>税前工资</th>
					                            <th>所得税</th>
					                            <th>扣款合计</th>
					                            <th>实发工资</th>   
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
        </div>
    </div>
</form>
<script type="text/javascript">
$(function(){
	var gzChart = gzzcDataLoad();
	var table = loadTable("mytable","${pageContext.request.contextPath}/grgzqk/getJzggzzcmx?"+$("form").serialize());
	chartClick(gzChart, table);
	
	//年度变化自动查询
    $(document).on("change","#txt_ndqj_begin",function(){
    	$("font[name=font_nd]").text($("#txt_ndqj_begin").val());
    	chartClick(gzzcDataLoad(), tableRelod(table));
    });

});


var chartClick = function(ech, table){
    ech.on("click",function(params){
        tableRelod(table, "mon="+params.name);
    }); 
}

var chart;
var gzzcDataLoad = function(){
    $("div[name='tb']").prop("innerHTML","");
    $.ajax({
        type:"post",
        url:"${pageContext.request.contextPath}/grgzqk/getJzggzzc",
        data:$("form").serialize(),
        async: false,
        success:function(val){
            close(index);
            val = $.trim(val);
            val = JSON.getJson(val);

            if(val.gzzcoption){
               chart = setBarTb($("#d_gzzc")[0],val.gzzcoption);
            }else{
               $("#d_gzzc").prop("innerHTML","<div class='center-content' style='color:Red;'>没有查询到信息</div>");
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
    
    return chart;
}

var tableRelod = function(table, _data) {
    var _url ="";
    if(_data){
        _url = _data+"&"
    }
    table.ajax.url("${pageContext.request.contextPath}/grgzqk/getJzggzzcmx?"+_url+$("form").serialize());
    return table.ajax.reload();
}


var loadTable = function(_tableId,_url){
    var dataTable = $('#'+_tableId).DataTable({
        "ajax": {
            url: _url
        },
        "pagingType":"full_numbers",
        "lengthMenu":[20],
        "order": [ 3, 'desc' ],
        "serverSide": true,
        "scrollX": true,
        "scrollY": true,
        "columns": [
            {"data" : "_XH",orderable : false,"class":'text-center','render' : function(data, type, full, meta) {
                return data;
            },"width" : ""},
            {"data" : "XM","class":'text-left',defaultContent : "","width" : "",orderable:false},
          //  {"data" : "ZC","class":'text-left',defaultContent : "","width" : "",orderable:false},
            {"data" : "YDDM","class":'text-left',defaultContent : "","width" : "",orderable:false},
            {"data" : "GWGZ","class":'text-right',defaultContent : "",orderable:false},
            {"data" : "XJGZ","class":'text-right',defaultContent : "",orderable:false},
            {"data" : "WSF","class":'text-right',defaultContent : "",orderable:false},
            {"data" : "YCXZF","class":'text-right',defaultContent : "",orderable:false},
            {"data" : "DSZNBT","class":'text-right',defaultContent : "",orderable:false},
            {"data" : "WCBT3","class":'text-right',defaultContent : "",orderable:false},
            {"data" : "SHJT","class":'text-right',defaultContent : "",orderable:false},
            {"data" : "YCXBT","class":'text-right',defaultContent : "",orderable:false},
            {"data" : "JCXJX","class":'text-right',defaultContent : "",orderable:false},
            {"data" : "YFHJ","class":'text-right',defaultContent : "",orderable:false},
            {"data" : "GJJ","class":'text-right',defaultContent : "",orderable:false},
            {"data" : "GJJBX","class":'text-right',defaultContent : "",orderable:false},
            {"data" : "YILBX","class":'text-right',defaultContent : "",orderable:false},
            {"data" : "YANGLBX","class":'text-right',defaultContent : "",orderable:false},
            {"data" : "SYBX","class":'text-right',defaultContent : "",orderable:false},
            {"data" : "WYF","class":'text-right',defaultContent : "",orderable:false},
            {"data" : "SQGZ","class":'text-right',defaultContent : "",orderable:false},
            {"data" : "SDS","class":'text-right',defaultContent : "",orderable:false},
            {"data" : "KKHJ","class":'text-right',defaultContent : "",orderable:false},
            {"data" : "SFGZ","class":'text-right',defaultContent : "",orderable:false}
        ] ,
        "language":{
            "lengthMenu": "每页_MENU_条记录",
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
            "searchPlaceholder":"请输入用户关键字"
        },
        "dom":"<'row'<'col-md-9 col-sm-9 col-xs-9 mar-bo5 pull-right'p><'col-md-3 col-sm-3 col-xs-3'f>>t<'row'<'col-md-2 col-sm-2 col-xs-2'l><'col-md-10 col-sm-10 col-xs-10 text-right'i>>",
    }); 
    
    return dataTable;
}
</script>
</body>
</html>
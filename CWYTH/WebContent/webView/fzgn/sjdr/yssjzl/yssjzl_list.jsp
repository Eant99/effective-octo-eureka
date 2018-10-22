<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产管理系统管理员建账</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.row .input-group {
	   margin-bottom: 0px;
	}
	.row.bottom{
		background-color: white;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox">
		<form id="myform" class="form-inline" role="form" action="">
			<input type="hidden" value="${mkbh}">
			<div class="row ztbz">
                <div class="col-md-9 checkbox">
                	<span class="zt_label">资产大类：</span>
                	<a type="button" data-value="ALL"class="btn btn-link btn-mark sj_all active">全部大类</a>
                	<a type="button" data-value="FW" class="btn btn-link btn-mark sj_fw">房屋类</a>
                	<a type="button" data-value="GZW" class="btn btn-link btn-mark sj_gzw">构筑物类</a>
                	<a type="button" data-value="TD" class="btn btn-link btn-mark sj_td">土地类</a>
                	<a type="button" data-value="PLANT" class="btn btn-link btn-mark sj_plant">植物类</a>
                	<a type="button" data-value="WW" class="btn btn-link btn-mark sj_ww">文物及陈列品类</a>
                	<a type="button" data-value="BOOKS" class="btn btn-link btn-mark sj_books">图书类</a>
                	<a type="button" data-value="JJ" class="btn btn-link btn-mark sj_jj">家具用具及被服装具类</a>
                	<a type="button" data-value="ANIMALS" class="btn btn-link btn-mark sj_animals">动物类</a>
                	<a type="button" data-value="CAR" class="btn btn-link btn-mark sj_car">交通运输类</a>
                	<a type="button" data-value="WXZC" class="btn btn-link btn-mark sj_wxzc">无形资产</a>
                	<a type="button" data-value="PTSB" class="btn btn-link btn-mark sj_ptsb">一般设备类</a>
              	</div>
              	<div class="btn-group pull-right" role="group" style="margin-right: 5px;">
	                <button type="button" class="btn btn-default" id="btn_sh">审核</button>
	                <button type="button" class="btn btn-default" id="btn_save">保存</button>
	                <!-- <button type="button" class="btn btn-default" id="btn_submit">提交</button> -->
	                <button type="button" class="btn btn-default" id="btn_rz">入账</button>
	                <button type="button" class="btn btn-default" id="btn_del">删除</button>
	                <button type="button" class="btn btn-default" id="btn_delxx">清空</button>
            	</div>
        	</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
	        	<div class='scrollable-area'>
	        		 <div class="alert alert-info pull-right wxts_sh" style="color: red;">温馨提示：如果对列表数据进行修改后，请先点击<b>【保存】</b>按钮进行保存，保存后再进行数据审核、提交、入账。</div>
		        	<table id="mydatatables" class="table table-striped table-bordered">
			    		<thead>
						    <tr>
						        <th><input type="checkbox" class="select-all"/></th>
						        <th>序号</th>
						        <th>资产编号</th>
						        <th>资产名称</th>
						        <th>分类号</th>
						        <th>分类名称</th>
						        <th class="qb">价值类型</th>
						        <th class="wxzc td gzw ww">单价</th>
						        <th class="qb fw zw ts jj dw  jt sb">总价</th>
						        <th class="qb td wxzc">折旧状态</th>
						        <th class="qb td wxzc">累计折旧</th>
					 			<th>使用人</th>
						        <th>使用单位</th>
						        <th>使用方向</th>
						        <th class="fw_gzw_td">存放地点</th>
						        <th class="qb dw">调转入日期</th>
						        <th>购置日期</th>
						        <th>计量单位</th>
						        <th>现状</th>
						        <th>经费来源</th>
						        <th>资产来源</th>
								<th>入账日期</th>
						        <th>记账类型</th>
						        <th>采购组织形式</th>
						        <th class="qb ts">预算项目编号</th>
						        <th class="qb ts">会计凭证号</th>
						        <th class="qb td zw ww ts jj dw jt wxzc sb">竣工日期</th><!-- ccrq -->
						        <th class="qb fw gzw td zw ww jj dw jt wxzc sb">出版日期</th><!-- ccrq -->
						        <th class="qb fw gzw td ts zw ww dw wxzc sb">出厂日期</th> <!-- ccrq -->
						        <th class="qb zw ww ts dw ">投入使用日期</th>
						        <th class="qb fw gzw td zw ww ts dw wxzc">预计使用年限</th><!-- SYNX -->
						        <th class="qb fw gzw td zw ww jj ts jt wxzc sb">预计寿命</th><!-- SYNX -->
						        <th class="qb gzw td zw ww ts jj dw jt wxzc sb">房屋所有权人</th><!-- CZR -->
						        <th class="qb fw gzw td zw dw ww jj wxzc sb ts">持证人</th><!-- CZR -->
						        <th class="qb fw gzw td zw dw ww jj jt sb ts">发明人</th><!-- CZR -->
						        <th class="qb gzw td zw dw ww jj jt sb ts wxzc">平面图数</th><!-- TPH -->
						        <th class="qb fw gzw td zw ww jj jt sb ts wxzc">年龄</th><!-- TPH -->
					 			<th class="qb gzw td zw ww jj jt sb ts dw wxzc ">承建单位</th><!-- sccj -->
					 			<th class="qb fw gzw td zw jj jt sb ts dw wxzc">制造者</th><!-- sccj -->
					 			<th class="qb fw gzw td zw jj jt sb ww dw wxzc">出版社</th><!--sccj  -->
					 			<th class="qb fw gzw td zw ts sb ww dw wxzc">生产厂家</th><!--  sccj-->
					 			<th class="qb fw gzw td zw ww jj jt sb ts wxzc">产地</th><!-- sccj -->
						        <th class="qb gzw td zw ww jj jt sb ts wxzc dw">完损状况</th><!-- tddj -->
						        <th class="qb fw td zw jj jt sb ww dw wxzc ts">安全等级</th><!-- tddj -->
						        <th class="qb fw gzw zw jj jt sb ww dw wxzc ts">土地等级</th><!-- tddj -->
						        <th class="qb fw gzw td zw ww ts jj dw jt sb">校区编号</th><!-- htbh -->
						        <th class="qb fw gzw td zw wxzc ts jj dw jt sb">照片编号</th><!-- htbh -->
						        <th class="qb fw gzw td zw wxzc ww sb">合同编号</th><!-- htbh -->
						        <th class="qb wxzc sb ts jj jt dw ww">坐落位置</th><!-- xss -->
						        <th class="qb fw gzw ts zw jj jt sb ww dw wxzc">四角坐标</th><!-- xss -->
						        <th class="qb fw gzw td ts zw jj jt sb dw wxzc">搜集者</th><!--  xss-->
						        <th class="qb fw gzw td zw ts zw sb ww wxzc">销售商</th><!--xss  -->
						        <th class="qb td zw jj jt sb ww dw wxzc ts">权属性质</th>
						        <th class="qb td zw jj jt sb ww dw wxzc ts">房管形式</th><!-- fgxs -->
						        <th class="qb zw jj jt sb ww dw wxzc ts">产权形式</th>
						        <th class="qb td zw jj jt sb dw wxzc ts ww">产权来源</th><!-- CQLY --> 
						        <th class="qb fw gzw td zw wxzc ts jj dw jt sb">来源地</th><!-- CQLY --> 
						        <th class="qb gzw td zw ww jj jt sb ts wxzc dw">权属证明</th>
						        <th class="qb td zw ww jj jt ts wxzc dw">权属证号</th><!-- JSH -->
						        <th class="qb fw gzw zw wxzc ts jj dw jt sb ww">宗地编号</th><!-- JSH -->
						        <th class="qb fw gzw zw wxzc ts jj dw jt sb td">文物编号</th> <!-- JSH -->
					 			<th class="qb td gzw zw wxzc ts jj dw jt sb ww ">证载面积</th><!-- zmmj -->
					 			<th class="qb fw gzw zw ww jj jt sb ts wxzc dw">使用权面积</th><!--zmmj  -->
						        <th class="qb gzw td zw ww jj jt sb ts wxzc dw ">发证日期</th>
						        <th class="qb td wxzc ts jj jt sb ww zw dw">建成年份</th><!-- jcnf -->
						        <th class="qb fw gzw td ww wxzc ts jj dw jt sb">栽种年份</th><!-- jcnf -->
						        <th class="qb fw gzw td ww wxzc ts jj zw jt sb">出生年份</th><!-- jcnf -->
						        <th class="qb gzw td zw ww wxzc ts jj dw jt sb">房屋用途</th>
						        <th class="qb td zw ww wxzc ts jj dw jt sb">建筑类型</th>
						        <th class="qb td zw ww wxzc ts jj dw jt sb">地上面积</th>
						        <th class="qb td zw ww wxzc ts jj dw jt sb">地下面积</th>
						        <th class="qb gzw td zw ww wxzc ts jj dw jt sb">自然间数</th>
						        <th class="qb td zw ww wxzc ts jj dw jt sb">附属设施</th><!--gzyq  -->
						        <th class="qb fw gzw td zw ww jj jt ts wxzc dw">随机资料</th><!-- gzyq -->
						        <th class="qb fw gzw sb zw ww jj jt ts wxzc dw">地下设施</th><!-- gzyq -->
						        <th class="qb fw gzw td sb ww jj jt ts wxzc dw">拉丁文名</th><!-- gzyq -->
						        <th class="qb fw gzw td zw sb jj jt ts wxzc dw">特征描写</th><!-- gzyq -->
						        <th class="qb td zw ww wxzc ts jj dw jt sb">建筑面积</th><!-- fjzj -->
						        <th class="qb fw gzw ww zw sb jj jt ts wxzc dw">实际面积</th> <!-- fjzj -->
				     			<th class="qb gzw td ww zw sb jj jt ts wxzc dw">自用面积</th>
						        <th class="qb gzw ww zw sb jj jt ts wxzc dw">自用价值</th>
						        <th class="qb fw td gzw ww zw wxzc dw">型号</th><!-- xh -->
						        <th class="qb gzw td zw ww wxzc ts jj dw jt sb">建筑结构</th><!-- xh -->
						        <th class="qb fw gzw td zw sb jj jt ts wxzc dw">藏品年代</th><!-- xh -->
						        <th class="qb gzw td zw sb jj jt ts wxzc dw ww">出借面积</th>
						        <th class="qb fw gzw td wxzc jt sb jj">外币单价</th><!-- wbje -->
						        <th class="qb gzw td zw ww wxzc ts jj dw jt sb">占地面积</th><!-- wbje -->
						        <th class="qb fw gzw td zw ww wxzc ts jj dw sb">行车里程</th><!-- wbje -->
						        <th class="qb gzw td zw ww wxzc ts jj dw jt sb">出租面积</th>
						        <th class="qb gzw zw ww wxzc ts jj dw jt sb">出租价值</th>
						        <th class="qb td zw ww wxzc ts jj dw jt sb">供暖面积</th>
						        <th class="qb gzw td zw ww wxzc ts jj dw jt sb">对外投资面积</th>
						        <th class="qb td zw ww wxzc ts jj dw jt sb">使用面积</th>
					 			<th class="qb gzw td zw ww wxzc ts jj dw jt sb">担保面积</th>
						        <th class="qb gzw td zw ww wxzc ts jj dw jt sb">担保价值</th>
						        <th class="qb gzw td zw ww wxzc ts jj dw jt sb">幢号</th><!-- cch -->
						        <th class="qb fw gzw td zw wxzc ts jj jt dw sb">拓片编号</th><!--cch  -->
						        <th class="qb fw gzw td zw wxzc ww jj jt dw sb">出版编号</th><!--cch -->
						        <th class="qb fw gzw td zw wxzc ts ww jt dw sb">出厂编号</th><!--cch  -->
						        <th class="qb fw gzw td zw wxzc ts ww jj dw sb">发动机号</th><!--cch  -->
						        <th class="qb td gzw zw wxzc ts ww jj dw sb jt">其他面积</th><!-- fw_qtmj(房屋的) -->
						        <th class="qb fw gzw zw wxzc ts ww jj dw sb jt">其他面积</th><!-- td_qtmj(土地的) -->
						        <th class="qb gzw zw wxzc ts ww jj dw sb jt">其他价值</th>
						        <th class="qb fw gzw td wxzc ts">进口总价</th><!-- jkdj --> 
						        <th class="qb td zw wxzc ts ww jt dw jj sb">层数</th><!-- jkdj -->
						        <th class="qb fw gzw td zw wxzc ww jt dw jj sb">实价</th><!-- jkdj -->
						        <th class="qb td zw wxzc ts ww jt dw jj sb">主要用途</th>
						        <th class="qb fw td wxzc jt">学科类别</th>
						        <th class="qb fw td wxzc jt">学科</th>
						        <th class="qb fw gzw zw wxzc ts ww jj dw sb jt">入账科目</th>
						        <th class="qb fw gzw zw wxzc ts ww jj dw sb jt">权属年限</th>
					 <!-- 70 --><th class="qb fw gzw zw wxzc ts ww jj dw sb jt">权属性质</th>
						        <th class="qb fw gzw zw wxzc ts ww jj dw sb jt">权属证明</th>
						        <th class="qb fw gzw jt zw wxzc ts ww jj dw sb">土地使用权人</th><!--djh  -->
						        <th class="qb fw gzw td jt zw wxzc ts jj dw sb">文物等级</th><!-- djh -->
						        <th class="qb fw gzw jt zw td ts ww jj dw sb">证书号</th><!-- djh -->
						        <th class="qb fw gzw td zw wxzc ww">单据号</th><!--djh  -->
						        <th class="qb fw gzw jt zw wxzc ts ww jj dw sb">独用面积</th>
						        <th class="qb fw gzw jt zw wxzc ts ww jj dw sb">发证日期</th>
						        <th class="qb fw gzw jt zw wxzc ts ww jj dw sb">分摊面积</th>
						        <th class="qb fw jj gzw jt zw wxzc ts ww dw">用途</th><!-- td_yt,土地 -->
						        <th class="qb fw td gzw jt zw wxzc ts ww dw">用途</th><!-- yt,家具 -->
						        <th class="qb fw gzw jt zw wxzc ts ww jj dw sb">自用面积</th>
						        <th class="qb fw gzw jt zw wxzc ts ww jj dw sb">出租面积</th>
						        <th class="qb fw gzw jt zw wxzc ts ww jj dw sb">出借面积</th>
					 <!-- 80 --><th class="qb fw gzw jt zw wxzc ts ww jj dw sb">出借价值</th>
						        <th class="qb fw gzw jt zw wxzc ts ww jj dw sb">对外投资面积</th>
						        <th class="qb fw gzw jt zw wxzc ts ww jj dw sb">对外投资价值</th>
						        <th class="qb fw gzw td jt wxzc ts ww jj sb">纲属科</th><!-- gg -->
						        <th class="qb fw gzw jt zw wxzc ts td jj dw sb">质料</th><!--  gg-->
						        <th class="qb fw gzw jt zw wxzc td dw sb ww">规格</th><!-- gg -->
						        <th class="qb fw gzw td jt wxzc ts ww jj sb dw ">栽种树龄</th>
						        <th class="qb fw gzw td wxzc">外币种类</th>
						        <th class="fw gzw td wxzc">采购人</th>
						        <th class="qb fw gzw td jt wxzc zw">项目实验室负责人</th>
						        <th class="qb fw gzw td zw wxzc ww jj jt dw sb">图书册数</th>
						        <th class="qb fw gzw zw wxzc td ww">国别</th>
					 <!-- 90 --><th class="qb fw gzw td zw wxzc ww jj jt dw sb">档案号</th>
						        <th class="qb fw gzw zw wxzc td ww">科研项目</th>
						        <th class="qb fw gzw zw wxzc td ww">重点实验室</th>
						        <th class="qb fw gzw zw wxzc ts td dw ww">保修截止日期</th>
						        <th class="qb fw gzw zw wxzc ts td dw ww">品牌</th>
						        <th class="qb fw gzw zw wxzc ts td jj dw sb ww">排气量</th>
						        <th class="qb fw gzw zw wxzc ts td jj dw sb ww">车辆产地</th>
						        <th class="qb fw gzw zw wxzc ts td jj dw sb ww">编制情况</th>
						        <th class="qb fw gzw zw wxzc ts td jj dw sb ww">使用性质</th>
						        <th class="qb fw gzw zw wxzc ts td jj dw sb ww">车辆用途</th>
				    <!-- 100 --><th class="qb fw gzw zw wxzc ts td jj dw sb ww">持证日期</th><!-- fzrq -->
				    			<th class="qb fw gzw zw ts td jj dw sb ww jt">授权公告日</th><!-- fzrq -->
				                <th class="qb fw gzw jt zw ts td jj dw sb ww">评估价值</th>
				                <th class="qb fw gzw jt zw ts td jj dw sb ww">注册登记机关</th>
				                <th class="qb fw gzw jt zw ts td jj dw sb ww">注册登记日期</th>
				                <th class="qb fw gzw jt zw ts td jj dw sb ww">专利号</th>
				                <th class="qb fw gzw jt zw ts td jj dw sb ww">批准文号</th>
				                <th class="qb fw gzw jt zw ts td jj dw sb ww">管理机构</th>
				                <th class="qb fw gzw jt zw ts td jj dw sb ww">发明名称</th>
				                <th class="qb fw gzw jt zw ts td jj dw sb ww">年摊销额</th>
				                <th class="qb fw gzw jt zw ts td jj dw wxzc ww">功率</th>
						        <th>备注</th>
						    </tr>
			    		</thead>
				    <tbody>
				    </tbody>
				</table>
	        </div>
		</div>
	</div>
</div>
<div style="height:100px;border: 1px #b2c5d9 solid;overflow: auto;margin-left: 5px;margin-right: 5px;margin-top: 2px;" id="yzxx">&ensp;暂无校验信息！</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function (){
	//
	//列表数据
   	var columns = [
		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
           return '<input type="checkbox" yqbh='+full.YQBH+' class="keyId" id="id" name="zjbmx['+full._XH+'].guid" value="'+data+'">';
      	},"width":20,'searchable': false},
	   	{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   	   return data;
   		},"width":41,"searchable": false,"class":"text-center"},
	   {"data": "YQBH",defaultContent:"","render":function (data, type, full, meta){
        	return '<input type="text" id="yqbh" name="zjbmx['+full._XH+'].yqbh" class="form-control" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       }},//2
	   {"data": "YQMC",defaultContent:"","render":function (data, type, full, meta){
        	return '<input type="text" name="zjbmx['+full._XH+'].yqmc" class="form-control" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       }},//3
	   {"data": "FLH",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group"><input type="text" readonly class="form-control" style="width:80px;border:0" id="text_flh'+full._XH+'" name="zjbmx['+full._XH+'].flh" style="border:0" value="' +(undefined==data?'':data)+ '"/><span class="input-group-btn"><button type="button" class="btn btn-link btn-custom btn_flh">选择</button></span></div>';
	   }},//4
	   {"data": "FLMC",defaultContent:"","render":function (data, type, full, meta){
        	return '<input type="text" readonly id="flmc'+full._XH+'" class="form-control" name="zjbmx['+full._XH+'].flmc" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       }},//5
       {"data": "JZXS","render":function (data, type, full, meta){
		   return '<select id="text_jzxs'+full._XH+'" name="zjbmx['+full._XH+'].jzxs" class="form-control input-radius"><option value=""></option><c:forEach var="jzxslist" items="${jzxslist}"><option value="${jzxslist.DM}" '+("${jzxslist.DM}"==data? 'selected' : '')+'>${jzxslist.MC}</option></c:forEach></select>';
	   },'class':'qb'},//6
	   {"data": "DJ",defaultContent:"","render":function (data, type, full, meta){
        	return '<input type="text" name="zjbmx['+full._XH+'].dj" class="form-control text-right number" style="width:80px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'wxzc td gzw ww'},//7
       {"data": "ZZJ",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" name="zjbmx['+full._XH+'].zzj" class="form-control text-right number" style="width:80px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb fw zw ts jj dw  jt sb'},//7
       {"data": "ZJJT","render":function (data, type, full, meta){
		   return '<select id="text_zjjt'+full._XH+'" name="zjbmx['+full._XH+'].zjjt" class="form-control input-radius"><option value=""></option><c:forEach var="zjjtlist" items="${zjjtlist}"><option value="${zjjtlist.DM}" '+("${zjjtlist.DM}"==data? 'selected' : '')+'>${zjjtlist.MC}</option></c:forEach></select>';
	   },'class':'qb td wxzc'},//8
	   {"data": "LJZJ",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" name="zjbmx['+full._XH+'].ljzj" class="form-control text-right number" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb td wxzc'},//9
	   {"data": "SYR",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" id="text_syr'+full._XH+'" name="zjbmx['+full._XH+'].syr" style="border:0" value="'+(undefined==data?'':data)+'" onkeyup="check(this)"/><span class="input-group-btn"><button type="button" class="btn btn-link btn_syr">选择</button></span></div>';
	   }},//10
	   {"data": "SYDW",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" id="text_sydw'+full._XH+'" name="zjbmx['+full._XH+'].sydw" style="border:0" value="'+(undefined==data?'':data)+'" onkeyup="check(this)"/><span class="input-group-btn"><button type="button" class="btn btn-link btn-custom btn_sydw">选择</button></span></div>';
	   }},//11
	   {"data": "SYFX","render":function (data, type, full, meta){
		   return '<select id="text_syfx'+full._XH+'" name="zjbmx['+full._XH+'].syfx" class="form-control input-radius"><option value=""></option><c:forEach var="syfxlist" items="${syfxlist}"><option value="${syfxlist.DM}" '+("${syfxlist.DM}"==data? 'selected' : '')+'>${syfxlist.MC}</option></c:forEach></select>';
	   }},//12
	   {"data": "BZXX",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" id="text_bzxx'+full._XH+'" name="zjbmx['+full._XH+'].bzxx" style="border:0" value="'+(undefined==data?'':data)+'" onkeyup="check(this)"/><span class="input-group-btn"><button type="button" class="btn btn-link btn_bzxx">选择</button></span></div>';
	   },'class':'fw_gzw_td'},//13
	   {"data": "DZRRQ",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" class="form-control date" id="text_dzrrq'+full._XH+'" name="zjbmx['+full._XH+'].dzrrq" style="width:80px;border:0" data-format="yyyy-MM-dd" value="'+(undefined==data?'':data)+'"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>';
	   },'class':'qb dw'},//14
	   {"data": "GZRQ",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" class="form-control date" id="text_gzrq'+full._XH+'" name="zjbmx['+full._XH+'].gzrq" style="width:80px;border:0" data-format="yyyy-MM-dd" value="'+(undefined==data?'':data)+'"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>';
	   }},//15
	   {"data": "JLDW",defaultContent:"","render":function (data, type, full, meta){
		   return '<select id="text_jldw'+full._XH+'" name="zjbmx['+full._XH+'].jldw" class="form-control input-radius"><option value=""></option><c:forEach var="jldwlist" items="${jldwlist}"><option value="${jldwlist.DM}" '+("${jldwlist.DM}"==data? 'selected' : '')+'>${jldwlist.MC}</option></c:forEach></select>';
	   }},//16
	   {"data": "XZ",defaultContent:"","render":function (data, type, full, meta){
		   return '<select id="text_xz'+full._XH+'" name="zjbmx['+full._XH+'].xz" class="form-control input-radius"><option value=""></option><c:forEach var="xzlist" items="${xzlist}"><option value="${xzlist.DM}" '+("${xzlist.DM}"==data? 'selected' : '')+'>${xzlist.MC}</option></c:forEach></select>';
	   }},//17
	   {"data": "JFKM",defaultContent:"","render":function (data, type, full, meta){
		   return '<select id="text_jfkm'+full._XH+'" name="zjbmx['+full._XH+'].jfkm" class="form-control input-radius"><option value=""></option><c:forEach var="jfkmlist" items="${jfkmlist}"><option value="${jfkmlist.DM}" '+("${jfkmlist.DM}"==data? 'selected' : '')+'>${jfkmlist.MC}</option></c:forEach></select>';
	   }},//18
	   {"data": "ZCLY",defaultContent:"","render":function (data, type, full, meta){
		   return '<select id="text_zcly'+full._XH+'" name="zjbmx['+full._XH+'].zcly" class="form-control input-radius"><option value=""></option><c:forEach var="zclylist" items="${zclylist}"><option value="${zclylist.DM}" '+("${zclylist.DM}"==data? 'selected' : '')+'>${zclylist.MC}</option></c:forEach></select>';
	   }},//19
	   {"data": "RZRQ",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" class="form-control date" id="text_rzrq'+full._XH+'" name="zjbmx['+full._XH+'].rzrq" style="width:80px;border:0" data-format="yyyy-MM-dd" value="'+(undefined==data?'':data)+'"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>';
	   }},//20
	   {"data": "JZLX",defaultContent:"","render":function (data, type, full, meta){
		   return '<select id="text_jzlx'+full._XH+'" name="zjbmx['+full._XH+'].jzlx" class="form-control input-radius"><option value=""></option><c:forEach var="jzlxlist" items="${jzlxlist}"><option value="${jzlxlist.DM}" '+("${jzlxlist.DM}"==data? 'selected' : '')+'>${jzlxlist.MC}</option></c:forEach></select>';
	   }},//21
	   {"data": "CGZZXS",defaultContent:"","render":function (data, type, full, meta){
		   return '<select id="text_cgzzxs'+full._XH+'" name="zjbmx['+full._XH+'].cgzzxs" class="form-control input-radius"><option value=""></option><c:forEach var="cgzzxslist" items="${cgzzxslist}"><option value="${cgzzxslist.DM}" '+("${cgzzxslist.DM}"==data? 'selected' : '')+'>${cgzzxslist.MC}</option></c:forEach></select>';
	   }},//22
	   {"data": "XMH",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].xmh" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb ts'},//23
	   {"data": "PZH",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].pzh" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb ts'},//24
       {"data": "CCRQ",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" class="form-control date" id="text_ccrq'+full._XH+'" name="zjbmx['+full._XH+'].ccrq" style="width:80px;border:0" data-format="yyyy-MM-dd" value="'+(undefined==data?'':data)+'"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>';
	   },'class':'qb td zw ww ts jj dw jt wxzc sb'},//25
	   {"data": "CCRQ",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" class="form-control date" id="text_ccrq'+full._XH+'" name="zjbmx['+full._XH+'].ccrq" style="width:80px;border:0" data-format="yyyy-MM-dd" value="'+(undefined==data?'':data)+'"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>';
	   },'class':'qb fw gzw td zw ww jj dw jt wxzc sb'},//25
	   {"data": "CCRQ",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" class="form-control date" id="text_ccrq'+full._XH+'" name="zjbmx['+full._XH+'].ccrq" style="width:80px;border:0" data-format="yyyy-MM-dd" value="'+(undefined==data?'':data)+'"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>';
	   },'class':'qb fw gzw td ts zw ww dw wxzc sb'},//25
       {"data": "QSRQ",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" class="form-control date" id="text_qsrq'+full._XH+'" name="zjbmx['+full._XH+'].qsrq" style="width:80px;border:0" data-format="yyyy-MM-dd" value="'+(undefined==data?'':data)+'"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>';
	   },'class':'qb zw ww ts dw '},//26
	   {"data": "SYNX",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control text-right int" name="zjbmx['+full._XH+'].synx" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb fw gzw td zw ww ts dw wxzc'},//27
       {"data": "SYNX",defaultContent:"","render":function (data, type, full, meta){
          	return '<input type="text" class="form-control text-right int" name="zjbmx['+full._XH+'].synx" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
          },'class':'qb fw gzw td zw ww jj ts jt wxzc sb'},//27
	   {"data": "CZR",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].czr" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb gzw td zw ww ts jj dw jt wxzc sb'},//28
       {"data": "CZR",defaultContent:"","render":function (data, type, full, meta){
        return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].czr" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb fw gzw td zw dw ww jj wxzc sb ts'},//28
       {"data": "CZR",defaultContent:"","render":function (data, type, full, meta){
        return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].czr" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb fw gzw td zw dw ww jj jt sb ts'},//28
	   {"data": "TPH",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control text-right int" name="zjbmx['+full._XH+'].tph" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb gzw td zw dw ww jj jt sb ts wxzc'},//29
       {"data": "TPH",defaultContent:"","render":function (data, type, full, meta){
        return '<input type="text" class="form-control text-right int" name="zjbmx['+full._XH+'].tph" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb fw gzw td zw ww jj jt sb ts wxzc'},//29
	   {"data": "SCCJ",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].sccj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb gzw td zw ww jj jt sb ts dw wxzc'},//30
       {"data": "SCCJ",defaultContent:"","render":function (data, type, full, meta){
          	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].sccj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
          },'class':'qb fw gzw td zw jj jt sb ts dw wxzc'},//30
          {"data": "SCCJ",defaultContent:"","render":function (data, type, full, meta){
             	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].sccj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
             },'class':'qb fw gzw td zw jj jt sb ww dw wxzc'},//30
             {"data": "SCCJ",defaultContent:"","render":function (data, type, full, meta){
                	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].sccj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
                },'class':'qb fw gzw td zw ts sb ww dw wxzc'},//30
                {"data": "SCCJ",defaultContent:"","render":function (data, type, full, meta){
                   	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].sccj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
                   },'class':'qb fw gzw td zw ww jj jt sb ts wxzc'},//30
	   {"data": "TDDJ",defaultContent:"","render":function (data, type, full, meta){
		return '<select id="text_tddj'+full._XH+'" name="zjbmx['+full._XH+'].tddj" class="form-control input-radius"><option value=""></option><c:forEach var="tddjlist" items="${tddjlist}"><option value="${tddjlist.DM}" '+("${tddjlist.DM}"==data? 'selected' : '')+'>${tddjlist.MC}</option></c:forEach></select>';
       },'class':'qb gzw td zw ww jj jt sb ts wxzc dw'},//31，完损状况
       {"data": "TDDJ",defaultContent:"","render":function (data, type, full, meta){
          	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].tddj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
          },'class':'qb fw td zw jj jt sb ww dw wxzc ts'},//31,安全等级
          {"data": "TDDJ",defaultContent:"","render":function (data, type, full, meta){
             	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].tddj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
             },'class':'qb fw gzw  zw jj jt sb ww dw wxzc ts'},//31,土地等级
	   {"data": "HTBH",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].htbh" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb ww ts jj dw jt sb wxzc'},//32
       {"data": "HTBH",defaultContent:"","render":function (data, type, full, meta){
          	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].htbh" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
          },'class':'qb fw gzw td zw wxzc ts jj dw jt sb'},//32
          {"data": "HTBH",defaultContent:"","render":function (data, type, full, meta){
             	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].htbh" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
             },'class':'qb fw gzw td zw wxzc ww sb'},//32
	   {"data": "XSS",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].xss" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb wxzc sb ts jj jt dw ww'},//33
       {"data": "XSS",defaultContent:"","render":function (data, type, full, meta){
          	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].xss" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
          },'class':'qb fw gzw ts zw jj jt sb ww dw wxzc'},//33
          {"data": "XSS",defaultContent:"","render":function (data, type, full, meta){
             	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].xss" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
             },'class':'qb fw gzw td ts zw jj jt sb dw wxzc'},//33
             {"data": "XSS",defaultContent:"","render":function (data, type, full, meta){
                	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].xss" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
                },'class':'qb fw gzw td zw ts zw sb ww wxzc'},//33
       {"data": "QSXZ",defaultContent:"","render":function (data, type, full, meta){
		   return '<select id="text_qsxz'+full._XH+'" name="zjbmx['+full._XH+'].qsxz" class="form-control input-radius"><option value=""></option><c:forEach var="qsxzlist" items="${qsxzlist}"><option value="${qsxzlist.DM}" '+("${qsxzlist.DM}"==data? 'selected' : '')+'>${qsxzlist.MC}</option></c:forEach></select>';
	   },'class':'qb td zw jj jt sb ww dw wxzc ts'},//34
       {"data": "FGXS",defaultContent:"","render":function (data, type, full, meta){
		   return '<select id="text_fgxs'+full._XH+'" name="zjbmx['+full._XH+'].fgxs" class="form-control input-radius"><option value=""></option><c:forEach var="fgxslist" items="${fgxslist}"><option value="${fgxslist.DM}" '+("${fgxslist.DM}"==data? 'selected' : '')+'>${fgxslist.MC}</option></c:forEach></select>';
	   },'class':'qb td zw jj jt sb ww dw wxzc ts'},//35
       {"data": "CQXS",defaultContent:"","render":function (data, type, full, meta){
		   return '<select id="text_cqxs'+full._XH+'" name="zjbmx['+full._XH+'].cqxs" class="form-control input-radius"><option value=""></option><c:forEach var="cqxslist" items="${cqxslist}"><option value="${cqxslist.DM}" '+("${cqxslist.DM}"==data? 'selected' : '')+'>${cqxslist.MC}</option></c:forEach></select>';
	   },'class':'qb zw jj jt sb ww dw wxzc ts'},//36
	   {"data": "CQLY",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].cqly" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb td zw jj jt sb  dw wxzc ts ww'},//37
       {"data": "CQLY",defaultContent:"","render":function (data, type, full, meta){
          	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].cqly" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
          },'class':'qb fw gzw td zw wxzc ts jj dw jt sb'},//37
	   {"data": "FW_QSZM",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].fw_qszm" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb gzw td zw ww jj jt sb ts wxzc dw'},//38
	   {"data": "JSH",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].jsh" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb td zw ww jj jt  ts wxzc dw'},//39，权属证号（房屋、构筑物）
       {"data": "JSH",defaultContent:"","render":function (data, type, full, meta){
          	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].jsh" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
          },'class':'qb fw gzw zw wxzc ts jj dw jt sb ww'},//39
          {"data": "JSH",defaultContent:"","render":function (data, type, full, meta){
             	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].jsh" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
             },'class':'qb fw gzw zw wxzc ts jj dw jt sb td'},//39
	   {"data": "ZMMJ",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].zmmj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb td gzw zw wxzc ts jj dw jt sb ww'},//40
       {"data": "ZMMJ",defaultContent:"","render":function (data, type, full, meta){
          	return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].zmmj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
          },'class':'qb fw gzw zw ww jj jt sb ts wxzc dw'},//40
       {"data": "FW_FZRQ",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" class="form-control date" id="text_fw_fzrq'+full._XH+'" name="zjbmx['+full._XH+'].fw_fzrq" style="width:80px;border:0" data-format="yyyy-MM-dd" value="'+(undefined==data?'':data)+'"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>';
	   },'class':'qb gzw td zw ww jj jt sb ts wxzc dw'},//41
	   {"data": "JCNF",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" class="form-control year" id="text_jcnf'+full._XH+'" name="zjbmx['+full._XH+'].jcnf" style="width:80px;border:0" data-format="yyyy-MM-dd" value="'+(undefined==data?'':data)+'"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>';
       },'class':'qb td wxzc ts jj jt sb ww zw dw'},//42
       {"data": "JCNF",defaultContent:"","render":function (data, type, full, meta){
    	   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" class="form-control year" id="text_jcnf'+full._XH+'" name="zjbmx['+full._XH+'].jcnf" style="width:80px;border:0" data-format="yyyy-MM-dd" value="'+(undefined==data?'':data)+'"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>';
       },'class':'qb fw gzw td ww wxzc ts jj dw jt sb'},//42
       {"data": "JCNF",defaultContent:"","render":function (data, type, full, meta){
        return '<div class="input-group" style="margin-bottom:0px;"><input type="text" class="form-control year" id="text_jcnf'+full._XH+'" name="zjbmx['+full._XH+'].jcnf" style="width:80px;border:0" data-format="yyyy-MM-dd" value="'+(undefined==data?'':data)+'"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>';
       },'class':'qb fw gzw td ww wxzc ts jj zw jt sb'},//42
	   {"data": "FWYT",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].fwyt" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb gzw td zw ww wxzc ts jj dw jt sb'},//43
       {"data": "ZJ",defaultContent:"","render":function (data, type, full, meta){
		   return '<select id="text_zj'+full._XH+'" name="zjbmx['+full._XH+'].zj" class="form-control input-radius"><option value=""></option><c:forEach var="zjlist" items="${zjlist}"><option value="${zjlist.DM}" '+("${zjlist.DM}"==data? 'selected' : '')+'>${zjlist.MC}</option></c:forEach></select>';
	   },'class':'qb td zw ww wxzc ts jj dw jt sb'},//44
	   {"data": "DSMJ",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].dsmj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb td zw ww wxzc ts jj dw jt sb'},//45
	   {"data": "DXMJ",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].dxmj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb td zw ww wxzc ts jj dw jt sb'},//46
	   {"data": "FW_ZRJS",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control text-right int" name="zjbmx['+full._XH+'].fw_zrjs" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb gzw td zw ww wxzc ts jj dw jt sb'},//47
	   {"data": "GZYQ",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].gzyq" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb td zw ww wxzc ts jj dw jt sb'},//48
       {"data": "GZYQ",defaultContent:"","render":function (data, type, full, meta){
          	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].gzyq" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
          },'class':'qb fw gzw td zw ww jj jt ts wxzc dw'},//48
          {"data": "GZYQ",defaultContent:"","render":function (data, type, full, meta){
             	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].gzyq" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
             },'class':'qb fw gzw sb zw ww jj jt ts wxzc dw'},//48
             {"data": "GZYQ",defaultContent:"","render":function (data, type, full, meta){
                	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].gzyq" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
                },'class':'qb fw gzw td sb ww jj jt ts wxzc dw'},//48
                {"data": "GZYQ",defaultContent:"","render":function (data, type, full, meta){
                   	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].gzyq" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
                   },'class':'qb fw gzw td zw sb jj jt ts wxzc dw'},//48
	   {"data": "FJZJ",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].fjzj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb td zw ww wxzc ts jj dw jt sb'},//49
       {"data": "FJZJ",defaultContent:"","render":function (data, type, full, meta){
          	return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].fjzj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
          },'class':'qb fw gzw ww zw sb jj jt ts wxzc dw'},//49
	   {"data": "FW_ZYMJ",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].fw_zymj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb gzw td ww zw sb jj jt ts wxzc dw'},//50
	   {"data": "ZYJZ",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].zyjz" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb gzw ww zw sb jj jt ts wxzc dw'},//51
       {"data": "XH",defaultContent:"","render":function (data, type, full, meta){
    	   return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].xh" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw td gzw ww zw wxzc dw'},//52
	   {"data": "XH",defaultContent:"","render":function (data, type, full, meta){
		   return '<select id="text_xh'+full._XH+'" name="zjbmx['+full._XH+'].xh" class="form-control input-radius"><option value=""></option><c:forEach var="xhlist" items="${xhlist}"><option value="${xhlist.DM}" '+("${xhlist.DM}"==data? 'selected' : '')+'>${xhlist.MC}</option></c:forEach></select>';
	   },'class':'qb gzw td zw ww wxzc ts jj dw jt sb'},//52
	   {"data": "XH",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" class="form-control year" id="text_xh'+full._XH+'" name="zjbmx['+full._XH+'].xh" style="width:80px;border:0" data-format="yyyy-MM-dd" value="'+(undefined==data?'':data)+'"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>';
	   },'class':'qb fw gzw td zw sb jj jt ts wxzc dw'},//52
	   {"data": "FW_CJMJ",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].fw_cjmj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb gzw td zw sb jj jt ts wxzc dw ww'},//53
	   {"data": "WBJE",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].wbje" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb fw gzw td wxzc jt sb jj'},//54，外币单价
       {"data": "WBJE",defaultContent:"","render":function (data, type, full, meta){
          	return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].wbje" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
          },'class':'qb gzw td zw ww wxzc ts jj dw jt sb'},//54
          {"data": "WBJE",defaultContent:"","render":function (data, type, full, meta){
             	return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].wbje" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
             },'class':'qb fw gzw td zw ww wxzc ts jj dw  sb'},//54
	   {"data": "FW_CZMJ",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].fw_czmj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb gzw td zw ww wxzc ts jj dw jt sb'},//55
	   {"data": "CZJZ",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].czjz" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb gzw  zw ww wxzc ts jj dw jt sb'},//56
	   {"data": "FW_GNMJ",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].fw_gnmj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb td zw ww wxzc ts jj dw jt sb'},//57
	   {"data": "FW_JYMJ",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].fw_jymj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb gzw td zw ww wxzc ts jj dw jt sb'},//58
	   {"data": "SYMJ",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].symj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb td zw ww wxzc ts jj dw jt sb'},//59
	   {"data": "FW_XZMJ",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].fw_xzmj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb gzw td zw ww wxzc ts jj dw jt sb'},//60
	   {"data": "XZJZ",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].xzjz" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb gzw td zw ww wxzc ts jj dw jt sb'},//61
	   {"data": "CCH",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].cch" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb gzw td zw ww wxzc ts jj dw jt sb'},//62
       {"data": "CCH",defaultContent:"","render":function (data, type, full, meta){
          	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].cch" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
          },'class':'qb fw gzw td zw wxzc ts jj jt dw sb'},//62
          {"data": "CCH",defaultContent:"","render":function (data, type, full, meta){
             	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].cch" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
             },'class':'qb fw gzw td zw wxzc ww jj jt dw sb'},//62
             {"data": "CCH",defaultContent:"","render":function (data, type, full, meta){
                	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].cch" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
                },'class':'qb fw gzw td zw wxzc ts ww jt dw sb'},//62
                {"data": "CCH",defaultContent:"","render":function (data, type, full, meta){
                   	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].cch" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
                   },'class':'qb fw gzw td zw wxzc ts ww jj dw sb'},//62
	   {"data": "FW_QTMJ",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].fw_qtmj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb td gzw zw wxzc ts ww jj dw sb jt'},//63,其他面积，房屋的
       {"data": "TD_QTMJ",defaultContent:"","render":function (data, type, full, meta){
        return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].td_qtmj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb fw gzw zw wxzc ts ww jj dw sb jt'},//63，其他面积，土地的
	   {"data": "QTJZ",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].qtjz" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb gzw zw wxzc ts ww jj dw sb jt'},//64
	   {"data": "JKDJ",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control text-right" name="zjbmx['+full._XH+'].jkdj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb fw gzw td wxzc ts'},//65
       {"data": "JKDJ",defaultContent:"","render":function (data, type, full, meta){
          	return '<input type="text" class="form-control text-right" name="zjbmx['+full._XH+'].jkdj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
          },'class':'qb td zw wxzc ts ww jt dw jj sb'},//65
          {"data": "JKDJ",defaultContent:"","render":function (data, type, full, meta){
             	return '<input type="text" class="form-control text-right" name="zjbmx['+full._XH+'].jkdj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
             },'class':'qb fw gzw td zw wxzc ww jt dw jj sb'},//65
	   {"data": "ZYYT",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].zyyt" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb td zw wxzc ts ww jt dw jj sb'},//66
       {"data": "XKLB",defaultContent:"","render":function (data, type, full, meta){
		   return '<select id="text_xklb'+full._XH+'" name="zjbmx['+full._XH+'].xklb" class="form-control input-radius"><option value=""></option><c:forEach var="xklblist" items="${xklblist}"><option value="${xklblist.DM}" '+("${xklblist.DM}"==data? 'selected' : '')+'>${xklblist.MC}</option></c:forEach></select>';
	   },'class':'qb fw td wxzc jt'},//67
	   {"data": "XK",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" id="text_xks'+full._XH+'" name="zjbmx['+full._XH+'].xk" style="border:0" value="'+(undefined==data?'':data)+'" onkeyup="check(this)"/><span class="input-group-btn"><button type="button" class="btn btn-link btn_xk">选择</button></span></div>';
	   },'class':'qb fw td wxzc jt'},//68
	   {"data": "TD_RZKM",defaultContent:"","render":function (data, type, full, meta){
		   return '<select id="text_td_rzkm'+full._XH+'" name="zjbmx['+full._XH+'].td_rzkm" class="form-control input-radius"><option value=""></option><c:forEach var="td_rzkmlist" items="${td_rzkmlist}"><option value="${td_rzkmlist.DM}" '+("${td_rzkmlist.DM}"==data? 'selected' : '')+'>${td_rzkmlist.MC}</option></c:forEach></select>';
	   },'class':'qb fw gzw zw wxzc ts ww jj dw sb jt'},//69
	   {"data": "TD_QSNX",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control text-right int" name="zjbmx['+full._XH+'].td_qsnx" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw zw wxzc ts ww jj dw sb jt'},//权属年限
	   {"data": "TD_QSXZ",defaultContent:"","render":function (data, type, full, meta){
		   return '<select id="text_td_qsxz'+full._XH+'" name="zjbmx['+full._XH+'].td_qsxz" class="form-control input-radius"><option value=""></option><c:forEach var="td_qsxzlist" items="${td_qsxzlist}"><option value="${td_qsxzlist.DM}" '+("${td_qsxzlist.DM}"==data? 'selected' : '')+'>${td_qsxzlist.MC}</option></c:forEach></select>';
	   },'class':'qb fw gzw zw wxzc ts ww jj dw sb jt'},//70
	   {"data": "TD_QSZM",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].td_qszm" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw zw wxzc ts ww jj dw sb jt'},//71
	   {"data": "DJH",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].djh" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw jt zw wxzc ts ww jj dw sb'},//72
	   {"data": "DJH",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].djh" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw td jt zw wxzc ts  jj dw sb'},//72
	   {"data": "DJH",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].djh" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw jt zw td ts ww jj dw sb'},//72
	   {"data": "DJH",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].djh" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw td zw wxzc ww'},//72
	   {"data": "TD_DYMJ",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].td_dymj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw jt zw wxzc ts ww jj dw sb'},//73
	   {"data": "TD_FZRQ",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" class="form-control date" id="text_td_fzrq'+full._XH+'" name="zjbmx['+full._XH+'].td_fzrq" style="width:80px;border:0" data-format="yyyy-MM-dd" value="'+(undefined==data?'':data)+'"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>';
	   },'class':'qb fw gzw jt zw wxzc ts ww jj dw sb'},//74
	   {"data": "TD_FTMJ",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].td_ftmj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw jt zw wxzc ts ww jj dw sb'},//75
	   {"data": "TD_YT",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].td_yt" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw jj gzw jt zw wxzc ts ww dw'},//76,土地
	   {"data": "YT",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].yt" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw td gzw jt zw wxzc ts ww dw'},//76,家具
	   {"data": "TD_ZYMJ",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].td_zymj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw jt zw wxzc ts ww jj dw sb'},//77
	   {"data": "TD_CZMJ",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].td_czmj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw jt zw wxzc ts ww jj dw sb'},//78
	   {"data": "TD_CJMJ",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].td_cjmj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw jt zw wxzc ts ww jj dw sb'},//79
	   {"data": "CJJZ",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].cjjz" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw jt zw wxzc ts ww jj dw sb'},//80
	   {"data": "TD_JYMJ",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].td_jymj" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw jt zw wxzc ts ww jj dw sb'},//81
	   {"data": "JYJZ",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].jyjz" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw jt zw wxzc ts ww jj dw sb'},//82
	   {"data": "GG",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].gg" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw td jt wxzc ts ww jj sb'},//83
	   {"data": "GG",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].gg" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw jt zw wxzc ts td jj dw sb'},//83
	   {"data": "GG",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].gg" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw jt zw wxzc td dw sb ww'},//83
	   {"data": "FJS",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control text-right" name="zjbmx['+full._XH+'].fjs" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw td jt wxzc ts ww jj sb dw'},//84
	   {"data": "WBZL","render":function (data, type, full, meta){
		   return '<select id="text_wbzl'+full._XH+'" name="zjbmx['+full._XH+'].wbzl" class="form-control input-radius"><option value=""></option><c:forEach var="wbzllist" items="${wbzllist}"><option value="${wbzllist.DM}" '+("${wbzllist.DM}"==data? 'selected' : '')+'>${wbzllist.MC}</option></c:forEach></select>';
	   },'class':'qb fw gzw td wxzc'},//85
	   {"data": "CGR",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" id="text_cgr'+full._XH+'" name="zjbmx['+full._XH+'].cgr" style="border:0" value="'+(undefined==data?'':data)+'" onkeyup="check(this)"/><span class="input-group-btn"><button type="button" class="btn btn-link btn_cgr">选择</button></span></div>';
       },'class':'fw gzw td wxzc'},//86
	   {"data": "FZR",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].fzr" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb fw gzw td jt wxzc zw'},//87
	   {"data": "SYKH",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].sykh" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb fw gzw td zw wxzc ww jj jt dw sb'},//88
       {"data": "GBM",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" id="text_gbm'+full._XH+'" name="zjbmx['+full._XH+'].gbm" style="border:0" value="'+(undefined==data?'':data)+'" onkeyup="check(this)"/><span class="input-group-btn"><button type="button" class="btn btn-link btn-custom btn_gbm">选择</button></span></div>';
	   },'class':'qb fw gzw zw wxzc td ww'},//89
	   {"data": "DABH",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].dabh" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb fw gzw td zw wxzc ww jj jt dw sb'},//90
	   {"data": "KYXM",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].kyxm" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb fw gzw zw wxzc td ww'},//91
       {"data": "ZDSYS",defaultContent:"","render":function (data, type, full, meta){
		   return '<select id="text_zdsys'+full._XH+'" name="zjbmx['+full._XH+'].zdsys" class="form-control input-radius"><option value=""></option><c:forEach var="zdsyslist" items="${zdsyslist}"><option value="${zdsyslist.DM}" '+("${zdsyslist.DM}"==data? 'selected' : '')+'>${zdsyslist.MC}</option></c:forEach></select>';
	   },'class':'qb fw gzw zw wxzc td ww'},//92
	   {"data": "BXJZRQ",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" class="form-control date" id="text_bxjzrq'+full._XH+'" name="zjbmx['+full._XH+'].bxjzrq" style="width:80px;border:0" data-format="yyyy-MM-dd" value="'+(undefined==data?'':data)+'"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>';
	   },'class':'qb fw gzw zw wxzc ts td dw ww'},//93
	   {"data": "PP",defaultContent:"","render":function (data, type, full, meta){
       	   return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].pp" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb fw gzw zw wxzc ts td dw ww'},//94
	   {"data": "JT_PQL",defaultContent:"","render":function (data, type, full, meta){
       	   return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].jt_pql" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb fw gzw zw wxzc ts td jj dw sb ww'},//95
	   {"data": "JT_CLCD",defaultContent:"","render":function (data, type, full, meta){
       	   return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].jt_clcd" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       },'class':'qb fw gzw zw wxzc ts td jj dw sb ww'},//96
	   {"data": "JT_CLBZ",defaultContent:"","render":function (data, type, full, meta){
		   return '<select id="text_jt_bzqk'+full._XH+'" name="zjbmx['+full._XH+'].jt_clbz" class="form-control input-radius"><option value=""></option><c:forEach var="jt_bzqklist" items="${jt_bzqklist}"><option value="${jt_bzqklist.DM}" '+("${jt_bzqklist.DM}"==data? 'selected' : '')+'>${jt_bzqklist.MC}</option></c:forEach></select>';
       },'class':'qb fw gzw zw wxzc ts td jj dw sb ww'},//97编制情况
       {"data": "JT_SYXZ","render":function (data, type, full, meta){
    	   return '<select id="text_syxz'+full._XH+'" name="zjbmx['+full._XH+'].jt_syxz" class="form-control input-radius"><option value=""></option><c:forEach var="jt_syxzlist" items="${jt_syxzlist}"><option value="${jt_syxzlist.DM}" '+("${jt_syxzlist.DM}"==data? 'selected' : '')+'>${jt_syxzlist.MC}</option></c:forEach></select>';
	   },'class':'qb fw gzw zw wxzc ts td jj dw sb ww'},//98  //使用性质
       {"data": "SYXZ","render":function (data, type, full, meta){//车辆用途
		   return '<select id="text_syxz'+full._XH+'" name="zjbmx['+full._XH+'].syxz" class="form-control input-radius"><option value=""></option><c:forEach var="syxzlist" items="${syxzlist}"><option value="${syxzlist.DM}" '+("${syxzlist.DM}"==data? 'selected' : '')+'>${syxzlist.MC}</option></c:forEach></select>';
	   },'class':'qb fw gzw zw wxzc ts td jj dw sb ww'},//99   
	   {"data": "FZRQ",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" class="form-control date" id="text_fzrq'+full._XH+'" name="zjbmx['+full._XH+'].fzrq" style="width:80px;border:0" data-format="yyyy-MM-dd" value="'+(undefined==data?'':data)+'"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>';
	   },'class':'qb fw gzw zw wxzc ts td jj dw sb ww'},//100
	   {"data": "FZRQ",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" class="form-control date" id="text_fzrq'+full._XH+'" name="zjbmx['+full._XH+'].fzrq" style="width:80px;border:0" data-format="yyyy-MM-dd" value="'+(undefined==data?'':data)+'"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>';
	   },'class':'qb fw gzw zw ts td jj dw sb ww jt'},//100
	   {"data": "WX_PGJZ",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].wx_pgjz" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw jt zw ts td jj dw sb ww'},//101
	   {"data": "WX_DJJG",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].wx_djjg" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw jt zw ts td jj dw sb ww'},//102
	   {"data": "WX_DJRQ",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" class="form-control date" id="text_wx_djrq'+full._XH+'" name="zjbmx['+full._XH+'].wx_djrq" style="width:80px;border:0" data-format="yyyy-MM-dd" value="'+(undefined==data?'':data)+'"/><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span></div>';
	   },'class':'qb fw gzw jt zw ts td jj dw sb ww'},//103
	   {"data": "WX_ZLH",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].wx_zlh" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw jt zw ts td jj dw sb ww'},//104
	   {"data": "WX_PZWH",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].wx_pzwh" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw jt zw ts td jj dw sb ww'},//105
	   {"data": "WX_GLJG",defaultContent:"","render":function (data, type, full, meta){
		   return '<div class="input-group" style="margin-bottom:0px;"><input type="text" id="text_wx_gljg'+full._XH+'" name="zjbmx['+full._XH+'].wx_gljg" style="border:0" value="'+(undefined==data?'':data)+'" onkeyup="check(this)"/><span class="input-group-btn"><button type="button" class="btn btn-link btn-custom btn_sydw">选择</button></span></div>';
	       //return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].wx_gljg" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw jt zw ts td jj dw sb ww'},//106
	   {"data": "WX_FMMC",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].wx_fmmc" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw jt zw ts td jj dw sb ww'},//107
	   {"data": "WX_NTXE",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control text-right number" name="zjbmx['+full._XH+'].wx_ntxe" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw jt zw ts td jj dw sb ww'},//108
	   {"data": "SB_GL",defaultContent:"","render":function (data, type, full, meta){
	       return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].sb_gl" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
	   },'class':'qb fw gzw jt zw ts td jj dw sb ww'},//109
	   {"data": "BZ",defaultContent:"","render":function (data, type, full, meta){
       	return '<input type="text" class="form-control" name="zjbmx['+full._XH+'].bz" style="width:100px;border:0" value="' + (undefined==data?'':data) + '" onkeyup="check(this)"/>';
       }}//110
	];
   	table = getDataTableByListHj("mydatatables","${pageContext.request.contextPath}/yssjzl/getPageList",[2,'asc,GUID asc'],columns,0,1,setGroup,"yssjzl");
   	table.columns('.qb,.fw,.gzw,.td,.zw,.ww,.ts,.jj,.dw,.jt,.wxzc,.sb').visible(false);
   	//保存
   	$("#btn_save").click(function(){
   		var checkbox = $("#mydatatables").find("[id='id']").filter(":checked");
   		if(checkbox.length>0){
   			var content = "";
   	   		$(".selected").each(function(){
   	   			$(this).find("input,select").each(function(){
   	   				content += $(this).attr("name")+"="+$(this).val().trim()+"&";
   	   			});
   	   		});
   	   		var index;
  	   			$.ajax({
  	   	   			type:"post",
  	   	   			url:"${pageContext.request.contextPath}/yssjzl/doSave",
  	   	   			data:content.substring(0,content.length-1),
	   	   	   		success:function(val){
	   	   	  		var val = JSON.getJson(val);
	   	   	  		if(val.success){
	   	   	  			table.ajax.reload();
	   	   	  			alert(val.msg);
	   	   	  			close(index);
	   	   	  		}else{
	   	   	  			alert(val.msg);
	   	   	  		}
	   	   	   	}
 	   				});
   		}else{
   			alert("未选择任何记录！");
   		}
   	});
  	//审核按钮
   	$("#btn_sh").click(function(){
   		var index;
   		var lx = $(".checkbox").find(".active").attr("data-value");
   		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/yssjzl/doShcheck",
			data:"type="+lx,
			success:function(val){
				close(index);
				var data = JSON.getJson(val);
				if(data.success){
					alert("数据验证通过!");
				}else{
					alert("数据验证未通过，原因详见列表!");
				}
				$("div[id=yzxx]").empty();
				$("div[id=yzxx]").append(data.msg);
			},
			error:function(){
				close(index);
				alert(getPubErrorMsg());
			},
			beforeSend:function(){
				index = loading(2);
			}
		});
   	});
	//批量删除按钮
   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[id='id']").filter(":checked");
   		if(checkbox.length>0){
   			var id = [];
   			checkbox.each(function(){
   				id.push($(this).val());
   			});
   			doDel("id="+id.join(","),"${pageContext.request.contextPath}/yssjzl/doDelete",function(val){
  	   	   			table.ajax.reload();
  	   	   		},function(val){
  	   	   			
  	   	   		},id.length);
   		}else{
   			alert("请选择至少一条信息删除！");
   		}
   	});
	//清空按钮
	$("#btn_delxx").click(function(){
		confirm("确定要清空数据吗？",{title:"提示"},function(){
			 $.ajax({
				type:"post",
				url:"${pageContext.request.contextPath}/yssjzl/doDel",
				success:function(val){
					close(index);
					table.ajax.reload();
					var data = JSON.getJson(val);
					if(data.success){
						alert(data.msg);
					}else{
						alert(data.msg);
					}
				},
				error:function(){
					close(index);
					alert(getPubErrorMsg());
				},
				beforeSend:function(){
					index = loading(2);
				}
			});
		}); 
	});
	//入账按钮
	$("#btn_rz").click(function(){
   		var checkbox = $("#mydatatables").find("[id='id']").filter(":checked");
   		if(checkbox.length>0){
   			var id = [];
   			checkbox.each(function(){
   				id.push($(this).val());
   			});
   			var index;
   	   		var lx = $(".checkbox").find(".active").attr("data-value");
   	   		$.ajax({
   				type:"post",
   				url:"${pageContext.request.contextPath}/yssjzl/doShcheck",
   				data:"type="+lx,
   				success:function(val){
   					close(index);
   					var data = JSON.getJson(val);
  	 				if(data.success){
	  	 		   		 $.ajax({
	  	 		 			type:"post",
	  	 		 			data:"id="+id.join(","),
	  	 		 			url:"${pageContext.request.contextPath}/yssjzl/doRz",
	  	 		 			success:function(val){
	  	 		 				close(index);
	  	 		 				table.ajax.reload();
	  	 		 				var data = JSON.getJson(val);
	  	 		 				if(data.success){
	  	 		 					alert(data.msg);
	  	 		 				}else{
	  	 		 					alert(data.msg);
	  	 		 				}
	  	 		 			},
	  	 		 			error:function(){
	  	 		 				close(index);
	  	 		 				alert(getPubErrorMsg());
	  	 		 			},
	  	 		 			beforeSend:function(){
	  	 		 				index = loading(2);
	  	 		 			}
	  	 		 		});
  	 				}else{
  	 					alert("数据验证未通过，原因详见列表！");
  	 					$("div[id=yzxx]").empty();
  	 					$("div[id=yzxx]").append(data.msg);
  	 				}
   				},
   				error:function(){
   					close(index);
   					alert(getPubErrorMsg());
   				},
   				beforeSend:function(){
   					index = loading(2);
   				}
   			});
   		}else{
   			alert("请选择至少一条信息进行操作！");
   		}
   	});
	//提交按钮
	$("#btn_submit").click(function(){
   		var checkbox = $("#mydatatables").find("[id='id']").filter(":checked");
   		if(checkbox.length>0){
   			var id = [];
   			checkbox.each(function(){
   				id.push($(this).attr("yqbh"));
   			});
   			var index;
   	   		var lx = $(".checkbox").find(".active").attr("data-value");
   	   		$.ajax({
   				type:"post",
   				url:"${pageContext.request.contextPath}/yssjzl/doShcheck",
   				data:"type="+lx+"&tj=1",
   				success:function(val){
   					close(index);
   					var data = JSON.getJson(val);
  	 				if(data.success){
  	 					   doSub("&mkbh=${mkbh}&keyid="+id.join(","),"${pageContext.request.contextPath}/shenhe/doSubmit", function(val){
				   			table.ajax.reload();
				   		}, function(val){
				   		}, "1");  
  	 				}else{
  	 					alert("数据验证未通过，原因详见列表！");
  	 					$("div[id=yzxx]").empty();
  	 					$("div[id=yzxx]").append(data.msg);
  	 				}
   				},
   				error:function(){
   					close(index);
   					alert(getPubErrorMsg());
   				},
   				beforeSend:function(){
   					index = loading(2);
   				}
   			});
   		}else{
   			alert("请选择至少一条信息进行操作！");
   		}
   	});
	//使用单位
	$(document).on("click",".btn_sydw",function(){
		var id = $(this).parents("tr").find("[id^=text_sydw]").attr("id");
		select_commonWin("${pageContext.request.contextPath}/zcfltree/goZjbWindow?controlId="+id,"单位信息","920","630");
    });
	//使用人
	$(document).on("click",".btn_syr",function(){
		var id = $(this).parents("tr").find("[id^=text_syr]").attr("id");
		select_commonWin("${pageContext.request.contextPath}/webView/window/ryxx/window.jsp?controlId="+id,"人员信息","920","630");
    });
	//采购人
	$(document).on("click",".btn_cgr",function(){
		var id = $(this).parents("tr").find("[id^=text_cgr]").attr("id");
		select_commonWin("${pageContext.request.contextPath}/webView/window/ryxx/window.jsp?controlId="+id,"人员信息","920","630");
    });
	//存放地点
	$(document).on("click",".btn_bzxx",function(){
		var id = $(this).parents("tr").find("[id^=text_bzxx]").attr("id");
		select_commonWin("${pageContext.request.contextPath}/webView/window/ddxx/window.jsp?controlId="+id,"地点信息","920","630");
    });
	//分类代码弹窗
   	$(document).on("click",".btn_flh",function(){
   		var id = $(this).parents("tr").find("[id^=text_flh]").attr("id");
   		var flmc = $(this).parents("tr").find("[id^=flmc]").attr("id");
		select_commonWin("${pageContext.request.contextPath}/zcfltree/goWindow?controlId="+id+"&flmc="+flmc,"分类信息","920","630");
    });
  	//学科
	$(document).on("click",".btn_xk",function(){
		var id = $(this).parents("tr").find("[id^=text_xks]").attr("id");
		select_commonWin("${pageContext.request.contextPath}/webView/window/xkxx/window.jsp?controlId="+id,"学科信息","920","630");
    });
	//国别
	$(document).on("click",".btn_gbm",function(){
		var id = $(this).parents("tr").find("[id^=text_gbm]").attr("id");
		select_commonWin("${pageContext.request.contextPath}/webView/window/gbxx/window.jsp?controlId="+id,"国别信息","920","630");
    });
	//点击各个资产状态
    $(document).on("click",".sj_all",function(){
		$(this).addClass("active");
		$(".sj_fw,.sj_gzw,.sj_td,.sj_plant,.sj_ww,.sj_books,.sj_jj,.sj_animals,.sj_car,.sj_wxzc,.sj_ptsb").removeClass("active");
		table.columns('.qb,.fw,.gzw,.td,.zw,.ww,.ts,.jj,.dw,.jt,.wxzc,.sb').visible(false);
		table.columns('.fw_gzw_td').visible(true);
		table.ajax.url("${pageContext.request.contextPath}/yssjzl/getPageList?type=all").load();
    	return false;
    });
    $(document).on("click",".sj_fw",function(){
		$(this).addClass("active");
		$(".sj_all,.sj_gzw,.sj_td,.sj_plant,.sj_ww,.sj_books,.sj_jj,.sj_animals,.sj_car,.sj_wxzc,.sj_ptsb").removeClass("active");
		table.columns('.qb,.fw,.gzw,.td,.zw,.ww,.ts,.jj,.dw,.jt,.wxzc,.sb,.fw_gzw_td').visible(true);
		table.columns('.fw,.fw_gzw_td').visible(false);
		table.ajax.url("${pageContext.request.contextPath}/yssjzl/getPageList?type=fw").load();
    	return false;
    });
    $(document).on("click",".sj_gzw",function(){
		$(this).addClass("active");
		$(".sj_all,.sj_fw,.sj_td,.sj_plant,.sj_ww,.sj_books,.sj_jj,.sj_animals,.sj_car,.sj_wxzc,.sj_ptsb").removeClass("active");
		table.columns('.qb,.fw,.gzw,.td,.zw,.ww,.ts,.jj,.dw,.jt,.wxzc,.sb,.fw_gzw_td').visible(true);
		table.columns('.gzw,.fw_gzw_td').visible(false);
		table.ajax.url("${pageContext.request.contextPath}/yssjzl/getPageList?type=gzw").load();
    	return false;
    });
    $(document).on("click",".sj_td",function(){
		$(this).addClass("active");
		$(".sj_all,.sj_fw,.sj_gzw,.sj_plant,.sj_ww,.sj_books,.sj_jj,.sj_animals,.sj_car,.sj_wxzc,.sj_ptsb").removeClass("active");
		table.columns('.qb,.fw,.gzw,.td,.zw,.ww,.ts,.jj,.dw,.jt,.wxzc,.sb,.fw_gzw_td').visible(true);
		table.columns('.td,.fw_gzw_td').visible(false);
		table.ajax.url("${pageContext.request.contextPath}/yssjzl/getPageList?type=td").load();
    	return false;
    });
    $(document).on("click",".sj_plant",function(){
		$(this).addClass("active");
		$(".sj_all,.sj_fw,.sj_gzw,.sj_td,.sj_ww,.sj_books,.sj_jj,.sj_animals,.sj_car,.sj_wxzc,.sj_ptsb").removeClass("active");
		table.columns('.qb,.fw,.gzw,.td,.zw,.ww,.ts,.jj,.dw,.jt,.wxzc,.sb,.fw_gzw_td').visible(true);
		table.columns('.zw').visible(false);
		table.ajax.url("${pageContext.request.contextPath}/yssjzl/getPageList?type=plant").load();
    	return false;
    });
    $(document).on("click",".sj_ww",function(){
		$(this).addClass("active");
		$(".sj_all,.sj_fw,.sj_gzw,.sj_td,.sj_plant,.sj_books,.sj_jj,.sj_animals,.sj_car,.sj_wxzc,.sj_ptsb").removeClass("active");
		table.columns('.qb,.fw,.gzw,.td,.zw,.ww,.ts,.jj,.dw,.jt,.wxzc,.sb,.fw_gzw_td').visible(true);
		table.columns('.ww').visible(false);
		table.ajax.url("${pageContext.request.contextPath}/yssjzl/getPageList?type=ww").load();
    	return false;
    });
    $(document).on("click",".sj_books",function(){
		$(this).addClass("active");
		$(".sj_all,.sj_fw,.sj_gzw,.sj_td,.sj_plant,.sj_ww,.sj_jj,.sj_animals,.sj_car,.sj_wxzc,.sj_ptsb").removeClass("active");
		table.columns('.qb,.fw,.gzw,.td,.zw,.ww,.ts,.jj,.dw,.jt,.wxzc,.sb,.fw_gzw_td').visible(true);
		table.columns('.ts').visible(false);
		table.ajax.url("${pageContext.request.contextPath}/yssjzl/getPageList?type=books").load();
    	return false;
    });
    $(document).on("click",".sj_jj",function(){
		$(this).addClass("active");
		$(".sj_all,.sj_fw,.sj_gzw,.sj_td,.sj_plant,.sj_ww,.sj_books,.sj_animals,.sj_car,.sj_wxzc,.sj_ptsb").removeClass("active");
		table.columns('.qb,.fw,.gzw,.td,.zw,.ww,.ts,.jj,.dw,.jt,.wxzc,.sb,.fw_gzw_td').visible(true);
		table.columns('.jj').visible(false);
		table.ajax.url("${pageContext.request.contextPath}/yssjzl/getPageList?type=jj").load();
    	return false;
    });
    $(document).on("click",".sj_animals",function(){
		$(this).addClass("active");
		$(".sj_all,.sj_fw,.sj_gzw,.sj_td,.sj_plant,.sj_ww,.sj_books,.sj_jj,.sj_car,.sj_wxzc,.sj_ptsb").removeClass("active");
		table.columns('.qb,.fw,.gzw,.td,.zw,.ww,.ts,.jj,.dw,.jt,.wxzc,.sb,.fw_gzw_td').visible(true);
		table.columns('.dw').visible(false);
		table.ajax.url("${pageContext.request.contextPath}/yssjzl/getPageList?type=animals").load();
    	return false;
    });
    $(document).on("click",".sj_car",function(){
		$(this).addClass("active");
		$(".sj_all,.sj_fw,.sj_gzw,.sj_td,.sj_plant,.sj_ww,.sj_books,.sj_animals,.sj_jj,.sj_wxzc,.sj_ptsb").removeClass("active");
		table.columns('.qb,.fw,.gzw,.td,.zw,.ww,.ts,.jj,.dw,.jt,.wxzc,.sb,.fw_gzw_td').visible(true);
		table.columns('.jt').visible(false);
		table.ajax.url("${pageContext.request.contextPath}/yssjzl/getPageList?type=car").load();
    	return false;
    });
    $(document).on("click",".sj_wxzc",function(){
		$(this).addClass("active");
		$(".sj_all,.sj_fw,.sj_gzw,.sj_td,.sj_plant,.sj_ww,.sj_books,.sj_animals,.sj_jj,.sj_car,.sj_ptsb").removeClass("active");
		table.columns('.qb,.fw,.gzw,.td,.zw,.ww,.ts,.jj,.dw,.jt,.wxzc,.sb,.fw_gzw_td').visible(true);
		table.columns('.wxzc').visible(false);
		table.ajax.url("${pageContext.request.contextPath}/yssjzl/getPageList?type=wxzc").load();
    	return false;
    });
    $(document).on("click",".sj_ptsb",function(){
		$(this).addClass("active");
		$(".sj_all,.sj_fw,.sj_gzw,.sj_td,.sj_plant,.sj_ww,.sj_books,.sj_animals,.sj_jj,.sj_car,.sj_wxzc").removeClass("active");
		table.columns('.qb,.fw,.gzw,.td,.zw,.ww,.ts,.jj,.dw,.jt,.wxzc,.sb,.fw_gzw_td').visible(true);
		table.columns('.sb').visible(false);
		table.ajax.url("${pageContext.request.contextPath}/yssjzl/getPageList?type=ptsb").load();
    	return false;
    });
});
//联想输入
function check(x){
	$("[id^='text_sydw']").bindChange("${pageContext.request.contextPath}/suggest/getXx","SD");
	$("[id^='text_syr']").bindChange("${pageContext.request.contextPath}/suggest/getXx","R");
	$("[id^='text_cgr']").bindChange("${pageContext.request.contextPath}/suggest/getXx","R");
	$("[id^='text_bzxx']").bindChange("${pageContext.request.contextPath}/suggest/getXx","P");
	$("[id^='text_xk']").bindChange("${pageContext.request.contextPath}/suggest/getXx","X");
	$("[id^='text_gbm']").bindChange("${pageContext.request.contextPath}/suggest/getXx","G");
};
var setGroup = function(json){}
</script>
</body>
</html>
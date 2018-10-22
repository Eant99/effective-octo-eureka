var ctxPath = getContextPath();
var basePath = getBasePath();

//切换辅助录入展示
function switchPzlrmxShow(a_num){
	$("[data-pzfz]").hide();
	$("[data-pzfz='"+a_num+"']").show();
	var jsfs = $("[data-pzfz='"+a_num+"']").find(".jsfs").val();
	if(jsfs == "0005"){
		$(".xingming").show();
		$(".beizhu").hide();
	}else{
		$(".xingming").hide();
		$(".beizhu").show();
	}
}
$(function(){
	$(document).ready(function(){
		$("[data-line='1'] [data-col='1'] *").focus();
		$("input,textarea").attr("readonly","readonly");
		$("#txt_pzrq").attr("disabled","disabled");
		$("select:not(#txt_pzbh,#txt_pzz)").attr("disabled","disabled");
	});
	//表格内文本框获取焦点事件，切换凭证辅助信息
	$(document).on("focus","[data-col] [name]",function(){
		switchPzlrmxShow($(this).parents("[data-line]").attr("data-line"));
	});
	/**将元素的所有input子元素转化为json*/
	$.fn.getJson = function(){
		var json = {};
		$.each($(this).find("input"),function(){
			var name = $(this).attr("name");
			var val = $(this).val();
			if(val == null||val == ""){
				return;
			}
			json[name] = val;
		});
		return json;
	}
	//获取交互数据
	$(document).on("change",".echo",function(){
		var value = $(this).val();
		var type = $(this).attr("name");
		var $line = $(this).parents("tr");
		var num = $line.attr("data-line");
		var $this = $(this);
		var $pzfz = $("[data-pzfz='"+num+"']");
		var url = basePath+"/getEchoData?type="+type;
		var lineJson = $line.getJson();
		//每次发送请求前先清空辅助录入数据，初始化到期时间状态
		emptyFzlrData(type,num);
		$.ajax({
			type:"post",
			url:url,
			data:lineJson,
			dataType:"json",
			async:false,
			success:function(data){
				loadEchoData(type,data,$line);
			},
			error:function(){
				
			},
			beforeSend:function(){
				
			}
		})
	});
	/**
	 * 首先清空辅助录入数据
	 * @returns
	 */
	function emptyFzlrData(type,num){
		var $pzfz = $("[data-pzfz='"+num+"']");
		if(type == "kmbh"){
			$pzfz.find("[name='kmmc'],[name='kmye'],[name='dqsj']").val("");
		}else if(type == "jjfl"){
			$pzfz.find("[name='zcjjflkm']").val("");
		}else if(type == "bmbh"){
			$pzfz.find("[name='bmmc']").val("");
		}else if(type == "xmbh"){
			var arr = ["xmmc","srkm","zckm","xmye","fzr","xmgkxxm","xmlx","bz"];
			for(var i in arr){
				var name = arr[i];
				$pzfz.find("[name='"+name+"']").val("");
			}
		}
	}
	/**
	 * 
	 * @param type 类型
	 * @param data json数据
	 * @param $line 凭证明细每一行的tr
	 * @returns
	 */
	function loadEchoData(type,data,$line){
		var num = $line.attr("data-line");
		var $pzfz = $("[data-pzfz='"+num+"']");
		if(type == "kmbh") {
			$pzfz.find("[name='kmmc']").val(data.KMMC);
			$line.find("[name='kmbh']").attr("title",data.KMMC);
			var kmye = isNull(data.KMYE) ? "" : parseFloat(data.KMYE).toFixed(2); 	
			$pzfz.find("[name='kmye']").val(kmye);
			$line.find("[name='jdfx']").val(data.YEFX);
			if(data.SFJJFLKM == "0"){
				$line.find("[name='jjfl']").addClass("non-edit").val("");
				$("[data-pzfz='"+num+"']").find("[name='zcjjflkm']").val("");
			}else{
				$line.find("[name='jjfl']").removeClass("non-edit");
			}
			if(data.BMHS == "0"){
				$line.find("[name='bmbh']").addClass("non-edit").val("");	
				$("[data-pzfz='"+num+"']").find("[name='bmmc']").val("");
			}else{
				$line.find("[name='bmbh']").removeClass("non-edit");
			}
			if(data.XMHS == "0"){
				$line.find("[name='xmbh']").addClass("non-edit").val("");
				$("[data-pzfz='"+num+"']").find("[name^=xm],[name='fzr']").val("");
			}else{
				$line.find("[name='xmbh']").removeClass("non-edit");
			}
			return;
		}
		if(type == "bmbh"){
			for(var key in data){
				var value = data[key];
				key = key.toLowerCase();
				$pzfz.find("[name='"+key+"']").val(value);
				$line.find("[name='bmbh']").attr("title",value);
			}
			var xmbh = $line.find("[name='xmbh']").val();
			if(!isNull(xmbh)){
				$line.find("[name='xmbh']").change();
			}
			return;
		}
		for(var key in data){
			var value = data[key];
			key = key.toLowerCase();
			$pzfz.find("[name='"+key+"']").val(value);
			if(type=="jjfl")
			{
				$line.find("[name='jjfl']").attr("title",value);
			}
			if(type=="xmbh" && key=="xmmc")
			{
				$line.find("[name='xmbh']").attr("title",value);
			}
		}
	}
	/**
	 * 凭证编号 下拉框
	 */
	$("#txt_pzbh").change(function(){
		pzbh = $(this).val();
		pzlx=$("#txt_pzz").val();
		pageSkip(basePath,"pzlr&pzbh="+pzbh+"&pzlx="+pzlx);
	});
	//凭证类型下拉框页面跳转
	$("#txt_pzz").change(function(){
		pzlx = $(this).val();
		console.log(basePath);
		pageSkip(basePath,"pzlr&pzlx="+pzlx+"&qcz=change");
	});
	//分页
	$("#btn_first,#btn_prev,#btn_next,#btn_last").click(function(){
		var type = $(this).data("type");
		var pzlx=$("#txt_pzz").val();
		pageSkip(basePath,"pzlr&type="+type+"&pzlx="+pzlx);
	})
	//打印凭证
	$("[id='btn_print']").click(function(){
		select_commonWin(ctxPath+"/kjhs/pzxx/pzlr/pageSkip?pageName=print","凭证打印","1000","650");
	});
	//查看附件
//	$(document).on("click","#btn_ckfj",function(){
//		var guid=$("#txt_bxid").val();
//		doOperate(ctxPath+"/kjhs/pzxx/pzlr/goFjckPage?uploadId="+guid);
//	});
	$("#btn_ckfj").click(function(){
		select_commonWin(basePath+"/upload?uploadId="+$("#txt_bxid").val(),"单据上传","850","650");
	});
});
//2018-02-05开始新加入的功能  leefly
//凭证分录复制
//1.点击确定复制行
$(document).on("click",".selected",function(e){
	e.stopPropagation();
	$(".copyed").removeClass("copyed");
	$(this).addClass("copyed");
});
//移出表格之后取消样式
$(document).on("click","body:not(tr)",function(){
	$(".copyed").removeClass("copyed");
})
//点击复制按钮
$(document).on("click","#btn_copyByFl",function(){
	var count = 0;
	var copyTr = $("#pzlr_mx").find(".copyed");
	if(copyTr.length==0){
		window.alert("请单击选择要复制的分行！");
		return;
	}
	var copyTd = copyTr.find("td"); 
	$.each(copyTd,function(i,v){
		var tdText = $(this).find("text,textarea").val();
		if(tdText==""||tdText==null){
			count++;
		}
	});
	if(count==copyTd.length){
		window.alert("没有可以复制的内容！");
		return;
	}
	var parentTr = copyTr.clone();//克隆副复制的明细行
	var parentFz = $(".pzfz_info:visible").clone();//克隆复制的辅助信息
	parentTr.removeClass("copyed");
	//修改相关的序号
	var num = $("[id='pzlr_mx']").find("tr:visible").length;
	parentTr.find(".xh").text(num);
	parentTr.attr("data-line",num);
	parentTr.find("[name='xmid']").attr("id","txt_xmid_"+num);
	parentFz.attr("data-pzfz",num);
	parentFz.find("[id='pzfz_xh']").text(num);
	parentFz.css("display","none");
	var oldNum = copyTr.find(".xh").text();
	if(oldNum=="1") oldNum = 2;
	parentFz.find("[name='zrr']").removeClass("input-radius"+oldNum).addClass("input-radius"+num).attr("id","txt_zrr_"+num);
	parentFz.find("[name='dfdw']").removeClass("input-radius"+oldNum).addClass("input-radius"+num).attr("id","txt_dfdw_"+num);
	//拼接
	$("[id='pzlr_mx']").find(".hj").before(parentTr);
	$("#fzForm").append(parentFz);
	computerJfje();
	computerDfje();
});
//复制凭证
//1.初始的判断
$(document).on("click","[id='btn_copyByPz']",function(){
	var guid = $("[name='bxid']").val();
	//获取最新的凭证日期  赋值当前年度 当前期间
	var pznd = $(this).attr("pznd");
	var kjqj = $(this).attr("kjqj");
	var maxDay = getDaysInMonth(pznd,kjqj);
	var day = new Date().getDate();
	if(day > maxDay){
		day = maxDay;
	}
	var pzrq = formatDate(pznd,kjqj,day);
	$.ajax({
		url:basePath+"/selectCopy",
		data:"guid="+guid+"&pzrq="+pzrq+"&dqnd="+pznd+"&dqqj="+kjqj,
		type:"post",
		dataType:"json",
		success:function(data){
			if(data.success){
				window.alert("操作成功！");
				pageSkip(basePath,"pzlr&type=last&copy=yes&pzbh="+data.pzbh+"&pzlx="+data.pzz+"");
			}else{
				window.alert("请先保存要进行复制的凭证信息！");
			}
		},
		error:function(result){
			window.alert("系统错误，请联系管理员！");
		}
	});
});
//获取某个月多少天
function getDaysInMonth(year,month){  
    month = parseInt(month,10);  
    var temp = new Date(year,month,0);  
    return temp.getDate();  
}  
//转换日期格式
var formatDate = function (y,m,d) {  
    m = m < 10 ? '0' + m : m;  
    d = d < 10 ? ('0' + d) : d;  
    return y + '-' + m + '-' + d;  
};  
//加载日期
function loadPzrq(year,month,day){
	var maxDay = getDaysInMonth(year,month);
	if(day > maxDay){
		day = maxDay;
	}
	$("#txt_pzrq").val(formatDate(year,month,day));
}
//保存以前的凭证为模板
$(document).on("click","[id='btn_saveToMb']",function(){
	confirm("金额是否设置为模板内容?","",function(){
		doSaveToMb("yes");
	},function(){
		doSaveToMb("no");
	});
});
function doSaveToMb(param){
	var guid = $("[name='bxid']").val();
	$.ajax({
		url:basePath+"/savePzToMb",
		data:"guid="+guid+"&param="+param,
		type:"post",
		dataType:"json",
		success:function(data){
			if(data.success){
				window.alert("操作成功！");
			}else{
				window.alert("请先保存要进行复制成模板的凭证信息！");
			}
		},
		error:function(result){
			window.alert("系统错误，请联系管理员！");
		}
	});
}

//查看
$(document).on("click","#btn_lookMx",function(){
	var copyTr = $("#pzlr_mx").find(".copyed");
	var mxid = copyTr.find("[name='mxid']").val();
	var zbid = $("[name='uploadId']").val();
	if(mxid==""||zbid==""){
		alert("系统异常，请联系管理员！");
		return;
	}
	var xh = copyTr.find(".xh").text();
	select_commonWin(basePath+"/btn_lookMx?mxid="+mxid+"&zbid="+zbid,"银行明细查看","850","650");
});
////导出
//$(document).on("click","#btn_exp",function(){
//	var zbid = $("[name='uploadId']").val();
//	doExp("",basePath+"/expExcel2?zbid="+zbid,"银行信息","${pageContext.request.contextPath}",zbid);
//});
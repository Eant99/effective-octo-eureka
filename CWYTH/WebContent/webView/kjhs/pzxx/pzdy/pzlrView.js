var ctxPath = getContextPath();
var basePath = getBasePath();

//切换辅助录入展示
function switchPzlrmxShow(a_num){
	$("[data-pzfz]").hide();
	$("[data-pzfz='"+a_num+"']").show();
}
$(function(){
	$(document).ready(function(){
		$("[data-line='1'] [data-col='1'] *").focus();
		$("input,textarea").attr("readonly","readonly");
		$("#txt_pzrq").attr("disabled","disabled");
		$("select:not(#txt_pzbh,#txt_pzz)").attr("disabled","disabled");
		wldc();
	});
	
	//表格内文本框获取焦点事件，切换凭证辅助信息
	$(document).on("focus","[data-col] *",function(){
		switchPzlrmxShow($(this).parents("[data-line]").attr("data-line"));
	});
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
	$.ajax({
		url:basePath+"/selectCopy",
		data:"guid="+guid,
		type:"post",
		dataType:"json",
		success:function(data){
			if(data.success){
				window.alert("操作成功！");
				pageSkip(basePath,"pzlr&type=last&pzlx="+data.pzz+"");
			}else{
				window.alert("请先保存要进行复制的凭证信息！");
			}
		},
		error:function(result){
			window.alert("系统错误，请联系管理员！");
		}
	});
});

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
function wldc(){
	var wldcObj = $(".wldc");
	$.each(wldcObj,function(){
		var xs = $(this).attr("xs");
		if(xs!="true"){
			compareToWldc($(this));
			compareToKhyh($(this).parent("div").parent("div").parent("div"));
		}
	});
}
//往来对冲
function compareToWldc(obj){
	var zrr = obj.attr("zrr");
	var dfdw = obj.attr("dfdw");
	var wl = obj.parent("div").find("[name='wldc']");
	var wldc = wl.attr("wldc");
	wl.find("option").remove(); 
	wl.append("<option value=''>--未选择--</option>");
	$.ajax({
		url:basePath+"/getWldc",
		data:"zrr="+zrr+"&dfdw="+dfdw,
		type:"post",
		dataType:"json",
		async:false,
		success:function(data){
			if(data){
				$.each(data,function(i,v){
					if(wldc==v.JSDH){
						wl.append("<option value="+v.JSDH+" selected>"+v.JSDH+"</option>");
					}else{
						wl.append("<option value="+v.JSDH+">"+v.JSDH+"</option>");
					}
				});
			}
		}
	});
}
//开户银行
function compareToKhyh(obj){
	var zrr = obj.parent("div").find("[name='zrr']").val();
	var dfdw = obj.parent("div").find("[name='dfdw']").val();
	var kh = obj.parent("div").find("[name='khyh']");
	var khyh = obj.parent("div").find("[name='khyh']").attr("khyh");
	kh.find("option").remove(); 
	kh.append("<option value=''>--未选择--</option>");
	var type = "";
	var hm = "";
	if(isNull(zrr)&&isNull(dfdw)) return;
	if(!isNull(zrr)){
		type="zrr";
		hm = zrr;
	}
	if(!isNull(dfdw)){
		type="dfdw";
		hm = dfdw;
	}
	$.ajax({
		url:basePath+"/getKhyh",
		data:"type="+type+"&hm="+hm,
		type:"post",
		dataType:"json",
		async:false,
		success:function(data){
			if(data){
				$.each(data,function(i,v){
					console.log(v.KHYH);
					if(khyh==v.GUID){
						kh.append("<option value="+v.GUID+" zh="+v.KHYHZH+" selected>"+v.KHYH+"</option>");
					}else{
						kh.append("<option value="+v.GUID+" zh="+v.KHYHZH+">"+v.KHYH+"</option>");
					}
				});
			}
		}
	});
}
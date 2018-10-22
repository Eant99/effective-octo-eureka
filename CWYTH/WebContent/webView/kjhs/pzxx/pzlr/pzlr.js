//定义基准路径
var ctxPath = getContextPath();
var basePath = getBasePath();
//定义常量
var leftCode = 37;
var upCode = 38;
var rightCode = 39;
var downCode = 40;
var enterCode = 13;
var spaceCode = 32;//空格键
var equalCode = 187;//= 键   英文下 187 || 中文下 keyCode == "229"
//最大行数
var maxCol = 7;

//切换焦点
$.fn.focusChange = function(keyCode){
	//行数
	var line = $(this).parents("tr").attr("data-line");
	//列数
	var col = $(this).parents("td").attr("data-col");
	//最大列数
	var maxLine = $("[data-line]").last().attr("[data-line]");
	//
	switch (keyCode) {
	case leftCode:
		if(col == "1"){
			line --;
			col = maxCol;
			 $(".copyed").removeClass("copyed");
			 $("[data-line='"+line+"']").addClass("copyed");
		}else{
			col --;
		}
		break;
	case upCode:
		line --;
		 $(".copyed").removeClass("copyed");
		 $("[data-line='"+line+"']").addClass("copyed");
		break;
	case rightCode:
		 $("[data-line='"+line+"']").addClass("copyed");
	case enterCode:
		if(col == maxCol){
			line ++;
			col = 1;
			 $(".copyed").removeClass("copyed");
			 $("[data-line='"+line+"']").addClass("copyed");
		}else{
			col ++;
		}
		break;
	case downCode:
		line ++;
		 $(".copyed").removeClass("copyed");
		 $("[data-line='"+line+"']").addClass("copyed");
		break;
	default:
		return;
	}
	//定义一个将要获取焦点的目标
	var $target = $("[data-line='"+line+"'] [data-col='"+col+"'] *");
	//如果目标不为空
	if($target[0] != null){
		if($target.hasClass("non-edit")||checkSkipJfje($target)||checkSkipDfje($target)){
			$target.focusChange(keyCode);
		}else{
			$target.focus();
		}
	}
};
function change(){
	var pzlx = $("#txt_pzz").val();
	if(pzlx == '03'){
		$("#btn_bxpzsc").show();
		$("#btn_xzpzsc").show();
	}else if(pzlx == '02'){
		$("#btn_bxpzsc").hide();
		$("#btn_xzpzsc").show();
	}else{
		$("#btn_bxpzsc").hide();
		$("#btn_xzpzsc").hide();
	}
}
//function jsfschange(){
//	alert("123");
//	var jsfs = $("#txt_jsfs").val();
//	if(jsfs == '0005'){
//		$("#beizhu").hide();
//		$("#xingming").show();
//	}else{
//		$("#xingming").hide();
//		$("#beizhu").show();
//	}
//}
//检查是否跳过借方金额
function checkSkipJfje($obj){
	var name = $obj.attr("name");
	if(name=="jfje"){
		var dfje = $obj.parents("tr").find("[name='dfje']").val();
		if(noNull(dfje)){
			return true;
		}
	}
	return false;
}
//检查是否跳过贷方金额
function checkSkipDfje($obj){
	var name = $obj.attr("name");
	if(name=="dfje"){
		var jfje = $obj.parents("tr").find("[name='jfje']").val();
		if(noNull(jfje)){
			return true;
		}
	}
	return false;
}
/**
 * str,需要被填充的字符串
 	length,填充后的长度
 	fillStr,用来填充的字符串
 	before,是否在字符串之前填充，默认true
 */
function autoFill(str,length,fillStr,before){
	if(before == null){
		before = true;
	}
	var len = str.length;
	if(len < length){
		for(var i=0;i<length -len;i++){
			if(before){
				str = fillStr+str;
			}else{
				str = str+fillStr;
			}
		}
	}
	return str;
}
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
/**数组arr是否包含字符串str*/
function contains(arr,str){
	var flag = false;
	for(var i in arr){
		if(arr[i] == str){
			flag = true;
			break;
		}
	}
	return flag;
}
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
/**是否为空*/
function isNull(a_val){
	if(a_val == null || a_val == ""){
		return true;
	}else{
		return false;
	}
}
/**
 * 首先清空辅助录入数据
 * @returns
 */
function emptyFzlrData(type,num){
	var $pzfz = $("[data-pzfz='"+num+"']");
	if(type == "kmbh"){
		$pzfz.find("[name='kmmc'],[name='kmye']").val("");
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
		var value = data.BMMC;
		$pzfz.find("[name='bmmc']").val(value);
		$line.find("[name='bmbh']").attr("title",value);
		var bmhasxmbh = data.XMBH;
		var xmbharr = bmhasxmbh.split(",");
		var xmbh = $line.find("[name$='xmbh']").val();
		if(!isNull(xmbh)){
			if(xmbharr.indexOf(xmbh)==-1){
				$line.find("[name$='xmbh']").val("");
			}
			$line.find("[name$='xmbh']").change();
		}
		return;
	}
	for(var key in data){
		var value = data[key];
		key = key.toLowerCase();
		$pzfz.find("[name='"+key+"']").val(value);
		if(type=="jjfl"){
			$line.find("[name='jjfl']").attr("title",value);
		}
		if(type=="xmbh" && key=="xmmc"){
			$line.find("[name='xmbh']").attr("title",value);
		}
	}
}
//手动触发change
function triggerChange(id){
	$("#"+id).change();
}
//上移
$(document).on("click",".btn_up",function(){
		
	//兑换摘要
	var zy1 = $(this).parents("tr").find("[name='zy']").val();
	var zy2 = $(this).parents("tr").prev("tr").find("[name='zy']").val();
	$(this).parents("tr").find("[name='zy']").val(zy2);
	$(this).parents("tr").prev("tr").find("[name='zy']").val(zy1);
	//兑换mxguid 明细
	var mxguyid1 = $(this).parents("tr").find("[name='mxid']").val();
	var mxguid2 = $(this).parents("tr").prev("tr").find("[name='mxid']").val();
	$(this).parents("tr").find("[name='mxid']").val(mxguid2);
	$(this).parents("tr").prev("tr").find("[name='mxid']").val(mxguyid1);
	//兑换科目编号
	var kmbh1 = $(this).parents("tr").find("[name='kmbh']").val();
	var kmbh2 = $(this).parents("tr").prev("tr").find("[name='kmbh']").val();
	$(this).parents("tr").find("[name='kmbh']").val(kmbh2);
	$(this).parents("tr").prev("tr").find("[name='kmbh']").val(kmbh1);
	//对换经济分类
	var a1 = $(this).parents("tr").find("[name='jjfl']").hasClass("non-edit");
	var b1 = $(this).parents("tr").prev("tr").find("[name='jjfl']").hasClass("non-edit");
	if(a1==true){
		$(this).parents("tr").prev("tr").find("[name='jjfl']").addClass("non-edit");
	}else{
		$(this).parents("tr").prev("tr").find("[name='jjfl']").removeClass("non-edit");
	}
	if(b1==true){
		$(this).parents("tr").find("[name='jjfl']").addClass("non-edit");
	}else{
		$(this).parents("tr").find("[name='jjfl']").removeClass("non-edit");
	}
	var jjfl1 = $(this).parents("tr").find("[name='jjfl']").val();
	var jjfl2 = $(this).parents("tr").prev("tr").find("[name='jjfl']").val();
	$(this).parents("tr").find("[name='jjfl']").val(jjfl2);
	$(this).parents("tr").prev("tr").find("[name='jjfl']").val(jjfl1);
	//兑换 部门编号
	var a2 = $(this).parents("tr").find("[name='bmbh']").hasClass("non-edit");
	var b2 = $(this).parents("tr").prev("tr").find("[name='bmbh']").hasClass("non-edit");
	if(a2==true){
		$(this).parents("tr").prev("tr").find("[name='bmbh']").addClass("non-edit");
	}else{
		$(this).parents("tr").prev("tr").find("[name='bmbh']").removeClass("non-edit");
	}
	if(b2==true){
		$(this).parents("tr").find("[name='bmbh']").addClass("non-edit");
	}else{
		$(this).parents("tr").find("[name='bmbh']").removeClass("non-edit");
	}
	var bmbh1 = $(this).parents("tr").find("[name='bmbh']").val();
	var bmbh2 = $(this).parents("tr").prev("tr").find("[name='bmbh']").val();
	$(this).parents("tr").find("[name='bmbh']").val(bmbh2);
	$(this).parents("tr").prev("tr").find("[name='bmbh']").val(bmbh1);
	//对换项目编号
	var a3 = $(this).parents("tr").find("[name='xmbh']").hasClass("non-edit");
	var b3 = $(this).parents("tr").prev("tr").find("[name='xmbh']").hasClass("non-edit");
	if(a3==true){
		$(this).parents("tr").prev("tr").find("[name='xmbh']").addClass("non-edit");
	}else{
		$(this).parents("tr").prev("tr").find("[name='xmbh']").removeClass("non-edit");
	}
	if(b3==true){
		$(this).parents("tr").find("[name='xmbh']").addClass("non-edit");
	}else{
		$(this).parents("tr").find("[name='xmbh']").removeClass("non-edit");
	}
	var xmbh1 = $(this).parents("tr").find("[name='xmbh']").val();
	var xmbh2 = $(this).parents("tr").prev("tr").find("[name='xmbh']").val();
	$(this).parents("tr").find("[name='xmbh']").val(xmbh2);
	$(this).parents("tr").prev("tr").find("[name='xmbh']").val(xmbh1);
	//对换借方金额
	var jfje1 = $(this).parents("tr").find("[name='jfje']").val();
	var jfje2 = $(this).parents("tr").prev("tr").find("[name='jfje']").val();
	$(this).parents("tr").find("[name='jfje']").val(jfje2);
	$(this).parents("tr").prev("tr").find("[name='jfje']").val(jfje1);
	//对换贷方金额
	var dfje1 = $(this).parents("tr").find("[name='dfje']").val();
	var dfje2 = $(this).parents("tr").prev("tr").find("[name='dfje']").val();
	$(this).parents("tr").find("[name='dfje']").val(dfje2);
	$(this).parents("tr").prev("tr").find("[name='dfje']").val(dfje1);
    //对换 data-line 的值
	var line1=$(this).parents("tr").attr("data-line");
	var line2=$(this).parents("tr").prev("tr").attr("data-line");
	var fzline = $(".pzfz_info");
	 $.each(fzline,function(){
		 var fzVal = $(this).attr("data-pzfz");
		 if(fzVal==line1){
			 $(this).addClass("first");
			 $(this).find("#pzfz_xh").text(line2);
		 }
         if(fzVal==line2){
        	 $(this).addClass("second");
        	 $(this).find("#pzfz_xh").text(line1);
		 }
	 });
	 $(".first").attr("data-pzfz",line2);
	 $(".second").attr("data-pzfz",line1);
	 $(".first").removeClass("first");
	 $(".second").removeClass("second");
	 
	 var divList = $(".pzfz_info");
	 divList.sort(function(a, b){
	     return $(a).data("pzfz")-$(b).data("pzfz")
	 });
	 $("#fzForm").html(divList);

});
//表格增加一行
function addLine(){
	//寻找被选中的行  class="copyed"
	var copyTr = $("#pzlr_mx").find(".copyed");
	var num = copyTr.find(".xh").text();
	if(num==""){
		num = $("[data-line]").last().attr("data-line");
	}
	$.ajax({
		url:basePath+"/getGuid",
		data:"",
		type:"post",
		async:false,
		success:function(val){
			var $lineTemp = $("[data-line='0']").clone(true).show();
			num++;
			$lineTemp.attr("data-line",num).find(".xh").text(num);
			$lineTemp.attr("data-line",num).find("[name='mxid']").val(val);
			$lineTemp.find("[name='zy']").attr("id","txt_zy_"+num);
			$lineTemp.find("[name='kmbh']").attr("id","txt_kmbh_"+num);
			$lineTemp.find("[name='jjfl']").attr("id","txt_jjfl_"+num);
			$lineTemp.find("[name='bmbh']").attr("id","txt_bmbh_"+num);
			$lineTemp.find("[name='xmbh']").attr("id","txt_xmbh_"+num);
			num--;
		 	$("[data-line]").eq(num).after($lineTemp);
		 	if(num>1){
		 	   $lineTemp.find("[name='zy']").focus();
		 	}
		}
	});
	
}
//增加一个凭证辅助信息
function addPzfz(){
	//寻找被选中的行  class="copyed"
	var copyTr = $("#pzlr_mx").find(".copyed");
	var num = copyTr.find(".xh").text();
	if(num==""){
		num = $("[data-pzfz]:last-child").attr("data-pzfz");
	}
	var $pzfzTemp = $("[data-pzfz='0']").clone(true);
	//var num = $("[data-pzfz]:last-child").attr("data-pzfz");
	num++;
	$pzfzTemp.attr("data-pzfz",num);
	$pzfzTemp.find("#pzfz_xh").text(num);
	$pzfzTemp.find("[name='zrr']").attr("id","txt_zrr_"+num);
	$pzfzTemp.find("[name='wldw']").attr("id","txt_wldw_"+num);
	$pzfzTemp.find("[name='dfdw']").attr("id","txt_dfdw_"+num);
	$pzfzTemp.find("[name='gnkm']").attr("id","txt_gnkm_"+num);
	num--;
	$("[data-pzfz]").eq(num).after($pzfzTemp);
	sort();
}
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
//凭证生成
function pzsc(data){
	$("[data-line]:not([data-line='0'])").remove();
	$("[data-pzfz]:not([data-pzfz='0'])").remove();
	var size = data.size;
	var lineArray = data.data;
	for(var i=0;i<size;i++){
		addPzmx();
	}
	$("[data-line='1'] [data-col='1'] *").focus();
	$("#txt_bxid").val(data.guid);
	$("[name='uploadId']").val(data.guid);
	for(var index in lineArray){
		var json = lineArray[index];
		for(var key in json){
			line_num = parseInt(index)+1;
			var name = key.toLowerCase();
			var value = json[key];
			$("[data-line="+line_num+"]").find("[name='"+name+"']").val(value);
		}
	}
	$(".sign-number").blur();
	computerJfje();
	computerDfje();
	//触发科目编号验证
	$("[name='kmbh']:visible").change();
	//交互数据
	$(".echo:not([name='kmbh'],.non-edit):visible").change();
}
//选择模板
function xzmb(data){
	$("[data-line]:not([data-line='0'])").remove();
	$("[data-pzfz]:not([data-pzfz='0'])").remove();
	var size = data.size;
	var lineArray = data.data;
	for(var i=0;i<size;i++){
		addPzmx();
	}
	$("[data-line='1'] [data-col='1'] *").focus();
	for(var index in lineArray){
		var json = lineArray[index];
		console.log(json)
		for(var key in json){
			line_num = parseInt(index)+1;
			var name = key.toLowerCase();
			var value = json[key];
			$("[data-line='"+line_num+"']").find("[name='"+name+"']").val(value);
		}
	}
	$("[name='kmbh']:visible").change();
	computerJfje();
	computerDfje();
	$("[data-line='1'] .btn_delete").hide();
	$("[data-line='2'] .btn_delete").hide();


}


//验重
function checkReply($obj){
	var json = {};
	var flag = false;
	$.each($obj,function(){
		var value = $(this).val();
		if(isNull(value)){
			return;
		}
		if(json[value]==null){
			json[value] = "1";
		}else{
			flag = true;
		}
	});
	return flag;
}
//检验表格输入的数据
function checkRequired(){
	var errMsg = {"num":0,"nullMsg":{}};
	//空信息
	var nullMsg = errMsg.nullMsg;
	var num = 0;
	var tag = true;
	$.each($(".required:visible"),function(i,v){
		var null_len = 0;
		var val = $(this).val();
		var name = $(this).attr("name");
		var line_num = $(this).parents("[data-line]").attr("data-line");
		if(num!=line_num){//全部为空的话直接通过
			num = line_num;
			var input_info = $(this).parents("tr").find(".required");
			$.each(input_info,function(){
				var value = $(this).val().trim();
				if(isNull(value)){
					null_len++;
				}
			});
			if(input_info.length==null_len){
				tag = false;
				if(line_num>2){
					$(this).parents("tr").remove();
				}
			}else{
				tag = true;
			}
			if(num==i+1){
				tag = true;
			}
		}
		if(isNull(val)&&tag){
			//如果该文本框不是不可编辑
			if(!$(this).hasClass("non-edit")){
				if(name=="jfje"){
					
				}else if(name=="dfje"){
					var jfje = $(this).parents("tr").find("[name='jfje']").val();
					if(isNull(jfje)){
						nullMsg["&emsp;&emsp;&emsp;&ensp;第"+line_num+"行"] = "存在空信息！";
						errMsg.num = errMsg.num + 1;
					}
			 	}else if(name=="zy"){
			 		var zy = $(this).parents("tr").find("[name='zy']").val();
			 		if(isNull(zy)){
			 			nullMsg["&emsp;&emsp;&emsp;&ensp;第"+line_num+"行"] = "存在空信息！";
						errMsg.num = errMsg.num + 1;
			 		}	
			 	}else{
					nullMsg["&emsp;&emsp;&emsp;&ensp;第"+line_num+"行"] = "存在空信息！";
					errMsg.num = errMsg.num + 1;
				}
			}
		}
	});
	return errMsg;
}
/**检查长期投资时间*/
function checkDqsj(){
	var errMsg = {
			num:0,
			msg:"",
			alertErrMsg:function(){
				alert(this.msg);
			}
	}
	var num = 0;
	//空信息
	$.each($("[name='dqsj']:enabled"),function(){
		var dqsj = $(this).val();
		var line_num = $(this).parents("[data-pzfz]").attr("data-pzfz");
		errMsg.msg = "辅助录入第"+line_num+"行,";
		if(isNull(dqsj)){
			errMsg.msg += "到期时间不能为空！</br>";
			num++;
		}
	});
	errMsg.num = num;
	return errMsg;
}
//检验金额
function checkJe(){
	var a = "";
	var jfje_total = $("#txt_jfzje").text();
	var dfje_total = $("#txt_dfzje").text();
	if(jfje_total != dfje_total){
		a = "1";
	}
	$.each($("[name='jfje']"),function(){
		var val = $(this).val();
		if(val=="0.00"){
			a = "0";
			return false 
		}
	});
	$.each($("[name='dfje']"),function(){
		var val = $(this).val();
		if(val=="0.00"){
			a = "0";
			return false 
		}
	});
	return a;
}
//检查凭证日期是否在当前账套期间
function checkDate(){
	var pzrq = $("#txt_pzrq").val();
	var i = pzrq.lastIndexOf("-");
	pzrq = pzrq.substring(0,i);
	if(pzrq!=kjqj){
		return false;
	}
	return true;
}
//排序
function sort(){
	$.each($(".xh"),function(i){
		$(this).text(i);
		$(this).parents("[data-line]").attr("data-line",i);
	});
	$.each($("[data-pzfz]"),function(i){
		$(this).attr("data-pzfz",i);
		$(this).find("#pzfz_xh").text(i);
	})
}
//计算借方，贷方总金额
function computerJfje(){
	var jfje_total = 0;
	$.each($("[name='jfje']"),function(){
		var val = $(this).val();
		if(isNull(val)){
			val = 0;
		}
		jfje_total += parseFloat(val);
	});
	var jfzje = jfje_total.toFixed(2);
	if(jfzje == -0.00||jfzje== ".00"||jfzje==0){
		jfzje = 0.00.toFixed(2);
	}
	$("#txt_jfzje").text(jfzje);
	//return jfje_total;
	return jfzje;
}
function computerDfje(){
	var dfje_total = 0;
	$.each($("[name='dfje']"),function(){
		var val = $(this).val();
		if(isNull(val)){
			val = 0;
		}
		dfje_total += parseFloat(val);
	});
	var dfzje = dfje_total.toFixed(2);
	if(dfzje == -0.00||dfzje== ".00"||dfzje==0){
		dfzje = 0.00.toFixed(2);
	}
	$("#txt_dfzje").text(dfzje);
	//return dfzje;
	return dfzje;
}
/**
 * 增加一条凭证明细：表格增加一行，辅助录入增加一块
 * @returns
 */
function addPzmx(){
	addLine();
	addPzfz();
}
/**元素监听事件*/
$(function(){
	//方向键控制焦点
	$(".top_table").on("keydown","[data-col] *",function(e){
		var name = $(this).attr("name");
		var value = $(this).val();
		var keyCode = e.keyCode;
		var maxLine = $("[data-line]").last().attr("data-line");
		var line = $(this).parents("tr").attr("data-line");
		//如果是最后一行的借方金额，并且输入的是回车，并且值不为空，则增加一行
		if( name == "jfje" ){
			if(keyCode == enterCode){
				if(isNull(value)){
					$(this).parents("tr").find("[name$=dfje]").focus();
					return;
				}else{
					if(line == maxLine){
					//如果有值，增加一行
						addLine();
						addPzfz();
					}
				}
			}else if(keyCode == equalCode || keyCode == "229"){
				$(this).val("");
				var autoJe = computerDfje()-computerJfje();
				$(this).val(parseFloat(autoJe).toFixed(2));
				$(this).change();
			}
		}
		//如果是最后一行的贷方金额，并且输入的是回车
		if(name == "dfje"){
			if(keyCode == enterCode){
				if(isNull(value)){
					//如果没有值，自动计算金额，使借贷平衡
					var autoJe = computerJfje()-computerDfje();
					if(autoJe>0){
					  $(this).val(parseFloat(autoJe).toFixed(2));
					}else{
					  $(this).parents("tr").find("[name$=jfje]").focus().val(parseFloat(autoJe*-1).toFixed(2));
					}
					computerDfje();
					computerJfje();
					return;
				}else{
					if(line == maxLine){
						//如果有值，增加一行
						addLine();
						addPzfz();
					}
				}
			}else if(keyCode == equalCode || keyCode == "229"){
				$(this).val("");
				var autoJe = computerJfje()-computerDfje();
				$(this).val(parseFloat(autoJe).toFixed(2));
				$(this).change();
			}
		}
		//如果是摘要，并且输入的是回车
		if(name == "zy" && keyCode == enterCode && line == maxLine){
			if(isNull(value.trim())&&line!="1"){
				//如果值为空，则默认取上一行的摘要
				line--;
				var preVal = $("[data-line='"+line+"']").find("[name='zy']").val();
				$(this).val(preVal);
			}
		}
		//如果是科目编号，并且输入的是回车
		if(name == "kmbh" && keyCode == enterCode && line == maxLine){
			if(isNull(value.trim())&&line!="1"){
				//如果值为空，则默认取上一行的科目编号
				line--;
				var preVal = $("[data-line='"+line+"']").find("[name='kmbh']").val();
				$(this).val(preVal);
				$(this).change();
			}
		}
		//如果没有联想输入
		if($("#popup")[0]==null){
			$(this).focusChange(keyCode);
		}else{
			//如果有联想输入，只让左右生效
			if(keyCode==leftCode||keyCode==rightCode){
				$(this).focusChange(keyCode);
			}
		}
	});
	
	//增行，删行
	$("#btn_add").click(function(){
		addPzmx();
	})
	$(document).on("click",".btn_delete",function(){
		var $line = $(this).parents("tr");
		//获取这一行的明细id 保存时 同步删除与这个明细有关的银行明细
		var mxid = $(this).parents("tr").find("#txt_mxid").val();
		if(mxid != "undefined"){
			$("#delmxid").append(mxid+",");
		}
		var line_num = $line.attr("data-line");
		$line.remove();
		$("[data-pzfz='"+line_num+"']").remove();
		$("[data-pzfz='1']").show();
		sort();
		computerJfje();
		computerDfje();
	})
	//借贷互换
	$("#btn_jdhh").click(function(){
		var $jf = $("[name='jfje']");
		var $df = $("[name='dfje']");
		var length = $jf.length;
		for(var i=0;i<length;i++){
			var t = $jf[i].value;
			$jf[i].value = $df[i].value;
			$df[i].value = t;
		}
	});
	
	//edit 保存
	$("#btn_save").click(function(){
		//关于单据的县相关验证start
		var fjzs = $("[id='txt_fjzs']").val();
		var bool = false;
		if(fjzs==""||fjzs==null||isNaN(fjzs)||fjzs<=0){
			alert("附件张数必须不能为空且必须大于0！");
			return;
		}
		$.ajax({
			url:basePath+"/checkDj",
			data:"guid="+$("[name='uploadId']").val(),
			type:"post",
			dataType:"json",
			async:false,
			success:function(data){
				bool = data.success;//单据是否必传的验证
			}
		});
		if(!bool){
			alert("请上传单据！");
			return;
		}
		//关于单据的县相关验证end
		
		//验证个人辅助和往来单位辅助是否填写start
		var pzfz_info = $(".pzfz_info");
		var kmbhMsg = [];
		var fzxxMsg = [];
		var wyxxMsg = [];
		var kmbhs = [];
		$.each(pzfz_info,function(index,value){
			var pzfz = $(this).attr("data-pzfz");
			if(pzfz>0){
				var zrr = $(this).find("[name='zrr']:enabled").val();
				var dfdw = $(this).find("[name='dfdw']:enabled").val();
				var gnkm = $(this).find("[name='gnkm']:enabled").val();
				var jsfs = $(this).find("[name='jsfs']:visible").val();
				var kmbh = $("[data-line='"+pzfz+"']").find("[name='kmbh']").val();
				kmbhs.push(kmbh);
				if(zrr==""||dfdw==""||gnkm==""){
					kmbhMsg.push(kmbh);
					returnNullFzlr(zrr,dfdw,gnkm,fzxxMsg);
				}
				if(jsfs=="0002"||jsfs=="0003"){
					var khyh = $(this).find("[name='khyh']:visible").val();
					var hm = $(this).find("[name='hm']:visible").val();
					var zh = $(this).find("[name='zh']:visible").val();
					if(khyh==""||hm==""||zh==""){
						wyxxMsg.push(kmbh);
					}
				}
			}
		});
		// 验证是否是预算收入类  6001... 以 6 开头的科目编号
		   if(kmbhs.length>0){
			   var firStr = [];
			   var flag6 = false;
			   var has =false ;
			   for(var i=0;i<kmbhs.length;i++){
				   var fir = kmbhs[i].slice(0,1);
				   firStr.push(fir);
			   }
			   for(var m=0;m<firStr.length;m++){
				   if(firStr[m] == "6"){
					   has = true;
				   }else{
					   flag6 = true;
				   }
			   }
			   if(has && flag6){
				   alert("存在预算收入类科目，不能与其它科目同时保存！")
				   return;
			   }
				 
			}
		if(kmbhMsg.length>0&&fzxxMsg.length>0){
			alert("存在科目:"+kmbhMsg.join(",")+"有必填的辅助录入:"+fzxxMsg.join(",")+";");
			return;
		}
		if(wyxxMsg.length>0){
			alert("科目编号:"+wyxxMsg.join(",")+"的开户银行信息不能为空！");
			return;
		}
		//验证个人辅助和往来单位辅助是否填写end
		
		//开始验证往来对冲的额金额是否超出借款金额
		var wllMsg = checkWldc();
		if(wllMsg.length>0){
			alert("往来对冲信息:"+wllMsg.join(",")+"超出借款金额；");
			return;
		}
		//结束验证往来对冲的额金额是否超出借款金额
		
		//验证项目金额start
		var moneyMsg = checkXmMoney();
		if(isNull(moneyMsg)){
			//判断 修改人(当前登录人) 和 制单人 是否 相同
			var xgr = $("#dqrxm").val();
			var zdr = $("#zdr").text();
			if(xgr != zdr){
				confirm("是否确定修改他人凭证？","W",{title:"提示"},
				    	function(){	//确定
					      doSavePz();
				    	},
				    	function(){//取消
				    		return;
				    	}
				    );
			}else{
			   doSavePz();
			}
		}else{
			alert(moneyMsg+"已超出目前项目的可用金额");
			return;
		}
	});
	//保存执行的方法
	function doSavePz(){
		var errMsg = checkRequired();
		//空信息
		var nullMsg = errMsg.nullMsg;
		var str = "<br/>";
		if(errMsg.num != 0){
			for(var key in nullMsg){
				str = str + key + nullMsg[key] + "<br/>";
			}
			alert(str);
			return;
		}
		var a = checkJe();
		if("0"==a){
			alert("分录金额不能为零！");
			return;
		}else if("1"==a){
			alert("借方总金额必须等于贷方总金额");
			return;
		}
		var cqtzMsg = checkDqsj();
		if(cqtzMsg.num != 0){
			cqtzMsg.alertErrMsg();
			return;
		}
		var zbJson = arrayToJson($("#zbForm").serializeArray());
		var mxJson = $("#mxForm").serializeObject("zy","dfje");
		var fzJson = $("#fzForm").serializeObject("start","end");
		var jfzje = $("#txt_jfzje").text();
		var dfzje = $("#txt_dfzje").text();
		zbJson["mxJson"] = JSON.stringify(mxJson);
		zbJson["fzJson"] = JSON.stringify(fzJson);
		zbJson["jfjehj"] = jfzje;
		zbJson["dfjehj"] = dfzje;
		$.ajax({
			type:"post",
			url:basePath+doSaveUrl,
			data:zbJson,
			dataType:"json",
			success:function(data){
				if(data.success){
					alert("保存成功！");
					pageSkip(basePath,"pzlr&type=self&pzlx="+data.pzz+"");
				}else{
					 //$("#txt_pzz").find("option[value='"+data.pzz+"']").attr("selected",true);
					alert("保存失败，请稍后重试！");
				}
			},
			error:function(){
				alert("抱歉，系统出现错误！");
			}
		});
	
	}
	//add 保存
	$("#btn_save1").click(function(){
		//关于单据的县相关验证start
		var fjzs = $("[id='txt_fjzs']").val();
		var bool = false;
		if(fjzs==""||fjzs==null||isNaN(fjzs)||fjzs<=0){
			alert("附件张数必须不能为空且必须大于0！");
			return;
		}
		
		$.ajax({
			url:basePath+"/checkDj",
			data:"guid="+$("[name='uploadId']").val(),
			type:"post",
			dataType:"json",
			async:false,
			success:function(data){
				bool = data.success;//单据是否必传的验证
			}
		});
		if(!bool){
			alert("请上传单据！");
			return;
		}
		//关于单据的县相关验证end
		
		//验证个人辅助和往来单位辅助是否填写start
		var pzfz_info = $(".pzfz_info");
		var kmbhMsg = [];
		var fzxxMsg = [];
		var wyxxMsg = [];
		var kmbhs = [];
		$.each(pzfz_info,function(index,value){
			var pzfz = $(this).attr("data-pzfz");
			if(pzfz>0){
				var zrr = $(this).find("[name='zrr']:enabled").val();
				var dfdw = $(this).find("[name='dfdw']:enabled").val();
				var gnkm = $(this).find("[name='gnkm']:enabled").val();
				var jsfs = $(this).find("[name='jsfs']:visible").val();
				var kmbh = $("[data-line='"+pzfz+"']").find("[name='kmbh']").val();
				kmbhs.push(kmbh);
				if(zrr==""||dfdw==""||gnkm==""){
					kmbhMsg.push(kmbh);
					returnNullFzlr(zrr,dfdw,gnkm,fzxxMsg);
				}
				if(jsfs=="0002"||jsfs=="0003"){
					var khyh = $(this).find("[name='khyh']:visible").val();
					var hm = $(this).find("[name='hm']:visible").val();
					var zh = $(this).find("[name='zh']:visible").val();
					if(khyh==""||hm==""||zh==""){
						wyxxMsg.push(kmbh);
					}
				}
			}
		});
		// 验证是否是预算收入类  6001... 以 6 开头的科目编号
		   if(kmbhs.length>0){
			   var firStr = [];
			   var flag6 = false;
			   var has =false ;
			   for(var i=0;i<kmbhs.length;i++){
				   var fir = kmbhs[i].slice(0,1);
				   firStr.push(fir);
			   }
			   for(var m=0;m<firStr.length;m++){
				   if(firStr[m] == "6"){
					   has = true;
				   }else{
					   flag6 = true;
				   }
			   }
			   if(has && flag6){
				   alert("存在预算收入类科目，不能与其它科目同时保存！")
				   return;
			   }
				 
			}
		if(kmbhMsg.length>0&&fzxxMsg.length>0){
			alert("存在科目:"+kmbhMsg.join(",")+"有必填的辅助录入:"+fzxxMsg.join(",")+";");
			return;
		}
		if(wyxxMsg.length>0){
			alert("科目编号:"+wyxxMsg.join(",")+"的开户银行信息不能为空！");
			return;
		}
		//验证个人辅助和往来单位辅助是否填写end
		
		//开始验证往来对冲的额金额是否超出借款金额
		var wllMsg = checkWldc();
		if(wllMsg.length>0){
			alert("往来对冲信息:"+wllMsg.join(",")+"超出借款金额；");
			return;
		}
		//结束验证往来对冲的额金额是否超出借款金额
		
		//验证项目金额start
		var moneyMsg = checkXmMoney();
		if(isNull(moneyMsg)){
			doSavePz();
		}else{
			alert(moneyMsg+"已超出目前项目的可用金额");
			return;
		}
	});
	//保存执行的方法
	function doSavePz(){
		var errMsg = checkRequired();
		//空信息
		var nullMsg = errMsg.nullMsg;
		var str = "<br/>";
		if(errMsg.num != 0){
			for(var key in nullMsg){
				str = str + key + nullMsg[key] + "<br/>";
			}
			alert(str);
			return;
		}
		var a = checkJe();
		if("0"==a){
			alert("分录金额不能为零！");
			return;
		}else if("1"==a){
			alert("借方总金额必须等于贷方总金额");
			return;
		}
		var cqtzMsg = checkDqsj();
		if(cqtzMsg.num != 0){
			cqtzMsg.alertErrMsg();
			return;
		}
		var zbJson = arrayToJson($("#zbForm").serializeArray());
		var mxJson = $("#mxForm").serializeObject("zy","dfje");
		var fzJson = $("#fzForm").serializeObject("start","end");
		var jfzje = $("#txt_jfzje").text();
		var dfzje = $("#txt_dfzje").text();
		console.log("------"+fzJson);
		zbJson["mxJson"] = JSON.stringify(mxJson);
		zbJson["fzJson"] = JSON.stringify(fzJson);
		zbJson["jfjehj"] = jfzje;
		zbJson["dfjehj"] = dfzje;
	    //减掉的mxid
		var mxids = $("#delmxid").text();
		zbJson["mxids"] = mxids;
		$.ajax({
			type:"post",
			url:basePath+doSaveUrl,
			data:zbJson,
			dataType:"json",
			success:function(data){
				if(data.success){
					alert("保存成功！");
					pageSkip(basePath,"pzlr&type=self&pzlx="+data.pzz+"");
				}else{
					 //$("#txt_pzz").find("option[value='"+data.pzz+"']").attr("selected",true);
					alert("保存失败，请稍后重试！");
				}
			},
			error:function(){
				alert("抱歉，系统出现错误！");
			}
		});
	
	}
	//借方金额，贷方金额只有一个可编辑
	$(document).on("focus","[name='jfje']",function(){
		var val = $(this).parents("[data-line]").find("[name='dfje']").val();
		if(noNull(val)){
			$(this).blur();
		}
	});
	$(document).on("focus","[name='dfje']",function(){
		var val = $(this).parents("[data-line]").find("[name='jfje']").val();
		if(noNull(val)){
			$(this).blur();
		}
	});
	//文本不可编辑
	$(document).on("focus",".non-edit",function(){
		$(this).blur();
	});
	//验证输入的金额是否是负数，暂时先不用
//	$(document).on("change","[name='jfje'],[name='dfje']",function(){
//		var val = parseFloat($(this).val());
//		if(val<0){
//			$(this).addClass("minus");
//		}else{
//			$(this).removeClass("minus");
//		}
//	});
	//附件张数默认为0
	$(document).on("change","#txt_fjzs",function(){
		var value = $(this).val();
		if(isNull(value)){
			value = 0;
		}
		$(this).val(value);
	});
	//表格内文本框获取焦点事件，切换下方辅助录入信息
	$(document).on("focus","[data-col] [name]",function(){
		switchPzlrmxShow($(this).parents("[data-line]").attr("data-line"));
	});
	//借方，贷方金额统计  判断输入金额  与 科目余额 
	$(document).on("change","[name='jfje']",function(){
//		var jfje = $(this).val();//输入的金额
//		var $line = $(this).parents("tr");
//		var line_num = $line.attr("data-line");  
//		var kmye = $("[data-pzfz='"+line_num+"']").find("#txt_kmye").val();
//		if(kmye == ""){
//			kmye = 0;
//		}
//		if(parseFloat(jfje) > parseFloat(kmye)){
//			alert("输入的金额大于当前科目余额！");
//		}else{
			computerJfje();
//		}
	})
	$(document).on("change","[name='dfje']",function(){
//		var dfje = $(this).val();//输入的金额
//		var $line = $(this).parents("tr");
//		var line_num = $line.attr("data-line");  
//		var kmye = $("[data-pzfz='"+line_num+"']").find("#txt_kmye").val();
//		if(kmye == ""){
//			kmye = 0;
//		}
//		if(parseFloat(dfje) > parseFloat(kmye) ){
//			alert("输入的金额大于当前科目余额！");
//		}else{
			computerDfje();
//		}
	})
	//获取交互数据
	$(document).on("change",".echo",function(){
		var value = $(this).val();
		var type = $(this).attr("name");
		var $line = $(this).parents("tr");
		var num = $line.attr("data-line");
		var $this = $(this);
		var $pzfz = $("[data-pzfz='"+num+"']");
		
		//录入无值  编辑 有  编辑时 计算 可用金额
		var pzzbguid = $("#txt_bxid").val();
		var url = basePath+"/getEchoData?type="+type+"&pzzbguid="+pzzbguid;
		var lineJson = $line.getJson();
		//新加的到期时间
		var $dqsj = $("[data-pzfz='"+num+"']").find(".dqsj");
		//每次发送请求前先清空辅助录入数据，初始化到期时间状态
		emptyFzlrData(type,num);
		//如果值为空，返回
		if(isNull(value)){
			//如果是科目编号，则去掉后面几个文本框不可编辑限制
			if(type == "kmbh"){
				$this.parents("tr").find(".echo").removeClass("non-edit");
			}
			return;
		}else{
			//如果是以下几个编号，则显示到期时间
			if(type == "kmbh"){
				if(value.indexOf("2401")==0 || value.indexOf("1401")==0 || value.indexOf("2402") ==0 ){
					$dqsj.show().find("[name='dqsj']").removeAttr("disabled");
				}else{
					$dqsj.hide().attr("disabled","disabled");
				}
			}
		}
		//验证科目编号重复
//		if(checkReply($("[name='kmbh']"))){
//			alert("科目编号"+value+"已经存在！");
//			$(this).val("");
//			return;
//		}
		
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
	//凭证编号下拉框页面跳转
	$("#txt_pzbh").change(function(){
		pzbh = $(this).val();
		pzlx=$("#txt_pzz").val();
		pageSkip(basePath,"pzlr&pzbh="+pzbh+"&pzlx="+pzlx);
	});
	//凭证类型下拉框页面跳转
	$("#txt_pzz").change(function(){
		pzlx = $(this).val();
		pageSkip(basePath,"pzlr&pzlx="+pzlx+"&cmd=add");
	});
	//分页
	$("#btn_first,#btn_prev,#btn_next,#btn_last").click(function(){
		var type = $(this).data("type");
		var pzlx=$("#txt_pzz").val();
		pageSkip(basePath,"pzlr&type="+type+"&pzlx="+pzlx);
	})
	//凭证生成，凭证模板
	$("#btn_bxpzsc").click(function(){
		var pzlx=$("#txt_pzz").val();
		var pzbh = $("[name='pzbh']").find("option:last").val();
		select_commonWin(basePath+"/pageSkip?pageName=bxxx&pzlx="+pzlx+"&pzbh="+pzbh,"报销信息","850","650");
//		var pzlx = $("#txt_pzz").val();
//			select_commonWin(basePath+"/pageSkip?pageName=bxxx&pzlx="+pzlx,"报销信息","850","650");
	});
	$("#btn_xzpzsc").click(function(){
		var pzlx = $("#txt_pzz").val();
		select_commonWin(basePath+"/pageSkip?pageName=xzxx&pzlx="+pzlx,"薪资信息","250","250");
	});
	$("#btn_xzmb").click(function(){
		select_commonWin(basePath+"/pageSkip?pageName=pzmb","选择模板","850","650");
	});
	$("#btn_upload").click(function(){
		select_commonWin(basePath+"/upload?uploadId="+$("[name='uploadId']").val(),"单据上传","850","650");
	});
	//管理费列表页面
	$("#btn_glf").click(function(){
		var pzlx = $("#txt_pzz").val();
		select_commonWin(basePath+"/pageSkip?pageName=glfxx&pzlx="+pzlx,"科研管理费信息","1000","650");
	});
	//凭证明细双击弹窗
	$(document).on("dblclick","[name='kmbh']",function(){
		var id = $(this).attr("id");
		select_commonWin(ctxPath+"/webView/kjhs/pzxx/pzlr/kmTree.jsp?pageName=kjkm&controlId="+id,"会计科目信息","1000","650");
	});
	//右键点击科目触发双击事件
	$(document).on("contextmenu","[name='kmbh']",function(e){
		//取消默认的右键事件
		e.preventDefault();
	    var id = $(this).attr("id");
		select_commonWin(ctxPath+"/webView/kjhs/pzxx/pzlr/kmTree.jsp?pageName=kjkm&controlId="+id,"会计科目信息","1000","650");
	});
	$(document).on("dblclick","[name='jjfl']",function(){
		if($(this).hasClass("non-edit")){
			return;
		}
		var id = $(this).attr("id");
		select_commonWin(basePath+"/pageSkip?pageName=jjkmTree&controlId="+id,"经济科目信息","1000","650");
	});
	//右键点击触发双击事件
	$(document).on("contextmenu","[name='jjfl']",function(e){
		//取消默认的右键事件
		e.preventDefault();
	    if($(this).hasClass("non-edit")){
			return;
		}
		var id = $(this).attr("id");
		select_commonWin(basePath+"/pageSkip?pageName=jjkmTree&controlId="+id,"经济科目信息","1000","650");
	});
	$(document).on("dblclick","[name='bmbh']",function(){
		if($(this).hasClass("non-edit")){
			return;
		}
		var id = $(this).attr("id");
		select_commonWin(basePath+"/pageSkip?pageName=dwTree&type=all&controlId="+id,"部门信息","1000","650");
	});
	//右键点击触发双击事件
	$(document).on("contextmenu","[name='bmbh']",function(e){
		//取消默认的右键事件
		e.preventDefault();
	    if($(this).hasClass("non-edit")){
			return;
		}
		var id = $(this).attr("id");
		select_commonWin(basePath+"/pageSkip?pageName=dwTree&controlId="+id,"部门信息","1000","650");
	});
	$(document).on("dblclick","[name='xmbh']",function(){
		if($(this).hasClass("non-edit")){
			return;
		}
		var id = $(this).attr("id");
		var bmbh = $(this).parents("tr").find("[name='bmbh']").val();
		var bmbhid = "";
		if(bmbh==""){
			bmbhid = $(this).parents("tr").find("[name='bmbh']").attr("id");
		}
		select_commonWin(basePath+"/pageSkip?pageName=xmxx&bmbh="+bmbh+"&controlId="+id+"&bmbhid="+bmbhid,"项目信息","1000","650");
	});
	//右键点击触发双击事件
	$(document).on("contextmenu","[name='xmbh']",function(e){
		//取消默认的右键事件
		e.preventDefault();
	    if($(this).hasClass("non-edit")){
			return;
		}
		var id = $(this).attr("id");
		var bmbh = $(this).parents("tr").find("[name='bmbh']").val();
		var bmbhid = "";
		if(bmbh==""){
			bmbhid = $(this).parents("tr").find("[name='bmbh']").attr("id");
		}
		select_commonWin(basePath+"/pageSkip?pageName=xmxx&bmbh="+bmbh+"&controlId="+id+"&bmbhid="+bmbhid,"项目信息","1000","650");
	});
	//联想输入提示
	$("[name='zy']").bindChange_2(basePath+"/getSuggestInfo","zy",null,null,null,150,false,true);
	$("[name='kmbh']").bindChange_2(basePath+"/getSuggestInfo","kmbh",null,null,null,150);
	$("[name='jjfl']").bindChange_2(basePath+"/getSuggestInfo","jjfl",null,null,null,150);
	$("[name='bmbh']").bindChange_2(basePath+"/getSuggestInfo","bmbh",null,null,null,150);
	$("[name='xmbh']").bindChange_2(basePath+"/getSuggestInfo","xmbh",function($obj){
		var bmbh = $obj.parents("tr").find("[name='bmbh']").val();
		return "bmbh="+bmbh;
	},null,function(){
		
	},150);
	$("[name='zrr']").bindChange(ctxPath+"/suggest/getXx","R");
	$("[name='dfdw']").bindChange(ctxPath+"/suggest/getXx","WL");
//	$("[name='gnkm']").bindChange(ctxPath+"/suggest/getXx","GNKM");
	//辅助录入弹窗
	$(document).on("click","#btn-zrr",function(){
		var controlId = $(this).parents("[data-pzfz]").find("[name='zrr']").attr("id");
		select_commonWin(ctxPath+"/window/rypage?flag=pzfz&controlId="+controlId,"人员信息","1000","650");
	});
	$(document).on("click","#btn-wldw",function(){
 		var controlId = $(this).parents("[data-pzfz]").find("[name='wldw']").attr("id");
		select_commonWin(basePath+"/pageSkip?pageName=wldwsz&controlId="+controlId,"往来单位","1000","650");
	});
	$(document).on("click","#btn-dfdw",function(){
		var controlId = $(this).parents("[data-pzfz]").find("[name='dfdw']").attr("id");
		var num = $(this).parents("[data-pzfz]").attr("data-pzfz");
		var kmbh = $("#pzlr_mx").find("[data-line='"+num+"']").find("[id^='txt_kmbh_']").val();
		select_commonWin(ctxPath+"/kjhs/pzxx/pzlr/wldw?controlId="+controlId+"&kmbh="+kmbh,"往来单位","1000","650");
	});
	$(document).on("click","#btn-gnkm",function(){
		var controlId = $(this).parents("[data-pzfz]").find("[name='gnkm']").attr("id");
		select_commonWin(ctxPath+"/kjhs/pzxx/pzlr/mainKjkmszTree?controlId="+controlId,"功能科目","1000","650");
	});
	//限制凭证日期只能选择未结账日期
	$("#txt_pzrq").click(function(){
		WdatePicker({minDate:pznd+'-'+kjqj+'-01',maxDate:pznd+'-'+kjqj+'-%ld', readOnly:true});
	});
	//查看附件
	$(document).on("click","#btn_ckfj",function(){
		var guid=$("#txt_bxid").val();
		doOperate(ctxPath+"/kjhs/pzxx/pzlr/goFjckPage?uploadId="+guid);
	});
	$(".top_table").on("focus","[name='zy']",function(){
		$(this).parents("[data-col]").addClass("focused");
	});
	$(".top_table").on("blur","[name='zy']",function(){
		$(this).parents("[data-col]").removeClass("focused");
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
		window.alert("没有可以复制的内容或必填项还未填写！");
		return;
	}
	var parentTr = copyTr.clone();//克隆副复制的明细行
	
	parentTr.find(".action").remove();
	var str = '<td class="action"><div class="btn_delete" style=""></div></td>'
		str += '<td class="action"><div class="btn_up" style="">上移</div></td>'
	parentTr.prepend(str);
	var parentFz = $(".pzfz_info:visible").clone();//克隆复制的辅助信息
	parentTr.removeClass("copyed");
	//修改相关的序号
	var num = $("[id='pzlr_mx']").find("tr:visible").length;
	$.ajax({
		url:basePath+"/getGuid",
		data:"",
		type:"post",
		async:"false",
		success:function(val){
			parentTr.find("[name='mxid']").val(val);
		}
	});
	
	parentTr.find(".xh").text(num);
	parentTr.attr("data-line",num);	
	parentTr.find("[name='zy']").attr("id","txt_zy_"+num);
	parentTr.find("[name='kmbh']").attr("id","txt_kmbh_"+num);
	parentTr.find("[name='jjfl']").attr("id","txt_jjfl_"+num);
	parentTr.find("[name='bmbh']").attr("id","txt_bmbh_"+num);
	parentTr.find("[name='xmbh']").attr("id","txt_xmbh_"+num);
	
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
	//联想
	$("[name='zy']").bindChange_2(basePath+"/getSuggestInfo","zy",null,null,null,150,false,true);
	$("[name='kmbh']").bindChange_2(basePath+"/getSuggestInfo","kmbh",null,null,null,150);
	$("[name='jjfl']").bindChange_2(basePath+"/getSuggestInfo","jjfl",null,null,null,150);
	$("[name='bmbh']").bindChange_2(basePath+"/getSuggestInfo","bmbh",null,null,null,150);
	$("[name='xmbh']").bindChange_2(basePath+"/getSuggestInfo","xmbh",function($obj){
		var bmbh = $obj.parents("tr").find("[name='bmbh']").val();
		return "bmbh="+bmbh;
	},null,function(){
		
	},150);
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
				window.alert(data.msg);
			}
		},
		error:function(result){
			window.alert("系统错误，请联系管理员！");
		}
	});
});
//常用摘要:回车和失去焦点的时候触发
$(document).on("keydown","[name='zy']",function(){
	var e = event || window.event || arguments.callee.caller.arguments[0];
    if(e && e.keyCode==13){
    	var zy = $(this).val();
    	var $this = $(this);
    	$.ajax({
    		url:basePath+"/checkZy",
    		data:"zy="+zy,
    		type:"post",
    		dataType:"json",
    		success:function(data){
    			console.log(data.success);
    			if(data.success){
    				$this.parents("tr").find("[name='kmbh']").val(data.kmbh).change();
    			}
    		}
    	});
   }
});
//$(document).on("blur","[name='zy']",function(){
//	var zy = $(this).val();
//	var $this = $(this);
//	$.ajax({
//		url:basePath+"/checkZy",
//		data:"zy="+zy,
//		type:"post",
//		dataType:"json",
//		success:function(data){
//			if(data.success){
//				$this.parents("tr").find("[name='kmbh']").val(data.kmbh).change();
//			}
//		}
//	});
//});
//保存的时候进行项目余额为0的时候进行提示
function doMoney(money1,money2){
	if(money1==""||money1==null||money1=="undefined"||isNaN(money1)){
		return Math.abs(money2);
	}
	return Math.abs(money1);
}
//$(document).on("click","[id='btn_save']",function(){checkXmMoney()});
//当把钱 输在 借方金额 上   ，项目减 钱 
function checkXmMoney(){
	//根据部门编号和项目编号遍历所有的项目
	var objTr = $("#pzlr_mx").find("tr:visible");
	var params = [];
	var param = "";
	var xmMsg = [];
	$.each(objTr.find("[name='bmbh']"),function(index,value){
		var xmbh = $(this).parents("tr").find("[name='xmbh']").val();
		var bmbh = $(this).val();
		if(!isNull(xmbh)&&!isNull(bmbh)){
			param = xmbh+"@"+bmbh;
			if(params.join(",").indexOf(param)<0){
				var je = 0;
				var JeIsJf = false; //金额是借方 false
				//获取 输入 金额 和 借贷 方向
				$.each(objTr.find("[name='xmbh']"),function(){
					var xm = $(this).val();
					var bm = $(this).parents("tr").find("[name='bmbh']").val();
					if(xm+"@"+bm==param){
						var jfje = $(this).parents("tr").find("[name='jfje']").val();
						var dfje = $(this).parents("tr").find("[name='dfje']").val();
						if(jfje != "" && jfje >0 ){
							JeIsJf=true;
							je = parseFloat(je) +parseFloat(jfje);
						}
					}
				});
				//* 判断科目余额方向 和 输入借贷金额  是否同方向  不是同方向 做判断 s
				if(JeIsJf){
					var xh = $(this).parents("tr").attr("data-line");
					params.push(param);
					//剩余金额
					var money = $("[data-pzfz='"+xh+"']").find("[name='xmye']").val();
					//项目 可用金额
					var kyje = $("[data-pzfz='"+xh+"']").find("[name='kyje']").val();
					/*$.ajax({
						type:"post",
						data:"bmbh="+bmbh+"&xmbh="+xmbh,
						url:basePath+"/getXmkyje",
						async:false,
						dataType:"json",
						success:function(val){
							kyje = val.kyje;
						}
					});*/
					console.log(je+"****"+parseFloat(kyje))
					if(je>kyje){
						xmMsg.push("部门编号:"+bmbh+"项目编号:"+xmbh);
					}
				}
				
			}
		}
	});
    //返回输出信息
	if(xmMsg.length>0){
		return xmMsg.join(",");
	}else{
		return "";
	}
};
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
//单据张数后回车事件
$(document).on("keydown","#txt_fjzs",function(){});
function disableEnter(event){
    var e = event || window.event || arguments.callee.caller.arguments[0];
     if(e && e.keyCode==13){
       $("#pzlr_mx").find("tr:visible").eq(0).find("[name='zy']").focus();
    }
}
//科目编号失去焦点判断是否是个人辅助和往来单位辅助
$(document).on("blur","[name='kmbh']",function(){
	checkKmbh($(this));
});
$(document).on("change","[name='kmbh']",function(){
	checkKmbh($(this));
});
$(document).on("keydown","[name='kmbh']",function(){
	var e = event || window.event || arguments.callee.caller.arguments[0];
    if(e && e.keyCode==13){
    	checkKmbh($(this));
   }
});
function checkKmbh($this){
	var kmbh = $this.val();
	if(kmbh=="") return;
	$.ajax({
		url:basePath+"/checkKmbh",
		data:{"kmbh":kmbh},
		type:"post",
		dataType:"json",
		success:function(data){
			var xh = $this.parents("tr").attr("data-line");
			var obj = $("[data-pzfz='"+xh+"']");
			//控制个人辅助的显示
			if(data.grfz=="true"){
				obj.find("[name='zrr']").removeAttr("readonly");
				obj.find("[name='zrr']").removeAttr("disabled");
				obj.find("[id^='btn-zrr']").removeAttr("disabled");
			}else{
				obj.find("[name='zrr']").attr("readonly","readonly");
				obj.find("[name='zrr']").attr("disabled","disabled").val("");
				obj.find("[id^='btn-zrr']").attr("disabled","disabled");
			}
			//控制方来单位辅助的显示
			if(data.wlfz=="true"){
				obj.find("[name='dfdw']").removeAttr("disabled");
				obj.find("[name='dfdw']").removeAttr("readonly");
				obj.find("[id^='btn-dfdw']").removeAttr("disabled");
			}else{
				obj.find("[name='dfdw']").attr("readonly","readonly");
				obj.find("[name='dfdw']").attr("disabled","disabled").val("");
				obj.find("[id^='btn-dfdw']").attr("disabled","disabled");
			}
			if(data.wlfz=="false"&&data.grfz=="false"){
				obj.find("[name='wldc']").attr("readonly","readonly");
				obj.find("[name='wldc']").attr("disabled","disabled");
				obj.find("[name='jsdh']").attr("readonly","readonly");
				obj.find("[name='jsdh']").attr("disabled","disabled");
			}else{
				obj.find("[name='wldc']").removeAttr("readonly");
				obj.find("[name='jsdh']").removeAttr("readonly");
				obj.find("[name='wldc']").removeAttr("disabled");
				obj.find("[name='jsdh']").removeAttr("disabled");
			}
			//控制功能科目的显示
			if(data.gnkm=="true"){
				obj.find("[name='gnkm']").removeAttr("disabled");
				obj.find("[name='gnkm']").removeAttr("readonly");
				obj.find("[id^='btn-gnkm']").removeAttr("disabled");
			}else{
				obj.find("[name='gnkm']").attr("readonly","readonly");
				obj.find("[name='gnkm']").attr("disabled","disabled").val("");
				obj.find("[id^='btn-gnkm']").attr("disabled","disabled");
			}
		}
	});
}

//返回空的辅助录入
function returnNullFzlr(zrr,dfdw,gnkm,arys){
//	if((wldc==""||wldc==null)&&arys.join(",").indexOf("往来对冲")<0){
//		arys.push("往来对冲");
//	}
	if((zrr===""||zrr===null)&&arys.join(",").indexOf("个人")<0){
		arys.push("个人");
	}
	if((dfdw===""||dfdw===null)&&arys.join(",").indexOf("往来单位")<0){
		arys.push("往来单位");
	}
	if((gnkm===""||gnkm===null)&&arys.join(",").indexOf("功能科目")<0){
		arys.push("功能科目");
	}
}
//结算方式控制是否显示银行信息
$(document).on("change","[name='jsfs']",function(){
	var value = $(this).val();
	var parentDiv = $(this).parent("div").parent("div").parent("div").parent("div");
	if(value=="0002"||value=="0003"){
		parentDiv.find(".wyxx").show();
		parentDiv.find(".wyxx").find("input").removeAttr("disabled");
//		parentDiv.find(".wyxx").find("select").removeAttr("disabled");
		var zrr = parentDiv.find("[name='zrr']:visible").val();
		var dfdw = parentDiv.find("[name='dfdw']:visible").val();
		if(!isNull(dfdw)&&dfdw!="undefined"){//生成开户银行
			getKhyhBAndHm("dfdw",parentDiv.find("[name='dfdw']:visible"));
//			getJsdhAndHm("dfdw",parentDiv.find("[name='dfdw']:visible"));
		}
		if(!isNull(zrr)&&zrr!="undefined"){//生成开户银行
			getKhyhBAndHm("zrr",parentDiv.find("[name='zrr']:visible"));
//			getJsdhAndHm("zrr",parentDiv.find("[name='zrr']:visible"));
		}
	}else{
		parentDiv.find(".wyxx:visible").hide();
		parentDiv.find(".wyxx:visible").find("input").attr("disabled","disabled");
	}
});
//生成开户银行
$(document).on("focus","[name='zrr']",function(){
	getKhyhBAndHm("zrr",$(this));
	getJsdhAndHm("zrr",$(this));
});
$(document).on("focus","[name='dfdw']",function(){
	getKhyhBAndHm("dfdw",$(this));
	getJsdhAndHm("dfdw",$(this));
});
//银行信息
function getKhyhBAndHm(type,$this){
	var parentDiv = $this.parent("div").parent("div").parent("div").parent("div");
	//生成户名
	var hm = $this.val();
	parentDiv.find("[name='hm']").val(hm);
	//开始生成开户银行下拉框
	var $khyh = parentDiv.find("[name='khyh']:visible");
	$khyh.find("option").remove(); 
	$khyh.append("<option value=''>--未选择--</option>");
	$.ajax({
		url:basePath+"/getKhyh",
		data:"hm="+hm+"&type="+type,
		type:"post",
		dataType:"json",
		async:false,
		success:function(data){
			if(data){
				$.each(data,function(i,v){
					$khyh.append("<option value="+v.GUID+" zh="+v.KHYHZH+">"+v.KHYH+"</option>");
				});
			}
		}
	});
}
//结算单号
function getJsdhAndHm(type,$this){
	var parentDiv = $this.parent("div").parent("div").parent("div").parent("div");
	//生成户名
	var hm = $this.val();
	parentDiv.find("[name='hm']").val(hm);
	//开始生成开户银行下拉框
	var wldc = parentDiv.find("[name='wldc']:enabled");
	wldc.find("option").remove(); 
	wldc.append("<option value=''>--未选择--</option>");
	$.ajax({
		url:basePath+"/getJsdh",
		data:"hm="+hm+"&type="+type,
		type:"post",
		dataType:"json",
		async:false,
		success:function(data){
			if(data){
				$.each(data,function(i,v){
					wldc.append("<option value="+v.ZCLX+">"+v.ZCLX+"</option>");
				});
			}
		}
	});
}
//生成银行账号
$(document).on("change","[name='khyh']",function(){
	var parentDiv = $(this).parent("div").parent("div").parent("div").parent("div");
	var val = $(this).val();
	if(isNull(val)||val=="undefined"){
		return;
	}
	var khyhzh = $(this).find("option:selected").attr("zh");
	if(!isNull(khyhzh)&&khyhzh!="undefined"){
		parentDiv.find("[name='zh']").val(khyhzh);
	}
});
//验证往来对冲的钱是否过多
function checkWldc(){
	var fzObj = $(".pzfz_info:gt(0)");
	var checkMsg = [];
	var types = [];
	$.each(fzObj,function(i,v){
		var zrr = $(this).find("[name='zrr']").val();
		var dfdw = $(this).find("[name='dfdw']").val();
		var wldc = $(this).find("[name='wldc']").val();
		if(isNull(wldc)){
			return;
		}
		if(!isNull(zrr)&&types.join(",").indexOf("zrr"+zrr)<0){
			types.push("zrr"+zrr);
			var ss = getCheckMsg("zrr",$(this),zrr);
			if(!isNull(ss)){
				checkMsg.push(ss);
			}
		}
		if(!isNull(dfdw)&&types.join(",").indexOf("dfdw"+dfdw)<0){
			types.push("dfdw"+dfdw);
			var ss = getCheckMsg("dfdw",$(this),dfdw);
			if(!isNull(ss)){
				checkMsg.push(ss);
			}
		}
	});
	return checkMsg;
}
function getCheckMsg(type,$this,val){
//	var val = $this.val();
	var wldc = $this.find("[name='wldc']").val();
	var name = $this.find("[name='"+type+"']");
	var money = 0;
	var paaa = "";
	var pzid = 	$("[name='uploadId']").val();
	$.each(name,function(){
		var typeVal = $(this).val();
		var typeWldc = $(this).parent("div").parent("div").parent("div").parent("div").find("[name='wldc']").val();
		if(val==typeVal&&wldc==typeWldc){
			var jfje = $("[data-line='"+$this.attr("data-pzfz")+"']").find("[name='jfje']").val();
			var dfje = $("[data-line='"+$this.attr("data-pzfz")+"']").find("[name='dfje']").val();
			var je = doMoney(jfje,dfje);
			money += je;
		}
	});
	$.ajax({
		url:basePath+"/checkWldc",
		data:{"wldc":wldc,"money":money,"pzid":pzid},
		type:"post",
		dataType:"json",
		async:false,
		success:function(result){
			if(!result.success){
				paaa =  "借款单号:"+wldc+",个人:"+val;
			}
		}
	});
	return paaa;
}
//验证借款单号是否重复
$(document).on("blur","[name='zclx']",function(){
	var zclx = $(this).val();
	if(isNull(zclx)){
		$(this).parent("div").parent("div").parent("div").parent("div").find("[name='wldc']").removeAttr("disabled");
	}else{
		var other = $("[name='zclx']:hidden");
		var zclxMsg = [];
		$.each(other,function(i,v){
			var val = $(this).val();
			if(val==zclx&&zclxMsg.join(",").indexOf(val)<0){
				zclxMsg.push(val);
			}
		});
		var pzid = $("[name='uploadId']").val();
		$.ajax({
			url:basePath+"/checkJkdh",
			data:{"zclx":zclx,"pzid":pzid},
			type:"post",
			dataType:"json",
			async:false,
			success:function(data){
				if(data.success){
					zclxMsg.push(zclx);
				}
			}
		});
		if(zclxMsg.length>0){
			alert("借款单号已存在！");
			$(this).val("");
			$(this).parent("div").parent("div").parent("div").parent("div").find("[name='wldc']").removeAttr("disabled");
			return;
		}
		$(this).parent("div").parent("div").parent("div").parent("div").find("[name='wldc']").attr("disabled","disabled");
	}
});
$(document).on("change","[name='wldc']",function(){
	var val = $(this).val();
	if(isNull(val)){
		$(this).parent("div").parent("div").parent("div").parent("div").find("[name='zclx']").removeAttr("readonly");
		return;
	}else{
		$(this).parent("div").parent("div").parent("div").parent("div").find("[name='zclx']").attr("readonly","readonly").val("");
	}
});
////根据支付方式验证银行找好等必填项
//function wyxx(){
//	var obj = $(".pzfz_info:gt(0)");
//	var msg = [];
//	$.each(obj,function(i,v){
//		var jsfs = $(this).find("[name='jsfs']").val();
//		if(isNull(jsfs)||jsfs=="0004"||jsfs=="0005"){
//			return;
//		}
//		var khyh = $(this).find("[name='khyh']").val();
//		var hm = $(this).find("[name='hm']").val();
//		var zh = $(this).find("[name='zh']").val();
//	});
//}
//添加银行明细
$(document).on("click","#btn_saveMx",function(){
	var copyTr = $("#pzlr_mx").find(".copyed");
	if(copyTr.length==0){
		window.alert("请单击选择录入明细的分录行！");
		return;
	}
	var mxid = copyTr.find("[name='mxid']").val();
	var zbid = $("[name='uploadId']").val();
	if(mxid==""||zbid==""){
		alert("系统异常，请联系管理员！");
		return;
	}
	var xh = copyTr.find(".xh").text();
	//获取输入的借贷方金额
	var jfje = copyTr.find("[name='jfje']").val(); 
	var dfje = copyTr.find("[name='dfje']").val(); 
	var je ;
	if(jfje != "" && dfje == ""){
		je = jfje;
	}else if(jfje == "" && dfje != ""){
		je = dfje;
	}else if(jfje == "" && dfje == ""){
		je = 0.00;
	}	
	select_commonWin(basePath+"/WindowMx?mxid="+mxid+"&zbid="+zbid+"&je="+je,"第"+xh+"行分录明细录入","850","650");
});
//银行明细  和 借贷方 金额 比较
$("[name='jfje']").blur(function(){
	var val = $(this).val();
	var partr = $(this).parents("tr");
	var num = partr.attr("data-line");
	var mxid = partr.find("#txt_mxid").val();
	if(val != ""){
		$.ajax({
			url:basePath+"/getSumMxje",
			data:"mxid="+mxid,
			type:"post",
			dataType:"json",
			async:false,
			success:function(data){
				if(data.sumje!=null && data.sumje != Math.abs(val)){
					alert("第"+num+"行银行明细总金额和输入的借方金额不一样,请及时修改")
				}
			}
		});
	}//if
});
$("[name='dfje']").blur(function(){
	var val = $(this).val();
	var partr = $(this).parents("tr");
	var num = partr.attr("data-line");
	var mxid = partr.find("#txt_mxid").val();
	if(val != ""){
		$.ajax({
			url:basePath+"/getSumMxje",
			data:"mxid="+mxid,
			type:"post",
			dataType:"json",
			async:false,
			success:function(data){
				if(data.sumje!=null && data.sumje != Math.abs(val)){
					alert("第"+num+"行银行明细总金额和输入的贷方金额不一样,请及时修改")
				}
			}
		});
	}//if
});









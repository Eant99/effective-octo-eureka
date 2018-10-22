//获取顶级窗口 和各嵌套页面名
function getParent(parentName){
  if(parentName==null||parentName==undefined||parentName=='undefined'){
      parentName='login/login_toIndex';
  }
  var names = this.name;
  var tops=this;
  while(tops.parent!=tops){//顶级的parent和自身相同
	  if (tops.location.pathname.indexOf(parentName)>0){
		  break;
	  }else{
		  tops=tops.parent;
		  if(names != ""){
			  names += ",";
		  }
		  names += tops.name
	  }
  }
  names=names.substring(0,names.length-1);
  return {name:names};//只能用索引名访问  return [tops,names] //索引访问;
};
var getPname = function(){
	var par = getParent();
	return String(par["name"]);
};

//查找控件(适用于直接往页面内的某个id控件写入内容)
function getIframeControl(names,controlId){
    var name= names.split(",");
    var findC='$(top.document)'
    for (var i=name.length-1;i>=0;i--){
      findC+='.contents().find("[id='+name[i]+']")';
    }
    findC+='.contents().find("[id='+controlId+']")';
    try{
    	return eval(findC);
    }catch(e){
        return null;
    }
}
//凭证录入查找控件(适用于直接往页面内的某个id控件写入内容)
function getIframeControlForPzlr(names,controlId){
    var name= names.split(",");
    var findC='$(top.document)'
    for (var i=name.length-1;i>=0;i--){
      findC+='.contents().find("[id='+name[i]+']")';
    }
    findC+='.contents().find(".copyed").find("[id='+controlId+']")';
    try{
    	return eval(findC);
    }catch(e){
        return null;
    }
}
//凭证辅助录入查找控件(适用于直接往页面内的某个id控件写入内容)
function getIframeControlForPzfz(names,controlId){
    var name= names.split(",");
    var findC='$(top.document)'
    for (var i=name.length-1;i>=0;i--){
      findC+='.contents().find("[id='+name[i]+']")';
    }
    findC+='.contents().find(".pzfz_info:visible").find("[id='+controlId+']")';
    try{
    	return eval(findC);
    }catch(e){
        return null;
    }
}
//获取控件iframe对象
function getIframTarget(names){
    var name= names.split(",");
    var findC='$(top.document)'
    for (var i=name.length-1;i>=0;i--){
      findC+='.contents().find("[id='+name[i]+']")';
    }
    try{
    	return eval(findC);
    }catch(e){
    	return null;
    }
}
//获取控件iframe对象内容（可以直接使用jquery的方式查询控件）
function getIframDoc(names){
    var name= names.split(",");
    var findC='$(top.document)'
    for (var i=name.length-1;i>=0;i--){
      findC+='.contents().find("#'+name[i]+'")';
    }
    findC+='.contents()';
    try{
      return eval(findC);
    }catch(e){
      return null;
    }
}
//获取控件iframe对象window窗体
function getIframWindow(names){
	var name= names.split(",");
	var findC='$(top.document)';
	for (var i=name.length-1;i>=0;i--){
		findC+='.contents().find("#'+name[i]+'")';
	}
	findC+='[0].contentWindow';
	try{
		return eval(findC);
	}catch(e){
		return null;
	}
}
//**************************************//
//通用弹出选择框 
//参数说明：_url请求路径  _title弹窗标题  _width弹窗宽度  _height弹窗高度
function select_commonWin(_url,_title,_width,_height,_scroll){
	if($("#txt_sjdw").length > 0){
		$("#txt_sjdw").focus();
	}
	
	if(_scroll == undefined){
		_scroll = false;
	}
	else if(typeof _scroll == "string"){
		if(_scroll == "yes"){
			_scroll = true;
		}
		else{
			_scroll = false;
		}
	}
	
	var par=getParent();
	if (_url.indexOf('?')>0){
	    _url=_url+"&pname="+String(par["name"]);
	}else{
	    _url=_url+"?pname="+String(par["name"]);
	}
	getTopFrame().layer.open({
	 	title:_title,
	    type: 2,
	    shadeClose:true,
	    scrollbar: _scroll,
	    content: _url,
	    area: [_width+"px",_height+"px"]//,
//	    success: function(layer, index) {
//	    	getTopFrame().layer.iframeAuto(index);
//	    }
	});
}

//**************************************//
//综合查询弹出窗
//参数说明：_url请求路径  
function search_commonWin(_url,_scroll){
	
	if(_scroll == undefined){
		_scroll = false;
	}
	else if(typeof _scroll == "string"){
		if(_scroll == "yes"){
			_scroll = true;
		}
		else{
			_scroll = false;
		}
	}
	
	var par=getParent();
	if (_url.indexOf('?')>0){
	    _url=_url+"&pname="+String(par["name"]);
	}else{
	    _url=_url+"?pname="+String(par["name"]);
	}
	getTopFrame().layer.open({
	 	title:"综合查询",
	    type: 2,
	    shadeClose:true,
	    scrollbar: _scroll,
	    content: _url,
	    area: ["920px","630px"]//,
	});
}

function zhcx_win(_url){
	
	var par=getParent();
	if (_url.indexOf('?')>0){
	    _url=_url+"&pname="+String(par["name"]);
	}else{
	    _url=_url+"?pname="+String(par["name"]);
	}
	getTopFrame().layer.open({
	 	title:"综合查询",
	    type: 2,
	    shadeClose:true,
	    scrollbar: false,
	    content: _url,
	    area: ["780px","460px"]//,
	});
}


/************************************************** 通用人员、单位等弹出窗口   开始***************************************************/
$(document).ready(function(){
	//单位信息查看弹窗(通用)
   	$(document).on("click",".dwlook",function(){
   		var dwbh = $(this).attr("dwbh");
   		var path = $(this).attr("path");
   		if(dwbh!=""&&dwbh!=undefined){
   			select_commonWin(path+"/dwb/goTyDwbWin?operateType=L&dwbh="+dwbh,"单位信息","500","280");
   		}
   		return false;
   	});
   	//人员信息查看弹窗(通用)
   	$(document).on("click",".rylook",function(){
   		var rybh = $(this).attr("rybh");
   		var path = $(this).attr("path");
   		if(rybh!=""&&rybh!=undefined){
   			//select_commonWin(path+"/ryb/goRybLook?operateType=L&rybh="+rybh,"人员信息","400","350");
   			select_commonWin(path+"/ryb/goTyRybWin?operateType=L&rybh="+rybh,"人员信息","550","260");
   		}
   		return false;
   	});
   	//地点信息查看弹窗（通用）
   	$(document).on("click",".ddlook",function(){
   		var ddbh = $(this).attr("ddbh");
   		var path = $(this).attr("path");
   		if(ddbh != "" && ddbh != undefined){
   			select_commonWin(path+"/ddb/goTyDdbWin?operateType=L&ddbh="+ddbh,"地点信息","350","240");
   		}
   		return false;
   	});
   	//资产信息查看弹窗(通用)
	$(document).on("click",".zclook",function(){
   		var yqbh = $(this).attr("yqbh");
   		var path = $(this).attr("path");
   		var ysdh = $(this).attr("ysdh");
   		var jzrlx = $(this).attr("jzrlx");
   		if(yqbh!="" && yqbh!=undefined){
   			select_commonWin(path+"/zjb/goLookPage?yqbh="+yqbh+"&ysdh="+ysdh+"&jzrlx="+jzrlx,"资产信息","1200","630","yes");
   			//select_commonWin(path+"/zjb/goLookPage?yqbh="+yqbh+"&ysdh="+ysdh+"&jzrlx="+jzrlx,"资产信息","600","430","yes");
   		}
   		return false;
   	});	
});

/**
 * 批量赋值弹窗窗口
 * @param _length  数据条数
 * @param _url   路径及参数
 * @param _width  弹出框长度
 * @param _height   弹出框宽度
 */
function doPlfz(_length,_url,_width,_height){
	confirm("确认要批量赋值"+_length+"条信息",{title:"提示"},function(idx){
		close(idx);
		select_commonWin(_url,"批量赋值",_width,_height);
	});
}

/************************************************** 通用人员、单位等弹出窗口   结束***************************************************/

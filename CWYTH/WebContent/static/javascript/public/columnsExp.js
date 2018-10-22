/**
 * 1：该方法集成了列选择与导出功能，但需要注意，列选择按钮的id必须是btn_lxz，导出按钮的id必须是btn_exp，否则本方法不起作用
 * 2：传入的参数：
 * 　　(1) columns：list页面当中列表数据当中的columns变量
 *     (2) tableid：可以标识要导出列的table的标志。一般用id
 *     (3) boxid：searchBox的id
 *     (4) tabledom：list页面当中的列表数据当中的table变量
 *     (5) expurl：导出的路径，如果页面上没有btn_exp按钮
 *     (6) exptitle：导出的excel的名称
 *     (7) _path：${pageContext.request.contextPath}
 * 3：注意：
 * 	   (1) 本方法依赖于public-js.js和FileController.java中的fileDownload方法
 * 	   (2) 页面中columns里选择框的class需要带有keyId
 * 	   (3) 使用本方法需要在页面上的columns中添加
 * 			a：zhpxname属性，值为导出时表头需要显示的信息，如果该列不需要导出，不要加该属性，如果该列有expCol属性，则导出时zhpxname没有任何作用
 * 			b：expCol属性，值为导出时表头需要显示的信息，当页面上组合排序、列选择、导出等按钮同时存在，只有导出需要导该字段，其他都不要时，添加本属性，只在导出时起作用，优先级高于zhpxname
 * 			c：expType属性，值为num，表示该字段是数字，导出的信息居右显示，值为dbl，表示该字段是保留两位小数的数字 导出的信息除居右显示外，还需要保留两位小数，不是数字不需要添加该属性
 * 			d：expName属性，当导出的字段跟data字段不一致时，需要添加该属性，值为导出字段的名称，一致时不需要添加该属性，优先级高于columns的data
 * 	   (4) 使用本方法需提前在页面上添加
 * 			a：<div class="btn-group"><button id="btn_lxz" class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">列选择</button></div>
 * 			b：<button id="btn_exp" type="button" class="btn btn-default">导出Excel</button>
 * 			两个按钮，不想使用列选择的可以不加a列选择按钮，不想使用导出的，可以不加b按钮，如果页面上的列选择或者导出比较特殊，可以将id改成其他名字
 * 	   (5) 使用本方法需在页面上调用lxzExp，例如：lxzExp(columns,"#mydatatables","#searchBox",table,"${pageContext.request.contextPath}/shenhe/doExp?mkbh=${param.mkbh}&ztbz=${param.sh_ztbz}","耐用品建账归口审核信息","${pageContext.request.contextPath}");
 */	
var lxzExp = function(columns,tableid,boxid,tabledom,expurl,exptitle,_path){
	console.log(1);
	var ulid = "ul_"+Math.floor(Math.random()*100);//随机给ul产生一个id
	if(expurl.indexOf("?") < 0){
		expurl = expurl + "?";
	}
	else{
		expurl = expurl + "&";
	}
	
	if($("#btn_lxz").length > 0){
		//1 填充列选择的下拉框
		console.log(2);
		
		$("#btn_lxz").after('<ul id="' + ulid + '" class="dropdown-menu lxz dropdown-menu-right" role="menu"></ul>');
		
		ulid = "#"+ulid;
		$(ulid).append('<li style="margin-left: 5px;"><input type="button" class="btn btn-default" id="btn_lxz_check_all" value="全选"><input type="button" class="btn btn-default" id="btn_lxz_check_none" value="全不选"></li>');
		
		for(var i=0;i<columns.length;i++){
			if(columns[i].zhpxname != undefined || columns[i].expCol != undefined){
				$(ulid).append("<li><input type='checkbox' class='except' name='icheck' data-column='"+i+"' checked />"+(columns[i].expCol == undefined ? columns[i].zhpxname : columns[i].expCol)+"</li>");
			}
		}
	
		console.log(3);
		//2 点击列选择的选项时使该选项导出不导出和控制list页面使该列显示不显示
		$(ulid).on("change",'input[name="icheck"]',function(){
			var column = tabledom.column( $(this).data('column') );
			if($(this).is(":checked")){
				column.visible(true);
	   			if($(tableid).width() > $(boxid).width()){
		   			$("div.DTFC_RightWrapper").show();
		   		}
	   		}else{
	   	        column.visible(false);
	   	     	if($(tableid).width() <= $(boxid).width()){
					$("div.DTFC_RightWrapper").hide();
				}
	   		}
		});
		console.log(4);
		$(ulid).on("click","#btn_lxz_check_all",function(){
			$(".lxz .except").each(function(){
				$(this).prop("checked",true);
				var column = tabledom.column( $(this).data('column') );
	   			column.visible(true);
	   			if($(tableid).width() > $(boxid).width()){
		   			$("div.DTFC_RightWrapper").show();
		   		}
			});
		});
		console.log(5);
		//全不选
		$(ulid).on("click","#btn_lxz_check_none",function(){
			$(".lxz .except").each(function(){
				$(this).prop("checked",false);
				var column = tabledom.column( $(this).data('column') );
				column.visible(false);
				if($(tableid).width() <= $(boxid).width()){
					$("div.DTFC_RightWrapper").hide();
				}
			});
		});
		console.log(6);
	}
	
	if($("#btn_exp").length > 0){
		$("#btn_exp").click(function(){
			//searchJson方法在public-js.js里
			var json = searchJson(boxid.substr(1));
			var checkbox = $(tableid).find(".keyId").filter(":checked");
			var id = [];
			checkbox.each(function(){
				id.push($(this).val());
			});
			
			if($("#btn_lxz").length > 0){
				var list = [];
				$(ulid).find("[name='icheck']").filter(":checked").each(function(){
					var i = $(this).data('column');
					if((columns[i].expCol == undefined || columns[i].expCol == "") && (columns[i].zhpxname == undefined || columns[i].zhpxname == "")){
						
					}
					else{
						var expData = (columns[i].expName == undefined || columns[i].expName == "") ? columns[i].data : columns[i].expName;
						var expVal = (columns[i].expCol == undefined || columns[i].expCol == "") ? columns[i].zhpxname : columns[i].expCol;
						var expType = (columns[i].expType == undefined || columns[i].expType == "") ? "" : columns[i].expType;
						
						list.push("{expData:'" + expData + "',expVal:'" + expVal + "',expType:'" + expType + "'}");
					} 
				});
				doLxzExp(expurl+"searchJson="+json+getOrderCol(tabledom,columns)+"&columns="+encodeURIComponent("[" + list.join(",") + "]"),exptitle,_path,id.join(","));
			}
			else{
				var list = [];
				for(var i=0;i<columns.length;i++){
					if((columns[i].expCol == undefined || columns[i].expCol == "") && (columns[i].zhpxname == undefined || columns[i].zhpxname == "")){
						
					}
					else{
						var expData = (columns[i].expName == undefined || columns[i].expName == "") ? columns[i].data : columns[i].expName;
						var expVal = (columns[i].expCol == undefined || columns[i].expCol == "") ? columns[i].zhpxname : columns[i].expCol;
						var expType = (columns[i].expType == undefined || columns[i].expType == "") ? "" : columns[i].expType;
						
						list.push("{expData:'" + expData + "',expVal:'" + expVal + "',expType:'" + expType + "'}");
					} 
				}
				
				if(list.length == 0){
					doExp(json,expurl+"searchJson="+json+getOrderCol(tabledom,columns),exptitle,_path,id.join(","));
				}
				else{
					doExp(json,expurl+"searchJson="+json+getOrderCol(tabledom,columns)+"&columns="+encodeURIComponent("[" + list.join(",") + "]"),exptitle,_path,id.join(","));
				}
			}
		});
	}
};

function doLxzExp(_url,_fileName,_path,_selectedId,xx){
	var index;
	if(xx == undefined || xx == ""){
		xx = "确认导出全部信息？";
		if(_selectedId != ''&& _selectedId != 'undefined'){
			xx = "确认要导出选中的信息？";
		}
	}
	confirm(xx, {title:"提示"}, function(z){
		$.ajax({
			type:"post",
			data:"xlsname=" + _fileName + "&id=" + _selectedId,
			url:_url,
			dataType:"json",
			success:function(val){
				close(index);
				close(z);
				if(val.url != "undefined" && val.url != ""){
					FileDownload(_path + "/file/fileDownload", _fileName + ".xls", val.url);
				}
				else if(val.errurl){
					if(val.msg){
						alert(val.msg);
					}
					else{
						alert("导出失败！");
					}
					FileDownload(_path + "/file/fileDownload", _fileName + "_错误信息.xls", val.errurl);
				}
				else if(val.msg){
					alert(val.msg);
				}
				else{
					alert("导出失败！");
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
}

function getOrderCol(tabledom,columns){
	if(tabledom.order().length == 0){
		return "";
	}
	else{
		var ordercol = "";
		var flag = true;
		for(var i = 0; i < tabledom.order().length; i++){
			if(flag){
				var item = tabledom.order()[i];
				if(typeof item == 'number'){
					ordercol += columns[item].data + " " + (tabledom.order()[1] == undefined ? "" : tabledom.order()[1]) + ",";
					flag = false;
				}
				else if(typeof item == 'string'){
					ordercol += item + " " + (tabledom.order()[1] == undefined ? "" : tabledom.order()[1]) + ",";
					flag = false;
				}
				else{
					ordercol += columns[item[0]].data + " " + item[1] + ",";
				}
			}
		}
		if(ordercol == ""){
			return "";
		}
		else{
			return "&ordercol=" + ordercol.substr(0,ordercol.length - 1);
		}
	}
}
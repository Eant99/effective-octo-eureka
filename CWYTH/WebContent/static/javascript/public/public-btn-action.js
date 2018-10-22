/****************列表页面生成的操作按钮   开始********************************/
var HREF_EDIT = '<a href="javascript:void(0);" class="btn btn-link btn_edit" data-url-type="skip" data-url="basePath/goEditPage" data-operate-type="update" data-param-name="guid,%P">编辑</a>';
var HREF_VIEW = '<a href="javascript:void(0);" class="btn btn-link btn_view" data-url-type="skip" data-url="basePath/goViewPage" data-operate-type="view" data-param-name="guid,%P">查看</a>';
var HREF_CHECK = '<a href="javascript:void(0);" class="btn btn-link btn_check" data-url-type="skip" data-url="basePath/goViewPage" data-operate-type="check" data-param-name="guid,%P">审核</a>';
var HREF_RECORD = '<a href="javascript:void(0);" class="btn btn-link btn_record" data-url-type="skip" data-url="'+getContextPath()+'/process/processLs" data-param-name="guid,procinstid,%P">办理记录</a>';
var HREF_SUBMIT = '<a href="javascript:void(0);" class="btn btn-link btn_submit" data-url-type="action" data-url="basePath/doSubmit" data-param-name="guid,%P">提交</a>';
var HREF_DELETE = '<a href="javascript:void(0);" class="btn btn-link btn_delete" data-url-type="action" data-url="basePath/doDelete" data-param-name="guid,%P">删除</a>';
var HREF_PRINT = '<a href="javascript:void(0);" class="btn btn-link btn_print" data-url-type="window" data-url="basePath/goPrintPage" data-param-name="guid,%P">打印</a>';
/*******************列表页面生成的操作按钮   结束*****************************/
$.fn.bindAction = function(){
	var basePath = getBasePath();
	var ctxPath = getContextPath();
	$(this).on("click",function(){
		//路径类型
		var urlType = $(this).data("url-type");
		//路径
		var url = $(this).data("url").replace("basePath",basePath);
		//参数名称数组
		var paramNames = $(this).data("param-name").split(",");
		//checkbox中保存的数据
		var datas = $(this).parents("tr").find(".keyId").data();
		if(isNull(datas)){
			datas = {};
		}
		//遍历参数名称数组生成参数
		var param = "";
		for(var i in paramNames){
			var paramName = paramNames[i];
			var paramValue = datas[paramName];
			if(isNull(paramValue)){
				continue;
			}
			param += "&" + paramName + "=" + paramValue;
		}
		var action = $(this).text();
		switch(urlType){
			case "skip":
				var operateType = $(this).data("operate-type");
				doSkip(url,param,operateType);
				break;
			case "action":
				doDeal(action,url,param);
				break;
			default:
				break;
		}
	});
};
$(document).on("click","#btn_batchDelete",function(){
	var url = getBasePath() + "/doDelete";
	doBatchDeal("删除",url);
})
$(document).on("click","#btn_batchSubmit",function(){
	var url = getBasePath() + "/doSubmit";
	doBatchDeal("提交",url);
})


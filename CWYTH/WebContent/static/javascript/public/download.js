/**
                ☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★★
                ★ 脚本说明：js无刷新下载文件类                        ★
                ☆ 版权信息：Copyright ® master                    ☆
                ★ 时 间：   2016.11.05                           ★
                ☆ 作 者：   袁震（参考自赵振江经理的类）                ☆
                ★ 版 本：   Ver 0.1                              ★
                ☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆
**/
/*
文件下载方法
*/
var FileDownload = function(url,filename,filepath){
    var thelement = {};//声明内部对象
    thelement.url = url;
    thelement._ifbody = null;
    thelement._iframe = document.createElement("iframe");
    thelement._form = document.createElement("form");//form表单元素
    thelement._body = document.createElement("div");//form表单容器不显示
    thelement._input = document.createElement("input");//元素
    thelement._input2 = document.createElement("input");//元素
    thelement._init = function(){//初始类函数
        thelement._body.style.display = "none";
        thelement._body.appendChild(thelement._iframe);
        document.body.appendChild(thelement._body);
        thelement._form.action = [thelement.url,"?time=",(new Date()).getTime()].join("");
        thelement._form.method = "post";
        thelement._input.type = "hidden";
        thelement._input.name = "fileName";
        thelement._input2.type = "hidden";
        thelement._input2.name = "filePath";
        if(!filename){
        	filename = "";
    	}
        thelement._input.value = filename;
        thelement._input2.value = filepath;
    	thelement._form.appendChild(thelement._input);
    	thelement._form.appendChild(thelement._input2);
        if (thelement._iframe.contentDocument)
        	thelement._ifbody = thelement._iframe.contentDocument.body;
        else if (test.document)
        	thelement._ifbody = thelement._iframe.document.body;
        else
        	thelement._ifbody = thelement._iframe.contentWindow.document.body;
        thelement._ifbody.appendChild(thelement._form);
        thelement._form.submit();
    };
    thelement._init();//初始类函数
};

/*
文件下载方法
*/
var FileDownloadOne = function(url){
	var thelement = {};//声明内部对象
	thelement.url = url;
	thelement._form = document.createElement("form");//form表单元素
	thelement._body = document.createElement("div");//form表单容器不显示
	thelement._init = function(){//初始类函数
	thelement._body.style.display = "none";
	thelement._body.appendChild(thelement._form);
	document.body.appendChild(thelement._body);
	thelement._form.action = [thelement.url,"?time=",(new Date()).getTime()].join("");
	thelement._form.method = "get";
	thelement._form.submit();
	};
	thelement._init();//初始类函数
};
<%--基础脚本--%>
<script src="${pageContext.request.contextPath}/static/javascript/jquery/jquery.js"></script>
<script src="${pageContext.request.contextPath}/static/javascript/bootstrap/bootstrap.min.js"></script>
<%--第三方脚本plugins--%>
<script src="${pageContext.request.contextPath}/static/plugins/datatable/js/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/datetimepicker/bootstrap-datetimepicker.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/validator/bootstrapValidator.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/fileinput/fileinput.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/suggest/jquerySuggest.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/select2/js/select2.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/select2/js/i18n/zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/My97DatePicker/WdatePicker.js"></script>
<script src="${pageContext.request.contextPath}/static/javascript/img/jquery.photo.gallery.js"></script>
<%--自定义脚本--%>
<script src="${pageContext.request.contextPath}/static/javascript/public/public-window.js"></script>
<script src="${pageContext.request.contextPath}/static/javascript/public/public-js.js"></script>
<script src="${pageContext.request.contextPath}/static/javascript/public/public-smsc.js"></script>
<%--解决回车弹窗多次触发事件问题--%>
<script>
document.onkeypress = function(e){    
    var ev =document.all ? window.event : e;  
    if(ev.keyCode==13) { 
        $(this).trigger("click");
		return false
    }  
  }
</script>

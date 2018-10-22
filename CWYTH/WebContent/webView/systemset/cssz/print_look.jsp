<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>打印格式预览</title>
</head>
<body>
	<center>
      	<form name="printContent" method="post" action="" id="printContent">
      		<div style="margin: 0px; padding: 0px;text-align:center">
      			<c:if test="${djlx=='ysd'}">
   				    <c:if test="${sftd=='1'}">
   				       <img id="img_print"  src="${pageContext.request.contextPath}/static/images/systemSet/printimgs/ysdtd.jpg" alt="模板打印预览……"/>
   				    </c:if>
   				    <c:if test="${sftd=='0'}">
   				    	<c:if test="${mbbh=='1'||mbbh=='3'}">
   				       		<img id="img_print"  src="${pageContext.request.contextPath}/static/images/systemSet/printimgs/ysdftd.jpg" alt="模板打印预览……"/>
   				    	</c:if>
   				    	<c:if test="${mbbh=='2'}">
   				       		<img id="img_print"  src="${pageContext.request.contextPath}/static/images/systemSet/printimgs/ysdmb1_ftd.jpg" alt="模板打印预览……"/>
   				    	</c:if>
   				    	<c:if test="${mbbh=='4'}">
   				       		<img id="img_print"  src="${pageContext.request.contextPath}/static/images/systemSet/printimgs/yshd_4.jpg" alt="模板打印预览……"/>
   				    	</c:if>
   				    	<c:if test="${mbbh=='5'}">
   				       		<img id="img_print"  src="${pageContext.request.contextPath}/static/images/systemSet/printimgs/yshd_5.jpg" alt="模板打印预览……"/>
   				    	</c:if>
   				    </c:if>
      			</c:if>
      			<c:if test="${djlx=='kp'}">
      				<c:if test="${mbbh=='1'}">
      				       <img id="img_print"  src="${pageContext.request.contextPath}/static/images/systemSet/printimgs/kpmrmb.jpg" alt="模板打印预览……"/>
      				</c:if>
      			</c:if>
      			<c:if test="${djlx=='bdd'}">
			    	<c:if test="${mbbh=='1'}">
			       		<img id="img_print"  src="${pageContext.request.contextPath}/static/images/systemSet/printimgs/bdd_xmbd.jpg" alt="模板打印预览……"/>
			    	</c:if>
			    	<c:if test="${mbbh=='2'}">
			       		<img id="img_print"  src="${pageContext.request.contextPath}/static/images/systemSet/printimgs/bdd_dj.jpg" alt="模板打印预览……"/>
			    	</c:if>
			    	<c:if test="${mbbh=='3'}">
			       		<img id="img_print"  src="${pageContext.request.contextPath}/static/images/systemSet/printimgs/bdd_fjzj.jpg" alt="模板打印预览……"/>
			    	</c:if>
			    	<c:if test="${mbbh=='4'}">
			       		<img id="img_print"  src="${pageContext.request.contextPath}/static/images/systemSet/printimgs/bdd_czfj.jpg" alt="模板打印预览……"/>
			    	</c:if>
      			</c:if>
      			<c:if test="${djlx=='czd'}">
			    	<c:if test="${mbbh=='1'}">
			       		<img id="img_print"  src="${pageContext.request.contextPath}/static/images/systemSet/printimgs/czd_1.jpg" alt="模板打印预览……"/>
			    	</c:if>
			    	<c:if test="${mbbh=='2'}">
			       		<img id="img_print"  src="${pageContext.request.contextPath}/static/images/systemSet/printimgs/czd_2.jpg" alt="模板打印预览……"/>
			    	</c:if>
			    	<c:if test="${mbbh=='3'}">
			       		<img id="img_print"  src="${pageContext.request.contextPath}/static/images/systemSet/printimgs/czd_3.jpg" alt="模板打印预览……"/>
			    	</c:if>
			    	<c:if test="${mbbh=='4'}">
			       		<img id="img_print"  src="${pageContext.request.contextPath}/static/images/systemSet/printimgs/czd_4.jpg" alt="模板打印预览……"/>
			    	</c:if>
      			</c:if>
      		</div>
      	</form>
    </center>
</body>
</html>
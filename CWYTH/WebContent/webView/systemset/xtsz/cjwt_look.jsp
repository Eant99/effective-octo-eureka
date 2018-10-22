<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>常见问题</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico"/>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
        .wb{
            font-size:11pt;
            line-height:26px;
        }
        .kg{
            padding:0 16pt;
        }
    </style>

</head>
<body style="background-color: white;  font-family: '宋体';">
<form id="myform" class="form-horizontal" action="" method="post" >
	<div class="container-fluid dialog-body" style="font-size: 20px;padding:20px;">
		<div class="row">
			<div class="col-md-5"></div>
			<div class="col-md-2">
				<div class="input-group">
					<p style="font-weight: bold;">常见问题</p>
				</div>
			</div>
			<div class="col-md-5"></div>
		</div>
	</div>
	<c:if test="${fn:length(cjwtList) >0}">
		<c:forEach var="cjwt" items="${cjwtList}" varStatus="status"> 
			<div class="container-fluid dialog-body" style="font-size: 14px;padding-bottom:10px;">
				<div class="row">
					<div class="col-md-2"></div>
					<div class="col-md-8">
						<div class="input-group">
							<p>
								<strong>${status.index+1 }、${cjwt.TITLE}<span style="color:#D3D4D5;  margin: 20px;">最近更新时间：${cjwt.UPDDATE}</span></strong>  
							</p>
							<p>
					            <span  class="kg"></span> 答：${cjwt.XX}
					            <br />
							</p>
						</div>
					</div>
					<div class="col-md-2"></div>
				</div>
			</div>
		</c:forEach>
	</c:if>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
</body>
</html>
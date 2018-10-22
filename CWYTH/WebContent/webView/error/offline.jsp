<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>超时页面</title>
<%@include file="/static/include/public-manager-css.inc"%>
<body>
	<div class="container-fluid" id="main-container">
		<div id="main-content" class="clearfix">
			<div id="page-content" class="clearfix">
				<div class="row-fluid">
					<div class="error-container">
						<div class="well" style="margin-top: 20px;">
							<h1 class="grey lighter smaller">
								<span class="blue bigger-125"><i class="icon-sitemap"></i>
								</span>系统已超时掉线！
							</h1>
							<hr />
							<h3 class="lighter smaller">你可以尝试以下方法：</h3>
							<div>
								<div class="space"></div>
								<ul class="unstyled spaced inline bigger-110">
									<li><i class="icon-hand-right blue"></i><h5>您的登录超时失效,请重新登录!</h5></li>
							</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<%@include file="/static/include/public-manager-js.inc"%>	
</body>
</html>
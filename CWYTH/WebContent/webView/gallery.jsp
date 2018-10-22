<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/javascript/img/photoGallery.css"/>
	<script src="${pageContext.request.contextPath}/static/javascript/jquery/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/static/javascript/img/jquery.photo.gallery.js"></script>
</head>
<body>
<div class="box">
	<header drag>
		<div class="winControl" noDrag>
	        <span class="closeWin" ><i class="icon_close-big"></i></span>
	    </div>
	</header>
	<div class="gallery" ></div>
</div>
<script>
	$.initGallery();
</script>
</body>
</html>
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang="en"> <!--<![endif]-->
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

	<title>Ftp Uploader Sample</title>
	<meta name="description" content="">
	<meta name="author" content="">

	<meta name="viewport" content="width=device-width">

	<link href="${f:url('/favicon.ico')}" rel="icon" type="image/vnd.microsoft.icon" />
	<link href="${f:url('/favicon.ico')}" rel="shortcut icon" type="image/vnd.microsoft.icon" />
	<link rel="stylesheet" href="${f:url('/css/bootstrap.css')}">
	<style>
	body {
	  padding-top: 60px;
	  padding-bottom: 40px;
	}
	</style>
	<link rel="stylesheet" href="${f:url('/css/bootstrap-responsive.css')}">
	<link rel="stylesheet" href="${f:url('/css/style.css')}">

	<script src="${f:url('/js/libs/modernizr-2.5.3-respond-1.1.0.min.js')}"></script>
</head>
<body>
<!--[if lt IE 7]><p class=chromeframe>Your browser is <em>ancient!</em> <a href="http://browsehappy.com/">Upgrade to a different browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">install Google Chrome Frame</a> to experience this site.</p><![endif]-->

    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="#">Ftp Uploader Sample</a>
          <div class="nav-collapse">
            <ul class="nav">
              <li class="active"><a href="${f:url('/home')}">Home</a></li>
              <li><a href="#about">About</a></li>
              <li><a href="#contact">Contact</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

	<div class="container">

		<!-- Main hero unit for a primary marketing message or call to action -->
		<div>
			<h1>ログイン画面</h1>
			<html:errors/>
			<s:form>
				<label>ユーザー名：<html:text property="username"/></label><br>
				<label>パスワード：<html:password property="password"/></label><br>
				<input type="submit" name="doLogin" value="ログイン"/>
			</s:form>
		</div>

		<hr>

		<footer>
			<p>&copy; Company 2012</p>
		</footer>

	</div> <!-- /container -->
<script src="${f:url('/js/libs/jquery-1.7.1.min.js')}"></script>
<script src="${f:url('/js/libs/bootstrap/transition.js')}"></script>
<script src="${f:url('/js/libs/bootstrap/collapse.js')}"></script>

<script src="${f:url('/js/script.js')}"></script>
<%--
<script>
	var _gaq=[['_setAccount','UA-XXXXX-X'],['_trackPageview']];
	(function(d,t){var g=d.createElement(t),s=d.getElementsByTagName(t)[0];
	g.src=('https:'==location.protocol?'//ssl':'//www')+'.google-analytics.com/ga.js';
	s.parentNode.insertBefore(g,s)}(document,'script'));
</script>
--%>
</body>
</html>

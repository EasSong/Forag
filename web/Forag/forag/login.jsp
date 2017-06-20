<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Forag | Login</title>
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<!-- Font Awesome -->
  <link rel="stylesheet" href="font-awesome-4.4.0/css/font-awesome.min.css" >
  <link rel="stylesheet" href="ionicons-2.0.1/css/ionicons.min.css">
  <%--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">--%>
<!-- Ionicons -->
<%--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">--%>
<link rel="stylesheet" href="../plugins/iCheck/square/blue.css">
<!-- Theme style -->
<link rel="stylesheet" href="../dist/css/AdminLTE.min.css">
<!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet" href="../dist/css/skins/skin-black-light.min.css">
<link rel="stylesheet" href="css/common.css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="js/html5shiv.js"></script>
  <script src="js/respond.min.js"></script>
  <![endif]-->
</head>
<!-- ADD THE CLASS layout-top-nav TO REMOVE THE SIDEBAR. -->
<body class="hold-transition skin-black-light layout-top-nav">
<div class="wrapper">
  <header class="main-header">
    <nav class="navbar navbar-fixed-top">
      <div class="container">
        <div class="navbar-header col-md-4"> <a href="index.html" class="navbar-brand" style="border:none;">Forag<b>ER</b></a>
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse" aria-expanded="false">
            <i class="fa fa-bars"></i>
          </button>
        </div>
        
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="navbar-collapse pull-left collapse col-md-3" id="navbar-collapse" aria-expanded="false" style="height: 1px;">
         <form class="navbar-form navbar-right" role="search">
                	<div class="input-group">
                        <input class="form-control" type="text" placeholder="Search">
                            <span class="input-group-btn">
                              <button type="button" class="btn btn-info btn-flat">Go!</button>
                            </span>
                      </div>
         </form>
        </div>
        <!-- /.navbar-collapse --> 
        <!-- Navbar Right Menu -->
        <div class="navbar-custom-menu col-md-5">
          <ul class="nav navbar-nav" style="float:right;">
            <!-- Messages: style can be found in dropdown.less-->

            <!-- Notifications Menu -->
            <li class="dropdown messages-menu">
              <a href="login.jsp" class="text-center" style="color: #3c8dbc">请登录</a>
            </li>
            <!-- Tasks Menu -->
            <li class="dropdown messages-menu">
              <a href="register.jsp" class="text-center" style="color: #3c8dbc">注册账号</a>
            </li>
            <li class="dropdown messages-menu">
              <a><br/></a>
            </li>
          </ul>
        </div>
        <!-- /.navbar-custom-menu --> 
      </div>
      <!-- /.container-fluid --> 
    </nav>
  </header>
  
  <!-- Full Width Column -->
    <div class="container-fluid" style="background-color:#FCC;">
      <div class="login-box">
          <div class="login-logo">
            <a href="../index2.html">Forag<b>ER</b></a>
          </div>
          <!-- /.login-logo -->
          <div class="login-box-body">
            <p class="login-box-msg">Sign in to start your session</p>
        
            <form action="/UserLogin" name="userLoginForm" method="post" onSubmit="return loginFormHandle();">
              <div class="form-group has-feedback">
                <input type="email" class="form-control" name="userEmail" placeholder="Email" required>
                <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
              </div>
              <div class="form-group has-feedback">
                <input type="password" class="form-control" name="userPassword" placeholder="Password" pattern="^.{6,16}$" required>
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
              </div>
              <div class="row">
                <div class="col-xs-8">
                  <div class="checkbox icheck">
                    <label>
                      <input type="checkbox"> Remember Me
                    </label>
                  </div>
                </div>
                <!-- /.col -->
                <div class="col-xs-4">
                  <button type="submit" class="btn btn-primary btn-block btn-flat">Sign In</button>
                </div>
                <!-- /.col -->
              </div>
            </form>
        
            <div class="social-auth-links text-center">
              <p>- OR -</p>
              <a href="#" class="btn btn-block btn-social btn-facebook btn-flat"><i class="fa fa-facebook"></i> Sign in using
                Facebook</a>
              <a href="#" class="btn btn-block btn-social btn-google btn-flat"><i class="fa fa-google-plus"></i> Sign in using
                Google+</a>
            </div>
            <!-- /.social-auth-links -->
        
            <a href="#">I forgot my password</a><br>
            <a href="register.jsp" class="text-center">Register a new membership</a>
        
          </div>
          <!-- /.login-box-body -->
        </div>
        <!-- /.login-box -->
            </div>
          <!-- /.content-wrapper -->
              
          <footer class="main-footer">
            <div class="container">
              <div class="pull-right hidden-xs"> <b>Version</b> 2.3.8 </div>
              <strong>Copyright &copy; 2014-2016 <a href="http://almsaeedstudio.com">Almsaeed Studio</a>.</strong> All rights
              reserved. </div>
            <!-- /.container --> 
          </footer>
        </div>
<!-- ./wrapper --> 

<!-- jQuery 2.2.3 --> 
<script src="../plugins/jQuery/jquery-2.2.3.min.js"></script> 
<!-- Bootstrap 3.3.6 --> 
<script src="bootstrap/js/bootstrap.min.js"></script> 
<script src="../plugins/iCheck/icheck.min.js"></script>
<script src="js/md5.js"></script>
<script>
  $(function () {
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
  });
  
  function loginFormHandle(){
  	var password = document.getElementsByName("userPassword").item(0);
	password.value = hex_md5(hex_md5(password.value));
	return true;
  };
</script>
</body>
</html>

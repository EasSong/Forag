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
<title>AdminLTE 2 | Top Navigation</title>
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
<!-- Theme style -->
<link rel="stylesheet" href="../dist/css/AdminLTE.min.css">
<!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet" href="../dist/css/skins/skin-black-light.min.css">
<link rel="stylesheet" href="css/common.css">
  <%--<script src="js/json.js"></script>--%>
  <%--<script src="js/jquery-3.0.0.min.js"></script>--%>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="js/html5shiv.js"></script>
  <script src="js/respond.min.js"></script>
  <![endif]-->
</head>
<script>
    var xmlHttp;
    //获得xmlHttp对象
    function createXMLHttp() {
        //对于大多数浏览器适用
        var xmlHttp;
        if (window.XMLHttpRequest) {
            xmlHttp = new XMLHttpRequest();
        }
        //考虑浏览器的兼容性
        if (window.ActiveXObject) {
            xmlHttp = new ActiveXOject("Microsoft.XMLHTTP");
            if (!xmlHttp) {
                xmlHttp = new ActiveXOject("Msxml2.XMLHTTP");
            }
        }
        return xmlHttp;
    }
    function callback() {
        //4代表完成
        if(xmlHttp.readyState == 4){
            //200代表服务器响应成功，404代表资源未找到，500服务器内部错误
            if(xmlHttp.status == 200){
                //交互成功获得响应的数据，是文本格式,并解析
                var xmlResult = xmlHttp.responseText;
                var temp = eval('('+xmlResult+')');
                if (temp.state == "success"){
                    location.href="/AdminLTE-2.3.11/forag/login.jsp";
                }
                else if(temp.state == "notLogin"){
                    location.href="/AdminLTE-2.3.11/forag/login.jsp";
                }else if (temp.state == "isLogin"){

                }
            }
        }
    }
    function loginOut() {
        //向服务器发送内容，用到XmlHttp对象
        xmlHttp = createXMLHttp();
        //给服务器发送数据，escape()不加中文会有问题
        var url = "/ShowInforEdit";
        //true表示js的脚本会在send()方法之后继续执行而不会等待来自服务器的响应
        xmlHttp.open("POST",url,true);
        //xmlHttp绑定回调方法，这个方法会在xmlHttp状态改变的时候调用,xmlHttp状态有0-4，
        //我们只关心4，4表示完成
        xmlHttp.onreadystatechange=callback;
        xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        xmlHttp.send("type=loginOut");
    }
    function checkLogin() {
        xmlHttp = createXMLHttp();
        var url = "/ShowInforEdit";
        xmlHttp.open("POST",url,true);
        xmlHttp.onreadystatechange=callback;
        //POST方法必须设置报文Header
        xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        xmlHttp.send("type=checkLogin");
    }
</script>
<!-- ADD THE CLASS layout-top-nav TO REMOVE THE SIDEBAR. -->
<body class="hold-transition skin-black-light layout-top-nav" onload="checkLogin()">
<%
	//防止用户通过URL访问此页面
	if (session.getAttribute("userShowInfor") == null){
		out.print("<script language=\"javascript\"type=\"text/javascript\">window.location.href=\"/AdminLTE-2.3.11/forag/login.jsp\";</script>");
		session.setAttribute("isLogin", false);
	}
	else{
		session.setAttribute("isLogin", true);
	}
 %>
<jsp:useBean id="userShowInfor" class="cuit.model.UserBean" scope="session"></jsp:useBean>
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
              <!-- Menu toggle button --> 
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"> <i class="fa fa-bell-o"></i> <span class="label label-warning">10</span> </a>
              <ul class="dropdown-menu">
                <li class="header">You have 10 requests</li>
                <li> 
                  <!-- Inner Menu: contains the notifications -->
                  <ul class="menu">
                    <li>
                    	<ul class="menu">
                    <li><!-- start message --> 
                      <a href="#">
                      <div class="pull-left"> 
                        <!-- User Image --> 
                        <img src="../dist/img/user2-160x160.jpg" class="img-circle" alt="User Image"> </div>
                      <!-- Message title and timestamp -->
                      <h4> Alexander Pierce <small><i class="fa fa-clock-o"></i> 5 mins</small> </h4>
                      <!-- The message -->
                      <p>Why not buy a new awesome theme?</p>
                      </a> </li>
                    <!-- end message -->
                  </ul>
                    </li>
                    <!-- end notification -->
                  </ul>
                </li>
                <li class="footer"><a href="userProfile.jsp">See all</a></li>
              </ul>
            </li>
            <!-- Tasks Menu -->
            <li class="dropdown messages-menu"> 
              <!-- Menu Toggle Button --> 
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"> <i class="glyphicon glyphicon-time"></i> <span class="label label-danger">9</span> </a>
              <ul class="dropdown-menu">
                <li class="header">You have 4 history</li>
                <li> 
                  <!-- inner menu: contains the messages -->
                  <ul class="menu">
                    <li><!-- start message --> 
                      <a href="#">
                      <div class="pull-left"> 
                        <!-- User Image --> 
                        <img src="../dist/img/user2-160x160.jpg" class="img-circle" alt="User Image"> </div>
                      <!-- Message title and timestamp -->
                      <h4> Alexander Pierce <small><i class="fa fa-clock-o"></i> 5 mins</small> </h4>
                      <!-- The message -->
                      <p>Why not buy a new awesome theme?</p>
                      </a> </li>
                    <!-- end message -->
                  </ul>
                  <!-- /.menu --> 
                </li>
                <li class="footer"><a href="userProfile.jsp">See All</a></li>
              </ul>
            </li>
            <!-- User Account Menu -->
            <li class="dropdown user user-menu"> 
              <!-- Menu Toggle Button --> 
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"> 
              <!-- The user image in the navbar--> 
              <img src="../dist/img/user2-160x160.jpg" class="user-image" alt="User Image"> 
              <!-- hidden-xs hides the username on small devices so only the image appears. --> 
              <span class="hidden-xs"><%=userShowInfor.getName()%></span> </a>
              <ul class="dropdown-menu">
                <!-- The user image in the menu -->
                <li class="user-header"> <img src="../dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
                  <p> <%=userShowInfor.getName()%> - Web Developer <small>Member since Nov. 2012</small> </p>
                </li>
                <!-- Menu Body -->
                <li class="user-body">
                  <div class="row">
                    <div class="col-xs-4 text-center">
                    	<div class="description-block">
                            <h5 class="description-header">3,200</h5>
                            <span class="description-text"><a href="#">Fllowers</a></span>
                         </div>
                    </div>
                    <div class="col-xs-4 text-center">
                    	<div class="description-block">
                            <h5 class="description-header">3,200</h5>
                            <span class="description-text"><a href="#">Interest</a></span>
                         </div>
                    </div>
                    <div class="col-xs-4 text-center">
                    	<div class="description-block">
                            <h5 class="description-header">3,200</h5>
                            <span class="description-text"><a href="#">Friends</a></span>
                         </div>
                    </div>
                    
                  </div>
                  <!-- /.row --> 
                </li>
                <!-- Menu Footer-->
                <li class="user-footer">
                  <div class="pull-left"> <a href="userProfile.jsp" class="btn btn-default btn-flat">Profile</a> </div>
                  <div class="pull-right"> <a href="login.jsp" onclick="loginOut()" class="btn btn-default btn-flat">Sign out</a> </div>
                </li>
              </ul>
            </li>
          </ul>
        </div>
        <!-- /.navbar-custom-menu --> 
      </div>
      <!-- /.container-fluid --> 
    </nav>
  </header>
  <div class="container-fluid" style="background-color:#FCC;">
    <div class="row" style="margin-top:10px;">
      <div class="col-md-3" id="user_infor">
        <div class="box box-widget widget-user">
            <!-- Add the bg color to the header using any of the bg-* classes -->
            <div class="widget-user-header bg-black" style="background: url('../dist/img/photo1.png') center center;">
              <h3 class="widget-user-username"><%=userShowInfor.getName()%></h3>
              <h5 class="widget-user-desc">Web Designer</h5>
            </div>
            <div class="widget-user-image">
              <img class="img-circle" src="../dist/img/user3-128x128.jpg" alt="User Avatar">
            </div>
            <div class="box-footer">
              <div class="row">
                <div class="col-sm-4 border-right">
                  <div class="description-block">
                    <h5 class="description-header">3,200</h5>
                    <span class="description-text">SALES</span>
                  </div>
                  <!-- /.description-block -->
                </div>
                <!-- /.col -->
                <div class="col-sm-4 border-right">
                  <div class="description-block">
                    <h5 class="description-header">13,000</h5>
                    <span class="description-text">FOLLOWERS</span>
                  </div>
                  <!-- /.description-block -->
                </div>
                <!-- /.col -->
                <div class="col-sm-4">
                  <div class="description-block">
                    <h5 class="description-header">35</h5>
                    <span class="description-text">PRODUCTS</span>
                  </div>
                  <!-- /.description-block -->
                </div>
                <!-- /.col -->
              </div>
              <!-- /.row -->
            </div>
          </div>
        <div class="box box-primary">
          <div class="box-header with-border">
            <h3 class="box-title">About Me</h3>
          </div>
          <!-- /.box-header -->
          <div class="box-body"> <strong><i class="fa fa-book margin-r-5"></i> Education</strong>
            <p class="text-muted"> <%=userShowInfor.getEducation()%> </p>
            <hr>
            <strong><i class="fa fa-map-marker margin-r-5"></i> Location</strong>
            <p class="text-muted"><%=userShowInfor.getLocation()%></p>
            <hr>
            <strong><i class="fa fa-pencil margin-r-5"></i> Skills</strong>
            <p>
              <span class="label label-danger">UI Design</span>
              <span class="label label-success">Coding</span>
              <span class="label label-info">Javascript</span>
              <span class="label label-warning">PHP</span>
              <span class="label label-primary">Node.js</span>
            </p>
            <hr>
            <strong><i class="fa fa-file-text-o margin-r-5"></i> Notes</strong>
            <p><%=userShowInfor.getIntro()%></p>
          </div>
          <!-- /.box-body --> 
        </div>
      </div>
      <div class="col-md-9">
        <div class="nav-tabs-custom">
          <ul class="nav nav-tabs">
            <li class="active"><a href="#brace" data-toggle="tab" aria-expanded="true">Brace</a></li>
            <li><a href="#interest" data-toggle="tab" aria-expanded="false">Interest</a></li>
            <li><a href="#settings" data-toggle="tab" aria-expanded="false">Settings</a></li>
            <li><a href="#password" data-toggle="tab" aria-expanded="false">Password</a></li>
          </ul>
          <div class="tab-content">
            <div class="tab-pane active" id="brace"> 
              <div class="container-fluid" style="padding-bottom:15px;">
              	<div class="row">
                	<div class="col-md-12">
                    	<div class="box box-solid">
                        	<div class="box-header with-border">
                            	<h3 class="box-title">Browsing History</h3>
                            </div>
                            <div class="box-body">
                            	<table class="table table-hover">
                                        <tbody>
                                        <tr>
                                          <td>11-7-2014</td>
                                          <td>John Doe</td>
                                          <td>Bacon ipsum dolor sit amet salami venison chicken flank fatback doner.</td>
                                        </tr>
                                        <tr>
                                           <td>11-7-2014</td>
                                          <td>John Doe</td>
                                          <td>Bacon ipsum dolor sit amet salami venison chicken flank fatback doner.</td>
                                        </tr>
                                        <tr>
                                          <td>11-7-2014</td>
                                          <td>John Doe</td>
                                          <td>Bacon ipsum dolor sit amet salami venison chicken flank fatback doner.</td>
                                        </tr>
                                        <tr>
                                           <td>11-7-2014</td>
                                          <td>John Doe</td>
                                          <td>Bacon ipsum dolor sit amet salami venison chicken flank fatback doner.</td>
                                        </tr>
                                      </tbody></table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                	<div class="col-md-12">
                    	<div class="box box-solid">
                        	<div class="box-header with-border">
                            	<h3 class="box-title">Your Comment</h3>
                            </div>
                            <div class="box-body">
                            	<table class="table table-hover">
                                        <tbody>
                                        <tr>
                                          <td>11-7-2014</td>
                                          <td>your conmment conmment conmment conmment </td>
                                          <td>Bacon ipsum dolor sit amet salami venison chicken flank fatback doner.</td>
                                        </tr>
                                       <tr>
                                          <td>11-7-2014</td>
                                          <td>your conmment conmment conmment conmment </td>
                                          <td>Bacon ipsum dolor sit amet salami venison chicken flank fatback doner.</td>
                                        </tr>
                                        <tr>
                                          <td>11-7-2014</td>
                                          <td>your conmment conmment conmment conmment </td>
                                          <td>Bacon ipsum dolor sit amet salami venison chicken flank fatback doner.</td>
                                        </tr>
                                        <tr>
                                          <td>11-7-2014</td>
                                          <td>your conmment conmment conmment conmment </td>
                                          <td>Bacon ipsum dolor sit amet salami venison chicken flank fatback doner.</td>
                                        </tr>
                                      </tbody></table>
                            </div>
                        </div>
                    </div>
                </div>
              </div>
            </div>
            <div class="tab-pane" id="interest">
              <div class="container-fluid">
                <div class="row">
                  <div class="col-md-6">
                    <div class="box box-danger">
                      <div class="box-header with-border">
                        <h3 class="box-title"><strong><i class="fa fa-pencil margin-r-5"></i> Your Interest</strong></h3>
                      </div>
                      <!-- /.box-header -->
                      <div class="box-body" style="min-height:150px;">
                        <p> <span class="label label-danger margin-r-5">UI Design</span> <span class="label label-success margin-r-5">Coding</span> <span class="label label-info margin-r-5">Javascript</span> <span class="label label-warning margin-r-5">PHP</span> <span class="label label-primary margin-r-5">Node.js</span> <span class="label label-info margin-r-5">Javascript</span> <span class="label label-warning margin-r-5">PHP</span> </p>
                      </div>
                      <!-- /.box-body --> 
                    </div>
                    <div class="box box-warning">
                        <div class="box-header">
                          <h3 class="box-title with-border"><strong><i class="fa fa-pencil margin-r-5"></i> Hot Interest</strong></h3>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body" style="min-height:150px;">
                          <p>
                            <span class="label label-danger margin-r-5">UI Design</span>
                            <span class="label label-success margin-r-5">Coding</span>
                            <span class="label label-info margin-r-5">Javascript</span>
                            <span class="label label-warning margin-r-5">PHP</span>
                            <span class="label label-primary margin-r-5">Node.js</span>
                          </p>
                        </div>
                        <!-- /.box-body -->
                      </div>
                  </div>
                  <div class="col-md-6">
                  	<form class="form-horizontal">
                    <div class="form-group">
                      <div class="col-sm-8">
                        <div class="input-group"> <span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>
                          <input class="form-control" placeholder="Search Interest" type="text">
                        </div>
                      </div>
                      <div class="col-sm-4">
                        <button type="submit" class="btn btn-block btn-danger">Search</button>
                      </div>
                    </div>
                  </form>
                    <div class="box box-info">
                        <div class="box-header with-border">
                          <h3 class="box-title">Search Result</h3>
                        </div>
                        <div class="box-body" style="min-height:150px;">
                          <p> <span class="label label-danger margin-r-5">UI Design</span> <span class="label label-success margin-r-5">Coding</span> <span class="label label-info margin-r-5">Javascript</span> <span class="label label-warning margin-r-5">PHP</span> <span class="label label-primary margin-r-5">Node.js</span> </p>
                        </div>
                        <div class="box-footer"> <span class="text-muted">In the search search to 5 </span> </div>
                      </div>
                  </div>
                </div>
                
              </div>
            </div>
            <div class="tab-pane" id="settings">
              <form class="form-horizontal" name="showInforForm" method="post" action="/ShowInforEdit">
                <input type="hidden" name="type" value="edit"/>
                <div class="form-group">
                  <label for="inputName" class="col-sm-2 control-label">Name</label>
                  <div class="col-sm-10">
                    <input class="form-control" id="inputName" name="name" placeholder="Name" required="required" pattern="^.{1,50}$" type="text" value='<jsp:getProperty property="name" name="userShowInfor"/>'>
                  </div>
                </div>
                <div class="form-group">
                  <label for="inputEmail" class="col-sm-2 control-label">Email</label>
                  <div class="col-sm-10">
                    <input class="form-control" id="inputEmail" name="email" placeholder="Email" required="required" pattern="^.{1,50}$" type="email" value='<jsp:getProperty property="email" name="userShowInfor"/>'>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Location</label>
                  <div class="col-sm-10">
                    <input class="form-control" id="inputLocation" name="location" placeholder="Location" required="required" pattern="^.{1,50}$" type="text" value='<jsp:getProperty property="location" name="userShowInfor"/>'>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Profession</label>
                  <div class="col-sm-10">
                    <input class="form-control" id="inputProfession" name="profession" placeholder="Profession" required="required" pattern="^.{1,50}$" type="text" value='<jsp:getProperty property="profession" name="userShowInfor"/>'>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Education</label>
                  <div class="col-sm-10">
                    <input class="form-control" id="inputEducation" name="education" placeholder="Education" required="required" pattern="^.{1,50}$" type="text" value='<jsp:getProperty property="education" name="userShowInfor"/>'>
                  </div>
                </div>
                <div class="form-group">
                  <label for="inputSkills" class="col-sm-2 control-label">Skills</label>
                  <div class="col-sm-10">
                    <input class="form-control" id="inputSkills" name="skills" placeholder="Skills" required="required" pattern="^.{1,50}$" type="text" value='<jsp:getProperty property="skills" name="userShowInfor"/>'>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Introducation</label>
                  <div class="col-sm-10">
                    <textarea class="form-control" id="inputIntroducation" name="intro" required="required" maxlength="150" placeholder="Introducation"><jsp:getProperty property="intro" name="userShowInfor"/></textarea>
                  </div>
                 </div>
                <div class="form-group">
                  <div class="col-sm-offset-2 col-sm-10">
                    <div class="checkbox">
                      <label>
                        <input type="checkbox">
                        I agree to the <a href="#">terms and conditions</a> </label>
                    </div>
                  </div>
                </div>
                <div class="form-group">
                  <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-danger">Submit</button>
                  </div>
                </div>
              </form>
            </div>
            <div class="tab-pane" id="password">
              <form class="form-horizontal" name="editPasswordForm" method="post" action="/PasswordEdit" onSubmit="return formCheck();">
                <div class="form-group">
                  <label for="inputOldPassword" class="col-sm-2 control-label">OldPassword</label>
                  <div class="col-sm-10">
                    <input class="form-control" id="inputOldPassword" name="oldPassword" placeholder="OldPassword" required="required" pattern="^.{6,16}$" type="password">
                  </div>
                </div>
                <div class="form-group">
                  <label for="inputNewPassword" class="col-sm-2 control-label">NewPassword</label>
                  <div class="col-sm-10">
                    <input class="form-control" id="inputNewPassword" name="newPassword" placeholder="NewPassword" required="required" pattern="^.{6,16}$" type="password">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">AgainPassword</label>
                  <div class="col-sm-10">
                    <input class="form-control" id="inputAgainPassword" name="againPassword" placeholder="AgainPassword" data-toggle="popover" data-placement="bottom" data-content="请保证前后密码一致" data-trigger="manual" onClick="hideTip()" required="required" pattern="^.{6,16}$" type="password">
                  </div>
                </div>
                <div class="form-group">
                  <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-danger">Submit</button>
                  </div>
                </div>
              </form>
            </div>
            <!-- /.tab-pane --> 
          </div>
          <!-- /.tab-content --> 
        </div>
        <!-- /.nav-tabs-custom --> 
      </div>
    </div>
  </div>
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
<script src="js/md5.js"></script>
<script>
  function hideTip(){
	  var passwordAgain = document.getElementsByName("againPassword").item(0);
	  $(passwordAgain).popover('hide');
  }
  
  function formCheck(){
  	  var oldPassword = document.getElementsByName("oldPassword").item(0);
	  var newPassword = document.getElementsByName("newPassword").item(0);
	  var againPassword = document.getElementsByName("againPassword").item(0);
  	  if (newPassword.value != againPassword.value)
	  {
		  $(passwordAgain).popover('show');
		  return false;
	  }
	  else
	  {
	  	  oldPassword.value = hex_md5(hex_md5(oldPassword.value))
		  newPassword.value = hex_md5(hex_md5(newPassword.value))
		  againPassword.value = newPassword.value
		  return true;
	  }
  }
</script>
<%
	//防止用户通过URL访问此页面
	if ((Boolean)session.getAttribute("isLogin") == false){
		session.setAttribute("userShowInfor", null);
	}
 %>
</body>
</html>

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
<title>Forag | Profile</title>
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
  <!-- my js-->
  <script src="js/forag_util.js"></script>
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
                    location.href="/Forag/forag/index.html";
                }
                else if(temp.state == "notLogin"){
                    location.href="/Forag/forag/index.html";
                }else if (temp.state == "isLogin"){
                    var topLinkUl = document.getElementById("top-login-register-link");
                    var topUserInforUl = document.getElementById("top-user-information");
                    topLinkUl.style.display = "none";
                    topUserInforUl.style.display = "inline";
                    document.getElementById("profile-user-name").innerHTML = temp.userInfor.uName;
                    document.getElementById("profile-user-name-hidden").innerHTML = temp.userInfor.uName;
                    setUserDetailInfo(temp.userInfor);
                    getHotTags();
                    getUserTimeLine(0,5);
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
    var labelColor = ["label-success","label-info","label-primary","label-warning","label-danger"];
    function setHotTag(data) {
        var tempHtml = "";
        var hotTagList = document.getElementById("hot-tag-list");
        if (data.length <= 0){
            hotTagList.innerHTML = "没有这个标签";
            return;
        }
        var count = 0;
        for (var i = 0; i < data.length; i++) {
            tempHtml += "<button type='button' class='btn " + labelColor[count++ % labelColor.length] + "' style='padding: 0 2px;margin-right: 5px' onclick='addTagToNowTagList(this)'>"
                + data[i] + "</button>";
        }
        hotTagList.innerHTML = tempHtml;
    }
    function getHotTags() {
        $.ajax({
            type:"POST",
            url:"/ShowInforEdit",
            dataType:"json",
            data:"type=hotTag",
            success:function (data) {
                setHotTag(data);
            }
        });
    }
    var arrTags = new Array();
    var userName = "";
    function setUserDetailInfo(userInfo) {
        var userSkills = (userInfo.uSkill).split(",");
        var userInterest = userInfo.uInterest;
        userName = userInfo.uName;
        var skillList = document.getElementById("user-skill-list");
        var interestList = document.getElementById("interest-tag-list");
        var tempHtml = "";
        var count = 0;
        for (var i = 0; i < userSkills.length; i++){
            if (userSkills[i] == ""){
                continue;
            }
            tempHtml += "<span class='label "+labelColor[count++%labelColor.length]+"' style='margin-right: 5px'>"+userSkills[i]+"</span>";
        }
        skillList.innerHTML = tempHtml;
        tempHtml = "";
        for (var i in userInterest){
            tempHtml += "<button type='button' class='btn "+labelColor[count++%labelColor.length]+"' style='padding: 0 2px;margin-right: 5px' onclick='deleteTag(this)'>"
                +i+"</button>";
            arrTags.push(i);
        }
        interestList.innerHTML = tempHtml;
    }
    function deleteTag(obj) {
        if (arrTags.length <= 1){
            alert("至少保留1个标签");
            return;
        }
        var parentElement = obj.parentElement;
        parentElement.removeChild(obj);
        arrTags = removeByValue(obj.innerHTML,arrTags);
    }
    function addTagToNowTagList(obj) {
        if (contains(obj.innerHTML,arrTags)){
            alert("不能重复添加");
            return;
        }
        var parentPNode = document.getElementById("interest-tag-list");
        var tempHtml = "<button type='button' class='"+obj.className+"' style='padding: 0 2px;margin-right: 5px' onclick='deleteTag(this)'>"
            +obj.innerHTML+"</button>";
        parentPNode.innerHTML += tempHtml;
        arrTags.push(obj.innerHTML);
        submitUserInterest();
    }
    function searchTagListByLikeName() {
        var tagName = document.getElementById("input-tag-name").value;
        $.ajax({
            type:"POST",
            url:"/TagEdit",
            dataType:"json",
            data:"type=search&tagName="+tagName,
            success:function (tagList) {
                setHotTag(tagList);
            }
        });
    }
    function submitUserInterest(){
        $.ajax({
            type:"POST",
            url:"/ShowInforEdit",
            dataType:"json",
            data:"type=interest&interest="+arrTags.toString(),
            success:function (data) {
                alert("提交成功");
            }
        });
    }
    function getUserTimeLine(offset,len) {
        $.ajax({
            type:"POST",
            url:"/UserTimeLine",
            dataType:"json",
            data:"offset="+offset+"&len="+len,
            success:function (logInfo) {
                setTimeLine(logInfo,offset);
            }
        });
    }
    function setTimeLine(logInfo,offset) {
        var timeLineTemp = "";
        var timeLineUl = document.getElementById("time-line-ul-id");
        if (offset == 0) {
            timeLineUl.innerHTML = "";
        }
        for (var i = 0; i < logInfo.length; i++) {
            var type = logInfo[i].type;
            if (type == "date") {
                timeLineTemp += setTimeLineDate(logInfo[i]);
            } else if (type == "browse") {
                timeLineTemp += setTimeLineBrowse(logInfo[i]);
            } else if (type == "comment") {
                timeLineTemp += setTimeLineComment(logInfo[i]);
            } else if (type == "like") {
                timeLineTemp += setTimeLineLike(logInfo[i]);
            } else if (type == "dislike") {
                timeLineTemp += setTimeLineDislike(logInfo[i]);
            } else if (type == "collect") {
                timeLineTemp += setTimeLineCollect(logInfo[i]);
            } else if (type == "transmit") {
                timeLineTemp += setTimeLineShare(logInfo[i]);
            }
        }
        timeLineUl.innerHTML += timeLineTemp;
//        timeLineUl.parentNode.parentNode.innerHTML += "<div><a href='#' class='btn btn-block btn-default'><i class='fa fa-angle-double-down' style='font-size: 18px'></i></a></div>";
        var readMoreBtn = document.getElementById("time-line-read-more");
        if (logInfo.length <= 0) {
            readMoreBtn.innerHTML = "<a class='btn btn-block btn-default disabled'>No more history</a>";
        } else {
            readMoreBtn.innerHTML = "<a href='#' class='btn btn-block btn-default' onclick='getUserTimeLine(" + (offset + 5) + ",5)'><i class='fa fa-angle-double-down' style='font-size: 18px'></i></a>";

        }
    }
    var bgColor = ["bg-red","bg-yellow","bg-aqua","bg-blue","bg-light-blue","bg-green","bg-navy",
        "bg-teal","bg-olive","bg-lime","bg-orange","bg-fuchsia","bg-purple","bg-maroon","bg-black"];
    var dateLiCount = 0;
    function setTimeLineDate(date) {
        return "<li class='time-label'>"
            +"<span class='"+bgColor[dateLiCount++%bgColor.length]+"'>"
            +date.date
        +"</span>"
        +"</li>";
    }
    function setTimeLineBrowse(browse) {
        return "<li>"
            +"<i class='fa fa-bookmark bg-blue'></i>"
        +"<div class='timeline-item' style='background-color: rgb(236, 240, 245)'>"
        +"<span class='time'><i class='fa fa-clock-o'></i>"+browse.time+"</span>"
        +"<h3 class='timeline-header'><a href='#'>"+((browse.objName==null|| browse.objName==userName)?'You':browse.objName)+"</a> sent a message</h3>"
        +"<div class='timeline-body'>The message's title is: "
            +browse.title
        +"</div>"
        +"<div class='timeline-footer'>"
            +"<a href='article.html?mId="+browse.mId+"' class='btn bg-blue btn-flat btn-xs'>View message</a>"
        +"</div>"
        +"</div>"
        +"</li>";
    }
    function setTimeLineLike(like) {
        return "<li>"
        +"<i class='fa fa-thumbs-o-up bg-green'></i>"
        +"<div class='timeline-item' style='background-color: rgb(236, 240, 245)'>"
        +"<span class='time'><i class='fa fa-clock-o'></i>"+like.time+"</span>"
        +"<h3 class='timeline-header'><a href='#'>"+((like.objName==null|| like.objName==userName)?'You':like.objName)+"</a> think a message is good</h3>"
        +"<div class='timeline-body'>The message's title is: "
        +like.title
        +"</div>"
        +"<div class='timeline-footer'>"
        +"<a href='article.html?mId="+like.mId+"' class='btn bg-green btn-flat btn-xs'>View message</a>"
        +"</div>"
        +"</div>"
        +"</li>";
    }
    function setTimeLineComment(comment) {
        return "<li>"
        +"<i class='fa fa-comments bg-yellow'></i>"
        +"<div class='timeline-item' style='background-color: rgb(236, 240, 245)'>"
        +"<span class='time'><i class='fa fa-clock-o'></i>"+comment.time+"</span>"
        +"<h3 class='timeline-header'><a href='#'>You</a> comment "+((comment.objName==null)?'a message':((comment.objName==userName)?'yourself comment':comment.objName+'\'s comment'))+"</h3>"
        +"<div class='timeline-body'>"
        +comment.context
        +"</div>"
        +"<div class='timeline-footer'>"
        +"<a href='article.html?mId="+comment.mId+"' class='btn btn-warning btn-flat btn-xs'>View comment</a>"
        +"</div>"
        +"</div>"
        +"</li>";
    }
    function setTimeLineDislike(dislike) {
        return "<li>"
            +"<i class='fa fa-thumbs-down bg-red'></i>"
            +"<div class='timeline-item' style='background-color: rgb(236, 240, 245)'>"
            +"<span class='time'><i class='fa fa-clock-o'></i>"+dislike.time+"</span>"
            +"<h3 class='timeline-header'><a href='#'>"+((dislike.objName==null|| dislike.objName==userName)?'You':dislike.objName)+"</a> maybe not like this message</h3>"
            +"<div class='timeline-body'>The message's title is: "
            +dislike.title
            +"</div>"
            +"<div class='timeline-footer'>"
            +"<a href='article.html?mId="+dislike.mId+"' class='btn bg-red btn-flat btn-xs'>View message</a>"
            +"</div>"
            +"</div>"
            +"</li>";
    }
    function setTimeLineCollect(collect) {
        return "<li>"
            +"<i class='fa fa-folder-open bg-teal' style='padding-top: 2px;padding-left: 3px'></i>"
            +"<div class='timeline-item' style='background-color: rgb(236, 240, 245)'>"
            +"<span class='time'><i class='fa fa-clock-o'></i>"+collect.time+"</span>"
            +"<h3 class='timeline-header'><a href='#'>"+((collect.objName==null|| collect.objName==userName)?'You':collect.objName)+"</a> collect a message</h3>"
            +"<div class='timeline-body'>The message's title is: "
            +collect.title
            +"</div>"
            +"<div class='timeline-footer'>"
            +"<a href='article.html?mId="+collect.mId+"' class='btn bg-teal btn-flat btn-xs'>View message</a>"
            +"</div>"
            +"</div>"
            +"</li>";
    }
    function setTimeLineShare(share) {
        return "<li>"
            +"<i class='fa fa-share-alt bg-aqua'></i>"
            +"<div class='timeline-item' style='background-color: rgb(236, 240, 245)'>"
            +"<span class='time'><i class='fa fa-clock-o'></i>"+share.time+"</span>"
            +"<h3 class='timeline-header'><a href='#'>"+((share.objName==null|| share.objName==userName)?'You':share.objName)+"</a> think a message is good</h3>"
            +"<div class='timeline-body'>The message's title is: "
            +share.title
            +"</div>"
            +"<div class='timeline-footer'>"
            +"<a href='article.html?mId="+share.mId+"' class='btn bg-aqua btn-flat btn-xs'>View message</a>"
            +"</div>"
            +"</div>"
            +"</li>";
    }
</script>
<!-- ADD THE CLASS layout-top-nav TO REMOVE THE SIDEBAR. -->
<body class="hold-transition skin-black-light layout-top-nav" onload="checkLogin()"  onclick="setTipListShow()">
<%
	//防止用户通过URL访问此页面
	if (session.getAttribute("userShowInfor") == null){
		out.print("<script language=\"javascript\"type=\"text/javascript\">window.location.href=\"/Forag/forag/login.jsp\";</script>");
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
      <a href="index.html" class="logo hidden-xs" style="border:none;color: #000000;margin-left: 10%;width: 100px">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini"><b>E</b>va</span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg">Forag<b>ER</b></span>
      </a>
      <div class="with-border">
        <a href="#" class="sidebar-toggle visible-xs" data-toggle="collapse" data-target="#navbar-collapse" aria-expanded="false">
          <span class="sr-only">Toggle navigation</span>
        </a>
      </div>
      <!-- /.navbar-collapse -->
      <!-- Navbar Right Menu -->
      <div class="navbar-custom-menu col-md-5 navbar-collapse collapse pull-right" id="navbar-collapse" style="margin-right: 10%">
        <ul class="nav navbar-nav" id="top-login-register-link" style="float:right;">
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
        <ul class="nav navbar-nav" id="top-user-information" style="float:right; display: none">
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
              <span class="hidden-xs" id="profile-user-name">UserName</span> </a>
            <ul class="dropdown-menu">
              <!-- The user image in the menu -->
              <li class="user-header"> <img src="../dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
                <p> <span id="profile-user-name-hidden">UserName</span>-<span id="profile-user-profession">Web Developer</span> <small>Member since Nov. 2012</small> </p>
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
      <!-- Collect the nav links, forms, and other content for toggling -->
      <div class="no-padding pull-right col-md-2" aria-expanded="false" style="height: 1px;margin-right:20px">
        <div class="input-group" style="margin-top: 10px">
          <input class="form-control" type="text" placeholder="Search" id="top-input-search" oninput="setSearchTipList(this)" onfocus="setSearchTipList(this)">
          <span class="input-group-btn">
               <button type="button" class="btn btn-info btn-flat" onclick="submitSearch('top-input-search')"><i class="fa fa-search"></i></button>
             </span>
        </div>
        <div style="width: 100%" >
          <table id="search-tip-list" class="table table-hover" style="position: absolute;background-color: white;display: none" >
            <tr><td><a href="#">12312412512515</a></td></tr>
            <tr><td><a href="#">12312412512515</a></td></tr>
            <tr><td><a href="#">12312412512515</a></td></tr>
          </table>
        </div>
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
              <h3 class="widget-user-username"><%=userShowInfor.getUtName()%></h3>
              <h5 class="widget-user-desc">Web Designer</h5>
            </div>
            <div class="widget-user-image">
              <img class="img-circle" src="images/userDefaultPic.jpg" alt="User Avatar">
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
            <p class="text-muted"> <%=userShowInfor.getUtEdu()%> </p>
            <hr>
            <strong><i class="fa fa-map-marker margin-r-5"></i> Location</strong>
            <p class="text-muted"><%=userShowInfor.getUtAddr()%></p>
            <hr>
            <strong><i class="fa fa-pencil margin-r-5"></i> Skills</strong>
            <p id="user-skill-list">
              <span class="label label-danger">UI Design</span>
              <span class="label label-success">Coding</span>
              <span class="label label-info">Javascript</span>
              <span class="label label-warning">PHP</span>
              <span class="label label-primary">Node.js</span>
            </p>
            <hr>
            <strong><i class="fa fa-file-text-o margin-r-5"></i> Notes</strong>
            <p><%=userShowInfor.getUtIntro()%></p>
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
              <section class="content">
                <!-- row -->
                <div class="row">
                  <div class="col-md-12">
                    <!-- The time line -->
                    <ul class="timeline" id="time-line-ul-id">
                      <!-- timeline time label -->
                      <li class="time-label">
                        <span class="bg-red">
                          10 Feb. 2014
                        </span>
                      </li>
                      <!-- /.timeline-label -->
                      <!-- timeline item -->
                      <li>
                        <i class="fa fa-bookmark bg-blue"></i>

                        <div class="timeline-item" style="background-color: rgb(236, 240, 245)">
                          <span class="time"><i class="fa fa-clock-o"></i> 12:05</span>

                          <h3 class="timeline-header"><a href="#">You</a> sent you a message</h3>

                          <div class="timeline-body">
                            Etsy doostang zoodles disqus groupon greplin oooj voxy zoodles,
                            weebly ning heekya handango imeem plugg dopplr jibjab, movity
                            jajah plickers sifteo edmodo ifttt zimbra. Babblely odeo kaboodle
                            quora plaxo ideeli hulu weebly balihoo...
                          </div>
                          <div class="timeline-footer">
                            <a class="btn btn-primary btn-flat btn-xs">View message</a>
                          </div>
                        </div>
                      </li>
                      <!-- END timeline item -->
                      <!-- timeline item -->
                      <li>
                        <i class="fa fa-thumbs-o-up bg-aqua"></i>

                        <div class="timeline-item" style="background-color: rgb(236, 240, 245)">
                          <span class="time"><i class="fa fa-clock-o"></i> 5 mins ago</span>

                          <h3 class="timeline-header no-border"><a href="#">You</a> think a message is very good</h3>
                          <div class="timeline-body">
                            This message`s title is ............................
                          </div>
                          <div class="timeline-footer">
                            <a class="btn bg-aqua btn-flat btn-xs">View message</a>
                          </div>
                        </div>
                      </li>
                      <!-- END timeline item -->
                      <!-- timeline item -->
                      <li>
                        <i class="fa fa-comments bg-yellow"></i>

                        <div class="timeline-item" style="background-color: rgb(236, 240, 245)">
                          <span class="time"><i class="fa fa-clock-o"></i> 27 mins ago</span>

                          <h3 class="timeline-header"><a href="#">Jay White</a> commented on your comment</h3>

                          <div class="timeline-body">
                            Take me to your leader!
                            Switzerland is small and neutral!
                            We are more like Germany, ambitious and misunderstood!
                          </div>
                          <div class="timeline-footer">
                            <a class="btn btn-warning btn-flat btn-xs">View comment</a>
                          </div>
                        </div>
                      </li>
                      <!-- END timeline item -->
                      <!-- timeline time label -->
                      <li class="time-label">
                        <span class="bg-green">
                          3 Jan. 2014
                        </span>
                      </li>
                      <!-- /.timeline-label -->
                      <li>
                        <i class="fa fa-bookmark bg-blue"></i>

                        <div class="timeline-item" style="background-color: rgb(236, 240, 245)">
                          <span class="time"><i class="fa fa-clock-o"></i> 12:05</span>

                          <h3 class="timeline-header"><a href="#">You</a> sent you a message</h3>

                          <div class="timeline-body">
                            Etsy doostang zoodles disqus groupon greplin oooj voxy zoodles,
                            weebly ning heekya handango imeem plugg dopplr jibjab, movity
                            jajah plickers sifteo edmodo ifttt zimbra. Babblely odeo kaboodle
                            quora plaxo ideeli hulu weebly balihoo...
                          </div>
                          <div class="timeline-footer">
                            <a class="btn btn-primary btn-flat btn-xs">View message</a>
                          </div>
                        </div>
                      </li>
                      <!-- END timeline item -->
                      <!-- timeline item -->
                      <li>
                        <i class="fa fa-thumbs-o-up bg-aqua"></i>

                        <div class="timeline-item" style="background-color: rgb(236, 240, 245)">
                          <span class="time"><i class="fa fa-clock-o"></i> 5 mins ago</span>

                          <h3 class="timeline-header no-border"><a href="#">You</a> think a message is very good</h3>
                          <div class="timeline-body">
                            This message`s title is ............................
                          </div>
                          <div class="timeline-footer">
                            <a class="btn bg-aqua btn-flat btn-xs">View message</a>
                          </div>
                        </div>
                      </li>
                      <!-- END timeline item -->
                      <!-- timeline item -->
                      <li>
                        <i class="fa fa-comments bg-yellow"></i>

                        <div class="timeline-item" style="background-color: rgb(236, 240, 245)">
                          <span class="time"><i class="fa fa-clock-o"></i> 27 mins ago</span>

                          <h3 class="timeline-header"><a href="#">Jay White</a> commented on your comment</h3>

                          <div class="timeline-body">
                            Take me to your leader!
                            Switzerland is small and neutral!
                            We are more like Germany, ambitious and misunderstood!
                          </div>
                          <div class="timeline-footer">
                            <a class="btn btn-warning btn-flat btn-xs">View comment</a>
                          </div>
                        </div>
                      </li>
                    </ul>
                  </div>
                  <!-- /.col -->
                </div>
                <!-- /.row -->
              </section>
              <div id="time-line-read-more"><a href="#" class="btn btn-block btn-default" onclick="getUserTimeLine(0,5)"><i class='fa fa-angle-double-down' style='font-size: 18px'></i></a></div>
            </div>
            <div class="tab-pane" id="interest">
              <div class="container-fluid">
                <div class="row">
                  <div class="col-md-12">
                    <div class="box box-danger">
                      <div class="box-header with-border">
                        <h3 class="box-title"><strong><i class="fa fa-pencil margin-r-5"></i> Your Interest</strong></h3>
                      </div>
                      <!-- /.box-header -->
                      <div class="box-body" style="min-height:150px;">
                        <p id="interest-tag-list">
                          <span class="label label-danger margin-r-5">UI Design</span>
                          <span class="label label-success margin-r-5">Coding</span>
                          <span class="label label-info margin-r-5">Javascript</span>
                          <span class="label label-warning margin-r-5">PHP</span>
                          <span class="label label-primary margin-r-5">Node.js</span>
                          <span class="label label-info margin-r-5">Javascript</span>
                          <span class="label label-warning margin-r-5">PHP</span>
                        </p>
                      </div>
                      <!-- /.box-body --> 
                    </div>
                    <div class="box box-warning">
                        <div class="box-header with-border">
                          <h3 class="box-title"><strong><i class="fa fa-pencil margin-r-5"></i> Hot Interest</strong></h3>
                          <div class="input-group pull-right" style="width: 300px">
                            <input name="message" id="input-tag-name" placeholder="Tag name ..." class="form-control" type="text">
                            <span class="input-group-btn">
                              <button type="submit" class="btn btn-primary btn-flat" onclick="searchTagListByLikeName()"><i class="fa fa-fw fa-search"></i></button>
                            </span>
                          </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body" style="min-height:150px;">
                          <p id="hot-tag-list">
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
                  <%--<div class="col-md-6">
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
                  </div>--%>
                </div>
                
              </div>
            </div>
            <div class="tab-pane" id="settings">
              <form class="form-horizontal" name="showInforForm" method="post" action="/ShowInforEdit">
                <input type="hidden" name="type" value="edit"/>
                <div class="form-group">
                  <label for="inputName" class="col-sm-2 control-label">Name</label>
                  <div class="col-sm-10">
                    <input class="form-control" id="inputName" name="name" placeholder="Name" required="required" pattern="^.{1,50}$" type="text" value='<jsp:getProperty property="utName" name="userShowInfor"/>'>
                  </div>
                </div>
                <div class="form-group">
                  <label for="inputEmail" class="col-sm-2 control-label">Email</label>
                  <div class="col-sm-10">
                    <input class="form-control" id="inputEmail" name="email" disabled="disabled" placeholder="Email" required="required" pattern="^.{1,50}$" type="email" value='<jsp:getProperty property="utMail" name="userShowInfor"/>'>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Location</label>
                  <div class="col-sm-10">
                    <input class="form-control" id="inputLocation" name="location" placeholder="Location" required="required" pattern="^.{1,50}$" type="text" value='<jsp:getProperty property="utAddr" name="userShowInfor"/>'>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Profession</label>
                  <div class="col-sm-10">
                    <input class="form-control" id="inputProfession" name="profession" placeholder="Profession" required="required" pattern="^.{1,50}$" type="text" value='<jsp:getProperty property="utPro" name="userShowInfor"/>'>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Education</label>
                  <div class="col-sm-10">
                    <input class="form-control" id="inputEducation" name="education" placeholder="Education" required="required" pattern="^.{1,50}$" type="text" value='<jsp:getProperty property="utEdu" name="userShowInfor"/>'>
                  </div>
                </div>
                <div class="form-group">
                  <label for="inputSkills" class="col-sm-2 control-label">Skills</label>
                  <div class="col-sm-10">
                    <input class="form-control" id="inputSkills" name="skills" placeholder="Skills" required="required" pattern="^.{1,50}$" type="text" value='<jsp:getProperty property="utSkill" name="userShowInfor"/>'>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">Introducation</label>
                  <div class="col-sm-10">
                    <textarea class="form-control" id="inputIntroducation" name="intro" required="required" maxlength="150" placeholder="Introducation"><jsp:getProperty property="utIntro" name="userShowInfor"/></textarea>
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

<div class="modal fade" id="searchModal" tabindex="-1" role="dialog" aria-labelledby="searchModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content no-padding">
      <div class="modal-header" style="padding: 3px">
        <div class="box-header">
          <h3 class="box-title"><strong><i class="fa fa-cloud" style="font-size: 24px"></i>Search Result</strong></h3>

          <div class="box-tools">
            <div class="input-group input-group-sm" style="width: 150px;">
              <input name="table_search" class="form-control pull-right" placeholder="Search" type="text" id="search-input-modal">

              <div class="input-group-btn">
                <button type="button" class="btn btn-default" onclick="submitSearch('search-input-modal')"><i class="fa fa-search"></i></button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="modal-body table-responsive no-padding">
        <table id="search-table" class="table table-hover">
          <tr><td><a href="#">12312412512515</a></td></tr>
          <tr><td><a href="#">12312412512515</a></td></tr>
          <tr><td><a href="#">12312412512515</a></td></tr>
        </table>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

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

(function(window){
    document.writeln("    <link rel=\'stylesheet\' href=\'../css/templet/header.css\'>");
    document.writeln("    <header>");
    document.writeln("        <div id=\'header-container\'>");
    document.writeln("            <div class=\'header-body\'>");
    document.writeln("                <ul id=\'login-info\' class=\'header-body-left\'>");
    document.writeln("                    <li><em style=\'margin-right:10px;\'>欢迎来到旅途</em></li>");
    if (loginState === "unlogin") {
        document.writeln("                    <li class=\'unlogin\'>");
        document.writeln("                        <a href=\'../html/login&register.html\' style=\'margin-right:10px;\'>请登录</a>");
        document.writeln("                        <a href=\'../html/login&register.html\'>注册</a>");
        document.writeln("                    </li>");
    }
    if (loginState === "logined") {
        document.writeln("                    <li class=\'logined\'>");
        document.writeln("                        <a href=\'\'><i class=\'fa fa-user-circle\'>用户名</i></a>");
        document.writeln("                    </li>");
    }
    document.writeln("                </ul>");
    document.writeln("                <ul id=\'menu-info\' class=\'header-body-right\'>");
    document.writeln("                    <li>");
    document.writeln("                        <a>首页</a>");
    document.writeln("                    </li>");
    document.writeln("                    <li>·</li>");
    document.writeln("                    <li id='header-open-collect'>");
    document.writeln("                        <a>收藏夹</a>");
    document.writeln("                    </li>");
    document.writeln("                    <li>·</li>");
    document.writeln("                    <li>");
    document.writeln("                        <a>我要发布</a>");
    document.writeln("                    </li>");
    document.writeln("                </ul>");
    document.writeln("            </div>");
    document.writeln("        </div>");
    document.writeln("        <span id=\"top\"></span>");
    document.writeln("    </header>   ");
    var headerOpencollect=document.querySelector("#header-open-collect");
    headerOpencollect.onclick=function(){
        //登录状态打开收藏夹
        if(loginState==="logined"){
            collect.openCollect();
        }
    }
})(window);
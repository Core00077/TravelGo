//设置登录状态
loginState = "unlogin";

(function () {
    document.writeln("<link rel=\'stylesheet\' href=\'../css/templet/header.css\'>");
    document.writeln("<header>");
    document.writeln("    <div id=\'header-container\'>");
    document.writeln("        <div class=\'header-body\'>");
    document.writeln("            <ul id=\'login-info\' class=\'header-body-left\'>");
    document.writeln("                <li><em style=\'margin-right:10px;\'>欢迎来到旅途</em></li>");
    document.writeln("                    <li class=\'unlogin\'>");
    document.writeln("                        <a href=\'../html/login&register.html\' style=\'margin-right:10px;\'>请登录</a>");
    document.writeln("                        <a href=\'../html/login&register.html\'>注册</a>");
    document.writeln("                    </li>");
    document.writeln("                    <li class=\'logined\'>");
    document.writeln("                        <a href=\'../html/user.html\'><i class=\'fa fa-user-circle\'>用户名</i></a>");
    document.writeln("                        <a href=\'javascript:;\' id=\'quit\'>退出</a>");
    document.writeln("                    </li>");
    document.writeln("            </ul>");
    document.writeln("            <ul id=\'menu-info\' class=\'header-body-right\'>");
    document.writeln("                <li>");
    document.writeln("                    <a href='../html/main.html'>首页</a>");
    document.writeln("                </li>");
    document.writeln("                <li>·</li>");
    document.writeln("                <li id=\'header-open-collect\'>");
    document.writeln("                    <a>收藏夹</a>");
    document.writeln("                </li>");
    document.writeln("                <li>·</li>");
    document.writeln("                <li>");
    document.writeln("                    <a>我要发布</a>");
    document.writeln("                </li>");
    document.writeln("            </ul>");
    document.writeln("        </div>");
    document.writeln("    </div>");
    document.writeln("    <span id=\'top\'></span>");
    document.writeln("</header> ");
})();

(function () {
    //将登录、注册隐藏，显示用户名
    function setHeader(username) {
        var unlogin = document.querySelector("#login-info .unlogin");
        unlogin.style.display = "none";
        var logined = document.querySelector("#login-info .logined");
        var user = document.querySelector("#login-info .logined .fa-user-circle");
        user.innerText = " " + username;
        logined.style.display = "list-item";
    }

    //退出
    var quit = document.querySelector("#quit");
    quit.onclick = function () {
        //发Ajax请求，清除seesion
        var quitRequest = ajaxRequest("get", "/logout");
        quitRequest.then(function (responseText) {
            var result=JSON.parse(responseText);
            if(result.status==="success"){
                window.location.reload(true);
            }
            else{
                dialog.showDialog("退出失败");
                setTimeout(dialog.closeDialog, 1000);
            }
        }).catch(function (errorText) {
            dialog.showDialog("网络错误");
            setTimeout(dialog.closeDialog, 1000);
        });
    }

    //收藏夹
    var headerOpencollect = document.querySelector("#header-open-collect");
    headerOpencollect.onclick = function () {
        //登录状态打开收藏夹
        if (loginState === "logined") {
            //打开收藏夹
            openCollect();
        } else if (loginState === "unlogin") {
            dialog.showDialog("未登录");
            setTimeout(dialog.closeDialog, 1000);
        }
    }
    window.setHeader = setHeader;
})(window);
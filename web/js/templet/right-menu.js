//内容引入
(function(){
    document.writeln("<link rel=\'stylesheet\' href=\'../css/templet/right-menu.css\'>");
    document.writeln("");
    document.writeln("<div id=\'right-menu\'>");
    document.writeln("    <ul id=\'right-menu-ui\'>");
    document.writeln("        <li class=\'user-index\' title=\'个人主页\'>");
    document.writeln("            <a href=\'#javascript:;\' id=\'open-user\'>");
    document.writeln("                <span>");
    document.writeln("                    <i class=\'fa fa-user-circle fa-2x\'></i>");
    document.writeln("                </span>");
    document.writeln("            </a>");
    document.writeln("        </li>");
    document.writeln("        <li class=\'collect-index\' title=\'收藏夹\' id=\'right-open-collect\'>");
    document.writeln("            <a>");
    document.writeln("                <i class=\'fa fa-heart-o fa-2x\'></i>");
    document.writeln("            </a>");
    document.writeln("        </li>");
    document.writeln("        <span class=\'slide-out\' id=\'slide-out\'>");
    document.writeln("            <i class=\'fa fa-caret-left fa-3x\'></i>");
    document.writeln("        </span>");
    document.writeln("    </ul>");
    document.writeln("</div>");
    document.writeln("<span id=\'to-top\' title=\'回到顶部\'>");
    document.writeln("    <a href=\'#\'><i class=\'fa fa-chevron-up fa-2x\'></i></a>");
    document.writeln("</span>");
})();

//
(function(){
    //右部菜单栏淡入淡出
    var rightMenu = document.querySelector("#right-menu");
    rightMenu.addEventListener("mouseover",function(){
        rightMenu.style.right="0px";
    })

    rightMenu.addEventListener("mouseout",function(){
        rightMenu.style.right="-40px";
    })

    var openUser=document.querySelector("#open-user");
    openUser.onclick=function(){
        if(loginState==="logined"){
            window.location.href="user.html";
        }
        if(loginState==="unlogin"){
            dialog.showDialog("未登录");
            setTimeout(dialog.closeDialog,1000);
        }
    }
    var rightOpencollect=document.querySelector("#right-open-collect");
    rightOpencollect.onclick=function(){
        //登录状态打开收藏夹
        if(loginState==="logined"){
            collect.openCollect();
        }
        if(loginState==="unlogin"){
            dialog.showDialog("未登录");
            setTimeout(dialog.closeDialog,1000);
        }
    }
})();
(function(){
    var inputGroup=document.querySelectorAll(".content form .input-text");
    window.onload=function(){
        inputGroup[0].focus();
    }
    //请求数据
    var promise=ajaxRequest("get","/");
    promise.then(function(responseText){
        //
        var result=JSON.parse(responseText);
        //用户数据
        var data=result.data;
        if(result.msg==="unlogin"){
            loginState="unlogin";
            dialog.showDialog("未登录");;
            setTimeout(dialog.closeDialog,1000);
        }
        if(result.msg==="logined"){
            loginState="logined";
            setHeader(data.userName);
            //通过返回的数据填写内容
            var userName=document.querySelector("#username");
            uerName.value=data.userName;
            var trueName=document.querySelector("#truename");
            trueName.value=data.trueName;
            var home=document.querySelector("#home");
            home.value=data.home;
        }

    }).catch(function(errorText){
        dialog.showDialog(errorText);
        setTimeout(dialog.closeDialog,1000);
    });
    var sumbitButton=document.querySelector("#change-message-button");
    sumbitButton.onclick=function(){
        
        dialog.showDialog("修改成功");
        setTimeout(dialog.closeDialog,1500);
    }
})();
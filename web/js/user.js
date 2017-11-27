(function(){
    var inputGroup=document.querySelectorAll(".content form .input-text");
    window.onload=function(){
        inputGroup[0].focus();
    }
    var sumbitButton=document.querySelector("#change-message-button");
    sumbitButton.onclick=function(){
        dialog.showDialog("修改成功");
        setTimeout(dialog.closeDialog,1500);
    }
})();
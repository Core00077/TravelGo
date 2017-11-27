(function(window){
    //详情图
    var smallImages=document.querySelectorAll("#small-images div");
    var bigImages=document.querySelectorAll("#big-images img");
    window.onload=function(){
        smallImages[0].style.border="3px solid #1bab8d";
    }
    for(let i=0;i<smallImages.length;i++){
        smallImages[i].onclick=function(){
            bigImages[i].style.zIndex=1;
            bigImages[i].style.visibility="visible";
            this.style.border="3px solid #1bab8d";
            for(let j=0;j<smallImages.length;j++){
                if(j!==i){
                    bigImages[j].style.zIndex=0;
                    bigImages[j].style.visibility="hidden";
                    smallImages[j].style.border=0;
                }
            }
        }
    }

    //出行人数
    var minusButton=document.querySelector("#number-minus-button");
    var addButton=document.querySelector("#number-add-button");
    var numberSelect=document.querySelector("#number-select");
    minusButton.onclick=function(){
        if(numberSelect.value>1){
            numberSelect.value--;
        }
    }
    addButton.onclick=function(){
        numberSelect.value++;
    }
    
    //提交预定
    var bookButton=document.querySelector("#book-button");
    bookButton.onclick=function(){
        var orderTime=document.querySelector("#order-time-input");
        if(orderTime.value===""){
            dialog.showDialog("请输入出行时间");
            setTimeout(dialog.closeDialog,1000);
        }
    }
})(window);
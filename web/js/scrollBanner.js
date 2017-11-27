(function(){
    //控制当前显示的banner
    var banner={
        nowIndex:0,
        loadBanner:function(){
            var scrollPictures=document.querySelectorAll(".main .scroll-banner img");
            var controlSquare=document.querySelectorAll(".main .search-index span");
            scrollPictures[this.nowIndex%3].style.zIndex=1;
            scrollPictures[this.nowIndex%3].style.visibility="visible";
            scrollPictures[this.nowIndex%3].style.opacity=1;
            controlSquare[this.nowIndex%3].style.backgroundColor="white";
            scrollPictures[(this.nowIndex+1)%3].style.zIndex=0;
            scrollPictures[(this.nowIndex+1)%3].style.visibility="hidden";
            scrollPictures[(this.nowIndex+1)%3].style.opacity=0;
            controlSquare[(this.nowIndex+1)%3].style.backgroundColor="#a2a1a1";
            scrollPictures[(this.nowIndex+2)%3].style.zIndex=0;
            controlSquare[(this.nowIndex+2)%3].style.backgroundColor="#a2a1a1";
            scrollPictures[(this.nowIndex+2)%3].style.opacity=0;
        }
    };
    var timer;
    //自动变换banner
    var scrollBanner=function(){
        banner.loadBanner();
        banner.nowIndex++;
        timer=setTimeout(scrollBanner,4000);
    }
    //通过点击改变banner
    var controlSpan=document.querySelectorAll(".search-index span");
    for(let i=0;i<controlSpan.length;i++){
        controlSpan[i].onclick=function(){
            clearTimeout(timer);
            banner.nowIndex=i;
            banner.loadBanner();
            //重新开始轮播
            scrollBanner();
        }
    }
    //外部变量
    window.scrollBanner=scrollBanner;
})();
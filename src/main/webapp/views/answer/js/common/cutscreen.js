$(function(){
    orient();
});
function orient() {
    if (window.orientation == 0 || window.orientation == 180) {
        //alert('竖屏');
        //orientation = 'portrait';
        return false;
    }else if (window.orientation == 90 || window.orientation == -90) {
        //$('#div1').hide();
        //$('#img1').show();
        //alert('请切换为竖屏');
        //       orientation = 'landscape';
        return false;
    }else{
        if(window.innerWidth > window.innerHeight){
            //alert('请切换为竖屏');
            return false;
        }
    }
}

/* 在用户变化屏幕显示方向的时候调用*/
$(window).bind( 'orientationchange', function(e){
    orient();
});
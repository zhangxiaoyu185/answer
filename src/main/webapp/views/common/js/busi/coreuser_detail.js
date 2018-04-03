$(function () {
    initDetail();
});

//初始化
function initDetail() {
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'crusrUuid=' + encodeURIComponent(id);
    getOData(str, "coreUser/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".crusrName").text(oData.data.crusrName || "");
                $(".crusrCode").text(oData.data.crusrCode || "");
                $(".crusrMobile").text(oData.data.crusrMobile || "");
                $(".crusrCdate").text(getFormatDate(oData.data.crusrCdate) || "");
                $(".crusrUdate").text(getFormatDate(oData.data.crusrUdate) || "");
                var crusrGenderCH = "男";
                if(oData.data.crusrGender == 0){
                    crusrGenderCH = "女";
                }
                $(".crusrGender").text(crusrGenderCH || "");
                $(".crusrSchool").text(oData.data.crusrSchool || "");
                $(".crusrClass").text(oData.data.crusrClass || "");
            } else {
                alert(data.errMsg);
            }
        }
    });
}

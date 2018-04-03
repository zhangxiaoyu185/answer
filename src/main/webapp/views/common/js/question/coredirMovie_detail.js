// JavaScript Document
$(function () {
    initDetail();
});

//初始化
function initDetail() {
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'crdirUuid=' + encodeURIComponent(id);
    getOData(str, "coreDirMovie/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".crdirName").text(oData.data.crdirName || "");
            } else {
                alert(data.errMsg);
            }
        }
    });
}

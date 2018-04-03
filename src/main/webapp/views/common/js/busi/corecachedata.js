// JavaScript Document
$(function () {
    $(".cur_postion").html("当前位置： [ " + sessionStorage.getItem("pmenuname") + " ] - [ " + sessionStorage.getItem("cmenuname") + " ]");
    initModify();
    //提交
    $(".submit").on("click", function () {
        checkModify();
    });
});

//初始化
function initModify() {
    $(".crcdaValue").val("");
    getInfo('zjjgsj');
}

//获取详情
function getInfo(id) {
    var str = 'crcdaUuid=' + encodeURIComponent(id);
    getOData(str, "coreCacheData/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".crcdaValue").val(oData.data.crcdaValue);
            } else {
                alert(data.errMsg);
            }
        }
    });
}

//检查提交
function checkModify() {
    if ($.trim($(".crcdaValue").val()) == "") {
        alert("做卷间隔时间不能为空，请填写完再提交！");
        $(".crcdaValue").focus();
        return false;
    }

    var r = confirm("是否确认修改？");
    if (r == true) {
        Modify();
    }
}

//提交
function Modify() {
    var crcdaUuid = 'zjjgsj';
    var crcdaValue = $(".crcdaValue").val();
    var str = 'crcdaUuid=' + encodeURIComponent(crcdaUuid) + '&crcdaValue=' + encodeURIComponent(crcdaValue);
    getOData(str, "coreCacheData/update/coreCacheData", {
        fn: function (oData) {
            alert("修改成功！");
        },
        fnerr: function (oData) {
            alert("修改失败！");
        }
    });
}
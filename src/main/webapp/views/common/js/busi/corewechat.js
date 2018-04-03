// JavaScript Document
$(function () {
    initModify();
    //提交
    $(".submit").on("click", function () {
        checkModify();
    });
});

//初始化
function initModify() {
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'crwctUuid=' + encodeURIComponent(sessionStorage.getItem("weixinUuid"));
    getOData(str, "coreWechat/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".crwctAppid").val(oData.data.crwctAppid || "");
                $(".crwctAppsecret").val(oData.data.crwctAppsecret || "");
                $(".crwctPartner").val(oData.data.crwctPartner || "");
                $(".crwctPartnerkey").val(oData.data.crwctPartnerkey || "");
                $(".crwctRemarks").val(oData.data.crwctRemarks || "");
            } else {
                alert(data.errMsg);
            }
        }
    });
}

//检查提交
function checkModify() {
    if ($.trim($(".crwctAppid").val()) == "") {
        alert("appid不能为空，请填写完再提交！");
        $(".crwctAppid").focus();
        return false;
    }
    if ($.trim($(".crwctAppsecret").val()) == "") {
        alert("appsecret不能为空，请填写完再提交！");
        $(".crwctAppsecret").focus();
        return false;
    }

    var r = confirm("是否确认修改？");
    if (r == true) {
        Modify();
    }
}

//提交
function Modify() {
    var crwctUuid = encodeURIComponent(sessionStorage.getItem("weixinUuid"));
    var crwctAppid = $(".crwctAppid").val();
    var crwctAppsecret = $(".crwctAppsecret").val();
    var crwctPartner = $(".crwctPartner").val();
    var crwctPartnerkey = $(".crwctPartnerkey").val();
    var crwctRemarks = $(".crwctRemarks").val();
    var str = 'crwctUuid=' + encodeURIComponent(crwctUuid) + '&crwctAppid=' + encodeURIComponent(crwctAppid) + '&crwctAppsecret=' + encodeURIComponent(crwctAppsecret) + '&crwctPartner=' + encodeURIComponent(crwctPartner) + '&crwctPartnerkey=' + encodeURIComponent(crwctPartnerkey) + '&crwctRemarks=' + encodeURIComponent(crwctRemarks);
    getOData(str, "coreWechat/update/coreWechat", {
        fn: function (oData) {
            alert("修改成功！");
        },
        fnerr: function (oData) {
            alert("修改失败！");
        }
    });
}
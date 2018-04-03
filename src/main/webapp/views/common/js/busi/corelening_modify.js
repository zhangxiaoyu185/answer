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
    var str = 'crlngUuid=' + encodeURIComponent(id);
    getOData(str, "coreLening/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".crlngName").val(oData.data.crlngName || "");
            } else {
                alert(data.errMsg);
            }
        }
    });
}

//检查提交
function checkModify() {
    if ($.trim($(".crlngName").val()) == "") {
        alert("教辅名称不能为空，请填写完再提交！");
        $(".crlngName").focus();
        return false;
    }

    var r = confirm("是否确认修改？");
    if (r == true) {
        var msgObject = parent.layer.msg('处理中，请等待……', {
            icon: 16,
            shade: 0.4,
            time: waitImgTime //（如果不配置，默认是3秒）
        }, function (index) {
            parent.layer.close(index);
        });
        Modify(msgObject);
    }
}

//提交
function Modify(msgObject) {
    var crlngUuid = getQueryString("id");
    var crlngName = $(".crlngName").val();
    var str = 'crlngUuid=' + encodeURIComponent(crlngUuid)
        + '&crlngName=' + encodeURIComponent(crlngName);
    getOData(str, "coreLening/update/coreLening", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("修改成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}
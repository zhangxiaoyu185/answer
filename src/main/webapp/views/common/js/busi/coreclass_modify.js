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
    var str = 'crcasUuid=' + encodeURIComponent(id);
    getOData(str, "coreClass/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".crcasName").val(oData.data.crcasName || "");
            } else {
                alert(data.errMsg);
            }
        }
    });
}

//检查提交
function checkModify() {
    if ($.trim($(".crcasName").val()) == "") {
        alert("班级名称不能为空，请填写完再提交！");
        $(".crcasName").focus();
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
    var crcasUuid = getQueryString("id");
    var crcasName = $(".crcasName").val();
    var str = 'crcasUuid=' + encodeURIComponent(crcasUuid)
        + '&crcasName=' + encodeURIComponent(crcasName);
    getOData(str, "coreClass/update/coreClass", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("修改成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}
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
    $(".crdirName").val("");
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'crdirUuid=' + encodeURIComponent(id);
    getOData(str, "coreDirMovie/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".crdirName").val(oData.data.crdirName || "");
            } else {
                alert(data.errMsg);
            }
        }
    });
}

//检查提交
function checkModify() {
    if ($.trim($(".crdirName").val()) == "") {
        alert("目录名称不能为空，请填写完再提交！");
        $(".crdirName").focus();
        return false;
    }

    var r = confirm("是否确认修改？");
    if (r == true) {
        var msgObject = parent.layer.msg('处理中，请等待……', {
            icon: 16,
            shade: 0.4,
            time: waitImgTime //（如果不配置，默认是3秒）
        }, function (index) {
            //do something
            parent.layer.close(index);
        });
        Modify(msgObject);
    }
}

//提交
function Modify(msgObject) {
    var crdirUuid = getQueryString("id");
    var crdirName = $(".crdirName").val();
    var str = 'crdirUuid=' + encodeURIComponent(crdirUuid) + '&crdirName=' + encodeURIComponent(crdirName);
    getOData(str, "coreDirMovie/update/coreDirMovie", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("修改成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}
// JavaScript Document
$(function () {
    initAdd();
    //提交
    $(".submit").on("click", function () {
        checkAdd();
    });
});

//初始化
function initAdd() {
    $(".crdirName").val("");
}

//检查提交
function checkAdd() {
    if ($.trim($(".crdirName").val()) == "") {
        alert("目录名称不能为空，请填写完再提交！");
        $(".crdirName").focus();
        return false;
    }

    var r = confirm("是否确认增加？");
    if (r == true) {
        var msgObject = parent.layer.msg('处理中，请等待……', {
            icon: 16,
            shade: 0.4,
            time: waitImgTime //（如果不配置，默认是3秒）
        }, function (index) {
            //do something
            parent.layer.close(index);
        });
        Add(msgObject);
    }
}

//提交
function Add(msgObject) {
    var crdirName = $(".crdirName").val();
    var str = 'crdirName=' + encodeURIComponent(crdirName);
    getOData(str, "coreDirMovie/add/coreDirMovie", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("增加成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}
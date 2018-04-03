$(function () {
    initModify();
    //提交
    $(".submit").on("click", function () {
        checkModify();
    });

    //下拉选择框
    $(".select-item").unbind().on('click', function () {
        var thisinput = $(this);
        var thisul = $(this).parent().find("ul");
        if (thisul.css("display") == "none") {
            if (thisul.height() > 150) {
                thisul.css({height: "150" + "px", "overflow-y": "scroll"});
            }
            ;
            thisul.fadeIn("100");
            thisul.hover(function () {
            }, function () {
                thisul.fadeOut("100");
            });

        }
        else {
            thisul.fadeOut("fast");
        }
    });
});

//初始化
function initModify() {


    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'crrolUuid=' + encodeURIComponent(id);
    getOData(str, "coreRole/getRoleByUUID", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".roleName").val(oData.data.crrolName);
                $(".menu").find(".select-item").val(oData.data.crrolFunsName);
                $(".menu").find(".select-item").attr("data-ids", oData.data.crrolFuns);
                $(".desc").val(oData.data.crrolDesc);
                getMenu([], 0, function (zNodes) {
                    $.fn.zTree.init($("#selectTree"), setting, zNodes);
                });
            } else {
                alert(data.errMsg);
            }
        }
    });
}

//检查提交
function checkModify() {
    if ($.trim($(".roleName").val()) == "") {
        alert("角色名不能为空，请填写完再提交！");
        $(".roleName").focus();
        return false;
    }
    if ($.trim($(".menu").find(".select-item").val()) == "") {
        alert("菜单不能为空，请填写完再提交！");
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
    var crrolUuid = getQueryString("id");
    var crrolName = $(".roleName").val();
    var crrolFuns = $(".menu").find(".select-item").attr("data-ids");
    var crrolDesc = $(".desc").val();

    var str = 'crrolUuid=' + encodeURIComponent(crrolUuid) + '&crrolName=' + encodeURIComponent(crrolName) + '&crrolFuns=' + encodeURIComponent(crrolFuns) + '&crrolDesc=' + encodeURIComponent(crrolDesc);
    getOData(str, "coreRole/updateRole", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("修改成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }

    });
}

var setting = {
    view: {
        showIcon: false,
        selectedMulti: false
    },
    check: {
        enable: true
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeCheck: beforeCheck,
        onCheck: onCheck
    }
};

function beforeCheck(treeId, treeNode) {
    return (treeNode.doCheck !== false);
}

function onCheck(e, treeId, treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj("selectTree"),
        nodes = treeObj.getCheckedNodes(true),
        v = "",
        ids = "";
    for (var i = 0; i < nodes.length; i++) {
        v += nodes[i].name + "|";
        ids += nodes[i].id + "|";
    }
    $(".menu").find(".select-item").attr("data-ids", ids);
    $(".menu").find(".select-item").val(v);
}

function getMenu(zNodes, deleted, fn) {
    var str = '';
    getOData(str, "coreFunction/menuList", {
        fn: function (oData) {
            var ids = $(".menu").find(".select-item").attr("data-ids").split("|");
            $.each(oData.data, function (i, item) {
                var obj = {};
                obj.id = item.crfntUuid;
                obj.pId = item.crfntFatherUuid;
                obj.name = item.crfntFunName;

                obj.open = false;
                for (var j = 0; j < ids.length; j++) {
                    if (obj.id == ids[j]) {
                        obj.checked = true;
                    }
                }
                zNodes.push(obj);
            });

            fn && fn(zNodes);
        }
    });
}
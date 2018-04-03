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
            thisul.find(".searchit").focus();
            thisul.find('.searchit').on('keyup', function () {
                var searchText = $(this).val();
                thisul.find("li").each(function () {
                    if ($(this).text().indexOf(searchText) > -1) {
                        $(this).show();
                    }
                    else {
                        $(this).hide();
                    }
                });
            });
            thisul.find("li").unbind().click(function () {
                thisinput.val($(this).text());
                thisinput.attr("data-id", $(this).attr("data-id"));
                thisinput.css('color', '#000');
                thisul.fadeOut("100");
            }).hover(function () {
                    $(this).addClass("select-hover");
                },
                function () {
                    $(this).removeClass("select-hover");
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
    var str = 'crfntUuid=' + encodeURIComponent(id);
    getOData(str, "coreFunction/getMenuByUUID", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".name").val(oData.data.crfntFunName);
                $(".address").val(oData.data.crfntResource);
                $(".parentMenu").find(".select-item").attr("data-id", oData.data.crfntFatherUuid);
                $(".parentMenu").find(".select-item").val(oData.data.crfntFatherName);
                getParentMenu();
            } else {
                alert(data.errMsg);
            }
        }
    });
}

//获取下拉框里的值
function getParentMenu() {
    var str = '';
    getOData(str, "coreFunction/main/menu", {
        fn: function (oData) {
            if (oData.code == 1) {
                var strhtml_selectUl = '';
                strhtml_selectUl += '<input type="text" style="width:400px;" class="searchit"></input>';
                var arrData = oData.data;
                var ln = arrData.length;
                for (var i = 0; i < ln; i++) {
                    strhtml_selectUl += '<li data-id="' + arrData[i].crfntUuid + '">' + arrData[i].crfntFunName + '</li>';
                }
                $('.parentMenu').find('.select-ul').html(strhtml_selectUl);
            } else {
                alert(data.errMsg);
            }
        }
    });
}

//检查提交
function checkModify() {
    if ($.trim($(".name").val()) == "") {
        alert("菜单名不能为空，请填写完再提交！");
        $(".name").focus();
        return false;
    }
    if ($.trim($(".address").val()) == "") {
        alert("源地址不能为空，请填写完再提交！");
        $(".address").focus();
        return false;
    }

    if ($.trim($(".parentMenu").find(".select-item").val()) == "") {
        alert("上级菜单不能为空，请填写完再提交！");
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
    var crfntUuid = getQueryString("id");
    var crfntFunName = $(".name").val();
    var crfntResource = $(".address").val();
    var crfntFatherUuid = $(".parentMenu").find(".select-item").attr("data-id");
    var str = 'crfntUuid=' + encodeURIComponent(crfntUuid) + '&crfntFunName=' + encodeURIComponent(crfntFunName) + '&crfntResource=' + encodeURIComponent(crfntResource) + '&crfntFatherUuid=' + encodeURIComponent(crfntFatherUuid);
    getOData(str, "coreFunction/updateMenu", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("修改成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }

    });
}

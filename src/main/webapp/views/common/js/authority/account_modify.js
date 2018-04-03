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
                var ids = "";
                var str_val = "";
                if ($(this).find('input').is(':checked')) {
                    $(this).find("input").attr("checked", '');
                    $(this).find("input").removeAttr("checked");
                }
                else {
                    $(this).find("input").attr("checked", 'checked');
                }
                $(this).parent().find(":checkbox").each(function (i) {
                    if ($(this).attr("checked")) {
                        ids += $(this).parent("li").attr("data-id") + "|";
                        str_val += $(this).parent("li").text() + "|";
                    }
                });
                thisinput.val(str_val);

                thisinput.attr("data-ids", ids);
                thisinput.css('color', '#000');
                //thisul.fadeOut("100");
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
    var str = 'cractUuid=' + encodeURIComponent(id);
    getOData(str, "coreAccount/getAccountByUNID", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".accountName").text(oData.data.cractName);
                $(".accountRoles").find(".select-item").attr("data-ids", oData.data.cractRoles);
                $(".accountRoles").find(".select-item").val(oData.data.cractRolesName);
                $(".accountTel").val(oData.data.cractTel);
                $(".accountEmail").val(oData.data.cractEmail);
                $(".accountRemarks").val(oData.data.cractRemarks);
                getRoles(oData.data.cractRoles);
            } else {
                alert(data.errMsg);
            }
        }
    });
}

//获取下拉框里的值
function getRoles(roles) {
    var str = '';
    getOData(str, "coreRole/roleList", {
        fn: function (oData) {
            if (oData.code == 1) {
                var strhtml_selectUl = '';
                strhtml_selectUl += '<input type="text" style="width:400px;" class="searchit"></input>';
                var arrData = oData.data;
                var ln = arrData.length;
                var ids = roles.split("|");
                for (var i = 0; i < ln; i++) {
                    var ischecked = false;
                    for (var j = 0; j < ids.length; j++) {
                        if (ids[j] == arrData[i].crrolUuid) {
                            ischecked = true;
                            continue;
                        }
                    }
                    if (ischecked) {
                        strhtml_selectUl += '<li data-id="' + arrData[i].crrolUuid + '"><input type="checkbox" name="checkbox" checked="true"/>' + arrData[i].crrolName + '</li>';
                    } else {
                        strhtml_selectUl += '<li data-id="' + arrData[i].crrolUuid + '"><input type="checkbox" name="checkbox"/>' + arrData[i].crrolName + '</li>';
                    }

                }
                $('.accountRoles').find('.select-ul').html(strhtml_selectUl);
            } else {
                alert(data.errMsg);
            }
        }
    });
}

//检查提交
function checkModify() {
    if ($.trim($(".accountRoles").find(".select-item").val()) == "") {
        alert("角色不能为空，请填写完再提交！");
        $(".accountRoles").focus();
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
    var cractUuid = getQueryString("id");
    var cractRoles = $(".accountRoles").find(".select-item").attr("data-ids");
    var cractTel = $(".accountTel").val();
    var cractEmail = $(".accountEmail").val();
    var cractRemarks = $(".accountRemarks").val();

    var str = 'cractUuid=' + encodeURIComponent(cractUuid) + '&cractRoles=' + encodeURIComponent(cractRoles) + '&cractTel=' + encodeURIComponent(cractTel) + '&cractEmail=' + encodeURIComponent(cractEmail) + '&cractRemarks=' + encodeURIComponent(cractRemarks);
    getOData(str, "coreAccount/updateAccount", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("修改成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}

$(function () {
    $(".cur_postion").html("当前位置： [ " + sessionStorage.getItem("pmenuname") + " ] - [ " + sessionStorage.getItem("cmenuname") + " ]");

    //查询条件
    var strhtml_searchContent = '<div class="inline-block margin">'
        + '<span>所属教辅:</span>'
        + '<select class="inputStyle_condition crpesLening"></select>'
        + '<span>试卷名:</span>'
        + '<input type="text" class="inputStyle_condition crpesName"/>'
        + '</div>';
    $(".searchContent").html(strhtml_searchContent);

    getOData('',"coreLening/find/all",{fn:function(oData){
        if(oData.code == 1) {
            var strhtml_select = '<option value="">全部</option>';
            var arrData = oData.data;
            var ln = arrData.length;
            for(var i=0;i<ln;i++){
                strhtml_select += '<option value="'+arrData[i].crlngUuid+'">'+arrData[i].crlngName+'</option>';
            }
            $('.crpesLening').append(strhtml_select);
        } else {
            alert(data.errMsg);
        }
    }},true);

    //是否显示查询条件
    showSearchBox(true);

    var obj = {};//查询条件对象
    searchContent(obj);
    showList(obj, 1);

    //详情
    $("body").delegate('.detailit', 'click', function () {
        layer.open({
            type: 2,
            title: '详情',
            scrollbar: false,
            maxmin: true,
            shadeClose: false, //点击遮罩关闭层
            area: [widthLayer, heightLayer],
            content: '../question/corepapers_detail.html?id=' + $(this).attr("data-id") + '&timestamp=' + (new Date()).valueOf()
        });
    });
    //新增
    $('.addit').on('click', function () {
        layer.open({
            type: 2,
            title: '新增',
            scrollbar: false,
            maxmin: true,
            shadeClose: false, //点击遮罩关闭层
            area: [widthLayer, heightLayer],
            content: '../question/corepapers_add.html?timestamp=' + (new Date()).valueOf()
        });
    });
    //修改
    $("body").delegate('.modifyit', 'click', function () {
        layer.open({
            type: 2,
            title: '修改',
            scrollbar: false,
            maxmin: true,
            shadeClose: false, //点击遮罩关闭层
            area: [widthLayer, heightLayer],
            content: '../question/corepapers_modify.html?id=' + $(this).attr("data-id") + '&timestamp=' + (new Date()).valueOf()
        });
    });
    //编辑题目
    $("body").delegate('.modifyQuestion', 'click', function () {
        layer.open({
            type: 2,
            title: '编辑题目',
            scrollbar: false,
            maxmin: true,
            shadeClose: false, //点击遮罩关闭层
            area: ['900px', heightLayer],
            content: '../question/questions_modify.html?id=' + $(this).attr("data-id") + '&timestamp=' + (new Date()).valueOf()
        });
    });
    //全选
    $("body").delegate("input[name='checkboxAll']", "click", function () {
        if ($(this).attr("checked")) {
            $("input[name='checkbox']").each(function () {
                $(this).attr("checked", true);
            });
        } else {
            $("input[name='checkbox']").each(function () {
                $(this).removeAttr("checked");
            });
        }
    });
    //批量删除
    $("body").delegate(".delthese", "click", function () {
        var ids = '';
        $("input[name='checkbox']").each(function () {
            if ($(this).attr("checked")) {
                ids += $(this).attr("data-id") + "|";
            }
        });
        if (ids == "") {
            alert("未选择删除对象！");
        } else {
            var r = confirm("是否确认删除所选的记录？");
            if (r == true) {
                var str = 'crpesUuids=' + encodeURIComponent(ids);
                getOData(str, "corePapers/delete/batch", {
                        fn: function (oData) {
                            var obj = {};//查询条件对象
                            searchContent(obj);
                            pagenum = parseInt($(".curpage").text());
                            isNull(obj, pagenum);
                            alert("删除成功！");
                        }
                    }
                );
            }
        }
    });
    //打开
    $("body").delegate(".openit", "click", function () {
        var r = confirm("是否确认打开？");
        if (r == true) {
            var str = 'crpesUuid=' + encodeURIComponent($(this).attr("data-id"));
            getOData(str, "corePapers/open", {
                    fn: function (oData) {
                        var obj = {};//查询条件对象
                        searchContent(obj);
                        pagenum = parseInt($(".curpage").text());
                        isNull(obj, pagenum);
                        alert("打开成功！");
                    }
                }
            );
        }
    });
    //关闭
    $("body").delegate(".closeit", "click", function () {
        var r = confirm("是否确认关闭？");
        if (r == true) {
            var str = 'crpesUuid=' + encodeURIComponent($(this).attr("data-id"));
            getOData(str, "corePapers/close", {
                    fn: function (oData) {
                        var obj = {};//查询条件对象
                        searchContent(obj);
                        pagenum = parseInt($(".curpage").text());
                        isNull(obj, pagenum);
                        alert("关闭成功！");
                    }
                }
            );
        }
    });
    //tr高亮显示
    $("body").delegate(".trHighLight", "mouseleave", function () {
        $(this).find("td").css("background-color", "#fff");
    });
    //tr高亮显示并显示图
    $("body").delegate(".trHighLight", "mouseenter", function () {
        $(this).find("td").css("background-color", "#c1ebff");
        var imgurl = $(this).attr("data-imageurl");
        if (imgurl != "#") {
            var that = this;
            $("<img/>").attr("src", imgurl).load(function () {
                var width = 150;
                var realWidth = this.width;
                var realHeight = this.height;
                var height = realHeight / realWidth * width;
                if (realWidth >= width) {
                    var bannerimg = '<img id="bannerimg" src="' + imgurl + '" width="' + width + 'px" height="' + height + 'px" />';
                }
                else {//如果小于浏览器的宽度按照原尺寸显示
                    $("#bannerimg").css("width", realWidth + 'px').css("height", realHeight + 'px');
                    var bannerimg = '<img id="bannerimg" src="' + imgurl + '" width="' + realWidth + 'px" height="' + realHeight + 'px" />';
                }
                layer.tips(bannerimg, that, {
                    tips: 3
                });
            });
        }
    });
    //查询
    $("body").delegate(".searchBtn", "click", function () {
        var obj = {};//查询条件对象
        searchContent(obj);
        showList(obj, 1);
    });
    //重置
    $("body").delegate(".resetBtn", "click", function () {
        var obj = {};//查询条件对象
        $(".crpesLening").val('');
        $(".crpesName").val('');
        searchContent(obj);
        showList(obj, 1);
    });
    //上一页
    $('.manageBox').delegate(".backpage", "click", function () {
        var obj = {};//查询条件对象
        searchContent(obj);
        pagenum = parseInt($(this).attr("data-pagenum"));
        showList(obj, pagenum);
    });
    //下一页
    $('.manageBox').delegate(".nextpage", "click", function () {
        var obj = {};//查询条件对象
        searchContent(obj);
        var pagenum = parseInt($(this).attr("data-pagenum"));
        showList(obj, pagenum);
    });
    //首页
    $('.manageBox').delegate(".firstpage", "click", function () {
        var obj = {};//查询条件对象
        searchContent(obj);
        var pagenum = parseInt($(this).attr("data-pagenum"));
        showList(obj, pagenum);
    });
    //末页
    $('.manageBox').delegate(".lastpage", "click", function () {
        var obj = {};//查询条件对象
        searchContent(obj);
        var pagenum = parseInt($(this).attr("data-pagenum"));
        showList(obj, pagenum);
    });
    //跳转至
    $('.manageBox').delegate(".jumppage", "click", function () {
        var obj = {};//查询条件对象
        searchContent(obj);
        var pagenum = parseInt($('.jumppagetext').val());
        if (pagenum > 0 && pagenum <= parseInt($(this).attr("data-pagemax"))) {
            showList(obj, pagenum);
        } else {
            alert("查无此页！");
        }
    });

});

function showList(obj, pagenum) {
    var aData = [{name: "<input type='checkbox' name='checkboxAll' value='checkbox' />", percent: "5"},
        {name: "所属教辅", percent: "20"},
        {name: "试卷名", percent: "20"},
        {name: "总分", percent: "8"},
        {name: "开放状态", percent: "9"},
        {name: "创建时间", percent: "15"},
        {name: "操作", percent: "23"}];
    setTableHead(aData);
    var str = 'pageNum=' + pagenum + '&pageSize=10';
    if (obj.crpesLening && obj.crpesLening != "") {
        str += '&crpesLening=' + encodeURIComponent(obj.crpesLening);
    }
    if (obj.crpesName != "") {
        str += '&crpesName=' + encodeURIComponent(obj.crpesName);
    }
    getOData(str, "corePapers/find/by/cnd", {
            fn: function (oData) {
                var strhtml_list = "";
                var arrData = oData.data.result;
                var ln = arrData.length;
                for (var i = 0; i < ln; i++) {
                    var crpesOpenCH = "已开放";
                    var crpesOpenOper = '<a  class="p-edit closeit" data-id="' + arrData[i].crpesUuid + '">关闭</a>';
                    if(arrData[i].crpesOpen == 0) {
                        crpesOpenCH = "未开放";
                        crpesOpenOper = '<a  class="p-edit openit" data-id="' + arrData[i].crpesUuid + '">打开</a>';
                    }
                    strhtml_list += '<tr class="trHighLight">'
                        + '<td>' + '<input type="checkbox" name="checkbox" value="checkbox" data-id="' + arrData[i].crpesUuid + '"/>' + '</td>'
                        + '<td>' + (arrData[i].crlngName || "") + '</td>'
                        + '<td>' + (arrData[i].crpesName || "") + '</td>'
                        + '<td>' + (arrData[i].crpesScore || 0) + '</td>'
                        + '<td>' + (crpesOpenCH || "") + '</td>'
                        + '<td>' + (getFormatDate(arrData[i].crpesCdate) || "") + '</td>'
                        + '<td>'
                        + '<a  class="p-edit detailit" data-id="' + arrData[i].crpesUuid + '">查看</a>'
                        + '<a  class="p-edit modifyit" data-id="' + arrData[i].crpesUuid + '">修改</a>'
                        + '<a  class="p-edit modifyQuestion" data-id="' + arrData[i].crpesUuid + '">编辑题目</a>'
                        + '<a  class="p-edit delit" data-id="' + arrData[i].crpesUuid + '">删除</a>'
                        + crpesOpenOper
                        + '</td>'
                        + '</tr>';
                }
                $(".tb-body").html(strhtml_list);
                setTableFoot(oData.data, aData.length);
            }
        }
    );
}

function isNull(obj, pagenum) {
    var str = 'pageNum=' + pagenum + '&pageSize=10';
    if (obj.crpesLening && obj.crpesLening != "") {
        str += '&crpesLening=' + encodeURIComponent(obj.crpesLening);
    }
    if (obj.crpesName != "") {
        str += '&crpesName=' + encodeURIComponent(obj.crpesName);
    }
    getOData(str, "corePapers/find/by/cnd", {
        fn: function (oData) {
            var arrData = oData.data.result;
            var ln = arrData.length;
            if (ln == 0) {
                if (oData.data.totalCount != 0 && pagenum != 1) {
                    showList(obj, pagenum - 1);
                } else {
                    showList(obj, 1);
                }
            } else {
                showList(obj, pagenum);
            }
        }
    });
}

function refreshList() {
    var obj = {};//查询条件对象
    searchContent(obj);
    showList(obj, parseInt($(".curpage").text()));
    layer.closeAll();
}

function searchContent(obj) {
    obj.crpesLening = $(".crpesLening").val();
    obj.crpesName = $(".crpesName").val();
}

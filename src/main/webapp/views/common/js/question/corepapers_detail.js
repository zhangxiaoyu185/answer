$(function () {
    initDetail();
});

//初始化
function initDetail() {
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'crpesUuid=' + encodeURIComponent(id);
    getOData(str, "corePapers/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".crlngName").text(oData.data.crlngName || "");
                $(".crpesName").text(oData.data.crpesName || "");
                $(".crpesExpire").text(oData.data.crpesExpire || 0);
                $(".crpesScore").text(oData.data.crpesScore || 0);
                $(".crpesCdate").text(getFormatDate(oData.data.crpesCdate) || "");
                $(".crpesUdate").text(getFormatDate(oData.data.crpesUdate) || "");
                $(".qurl").text(urlfile+"views/answer/index.html?paper="+encodeURIComponent(id) || "");
                var crpesOpenCH = "已开放";
                if(oData.data.crpesOpen == 0) {
                    crpesOpenCH = "未开放";
                }
                $(".crpesOpen").text(crpesOpenCH || "");
                var crpesQuestion = oData.data.crpesQuestion;
                if(''!=crpesQuestion && undefined!=crpesQuestion){
                    var parsedJson = jQuery.parseJSON(crpesQuestion);
                    var problems = parsedJson.problem;
                    var strhtml_list = "";
                    for ( var i in problems) {
                        strhtml_list = strhtml_list + '<tr class="trHighLight">'
                            +'<td>'+(problems[i].name || "")+'</td>'
                            +'<td>'+(problems[i].result || "")+'</td>'
                            +'<td>'+(problems[i].oldScore || 0)+'</td>'
                            +'<td>'+(problems[i].dirMovie || "")+'</td>'
                            +'<td>'+(problems[i].movie || "")+'</td>'
                            +'</tr>';
                    }
                    $(".tb-body").html(strhtml_list);
                }
                $("#qcode").qrcode({
                    render: "table", //table方式
                    width: 150, //宽度
                    height:150, //高度
                    text: urlfile+"views/answer/index.html?paper="+encodeURIComponent(id) //任意内容
                });
            } else {
                alert(oData.errMsg);
            }
        }
    });
}

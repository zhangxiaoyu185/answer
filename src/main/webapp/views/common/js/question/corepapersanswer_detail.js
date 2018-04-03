$(function () {
    initDetail();
});

//初始化
function initDetail() {
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'crpsaUuid=' + encodeURIComponent(id);
    getOData(str, "corePapersAnswer/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".crlngName").text(oData.data.crlngName || "");
                $(".crpesName").text(oData.data.crpesName || "");
                $(".crpsaPaperScore").text(oData.data.crpsaPaperScore || "");
                $(".crpsaScore").text(oData.data.crpsaScore || 0);
                $(".crpsaExpire").text(oData.data.crpsaExpire || 0);
                $(".crusrCode").text(oData.data.crusrCode || "");
                $(".crusrName").text(oData.data.crusrName || "");
                $(".crpsaCdate").text(getFormatDate(oData.data.crpsaCdate) || "");
                $(".crpsaUdate").text(getFormatDate(oData.data.crpsaUdate) || "");
                var crpsaQuestion = oData.data.crpsaQuestion;
                if(''!=crpsaQuestion && undefined!=crpsaQuestion){
                    var parsedJson = jQuery.parseJSON(crpsaQuestion);
                    var problems = parsedJson.problem;
                    var strhtml_list = "";
                    for ( var i in problems) {
                        var judgeCH = '对';
                        if(problems[i].judge == 2) {
                            judgeCH = '错';
                        }
                        strhtml_list += '<tr class="trHighLight">'
                            +'<td>'+(problems[i].name || "")+'</td>'
                            +'<td>'+(problems[i].content|| "")+'</td>'
                            +'<td>'+(problems[i].result || "")+'</td>'
                            +'<td>'+(judgeCH || "")+'</td>'
                            +'<td>'+(problems[i].score || 0)+'</td>'
                            +'<td>'+(problems[i].oldScore || 0)+'</td>'
                            +'<td>'+(problems[i].dirMovie || "")+'</td>'
                            +'<td>'+(problems[i].movie || "")+'</td>'
                            +'</tr>';
                    }
                    $(".tb-body").html(strhtml_list);
                }
            } else {
                alert(data.errMsg);
            }
        }
    });
}

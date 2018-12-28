var Sheet = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "sheet"
};

/**
 * 插入
 */
Sheet.insert = function () {
    var form = $("#create-form");
    var formData = getFormJson(form);
    console.log(formData);

    var date = formData.date;
    if(null==date||""==date){
        error("日期不能为空");
        return;
    }
    var time = formData.time;
    if(null==time||""==time){
        error("时间不能为空");
        return;
    }
    // var accidentMan = formData.accidentMan;
    // if(null==accidentMan||""==accidentMan){
    //     error("涉及人员不能为空");
    //     return;
    // }
    var accidentPlace = formData.accidentPlace;
    if(null==accidentPlace||""==accidentPlace){
        error("事发地点不能为空");
        return;
    }
    var accidentSituation = formData.accidentSituation;
    if(null==accidentSituation||""==accidentSituation){
        error("事发情况不能为空");
        return;
    }
    var reportMan = formData.reportMan;
    if(null==reportMan||""==reportMan){
        error("汇报人不能为空");
        return;
    }
    var workNum = formData.workNum;
    if(null==workNum||""==workNum){
        error("汇报人工号不能为空");
        return;
    }

    $.ajax({
        url: "/employee/insert",
        type: 'POST',
        data: JSON.stringify(formData),
        async: true,
        cache: false,
        contentType: "application/json",
        success: function (r) {
            if (r.code === 0) {
                successthen("保存成功，请联系EHS主管-陆恺",null,"/employee/sheet")//"/createDemand/create");
            }
        },
        error: function () {
            info('服务错误请刷新后重新提交！');
        }
    })
};


Sheet.reset = function () {
    window.location.href = "/employee/sheet";
}

$(function() {

});
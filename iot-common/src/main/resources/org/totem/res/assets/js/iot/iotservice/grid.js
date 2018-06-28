jQuery.iotServiceGrid = {
    column:[
        {"data": "serviceId","sWidth":"1em", "orderable":false,"createdCell": function (nTd, sData, oData, iRow, iCol) {
            var checked = $("#iotServiceChecked").val();
            var actionType = $("#actionType").val()||'checkbox';
            if (($("#_tab").length>0&&$("#_tab").val()=="iotService")||actionType=="list"){
                actionType = "checkbox";
            }
            if(checked.indexOf(sData)>=0){
                $(nTd).html("<a href='javascript:;' onclick='iotServiceManage.check(\""+sData+"\")'><input name='checkbox'  type='"+actionType+"'  checked='checked' id='" + sData + "' /></a>");
            }else {
                $(nTd).html("<a href='javascript:;' onclick='iotServiceManage.check(\""+sData+"\")'><input name='checkbox'  type='"+actionType+"'  id='" + sData + "' /></a>");
            }
        }},

    ]

}

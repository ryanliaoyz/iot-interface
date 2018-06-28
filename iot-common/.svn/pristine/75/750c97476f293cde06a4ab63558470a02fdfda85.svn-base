jQuery.iotUserGrid = {
    column:[
        {"data": "iotUserId","sWidth":"1em", "orderable":false,"createdCell": function (nTd, sData, oData, iRow, iCol) {
            var checked = $("#iotUserChecked").val();
            var actionType = $("#actionType").val()||'checkbox';
            if (($("#_tab").length>0&&$("#_tab").val()=="iotUser")||actionType=="list"){
                actionType = "checkbox";
            }
            if(checked.indexOf(sData)>=0){
                $(nTd).html("<a href='javascript:;' onclick='iotUserManage.check(\""+sData+"\")'><input name='checkbox'  type='"+actionType+"'  checked='checked' id='" + sData + "' /></a>");
            }else {
                $(nTd).html("<a href='javascript:;' onclick='iotUserManage.check(\""+sData+"\")'><input name='checkbox'  type='"+actionType+"'  id='" + sData + "' /></a>");
            }
        }},
        { "data": "userValid","name": "userValid","title":"激活","sWidth":100,"render": function ( data, type, full, meta ) {
            return data =="1" ?"启用":"禁用";
        }},
        { "data": "userName","name": "userName","title":"用户名","sWidth":10,"render": function ( data, type, full, meta ) {
            if(data ){
                return data;
            }else{
                return "";
            }
        }},
        { "data": "userDisplayName","name": "userDisplayName","title":"显示名","sWidth":10,"render": function ( data, type, full, meta ) {
            if(data ){
                return data;
            }else{
                return "";
            }
        }},
        { "data": "userToken","name": "userToken","title":"密钥","sWidth":30,"render": function ( data, type, full, meta ) {
            if(data ){
                return data;
            }else{
                return "";
            }
        }},
        { "data": "userEmail","name": "userEmail","title":"邮箱","sWidth":20,"render": function ( data, type, full, meta ) {
            if(data ){
                return data;
            }else{
                return "";
            }
        }},
        { "data": "userBalance","name": "userBalance","title":"余额","sWidth":5,"render": function ( data, type, full, meta ) {
            if(data ){
                return data;
            }else{
                return "";
            }
        }},
        { "data": "userDate","name": "userDate","title":"注册日期","sWidth":10,"render": function ( data, type, full, meta ) {
            if(data ){
                return data;
            }else{
                return "";
            }
        }},

    ]

}

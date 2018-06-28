jQuery.iotDeviceGrid = {
    column:[
        {"data": "deviceId","sWidth":"1em", "orderable":false,"createdCell": function (nTd, sData, oData, iRow, iCol) {
            var checked = $("#iotDeviceChecked").val();
            var actionType = $("#actionType").val()||'checkbox';
            if (($("#_tab").length>0&&$("#_tab").val()=="iotDevice")||actionType=="list"){
                actionType = "checkbox";
            }
            if(checked.indexOf(sData)>=0){
                $(nTd).html("<a href='javascript:;' onclick='iotDeviceManage.check(\""+sData+"\")'><input name='checkbox'  type='"+actionType+"'  checked='checked' id='" + sData + "' /></a>");
            }else {
                $(nTd).html("<a href='javascript:;' onclick='iotDeviceManage.check(\""+sData+"\")'><input name='checkbox'  type='"+actionType+"'  id='" + sData + "' /></a>");
            }
        }},
        { "data": "deviceManufa","name": "deviceManufa","title":"设备厂商","sWidth":100,"render": function ( data, type, full, meta ) {
            if(data ){
                return data;
            }else{
                return "";
            }
        }},
        { "data": "deviceTypeShowLabel","name": "deviceType","title":"设备类型","sWidth":100,"render": function ( data, type, full, meta ) {
            if(data ){
                return data;
            }else{
                return "";
            }
        }},
        { "data": "deviceName","name": "deviceName","title":"设备名","sWidth":100,"render": function ( data, type, full, meta ) {
            if(data ){
                return data;
            }else{
                return "";
            }
        }},

    ]

}

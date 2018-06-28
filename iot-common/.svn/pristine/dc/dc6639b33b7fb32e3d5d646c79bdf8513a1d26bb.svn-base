function changeIotDeviceValue(parent,data){
    if ($.t_iot!=undefined&&$.t_iot.onChange!=undefined){
        if (!$.t_iot.onChange("IotDevice_"+parent,data)){
            return;
        }
    }
}
(function(window,$){
    var edit = {
        init:function(){
            //图片
            $('.viewer').viewer({url:'data-original'});

            //变量
            var actionType = $("#actionType").val();
            var id = $("#deviceId").val();
            var that = this;
            var _uri =$("#_uri").val();
            var _tab =$("#_tab").val();

            if($("#iotDevice_picker").length>0){
                uploader();
            }

            //成员新增赋值
            if(actionType=='create'){
                if($("#iotDevice_DeviceType").length>0&&$("#iotDevice_DeviceType").val()!=""&&$("#deviceType").length>0){
                    var iotDevice_DeviceType = $("#iotDevice_DeviceType").val();
                    $("#deviceType").val(iotDevice_DeviceType);
                    $("#deviceType").parent().parent().hide();
                }
                if($("#iotDevice_DeviceService").length>0&&$("#iotDevice_DeviceService").val()!=""&&$("#deviceService").length>0){
                    var iotDevice_DeviceService = $("#iotDevice_DeviceService").val();
                    $("#deviceService").val(iotDevice_DeviceService);
                    $("#deviceService").parent().parent().hide();
                }
                if($("#iotDevice_DeviceBelonging").length>0&&$("#iotDevice_DeviceBelonging").val()!=""&&$("#deviceBelonging").length>0){
                    var iotDevice_DeviceBelonging = $("#iotDevice_DeviceBelonging").val();
                    $("#deviceBelonging").val(iotDevice_DeviceBelonging);
                    $("#deviceBelonging").parent().parent().hide();
                }
            }

            //初始化控件
            if($("#iotDeviceEditForm").length>0){
                if($("#iotDeviceEditForm #deviceType").length>0){
                    $("#deviceType").select2({
                        data:$.map($.totemUtils.getTypeCode('deviceType'), function (o) {
                            return {id: o.codeValue, text: o.codeLabel}
                        }),
                        placeholder: "请选择",
                        allowClear: true
                    });
                }
                if($("#iotDeviceEditForm #deviceType").length>0){
                    $("#deviceType").on("select2:select",function(e){
                        if(e.params.data!=undefined){
                            changeIotDeviceValue("deviceType",e.params.data);
                        }
                    })
                }

                if($("#iotDeviceEditForm #deviceService").length>0){
                    $("#deviceService").select2({
                        data:$.map($.totemUtils.getTypeCode('deviceService'), function (o) {
                            return {id: o.codeValue, text: o.codeLabel}
                        }),
                        placeholder: "请选择",
                        allowClear: true
                    });
                }
                if($("#iotDeviceEditForm #deviceService").length>0){
                    $("#deviceService").on("select2:select",function(e){
                        if(e.params.data!=undefined){
                            changeIotDeviceValue("deviceService",e.params.data);
                        }
                    })
                }

            }

            //点击事件
            $("#iotDeviceeditCancleBtn").on("click",function(){
                that.cancle();
            })
            $("#iotDeviceSaveBtn").on("click",function(){
                that.save();
            })

        },

        cancle:function () {
            var url = $("#p_uri").val()||document.referrer;
            if(url.indexOf("_ss")>=0){
                window.location.href=url;
            }else if(url.indexOf("?")>=0){
                window.location.href=url+"&_ss=1";
            }else {
                window.location.href=url+"?_ss=1";
            }
        },
        save:function(){
            var that=this;
            var validresult = $("#iotDeviceEditForm").valid();
            $("#iotDeviceSaveBtn").attr('disabled', true);
            if(validresult){
                var formData = $("#iotDeviceEditForm").serializeJSON();
                $.ajax({
                    type: 'POST',
                    url: _appsite + "iot/iotdevice/update",
                    data: formData,
                    dataType: 'json',
                    async: false,
                    success: function (result) {
                        try{
                            result = eval('(' + result + ')');
                        }catch (e){}

                        if(result.result==true){
                            $.confirm({
                                title: '提示',
                                content: '保存成功',
                                buttons: {
                                    confirm: {
                                        text:"确认",
                                        action: function(){
                                            that.cancle();
                                        }
                                    }
                                }
                            });
                        }else{
                            $("#iotDeviceSaveBtn").attr('disabled', false);
                            $.alert({title : '提示',content : result.msg||'保存失败'});

                        }
                    },
                    error: function (result) {
                        $.alert({title : '提示',content : '网络异常'});
                        $("#iotDeviceSaveBtn").attr('disabled', false);
                    }
                });
            }else{
                $("#iotDeviceSaveBtn").attr('disabled', false);
            }

        },

    }

    $(function(){
        edit.init();
    });

    window.iotDeviceEdit = edit;

})(window,jQuery)
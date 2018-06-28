(function(window,$){
    $(function(){
        $("#iotDeviceEditForm").validate({
            rules:{
                deviceManufa:{
                    required:false,
                    maxlength:32,

                },
                deviceType:{
                    required:false,

                },
                deviceService:{
                    required:false,

                },
                deviceName:{
                    required:false,
                    maxlength:32,

                },
                deviceBelonging:{
                    required:false,
                    maxlength:32,

                }

            }
        });

    })
})(window,jQuery)
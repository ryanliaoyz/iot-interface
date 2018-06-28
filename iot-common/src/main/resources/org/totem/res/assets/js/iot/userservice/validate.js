(function(window,$){
    $(function(){
        $("#userServiceEditForm").validate({
            rules:{
                serviceName:{
                    required:false,
                    maxlength:32,

                },
                serviceType:{
                    required:false,
                    maxlength:32,

                },
                serviceDescription:{
                    required:false,
                    maxlength:2048,

                }

            }
        });

    })
})(window,jQuery)
function nameOnblur() {
    var userName = $("input[name='userName']").val();
    if (userName == ""){
        $("label[name='validUsernameAlert']").show();
        return false;
    }
    else {
        $("label[name='validUsernameAlert']").hide();
        return true;
    }
}
function passOnblur() {
    //var password = document.getElementsByName("userPassword").value;
    var password = $("input[name='userPassword']").val();
    var regx = /^(?=(?:[^a-z]*[a-z]){1})(?=(?:[^A-Z]*[A-Z]){1})(?=(?:\D*\d){1})(?=(?:[^!@#$%^&*)(]*[!@#$%^&*)(]){1}).{8,25}$/;
    if(!regx.test(password)){
        $("label[name='validPasswordAlert']").show();
        return false;
    }
    else{
        $("label[name='validPasswordAlert']").hide();
        return true;
    }

}

function passROnBlur(){
    var password = $("input[name='userPassword']").val();
    var passwordR = $("input[name='userPasswordR']").val();
    if (password != passwordR){
        $("label[name='unPasswordAlert']").show();
        return false;
    }
    else{
        $("label[name='unPasswordAlert']").hide();
        return true;
    }
}

$(function(){
    $("#returnLogin").on("click",function(){
        window.location.href = "/iot/login";
        return false;
    })

    $("#submitPass").on("click",function(){
        // var password = $("input[name='userPassword']").val();
        // var passwordR = $("input[name='userPasswordR']").val();
        // var regx = /^(?=(?:[^a-z]*[a-z]){1})(?=(?:[^A-Z]*[A-Z]){1})(?=(?:\D*\d){1})(?=(?:[^!@#$%^&*)(]*[!@#$%^&*)(]){1}).{8,25}$/;
        // if (password != passwordR || !regx.test(password)){
        //     return false;
        // }
        if(!(passOnblur() && passROnBlur() && nameOnblur())){
            return false;
        }
    })

    // $("#submitEmail).on("click",function(){
    //
    // })

})



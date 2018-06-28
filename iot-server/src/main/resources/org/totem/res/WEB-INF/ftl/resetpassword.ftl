<html lang="en" >

<head>
    <meta charset="UTF-8">
    <title>User Sign Up</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">

    <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>

    <link rel="stylesheet" href="/iot/assets/signup/css/style.css">


</head>

<body>


<div class="container">
    <form action="/iot/iot/resetpassword" method="post">
        <div class="row">
            <h4>E-Mail</h4>
            <div class="input-group input-group-icon">
                <input type="email" name="userEmail" placeholder="Email Adress"/>
                <div class="input-icon"><i class="fa fa-envelope"></i></div>
            </div>
        </div>


        <div class = "row">
            <button id = "submitEmail">submit</button>
            <button id = "returnLogin">return</button>
        </div>
    </form>
</div>
<script src='http://code.bianwoyou.cn/static/assets/adminlte/plugins/jQuery/jquery-2.2.3.min.js'></script>



<script  src="/iot/assets/signup/js/index.js"></script>




</body>

</html>
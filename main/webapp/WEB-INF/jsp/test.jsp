<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>渡一在线视频学习</title>

    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" crossorigin="anonymous">

</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">

    <div class="container">

        <a class="navbar-brand" href="#">渡一视频</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="#">首页 <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">课程</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">会员</a>
                </li>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">直播</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">工具</a>
                </li>

            </ul>
            <a href="#" data-toggle="modal" data-target="#loginModal" data-whatever="@fat" class="mr-1">登录</a> /
            <a href="#" data-toggle="modal" data-target="#registModal" data-whatever="@mdo" class="ml-1 mr-3">注册</a>
            <form class="form-inline my-2 my-lg-0">
                <input class="form-control mr-sm-2" type="search" placeholder="搜索视频" aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">搜索</button>
            </form>
        </div>

    </div>
</nav>


<!-- 登录对话框 -->
<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form>
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">登录</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-body">
                    <div class="form-row">
                        <div class="col-md-12 mb-3">
                            <label for="validationServer01">邮箱</label>
                            <!-- is-valid is-invalid-->
                            <input type="text" class="form-control " id="validationServer01" required>
                            <div class="valid-feedback">
                                Looks good!
                            </div>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="col-md-12 mb-3">
                            <label for="validationServer03">密码</label>
                            <input type="password" class="form-control " id="validationServer03" required>
                            <div class="invalid-feedback">
                                Please provide a valid city.
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="form-check">
                            <input class="form-check-input " type="checkbox" value="" id="invalidCheck3">
                            <label class="form-check-label">
                                自动登录
                            </label>

                            <a href="#" class="float-right">忘记密码</a>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                    <button type="submit" class="btn btn-primary">登录</button>
                </div>
                <div class="mb-3 ml-3">
                    <a href="#" data-toggle="modal" data-dismiss="modal" data-target="#registModal">还没有账号？点我注册</a>
                </div>

            </form>
        </div>


    </div>

</div>


<!-- 注册对话框 -->
<div class="modal fade" id="registModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="post" action="/register" onsubmit="return registerSubmit()">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabe">注册</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">

                    <div class="form-row">
                        <div class="col-md-12 mb-3">
                            <label for="validationEmail">邮箱</label>
                            <%-- 验证有效邮箱的正则表达式,onblur事件会在失去焦点时发送--%>
                            <input type="text" name="email" placeholder="请输入邮箱" onblur="checkEmail(this)"
                                   pattern="[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?"
                                   class="form-control " id="validationEmail" required>
                            <div class="valid-feedback" id="feedbackEmail">
                                Looks good!
                            </div>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="col-md-12 mb-3">
                            <label for="validationMobile">手机</label>
                            <%-- 11位手机号的正则表达式验证 --%>
                            <input type="text" name="mobile" placeholder="请输入手机号" pattern="1[3456789]\d{9}$"
                                   class="form-control "
                                   id="validationMobile" required>
                            <div class="valid-feedback">
                                Looks good!
                            </div>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="col-md-12 mb-3">
                            <label for="validationPassword">密码</label>
                            <input type="password" name="password" placeholder="包含数字和字母且在6-20位之间"
                                   pattern="^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$).{6,20}$" class="form-control"
                                   id="validationPassword" required>
                            <div class="invalid-feedback">
                                Please provide a valid city.
                            </div>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="col-md-12 mb-3">
                            <label for="validationVcode">验证码</label>
                            <!-- is-valid is-invalid-->
                            <div class="row">
                                <div class="col-md-7">
                                    <input type="text" name="vcode" placeholder="请输入验证码"
                                           class="form-control"
                                           id="validationVcode"
                                           maxlength="4"
                                           required>
                                    <div class="valid-feedback" id="feedbackVcode">
                                        Looks good!
                                    </div>
                                </div>
                                <div class="col-md-5"><img src="/vcode" onclick="changeVcode(this)"/></div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                    <button type="submit" class="btn btn-primary">注册</button>
                </div>

                <div class="mb-3 ml-3">
                    <a href="#" data-toggle="modal" data-dismiss="modal" data-target="#loginModal">已有账号？点我登录</a>
                </div>

            </form>
        </div>
    </div>
</div>


<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="${pageContext.request.contextPath}/static/js/jquery-3.3.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>

<script type="application/javascript">

    function checkEmail(emailNode) {
        var email = emailNode.value;
        $.ajax({
            url: "/checkEmail?email=" + email,
            success: function (result) {
                if (result.rcode == 1) {
                    // 可以注册 valid-feedback invalid-feedback
                    $("#validationEmail").addClass("is-valid");
                    $("#validationEmail").removeClass("is-invalid");
                    $("#feedbackEmail").text("邮箱可用");
                    $("#feedbackEmail").attr("class", "valid-feedback");
                } else {
                    // 不可以注册
                    $("#validationEmail").addClass("is-invalid");
                    $("#validationEmail").removeClass("is-valid");
                    $("#feedbackEmail").text("邮箱不可用！");
                    $("#feedbackEmail").attr("class", "invalid-feedback");
                }
            }
        });
    }

    function changeVcode(imgNode) {
        <!-- 增加验证码的点击事件,每点击一次换一次 -->
        imgNode.src = "/vcode?ram=" + new Date().getTime();
    }

    function registerSubmit() {
        var vcodeFlag = checkVcode();
        if (!vcodeFlag) {
            // 验证码不正确
            $("#validationVcode").removeClass("is-valid");
            $("#validationVcode").addClass("is-invalid");
            $("#feedbackVcode").text("验证码填写错误！");
            $("#feedbackVcode").attr("class", "invalid-feedback");
            return false;
        }
        // 提交注册
        return true;
    }

    function checkVcode() {
        var vcode = $("#validationVcode").val();
        var flag = false;
        $.ajax({
            url: "/checkVcode?vcode=" + vcode,
            success: function (result) {
                if (result.rcode == 1) {
                    flag = true;
                } else {
                    flag = false;
                }
            },
            // 同步请求
            // 不能用异步,因为还没等到checkEmail给结果,registerSubmit就提交了
            async: false
        });
        return flag;
    }

</script>
</body>
</html>
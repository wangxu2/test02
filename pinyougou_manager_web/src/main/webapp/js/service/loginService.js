//登陆服务
app.service('loginService',function ($http) {
    //读取登陆人的姓名
    this.getLoginName=function () {
        return $http.get('../login/user/getName.do');//跳转到登陆页面loginController后台
    }
})
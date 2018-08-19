app.controller('indexController',function ($scope,loginService) {
    //读取当前登陆人
    $scope.getLoginName = function () {
        loginService.getLoginName().success(
            function (response) {//Map返回的值loginName
                $scope.loginName = response.loginName;
            }
        )
    }
})
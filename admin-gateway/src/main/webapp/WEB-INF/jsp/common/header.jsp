<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ include  file="head.jsp"%>
<div class="row">
    <div class="col-sm-3 col-md-6 col-lg-8">
        <ul class="nav navbar-nav hidden-sm hidden-xs">
            <li><span class="navbar-text">官方QQ群:139139139</span></li>
        </ul>
    </div>
    <div class="col-sm-9 col-md-6 col-lg-4">
        <shiro:guest>
            <ul id="nonLoginBar" class="nav navbar-nav navbar-right navbar-sm hidden-sm hidden-xs">
                <li><a id="login-panel" href="/login" rel="nofollow">登录</a></li>
            </ul>
        </shiro:guest>
        <shiro:authenticated>
            <ul id="loginBar" class="nav navbar-nav navbar-right navbar-sm hidden-sm hidden-xs">
                <li><a id="nomeaming" class="btn btn-sm"> 欢迎[<shiro:principal></shiro:principal>] </a></li>
                <li><a id="logout" href="/logout" class="btn btn-sm" rel="nofollow">退出</a></li>
            </ul>
        </shiro:authenticated>
    </div>
</div>
<shiro:hasRole name="admin">
    <div class="row">
        <nav class="navbar navbar-default navbar-static-top" role="navigation">
            <div class="navbar-header">
                <a class="navbar-brand hidden-sm hidden-xs" href="/">凤凰支付</a>
                <ul class="nav navbar-nav">
                    <li class="hidden-sm hidden-xs"><a href="/channel/defaultConfig">通道缺省配置</a></li>
                    <li class="hidden-sm hidden-xs"><a href="/channel/list">通道配置</a></li>
                    <li class="hidden-sm hidden-xs"><a href="/settle/list">充值统计</a></li>
                    <li class="hidden-sm hidden-xs"><a href="/settle/query">充值查询</a></li>
                    <li class="hidden-sm hidden-xs"><a href="/settle/supple">充值补单</a></li>
                    <li class="hidden-sm hidden-xs"><a href="/register0">CP用户注册</a></li>

                    <li class="dropdown hidden-md hidden-lg">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            凤凰支付<span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="/channel/defaultConfig">通道缺省配置</a></li>
                            <li><a href="/channel/list">通道配置</a></li>
                            <li><a href="/settle/list">充值统计</a></li>
                            <li><a href="/settle/query">充值查询</a></li>
                            <li><a href="/settle/supple">充值补单</a></li>
                            <li><a href="/register0">CP用户注册</a></li>
                            <li class="divider"></li>
                            <li><a href="/logout">退出</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
</shiro:hasRole>
<shiro:hasRole name="cp">
    <div class="row">
        <nav class="navbar navbar-default navbar-static-top" role="navigation">
            <div class="navbar-header">
                <a class="navbar-brand hidden-sm hidden-xs" href="/">凤凰支付</a>
                <ul class="nav navbar-nav">
                    <li class="hidden-sm hidden-xs"><a href="/channel/view">通道配置查看</a></li>
                    <li class="hidden-sm hidden-xs"><a href="/settle/cp/list">充值统计</a></li>
                    <li class="hidden-sm hidden-xs"><a href="/settle/cp/query">充值查询</a></li>

                    <li class="dropdown hidden-md hidden-lg">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            凤凰支付<span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="/channel/view">通道配置查看</a></li>
                            <li><a href="/settle/cp/list">充值统计</a></li>
                            <li><a href="/settle/cp/query">充值查询</a></li>
                            <li class="divider"></li>
                            <li><a href="/logout">退出</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
</shiro:hasRole>


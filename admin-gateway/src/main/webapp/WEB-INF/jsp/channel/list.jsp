<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../common/header.jsp"%>
<h2>所有的cp配置总表</h2>
<div class="row">
    <div class="col-sm-3 col-md-6 col-lg-8">

    </div>
    <div class="col-sm-9 col-md-6 col-lg-4">
        <a href="/channel/config1step" class="btn btn-primary" role="button">
            添加新的CP
        </a>
    </div>
</div>
<div class="row">
    <div class="table-responsive">
        <table class="table">
            <thead>
            <tr>
                <th>cp名称</th>
                <th>短码</th>
                <th>应用秘钥</th>
                <th>交易查询接口</th>
                <th>交易通知接口</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${accounts}" var="item">
                <tr>
                    <td>${item.cpName}</td>
                    <td>${item.shortcode}</td>
                    <td>${item.appkey}</td>
                    <td>${item.searchurl}</td>
                    <td>${item.noticeurl}</td>
                    <td>${item.status}</td>
                    <td>
                        <a href="/channel/config1step/${item.id}" class="btn btn-default btn-sm" role="button">
                            编辑
                        </a>
                        <a href="/channel/config2step/${item.id}" class="btn btn-default btn-sm" role="button">
                            通道配置详情
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@include file="../common/footer.jsp"%>
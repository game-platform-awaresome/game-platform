<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../common/header.jsp"%>
<h2>缺省计费通道配置</h2>
<div class="alert alert-warning">
    <a href="#" class="close" data-dismiss="alert">
        &times;
    </a>
    <strong>警告！</strong>您在本页面做的任何操作，都会影响到所有的cp计费配置。
</div>
<div class="table-responsive">
    <table class="table">
        <thead>
        <tr>
            <th>通道名</th>
            <th>类型</th>
            <th>运营商</th>
            <th>金额</th>
            <th>金额min</th>
            <th>金额max</th>
            <th>版本</th>
            <th>排序值</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
<c:forEach items="${account.items}" var="item">
        <tr>
            <td>${item.channelName}</td>
            <td>${item.cate}</td>
            <td>${item.operator}</td>
            <td>${item.fee}</td>
            <td>${item.fee_min}</td>
            <td>${item.fee_max}</td>
            <td>${item.version}</td>
            <td>${item.sortcode}</td>
            <td>${item.status}</td>
            <td>
                <c:if test="${item.status eq 'ok' or item.status eq 'OK'}">
                    <a href="/channel/defaultConfig/statua/${item.channel}" class="btn btn-default btn-sm" role="button">
                        停用
                    </a>
                </c:if>
                <c:if test="${item.status ne 'ok' and item.status ne 'OK'}">
                    <a href="/channel/defaultConfig/statua/${item.channel}" class="btn btn-default btn-sm" role="button">
                        启用
                    </a>
                </c:if>
                <a href="/channel/defaultConfig/sortcode/${item.channel}/1" class="btn btn-default btn-sm" role="button">
                    排序上升
                </a>
                <a href="/channel/defaultConfig/sortcode/${item.channel}/-1" class="btn btn-default btn-sm" role="button">
                    排序下降
                </a>
            </td>
        </tr>
</c:forEach>
        </tbody>
    </table>
</div>


<%@include file="../common/footer.jsp"%>
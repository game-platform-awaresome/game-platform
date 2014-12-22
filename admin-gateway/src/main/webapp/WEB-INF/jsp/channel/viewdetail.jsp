<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../common/header.jsp"%>
<h2>计费通道配置信息</h2>
<div class="alert alert-info alert-dismissable">
    <button type="button" class="close" data-dismiss="alert"
            aria-hidden="true">
        &times;
    </button>
    CP：${account.cpName} 通道配置详情
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
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>


<%@include file="../common/footer.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="../common/header.jsp"%>

<h4>CP计费统计信息查询</h4>
<div class="row">

    <div class="col-sm-8 col-md-4 col-lg-3">
    <form  role="form" action="/settle/cp/list">
        <input value="q" type="hidden" name="op">
        <div class="input-group form-group">
            <span class="input-group-addon">通道</span>
            <select class="form-control" name="channel" >
                <option value="all_multi" <c:if test="${selectedChannel eq 'all_multi'}"> selected="selected" </c:if> >按通道统计所有</option>
                <c:forEach items="${channels}" var="channelEntry">
                    <option value="${channelEntry.key}" <c:if test="${selectedChannel eq channelEntry.key}"> selected="selected" </c:if>>${channelEntry.value}</option>
                </c:forEach>
            </select>
        </div>
        <div class="input-group form-group">
            <span class="input-group-addon">开始时间</span>
            <input type="text" name="beginDate" class="form-control" placeholder="开始时间"  value="${beginDate}">
        </div>
        <div class="input-group form-group">
            <span class="input-group-addon">结束时间</span>
            <input type="text" name="endDate" class="form-control" placeholder="结束时间" value="${endDate}">
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-block btn-primary">查询</button>
        </div>
    </form>
        </div>

        <div class="col-sm-4 col-md-8 col-lg-9">
        </div>
</div>
<c:if test="${not empty op}">

<div class="table-responsive">
    <table class="table">
        <thead>
        <tr>
            <c:if test="${selectedChannel eq 'all_multi'}">
                <th>通道</th>
            </c:if>
            <c:if test="${selectedChannel ne 'all_multi'}">
                <th>日期</th>
            </c:if>
            <th>订单总数</th>
            <th>总金额</th>
            <th>未计费数</th>
            <th>未计费金额</th>
            <th>已计费未通知cp数</th>
            <th>已计费未通知cp金额</th>
            <th>已计费已通知cp数</th>
            <th>已计费已通知cp金额</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${settles}" var="item">
            <tr>
                <td>${item.statisticTarget}</td>
                <td>${item.s_count}</td>
                <td>${item.s_sum}</td>
                <td>${item.s1_count}</td>
                <td>${item.s1_sum}</td>
                <td>${item.s4_count}</td>
                <td>${item.s4_sum}</td>
                <td>${item.s6_count}</td>
                <td>${item.s6_sum}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</c:if>
<%@include file="../common/footer.jsp"%>
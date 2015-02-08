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
                <c:if test="${selectedChannel eq 'all_multi'}">
                    <option value="all_multi"  selected="selected">统计所有通道</option>
                </c:if>
                <c:if test="${selectedChannel ne 'all_multi'}">
                    <option value="all_multi">所有通道统计</option>
                </c:if>
                <c:forEach items="${channels}" var="channelEntry">
                    <c:if test="${selectedChannel eq channelEntry.key}">
                        <option value="${channelEntry.key}" selected="selected">${channelEntry.value}</option>
                    </c:if>
                    <c:if test="${selectedChannel ne channelEntry.key}">
                        <option value="${channelEntry.key}">${channelEntry.value}</option>
                    </c:if>
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

<c:if test="${empty settles and not empty op}">
    <div class="alert alert-danger">
        <h4>结果为空</h4>
    </div>
</c:if>

<c:if test="${not empty settles}">

<div class="table-responsive">
    <table class="table">
        <thead>
        <tr>
            <th>日期</th>
            <th>活跃数</th>
            <!--
            <th>订单总数</th>
            <th>总金额</th>
            <th>未计费数</th>
            <th>未计费金额</th>
            <th>已计费未通知cp数</th>
            <th>已计费未通知cp金额</th>
            -->
            <th>计费数</th>
            <th>计费金额</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${settles}" var="item">
            <tr>
                <td>${item.statisticTarget}</td>
                <td>${item.ac_count}</td>
                <td>${item.s6_count}</td>
                <td>${item.s6_sum}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</c:if>
<%@include file="../common/footer.jsp"%>
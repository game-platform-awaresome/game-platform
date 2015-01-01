<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="../common/header.jsp"%>

<h4>订单查询</h4>
<div class="row">
    <div class="col-sm-8 col-md-4 col-lg-3">
        <form  role="form" action="/recharge/query/order">
            <input value="q" type="hidden" name="op">
            <div class="input-group form-group">
                <span class="input-group-addon">手机号</span>
                <input type="text" name="mobile" class="form-control" placeholder="手机号"  value="${mobile}">
            </div>
            <div class="input-group form-group">
                <span class="input-group-addon">CP订单号</span>
                <input type="text" name="orderno" class="form-control" placeholder="CP订单号"  value="${orderno}">
            </div>
            <div class="input-group form-group">
                <span class="input-group-addon">流水号</span>
                <input type="text" name="id" class="form-control" placeholder="流水号"  value="${id}">
            </div>
            <div class="input-group form-group">
                <span class="input-group-addon">开始时间</span>
                <input type="text" name="begindate" class="form-control" placeholder="开始时间"  value="${begindate}">
            </div>
            <div class="input-group form-group">
                <span class="input-group-addon">结束时间</span>
                <input type="text" name="enddate" class="form-control" placeholder="结束时间" value="${enddate}">
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-block btn-primary">查询</button>
            </div>
        </form>
    </div>
    <div class="col-sm-4 col-md-8 col-lg-9">
    </div>
</div>

<c:if test="${empty orders and not empty op}">
    <div class="alert alert-danger">
        <h4>结果为空</h4>
    </div>
</c:if>

<c:if test="${not empty orders}">
    <div class="table-responsive">
        <table class="table">
            <thead>
            <tr>
                <th>CP短码</th>
                <th>通道号</th>
                <th>CP订单号</th>
                <th>流水号</th>
                <th>时间</th>
                <th>金额</th>
                <th>手机号</th>
                <th>状态</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${orders}" var="item">
                <tr>
                    <td>${item.shortcode}</td>
                    <td>${item.channel}</td>
                    <td>${item.orderno}</td>
                    <td>${item.id}</td>
                    <td>${item.createdDate}</td>
                    <td>${item.fee}</td>
                    <td>${item.mobile}</td>
                    <td>
                    <c:choose>
                        <c:when test="${item.state == 1}">
                            未扣费
                        </c:when>
                        <c:when test="${item.state == 2}">
                            扣费失败
                        </c:when>
                        <c:when test="${item.state == 3}">
                            扣费未成功
                        </c:when>
                        <c:when test="${item.state == 4}">
                            扣费成功
                        </c:when>
                        <c:when test="${item.state == 5}">
                            扣费成功，通知CP失败
                        </c:when>
                        <c:otherwise>
                            扣费成功，通知CP成功
                        </c:otherwise>
                    </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>
<%@include file="../common/footer.jsp"%>
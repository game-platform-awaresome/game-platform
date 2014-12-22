<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="../common/header.jsp"%>

<h4>补单</h4>
<div class="row">
    <div class="col-sm-8 col-md-4 col-lg-3">
        <form  role="form" action="/settle/cp/list">
            <div class="input-group form-group">
                <span class="input-group-addon">流水号</span>
                <input type="text" name="id" class="form-control" placeholder="流水号">
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-block btn-primary">提交</button>
            </div>
        </form>
        <div class="alert alert-info alert-dismissable">
            <button type="button" class="close" data-dismiss="alert"
                    aria-hidden="true">
                &times;
            </button>
            <c:if test="${not empty msg}">
            处理结果：${msg}
            </c:if>
        </div>
    </div>

    <div class="col-sm-4 col-md-8 col-lg-9">
    </div>
</div>
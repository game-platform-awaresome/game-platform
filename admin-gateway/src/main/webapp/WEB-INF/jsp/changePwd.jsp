<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/header.jsp"%>
<div class="row">
    <div class="col-sm-4 col-md-8 col-lg-9">
    </div>
    <div class="col-sm-8 col-md-4 col-lg-3">
        <h1 style="text-align: center">
            修改密码
        </h1>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">
                <h4>${error}</h4>
            </div>
        </c:if>
        <form  role="form" method="post" action="/changepwd/<shiro:principal/>">
            <div class="input-group form-group input-group-lg">
                <span class="glyphicon glyphicon-lock input-group-addon"></span>
                <input type="password" name="oldPwd" class="form-control" placeholder="老密码">
                <span class="input-group-addon"></span>
            </div>
            <div class="input-group form-group input-group-lg">
                <span class="glyphicon glyphicon-lock input-group-addon"></span>
                <input type="password" name="pwd" class="form-control" placeholder="新密码">
                <span class="input-group-addon"></span>
            </div>
            <div class="input-group form-group input-group-lg">
                <span class="glyphicon glyphicon-lock input-group-addon"></span>
                <input type="password" name="rePwd" class="form-control" placeholder="确认密码">
                <span class="input-group-addon"></span>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-block btn-primary">提交</button>
            </div>
        </form>
    </div>
</div>
<%@include file="common/footer.jsp"%>
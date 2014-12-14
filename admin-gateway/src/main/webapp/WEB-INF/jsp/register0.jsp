<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/header.jsp"%>
<div class="row">
    <div class="col-sm-4 col-md-8 col-lg-9">
    </div>
    <div class="col-sm-8 col-md-4 col-lg-3">
        <h1 style="text-align: center">
            输入短码/KEY
        </h1>
        <form  role="form" method="post" action="/register0">
            <div class="input-group form-group input-group-lg">
                <span class="glyphicon glyphicon-user input-group-addon"></span>
                <input type="text" name="shortCode" class="form-control" placeholder="分配的CP短码">
                <span class="input-group-addon"></span>
            </div>
            <div class="input-group form-group input-group-lg">
                <span class="glyphicon glyphicon-lock input-group-addon"></span>
                <input type="password" name="key" class="form-control" placeholder="分配的key">
                <span class="input-group-addon"></span>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-block btn-primary">下一步</button>
            </div>
        </form>
    </div>
</div>


<%@include file="common/footer.jsp"%>
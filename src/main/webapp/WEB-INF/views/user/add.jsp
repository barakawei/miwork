<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../common/taglib.jsp" %>
<h2 class="contentTitle">用户</h2>

<form action="${ctx}/user/create?navTabId=userList" method="post" class="pageForm required-validate"
      onsubmit="return validateCallback(this, navTabAjaxDone)">
    <div class="pageContent">
        <div class="pageFormContent" layoutH="97">
            <div>
                <p>
                    <label>账号</label>
                    <input type="text" name="account" maxlength="20" class="required"/>
                </p>
                <dl>
                    <dt>密码</dt>
                    <dd>
                        <input type="password" id="password" name="password" minlength="6" maxlength="20"
                               class="required alphanumeric"/>
                    </dd>
                </dl>
                <dl>
                    <dt>密码确认</dt>
                    <dd>
                        <input type="password" name="passwordConfirm" equalto="#password" class="required"/>
                    </dd>
                </dl>

                <dl>
                    <dt>姓名</dt>
                    <dd>
                        <input type="text" name="name" maxlength="20" class="required"/>
                    </dd>
                </dl>

                <dl>
                    <dt>角色</dt>
                    <dd>
                        <select class="combox required" name="roles[0].id">
                            <option value="">请选择</option>
                            <c:forEach items="${roles}" var="role">
                                <option value="${role.id}">${role.description}</option>
                            </c:forEach>
                        </select>
                    </dd>
                </dl>

                <dl>
                    <dt>邮箱</dt>
                    <dd>
                        <input type="text" name="email" class="email" maxlength="50"/>
                    </dd>
                </dl>

                <dl>
                    <dt>电话</dt>
                    <dd>
                        <input type="text" name=" telephone" maxlength="50" class="phone"/>

                    </dd>
                </dl>

                <dl>
                    <dt>手机</dt>
                    <dd>
                        <input type="text" name="phone" maxlength="50" class="phone"/>
                    </dd>
                </dl>
            </div>
        </div>

        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit">添加</button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="button">
                        <div class="buttonContent">
                            <button class="close" type="button">取消</button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</form>

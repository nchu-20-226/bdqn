<div class="page-header">
    <div class="header-banner">
        <img src="/images/header.png" alt="CoolMeeting"/>
    </div>
    <div class="header-title">
        欢迎访问Cool-Meeting会议管理系统
    </div>
    <div class="header-quicklink">
        欢迎您，
        <#if current??>
            <strong>${current.employeename}</strong>
            <a href="/doLogout">[退出登录]</a>
        </#if>

        <a href="changepassword.html">[修改密码]</a>
    </div>
</div>
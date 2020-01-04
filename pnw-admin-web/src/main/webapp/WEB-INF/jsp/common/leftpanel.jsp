<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<div class="leftpanel">
  <div class="logopanel">
      <h1><span>[</span> PNW-ADMIN <span>]</span></h1>
  </div><!-- logopanel -->

  <div class="leftpanelinner">

      <!-- 页面变小后显示用户功能,默认隐藏 -->
      <div class="visible-xs hidden-sm hidden-md hidden-lg">
          <div class="media userlogged">
              <img alt="" src="${pageContext.request.contextPath }/images/photos/loggeduser.png" class="media-object">
              <div class="media-body">
                  <h4>管理员姓名</h4>
                  <span>"备注信息"</span>
              </div>
          </div>

          <h5 class="sidebartitle actitle">Account</h5>
          <ul class="nav nav-pills nav-stacked nav-bracket mb30">
            <li><a href="profile.html"><i class="fa fa-user"></i> <span>个人信息</span></a></li>
            <li><a href=""><i class="fa fa-cog"></i> <span>系统设置</span></a></li>
            <li><a href=""><i class="fa fa-question-circle"></i> <span>帮助</span></a></li>
            <li><a href="signout.html"><i class="fa fa-sign-out"></i> <span>登出</span></a></li>
          </ul>
      </div>
	<!--功能面板-->
    <h5 class="sidebartitle">Navigation</h5>
    <ul class="nav nav-pills nav-stacked nav-bracket">
      <li class="active"><a href="index.html"><i class="fa fa-home"></i> <span>主面板</span></a></li>
      <li><a href="email.html"><span class="pull-right badge badge-success">2</span><i class="fa fa-envelope-o"></i> <span>Email</span></a></li>
      <li class="nav-parent"><a href=""><i class="fa fa-pied-piper"></i> <span>停车场</span></a>
        <ul class="children">
          <li><a href="${pageContext.request.contextPath }/park/view"><i class="fa fa-caret-right"></i> 信息总览 </a></li>
          <li><a href="${pageContext.request.contextPath }/park/list"><i class="fa fa-caret-right"></i> 停车场管理 </a></li>
          <li><a href="${pageContext.request.contextPath }/park/developing"><i class="fa fa-caret-right"></i> 开发中的停车场 </a></li>
          <li><a href="${pageContext.request.contextPath }/park/add"><i class="fa fa-caret-right"></i> 新增停车场 </a></li>
          <li><a href="${pageContext.request.contextPath }/park/info"><i class="fa fa-caret-right"></i> 停车场详情 </a></li>
          <li><a href="${pageContext.request.contextPath }/park/fix"><i class="fa fa-caret-right"></i> 维修状况 </a></li>
          <li><a href="${pageContext.request.contextPath }/park/search"><i class="fa fa-caret-right"></i> 搜索引擎</a></li>
        </ul>
      </li>
      <li class="nav-parent"><a href=""><i class="fa fa-car"></i> <span>停车位</span></a>
        <ul class="children">
          <li><a href="${pageContext.request.contextPath }/space/list"><i class="fa fa-caret-right"></i> 停车位列表</a></li>
          <li><a href="${pageContext.request.contextPath }/space/info"><span class="pull-right badge badge-danger">updated</span><i class="fa fa-caret-right"></i> 停车位详情 </a></li>
          <li><a href="typography.html"><i class="fa fa-caret-right"></i> 新增停车位</a></li>
          <li><a href="alerts.html"><i class="fa fa-caret-right"></i> 节点信息 </a></li>
          <li><a href="modals.html"><i class="fa fa-caret-right"></i> 维修状况 </a></li>
          <li><a href="tabs-accordions.html"><i class="fa fa-caret-right"></i> Tabs &amp; Accordions</a></li>
          <li><a href="sliders.html"><i class="fa fa-caret-right"></i> Sliders</a></li>
          <li><a href="graphs.html"><i class="fa fa-caret-right"></i> Graphs &amp; Charts</a></li>
          <li><a href="widgets.html"><i class="fa fa-caret-right"></i> Panels &amp; Widgets</a></li>
          <li><a href="extras.html"><i class="fa fa-caret-right"></i> Extras</a></li>
        </ul>
      </li>
      <li class="nav-parent"><a href="tables.html"><i class="fa fa-th-list"></i> <span>订单管理</span></a>
      	 <ul class="children">
              <li><a href="bug-tracker.html"><i class="fa fa-caret-right"></i> thing</a></li>
              <li><a href="bug-issues.html"><i class="fa fa-caret-right"></i> Issues</a></li>
              <li><a href="view-issue.html"><i class="fa fa-caret-right"></i> View Issue</a></li>
          </ul>
      </li>
      <li class="nav-parent"><a href=""><i class="fa fa-bug"></i> <span>用户管理</span></a>
          <ul class="children">
              <li><a href="bug-tracker.html"><i class="fa fa-caret-right"></i> Summary</a></li>
              <li><a href="bug-issues.html"><i class="fa fa-caret-right"></i> Issues</a></li>
              <li><a href="view-issue.html"><i class="fa fa-caret-right"></i> View Issue</a></li>
          </ul>
      </li>
      <li><a href="${pageContext.request.contextPath }/map"><i class="fa fa-map-marker"></i> <span>Maps</span></a></li>
      <li class="nav-parent"><a href=""><i class="fa fa-file-text"></i> <span>负责人</span></a>
        <ul class="children">
          <li><a href="calendar.html"><i class="fa fa-caret-right"></i> Calendar</a></li>
          <li><a href="media-manager.html"><i class="fa fa-caret-right"></i> Media Manager</a></li>
          <li><a href="timeline.html"><i class="fa fa-caret-right"></i> Timeline</a></li>
          <li><a href="blog-list.html"><i class="fa fa-caret-right"></i> Blog List</a></li>
          <li><a href="blog-single.html"><i class="fa fa-caret-right"></i> Blog Single</a></li>
          <li><a href="people-directory.html"><i class="fa fa-caret-right"></i> People Directory</a></li>
          <li><a href="profile.html"><i class="fa fa-caret-right"></i> Profile</a></li>
          <li><a href="invoice.html"><i class="fa fa-caret-right"></i> Invoice</a></li>
          <li><a href="search-results.html"><i class="fa fa-caret-right"></i> Search Results</a></li>
          <li><a href="blank.html"><i class="fa fa-caret-right"></i> Blank Page</a></li>
          <li><a href="notfound.html"><i class="fa fa-caret-right"></i> 404 Page</a></li>
          <li><a href="locked.html"><i class="fa fa-caret-right"></i> Locked Screen</a></li>
          <li><a href="signin.html"><i class="fa fa-caret-right"></i> Sign In</a></li>
          <li><a href="signup.html"><i class="fa fa-caret-right"></i> Sign Up</a></li>
        </ul>
      </li>
      <li class="nav-parent"><a href="javaScript:void(0)"><i class="fa fa-laptop"></i> <span>搜索引擎</span></a>
          <ul class="children">
              <li><a href="layouts.html"><i class="fa fa-caret-right"></i> General Layouts</a></li>
              <li><a href="horizontal-menu.html"><i class="fa fa-caret-right"></i> Top Menu</a></li>
              <li><a href="horizontal-menu2.html"><i class="fa fa-caret-right"></i> Top Menu w/ Sidebar</a></li>
              <li><a href="fixed-width.html"><i class="fa fa-caret-right"></i> Fixed Width Page</a></li>
              <li><a href="${pageContext.request.contextPath }/search/manager"><i class="fa fa-caret-right"></i> 生成索引库</a></li>
          </ul>
      </li>
    </ul>

    <div class="infosummary">
      <h5 class="sidebartitle">信息总汇</h5>
      <ul>
          <li>
              <div class="datainfo">
                  <span class="text-muted">Daily Traffic</span>
                  <h4>630, 201</h4>
              </div>
              <div id="sidebar-chart" class="chart"></div>
          </li>
          <li>
              <div class="datainfo">
                  <span class="text-muted">Average Users</span>
                  <h4>1, 332, 801</h4>
              </div>
              <div id="sidebar-chart2" class="chart"></div>
          </li>
          <li>
              <div class="datainfo">
                  <span class="text-muted">Disk Usage</span>
                  <h4>82.2%</h4>
              </div>
              <div id="sidebar-chart3" class="chart"></div>
          </li>
          <li>
              <div class="datainfo">
                  <span class="text-muted">CPU Usage</span>
                  <h4>140.05 - 32</h4>
              </div>
              <div id="sidebar-chart4" class="chart"></div>
          </li>
          <li>
              <div class="datainfo">
                  <span class="text-muted">Memory Usage</span>
                  <h4>32.2%</h4>
              </div>
              <div id="sidebar-chart5" class="chart"></div>
          </li>
      </ul>
    </div><!-- infosummary -->

  </div><!-- leftpanelinner -->
</div><!-- leftpanel -->
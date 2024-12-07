<template>
  <div class="chat-layout">
    <div class="main-content">
      <!-- 顶部导航 -->
      <div class="nav-tabs">
        <div
            v-for="(tab, index) in tabs"
            :key="index"
            :class="['tab-item', { active: currentTab === index }]"
            @click="switchTab(index)"
        >
          {{ tab.name }}
        </div>
      </div>

      <!-- 内容区域 -->
      <div class="content-area">
        <!-- 招生政策咨询 -->
        <div v-if="currentTab === 0" class="policy-content">
          <div class="section">
            <h3>招生政策</h3>
            <div class="policy-items">
              <div class="policy-item" @click="showInfo('admission_plan')">
                <span>📋 2024招生计划</span>
              </div>
              <div class="policy-item" @click="showInfo('admission_requirements')">
                <span>📝 报考要求</span>
              </div>
              <div class="policy-item" @click="showInfo('admission_rules')">
                <span>📊 录取规则</span>
              </div>
            </div>
          </div>

          <div class="section">
            <h3>特殊类型招生</h3>
            <div class="policy-items">
              <div class="policy-item" @click="showInfo('talent_admission')">
                <span>🎯 特长生招生</span>
              </div>
              <div class="policy-item" @click="showInfo('art_admission')">
                <span>🎨 艺术类招生</span>
              </div>
              <div class="policy-item" @click="showInfo('sports_admission')">
                <span>⚽ 体育类招生</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 专业信息查询 -->
        <div v-if="currentTab === 1" class="major-content">
          <div class="section">
            <h3>重点专业</h3>
            <div class="major-items">
              <div class="major-item" @click="showInfo('normal_major')">
                <span>🎓 师范专业</span>
              </div>
              <div class="major-item" @click="showInfo('computer_major')">
                <span>💻 计算机类</span>
              </div>
              <div class="major-item" @click="showInfo('language_major')">
                <span>🌏 外语类</span>
              </div>
            </div>
          </div>

          <div class="section">
            <h3>专业详情</h3>
            <div class="major-items">
              <div class="major-item" @click="showInfo('course_info')">
                <span>📚 课程设置</span>
              </div>
              <div class="major-item" @click="showInfo('practice_info')">
                <span>🔬 实习实践</span>
              </div>
              <div class="major-item" @click="showInfo('career_info')">
                <span>💼 就业前景</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 历年数据分析 -->
        <div v-if="currentTab === 2" class="data-content">
          <div class="section">
            <h3>分数线查询</h3>
            <div class="data-items">
              <div class="data-item" @click="showInfo('score_2023')">
                <span>📈 2023分数线</span>
              </div>
              <div class="data-item" @click="showInfo('score_trend')">
                <span>📊 分数趋势</span>
              </div>
              <div class="data-item" @click="showInfo('score_province')">
                <span>🗺️ 分省分数</span>
              </div>
            </div>
          </div>

          <div class="section">
            <h3>就业数据</h3>
            <div class="data-items">
              <div class="data-item" @click="showInfo('employment_rate')">
                <span>📊 就业率</span>
              </div>
              <div class="data-item" @click="showInfo('salary_info')">
                <span>💰 薪资水平</span>
              </div>
              <div class="data-item" @click="showInfo('career_direction')">
                <span>🎯 就业去向</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Coze聊天容器 -->
      <div id="coze-chat-container"></div>

      <!-- 返回按钮 -->
      <button @click="goHome" class="home-button">返回首页</button>

      <!-- 添加信息弹窗 -->
      <div v-if="showDialog" class="dialog-overlay" @click="closeDialog">
        <div class="dialog-content" @click.stop>
          <div class="dialog-header">
            <h3>{{ dialogTitle }}</h3>
            <span class="close-btn" @click="closeDialog">×</span>
          </div>
          <div class="dialog-body">
            <div v-html="dialogContent"></div>
          </div>
          <div class="dialog-footer">
            <button @click="askQuestionFromDialog" class="ask-btn">询问详情</button>
            <button @click="closeDialog" class="close-btn">关闭</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ChatPage',
  data() {
    return {
      currentTab: 0,
      cozeSDK: null,
      tabs: [
        { name: '招生政策咨询' },
        { name: '专业信息查询' },
        { name: '历年数据分析' }
      ],
      showDialog: false,
      dialogTitle: '',
      dialogContent: '',
      currentQuestion: '',
      infoMap: {
        'admission_plan': {
          title: '2024招生计划',
          content: `
            <h4>2024年招生计划</h4>
            <p>1. 招生规模</p>
            <ul>
              <li>本科计划总数：5000人</li>
              <li>其中师范类专业：2500人</li>
              <li>非师范类专业：2500人</li>
            </ul>
            <p>2. 专业分布</p>
            <ul>
              <li>文学院：500人</li>
              <li>理学院：600人</li>
              <li>教育学院：800人</li>
              <li>计算机学院：400人</li>
              <li>外国语学院：300人</li>
              <li>其他学院：2400人</li>
            </ul>
            <p>3. 特殊类型招生</p>
            <ul>
              <li>艺术类：200人</li>
              <li>体育类：100人</li>
              <li>国家专项：300人</li>
            </ul>
          `
        },
        'admission_requirements': {
          title: '报考要求',
          content: `
            <h4>基本报考要求</h4>
            <p>1. 报考条件</p>
            <ul>
              <li>参加2024年全国普通高等学校招生考的应届、往届高中毕业生</li>
              <li>政治思想品德合格，体检符合相关标准</li>
              <li>外语语种不限</li>
            </ul>
            <p>2. 分数要求</p>
            <ul>
              <li>普通类专业：一批次分数线以上</li>
              <li>师范类专业：一批次分数线以上，且语文成绩不低于110分</li>
              <li>艺术类专业：专业课合格，文化课达到相应要求</li>
            </ul>
            <p>3. 特殊要求</p>
            <ul>
              <li>师范类专业：身心健康，五官端正，语言清晰</li>
              <li>艺术类专业：须参加省级专业统考并合格</li>
              <li>体育类专业：须参加体育专业测试</li>
            </ul>
          `
        },
        'special_admission': {
          title: '特殊类型招生',
          content: `
            <h4>特殊类型招生说明</h4>
            <p>1. 艺术类专业</p>
            <ul>
              <li>美术学：需参加省统考</li>
              <li>音乐学：需参加专业考试</li>
              <li>舞蹈学：需参加专业考试</li>
            </ul>
            <p>2. 体育类专业</p>
            <ul>
              <li>体育教育：参加体育专业测试</li>
              <li>运动训练：需有运动员等级证书</li>
            </ul>
            <p>3. 其他特殊类型</p>
            <ul>
              <li>高水平运动队：需有相关竞赛获奖证明</li>
              <li>高水平艺术团：需参加专门测试</li>
            </ul>
          `
        },
        'admission_rules': {
          title: '录取规则',
          content: `
            <h4>录取规则说明</h4>
            <p>1. 按照"分数优先，遵循志愿"的原则进行录取</p>
            <p>2. 录取批次：</p>
            <ul>
              <li>提前批：艺术类、体育类专业</li>
              <li>普通批：文理科各专业</li>
            </ul>
            <p>3. 录取程序：</p>
            <ul>
              <li>第一步：确定考生投档成绩</li>
              <li>第二步：按专业志愿顺序安排专业</li>
              <li>第三步：发放录取通知书</li>
            </ul>
          `
        },
        'major_info': {
          title: '专业介绍',
          content: `
            <h4>重点专业介绍</h4>
            <p>1. 师范类专业</p>
            <ul>
              <li>教育学：国家级一流本科专业</li>
              <li>学前教育：省级重点专业</li>
              <li>特殊教育：特色专业</li>
            </ul>
            <p>2. 计算机类专业</p>
            <ul>
              <li>计算机科学与技术：省级一流专业</li>
              <li>软件工程：新工科建设专业</li>
            </ul>
          `
        },
        'score_info': {
          title: '分数线信息',
          content: `
            <h4>2023年分数线</h4>
            <p>1. 文科</p>
            <ul>
              <li>普通类：一批次 545分</li>
              <li>师范类：一批次 538分</li>
            </ul>
            <p>2. 理科</p>
            <ul>
              <li>普通类：一批次 535分</li>
              <li>师范类：一批次 528分</li>
            </ul>
          `
        },
        'talent_admission': {
          title: '特长生招生',
          content: `
            <h4>特长生招生项目</h4>
            <p>1. 高水平运动队</p>
            <ul>
              <li>篮球：招收前锋、中锋、后卫各2名</li>
              <li>田径：短跑、跳远、铅球项目</li>
              <li>游泳：自由泳  蛙泳项目</li>
            </ul>
            <p>2. 艺术特长生</p>
            <ul>
              <li>音乐：声乐、钢琴、小提琴等</li>
              <li>美术：国画、油画、设计等</li>
              <li>舞蹈：民族舞、现代舞等</li>
            </ul>
          `
        },
        'art_admission': {
          title: '艺术类招生',
          content: `
            <h4>艺术类专业招生要求</h4>
            <p>1. 招生专业</p>
            <ul>
              <li>美术学：国画、油画方向</li>
              <li>音乐表演：声乐、器乐方向</li>
              <li>舞蹈表演：民族舞、现代舞方向</li>
            </ul>
            <p>2. 考试要求</p>
            <ul>
              <li>须参加省级统考并达到合格线</li>
              <li>参加校考的专业需参加学校组织的专业测试</li>
              <li>文化课成绩要求：不低于艺术类本科线</li>
            </ul>
          `
        },
        'score_2023': {
          title: '2023年分数线',
          content: `
            <h4>2023年各专业分数线</h4>
            <p>1. 文科类</p>
            <ul>
              <li>汉语言文学：558分</li>
              <li>英语：552分</li>
              <li>历史学：545分</li>
            </ul>
            <p>2. 理科类</p>
            <ul>
              <li>计算机科学：562分</li>
              <li>数学与应用数学：548分</li>
              <li>物理学：542分</li>
            </ul>
          `
        },
        'employment_rate': {
          title: '就业率统计',
          content: `
            <h4>就业率详情</h4>
            <p>1. 整体就业率</p>
            <ul>
              <li>2023届：95.6%</li>
              <li>2022届：94.8%</li>
              <li>2021届：93.5%</li>
            </ul>
            <p>2. 专业就业率</p>
            <ul>
              <li>师范类：98.2%</li>
              <li>工科类：96.5%</li>
              <li>文科类：93.8%</li>
            </ul>
          `
        },
        'sports_admission': {
          title: '体育类招生',
          content: `
            <h4>体育类专业招生政策</h4>
            <p>1. 招生专业</p>
            <ul>
              <li>体育教育：师范类专业</li>
              <li>运动训练：竞技体育方向</li>
              <li>社会体育：体育产业方向</li>
            </ul>
            <p>2. 考试要求</p>
            <ul>
              <li>专业测试：体能测试、专项技能测试</li>
              <li>文化课要求：达到体育类本科分数线</li>
              <li>身体条件：符合体育专业身体素质要求</li>
            </ul>
            <p>3. 录取办法</p>
            <ul>
              <li>专业成绩占比：60%</li>
              <li>文化课成绩占比：40%</li>
              <li>  合分排名择优录取</li>
            </ul>
            <p>4. 特殊政策</p>
            <ul>
              <li>省级以上运动员可享受降分录取</li>
              <li>国家级运动员可优先录取</li>
              <li>提供专业训练设施和场地</li>
            </ul>
          `
        },
        'normal_major': {
          title: '师范专业',
          content: `
            <h4>师范类专业优势</h4>
            <p>1. 重点专业设置</p>
            <ul>
              <li>教育学：国家级一流本科专业建设点</li>
              <li>学前教育：省级特色专业</li>
              <li>小学教育：省级重点专业</li>
              <li>心理学：特色师范专业</li>
            </ul>
            <p>2. 培养特色</p>
            <ul>
              <li>师范生公费教育政策</li>
              <li>教育实践基地合作</li>
              <li>双导师培养模式</li>
              <li>教师资格证考试辅导</li>
            </ul>
            <p>3. 就业优势</p>
            <ul>
              <li>省内重点中小学优先选聘</li>
              <li>教师编制专项计划</li>
              <li>就业率保持在95%以上</li>
              <li>升学深造机会多</li>
            </ul>
          `
        },
        'computer_major': {
          title: '计算机类专业',
          content: `
            <h4>计算机类专业情况</h4>
            <p>1. 专业方向</p>
            <ul>
              <li>计算机科学与技术：人工智能应用方向</li>
              <li>软件工程：大数据与云计算方向</li>
              <li>物联网工程：智能物联网方向</li>
              <li>网络工程：网络安全方向</li>
            </ul>
            <p>2. 实验条件</p>
            <ul>
              <li>省级实验教学示范中心</li>
              <li>人工智能创新实验室</li>
              <li>校企合作实训基地</li>
              <li>ACM竞赛培训基地</li>
            </ul>
            <p>3. 就业方向</p>
            <ul>
              <li>互联网公司：BAT等知名企业</li>
              <li>软件公司：华为、中兴等</li>
              <li>科研院所：研究所、高校等</li>
              <li>创新创业：互联网创业</li>
            </ul>
          `
        },
        'language_major': {
          title: '外语类专业',
          content: `
            <h4>外语类专业实力</h4>
            <p>1. 专业设置</p>
            <ul>
              <li>英语（师范）：教育部特色专业</li>
              <li>商务英语：省级重点专业</li>
              <li>日语：新兴特色专业</li>
              <li>俄语：特色语种</li>
            </ul>
            <p>2. 培养特色</p>
            <ul>
              <li>小班化教学：25人左右</li>
              <li>外教全程参与教学</li>
              <li>国际交换生项目</li>
              <li>专业四、八级考试辅导</li>
            </ul>
            <p>3. 国际合作</p>
            <ul>
              <li>海外合作院校交换项目</li>
              <li>暑期海外研修项目</li>
              <li>国际学术交流活动</li>
              <li>中外合作办学项目</li>
            </ul>
            <p>4. 就业方向</p>
            <ul>
              <li>教育机构：中小学、培训机构</li>
              <li>外企：跨国公司、贸易公司</li>
              <li>公共部门：外事、翻译等</li>
              <li>出国深造：海外名校深造</li>
            </ul>
          `
        },
        'course_info': {
          title: '课程设置',
          content: `
            <h4>专业课程体系</h4>
            <p>1. 公共基础课程</p>
            <ul>
              <li>思想政治理论课程：思想道德修养、毛泽东思想等</li>
              <li>大学英语：综合英语、英语听说等</li>
              <li>计算机基础：计算机应用基础、程序设计等</li>
              <li>体育与健康：体育课、体质健康等</li>
            </ul>
            <p>2. 专业核心课程</p>
            <ul>
              <li>师范类：教育学、心理学、教学法等</li>
              <li>理工类：高等数学、大学物理、专业必修课等</li>
              <li>文科类：专业理论课、研究方法等</li>
              <li>艺术类：专业技能课、艺术理论等</li>
            </ul>
            <p>3. 实践教学课程</p>
            <ul>
              <li>专业实验课程：各专业实验课程</li>
              <li>实习实训课程：教育实习、专业实习等</li>
              <li>创新创业课程：创新思维、创业基础等</li>
              <li>毕业论文/设计：毕业设计指导等</li>
            </ul>
          `
        },
        'practice_info': {
          title: '实习实践',
          content: `
            <h4>实习实践安排</h4>
            <p>1. 实习类型与时间</p>
            <ul>
              <li>认知实习：1-2周，了解专业工作环境</li>
              <li>专业实习：4-6周，专业技能实践</li>
              <li>毕业实习：8-12周，岗位实践锻炼</li>
              <li>社会实践：寒暑假期间开展</li>
            </ul>
            <p>2. 实践基地</p>
            <ul>
              <li>校内实训基地：专业实验室、实训中心</li>
              <li>校外合作企业：知名企业实习基地</li>
              <li>教育实践基地：合作中小学、幼儿园</li>
              <li>创新创业基地：大学生创业园</li>
            </ul>
            <p>3. 实习保障</p>
            <ul>
              <li>双导师制度：校内导师+企业导师</li>
              <li>实习补贴：提供实习补助</li>
              <li>安全保险：购买实习保险</li>
              <li>就业推荐：优秀实习生优先录用</li>
            </ul>
          `
        },
        'career_info': {
          title: '就业前景',
          content: `
            <h4>就业前景分析</h4>
            <p>1. 就业方向</p>
            <ul>
              <li>教育系统：中小学、教育机构、培训机构</li>
              <li>企事   单位：IT企业、金融机构、外贸公司</li>
              <li>政府部门：公务员、事业单位</li>
              <li>升学深造：考研、出国留学</li>
            </ul>
            <p>2. 就业优势</p>
            <ul>
              <li>师范类专业就业率98%以上</li>
              <li>计算机类专业平均薪资8K以上</li>
              <li>外语类专业国际化就业机会多</li>
              <li>艺术类专业就业范围广</li>
            </ul>
            <p>3. 发展前景</p>
            <ul>
              <li>教师编制专项计划</li>
              <li>企业管理培训生项目</li>
              <li>创新创业扶持政策</li>
              <li>考研升学奖学金支持</li>
            </ul>
          `
        },
        'score_trend': {
          title: '分数线趋势',
          content: `
            <h4>近三年分数线趋势</h4>
            <p>1. 文科类专业</p>
            <ul>
              <li>2023年：最高558分，最低545分</li>
              <li>2022年：最高555分，最低542分</li>
              <li>2021年：最高552分，最低538分</li>
            </ul>
            <p>2. 理科类专业</p>
            <ul>
              <li>2023年：最高562分，最低542分</li>
              <li>2022年：最高558分，最低538分</li>
              <li>2021年：最高555分，最低535分</li>
            </ul>
            <p>3. 分数线变化趋势分析</p>
            <ul>
              <li>整体呈稳步上升趋势</li>
              <li>重点专业分数线涨幅明显</li>
              <li>师范类专业竞争逐年加剧</li>
            </ul>
          `
        },
        'score_province': {
          title: '分省分数线',
          content: `
            <h4>2023年各省份录取分数线</h4>
            <p>1. 东北地区</p>
            <ul>
              <li>黑龙江：文科545分，理科535分</li>
              <li>吉林：文科548分，理科538分</li>
              <li>辽宁：文科552分，理科542分</li>
            </ul>
            <p>2. 华北地区</p>
            <ul>
              <li>河北：文科558分，理科548分</li>
              <li>山东：文科562分，理科552分</li>
              <li>山西：文科550分，理科540分</li>
            </ul>
            <p>3. 其他地区</p>
            <ul>
              <li>河南：文科555分，理科545分</li>
              <li>江苏：文科565分，理科555分</li>
              <li>其他省份分数线请咨询招生办</li>
            </ul>
          `
        },
        'salary_info': {
          title: '薪资水平',
          content: `
            <h4>毕业生薪资情况</h4>
            <p>1. 应届生起薪</p>
            <ul>
              <li>师范类：6000-8000元/月</li>
              <li>计算机类：8000-12000元/月</li>
              <li>外语类：7000-10000元/月</li>
              <li>艺术类：6000-9000元/月</li>
            </ul>
            <p>2. 薪资成长</p>
            <ul>
              <li>工作3年后平均涨幅：50%</li>
              <li>工作5年后平均涨幅：100%</li>
              <li>管理岗位年薪：15-30万</li>
              <li>特殊岗位年薪：20-40万</li>
            </ul>
            <p>3. 行业分布</p>
            <ul>
              <li>教育行业：年薪8-15万</li>
              <li>IT行业：年薪12-25万</li>
              <li>金融行业：年薪15-30万</li>
              <li>公务员：年薪8-12万</li>
            </ul>
          `
        },
        'career_direction': {
          title: '就业去向',
          content: `
            <h4>毕业生就业去向分析</h4>
            <p>1. 行业分布</p>
            <ul>
              <li>教育行业：45%（中小学、培训机构）</li>
              <li>IT行业：20%（互联网公司、软件企业）</li>
              <li>企事业单位：25%（国企、机关单位）</li>
              <li>其他行业：10%（自主创业等）</li>
            </ul>
            <p>2. 地域分布</p>
            <ul>
              <li>省内就业：60%（主要在哈尔滨等城市）</li>
              <li>北上广深：25%（一线城市发展）</li>
              <li>其他地区：15%（新一线城市为主）</li>
            </ul>
            <p>3. 就业质量</p>
            <ul>
              <li>专业对口率：85%以上</li>
              <li>就业满意度：90%以上</li>
              <li>考研深造率：25%左右</li>
              <li>公务员考取率：10%左右</li>
            </ul>
          `
        }
      }
    }
  },
  methods: {
    switchTab(index) {
      this.currentTab = index
    },
    goHome() {
      this.$router.push('/')
    },
    askQuestion(question) {
      if (this.cozeSDK) {
        this.cozeSDK.sendMessage({
          content: question
        })
      }
    },
    async initCozeSDK() {
      const script = document.createElement('script')
      script.src = 'https://lf-cdn.coze.cn/obj/unpkg/flow-platform/chat-app-sdk/1.0.0-beta.4/libs/cn/index.js'
      script.onload = () => {
        this.cozeSDK = new window.CozeWebSDK.WebChatClient({
          container: '#coze-chat-container',
          config: {
            botId: '7443828604372516915'
          },
          auth: {
            type: 'oauth',
            clientId: '1131880959856',
            clientSecret: '92796182798835505796120279797203.app.coze'
          }
        })
      }
      document.head.appendChild(script)
    },
    showInfo(type) {
      const info = this.infoMap[type]
      if (info) {
        this.dialogTitle = info.title
        this.dialogContent = info.content
        this.currentQuestion = `详细介绍${info.title}的具体情况`
        this.showDialog = true
      }
    },
    closeDialog() {
      this.showDialog = false
    },
    askQuestionFromDialog() {
      this.askQuestion(this.currentQuestion)
      this.closeDialog()
    },
    handleItemClick(type) {
      this.showInfo(type)
    }
  },
  mounted() {
    this.initCozeSDK()
  },
  beforeUnmount() {
    const script = document.querySelector('script[src*="coze-chat-sdk"]')
    if (script) {
      script.remove()
    }
  }
}
</script>

<style scoped>
.chat-layout {
  height: 100vh;
  background-color: #f5f5f5;
  padding: 20px;
}

.main-content {
  max-width: 1200px;
  margin: 0 auto;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.1);
  padding: 20px;
}

.nav-tabs {
  display: flex;
  border-bottom: 1px solid #e8e8e8;
  margin-bottom: 20px;
}

.tab-item {
  padding: 12px 24px;
  cursor: pointer;
  transition: all 0.3s;
  border-bottom: 2px solid transparent;
}

.tab-item.active {
  color: #1890ff;
  border-bottom-color: #1890ff;
}

.section {
  margin-bottom: 20px;
}

.section h3 {
  margin-bottom: 16px;
  color: #333;
}

.policy-items, .major-items, .data-items {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 16px;
  margin-bottom: 20px;
}

.policy-item, .major-item, .data-item {
  padding: 16px;
  background-color: #f5f7fa;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
  text-align: center;
}

.policy-item:hover, .major-item:hover, .data-item:hover {
  background-color: #e6f7ff;
  transform: translateY(-2px);
}

#coze-chat-container {
  height: 500px;
  margin: 20px 0;
  border: 1px solid #e8e8e8;
  border-radius: 8px;
}

.home-button {
  width: 100%;
  padding: 12px;
  background-color: #1890ff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.home-button:hover {
  background-color: #40a9ff;
}

.dialog-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.dialog-content {
  background-color: white;
  border-radius: 8px;
  width: 80%;
  max-width: 600px;
  max-height: 80vh;
  display: flex;
  flex-direction: column;
}

.dialog-header {
  padding: 16px;
  border-bottom: 1px solid #e8e8e8;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.dialog-body {
  padding: 20px;
  overflow-y: auto;
  flex: 1;
}

.dialog-footer {
  padding: 16px;
  border-top: 1px solid #e8e8e8;
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

.ask-btn {
  padding: 8px 16px;
  background-color: #1890ff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.close-btn {
  padding: 8px 16px;
  background-color: #f5f5f5;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.dialog-body h4 {
  margin-bottom: 16px;
  color: #1890ff;
}

.dialog-body p {
  margin-bottom: 12px;
  line-height: 1.6;
}

.dialog-body ul {
  margin-bottom: 16px;
  padding-left: 20px;
}

.dialog-body li {
  margin-bottom: 8px;
  line-height: 1.4;
}
</style>
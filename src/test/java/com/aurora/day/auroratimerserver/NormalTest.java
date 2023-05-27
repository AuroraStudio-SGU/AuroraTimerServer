package com.aurora.day.auroratimerserver;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.aurora.day.auroratimerserver.config.TimerConfig;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class NormalTest {

    @Test
    void md5Test() {
        String str1 = "str1";
        String str2 = "a lot of testtttttttttttttttttttttttttttttttttttttttttttttttttttttttttt";
        System.out.println("md5 of str1:" + SecureUtil.md5(str1));
        System.out.println("md5 of str2:" + SecureUtil.md5(str2));
        System.out.println("length of str2:" + SecureUtil.md5(str1).length());
        System.out.println("length of str2:" + SecureUtil.md5(str2).length());
    }


    @Test
    void htmldemo() {

    }

    @Test
    void rexTest() {

    }

    static String html = "<!DOCTYPE html>\n" +
            "\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><title>\n" +
            "\t韶关学院校园网\n" +
            "</title><meta http-equiv=\"x-UA-Compatible\" content=\"IE=edge\" />\n" +
            "    <!-- 防止ie进入怪异文档模式 -->\n" +
            "\n" +
            "    <link rel=\"stylesheet\" type=\"text/css\" href=\"ncss/nav.css\" />\n" +
            "    <!-- 菜单栏css  -->\n" +
            "    <link rel=\"stylesheet\" type=\"text/css\" href=\"ncss/list1.css\" /><link rel=\"stylesheet\" type=\"text/css\" href=\"ncss/banner.css\" />\n" +
            "    <!-- 左上轮播css -->\n" +
            "    <script type=\"text/javascript\" src=\"njs/banner.js\"></script>\n" +
            "    <!-- 左上轮播js -->\n" +
            "\n" +
            "    <link rel=\"stylesheet\" type=\"text/css\" href=\"ncss/nivo.slider.css\" />\n" +
            "    <!-- 百叶窗轮播css -->\n" +
            "    <script src=\"njs/jquery-1.7.2.min.js\" type=\"text/javascript\"></script>\n" +
            "    <script type=\"text/javascript\" src=\"njs/nivo.slider.js\"></script>\n" +
            "    <!-- 百叶窗轮播js -->\n" +
            "    <script type=\"text/javascript\">\n" +
            "        jQuery(function ($) {\n" +
            "            $(window).load(function () {\n" +
            "                $('#slider').nivoSlider({\n" +
            "                    directionNav: true,\n" +
            "                    captionOpacity: 0.4,\n" +
            "                    controlNav: true,\n" +
            "                    boxCols: 8,\n" +
            "                    boxRows: 4,\n" +
            "                    slices: 15,\n" +
            "                    effect: 'random',\n" +
            "                    animSpeed: 500,\n" +
            "                    pauseTime: 3000\n" +
            "                });\n" +
            "            });\n" +
            "        });\n" +
            "    </script>\n" +
            "    <!-- 百叶窗轮播js -->\n" +
            "\n" +
            "    <link rel=\"stylesheet\" type=\"text/css\" href=\"ncss/r_content.css\" /><link rel=\"stylesheet\" type=\"text/css\" href=\"ncss/footer.css\" />\n" +
            "    <!--  脚本css样式 -->\n" +
            "    <style type=\"text/css\">\n" +
            "        .pages {\n" +
            "            color: #999;\n" +
            "            overflow: auto;\n" +
            "        }\n" +
            "\n" +
            "            .pages a, .pages .cpb {\n" +
            "                text-decoration: none;\n" +
            "                float: left;\n" +
            "                padding: 0 5px;\n" +
            "                border: 1px solid #ddd;\n" +
            "                background: #ffff;\n" +
            "                margin: 0 2px;\n" +
            "                font-size: 11px;\n" +
            "                color: #000;\n" +
            "            }\n" +
            "\n" +
            "                .pages a:hover {\n" +
            "                    background-color: #E61636;\n" +
            "                    color: #fff;\n" +
            "                    border: 1px solid #E61636;\n" +
            "                    text-decoration: none;\n" +
            "                }\n" +
            "\n" +
            "            .pages .cpb {\n" +
            "                font-weight: bold;\n" +
            "                color: #fff;\n" +
            "                background: #E61636;\n" +
            "                border: 1px solid #E61636;\n" +
            "            }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <form name=\"Form1\" method=\"post\" id=\"Form1\">\n" +
            "<div class=\"aspNetHidden\">\n" +
            "<input type=\"hidden\" name=\"__VIEWSTATE\" id=\"__VIEWSTATE\" value=\"CVo+BMSYGfrEi8vbt1BxlSbs+hUFDZRkLbFEthiTSRIS/1xd66A8dldnLemnAY10VXtecw3Lhnu0l/uCNaFKyvxpwC8A5vuuNKFdwRQRBmMdmzQ7C1DQS4B4T7zVRqh+bENp+RRXD84e1jXUwnKxD7q05MnLWEwehcPcaRDZpAI=\" />\n" +
            "</div>\n" +
            "\n" +
            "<div class=\"aspNetHidden\">\n" +
            "\n" +
            "\t<input type=\"hidden\" name=\"__VIEWSTATEGENERATOR\" id=\"__VIEWSTATEGENERATOR\" value=\"AB3BDE6C\" />\n" +
            "\t<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"GV3AD7kDQx8j5cLGx7+ZymcUK3725cfRE9n/eshXef/gbdvLe9nGQCRjym3pedRsp8CeyMCkL7FNhQ+ElSY6whFBhI082EH6Ph0SyW32Db8YM2zr8zBLL6p7WoMMd7CHIjaA6Psypd9sRjk+ax5CaHYsXaXSljyIZW5d7mif2BlEvFmqEfXOJw04tD7IOi7V3a0iMVWf78hhyUTEuQ59pw==\" />\n" +
            "</div>\n" +
            "        <div class=\"container\">\n" +
            "            <div class=\"header\">\n" +
            "                <div class=\"logo\">\n" +
            "                    <a href=\"http://www.sgu.edu.cn\">\n" +
            "                        <img src=\"nimg/logo.png\" /></a>\n" +
            "                </div>\n" +
            "\n" +
            "                <div class=\"nav\">\n" +
            "                    <ul>\n" +
            "                    <li><a href=\"http://www.sgu.edu.cn/gk/xxgk.html\" target=\"_blank\">学校概况</a></li>\n" +
            "                    <li class=\"border\"></li>\n" +
            "                    <li><a href=\"http://www.sgu.edu.cn/gk/jgsz.html\" target=\"_blank\">机构设置</a></li>\n" +
            "                    <li class=\"border\"></li>\n" +
            "                    <li><a href=\"http://www.sgu.edu.cn/gk/jxky.html\" target=\"_blank\">教学科研</a></li>\n" +
            "                    <li class=\"border\"></li>\n" +
            "                    <li><a href=\"http://www.sgu.edu.cn/gk/zsjy.html\" target=\"_blank\">招生就业</a></li>\n" +
            "                    <li class=\"border\"></li>\n" +
            "                    <li><a href=\"http://lib.sgu.edu.cn/\" target=\"_blank\">图书馆</a></li>\n" +
            "                    <li class=\"border\"></li>\n" +
            "                    <li><a href=\"http://www2.sgu.edu.cn/web/rsb/\" target=\"_blank\">人才招聘</a></li>\n" +
            "                    <li class=\"border\"></li>\n" +
            "                    <li><a href=\"http://sgxyxyh.sgu.edu.cn/\" target=\"_blank\">校友之家</a></li>\n" +
            "                    <li class=\"border\"></li>\n" +
            "                    <li><a href=\"http://www2.sgu.edu.cn/site/xzb/xxgk/index.aspx\" target=\"_blank\">信息公开</a></li>\n" +
            "                    <li class=\"border\"></li>\n" +
            "                    <li class=\"nav-end\"><a href=\"http://www.sgu.edu.cn/gk/yzxx.html\" target=\"_blank\">校长信箱</a></li>\n" +
            "                    </ul>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "            <div class=\"content\">\n" +
            "                <div class=\"top\">\n" +
            "                    <div id=\"top-pic\">\n" +
            "                        <div id=\"top-list\" style=\"left: -350px;\"><a href=\"#\"><img /></a><a href=\"d110567.html\" target=\"_blank\" title=\"我校召开学习贯彻习近平新时代中国特色社会主义思想主题教育动员部署会\"><img src=\"http://mis.sgu.edu.cn/indexPic/638176879135662628_1.jpg\" /></a><a href=\"d108059.html\" target=\"_blank\" title=\"《光明日报》刊发我校党建工作文章\"><img src=\"http://mis.sgu.edu.cn/indexPic/638048782654848392_1.jpg\" /></a><a href=\"d99096.html\" target=\"_blank\" title=\"省领导王曦到我校考察指导\"><img src=\"http://mis.sgu.edu.cn/indexPic/637600604111631106_1.jpg\" /></a><a href=\"d105576.html\" target=\"_blank\" title=\"省政协副主席许瑞生率队来我校调研指导\"><img src=\"http://mis.sgu.edu.cn/indexPic/637916568391707839_1.jpg\" /></a><a href=\"d108306.html\" target=\"_blank\" title=\"我校与浈江区人民政府签订乡村振兴战略合作框架协议\"><img src=\"http://mis.sgu.edu.cn/indexPic/638048783113177197_1.jpg\" /></a><a href=\"#\"><img /></a></div><div id=\"button\"><div class=\"btnDiv\"><span class=\"on\">1</span><span>2</span><span>3</span><span>4</span><span>5</span></div></div>\n" +
            "                        <a href=\"javascript:;\" id=\"prev\" class=\"arrow\">&lt;</a>\n" +
            "                        <a href=\"javascript:;\" id=\"next\" class=\"arrow\">&gt;</a>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div id=\"slider-wrap\">\n" +
            "                        \n" +
            "\n" +
            "                        <div id=\"slider\" class=\"nivoSlider\">\n" +
            "                        <!--  <img src=\"nimg/pic13.jpg\" alt=\"\" title=\"\" />\n" +
            "                        <img src=\"nimg/pic8.jpg\" alt=\"\" title=\"\" />\n" +
            "                        <img src=\"nimg/pic1.jpg\" alt=\"\" title=\"\" />\n" +
            "                        <img src=\"nimg/pic12.jpg\" alt=\"\" title=\"\" />\n" +
            "                       -->\n" +
            "                        <img src=\"nimg/pic22.jpg\" alt=\"\" title=\"\" />\n" +
            "                        <img src=\"nimg/pic21.jpg\" alt=\"\" title=\"\" />\n" +
            "                        </div>\n" +
            "                        <!-- <div class=\"slider-text1\">\n" +
            "                            <span>立志</span>\n" +
            "                            <span>崇德</span>\n" +
            "                        </div>\n" +
            "                        <div class=\"slider-text2\">\n" +
            "                            <span>勤学</span>\n" +
            "                            <span>创新</span>\n" +
            "                        </div>\n" +
            "                        -->\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div class=\"mid\">\n" +
            "                    <div class=\"l_m_left\">\n" +
            "                        <div class=\"location\">\n" +
            "                            您现在的位置&gt;&gt<a href=\"http://www.sgu.edu.cn\" title=\"韶关学院校园网\">首页</a>&gt;&gt;<a href=\"5m1.html\">通知公告</a>\n" +
            "                        </div>\n" +
            "                        <div class=\"search\">\n" +
            "                            <ul>\n" +
            "                                <li>\n" +
            "                                    <label>查找方式：</label>\n" +
            "                                    <select name=\"ddlSearchType\" id=\"ddlSearchType\" style=\"width:120px;\">\n" +
            "\t<option value=\"1\">--按标题--</option>\n" +
            "\t<option value=\"2\">--按内容--</option>\n" +
            "\t<option value=\"3\">--按作者--</option>\n" +
            "\n" +
            "</select>\n" +
            "                                </li>\n" +
            "                                <li>\n" +
            "                                    <label>关 键 字：</label>\n" +
            "                                    <input name=\"txtKeyWord\" type=\"text\" value=\"校历\" id=\"txtKeyWord\" style=\"width:120px;\" />\n" +
            "                                </li>\n" +
            "                                <li>\n" +
            "                                    <input type=\"submit\" name=\"btnSearch\" value=\"查找\" id=\"btnSearch\" />\n" +
            "                                </li>\n" +
            "                            </ul>\n" +
            "                        </div>\n" +
            "                        <div class=\"l_content\">\n" +
            "                            <div style=\"min-height: 420px; margin-bottom: 10px;\">\n" +
            "                                <ul><li><a href=\"d110406.html\" target=\"_blank\" style=\"color:#000000;\" title=\"关于公布 《韶关学院2023-2024学年校历》的通知\"><img src=\"nimg/dotted.png\" />关于公布 《韶关学院2023-2024学年校历》的通知&nbsp;2023/4/4</a></li><li><a href=\"d103654.html\" target=\"_blank\" style=\"color:#000000;\" title=\"关于公布 《韶关学院2022-2023学年校历》的通知\"><img src=\"nimg/dotted.png\" />关于公布 《韶关学院2022-2023学年校历》的通知&nbsp;2022/3/18</a></li></ul>\n" +
            "                            </div>\n" +
            "                            <div class=\"page\">\n" +
            "                                \n" +
            "<!-- AspNetPager 7.5 Copyright:2003-2015 Webdiyer (www.webdiyer.com) -->\n" +
            "<!--记录总数只有一页，AspNetPager已自动隐藏，若需在只有一页数据时显示AspNetPager，请将AlwaysShow属性值设为true！-->\n" +
            "<!-- AspNetPager 7.5 Copyright:2003-2015 Webdiyer (www.webdiyer.com) -->\n" +
            "\n" +
            "\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "\n" +
            "                    </div>\n" +
            "                    <div class=\"m_right\">\n" +
            "                        <div class=\"menu\">管理平台</div>\n" +
            "                        <div class=\"ter\">\n" +
            "                            <ul>\n" +
            "                            <li>\n" +
            "                                <span></span>\n" +
            "                                <a href=\"http://www2.sgu.edu.cn/web/xzb/dt2.aspx\" target=\"_blank\">一周安排</a>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <span></span>\n" +
            "                                <a href=\"https://cas.sgu.edu.cn/\" target=\"_blank\">统一身份认证平台</a>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <span></span>\n" +
            "                                <a href=\"https://mail.sgu.edu.cn/\" target=\"_blank\">校内邮箱</a>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <span></span>\n" +
            "                                <a href=\"https://sscg.cbpt.cnki.net\" target=\"_blank\">学报投稿平台</a>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <span></span>\n" +
            "                                <a href=\"http://card.sgu.edu.cn/ias/prelogin?sysid=FWDT\" target=\"_blank\">校园卡服务平台</a>\n" +
            "                            </li>\n" +
            "                            </ul>\n" +
            "                        </div>\n" +
            "\n" +
            "                        <div class=\"menu\">专题网站</div>\n" +
            "                        <div class=\"web\">\n" +
            "                            <ul>\n" +
            "                            <li>\n" +
            "                                <div class=\"color2\"></div>\n" +
            "                                <!--<a href=\"http://www2.sgu.edu.cn/web/yqfk/index.aspx\" target=\"_blank\">疫情防控专题网站</a>-->\n" +
            "                                <a href=\"http://www2.sgu.edu.cn/web/esd/\" target=\"_blank\">学习贯彻党的二十大精神</a>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <div class=\"color1\"></div>\n" +
            "                                <a href=\"http://hnyxlib.sgu.edu.cn:81/\" target=\"_blank\">华南研学资料与数据中心</a>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <div class=\"color2\"></div>\n" +
            "                                <a href=\"http://www2.sgu.edu.cn/web/pgw/\" target=\"_blank\">本科教学审核评估网</a>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <div class=\"color1\"></div>\n" +
            "                                <a href=\"http://www2.sgu.edu.cn/site/jwc/newsite/\" target=\"_blank\">教学管理</a>\n" +
            "                                <!--<a href=\"http://www.sgu.edu.cn/gk/jsgl.html\" target=\"_blank\">教学管理</a>-->\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <div class=\"color4\"></div>\n" +
            "                                <a href=\"http://www2.sgu.edu.cn/site/zbtb/index.aspx\" target=\"_blank\">招标公告</a>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <div class=\"color3\"></div>\n" +
            "                                <a href=\"http://www2.sgu.edu.cn/site/dwb/cwgk/cw/finance.aspx?fm=管理系统\" target=\"_blank\">财务管理</a>\n" +
            "                            </li>\n" +
            "                            </ul>\n" +
            "                        </div>\n" +
            "                        <div class=\"menu\">友情链接</div>\n" +
            "                        <div class=\"link\">\n" +
            "                            <ul>\n" +
            "                                <li>\n" +
            "                                    <img src=\"nimg/icon2.png\" />\n" +
            "                                    <a href=\"http://www.moe.gov.cn/\" target=\"_blank\">教育部</a>\n" +
            "                                </li>\n" +
            "                                <li>\n" +
            "                                    <img src=\"nimg/icon2.png\" />\n" +
            "                                    <a href=\"http://edu.gd.gov.cn/\" target=\"_blank\">教育厅</a>\n" +
            "                                </li>\n" +
            "                                <li>\n" +
            "                                    <img src=\"nimg/icon2.png\" />\n" +
            "                                    <a href=\"http://www.most.gov.cn/\" target=\"_blank\">科技部</a>\n" +
            "                                </li>\n" +
            "                                <li>\n" +
            "                                    <img src=\"nimg/icon2.png\" />\n" +
            "                                    <a href=\"http://gdstc.gd.gov.cn/\" target=\"_blank\">科技厅</a>\n" +
            "                                </li>\n" +
            "                            </ul>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div class=\"footer\">\n" +
            "                    <div class=\"f_left\">\n" +
            "                        <ul class=\"f_menu\">\n" +
            "                            <li>\n" +
            "                                <a href=\"#\">\n" +
            "                                    <img src=\"nimg/weibo.png\" />\n" +
            "                                </a>\n" +
            "                                <span>官方微博：韶关学院</span>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <a href=\"#\">\n" +
            "                                    <img src=\"nimg/code.png\" />\n" +
            "                                </a>\n" +
            "                                <span>官方微信：SGXYGFWX</span>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <a href=\"#\">\n" +
            "                                    <img src=\"nimg/icon.png\" />\n" +
            "                                </a>\n" +
            "                                <span>粤ICP备14094835号-1</span>\n" +
            "                            </li>\n" +
            "                        </ul>\n" +
            "                    </div>\n" +
            "                    <div class=\"f_right\">\n" +
            "                        <ul>\n" +
            "                            <li>copyright  2016 韶关学院. All  rights  reserved. 中国.广东省.韶关市浈江区大学路288号</li>\n" +
            "                            <li>网站维护：校长办公室  党委宣传部  数据与信息管理中心  <span>邮编：512005</span>\n" +
            "                            </li>\n" +
            "                        </ul>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </form>\n" +
            "</body>\n" +
            "</html>\n" +
            "\n";
    static String html2 = "<!DOCTYPE html>\n" +
            "\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><title>\n" +
            "\t关于公布 《韶关学院2022-2023学年校历》的通知\n" +
            "</title><meta http-equiv=\"x-UA-Compatible\" content=\"IE=edge\" />\n" +
            "    <!-- 防止ie进入怪异文档模式 -->\n" +
            "\n" +
            "    <link rel=\"stylesheet\" type=\"text/css\" href=\"ncss/nav.css\" />\n" +
            "    <!-- 菜单栏css  -->\n" +
            "    <link rel=\"stylesheet\" type=\"text/css\" href=\"ncss/list2.css\" /><link rel=\"stylesheet\" type=\"text/css\" href=\"ncss/r_content.css\" /><link rel=\"stylesheet\" type=\"text/css\" href=\"ncss/footer.css\" />\n" +
            "    <!--  脚本css样式 -->\n" +
            "</head>\n" +
            "<body>\n" +
            "    <div class=\"container\" id=\"container\">\n" +
            "        <div class=\"header\">\n" +
            "            <div class=\"logo\">\n" +
            "                <a href=\"http://www.sgu.edu.cn\">\n" +
            "                    <img src=\"nimg/logo.png\" /></a>\n" +
            "            </div>\n" +
            "\n" +
            "            <div class=\"nav\">\n" +
            "                <ul>\n" +
            "                    <li><a href=\"http://www.sgu.edu.cn/gk/xxgk.html\" target=\"_blank\">学校概况</a></li>\n" +
            "                    <li class=\"border\"></li>\n" +
            "                    <li><a href=\"http://www.sgu.edu.cn/gk/jgsz.html\" target=\"_blank\">机构设置</a></li>\n" +
            "                    <li class=\"border\"></li>\n" +
            "                    <li><a href=\"http://www.sgu.edu.cn/gk/jxky.html\" target=\"_blank\">教学科研</a></li>\n" +
            "                    <li class=\"border\"></li>\n" +
            "                    <li><a href=\"http://www.sgu.edu.cn/gk/zsjy.html\" target=\"_blank\">招生就业</a></li>\n" +
            "                    <li class=\"border\"></li>\n" +
            "                    <li><a href=\"http://lib.sgu.edu.cn/\" target=\"_blank\">图书馆</a></li>\n" +
            "                    <li class=\"border\"></li>\n" +
            "                    <li><a href=\"http://www2.sgu.edu.cn/web/rsb/\" target=\"_blank\">人才招聘</a></li>\n" +
            "                    <li class=\"border\"></li>\n" +
            "                    <li><a href=\"http://sgxyxyh.sgu.edu.cn/\" target=\"_blank\">校友之家</a></li>\n" +
            "                    <li class=\"border\"></li>\n" +
            "                    <li><a href=\"http://www2.sgu.edu.cn/site/xzb/xxgk/index.aspx\" target=\"_blank\">信息公开</a></li>\n" +
            "                    <li class=\"border\"></li>\n" +
            "                    <li class=\"nav-end\"><a href=\"http://www.sgu.edu.cn/gk/yzxx.html\" target=\"_blank\">校长信箱</a></li>\n" +
            "                </ul>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "\n" +
            "        <div class=\"content\" id=\"content\">\n" +
            "            <div class=\"content1\">\n" +
            "                <div class=\"location\">\n" +
            "                    您现在的位置&gt;&gt;\n" +
            "                    <a class=\"location1\" href=\"http://www.sgu.edu.cn\" title=\"韶关学院校园网\">首页</a>&gt;&gt;\n" +
            "                    <a class=\"location1\" href=\"5m1.html\">通知公告</a>\n" +
            "                    &gt;&gt;\n" +
            "                    具体新闻内容\n" +
            "                </div>\n" +
            "                <div class=\"essay\">\n" +
            "                    <h1 class=\"article_title\">\n" +
            "                        <span>关于公布 《韶关学院2022-2023学年校历》的通知</span>\n" +
            "                    </h1>\n" +
            "                    <div class=\"article_describe\">\n" +
            "                     <label>单位：<span class=\"des_Author\">教务处&nbsp;&nbsp;</span></label><label  class=\"des1\">发表日期：<span class=\"des_Date\">2022/3/18</span></label><label>浏览次数：<span class=\"des_ViewCount\">5171</span></label>\n" +
            "                    </div>\n" +
            "                    <div class=\"e_content\">\n" +
            "                        <p style=\"background: white; margin: 0cm 0cm 0pt; text-align: left; line-height: 24pt; font-size: 10.5pt; vertical-align: baseline;\" align=\"left\"><span style=\"color: black; letter-spacing: 0.5pt; font-size: 12pt;\">学校各部门、直属单位，二级学院：</span></p>\n" +
            "<font style=\"font-size: 12pt;\">\n" +
            "</font>\n" +
            "<p style=\"background: white; margin: 0cm 0cm 0pt; text-align: left; line-height: 24pt; text-indent: 24pt; font-size: 10.5pt; vertical-align: baseline;\" align=\"left\"><font style=\"font-size: 12pt;\"><span style=\"color: black; letter-spacing: 0.5pt; font-size: 12pt;\">《韶关学院</span><span style=\"color: black; letter-spacing: 0.5pt; font-size: 12pt;\">2022-</span><span style=\"color: black; letter-spacing: 0.5pt; font-size: 12pt;\">2023</span><span style=\"color: black; letter-spacing: 0.5pt; font-size: 12pt;\">学年校历》已经校长办公会议讨论通过，现予以公布（见附件），请按照校历认真部署下一学年的工作。</span></font></p>\n" +
            "<font style=\"font-size: 12pt;\">\n" +
            "</font>\n" +
            "<p style=\"background: white; margin: 0cm 0cm 0pt; text-align: left; line-height: 24pt; text-indent: 24pt; font-size: 10.5pt; vertical-align: baseline;\" align=\"left\"><span style=\"color: black; letter-spacing: 0.5pt; font-size: 12pt;\">特此通知。</span></p>\n" +
            "<font style=\"font-size: 12pt;\">\n" +
            "</font>\n" +
            "<p style=\"background: white; margin: 0cm 0cm 0pt; text-align: left; line-height: 24pt; text-indent: 24pt; font-size: 10.5pt; vertical-align: baseline;\" align=\"left\"><font style=\"font-size: 12pt;\"><span style=\"color: black; letter-spacing: 0.5pt; font-size: 12pt;\">附件：</span><span style=\"letter-spacing: 0.5pt; font-size: 12pt;\"><a target=\"_blank\" href=\"http://mis.sgu.edu.cn/xxfbfile/2022/03/18/20220318084212445.xls\">韶关学院2022-2023学年校历.xls</a></span></font></p>\n" +
            "<font style=\"font-size: 12pt;\">\n" +
            "</font>\n" +
            "<p style=\"margin: 0cm 0cm 0pt; text-align: right; font-size: 10.5pt;\"><span style=\"font-size: 12pt;\">教务处</span></p>\n" +
            "<font style=\"font-size: 12pt;\">\n" +
            "</font>\n" +
            "<p style=\"margin: 0cm 0cm 0pt; text-align: right; font-size: 10.5pt;\"><font style=\"font-size: 12pt;\">2022年3月19日</font></p>\n" +
            "<font style=\"font-size: 12pt;\">\n" +
            "</font>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "\n" +
            "                <div class=\"m_right\">\n" +
            "                    <div class=\"menu\">管理平台</div>\n" +
            "                    <div class=\"ter\">\n" +
            "                        <ul>\n" +
            "                            <li>\n" +
            "                                <span></span>\n" +
            "                                <a href=\"http://www2.sgu.edu.cn/web/xzb/dt2.aspx\" target=\"_blank\">一周安排</a>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <span></span>\n" +
            "                                <a href=\"https://cas.sgu.edu.cn/\" target=\"_blank\">统一身份认证平台</a>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <span></span>\n" +
            "                                <a href=\"https://mail.sgu.edu.cn/\" target=\"_blank\">校内邮箱</a>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <span></span>\n" +
            "                                <a href=\"https://sscg.cbpt.cnki.net\" target=\"_blank\">学报投稿平台</a>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <span></span>\n" +
            "                                <a href=\"http://card.sgu.edu.cn/ias/prelogin?sysid=FWDT\" target=\"_blank\">校园卡服务平台</a>\n" +
            "                            </li>\n" +
            "                        </ul>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div class=\"menu\">专题网站</div>\n" +
            "                    <div class=\"web\">\n" +
            "                        <ul>\n" +
            "                            <li>\n" +
            "                                <div class=\"color2\"></div>\n" +
            "                                <!--<a href=\"http://www2.sgu.edu.cn/web/yqfk/index.aspx\" target=\"_blank\">疫情防控专题网站</a>-->\n" +
            "                                <a href=\"http://www2.sgu.edu.cn/web/esd/\" target=\"_blank\">学习贯彻党的二十大精神</a>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <div class=\"color1\"></div>\n" +
            "                                <a href=\"http://hnyxlib.sgu.edu.cn:81/\" target=\"_blank\">华南研学资料与数据中心</a>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <div class=\"color2\"></div>\n" +
            "                                <a href=\"http://www2.sgu.edu.cn/web/pgw/\" target=\"_blank\">本科教学审核评估网</a>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <div class=\"color1\"></div>\n" +
            "                                <a href=\"http://www2.sgu.edu.cn/site/jwc/newsite/\" target=\"_blank\">教学管理</a>\n" +
            "                                <!--<a href=\"http://www.sgu.edu.cn/gk/jsgl.html\" target=\"_blank\">教学管理</a>-->\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <div class=\"color4\"></div>\n" +
            "                                <a href=\"http://www2.sgu.edu.cn/site/zbtb/index.aspx\" target=\"_blank\">招标公告</a>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <div class=\"color3\"></div>\n" +
            "                                <a href=\"http://www2.sgu.edu.cn/site/dwb/cwgk/cw/finance.aspx?fm=管理系统\" target=\"_blank\">财务管理</a>\n" +
            "                            </li>\n" +
            "                        </ul>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div class=\"menu\">友情链接</div>\n" +
            "                    <div class=\"link\">\n" +
            "                        <ul>\n" +
            "                            <li>\n" +
            "                                <img src=\"nimg/icon2.png\" />\n" +
            "                                <a href=\"http://www.moe.gov.cn/\" target=\"_blank\">教育部</a>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <img src=\"nimg/icon2.png\" />\n" +
            "                                <a href=\"http://edu.gd.gov.cn/\" target=\"_blank\">教育厅</a>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <img src=\"nimg/icon2.png\" />\n" +
            "                                <a href=\"http://www.most.gov.cn/\" target=\"_blank\">科技部</a>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                <img src=\"nimg/icon2.png\" />\n" +
            "                                <a href=\"http://gdstc.gd.gov.cn/\" target=\"_blank\">科技厅</a>\n" +
            "                            </li>\n" +
            "                        </ul>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "\n" +
            "\n" +
            "            <div class=\"footer\">\n" +
            "                <div class=\"f_left\">\n" +
            "                    <ul class=\"f_menu\">\n" +
            "                        <li>\n" +
            "                            <a href=\"#\">\n" +
            "                                <img src=\"nimg/weibo.png\" />\n" +
            "                            </a>\n" +
            "                            <span>官方微博：韶关学院</span>\n" +
            "                        </li>\n" +
            "                        <li>\n" +
            "                            <a href=\"#\">\n" +
            "                                <img src=\"nimg/code.png\" />\n" +
            "                            </a>\n" +
            "                            <span>官方微信：SGXYGFWX</span>\n" +
            "                        </li>\n" +
            "                        <li>\n" +
            "                            <a href=\"#\">\n" +
            "                                <img src=\"nimg/icon.png\" />\n" +
            "                            </a>\n" +
            "                            <span>粤ICP备14094835号-1</span>\n" +
            "                        </li>\n" +
            "                    </ul>\n" +
            "                </div>\n" +
            "                <div class=\"f_right\">\n" +
            "                    <ul>\n" +
            "                        <li>copyright  2016 韶关学院. All  rights  reserved. 中国.广东省.韶关市浈江区大学路288号</li>\n" +
            "                        <li>网站维护：校长办公室  党委宣传部  数据与信息管理中心  <span>邮编：512005</span>\n" +
            "                        </li>\n" +
            "                    </ul>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "            <div style=\"clear: both\"></div>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</body>\n" +
            "</html>\n";
}

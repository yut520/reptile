package enums;

/**
 * 链家城市url
 * TODO 遍历此枚举可用于爬去全国
 * @author iyut
 */
public enum LianjiaDomain {

    BJ("北京", "bj.fang.lianjia.com"),
    BD("保定", "bd.fang.lianjia.com"),
    BT("保亭", "bt.fang.lianjia.com"),

    CHENGDE("承德", "chengde.fang.lianjia.com"),
    CC("长春", "cc.fang.lianjia.com"),
    CZ("滁州", "cz.fang.lianjia.com"),
    CS("长沙", "cs.fang.lianjia.com"),
    CM("澄迈", "cm.fang.lianjia.com"),
    CQ("重庆", "cq.fang.lianjia.com"),
    CD("成都", "cd.fang.lianjia.com"),

    DL("大连", "dl.fang.lianjia.com"),
    DG("东莞", "dg.fang.lianjia.com"),
    DZ("儋州", "dz.fang.lianjia.com"),
    DONGFANG("东方", "dongfang.fang.lianjia.com"),
    DA("定安", "da.fang.lianjia.com"),
    DY("德阳", "dy.fang.lianjia.com"),
    DALI("大理", "dali.fang.lianjia.com"),

    FS("佛山", "fs.fang.lianjia.com"),

    GZ("广州", "gz.fang.lianjia.com"),
    GY("贵阳", "gy.fang.lianjia.com"),

    HD("邯郸", "hd.fang.lianjia.com"),
    HS("衡水", "hs.fang.lianjia.com"),
    HHHT("呼和浩特", "hhht.fang.lianjia.com"),
    HZ("杭州", "hz.fang.lianjia.com"),
    HF("合肥", "hf.fang.lianjia.com"),
    HG("黄冈", "hg.fang.lianjia.com"),
    HUI("惠州", "hui.fang.lianjia.com"),
    HK("海口", "hk.fang.lianjia.com"),

    JZ("晋中", "jz.fang.lianjia.com"),
    JX("嘉兴", "jx.fang.lianjia.com"),
    JN("济南", "jn.fang.lianjia.com"),

    LF("廊坊", "lf.fang.lianjia.com"),
    LY("龙岩", "ly.fang.lianjia.com"),
    LG("临高", "lg.fang.lianjia.com"),
    LD("乐东", "ld.fang.lianjia.com"),
    LS("陵水", "ls.fang.lianjia.com"),
    LESHAN("乐山", "leshan.fang.lianjia.com"),
    MS("眉山", "ms.fang.lianjia.com"),
    NJ("南京", "nj.fang.lianjia.com"),
    NT("南通", "nt.fang.lianjia.com"),

    QHD("秦皇岛", "qhd.fang.lianjia.com"),
    QUANZHOU("泉州", "quanzhou.fang.lianjia.com"),
    QD("青岛", "qd.fang.lianjia.com"),
    QY("清远", "qy.fang.lianjia.com"),
    QH("琼海", "qh.fang.lianjia.com"),
    QZ("琼中", "qz.fang.lianjia.com"),

    SJZ("石家庄", "sjz.fang.lianjia.com"),
    SY("沈阳", "sy.fang.lianjia.com"),
    SH("上海", "sh.fang.lianjia.com"),
    SU("苏州", "sz.fang.lianjia.com"),
    SX("绍兴", "sx.fang.lianjia.com"),
    SZ("深圳", "sz.fang.lianjia.com"),
    SAN("三亚", "san.fang.lianjia.com"),

    TJ("天津", "tj.fang.lianjia.com"),
    TY("太原", "ty.fang.lianjia.com"),

    WX("无锡", "wx.fang.lianjia.com"),
    WEIHAI("威海", "weihai.fang.lianjia.com"),
    WH("武汉", "wh.fang.lianjia.com"),
    WZS("五指山", "wzs.fang.lianjia.com"),
    WC("文昌", "wc.fang.lianjia.com"),
    WN("万宁", "wn.fang.lianjia.com"),

    XT("邢台", "xt.fang.lianjia.com"),
    XZ("徐州", "xz.fang.lianjia.com"),
    XM("厦门", "xm.fang.lianjia.com"),
    XN("咸宁", "xn.fang.lianjia.com"),
    XSBN("西双版纳", "xsbn.fang.lianjia.com"),
    XA("西安", "xa.fang.lianjia.com"),
    XAN("雄安新区", "xan.ke.com"),

    YT("烟台", "yt.fang.lianjia.com"),

    ZJK("张家口", "zjk.fang.lianjia.com"),
    ZJ("镇江", "zj.fang.lianjia.com"),
    ZHANGZHOU("漳州", "zhangzhou.fang.lianjia.com"),
    ZZ("郑州", "zz.fang.lianjia.com"),
    ZH("珠海", "zh.fang.lianjia.com"),
    ZS("中山", "zs.fang.lianjia.com"),

    ;

    private String name;

    private String url;

    LianjiaDomain(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}

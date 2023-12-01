package com.aurora.day.auroratimerservernative.schemes.eums;

import org.noear.snack.ONode;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.List;

/*
    0:考核成员
    1:正常成员
    2~10:职务方面的
    11~50:各个端组长
    100:负责人。
 */
public enum PrivilegeEnum {
    //以下同级之间没有任何排序关系(叠甲)
    Examining(0,"考核成员"),
    Normal(1,"普通成员"),
    DutyManager(2,"值日委员"),
    AlgorithmLeader(11,"算法端组长"),
    DesignLeader(12,"设计组长"),
    HardwareLeader(13,"嵌入式组长"),
    DataAnalysisLeader(14,"数据分析组长"),
    ArtificialIntelligenceLeader(15,"人工智能组长"),
    NetworkSecurityLeader(16,"网安组长"),
    WebLeader(17,"Web端组长"),
    Admin(100,"管理员");
    /**
     * 权限名集合
     */
    public static final List<String> PrivList = Arrays.asList("普通成员","考核成员","Web端组长","设计组长","算法端组长","嵌入式组长","人工智能组长","数据分析组长","网络安全组长","值日委员","管理员");
    public final int val;
    public final String candidName;

    PrivilegeEnum(int i,String candidName) {
        this.val = i;
        this.candidName = candidName;
    }

    @Nullable
    public static PrivilegeEnum conventToEnum(int index){
        PrivilegeEnum privilege = null;
        try{
            privilege = Arrays.stream(values()).filter(privilegeEnum -> privilegeEnum.val==index).findFirst().get();
        }catch (Exception e){
            return null;
        }
        return privilege;
    }

    public String toJSON(){
        ONode json = new ONode();
        json.set("val",val);
        json.set("candidName",candidName);
        return json.toJson();
    }
    public static PrivilegeEnum parseJSON(String str){
        ONode json = ONode.loadStr(str);
        return conventToEnum(json.get("val").getInt());
    }

}

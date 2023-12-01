package com.aurora.day.auroratimerservernative;

import cn.hutool.json.JSONUtil;
import com.aurora.day.auroratimerservernative.schemes.eums.PrivilegeEnum;
import org.junit.jupiter.api.Test;
import org.noear.snack.ONode;

public class NormalJavaTest {

    @Test
    void priv(){
        PrivilegeEnum privilegeEnum = PrivilegeEnum.conventToEnum(100);
        String jsonstr = privilegeEnum.toJSON();
        System.out.println(jsonstr);
        System.out.println(PrivilegeEnum.parseJSON(jsonstr));
    }

}

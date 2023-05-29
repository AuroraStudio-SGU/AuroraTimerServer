package com.aurora.day.auroratimerserver.pojo;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TermTime {
    public Term first;
    public Term second;

    public Term getCurrentTerm(){
        if(DateUtil.isIn(DateUtil.date(),first.start,first.end)) return first;
        if(DateUtil.isIn(DateUtil.date(),second.start,second.end)) return second;
        //也有可能都不在,(例如暑假寒假)
        return null;
    }
}

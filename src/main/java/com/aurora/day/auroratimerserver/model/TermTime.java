package com.aurora.day.auroratimerserver.model;

import lombok.Data;

@Data
public class TermTime {
    private Term first;
    private Term second;

//    public Term getCurrentTerm(){
//        if(DateUtil.isIn(DateUtil.date(),first.getStart(),first.getEnd())) return first;
//        if(DateUtil.isIn(DateUtil.date(),second.start,second.end)) return second;
//        //也有可能都不在,(例如暑假寒假)
//        return null;
//    }
}
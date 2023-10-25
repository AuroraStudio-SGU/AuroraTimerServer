package com.aurora.day.auroratimerserver.mapper;

import com.aurora.day.auroratimerserver.pojo.Term;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TermMapper extends BaseMapper<Term> {

    @Select("select * from term where start<=#{day} and end>=#{day} ")
    Term queryTermInDate(String day);

}

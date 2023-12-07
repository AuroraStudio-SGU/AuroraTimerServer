package com.aurora.day.auroratimerservernative.servicelmpl;

import cn.hutool.core.date.DateUtil;
import com.aurora.day.auroratimerservernative.config.TimerConfig;
import com.aurora.day.auroratimerservernative.mapper.TermMapper;
import com.aurora.day.auroratimerservernative.pojo.Notice;
import com.aurora.day.auroratimerservernative.pojo.Term;
import com.aurora.day.auroratimerservernative.service.ITermService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TermServiceImpl implements ITermService {

    private final TermMapper termMapper;

    @Override
    public Term getCurrentTerm() {
        return termMapper.queryTermInDate(DateUtil.today());
    }

    @Override
    public boolean updateTermById(Term term) {
        return termMapper.updateById(term)==1;
    }

    @Override
    public List<Term> getAllTerms() {
        return termMapper.selectList(null);
    }

    @Override
    public boolean insertTerm(Term term) {
        if(termMapper.selectCount(null)>= TimerConfig.TermSize){
            QueryWrapper<Term> wrapper = new QueryWrapper<>();
            wrapper.orderByAsc("id").last("limit 1");//not safe sql
            termMapper.delete(wrapper);
        }
        return termMapper.insert(term)==1;
    }


}

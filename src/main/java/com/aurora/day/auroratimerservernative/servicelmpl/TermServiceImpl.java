package com.aurora.day.auroratimerservernative.servicelmpl;

import cn.hutool.core.date.DateUtil;
import com.aurora.day.auroratimerservernative.mapper.TermMapper;
import com.aurora.day.auroratimerservernative.pojo.Term;
import com.aurora.day.auroratimerservernative.service.ITermService;
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


}

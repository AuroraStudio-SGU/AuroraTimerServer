package com.aurora.day.auroratimerservernative.servicelmpl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.aurora.day.auroratimerservernative.mapper.TermMapper;
import com.aurora.day.auroratimerservernative.pojo.Term;
import com.aurora.day.auroratimerservernative.pojo.TermTime;
import com.aurora.day.auroratimerservernative.service.ITermService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TermServiceImpl implements ITermService {

    private final TermMapper termMapper;
    private static final Logger log = LoggerFactory.getLogger(TermServiceImpl.class);

    @Override
    public Term getCurrentTerm() {
        List<Term> terms = termMapper.queryTermInDate(DateUtil.today());
        if (terms.size() > 1) {
            log.warn("存在多个相同的学期");
        }
        if(terms.isEmpty()) return null;
        return terms.get(0);
    }

    @Override
    public boolean updateTermById(Term term) {
        QueryWrapper<Term> wrapper = new QueryWrapper<>();
        wrapper.eq("id", term.getId());
        if (termMapper.exists(wrapper)) {
            return termMapper.updateById(term) == 1;
        } else {
            return termMapper.insert(term) == 1;
        }
    }

    @Override
    public boolean updateTermTime(TermTime termTime) {
        int op = 0;
        QueryWrapper<Term> first = new QueryWrapper<Term>().eq("id", termTime.first.getId());
        if (termMapper.exists(first)) {
            op += termMapper.updateById(termTime.first);
        } else {
            op += termMapper.insert(termTime.first);
        }
        QueryWrapper<Term> second = new QueryWrapper<Term>().eq("id", termTime.second.getId());
        if (termMapper.exists(second)) {
            op += termMapper.updateById(termTime.second);
        } else {
            op += termMapper.insert(termTime.second);
        }
        return op == 2;
    }

    @Override
    public List<Term> getAllTerms() {
        return termMapper.selectList(null);
    }

    @Deprecated
    @Override
    public boolean insertTerm(Term term) {
        return termMapper.insert(term) == 1;
    }


}

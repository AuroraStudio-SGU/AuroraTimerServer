package com.aurora.day.auroratimerserver.services.impl;

import com.aurora.day.auroratimerserver.mapper.TermMapper;
import com.aurora.day.auroratimerserver.model.Term;
import com.aurora.day.auroratimerserver.services.ITermService;
import com.mybatisflex.core.query.QueryWrapper;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class TermService implements ITermService {

    @Inject
    TermMapper termMapper;

    @Override
    public Term getCurrentTerm() {
        QueryWrapper wp = new QueryWrapper();
        wp.gt(Term::getStart, LocalDate.now())
                .le(Term::getEnd, LocalDate.now());
        Term term = termMapper.selectOneByQuery(wp);
        if (term == null) {
            //TODO 为空则进行获取
        }
        return term;
    }

    @Override
    public boolean insertOrUpdateTerm(Term term) {
        return termMapper.insertOrUpdate(term, true) > 0;
    }

    @Override
    public Term getTermByWeekStart(String weekStart) {
        QueryWrapper wp = new QueryWrapper();
        wp.le(Term::getEnd, LocalDate.parse(weekStart, DateTimeFormatter.ISO_DATE));
        Term term = termMapper.selectOneByQuery(wp);
        if (term == null) {
            //TODO 为空则进行获取
        }
        return term;
    }

    @Override
    public List<Term> getAllTerms() {
        return termMapper.selectAll();
    }
}

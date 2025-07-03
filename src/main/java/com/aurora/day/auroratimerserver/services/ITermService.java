package com.aurora.day.auroratimerserver.services;

import com.aurora.day.auroratimerserver.model.Term;

import java.util.List;

public interface ITermService {

    Term getCurrentTerm();

    boolean insertOrUpdateTerm(Term term);

    Term getTermByWeekStart(String weekStart);

    List<Term> getAllTerms();
}

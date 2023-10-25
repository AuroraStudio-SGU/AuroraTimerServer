package com.aurora.day.auroratimerserver.service;

import com.aurora.day.auroratimerserver.pojo.Term;

import java.util.Date;
import java.util.List;

public interface ITermService {

    /***
     * 查询现在所在的学期之内
     * @return 如果存在则返回Term，否则返回null
     */
    Term getCurrentTerm();

    /***
     * 更新学期
     * @param term 需要更新的学期
     * @return 是否更新成功
     */
    boolean updateTermById(Term term);

    /***
     * 获取数据库中所有的学期
     */
    List<Term> getAllTerms();

}

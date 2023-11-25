package com.aurora.day.auroratimerservernative.schemes.request;

import cn.hutool.core.date.DateUtil;
import com.aurora.day.auroratimerservernative.pojo.Term;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class updateTermRequest {
    @NotNull(message = "id不能为空")
    @Schema(name = "id",description = "学期id")
    private Integer id;
    @Schema(name = "start",description = "起始时间(日期格式 yyyy-mm-dd)")
    @Pattern(regexp = "^([1-2][0-9][0-9][0-9]-[0-1]{0,1}[0-9]-[0-3]{0,1}[0-9])\\s(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$",message = "日期格式不正确")
    private String start;
    @Schema(name = "end",description = "结束时间(日期格式 yyyy-mm-dd)")
    @Pattern(regexp = "^([1-2][0-9][0-9][0-9]-[0-1]{0,1}[0-9]-[0-3]{0,1}[0-9])\\s(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$",message = "日期格式不正确")
    private String end;

    public Term toTerm(){
        return new Term(this.id,null, DateUtil.parse(this.start),DateUtil.parse(this.end));
    }
}

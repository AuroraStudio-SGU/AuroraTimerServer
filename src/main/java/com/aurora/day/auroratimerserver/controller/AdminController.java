package com.aurora.day.auroratimerserver.controller;

import com.aurora.day.auroratimerserver.schemes.R;
import com.aurora.day.auroratimerserver.schemes.request.UpLoadNoticeRequest;
import com.aurora.day.auroratimerserver.servicelmpl.NoticeServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RestControllerAdvice
@Validated
@RequiredArgsConstructor
@Tag(name = "AdminController", description = "管理员操作接口")
public class AdminController {

    private final NoticeServiceImpl noticeService;

    @PostMapping("/admin/uploadNotice")
    public R notice(@Valid @RequestBody UpLoadNoticeRequest request){
        if(noticeService.insertNotice(request.toNotice())) return R.OK();
        else return R.error("上传失败");
    }

}

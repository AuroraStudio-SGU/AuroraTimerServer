package com.aurora.day.auroratimerserver.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@CrossOrigin
@RestControllerAdvice
@Validated
@Tag(name = "TimerController", description = "计时器接口")
public class TimerController {

}

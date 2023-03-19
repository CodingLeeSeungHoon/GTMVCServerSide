package com.example.gtmvcserverside.member.controller;

import com.example.gtmvcserverside.member.service.GTMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityExistsException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GTMemberController {

    private final GTMemberService memberService;

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<String> handleEntityExistException(EntityExistsException ex){
        return ResponseEntity.badRequest()
                .body(ex.getMessage());
    }

}
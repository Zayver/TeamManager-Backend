package com.jnz.teamManager.controller;

import com.jnz.teamManager.entity.Request;
import com.jnz.teamManager.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request")
public class RequestController {
    @Autowired
    RequestService requestService;

    @PostMapping("/add")
    public Request addRequest(@RequestBody Request request){
        return requestService.addRequest(request);
    }

    @PostMapping("/accept")
    public void acceptRequest(@RequestBody Request request){
        //TODO
    }

    @DeleteMapping("/decline")
    public void declineRequest(@RequestBody Request request){
        requestService.deleteRequest(request);
    }
}

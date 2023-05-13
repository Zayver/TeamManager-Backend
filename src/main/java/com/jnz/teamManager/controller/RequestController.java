package com.jnz.teamManager.controller;

import com.jnz.teamManager.dto.RequestDTO;
import com.jnz.teamManager.entity.Request;
import com.jnz.teamManager.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/request")
public class RequestController {
    @Autowired
    RequestService requestService;

    @PostMapping("/add")
    public void addRequest(@RequestBody Map<String, String> request){
        requestService.addRequest(request);
    }

    @GetMapping("/get/{id}")
    public Iterable<RequestDTO> getRequestByTeamId(@PathVariable("id") Long id){
        return requestService.getRequestByTeamId(id);
    }

    @PostMapping("/accept")
    public void acceptRequest(@RequestBody Request request){
        requestService.acceptRequest(request);
    }

    @PostMapping("/decline")
    public void declineRequest(@RequestBody Request request){
        requestService.deleteRequest(request);
    }
}

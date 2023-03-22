package com.jnz.teamManager.service;

import com.jnz.teamManager.entity.Request;
import com.jnz.teamManager.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestService {

    @Autowired
    RequestRepository requestRepository;

    public Request addRequest(Request request){
        return requestRepository.save(request);
    }
    public void deleteRequest(Request request){
        requestRepository.delete(request);
    }
}

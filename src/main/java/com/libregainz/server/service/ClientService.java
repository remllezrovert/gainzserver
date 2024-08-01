package com.libregainz.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.libregainz.server.model.Client;

import com.libregainz.server.repo.ClientRepo;


@Service
public class ClientService {
private final ClientRepo clientRepo;
private EmailService emailService;

public ClientService(ClientRepo clientRepo, EmailService emailService){
    this.clientRepo = clientRepo;
}

public ClientService(){
    this.clientRepo = new ClientRepo();
    this.emailService = new EmailService();
}


public List<Client> allClients(){
    List<Client> clients  = new ArrayList<>();
    clientRepo.findAll().forEach(clients::add);
    return clients;
}




}

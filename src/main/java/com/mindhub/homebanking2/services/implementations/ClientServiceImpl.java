package com.mindhub.homebanking2.services.implementations;

import com.mindhub.homebanking2.dtos.ClientDTO;
import com.mindhub.homebanking2.models.Card;
import com.mindhub.homebanking2.models.Client;
import com.mindhub.homebanking2.repository.ClientRepository;
import com.mindhub.homebanking2.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<ClientDTO> getClientsDTO() {
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClientDTO(Long id) {
        return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public Client getAuthClientDTO(Authentication authentication) {
        return clientRepository.findByEmail(authentication.getName());
    }

    @Override
    public int getActiveAccounts(Client client){
        int activeAccounts = (int) client.getAccounts().stream().filter(account -> account.isActive()).count();
        return activeAccounts;
    }

    @Override
    public List<Card> getActiveCards(Client client){
        List<Card> activeCards = (List<Card>) client.getAccounts().stream().filter(account -> account.isActive() == true);
        return activeCards;
    }

}

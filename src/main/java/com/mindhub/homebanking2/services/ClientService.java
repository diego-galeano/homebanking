package com.mindhub.homebanking2.services;

import com.mindhub.homebanking2.dtos.ClientDTO;
import com.mindhub.homebanking2.models.Card;
import com.mindhub.homebanking2.models.Client;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


public interface ClientService {
    List<ClientDTO> getClientsDTO();
    ClientDTO getClientDTO(Long id);
    Client findByEmail(String email);
    void saveClient(Client client);
    Client getAuthClientDTO(Authentication authentication);

    int getActiveAccounts(Client client);

    List<Card> getActiveCards(Client client);
}

package com.laroz.services;

import com.laroz.dtos.clients.ClientResponse;
import com.laroz.dtos.clients.CreateClient;
import com.laroz.dtos.clients.UpdateClient;
import com.laroz.dtos.user.UserResponse;
import com.laroz.models.Client;
import com.laroz.repositories.ClientRepository;
import com.laroz.specifications.ClientSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public ClientResponse create(CreateClient request) {
        return new ClientResponse(clientRepository.save(new Client(request)));
    }

    public Page<ClientResponse> list(
            Pageable page,
            String name,
            String email,
            String instagram,
            String phone
    ) {
        Specification<Client> spec = Specification.where(ClientSpecification.isActive());

        if (name != null && !name.isEmpty()) {
            spec = spec.and(ClientSpecification.hasName(name));
        }
        if (email != null && !email.isEmpty()) {
            spec = spec.and(ClientSpecification.hasEmail(email));
        }
        if (instagram != null && !instagram.isEmpty()) {
            spec = spec.and(ClientSpecification.hasInstagram(instagram));
        }
        if (phone != null && !phone.isEmpty()) {
            spec = spec.and(ClientSpecification.hasPhone(phone));
        }

        return clientRepository.findAll(spec, page).map(ClientResponse::new);
    }

    public ClientResponse getById(Long id) {
        return new ClientResponse(clientRepository.getReferenceById(id));
    }

    public ClientResponse update(Long id, UpdateClient request) {
        var client = clientRepository.getReferenceById(id);
        client.update(request);
        clientRepository.save(client);
        return new ClientResponse(client);
    }


    public void delete(Long id) {
        var client = clientRepository.getReferenceById(id);
        client.delete();
        clientRepository.save(client);
    }

    public List<ClientResponse> create_many(MultipartFile file) throws IOException {
        var newClients = new ArrayList<Client>();
        var reader = new BufferedReader((new InputStreamReader(file.getInputStream())));
        String row;
        while ((row = reader.readLine()) != null) {
            String[] data = row.split(",");
            if (data.length != 4) continue;

            newClients.add(
                    new Client(
                            new CreateClient(
                                    data[0],
                                    data[1],
                                    data[2],
                                    data[3]
                            )
                    )
            );
        }

        return clientRepository.saveAll(newClients).stream().map(ClientResponse::new).toList();
    }

    public ByteArrayResource exportAllCsv() throws IOException {
        List<Client> clients = clientRepository.findAll();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

        writer.write("ID,Name,Email,Phone,Active,CreatedAt,UpdatedAt\n");
        for (Client client : clients) {
            writer.write(String.format("%d,%s,%s,%s,%s,%s,%s,\n",
                    client.getId(),
                    client.getName(),
                    client.getEmail(),
                    client.getPhone(),
                    client.isActive(),
                    client.getCreatedAt().toString(),
                    client.getUpdatedAt().toString()
            ));
        }
        writer.flush();
        return new ByteArrayResource(out.toByteArray());
    }
}

package com.lucianozimermann.dsclient.repositories;

import com.lucianozimermann.dsclient.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long>
{}

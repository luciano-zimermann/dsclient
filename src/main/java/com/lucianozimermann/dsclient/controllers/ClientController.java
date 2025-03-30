package com.lucianozimermann.dsclient.controllers;

import com.lucianozimermann.dsclient.dto.ClientDTO;
import com.lucianozimermann.dsclient.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping( value = "clients")
public class ClientController
{
    @Autowired
    private ClientService service;

    @GetMapping( value = "/{id}" )
    public ResponseEntity<ClientDTO> findyById( @PathVariable Long id )
    {
        return ResponseEntity.ok( service.findById( id ) );
    }

    @GetMapping()
    public ResponseEntity<Page<ClientDTO>> findAll( Pageable pageable )
    {
        return ResponseEntity.ok( service.findAll( pageable ) );
    }

    @PostMapping
    public ResponseEntity<ClientDTO> insert( @Valid @RequestBody ClientDTO dto )
    {
        dto = service.insert( dto );

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                                             .path( "/{id}" )
                                             .buildAndExpand( dto.getId() )
                                             .toUri();

        return ResponseEntity.created( uri ).body( dto );
    }

    @PutMapping( value = "/{id}" )
    public ResponseEntity<ClientDTO> update( @PathVariable Long id, @Valid @RequestBody ClientDTO dto )
    {
        return ResponseEntity.ok( service.update( id, dto ) );
    }

    @DeleteMapping( value = "/{id}" )
    public ResponseEntity<Void> delete( @PathVariable Long id )
    {
        service.delete( id );

        return ResponseEntity.noContent().build();
    }
}

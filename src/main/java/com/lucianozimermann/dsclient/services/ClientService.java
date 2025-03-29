package com.lucianozimermann.dsclient.services;

import com.lucianozimermann.dsclient.dto.ClientDTO;
import com.lucianozimermann.dsclient.entities.Client;
import com.lucianozimermann.dsclient.repositories.ClientRepository;
import com.lucianozimermann.dsclient.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService
{
    @Autowired
    private ClientRepository repository;

    @Transactional( readOnly = true )
    public ClientDTO findById( Long id )
    {
        Client client = repository.findById( id )
                                  .orElseThrow( () -> new ResourceNotFoundException( "Recurso não encontrado" ) );

        return new ClientDTO( client );
    }

    @Transactional( readOnly = true )
    public Page<ClientDTO> findAll( Pageable pageable )
    {
        Page<Client> result = repository.findAll( pageable );

        return result.map( ClientDTO::new );
    }

    @Transactional
    public ClientDTO insert( ClientDTO dto )
    {
        Client client = new Client();

        copyDtoToEntity( dto, client );

        return getDtoFromEntity( client );
    }

    @Transactional
    public ClientDTO update( Long id, ClientDTO dto )
    {
        try
        {
            Client client = repository.getReferenceById( id );

            copyDtoToEntity( dto, client );

            return getDtoFromEntity( client );
        }

        catch ( EntityNotFoundException e )
        {
            throw new ResourceNotFoundException( "Recurso não encontrado" );
        }
    }

    @Transactional( propagation = Propagation.SUPPORTS )
    public void delete( Long id )
    {
        if ( !repository.existsById( id ) )
        {
            throw new ResourceNotFoundException( "Recurso não encontrado" );
        }

        repository.deleteById( id );
    }

    private void copyDtoToEntity( ClientDTO dto, Client entity )
    {
        entity.setName( dto.getName() );
        entity.setCpf( dto.getCpf() );
        entity.setIncome( dto.getIncome() );
        entity.setBirthDate( dto.getBirthDate() );
        entity.setChildren( dto.getChildren() );
    }

    private ClientDTO getDtoFromEntity( Client entity )
    {
        entity = repository.save( entity );

        return new ClientDTO( entity );
    }
}

package com.yme.clientservice.infraestructure.output.adapter.mapper;

import com.yme.clientservice.domain.Client;
import com.yme.clientservice.infraestructure.output.adapter.repository.entity.ClientEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface ClientMapper {

    Client toClient(ClientEntity clientEntity);

    ClientEntity toClientEntity(Client client);


}

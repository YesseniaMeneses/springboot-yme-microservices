package com.yme.clientservice.mapper;

import com.yme.clientservice.domain.Client;
import com.yme.clientservice.entity.ClientEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface ClientMapper {

    @Mapping(target = "clientId", source = "id")
    Client toClient(ClientEntity clientEntity);

    @Mapping(target = "id", source = "clientId")
    ClientEntity toClientEntity(Client client);


}

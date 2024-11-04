package com.GREENO.mapper;

import com.GREENO.Entities.Client;
import com.GREENO.Entities.Deliverer;
import com.GREENO.Entities.Seller;
import com.GREENO.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Set;
import java.util.stream.Collectors;

public class DTOUtils {

    public DTOEntity convertToDto(Object obj, DTOEntity mapper){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        modelMapper.typeMap(Client.class, UserDTO.class).addMappings(mapping -> {
            mapping.map(Client::getIsEnabled, UserDTO::setIsEnabled);
            mapping.map(Client::getPenaltyPoints, UserDTO::setPenaltyPoints);
        });

        modelMapper.typeMap(Seller.class, UserDTO.class).addMappings(mapping ->
                mapping.map(Seller::getIsEnabled, UserDTO::setIsEnabled));

        modelMapper.typeMap(Deliverer.class, UserDTO.class).addMappings(mapping ->
                mapping.map(Deliverer::getIsEnabled, UserDTO::setIsEnabled));

        return modelMapper.map(obj, mapper.getClass());
    }


    public <DTOEntity, Object> Set<DTOEntity> convertToDtos(Set<Object> obj, DTOEntity mapper){

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return (Set<DTOEntity>) obj.stream().map(o -> modelMapper.map(o, mapper.getClass())).collect(Collectors.toSet());

    }

    public Object convertToEntity(Object obj, DTOEntity mapper){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(mapper, obj.getClass());
    }
    public Object convertToEntityStrict(Object obj, DTOEntity mapper){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(mapper, obj.getClass());
    }

}

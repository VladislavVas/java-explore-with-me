package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.model.EndpointHit;

@Mapper(componentModel = "spring")
public interface EndPointHitMapper {

    @Mapping(
            source = "timestamp",
            target = "timestamp",
            dateFormat = "yyyy-MM-dd HH:mm:ss"
    )
    EndpointHit toEndpointHit(EndpointHitDto endpointHitDto);

    @Mapping(
            source = "timestamp",
            target = "timestamp",
            dateFormat = "yyyy-MM-dd HH:mm:ss"
    )
    EndpointHitDto toEndpointHitDto(EndpointHit endpointHit);

}
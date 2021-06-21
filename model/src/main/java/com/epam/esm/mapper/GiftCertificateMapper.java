package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GiftCertificateMapper extends BaseMapper<GiftCertificateDto, GiftCertificate> {

}

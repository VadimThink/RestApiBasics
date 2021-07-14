package com.epam.esm.mapper;

import com.epam.esm.dto.UpdateGiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateGiftCertificateMapper extends BaseMapper<UpdateGiftCertificateDto, GiftCertificate> {
}

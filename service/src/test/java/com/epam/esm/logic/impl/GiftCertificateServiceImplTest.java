package com.epam.esm.logic.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.logic.GiftCertificateService;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.GiftCertificateMapperImpl;
import com.epam.esm.mapper.UpdateGiftCertificateMapper;
import com.epam.esm.mapper.UpdateGiftCertificateMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GiftCertificateServiceImplTest {
    private static final long ID = 1;
    private static final GiftCertificate GIFT_CERTIFICATE = new GiftCertificate(ID, "test",
            "test", BigDecimal.TEN, 5,
            ZonedDateTime.now(), ZonedDateTime.now(), new ArrayList<Tag>());
    private GiftCertificateDto GIFT_CERTIFICATE_DTO;
    private TagDao tagDao;
    private GiftCertificateDao giftCertificateDao;
    private GiftCertificateService giftCertificateService;
    private GiftCertificateMapper giftCertificateMapper;
    private UpdateGiftCertificateMapper updateGiftCertificateMapper;

    @BeforeEach
    void init() {
        tagDao = Mockito.mock(TagDaoImpl.class);
        giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);
        giftCertificateMapper = Mockito.mock(GiftCertificateMapperImpl.class);
        updateGiftCertificateMapper = Mockito.mock(UpdateGiftCertificateMapperImpl.class);
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDao, tagDao,
                giftCertificateMapper, updateGiftCertificateMapper);
        GIFT_CERTIFICATE_DTO = giftCertificateMapper.mapToDto(GIFT_CERTIFICATE);
    }

    @Test
    void create() {
        //given
        GiftCertificateDto actual = new GiftCertificateDto();
        //when
        try {
            actual = giftCertificateService.create(GIFT_CERTIFICATE_DTO);
        } catch (DuplicateException | NoSuchEntityException e) {
            e.printStackTrace();
        }
        //then
        assertEquals(GIFT_CERTIFICATE_DTO, actual);
    }

    @Test
    void findById() {
    }

    @Test
    void updateById() {
    }

    @Test
    void replaceById() {
    }

    @Test
    void findBySearchParams() {
    }
}
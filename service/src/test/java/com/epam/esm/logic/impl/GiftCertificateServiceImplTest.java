package com.epam.esm.logic.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UpdateGiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.logic.GiftCertificateService;
import com.epam.esm.mapper.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GiftCertificateServiceImplTest {
    private static final long ID = 1;
    private static final GiftCertificate GIFT_CERTIFICATE = new GiftCertificate(ID, "test",
            "test", BigDecimal.TEN, 5,
            ZonedDateTime.now(), ZonedDateTime.now(), new ArrayList<Tag>());
    private static final GiftCertificateDto GIFT_CERTIFICATE_DTO = new GiftCertificateDto(ID, "test", "test", BigDecimal.TEN, 5,
            ZonedDateTime.now(), ZonedDateTime.now(), new ArrayList<TagDto>());
    private static final UpdateGiftCertificateDto UPDATE_GIFT_CERTIFICATE_DTO = new UpdateGiftCertificateDto(ID, "test", "test", BigDecimal.TEN, 5,
            ZonedDateTime.now(), ZonedDateTime.now(), new ArrayList<TagDto>());

    private TagDao tagDao;
    private GiftCertificateDao giftCertificateDao;
    private GiftCertificateService giftCertificateService;
    private GiftCertificateMapper giftCertificateMapper;
    private UpdateGiftCertificateMapper updateGiftCertificateMapper;

    @BeforeEach
    void init() {
        tagDao = Mockito.mock(TagDaoImpl.class);
        giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);
        giftCertificateMapper = Mockito.mock(GiftCertificateMapper.class);
        updateGiftCertificateMapper = Mockito.mock(UpdateGiftCertificateMapper.class);
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDao, tagDao, giftCertificateMapper, updateGiftCertificateMapper);
    }

    @Test
    void certificateShouldBeCreated() throws DuplicateException, NoSuchEntityException {
        //when
        Mockito.when(giftCertificateMapper.mapToEntity(GIFT_CERTIFICATE_DTO)).thenReturn(GIFT_CERTIFICATE);
        Mockito.when(giftCertificateDao.findByName("test")).thenReturn(Optional.empty())
                .thenReturn(Optional.of(GIFT_CERTIFICATE));
        Mockito.when(giftCertificateMapper.mapToDto(GIFT_CERTIFICATE)).thenReturn(GIFT_CERTIFICATE_DTO);
        //then
        GiftCertificateDto actual = giftCertificateService.create(GIFT_CERTIFICATE_DTO);
        assertNotNull(actual);

    }

    @Test
    void createShouldThrowDuplicateException() {
        //when
        Mockito.when(giftCertificateMapper.mapToEntity(GIFT_CERTIFICATE_DTO)).thenReturn(GIFT_CERTIFICATE);
        Mockito.when(giftCertificateDao.findByName("test")).thenReturn(Optional.of(GIFT_CERTIFICATE));
        //then
        assertThrows(DuplicateException.class, () -> giftCertificateService.create(GIFT_CERTIFICATE_DTO));
    }

    @Test
    void createShouldThrowNoSuchEntityException(){
        //when
        Mockito.when(giftCertificateMapper.mapToEntity(GIFT_CERTIFICATE_DTO)).thenReturn(GIFT_CERTIFICATE);
        Mockito.when(giftCertificateDao.findByName("test"))
                .thenReturn(Optional.empty()).thenReturn(Optional.empty());
        //then
        assertThrows(NoSuchEntityException.class, () -> giftCertificateService.create(GIFT_CERTIFICATE_DTO));
    }

    @Test
    void findByIdShouldFindSomething() throws NoSuchEntityException {
        //when
        Mockito.when(giftCertificateDao.findById(ID)).thenReturn(Optional.of(GIFT_CERTIFICATE));
        Mockito.when(giftCertificateMapper.mapToDto(GIFT_CERTIFICATE)).thenReturn(GIFT_CERTIFICATE_DTO);
        GiftCertificateDto actual = giftCertificateService.findById(ID);
        //then
        assertNotNull(actual);
    }

    @Test
    void findByIdThrowNoSuchEntityException(){
        //when
        Mockito.when(giftCertificateDao.findById(ID)).thenReturn(Optional.empty());
        //then
        assertThrows(NoSuchEntityException.class, () -> giftCertificateService.findById(ID));
    }

    @Test
    void updateByIdReturnSomething() throws NoSuchEntityException {
        //when
        Mockito.when(updateGiftCertificateMapper.mapToEntity(UPDATE_GIFT_CERTIFICATE_DTO)).thenReturn(GIFT_CERTIFICATE);
        Mockito.when(giftCertificateDao.findById(ID))
                .thenReturn(Optional.of(GIFT_CERTIFICATE)).thenReturn(Optional.of(GIFT_CERTIFICATE));
        Mockito.when(giftCertificateMapper.mapToDto(GIFT_CERTIFICATE)).thenReturn(GIFT_CERTIFICATE_DTO);
        //then
        assertNotNull(giftCertificateService.updateById(ID, UPDATE_GIFT_CERTIFICATE_DTO));
    }

    @Test
    void updateByIdThrowNoSuchEntityException(){
        //when
        Mockito.when(updateGiftCertificateMapper.mapToEntity(UPDATE_GIFT_CERTIFICATE_DTO)).thenReturn(GIFT_CERTIFICATE);
        Mockito.when(giftCertificateDao.findById(ID)).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> giftCertificateService.updateById(ID, UPDATE_GIFT_CERTIFICATE_DTO));
    }

    @Test
    void replaceByIdShouldReturnSomething() throws NoSuchEntityException {
        //when
        Mockito.when(giftCertificateMapper.mapToEntity(GIFT_CERTIFICATE_DTO)).thenReturn(GIFT_CERTIFICATE);
        Mockito.when(giftCertificateDao.findById(ID))
                .thenReturn(Optional.of(GIFT_CERTIFICATE)).thenReturn(Optional.of(GIFT_CERTIFICATE));
        Mockito.when(giftCertificateMapper.mapToDto(GIFT_CERTIFICATE)).thenReturn(GIFT_CERTIFICATE_DTO);
        //then
        assertNotNull(giftCertificateService.replaceById(ID, GIFT_CERTIFICATE_DTO));
    }

    @Test
    void replaceByIdThrowNoSuchEntityException() {
        //when
        Mockito.when(giftCertificateMapper.mapToEntity(GIFT_CERTIFICATE_DTO)).thenReturn(GIFT_CERTIFICATE);
        Mockito.when(giftCertificateDao.findById(ID)).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> giftCertificateService.replaceById(ID, GIFT_CERTIFICATE_DTO));
    }

}
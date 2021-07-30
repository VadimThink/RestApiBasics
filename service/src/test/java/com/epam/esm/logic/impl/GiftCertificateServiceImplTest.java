package com.epam.esm.logic.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UpdateGiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.InvalidParametersException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.UpdateGiftCertificateMapper;
import com.epam.esm.query.SortingParametersValidator;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GiftCertificateServiceImpl.class)
public class GiftCertificateServiceImplTest {
    private static final long ID = 1;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 50;

    private static final GiftCertificate GIFT_CERTIFICATE = new GiftCertificate("test",
            "test", BigDecimal.TEN, 5,
            ZonedDateTime.now(), ZonedDateTime.now(), new ArrayList<Tag>());
    private static final GiftCertificateDto GIFT_CERTIFICATE_DTO = new GiftCertificateDto(ID, "test", "test", BigDecimal.TEN, 5,
            ZonedDateTime.now(), ZonedDateTime.now(), new ArrayList<TagDto>());

    @MockBean
    private GiftCertificateRepositoryImpl certificateRepository;
    @MockBean
    private TagRepositoryImpl tagRepository;
    @MockBean
    private SortingParametersValidator sortingParametersValidator;
    @MockBean
    private GiftCertificateMapper giftCertificateMapper;
    @MockBean
    private UpdateGiftCertificateMapper updateGiftCertificateMapper;

    @Autowired
    private GiftCertificateServiceImpl giftCertificateService;


    @Test
    public void testCreateShouldCreateWhenNotExist() throws DuplicateException, NoSuchEntityException {
        when(giftCertificateMapper.mapToEntity(GIFT_CERTIFICATE_DTO)).thenReturn(GIFT_CERTIFICATE);
        when(tagRepository.findByName(any())).thenReturn(Optional.empty());
        when(certificateRepository.create(GIFT_CERTIFICATE)).thenReturn(GIFT_CERTIFICATE);
        when(giftCertificateMapper.mapToDto(GIFT_CERTIFICATE)).thenReturn(GIFT_CERTIFICATE_DTO);
        assertEquals(GIFT_CERTIFICATE_DTO.getName(), giftCertificateService.create(GIFT_CERTIFICATE_DTO).getName());
    }

    @Test
    public void testGetAllShouldGetAll() throws InvalidParametersException, NoSuchEntityException {
        when(certificateRepository.getAllWithSortingFiltering(null, null, null,
                DEFAULT_PAGE, DEFAULT_PAGE_SIZE)).thenReturn(Collections.singletonList(GIFT_CERTIFICATE));
        when(giftCertificateMapper.mapListToDto(Collections.singletonList(GIFT_CERTIFICATE))).thenReturn(Collections.singletonList(GIFT_CERTIFICATE_DTO));
        List<GiftCertificateDto> giftCertificateDtos = giftCertificateService.findBySearchParams(null, null, null, null,
                DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
        assertEquals(1, giftCertificateDtos.size());
    }

    @Test
    public void testGetByIdShouldGetWhenFound() throws NoSuchEntityException {
        when(certificateRepository.findById(ID)).thenReturn(Optional.of(GIFT_CERTIFICATE));
        when(giftCertificateMapper.mapToDto(GIFT_CERTIFICATE)).thenReturn(GIFT_CERTIFICATE_DTO);
        assertEquals("test",giftCertificateService.findById(ID).getName());
    }


    @Test
    public void testUpdateByIdShouldUpdateWhenFound() throws NoSuchEntityException {
        when(certificateRepository.findById(ID)).thenReturn(Optional.of(GIFT_CERTIFICATE));
        UpdateGiftCertificateDto updateGiftCertificateDto = new UpdateGiftCertificateDto();
        updateGiftCertificateDto.setName("new name");
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("new name");
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName("new name");
        when(updateGiftCertificateMapper.mapToEntity(updateGiftCertificateDto)).thenReturn(giftCertificate);
        when(giftCertificateMapper.mapToDto(any())).thenReturn(giftCertificateDto);
        assertEquals("new name", giftCertificateService.updateById(ID,updateGiftCertificateDto).getName());
    }

}
package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.query.SortingParameters;
import com.epam.esm.repository.GiftCertificateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestJpaConfig.class)
@Transactional
public class GiftCertificateRepositoryImplTest {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    @Autowired
    private GiftCertificateRepository certificateRepository;

    @Test
    public void testCreateCertificateShouldCreate() {
        //given
        GiftCertificate giftCertificate = new GiftCertificate("name", "description", new BigDecimal(1), 1);
        //when
        GiftCertificate createdCertificate = certificateRepository.create(giftCertificate);
        //then
        assertNotNull(createdCertificate);
    }

    @Test
    public void testGetAllShouldGet() {
        //given
        //when
        List<GiftCertificate> giftCertificates = certificateRepository.getAll(DEFAULT_PAGE, DEFAULT_SIZE);
        //then
        assertNotEquals(0,giftCertificates.size());
    }

    @Test
    public void testGetAllWithSortingFilteringShouldGetSortedCertificates() {
        //given
        SortingParameters sortingParameters = new SortingParameters(
                Collections.singletonList("id"), Collections.singletonList("DESC"));
        //when
        List<GiftCertificate> giftCertificates = certificateRepository.getAllWithSortingFiltering(
                sortingParameters, null, null, DEFAULT_PAGE, DEFAULT_SIZE);
        //then
        assertNotEquals(0,giftCertificates.size());
    }

    @Test
    public void testGetAllWithFilteringShouldGetTwoCertificates() {
        //given
        //when
        List<GiftCertificate> giftCertificates = certificateRepository.getAllWithSortingFiltering(null,
                Collections.singletonList("certificate 3"), "certif", DEFAULT_PAGE, DEFAULT_SIZE);
        //then
        assertEquals(2, giftCertificates.size());
    }

    @Test
    public void testFindByIdShouldFind() {
        //given
        //when
        Optional<GiftCertificate> giftCertificate = certificateRepository.findById(1);
        //then
        assertEquals(1, giftCertificate.get().getId());
    }

    @Test
    public void testUpdateByIdShouldUpdate() {
        //given
        String savedName = "certificate 1";
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("new name");
        //when
        GiftCertificate updatedCertificate = certificateRepository.update(giftCertificate);
        //then
        assertEquals(updatedCertificate.getName(), "new name");
    }

}
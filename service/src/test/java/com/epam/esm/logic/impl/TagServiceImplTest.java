package com.epam.esm.logic.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TagServiceImpl.class)
public class TagServiceImplTest {
    private static final long ID = 1;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 50;

    private static final String NAME = "name";

    private static final TagDto TAG_DTO = new TagDto(ID, NAME);

    @MockBean
    private TagRepository tagRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private TagMapper tagMapper;

    @Autowired
    private TagServiceImpl tagService;

    @Test
    public void testCreateWhenAllGood() {
        when(tagRepository.findByName(NAME)).thenReturn(Optional.empty());
        Tag tag = new Tag();
        tag.setName(NAME);
        tag.setId(ID);
        when(tagMapper.mapToEntity(TAG_DTO)).thenReturn(tag);
        when(tagRepository.create(tag)).thenReturn(tag);
        when(tagMapper.mapToDto(tag)).thenReturn(TAG_DTO);
        assertNotNull(tagService.create(TAG_DTO));
    }

    @Test
    public void testGetAll() {
        Tag tag = new Tag();
        tag.setName(NAME);
        tag.setId(ID);
        when((tagRepository.getAll(DEFAULT_PAGE, DEFAULT_PAGE_SIZE))).thenReturn(Collections.singletonList(tag));
        when(tagMapper.mapListToDto(Collections.singletonList(tag))).thenReturn(Collections.singletonList(TAG_DTO));
        assertNotNull(tagService.getAll(DEFAULT_PAGE, DEFAULT_PAGE_SIZE));

    }

    @Test
    public void testGetById() {
        Tag tag = new Tag();
        tag.setName(NAME);
        tag.setId(ID);
        when(tagRepository.findById(ID)).thenReturn(Optional.of(tag));
        when(tagMapper.mapToDto(tag)).thenReturn(TAG_DTO);
        assertNotNull(tagService.getById(ID));
    }
}
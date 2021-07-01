package com.epam.esm.mapper;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TagMapperTest {

    @Test
    void tagToTagDto() {
        //given
        ApplicationContext context = new AnnotationConfigApplicationContext("mapper");
        Tag tag = new Tag(1, "name");
        //when
        TagDto tagDto = context.getBean(TagMapper.class).mapToDto(tag);
        //then
        assertEquals(tag.getId(), tagDto.getId());
        assertEquals(tag.getName(), tagDto.getName());
    }
}
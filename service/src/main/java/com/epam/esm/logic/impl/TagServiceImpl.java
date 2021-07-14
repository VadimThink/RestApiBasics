package com.epam.esm.logic.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.logic.TagService;
import com.epam.esm.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final TagMapper mapper;

    @Autowired
    public TagServiceImpl(TagDao tagDao, TagMapper mapper) {
        this.tagDao = tagDao;
        this.mapper = mapper;
    }

    @Override
    public TagDto create(TagDto tagDto) throws DuplicateException {
        String tagName = tagDto.getName();
        boolean isTagExist = tagDao.findByName(tagName).isPresent();
        if (isTagExist) {
            throw new DuplicateException("tag.exist");
        }
        Tag tag = mapper.mapToEntity(tagDto);
        tagDao.create(tag);
        Optional<Tag> optionalTag = tagDao.findByName(tagName);
        Tag newTag = optionalTag.orElse(new Tag());
        return mapper.mapToDto(newTag);
    }

    @Override
    public List<TagDto> getAll() {
        return mapper.mapListToDto(tagDao.getAll());
    }

    @Override
    public TagDto getById(long id) throws NoSuchEntityException {
        Optional<Tag> optionalTag = tagDao.findById(id);
        Tag tag = optionalTag.orElseThrow(() -> new NoSuchEntityException("tag.not.found"));
        return mapper.mapToDto(tag);
    }

    @Override
    public void deleteById(long id) {

    }
}

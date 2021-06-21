package com.epam.esm.logic.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.logic.TagService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TagServiceImpl implements TagService {
    @Override
    public long create(Tag tag) {
        return 0;
    }

    @Override
    public Set<Tag> getAll() {
        return null;
    }

    @Override
    public Tag getById(long id) {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }
}

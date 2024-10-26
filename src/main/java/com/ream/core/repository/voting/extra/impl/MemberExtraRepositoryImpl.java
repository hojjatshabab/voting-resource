package com.ream.core.repository.voting.extra.impl;

import com.ream.core.repository.voting.extra.MemberExtraRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
public class MemberExtraRepositoryImpl implements MemberExtraRepository {
    @Autowired
    EntityManager entityManager;


}
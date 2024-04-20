package com.hack.hackathon.service;

import com.hack.hackathon.entity.Event;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public class EventService extends AbstractCrudService<Event> {
    @NotNull
    @Override
    public Function1<Long, RuntimeException> supplierNotFound() {
        return null;
    }

    @NotNull
    @Override
    public JpaRepository getRepository() {
        return null;
    }
}

package com.msglearning.javabackend.converters;

import com.msglearning.javabackend.entity.Publisher;
import com.msglearning.javabackend.to.PublisherTO;

public class PublisherConverter {
    public PublisherTO convertToTo(Publisher publisher) {
        return PublisherTO.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .website(publisher.getWebsite())
                .build();
    }
    public Publisher convertToEntity(PublisherTO publisherTO) {
        return Publisher.builder()
                .id(publisherTO.getId())
                .name(publisherTO.getName())
                .website(publisherTO.getWebsite())
                .build();
    }
}

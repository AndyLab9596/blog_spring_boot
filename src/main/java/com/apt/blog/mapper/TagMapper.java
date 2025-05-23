package com.apt.blog.mapper;

import com.apt.blog.domain.PostStatus;
import com.apt.blog.domain.dtos.TagResponse;
import com.apt.blog.domain.entities.Post;
import com.apt.blog.domain.entities.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    TagResponse toTagResponse(Tag tag);

    @Named("calculatePostCount")
    default Integer calculatePostCount(Set<Post> posts) {
        if (null == posts) {
            return 0;
        }
        return (int) posts
                .stream()
                .filter((s) -> PostStatus.PUBLISHED.equals(s.getStatus()))
                .count();
    }
}

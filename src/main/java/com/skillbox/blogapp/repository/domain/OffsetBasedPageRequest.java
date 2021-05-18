package com.skillbox.blogapp.repository.domain;

import net.logstash.logback.encoder.org.apache.commons.lang3.builder.EqualsBuilder;
import net.logstash.logback.encoder.org.apache.commons.lang3.builder.HashCodeBuilder;
import net.logstash.logback.encoder.org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class OffsetBasedPageRequest implements Pageable {

    private long offset;
    private int limit;
    private final Sort sort;

    public OffsetBasedPageRequest(long offset, int limit, Sort sort) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }
        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }
        this.limit = limit;
        this.offset = offset;
        this.sort = sort;

    }

    @Override
    public int getPageNumber() {
        return (int) (offset / limit);
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new OffsetBasedPageRequest(getOffset() + getPageSize(), getPageSize(), getSort());
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return previousOrFirst();
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }

    public OffsetBasedPageRequest previous() {
        return hasPrevious() ? new OffsetBasedPageRequest(getOffset() - getPageSize(),
            getPageSize(), getSort()) : this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof OffsetBasedPageRequest)) return false;

        OffsetBasedPageRequest that = (OffsetBasedPageRequest) o;

        return new EqualsBuilder()
            .append(limit, that.limit)
            .append(offset, that.offset)
            .append(sort, that.sort)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(limit)
            .append(offset)
            .append(sort)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("limit", limit)
            .append("offset", offset)
            .append("sort", sort)
            .toString();
    }

}

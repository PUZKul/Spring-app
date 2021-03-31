package kul.pl.biblioteka.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNullApi;

import java.util.Objects;

import static kul.pl.biblioteka.utils.Constants.LIMIT;

public class LibraryPage implements Pageable {
    private int limit;
    private int offset;
    private final Sort sort;

    
    public LibraryPage(int offset, int limit, Sort sort) {
        if (offset < 0)
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        if (limit < 1)
            throw new IllegalArgumentException("Limit must not be less than one!");
        if(limit > 50 )
            throw new IllegalArgumentException(String.format("Given limit (%d) cannot be greater than %d", limit, LIMIT));
        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
    }

    public LibraryPage(int offset, int limit, Sort.Direction direction, String... properties) {
        this(offset, limit, Sort.by(direction, properties));
    }

    public LibraryPage(int offset, int limit) {
        this(offset, limit, Sort.unsorted());
    }

    @Override
    public int getPageNumber() {
        return offset / limit;
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
        return new LibraryPage((int) (getOffset() + getPageSize()), getPageSize(), getSort());
    }

    public LibraryPage previous() {
        return hasPrevious() ? new LibraryPage((int) (getOffset() - getPageSize()), getPageSize(), getSort()) : this;
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new LibraryPage(0, getPageSize(), getSort());
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibraryPage that = (LibraryPage) o;
        return limit == that.limit &&
                offset == that.offset &&
                Objects.equals(sort, that.sort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(limit, offset, sort);
    }

    @Override
    public String toString() {
        return "limit: " + limit + "offset: " + offset + "sort: " + sort;
    }


}

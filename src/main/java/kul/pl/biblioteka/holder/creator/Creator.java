package kul.pl.biblioteka.holder.creator;

import org.springframework.data.domain.Page;

import java.util.Collection;

public interface Creator<T, B> {
  Collection<T> create(Collection<B> page);
}

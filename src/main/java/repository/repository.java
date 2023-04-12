package repository;

import exception.RepositoryException;

public interface repository<E,ID>{
    void add(E entity);

    E findById(ID id) throws RepositoryException;

    Iterable<E> getAll();
}

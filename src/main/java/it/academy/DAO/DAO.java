package it.academy.DAO;

import java.io.Serializable;
import java.util.List;

public interface DAO<T extends Serializable, R> {

    T create(T obj);
    T get(R id);
    T update(T obj);
    boolean delete(R id);
    Long getCountOf();
    List<T> getPage(Integer countPerPage, Integer pageNum);

}
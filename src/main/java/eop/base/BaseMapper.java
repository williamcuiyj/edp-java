package eop.base;

import java.util.List;

/**
 * E 处理的实体类
 * Q 查询条件实体类
 */
public interface BaseMapper<E,Q> {
    int insert(E entity);
    int update(E entity);
    List<E> list(Q q);
    E getById(Integer id);
    int delete(Integer id);
}

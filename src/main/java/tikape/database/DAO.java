package tikape.database; 

import java.sql.SQLException;
import java.util.List;
public interface DAO<T, K> {
    
    List<T> findAll() throws SQLException;
    
    T findOne(K key) throws SQLException;
    
    void delete(K key) throws SQLException;
    
    void saveOrUpdate(T type) throws SQLException;
    
    void save(T type) throws SQLException;
    
    void update(T type) throws SQLException;
}

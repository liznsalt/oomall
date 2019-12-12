package xmu.oomall.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.oomall.domain.MallUser;

import java.util.List;

/**
 * @author liznsalt
 */
@Component
@Mapper
public interface UserMapper {
    int addUser(MallUser user);
    int deleteById(Integer id);
    int updateUser(MallUser user);
    MallUser findById(Integer user);
    List<MallUser> findByCondition(int page, int limit);

    List<MallUser> findByName(String name);
    List<MallUser> findByTelephone(String telephone);

    List<MallUser> getAllUsers();
}

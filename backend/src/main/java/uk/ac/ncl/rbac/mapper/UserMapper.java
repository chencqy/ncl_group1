package uk.ac.ncl.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import uk.ac.ncl.rbac.common.entity.User;

/**
 * User table (User) database access layer
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}

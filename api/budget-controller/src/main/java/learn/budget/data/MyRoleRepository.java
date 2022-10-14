package learn.budget.data;

import learn.budget.models.MyRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MyRoleRepository {
    List<MyRole> findAll();

    @Transactional
    MyRole findById(int roleID);
}

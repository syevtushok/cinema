package mate.academy.cinema.dao;

import mate.academy.cinema.model.Role;

public interface RoleDao {
    Role add(Role role);

    Role getRole(String role);
}

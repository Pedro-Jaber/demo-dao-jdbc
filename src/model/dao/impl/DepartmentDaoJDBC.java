package model.dao.impl;

import db.DB;
import db.DbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

    private final Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            st = conn.prepareStatement(
                    "INSERT INTO Department "
                            + "(Name) "
                            + "VALUES "
                            + "(?) ",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getName());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                rs = st.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }

            } else {
                throw new DbException("Unexpected error! No rows affexted!");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void update(Department obj) {
        PreparedStatement st = null;

        try {

            st = conn.prepareStatement(
                    "UPDATE department "
                            + "SET Name = ? "
                            + "WHERE Id = ?");

            st.setString(1, obj.getName());

            st.setInt(2, obj.getId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;

        try {

            st = conn.prepareStatement(
                    "DELETE FROM department "
                            + "WHERE ID = ? ");

            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("No registry with id " + id + " found");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Department findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            st = conn.prepareStatement(
                    "SELECT * "
                            + "FROM department "
                            + "WHERE Id = ? ");

            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()) {
                Department dep = instatiateDepartment(rs);

                return dep;
            }

            System.out.println("No registry with id " + id + " found");
            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Department instatiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();

        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("Name"));

        return dep;
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            st = conn.prepareStatement(
                    "SELECT * "
                            + "FROM Department "
                            + "ORDER BY Name ");

            rs = st.executeQuery();

            List<Department> list = new ArrayList<>();

            while (rs.next()) {

                Department department = instatiateDepartment(rs);

                list.add(department);
            }

            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

}

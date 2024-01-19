package model.dao.implementations;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection connection;

    public SellerDaoJDBC(Connection connection){
        this.connection = connection;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement preparedStatement = null;

        try{
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO seller " +
                    "(Name,Email,BirthDate,BaseSalary,DepartmentId) " +
                    "VALUES (?,?,?,?,?);",
                    Statement.RETURN_GENERATED_KEYS
            );

            preparedStatement.setString(1,obj.getName());
            preparedStatement.setString(2, obj.getEmail());
            preparedStatement.setDate(3, java.sql.Date.valueOf(obj.getBirthDate()));
            preparedStatement.setDouble(4, obj.getBaseSalary());
            preparedStatement.setInt(5, obj.getDepartment().getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected > 0){
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next()){
                    int id = resultSet.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(resultSet);
            }
            else{
                throw new DbException("Error: The Affected rows is under 0!");
            }
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public void update(Seller obj) {
        PreparedStatement preparedStatement = null;

        try{
            preparedStatement = connection.prepareStatement(
                    "UPDATE seller SET " +
                    "Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
                    "WHERE seller.Id = ?;"
            );

            preparedStatement.setString(1,obj.getName());
            preparedStatement.setString(2, obj.getEmail());
            preparedStatement.setDate(3, java.sql.Date.valueOf(obj.getBirthDate()));
            preparedStatement.setDouble(4, obj.getBaseSalary());
            preparedStatement.setInt(5, obj.getDepartment().getId());
            preparedStatement.setInt(6,obj.getId());

            preparedStatement.executeUpdate();

        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*, department.name AS DepartmentName " +
                    "FROM seller " +
                    "INNER JOIN department ON department.Id = seller.DepartmentId " +
                    "WHERE seller.Id = ?;"
            );

            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Department department = instantiateDepartment(resultSet);
                return instantiateSeller(resultSet, department);
            }
            return null;
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*, department.name AS DepartmentName " +
                            "FROM seller " +
                            "INNER JOIN department ON department.Id = seller.DepartmentId " +
                            "ORDER BY seller.Id;"
            );

            resultSet = preparedStatement.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while(resultSet.next()){

                Department dep = map.get(resultSet.getInt("DepartmentId"));

                if(dep == null){
                    dep = instantiateDepartment(resultSet);
                    map.put(resultSet.getInt("DepartmentId"),dep);
                }
                list.add(instantiateSeller(resultSet,dep));
            }
            return list;
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*, department.name AS DepartmentName " +
                            "FROM seller " +
                            "INNER JOIN department ON department.Id = seller.DepartmentId " +
                            "WHERE seller.DepartmentId = ? " +
                            "ORDER BY seller.Name;"
            );

            preparedStatement.setInt(1,department.getId());
            resultSet = preparedStatement.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while(resultSet.next()){

                Department dep = map.get(resultSet.getInt("DepartmentId"));

                if(dep == null){
                    dep = instantiateDepartment(resultSet);
                    map.put(resultSet.getInt("DepartmentId"),dep);
                }
                list.add(instantiateSeller(resultSet,dep));
            }
            return list;
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException{
        return new Seller(resultSet.getInt("Id"),resultSet.getString("Name"),resultSet.getString("Email"),resultSet.getDate("BirthDate").toLocalDate(),resultSet.getDouble("BaseSalary"),department);

    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException{
        return new Department(resultSet.getInt("DepartmentId"),resultSet.getString("DepartmentName"));
    }
}

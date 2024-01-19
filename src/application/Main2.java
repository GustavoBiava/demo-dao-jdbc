package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("Test 1 - Department insert: ");
        Department department = new Department(null, "Food");
        departmentDao.insert(department);
        System.out.println("Inserted successfully! Id = " + department.getId());

        System.out.println();
        System.out.println("Test 2 - Department update: ");
        Department dep = departmentDao.findById(1);
        dep.setName("ALTERADO");
        departmentDao.update(dep);
        System.out.println("Updated with success!");

        System.out.println();
        System.out.println("Test 3 - Department deleteById: ");
        System.out.print("Enter a Id to delete: ");
        int id = sc.nextInt();
        departmentDao.deleteById(id);
        System.out.println("Deleted with success!");

        System.out.println();
        System.out.println("Test 4 - Department findById: ");
        dep = departmentDao.findById(4);
        System.out.println(dep);

        System.out.println();
        System.out.println("Test 5 - Department findAll: ");
        List<Department> list = departmentDao.findAll();
        list.forEach(System.out::println);


    }
}

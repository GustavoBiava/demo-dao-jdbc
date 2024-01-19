package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("Test 1 - Seller FindById: ");
        Seller seller = sellerDao.findById(7);
        System.out.println(seller);

        System.out.println();
        System.out.println("Test 2 - Seller FindByDepartment: ");
        List<Seller> list = sellerDao.findByDepartment(new Department(1,null));
        list.forEach(System.out::println);

    }
}

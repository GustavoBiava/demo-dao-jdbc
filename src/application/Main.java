package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("Test 1 - Seller FindById: ");
        Seller seller = sellerDao.findById(7);
        System.out.println(seller);

        System.out.println();
        System.out.println("Test 2 - Seller FindByDepartment: ");
        List<Seller> list = sellerDao.findByDepartment(new Department(2,null));
        list.forEach(System.out::println);

        System.out.println();
        System.out.println("Test 3 - Seller FindAll: ");
        list = sellerDao.findAll();
        list.forEach(System.out::println);

        System.out.println();
        System.out.println("Test 4 - Seller insert: ");
        Seller newSeller = new Seller(null, "Nélio", "nelio@gmail.com", LocalDate.now(), 15250.00,new Department(2,null));
        sellerDao.insert(newSeller);
        System.out.println("Done! The seller ID is: " + newSeller.getId());

        System.out.println();
        System.out.println("Test 4 - Seller update: ");
        seller = sellerDao.findById(1);
        seller.setName("Fernando");
        sellerDao.update(seller);
        System.out.println("Update done!");


    }
}

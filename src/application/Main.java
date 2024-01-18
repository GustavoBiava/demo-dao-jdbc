package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Department department = new Department(1,"Books");

        Seller seller = new Seller(14,"Cristiano", "cristiano@gmail.com", LocalDate.now(),5000.00, department);

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println(seller);

    }
}

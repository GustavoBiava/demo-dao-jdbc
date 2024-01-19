package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Main {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("Test 1 - Seller FindById: ");
        Seller seller = sellerDao.findById(7);
        System.out.println(seller);

    }
}

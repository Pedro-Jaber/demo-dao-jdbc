package application;

import java.util.List;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: sellet findById ===");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);
        System.out.println("");

        System.out.println("=== TEST 2: sellet findByDepartment ===");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.finfByDepartment(department);
        System.out.println("Seller by Department");
        for (Seller obj : list) {
            System.out.println(obj);
        }
        System.out.println("");

        System.out.println("=== TEST 3: sellet findAll ===");
        list = sellerDao.findAll();
        System.out.println("Seller by Department");
        for (Seller obj : list) {
            System.out.println(obj);
        }
        System.out.println("");

    }

}

package org.smart4j.chapter1.Service;



import com.mysql.jdbc.log.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter1.Helper.DatabaseHelper;
import org.smart4j.chapter1.Model.Customer;
import org.smart4j.chapter1.Util.ProsUtil;


import java.sql.*;
import java.util.*;


/**
 * Created by Administrator on 2017/5/9.
 */
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);



    public  List<Customer> getCustomerList(){
        List<Customer> customersList = new ArrayList<>();
        String sql = "select * from customer";
        customersList = DatabaseHelper.quertEntityList(Customer.class,sql);
        return  customersList;
    }

    public  Customer getCustomer(Long id ){

        return  new Customer();
    }

    public  boolean createCustomer(){
        Customer c = new Customer();
        c.setContact("1111");
        c.setEmail("2222");
        c.setName("3333");
        c.setRemark("4444");
        c.setTelephone("5555");
        Map<String,Object> m = new HashMap<>();
        m.put("name","1111111");
        boolean b = DatabaseHelper.insertEntity(Customer.class,m);
        return  false;
    }

    public  boolean updateCustomer(Long id , Map<String,Object> fieldMap){
        return  false;
    }

    public  boolean deleteCustomer(Long id){
        return  false;
    }
}

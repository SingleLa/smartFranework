package org.smart4j.chapter1.Service;

import org.junit.Test;
import org.smart4j.chapter1.Model.Customer;
import org.smart4j.chapter1.Util.ProsUtil;

import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2017/5/31.
 */
public class test {
    private  final  CustomerService customerService;

    public test(){
        customerService = new CustomerService();
    }
    @Test
    public void test1(){
       boolean b = customerService.createCustomer();

    }
}

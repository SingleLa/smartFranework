package org.smart4j.chapter1.Service;

import org.junit.Test;
import org.smart4j.chapter1.Annotation.Inject;
import org.smart4j.chapter1.Helper.ConfigHelper;
import org.smart4j.chapter1.Model.Customer;
import org.smart4j.chapter1.Util.ClassUtil;
import org.smart4j.chapter1.Util.ProsUtil;

import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Created by Administrator on 2017/5/31.
 */
public class test {
    @Inject
    private CustomerService customerService;
    @Test
    public void test1(){
        List<Customer> list = customerService.getCustomerList();
        System.out.print(list);

    }
}

package org.smart4j.chapter1.Controller;

import org.smart4j.chapter1.Annotation.Action;
import org.smart4j.chapter1.Annotation.Controller;
import org.smart4j.chapter1.Annotation.Inject;
import org.smart4j.chapter1.Model.Customer;
import org.smart4j.chapter1.Model.Data;
import org.smart4j.chapter1.Service.CustomerService;

import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 */
@Controller
public class CustomerCreate {
    @Inject
    private CustomerService customerService;
    @Action("get:/customer")
    public Data getcustom(){
        List<Customer> list = customerService.getCustomerList();
        return new Data(list);
    }
}

package com.arpit;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

@Controller
public class WebController {

    Logger logger = LoggerFactory.getLogger(WebController.class);

    @GetMapping("/")
    public String blank_index() {
        return "check";
    }

    @GetMapping("/check")
    public String check() {
        return "check";
    }

    @Autowired
    MongoTemplate mongoTemplate;

    @RequestMapping(value={"/search"},method = RequestMethod.GET)
    @ResponseBody
    public List<Customer> search(@RequestParam Map<String,String> allRequestParams, ModelMap model) {

        Query searchUserQuery = new Query();

        for(Map.Entry<String,String> entry : allRequestParams.entrySet())
        {
            if(entry.getValue()==null)
                entry.setValue("");
            searchUserQuery.addCriteria(Criteria.where(entry.getKey()).is(entry.getValue()));
        }

        Criteria criteria1 = Criteria.where("status").is("G"),
        criteria2 = Criteria.where("status").is("W"),
        criteria3 = Criteria.where("status").is("A");

        searchUserQuery.addCriteria(new Criteria().orOperator(criteria1,criteria2,criteria3));
        searchUserQuery.with(new Sort(new Sort.Order(Sort.Direction.ASC, "token")));

        List<Customer> list = mongoTemplate.find(searchUserQuery,Customer.class,"amitynoida");
        //for(Customer bean : list)
        {

        }
        //return context.getBean(MyBean.class).topic;
        return list;
    }


}
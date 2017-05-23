package in.gvc;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;

@CrossOrigin
@Controller
public class WebController {

    Logger logger = LoggerFactory.getLogger(WebController.class);

    @GetMapping("/")
    public String blank_index() {
        return "xyz";
    }

    @GetMapping("/check")
    public String check() {
        return "xyz2";
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
        criteria3 = Criteria.where("status").is("A"),
        criteria4 = Criteria.where("status").is("O");

        searchUserQuery.addCriteria(new Criteria().orOperator(criteria1,criteria2,criteria3,criteria4));
        searchUserQuery.with(new Sort(new Sort.Order(Sort.Direction.ASC, "token")));

        List<Customer> list = mongoTemplate.find(searchUserQuery,Customer.class,"amitynoida");
        //for(Customer bean : list)
        {

        }
        //return context.getBean(MyBean.class).topic;
        return list;
    }

    @RequestMapping(value={"/summary"},method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Summary> searchFilter(@RequestParam (value="from")String from,@RequestParam(value="to")String to) throws ParseException {

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date date_from = df.parse(from);
        Date date_to = df.parse(to);
        Calendar c = Calendar.getInstance();
        c.setTime(date_to);
        c.add(Calendar.DATE,1);
        date_to = c.getTime();
        c.setTime(date_from);


        ArrayList<Summary> final_list = new ArrayList<Summary>();
        //System.out.println(date_from.toString());
        //System.out.println(date_to.toString());

        for(;!date_from.toString().equals(date_to.toString());date_from = c.getTime())
        {
            Query searchUserQuery = new Query();

            Criteria criteria = new Criteria();
            criteria.orOperator(Criteria.where("gdate").is(df.format(date_from)));

            //System.out.println(df.format(date_from));

            searchUserQuery.addCriteria(criteria);
            List<Customer> list = mongoTemplate.find(searchUserQuery,Customer.class,"amitynoida");
            long total=0;
            DateFormat temp = new SimpleDateFormat("mm:ss");
            for(Customer cus : list)
            {
                if(!cus.status.equals("O"))
                    continue;
                StringBuilder builder = new StringBuilder(cus.total+"");
                String min = builder.substring(0,builder.indexOf(":"));
                builder.delete(0,builder.indexOf(":")+1);
                String sec = builder.toString();

                total += Integer.parseInt(min)*60 + Integer.parseInt(sec);

            }
            //System.out.println(list.size());
            Summary summary = new Summary();
            //System.out.println("Total: "+total);

            long min = total/60;
            long sec = total%60;
            long hour = min/60;
            min = min %60;
            summary.total_time = hour+":"+min+":"+sec;
            summary.token = list.size()+"";
            summary.date = df.format(date_from);

            if(!summary.token.equals("0"))
                final_list.add(summary);

            c.add(Calendar.DATE,1);
        }

        //searchUserQuery.with(new Sort(new Sort.Order(Sort.Direction.ASC, "token")));

        //return context.getBean(MyBean.class).topic;
        return final_list;
    }
    @RequestMapping(value={"/monitor"},method = RequestMethod.GET)
    @ResponseBody
    public List<Customer> monitor(@RequestParam Map<String,String> allRequestParams, ModelMap model) throws ParseException {

        Query searchUserQuery = new Query();

        for(Map.Entry<String,String> entry : allRequestParams.entrySet())
        {
            if(entry.getValue()==null)
                entry.setValue("");
            searchUserQuery.addCriteria(Criteria.where(entry.getKey()).is(entry.getValue()));
        }

        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date current_date = new Date();
        String current_date_str = format.format(current_date);

        Criteria criteria2 = Criteria.where("status").is("W"),
                criteria3 = Criteria.where("status").is("A"),
                criteria4 = Criteria.where("gdate").is(current_date_str);

        searchUserQuery.addCriteria(new Criteria().orOperator(criteria2,criteria3,criteria4));
        searchUserQuery.with(new Sort(new Sort.Order(Sort.Direction.ASC, "token")));

        List<Customer> list = mongoTemplate.find(searchUserQuery,Customer.class,"amitynoida");
        for(Customer bean : list)
        {
            DateFormat df =new SimpleDateFormat("HH:mm:ss");
            Date date=null;
            if(bean.status.equals("W"))
                date = df.parse(bean.wtime);
            else
                date = df.parse(bean.atime);

            Date current = df.parse(df.format(new Date()));
            long diff = current.getTime() - date.getTime();
            diff=diff/1000;
            String time_inside = diff/60 + ":"+diff%60;
            bean.time_inside = time_inside;

        }
        //return context.getBean(MyBean.class).topic;
        return list;
    }
}
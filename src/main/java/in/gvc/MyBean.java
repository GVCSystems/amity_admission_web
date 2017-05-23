package in.gvc;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@Service
public class MyBean {
    public String topic;

    @Autowired
    MongoTemplate mongoTemplate;

    @PostConstruct
    public void main() {

        topic = "amitynoida";
        String content = "Sent by arpit from java application";
        int qos = 1;
        String broker = "tcp://m12.cloudmqtt.com:16479";

        //MQTT client id to use for the device. "" will generate a client id automatically
        String clientId = "";

        MemoryPersistence persistence = new MemoryPersistence();

        MqttClient mqttClient = null;
        try {
            mqttClient = new MqttClient(broker, clientId, persistence);
            mqttClient.setCallback(new MqttCallback() {

                public void messageArrived(String topic, MqttMessage msg)
                        throws Exception {
                    //System.out.println("Recived:" + topic);
                    insert_in_db(new String(msg.getPayload()));
                }

                public void deliveryComplete(IMqttDeliveryToken arg0) {
                    System.out.println("Delivery complete");
                }

                public void connectionLost(Throwable arg0) {
                    // TODO Auto-generated method stub
                }
            });

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName("sxdzesyk");
            connOpts.setPassword("dc_pY7Q7gOTw".toCharArray());
            mqttClient.connect(connOpts);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            //System.out.println("Publish message: " + message);
            mqttClient.subscribe(topic, qos);
            //mqttClient.publish(topic, message);
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }

    public void insert_in_db(String str) throws ParseException {
        StringBuilder builder = new StringBuilder(str);
        if (!(builder.substring(0, 1).equals("*") &&
                builder.substring(builder.length() - 1, builder.length()).equals("#"))) {
            return;
        }

        String cabin = builder.subSequence(1, builder.indexOf(",")).toString();
        builder.delete(0, builder.indexOf(",") + 1);
        String token = builder.substring(0, builder.indexOf(",")).toString();
        builder.delete(0, builder.indexOf(",") + 1);
        String status = builder.substring(0, builder.length() - 1);

        Customer customer = new Customer();
        customer.cabin = cabin;
        customer.token = token;
        customer.status = status;

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();


        System.out.println(str);
        if (status.equals("G")) {
            customer.gtime = dateFormat.format(date).toString();

            dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            customer.gdate = dateFormat.format(date).toString();

            mongoTemplate.insert(customer, "amitynoida");
        } else {


            Query searchUserQuery = new Query();
            Criteria criteria = Criteria.where("token").is(token);
            searchUserQuery.addCriteria(criteria);

            Update update = new Update();
            update.set("cabin", cabin);
            update.set("status", status);

            if (status.equals("W")) {
                customer.wtime = dateFormat.format(date).toString();
                dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                customer.wdate = dateFormat.format(date).toString();
                update.set("wdate", customer.wdate);
                update.set("wtime", customer.wtime);
            } else if (status.equals("A")) {
                customer.atime = dateFormat.format(date).toString();
                dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                customer.adate = dateFormat.format(date).toString();
                update.set("atime", customer.atime);
                update.set("adate", customer.adate);
            } else if (status.equals("O")) {
                customer.otime = dateFormat.format(date).toString();
                dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                customer.odate = dateFormat.format(date).toString();
                update.set("otime", customer.otime);
                update.set("odate", customer.odate);

                Customer cus = mongoTemplate.findOne(searchUserQuery,Customer.class,"amitynoida");

                DateFormat df = new SimpleDateFormat("HH:mm:ss");

                Date date_from = df.parse(cus.wtime);
                Date date_to = df.parse(customer.otime);

                long diff = date_to.getTime() - date_from.getTime();
                String total=diff/1000+"";
                if(diff > 60)
                {
                    int min = Integer.parseInt(diff/60000+"");
                    int sec = Integer.parseInt((diff/1000)%60+"");
                    total = min+":"+sec;
                }

                //String total = df.format(date_from);
                update.set("total",total);

            }

            mongoTemplate.updateFirst(searchUserQuery, update, "amitynoida");

        }
        /*else if(status.equals("A"))
        {
            Query searchUserQuery = new Query();
            Criteria criteria = Criteria.where("token").is(token);
            searchUserQuery.addCriteria(criteria);

            Update update = new Update();
            update.addToSet("cabin",cabin);
            update.addToSet("status",status);

            mongoTemplate.updateFirst(searchUserQuery,update,"amitynoida");
        }*/
    }
}
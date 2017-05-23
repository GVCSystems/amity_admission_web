package in.gvc;

/**
 * Created by arpit on 22/5/17.
 */
public class Summary {
    public String token,total_time,date;

    public void setTotal_time(String total_time)
    {this.total_time = total_time;}
    public void setToken(String token)
    {
        this.token = token;
    }
    public void setDate(String date){this.date = date;}

    public String getToken(){return this.token;}
    public String getTotal_time(){return this.total_time;}
    public String getDate(){return this.date;}
}

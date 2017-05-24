package in.gvc;

/**
 * Created by arpit on 24/5/17.
 */
public class Cabin {

    String cabin,date;
    String time_served;
    int total_token;

    public void setCabin(String cabin)
    {this.cabin = cabin;}
    public void setTotal_token(int total_token)
    {this.total_token = total_token;}
    public void setDate(String date)
    {this.date = date;}
    public void setTime_served(String time_served)
    {this.time_served  = time_served;}

    public String getCabin(){return this.cabin;}
    public int getTotal_token(){return this.total_token;}
    public String getDate(){return this.date;}
    public String getTime_served(){return this.time_served;}

    public void add_seconds_to_time(long seconds)
    {
        StringBuilder builder = new StringBuilder(time_served);
        String min = builder.substring(0,builder.indexOf(":"));
        builder.delete(0,builder.indexOf(":")+1);
        String sec = builder.toString();
        long total_sec = Integer.parseInt(min)*60 + Integer.parseInt(sec) + seconds;

        time_served = total_sec/60 + ":" + total_sec%60;
    }
}

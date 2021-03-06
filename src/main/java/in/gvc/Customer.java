package in.gvc;

import org.springframework.data.annotation.Id;

/**
 * Created by arpit on 14/5/17.
 */
public class Customer {

    String cabin,token,status,gtime,wtime,atime,otime,gdate,wdate,adate,odate;
    String total="0";
    String time_inside;

    public void setCabin(String cabin)
    {this.cabin=cabin;}

    public void setToken(String token)
    {this.token=token;}

    public void setStatus(String status)
    {this.status=status;}

    public void setgtime(String gtime)
    {this.gtime = gtime;}

    public void setwtime(String wtime)
    {this.wtime = wtime;}

    public void setatime(String atime)
    {this.atime = atime;}

    public void setotime(String otime)
    {this.otime = otime;}

    public void setodate(String odate)
    {this.otime = odate;}

    public void setadate(String adate)
    {this.adate = adate;}

    public void setgdate(String gdate)
    {this.gdate = gdate;}

    public void setwdate(String wdate)
    {this.wdate = wdate;}

    public void settotal(String total)
    {this.total = total;}

    public void setTime_inside(String time_inside)
    {this.time_inside = time_inside;}

    public String getotime(){return this.otime;}
    public String getatime(){return this.atime;}
    public String getwtime(){return this.wtime;}
    public String getgtime(){return this.gtime;}
    public String getStatus(){return this.status;}
    public String getCabin(){return this.cabin;}
    public String getToken(){return this.token;}
    public String getgdate(){return this.gdate;}
    public String getodate(){return this.odate;}
    public String getwdate(){return this.wdate;}
    public String getadate(){return this.adate;}
    public String getTotal(){return this.total;}
    public String getTime_inside(){return this.time_inside;}

    public long total_in_seconds()
    {
        StringBuilder builder = new StringBuilder(total);
        String min = builder.substring(0,builder.indexOf(":"));
        builder.delete(0,builder.indexOf(":")+1);
        String sec = builder.toString();
        long seconds = Integer.parseInt(min)*60 + Integer.parseInt(sec);
        return seconds;
    }
}

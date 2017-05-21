package in.gvc;

import org.springframework.data.annotation.Id;

/**
 * Created by arpit on 14/5/17.
 */
public class Customer {

    String cabin,token,status,Gtime,Wtime,Atime,Otime;

    public void setCabin(String cabin)
    {this.cabin=cabin;}
    public void setToken(String token)
    {this.token=token;}
    public void setStatus(String status)
    {this.status=status;}
    public void setGtime(String Gtime)
    {this.Gtime = Gtime;}
    public void setWtime(String Wtime)
    {this.Wtime = Wtime;}
    public void setAtime(String Atime)
    {this.Atime=Atime;}
    public void setOtime(String Otime)
    {this.Otime = Otime;}
    public String getOtime(){return this.Otime;}
    public String getAtime(){return this.Atime;}
    public String getWtime(){return this.Wtime;}
    public String getGtime(){return this.Gtime;}
    public String getStatus(){return this.status;}
    public String getCabin(){return this.cabin;}
    public String getToken(){return this.token;}
}

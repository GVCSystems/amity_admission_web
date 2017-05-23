function getjson(){


        var http_request = new XMLHttpRequest();
        http_request.onreadystatechange = function(){

            if (http_request.readyState == 4  ){
                // Javascript function JSON.parse to parse JSON data
                var jsonObj = JSON.parse(http_request.responseText);

                // jsonObj variable now contains the data structure and can
                // be accessed as jsonObj.name and jsonObj.country.
                document.getElementById("mydiv2").innerHTML="<table  id=\"table_data\" class=\"table table-hover\"> <th>Token</th> <th>cabin</th> <th>status</th><th>In Time</th><th>Out Time</th> <th> Total Time Taken</th>";
                for(i=0;i<jsonObj.length;i++)
                {
                    var token = jsonObj[i].token;
                    var cabin = jsonObj[i].cabin;
                    var status = jsonObj[i].status;

                    if(status === "G")
                    {
                        $("#table_data").append("<tr> <td class=\"\" > "+token+"  </td>"+
                        "<td class=\"\" >--</td>"+
                        "<td class=\"\" >Please Wait for your turn</td>"+
                            "<td class=\"\" >"+jsonObj[i].gtime+"</td>"+
                            "<td class=\"\" >"+jsonObj[i].otime+"</td>"+
                        "</tr>");
                    }
                    else if(status === "W")
                    {
                        $("#table_data").append("<tr> <td class=\"warning\" > "+token+"  </td>"+
                            "<td class=\"warning\" >"+cabin+"</td>"+
                            "<td class=\"warning\" > Go inside the cabin  </td>"+
                            "<td class=\"\" >"+jsonObj[i].gtime+"</td>"+
                            "<td class=\"\" >"+jsonObj[i].otime+"</td>"+
                            "</tr>");
                    }
                    else if(status === "A")
                    {
                        $("#table_data").append("<tr> <td class=\"success\" > "+token+"  </td>"+
                            "<td class=\"success\" >"+cabin+" </td>"+
                            "<td class=\"success\" > You are being Attended </td>"+
                            "<td class=\"\" >"+jsonObj[i].gtime+"</td>"+
                            "<td class=\"\" >"+jsonObj[i].otime+"</td>"+
                            "</tr>");
                    }
                    else if(status === "O")
                    {
                        $("#table_data").append("<tr> <td class=\"success\" > "+token+"  </td>"+
                            "<td class=\"success\" >"+cabin+" </td>"+
                            "<td class=\"success\" >Attended </td>"+
                            "<td class=\"\" >"+jsonObj[i].gtime+"</td>"+
                            "<td class=\"\" >"+jsonObj[i].otime+"</td>"+
                            "<td class=\"\" >"+jsonObj[i].total+"</td>"+

                            "</tr>");
                    }
                }
                document.getElementById("mydiv2").innerHTML=document.getElementById("mydiv2").innerHTML+"</table>";
            }
        }
        http_request.open("GET", "http://"+location.hostname+":"+location.port+"/search", true);
        http_request.send();

    };
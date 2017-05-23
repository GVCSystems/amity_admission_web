function getjson(){


    var http_request = new XMLHttpRequest();
    http_request.onreadystatechange = function(){

        if (http_request.readyState == 4  ){
            // Javascript function JSON.parse to parse JSON data
            var jsonObj = JSON.parse(http_request.responseText);

            // jsonObj variable now contains the data structure and can
            // be accessed as jsonObj.name and jsonObj.country.
            document.getElementById("mydiv2").innerHTML="<table id=\"table_data\" class=\"table table-hover\">"+
            "<th>Date</th>"+
            "<th>Total Token</th>"+
            "<th>Total Time</th>";
            for(i=0;i<jsonObj.length;i++)
            {
                var token = jsonObj[i].token;
                var total_time = jsonObj[i].total_time;
                var date = jsonObj[i].date;


                {
                    $("#table_data").append("<tr> <td  > "+date+"  </td>"+
                        "<td>"+token+" </td>"+
                        "<td>"+total_time+" </td>"+

                        "</tr>");
                }
            }
            document.getElementById("mydiv2").innerHTML=document.getElementById("mydiv2").innerHTML+"</table>";
        }
    }

    var date_from = document.getElementsById("date_from").value;
    http_request.open("GET", "http://"+location.hostname+":"+location.port+"/summary?from=2017/05/22&to=2017/05/23", true);
    http_request.send();

};
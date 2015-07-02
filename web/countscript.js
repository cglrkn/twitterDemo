/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    function GetParam(username)
{
  var start=location.search.indexOf("?"+username+"=");
  if (start<0) start=location.search.indexOf("&"+username+"=");
  if (start<0) return '';
  start += username.length+2;
  var end=location.search.indexOf("&",start)-1;
  if (end<0) end=location.search.length;
  var result='';
  for(var i=start;i<=end;i++) {
    var c=location.search.charAt(i);
    result=result+(c=='+'?' ':c);
  }
  return unescape(result);
}
        
    $.post("followerCnt", {}, 
            function(data) {
                   data = data.replace(" ", "\n");
                    $( "#friendList" ).text( data );
                     
            });
     
    });

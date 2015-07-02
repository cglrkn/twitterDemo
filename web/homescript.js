/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    
    $.extend({
  getUrlVars: function(){
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split(' ');
    for(var i = 0; i < hashes.length; i++)
    {
      hash = hashes[i].split('=');
      vars.push(hash[0]);
      vars[hash[0]] = hash[1];
    }
    
    return vars;
  },
  getUrlVar: function(name){
    return $.getUrlVars()[name];
  }
});

var username1 = $.getUrlVar("username");
document.getElementById("namep").innerHTML = username1;
    
    $.post("tweetList", { username: username1}, 
            function(data) {
    var d = window.document,
            
    elm = d.getElementById('newsfeed'),
    html = '<table>',
    classes = data.split('\n'),
    i = 0;

        for(; classes[i]; i++) {
                html += '<tr><td>' + classes[i] +'</td></tr>';
        }

        elm.innerHTML = html + '</table>';
        $("#send").click(function(){ 
            location.reload();
    });
    });
            
    $.post("FollowingCnt", { username: username1}, 
            function(data) {
   
        document.getElementById("infosec").innerHTML = "Following: " + data + " people";
      
    });
    $("#ff").click(function (){
        window.location.href = "http://localhost:8080/TwitterDemo/findFriends.html?username="+username1;
    });
            
    $("#send").click(function() {
        var tweet = $("#tweet").val();
               
        if (tweet == '') {
        alert("You cannot send an empty post!");
        }else {
            $.post("home", { username: username1, tweet: tweet}, 
            function(data) {
                   alert("sent: " + data)
            });
            
        }
        
    });
    $("#logoutb").click(function() {
        window.location.href = "http://localhost:8080/TwitterDemo/loginhtml.html";
    });
     
   
});

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
            $.post("fList", {username: username1}, 
            function(data) {
                   
        var d = window.document,

        elm = d.getElementById('test'),
        html = '<ul>',
        classes = data.split(' '),
        i = 0;
               
        for(; classes[i]; i++) {
                html += '<li>' + classes[i] + '</li>';
             
        }
          
        elm.innerHTML = html + '</ul>';
                     
            });
             $.post("FollowingCnt", { username: username1}, 
            function(data) {
   
        document.getElementById("infosec").innerHTML = "No of Following: " + data;
      
    });
         
     $("#filter").click(function() {
         var filtered = $("#filtered").val()   
            $.post("filterFriends", { filtered: filtered}, 
            function(data) {
           document.getElementById("test").innerHTML = data;
                     
            });
            });
                    
    });
     
     
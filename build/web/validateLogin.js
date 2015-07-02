$(document).ready(function() {
   
 var i = 1;
 
  setInterval(function(){
     var images = ["https://dorindaduclos.files.wordpress.com/2014/08/sunrise-above-the-clouds-nature_112825.jpg", "https://dorindaduclos.files.wordpress.com/2014/08/pink-clouds.jpg", "http://wallpapercolor.net/wallpapers/blue-pink-background-wallpaper-14157.jpg" ];
    
      document.body.style.backgroundImage = "url('" + images[i] + "')";
    i++;
    if(i== images.length)
    {
        i=0;
    }
  }, 5000);
    $("#submit").click(function() {
        var email = $("#email").val();
        var password = $("#password").val();
        
        if (email == '' || password == '' ) {
        alert("Please fill all fields!");
        }else {
            $.post("login", { email: email, password: password}, 
            function(data) {
                                    
                         alert(data);
                    
                 
            });
        }
    });
    
    $("#register").click(function() {
        var username1 = $("#username1").val();
        var password1 = $("#password1").val();
        var fullname = $("#fullname").val();
        
        if (username1 == '' || password1 == '' || fullname == '') {
        alert("Please fill all fields!");
        }else {
            $.post("register", { username1: username1, password1: password1, fullname: fullname}, 
            function(data) {
                    if(username1.length < 3){
                        alert("Username must contain at least 3 characters")
                    }
                    else{
                     if(data == 'This username is already taken!')
                         alert(data);
                     else
                         window.location.href = "http://localhost:8080/TwitterDemo/loginAfterRegister.html";
                    }
            });
            
        }
    });
   
});
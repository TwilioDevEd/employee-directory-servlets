$(document).ready(function(){
    $("form").submit(function(e){
        e.preventDefault();
        var form = $(this);
        $.ajax({
          type: 'GET',
          url: "lookup/employee",
          data: form.serialize(),
          dataType: "xml",
          success: function (xml){
            var responseDiv = $(".response")[0] || $("<div>").attr("class","response").appendTo(form)[0];
            responseDiv.innerHTML = $(xml).find('Message').first().text().replace(/\n/g, "<br />");
          },
          failure: function(){
            alert("Could not return a response. Check out the server code.");
          }
        });
    });
});
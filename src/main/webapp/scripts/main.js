$(document).ready(function(){
    $("form").submit(function(e){
        e.preventDefault();
        var form = $(this);
        $.ajax({
          type: 'POST',
          url: "directory/search",
          data: form.serialize(),
          dataType: "xml",
          success: function (xml, status, xhr){
            var responseDiv = $(".response")[0] || $("<div>").attr("class","response").appendTo(form)[0];
            responseDiv.innerHTML = $(xml).find('Message').first().html().replace(/\n/g, "<br />");
            var mediaTag = $(responseDiv).find("media");
            mediaTag.replaceWith($("<img>").addClass("media").attr("src", mediaTag.text()));
            console.log("Cookie: " + xhr.getResponseHeader("Set-Cookie"));
          },
          failure: function(){
            alert("Could not return a response. Check out the server code.");
          }
        });
    });
});
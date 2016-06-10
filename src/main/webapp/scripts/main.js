
$(document).ready(function(){
     var form = $("form").submit(function(e){
        var searchInput = $("#search-input");
        e.preventDefault();
        $.post("/directory/search", form.serialize())
        .done(function(xml) {
            var responseDiv = $(".response")[0] || $("<div>").attr("class", "response")
            .appendTo(form)[0];
            var content = $(xml).find('Message').first().html().replace(/\n/g, "<br/>");
            responseDiv.innerHTML = content;
            var mediaTag = $(responseDiv).find("media");
            mediaTag.replaceWith($("<img>").attr("title", "Press over the picture to see it in full size")
            .addClass("media").attr("src", mediaTag.text()));
            searchInput.focus().select();
        })
        .fail(function(){
            alert("Could not return a response. Check out the server code.");
        });
    });
});

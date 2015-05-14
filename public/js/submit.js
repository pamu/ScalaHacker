$(function() {
    var source = $("textarea#code_box").val();
    var testcases = $("#testcases_box").val();
    $("#done").click(function() {
         if (source.length > 0) {
                console.log(source);
            } else {
                $("#msg_center").html('<span class="alert alert-danger">source cannot be empty.</span>')
            }
    });
});
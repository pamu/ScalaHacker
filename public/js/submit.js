$(function() {
    $('#done').click(function() {
         var source = $('#code_box').val();
         var testcases = $('#testcases_box').val().split('\n');
         var json = '{"source" : ' + JSON.stringify(source) + ', "lang" : ' + 15 + ', "testcases" : ' + JSON.stringify(testcases) + '}';
         console.log(json);
         if (source.trim().length > 0) {
                $.ajax({
                    url: '/hackerrank',
                    type: 'POST',
                    data: json,
                    contentType: 'application/json; charset=utf-8',
                    dataType: 'json',
                    async: true,
                    success: function(msg) {
                        if (msg.success) {
                            var res = JSON.parse(msg.success);
                            console.log(res);
                            console.log(res.result.result);
                            if (res.result.result === 0) {
                                alert("done");
                                $('#output').html('<span class="alert alert-success">Success</span>')
                            } else {
                                var line = res.result.compilemessage.split("\n");
                                console.log(line);
                                for(i = 0; i < line.length; i++) {
                                    console.log(line[i]);
                                    $('#output').append('<div>' + line[i] + '</div>')
                                }
                            }
                        } else {
                            alert(msg.failure);
                        }
                    }
                });
            } else {
                $("#msg_center").html('<span class="alert alert-danger">source cannot be empty.</span>')
            }
    });
});
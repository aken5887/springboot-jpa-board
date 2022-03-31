/**
  모듈패턴
**/
let replyManager = (function(){

    let getAll = function(obj, callback) {
        console.log("get All...");
        $.getJSON('/replies/'+obj, callback);
    };

    let add = function(obj, callback) {
         console.log("add ...");
         $.ajax({
            type : 'post',
            url : '/replies/'+obj.bno,
            data : JSON.stringify(obj),
            beforeSend : function(xhr){
              xhr.setRequestHeader(obj.csrf.headerName, obj.csrf.token);
            },
            dataType : 'json',
            contentType : 'application/json',
            success : callback
         });
    };

    let update = function(obj, callback) {
         console.log("update ...");
         $.ajax({
            type : 'PUT',
            url : '/replies/' + obj.bno,
            dataType : 'json',
             beforeSend : function(xhr){
                          xhr.setRequestHeader(obj.csrf.headerName, obj.csrf.token);
                        },
            data : JSON.stringify(obj),
            contentType : 'application/json',
            success : callback
         });
    };

    let remove = function(obj, callback) {
         console.log("remove ...");
         $.ajax({
            type : 'delete',
            url : '/replies/'+obj.bno+"/"+obj.rno,
            dataType : 'json',
             beforeSend : function(xhr){
                          xhr.setRequestHeader(obj.csrf.headerName, obj.csrf.token);
                        },
            contentType : 'application/json',
            success : callback
         });
    };

    return {
      getAll : getAll,
      add : add,
      update : update,
      remove : remove
    }

})();

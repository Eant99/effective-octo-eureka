
/*
   数据格式控制
*/

$().ready(function(){
  
        $(".Datetime").on("click", function() {
            WdatePicker();
        });
        
        $(".Datetime").on("keypress", function() {
            if (/[^0-9-]/.test(String.fromCharCode(event.keyCode)))
                event.keyCode = 0
        });
        
        $(".Datetime").on("dragenter", function() {
            return false;
        });
        
        $(".Datetime").on("paste", function() {
            return false;
        });
        $(".Decimal").each(function() {
            if($(this).val()!=""&&$(this).val().indexOf(".")==-1)
            {
                $(this).val($(this).val()+".00")
            }
        });
         $(".Decimal").on("keypress", function() {
            if (/[^0-9.]/.test(String.fromCharCode(event.keyCode)))
                event.keyCode = 0
        });
        
        $(".Decimal").on("focus", function() { 
            if($(this).val()=="0.00")
             {
               $(this).val("");
             }
        }); 
        
        $(".Decimal").on("blur", function() { 
            if($(this).val()=="")
             {
               $(this).val("0.00");
             }
        }); 
        $(".Int").on("keypress", function() {
            if (/[^0-9]/.test(String.fromCharCode(event.keyCode)))
                event.keyCode = 0
        });
        $(".Int").on("dragenter", function() {
            return false;
        });
        $(".Int").on("paste", function() {
            return false;
        });
         $(".Int").on("focus", function() { 
                        if($(this).val()=="0")
                         {
                           $(this).val("");
                         }
       }); 
       
        $(".Int").on("blur", function() { 
            if($(this).val()=="")
             {
               $(this).val("0");
             }
       }); 
});


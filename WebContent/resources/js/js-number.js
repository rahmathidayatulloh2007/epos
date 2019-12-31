function checkNumeric(evt,obj,bilBulat){        
    keyCode = null;
    if( evt.which ){
        keyCode = evt.which;
    }else if( evt.keyCode ){
        if( evt.charCode == 0 )
            return true;
        else
            keyCode = evt.keyCode;
    }
    
    if(bilBulat == true){
        if((48 > keyCode || keyCode > 57) && keyCode != 8)
            return false;
               
    } else{
        if( (48 > keyCode || keyCode > 57) && keyCode != 8  
            && keyCode != 44 && keyCode != 46 )
            return false;
    }
    oldValue = obj.value;
    return true;
}

function checkHuruf(evt,obj){        
    keyCode = null;
    keyCode = evt.which;    
    
    if(keyCode == 45)
        return false;
               
   
    oldValue = obj.value;
    return true;
}

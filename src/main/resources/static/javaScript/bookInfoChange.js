window.onload=function(){
    var aForm=document.getElementById('new_bkform');
    var oBtn=aForm.getElementsByClassName('subm')[0];
    var aDiv_a=aForm.getElementsByClassName('div_a')[0];
    var aInput=aForm.elements;   /*获取表单中的表单元素*//*包括submit*/
    var oP=aDiv_a.getElementsByTagName('p');
    var i=0;
    oBtn.onclick=function(){
        for(i=1;i<aInput.length-1;i++){
            aInput[i].index=i;
            if(isEmpty(aInput[i])){
                alert(oP[aInput[i].index].innerHTML+'不能为空！');
                aInput[i].focus();
                return false;
            }
        }
    }
}

function isEmpty(obj){
    if(obj.value==""||obj.value.trim()==""){
        return true;
    }else{
        return false;
    }
}
window.onload=function(){
	var aForm=document.getElementById('form_addr');
	var aInput=aForm.elements;
	var i=0;
	aInput[aInput.length-1].onclick=function(){
		for(i=0;i<aInput.length-1;i++){
			if(isEmpty(aInput[i])){
				alert('请将信息填写完整！');	
				return false;
			}
		}
	}
};


function isEmpty(obj){
    if(obj.value==""||obj.value.trim()==""){
        return true;
    }else{
        return false;
    }
}
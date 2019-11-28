window.onload=function(){
	var aDiv=document.getElementsByClassName('inform')[0];
	var aSpan=aDiv.getElementsByTagName('span')[0];
	var aTxt_name=document.getElementById('lib_name');	
	var aTxt_addr=document.getElementById('lib_addr');
	var aTxt_telnub=document.getElementById('lib_telnub');
	var oBtn=document.getElementById('btn');
		oBtn.onclick=function(){
			if(isNull(aTxt_name)){
					aSpan.innerHTML="请输入图书馆名称！";
					aTxt_name.focus();
					return false;
				}
			else if(isNull(aTxt_addr)){
				aSpan.innerHTML="请输入图书馆地址！";
				aTxt_addr.focus();
				return false;
			}	
			else if(isNull(aTxt_telnub)){
				aSpan.innerHTML="请输入电话/手机号码！";
				aTxt_telnub.focus();
				return false;
			}
		}
	
}

function isNull(obj){    /*   为空检验*/
		if(obj.value==""||obj.value.trim()=="")	{
			return true;
		}
		else
		return false;
	}
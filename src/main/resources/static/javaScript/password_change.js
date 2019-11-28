window.onload=function(){
	var aDiv=document.getElementsByClassName('inform')[0];
	var aSpan=aDiv.getElementsByTagName('span')[0];
	var aTxt_pd=document.getElementById('txt_password');	
	var aTxt_pd1=document.getElementById('txt_password1');
	var aTxt_pd2=document.getElementById('txt_password2');
	var oBtn=document.getElementById('btn');
		oBtn.onclick=function(){
			if(isNull(aTxt_pd)){
					aSpan.innerHTML="请输入原密码！";
					aTxt_pd.focus();
					return false;
				}
			else if(isNull(aTxt_pd1)){
				aSpan.innerHTML="请输入新的密码！";
				aTxt_pd1.focus();
				return false;
			}	
			else if(isNull(aTxt_pd2)){
				aSpan.innerHTML="请再次输入新密码！";
				aTxt_pd2.focus();
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
window.onload=function ()
{
	var oTable=document.getElementById('cart_table');
	var oBtnSelectAll=oTable.getElementsByTagName('input')[0];
	var aDelete=oTable.getElementsByClassName('delete');
	var oChange=oTable.getElementsByClassName('change');
	var aRows=oTable.tBodies[0].rows;
	var aTbody=oTable.getElementsByTagName('tbody')[0];
	var aTr=aTbody.children;
	var i=0;
	//隔行变色
		interlaceColor();
	//加删除事件
		for(i=0;i<aDelete.length;i++)
		{
			
			aDelete[i].index=i;
			aDelete[i].onclick=function(){
				var pd=confirm('确认删除');	
					if(pd){	/*删除子节点tr*/
                        var d=this.index; //记录后台需要删除的配送信息的索引值
						aTbody.removeChild(aTr[this.index]);
						for(i=0;i<aDelete.length;i++){
							aDelete[i].index=i;   //删除子节点后，必须重新匹配索引值
						}
                        $.ajax({    //通过ajax向后台发送删除请求
                            type: "get",
                            url: "/deleteDelivery",
                            data:"d="+d,   //传递索引
                        });
						interlaceColor();//隔行换色
					}
			}
		}
	
	oBtnSelectAll.onclick=function () /*全选*/
	{
		selectAll();
		
	}
	
		var oFoot=document.getElementsByClassName('foot_div')[0]; 
		var oDelete_ft=oFoot.getElementsByClassName('foot_btn1')[0]; 
		var aCheck=aTbody.getElementsByClassName('ckbox');
			oDelete_ft.onclick=function(){ 
				var pd=confirm('确认删除');  
				if(pd){ //删除所有被选择的地址
					for(i=0;i<aCheck.length;i++){
						 if(aCheck[i].checked){
						 	aTbody.removeChild(aTr[i]);
						 	var d=i;
                             $.ajax({    //通过ajax向后台发送删除请求
                                 type: "get",
                                 url: "/deleteDelivery",
                                 data:"d="+d,   //传递索引
                             });

							i--;  //删除一个子节点后i必须自减1，再进行循环（因为删除一个子节点后，处在被删除子节点后面的所有节点的位置前进了1）
						 }
					}
				 	interlaceColor();
				 }
			}
		
			for(i=0;i<oChange.length;i++){			
				oChange[i].onclick=function(){
						
				}
			}
		
		
		
}

//---------------------------------------------------------------------------下面是函数

	
function selectAll()     /*全选键*/
{
	var oTable=document.getElementById('cart_table');
	var oBtnSelectAll=oTable.getElementsByTagName('input')[0];
	var aInputs=oTable.tBodies[0].getElementsByTagName('input');
	
	var i=0;
	
	for(i=0;i<aInputs.length;i++)
	{
		if(aInputs[i].type=='checkbox')
		{
			aInputs[i].checked=oBtnSelectAll.checked;
		}
	}
}

function interlaceColor()  /*  隔行换色*/
{
	var oTable=document.getElementById('cart_table');
	var aRows=oTable.tBodies[0].rows;
	var i=0;
	
	for(i=0;i<aRows.length;i++)
	{
		if(i%2)
		{
			aRows[i].style.background='#FFC';
		}
		else
		{
			aRows[i].style.background='';
		}
	}
}
// JavaScript Document
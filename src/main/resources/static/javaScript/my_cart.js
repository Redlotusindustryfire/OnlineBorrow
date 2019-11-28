window.onload=function ()
{
	var oTable=document.getElementById('cart_table');
	var oBtnSelectAll=oTable.getElementsByTagName('input')[0];
	var aDelete=oTable.getElementsByClassName('delete');
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
				var pd=confirm("是否删除？");
				var j=this.index;
				if(pd){		/*删除子节点tr*/
					$.ajax({
						type: "post",
						dataType: 'text',
						url: "/deleteShopCart",
						data:"index="+j,
						success: function (result) {
                            aTbody.removeChild(aTr[j]);
                            for(i=0;i<aDelete.length;i++){
                                aDelete[i].index=i;   //删除子节点后，必须重新匹配索引值
                            }
                            interlaceColor();//隔行换色
                            calculateTotal();// 删除子节点后重新计算合计的值
							alert(result);
                        }
					});

				}
			}
		}
	
	oBtnSelectAll.onclick=function () /*全选*/
	{
		selectAll();
		calculateTotal();/*添加计算总价事件*/
		
	}
	var oBtn1=oTable.getElementsByClassName('btn1');
	var oBtn2=oTable.getElementsByClassName('btn2');
	var oTxt1=oTable.getElementsByClassName('txt1');
	var oTxt2=oTable.getElementsByClassName('txt2');
	var oSpan=oTable.getElementsByTagName('span');
	var i=0;
	var k=[];  /*定义数组存储书本借阅数量，避免变量使用冲突*/
	var p=0;
		for(i=0;i<oTxt2.length;i++){      /*设置书本默认订购一本时的订阅价*/
			oTxt2[i].value=parseFloat(oSpan[i].innerHTML).toFixed(2);
		}
		for(i=0;i<oBtn2.length;i++){
			k[i]=1;        /*赋值初始数量一本*/
		}
		for(i=0;i<oBtn2.length;i++){  /*加减订阅数量*/
			oBtn2[i].index=i;
			oBtn2[i].onclick=function(){
				p=parseFloat(oSpan[this.index].innerHTML);
				oTxt1[this.index].value=++k[this.index];
				oTxt2[this.index].value=(p*k[this.index]).toFixed(2);
				calculateTotal(); //数量改变后，重新计算合计
			}
		}
		for(i=0;i<oBtn1.length;i++){
			oBtn1[i].index=i;
			oBtn1[i].onclick=function(){
				p=parseFloat(oSpan[this.index].innerHTML);
				if(k[this.index]>1){
					oTxt1[this.index].value=--k[this.index];
					oTxt2[this.index].value=(p*k[this.index]).toFixed(2);
				}
				calculateTotal();//数量改变后，重新计算合计
			}
		}
		var aCheck=oTable.tBodies[0].getElementsByClassName('ckbox');
		
			for(i=0;i<aCheck.length;i++){
				 
			 	aCheck[i].onclick=function(){ /*添加计算总价事件*/
					calculateTotal();
				}
			}
			
		var oFoot=document.getElementsByClassName('foot_div')[0]; 
		var oDelete_ft=oFoot.getElementsByClassName('foot_btn1')[0]; 
		oDelete_ft.onclick=function(){     //删除所有被选择的书本
			for(i=0;i<aCheck.length;i++){
				 if(aCheck[i].checked){
					 aTbody.removeChild(aTr[i]);
					i--;  //删除一个子节点后i必须自减1，再进行循环（因为删除一个子节点后，处在被删除子节点后面的所有节点的位置发生了变化）
				 }
			}
			calculateTotal();
			 interlaceColor();
		}
		
		
};

//---------------------------------------------------------------------------下面是函数
	function calculateTotal(){							/*计算总价*/
		var oTable=document.getElementById('cart_table');
		var oTxt2=oTable.getElementsByClassName('txt2');
		var oFoot=document.getElementsByClassName('foot_div')[0]; 
	    var aCheck=oTable.tBodies[0].getElementsByClassName('ckbox'); 
		var oTxt_ft=oFoot.getElementsByClassName('txt2')[0];
		var total=0;
		var j=0;
			for(j=0;j<oTxt2.length;j++){
					if(aCheck[j].checked){		/*复选框被选择时，将该书的总借阅价加到total里面*/
					
						total+=parseFloat(oTxt2[j].value);
					}
			 }
			 oTxt_ft.value=parseFloat(total).toFixed(2);/* 将暂存变量赋值给合计oTxt_ft.value，并保留两位小数*/
			total=0;  /*循环计算结束后将total初始化为0*/
}
	
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
			aRows[i].style.background='#CFF';
		}
		else
		{
			aRows[i].style.background='';
		}
	}
}



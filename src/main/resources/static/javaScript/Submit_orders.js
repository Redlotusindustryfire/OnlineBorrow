// JavaScript Document
window.onload=function() {
    var oBtn1 = document.getElementById('btn1');
    var oBtn2 = document.getElementById('btn2');
    var oTxt1 = document.getElementById('txt1');
    var p=$("#txt2").text();
    var k=1;
    var quantity=1;   //初始化订阅数量
    oBtn2.onclick = function () {
        oTxt1.value = ++k;
        $("#txt2").text(p*k);
        quantity=$("#txt1").val(); //记录订阅数量
    };
    oBtn1.onclick = function () {
        if (k > 1) {
            oTxt1.value = --k;
            $("#txt2").text(p*k);
            quantity=$("#txt1").val();//记录订阅数量
        }
    };
    var active;
    var oDiv=document.getElementsByClassName("address_div");
    for(var i=0;i<oDiv.length;i++){
    	oDiv[i].index=i;
    	oDiv[i].onclick=function () {   //只让点击的div样式改变
    		for(var i=0;i<oDiv.length;i++){
    			oDiv[i].classList.remove("active");
			}
            this.classList.add("active");
    		active=this.index; //记录被选配送信息的索引值，之后靠它从数据库找到对应信息
        }
	}
    var id=$("#id").text();
    $("#btn_sub").click(function () {
    	if(active+1) {   //在jquery中0是null型的，但active=0是我们希望通过的一种情况，所以将它+1，再进行判断
            $.ajax({
                type: "post",
				dataType: 'text',
                url: "/Submit_orders/submit",
                data:"active="+active+"&id="+id+"&quantity="+quantity,
                success: function (message) {
                    alert(message);
                }
            })
        }
        else {
    		alert("请选择配送地址！");
		}
    })
};



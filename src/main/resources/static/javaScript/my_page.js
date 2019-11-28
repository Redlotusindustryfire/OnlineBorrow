window.onload=function(){
    var oD=document.getElementById("div2");
    var oU=document.getElementsByTagName("ul")[0];
    var oL=getByClass(oU,'li1')	;
    var oD1=getByClass(oD,'div1');
    var i=0;
    for(i=0;i<oL.length;i++){
        oL[i].index=i;
        oL[i].onmouseover=function(){
            oD1[this.index].style.display='block';
        };
        oL[i].onmouseout=function(){
            oD1[this.index].style.display='none';
        };
    }
    interlaceColor($(".delivery_lt_div"));
    interlaceColor($(".delivery_rt_div"));

    var oBtn1=document.getElementsByClassName("btn1");
    var oBtn=document.getElementsByClassName("btn");
    var aDiv_lt_div=document.getElementsByClassName("delivery_lt_div");
    var aDiv_rt_div=document.getElementsByClassName("delivery_rt_div");
    var aDiv_lt=document.getElementsByClassName("delivery_lt")[0];
    var aDiv_rt=document.getElementsByClassName("delivery_rt")[0];
    for ( i=0;i<oBtn1.length;i++){  //图书送达
        oBtn1[i].index=i;
        oBtn1[i].onclick=function () {
            var pd=confirm("确认图书送达了吗？");
            if(pd){
                var j=this.index;
                $.ajax({
                    type: "post",
                    url:"/arrived",
                    data: "index="+this.index, //将要更新的借阅索引交给后台
                    success: function () {
                       aDiv_lt.removeChild(aDiv_lt_div[j]);
                       aDiv_rt.removeChild(aDiv_rt_div[j]);
                       for(i=0;i<oBtn1.length;i++){
                           oBtn1[i].index=i;       //删除子节点后，需要重新匹配索引
                       }
                    }
                })
            }
        }
    }
    for(var k=0;k<oBtn.length;k++){  //取消订单
        oBtn[k].index=k;
        oBtn[k].onclick=function () {
            var j=this.index;
            var pd=confirm("是否取消订单？");
            if(pd){
                $.ajax({
                    type: "post",
                    url: "/cancelOrder",
                    data: "index="+j,
                    success: function () {
                        aDiv_lt.removeChild(aDiv_lt_div[j]);
                        aDiv_rt.removeChild(aDiv_rt_div[j]);
                        for(k=0;k<oBtn.length;k++){
                            oBtn[k].index=k;   //删除节点后，重新匹配索引
                        }
                    }
                })
            }
        }
    }

};


function getByClass(oParent, sClassName) /*获取父节点下指定class的子节点*/
{
    var aElm=oParent.getElementsByTagName('*');
    var aArr=[];
    for(var i=0; i<aElm.length; i++)
    {
        if(aElm[i].className==sClassName)
        {
            aArr.push(aElm[i]);
        }
    }
    return aArr;
}


function interlaceColor(aDivs)  /*  隔行换色*/
{

    for(var i=0;i<aDivs.length;i++)
    {
        if(i%2)
        {
            aDivs[i].style.background='#FFC';
        }
        else
        {
            aDivs[i].style.background='';
        }
    }
}

window.onload=function ()
{
	var oBtn=document.getElementsByClassName("btn");
	var aRecord_lt=document.getElementById("record_lt");
	var aRecord_md=document.getElementById("record_md");
	var aRecord_rt=document.getElementById("record_rt");
	var aRecord_lt_div=document.getElementsByClassName("record_lt_div");
    var aRecord_md_div=document.getElementsByClassName("record_md_div");
    var aRecord_rt_div=document.getElementsByClassName("record_rt_div");
    interlaceColor(aRecord_lt_div);
    interlaceColor(aRecord_md_div);
    interlaceColor(aRecord_rt_div);
    for (var i=0;i<oBtn.length;i++){
        oBtn[i].index=i;
        oBtn[i].onclick=function () {
            var pd=confirm("是否归还图书？");
            if(pd){
                var j=this.index;
                $.ajax({
                    type: "post",
                    url:"/returnBook",
                    data: "index="+this.index, //将要更新的借阅索引交给后台
                    success: function () {
                        aRecord_lt.removeChild(aRecord_lt_div[j]);
                        aRecord_md.removeChild(aRecord_md_div[j]);
                        aRecord_rt.removeChild(aRecord_rt_div[j]);
                        for(i=0;i<oBtn.length;i++){
                            oBtn[i].index=i;       //删除子节点后，需要重新匹配索引
                        }
                        interlaceColor(aRecord_lt_div);
                        interlaceColor(aRecord_md_div);
                        interlaceColor(aRecord_rt_div);
                    }
                })
            }
        }
    }

		
		
}

//---------------------------------------------------------------------------下面是函数



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
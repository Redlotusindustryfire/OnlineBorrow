window.onload=function () {
    var oBtn = document.getElementsByClassName("btn");
    var aRecord_lt = document.getElementById("record_lt");
    var aRecord_rt = document.getElementById("record_rt");
    var aRecord_last = document.getElementById("record_last");
    var aRecord_lt_div = document.getElementsByClassName("record_lt_div");
    var aRecord_rt_div = document.getElementsByClassName("record_rt_div");
    var aRecord_last_div = document.getElementsByClassName("record_last_div");
    interlaceColor(aRecord_lt_div);
    interlaceColor(aRecord_rt_div);
    interlaceColor(aRecord_last_div);

    for ( i= 0; i < oBtn.length; i++) {
        oBtn[i].index = i;
        oBtn[i].onclick = function () {
            var pd = confirm("是否已收到归还的图书？");
            if (pd) {
                var j = this.index;
                $.ajax({
                    type: "post",
                    url: "/returningSuccess",
                    data: "index=" + this.index, //将要更新的借阅索引交给后台
                    success: function () {
                        aRecord_lt.removeChild(aRecord_lt_div[j]);
                        aRecord_rt.removeChild(aRecord_rt_div[j]);
                        aRecord_last.removeChild(aRecord_last_div[j]);
                        for (i = 0; i < oBtn.length; i++) {
                            oBtn[i].index = i;       //删除子节点后，需要重新匹配索引
                        }
                        interlaceColor(aRecord_lt_div);
                        interlaceColor(aRecord_rt_div);
                        interlaceColor(aRecord_last_div);
                    }
                })
            }
        }
    }

};

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
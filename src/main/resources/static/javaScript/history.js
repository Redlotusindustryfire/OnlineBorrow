window.onload=function () {
    var aRecord_lt_div = document.getElementsByClassName("record_lt_div");
    var aRecord_md_div = document.getElementsByClassName("record_md_div");
    var aRecord_rt_div = document.getElementsByClassName("record_rt_div");
    //隔行变
    interlaceColor(aRecord_lt_div);
    interlaceColor(aRecord_md_div);
    interlaceColor(aRecord_rt_div);
};

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
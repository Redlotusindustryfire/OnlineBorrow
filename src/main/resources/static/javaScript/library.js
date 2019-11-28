window.onload=function() {
    var oD = document.getElementsByClassName("nav_top")[0];
    var oU = document.getElementsByTagName("ul")[0];
    var oL = getByClass(oU, 'li1');
    var oD1 = getByClass(oD, 'div1');
    var i = 0;
    for (i = 0; i < oL.length; i++) {
        oL[i].index = i;
        oL[i].onmouseover = function () {
            oD1[this.index].style.display = 'block';
            return false;
        }
        oL[i].onmouseout = function () {
            oD1[this.index].style.display = 'none';
            return false;
        }
    }

    var oBtn = document.getElementsByClassName("btn");
    var aRecord_lt = document.getElementById("record_lt");
    var aRecord_md = document.getElementById("record_md");
    var aRecord_rt = document.getElementById("record_rt");
    var aRecord_last = document.getElementById("record_last");
    var aRecord_lt_div = document.getElementsByClassName("record_lt_div");
    var aRecord_md_div = document.getElementsByClassName("record_md_div");
    var aRecord_rt_div = document.getElementsByClassName("record_rt_div");
    var aRecord_last_div = document.getElementsByClassName("record_last_div");
    interlaceColor(aRecord_lt_div);
    interlaceColor(aRecord_md_div);
    interlaceColor(aRecord_rt_div);
    interlaceColor(aRecord_last_div);

    for ( i= 0; i < oBtn.length; i++) {
        oBtn[i].index = i;
        oBtn[i].onclick = function () {
            var pd = confirm("图书是否已配送成功？");
            if (pd) {
                var j = this.index;
                $.ajax({
                    type: "post",
                    url: "/successfulDelivery",
                    data: "index=" + this.index, //将要更新的借阅索引交给后台
                    success: function () {
                        aRecord_lt.removeChild(aRecord_lt_div[j]);
                        aRecord_md.removeChild(aRecord_md_div[j]);
                        aRecord_rt.removeChild(aRecord_rt_div[j]);
                        aRecord_last.removeChild(aRecord_last_div[j]);
                        for (i = 0; i < oBtn.length; i++) {
                            oBtn[i].index = i;       //删除子节点后，需要重新匹配索引
                        }
                        interlaceColor(aRecord_lt_div);
                        interlaceColor(aRecord_md_div);
                        interlaceColor(aRecord_rt_div);
                        interlaceColor(aRecord_last_div);
                    }
                })
            }
        }
    }
    setInterval(function () {    //检测用户是否取消了订单
        $.ajax({
            type: "post",
            dataType: 'text',
            url:"/CheckOrderCancelled",
            success: function (result) {
                if(result!=""){
                    alert(result);
                }

            }
        })
    },5000);


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

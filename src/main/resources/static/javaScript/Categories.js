window.onload=function() {
    var oTop = document.getElementById('return_top');
    /* 返回顶部*/
    var bSys = true;
    var timer = null;

    //检测用户拖动了滚动条
    window.onscroll = function () {
        if (!bSys) {
            clearInterval(timer);
        }
        bSys = false;
    };
    oTop.onclick = function () {
        timer = setInterval(function () {
            var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
            var iSpeed = Math.floor(-scrollTop / 8);

            if (scrollTop == 0) {
                clearInterval(timer);
            }

            bSys = true;
            document.documentElement.scrollTop = document.body.scrollTop = scrollTop + iSpeed;
        }, 30);
    };
    /* 返回顶部结束*/
    $("#a1").click(function () {		//综合排序
            ajaxResh("/Categories/integrate");
    });
    $("#a2").click(function () {				//借阅量降序排序
            ajaxResh("/Categories/saleDesc");   //调用ajax获取数据
    })
    $("#a3").click(function () {      //价格升序
            ajaxResh("/Categories/priceAsc");   //调用ajax获取数据
    });
    $("#a4").click(function () {		//价格降序
            ajaxResh("/Categories/priceDesc");   //调用ajax获取数据
    });
};

function ajaxResh(Url) {
    var a=$("#span1").text();
    var t=$("#span2").text();//按照用户给定的排序条件进行页面局部刷新
    $.ajax({
        type: "get",
        dataType: 'json',
        url: Url,
        data: "category="+t+"&a="+a,
        success: function (result) {
            $(".content_div").remove();
            $.each(result,function (i,obj) {
                var div1=$("<div class='content_div'><img src='"+obj.image+"'>" +
                    "<a href='/shop_trading/"+obj.id+"' target='_blank'>"+obj.title+"</a>" +
                    "<h3>￥"+obj.price+"</h3><p>借阅量："+obj.bw_amount+"</p>" +
                    "<h4>可借阅天数："+obj.max_day+"</h4></div>");
                $("#categories_content").append(div1);
            })
        }
    })
}
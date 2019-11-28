window.onload=function () {

    var userSave = $.cookie("userSave");

    $("#btn_add").click(function () {
        if (userSave == null||userSave==""){ //判断用户是否登录
            alert("请先登录网站！");
            window.location="http://localhost:8080/HomePage";
        }
        else {
            var pathName=location.pathname.split("/");//通过location获取该网页的网址，再进行分割处理
            var id=pathName[pathName.length-1];  //取数组的最后的一个元素，即为book的id
            $.ajax({
                type: "post",
                dataType: 'text',
                url: "/addInBookList",
                data: "id="+id,
                success: function (result) {
                    alert(result);
                }
            })
        }
    });

    $("#btn").click(function () {
        if (userSave == null || userSave == ""){ //判断用户是否登录
            alert("请先登录网站！");
            window.location="http://localhost:8080/HomePage";
            return false;
        }
    });

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
};

// JavaScript Document
window.onload=function(){

    var oDiv=document.getElementById('clock');
    var aImg=oDiv.getElementsByTagName('img');
    var i;

    for(i=0;i<aImg.length;i++)
    {
        if(!isNaN(parseInt(aImg[i].alt)))
        {
            g_aImg.push(aImg[i]);
        }
    }

    g_aImg.push(aImg[aImg.length-2]);

    aNow=getTimeArray();

    for(i=0;i<g_aImg.length;i++)
    {
        g_aImg[i].now=-1;
    }

    checkSwitch();

    setInterval(checkSwitch, 1000);
    checkSwitch();

	var oLogin_top=document.getElementById('toplogin');/*top登录按钮*/
	var oLogin=document.getElementById('login');		/*登录框*/
	var oClose_lg=document.getElementById('login_close');  /*登录框关闭按钮*/
	var oReg_top=document.getElementById('top_registered');/*top注册按钮*/
	var oReg=document.getElementById('reg_frame');		/*注册框*/
	var oClose_reg=document.getElementById('reg_close');/*注册框关闭按钮			*/											
														
														
											/*登录框打开关闭*/
	 oLogin_top.onclick=function(){
		oLogin.style.display="block";
         $(".msg").text("");  //清空提示文字
		};
	
	oClose_lg.onclick=function(){
		oLogin.style.display="none";
		};
											/*	注册框打开关闭*/
		oReg_top.onclick=function(){
		oReg.style.display="block";
            $(".re_msg").text("");  //清空提示文字
		};
	
	oClose_reg.onclick=function(){
		oReg.style.display="none";
		};
		dragMove(oLogin);					/*div拖拽*/
		dragMove(oReg);
		
	var aDiv_lg1=document.getElementById('login_div1');
	var aP_lg=aDiv_lg1.getElementsByTagName('p')[0];
	var aTxt_lg1=document.getElementById('login_txt1');
	var aTxt_lg2=document.getElementById('login_txt2');
	var valiCode = document.getElementById("vacode");
	var oBnt_lg=document.getElementById('login_bnt');	/*登录验证*/
			createCode();
			oBnt_lg.onclick=function(){
				if(isNull(aTxt_lg1)){
					aP_lg.innerHTML="账号不能为空！";
					aTxt_lg1.focus();
					return false;
				}
				else if(isNull(aTxt_lg2)){
					aP_lg.innerHTML="密码不能为空！";
					aTxt_lg2.focus();
					return false;
				}
				else if(validate()){
                    valiCode.value="";
					valiCode.focus();
					return false;
				}
                valiCode.value="";
                createCode();
                var username=$("#login_txt1").val();
                var password=$("#login_txt2").val();
				login(username,password);   //输入框非空及验证码正确后，调用登录功能
			};


                $(function () {             //在进入页面后,判断是否存在cookie，如果存在则自动登录
                    var userSave = $.cookie("userSave");
                    var passSave = $.cookie("passSave");
                    if (userSave != null && userSave != "" && passSave != null && passSave != "") {
                        var username = userSave;
                        var password = passSave;
                        login(username, password);//调用登录函数，自动登录
                    }
                  });



	
	var aDiv_reg1=document.getElementById('reg_div1');
	var aP_reg=aDiv_reg1.getElementsByTagName('p')[0];
	var aTxt_reg1=document.getElementById('reg_txt1');
	var aTxt_reg2=document.getElementById('reg_txt2');
	var aTxt_reg3a=document.getElementById('reg_txt3a');
	var aTxt_reg3b=document.getElementById('reg_txt3b');
	var oBtn_reg=document.getElementById('reg_bnt');
	var aRadio_reg=document.getElementsByName('userType');
		oBtn_reg.onclick=function(){
			if(isNull(aTxt_reg1)){
				aP_reg.innerHTML="账号不能为空！";
				aTxt_reg1.focus();
				return false;
			}
			else if(isNull(aTxt_reg2)){
				aP_reg.innerHTML="昵称不能为空！";
				aTxt_reg2.focus();
				return false;
			}
			else if(isNull(aTxt_reg3a)){
				aP_reg.innerHTML="密码不能为空！";
				aTxt_reg3a.focus();
				return false;
			}
			else if(isNull(aTxt_reg3b)){
				aP_reg.innerHTML="密码不能为空！";
				aTxt_reg3b.focus();
				return false;
			}
			else if(aTxt_reg3a.value.trim()!=aTxt_reg3b.value.trim()){
				aP_reg.innerHTML="两次填写的密码不一致！";
				return false;
			}
                var regData=$("#reg_form").serialize();
                $.ajax({
                    type:"post",
                    dataType:"text",
                    url:"/HomePage/reg",
                    data:regData,
                    success: function (data) {
                        $(".re_msg").text(data);
                        if(data=="注册成功，请登录！"){   //注册成功2s后关闭注册框
                        	setTimeout(function () {
								$("#reg_frame").css("display","none");
                            },2000)
						}
                    },
                    error:function () {
                        alert("注册失败！");
                    }
                })
		};
		$("#ss_bnt").click(function () {
			if($.trim($("#ss_txt").val())==""){
                $("#ss_bnt").next().text("请输入搜索内容！");
                return false;
			}
        });

	var oNavleftul=document.getElementById('nav_leftul');	/*左侧菜单栏的ul*/
	var oLi_nlu=oNavleftul.getElementsByTagName('li');	/*获取ul下的li*/
	var oDiv_linlu=oNavleftul.getElementsByTagName('div');
	var i=0;
		for(i=0;i<oLi_nlu.length;i++){                     /*菜单栏的显示与隐藏*/
			oLi_nlu[i].index=i;
			oLi_nlu[i].onmouseover=function(){
					oDiv_linlu[this.index].style.display='block';
					iMove(oDiv_linlu[this.index],'width',500);
				};
            oLi_nlu[i].onmouseout=function(){
					var j=this.index;
                    iMove(oDiv_linlu[this.index],'width',0);
                    this.timer=setTimeout(function () {  //设定一个定时器，让div的宽度先逐渐减小，再隐藏div
                           oDiv_linlu[j].style.display="none";
                       },1000);

				};
            for(var k=0;k<oDiv_linlu.length;k++){
                oDiv_linlu[k].index=k;
                oDiv_linlu[k].onmouseover=function () {
                    clearTimeout(oLi_nlu[this.index].timer);   //清空这个div的父节点li的计时器，
                    this.style.display='block';               // 避免鼠标离开li，1s后，div就消失
                }
            }

		}

		var oDiv_nav_leftmenu=document.getElementsByClassName('nav_leftmenu')[0];
		var oA=oDiv_nav_leftmenu.getElementsByTagName('a');   //选取左侧菜单栏的所有a标签
		var stm;
		for(i=0;i<oA.length;i++){
		    oA[i].index=i;
		    oA[i].onclick=function () {
                stm=oA[this.index].innerHTML;   //将a的文本赋值给stm
                window.location="/search?stm="+stm;  //点击a标签，带参stm跳转到搜索页面
            }
        }
		
		/*幻灯片*/
		var oDiv_slide=document.getElementById('cs_div');
		var oPrev=document.getElementById('slide_prev');
		var oNext=document.getElementById('slide_next');
		var oUl_slide=document.getElementById('slide_ul');
		var aLi_slide=oUl_slide.getElementsByTagName('li');
        var i=0,k=2;
        function autoShow() {    //自动换图，由于需要共享i、k的值，所以需要将这个方法作为内部方法
            aLi_slide.Timer=setInterval(function () {
                aLi_slide[i].style.zIndex=k;
                i++;
                k++;
                if(i==aLi_slide.length){
                    i=0;
                }
            },2000);
        }
        autoShow();
        oDiv_slide.onmouseover=function(){   /*prev、next图标的显示*/
            clearInterval(aLi_slide.Timer);
            oPrev.style.display='block';
            oNext.style.display='block';
        };
        oDiv_slide.onmouseout=function(){   /*prev、next图标的隐藏*/
            autoShow();
            oPrev.style.display='none';
            oNext.style.display='none';
         };
        oNext.onclick=function () {      //下一张图
            aLi_slide[i].style.zIndex=k;
            i++;
            k++;
            if(i==aLi_slide.length){
                i=0;
            }
        };

        oPrev.onclick=function () {   //上一张图片
            aLi_slide[i].style.zIndex=k;
            i--;
            k++;
            if(i==-1){
                i=aLi_slide.length-1;
            }
        }

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





var timer=null;       //时钟开始--------------------------------------
var aNow=null;
var g_aImg=[];
var g_oImgWeek=null;
var g_aWeekName=
    [
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven"
    ];
var g_iImgHeigth=0;
var g_iTarget=0;
var g_iMax=0;

function checkSwitch()
{
    var i=0;
    aNow=getTimeArray();

    g_imgHeigth=g_aImg[0].offsetHeight;
    g_iTarget=-g_imgHeigth;
    g_iMax=g_imgHeigth;

    timer=setInterval(doSwitch, 30);
}

function doSwitch()
{
    var bEnd=false;
    var i=0;

    g_imgHeigth-=5;
    if(g_imgHeigth<=g_iTarget)
    {
        g_imgHeigth=g_iTarget;
        bEnd=true;
    }

    for(i=0;i<g_aImg.length;i++)
    {
        if(g_aImg[i].now!=aNow[i])
        {
            if(g_imgHeigth>0)
            {
                g_aImg[i].style.height=g_imgHeigth+'px';
                g_aImg[i].style.top=-(g_iMax-g_imgHeigth)/2+'px';
            }
            else
            {
                if(i==g_aImg.length-1)
                {
                    g_aImg[i].src="/images/time/" + g_aWeekName[aNow[i]] + ".png";
                }
                else
                {
                    g_aImg[i].src="/images/time/" + aNow[i] + ".png";
                }

                g_aImg[i].style.height=-g_imgHeigth+'px';
                g_aImg[i].style.top=-(g_iMax+g_imgHeigth)/2+'px';
            }
        }
    }

    if(bEnd)
    {
        for(i=0;i<g_aImg.length;i++)
        {
            g_aImg[i].now=aNow[i];
        }

        clearInterval(timer);
    }
}

function toDouble(iNum)
{
    if(iNum<10)
    {
        return '0'+iNum;
    }
    else
    {
        return ''+iNum;
    }
}

function getTimeArray()
{
    var oDate=new Date();
    var aNumber=[];

    var iYear=oDate.getYear();
    var iMonth=oDate.getMonth();
    var iDay=oDate.getDate();
    var iHour=oDate.getHours();
    var iMin=oDate.getMinutes();
    var iSec=oDate.getSeconds();
    var iWeek=(oDate.getDay()+6)%7;

    if(iYear<1900)
    {
        iYear+=1900;
    }

    var str=''+(iYear)+toDouble(iMonth+1)+toDouble(iDay)+toDouble(iHour)+toDouble(iMin)+toDouble(iSec)+iWeek;
    var aChar=str.split('');

    for(i=0;i<aChar.length;i++)
    {
        aNumber[i]=parseInt(aChar[i]);
    }

    return aNumber;							//时钟结束-------
}



function login(username,password) {
    $.ajax({
        type: "post",
        dataType: 'json',
        url: "/HomePage/login",
        data: "username=" + username + "&password=" + password,
        success: function (result) {
            $(".msg").text(result.nickname);
            if (result.type) {     //通过判断传回来的实体类的属性type是否存在，来判断是否登录成功
                $("#login").css("display", "none");  //登录成功后让登录框关闭
                if (result.type == 1) {     //普通用户
                    $a1 = $("<a class='rt' href='my_page.html'target='_blank'></a>");
                    $(".aa").attr({href: "my_cart.html", target: "_blank"});
                    $(".ab").attr({href: "my_finished", target: "_blank"});
                    if(result.sex=='male'){
                        $p1=$("<p>欢迎您<span></span>先生</p>");
					}
					else {
                        $p1=$("<p>欢迎您<span></span>女士</p>");
					}
                    $(".nav_top").append($p1);
                    $(".nav_top span").text(result.nickname);
                }
                else {    //图书馆用户
                    $(".ab,.aa").remove();
                    $a1 = $("<a class='rt' href='library.html' target='_blank'></a>");
                    $a3=$("<a class='rt' href='lib_finished.html' target='_blank'></a>");
                    $a3.text("交易订单");
                    $(".nav_top").append($a3);
                }

                if ($("#chkSave:checked").val() == "yes") {  //判断是否需要保存cookie
                    $.cookie("userSave", result.username, {expires: 30});
                    $.cookie("passSave", result.password, {expires: 30});
                }
                $("#toplogin,#top_registered").remove();
                $a2=$("<a class='rt exit' >退出</a>");
                $a1.text(result.username);
                $(".nav_top").append($a1);
                $(".nav_top").append($a2);


                $(".exit").click(function () {   //退出登录，清空cookie
                    var pd=confirm("是否退出登录？");
                    if(pd){
                        $.cookie("userSave","");  //记住不能用null，只能用“”表示空，因为null会被当做字符串“null”
                        $.cookie("passSave","");
                        window.location="http://localhost:8080/HomePage";   //退出登录后重新加载首页
						var x="exit";
						$.ajax({  //用户确认退出后，用ajax向后台发送销毁session的请求
										// （不明白为什么attr添加th:href属性无法触发,所以只好用ajax请求了）
							type:"get",
							url:"/exit",
							data:"exitP="+x
						})
                    }
                });
            }
        },
        error: function () {
            alert("登录失败");
        }
    });
}


    function autoPlay(obj) {
        var k=0;
        obj.Timer=setInterval(function () {
            for(var i=0;i<obj.length;i++){
                obj[i].className="";
            }
            obj[k].className="active";
            k++;
            if(k>3){
                k=0;
            }
        },2000);
}


	function dragMove(obj){              /*拖拽框架*/
		obj.onmousedown=function ()
		{
				var disX=event.clientX-obj.offsetLeft;
				var disY=event.clientY-obj.offsetTop;
			 document.onmousemove=function ()
				{
					obj.style.left=event.clientX-disX+'px';
					obj.style.top=event.clientY-disY+'px';
					event.preventDefault();
				};
				
			document.onmouseup=function ()
				{
					document.onmousemove=null;
					document.onmouseup=null;
				
				};

		}
			
	}
	
	function isNull(obj){    /*   为空检验*/
		if(obj.value==""||obj.value.trim()=="")	{
			return true;
		}
		else
		return false;
	}
	 
	 var code ; //在全局 定义验证码   
     function createCode()   
     {    
       code="";   
       var codeLength = 4;//验证码的长度   
       var checkCode = document.getElementById("checkcode");   
       var selectChar = new Array(0,1,2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z');//所有候选组成验证码的字符，当然也可以用中文的      
       for(var i=0;i<codeLength;i++)   
       {   
        var charIndex = Math.floor(Math.random()*36);   
        code+=selectChar[charIndex];   
       }    
         checkCode.value=code;           
     }     
     function validate(){ 
	 	var aDiv_lg1=document.getElementById('login_div1');  
       var valiCode = document.getElementById("vacode");
	   var aP_lg=aDiv_lg1.getElementsByTagName('p')[0];  
       if(isNull(valiCode))   
       {   
           aP_lg.innerHTML="请输入验证码";
		   return true;
       }   
       else if(valiCode.value.toUpperCase()!=code)   
       {   
          aP_lg.innerHTML="验证码错误！";
          createCode();//刷新验证码
		   return true;     
       }   
       else   
       {   
         aP_lg.innerHTML="登录成功请稍候...";
		 return false;
       }   
 }   
	
	
 function getStyle(obj,attr){				/*获取元素属性值兼容*/
            if(window.getComputedStyle){
                return window.getComputedStyle(obj,null)[attr]; /*FF goolge*/
            }
            return obj.currentStyle[attr];
        }
		
function iMove(obj,attr,iMubiao){			/*div属性变化框架*/
	clearInterval(obj.Timer);
	obj.Timer= setInterval(function(){
		obj.ispeed=(iMubiao-parseInt(getStyle(obj,attr)))/8;
		obj.ispeed=obj.ispeed>0?Math.ceil(obj.ispeed):Math.floor(obj.ispeed);
		if(iMubiao==parseInt(getStyle(obj,attr))){
			clearInterval(obj.Timer);
			}
			else{	
			obj.style[attr]=parseInt(getStyle(obj,attr))+obj.ispeed+'px';
			
			}
	},30);
		
		
		}   

	
	
	function getByClass(oParent, sClassName) 
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
function checkForm() {
    var pwdipt = document.getElementById("pwd");
    var pwdvalue = pwdipt.value;
    pwdvalue = md5(pwdvalue);
    //加密后的密码进行第二次加密，是对称加密
    var uuidSalt = document.getElementById("uuidSalt").value;
    pwdvalue = encrypt(pwdvalue, uuidSalt, uuidSalt);
    //alert(pwdvalue + "," + pwdvalue.length);
    if(pwdvalue.length == 44){
        pwdipt.value = pwdvalue;
        return true;
    }else{
        return false;
    }
}

function encrypt(data,key,iv) { //key,iv必须是16位的字符串
    var key1  = CryptoJS.enc.Latin1.parse(key);
    var iv1   = CryptoJS.enc.Latin1.parse(iv);
    return CryptoJS.AES.encrypt(data, key1, {iv:iv1,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.ZeroPadding}).toString();
}
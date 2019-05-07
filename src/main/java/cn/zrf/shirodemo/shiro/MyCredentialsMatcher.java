package cn.zrf.shirodemo.shiro;

import cn.zrf.shirodemo.utils.AesEncryptUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;

public class MyCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token1, AuthenticationInfo info) {
        //完全由我自己定义用户输入的密码，和数据库中密码比对的规则
        UsernamePasswordToken token = (UsernamePasswordToken) token1;
        String password = new String(token.getPassword());
        //从服务器端，取出UUIDSalt盐值(根据shiro的session取)
        Session session = SecurityUtils.getSubject().getSession();
        String uuidSalt = (String) session.getAttribute("uuidSalt");
        //封装如token之前，将密码进行一次解密
        try {
            password = AesEncryptUtil.desEncrypt(password, uuidSalt, uuidSalt);
            session.removeAttribute("uuidSalt");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IncorrectCredentialsException("受到重放攻击");
        }
        String formPassword = (new SimpleHash("MD5", password, token.getUsername(), 1024)).toString();
        String accountCredentials = (String) this.getCredentials(info);
        return formPassword.equals(accountCredentials);
    }

}

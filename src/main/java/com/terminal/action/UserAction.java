package com.terminal.action;

import com.terminal.beans.CodeMsg;
import com.terminal.beans.MessageBean;
import com.terminal.pojos.User;
import com.terminal.utils.DeepCopyUtil;
import com.terminal.utils.ValidateUtil;
import com.terminal.utils.WebSshUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import java.io.InputStream;
import java.util.Properties;

public class UserAction extends BaseAction<User> {

    private static final long serialVersionUID = 1L;

    private InputStream inputStream;
    private String verify;   //verify code
    private Integer remember;  //remember me ?

    @Autowired
    private Properties users;

    public String logout() {
        if (session != null) {
            session.remove("user");
            session.clear();
        }
        Cookie emailCookie = new Cookie("email", "");
        emailCookie.setPath("/");
        emailCookie.setMaxAge(0);
        Cookie pwdCookie = new Cookie("pwd", "");
        pwdCookie.setPath("/");
        pwdCookie.setMaxAge(0);
        servletResponse.addCookie(emailCookie);
        servletResponse.addCookie(pwdCookie);
        return "logout";
    }

    public String login() {

        //get verify code in the session
        String verifyCode = (String) session.get("securityCode");

        //check verify code
        if (verifyCode != null && verifyCode.equals(verify)) {

            //check email and password
            if (ValidateUtil.validStrings(model.getEmail(), model.getPassword())) {
                String _pwd = users.getProperty(model.getEmail());

                if (_pwd!=null && model.getPassword().equals(_pwd)) {

                    //put user into session
                    User tempUser = DeepCopyUtil.deepCopy(model);
                    session.put("user", tempUser);


                    //success
                    message = new MessageBean(CodeMsg.USERACTION_LOGIN_SUCCESS_CODE,
                            getText(CodeMsg.USERACTION_LOGIN_SUCCESS_MSG));

                    //remember me ?
                    if (remember != null && remember == 1) {
                        Cookie emailCookie = new Cookie("email", model.getEmail());
                        emailCookie.setPath("/");
                        emailCookie.setMaxAge(3600 * 24 * 30);
                        Cookie pwdCookie = new Cookie("pwd", tempUser.getPassword());
                        pwdCookie.setPath("/");
                        pwdCookie.setMaxAge(3600 * 24 * 30);
                        servletResponse.addCookie(emailCookie);
                        servletResponse.addCookie(pwdCookie);
                    }
                } else {
                    //failed
                    message = new MessageBean(CodeMsg.USERACTION_LOGIN_FAILED_CODE,
                            getText(CodeMsg.USERACTION_LOGIN_FAILED_MSG));
                }
            }
        } else {
            message = new MessageBean(CodeMsg.USERACTION_LOGIN_VERIFYCODE_ERROR_CODE,
                    getText(CodeMsg.USERACTION_LOGIN_VERIFYCODE_ERROR_MSG));
        }
        inputStream = WebSshUtil.toJsonMessage(message);
        return "login";
    }


    /**
     * *****************************UI page******************************************
     */
    public String updatePasswordPage() {
        return "updatePasswordPage";
    }

    public String uploadFacePage() {
        return "uploadFacePage";
    }

    public String registPage() {
        return "registPage";
    }

    public String loginPage() {
        return "loginPage";
    }


    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public Integer getRemember() {
        return remember;
    }

    public void setRemember(Integer remember) {
        this.remember = remember;
    }



}

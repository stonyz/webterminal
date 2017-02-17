package com.terminal.action;

import com.terminal.pojos.User;

public class IndexAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String index() {
		return SUCCESS;
	}

    public String openShellTerminalPage() {
        User user = (User) session.get("user");
        if (user == null) {
            return "notlogin";
        }
        return "openShellTerminalPage";
    }
}

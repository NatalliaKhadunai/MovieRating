package main.controller;

import main.model.command.ActionCommand;
import main.model.command.EmptyCommand;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    public ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand current = new EmptyCommand();
        String action = request.getParameter("requestType");
        if (action == null || action.isEmpty()) {
            return current;
        }
        RequestType currentRequest = RequestType.fromValue(action);
        current = currentRequest.getCurrentRequest();
        return current;
    }
}

package main.controller;

import main.model.command.ActionCommand;
import main.model.command.EmptyCommand;

import javax.servlet.http.HttpServletRequest;

/**
 * Class {@code ActionFactory} is used for defining command by request.
 */

public class ActionFactory {
    /**
     * Method is used to define command by request
     * @param request contains requestType
     * @return ActionCommand defined by requestType
     */
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

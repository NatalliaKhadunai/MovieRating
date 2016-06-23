package main.model.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public interface ActionCommand {
    String execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException;
}

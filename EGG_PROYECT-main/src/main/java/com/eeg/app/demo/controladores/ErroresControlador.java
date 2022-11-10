package com.eeg.app.demo.controladores;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class ErroresControlador implements ErrorController {

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        ModelAndView errorPage = new ModelAndView("error/error");
        String errorMsg = "";

        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400:
                errorMsg = "El recurso solicitado no existe";
                break;
            case 401:
                errorMsg = "No se encuentra autorizado";
                break;
            case 403:
                errorMsg = "No tiene permiso para acceder al recurso";
                break;
            case 404:
                errorMsg = "No tiene permiso para acceder al recurso";
                break;
            case 500:
                errorMsg = "Ocurrio un error interno";
                break;

            default:
                throw new AssertionError();
        }
        errorPage.addObject("codigo", httpErrorCode);
        errorPage.addObject("mensaje", errorMsg);
        return errorPage;

    }

    public String getErrorPath() {
        return "/error/error.html";
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }
}

package org.pawkrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by pawkrol on 2017-04-23.
 */
@Controller
public class NotificationController {

    private final ArduinoService arduinoService;

    @Autowired
    public NotificationController(ArduinoService arduinoService) {
        this.arduinoService = arduinoService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/blink")
    @ResponseBody
    public String startBlinking() {
        arduinoService.startBlinking();
        return "OK";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/blink/mode/{mode}")
    @ResponseBody
    public String setBlinkingMode(@PathVariable int mode) {
        arduinoService.setMode(mode);
        return "OK";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/notif/check")
    @ResponseBody
    public String checkClicked() {
        return arduinoService.checkClicked()? "clicked" : "not clicked";
    }

    @RequestMapping(method = RequestMethod.POST, name = "/notif/remove")
    @ResponseBody
    public String remove() {
        arduinoService.remove();
        return "OK";
    }
}

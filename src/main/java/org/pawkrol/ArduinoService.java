package org.pawkrol;

import org.pawkrol.arduino.ArduinoSerial;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by pawkrol on 2017-04-23.
 */
@Service
public class ArduinoService {

    private ArduinoSerial arduinoSerial;

    private boolean wasClicked = false;

    @PostConstruct
    public void init() {
        arduinoSerial = new ArduinoSerial(NotificationsApplication.PORT);
        arduinoSerial.setSerialReader(this::receiveData);
        arduinoSerial.init();
    }

    public void startBlinking(){
        arduinoSerial.write( (char)1 );
    }

    public boolean checkClicked(){
        boolean clicked = wasClicked;
        wasClicked = false;

        return clicked;
    }

    public void setMode(int mode){
        arduinoSerial.write( (char)mode );
    }

    public void remove(){
        arduinoSerial.write( (char)2 );
    }

    public void close() {
        arduinoSerial.close();
    }

    private void receiveData(String data){
        if (data.equals("1")) {
            wasClicked = true;
        }
    }
}

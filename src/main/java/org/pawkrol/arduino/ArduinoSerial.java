package org.pawkrol.arduino;

import gnu.io.*;

import java.io.*;

/**
 * Created by pawkrol on 2017-04-23.
 */
public class ArduinoSerial implements SerialPortEventListener {

    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 9600;

    private SerialPort serialPort;
    private BufferedReader input;
    private BufferedWriter output;

    private SerialReader serialReader;

    private final String portName;

    public ArduinoSerial(String portName) {
        this.portName = portName;
    }

    public void init() {
        CommPortIdentifier portId;

        try {
            portId = CommPortIdentifier.getPortIdentifier(portName);
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
            serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            input = new BufferedReader(new InputStreamReader(
                    serialPort.getInputStream()
            ));

            output = new BufferedWriter(new OutputStreamWriter(
                    serialPort.getOutputStream()
            ));

            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if (serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputLine = input.readLine();
                serialReader.read(inputLine);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(char data) {
        try {
            output.write(data);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSerialReader(SerialReader serialReader){
        this.serialReader = serialReader;
    }
}

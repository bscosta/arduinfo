package com.arduinfo.arduinfo.services;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class ArduinoService {

    /*@Value("${arduino.port}")
    private String arduinoPort;

    @Value("${arduino.taxa}")
    private Integer arduinoTaxa;*/

    private OutputStream serialOut;

    public String initialize(String arduinoPort, Integer arduinoTaxa) {
        try {
            //Define uma variável portId do tipo CommPortIdentifier para realizar a comunicação serial
            CommPortIdentifier portId = null;
            try {
                //Tenta verificar se a porta COM informada existe
                CommPortIdentifier.getPortIdentifiers();
                portId = CommPortIdentifier.getPortIdentifier(arduinoPort);
            }catch (NoSuchPortException npe) {
                //Caso a porta COM não exista será exibido um erro
                JOptionPane.showMessageDialog(null, "Porta COM não encontrada.",
                        "Porta COM", JOptionPane.PLAIN_MESSAGE);

                return npe.getLocalizedMessage();
            }
            //Abre a porta COM
            SerialPort port = (SerialPort) portId.open("Comunicação serial", arduinoTaxa);
            serialOut = port.getOutputStream();
            port.setSerialPortParams(arduinoTaxa, //taxa de transferência da porta serial
                    SerialPort.DATABITS_8, //taxa de 10 bits 8 (envio)
                    SerialPort.STOPBITS_1, //taxa de 10 bits 1 (recebimento)
                    SerialPort.PARITY_NONE); //receber e enviar dados

            return portId.getName();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Método que fecha a comunicação com a porta serial
     */
    public Boolean close() {
        try {
            serialOut.close();
        }catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível fechar porta COM.",
                    "Fechar porta COM", JOptionPane.PLAIN_MESSAGE);

            return false;
        }

        return true;
    }

    /**
     * @param opcao - Valor a ser enviado pela porta serial
     */
    public void enviaDados(int opcao){
        try {
            serialOut.write(opcao);//escreve o valor na porta serial para ser enviado
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível enviar o dado. ",
                    "Enviar dados", JOptionPane.PLAIN_MESSAGE);
        }
    }
}

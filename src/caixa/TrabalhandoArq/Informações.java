/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caixa.TrabalhandoArq;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author junio
 */
public class Informações extends Thread{
    private ArrayList<String> principal;
    private ArrayList<String> secundarios;
    private ArrayList<String> controle;
    private long lest = 1;

    public Informações(){
        this.principal = new ArrayList<>();
        this.secundarios = new ArrayList<>();
        this.controle = new ArrayList<>();
        this.ler();
    }
    
    @Override
    public void run(){
        while(true){
            File cont = new File("./controle.dat");
            if(cont.lastModified()>this.lest){
                this.ler();
                this.lest = cont.lastModified();
            }
            try {
            sleep(3000);
            } catch (InterruptedException ex) {
            Logger.getLogger(Informações.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public long getLest() {
        return lest;
    }

    public void setLest(long lest) {
        this.lest = lest;
    }

    public ArrayList<String> getControle() {
        return controle;
    }

    public void setControle(ArrayList<String> controle) {
        this.controle = controle;
    }

    public ArrayList<String> getPrincipal() {
        return principal;
    }

    public void setPrincipal(ArrayList<String> principal) {
        this.principal = principal;
    }

    public ArrayList<String> getSecundarios() {
        return secundarios;
    }

    public void setSecundarios(ArrayList<String> secundarios) {
        this.secundarios = secundarios;
    }
    
    public boolean ler(){
        try{
            ObjectInputStream in = new ObjectInputStream( new FileInputStream("./principal.dat"));
            this.principal = (ArrayList<String>) in.readObject();
            /*
            for(int i = in.readInt() ; i>0 ; i--){
                this.principal.add((String) in.readObject());
            }
            */
            in.close();

            ObjectInputStream in1 = new ObjectInputStream( new FileInputStream("./secundarios.dat"));
            this.secundarios = (ArrayList<String>) in1.readObject();
            /*
            for(int i = in1.readInt() ; i>0 ; i--){
                this.secundarios.add((String) in1.readObject());
            }
            */
            in1.close();
            
            ObjectInputStream in2 = new ObjectInputStream( new FileInputStream("./controle.dat") );
            this.controle = (ArrayList<String>) in2.readObject();
            /*
            System.out.println(in2.readObject());
            for(int i = in2.readInt() ; i>0 ; i--){
                try{
                String y = (String) in2.readObject();
                this.controle.add(y);
                }catch(EOFException e){}
            }
            */
            in2.close();
            this.lest = new File("./controle.dat").lastModified();
        }catch(IOException | ClassNotFoundException  e){
            return false;
        }
        return true;
    }
    public boolean ler(String x){
        return true;
    }
    
    public boolean gravar(){
        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./principal.dat"));
            //out.writeInt(this.principal.size());
            //for (String principal1 : this.principal) {
                out.writeObject(this.principal);
            //}
            out.close();

            ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream("./secundarios.dat"));
            //out1.writeInt(this.secundarios.size());
            //for (String secundario : this.secundarios) {
                out1.writeObject(this.secundarios);
            //}
            out1.close();
        }catch(IOException e){
            return false;
        }
        return true;
    }
    public boolean gravar(String gravar){
        try{
            ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream("./controle.dat"));
            //out2.writeInt(this.controle.size());
            //for (int i=0; i<this.controle.size(); i++){
                out2.writeObject(this.controle);
            //}
            out2.close();
        }catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(Informações.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        File cont = new File("./controle.dat");
        this.lest = cont.lastModified();
        return true;
    }
    
    public boolean gControle(){
        try{
            File arquivo1 = new File("./Controle.csv");
            PrintWriter pt = new PrintWriter(arquivo1);
            for (String controle1 : this.controle) {
                pt.println(controle1);
            }
            pt.close();
        }catch(FileNotFoundException e){
            return false;
        }
        return true;
    }
    
    
}

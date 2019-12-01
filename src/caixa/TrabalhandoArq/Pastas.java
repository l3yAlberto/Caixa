/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caixa.TrabalhandoArq;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author junio
 */
public class Pastas{
    private String[] nomes;
    private String diretorio;
    
    public Pastas(String diretorio,int tamanho){
        this.nomes = new String[tamanho];
        this.diretorio = diretorio;
    }

    public String[] getNomes() {
        return nomes;
    }

    public void setNomes(String[] nomes) {
        this.nomes = nomes;
    }

    public String getDiretorio() {
        return diretorio;
    }

    public void setDiretorio(String diretorio) {
        this.diretorio = diretorio;
    }
    
    public void nomes(){
        if(this.diretorio!=null){
            File file = new File(this.diretorio);
            FileFilter filtro1 = (File pathname) -> pathname.isDirectory() && !pathname.isHidden();
            File[] files = file.listFiles(filtro1);
            ArrayList<String> temp = new ArrayList();
            try{
            for(File f1 : files){
                temp.add(f1.lastModified()+";"+f1.getName());
            }
            }catch(NullPointerException e){}
            Collections.sort(temp, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
            String[] temp6 = ((String) o1).split(";");
            Double p1 = Double.parseDouble(temp6[0]);
            temp6 = ((String) o2).split(";");
            Double p2 = Double.parseDouble(temp6[0]);
            return p1 > p2 ? -1 : (p1 < p2 ? +1 : 0);
            }
            });
            for(int i = 0 ; i<this.nomes.length ;i++){
                if(i<temp.size()){
                    String[] temp8 = temp.get(i).split(";");
                    String temp9 = "";
                    for(int i1 = 1 ; i1<temp8.length ;i1++){
                        temp9 = temp9+temp8[i1];
                    }
                    this.nomes[i] = temp9;
                }
            }
        }
    }
}

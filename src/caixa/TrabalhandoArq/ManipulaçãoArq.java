/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caixa.TrabalhandoArq;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

/**
 *
 * @author junior
 */
public class ManipulaçãoArq {
    private boolean valid = false;
    private ArrayList<String> serviçosdaloja ;
    private ArrayList<String> infor ;
    private ArrayList<String> dependente;
    private FileFilter filtro = (File pathname) -> !pathname.isDirectory() && !pathname.isHidden() && tipoFoto(pathname.getName());
    private FileFilter pessoais = (File pathname) -> pathname.isDirectory() && !pathname.isHidden() && !verServiço(pathname.getName());
    private FileFilter filtro1 = (File pathname) -> pathname.isDirectory() && !pathname.isHidden();
    
    public ManipulaçãoArq(ArrayList<String> serviçosdaloja, ArrayList<String> dependente) {
        this.serviçosdaloja = serviçosdaloja;
        this.infor = new ArrayList<>();
        this.dependente = dependente;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public ArrayList<String> getServiçosdaloja() {
        return serviçosdaloja;
    }

    public void setServiçosdaloja(ArrayList<String> serviçosdaloja) {
        this.serviçosdaloja = serviçosdaloja;
    }

    public ArrayList<String> getInfor() {
        return infor;
    }

    public void setInfor(ArrayList<String> infor) {
        this.infor = infor;
    }

    public ArrayList<String> getDependente() {
        return dependente;
    }

    public void setDependente(ArrayList<String> dependente) {
        this.dependente = dependente;
    }
    
    public void findFile (File dir, String name)  {
        FileFilter diretorio = (File pathname) -> pathname.isDirectory() && pathname.getName().toLowerCase().contains(name.toLowerCase()) && !pathname.isHidden();
        File[] temp = dir.listFiles(diretorio);
        if(temp!=null){
            infNotinha(temp[0]);
            return ;
        }
        File[] files = dir.listFiles();
        //if(valid)return;
        if(files != null /*&& valid == false*/){
            for (int i = 0; i < files.length; ++i) {
                File pathname = files[i];
                String nm = pathname.getName();
                if (nm.toLowerCase().contains(name.toLowerCase()) && pathname.isDirectory()) {
                    infNotinha (pathname);
                }
                if (pathname.isDirectory()) /**&& !nm.equals(".") && !nm.equals("..")*/ {
                        findFile (pathname, name);
                }
            }
        }
    }
    public void infNotinha(File pasta){
        File[] temp = pasta.listFiles(pessoais);            //pastas pessoais
        int arquivos = pasta.listFiles(filtro).length;         //apenas arqvuios
        File[] arquivos1 = pasta.listFiles(filtro1);       //apenas diretorios
        String teste = "";
        String[] pastaCli = pasta.getName().split(" ");
        if(verServiço(pasta.getName())){
            for (File arquivos11 : arquivos1) {
                if (serviçoSecun(arquivos11.getName())){
                    infNotinha(arquivos11);
                    String[] x = arquivos11.getAbsolutePath().replace("\\", ";").split(";");
                    for (int t = x.length-1; t>0; t--) {
                        if (verPrimario(x[t])) {
                            String[] h = x[t].split(" ");
                            for (int h1 = 0; h1<h.length; h1++) {
                                if (verPrimario(h[h1])) {
                                    String l ="";
                                    for(int h2 = h1 ; h2<h.length ; h2++){
                                        l = l+h[h2]+" ";
                                    }
                                    addInfor(l, arquivos11.listFiles(filtro).length);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }   
            }
        }
        if(temp.length!=0){
            for(File x : temp){
                if(x.listFiles(pessoais).length==0){
                    int quant = x.listFiles(filtro).length;
                    String[] dire = x.getAbsolutePath().replace("\\", ";").split(";");
                    for(int t = dire.length-1 ; t>0 ; t--){
                        String[] temp2 = dire[t].toLowerCase().split("x");
                        if(temp2.length==1 && dire[t].toLowerCase().contains("x")){
                            quant = quant*(Integer.parseInt(temp2[0]));
                        }
                        if(verServiço(dire[t])){
                            String[] temp1 = dire[t].split(" ");
                            for(int i = 0 ; i<temp1.length ; i++){
                                if(verServiço(temp1[i])){
                                    String ser = "";
                                    for(int i1 = i ; i1<temp1.length;i1++){
                                        ser = ser+temp1[i1]+" ";
                                    }
                                    addInfor(ser , quant);
                                }
                            }
                        }
                    }
                }else{
                    infNotinha(x);
                }
            }
        }
        if(verServiço(pasta.getName())){
            for(int i = 0 ; i<pastaCli.length; i++){
                try{
                    if(verServiço(pastaCli[i].toLowerCase())){
                        for(int i1 = i ;i1<pastaCli.length; i1++){
                            teste = teste+pastaCli[i1]+" ";
                        }
                        addInfor(teste , arquivos);
                        break;
                    }
                }catch(Exception e){
                }
            }
        }
        else{
            for(int i = 0 ; i<arquivos1.length ; i++){
                infNotinha(arquivos1[i]);
            }
        }
    }
    
    public boolean verServiço(String serviço){
        String[] pastaCli = serviço.split(" ");
        for(int i = 0 ; i<pastaCli.length; i++){
            if(this.serviçosdaloja.contains(pastaCli[i].toLowerCase())){
                return true;
            }
            if(this.dependente.contains(pastaCli[i].toLowerCase())){
                return true;
            }
            try{
            String[] verServiço = pastaCli[i].toLowerCase().split("x");
            for(int i1 = 0 ; i1<verServiço.length;i1++){
                if(verServiço.length==2){
                    double teste = Double.parseDouble(verServiço[0].replace(",", "."));
                    double teste1 = Double.parseDouble(verServiço[1].replace(",", "."));
                    return true;
                }
            }
            }catch(Exception e){
            }
        }
        return false;
    }
    
    public void addInfor(String tipo, int quantidade){
        String[] serviço;
        String tempo;
        for(int i = 0; i<this.infor.size(); i++){
            if(this.infor.get(i).toLowerCase().contains(tipo.toLowerCase())){
                serviço = this.infor.get(i).split(" ");
                int temp = Integer.parseInt(serviço[0]);
                temp += quantidade;
                tempo = temp+" ";
                for(int i1 = 1; i1<serviço.length;i1++){
                    tempo = tempo+serviço[i1]+" ";
                }
                this.infor.set(i, tempo);
                return;
            }
        }
        this.infor.add(quantidade+" "+tipo+" ");
    }
    
    public boolean serviçoSecun(String serviço){
        String[] pastaCli = serviço.split(" ");
        for(int i = 0 ; i<pastaCli.length; i++){
            if(this.dependente.contains(pastaCli[i].toLowerCase())){
                return true;
            }
        }
        return false;
    }
    
    public boolean verPrimario(String serviço){
    String[] pastaCli = serviço.split(" ");
    for(int i = 0 ; i<pastaCli.length; i++){
        if(this.serviçosdaloja.contains(pastaCli[i].toLowerCase())){
            return true;
        }
        try{
        String[] verServiço = pastaCli[i].toLowerCase().split("x");
        for(int i1 = 0 ; i1<verServiço.length;i1++){
            if(verServiço.length==2){
                double teste = Double.parseDouble(verServiço[0].replace(",", "."));
                double teste1 = Double.parseDouble(verServiço[1].replace(",", "."));
                return true;
            }
        }
        }catch(Exception e){
        }
    }
    return false;
    }
    
    public boolean tipoFoto(String nome){
        if((nome.toLowerCase().endsWith(".jpg")) || (nome.toLowerCase().endsWith(".jpeg")) || (nome.toLowerCase().endsWith(".png"))){
            return true;
        }
        return false;
    }
}

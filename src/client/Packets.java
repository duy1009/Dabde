package client;

public class Packets {
    private char[] data;
    public boolean justChange ;

    public Packets(int length){
        data = new char[length];

        justChange = true;
        for (int i=0; i< length; i++){
            data[i] = 0;
        }

//        justChange = true;
//        System.out.println("init :"+this.getJustChange());
    }

    public boolean getJustChange(){return this.justChange;}
    public void noChange(){
        this.justChange = false;
//        System.out.println("Change false:"+this.getJustChange());
    }
    public void setData(int index, int value){
        if (data[index] != value) {
            data[index] = (char)value;
            this.justChange = true;
//            System.out.println("Change value: "+this.getJustChange());
//            showDataInt();
        }
    }
    public void showDataInt(){
        for(int i=0 ;i< data.length;i++){
            System.out.print((int)data[i]+" ");
        }
        System.out.println("");
    }
    public char[] getData(){return data;}
    public void change(){
        this.justChange = true;
    }
}

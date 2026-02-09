public class Manager_Item {
    private int NO;
    private String materialItem;
    private String materialDes;
    private String unit;
    private int amount;

    private String op_user;

    private String alert_time;

    private String op_cz;


     Manager_Item( String materialItem, String materialDes, String unit, int amount) {
        this.materialItem=materialItem;
        this.materialDes=materialDes;
        this.unit=unit;
        this.amount=amount;

    }

    Manager_Item(int NO,String materialItem, String materialDes, String unit, int amount,String op_user,String alert_time,String op_cz) {
         this.NO=NO;
        this.materialItem=materialItem;
        this.materialDes=materialDes;
        this.unit=unit;
        this.amount=amount;
        this.op_user=op_user;
        this.alert_time=alert_time;
        this.op_cz=op_cz;
    }


    public int getNO() {
        return NO;
    }

    public String getMaterialItem() {
        return materialItem;
    }

    public String getMaterialDes() {
        return materialDes;
    }

    public String getUnit() {
        return unit;
    }

    public int getAmount() {
        return amount;
    }

    public String  getOp_user() {
        return op_user;
    }
    public String getAlert_time(){return alert_time;}

    public String getOp_cz(){return op_cz;}

//    以下是方便修改数据添加，
    public void setMaterialItem(String materialItem){
         this.materialItem=materialItem;
    }
    public void setUnit(String unit){
        this.unit=unit;
    }
    public void setMaterialDes(String materialDes){
        this.materialDes=materialDes;
    }
    public void setAmount(int amount){
        this.amount=amount;
    }

    public void setNO(int NO){this.NO=NO;}
    public void setOp_user(String op_user){this.op_user=op_user;}
    public void setAlert_time(String alert_time){this.alert_time=alert_time;}

    public void setOp_cz(String op_cz){this.op_cz=op_cz;}
}

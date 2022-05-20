//用来存储token的结构体
public class TypeNode {
    public TypeNode(MyTypeName myTypeName, String value,int code)
    {
        this.myTypeName = myTypeName ;
        this.value = value;
        this.code = code;
    }
    public TypeNode(){ }
    //token类型
    public MyTypeName myTypeName;
    public String value;
    public int code;

    public String toString(){
        return myTypeName.toString()+" "+value+" " + code;
    }
    public String toOutString(){
        return "\t"+value+"\t"+code+"\t"+myTypeName.toString();
    }

    public String getValue() {
        return value;
    }

    public String getMyTypeName() {
        return myTypeName.toString();
    }
}

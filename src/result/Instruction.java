public class Instruction {

    public Instruction(InstructionName instructionName,int arg0,int arg1)
    {
        this.instructionName=instructionName;
        t=arg0;
        a=arg1;
    }
    public Instruction()
    {

    }
    //ָ������
    public InstructionName instructionName;
    //����ʹ���붨��֮��Ĳ��
    public int t;
    //������Ե�ַ����ֵ
    public int a;

    public String toString(){
        return instructionName.toString()+" "+t+" "+a;
    }
    public String toOutString(){
        return instructionName.toString()+"\t"+t+"\t"+a;
    }
}
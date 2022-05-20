import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class Execute {

    // 1��ĺ�������ַ
    private int baseAddress = 0;
    // ��ǰִ�е�ָ����
    private int currentIndex = 0;

    // ����������
    private Scanner scanner = new Scanner(System.in);
    // ָ���б�
    private List<Instruction> instructions = new ArrayList<>();
    // ģ������ջ
    private Stack<Integer> runStack = new Stack<>();

    public void start() {
        //System.out.println("���������ִ���ļ���ַ:");
        if (readInstructionsFile(scanner.next()))
            execute();
        else
            MyError.ShowErrMsg(6, "�������н���!");

    }

    public void start(String SourcePath) {
            if (readInstructionsFile(SourcePath))
                execute();
            else
                MyError.ShowErrMsg(6, "�������н���!");
    }

    public void start(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
        execute();
    }
    /* �ļ��ڶ�ȡָ�� */
    public boolean readInstructionsFile(String fileName) {
        boolean result = true;
        File file = new File(fileName);
        BufferedReader br = null;
        try {
            //System.out.println("��ʼ��ȡָ��");
            br = new BufferedReader(new FileReader(file));
            String tempString;
            int line = 1;
            while ((tempString = br.readLine()) != null) {
                if (!tempString.equals("")) {
                    String[] str = tempString.split(" ");
                    Instruction instruction = new Instruction();
                    for (InstructionName e : InstructionName.values()) {
                        if (e.toString().equals(str[0])) {
                            instruction.instructionName = e;
                            break;
                        }
                    }
                    try {
                        instruction.t = Integer.parseInt(str[1]);
                        instruction.a = Integer.parseInt(str[2]);
                    } catch (Exception e) {
                        MyError.ShowErrMsg(6, "�����쳣,����ָ���ʽ!");
                        result = false;
                        return result;
                    }
                    instructions.add(instruction);
                    //System.out.println("line " + line + ":" + tempString);
                    line++;
                }
            }
            // System.out.println("ָ���ȡ���,���������:");
            // System.out.println("ָ���ȡ���");
        } catch (IOException e) {
            MyError.ShowErrMsg(2, "�Ҳ���Ŀ���ļ�!");
            result = false;
            return result;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e1) {
                    System.out.print("�ĵ��ر�ʧ��");
                }
            }
        }
        return result;
    }

    /* ָ������ڴ����ִ�� */
    public void execute() {
        while (currentIndex < instructions.size()) {
            if (!MyError.errFlag) {
                execute_word(currentIndex);
                currentIndex++;
            } else {
                return;
            }
        }
        // System.out.println("����ִ����ϣ�");
    }

    /* ����ָ����� */
    public void execute_word(int index) {
        int t = instructions.get(index).t;
        int a = instructions.get(index).a;

        // System.out.println(index + runStack.toString());
        switch (instructions.get(index).instructionName) {
            case LIT:
                LIT(t, a);
                break;
            case LOD:
                LOD(t, a);
                break;
            case STO:
                STO(t, a);
                break;
            case CAL:
                CAL(t, a);
                break;
            case INT:
                INT(t, a);
                break;
            case JMP:
                JMP(t, a);
                break;
            case JPC:
                JPC(t, a);
                break;
            case ADD:
                ADD(t, a);
                break;
            case SUB:
                SUB(t, a);
                break;
            case MUL:
                MUL(t, a);
                break;
            case DIV:
                DIV(t, a);
                break;
            case RED:
                RED(t, a);
                break;
            case WRT:
                WRT(t, a);
                break;
            case RET:
                RET(t, a);
                break;
            case LOR:
                LOR(t, a);
                break;
            case LOA:
                LOA(t, a);
                break;
            case EQY:
                EQY(t, a);
                break;
            case EQN:
                EQN(t, a);
                break;
            case BOM:
                BOM(t, a);
                break;
            case BQM:
                BQM(t, a);
                break;
            case SOM:
                SOM(t, a);
                break;
            case SQM:
                SQM(t, a);
                break;
            case MOD:
                MOD(t, a);
                break;
            case COV:
                COV(t, a);
                break;
            case NEG:
                NEG(t, a);
                break;
        }
    }

    /**
     * ���ٿռ�ָ��
     *
     * @param t ������
     * @param a �ռ��С
     *          ��ջ�п���a��int�ռ�
     */
    private void INT(int t, int a) {
        for (int i = 0; i < a; i++) {
            runStack.push(0);
            if (runStack.size() >= 500) {
                // System.out.println("ջ��500");
                MyError.ShowErrMsg(4, "��������Ϊ:��" + currentIndex + "��");
                return;
            }
        }
    }

    /**
     * ����aѹջ
     *
     * @param t ������
     * @param a ����ֵ
     */
    private void LIT(int t, int a) {
        runStack.push(a);
    }

    /**
     * t��ƫ����a�ı���ѹջ
     *
     * @param t ���
     * @param a ��Ե�ַ
     */
    private void LOD(int t, int a) {
        // ͬ�����,λ��Ϊ ����ַ+��Ե�ַ
        if (t == 0) {
            // System.out.println("This is " + baseAddress);
            // System.out.println("This is " + a);
            runStack.push(runStack.get(baseAddress + a));
        }
        // ȫ�ֱ���, λ��Ϊ 0 + ��Ե�ַ
        else {
            runStack.push(runStack.get(a));
        }
    }

    /**
     * ջ����ֵpop�������ַa�ı���
     *
     * @param t ���
     * @param a ��Ե�ַ
     *          ��ջ����ֵ������Ե�ַΪa�ı�����
     */
    private void STO(int t, int a) {
        // ͬ�����,λ��Ϊ ����ַ+��Ե�ַ
        if (t == 0) {
            runStack.set(baseAddress + a, runStack.pop());
        }
        // ������,λ��Ϊ 0+��Ե�ַ
        else {
            runStack.set(a, runStack.pop());
        }
    }

    private void CAL(int t, int a) {
        int base = runStack.size();
        runStack.push(baseAddress);
        runStack.push(currentIndex + 1);
        // ���µ�ǰ��������ַ
        // ���µ�ǰִ��ָ��ı��
        baseAddress = base;
        currentIndex = a - 1;
    }

    private void JMP(int t, int a) {
        currentIndex = a - 1;
    }

    private void JPC(int t, int a) {
        if (runStack.peek() == 0) {
            currentIndex = a - 1;
        }
    }

    /**
     * LOR 0 0
     * ��ջ����ջ�����������ջԪ�أ����ֵ��ջ
     *
     * @param t 0
     * @param a 0
     */
    private void LOR(int t, int a) {
        int val_a = runStack.pop();
        int val_b = runStack.pop();
        runStack.push(val_a | val_b);
    }

    /**
     * LOA 0 0
     * ��ջ����ջ�����룬������ջԪ�أ����ֵ��ջ
     *
     * @param t 0
     * @param a 0
     */
    private void LOA(int t, int a) {
        int val_a = runStack.pop();
        int val_b = runStack.pop();
        runStack.push(val_a & val_b);
    }

    /**
     * EQY 0 0
     * ��ջ����ջ����ȣ�������ջԪ�أ����ֵ��ջ
     *
     * @param t 0
     * @param a 0
     */
    private void EQY(int t, int a) {
        int val_a = runStack.pop();
        int val_b = runStack.pop();
        runStack.push((val_a == val_b) ? 1 : 0);
    }

    /**
     * EQN 0 0
     * ��ջ����ջ�����ȣ�������ջԪ�أ����ֵ��ջ
     *
     * @param t 0
     * @param a 0
     */
    private void EQN(int t, int a) {
        int val_a = runStack.pop();
        int val_b = runStack.pop();
        runStack.push((val_a != val_b) ? 1 : 0);
    }

    /**
     * BOM 0 0
     * ��ջ����ջ�����ڱȽϣ�������ջԪ�أ����ֵ��ջ
     *
     * @param t 0
     * @param a 0
     */
    private void BOM(int t, int a) {
        int val_a = runStack.pop();
        int val_b = runStack.pop();
        runStack.push((val_b > val_a) ? 1 : 0);
    }

    /**
     * BQM 0 0
     * ��ջ����ջ�����ڵ��ڱȽϣ�������ջԪ�أ����ֵ��ջ
     *
     * @param t 0
     * @param a 0
     */
    private void BQM(int t, int a) {
        int val_a = runStack.pop();
        int val_b = runStack.pop();
        runStack.push((val_b >= val_a) ? 1 : 0);
    }

    /**
     * SOM 0 0
     * ��ջ����ջ��С���ڱȽϣ�������ջԪ�أ����ֵ��ջ
     *
     * @param t 0
     * @param a 0
     */
    private void SOM(int t, int a) {
        int val_a = runStack.pop();
        int val_b = runStack.pop();
        runStack.push((val_b < val_a) ? 1 : 0);
    }

    /**
     * SQM 0 0
     * ��ջ����ջ�����ڵ��ڱȽϣ�������ջԪ�أ����ֵ��ջ
     *
     * @param t 0
     * @param a 0
     */
    private void SQM(int t, int a) {
        int val_a = runStack.pop();
        int val_b = runStack.pop();
        runStack.push((val_b <= val_a) ? 1 : 0);
    }

    /**
     * ADD 0 0
     * ��ջ����ջ����ӣ�������ջԪ�أ����ֵ��ջ
     *
     * @param t 0
     * @param a 0
     */
    private void ADD(int t, int a) {
        int val_a = runStack.pop();
        int val_b = runStack.pop();
        runStack.push(val_a + val_b);
    }

    /**
     * SUB 0 0
     * ��ջ����ȥջ����������ջԪ�أ����ֵ��ջ
     *
     * @param t 0
     * @param a 0
     */
    private void SUB(int t, int a) {
        int val_a = runStack.pop();
        int val_b = runStack.pop();
        runStack.push(val_b - val_a);
    }

    /**
     * MUL 0 0
     * ��ջ������ջ����������ջԪ�أ����ֵ��ջ
     *
     * @param t 0
     * @param a 0
     */
    private void MUL(int t, int a) {
        int val_a = runStack.pop();
        int val_b = runStack.pop();
        runStack.push(val_b * val_a);
    }

    /**
     * DIV
     * ��ջ������ջ����������ջԪ�أ����ֵ��ջ
     *
     * @param t 0
     * @param a 0
     */
    private void DIV(int t, int a) {
        int val_a = runStack.pop();
        if (val_a == 0) {
            MyError.ShowErrMsg(1, "��������Ϊ:��" + currentIndex + "��");
            return;
        }
        int val_b = runStack.pop();
        runStack.push(val_b / val_a);
    }

    /**
     * MOD
     * ��ջ��ģջ����������ջԪ�أ����ֵ��ջ
     *
     * @param t 0
     * @param a 0
     */
    private void MOD(int t, int a) {
        int val_a = runStack.pop();
        if (val_a == 0) {
            MyError.ShowErrMsg(1, "��������Ϊ:��" + currentIndex + "��");
            return;
        }
        int val_b = runStack.pop();
        runStack.push(val_b % val_a);
    }

    /**
     * RED 0 0
     * �������ж���һ����������ջ��
     *
     * @param t 0
     * @param a 0
     */
    private void RED(int t, int a) {

        runStack.push(scanner.nextInt());
    }

    /**
     * wrt 0 0
     * ջ��ֵ�������Ļ������
     *
     * @param t 0
     * @param a 0
     */
    private void WRT(int t, int a) {
        if (runStack.empty()) {
            // System.out.println("����ջʧ�ܣ�ջΪ��");
            MyError.ShowErrMsg(4, "��������Ϊ:��" + currentIndex + "��");
            return;
        } else {
            System.out.println(runStack.peek());
        }
    }

    /**
     * ret 0 0
     * �������ý�����,���ص��õ㲢��ջ
     * �����ǰΪ0�㣬�����н���
     *
     * @param t 0
     * @param a 0
     */
    private void RET(int t, int a) {
        if (baseAddress == 0) {
            currentIndex = 999999;
            return;
        }
        // ��Ҫreturn��ֵ����һ����ʱ������
        // �ѵ�ǰ�����ж���ջ
        // �ٰ�return��ֵ����ջ��
        int returnValue = runStack.peek();
        currentIndex = runStack.get(baseAddress + 1) - 1;
        int baseAddressTemp = baseAddress;
        baseAddress = runStack.get(baseAddress);
        while (runStack.size() > baseAddressTemp) {
            runStack.pop();
        }
        runStack.push(returnValue);
    }

    private void COV(int base, int offset) {
        // System.out.println("RUNSTACK1");
        // System.out.println(runStack.toString());
        Queue<Integer> cov = new LinkedList<>();
        for (int i = 0; i < base + offset; i++)
            cov.offer(runStack.pop());
        Stack<Integer> b = new Stack<>();
        Stack<Integer> o = new Stack<>();
        for (int i = 0; i < base; i++)
            b.push(cov.poll());
        for (int i = 0; i < offset; i++)
            o.push(cov.poll());
        while (!b.empty())
            runStack.push(b.pop());
        while (!o.empty())
            runStack.push(o.pop());
        baseAddress -= offset;
    }

    private void NEG(int a, int b) {
        int temp = runStack.pop();
        runStack.push(-temp);
    }
}

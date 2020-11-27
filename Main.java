import java.util.*;
public class Main {
    //Z - маркер дна, e - эпсилон
    static HashMap<String,String> delta = new HashMap<>() {{
        put("q0,1,Z", "q1,Z");
        put("q1,a,Z", "q1,aZ");
        put("q1,a,a", "q1,aa");
        put("q1,b,Z", "q2,bZ");
        put("q1,b,a", "q2,ba");
        put("q1,e,Z", "q7,Z");
        put("q1,e,a", "q7,a");
        put("q2,b,b", "q2,bb");
        put("q2,a,b", "q3,e");
        put("q3,e,b", "q4,e");
        put("q4,a,b", "q3,e");
        put("q4,e,Z", "q7,Z");
        put("q4,b,a", "q5,e");
        put("q5,e,a", "q6,e");
        put("q6,e,a", "q7,e");
        put("q7,b,a", "q5,e");
        put("q7,1,Z", "q8,Z");
    }};

    static String automata(String word,String state,Stack<Character> stack){
        for (int i = 0; i < word.length(); ++i){
            //e переход
            if(delta.containsKey(state+",e,"+stack.lastElement())){
               String[] eID = delta.get(state+",e,"+stack.lastElement()).split(",");
               //копирую МО автомата
               Stack<Character> eStack= new Stack<>();
               eStack.addAll(stack);
               eStack.pop();
               if(!eID[1].equals("e"))
                   for (int j = eID[1].length()-1; j >= 0; --j )
                        eStack.push(eID[1].charAt(j));
               //запускаю копию
               String eAutomata = automata(word.substring(i),eID[0],eStack);
               if(eAutomata.equals("Цепочка принадлежит языку"))
                   return "Цепочка принадлежит языку";
            }
            //обычный ход МП автомата
            if(!delta.containsKey(state+","+word.charAt(i)+","+stack.lastElement()))
                return "Ошибка: неожиданный символ "+ word.charAt(i) +" на позиции "+ (i+1);
            String[] ID = delta.get(state+","+word.charAt(i)+","+stack.pop()).split(",");
            state = ID[0];
            if(!ID[1].equals("e"))
                for (int j = ID[1].length()-1; j >= 0; --j )
                    stack.push(ID[1].charAt(j));
        }
        if(state.equals("q8")) return "Цепочка принадлежит языку";
        else return "Неожиданный конец строки";
    }

    public static void main(String[] args) {
        String word = (new Scanner(System.in)).nextLine(); //получаю цепочку
        //запускаю автомат с q0 состоянием и Z в магазине
        System.out.println(automata(word,"q0",new Stack<>(){{ push('Z'); }}));
    }
}
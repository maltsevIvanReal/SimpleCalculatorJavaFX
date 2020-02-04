package sample.calculator;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Calculator {
    private static String error;
    private Stack<String> stackReady = new Stack<>();
    private Stack<String> stackOp = new Stack<>();
    private Stack<Double> answer = new Stack<>();

    public Stack<Double> getAnswer() {
        return answer;
    }

    private final String OPERATORS = "+-*/";
    private final String OPERATOR_PLUS = "+";
    private final String OPERATOR_MINUS = "-";
    private final String OPERATOR_DIVISION = "/";
    private final String OPERATOR_MULTIPLY = "*";
    private int count = 0;
    double readyAnswer;

    public String getError() {
        return error;
    }

    private boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isOpenBracket(String token) {
        return token.equals("(");
    }

    private boolean isCloseBracket(String token) {
        return token.equals(")");
    }

    private boolean isOperator(String token) {
        return OPERATORS.contains(token);
    }

    private byte getPrecedence(String token) {
        if (token.equals("+") || token.equals("-")) {
            return 1;
        }
        return 2;
    }

    public List<String> buf = new ArrayList<>();

    public String StringBuild(List<String> expression) {
        StringBuilder expressionBuild = new StringBuilder();
        int length = expressionBuild.length();

        for (Object str : buf)
            expressionBuild.append(str);
        String s = expressionBuild.toString();
        s = s.replace(" ", "");
        buf.clear();

        return s;
    }

    private static boolean isNumeric(String s) throws NumberFormatException {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean checkCountBucket(Integer count) {
        if (count < 0) {
            System.out.println("Ошибка, закрывающая скобка перед открывающей " + count);
            error = "Ошибка, закрывающая скобка перед открывающей.";
            return true;
        } else if (count !=0 ) {
            System.out.println("Ошибка, проверьте количество скобок" + count);
            error = "Ошибка, проверьте количество скобок.";
            return true;
        }
        return false;
    }

    public byte expressionCheck(String expression) {
        expression = expression.replace(" ", "")
                .replace(",-", ",0-").replace("(+", "(0+")
                .replace("++", "+")
                .replace("--", "+")
                .replace("+-", "-")
                .replace("-+", "-")
                .replace("/+", "/")
        ;


        StringTokenizer stringTokenizer = new StringTokenizer(expression,
                OPERATORS + "()", true);
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();

            if (isOpenBracket(token)) {
                count++;
            } else if (isCloseBracket(token)) {
                count--;
                if (count < 0) {
                    return -1;
                }
            }
            buf.add(token);
        }
        if (checkCountBucket(count)) return -1;

        for (int i = 0; i < buf.size(); i++) {
            String s = buf.get(i);

            boolean numeric = isNumeric(buf.get(i));

            String OPEN_BUCKET = "(";
            String CLOSE_BUCKET = ")";
            if (s.equals(OPEN_BUCKET) && (buf.get(i + 1).equals(CLOSE_BUCKET))) {
                System.out.println("Ошбика! Пустые скобки.");
                error = "Ошбика! Пустые скобки.";
                return -1;
            }
            if ((i > 0) && (i < buf.size() - 1)) {
                String prev = buf.get(i - 1);
                if (s.contains("..")) {
                    System.out.println("Ошибка! Несколько точек подряд.");
                    error = "Ошибка! Несколько точек подряд.";
                    return -1;
                }
            }
            if (i <= buf.size() - 1) {
                if ((s.contains(OPEN_BUCKET)) && (buf.get(i + 1).contains(OPERATOR_PLUS) || buf.get(i + 1).contains(OPERATOR_MULTIPLY) || buf.get(i + 1).contains(OPERATOR_DIVISION))) {
                    System.out.println("Ошибка! знак после открывающей скобки.");
                    error = "Ошибка! Знак после открывающей скобки.";
                    return -1;
                }
            }

            if (i == 1) {
                String prev = buf.get(i - 1);
                if ((s.contains(OPEN_BUCKET)) && (((prev.contains(OPERATOR_PLUS)) || (prev).contains(OPERATOR_DIVISION))) || (prev).contains(OPERATOR_MULTIPLY)) {
                    System.out.println("Ошибка! Удалите знак перед скобкой.");
                    error = "Ошибка! Удалите знак перед скобкой.";
                    return -1;
                }
            }

            if (i < buf.size() - 1) {
                if ((!s.equals(OPERATOR_DIVISION) && !s.equals(OPEN_BUCKET) && !s.equals(CLOSE_BUCKET) && !s.equals(OPERATOR_MULTIPLY) && !s.equals(OPERATOR_MINUS) && !s.equals(OPERATOR_PLUS)) && (buf.get(i + 1).contains(OPEN_BUCKET))) {
                    System.out.println("Ошибка! Пропущен оператор перед открывающей скобкой");
                    error = "Ошибка! Пропущен оператор перед скобкой.";
                    return -1;
                }
            }

            if (i != 0) {
                String prev = buf.get(i - 1);
                if ((s.equals(CLOSE_BUCKET)) && ((prev.equals(OPERATOR_PLUS)) || (prev.equals(OPERATOR_MULTIPLY)) || (prev.equals(OPERATOR_DIVISION)) || prev.equals(OPERATOR_MINUS))) {
                    System.out.println("Ошибка! знак перед закрывающей скобкой.");
                    error = "Ошибка! знак перед закрывающей скобкой.";
                    return -1;
                }
            }
            if (i == 0) {
                if (s.equals(OPERATOR_MULTIPLY) || s.equals(OPERATOR_DIVISION)) {
                    System.out.println("Ошибка! / или * в начале выражения");
                    error = "Ошибка! / или * в начале выражения";
                    return -1;
                }
            }
            if (i == buf.size() - 1) {
                if (((s.equals(OPERATOR_PLUS)) || (s.equals(OPERATOR_MULTIPLY)) || (s.equals(OPERATOR_MINUS)) || s.equals(OPERATOR_DIVISION))) {
                    System.out.println("Ошибка! Последний оператор в выражении.");
                    error = "Ошибка! Последний оператор в выражении";
                    return -1;
                }
            }
            if (i < buf.size() - 1) {
                String next = buf.get(i + 1);
                if (((s.equals(OPERATOR_PLUS)) || (s.equals(OPERATOR_MULTIPLY)) || (s.equals(OPERATOR_DIVISION)) || s.equals(OPERATOR_MINUS))
                        && (next.equals(OPERATOR_MULTIPLY) || next.equals(OPERATOR_DIVISION))) {
                    System.out.println("Ошибка! Знаки подряд '*' или '/'");
                    error = "Ошибка! Знаки подряд '*' или '/'";
                    return -1;
                } else if ((s.equals(OPERATOR_MINUS)) && next.equals(OPERATOR_MINUS)) {
                    buf.set(i, "+");
                    buf.remove(next);
                }
            }
            if ((i == 0) && (i < buf.size() - 1)) {
                String next = buf.get(i + 1);
                if (s.equals(OPERATOR_MINUS) && (!next.equals(OPERATOR_DIVISION) && !next.equals(OPEN_BUCKET) && !next.equals(CLOSE_BUCKET) && !next.equals(OPERATOR_MULTIPLY) && !next.equals(OPERATOR_MINUS) && !next.equals(OPERATOR_PLUS))) {
                    buf.add(0, "(");
                    buf.add(i + 3, ")");
                    System.out.println("минус в начале выражения перед числом, оборачиваем в скобки");
                }
            }
            if ((s.equals(OPERATOR_PLUS) || s.equals(OPERATOR_MINUS) || s.equals(OPERATOR_DIVISION) || s.equals(OPERATOR_MULTIPLY)) &&
                    ((buf.get(i + 1).equals(OPERATOR_DIVISION)) || buf.get(i + 1).equals(OPERATOR_MULTIPLY))) {
                System.out.println("Ошибка! Несколько операторов подряд.");
                error = "Ошибка! Несколько операторов подряд.";
                return -1;
            }
            if ((i > 0) && (i < buf.size() - 1)) {
                String prev = buf.get(i - 1);
                if (isNumeric(buf.get(i)) && prev.equals(OPERATOR_MINUS) && !isNumeric(buf.get(i - 2)) && !buf.get(i - 2).equals(CLOSE_BUCKET)) {
                    buf.add(i - 1, "(");
                    buf.add(i - 1, " ");
                    buf.add(i + 2, " ");
                    buf.add(i + 4, ")");
                }
            }
            if (i > 0 && (i < buf.size())) {
                String prev = buf.get(i - 1);
                if (isNumeric(buf.get(i)) && prev.equals(OPERATOR_MINUS) && !isNumeric(buf.get(i - 2)) && !buf.get(i - 2).equals(CLOSE_BUCKET)) {
                    buf.add(i - 1, "(");
                    buf.add(i - 1, " ");
                    buf.add(i + 2, " ");
                    buf.add(i + 4, ")");
                }
            }
        }

        return 1;
    }

    public double getReadyAnswer() {
        return readyAnswer;
    }

    public void parse(String expression) throws ParseException, InterruptedException {
        stackOp.clear();
        stackReady.clear();
        buf.clear();
        byte b = 0;
        b = expressionCheck(expression);
        try {
            if (b != 1) {
                System.out.println("Не зашло в цикл parse");
                error = "Ошибка! Пожалуйста, проверьте выражние";
                System.out.println("COUNT " + count);
            } else {

                expression = StringBuild(buf);


                expression = expression
                        .replace(" ", "")
                        .replace("(-", "(0-");

                System.out.println("Зашло в цикл parse");
                StringTokenizer stringTokenizer = new StringTokenizer(expression,
                        OPERATORS + "()", true);


                while (stringTokenizer.hasMoreTokens()) {
                    String token = stringTokenizer.nextToken();
                    if (isOpenBracket(token)) {
                        stackOp.push(token);
                    } else if (isCloseBracket(token)) {
                        while (!stackOp.empty()
                                && !isOpenBracket(stackOp.lastElement())) {
                            stackReady.push(stackOp.pop());
                        }
                        stackOp.pop();

                    } else if (isNumber(token)) {
                        stackReady.push(token);

                    } else if (isOperator(token)) {
                        while (!stackOp.empty()
                                && isOperator(stackOp.lastElement())
                                && getPrecedence(token) <= getPrecedence(stackOp
                                .lastElement())) {
                            stackReady.push(stackOp.pop());
                        }
                        stackOp.push(token);
                    }
                }

                while (!stackOp.empty()) {
                    stackReady.push(stackOp.pop());
                }

                for (String s : stackReady) {
                    if (isNumber(s)) {
                        answer.push(Double.parseDouble(s));
                    } else {
                        double element1 = answer.pop();
                        double element2 = answer.pop();

                        switch (s) {
                            case OPERATOR_PLUS:
                                answer.push(element1 + element2);
                                break;
                            case OPERATOR_MINUS:
                                answer.push(element2 - element1);
                                break;
                            case OPERATOR_MULTIPLY:
                                answer.push(element1 * element2);
                                break;
                            case OPERATOR_DIVISION:
                                answer.push(element2 / element1);
                                break;
                        }
                    }
                }
                if (!answer.empty()) {
                    readyAnswer = answer.lastElement();
                    System.out.println(answer.lastElement());
                    expression = "";

                } else {
                    System.out.println("Ошибка");
                    error = "Пустая очередь для подсчёта выражения.";
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка! Пожалуйста, проверьте выражние! " + e);
            error = "Ошибка! Пожалуйста, введите выражение заново";
        }
    }
}

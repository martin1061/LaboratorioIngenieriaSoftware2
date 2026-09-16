package domain;

public class QuestionDistractors {

    private String opcionA;
    private String opcionB;
    private String opcionC;
    private String opcionD;

    public QuestionDistractors(String opcionA, String opcionB,
                                String opcionC, String opcionD) {

        this.opcionA = opcionA;
        this.opcionB = opcionB;
        this.opcionC = opcionC;
        this.opcionD = opcionD;
    }

    public String getOpcionA() {
        return opcionA;
    }

    public String getOpcionB() {
        return opcionB;
    }

    public String getOpcionC() {
        return opcionC;
    }

    public String getOpcionD() {
        return opcionD;
    }
}
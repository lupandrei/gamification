package domain;

public class QuestTrivia implements Entity<Integer>{

    private int id;
    private String question;
    private String answer;

    private String hint;
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) {
        id=integer;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public QuestTrivia(int id, String question, String answer, String hint) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.hint = hint;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
